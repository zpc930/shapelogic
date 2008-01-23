package org.shapelogic.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.context.HashMapContext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/** Base class for Task, very fundamental all other tasks are subclassed from this
 * The majority of task functionalety are placed here.
 * 
 * @author Sami Badawi
 *
 * 
 * I might take the TreeNode part out of this later, then I will need a pointer to the self node.
 * 
 */
public class BaseTask<T> extends DefaultMutableTreeNode implements Task<T> {
	public static final String NAMED_TASKS = "NamedTasks";
	public static final String CONTEXT_CALCULATION_MAP = "ContextCalculationMap";
	public static long WORK_NOT_KNOWN = -999999;
	public static float DEFAULT_INTEREST = 1.0f;
	public static float DEFAULT_SCORE = 1.0f;
	
	protected LogicState _state = LogicState.Fresh;
	protected long _pathWork = WORK_NOT_KNOWN;
	protected long _selfWork = WORK_NOT_KNOWN;
	protected long _selfWorkEstimate = WORK_NOT_KNOWN;
	protected long _startTime = WORK_NOT_KNOWN;
	protected long _endTime = WORK_NOT_KNOWN;

	protected float _interest = DEFAULT_INTEREST;
	protected float _score = DEFAULT_SCORE;
	protected HashMapContext _localContext;
	protected Module _localModule;
	protected Injector _injector;
	protected T _value;
	private String _name;
	protected LogicState _lastSubTaskState;
	protected int _succededSubTasksCount = 0;
	protected int _failedSubTasksCount = 0;
	private boolean _printOnFail;

	public BaseTask(BaseTask parent, boolean createLocalContext) {
		super();
		BaseTask parentTask = (BaseTask)parent; 
		if (parentTask == null) {
			_pathWork = 0;
		}
		else {
			parentTask.add(this);
		}
		if (createLocalContext) {
			_localContext = new HashMapContext();
		}
	}
	
	public BaseTask(BaseTask parent) {
		this(parent,true);
	}

	/** might change into a realy dirty flag */
	public boolean isDirty() {
		return _state == LogicState.Fresh || _state == LogicState.Calculating;
	}

	public float getInterest() {
		return _interest;
	}

	public long getPathWork() {
		return _pathWork;
	}

	public float getScore() {
		return _score;
	}

	public long getSelfWork() {
		return _selfWork;
	}

	public long getSelfWorkEstimate() {
		return _selfWorkEstimate;
	}

	public LogicState getState() {
		return _state;
	}

	public void setInterest(float interest) {
		this._interest = interest;
	}

	public void setState(LogicState state) {
		this._state = state;
	}

	/** In case I take the context out of the node 
	 * 
	 * */
	@Deprecated
	public JexlContext getContext() {
		return this;
	}

	public JexlContext getLowestContext() {
		if (_localContext != null)
			return _localContext;
		return getParentTask().getLowestContext();
	}

	public JexlContext getLocalContext() {
		return _localContext;
	}

	/** Get map with everything over and including current */
	@Override
	public Map getVars() {
		Map result = null;
		if (parent == null) {
			result = new HashMap();
		}
		else {
			result = getParentTask().getVars();
		}
		Map localMap = null;
		if (_localContext != null)
			localMap = _localContext.getVars();
		if (localMap != null)
			result.putAll(localMap);
		return result;
	}

	@Override
	public void setVars(Map vars) {
		JexlContext lowestContext = getLowestContext();
		if (lowestContext != null)
			lowestContext.getVars().putAll(vars);
	}

	@Override
	public void preCalc() {
		_startTime = System.currentTimeMillis();
	}
	
	@Override
	public void postCalc() {
		_endTime = System.currentTimeMillis();
		_selfWork = _endTime - _startTime; 
		if (isRoot()) {
			_pathWork = _selfWork; 
		}
		else if (getParentTask().getPathWork() != WORK_NOT_KNOWN) {
			_pathWork += _selfWork;
		}
	}

	@Override
	public T calc() {
		preCalc();
		_value = mainCalc();
		postCalc();
		return _value;
	}

	public Task getParentTask() {
		if (isRoot())
			return null;
		else
			return (Task) getParent();
	}

	public TreeNode getSelfTreeNode() {
		return this;
	}

	@Override
	/** This should normally be overridden */
	public T mainCalc() {
		if (!isDirty())
			return _value;
		try {
			setup();
			if (match()) {
				_state = LogicState.SucceededDone;
				
			}
			else {
				_state = LogicState.FailedDone;
				doPrintOnFail();
			}
		} catch (Exception e) {
			_state = LogicState.FailedDone;
			doPrintOnFail();
		}
		return _value; 
	}
	/** match() should be seen like a unify that can also try to set fields
	 * So this can be overridden */
	@Override
	public boolean match() throws Exception {
		return false;
	}

	@Override
	public void setup() {}

	@Override
	public Module getLocalModule() {
		return _localModule;
	}

	@Override
	public Iterator<Module> iterator() {
		return new TaskModuleIterator(this);
	}

	@Override
	public Injector getInjector() {
		if (_injector != null)
			return _injector;
		_injector = Guice.createInjector(this);
		return _injector;
	}

	@Override
	public ContextCalculation getContextCalculation(String name) {
		Map<String, ContextCalculation> contextCalculationMap = getContextCalculationMap();
		if (contextCalculationMap == null)
			return null;
		return contextCalculationMap.get(name);
	}

	@Override
	public void setContextCalculation(String name,
			ContextCalculation contextCalculation) {
		Map map = getLowestContext().getVars();
		Map<String,ContextCalculation> contextCalculationMap = 
			(Map<String,ContextCalculation>) map.get(CONTEXT_CALCULATION_MAP);
		if (contextCalculationMap == null) {
			contextCalculationMap = new HashMap<String,ContextCalculation>();
			map.put(CONTEXT_CALCULATION_MAP, contextCalculationMap);
		}
		contextCalculationMap.put(name, contextCalculation);
	}

	@Override
	/** First see if the the value is stored directly as variable
	 * try to evaluate it in the current context
	 * Find a context calculation and try to calculate that 
	 */
	public Object findNamedValue(String name) {
		Object value = getNamedValue(name);
		if (value != null)
			return value;
		try {
			Expression e = ExpressionFactory.createExpression( name );
			value = e.evaluate(this);
			if (value != null)
				return value;
		} catch (Exception e) {
		}
		Map<String, ContextCalculation> contextCalculationMap = getContextCalculationMap();
		if (contextCalculationMap != null) {
			ContextCalculation contextCalculation = contextCalculationMap.get(name);
			if (contextCalculation != null) {
				try {
					value = contextCalculation.calculation(this);
					return value;
				} catch (Exception e) {
					e.printStackTrace(); //XXX change
				}
			}
		}
		return findEnumValue(name);
	}

	/** If a named value cannot be found for a String key, instead 
	 * see if the first part of the string is the name of a class set in the context 
	 * if it is the name of a class that is an emum then try to see if rest is an legal value 
	 * 
	 * @param name input that should be an enum value with the class at first e.g. PointType.T_JUNCTION
	 * @return null if not an enum value
	 */
	public Object findEnumValue(String name) {
		if (name == null)
			return null;
		if (!Character.isUpperCase(name.charAt(0)))
			return null;
		int indexOfPeriod = name.indexOf('.');
		if (indexOfPeriod<0)
			return null;
		String className = name.substring(0, indexOfPeriod);
		String rest = name.substring(indexOfPeriod+1);
		Object klass1 = getNamedValue(className);
			if (!(klass1 instanceof Class))
				return null;
			Class klass = (Class) klass1;
			if (klass.isEnum()) {
				return Enum.valueOf(klass, rest);
		}
		return null;
	}

	@Override
	public Object findNamedValueUsingSubTasks(String name) {
		Object value = findNamedValue(name);
		if (value != null)
			return value;
		Task task = getNamedTask(name);
		if (task == null)
			return null;
//		add((BaseTask)task); // To add it to the task tree
		value = task.getValue();
		return value;
	}
	
	@Override
	/** This only works with exact variable names not */
	public Object getNamedValue(String name) {
		Object value = null;
		if (_localContext != null) {
			value = _localContext.getVars().get(name);
		}
		if (value != null)
			return value;
		if (getParentTask() == null)
			return null;
		return getParentTask().getNamedValue(name);
	}

	@Override
	public Map<String, ContextCalculation> getContextCalculationMap() {
		Map map = getLowestContext().getVars();
		return (Map<String,ContextCalculation>) map.get(CONTEXT_CALCULATION_MAP);
	}

	/** calculate the next sub task 
	 * 
	 * If it succeed it will return true and pop the last sub task.
	 * */
	@Override
	public Object calcNextSubTask() {
		if (children == null)
			return null;
		int childSize = children.size();
		Task lastTask = (Task) children.get(childSize-1);
		Object result = lastTask.calc();
		_lastSubTaskState = lastTask.getState();
		if (LogicState.SucceededDone.equals(_lastSubTaskState))
			_succededSubTasksCount++;
		else
			_failedSubTasksCount++;
		if (true || LogicState.SucceededDone.equals(_lastSubTaskState)) {
			this.remove(childSize-1);
			return result;
		}
		return null;
	}

	@Override
	public Task getNamedTask(String name) {
		Map<String,Task> taskMap = (Map<String,Task>) getNamedValue(NAMED_TASKS);
		if (taskMap == null)
			return null;
		return taskMap.get(name);
	}

	@Override
	public void setNamedTask(String name, Task task) {
		Map<String,Task> taskMap = (Map<String,Task>) getNamedValue(NAMED_TASKS);
		if (taskMap == null) {
			taskMap = new TreeMap<String,Task>();
			Map map = getLowestContext().getVars();
			map.put(NAMED_TASKS,taskMap);
		}
		taskMap.put(name, task);
	}

	@Override
	public T getValue() {
		if (isDirty())
			calc();
		return _value;
	}

	@Override
	/** Not sure if I need this */
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	public LogicState getLastSubTaskState() {
		return _lastSubTaskState;
	}

	@Override
	public void setNamedValue(String name, Object value) {
		JexlContext context = this.getLowestContext();
		if (context == null)
			return;
		context.getVars().put(name, value);
	}
	
	@Override
	public void setClassInContext(Class klass) {
		JexlContext context = this.getLowestContext();
		String className = klass.getName();
		String classNameBase = className;
		int lastIndex = className.lastIndexOf('.');
		classNameBase = className.substring(lastIndex+1); 
		context.getVars().put(classNameBase, klass);
	}
	
	@Override
	public void setClassInContext(Collection<Class> classes) {
		for (Class klass: classes) {
			setClassInContext(klass);
		}
	}
	
	@Override
	public void setClassInContext(Class[] classes) {
		for (Class klass: classes) {
			setClassInContext(klass);
		}
	}

	@Override
	public String errorOnFail() {
		return toString();
	}

	@Override
	public void doPrintOnFail() {
		if (isPrintOnFail())
			System.out.println(errorOnFail());
	}

	@Override
	public boolean isPrintOnFail() {
		return _printOnFail;
	}

	@Override
	public void setPrintOnFail(boolean printOnFail) {
		_printOnFail = printOnFail;
	}
}
