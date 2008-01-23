package org.shapelogic.logic;

/** Only one sub task should succeed for this to succeed.
 * 
 * Later I might change this so that there can be more that succeed, 
 * but chose the succeeding one that has the highest score. 
 * 
 * @author Sami Badawi
 *
 */
public class XOrTask extends BaseTask<String> {
	public static int NOT_STARTED = -1;
	protected int _currentChildTaskNo = NOT_STARTED;
	
	
	public XOrTask(BaseTask parent, boolean createLocalContext) {
		super(parent, createLocalContext);
	}
	
	@Override
	public String mainCalc() {
		_currentChildTaskNo = 0;
		_value = null;
		while (getChildCount() > 0) {
			Object currentResult = this.calcNextSubTask(); 
			if (currentResult != null) {
				if (currentResult!= null)
					_value = currentResult.toString();
				if (_succededSubTasksCount > 1) {
					break;
				}
			}
		}
		if (_succededSubTasksCount == 1) {
			_state = LogicState.SucceededDone;
		}
		else {
			_state = LogicState.FailedDone;
			_value = null;
		}
		return _value;
	} 


}
