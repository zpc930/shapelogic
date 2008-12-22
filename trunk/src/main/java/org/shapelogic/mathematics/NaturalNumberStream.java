package org.shapelogic.mathematics;

import org.shapelogic.streams.BaseCommonNumberedStream;

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
public class NaturalNumberStream extends BaseCommonNumberedStream<Integer> {
	private int _startIndex = 0;
	
	public NaturalNumberStream() {
        super();
	}
	
	public NaturalNumberStream(int maxLast) {
        this();
		setMaxLast(maxLast);
	}
	
	public NaturalNumberStream(int startIndex, Integer maxLast) {
        this();
		_startIndex = startIndex;
		if (maxLast != null)
			setMaxLast(maxLast-startIndex);
	}
	
	public int getStartIndex() {
		return _startIndex;
	}

    @Override
    public Integer invokeIndex(int index) {
		return index + _startIndex;
    }
}
