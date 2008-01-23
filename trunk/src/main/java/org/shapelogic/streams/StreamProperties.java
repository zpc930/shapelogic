package org.shapelogic.streams;

import java.util.Map;

/** Properties to be Stream the universal calculation and logic unit.<br /> 
 * <br />
 * This is going to replace the current Calc, so this should probably just be 
 * renamed to Calc or LazyCalc.<br />
 * 
 * Should I assume that null means absent of value?<br />
 * That seems a little harsh. 
 * 
 * @author Sami Badawi
 *
 */
public interface StreamProperties {
	/** If you can calculate 1 element independent of other elements. */
	boolean isRandomAccess();
	
	/** Is the result of a calculation cached. */
	boolean isCached();
	
	/** Is the result of a calculation deterministic. */
	boolean isDeterministic();
	
	/** If it needs a context. */
	boolean isContextBased();
	
	/** The context if needed. 
	 * Maybe it would be better to make a link to the task that contains the context.
	 * */
	Map getContext();
	
	/** If a stream has a name. */
	String getName();
}