package org.shapelogic.imageutil;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

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

	/** Opens an image and run a PlugInFilter on it. */
	static public SLImage runPluginFilterOnImage(String fileName, ImageOperation plugInFilter) {
		IJImage image = new IJImage(fileName);
		plugInFilter.setup("", image);
		plugInFilter.run();
		return image;
	}

	/** Opens an image and run a PlugInFilter on it. */
	static public SLImage runPluginFilterOnBufferedImage(String fileName, ImageOperation plugInFilter) {
		SLImage image = new SLBufferedImage(fileName);
		if (!image.isEmpty()) {
			plugInFilter.setup("", image);
			plugInFilter.run();
		}
		return image;
	}
	
	/** Crate any subclass of Image to a BufferedImage.<br />
	 * 
	 * @param image input
	 * @param newModelId e.g. BufferedImage.TYPE_INT_RGB
	 * @return
	 */
	public static BufferedImage toBufferedImage(Image image, int newModelId) {
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), 
				image.getHeight(null), newModelId);
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.drawImage(image, 0, 0, null);
		return bufferedImage;
	}
	
}
