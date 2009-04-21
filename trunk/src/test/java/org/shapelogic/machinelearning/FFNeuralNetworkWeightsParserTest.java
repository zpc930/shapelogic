package org.shapelogic.machinelearning;

import java.io.Reader;
import java.io.StringReader;

import junit.framework.TestCase;

/** Test of FFNeuralNetworkWeightsParser.<br />
 * 
 * @author Sami Badawi
 *
 */
public class FFNeuralNetworkWeightsParserTest extends TestCase {
	
	
	public void test() {
		String inputString = "========== FEATURES\n" +
				"input1\n" +
				"input2 input3\n" +
				"========== RESULTS\n" +
				"output1\n" +
				"output2 output3\n" +
				"========== WEIGHTS\n" +
				"1. 2. 3.\n" +
				"========== WEIGHTS\n" +
				"4. 5.";
		Reader input = new StringReader(inputString); 
		FFNeuralNetworkWeightsParser parser = new FFNeuralNetworkWeightsParser();
		FFNeuralNetworkWeights result = parser.parse(input);
		assertNotNull(result);
		assertEquals(3, result.getFeatureList().size());
		assertEquals(3, result.getOhList().size());
		assertEquals(2, result.getWeights().length);
		assertEquals(1., result.getWeights()[0][0]);
		assertEquals(2., result.getWeights()[0][1]);
		assertEquals(3., result.getWeights()[0][2]);
		assertEquals(4., result.getWeights()[1][0]);
		assertEquals(5., result.getWeights()[1][1]);
	}

}
