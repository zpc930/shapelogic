package org.shapelogic.machinelearning;

import java.io.Reader;
import java.io.StringReader;

import junit.framework.TestCase;

/** 
 * 
 * @author Sami Badawi
 *
 */
public class FFNeuralNetworkWeightsParserTest extends TestCase {
	
	
	public void test() {
		String inputString = "========== FEATURES\n" +
				"input1\n" +
				"input2 input3\n";
		Reader input = new StringReader(inputString); 
		FFNeuralNetworkWeightsParser parser = new FFNeuralNetworkWeightsParser();
		FFNeuralNetworkWeights result = parser.parse(input);
		assertNotNull(result);
		assertEquals(3, result.getFeatureList().size());
	}

}
