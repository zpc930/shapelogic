package org.shapelogic.imageutil;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** Adapter from SLImageFilter to PlugInFilter.
 * 
 * @author sbadawi
 *
 */
public class PlugInFilterAdapter implements PlugInFilter {
	
	protected BaseSLImageFilter _slImageFilter;
	
	public PlugInFilterAdapter(BaseSLImageFilter filter){
		_slImageFilter = filter;
	}
	
	@Override
	public void run(ImageProcessor ip) {
		_slImageFilter.run();
	}

	@Override
	public int setup(String arg, ImagePlus imp) {
		_slImageFilter.setGuiWrapper(IJGui.INSTANCE);
		return _slImageFilter.setup(arg, new IJImage(imp));
	}

}
