package org.shapelogic.streams;

import org.shapelogic.calculation.Calc1;

import java.util.Map.Entry;

/** Does calculation on input and if it is different from null return Entry of input result.
 * <br />
 * <br />
 * 
 * @author Sami Badawi
 * 
 * @param <E> Input and Output are the same
 */
public interface FilterCalcStream1<E,Out> extends Stream<Entry<E, Out> > {
	Calc1<E, Out> getCalc1();
}
