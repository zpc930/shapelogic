package org.shapelogic.imageutil;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** Shows how how to call one PlugInFilter from another.<br />
 * 
 * Used to be called org.shapelogic.imageutil.PlugInFilterCaller_, 
 * but that caused it to show up in the ImageJ memu.<br />
 * 
 * This is important for unit testing.
 * 
 * @author Sami Badawi
 *
 */
public class PlugInFilterCaller implements PlugInFilter {
	
	String _pluginName;
	String _arg;
	int _returnValueForSetup;
	
//	/** Default is to call dilate from ImageJ Binary. */
//	public PlugInFilterCaller_() {
//		this("ij.plugin.filter.Binary", "dilate", DOES_8G);
//	}
	
	/** Default is to call ShapeLogic segmenter. */
	public PlugInFilterCaller() {
		this("SBSegment_", "", DOES_8G+DOES_RGB+DOES_STACKS+SUPPORTS_MASKING);
	}
	
	/** Use this to setup your own plugin runner. */
	public PlugInFilterCaller(String pluginName, String arg, int returnValueForSetup) {
		_pluginName = pluginName;
		_arg = arg;
		_returnValueForSetup = returnValueForSetup;
	}
	
	/** Empty everything is done in setup */
	public void run(ImageProcessor ip) {
	}

	public int setup(String arg, ImagePlus imp) {
		IJ.runPlugIn(_pluginName, _arg);
		return _returnValueForSetup;
	}
}
