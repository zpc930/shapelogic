package org.shapelogic.imageprocessing;

import org.shapelogic.color.ColorDistance1;
import org.shapelogic.color.ColorDistance1RGB;
import org.shapelogic.color.IColorDistance;
import org.shapelogic.imageutil.HasSLImage;
import org.shapelogic.imageutil.PixelHandlerEnds;
import org.shapelogic.imageutil.SLImage;

/** Handles Color Replace for individual pixel, used with wrapper to work on whole image.<br />
 *
 * @author Sami Badawi
 */
public class ColorReplacePixelHandler implements PixelHandlerEnds, HasSLImage {
    protected static double _maxDistance = 15.0;
    protected static int _referenceColor = 1;
    protected IColorDistance _colorDistance;
    protected SLImage _image;

    public ColorReplacePixelHandler() {
    }
    
    public void setup() {
        if (_image == null || _image.isEmpty())
            return;
        if (_image.isRgb()) {
        	_colorDistance = new ColorDistance1RGB();
        }
        else {
        	_colorDistance = new ColorDistance1();
        }
        _colorDistance.setReferenceColor(_referenceColor);
    }

    @Override
    public void putPixel(int x, int y, int color) {
        int currentColor = _image.get(x, y);
        if (_colorDistance.distanceToReferenceColor(currentColor) < _maxDistance) {
            _image.set(x, y,_referenceColor);
        }
    }
    
    public IColorDistance getColorDistance() {
        return _colorDistance;
    }

    public void setColorDistance(IColorDistance _colorDistance) {
        this._colorDistance = _colorDistance;
    }

    public static double getMaxDistance() {
        return _maxDistance;
    }

    public static void setMaxDistance(double maxDistance) {
        ColorReplacePixelHandler._maxDistance = maxDistance;
    }

    public static int getReferenceColor() {
        return _referenceColor;
    }

    public static void setReferenceColor(int referenceColor) {
        _referenceColor = referenceColor;
    }

    @Override
    public void setImage(SLImage image) {
        _image = image;
        setup();
    }

    @Override
    public void handlePixelStart(int x, int y, int color) {
    }

    @Override
    public void handlePixelEnd(int x, int y, int color) {
    }

    @Override
    public void postProcess() {
    }
}
