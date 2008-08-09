package org.shapelogic.predicate;

import java.util.Map;
import java.util.TreeMap;

/** Predicate with 2 arguments.
 * 
 * BinaryPredicate is close to the Apache Commons BinaryPredicateBinaryFunction.<p/>
 * 
 * But defined independently since Commons Functor lib is still a sand box lib.
 * 
 * @author Sami Badawi
 *
 */
public class BinaryPredicateFactory {
	private static Map<String, BinaryPredicate> _map;  
	private static Class<BinaryPredicate>[] _arrayOfPredicateClasses = new Class[]
		{
		BinaryEqualPredicate.class,
		BinaryGreaterPredicate.class,
		BinarySmallerPredicate.class,
		};
	
	static {
		_map = new TreeMap<String, BinaryPredicate>();
		for (Class klass: _arrayOfPredicateClasses) {
			BinaryPredicate instance;
			try {
				instance = (BinaryPredicate) klass.newInstance();
				_map.put(instance.getName(),instance);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	/** Static factory method for BinaryPredicate  
	 * 
	 * @param name of BinaryPredicate
	 * @return corresponding BinaryPredicate
	 */
	public static BinaryPredicate getInstance(String name) {
		return _map.get(name);
	}
}
