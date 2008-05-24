package org.shapelogic.imageutil;

/** PixelHandlerOperation excecutes a PixelHandler on an image.<br />
 *
 * @author Sami Badawi
 */
public class PixelHandlerOperation extends BaseImageOperation {
    protected PixelHandler _pixelHandler;
    protected PixelAreaHandler _pixelAreaHandler;
    
    public PixelHandlerOperation(int setupReturnValue, String arg, SLImage image,
        PixelHandler pixelHandler) 
    {
        super(setupReturnValue,arg,image);
        _pixelHandler = pixelHandler;
        _pixelAreaHandler = new PixelAreaHandler(_image);
    }
    
    public PixelHandlerOperation(PixelHandler pixelHandler) 
    {
        super();
        _pixelHandler = pixelHandler;
        _pixelAreaHandler = new PixelAreaHandler(null);
    }
    
    @Override
    public void run() {
        if (_pixelHandler instanceof HasSLImage)
            ((HasSLImage)_pixelHandler).setImage(_image);
        _pixelAreaHandler.setImage(_image);
        if (_image.getRoi() == null)
            _pixelAreaHandler.handleAllPixels(_pixelHandler);
        else
            _pixelAreaHandler.handlePixelArea(_pixelHandler, _image.getRoi());
    }
    
    public PixelHandler getPixelHandler() {
        return _pixelHandler;
    }
}
