package org.shapelogic.polygon;

/** ShapeAnnotator takes a shape in and annotate it, without changing the version
 * 
 * @author Sami Badawi
 *
 * @param <S> for Shape, but this could be anything
 */
public interface ObjectAnnotator<S> extends Improver<S> {
	
	/** Try if there is anything that can be annotated.
	 * return true if there is.
	 */
	boolean isAnnotationsFound();
}
