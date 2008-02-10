package org.shapelogic.calculation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.shapelogic.predicate.Predicate;

/** Normal functional operations used on Java Collections.
 * <br />
 * Done as eager operations opposed to stream that are done as lazy operations. <br />
 * 
 * Add these operations as they are needed.<br />
 * 
 * @author Sami Badawi
 *
 */
public class CollectionCalc {
	
	/** Normal map operations.<br />
	 * 
	 * Maybe there is a better name to use in Java.
	 * 
	 * @param <In>
	 * @param <Out>
	 * @param collection
	 * @param calc
	 * @return
	 */
	static public <In,Out> Collection<Out> map(final Collection<In> collection, 
			final Calc1<In,Out> calc) 
	{
		ArrayList<Out> result = new ArrayList<Out>(collection.size());
		for (In element: collection) {
			result.add(calc.invoke(element));
		}
		return result;
	}
	
	/** Index map operations.<br />
	 * 
	 * Maybe there is a better name to use in Java.
	 * 
	 * @param <In>
	 * @param <Out>
	 * @param collection
	 * @param calc
	 * @return
	 */
	static public <In,Out> Collection<Out> map(final Collection<In> collection, 
			final CalcIndex1<In,Out> calc) 
	{
		ArrayList<Out> result = new ArrayList<Out>(collection.size());
		int index = 0;
		for (In element: collection) {
			result.add(calc.invoke(element,index));
			index++;
		}
		return result;
	}
	
	/** Normal filter with predicate as a Calc1.<br />
	 * 
	 * @param <In>
	 * @param collection
	 * @param predicateCalc
	 * @return ArrayList
	 */
	static public <In> Collection<In> filter(final Collection<In> collection, 
			final Calc1<In,Boolean> predicateCalc) 
	{
		ArrayList<In> result = new ArrayList<In>(collection.size());
		for (In element: collection) {
			if (predicateCalc.invoke(element))
				result.add(element);
		}
		return result;
	}
	
	/** Normal filter.<br />
	 * 
	 * @param <In>
	 * @param collection
	 * @param predicate
	 * @return ArrayList
	 */
	static public <In> Collection<In> filter(final Collection<In> collection, 
			final Predicate<In> calc) 
	{
		ArrayList<In> result = new ArrayList<In>(collection.size());
		for (In element: collection) {
			if (calc.evaluate(element))
				result.add(element);
		}
		return result;
	}
	
	/** Normal merge of sorted lists. 
	 * <br />
	 * No duplicates.
	 * */
	static public <E extends Comparable<? super E> > List<E> mergeSorted
	(final List<E> input1,final List<E> input2) {

		ArrayList<E> result = new ArrayList<E>();
		int current1 = 0, current2 = 0;
		while (current1 < input1.size() && current2 < input2.size()) {
			if (input1.get(current1).compareTo(input2.get(current2)) < 0) {
				result.add(input1.get(current1));
				current1++;
			}
			else if (input1.get(current1).compareTo(input2.get(current2)) > 0) {
				result.add(input2.get(current2));
				current2++;
			}
			else {
				result.add(input1.get(current1));
				current1++;
				current2++;
			}
 		}
		return result;
	}
	
}
