package org.shapelogic.calculation;

import java.util.Iterator;
import java.util.List;

/** Lazy Data Stream interface.
 * 
 * This version of Stream has been replaced with with Streams in package 
 * streams, use them instead.
 * TODO This should be deleted before release of ShapeLogic 0.9 
 * 
 * @author Sami Badawi
 *
 */
@Deprecated
public interface Stream <E> extends Iterator<E>, List<E>, Iterable<E> {
	
	/** If you can calculate 1 element independent of other elements. */
	boolean isRandomAccess();
	
	/** If there is a list that contains all the results. */
	boolean isCached();
	
	/** Calculated 1 individual element.
	 * 
	 * @param index of element in list
	 */
	E calcElement(int index);
	
	/** Number of calculated elements. Not sure if I need this. */
	int getCurrentSize();

	/** Last processed element when using next(). */
	int getCurrent();
	
	/** Last possible element. */
	int getLast();

	/** Set a max value for last possible element. */
	int getMaxLast();
	
	/** Get underlying list. */
	List<E> getList();

	/** Get underlying list. */
	void setList(List<E> list);
}
