package org.shapelogic.machinelearning;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


/** FFNeuralNetworkWeightsParser parses FFNeuralNetworkWeights.<br />
 * 
 * syntax: 
 * BLOCK_START 
 * 
 * "FEATURES"
 * string +
 * BLOCK_START 
 * 
 * "RESULTS"
 * string +
 * BLOCK_START 
 * 
 * ("WEIGHTS" number + BLOCK_START ) * 
 * "WEIGHTS" number
 *  
 * @author Sami Badawi
 *
 */
public class FFNeuralNetworkWeightsParser {
    
    final static public String BLOCK_START = "==========";
    final static public String FEATURES = "FEATURES";
    final static public String RESULTS = "RESULTS";
    final static public String WEIGHTS = "WEIGHTS";
    
    protected Scanner _scanner;
    protected FFNeuralNetworkWeights nnWeights;

    public FFNeuralNetworkWeights parse(Reader input) throws ParseException {
    	_scanner = new Scanner(input);
    	nnWeights = new FFNeuralNetworkWeights();
    	blockStart();
    	features();
    	results();
    	while (weights());
    	return nnWeights;
    }
    
    protected void blockStart() {
    	_scanner.next(BLOCK_START);    	
    }
    
    protected void features() {
    	_scanner.next(FEATURES);
    	_scanner.nextLine();
    	String feature;
    	do {
    		if (!_scanner.hasNext())
    			break;
    		feature = _scanner.next();
    		if (feature == null || BLOCK_START.equalsIgnoreCase(feature))
    			break;
    		nnWeights.getFeatureList().add(feature);
    	}
    	while (true);
    }
    
    protected void results() {
    	String result;
    	_scanner.next(RESULTS);
    	do {
    		if (!_scanner.hasNext())
    			break;
    		result = _scanner.next();
    		if (result == null || BLOCK_START.equalsIgnoreCase(result))
    			break;
    		nnWeights.getOhList().add(result);
    	}
    	while (true);
    }
    
    protected boolean weights() {
    	List<Double> weight = new ArrayList<Double>();
    	_scanner.next(WEIGHTS);
    	_scanner.nextLine();
    	while (_scanner.hasNextDouble())
    		weight.add(_scanner.nextDouble());
    	double[][] currentArray = nnWeights.getWeights();
    	double[][] newArray = Arrays.copyOf(currentArray, currentArray.length+1);
    	nnWeights.setWeights(newArray);
    	double[] newLayer = new double[weight.size()];
    	for (int i = 0; i < newLayer.length; i++)
    		newLayer[i] = weight.get(i);
    	newArray[newArray.length-1] = newLayer;
    	if (_scanner.hasNext(BLOCK_START)) {
    		_scanner.next(BLOCK_START);
    		return true;
    	}
    	else {
        	return false;
    	}
    }
    
}
