package org.shapelogic.machinelearning;

import org.shapelogic.streams.*;
import org.shapelogic.calculation.RecursiveContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.shapelogic.util.Constants;

/** FFNeuralNetworkStream a feed forward neural network wrapped in a stream.<br />
 *
 * Streams involved: <br />
 * <lu>
 * <li>ArrayOutputListStream: A list of names of input features that need to be streams</li>
 * <li>FFNeuralNetwork: Creates stream of double[] from the neural network</li>
 * <li>ConfidenceArrayListStream: Creat result stream of String</li>
 * </lu>
 *
 * Which of these needs names? None of them.<br />
 * <br />
 * It seems like the last stream will have the same output as the overall stream
 * is the overall then really needed? No.<br />
 * <br />
 * There should probably be a stack of Streams.<br />
 * This could possibly be a LISP list, no there is no advantage to this.<br />
 * The whole think should be created lazily, but when it gets created, you can
 * directly pipe one into the next.<br />
 * What should trigger the setup?<br />
 * As long as the first have a lazy setup we should be fine and it does.<br />
 * So the first should be any type of ListStream, while the next have to be
 * ListCalcStream1 or any ListStream with one input.<br />
 * <br />
 *
 * @author Sami Badawi
 */
public class FFNeuralNetworkStream extends NamedListCalcStream1 {
    public final static String DEFAULT_RESULT_NAME = "result";
    public final static String DEFAULT_INPUT_NAME = "FFNeuralNetworkStreamInput";

    public FFNeuralNetworkStream(List<String> featureList, List<String> ohList,
            double[][] weights, String inputName,
            RecursiveContext recursiveContext, String outputName, int maxLast
            )
    {
        super(null, inputName != null ? inputName : DEFAULT_INPUT_NAME,
                recursiveContext, outputName, maxLast);
        if (ohList == null || ohList.size() == 0) {
            ohList = new ArrayList<String>();
            ohList.add(DEFAULT_RESULT_NAME);
        }
        FFNeuralNetwork fFNeuralNetwork = new FFNeuralNetwork(featureList.size(), ohList.size());
        for (double[] weight: weights)
            fFNeuralNetwork.addLayer(weight);
        setCalc(fFNeuralNetwork);
        if (inputName == null)
            inputName = DEFAULT_INPUT_NAME;
        ArrayOutputListStream arrayOutputListStream =
                new ArrayOutputListStream(featureList, recursiveContext, inputName, Constants.LAST_UNKNOWN);

//        ConfidenceArrayListStream confidenceArrayListStream =
//                new ConfidenceArrayListStream( );
    }
    
    public FFNeuralNetworkStream(String[] featureList, String[] ohList,
            double[][] weights, String inputName,
            RecursiveContext recursiveContext, String outputName, int maxLast
            )
    {
        this(Arrays.asList(featureList), Arrays.asList(ohList), weights,
                inputName, recursiveContext, outputName, maxLast );
    }
}
