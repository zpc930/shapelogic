package org.shapelogic.imageprocessing;

import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/** Utility class for images. <br />
 * 
 * @author Sami Badawi
 *
 */
public class ImageUtil {

	/** Opens an image and run a PlugInFilter on it. */
	static public ImageProcessor runPluginFilterOnImage(String fileName, PlugInFilter plugInFilter) {
		Opener opener = new Opener();
		ImagePlus image = opener.openImage(fileName);
		ImageProcessor ip = image.getProcessor();
		plugInFilter.setup("", image);
		plugInFilter.run(ip);
		return ip;
	}

}
