package org.shapelogic.machinelearning;

import junit.framework.TestCase;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.calculation.SimpleRecursiveContext;
import org.shapelogic.mathematics.NumericTruthTableStream;

/** Test of ExampleNeuralNetwork Stream with external training. <br />
 *
 * The bias is considered the zeroth element of the synaptic weight.
 *
 * @author Sami Badawi
 */
public class ExampleNeuralNetworkTest extends TestCase {
    final static String ONE = "1";
    final static String TWO = "2";
    final static String RESULT = "RESULT";
    final static String NOTHING = "";
    final static String[] INPUT_ARRAY = { ONE, TWO };
    final static String[] RESULT_ARRAY = {RESULT};

    RecursiveContext _recursiveContext;
    NumericTruthTableStream numericTruthTableStream1;
    NumericTruthTableStream numericTruthTableStream2;

    @Override
    public void setUp() {
        _recursiveContext = new SimpleRecursiveContext(null);
        numericTruthTableStream1 = new NumericTruthTableStream(1);
        numericTruthTableStream2 = new NumericTruthTableStream(2,true);
        _recursiveContext.getContext().put(ONE, numericTruthTableStream1);
        _recursiveContext.getContext().put(TWO, numericTruthTableStream2);
    }


    public void testConstructor() {
        FFNeuralNetwork nn = new FFNeuralNetwork(2,1);
        assertEquals(2,nn.nInputNodes);
        assertEquals(1,nn.nOutputNodes);
    }

    public FFNeuralNetwork makeGreaterThanNeuralNetwork(double limit) {
        FFNeuralNetwork nn = new FFNeuralNetwork(1,1);
        nn.addLayers(ExampleNeuralNetwork.makeGreaterThanNeuralNetwork(limit));
        return nn;
    }

    public FFNeuralNetwork makeSmallerThanNeuralNetwork(double limit) {
        FFNeuralNetwork nn = new FFNeuralNetwork(1,1);
        nn.addLayers(ExampleNeuralNetwork.makeSmallerThanNeuralNetwork(limit));
        return nn;
    }

//======================GreaterThanNeuralNetwork NN======================

    public void testGreaterThanNeuralNetworkGreater() {
        double limit = 10;
        FFNeuralNetwork xOrNn = makeGreaterThanNeuralNetwork(limit);
        FFNeuralNetworkTest.assertNNTrue( xOrNn.invoke(new double[]{limit + 1})[0]);
    }

    public void testGreaterThanNeuralNetworkSmaller() {
        double limit = 10;
        FFNeuralNetwork xOrNn = makeGreaterThanNeuralNetwork(limit);
        FFNeuralNetworkTest.assertNNFalse( xOrNn.invoke(new double[]{limit - 1})[0]);
    }

//======================SmallerThanNeuralNetwork NN======================

    public void testSmallerThanNeuralNetworkGreater() {
        double limit = 10;
        FFNeuralNetwork xOrNn = makeSmallerThanNeuralNetwork(limit);
        FFNeuralNetworkTest.assertNNFalse( xOrNn.invoke(new double[]{limit + 1})[0]);
    }

    public void testSmallerThanNeuralNetworkSmaller() {
        double limit = 10;
        FFNeuralNetwork xOrNn = makeSmallerThanNeuralNetwork(limit);
        FFNeuralNetworkTest.assertNNTrue( xOrNn.invoke(new double[]{limit - 1})[0]);
    }

}
