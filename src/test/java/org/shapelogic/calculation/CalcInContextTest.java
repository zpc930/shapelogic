package org.shapelogic.calculation;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

/** Test CalcInContext.
 * 
 * This is a pretty general calculation. 
 * 
 * @author Sami Badawi
 *
 */
public class CalcInContextTest extends TestCase {
	private IQueryCalc<String, Integer> query;
	private Map<String, Integer> mapDirectValues;
	private Map<String, CalcValue<Integer> > mapLazyCalcValues;
	private final String KEY = "version";
	private Integer VALUE1 = 1;
	private Integer VALUE2 = 2;
	private Integer DVALUE1 = 2;
	private Integer DVALUE2 = 4;
	private CalcFixed<Integer> ONE = new CalcFixed<Integer>(VALUE2); 
	private final String DOUBLE_KEY = "dependent";
	private BaseCalcInContext doubleValue = 
		new BaseCalcInContext(DOUBLE_KEY) {
			@Override
			public Integer invoke(Map[] contextArray) {
				return ((Integer) _query.get(KEY, contextArray)) * 2;
			}
	};
	
	@Override
	public void setUp() {
		query = new QueryCalc<String, Integer>();
		mapDirectValues = new TreeMap<String, Integer>(); 
		mapLazyCalcValues = new TreeMap<String, CalcValue<Integer> >(); 
		mapDirectValues.put(KEY, VALUE1);
		mapLazyCalcValues.put(KEY, ONE);
		mapLazyCalcValues.put(DOUBLE_KEY, doubleValue);
	}

	/** Calculation is not set in this.
	 */
	public void testDirect() {
		assertNotNull(query);
		assertEquals(VALUE1,query.get(KEY, mapDirectValues));
		assertEquals(null,query.get(DOUBLE_KEY, mapDirectValues));
	}

	public void testCalc() {
		assertEquals(VALUE2,query.get(KEY, mapLazyCalcValues));
		assertEquals(DVALUE2,query.get(DOUBLE_KEY, mapLazyCalcValues));
	}

	public void testCalcDirect() {
		assertEquals(VALUE1,query.get(KEY, mapLazyCalcValues, mapDirectValues));
		assertEquals(DVALUE1,query.get(DOUBLE_KEY, mapLazyCalcValues, mapDirectValues));
	}

	public void testDirectCalc() {
		assertEquals(VALUE2,query.get(KEY, mapDirectValues, mapLazyCalcValues));
		assertEquals(DVALUE2,query.get(DOUBLE_KEY, mapDirectValues, mapLazyCalcValues));
	}
}
