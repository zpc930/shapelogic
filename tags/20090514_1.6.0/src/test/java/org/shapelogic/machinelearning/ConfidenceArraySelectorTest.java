package org.shapelogic.machinelearning;

import java.util.ArrayList;
import junit.framework.TestCase;

import org.shapelogic.util.Constants;

/** Test of ConfidenceArraySelector. <br />
 * 
 * Translates a double[] that could come from a neural network to either the
 * number of the one that is winning walue if any is or to a name for that.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ConfidenceArraySelectorTest extends TestCase {
    static final String INPUT_1 = "input1";
    static final String INPUT_2 = "input1";
    ArrayList<String> _inputNameList;
    double[][] _inputArray = {
        {0.,0.},
        {0.,1.},
        {1.,0.},
        {1.,1.},
    };
    ConfidenceArraySelector _confidenceArrayListStream;
	@Override
	public void setUp(){
        _inputNameList = new ArrayList();
        _inputNameList.add(INPUT_1);
        _inputNameList.add(INPUT_2);
        _confidenceArrayListStream = new ConfidenceArraySelector(_inputNameList);
	}
	
	public void testWithNextNames() {
        _confidenceArrayListStream.setOhNames(_inputNameList);
		assertEquals(Constants.NO_OH, _confidenceArrayListStream.invoke(_inputArray[0]));
		assertEquals(INPUT_2, _confidenceArrayListStream.invoke(_inputArray[1]));
		assertEquals(INPUT_1, _confidenceArrayListStream.invoke(_inputArray[2]));
		assertEquals(Constants.NO_OH, _confidenceArrayListStream.invoke(_inputArray[3]));
	}
	
}
