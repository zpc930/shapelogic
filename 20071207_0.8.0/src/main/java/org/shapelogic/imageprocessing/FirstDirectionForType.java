package org.shapelogic.imageprocessing;

import org.shapelogic.util.Constants;

/** To keep track of if a pixel has neighbors of a given type of pixel.
 * 
 * Simple enough not to use getter and setters.
 * 
 * @author Sami Badawi
 */
class FirstDirectionForType {
	public int count = 0;
	public int countUsed = 0;
	public byte firstDirection = Constants.DIRECTION_NOT_USED;
	public byte firstUsedDirection = Constants.DIRECTION_NOT_USED;
	
	public void addDirection(byte direction, boolean used) {
		if (used) {
			countUsed++;
			if (firstUsedDirection == Constants.DIRECTION_NOT_USED) {
				firstUsedDirection = direction;
			}
		}
		else {
			count++;
			if (firstDirection == Constants.DIRECTION_NOT_USED) {
				firstDirection = direction;
			}
		}
	}

	/** At lease on instance of this type present. */
	public boolean isTypePresent(){
		return firstDirection != Constants.DIRECTION_NOT_USED;
	}
	
	public void setup(){
		count = 0;
		firstDirection = Constants.DIRECTION_NOT_USED;
	}
	
	public int countAll() {
		return countUsed + count; 
	}
}