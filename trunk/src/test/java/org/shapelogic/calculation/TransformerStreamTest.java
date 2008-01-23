package org.shapelogic.calculation;

import org.apache.commons.collections.Transformer;

/** Test of TransformerStream.
 * 
 * This is a lazy stream that will calculate a value based on 
 * 
 * <ul>
 * <li>the input index of the element</li> 
 * <li>a transform function from the index</li>
 * <li>the other values in the stream</li> 
 * </ul>
 * <br />
 * This version of Stream has been replaced with with Streams in package 
 * streams, use them instead.
 * TODO This should be deleted before release of ShapeLogic 0.9 
 * 
 * @author Sami Badawi
 *
 */
@Deprecated
public class TransformerStreamTest extends AbstractStreamTests {

	public void setUp() throws Exception {
		super.setUp();
		fibonacciNumbersAtStart = 2;
		fibonacciNumbersAfterOneIteration = 2;
	}

	BaseStream<Integer> countingBaseStreamFactory(final int stopNumber) {
		BaseStream<Integer> stream = new TransformerStream<Integer>() {

			@Override
			public boolean hasNext() {
				return getCurrentSize() <= stopNumber;
			}
			
			{
				_maxLast = stopNumber;
			}
			
			{
				_transformer = new Transformer() {

					@Override
					public Object transform(Object arg0) {
						return arg0;
					}
					
				};
			}

		}; 
		return stream;
	}
	
	/** Infinite lazy fibonnacci stream
	 * 
	 * @return
	 */
	BaseStream<Integer> fibonacciBaseStreamFactory() {
		BaseStream<Integer> stream = new TransformerStream<Integer>() {
			{
				final BaseStream parent = this;
				//Add 2 first values
				add(1);
				add(1);
				_transformer = new Transformer() {

					@Override
					public Object transform(Object arg0) {
						int index = (Integer)arg0;
						return ((Number)parent.get(index-2)).intValue() + ((Number)parent.get(index-1)).intValue();
					}
				};
			}
		};
		return stream;
	}
	
}
