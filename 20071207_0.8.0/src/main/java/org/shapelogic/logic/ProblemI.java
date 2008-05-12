package org.shapelogic.logic;

/** Not sure if i need this one
 * The abstract class for a formal problem.  You should subclass this and
 * implement the method successor, and possibly __init__, goal_test, and
 * path_cost. Then you will create instances of your subclass and solve them
 * with the various search functions.
 * 
 * @author Sami Badawi
 *
 */
public interface ProblemI {
    public abstract Task successor();
    public abstract boolean goalTest();
    public abstract long pathCost(Task task);
}
