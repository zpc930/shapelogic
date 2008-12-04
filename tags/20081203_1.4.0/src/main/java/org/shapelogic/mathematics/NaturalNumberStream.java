package org.shapelogic.mathematics;

import org.shapelogic.streams.BaseListStream0;

/** Generated Natural Number. 
 * 
 * There can be both a start and stop value: <br /> 
 * 
 * start index default 0 <br /> 
 * <br />
 * max last / last number default not set <br />
 * 
 * @author Sami Badawi
 *
 */
public class NaturalNumberStream extends BaseListStream0<Integer> {
	private int _startIndex = 0;
	
	public NaturalNumberStream() {
	}
	
	public NaturalNumberStream(int maxLast) {
		setMaxLast(maxLast);
	}
	
	public NaturalNumberStream(int startIndex, Integer maxLast) {
		_startIndex = startIndex;
		if (maxLast != null)
			setMaxLast(maxLast-startIndex);
	}
	
	@Override
	public Integer invoke(int index) {
		return index + _startIndex;
	}
	
	public int getStartIndex() {
		return _startIndex;
	}
}
