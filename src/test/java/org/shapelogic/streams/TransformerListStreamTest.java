package org.shapelogic.streams;

import org.shapelogic.calculation.IndexTransform;
import org.shapelogic.streams.TransformerListStream;

/** Test of TransformerStream.
 * 
 * This is a lazy stream that will calculate a value based on 
 * 
 * <ul>
 * <li>the input index of the element</li> 
 * <li>a transform function from the index</li>
 * <li>the other values in the stream</li> 
 * </ul>
 * 
 * @author Sami Badawi
 *
 */
public class TransformerListStreamTest extends AbstractListStreamTests {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		fibonacciNumbersAtStart = 2;
		fibonacciNumbersAfterOneIteration = 2;
	}

	TransformerListStream<Object,Integer> countingBaseStreamFactory(final int stopNumber) {
		TransformerListStream<Object, Integer> stream = new TransformerListStream<Object,Integer>() {

			{
				_maxLast = stopNumber;
				_last = stopNumber;
			}
			
			{
				_transformer = new IndexTransform<Object,Integer>() {

					@Override
					public Integer transform(Object arg0, int index) {
						return index;
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
	TransformerListStream<Object,Integer> fibonacciBaseStreamFactory() {
		TransformerListStream<Object,Integer> stream = new TransformerListStream<Object,Integer>() {
			{
				final TransformerListStream<Object,Integer> parent = this;
				//Add 2 first values
				_list.add(1);
				_list.add(1);
				_transformer = new IndexTransform<Object,Integer>() {

					@Override
					public Integer transform(Object obj, int arg0) {
						int index = (Integer)arg0;
						return ((Number)parent.get(index-2)).intValue() + ((Number)parent.get(index-1)).intValue();
					}

				};
			}
		};
		return stream;
	}
	
}
