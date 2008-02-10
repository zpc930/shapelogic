package org.shapelogic.streams;

import org.shapelogic.calculation.CalcIndex1;
import org.shapelogic.util.Constants;

/** Make a stream that is using a Transformer class to do a calculation.
 * <br />
 * In type does not seem to be used.<br />
 * 
 * XXX This should be called ListCalcIndexStream1 or something close to that.<br />
 * 
 * @author Sami Badawi
 * 
 */
public class TransformerListStream<In, E> extends BaseListIndexedStream1<In, E> {
	protected CalcIndex1<In, E> _transformer;
	
	public TransformerListStream(CalcIndex1<In, E> transformer, ListStream<In> inputStream, int maxLast) {
		super(inputStream, maxLast);
		_transformer = transformer;
	}

	public TransformerListStream(CalcIndex1<In, E> transformer) {
		this(transformer,null, Constants.LAST_UNKNOWN);
	}

	public TransformerListStream() {
	}
	
	public TransformerListStream(CalcIndex1<In, E> transformer, int maxLast) {
		_transformer = transformer;
		setMaxLast(maxLast);
	}

	@Override
	public E invoke(In input, int index) {
		return _transformer.invoke(input, index);
	}
}
