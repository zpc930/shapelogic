package org.shapelogic.imageprocessing;

import java.util.List;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.IQueryCalc;
import org.shapelogic.calculation.QueryCalc;
import org.shapelogic.calculation.RootMap;
import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.imageutil.PixelArea;
import org.shapelogic.logic.CommonLogicExpressions;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.streamlogic.LoadLetterStreams;
import org.shapelogic.streamlogic.LoadParticleStreams;
import org.shapelogic.streamlogic.LoadPolygonStreams;
import org.shapelogic.streamlogic.StreamNames;
import org.shapelogic.streams.ListCalcStream1;
import org.shapelogic.streams.ListStream;
import org.shapelogic.streams.NumberedStream;
import org.shapelogic.streams.WrappedListStream;
import org.shapelogic.streams.XOrListStream;

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
    	_edgeTracer = new EdgeTracer(_image,_referenceColor,
    			_maxDistance, false);
		Calc1<IColorAndVariance, Polygon> chainCodeCalc1 = 
			new Calc1<IColorAndVariance, Polygon>() {
				@Override
				public Polygon invoke(IColorAndVariance input) {
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
    }
    
	/** Analyzes particles and group them.<br />
	 * 
	 * Not sure if I should use named streams or try to avoid it to make it more thread safe.
	 */
	@Override
	protected void categorizeStreams() {
		loadParticleStreams.exampleMakeParticleStream();
    	loadLetterStreams.makeXOrStream(StreamNames.PARTICLES, LoadParticleStreams.EXAMPLE_PARTICLE_ARRAY);
    	_categorizer = (XOrListStream) QueryCalc.getInstance().get(StreamNames.PARTICLES, this);
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
	}

	@Override
	protected void populateResultsTable(){
    	List<IColorAndVariance> particles = _particlesFiltered;
    	for (int i=0;i<particles.size();i++) {
    		if (populateResultsTableRow(i))
    			populateResultsTableRowCustom(i);
    	}
	}

}
