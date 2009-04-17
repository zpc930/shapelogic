package org.shapelogic.machinelearning;

import junit.framework.TestCase;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.calculation.SimpleRecursiveContext;
import org.shapelogic.mathematics.NumericTruthTableStream;
import org.shapelogic.streams.ListStream;
import org.shapelogic.util.Constants;
import static org.shapelogic.machinelearning.FFNeuralNetworkTest.WEIGHTS_FOR_AND;
import static org.shapelogic.machinelearning.FFNeuralNetworkTest.WEIGHTS_FOR_OR;
import static org.shapelogic.machinelearning.FFNeuralNetworkTest.WEIGHTS_FOR_NOT;
import static org.shapelogic.machinelearning.FFNeuralNetworkTest.WEIGHTS_FOR_XOR;

/** Test of Feed Forward Neural Network Stream with external training. <br />
 *
 * The bias is considered the zeroth element of the synaptic weight.
 *
 * @author Sami Badawi
 */
public class FFNeuralNetworkStreamTest extends TestCase {
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

//======================XOR NN======================

    public void testXOrNeuralNetworkStream() {
        FFNeuralNetworkStream nn = new FFNeuralNetworkStream(INPUT_ARRAY,
                RESULT_ARRAY , WEIGHTS_FOR_XOR, _recursiveContext);
        ListStream<String> output = nn.getOutputStream();
        assertEquals( NOTHING, output.next());
        assertEquals( RESULT, output.next());
        assertEquals( RESULT, output.next());
        assertEquals( NOTHING, output.next());
    }

//======================AND NN======================

    public void testAndNeuralNetworkStream() {
        FFNeuralNetworkStream nn = new FFNeuralNetworkStream(INPUT_ARRAY,
                RESULT_ARRAY , WEIGHTS_FOR_AND, null, _recursiveContext);
        ListStream<String> output = nn.getOutputStream();
        assertEquals( NOTHING, output.next());
        assertEquals( NOTHING, output.next());
        assertEquals( NOTHING, output.next());
        assertEquals( RESULT, output.next());
    }

//======================OR NN======================

    public void testOrNeuralNetworkStream() {
        FFNeuralNetworkStream nn = new FFNeuralNetworkStream(INPUT_ARRAY,
                RESULT_ARRAY , WEIGHTS_FOR_OR, null, _recursiveContext,
                Constants.LAST_UNKNOWN);
        ListStream<String> output = nn.getOutputStream();
        assertEquals( NOTHING, output.next());
        assertEquals( RESULT, output.next());
        assertEquals( RESULT, output.next());
        assertEquals( RESULT, output.next());
    }

//======================NOT NN======================

    public void testNotNeuralNetworkStream() {
        FFNeuralNetworkStream nn = new FFNeuralNetworkStream(new String[] {ONE},
                RESULT_ARRAY , WEIGHTS_FOR_NOT, null, _recursiveContext,
                Constants.LAST_UNKNOWN);
        ListStream<String> output = nn.getOutputStream();
        assertEquals( RESULT, output.next());
        assertEquals( NOTHING, output.next());
    }

}
