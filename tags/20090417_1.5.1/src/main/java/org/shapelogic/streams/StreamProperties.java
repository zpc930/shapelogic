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
	
	/** If there is a list that contains all the results. 
     * 
     * I would think that this should always happen for a ListStream,
     * but it could happen for other streams as well.
     */
	boolean isCached();
	
	/** Is the result of a calculation deterministic. */
	boolean isDeterministic();
	
    /** If null is a legal value. 
     * 
     * Otherwise when a null if encountered the stream has ended.
     */
    boolean isNullLegalValue();
	
	/** The context if needed. 
	 * Maybe it would be better to make a link to the task that contains the context.
	 * */
	Map getContext();
	
	/** If a stream has a name. */
	String getName();
}