package org.shapelogic.util;

import static org.shapelogic.util.Constants.PRECISION;

/** There are precision issues when doing calculations with double
 * 
 * @author Sami Badawi
 *
 */
public class DoubleCalculations {

	static public boolean doubleEquals(double x, double y) {
		return Math.abs(x - y) < PRECISION;
	}

	static public boolean doubleZero(double x) {
		return Math.abs(x) < PRECISION;
	}

	static public boolean doubleIsInt(double x) {
		return Math.abs(x - Math.round(x)) < PRECISION;
	}

	static public boolean isEven(double input) {
    	long inputLong = (long) input;
    	return (inputLong & 1) != 0;
    }

	/** If one is 0 and other not return false */
	static public boolean oppositeSign(double d1, double d2) {
		if (Math.abs(d1) <= PRECISION || Math.abs(d2) <= PRECISION){
			return false;
		}
		return (d1 < 0) !=  (d2 < 0);
	}

	/** If one is 0 and other not return false */
	static public boolean sameSign(double d1, double d2) {
		if (Math.abs(d1) <= PRECISION || Math.abs(d2) <= PRECISION){
			if (Math.abs(d1) <= PRECISION && Math.abs(d2) <= PRECISION)
				return true;
			return false;
		}
		return (d1 < 0) == (d2 < 0);
	}
}
