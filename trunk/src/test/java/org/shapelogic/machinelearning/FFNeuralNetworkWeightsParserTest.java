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
	
	public static final String INPUT_NO_PRINT = "========== FEATURES\n" +
	"input1\n" +
	"input2 input3\n" +
	"========== RESULTS\n" +
	"output1\n" +
	"output2 output3\n" +
	"========== WEIGHTS\n" +
	"1. 2. 3.\n" +
	"========== WEIGHTS\n" +
	"4. 5.";
	
	public static final String INPUT_ONLY_PRINT = "========== PRINTS\n" +
	"input1\n" +
	"input3\n";
	
	public static final String INPUT_BLOCK_START = "========== ";
	
	public static final String INPUT_RULES = "========== def A \n"+ 
		"POINT_COUNT == 5 ";// +
//		"LINE_COUNT == 5 \n" +
//		"HOLE_COUNT == 1 \n";
	
	public void testNoPrintNormalSequence() {
		String inputString = INPUT_NO_PRINT;
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

	public void testNormalSequence() {
		String inputString = INPUT_ONLY_PRINT + INPUT_NO_PRINT;
		Reader input = new StringReader(inputString); 
		FFNeuralNetworkWeightsParser parser = new FFNeuralNetworkWeightsParser();
		FFNeuralNetworkWeights result = parser.parse(input);
		assertNotNull(result);
		assertEquals(3, result.getFeatureList().size());
		assertEquals(3, result.getOhList().size());
		assertEquals(2, result.getPrintList().size());
		assertEquals(2, result.getWeights().length);
		assertEquals(1., result.getWeights()[0][0]);
		assertEquals(2., result.getWeights()[0][1]);
		assertEquals(3., result.getWeights()[0][2]);
		assertEquals(4., result.getWeights()[1][0]);
		assertEquals(5., result.getWeights()[1][1]);
	}
	
	public void testRuleDefinition() {
		String inputString = INPUT_RULES + INPUT_BLOCK_START +"\n";
		Reader input = new StringReader(inputString); 
		FFNeuralNetworkWeightsParser parser = new FFNeuralNetworkWeightsParser();
		FFNeuralNetworkWeights result = parser.parse(input);
		assertNotNull(result);
		assertEquals(0, result.getRulePredicates().size()); //XXX
	}
	
	public void testFile() throws Exception {
		FFNeuralNetworkWeightsParser parser = new FFNeuralNetworkWeightsParser();
		String path = "src/test/resources/data/neuralnetwork/default_particle_nn.txt";
		FFNeuralNetworkWeights result = parser.parse(path);
		assertNotNull(result);
		assertEquals(1, result.getFeatureList().size());
		assertEquals(2, result.getOhList().size());
		assertEquals(1, result.getWeights().length);
		assertEquals(1., result.getWeights()[0][0]);
		assertEquals(-1., result.getWeights()[0][1]);
		assertEquals(-1., result.getWeights()[0][2]);
		assertEquals(1., result.getWeights()[0][3]);
	}

}
