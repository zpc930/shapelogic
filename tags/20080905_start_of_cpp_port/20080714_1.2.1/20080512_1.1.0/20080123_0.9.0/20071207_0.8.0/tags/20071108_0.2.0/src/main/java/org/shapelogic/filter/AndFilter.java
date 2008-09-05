package org.shapelogic.filter;

/** Similar to AndPredicate every filter has to be true for this to be true 
 * 
 * @author Sami Badawi
 *
 */
public class AndFilter<BaseClass, Element> extends BaseFilter<BaseClass, Element> {
	
	protected IFilter<BaseClass, Element> _filter1;
	protected IFilter<BaseClass, Element> _filter2;
	
	public AndFilter() {
	}
	
	public AndFilter(IFilter<BaseClass, Element> filter1, IFilter<BaseClass, Element> filter2) {
		_filter1 = filter1;
		_filter2 = filter2;
	}
	
	@Override
	public boolean evaluate(Object arg0) {
		if (_filter1 != null && !_filter1.evaluate(arg0))
			return false;
		if (_filter2 != null && !_filter2.evaluate(arg0))
			return false;
		return true;
	}
	
	@Override
	public void setup() {
		if (_filter1 != null)
			_collection = _filter1.getCollection();
		else if (_filter2 != null)
			_collection = _filter2.getCollection();
	}

	@Override
	public void setParent(BaseClass parent) {
		super.setParent(parent);
		if (_filter1 != null)
			_filter1.setParent(parent);
		if (_filter2 != null)
			_filter2.setParent(parent);
	}

}
