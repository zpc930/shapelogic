package org.shapelogic.machinelearning;

import org.shapelogic.streams.*;
import org.shapelogic.calculation.RecursiveContext;

import java.util.ArrayList;
import java.util.List;
import org.shapelogic.util.Constants;

/** FFNeuralNetworkStream a feed forward neural network wrapped in a stream.<br />
 *
 * It takes a list of input features that need to be streams.<br />
 *
 * @author Sami Badawi
 */
public class FFNeuralNetworkStream extends NamedListCalcStream1 {
    public final static String DEFAULT_RESULT_NAME = "result";
    public FFNeuralNetworkStream(List<String> featureList, List<String> ohList,
            double[][] weights, String inputName,
            RecursiveContext recursiveContext, String outputName, int maxLast
            )
    {
        super(null, inputName, recursiveContext, outputName, maxLast);
        if (ohList == null || ohList.size() == 0) {
            ohList = new ArrayList<String>();
            ohList.add(DEFAULT_RESULT_NAME);
        }
        FFNeuralNetwork fFNeuralNetwork = new FFNeuralNetwork(featureList.size(), ohList.size());
        for (double[] weight: weights)
            fFNeuralNetwork.addLayer(weight);
        setCalc(fFNeuralNetwork);
        if (inputName == null)
            inputName = DEFAULT_RESULT_NAME;
        ArrayOutputListStream arrayOutputListStream =
                new ArrayOutputListStream(featureList, recursiveContext, inputName, Constants.LAST_UNKNOWN);
    }
    
}
