package org.shapelogic.imageprocessing;

import org.shapelogic.imageprocessing.PixelType;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class PixelTypeTest extends TestCase {

	public void testByteToInt() {
		byte allOneByte = -1;
		byte allOneByte2 = (byte)255;
		int minusOneInt = -1;
		int int255 = 255;
		assertEquals(allOneByte,allOneByte2);
		assertEquals(allOneByte,minusOneInt);
		int byteToInt = allOneByte2 & int255;
		assertEquals(int255, byteToInt); 
	}
	
	public void testGetPixelType() {
		for (PixelType pixelType: PixelType.values()){
			assertEquals(pixelType.color, PixelType.getPixelType(pixelType.color).color);
		}
	}

	public void testGetColorUsed() {
		for (PixelType pixelType: PixelType.values()){
			assertEquals("Color: " + pixelType,pixelType.color, PixelType.getPixelType(pixelType.getColorUsed()).color);
		}
	}
	
}
