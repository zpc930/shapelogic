package org.shapelogic.imageprocessing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.logic.CommonLogicExpressions;
import org.shapelogic.machinelearning.ExampleNeuralNetwork;
import org.shapelogic.machinelearning.FFNeuralNetworkStream;
import org.shapelogic.machinelearning.FFNeuralNetworkWeights;
import org.shapelogic.machinelearning.FFNeuralNetworkWeightsParser;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.reporting.BaseTableBuilder;
import org.shapelogic.reporting.TableDefinition;
import org.shapelogic.streamlogic.LoadLetterStreams;
import org.shapelogic.streamlogic.StreamNames;
import org.shapelogic.streams.NumberedStream;
import org.shapelogic.streams.StreamFactory;

/** Same vectorizer as MaxDistanceVectorizer, but logic implemented with streams.
 * <br />
 * 
 * @author Sami Badawi
 *
 */
public class StreamVectorizer extends BaseMaxDistanceVectorizer implements RecursiveContext {
	protected Map _context = new HashMap();
	protected LoadLetterStreams loadLetterStreams;
	protected NumberedStream<String> _categorizer;
	
    protected boolean _useInputDilog;
    protected boolean _useNeuralNetwork;
    protected String _neuralNetworkFile;
    
    protected BaseTableBuilder _tableBuilder;
    protected TableDefinition _tableDefinition;
    protected List<String> _printListOverwrite;
    
	/** This does really not belong in a vectorizer. */
	@Override
	protected void matchLines() {
		if (_categorizer == null) //To be backwards compatible
			_categorizer = StreamFactory.findNumberedStream(StreamNames.LETTERS, this);
		String message = "";
        StringBuffer internalInfo = new StringBuffer();
        if (_displayInternalInfo) {
            internalInfo.append("\n===================Internal info for skeletonized lines===================\n");
        }
		for (int i = 0; hasNext(); i++)
		{
			String currentMatch = _categorizer.next();
			if (i != 0)
				message += "; ";
			message += currentMatch;
			if (currentMatch == null || "".equals(currentMatch))
				System.out.println("\n\nMatch failed for this:\n" + _cleanedupPolygon);
            if (_displayInternalInfo) {
                Polygon currentPolygon = _stream.get(i);
                internalInfo.append(currentPolygon.toString());
            }
		}
		_matchingOH = message;
		if (_matchingOH == null) {
			System.out.println("\n\nLetter matched failed for this:\n" + _cleanedupPolygon);
		}
		showMessage("","Letter match result: " + _matchingOH);
        if (_displayInternalInfo) {
            showMessage("InternalInfo for skeletonized lines",internalInfo.toString());
        }
	}
	
	/** Method to override if you want to define your own rule set.<br />  
	 * 
	 * The default network is very simple it is marking particles Tall, Flat
	 * based on their aspect ratio.
	 */
	protected void defineRules() {		
		loadLetterStreams.loadLetterStream(null);
		_categorizer = StreamFactory.findNumberedStream(StreamNames.LETTERS, this);
	}

	/** Method to override if you want to define your own neural network.<br />  
	 * 
	 * The default network is very simple it is marking particles Dark or Light.
	 */
	protected void defineNeuralNetwork() {
		loadLetterStreams.loadLetterStream(null);
 		FFNeuralNetworkWeights fFNeuralNetworkWeights = null;
		if (_neuralNetworkFile != null && 0 < _neuralNetworkFile.trim().length() ) {
			FFNeuralNetworkWeightsParser parser = new FFNeuralNetworkWeightsParser();
			try {
				fFNeuralNetworkWeights = parser.parse(_neuralNetworkFile);
			} catch (Exception e) {
				//Ignore it for now and use default instead.
				fFNeuralNetworkWeights = null;
			}
		}
		if (fFNeuralNetworkWeights == null) {
			String[] objectHypotheses = new String[] {"No holes", "Holes"};
			String[] inputStreamName = {CommonLogicExpressions.HOLE_COUNT};
			double[][] weights = ExampleNeuralNetwork.makeSmallerThanGreaterThanNeuralNetwork(1.);
			fFNeuralNetworkWeights = new FFNeuralNetworkWeights(
					Arrays.asList(inputStreamName),
					Arrays.asList(objectHypotheses), 
					weights);
		}
		FFNeuralNetworkStream neuralNetworkStream = new FFNeuralNetworkStream(
				fFNeuralNetworkWeights,this);
         _categorizer = neuralNetworkStream.getOutputStream();
 		if (0 < fFNeuralNetworkWeights.getPrintList().size())
 			_printListOverwrite = fFNeuralNetworkWeights.getPrintList();
	}
	
	/** Use this to setup all the needed streams.
	 */
	@Override
	public void init() {
		_context.clear();
		super.init();
		_context.put(StreamNames.POLYGONS, getStream());
		loadLetterStreams = new LoadLetterStreams(this);
		matchSetup();
        if (_arg != null && _arg.indexOf("InternalInfo") != -1) {
        	_displayInternalInfo = true; 
        }
	}
	
	/** In order to match a different alphabet override this. 
	 */
	public void matchSetup() {
        if (_useNeuralNetwork) {
        	defineNeuralNetwork();
        }
        else {
        	defineRules();
        }
	}
	
	@Override
	public void run() {
		init();
		matchLines();
	}

	@Override
	public Map getContext() {
		return _context;
	}

	@Override
	public RecursiveContext getParentContext() {
		return null;
	}
}
