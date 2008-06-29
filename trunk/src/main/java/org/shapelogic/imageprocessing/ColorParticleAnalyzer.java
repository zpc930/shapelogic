package org.shapelogic.imageprocessing;

import java.util.List;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.RootMap;
import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.imageutil.PixelArea;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.streamlogic.LoadLetterStreams;
import org.shapelogic.streamlogic.LoadPolygonStreams;
import org.shapelogic.streamlogic.StreamNames;
import org.shapelogic.streams.ListCalcStream1;
import org.shapelogic.streams.ListStream;
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
	protected EdgeTracer _edgeTracer;
	protected ListCalcStream1<IColorAndVariance, Double> _aspectRatioStream;
	protected ListCalcStream1<IColorAndVariance, Boolean> _roundishStream;

	protected void defaultStreamDefinitions() {
    	_particleStream = new WrappedListStream<IColorAndVariance>(_particlesFiltered);
    	_edgeTracer = new EdgeTracer(_image,_colorHypothesis.getBackground().getMeanColor(),
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
    }
    
	/** Analyzes particles and group them.<br />
	 * 
	 * Not sure if I should use named streams or try to avoid it to make it more thread safe.
	 */
	@Override
	protected void categorizeStreams() {
		LoadLetterStreams.makeParticleStream();
    	LoadLetterStreams.makeXOrStream(StreamNames.PARTICLES, LoadLetterStreams.particleArray);
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
