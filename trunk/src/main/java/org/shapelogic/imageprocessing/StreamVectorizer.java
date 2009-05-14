package org.shapelogic.imageprocessing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
import org.shapelogic.util.Headings;

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
	
    protected boolean _useNeuralNetwork;
    protected String _neuralNetworkFile;
    
    protected BaseTableBuilder _tableBuilder;
    protected TableDefinition _tableDefinition;
    protected List<String> _printListOverwrite;
    
    protected boolean _displayAll = false;
    protected boolean _displayResultTable = false;
    
	/** This does really not belong in a vectorizer. */
	@Override
	protected void matchLines() {
		if (_categorizer == null) //To be backwards compatible
			_categorizer = StreamFactory.findNumberedStream(StreamNames.LETTERS, this);
		String message = "";
        StringBuffer internalInfo = new StringBuffer();
        if (_displayInternalInfo || _displayAll) {
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
            if (_displayInternalInfo || _displayAll) {
                Polygon currentPolygon = _stream.get(i);
                internalInfo.append(currentPolygon.toString());
            }
		}
		_matchingOH = message;
		if (_matchingOH == null) {
			System.out.println("\n\nLetter matched failed for this:\n" + _cleanedupPolygon);
		}
		showMessage("","Letter match result: " + _matchingOH);
        if (_displayInternalInfo || _displayAll) {
            showMessage("InternalInfo for skeletonized lines",internalInfo.toString());
        }
	}
	
    protected FFNeuralNetworkWeights readFFNeuralNetworkWeights() {
        FFNeuralNetworkWeightsParser parser = new FFNeuralNetworkWeightsParser();
        try {
            if (_neuralNetworkFile == null || _neuralNetworkFile.trim().length() == 0)
                return null;
            FFNeuralNetworkWeights result = parser.parse(_neuralNetworkFile);
            if (result == null)
                showMessage("Parsing error","File: " + _neuralNetworkFile +
                    "\n has error, it returns FFNeuralNetworkWeights == null.");
            return result;
        } catch (Exception e) {
            //Ignore it for now and use default instead.
            showMessage("Parsing error","File: " + _neuralNetworkFile +
                    "\n has error: " + e.getMessage());
            return null;
        }
    }

	/** Method to override if you want to define your own rule set.<br />  
	 * 
	 * The default network is very simple it is marking particles Tall, Flat
	 * based on their aspect ratio.
	 */
	protected void defineRules() {
 		FFNeuralNetworkWeights fFNeuralNetworkWeights =
                readFFNeuralNetworkWeights();
		if (fFNeuralNetworkWeights != null &&
                fFNeuralNetworkWeights.getRulePredicates().size() == 0) {
            showMessage("Missing rules","File: " + _neuralNetworkFile +
                    "\n has missing letter definition rules, using default instead.");
            fFNeuralNetworkWeights = null;
        }
        if (fFNeuralNetworkWeights != null)
            loadLetterStreams.loadUserDefinedSymbolStreams(
                    fFNeuralNetworkWeights, StreamNames.LETTERS);
        else
    		loadLetterStreams.loadLetterStream(null);
		_categorizer = StreamFactory.findNumberedStream(StreamNames.LETTERS, this);
	}

	/** Method to override if you want to define your own neural network.<br />  
	 * 
	 * The default network is very simple it is marking particles Dark or Light.
	 */
	protected void defineNeuralNetwork() {
		loadLetterStreams.loadLetterStream(null);
 		FFNeuralNetworkWeights fFNeuralNetworkWeights = 
                readFFNeuralNetworkWeights();
		if (fFNeuralNetworkWeights != null &&
                fFNeuralNetworkWeights.getWeights().length == 0) {
            showMessage("Missing weights","File: " + _neuralNetworkFile +
                    "\n has missing neural network weights, using default instead.");
            fFNeuralNetworkWeights = null;
        }
		if (fFNeuralNetworkWeights == null) {
			String[] objectHypotheses = new String[] {"No holes", "Holes"};
			String[] inputStreamName = {CommonLogicExpressions.HOLE_COUNT};
			double[][] weights = ExampleNeuralNetwork.makeSmallerThanGreaterThanNeuralNetwork(0.5);
			fFNeuralNetworkWeights = new FFNeuralNetworkWeights(
					Arrays.asList(inputStreamName),
					Arrays.asList(objectHypotheses), 
					weights);
            String[] printArray = {"Category", "Points", "lineCount", "holeCount"};
            List<String> printList = fFNeuralNetworkWeights.getPrintList();
            for (String element: printArray)
                printList.add(element);
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
		try {
			init();
			matchLines();
			if (_displayResultTable || _displayAll)
				printTable();
		} catch (NoSuchElementException e) {
			_errorMessage = e.getMessage();
            showMessage("Missing element", e.getMessage());
		}
	}

	@Override
	public Map getContext() {
		return _context;
	}

	@Override
	public RecursiveContext getParentContext() {
		return null;
	}

	public void printTable() {
        defaultStreamDefinitions();
        customStreamDefinitions();
        categorizeStreams();
        defaultColumnDefinitions();
        setupTableBuilder();
        populateResultsTable();
		displayResultsTable();
	}

	protected void defaultStreamDefinitions() {
	}
	
	protected void categorizeStreams() {
	}

	protected void customStreamDefinitions() {
        _tableDefinition = new TableDefinition(null);
        _tableDefinition.addDefinition(_categorizer, Headings.CATEGORY);
        _tableDefinition.addDefinition(CommonLogicExpressions.POINT_COUNT,"Points");
        _tableDefinition.addDefinition(CommonLogicExpressions.LINE_COUNT,"Lines");
		_tableDefinition.addDefinition(CommonLogicExpressions.HOLE_COUNT, "Holes");
		_tableDefinition.addDefinition(CommonLogicExpressions.T_JUNCTION_LEFT_POINT_COUNT,"T_left");
		_tableDefinition.addDefinition(CommonLogicExpressions.T_JUNCTION_RIGHT_POINT_COUNT,"T_right");
		_tableDefinition.addDefinition(CommonLogicExpressions.END_POINT_BOTTOM_POINT_COUNT, "End_bottom");
		_tableDefinition.addDefinition(CommonLogicExpressions.HORIZONTAL_LINE_COUNT, "Horizontal");
		_tableDefinition.addDefinition(CommonLogicExpressions.VERTICAL_LINE_COUNT, "Vertical");
		_tableDefinition.addDefinition(CommonLogicExpressions.END_POINT_COUNT, null);
		_tableDefinition.addDefinition(CommonLogicExpressions.SOFT_POINT_COUNT, null);

	}

	protected void defaultColumnDefinitions() {
	}

	protected void setupTableBuilder() {
	}

	protected void populateResultsTable(){
    	List<Polygon> polygons = _stream.getList();
        _tableDefinition.findNonEmptyColumns(this);
        if (_printListOverwrite != null)
        	_tableDefinition.sort(_printListOverwrite, this);
        _tableBuilder.buildHeadline();
    	for (int i=0;i<polygons.size();i++) {
    		if (populateResultsTableRow(i))
    			;
    	}
	}

	protected void displayResultsTable() {
        _tableBuilder = new BaseTableBuilder(_tableDefinition);
	}

	protected boolean populateResultsTableRow(int index) {
		return true;
	}

    public void setUseNeuralNetwork(boolean useNeuralNetwork) {
        _useNeuralNetwork = useNeuralNetwork;
    }

    public void setNeuralNetworkFile(String neuralNetworkFile) {
        _neuralNetworkFile = neuralNetworkFile;
    }

}
