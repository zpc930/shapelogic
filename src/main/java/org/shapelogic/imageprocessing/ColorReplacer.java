package org.shapelogic.imageprocessing;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.filter.ExtendedPlugInFilter;
import ij.plugin.filter.PlugInFilterRunner;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import java.awt.AWTEvent;
import java.awt.Rectangle;

import org.shapelogic.color.ColorDistance1;
import org.shapelogic.color.ColorDistance1RGB;
import org.shapelogic.color.ColorUtil;
import org.shapelogic.color.IColorDistance;

/** ColorReplacer replaces one color with another.<br />
 *
 * This is for comparison of how the direct ImageJ way compares to the 
 * ImageOperation working on SLImage.
 * 
 * @author Sami Badawi
 */
public class ColorReplacer implements ExtendedPlugInFilter, DialogListener {
    private int _flags = DOES_ALL|SUPPORTS_MASKING|PARALLELIZE_STACKS;
    private static double _maxDistance = 15.0;
    private int _referenceColor = 1;
	protected static int _rStatic = 0;
	protected static int _gStatic = 0;
	protected static int _bStatic = 0;
    protected IColorDistance _colorDistance;

    GenericDialog _gd;
    PlugInFilterRunner _pfr;

    @Override
    public int showDialog(ImagePlus imp, String command, PlugInFilterRunner pfr) {
        this._pfr = pfr;
        _gd = new GenericDialog(getClass().getSimpleName(), IJ.getInstance());
        _gd.addNumericField("Max distance: ", _maxDistance, (int)_maxDistance==_maxDistance?1:2);
        _gd.addNumericField("RGB_R: ", _rStatic, _rStatic);
        _gd.addNumericField("RGB_G: ", _gStatic, _gStatic);
        _gd.addNumericField("RGB_B / Gray: ", _bStatic, _bStatic);
        _gd.addPreviewCheckbox(pfr);
        _gd.addDialogListener(this);
        _gd.showDialog();
        if (_gd.wasCanceled()) {
            return DONE;
        }
        _flags |= KEEP_PREVIEW;      // standard filter without enlarge
//        if (imp.getStackSize()==1)
//            _flags |= NO_CHANGES;            // undoable as a "compound filter"
        //To make macros work with parameters
        _maxDistance = _gd.getNextNumber();
        int r = _rStatic = (int)_gd.getNextNumber();
        int g = _gStatic = (int)_gd.getNextNumber();
        int b = _bStatic = (int)_gd.getNextNumber();
        _referenceColor= ColorUtil.packColors( r, g, b);
        _colorDistance.setReferenceColor(_referenceColor);
        return IJ.setupDialog(imp, _flags);
    }

    @Override
    public void setNPasses(int arg0) {
    }

    @Override
    public int setup(String arg, ImagePlus imp) {
        if (imp.getProcessor() instanceof ColorProcessor) {
        	_colorDistance = new ColorDistance1RGB();
        }
        else {
        	_colorDistance = new ColorDistance1();
        }
        return _flags;
    }

    @Override
    public boolean dialogItemChanged(GenericDialog gd, AWTEvent e) {
        _maxDistance = gd.getNextNumber();
        if (gd.invalidNumber()) {
            if (gd.wasOKed()) IJ.error("Angle is invalid.");
            return false;
        }
        int r = _rStatic = (int)_gd.getNextNumber();
        int g = _gStatic = (int)_gd.getNextNumber();
        int b = _bStatic = (int)_gd.getNextNumber();
        _referenceColor= ColorUtil.packColors( r, g, b);
        _colorDistance.setReferenceColor(_referenceColor);
        return true;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
    	int currentColor;
    	int startLine = 0;
    	int endLine = imageProcessor.getHeight();
    	int startX = 0;
    	int endX = imageProcessor.getWidth();
		Rectangle rectangle = imageProcessor.getRoi();
    	if (rectangle != null) {
    		startX = rectangle.x;
    		startLine = rectangle.y;
    		endX = startX + rectangle.width;
    		endLine = startLine + rectangle.height;
    	}
        else {
    		startX = 0;
    		startLine = 0;
    		endX = imageProcessor.getWidth();
    		endLine = imageProcessor.getHeight();
        }
    	for (int y = startLine; y < endLine; y++) {
        	for (int x = startX; x < endX; x++) {
        		currentColor = imageProcessor.get(x, y);
        		if (_colorDistance.distanceToReferenceColor(currentColor) < _maxDistance) {
        			imageProcessor.set(x, y,_referenceColor);
        		}
        	}
    	}
    }
}
