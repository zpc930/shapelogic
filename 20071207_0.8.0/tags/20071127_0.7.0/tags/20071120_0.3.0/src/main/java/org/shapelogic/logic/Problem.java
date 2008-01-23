package org.shapelogic.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.tree.TreeNode;
import javax.swing.tree.DefaultTreeModel;
/*The abstract class for a formal problem.  You should subclass this and
 * implement the method successor, and possibly __init__, goal_test, and
 * path_cost. Then you will create instances of your subclass and solve them
 * with the various search functions.
 * 
 * @author Sami Badawi
 *
 */
public abstract class Problem  implements ProblemI {
	DefaultTreeModel _taskTree;
	Task _currentTask;
	List<Task> _pendingTask;
	public Problem(Task rootTask) {
		_taskTree = new DefaultTreeModel(rootTask);
		_pendingTask = new ArrayList<Task>();
		if (rootTask!= null)
			_pendingTask.add(rootTask);
	}

	public Problem() {
		this(null);
	}

	public abstract boolean goalTest();
    
    /** what should be the default sequence for this, I think that a deep first */
    public Task successor() {
		_currentTask = _pendingTask.remove(_pendingTask.size()-1);
    	return _currentTask;
    }

    public long pathCost(Task task) {
    	return task.getPathWork();
    }

    /** if I create a class without using a normal constructor */
    public void setTaskRoot(Task rootTask) {
    	_taskTree.setRoot(rootTask);
		if (rootTask!= null)
			_pendingTask.add(rootTask);
    }
}
