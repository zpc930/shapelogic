package org.shapelogic.calculation;

/** This is a calculation that is done.
 * <br />
 * The value is immutable.
 * 
 * @author Sami Badawi
 *
 * @param <T>
 */
final public class CalcFixed<T> implements CalcValue<T> {
	final private T _value;
	public CalcFixed(T value) {
		_value = value;
	}
	
	@Override
	public T getValue() {
		return _value;
	}

}
