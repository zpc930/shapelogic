package org.shapelogic.machinelearning;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.shapelogic.streamlogic.RulePredicate;

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
 * [ "RULES_PREDICATE" BLOCK_START
 * ( def string
 * (string relation number) * BLOCK_START )*
 * ]
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
    final static public String RULES_PREDICATE = "RULES_PREDICATE";
    final static public String DEFINITION = "def";
    
    protected Scanner _scanner;
    protected FFNeuralNetworkWeights _nnWeights;
    protected String _blockLookahead;

    public FFNeuralNetworkWeights parse(String path) throws Exception {
        if ((path == null) || (path.trim().length() == 0))
            return null;
    	return parse(open(path.trim()));
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
    	_nnWeights = new FFNeuralNetworkWeights();
    	blockStart();
    	while (true) {
    		_blockLookahead = null;
			if (_scanner.hasNext()) {
				_blockLookahead = _scanner.next();
//				_scanner.nextLine(); //This causes a problem if there are more than one word
			}
			else
				break;
	    	if (FEATURES.equalsIgnoreCase(_blockLookahead)) parseStringList(_nnWeights.getFeatureList());
	    	else if (RESULTS.equalsIgnoreCase(_blockLookahead)) parseStringList(_nnWeights.getOhList());
	    	else if (PRINTS.equalsIgnoreCase(_blockLookahead)) parseStringList(_nnWeights.getPrintList());
	    	else if (WEIGHTS.equalsIgnoreCase(_blockLookahead)) weights();
	    	else if (RULES_PREDICATE.equalsIgnoreCase(_blockLookahead)) parsePredicateRules();
	    	else if (DEFINITION.equalsIgnoreCase(_blockLookahead)) parseDefinition(_nnWeights.getRulePredicates());
	    	else {
	    		throw new ParseException("Unexpected symbol after " + 
	    				BLOCK_START + ": " + _blockLookahead, 0); 
	    	}
    	}
    	return _nnWeights;
    }
    
    protected void blockStart() {
    	_scanner.next(BLOCK_START);    	
    }
    
    protected void parseStringList(List<String> outputList) {
    	while (true) {
    		if (!_scanner.hasNext())
    			break;
    		String word = _scanner.next();
    		if (endOfBlock(word))
    			break;
    		outputList.add(word);
    	}
    }
    
    /** If there are more type of rules this define what the current type of rules are.<br />
     * 
     * There is not so currently this is empty. 
     * */
    protected void parsePredicateRules() throws ParseException {
    }
    
    protected void parseDefinition(List<RulePredicate> rulePredicates) throws ParseException {
		String definitionName = _scanner.next();
		_scanner.nextLine();
    	while (true) {
    		if (!_scanner.hasNext())
    			break;
    		String streamName = _scanner.next();
    		if (endOfBlock(streamName))
    			break;
    		String relation = _scanner.next();
    		if (!("==".equals(relation) || "<".equals(relation) || ">".equals(relation)))
    			new ParseException(
    					"Relation has to have the form ==, < or >, but found: "
    					+ relation, 0);
    		double number = _scanner.nextDouble();
    		RulePredicate rule = new RulePredicate(definitionName, streamName, relation, number);
    		rulePredicates.add(rule);
    		System.out.println("rulePredicates: " + rulePredicates.size());
    	}
    }

    protected boolean endOfBlock(String lookahead) throws ParseException {
        if (lookahead == null || BLOCK_START.equalsIgnoreCase(lookahead))
            return true;
        if (lookahead.indexOf("===") != -1)
            throw new ParseException("Illegal word: " + lookahead + " Block start is ========== ", 0);
        return false;
    }
    
    protected void weights() {
    	List<Double> weight = new ArrayList<Double>();
    	while (_scanner.hasNextDouble())
    		weight.add(_scanner.nextDouble());
    	double[][] currentArray = _nnWeights.getWeights();
    	double[][] newArray = Arrays.copyOf(currentArray, currentArray.length+1);
    	_nnWeights.setWeights(newArray);
    	double[] newLayer = new double[weight.size()];
    	for (int i = 0; i < newLayer.length; i++)
    		newLayer[i] = weight.get(i);
    	newArray[newArray.length-1] = newLayer;
    	if (_scanner.hasNext(BLOCK_START)) {
    		_scanner.next(BLOCK_START);
    	}
    }
    
}
