package org.shapelogic.streams;

import org.shapelogic.calculation.CalcIndex1;
import org.shapelogic.util.Constants;

/** Make a stream that is using a Transformer class to do a calculation.
 * <br />
 * In type does not seem to be used.<br />
 * 
 * XXX This should be called ListCalcIndexStream1 or something close to that.<br />
 * 
 * @author Sami Badawi
 * 
 */
public class ListCalcIndexStream1<In, E> extends BaseListIndexedStream1<In, E> {
	protected CalcIndex1<In, E> _calc;
	
	public ListCalcIndexStream1(CalcIndex1<In, E> calc, NumberedStream<In> inputStream, int maxLast) {
		super(inputStream, maxLast);
		_calc = calc;
	}

	public ListCalcIndexStream1(CalcIndex1<In, E> transformer) {
		this(transformer,null, Constants.LAST_UNKNOWN);
	}

	public ListCalcIndexStream1() {
	}
	
	public ListCalcIndexStream1(CalcIndex1<In, E> transformer, int maxLast) {
		_calc = transformer;
		setMaxLast(maxLast);
	}

	@Override
	public E invoke(In input, int index) {
		return _calc.invoke(input, index);
	}
}
