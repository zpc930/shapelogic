package org.shapelogic.imageprocessing;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.io.OpenDialog;
import ij.measure.ResultsTable;
import ij.plugin.filter.ExtendedPlugInFilter;
import ij.plugin.filter.PlugInFilterRunner;
import ij.process.ImageProcessor;

import java.io.File;
import org.shapelogic.imageutil.IJGui;
import org.shapelogic.imageutil.IJImage;
import org.shapelogic.imageutil.SLImage;
import org.shapelogic.reporting.IJTableBuilder;
import org.shapelogic.util.Headings;

/** Automatic particle counter for 24 bit RGB and 8 bit Gray.
 * <br />
 * 
 * Works with rectangular ROIs<br />
 * 
 * @author Sami Badawi
 *
 */
public class ColorParticleAnalyzerIJ extends ColorParticleAnalyzer implements ExtendedPlugInFilter {

	protected ResultsTable _rt;
    protected GenericDialog _gd;
    
    protected static int _iterationsStatic = ITERATIONS_DEFAULTS;
    protected static double _maxDistanceStatic = MAX_DISTANCE_DEFAULTS;
    protected static int _minPixelsInAreaStatic = MIN_PIXELS_IN_AREA_DEFAULTS;
    protected static int _maxPixelsInAreaStatic = MAX_PIXELS_IN_AREA_DEFAULTS;
    protected static boolean _countOnlyStatic = false;
    protected static boolean _toMaskStatic = false;
    protected static boolean _displayInternalInfoStatic = false;
    protected static boolean _useNeuralNetworkStatic = false;
    protected static String _neuralNetworkFileStatic = null;
    
	@Override
	public void run(ImageProcessor ip) {
		run();
	}

	@Override
	public int setup(String arg, ImagePlus imp) {
		_guiWrapper = IJGui.INSTANCE;
		if (imp == null)
			return setup(arg, (SLImage)null);
		return setup(arg, new IJImage(imp));
	}
	
	@Override
	protected void displayResultsTable() {
    	_rt.show("Particle properties");
	}

	@Override
	public void setNPasses(int passes) {
	}

	@Override
	public int showDialog(ImagePlus imp, String command, PlugInFilterRunner pfr) {
        _gd = new GenericDialog(getClass().getSimpleName(), IJ.getInstance());
        _gd.addNumericField("Max_distance", _maxDistanceStatic, 0);
        _gd.addNumericField("Min_pixels", _minPixelsInAreaStatic, 0);
        _gd.addNumericField("Max_pixels", _maxPixelsInAreaStatic, 0);
        _gd.addNumericField("Iterations", _iterationsStatic, 0);
        _gd.addCheckbox("CountOnly", _countOnlyStatic);
        _gd.addCheckbox("ToMask", _toMaskStatic);
        _gd.addCheckbox("DisplayInternalInfo: ", _displayInternalInfoStatic);
        _gd.addCheckbox("UseNeuralNetwork", _useNeuralNetworkStatic);
        _gd.addCheckbox("ShowFileDialog", false);
        _gd.addStringField("NeuralNetworkFile", _neuralNetworkFileStatic, 50); 
        _gd.showDialog();
        if (_gd.wasCanceled()) {
            return DONE;
        }
        _maxDistance = _maxDistanceStatic = _gd.getNextNumber();
        _minPixelsInArea = _minPixelsInAreaStatic = (int)_gd.getNextNumber();
        _maxPixelsInArea = _maxPixelsInAreaStatic = (int)_gd.getNextNumber();
        _iterations = _iterationsStatic = (int)_gd.getNextNumber();
        _countOnly = _countOnlyStatic = _gd.getNextBoolean();
        _toMask = _toMaskStatic = _gd.getNextBoolean();
        _displayInternalInfo = _displayInternalInfoStatic = _gd.getNextBoolean();
        _useNeuralNetwork = _useNeuralNetworkStatic = _gd.getNextBoolean();
        boolean showFileDialog = _gd.getNextBoolean();
        String tempFilePath = null;
        if (showFileDialog) {
            OpenDialog od = new OpenDialog("Choose a .txt config file", null);
            tempFilePath = getFilePath(od);
            if (tempFilePath != null)
                _neuralNetworkFile = _neuralNetworkFileStatic = tempFilePath;
        }
        if (tempFilePath == null)
            _neuralNetworkFile = _neuralNetworkFileStatic = _gd.getNextString();
        return IJ.setupDialog(imp, _setupReturnValue);
	}

    public static String getFilePath(OpenDialog od) {
        String dir = od.getDirectory();
        String fileName = od.getFileName();
        if (dir != null && fileName != null) {
            File file = new File(dir, fileName);
            return file.getPath();
        }
        else
            return null;
    }

	@Override
	protected void setupTableBuilder() {
	    _rt = new ResultsTable();
        _tableBuilder = new IJTableBuilder(_tableDefinition, _rt);
    }

	@Override
	protected boolean populateResultsTableRow(int index) {
		try {
            _tableBuilder.buildLine(index);
			return true;
		} catch (RuntimeException e) {
			String errorMessage = "Error: " + e.getMessage();
			_rt.addLabel(Headings.CATEGORY, errorMessage);
			System.out.println(errorMessage);
			e.printStackTrace();
			return false;
		}
	}

	@Override
    public void displayInternalInfo() {
		StringBuffer result = getInternalInfo();
        IJ.log(result.toString());
    }
}

