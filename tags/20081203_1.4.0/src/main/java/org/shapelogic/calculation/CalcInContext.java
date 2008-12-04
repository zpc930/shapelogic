package org.shapelogic.calculation;

/** Some calculations rely on the context that it is going on in. 
 * 
 * @author Sami Badawi
 *
 * @param <K>
 * @param <V>
 */
public interface CalcInContext<K,V> extends LazyCalc<V> {
	/** This is the method that the user should override.
	 * 
	 * @param contextArray
	 * @return
	 */
	V invoke(RecursiveContext<K> contextArray);

	/** This a convenience method that set the calculated value in the right context.
	 * 
	 * @param contextArray
	 * @return
	 */
	V calc(RecursiveContext<K> contextArray);
}
