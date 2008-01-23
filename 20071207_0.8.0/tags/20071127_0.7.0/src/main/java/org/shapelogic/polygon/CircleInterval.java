package org.shapelogic.polygon;

/** So the idea is that you should have a slice of the circle
 * Since it is circular it does not really make sense to talk about: 
 * min and max
 * 
 * what is the equivalent
 * 
 * I guess start and end assuming that you go in normal direction of increasing angles.
 * 
 * This will often be the same as min and max, but sometimes there is a cross over.
 * 
 * That really just mean that check for angle in [max; 2 * PI[ and [0; min]
 * Otherwise it is check [min; max].
 * 
 * How do I make it be the full circle?
 * 
 * Is this a closed interval?
 * 
 * For now do not create getter and setter
 * 
 * @author Sami Badawi
 *
 */
public class CircleInterval {
	double start;
	double end;
	boolean empty = true;
	boolean isContained(double angle){
		return true;
	}
	
	public void addClosestAngle(double angle) {
		angle = normalizeAngle(angle);
		if (empty) {
			start = angle; 
			end = angle;
			empty = false;
			return;
		}
		if (contains(angle)) 
			return;
		double distToStart = angleDistance(start , angle);
		double distToEnd   = angleDistance(end, angle);
		if (start == end) {
			if (angleDistance(start , angle) < angleDistance(angle, start))
				addGrowingAngle(angle);
			else
				addFallingAngle(angle);
		}
		else if (distToStart < distToEnd)
			addGrowingAngle(angle);
		else
			addFallingAngle(angle);
	}

	/** Turn new angle into start point */
	public void addGrowingAngle(double angle) {
		angle = normalizeAngle(angle);
		if (empty) {
			start = angle; 
			end = angle; 
			empty = false;
			return;
		}
		if (contains(angle)) 
			return;
		start = angle;
	}

	/** Turn new angle into end point */
	public void addFallingAngle(double angle) {
		angle = normalizeAngle(angle);
		if (empty) {
			start = angle; 
			end = angle; 
			empty = false;
			return;
		}
		if (contains(angle)) 
			return;
		end = angle;
	}
	
	public boolean containsZero() {
		if (empty) 
			return false;
		return start > end;
	}
	
	public boolean contains(double angle) {
		angle = normalizeAngle(angle);
		if (empty) 
			return false;
		if (!containsZero()) {
			return start <= angle && angle <= end;
		}
		else {
			return start <= angle || angle <= end;
		}
	}
	
	public double intervalLength() {
		if (empty)
			return 0.;
		if (containsZero())
			return Math.PI * 2 + end - start;
		else 
			return end - start;
	}
	
	public static double normalizeAngle(double angle) {
		return angle % (Math.PI * 2);
	}

	/** signed angle from angle1 to angle2 */
	public static double angleDistance(double angle1, double angle2) {
		double dist = angle2 - angle1;
		if (dist > 2 * Math.PI)
			dist %= 2 * Math.PI;
		return dist;
	}
}
