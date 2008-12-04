package org.shapelogic.calculation;

import java.util.Iterator;

/** BaseAccumulator accumulator.
 * 
 * For use with Streams and Iterators.
 * 
 * @author Sami Badawi
 *
 * @param <In>
 * @param <Out>
 */
abstract public class BaseAccumulator<In,Out> implements Accumulator<In,Out> {
	protected Iterator<In> _input;
	protected boolean _dirty = true;
	protected Out _value;
	protected Out _previousValue;
	protected In _inputElement;
	protected In _previousInputElement; //XXX maybe take out
	
	public BaseAccumulator(Iterator<In> input) {
		_input = input;
	}
	
	@Override
	abstract public Out accumulate(In element, Out out);

	@Override
	public Iterator<In> getInput() {
		return _input;
	}

	@Override
	public boolean hasNext() {
		boolean result = _input.hasNext();
		if (!result)
			_dirty = false;
		return result;
	}

	@Override
	public Out next() {
		_previousValue = _value;
		_previousInputElement = _inputElement; 
		_inputElement = _input.next();
		Out acc = accumulate(_inputElement,_previousValue);
		if (acc != null)
			_value = acc;
		return _value;
	}

	@Override
	/** I am not sure about the semantic of this */
	public void remove() {
		_input.remove();
	}

	@Override
	public boolean isDirty() {
		return _dirty;
	}

	@Override
	public void setup() {
		if (_inputElement == null && _inputElement instanceof Number) {
			if (_inputElement instanceof Integer)
			_inputElement = (In) new Integer(0);
		}
	}

	@Override
	/** Causes the lazy calculation */
	public Out getValue() {
		if (isDirty()) {
			setup();
			while(hasNext())
				next();
			_dirty = false;
		}
		return _value;
	}
	
	/** Not sure if this should be a lazy calculation too.
	 *  
	 * This might be moved into the interface.
	 * */
	public Out getPreviousValue() {
		if (_dirty)
			getValue();
		return _previousValue;
	}
}
