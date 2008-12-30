package org.shapelogic.streams;

import java.util.List;

import org.shapelogic.calculation.Calc1;
import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.util.Constants;

/** ConfidenceArrayListStream takes a ListStream of double[] and creates a
 * ListStream of String.<br />
 *
 * Translates a double[] that could come from a neural network to either the
 * number of the one that is winning walue if any is or to a name for that.<br />
 *
 * @author Sami Badawi
 *
 */
public class ConfidenceArrayListStream extends NamedListCalcStream1<double[], String>
    implements RecursiveContext, Calc1<double[], String>
{
	
	/** Parallel to the NumberedStream. */
	protected List<String> _ohNames;

	protected double _limit = 0.5;
	/** Use the ohName to also be the name of the input stream. <br />
	 * 
	 * @param ohNames
	 * @param maxLast
	 */
	public ConfidenceArrayListStream(List<String> ohNames, String inputName,
            RecursiveContext recursiveContext, String outputName, int maxLast) {
		super(null, inputName, recursiveContext, outputName, maxLast);
		_ohNames = ohNames;
        setCalc(this);
	}

    @Override
	public String invoke(double[] input) {
        int countOK = 0;
        int lastFound = -1;
        for (int i = 0; i < input.length; i++) {
            if (_limit < input[i]) {
                countOK++;
                lastFound = i;
            }
        }
        if (countOK == 1) {
            if (_ohNames != null && lastFound < _ohNames.size() )
                return _ohNames.get(lastFound);
            else
                return "" + lastFound;
        }
        return Constants.NO_OH;
    }

    public void setOhNames(List<String> ohNames) {
        _ohNames = ohNames;
    }

    public List<String> getOhNames() {
        return _ohNames;
    }
}
