package org.shapelogic.logic;

import org.shapelogic.logic.Problem;
import org.shapelogic.logic.ProblemI;

import junit.framework.TestCase;

/*The abstract class for a formal problem.  You should subclass this and
 * implement the method successor, and possibly __init__, goal_test, and
 * path_cost. Then you will create instances of your subclass and solve them
 * with the various search functions.
 * 
 * @author Sami Badawi
 *
 */
public abstract class ProblemTest extends TestCase
{
    ProblemI p = new Problem(null) {
	    public boolean goalTest(){return true;}
    };
}
