package org.shapelogic.imageprocessing;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.measure.ResultsTable;
import ij.plugin.filter.ExtendedPlugInFilter;
import ij.plugin.filter.PlugInFilterRunner;
import ij.process.ImageProcessor;

import org.shapelogic.imageutil.IJGui;
import org.shapelogic.imageutil.IJImage;
import org.shapelogic.imageutil.SLImage;
import org.shapelogic.reporting.IJTableBuilder;
import org.shapelogic.util.Headings;

/** Line vectorizer and categorizer as an ImageJ ExtendedPlugInFilter.<br />
 * <br />
 * 
 * Works with rectangular ROIs<br />
 * 
 * @author Sami Badawi
 *
 */
public class StreamVectorizerIJ extends StreamVectorizer implements ExtendedPlugInFilter {

	protected ResultsTable _rt;
    protected GenericDialog _gd;
    
    protected static boolean _noMatchStatic = false;
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
        if (arg != null && arg.indexOf("InternalInfo") != -1)
        	_useInputDilog = true;
		if (imp == null)
			return setup(arg, (SLImage)null);
		return setup(arg, new IJImage(imp));
	}
	
//	@Override
	protected void displayResultsTable() {
    	_rt.show("Polygon properties");
	}

	@Override
	public void setNPasses(int passes) {
	}

	@Override
	public int showDialog(ImagePlus imp, String command, PlugInFilterRunner pfr) {
		if (_useInputDilog) {
	        _gd = new GenericDialog(getClass().getSimpleName(), IJ.getInstance());
	        _gd.addCheckbox("DisplayInternalInfo: ", _displayInternalInfoStatic);
	        _gd.addCheckbox("UseNeuralNetwork", _useNeuralNetworkStatic);
	        _gd.addStringField("NeuralNetworkFile", _neuralNetworkFileStatic, 50); 
	        _gd.showDialog();
	        if (_gd.wasCanceled()) {
	            return DONE;
	        }
	        _displayInternalInfo = _displayInternalInfoStatic = _gd.getNextBoolean();
	        _useNeuralNetwork = _useNeuralNetworkStatic = _gd.getNextBoolean();
	        _neuralNetworkFile = _neuralNetworkFileStatic = _gd.getNextString();
		}
        return IJ.setupDialog(imp, _setupReturnValue);
	}

//	@Override
	protected void setupTableBuilder() {
	    _rt = new ResultsTable();
        _tableBuilder = new IJTableBuilder(_tableDefinition, _rt);
    }

//	@Override
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

//	@Override
//    public void displayInternalInfo() {
//		StringBuffer result = getInternalInfo();
//        IJ.log(result.toString());
//    }
}

