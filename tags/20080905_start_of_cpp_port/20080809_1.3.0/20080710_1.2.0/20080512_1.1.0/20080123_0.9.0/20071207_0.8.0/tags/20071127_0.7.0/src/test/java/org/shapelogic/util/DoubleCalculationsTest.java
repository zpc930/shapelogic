package org.shapelogic.util;

import junit.framework.TestCase;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class DoubleCalculationsTest  extends TestCase {
	
    /** the negative number are not handled like a cyclic group would be
     * they are treated as a separate negative space 
     */
	public void testModulo() {
    	assertEquals( -5, -13 % 8);
    	
    }

} 