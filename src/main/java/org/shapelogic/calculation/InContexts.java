package org.shapelogic.calculation;

import java.util.Map;

/** InContexts is for classes that live in the a contexts.
 * 
 * @author Sami Badawi
 *
 * @param <K> Key for the contexts.
 */
public interface InContexts<K> {
	Map<K,?>[] getContexts();
}
