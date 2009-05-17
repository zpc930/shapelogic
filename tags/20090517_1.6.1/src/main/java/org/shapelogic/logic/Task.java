package org.shapelogic.logic;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.commons.jexl.JexlContext;
import org.shapelogic.calculation.CalcInvoke;
import org.shapelogic.calculation.LazyCalc;

import com.google.inject.Injector;
import com.google.inject.Module;

/**   
 * 
 * @author Sami Badawi
 *
 */
public interface Task<T> extends TreeNode, JexlContext, CalcInvoke<T>, Iterable<Module> {

	//State
	LogicState getState();
	void setState(LogicState state);

	//Simple fields
	/** measured in milliseconds */
	long getPathWork(); 
	/** measured in milliseconds */
	long getSelfWork();
	long getSelfWorkEstimate();
	float getInterest();
	void setInterest(float interest);
	float getScore();

	//Node related
	JexlContext getContext(); //This should now return self
	JexlContext getLocalContext();
	JexlContext getLowestContext();
	Task getParentTask();
	/** Needed if TreeNode gets taken out of this interface*/
	TreeNode getSelfTreeNode(); //This should now return self
	
	//Calc this excersise the task
	void preCalc();
	T mainCalc();
	void postCalc();
	boolean match() throws Exception;
	Module getLocalModule();
	Iterator<Module> iterator();
	Injector getInjector();
	ContextCalculation getContextCalculation(String name);
	void setContextCalculation(String name, ContextCalculation contextCalculation);
	void setClassInContext(Class klass);
	void setClassInContext(Collection<Class> classes);
	void setClassInContext(Class[] classes);
	Object getNamedValue(String name);
	Object findNamedValue(String name);
	Object findNamedValueUsingSubTasks(String name);
	void setNamedValue(String name, Object value);
	Map<String,ContextCalculation> getContextCalculationMap();
	Object calcNextSubTask();
	Task getNamedTask(String name);
//	Iterator<Task> getNamedTaskIterator(String name);
	void setNamedTask(String name, Task task);
	T getValue();
	String getName();
	void setName(String name);
	LogicState getLastSubTaskState();
	String errorOnFail();
	void doPrintOnFail();
	void setPrintOnFail(boolean PrintOnFail);
	boolean isPrintOnFail();
}
