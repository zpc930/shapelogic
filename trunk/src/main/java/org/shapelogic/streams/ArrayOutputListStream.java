package org.shapelogic.streams;

import java.util.ArrayList;
import java.util.List;

import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.util.Constants;

/** ArrayOutputListStream takes a list of NumberStreams and creates a ListStream of double[].<br />
 *
 * This could be made more general, but start simple.<br />
 *
 * @author Sami Badawi
 *
 */
public class ArrayOutputListStream extends BaseListStreamList<Number,double[]> {
	
	/** Parallel to the NumberedStream. */
	protected List<String> _ohNames;
	
	/** Use the ohName to also be the name of the input stream. <br />
	 * 
	 * @param ohNames
	 * @param maxLast
	 */
	public ArrayOutputListStream(List<String> ohNames, RecursiveContext recursiveContext,int maxLast) {
		super(null,maxLast);
		_ohNames = ohNames;
		_inputStream = new ArrayList(); 
		for (String streamName: _ohNames) {
			NumberedStream numberedStream = StreamFactory.findNumberedStream(streamName, recursiveContext);
			if (numberedStream != null)
				getInputStream().add(numberedStream);
			else
				throw new RuntimeException("No stream found for name: " + ohNames);
		}
	}
	
	public ArrayOutputListStream(List<String> ohNames, RecursiveContext recursiveContext) {
		this(ohNames, recursiveContext, Constants.LAST_UNKNOWN);
	}
	
	public ArrayOutputListStream(List<String> ohNames, RecursiveContext recursiveContext, List<NumberedStream<Number> > inputStream, int maxLast) {
		super(inputStream, maxLast);
		_ohNames = ohNames;
	}
	
	public ArrayOutputListStream(List<String> ohNames, RecursiveContext recursiveContext, List<NumberedStream<Number> > inputStream) {
		this(ohNames,recursiveContext,inputStream,Constants.LAST_UNKNOWN);
	}
	
	@Override
	public double[] invoke(List<Number> input) {
        double[] result = new double[input.size()];
		int i = 0;
		for (Number bool : input) {
			result[i] = bool.doubleValue();
            i++;
        }
		return result;
	}

}
