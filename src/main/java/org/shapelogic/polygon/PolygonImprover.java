package org.shapelogic.polygon;

import org.shapelogic.calculation.CalcInvoke;

/** Create an new improved polygon from an old polygon 
 * Use the Improver interface instead
 * 
 * @author Sami Badawi
 *
 */
@Deprecated
public interface PolygonImprover extends CalcInvoke<Polygon> {
	
	public Polygon getInputPolygon();
	public Polygon getOutputPolygon();

}
