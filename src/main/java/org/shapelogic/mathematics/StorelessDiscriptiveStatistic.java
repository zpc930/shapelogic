package org.shapelogic.mathematics;

/** Find min, max, mean, standard deviation, variance.<br />
 * 
 * This exist in Apache Commons Math, but each statistic would have to be done 
 * individually.
 * 
 * @author Sami Badawi
 *
 */
public class StorelessDiscriptiveStatistic {
	private double _min = Double.POSITIVE_INFINITY;
	private double _max = Double.NEGATIVE_INFINITY;
	private double _total = 0.;
	private double _totalSquare = 0.;
	private int _count = 0;
	
	/** Add an extra element to be part of the input. */
	public void increment(double input) {
		if (input < _min)
			_min = input;
		if (_max < input)
			_max = input;
		_total += input;
		_totalSquare += input*input;
		_count++;
	}
	
	/** Reset the object for reuse. */
	public void clear() {
		_min = Double.POSITIVE_INFINITY;
		_max = Double.NEGATIVE_INFINITY;
		_total = 0.;
		_totalSquare = 0.;
		_count = 0;
	}

	public double getMin() {
		return _min;
	}

	public double getMax() {
		return _max;
	}

	public double getTotal() {
		return _total;
	}

	public double getTotalSquare() {
		return _totalSquare;
	}

	public int getCount() {
		return _count;
	}
	
	public double getMean() {
		return _total/_count;
	}
	
	/** The biased estimator for Standard Deviation. */
	public double getStandardDeviation() {
		if (_count == 0)
			return Double.NaN;
		return Math.sqrt(getVariance());
	}

	/** The biased estimator for variance. */
	public double getVariance() {
		if (_count == 0)
			return Double.NaN;
		double mean = getMean();
		return _totalSquare /_count - mean*mean;
	}
	
	public void merge(StorelessDiscriptiveStatistic input) {
		_total+=input.getTotal();
		_totalSquare+=input.getTotalSquare();
		_count+=input.getCount();
		_min = Math.min(_min, input.getMin());
		_max = Math.max(_max, input.getMax());
	}
}
