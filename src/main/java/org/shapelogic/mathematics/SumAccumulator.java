package org.shapelogic.mathematics;

import org.shapelogic.calculation.BaseAccumulator;

/** AddAccumulator is an accumulator that add integers.
 * 
 * Since this is used so much.
 * 
 * @author Sami Badawi
 *
 */
public class SumAccumulator extends BaseAccumulator<Integer,Long> {
	
	public SumAccumulator(java.util.Iterator<Integer> input){
		super(input);
		_value = 0L;
	}

	@Override
	public Long accumulate(Integer element, Long out) {
		return element + out;
	}
}
