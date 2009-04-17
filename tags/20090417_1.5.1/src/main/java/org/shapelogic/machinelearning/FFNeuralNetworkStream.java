package org.shapelogic.machinelearning;

import org.shapelogic.streams.*;
import org.shapelogic.calculation.RecursiveContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
public class FFNeuralNetworkStream {
    public final static String DEFAULT_RESULT_NAME = "Result";
    public final static String DEFAULT_OUTPUT_NAME = "FFNeuralNetworkStreamOutput";

    ListStream<double[]> _featureStream;
    ListCalcStream1<double[], double[]> _neuralNetworkStream;
    ListCalcStream1<double[], String> _outputStream;
    RecursiveContext _recursiveContext;

    public FFNeuralNetworkStream(List<String> featureList, List<String> ohList,
            double[][] weights, String[] streamNames,
            RecursiveContext recursiveContext, int maxLast
            )
    {
        _recursiveContext = recursiveContext;
        Map context = _recursiveContext.getContext();
        if (ohList == null || ohList.size() == 0) {
            ohList = new ArrayList<String>();
            ohList.add(DEFAULT_RESULT_NAME);
        }
        if (streamNames == null || 0 == streamNames.length ) {
            streamNames = new String[3];
            streamNames[2] = DEFAULT_OUTPUT_NAME;
        }
        if (streamNames.length < 3)
            throw new RuntimeException("Too few arguments for streamNames: " + streamNames.length);

// Setup stream 1
        _featureStream =  new ArrayOutputListStream(featureList,
                recursiveContext, streamNames[0], Constants.LAST_UNKNOWN);

// Setup stream 2
        FFNeuralNetwork fFNeuralNetwork =
                new FFNeuralNetwork(featureList.size(), ohList.size());
        for (double[] weight: weights)
            fFNeuralNetwork.addLayer(weight);
        _neuralNetworkStream = new ListCalcStream1<double[], double[]>(
                fFNeuralNetwork, _featureStream);
        if (streamNames[1] != null)
            context.put(streamNames[1], _neuralNetworkStream);
        
// Setup stream 3
        ConfidenceArraySelector confidenceArraySelector =
                new ConfidenceArraySelector(ohList);
        _outputStream = new ListCalcStream1<double[], String>(
                confidenceArraySelector, _neuralNetworkStream);
        if (streamNames[2] != null)
            context.put(streamNames[2], _outputStream);
    }
    
    public FFNeuralNetworkStream(List<String> featureList, List<String> ohList,
            double[][] weights, String[] streamNames,
            RecursiveContext recursiveContext)
    {
        this(featureList, ohList, weights, streamNames, recursiveContext,
                Constants.LAST_UNKNOWN);
    }

    public FFNeuralNetworkStream(List<String> featureList, List<String> ohList,
            double[][] weights, RecursiveContext recursiveContext)
    {
        this(featureList, ohList, weights, null, recursiveContext,
                Constants.LAST_UNKNOWN);
    }


//Constructors using String[] instead of List<String>
    
    public FFNeuralNetworkStream(String[] featureList, String[] ohList,
            double[][] weights, String[] streamNames,
            RecursiveContext recursiveContext, int maxLast
            )
    {
        this(Arrays.asList(featureList), Arrays.asList(ohList), weights,
                streamNames, recursiveContext, maxLast );
    }

    public FFNeuralNetworkStream(String[] featureList, String[] ohList,
            double[][] weights, String[] streamNames,
            RecursiveContext recursiveContext
            )
    {
        this(Arrays.asList(featureList), Arrays.asList(ohList), weights,
                streamNames, recursiveContext, Constants.LAST_UNKNOWN );
    }

    public FFNeuralNetworkStream(String[] featureList, String[] ohList,
            double[][] weights, RecursiveContext recursiveContext)
    {
        this(Arrays.asList(featureList), Arrays.asList(ohList), weights,
                null, recursiveContext, Constants.LAST_UNKNOWN );
    }

    public ListStream<double[]> getFeatureStream() {
        return _featureStream;
    }

    public ListCalcStream1<double[], double[]> getNeuralNetworkStream() {
        return _neuralNetworkStream;
    }

    public ListCalcStream1<double[], String> getOutputStream() {
        return _outputStream;
    }
}
