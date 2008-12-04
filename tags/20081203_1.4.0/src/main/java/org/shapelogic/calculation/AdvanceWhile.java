package org.shapelogic.calculation;

import java.util.Iterator;

/** This causes the iterator to be advanced as long as the the condition is true.
 * 
 * @author Sami Badawi
 *
 * @param <E>
 */
abstract public class AdvanceWhile<E> {
	public AdvanceWhile(Iterator<E> iterator){
		while (iterator.hasNext() ) {
			if (!evaluate(iterator.next()))
				break;
		}
	}
	
	abstract public boolean evaluate(E input);
}
