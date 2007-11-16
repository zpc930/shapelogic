package org.shapelogic.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NUMERIC_RULE")
/** Data class for rule to be used with Hibernate  
 * 
 * @author Sami Badawi
 *
 */
public class NumericRule {
	private Long _id;
	private String _parentOH;
	private String _name;
	private String _variable;
	private String _expression;
	private Double _expected;
	
	@Id @GeneratedValue @Column(name = "RULE_ID")
	public Long getId() {
		return _id;
	}
	
	public NumericRule(String parentOH, String name, String variable, String expression, Double expected) {
		_variable = variable;
		_expression = expression; 
		_name = name;
		_expected = expected;
		_parentOH = parentOH;
	}

	public NumericRule() {
		this(null,null,null,null,null);
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

}
