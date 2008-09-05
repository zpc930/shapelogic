package org.shapelogic.polygon;

import org.shapelogic.logic.LazyCalc;

/** ShapeImprover takes a geometric object and make an improved version.
 * This does not have to be a new object, only if there are big changes
 * 
 * Is there any reason why this need to be a shape?
 * No I think maybe I will stick with the name for now.
 * 
 * So this is also going to be the base interface for the annotation interface
 * 
 * @author Sami Badawi
 *
 */
public interface Improver<S> extends LazyCalc<S> {
	
	public S getInput();
	public void setInput(S input);
	public boolean createdNewVersion();
}
