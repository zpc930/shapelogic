package org.shapelogic.logic;

/** This is the stages that a calculation, task or variable can be in
 * 
 * @author Sami Badawi
 *
 */
public enum LogicState {
	Fresh, Instantiated, Calculating, FailedDone, SucceededDone;
}
