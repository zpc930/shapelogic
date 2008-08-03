package org.shapelogic.imageprocessing;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilterRunner;

import org.shapelogic.color.ColorUtil;

/** Particle analyzer where you set the RGB color for either foreground or background.
 * <br />
 * 
 * Works with rectangular ROIs<br />
 * 
 * @author Sami Badawi
 *
 */
public class RGBColorParticleAnalyzerIJ extends ColorParticleAnalyzerIJ {
	
	protected static int _rStatic = 0;
	protected static int _gStatic = 0;
	protected static int _bStatic = 0;
	protected static boolean _backgroundStatic = true;
	
	
	@Override
	public int showDialog(ImagePlus imp, String command, PlugInFilterRunner pfr) {
        _gd = new GenericDialog(getClass().getSimpleName(), IJ.getInstance());
        _gd.addNumericField("Max_distance: ", _maxDistanceStatic, 0);
        _gd.addNumericField("Min_pixels: ", _minPixelsInAreaStatic, 0);
        _gd.addNumericField("Max_pixels: ", _maxPixelsInAreaStatic, 0);
        _gd.addNumericField("RGB_R: ", _rStatic, 0);
        _gd.addNumericField("RGB_G: ", _gStatic, 0);
        _gd.addNumericField("RGB_B / Gray: ", _bStatic, 0);
        _gd.addCheckbox("Background", _backgroundStatic);
        
        _gd.showDialog();
        if (_gd.wasCanceled()) {
            return DONE;
        }
        _maxDistance = _maxDistanceStatic = _gd.getNextNumber();
        _minPixelsInArea = _minPixelsInAreaStatic = (int)_gd.getNextNumber();
        _maxPixelsInArea = _maxPixelsInAreaStatic = (int)_gd.getNextNumber();
        int r = _rStatic = (int)_gd.getNextNumber();
        int g = _gStatic = (int)_gd.getNextNumber();
        int b = _bStatic = (int)_gd.getNextNumber();
        int rgbValue = ColorUtil.packColors( r, g, b);
        _inputColor = rgbValue;
        _useReferenceAsBackground = _backgroundStatic = _gd.getNextBoolean();
        return IJ.setupDialog(imp, _setupReturnValue);
	}

}

