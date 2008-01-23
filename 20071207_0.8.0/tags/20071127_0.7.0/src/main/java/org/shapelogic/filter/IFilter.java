package org.shapelogic.filter;

import java.util.Collection;

import org.apache.commons.collections.Predicate;

/** 
 * 
 * @author Sami Badawi
 *
 */
public interface IFilter<BaseClass, Element> extends Predicate {

	BaseClass getParent();
	void setParent(BaseClass obj);
	
	Collection<Element> getCollection();
	void setCollection(Collection<Element> collection);
	
	Collection<Element> filter();
	
	Object getConstraint();
	void setConstraint(Object constraint);
	void setup() throws Exception;
}
