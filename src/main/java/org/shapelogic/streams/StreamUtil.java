package org.shapelogic.streams;


/** Class with utility methods for Stream.
 * 
 * @author Sami Badawi
 *
 */
public class StreamUtil {
	
	/** Find the size of a NumberedStream.
	 */
	static public int size(NumberedStream numberedStream) {
		if (numberedStream.isDirty()) {
			while (numberedStream.hasNext())
				numberedStream.next();
		}
		return numberedStream.getLast() + 1;
	}  
	
}
