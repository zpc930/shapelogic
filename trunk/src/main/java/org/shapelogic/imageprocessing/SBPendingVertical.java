package org.shapelogic.imageprocessing;

/** Contains information about one line that needs to be checked.
 *
 * @author Sami Badawi
 *
 */
public class SBPendingVertical implements Cloneable
{
	final int xMin, xMax, y;
	private boolean searchUp;
	
	SBPendingVertical(int xMinIn, int xMaxIn, int yIn, boolean searchUpIn){
		xMin = xMinIn;
		xMax = xMaxIn;
		y = yIn;
		searchUp = searchUpIn;
	}

	SBPendingVertical(int xMinIn, int xMaxIn, int yIn){
		xMin = xMinIn;
		xMax = xMaxIn;
		y = yIn;
	}

	SBPendingVertical(int xMinIn, int yIn){
		xMin = xMinIn;
		xMax = xMinIn;
		y = yIn;
	}

	SBPendingVertical(SBPendingVertical pVIn){
		xMin = pVIn.xMin;
		xMax = pVIn.xMax;
		y = pVIn.y;
		searchUp = pVIn.searchUp;		
	}
	
	static SBPendingVertical opposite(SBPendingVertical inLine)
	{
		if (inLine == null)
			return null;
		SBPendingVertical outLine = new SBPendingVertical(inLine);
		outLine.setSearchUp(!inLine.isSearchUp());
		return outLine;
	}
	/** Whether the line should be compared to the line that are up or down.
	 * 
	 * @return Returns the lookUp.
	 */
	public boolean isSearchUp() {
		return searchUp;
	}
	/** Whether the line should be compared to the line that are up or down.
	 * 
	 * @param searchUp The searchUp to set.
	 */
	public void setSearchUp(boolean searchUp) {
		this.searchUp = searchUp;
	}
}
