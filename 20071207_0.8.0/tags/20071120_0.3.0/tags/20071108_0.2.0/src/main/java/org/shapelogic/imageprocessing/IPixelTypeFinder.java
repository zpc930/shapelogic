package org.shapelogic.imageprocessing;

/** 
 * 
 * @author Sami Badawi
 *
 */
public interface IPixelTypeFinder extends PixelJumperByte {
	PixelTypeCalculator findPointType(int pixelIndex, PixelTypeCalculator reusedPixelTypeCalculator);
}
