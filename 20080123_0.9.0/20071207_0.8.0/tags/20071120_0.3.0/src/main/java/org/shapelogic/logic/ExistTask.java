package org.shapelogic.logic;

/** This only checks that something exists or can be found by a calculation
 * 
 * @author Sami Badawi
 *
 */
public class ExistTask<T> extends BaseTask<T> {
	String _nameInContext;
	/** Test is something exist in another context */
	public ExistTask(BaseTask parent, String nameInContext) {
		super(parent, false);
		_nameInContext = nameInContext;
	}
	
	@Override
	public boolean match() {
		_calcValue = (T) findNamedValue(_nameInContext);
		if (_calcValue == null)
			return false;
		return true;
	}
	
	@Override
	public void postCalc() {
		setNamedValue(_nameInContext, _calcValue);
	}
}
