package org.shapelogic.streams;

import java.util.List;

import org.shapelogic.calculation.CalcList;

/** InputStreamList is like InputStream1 but takes List of streams as input.
 * <br />
 * This takes care of the input functionality of a Stream.
 *  
 * @author Sami Badawi
 *
 */
public interface InputStreamList <In, E> extends CalcList<In, E> {
	
	/** Stream that this stream is connected to. 
	 * What if the Range is of different type?
	 * I think that maybe I can relax this later.
	 * */
	List<NumberedStream<In> > getInputStream();
	
	List<In> getInput(int index);
	
	/** How many input streams you have.
	 */
	int getDimension();
}