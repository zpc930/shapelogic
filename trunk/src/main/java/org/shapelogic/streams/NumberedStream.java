package org.shapelogic.streams;

/** NumberedStream is a Sequential Stream where each element has an intrinsic number.
 * 
 * @author Sami Badawi
 *
 */
public interface NumberedStream<E> extends Stream<E> {

	/** Get the calculate value for index based on the previous stream. <br />
     * <br />
	 * So it just calls 
	 * invoke(getInputStream().get(index), index) 
	 * 
	 * @param index in
	 * @return
	 */
	E get(int input);
	
	/** Index of last successfully processed element. */
	int getIndex();
	
	/** Last possible element. <br />
     *
     * If last is not known then this is set to LAST_UNKNOWN.<br />
     * When you add a new element this will not grow.<br />
     * When maxLast is set so it this, but this can can get lower.<br />
     * Set when you find the end.<br />
     * <br />
     * If you use this for iteration you need to call the function at each
     * iteration.
     */
	int getLast();

	/** Manually set max value for last possible element. */
	int getMaxLast();

	/** Set a max value for last possible element. */
	void setMaxLast(int maxLast);
}