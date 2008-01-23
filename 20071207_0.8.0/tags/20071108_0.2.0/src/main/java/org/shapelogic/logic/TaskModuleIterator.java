package org.shapelogic.logic;

import java.util.Iterator;

import com.google.inject.Module;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class TaskModuleIterator implements Iterator<Module> {
	private Task _currentTask;
	
	public TaskModuleIterator(Task currentTask){
		_currentTask = currentTask;
		findNext();
	}

	@Override
	public boolean hasNext() {
		if (_currentTask == null)
			return false;
		if (_currentTask.getLocalModule() != null)
			return true;
		return false;
	}

	@Override
	public Module next() {
		if (_currentTask == null)
			return null;
		Module result = null;
		if (_currentTask.getLocalModule() != null)
			result = _currentTask.getLocalModule();
		_currentTask = _currentTask.getParentTask();
		findNext();
		return result;
	}

	void findNext() {
		while (true) {
			if (_currentTask == null)
				return;
			if (_currentTask.getLocalModule() != null)
				return;
			_currentTask = _currentTask.getParentTask();
		}
	}
	
	@Override
	public void remove() {
	}

}
