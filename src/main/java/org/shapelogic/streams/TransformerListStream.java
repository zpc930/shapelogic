package org.shapelogic.streams;

import org.shapelogic.calculation.IndexTransform;
import org.shapelogic.util.Constants;

/** Make a stream that is using a Transformer class to do a calculation.
 * <br />
 * In type does not seem to be used.
 * 
 * @author Sami Badawi
 * 
 */
public class TransformerListStream<In, E> extends BaseListStream1<In, E> {
	protected IndexTransform<In, E> _transformer;
	
	public TransformerListStream(IndexTransform<In, E> transformer, ListStream<In> inputStream, int maxLast) {
		super(inputStream, maxLast);
		_transformer = transformer;
	}

	public TransformerListStream(IndexTransform<In, E> transformer) {
		this(transformer,null, Constants.LAST_UNKNOWN);
	}

	public TransformerListStream() {
	}
	
	public TransformerListStream(IndexTransform<In, E> transformer, int maxLast) {
		_transformer = transformer;
		setMaxLast(maxLast);
	}

	@Override
	public E invoke(In input, int index) {
		return _transformer.transform(input, index);
	}
}
