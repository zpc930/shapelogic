package org.shapelogic.streams;

import java.util.Map;

/** Universal calculation and logic unit. 
 * 
 * This is going to replace the current Calc, so this should probably just be 
 * renamed to Calc or LazyCalc.
 * 
 * Should I assume that null means absent of value?
 * That seems a little harsh. 
 * 
 * @author Sami Badawi
 *
 */
public interface IndexedInputStream2 <In0, In1, E> {
	
	/** Closure to calculated 1 individual element based on index and input for 
	 * the same index.
	 * 
	 * Should later just call the invoke on the closure.
	 * 
	 * Maybe this should be moved up later.
	 * 
	 * @param index of element in list
	 */
	E invoke(In0 input0, In1 input1, int index);
	
	//Maybe I only need on of the next 2 methods
	
	/** Get numbered element from InputStream */
	In0 getInput0(int index); 
	In1 getInput1(int index); 
	
	/** Stream that this stream is connected to. 
	 * What if the Range is of different type?
	 * I think that maybe I can relax this later.
	 * */
	NumberedStream<In0> getInputStream0();
	NumberedStream<In1> getInputStream1();
}