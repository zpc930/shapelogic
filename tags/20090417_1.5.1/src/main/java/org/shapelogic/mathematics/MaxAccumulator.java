package org.shapelogic.mathematics;

import org.shapelogic.calculation.BaseAccumulator;

/** AddAccumulator is an accumulator that add integers.
 * 
 * Since this is used so much.
 * 
 * @author Sami Badawi
 *
 */
public class MaxAccumulator extends BaseAccumulator<Integer,Integer> {
	
	public MaxAccumulator(java.util.Iterator<Integer> input){
		super(input);
	}

	@Override
	public Integer accumulate(Integer element, Integer out) {
		if (element == null)
			return null; //Maybe I should not do anything here
		if (out == null)
			out = element;
		else if (out < element)
			out = element;
		return out;
	}
}
