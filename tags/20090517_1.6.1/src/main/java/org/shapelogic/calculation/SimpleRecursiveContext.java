package org.shapelogic.calculation;

import java.util.HashMap;
import java.util.Map;

/** Simple implementation of a RecursiveContext.<br />
 * 
 * This could be a mix in class or just a test class.<br />
 * 
 * @author Sami Badawi
 *
 */
//@Deprecated
public class SimpleRecursiveContext<K> implements RecursiveContext<K> {
	Map<K,?> _context;
	RecursiveContext<K> _parentContext;
	
	public SimpleRecursiveContext(RecursiveContext<K> parentContext) {
		_parentContext = parentContext;
		_context = new HashMap();
	}
	
	public SimpleRecursiveContext(Map<K,?> context, RecursiveContext<K> parentContext) {
		_parentContext = parentContext;
		_context = context;
	}
	
	public void setContext(Map<K,?> context) {
		_context = context;
	}

	public void setParentContext(RecursiveContext<K> parentContext) {
		_parentContext = parentContext;
	}

	@Override
	public Map<K,?> getContext() {
		return _context;
	}

	@Override
	public RecursiveContext<K> getParentContext() {
		return _parentContext;
	}

}
