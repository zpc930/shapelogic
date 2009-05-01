package org.shapelogic.machinelearning;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


/** FFNeuralNetworkWeightsParser parses FFNeuralNetworkWeights.<br />
 * 
 * I need a lookahead for the block line. <br />
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
 * [ "PRINTS"
 * string +
 * BLOCK_START ] 
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
    final static public String PRINTS = "PRINTS";
    
    protected Scanner _scanner;
    protected FFNeuralNetworkWeights nnWeights;
    protected String blockLookahead;

    public FFNeuralNetworkWeights parse(String path) throws Exception {
    	return parse(open(path));
    }
    
	/** Opens URLs as well. */  
	protected Reader open(String path) throws Exception {
		InputStream inputStream = null;
		if (0 == path.indexOf("http://")) {  
			inputStream = new java.net.URL(path).openStream();
			return new InputStreamReader(inputStream);
		}
		return new FileReader(path);
	}

    public FFNeuralNetworkWeights parse(Reader input) throws ParseException {
    	if (input == null)
    		throw new ParseException("Input to FFNeuralNetworkWeightsParser is null.",0);
    	_scanner = new Scanner(input);
    	nnWeights = new FFNeuralNetworkWeights();
    	blockStart();
    	while (true) {
    		blockLookahead = null;
			if (_scanner.hasNext()) {
				blockLookahead = _scanner.next();
				_scanner.nextLine();
			}
			else
				break;
	    	if (FEATURES.equalsIgnoreCase(blockLookahead)) parseStringList(nnWeights.getFeatureList());
	    	else if (RESULTS.equalsIgnoreCase(blockLookahead)) parseStringList(nnWeights.getOhList());
	    	else if (PRINTS.equalsIgnoreCase(blockLookahead)) parseStringList(nnWeights.getPrintList());
	    	else if (WEIGHTS.equalsIgnoreCase(blockLookahead)) weights();
    	}
    	return nnWeights;
    }
    
    protected void blockStart() {
    	_scanner.next(BLOCK_START);    	
    }
    
    protected void parseStringList(List<String> outputList) {
    	while (true) {
    		if (!_scanner.hasNext())
    			break;
    		String word = _scanner.next();
    		if (word == null || BLOCK_START.equalsIgnoreCase(word))
    			break;
    		outputList.add(word);
    	}
    }
    
    protected void weights() {
    	List<Double> weight = new ArrayList<Double>();
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
    	}
    }
    
}
