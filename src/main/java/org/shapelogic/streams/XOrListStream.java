package org.shapelogic.streams;

import java.util.ArrayList;
import java.util.List;

import org.shapelogic.util.Constants;

/** AndListStream takes a list of Boolean streams and create the and of them.
 * 
 * @author Sami Badawi
 *
 */
public class XOrListStream extends BaseListStreamList<Boolean,String> {
	
	/** Parallel to the NumberedStream. */
	protected List<String> _ohNames;
	
	/** Use the ohName to also be the name of the input stream. <br />
	 * 
	 * @param ohNames
	 * @param maxLast
	 */
	public XOrListStream(List<String> ohNames, int maxLast) {
		super(null,maxLast);
		_ohNames = ohNames;
		_inputStream = new ArrayList(); 
		for (String streamName: _ohNames) {
			NumberedStream numberedStream = new NamedNumberedStream0(streamName);
			getInputStream().add(numberedStream);
		}
	}
	
	public XOrListStream(List<String> ohNames) {
		this(ohNames,Constants.LAST_UNKNOWN);
	}
	
	public XOrListStream(List<String> ohNames, List<NumberedStream<Boolean> > inputStream, int maxLast) {
		super(inputStream, maxLast);
		_ohNames = ohNames;
	}
	
	public XOrListStream(List<String> ohNames, List<NumberedStream<Boolean> > inputStream) {
		this(ohNames,inputStream,Constants.LAST_UNKNOWN);
	}
	
	@Override
	public String invoke(List<Boolean> input) {
		int i = 0;
		String foundName = null;
		int found = 0;
		for (Boolean bool : input) {
			if (bool) {
				found++;
				if (1 < found )
					return Constants.NO_OH;
				else
					foundName = _ohNames.get(i);
			}
			i++;
		}
		if (1 == found)
			return foundName;
		return Constants.NO_OH;
	}

}
