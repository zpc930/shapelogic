package org.shapelogic.imageprocessing;

/** Interface that are used for several Vectorizer.
 * 
 * @author Sami Badawi
 *
 */
public interface IPixelTypeFinder extends PixelJumperByte {
	PixelTypeCalculator findPointType(int pixelIndex, PixelTypeCalculator reusedPixelTypeCalculator);
}
