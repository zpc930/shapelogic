package org.shapelogic.imageprocessing;

import java.util.List;

import org.shapelogic.calculation.Calc1;
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

	protected void defaultStreamDefinitions() {
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
		RootMap.put(StreamNames.POLYGONS, _polygonStream);
		LoadPolygonStreams.loadStreamsRequiredForLetterMatch();
		LoadParticleStreams.loadStreamsRequiredForParticleMatch(_particleStream,_image);
    	_grayValueStream = (NumberedStream<Integer>) RootMap.get(StreamNames.COLOR_GRAY);
    	_hardCornerCountStream = (NumberedStream<Integer>) RootMap.get(CommonLogicExpressions.HARD_CORNER_COUNT);
    	_inflectionPointCountStream = (NumberedStream<Integer>) RootMap.get(CommonLogicExpressions.INFLECTION_POINT_COUNT);
    	_curveArchCountStream = (NumberedStream<Integer>) RootMap.get(CommonLogicExpressions.CURVE_ARCH_COUNT);
    }
    
	/** Analyzes particles and group them.<br />
	 * 
	 * Not sure if I should use named streams or try to avoid it to make it more thread safe.
	 */
	@Override
	protected void categorizeStreams() {
		LoadParticleStreams.exampleMakeParticleStream();
    	LoadLetterStreams.makeXOrStream(StreamNames.PARTICLES, LoadParticleStreams.EXAMPLE_PARTICLE_ARRAY);
    	_categorizer = (XOrListStream) RootMap.get(StreamNames.PARTICLES);
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
