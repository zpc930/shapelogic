package org.shapelogic.streams;

import java.util.Iterator;

import org.shapelogic.calculation.LazyCalc;

/** Stream where is a sequential StreamCalc that generate a sequence 
 * of calculated elements.
 * 
 * This is the base for a lot of other stream interfaces and classes. <br />
 * 
 * It is the basic building block. It is somewhat like a:<br />
 * Lazy stream<br />
 * UNIX pipe<br />
 * 
 * @author Sami Badawi
 *
 */
public interface Stream<E>  extends LazyCalc<E>, Iterator<E> {

}