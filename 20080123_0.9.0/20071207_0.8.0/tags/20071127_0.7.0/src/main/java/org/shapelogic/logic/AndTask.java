package org.shapelogic.logic;

/** In order for an And Task to succeed all the child Tasks need to succeed
 * 
 * So I should have a way to take one at a time and call calc on them.
 * 
 * @author Sami Badawi
 *
 */
public class AndTask extends BaseTask<String> {
	public static int NOT_STARTED = -1;
	protected int _currentChildTaskNo = NOT_STARTED;
	protected int _failedChildTasks = 0;
	
	
	public AndTask(BaseTask parent, boolean createLocalContext) {
		super(parent, createLocalContext);
	}
	
	@Override
	public String mainCalc() {
		_currentChildTaskNo = 0;
		while (getChildCount() > 0) {
			calcNextSubTask();
			if (getLastSubTaskState() != LogicState.SucceededDone) {
				_state = getLastSubTaskState();
				_failedChildTasks++;
				break;
			}
			else {
			}
		}
		if (_failedChildTasks == 0) {
			_state = LogicState.SucceededDone;
			return getName();
		}
		return null;
	} 


}
