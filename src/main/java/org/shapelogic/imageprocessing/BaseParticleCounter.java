package org.shapelogic.imageprocessing;

import ij.IJ;

import java.awt.Rectangle;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.color.ColorFactory;
import org.shapelogic.color.ColorHypothesis;
import org.shapelogic.color.ColorUtil;
import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.color.IColorHypothesisFinder;
import org.shapelogic.imageutil.BaseImageOperation;

import org.shapelogic.imageutil.PixelArea;
import org.shapelogic.polygon.BBox;
import org.shapelogic.streams.ListStream;

import static org.shapelogic.imageutil.ImageJConstants.*;

/** ParticleCounter count number of particles in a particle image.
 * <br />
 * 
 * This is using the SBSegmentation that is also used by Segmenter.<br />
 * 
 * In this base implementation:<br />
 * Find a color hypothesis.<br />
 * Set everything in the background color into one segment.<br />
 * Segment the rest into normal segments regardless of color.<br />
 * 
 * If this is overridden then there can be more segments in the background color.<br />
 * 
 * How do I know what is background colored?<br />
 * 
 * @author Sami Badawi
 *
 */
public class BaseParticleCounter extends BaseImageOperation 
        implements IParticleCounter, RecursiveContext
{
	//These defaults are not fine tuned yet
    final static protected int ITERATIONS_DEFAULTS = 2;
    final static protected double MAX_DISTANCE_DEFAULTS = 50.;
    final static protected int MIN_PIXELS_IN_AREA_DEFAULTS = 5;
    final static protected int MAX_PIXELS_IN_AREA_DEFAULTS = 10000;
	
	protected Boolean _particleImage;
	
	/** Modifying colors */
	protected boolean _modifying = false;
	protected SBSegmentation _segmentation;

	/** Create a IColorAndVariance, area for each particle. */
	protected boolean _saveArea = true;
    protected IColorHypothesisFinder _colorHypothesisFinder;
    protected ColorHypothesis _colorHypothesis;
	protected int _backgroundArea;
	protected int _backgroundCount;
	protected Integer _particleCount;
	protected double _boundingBoxArea;
	
    protected int _iterations = ITERATIONS_DEFAULTS;
    protected double _maxDistance = MAX_DISTANCE_DEFAULTS;
    protected int _minPixelsInArea = MIN_PIXELS_IN_AREA_DEFAULTS;
    protected int _maxPixelsInArea = MAX_PIXELS_IN_AREA_DEFAULTS;
	protected boolean _displayTable = true;
	protected boolean _countOnly = false;
	protected boolean _toMask = false;
    protected boolean _displayInternalInfo = false;
    
    protected List<IColorAndVariance> _particlesOrig = new ArrayList<IColorAndVariance>();
    protected List<IColorAndVariance> _particlesFiltered = new ArrayList<IColorAndVariance>();
    
    protected ListStream<String> _categorizer;
	protected Integer _inputColor;
	protected Integer _backgroundColor;
	protected Integer _referenceColor = _inputColor;
	protected boolean _useReferenceAsBackground = true;
    
	protected Map _context;
	protected RecursiveContext _parentContext;

    protected int _paintForground = 0;
    protected int _paintBackground = 0xffffff;
	
	public BaseParticleCounter()
	{
		super(DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING);
	}

    @Override
	public void run() {
		try {
            init();
            findColorHypothesis();
			segment();
			globalFilter();
            if (!_countOnly) {
                defaultColumnDefinitions();
                customColumnDefinitions();
                defaultStreamDefinitions();
                customStreamDefinitions();
                categorizeStreams();
                populateResultsTable();
            }
			showResultDialog();
			if (_displayTable && !_countOnly) {
				displayResultsTable();
			}
			if (_displayInternalInfo && !_countOnly) {
				displayInternalInfo();
			}
		}
		catch (Exception ex) {
			String errorMessage = "Error in run: " + ex.toString();
			showMessage(getClass().getSimpleName(), errorMessage);
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			IJ.log(sw.getBuffer().toString());
		}
	}
    
    /** Everything related to setting the background color.
     * 
     * TODO move all the set color stuff from segment() up her.
     * 
     * */
    protected void findColorHypothesis() {
    	if (_inputColor != null) {
            _particleImage = Boolean.TRUE;
    		_referenceColor = _inputColor;
    		_backgroundCount = 1;
    		return;
    	}
        _colorHypothesis = _colorHypothesisFinder.findBestColorHypothesis();
        if (_colorHypothesis.getBackground() == null) {
            _particleImage = Boolean.FALSE;
        }
        else {
        	_backgroundColor = _colorHypothesis.getBackground().getMeanColor();
    		_referenceColor = _backgroundColor;
        }
	}

    protected void segment() {
        if (_referenceColor != null) {
        	_segmentation.setMaxDistance((int)_maxDistance);
        	if (!_useReferenceAsBackground) {
	            _segmentation.setFarFromReferencColor(true);
	            _segmentation.segmentAll(_referenceColor);
	            _segmentation.setFarFromReferencColor(false);
        	}
        	else {
	            _segmentation.setFarFromReferencColor(false);
	            _segmentation.segmentAll(_referenceColor);
        	}
            //count how many components and how much area the background takes up
            findBackground();
            //Run over all set point and set them to background color
            if (_toMask) {
                if (getImage().isInvertedLut()) {
                    _paintBackground = 0;
                    _paintForground = 255;
                }
                else {
                    _paintBackground = 0xffffff;
                    _paintForground = 0;
                }
                int pixelsInImage = getImage().getHeight() * getImage().getWidth();
                for (int i = 0; i < pixelsInImage; i++)
                    if (_segmentation.pixelIsHandled(i))
                        getImage().set(i, _paintBackground);
            }
            //segment all the remaining
            if (_particleImage != null && _particleImage) {
                _segmentation.setMaxDistance(1000000000);//Everything get lumped together
                int effectiveColor = _paintBackground; //Background
                while (_segmentation.hasNext()) {
                    ArrayList<SBPendingVertical> lines = _segmentation.next();
                if (_minPixelsInArea <= _segmentation.getCurrentArea() &&
                        _segmentation.getCurrentArea() < _maxPixelsInArea)
                    effectiveColor = _paintForground;
                else
                    effectiveColor = _paintBackground;
                    if (_toMask) {
                        _segmentation.paintSegment(lines, effectiveColor);
                    }
                }
            }
        }
	}
    
    protected void globalFilter() {
    	_particlesOrig.clear();
    	final List<IColorAndVariance> store = _segmentation.getSegmentAreaFactory().getStore();
    	for (int i=_backgroundCount;i<store.size();i++) {
    		_particlesOrig.add( store.get(i) );
    	}
    	
    	_particlesFiltered.clear();
    	for (IColorAndVariance particle: _particlesOrig) {
    		int particleArea = particle.getArea();
    		if ((_minPixelsInArea <= particleArea) && (particleArea <= _maxPixelsInArea) )
    			_particlesFiltered.add( particle );
    	}
    	
    }

    /** Define extra streams and also extra columns.*/
    protected void defaultStreamDefinitions() {
    	
    }
    
    /** Define extra streams and also extra columns.*/
    protected void customStreamDefinitions() {
    	
    }
    
	/** Analyzes particles and group them.<br />*/
    protected void categorizeStreams() {
    	
    }
    
    /** Setup all the stream and other needed things. */
    protected void defaultColumnDefinitions() {
    	
    }
    
    /** Define extra streams and also extra columns.*/
    protected void customColumnDefinitions() {
    	
    }
    
    /** Populate the table with the streams. */
    protected void populateResultsTable() {
    	List<IColorAndVariance> particles = _particlesFiltered;
    	for (int i=0;i<particles.size();i++) {
    		if (populateResultsTableRow(i))
    			populateResultsTableRowCustom(i);
    	}
    }
    
    /** Populate one row of the result table with the default fields. */
    protected boolean populateResultsTableRow(int index) {
    	return false;
    }
    
    /** Populate one row of the result table with the extra fields. */
    protected void populateResultsTableRowCustom(int index) {
    	
    }
    
    protected void displayResultsTable() {
    	
    }

    protected void showResultDialog() {
		showMessage(getClass().getSimpleName(), getStatus());
    }

    public void displayInternalInfo() {
    
    }
    
	public StringBuffer getInternalInfo() {
		return null;
	}

	/** Setup all the needed factory methods based on what type the image has.
     * 
     * @throws java.lang.Exception
     */
    protected void init() throws Exception {
    	_context = new HashMap();
		SBSimpleCompare compare = ProcessingFactory.compareFactory(getImage());
		compare.setModifying(_modifying);
		_segmentation = new SBSegmentation();
		_segmentation.setSLImage(getImage());
		_segmentation.setPixelCompare(compare);
		if (_saveArea)
			_segmentation.setSegmentAreaFactory(ColorFactory.segmentAreaFactory(getImage()));
		_segmentation.init();
        _colorHypothesisFinder = new DistanceBasedColorHypothesisFinder(_arg, _image, _maxDistance);
        _colorHypothesisFinder.setIterations(_iterations);
    }
	
    @Override
	public String getStatus() {
		String status = _segmentation.getStatus();
		String negation = "";
		if (!isParticleImage())
			negation = "not ";
		status += "\nImage is " + negation + "a particle image.";
		if (isParticleImage()) {
			status += "\nParticle count: " + getParticleCount();
            status += "\nBackground color is: " + ColorUtil.colorToString(_referenceColor,_image.isRgb());
            if (_colorHypothesis != null && _colorHypothesis.getColors() != null)
            	status += "\nNumber of colors is: " + _colorHypothesis.getColors().size();
		}
		return status;
	}

    @Override
	public SBSegmentation getSegmentation() {
		return _segmentation;
	}

    /** This is not fine tuned. */
    @Override
	public boolean isParticleImage() {
		if (_particleImage == null) {
        double totalImageArea = getImageArea();
        double biggestAreaPercentage = _backgroundArea * 100 / totalImageArea;
        double boundingBoxPercentage = _boundingBoxArea * 100 / totalImageArea;
        _particleImage =  50 < biggestAreaPercentage && 
                90 < boundingBoxPercentage;
		}
		return _particleImage;
	}

    /** Count background pixels.<br />
     *  
     *  Should be called when only background have been segmented.<br />
     *  Not sure that this really makes sense, or I can assume that there is always 1 background.<br />
     *  
     * */
    protected boolean findBackground() {
        boolean result = false;
    	_backgroundArea = 0;
		List<IColorAndVariance> store = _segmentation.getSegmentAreaFactory().getStore();
        BBox aggregatedBoundingBox = new BBox();
        for (IColorAndVariance area: store) {
            _backgroundArea += area.getArea();
            PixelArea pixelArea = area.getPixelArea();
            if (pixelArea != null) {
                aggregatedBoundingBox.add(pixelArea.getBoundingBox());
            }
        }
        //XXX should take all with new method
        _backgroundCount = _segmentation.getSegmentAreaFactory().getStore().size();
        _boundingBoxArea = 
                (aggregatedBoundingBox.getDiagonalVector().getX() +1) * 
                (aggregatedBoundingBox.getDiagonalVector().getY() + 1);
    	if (_inputColor == null)
    		result = isParticleImage();
    	else
    		result = true;
        return result;
    }
    
    public double getImageArea() {
        Rectangle rectangle = getImage().getRoi();
        if (rectangle != null)
            return rectangle.getWidth() * rectangle.getHeight();
        else
            return getImage().getPixelCount();
    }
    
    @Override
	public int getParticleCount() {
        if (_particleCount == null) {
            _particleCount = _particlesFiltered.size();
        }
        return _particleCount;
	}

	public List<IColorAndVariance> getParticleFiltered() {
        return _particlesFiltered;
	}
    
    @Override
    public IColorHypothesisFinder getColorHypothesisFinder() {
        return _colorHypothesisFinder;
    }

    @Override
    public void setColorHypothesisFinder(IColorHypothesisFinder colorHypothesisFinder) {
        _colorHypothesisFinder = colorHypothesisFinder;
    }
    
    @Override
    public double getMaxDistance() {
        return _maxDistance;
    }
    
    @Override
    public void setMaxDistance(double maxDistance) {
        _maxDistance = maxDistance;
    }

    @Override
    public int getMinPixelsInArea() {
        return _minPixelsInArea;
    }

    @Override
    public void setMinPixelsInArea(int minPixelsInArea) {
        _minPixelsInArea = minPixelsInArea;
    }

    @Override
    public int getIterations() {
        return _iterations;
    }

    @Override
    public void setIterations(int iterations) {
        _iterations = iterations;
    }

	public void setDisplayTable(boolean table) {
		_displayTable = table;
	}

	public Integer getInputColor() {
		return _inputColor;
	}

	public void setInputColor(Integer color) {
		_inputColor = color;
	}

	public boolean isUseReferenceAsBackground() {
		return _useReferenceAsBackground;
	}

	public void setUseReferenceAsBackground(boolean referenceAsBackground) {
		_useReferenceAsBackground = referenceAsBackground;
	}
	
	@Override
	public Map getContext() {
		return _context;
	}

	@Override
	public RecursiveContext getParentContext() {
		return _parentContext;
	}

    public void setToMask(boolean toMask) {
        _toMask = toMask;
    }
}
