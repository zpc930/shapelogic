package org.shapelogic.filter;

/** Similar to NotPredicate filter has to be false for this to be true 
 * 
 * @author Sami Badawi
 *
 */
public class NotFilter<BaseClass, Element> extends BaseFilter<BaseClass, Element> {
	
	protected IFilter<BaseClass, Element> _filter1;
	
	public NotFilter() {
	}
	
	public NotFilter(IFilter<BaseClass, Element> filter1) {
		_filter1 = filter1;
	}
	
	@Override
	public boolean evaluate(Object arg0) {
		if (_filter1 != null)
			return !_filter1.evaluate(arg0);
		return true; //XXX not sure about this
	}
	
	@Override
	public void setup() throws Exception {
		if (_filter1 != null)
			_collection = _filter1.getCollection();
		if (_filter1 != null)
			_filter1.setup();
	}
}
