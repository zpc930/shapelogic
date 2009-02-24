package org.shapelogic.streams;

import org.shapelogic.calculation.Calc1;

/** Make a stream that is using a Calc1 class to do a calculation, based on
 * a named input stream stored in a context.<br />
 *
 * Based on ListCalcStream1.<br />
 *
 * Uses lazy setup so input stream does not have to exist when stream is created.<br />
 *
 * There should be a way to say whether you have to calculate all the values
 * sequentially to use this.<br />
 *
 * Close to NamedListCalcStream1, but with no caching.<br />
 *
 * @author Sami Badawi
 * 
 */
public class CalcNumberedStream1<In, E> extends BaseNumberedStream1<In, E>
{
	protected Calc1<In, E> _calc;

	public CalcNumberedStream1(Calc1<In, E> calc, NumberedStream<In> inputStream, int maxLast) {
		super(inputStream, maxLast);
		_calc = calc;
	}

	public CalcNumberedStream1(Calc1<In, E> calc, NumberedStream<In> inputStream) {
		this(calc,inputStream, inputStream.getMaxLast());
	}

	public CalcNumberedStream1() {
	}

	public CalcNumberedStream1(Calc1<In, E> calc, int maxLast) {
		this(calc,null,maxLast);
	}

	@Override
	public E invoke(In input) {
		return _calc.invoke(input);
	}
}
