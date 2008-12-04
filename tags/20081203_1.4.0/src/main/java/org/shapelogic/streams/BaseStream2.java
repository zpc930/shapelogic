package org.shapelogic.streams;


/** BaseDeclarativeStreamInputs2 a Stream with 2 inputs. 
 * 
 * The idea is that you can do a calculation, with 2 input and 1 output.
 * 
 * This could be used directly for a zip operation, but not if you need a 
 * Cartesian product.
 * 
 * I need a Cartesian product operation what should that be defined as?
 * I just need to override hasNext() and next(). This seem like the work for a
 * mixin. 
 * 
 * I think that this seems like a good solution, I do not think that you need 
 * to store all the values in a Cartesian product.
 * 
 * Do I need all the classes sequential, number and list?
 * I think that if you do a zip you just need 2 Interators as input.
 * 
 * For anything else you need a list input.
 * 
 * Output that make sense: sequential, number and list.
 * I think that maybe they can be sub classed from each other.
 * 
 * @author Sami Badawi
 *
 * @param <In0>
 * @param <In1>
 * @param <E>
 */
abstract public class BaseStream2<In0,In1,E> 
   implements Stream<E> , InputsStream2<In0, In1, E>
{
	protected boolean _dirty = true;
	protected Stream<In0> _inputStream0; 
	protected Stream<In1> _inputStream1;
	protected E _value;
	
	@Override
	public Stream<In0> getInputStream0() {
		return _inputStream0;
	}

	@Override
	public Stream<In1> getInputStream1() {
		return _inputStream1;
	}

	@Override
	public E getValue() {
		return _value;
	}

	@Override
	abstract public E invoke(In0 input0, In1 input1);

	@Override
	public boolean isDirty() {
		return _dirty;
	}

	@Override
	public void setup() {
	}

	@Override
	public boolean hasNext() {
		boolean result = _inputStream0.hasNext() && _inputStream1.hasNext();
		if (result)
			_dirty = false;
		return result;
	}

	@Override
	public E next() {
		_value = invoke(_inputStream0.next(), _inputStream1.next());
		return _value;
	}

	@Override
	public void remove() {
	}
}
