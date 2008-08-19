package org.shapelogic.calculation;

import java.util.Map;

/**
 * InContexts is for classes that live in the a contexts.<br />
 * 
 * It will start in the first context and work back when trying to find a
 * binding.<br />
 * 
 * Deprecated use RecursiveContext instead. <br />
 * 
 * The idea was that multiple contexts should be represented as an array of maps.<br />
 * 
 * @author Sami Badawi
 * 
 * @param <K>
 *            Key for the contexts.
 */
@Deprecated
public interface InContexts<K> {
	Map<K, ?>[] getContexts();
}
