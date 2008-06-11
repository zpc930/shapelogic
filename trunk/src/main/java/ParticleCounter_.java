import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.measure.ResultsTable;
import ij.plugin.filter.ExtendedPlugInFilter;
import ij.plugin.filter.PlugInFilterRunner;
import ij.process.ImageProcessor;

import java.util.List;

import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.imageprocessing.ParticleCounter;
import org.shapelogic.imageutil.IJGui;
import org.shapelogic.imageutil.IJImage;
import org.shapelogic.imageutil.PixelArea;
import org.shapelogic.imageutil.SLImage;

/** Automatic particle counter for 24 bit RGB and 8 bit Gray.
 * <br />
 * Currently just a wrapper around segmenter.
 * 
 * Works with rectangular ROIs
 * 
 * @author Sami Badawi
 *
 */
public class ParticleCounter_ extends ParticleCounter implements ExtendedPlugInFilter {

	static final String COLOR = "Color";
	
    protected ResultsTable _rt = new ResultsTable();
    protected GenericDialog _gd;
    
	@Override
	public void run(ImageProcessor ip) {
		run();
		populateTable();
	}

	@Override
	public int setup(String arg, ImagePlus imp) {
		_guiWrapper = IJGui.INSTANCE;
		if (imp == null)
			return setup(arg, (SLImage)null);
		return setup(arg, new IJImage(imp));
		
	}
	
    void populateTable() {
    	
    	List<IColorAndVariance> particles = _segmentation.getSegmentAreaFactory().getStore();
    	_rt.getFreeColumn(COLOR);
    	int i=0;
    	_rt.setDefaultHeadings();
    	for (IColorAndVariance particle: particles) {
        	if (particle == null)
        		continue;
        	if (particle.getArea() < getMinPixelsInArea())
        		continue;
        	_rt.incrementCounter();
        	_rt.addValue(ResultsTable.AREA, particle.getArea());
        	_rt.addValue(ResultsTable.STD_DEV, particle.getStandardDeviation());
        	_rt.addValue(COLOR, particle.getMeanColor());
        	PixelArea pixelArea = particle.getPixelArea();
        	if (pixelArea != null) {
            	_rt.addValue(ResultsTable.X_CENTER_OF_MASS, pixelArea.getCenterPoint().getX());
            	_rt.addValue(ResultsTable.Y_CENTER_OF_MASS, pixelArea.getCenterPoint().getY());
        	}
    		i++;
    	}
    	_rt.show("Particle properties");
    }

	@Override
	public void setNPasses(int passes) {
	}

	@Override
	public int showDialog(ImagePlus imp, String command, PlugInFilterRunner pfr) {
        _iterations = 3;
        _maxDistance = 70;
        _minPixelsInArea = 10;
        _gd = new GenericDialog(getClass().getSimpleName(), IJ.getInstance());
        _gd.addNumericField("Max distance: ", _maxDistance, 0);
        _gd.addNumericField("Min pixels: ", _minPixelsInArea, 0);
        _gd.addNumericField("Min pixels: ", _iterations, 0);
        _gd.showDialog();
        if (_gd.wasCanceled()) {
            return DONE;
        }
        _maxDistance = _gd.getNextNumber();
        _minPixelsInArea = (int)_gd.getNextNumber();
        _iterations = (int)_gd.getNextNumber();
        return IJ.setupDialog(imp, _setupReturnValue);
	}
}

