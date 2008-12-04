package org.shapelogic.mathematics;

import org.shapelogic.streams.BaseListStream0;

/** Generated Prime Number.<br />  
 * <br /> 
 * Should this be based on a BaseListStream, when there is no input?<br /> 
 * Probably not.<br /> 
 * 
 * How should they be generated?<br /> 
 * 
 * I could say start by last number and add until you get one that does not 
 * have any of the previous as divisors.<br /> 
 * 
 * There can be a stop value: <br /> 
 * 
 * start index default 0 <br /> 
 * <br />
 * max last / last number default not set <br />
 * 
 * @author Sami Badawi
 *
 */
public class PrimeNumberStream extends BaseListStream0<Integer> {
	Number nu;
	
	public PrimeNumberStream() {
		_list.add(2);
	}
	
	public PrimeNumberStream(int maxLast) {
		this();
		setMaxLast(maxLast);
	}
	
	@Override
	public Integer invoke(int index) {
		int last = _list.get(_list.size()-1);
		for (int i = last+1; i < Integer.MAX_VALUE; i++ ) {
			if (isPrimeToList(i))
				return i;
		}
		return null;
	}
	
	/** The input is not dividable by any element in the list. */
	private boolean isPrimeToList(int input) {
		for (Integer prime: _list) {
			if (input % prime == 0)
				return false;
		}
		return true;
	}
}
