package org.shapelogic.machinelearning;

import java.util.ArrayList;

/** Feed Forward Neural Network with external training. <br />
 *
 * The bias is considered the zeroth element of the synaptic weight.
 *
 * @author Sami Badawi
 */
public class FFNeuralNetwork {

    protected int _nInput;
    protected int _nOutput;
    protected ArrayList<Integer> _layerNodes = new ArrayList();
    protected ArrayList<double[]> _layerWeights = new ArrayList();

    public FFNeuralNetwork(int nInput, int nOutput) {
        _nInput = nInput;
        _nOutput = nOutput;
        _layerNodes.add(nInput);
        _layerWeights.add(new double[0]);
    }

    public double[] calc(double[] input) {
        return input;
    }

    public boolean loadLayer(double[] layer) {
        return true;
    }
}
