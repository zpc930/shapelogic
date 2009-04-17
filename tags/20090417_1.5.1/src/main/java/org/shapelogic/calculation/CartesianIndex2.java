package org.shapelogic.calculation;

import java.util.List;

import org.shapelogic.streams.StreamUtil;
import org.shapelogic.streams.NumberedStream;

/** CartesianIndex2 creates an accessing scheme for 2 NumberedStream, List or Array.
 * 
 * @author Sami Badawi
 *
 */
public class CartesianIndex2 implements CartesianIndex {
	static final int DIMINSION = 2;
	
	/** _value is the current index. */
	protected int[] _value = new int[2];
	
	/** _start is the start value of a dimension. */
	protected int[] _start = new int[2]; 

	/** _stop is the start value of a dimension. */
	protected int[] _stop = new int[2]; 
	protected int[] _lengths = new int[2]; 
	
	protected boolean _dirty = true;
	
	public CartesianIndex2(NumberedStream numberedStream0, NumberedStream numberedStream1){
		_lengths[0] = StreamUtil.size(numberedStream0);
		_stop[0] = numberedStream0.getLast();
		_start[0] = 0;
		_lengths[1] = StreamUtil.size(numberedStream1);
		_stop[1] = numberedStream1.getLast();
		_start[1] = 0;
		setup();
	}
	
	public CartesianIndex2(List list0, List list1){
		_lengths[0] = list0.size();
		_stop[0] = _lengths[0]-1;
		_start[0] = 0;
		_lengths[1] = list1.size();
		_stop[1] = _lengths[1]-1;
		_start[1] = 0;
		setup();
	}
	
	public CartesianIndex2(Object[] array0, Object[] array1){
		_lengths[0] = array0.length;
		_stop[0] = _lengths[0]-1;
		_start[0] = 0;
		_lengths[1] = array1.length;
		_stop[1] = _lengths[1]-1;
		_start[1] = 0;
		setup();
	}
	
	/** If there are cases where you know the start and stop.
	 */
	public void setup() {
		for (int i = 0; i < getDimension(); i++) {
			_value[i] = _start[i];
		}
		_dirty = false;
	}
	
	@Override
	public int[] get(int index) {
		int[] result = new int[2];
		result[0] = index % _lengths[0];
		result[1] = index / _lengths[0];
		return result;
	}

	@Override
	public int getDimension() {
		return DIMINSION;
	}

	@Override
	public int[] getLastNumbers() {
		return _stop;
	}

	@Override
	public int[] getStartNumbers() {
		return _start;
	}

	@Override
	public boolean hasNext() {
		return !(_value[0] == _stop[0] && _value[1] == _stop[1]) &&
		_value[0] <= _stop[0] && _value[1] <= _stop[1];
	}

	@Override
	public int[] next() {
		if (_value[0] < _stop[0])
			_value[0]++;
		else {
			if (hasNext()) {
				_value[0] = _start[0];
				_value[1]++;
			}
			else
				return null; //Not sure if this is right
		}
		return _value;
	}

	@Override
	/** This should maybe be overridden to lower the index.
	 */
	public void remove() {
	}

	@Override
	public boolean isDirty() {
		return _dirty;
	}

	@Override
	public int[] getValue() {
		return _value;
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLast() {
		return _lengths[0]*_lengths[1] - 1;
	}

	@Override
	public int getMaxLast() {
		return getLast();
	}

	@Override
	/** I do not think that this make sense but maybe it does.
	 * So it is not implemented for now.
	 */
	public void setMaxLast(int maxLast) {
	}
}
