package org.shapelogic.streams;

import java.util.ArrayList;
import org.shapelogic.mathematics.NaturalNumberStream;

import junit.framework.TestCase;
import org.shapelogic.calculation.SimpleRecursiveContext;

/** Test ArrayOutputListStream.
 * 
 * @author Sami Badawi
 *
 */
public class ArrayOutputListStreamTest extends TestCase {
	NumberedStream<Integer> _naturalNumberStream;
	NumberedStream<Integer> _naturalNumberStream1to3;
	SimpleRecursiveContext _context;
    ArrayOutputListStream _arrayOutputListStream;
    ArrayList _names;

	@Override
	public void setUp() {
        _context = new SimpleRecursiveContext(null);
        _names = new ArrayList();
        _names.add("naturalNumberStream");
        _names.add("naturalNumberStream1to3");
        _arrayOutputListStream = new ArrayOutputListStream(_names, _context);
		_naturalNumberStream = new NaturalNumberStream();
		_naturalNumberStream1to3 = new NaturalNumberStream(1,3);
        _context.getContext().put("naturalNumberStream", _naturalNumberStream);
        _context.getContext().put("naturalNumberStream1to3", _naturalNumberStream1to3);
	}
	
	public void testArrayInputListStreamGet() {
        assertNotNull(_arrayOutputListStream);
        assertTrue(_arrayOutputListStream.hasNext());
        assertEquals(0., _arrayOutputListStream.get(0)[0]);
        assertEquals(1., _arrayOutputListStream.get(0)[1]);
        assertEquals(1., _arrayOutputListStream.get(1)[0]);
        assertEquals(2., _arrayOutputListStream.get(1)[1]);
        assertEquals(2., _arrayOutputListStream.get(2)[0]);
        assertEquals(3., _arrayOutputListStream.get(2)[1]);
        assertTrue(_arrayOutputListStream.hasNext());
        assertNull(_arrayOutputListStream.get(3));
        assertTrue(_arrayOutputListStream.hasNext());
        assertEquals(0., _arrayOutputListStream.get(0)[0]);
        assertEquals(1., _arrayOutputListStream.get(0)[1]);
        assertEquals(1., _arrayOutputListStream.get(1)[0]);
        assertEquals(2., _arrayOutputListStream.get(1)[1]);
        assertEquals(2., _arrayOutputListStream.get(2)[0]);
        assertEquals(3., _arrayOutputListStream.get(2)[1]);
        assertTrue(_arrayOutputListStream.hasNext());
        assertNull(_arrayOutputListStream.get(3));
        assertTrue(_arrayOutputListStream.hasNext());
	}
	
	public void testArrayInputListStreamNext() {
        assertNotNull(_arrayOutputListStream);
        assertTrue(_arrayOutputListStream.hasNext());
        for (int i = 0; _arrayOutputListStream.hasNext(); i++ ) {
            double[] next = _arrayOutputListStream.next();
            assertEquals(0. + i, next[0]);
            assertEquals(1. + i, next[1]);
        }
        assertFalse(_arrayOutputListStream.hasNext());
        assertNull(_arrayOutputListStream.next());
        assertFalse(_arrayOutputListStream.hasNext());
	}

}
