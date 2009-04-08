package org.shapelogic.machinelearning;

import junit.framework.TestCase;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.calculation.SimpleRecursiveContext;
import org.shapelogic.mathematics.NumericTruthTableStream;
import org.shapelogic.streams.ListStream;

/** Test of ExampleNeuralNetwork Stream with external training. <br />
 *
 * The bias is considered the zeroth element of the synaptic weight.
 *
 * @author Sami Badawi
 */
public class ExampleNeuralNetworkTest extends TestCase {
    final static String ONE = "1";
    final static String SMALLER = "SMALLER";
    final static String GREATER = "GREATER";
    final static String NOTHING = "";
    final static String[] INPUT_ARRAY = { ONE };
    final static String[] GREATER_RESULT_ARRAY = {GREATER};
    final static String[] SMALLER_RESULT_ARRAY = {SMALLER};
    final static String[] SMALLER_GREATER_RESULT_ARRAY = {SMALLER, GREATER};

    RecursiveContext _recursiveContext;
    NumericTruthTableStream numericTruthTableStream1;
    NumericTruthTableStream numericTruthTableStream2;

    @Override
    public void setUp() {
        _recursiveContext = new SimpleRecursiveContext(null);
        numericTruthTableStream1 = new NumericTruthTableStream(1,true);
        _recursiveContext.getContext().put(ONE, numericTruthTableStream1);
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

    public FFNeuralNetwork makeSmallerThanGreaterThanNeuralNetwork(double limit) {
        FFNeuralNetwork nn = new FFNeuralNetwork(1,1);
        nn.addLayers(ExampleNeuralNetwork.makeSamllerThanGreaterThanNeuralNetwork(limit));
        return nn;
    }

//======================GreaterThanNeuralNetwork NN======================

    public void testGreaterThanNeuralNetworkGreater() {
        double limit = 10;
        FFNeuralNetwork xOrNn = makeGreaterThanNeuralNetwork(limit);
        FFNeuralNetworkTest.assertNNTrue( xOrNn.invoke(new double[]{limit + 0.1})[0]);
    }

    public void testGreaterThanNeuralNetworkSmaller() {
        double limit = 10;
        FFNeuralNetwork xOrNn = makeGreaterThanNeuralNetwork(limit);
        FFNeuralNetworkTest.assertNNFalse( xOrNn.invoke(new double[]{limit - 0.1})[0]);
    }

//======================SmallerThanNeuralNetwork NN======================

    public void testSmallerThanNeuralNetworkGreater() {
        double limit = 10;
        FFNeuralNetwork xOrNn = makeSmallerThanNeuralNetwork(limit);
        FFNeuralNetworkTest.assertNNFalse( xOrNn.invoke(new double[]{limit + 0.1})[0]);
    }

    public void testSmallerThanNeuralNetworkSmaller() {
        double limit = 10;
        FFNeuralNetwork xOrNn = makeSmallerThanNeuralNetwork(limit);
        FFNeuralNetworkTest.assertNNTrue( xOrNn.invoke(new double[]{limit - 0.1})[0]);
    }

//======================GREATER NN======================

    public void testGreterThanNeuralNetworkStream() {
        FFNeuralNetworkStream nn = new FFNeuralNetworkStream(INPUT_ARRAY,
                GREATER_RESULT_ARRAY , ExampleNeuralNetwork.makeGreaterThanNeuralNetwork(0.8),
                _recursiveContext);
        ListStream<String> output = nn.getOutputStream();
        assertEquals( NOTHING, output.next());
        assertEquals( GREATER, output.next());
    }

//======================SMALLER NN======================

    public void testSmallerThanNeuralNetworkStream() {
        FFNeuralNetworkStream nn = new FFNeuralNetworkStream(INPUT_ARRAY,
                SMALLER_RESULT_ARRAY , ExampleNeuralNetwork.makeSmallerThanNeuralNetwork(0.8),
                _recursiveContext);
        ListStream<String> output = nn.getOutputStream();
        assertEquals( SMALLER, output.next());
        assertEquals( NOTHING, output.next());
    }

//======================SMALLER GREATER NN======================

    public void testSmallerThanGreaterThanNeuralNetworkStream() {
        FFNeuralNetworkStream nn = new FFNeuralNetworkStream(INPUT_ARRAY,
                SMALLER_GREATER_RESULT_ARRAY, ExampleNeuralNetwork.makeSamllerThanGreaterThanNeuralNetwork(0.8),
                _recursiveContext);
        ListStream<String> output = nn.getOutputStream();
        assertEquals( SMALLER, output.next());
        assertEquals( GREATER, output.next());
    }

}
