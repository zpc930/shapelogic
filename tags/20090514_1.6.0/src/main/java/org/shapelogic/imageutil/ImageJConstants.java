package org.shapelogic.imageutil;

/** Constants defined in ImageJ.<br />
 * 
 * In order to decouple the image processing algorithm from ImageJ.
 * ShapeLogic need to have these constants so you do not need to add the ImageJ 
 * jar just to get them.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ImageJConstants {

	/** Set this flag if the filter handles 8-bit grayscale images. */
	public static final int DOES_8G = 1;
	/** Set this flag if the filter handles 8-bit indexed color images. */
	public static final int DOES_8C = 2;
	/** Set this flag if the filter handles 16-bit images. */
	public static final int DOES_16 = 4;
	/** Set this flag if the filter handles float images. */
	public static final int DOES_32 = 8;
	/** Set this flag if the filter handles RGB images. */
	public static final int DOES_RGB = 16;
	/** Set this flag if the filter handles all types of images. */
	public static final int DOES_ALL = DOES_8G+DOES_8C+DOES_16+DOES_32+DOES_RGB;
	/** Set this flag if the filter wants its run() method to be
		called for all the slices in a stack. */
	public static final int DOES_STACKS = 32;
	/** Set this flag if the filter wants ImageJ, for non-rectangular
		ROIs, to restore that part of the image that's inside the bounding
		rectangle but outside of the ROI. */
	public static final int SUPPORTS_MASKING = 64;
	/** Set this flag if the filter makes no changes to the pixel data and does not require undo. */
	public static final int NO_CHANGES = 128;
	/** Set this flag if the filter does not require undo. */
	public static final int NO_UNDO = 256;
	/** Set this flag if the filter does not require that an image be open. */
	public static final int NO_IMAGE_REQUIRED = 512;
	/** Set this flag if the filter requires an ROI. */
	public static final int ROI_REQUIRED = 1024;
	/** Set this flag if the filter requires a stack. */
	public static final int STACK_REQUIRED = 2048;
	/** Set this flag if the filter does not want its run method called. */
	public static final int DONE = 4096;
	/** Set this flag to have the ImageProcessor that is passed to the run() method 
		converted  to a FloatProcessor. With  RGB images, the run() method is  
		called three times, once for each channel. */
	public static final int CONVERT_TO_FLOAT = 8192;
	/** Set this flag if the filter requires a snapshot (copy of the pixels array). */
	public static final int SNAPSHOT = 16384;
    /** Set this flag if the filter wants to be called with arg = "dialog" after
        being invocated the first time */
    /** Set this flag if the slices of a stack may be processed in parallel threads */
    public static final int PARALLELIZE_STACKS = 32768;
    /** Set this flag if the setup method of the filter should be called again after
     *  the calls to the run(ip) have finished. The argument <code>arg</code> of setup
     *  will be "final" in that case. */
    public static final int FINAL_PROCESSING = 65536;
}
