package org.shapelogic.imageprocessing;

import org.shapelogic.streams.ListStream;

/** The idea is that you can call a plugin in both active and lazy mode.
 * <br />
 * 
 * How should this be instantiated?<br />
 * <br />
 * There should be a constructor where Imageprocessor is set.<br />
 * 
 * @author Sami Badawi
 *
 */
public interface LazyPlugInFilter<E> 
{
	
	/** Used when calling in lazy mode. */
	ListStream<E> getStream();
	  
	/** Maybe getStreamName would be better. */
	String getStreamName();

	/** Maybe setStreamName would be better. */
	void setStreamName(String name);

}
