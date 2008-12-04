package org.shapelogic.imageprocessing;


/** Unit test for LetterMatchingMaxDistanceVectorizer, 
 * The main letter matching and vectorization algorithm, 
 * that works for both straight and curved capital letters
 * Note that this is sub classed from another unit test for more basic tests 
 * that only work for straight letters  
 * 
 * @author Sami Badawi
 *
 */
public class LetterMatchingMaxDistanceVectorizerTest extends BaseLetterMatchingMaxDistanceVectorizerTests {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		vectorizer = new MaxDistanceVectorizer();
	}
	
}
