package org.shapelogic.imageprocessing;

import java.util.List;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.IQueryCalc;
import org.shapelogic.calculation.QueryCalc;
import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.imageutil.PixelArea;
import org.shapelogic.logic.CommonLogicExpressions;
import org.shapelogic.machinelearning.ExampleNeuralNetwork;
import org.shapelogic.machinelearning.FFNeuralNetworkStream;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.reporting.BaseTableBuilder;
import org.shapelogic.reporting.TableDefinition;
import org.shapelogic.streamlogic.LoadLetterStreams;
import org.shapelogic.streamlogic.LoadParticleStreams;
import org.shapelogic.streamlogic.LoadPolygonStreams;
import org.shapelogic.streamlogic.StreamNames;
import org.shapelogic.streams.BaseCommonNumberedStream;
import org.shapelogic.streams.CalcNumberedStream1;
import org.shapelogic.streams.ListCalcStream1;
import org.shapelogic.streams.ListStream;
import org.shapelogic.streams.NumberedStream;
import org.shapelogic.streams.WrappedListStream;
import org.shapelogic.util.Constants;
import org.shapelogic.util.Headings;

/** Analyzes a particle image in gray or RGB and group the particles according 
 * to shape rules.<br />
 * 
 * Find a place in the base class where there is a hook for this extended functionality.<br />
 * 
 * Some of the methods can be moved to ColorParticleAnalyzer to remove dependency of ImageJ.<br /> 
 * 
 * @author Sami Badawi
 *
 */
public class ColorParticleAnalyzer extends BaseParticleCounter {
	protected WrappedListStream<IColorAndVariance> _particleStream;
	protected ListStream<Polygon> _polygonStream;
	protected IEdgeTracer _edgeTracer;
	protected NumberedStream<Double> _aspectRatioStream;
	protected NumberedStream<Integer> _grayValueStream;
	protected NumberedStream<Integer> _hardCornerCountStream;
	protected ListCalcStream1<IColorAndVariance, Boolean> _roundishStream;
	protected NumberedStream<Integer> _inflectionPointCountStream;
	protected NumberedStream<Integer> _curveArchCountStream;
	protected LoadPolygonStreams loadPolygonStreams;
	protected LoadParticleStreams loadParticleStreams; 
	protected LoadLetterStreams loadLetterStreams;
	protected NumberedStream<Double> _xMinStream;
	protected NumberedStream<Double> _yMinStream;
	protected NumberedStream<Double> _xMaxStream;
	protected NumberedStream<Double> _yMaxStream;
    protected NumberedStream<Double> _perimeterStream;
    protected NumberedStream<Integer> _areaStream;

    protected TableDefinition _tableDefinition;
    protected BaseTableBuilder _tableBuilder;

    protected boolean _useNeuralNetwork;

	@Override
	public void init() throws Exception {
		super.init();
		loadPolygonStreams = new LoadPolygonStreams(this);
		loadParticleStreams = new LoadParticleStreams(this);
		loadLetterStreams = new LoadLetterStreams(this);
	}

	protected void defaultStreamDefinitions() {
		IQueryCalc queryCalc = QueryCalc.getInstance();
    	_particleStream = new WrappedListStream<IColorAndVariance>(_particlesFiltered);
		_context.put(StreamNames.PARTICLES, _particleStream);
        int traceColor = _paintForground;
        boolean traceCloseToColor = true;
        if (!_toMask) {
            traceColor = _referenceColor;
            traceCloseToColor = false;
        }
    	_edgeTracer = new EdgeTracer(_image, traceColor,
    			_maxDistance, traceCloseToColor);
		Calc1<IColorAndVariance, Polygon> chainCodeCalc1 = 
			new Calc1<IColorAndVariance, Polygon>() {
				@Override
				public Polygon invoke(IColorAndVariance input) {
					if (input == null)
						return null;
					PixelArea pixelArea = input.getPixelArea();
					return _edgeTracer.autoOutline(pixelArea.getStartX(), pixelArea.getStartY());
				}
		};
		_polygonStream = 
			new ListCalcStream1<IColorAndVariance, Polygon>(chainCodeCalc1,_particleStream); 
		_polygonStream.setup();
		_context.put(StreamNames.POLYGONS, _polygonStream);
		loadPolygonStreams.loadStreamsRequiredForLetterMatch();
		loadParticleStreams.loadStreamsRequiredForParticleMatch(_particleStream,_image);
    	_grayValueStream = (NumberedStream<Integer>) queryCalc.get(StreamNames.COLOR_GRAY, this);
    	_hardCornerCountStream = (NumberedStream<Integer>) queryCalc.get(CommonLogicExpressions.HARD_CORNER_COUNT, this);
    	_inflectionPointCountStream = (NumberedStream<Integer>) queryCalc.get(CommonLogicExpressions.INFLECTION_POINT_COUNT, this);
    	_curveArchCountStream = (NumberedStream<Integer>) queryCalc.get(CommonLogicExpressions.CURVE_ARCH_COUNT, this);
        makeBBoxStreams();
    }

    protected void makeBBoxStreams() {
        Calc1<IColorAndVariance, Double> xMinCalc1 =
			new Calc1<IColorAndVariance, Double>() {
				@Override
				public Double invoke(IColorAndVariance input) {
					if (input == null)
						return null;
					PixelArea pixelArea = input.getPixelArea();
					return pixelArea.getBoundingBox().minVal.getX();
				}
		};
        _xMinStream = new CalcNumberedStream1(xMinCalc1, _particleStream);
        _context.put(Headings.BOUNDING_BOX_X_MIN, _xMinStream);

        Calc1<IColorAndVariance, Double> yMinCalc1 =
			new Calc1<IColorAndVariance, Double>() {
				@Override
				public Double invoke(IColorAndVariance input) {
					if (input == null)
						return null;
					PixelArea pixelArea = input.getPixelArea();
					return pixelArea.getBoundingBox().minVal.getY();
				}
		};
        _yMinStream = new CalcNumberedStream1(yMinCalc1, _particleStream);
        _context.put(Headings.BOUNDING_BOX_Y_MIN, _yMinStream);

        Calc1<IColorAndVariance, Double> xMaxCalc1 =
			new Calc1<IColorAndVariance, Double>() {
				@Override
				public Double invoke(IColorAndVariance input) {
					if (input == null)
						return null;
					PixelArea pixelArea = input.getPixelArea();
					return pixelArea.getBoundingBox().maxVal.getX();
				}
		};
        _xMaxStream = new CalcNumberedStream1(xMaxCalc1, _particleStream);
        _context.put(Headings.BOUNDING_BOX_X_MAX, _xMaxStream);

        Calc1<IColorAndVariance, Double> yMaxCalc1 =
			new Calc1<IColorAndVariance, Double>() {
				@Override
				public Double invoke(IColorAndVariance input) {
					if (input == null)
						return null;
					PixelArea pixelArea = input.getPixelArea();
					return pixelArea.getBoundingBox().maxVal.getY();
				}
		};
        _yMaxStream = new CalcNumberedStream1(yMaxCalc1, _particleStream);
        _context.put(Headings.BOUNDING_BOX_Y_MAX, _yMaxStream);

    }
    
	/** Analyzes particles and group them.<br />
	 * 
	 * Not sure if I should use named streams or try to avoid it to make it more thread safe.
	 */
	@Override
	protected void categorizeStreams() {
        if (_useNeuralNetwork) {
        	defineNeuralNetwork();
        }
        else {
        	defineRules();
        }
	}
	
	
	/** Method to override if you want to define your own rule set.<br />  
	 * 
	 * The default network is very simple it is marking particles Tall, Flat
	 * based on their aspect ratio.
	 */
	protected void defineRules() {		
		loadParticleStreams.exampleMakeParticleStream();
        loadLetterStreams.makeXOrStream(StreamNames.PARTICLES, LoadParticleStreams.EXAMPLE_PARTICLE_ARRAY);
        _categorizer = (ListStream<String>) QueryCalc.getInstance().get(StreamNames.PARTICLES, this);
	}

	/** Method to override if you want to define your own neural network.<br />  
	 * 
	 * The default network is very simple it is marking particles Dark or Light.
	 */
	protected void defineNeuralNetwork() {
		String[] objectHypotheses = new String[] {"Tall", "Flat"};
		String[] inputStreamName = {StreamNames.ASPECT};
		double[][] weights = ExampleNeuralNetwork.makeSmallerThanGreaterThanNeuralNetwork(1.); 
		FFNeuralNetworkStream neuralNetworkStream = new FFNeuralNetworkStream(
				inputStreamName,objectHypotheses, weights,this);
         _categorizer = neuralNetworkStream.getOutputStream();
	}
	
	/** Define extra streams.*/
	@Override
	protected void customStreamDefinitions() {
		//XXX this is just a test definition, aspect ratio is already defined
		Calc1<IColorAndVariance, Double> aspectRatioCalc1 = 
			new Calc1<IColorAndVariance, Double>() {
				@Override
				public Double invoke(IColorAndVariance input) {
					PixelArea pixelArea = input.getPixelArea();
					return pixelArea.getBoundingBox().getAspectRatio();
				}
		};
		_aspectRatioStream = 
			new ListCalcStream1<IColorAndVariance, Double>(aspectRatioCalc1,_particleStream); 
		_aspectRatioStream.setup();
        _context.put(StreamNames.ASPECT, _aspectRatioStream);
	}

	@Override
	protected void defaultColumnDefinitions() {
        _tableDefinition = new TableDefinition(null);
        _tableDefinition.addDefinition(_categorizer, Headings.CATEGORY);

        Calc1<IColorAndVariance, Integer> areaClosure = new Calc1<IColorAndVariance, Integer>() {
            public Integer invoke(IColorAndVariance input) {
                return input.getArea();
            }
        };
        _areaStream = _tableDefinition.addClosureDefinition(_particleStream,areaClosure, Headings.AREA);

        Calc1<IColorAndVariance, Double> standardDeviantion = new Calc1<IColorAndVariance, Double>() {
            public Double invoke(IColorAndVariance input) {
                return input.getStandardDeviation();
            }
        };
        _tableDefinition.addClosureDefinition(_particleStream,standardDeviantion, Headings.COLOR_STD_DEV);

        Calc1<IColorAndVariance, Integer> meanColor = new Calc1<IColorAndVariance, Integer>() {
            public Integer invoke(IColorAndVariance input) {
                return input.getMeanColor();
            }
        };
        _tableDefinition.addClosureDefinition(_particleStream, meanColor, Headings.COLOR);

        if (getImage().isRgb()) {

            Calc1<IColorAndVariance, Integer> meanRed = new Calc1<IColorAndVariance, Integer>() {
                public Integer invoke(IColorAndVariance input) {
                    return input.getMeanRed();
                }
            };
            _tableDefinition.addClosureDefinition(_particleStream, meanRed, Headings.COLOR_RED);

            Calc1<IColorAndVariance, Integer> meanGreen = new Calc1<IColorAndVariance, Integer>() {
                public Integer invoke(IColorAndVariance input) {
                    return input.getMeanGreen();
                }
            };
            _tableDefinition.addClosureDefinition(_particleStream, meanGreen, Headings.COLOR_GREEN);

            Calc1<IColorAndVariance, Integer> meanBlue = new Calc1<IColorAndVariance, Integer>() {
                public Integer invoke(IColorAndVariance input) {
                    return input.getMeanBlue();
                }
            };
            _tableDefinition.addClosureDefinition(_particleStream, meanBlue, Headings.COLOR_BLUE);
        }

        Calc1<IColorAndVariance, Double> xCenterOfMass = new Calc1<IColorAndVariance, Double>() {
            public Double invoke(IColorAndVariance input) {
                PixelArea pixelArea = input.getPixelArea();
                if (pixelArea == null) return null;
                return pixelArea.getCenterPoint().getX();
            }
        };
        _tableDefinition.addClosureDefinition(_particleStream, xCenterOfMass, Headings.X_CENTER_OF_MASS);

        Calc1<IColorAndVariance, Double> yCenterOfMass = new Calc1<IColorAndVariance, Double>() {
            public Double invoke(IColorAndVariance input) {
                PixelArea pixelArea = input.getPixelArea();
                if (pixelArea == null) return null;
                return pixelArea.getCenterPoint().getY();
            }
        };
        _tableDefinition.addClosureDefinition(_particleStream, yCenterOfMass, Headings.Y_CENTER_OF_MASS);

        _tableDefinition.addDefinition(_xMinStream, Headings.BOUNDING_BOX_X_MIN);
        _tableDefinition.addDefinition(_yMinStream, Headings.BOUNDING_BOX_Y_MIN);
        _tableDefinition.addDefinition(_xMaxStream, Headings.BOUNDING_BOX_X_MAX);
        _tableDefinition.addDefinition(_yMaxStream, Headings.BOUNDING_BOX_Y_MAX);

        _tableDefinition.addDefinition(_aspectRatioStream, Headings.ASPECT_RATIO);

        Calc1<Polygon, Double> perimeterCalc = new Calc1<Polygon, Double>() {
            public Double invoke(Polygon input) {
                if (input == null) return null;
                return input.getPerimeter();
            }
        };
        _perimeterStream =
                _tableDefinition.addClosureDefinition(_polygonStream, perimeterCalc, Headings.PERIMETER);

// This is how you can make a stream that is using 2 other streams as input.
// Note that the input streams have to be defined at this point.
        BaseCommonNumberedStream<Double> circularityStream =
                new BaseCommonNumberedStream<Double>() {
            final NumberedStream<Double> _perimeterStreamInner = _perimeterStream;
            final NumberedStream<Integer> _areaStreamInner = _areaStream;

            @Override
            public Double invokeIndex(int index) {
                Double perimeter = _perimeterStreamInner.get(index);
                Integer area = _areaStreamInner.get(index);
                if (perimeter == null || area == null)
                    return null;
                return perimeter==0?0.0:4.0*Math.PI*area/(perimeter*perimeter);
            }

        };

        _tableDefinition.addDefinition(circularityStream, Headings.CIRCULARITY);
        _tableDefinition.addDefinition(_grayValueStream, Headings.GRAY_VALUE);
        _tableDefinition.addDefinition(_hardCornerCountStream, Headings.HARD_CORNERS);
        _tableDefinition.addDefinition(_inflectionPointCountStream, Headings.INFLECTION_POINT_COUNT);
        _tableDefinition.addDefinition(_curveArchCountStream, Headings.CURVE_ARCH_COUNT);
	}

	@Override
	protected void populateResultsTable(){
    	List<IColorAndVariance> particles = _particlesFiltered;
        _tableDefinition.findNonEmptyColumns(this);
        _tableBuilder.buildHeadline();
    	for (int i=0;i<particles.size();i++) {
    		if (populateResultsTableRow(i))
    			populateResultsTableRowCustom(i);
    	}
	}

	@Override
	protected void setupTableBuilder() {
        _tableBuilder = new BaseTableBuilder(_tableDefinition);
    }

	public StringBuffer getInternalInfo() {
	    StringBuffer result = new StringBuffer();
	    result.append(
	        "\n=====================Internal info about polygons outline of particle=====================\n");
	    result.append("Number of particles found: ").append(_particlesFiltered.size()).append("\n");
	    for (int i = 0; _polygonStream.getLast() == Constants.LAST_UNKNOWN || i <= _polygonStream.getLast(); i++) {
	        Polygon polygon = _polygonStream.get(i);
	        if (null != polygon)
	            result.append(polygon.toString());
	    }
	    return result;
	}
}
