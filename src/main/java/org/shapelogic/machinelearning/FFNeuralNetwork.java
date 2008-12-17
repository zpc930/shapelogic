package org.shapelogic.machinelearning;

import java.util.ArrayList;

/** Feed Forward Neural Network with external training. <br />
 *
 * The bias is considered the zeroth element of the synaptic weight.
 *
 * @author Sami Badawi
 */
public class FFNeuralNetwork {

    final public int nInputNodes;
    final public int nOutputNodes;
    protected ArrayList<Integer> _layerNodes = new ArrayList();
    protected ArrayList<double[]> _layerWeights = new ArrayList();
    protected String _message;
    protected int _biasWeight = -1;

    public FFNeuralNetwork(int nInput, int nOutput) {
        nInputNodes = nInput;
        nOutputNodes = nOutput;
        _layerNodes.add(nInput);
        _layerWeights.add(new double[0]);
    }

    public double[] calc(double[] input) {
        if (getLayerNodesInTopLayer() != nOutputNodes)
            return null;
        if (input.length != nInputNodes)
            return null;
        double[] lastResult = input;
        double[] result = null;
        for (int i = 1; i < _layerNodes.size(); i++) {
            result = calcLayer(lastResult, i);
            lastResult = result;
        }
        return result;
    }

    public double[] calcLayer(double[] lastResult,
            int layerNumber)
    {
        int currentNumberOfNodes = _layerNodes.get(layerNumber);
        double[] result = new double[currentNumberOfNodes];
        double[] currentWeights = _layerWeights.get(layerNumber);
        for (int i = 0; i < result.length; i++) {
            result[i] += currentWeights[i] * _biasWeight;
            for (int j = 0; j < lastResult.length; j++) {
                int index = i + (j+1)*currentNumberOfNodes;
                result[i] += currentWeights[index] * lastResult[j];
            }
            result[i] = transform(result[i]);
        }
        return result;
    }

    public boolean addLayer(double[] layer) {
//        if (_layerWeights.size() == 0) {}
        int lastNumberOfNodes = _layerNodes.get(_layerNodes.size()-1);
        int currentNumberOfNodes = layer.length / (lastNumberOfNodes+1);
        _layerWeights.add(layer);
        _layerNodes.add(currentNumberOfNodes);
        if (layer.length % lastNumberOfNodes != 0)
            _message = "Error number of weights for a layer = " +
                    "(1 + nodes in last) * (nodes in current)\n" +
                    "Values found \n" +
                    "number of weight in layer = " + layer.length +
                    "\nnumber of nodes in last layer = " + lastNumberOfNodes;
        return true;
    }

    public double transform(double input) {
        return 1 / (1 + Math.exp(-input));
    }

//=============== Getters and setters ===============
    public ArrayList<Integer> getLayerNodes() {
        return _layerNodes;
    }

    public int getLayerNodesInTopLayer() {
        return _layerNodes.get(_layerNodes.size()-1);
    }

    public ArrayList<double[]> getLayerWeights() {
        return _layerWeights;
    }

    public String getMessage() {
        return _message;
    }
}
