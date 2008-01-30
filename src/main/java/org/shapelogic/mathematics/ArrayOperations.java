package org.shapelogic.mathematics;

/** Mathematical operations on arrays in common use.
 *  
 * Add when needed.
 * 
 * @author Sami Badawi
 *
 */
public class ArrayOperations {

	/** Take product of integer array and return as long.
	 * 
	 * @param numberArray
	 * @return
	 */
	static public long product(int[] numberArray) {
		long product = 1;
		for (int element: numberArray) {
			product *= element;
		}
		return product;
	}

}
