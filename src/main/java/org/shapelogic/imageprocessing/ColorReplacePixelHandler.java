package org.shapelogic.imageprocessing;

import org.shapelogic.color.ColorDistance1;
import org.shapelogic.color.ColorDistance1RGB;
import org.shapelogic.color.IColorDistance;
import org.shapelogic.imageutil.HasSLImage;
import org.shapelogic.imageutil.PixelHandler;
import org.shapelogic.imageutil.SLImage;

/** Handles Color Replace for individual pixel, used with wrapper to work on whole image.<br />
 * 
 * Example of a PixelHandler that ca be used to change a whole image.<br />
 * 
 * @author Sami Badawi
 */
public class ColorReplacePixelHandler implements PixelHandler, HasSLImage {
    //Direct simple parameters are not just public fields without getters and setters.
    public static double maxDistance = 15.0;
    public static int referenceColor = 1;
    protected IColorDistance _colorDistance;
    protected SLImage _image;

    public void setup() {
        if (_image == null || _image.isEmpty())
            return;
        if (_colorDistance == null) {
            if (_image.isRgb()) {
                _colorDistance = new ColorDistance1RGB();
            }
            else {
                _colorDistance = new ColorDistance1();
            }
        }
        _colorDistance.setReferenceColor(referenceColor);
    }

    @Override
    public void putPixel(int x, int y, int color) {
        int currentColor = _image.get(x, y);
        if (_colorDistance.distanceToReferenceColor(currentColor) < maxDistance) {
            _image.set(x, y,referenceColor);
        }
    }
    
    public IColorDistance getColorDistance() {
        return _colorDistance;
    }

    public void setColorDistance(IColorDistance _colorDistance) {
        this._colorDistance = _colorDistance;
    }

    @Override
    public void setImage(SLImage image) {
        _image = image;
        setup();
    }
}
