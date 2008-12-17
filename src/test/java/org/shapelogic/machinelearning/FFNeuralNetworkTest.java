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

    /** Weights found using the Joone Neural Networks.  */
    final static double[][] WEIGHTS_FOR_AND = {
        {
            1.5, //Bias first hidden layer

            1.,
            1.
        }
    };

    /** Weights found using the Joone Neural Networks.  */
    final static double[][] WEIGHTS_FOR_OR = {
        {
            0.5, //Bias first hidden layer

            1.,
            1.
        }
    };

    /** Weights found using the Joone Neural Networks.  */
    final static double[][] WEIGHTS_FOR_NOT = {
        {
            -0.5, //Bias first hidden layer

            -1.
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

    private FFNeuralNetwork makeAndNN() {
        FFNeuralNetwork andNn = new FFNeuralNetwork(2,1);
        andNn.addLayer(WEIGHTS_FOR_AND[0]);
        return andNn;
    }

    private FFNeuralNetwork makeOrNN() {
        FFNeuralNetwork orNn = new FFNeuralNetwork(2,1);
        orNn.addLayer(WEIGHTS_FOR_OR[0]);
        return orNn;
    }

    private FFNeuralNetwork makeNotNN() {
        FFNeuralNetwork notNn = new FFNeuralNetwork(1,1);
        notNn.addLayer(WEIGHTS_FOR_NOT[0]);
        return notNn;
    }

//======================XOR NN======================

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

//======================AND NN======================

    public void testAndNeuralNetwork00() {
        FFNeuralNetwork xOrNn = makeAndNN();
        assertNNFalse( xOrNn.calc(new double[]{0.,0.})[0]);
    }

    public void testAndNeuralNetwork01() {
        FFNeuralNetwork xOrNn = makeAndNN();
        assertNNFalse( xOrNn.calc(new double[]{0.,1.})[0]);
    }

    public void testAndNeuralNetwork10() {
        FFNeuralNetwork xOrNn = makeAndNN();
        assertNNFalse( xOrNn.calc(new double[]{1.,0.})[0]);
    }

    public void testAndNeuralNetwork11() {
        FFNeuralNetwork xOrNn = makeAndNN();
        assertNNTrue( xOrNn.calc(new double[]{1.,1.})[0]);
    }


//======================OR NN======================

    public void testOrNeuralNetwork00() {
        FFNeuralNetwork xOrNn = makeOrNN();
        assertNNFalse( xOrNn.calc(new double[]{0.,0.})[0]);
    }

    public void testOrNeuralNetwork01() {
        FFNeuralNetwork xOrNn = makeOrNN();
        assertNNTrue( xOrNn.calc(new double[]{0.,1.})[0]);
    }

    public void testOrNeuralNetwork10() {
        FFNeuralNetwork xOrNn = makeOrNN();
        assertNNTrue( xOrNn.calc(new double[]{1.,0.})[0]);
    }

    public void testOrNeuralNetwork11() {
        FFNeuralNetwork xOrNn = makeOrNN();
        assertNNTrue( xOrNn.calc(new double[]{1.,1.})[0]);
    }

//======================OR NN======================

    public void testNotNeuralNetwork0() {
        FFNeuralNetwork xOrNn = makeNotNN();
        assertNNTrue( xOrNn.calc(new double[]{0.})[0]);
    }

    public void testNotNeuralNetwork1() {
        FFNeuralNetwork xOrNn = makeNotNN();
        assertNNFalse( xOrNn.calc(new double[]{1.})[0]);
    }

    public void testSigmoidFunction() {
        FFNeuralNetwork xOrNn = makeXORNN();
        assertEquals( 0.5, xOrNn.transform(0));
        assertEquals( 0.9999546021312976, xOrNn.transform(10));
        assertEquals( 4.5397868702434395E-5, xOrNn.transform(-10));
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
