package org.shapelogic.imageprocessing;

import ij.ImagePlus;
import ij.io.Opener;
import ij.process.ByteProcessor;
import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class ImageJCallTest extends TestCase {
	String dirURL = "./src/test/resources/images/smallThinShapes";

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void test() {
		Opener opener = new Opener();
		String fileName = "rotatedT.gif";
		ImagePlus image = opener.openImage(dirURL + "/" + fileName);
		
		/* make buffered image from it */
		ByteProcessor cp = (ByteProcessor) image.getProcessor();
		assertEquals(20,cp.getWidth());
		int pixel = cp.get(0,0);
		assertEquals(0,pixel);
	}

}
