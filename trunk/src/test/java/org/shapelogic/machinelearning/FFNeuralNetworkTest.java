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
    public final static double[][] WEIGHTS_FOR_XOR = {
        {
            //Output 0        , 1                , 2
            2.7388686085992333, 5.505721328606976, 4.235258932026585, //bias
                                                                         //Input
            -6.598582463774703, -3.678198637390036, -2.9604962169635076, // 0
            -6.59030690954159, -3.7790406961228347, -2.845930422442215   // 1
        },
        {
            -5.27100082610628,  //Bias for hidden second layer

            -10.45330943056037,
            6.582922049952558,
            4.7139611039662945
        }
    };

    /** Logic And as a neural network.  */
    public final static double[][] WEIGHTS_FOR_AND = {
        {
            -1.5, //Bias first hidden layer

            1.,
            1.
        }
    };

    /** Logic Or as a neural network.  */
    public final static double[][] WEIGHTS_FOR_OR = {
        {
            -0.5, //Bias first hidden layer

            1.,
            1.
        }
    };

    /** Logic Not as a neural network.  */
    public final static double[][] WEIGHTS_FOR_NOT = {
        {
            0.5, //Bias first hidden layer

            -1.
        }
    };

    /** Logic Identity as a neural network.  */
    public final static double[][] WEIGHTS_IDENTITY_1_1 = {
        {
            0., //Bias first hidden layer

            1.
        }
    };

    /** Logic Or as a neural network.  */
    public final static double[][] WEIGHTS_FOR_OR_MULTI_LAYER = {
        {
            -0.5, //Bias first hidden layer

            1.,
            1.
        },
        {
            -0.5, //Bias second hidden layer, not that it is not the identity

            1.
        }
    };

    public void testConstructor() {
        FFNeuralNetwork nn = new FFNeuralNetwork(2,1);
        assertEquals(2,nn.nInputNodes);
        assertEquals(1,nn.nOutputNodes);
    }

    private FFNeuralNetwork makeXORNNIndividual() {
        FFNeuralNetwork xOrNn = new FFNeuralNetwork(2,1);
        xOrNn.addLayer(WEIGHTS_FOR_XOR[0]);
        xOrNn.addLayer(WEIGHTS_FOR_XOR[1]);
        return xOrNn;
    }

    static public FFNeuralNetwork makeORNNMultiLayeredFlawed() {
        FFNeuralNetwork orNn = new FFNeuralNetwork(2,1);
        orNn.addLayer(WEIGHTS_FOR_OR[0]);
        orNn.addLayer(WEIGHTS_IDENTITY_1_1[0]);
        return orNn;
    }

    private FFNeuralNetwork makeXORNN() {
        FFNeuralNetwork xOrNn = new FFNeuralNetwork(2,1);
        xOrNn.addLayers(WEIGHTS_FOR_XOR);
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
        double[] result = xOrNn.invoke(new double[]{0.,0.});
        assertNotNull(result);
        assertEquals(1, result.length);
        assertNNFalse(result[0]);
    }

    public void testXORNeuralNetwork01() {
        FFNeuralNetwork xOrNn = makeXORNNIndividual();
        assertNNTrue( xOrNn.invoke(new double[]{0.,1.})[0]);
    }

    public void testXORNeuralNetwork10() {
        FFNeuralNetwork xOrNn = makeXORNN();
        assertNNTrue( xOrNn.invoke(new double[]{1.,0.})[0]);
    }

    public void testXORNeuralNetwork11() {
        FFNeuralNetwork xOrNn = makeXORNN();
        assertNNFalse( xOrNn.invoke(new double[]{1.,1.})[0]);
    }

//======================AND NN======================

    public void testAndNeuralNetwork00() {
        FFNeuralNetwork xOrNn = makeAndNN();
        assertNNFalse( xOrNn.invoke(new double[]{0.,0.})[0]);
    }

    public void testAndNeuralNetwork01() {
        FFNeuralNetwork xOrNn = makeAndNN();
        assertNNFalse( xOrNn.invoke(new double[]{0.,1.})[0]);
    }

    public void testAndNeuralNetwork10() {
        FFNeuralNetwork xOrNn = makeAndNN();
        assertNNFalse( xOrNn.invoke(new double[]{1.,0.})[0]);
    }

    public void testAndNeuralNetwork11() {
        FFNeuralNetwork xOrNn = makeAndNN();
        assertNNTrue( xOrNn.invoke(new double[]{1.,1.})[0]);
    }


//======================OR NN======================

    public void testOrNeuralNetwork00() {
        FFNeuralNetwork xOrNn = makeOrNN();
        assertNNFalse( xOrNn.invoke(new double[]{0.,0.})[0]);
    }

    public void testOrNeuralNetwork01() {
        FFNeuralNetwork xOrNn = makeOrNN();
        assertNNTrue( xOrNn.invoke(new double[]{0.,1.})[0]);
    }

    public void testOrNeuralNetwork10() {
        FFNeuralNetwork xOrNn = makeOrNN();
        assertNNTrue( xOrNn.invoke(new double[]{1.,0.})[0]);
    }

    public void testOrNeuralNetwork11() {
        FFNeuralNetwork xOrNn = makeOrNN();
        assertNNTrue( xOrNn.invoke(new double[]{1.,1.})[0]);
    }

  //======================OR and Identity layer, does not produce an OR ======================

    public void testOrMultiLayeredNeuralNetwork00() {
        FFNeuralNetwork xOrNn = makeORNNMultiLayeredFlawed();
        assertNNTrue( xOrNn.invoke(new double[]{0.,0.})[0]);
    }

    public void testOrMultiLayeredNeuralNetwork01() {
        FFNeuralNetwork xOrNn = makeORNNMultiLayeredFlawed();
        assertNNTrue( xOrNn.invoke(new double[]{0.,1.})[0]);
    }

    public void testOrMultiLayeredNeuralNetwork10() {
        FFNeuralNetwork xOrNn = makeORNNMultiLayeredFlawed();
        assertNNTrue( xOrNn.invoke(new double[]{1.,0.})[0]);
    }

    public void testOrMultiLayeredNeuralNetwork11() {
        FFNeuralNetwork xOrNn = makeORNNMultiLayeredFlawed();
        assertNNTrue( xOrNn.invoke(new double[]{1.,1.})[0]);
    }

//======================NOT NN======================

    public void testNotNeuralNetwork0() {
        FFNeuralNetwork xOrNn = makeNotNN();
        assertNNTrue( xOrNn.invoke(new double[]{0.})[0]);
    }

    public void testNotNeuralNetwork1() {
        FFNeuralNetwork xOrNn = makeNotNN();
        assertNNFalse( xOrNn.invoke(new double[]{1.})[0]);
    }

    public void testSigmoidFunction() {
        FFNeuralNetwork xOrNn = makeXORNN();
        assertEquals( 0.5, xOrNn.transform(0));
        assertEquals( 0.9999546021312976, xOrNn.transform(10));
        assertEquals( 4.5397868702434395E-5, xOrNn.transform(-10));
    }

    public static void assertNNTrue(double input) {
        boolean result = 0.5 < input;
        if (!result)
            System.out.println("input: " + input);
        assertTrue(result);
    }

    public static void assertNNFalse(double input) {
        boolean result = input <= 0.5;
        if (!result)
            System.out.println("input: " + input);
        assertTrue(result);
    }
}
