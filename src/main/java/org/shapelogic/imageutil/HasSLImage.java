package org.shapelogic.imageutil;

/** Property interface for SLImage.
 * 
 * @author Sami Badawi
 */
public interface HasSLImage {
	
    /** Should only be implemented if the PixelHandler needs to know what image
     * it is using.
     */
    void setImage(SLImage image);
    
}
