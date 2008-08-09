package org.shapelogic.polygon;

/** there is only one class implementing this I should probably take it out    
 * 
 * @author Sami Badawi
 *
 */
public interface ILine2D extends Comparable<ILine2D>, GeometricShape2D {
	IPoint2D getStart();
	IPoint2D getEnd();
}
