package org.shapelogic.imageprocessing;

import junit.framework.TestCase;

import static org.shapelogic.imageprocessing.ColorUtil.*;

/** Test ColorUtil.
 * 
 * @author Sami Badawi
 *
 */
public class ColorUtilTest extends TestCase {
	
	final static int ONE_TWO_THREE = 0x10203; // = 66051
	final static int[] THREE_TWO_ONE_ARRAY = {3,2,1};
	
	public void testSplit() {
		assertEquals(1,splitRed(ONE_TWO_THREE));
		assertEquals(2,splitGreen(ONE_TWO_THREE));
		assertEquals(3,splitBlue(ONE_TWO_THREE));
		int[] split = splitColor(ONE_TWO_THREE);
		for (int i=0; i<THREE_TWO_ONE_ARRAY.length; i++)
			assertEquals(THREE_TWO_ONE_ARRAY[i],split[i]);
		
		int[] splitColor2 = new int[3];
		splitColor(ONE_TWO_THREE,splitColor2);
		for (int i=0; i<THREE_TWO_ONE_ARRAY.length; i++)
			assertEquals(THREE_TWO_ONE_ARRAY[i],splitColor2[i]);
	}
	
	public void testPack() {
		assertEquals(ONE_TWO_THREE,packColors(THREE_TWO_ONE_ARRAY));
	}

}
