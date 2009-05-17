package org.shapelogic.calculation;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

/** Test QueryCalc.
 * 
 * @author Sami Badawi
 *
 */
public class QueryCalcTest extends TestCase {
	private IQueryCalc<String, Integer> query;
	private Map<String, Integer> mapDirectValues;
	private Map<String, CalcValue<Integer> > mapLazyCalcValues;
	private String KEY = "version";
	private Integer VALUE1 = 1;
	private Integer VALUE2 = 2;
	private CalcFixed<Integer> ONE = new CalcFixed<Integer>(VALUE2); 
	private String DOUBLE_KEY = "dependent";
	
	@Override
	public void setUp() {
		query = new QueryCalc<String, Integer>();
		mapDirectValues = new TreeMap<String, Integer>(); 
		mapLazyCalcValues = new TreeMap<String, CalcValue<Integer> >(); 
		mapDirectValues.put(KEY, VALUE1);
		mapLazyCalcValues.put(KEY, ONE);
	}

	public void test() {
		assertNotNull(query);
		assertEquals(VALUE1,query.get(KEY, mapDirectValues));

		assertEquals(VALUE2,query.get(KEY, mapLazyCalcValues));
		
		assertEquals(VALUE1,query.get(KEY, mapLazyCalcValues, mapDirectValues));
		
		assertEquals(VALUE2,query.get(KEY, mapDirectValues, mapLazyCalcValues));

	}
}
