package org.shapelogic.streams;

import java.util.List;

import org.shapelogic.calculation.RecursiveContext;
import org.shapelogic.machinelearning.ConfidenceArraySelector;

/** ConfidenceArrayListStream takes a ListStream of double[] and creates a
 * ListStream of String.<br />
 *
 * Translates a double[] that could come from a neural network to either the
 * number of the one that is winning walue if any is or to a name for that.<br />
 * <br />
 * This should not be needed take out before ShapeLogic 1.5 is released.
 *
 * @author Sami Badawi
 *
 */
@Deprecated
public class ConfidenceArrayListStream extends NamedListCalcStream1<double[], String>
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
		super(new ConfidenceArraySelector(ohNames), inputName, recursiveContext, outputName, maxLast);
		_ohNames = ohNames;
	}

    public void setOhNames(List<String> ohNames) {
        _calc = new ConfidenceArraySelector(ohNames);
        _ohNames = ohNames;
    }

    public List<String> getOhNames() {
        return _ohNames;
    }
}
