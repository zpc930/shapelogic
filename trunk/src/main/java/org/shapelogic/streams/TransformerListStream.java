package org.shapelogic.streams;

import org.shapelogic.calculation.IndexTransform;

/**
 * Make a stream that is using a Transformer class to do a calculation
 * 
 * @author Sami Badawi
 * 
 */
public class TransformerListStream<In, E> extends BaseListStream1<In, E> {
	protected IndexTransform<In, E> _transformer;
	
	public TransformerListStream() {
	}
	
	public TransformerListStream(IndexTransform<In, E> transformer) {
		_transformer = transformer;
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
