package org.shapelogic.imageprocessing;

import ij.measure.ResultsTable;

import java.util.List;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.imageutil.PixelArea;
import org.shapelogic.streams.ListCalcStream1;
import org.shapelogic.streams.ListStream;
import org.shapelogic.streams.WrappedListStream;
import org.shapelogic.util.Headings;

/** Analyzes a particle image in gray or RGB and group the particles according 
 * to shape rules.<br />
 * 
 * Find a place in the base class where there is a hook for this extended functionality.<br />
 * 
 * @author Sami Badawi
 *
 */
public class ColorParticleAnalyzer extends BaseParticleCounter {
	protected WrappedListStream<IColorAndVariance> _particleStream;
	protected ListStream<ChainCodeHandler> _chainCodeHandlerStream;
	protected EdgeTracer _edgeTracer;
	protected ListCalcStream1<IColorAndVariance, Double> _aspectRatioStream;

	/** Analyzes particles and group them.<br />
	 * 
	 * Not sure if I should use named streams or try to avoid it to make it more thread safe.
	 */
	@Override
	protected void analyzeParticles() {
    	_particleStream = new WrappedListStream<IColorAndVariance>(_particlesFiltered);
    	_edgeTracer = new EdgeTracer(_image,_colorHypothesis.getBackground().getMeanColor(),
    			_maxDistance, false);
		Calc1<IColorAndVariance, ChainCodeHandler> chainCodeCalc1 = 
			new Calc1<IColorAndVariance, ChainCodeHandler>() {
				@Override
				public ChainCodeHandler invoke(IColorAndVariance input) {
					PixelArea pixelArea = input.getPixelArea();
					return _edgeTracer.autoOutline(pixelArea.getStartX(), pixelArea.getStartY());
				}
		};
		_chainCodeHandlerStream = 
			new ListCalcStream1<IColorAndVariance, ChainCodeHandler>(chainCodeCalc1,_particleStream); 
		_chainCodeHandlerStream.setup();
		
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
    protected void prepareResultsTable() {
    	List<IColorAndVariance> particles = _particlesFiltered;
        ResultsTable _rt = new ResultsTable();
    	_rt.getFreeColumn(Headings.COLOR);
    	_rt.getFreeColumn(Headings.PERIMETER);
    	_rt.setDefaultHeadings();
    	for (int i=0;i<particles.size();i++) {
    		IColorAndVariance particle = _particleStream.get(i);
        	if (particle == null)
        		continue;
        	if (particle.getArea() < getMinPixelsInArea())
        		continue;
        	_rt.incrementCounter();
        	_rt.addValue(ResultsTable.AREA, particle.getArea());
        	_rt.addValue(ResultsTable.STD_DEV, particle.getStandardDeviation());
        	_rt.addValue(Headings.COLOR, particle.getMeanColor());
        	PixelArea pixelArea = particle.getPixelArea();
        	if (pixelArea != null) {
            	_rt.addValue(ResultsTable.X_CENTER_OF_MASS, pixelArea.getCenterPoint().getX());
            	_rt.addValue(ResultsTable.Y_CENTER_OF_MASS, pixelArea.getCenterPoint().getY());
        	}
    		ChainCodeHandler chainCodeHandler = _chainCodeHandlerStream.get(i); 
        	if (chainCodeHandler == null)
        		continue;
        	int perimeter = chainCodeHandler.getLastChain() + 1;
        	System.out.println("perimeter: " + perimeter);
        	_rt.addValue(Headings.PERIMETER, perimeter);
    	}
    }
}
