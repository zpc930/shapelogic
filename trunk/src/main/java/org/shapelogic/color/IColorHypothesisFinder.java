package org.shapelogic.color;

/** Interface for finding a ColorHypothesis in an image.<br /> 
 * 
 * Maybe this should be changed to a stream later.<br />
 * 
 * Do I need to know if this is a color or a gray image?<br />
 * 
 * I do not think so.<br />
 * 
 * @author Sami Badawi
 *
 */
public interface IColorHypothesisFinder {
	
	ColorHypothesis findBestColorHypothesis();
	ColorHypothesis colorHypothesisIteration(ColorHypothesis lastColorHypothesis);
	ColorHypothesis getColorHypothesis();
	int getMaxIterations();
    int getIteration();
    void setMaxIterations(int maxIterations);
    boolean verifyColor(IColorRange colorRange);
}
