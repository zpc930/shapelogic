package org.shapelogic.calculation;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

/** Test CalcInContext.<br />
 * 
 * The idea was that multiple contexts should be represented as an array of maps.<br />
 * So this test was set up to test that the first value was taken out.<br />
 * 
 * @author Sami Badawi
 *
 */
public class CalcInContextTest extends TestCase {
	private IQueryCalc<String, Integer> query;
	private Map<String, Integer> mapDirectValues;
	private Map<String, CalcValue<Integer> > mapLazyCalcValues;
	private final String KEY = "version";
	static final private Integer VALUE_DIRECT = 1;
	static final private Integer VALUE_LAZY = 3;
	static final private Integer DVALUE_DIRECT = 2;
	static final private Integer DVALUE_LAZY = 6;
	private CalcFixed<Integer> ONE = new CalcFixed<Integer>(VALUE_LAZY); 
	private final String DOUBLE_KEY = "dependent";
	private BaseCalcInContext doubleValue = 
		new BaseCalcInContext(DOUBLE_KEY) {
			@Override
			public Integer invoke(RecursiveContext contextArray) {
				return ((Integer) _query.get(KEY, contextArray)) * 2;
			}
	};
	
	@Override
	public void setUp() {
		query = new QueryCalc<String, Integer>();
		mapDirectValues = new TreeMap<String, Integer>(); 
		mapLazyCalcValues = new TreeMap<String, CalcValue<Integer> >(); 
		mapDirectValues.put(KEY, VALUE_DIRECT);
		mapDirectValues.put(DOUBLE_KEY, DVALUE_DIRECT);
		mapLazyCalcValues.put(KEY, ONE);
		mapLazyCalcValues.put(DOUBLE_KEY, doubleValue);
	}

	/** Calculation is not set in this.
	 */
	public void testDirect() {
		assertNotNull(query);
		assertEquals(VALUE_DIRECT,query.get(KEY, mapDirectValues));
		assertEquals(DVALUE_DIRECT,query.get(DOUBLE_KEY, mapDirectValues));
	}

	public void testCalc() {
		assertEquals(VALUE_LAZY,query.get(KEY, mapLazyCalcValues));
		assertEquals(DVALUE_LAZY,query.get(DOUBLE_KEY, mapLazyCalcValues));
	}

	public void testCalcDirect() {
		assertEquals(VALUE_DIRECT,query.get(KEY, mapLazyCalcValues, mapDirectValues));
		assertEquals(DVALUE_DIRECT,query.get(DOUBLE_KEY, mapLazyCalcValues, mapDirectValues));
	}

	public void testDirectCalc() {
		assertEquals(VALUE_LAZY,query.get(KEY, mapDirectValues, mapLazyCalcValues));
		assertEquals(DVALUE_LAZY,query.get(DOUBLE_KEY, mapDirectValues, mapLazyCalcValues));
	}
}
