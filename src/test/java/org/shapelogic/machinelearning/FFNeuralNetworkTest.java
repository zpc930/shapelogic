package org.shapelogic.machinelearning;

import junit.framework.TestCase;

/** Test of Feed Forward Neural Network with external training. <br />
 *
 * The bias is considered the zeroth element of the synaptic weight.
 *
 * @author Sami Badawi
 */
public class FFNeuralNetworkTest extends TestCase {

    /** Weights found using the Joone Neural Networks.  */
    final static double[][] WEIGHTS_FOR_XOR = {
        {
            -0.04987729759969203, 0.043065103789957926, -0.09799030765920698,
            //Bias first hidden layer

            -0.1732212620281772,  0.18873333446145452,  -0.06691055475908447,
            -0.182843680965388,  -0.11767688078014925,   0.02648590823005495
        },
        {
            0.11006835154877193, //Bias secone hidden layer

            0.08510015837536089,
            0.04260661544776684,
            0.13925520732807045
        }
    };

    public void testConstructor() {
        FFNeuralNetwork nn = new FFNeuralNetwork(2,1);
        assertEquals(2,nn.nInputNodes);
        assertEquals(1,nn.nOutputNodes);
    }

    private FFNeuralNetwork makeXORNN() {
        FFNeuralNetwork xOrNn = new FFNeuralNetwork(2,1);
        xOrNn.addLayer(WEIGHTS_FOR_XOR[0]);
        xOrNn.addLayer(WEIGHTS_FOR_XOR[1]);
        return xOrNn;
    }

    public void testXORNeuralNetwork00() {
        FFNeuralNetwork xOrNn = new FFNeuralNetwork(2,1);
        assertEquals(2, xOrNn.nInputNodes);
        assertEquals(1, xOrNn.nOutputNodes);
        assertEquals(2, xOrNn.getLayerNodesInTopLayer());
        assertTrue(xOrNn.addLayer(WEIGHTS_FOR_XOR[0]));
        assertEquals(3, xOrNn.getLayerNodesInTopLayer());
        assertTrue(xOrNn.addLayer(WEIGHTS_FOR_XOR[1]));
        assertEquals(1, xOrNn.getLayerNodesInTopLayer());
        double[] result = xOrNn.calc(new double[]{0.,0.});
        assertNotNull(result);
        assertEquals(1, result.length);
        assertNNFalse(result[0]);
    }

    public void testXORNeuralNetwork01() {
        FFNeuralNetwork xOrNn = makeXORNN();
        assertNNTrue( xOrNn.calc(new double[]{0.,1.})[0]);
    }

    public void testXORNeuralNetwork10() {
        FFNeuralNetwork xOrNn = makeXORNN();
        assertNNTrue( xOrNn.calc(new double[]{1.,0.})[0]);
    }

    public void testXORNeuralNetwork11() {
        FFNeuralNetwork xOrNn = makeXORNN();
        assertNNFalse( xOrNn.calc(new double[]{1.,1.})[0]);
    }

    public void assertNNTrue(double input) {
        boolean result = 0.5 < input;
        if (!result)
            System.out.println("input: " + input);
        assertTrue(result);
    }

    public void assertNNFalse(double input) {
        boolean result = input <= 0.5;
        if (!result)
            System.out.println("input: " + input);
        assertTrue(result);
    }
}
