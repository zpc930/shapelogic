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

    public FFNeuralNetwork(int nInput, int nOutput) {
        nInputNodes = nInput;
        nOutputNodes = nOutput;
        _layerNodes.add(nInput);
        _layerWeights.add(new double[0]);
    }

    public double[] calc(double[] input) {
        return input;
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
