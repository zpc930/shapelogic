package org.shapelogic.mathematics;

import junit.framework.TestCase;

/** Test NumericTruthTableStream.
 *
 * @author Sami Badawi
 */
public class NumericTruthTableStreamTest extends TestCase {

    public void testPhase1() {
        NumericTruthTableStream numericTruthTableStream = new NumericTruthTableStream();
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
    }

    public void testSetPhase1() {
        NumericTruthTableStream numericTruthTableStream = new NumericTruthTableStream(1);
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
    }

    public void testSetPhase1True() {
        NumericTruthTableStream numericTruthTableStream = new NumericTruthTableStream(1, true);
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
        assertFalse(numericTruthTableStream.hasNext());
        assertNull(numericTruthTableStream.next());
    }

    public void testPhase2() {
        NumericTruthTableStream numericTruthTableStream = new NumericTruthTableStream(2);
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
    }

    public void testPhase2True() {
        NumericTruthTableStream numericTruthTableStream = new NumericTruthTableStream(2, true);
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
        assertFalse(numericTruthTableStream.hasNext());
        assertNull(numericTruthTableStream.next());
    }

    public void testPhase4True() {
        NumericTruthTableStream numericTruthTableStream = new NumericTruthTableStream(4, true);
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(0., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
        assertEquals(1., numericTruthTableStream.next());
        assertFalse(numericTruthTableStream.hasNext());
        assertNull(numericTruthTableStream.next());
    }
}
