package org.shapelogic.machinelearning;

import java.util.ArrayList;
import java.util.List;

/** FFNeuralNetworkWeights represent a trained feed forward neural network.<br />
 * 
 * @author Sami Badawi
 *
 */
public class FFNeuralNetworkWeights {
	protected List<String> _featureList = new ArrayList<String>(); 
	protected List<String> _ohList = new ArrayList<String>();
	protected double[][] _weights = new double[0][0];
	protected List<String> _printList = new ArrayList<String>();
	
	public FFNeuralNetworkWeights() {}
	
	public FFNeuralNetworkWeights(List<String> featureList, 
			List<String> ohList, double[][] weights) {
		_featureList = featureList;
		_ohList = ohList;
		_weights = weights;
	}
	
	public List<String> getFeatureList() {
		return _featureList;
	}
	public void setFeatureList(List<String> list) {
		_featureList = list;
	}
	public List<String> getOhList() {
		return _ohList;
	}
	public void setOhList(List<String> list) {
		_ohList = list;
	}
	public double[][] getWeights() {
		return _weights;
	}
	public void setWeights(double[][] _weights) {
		this._weights = _weights;
	}
	public List<String> getPrintList() {
		return _printList;
	}
	public void setPrintList(List<String> list) {
		_printList = list;
	}
}
