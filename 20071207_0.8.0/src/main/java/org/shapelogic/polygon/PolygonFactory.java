package org.shapelogic.polygon;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class PolygonFactory {
	
	public static Polygon createSameType(Polygon poygon) {
		if (poygon instanceof MultiLinePolygon) {
			return new MultiLinePolygon();
		}
		else if (poygon instanceof Polygon) {
			return new Polygon();
		}
		else
			return null;
	}

}
