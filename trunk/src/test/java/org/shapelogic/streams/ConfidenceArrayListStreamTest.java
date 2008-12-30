package org.shapelogic.streams;

import java.util.ArrayList;
import junit.framework.TestCase;

import org.shapelogic.calculation.SimpleRecursiveContext;
import org.shapelogic.util.Constants;

/** Test of ConfidenceArrayListStream. <br />
 * 
 * Translates a double[] that could come from a neural network to either the
 * number of the one that is winning walue if any is or to a name for that.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ConfidenceArrayListStreamTest extends TestCase {
    static final String INPUT_ARRAY_NAME = "inputArrayName";
    static final String INPUT_1 = "input1";
    static final String INPUT_2 = "input1";
    ArrayList<String> _inputNameList;
    double[][] _inputArray = {
        {0.,0.},
        {0.,1.},
        {1.,0.},
        {1.,1.},
    };
    ArrayList<double[]> _inputList;
    ListStream<double[]> _inputStream;
	SimpleRecursiveContext _recursiveContext;
    ConfidenceArrayListStream _confidenceArrayListStream;
	@Override
	public void setUp(){
        _inputList = new ArrayList();
        for (double[] input: _inputArray)
            _inputList.add(input);
        _inputNameList = new ArrayList();
        _inputNameList.add(INPUT_1);
        _inputNameList.add(INPUT_2);
        _inputStream = new WrappedListStream(_inputList);
        _recursiveContext = new SimpleRecursiveContext(null);
        _recursiveContext.getContext().put(INPUT_ARRAY_NAME, _inputStream);
        _confidenceArrayListStream = new ConfidenceArrayListStream(null, 
                INPUT_ARRAY_NAME, _recursiveContext, null, Constants.LAST_UNKNOWN);
	}
	
	public void testWithNextNames() {
        _confidenceArrayListStream.setOhNames(_inputNameList);
		assertEquals(Constants.NO_OH, _confidenceArrayListStream.next());
		assertEquals(INPUT_2, _confidenceArrayListStream.next());
		assertEquals(INPUT_1, _confidenceArrayListStream.next());
		assertEquals(Constants.NO_OH, _confidenceArrayListStream.next());
		assertFalse(_confidenceArrayListStream.hasNext());
		assertNull(_confidenceArrayListStream.next());
	}
	
}
