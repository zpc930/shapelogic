package org.shapelogic.filter;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;

/** 
 * 
 * @author Sami Badawi
 *
 */
public class DynamicBaseFilter<BaseClass, Element> extends BaseFilter<BaseClass, Element> {

	protected String _criteriaExpression;
	protected String _collectionExpression;
	
	public DynamicBaseFilter(String criteriaExpression, String collectionExpression) {
		_criteriaExpression = criteriaExpression;
		_collectionExpression = collectionExpression;
	}
	
	@Override
	public Collection<Element> getCollection() {
		if (_collection == null) {
			if (_collectionExpression != null) {
				Object obj = null;
				try {
					obj = PropertyUtils.getSimpleProperty(this, _collectionExpression);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				 if (obj != null && (obj instanceof Collection))
					 _collection = (Collection) obj;
			}
		}
		return _collection;
	}

	@Override
	public boolean evaluate(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
