package org.shapelogic.calculation;

import java.util.Map;

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
	V invoke(Map<K,?>[] contextArray);

	/** This a convenience method that set the calculated value in the right context.
	 * 
	 * @param contextArray
	 * @return
	 */
	V calc(Map[] contextArray);
}
