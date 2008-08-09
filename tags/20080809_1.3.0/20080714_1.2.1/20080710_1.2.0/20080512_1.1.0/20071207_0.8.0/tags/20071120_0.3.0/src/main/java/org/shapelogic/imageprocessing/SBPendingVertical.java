package org.shapelogic.imageprocessing;
/**
 * @author Sami Badawi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
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
	/**
	 * @return Returns the lookUp.
	 */
	public boolean isSearchUp() {
		return searchUp;
	}
	/**
	 * @param lookUp The lookUp to set.
	 */
	public void setSearchUp(boolean searchUp) {
		this.searchUp = searchUp;
	}
}
