package org.shapelogic.streams;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.IQueryCalc;
import org.shapelogic.calculation.QueryCalc;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.util.Constants;

/** Make a stream that is using a Calc1 class to do a calculation, based on
 * a named input stream stored in a context.<br />
 *
 * Uses lazy setup so input stream does not have to exist when stream is created.<br />
 *
 * There should be a way to say whether you have to calculate all the values
 * sequentially to use this.<br />
 *
 * @author Sami Badawi
 * 
 */
public class NamedListCalcStream1<In, E> extends BaseListStream1<In, E>
{
	protected Calc1<In, E> _calc;
	protected static IQueryCalc _queryCalc = QueryCalc.getInstance();
    protected boolean _setupRun = false;
    String _inputName;
    String _outputName;

	public NamedListCalcStream1(Calc1<In, E> calc, String inputName,
            RecursiveContext recursiveContext, String outputName, int maxLast) {
		super(null, maxLast);
		_calc = calc;
        _inputName = inputName;
        _context = recursiveContext.getContext();
        _outputName = outputName;
        _parentContext = recursiveContext.getParentContext();
        if (outputName != null)
            _queryCalc.put(outputName, this, recursiveContext);
	}

	public NamedListCalcStream1(Calc1<In, E> calc, String inputName, 
            RecursiveContext context, String outputName)
    {
        this(calc, inputName, context, outputName, Constants.LAST_UNKNOWN);
	}

	public NamedListCalcStream1(Calc1<In, E> calc, String inputName,
            RecursiveContext context)
    {
        this(calc, inputName, context, null, Constants.LAST_UNKNOWN);
	}

	public NamedListCalcStream1() {
	}
	
	@Override
	public void setup() {
		_inputStream = (NumberedStream<In>) _queryCalc.get(_inputName, _context);
        _setupRun = true;
	}

	@Override
	public E invoke(In input) {
		return _calc.invoke(input);
	}

	@Override
	public NumberedStream<In> getInputStream() {
        if (!_setupRun)
            setup();
		return _inputStream;
	}

    public void setCalc(Calc1<In, E> calc) {
        _calc = calc;
    }
}
