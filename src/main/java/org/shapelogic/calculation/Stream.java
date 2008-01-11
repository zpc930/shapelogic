package org.shapelogic.calculation;

import java.util.Iterator;
import java.util.List;

/** Lazy Data Stream interface.
 * 
 * @author Sami Badawi
 *
 */
public interface Stream <E> extends Iterator<E>, List<E>, Iterable<E> {
	boolean isRandomAccess();
	E calcElement(int index);
	int getCalcIndex();
}
