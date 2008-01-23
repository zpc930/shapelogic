package org.shapelogic.calculation;

import org.apache.commons.collections.Transformer;

/** Make a stream that is using a Transformer class to do a calculation
 * 
 * This version of Stream has been replaced with with Streams in package 
 * streams, use them instead.
 * TODO This should be deleted before release of ShapeLogic 0.9 
 * 
 * @author Sami Badawi
 *
 */
@Deprecated
public class TransformerStream<E> extends BaseStream<E> {
	protected Transformer _transformer;
	
	@Override
	public E calcElement(int index) {
		return (E) _transformer.transform(index);
	}
}
