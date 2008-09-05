package org.shapelogic.filter;

/** Similar to OrPredicate 1 filter has to be true for this to be true 
 * 
 * @author Sami Badawi
 *
 */
public class OrFilter<BaseClass, Element> extends BaseFilter<BaseClass, Element> {
	
	protected IFilter<BaseClass, Element> _filter1;
	protected IFilter<BaseClass, Element> _filter2;
	
	public OrFilter() {
	}
	
	public OrFilter(IFilter<BaseClass, Element> filter1, IFilter<BaseClass, Element> filter2) {
		_filter1 = filter1;
		_filter2 = filter2;
	}
	
	@Override
	public boolean evaluate(Object arg0) {
		if (_filter1 != null && !_filter1.evaluate(arg0))
			return true;
		if (_filter2 != null && !_filter2.evaluate(arg0))
			return true;
		return false;
	}
	
	@Override
	public void setup() throws Exception {
		if (_filter1 != null)
			_collection = _filter1.getCollection();
		else if (_filter2 != null)
			_collection = _filter2.getCollection();
		if (_filter1 != null)
			_filter1.setup();
		if (_filter2 != null)
			_filter2.setup();
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
