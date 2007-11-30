package org.shapelogic.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.shapelogic.logic.BaseTask;
import org.shapelogic.logic.BooleanTask;
import org.shapelogic.logic.CountCollectionGreaterTask;
import org.shapelogic.logic.CountCollectionTask;
import org.shapelogic.logic.FilterCountGreaterTask;
import org.shapelogic.logic.FilterCountTask;
import org.shapelogic.logic.NumericGreaterTask;
import org.shapelogic.logic.SimpleNumericTask;

import static org.shapelogic.util.Constants.SIMPLE_NUMERIC_TASK;
import static org.shapelogic.util.Constants.COUNT_COLLECTION_TASK;
import static org.shapelogic.util.Constants.FILTER_COUNT_TASK;

import static org.shapelogic.util.Constants.NUMERIC_GREATER_TASK;
import static org.shapelogic.util.Constants.COUNT_COLLECTION_GREATER_TASK;
import static org.shapelogic.util.Constants.FILTER_COUNT_GREATER_TASK;

import static org.shapelogic.util.Constants.BOOLEAN_TASK;

@Entity
@Table(name = "NUMERIC_RULE")
/** Data class for definitions of rules.
 * 
 * It now translate the different rules to different low level tasks
 * 
 * This can be used with Hibernate for saving this rule to a database
 * 
 * @author Sami Badawi
 *
 */
public class NumericRule {
	private Long _id;
	/** Name of parent OH, Object Hypothesis */
	private String _parentOH;
	/** Name of this individual rule, there can be several rules for 1 OH */
	private String _name;
	/** The name of the object you want to do the test on in the JEXL context */
	private String _variable;
	/** The expression that you want to evaluate in the JEXL context */
	private String _expression;
	/** This is the expected value or the comparison value */
	private Double _expected;
	/** This determine what Task is created based on this rule */
	private String _className;
	
	@Id @GeneratedValue @Column(name = "RULE_ID")
	public Long getId() {
		return _id;
	}
	
	public NumericRule(String parentOH, String name, String variable, 
			String expression, Double expected, String className) {
		_variable = variable;
		_expression = expression; 
		_name = name;
		_expected = expected;
		_parentOH = parentOH;
		_className = className;
	}

	public NumericRule() {
		this(null,null,null,null,null,null);
	}

	public void setId(Long id) {
		this._id = id;
	}

	public String getExpression() {
		return _expression;
	}

	public void setExpression(String expression) {
		this._expression = expression;
	}

	public Double getExpected() {
		return _expected;
	}

	public void setExpected(Double expected) {
		this._expected = expected;
	}

	public String getParentOH() {
		return _parentOH;
	}

	public void setParentOH(String parentOH) {
		this._parentOH = parentOH;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}
	
	@Override
	public String toString() {
		String result = getParentOH() + "." + getName() + " " + getId() + 
		  ": " + getExpression() + 
		  " = " + getExpected();
		return result;
	}

	public String getVariable() {
		return _variable;
	}

	public void setVariable(String variable) {
		_variable = variable;
	}
	
	public String getVariableAndExpression() {
		String varAndExpression = "";
		if (getVariable() != null)
			varAndExpression = getVariable() + ".";
		varAndExpression += getExpression();
		return varAndExpression;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String name) {
		_className = name;
	}
	
	/** Create task based on rule.
	 * 
	 * @param parentTask used for inserting this rule as a child of parent 
	 * @return the task representing the rule
	 */
	public BaseTask makeTask(BaseTask parentTask){
		BaseTask task = null;

		//Equality test tasks
		if (SIMPLE_NUMERIC_TASK.equalsIgnoreCase(getClassName())) {
			task = new SimpleNumericTask(parentTask, false, getVariableAndExpression(),getExpected());
		}
		else if (COUNT_COLLECTION_TASK.equalsIgnoreCase(getClassName())) {
			task = new CountCollectionTask(parentTask, false, getVariable(),getExpression(),getExpected());
		}
		else if (FILTER_COUNT_TASK.equalsIgnoreCase(getClassName())) {
			task = new FilterCountTask(parentTask, false, getVariable(),getExpression(),getExpected());
		}
		
		//Greater test tasks
		else if (NUMERIC_GREATER_TASK.equalsIgnoreCase(getClassName())) {
			task = new NumericGreaterTask(parentTask, false, getVariableAndExpression(),getExpected());
		}
		else if (COUNT_COLLECTION_GREATER_TASK.equalsIgnoreCase(getClassName())) {
			task = new CountCollectionGreaterTask(parentTask, false, getVariable(),getExpression(),getExpected());
		}
		else if (FILTER_COUNT_GREATER_TASK.equalsIgnoreCase(getClassName())) {
			task = new FilterCountGreaterTask(parentTask, false, getVariable(),getExpression(),getExpected());
		}
		
		//Boolean test tasks
		else if (BOOLEAN_TASK.equalsIgnoreCase(getClassName())) {
			task = new BooleanTask(parentTask, false, getExpression());
		}
		else { //Default is SIMPLE_NUMERIC_TASK 
			task = new SimpleNumericTask(parentTask, false, getVariableAndExpression(),getExpected());
		}
		return task;
	}

}
