package org.shapelogic.calculation;

import org.apache.commons.collections.Transformer;

/** Make a stream that is using a Transformer class to do a calculation
 * 
 * @author Sami Badawi
 *
 */
public class TransformerStream<E> extends BaseStream<E> {
	protected Transformer _transformer;
	
	@Override
	public E calcElement(int index) {
		// TODO Auto-generated method stub
		return (E) _transformer.transform(index);
	}
}
