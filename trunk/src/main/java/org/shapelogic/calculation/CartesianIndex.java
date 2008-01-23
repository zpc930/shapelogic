package org.shapelogic.calculation;

import org.shapelogic.streams.NumberedStream;

/** Create indexes for the inputs to a Cartesian product.
 * 
 * There can be a nested for loop or there can be another strategy.
 * 
 * It would be enough to extend an Iterator instead of NumberedStream. 
 * 
 * It is interesting that this is a natural fit. 
 * So from 2 streams you are using another stream to combine them.
 * 
 * @author Sami Badawi
 *
 */
public interface CartesianIndex extends NumberedStream<int[]> {
	int[] getStartNumbers();
	int[] getLastNumbers();
	int getDimension(); //Dimension
}
