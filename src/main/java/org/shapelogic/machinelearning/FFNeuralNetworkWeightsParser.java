package org.shapelogic.machinelearning;

import java.io.Reader;
import java.util.Scanner;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


/** FFNeuralNetworkWeightsParser parses FFNeuralNetworkWeights.<br />
 * 
 * syntax: 
 * BLOCK_START FEATURES
 * string +
 * BLOCK_START RESULTS
 * string +
 * (BLOCK_START WEIGHTS number + ) + 
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

    public FFNeuralNetworkWeights parse(Reader input) throws ParseException {
    	_scanner = new Scanner(input);
    	FFNeuralNetworkWeights result = new FFNeuralNetworkWeights();
    	_scanner.next(BLOCK_START);
    	_scanner.next(FEATURES);
    	_scanner.nextLine();
    	String feature;
    	do {
    		if (!_scanner.hasNext())
    			break;
    		feature = _scanner.next();
    		if (feature == null || BLOCK_START.equalsIgnoreCase(feature))
    			break;
    		result.getFeatureList().add(feature);
    	}
    	while (true);
    	
    	return result;
    }
    
}
