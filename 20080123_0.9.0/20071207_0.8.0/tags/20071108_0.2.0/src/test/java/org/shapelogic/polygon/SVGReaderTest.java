package org.shapelogic.polygon;

import java.io.File;
import java.io.IOException;

import org.shapelogic.polygon.Polygon;
import org.shapelogic.polygon.SVGReader;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class SVGReaderTest extends TestCase {

	String dir = null;
	String baseDir = ".";
	String relDir = "src/test/resources/svg/letter/";

    String testParseLine = 
        "M 193.94929,211.91527 L 193.94929,779.621 L 480.83261,779.621";
    
	private String getDir() {
		return getBaseDir() + "/" +relDir;
	}
	
	/** I might have to change this to do a search if it is started in a different directory
	 * 
	 * I am a little surprised that local dir is the root dir for the project
	 * 
	 * So maybe I should just try to use a relative path instead
	 */
	private String getBaseDir() {
		File currentDir = new File(".");
		String path = null;
		try {
			path = currentDir.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
    public void testReadOfL() throws Exception {
    	SVGReader reader = new SVGReader(getDir() + "L.svg");
        Polygon polygon = reader.getPolygon();
        assertTrue(polygon instanceof Polygon);
        assertEquals("Wrong number of points",3,polygon.getPoints().size());
        assertEquals("Wrong number of points",2,polygon.getLines().size());
        System.out.println("Raw polygon for L");
        System.out.println(polygon.getPoints());
        Polygon polygonCleaned = polygon.cleanUp(true, 0.02f);
        System.out.println("Cleaned polygon for L");
        System.out.println(polygonCleaned.getPoints());
        assertEquals("Wrong number of points",3,polygonCleaned.getPoints().size());
        assertEquals("Wrong number of points",2,polygonCleaned.getLines().size());
    }

    public void testReadOfA() throws Exception {
    	SVGReader reader = new SVGReader(getDir() + "A.svg");
    	Polygon polygon = reader.getPolygon();
        assertTrue(polygon instanceof Polygon);
        System.out.println(polygon.getPoints());
        assertEquals("Wrong number of points",6,polygon.getPoints().size()); 
        assertEquals("Wrong number of lines",5,polygon.getLines().size());
        Polygon polygonCleaned = polygon.cleanUp(true, 0.02);
        System.out.println("Cleaned polygon for A");
        System.out.println(polygonCleaned.getPoints());
        System.out.println(polygonCleaned.getLines());
        assertEquals("Wrong number of points",5,polygonCleaned.getPoints().size());
        assertEquals("Wrong number of lines",5,polygonCleaned.getLines().size());
        assertEquals("Wrong number of lines",2,polygonCleaned.getEndPointCount());
    }
    
    public void te_stGetBaseDir() {
    	System.out.println("System.getProperties(): "+System.getProperties());
    	String foundOsName = System.getProperty("os.name");
    	if ("Linux".equalsIgnoreCase(foundOsName))
    		assertEquals(baseDir, getBaseDir());
    }

}