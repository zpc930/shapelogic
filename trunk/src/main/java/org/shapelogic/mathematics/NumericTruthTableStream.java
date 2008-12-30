package org.shapelogic.mathematics;

import org.shapelogic.streams.BaseCommonNumberedStream;
import org.shapelogic.util.Constants;

/** NumericTruthTableStream creates Numeric Truth Table Stream with different
 * phases. <br />
 *
 * @author Sami Badawi
 */
public class NumericTruthTableStream extends BaseCommonNumberedStream<Double> {
	private int _phase = 1;

	public NumericTruthTableStream() {
        super();
	}

	public NumericTruthTableStream(int phase) {
        this(phase, Constants.LAST_UNKNOWN);
	}

	public NumericTruthTableStream(int phase, boolean oneCycle) {
        this(phase, oneCycle ? (phase * 2) - 1 : Constants.LAST_UNKNOWN);
	}

	public NumericTruthTableStream(int phase, Integer maxLast) {
        this();
		_phase = phase;
		if (maxLast != null)
			setMaxLast(maxLast);
	}

	public int getStartIndex() {
		return _phase;
	}

    @Override
    public Double invokeIndex(int index) {
		return 0. + (index / _phase) % 2;
    }
}
