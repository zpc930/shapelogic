package org.shapelogic.calculation;

import java.util.Map;

/** Interface for classes that live in the a context with parent contexts.<br />
 * 
 * It will start in the first context and work back when trying to find a binding.<br />
 * 
 * @author Sami Badawi
 *
 * @param <K> Key for the contexts.
 */
public interface RecursiveContext<K> {
	Map<K,?> getContext();
	RecursiveContext<K> getParentContext();
}
