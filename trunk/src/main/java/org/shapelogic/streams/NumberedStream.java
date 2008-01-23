package org.shapelogic.streams;

/** NumberedStream is a Sequential Stream where each element has an intrinsic number.
 * 
 * @author Sami Badawi
 *
 */
public interface NumberedStream<E> extends Stream<E> {

	/** Get the calculate value for index based on the previous stream
	 * So it just calls 
	 * invoke(getInputStream().get(index), index) 
	 * 
	 * @param index in
	 * @return
	 */
	E get(int input);
	
	/** Index of last successfully processed element. */
	int getIndex();
	
	/** Last possible element. */
	int getLast();

	/** Set a max value for last possible element. */
	int getMaxLast();

	/** Set a max value for last possible element. */
	void setMaxLast(int maxLast);
}