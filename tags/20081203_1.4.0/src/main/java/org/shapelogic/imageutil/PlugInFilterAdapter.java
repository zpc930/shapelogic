package org.shapelogic.imageutil;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** Adapter from SLImageFilter to PlugInFilter.
 * 
 * @author Sami Badawi
 *
 */
public class PlugInFilterAdapter implements PlugInFilter {
	
	protected ImageOperation _imageOperation;
	
	public PlugInFilterAdapter(ImageOperation imageOperation){
		_imageOperation = imageOperation;
	}
	
	@Override
	public void run(ImageProcessor ip) {
		_imageOperation.run();
	}

	/** Default is that if the input image is null show the about dialog. */
	@Override
	public int setup(String arg, ImagePlus imp) {
		_imageOperation.setGuiWrapper(IJGui.INSTANCE);
		if (imp == null)
			return _imageOperation.setup(arg, null);
		return _imageOperation.setup(arg, new IJImage(imp));
	}
    
    public ImageOperation getImageOperation() {
        return _imageOperation;
    }
}
