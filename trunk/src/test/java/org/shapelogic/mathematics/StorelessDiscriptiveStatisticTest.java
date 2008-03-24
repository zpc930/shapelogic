package org.shapelogic.mathematics;

import junit.framework.TestCase;

/** Test StorelessDiscriptiveStatistic.<br />
 * 
 * @author Sami Badawi
 *
 */
public class StorelessDiscriptiveStatisticTest extends TestCase {
	
	public void test0() {
		StorelessDiscriptiveStatistic sds = new StorelessDiscriptiveStatistic();
		assertEquals(0., sds.getTotal());
		assertEquals(Double.NaN, sds.getMean());
		assertEquals(Double.NEGATIVE_INFINITY, sds.getMax());
		assertEquals(Double.POSITIVE_INFINITY, sds.getMin());
		assertEquals(Double.NaN, sds.getVariance());
		assertEquals(Double.NaN, sds.getStandardDeviation());
	}

	public void test1() {
		StorelessDiscriptiveStatistic sds = new StorelessDiscriptiveStatistic();
		sds.increment(100);
		assertEquals(100., sds.getTotal());
		assertEquals(100., sds.getMean());
		assertEquals(100., sds.getMax());
		assertEquals(100., sds.getMin());
		assertEquals(0., sds.getVariance());
		assertEquals(0., sds.getStandardDeviation());
	}
	
	public void test2() {
		StorelessDiscriptiveStatistic sds = new StorelessDiscriptiveStatistic();
		sds.increment(200);
		sds.increment(100);
		assertEquals(300., sds.getTotal());
		assertEquals(150., sds.getMean());
		assertEquals(200., sds.getMax());
		assertEquals(100., sds.getMin());
		assertEquals(2500., sds.getVariance());
		assertEquals(50., sds.getStandardDeviation());
	}
}
