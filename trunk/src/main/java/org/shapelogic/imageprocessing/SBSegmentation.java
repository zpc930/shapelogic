package org.shapelogic.imageprocessing;

import java.util.ArrayList;
import java.awt.Rectangle;

import org.shapelogic.color.IColorAndVariance;
import org.shapelogic.color.ValueAreaFactory;
import org.shapelogic.imageutil.SLImage;

/** High level class for segmentation.
 * <br />
 * Works with both color and gray scale.
 * 
 * @author Sami Badawi
 *
 */
public class SBSegmentation {
	private SLImage _slImage;
	private ArrayList<SBPendingVertical> _vPV;
	private SBPixelCompare _pixelCompare;
	
	protected ValueAreaFactory _segmentAreaFactory;
	protected IColorAndVariance _currentSegmentArea; 

	/** Dimensions of ROI. */
	private int _min_x;
	private int _max_x;
	private int _min_y;
	private int _max_y;
	
	private String _status = "";
	
	public SBSegmentation() {
		_vPV = new ArrayList<SBPendingVertical>();
	}
	
	/** Conviniens method to get the offset from the start of the image
	 * array to the first pixel of a line, at the edge of the image
	 * not the edge of to ROI.
	 * 
	 * @param y
	 * @return
	 */
	private int offsetToLineStart(int y)
	{
		int width = _slImage.getWidth();
		int offset = y * width;		
		return offset;
	}

	int pointToIndex(int x, int y){
		return _slImage.getLineStride() * y + x;
	}
	
	/** Given a point find the longest line vertical line similar to the chosen colors. 
	 * <br />
	 * If the start point does not match return null.<br /> 
	 * 
	 * @param x
	 * @param y
	 * @return this does not contain any up or down information
	 */
	private SBPendingVertical expandSBPendingVertical(SBPendingVertical lineIn)
	{
		int offset = offsetToLineStart(lineIn.y);
		if (!_pixelCompare.newSimilar(offset + lineIn.xMin) ||
			!_pixelCompare.newSimilar(offset + lineIn.xMax))
			return lineIn; // this should never happen
		int i_low;
		for (i_low = lineIn.xMin-1; _min_x <= i_low; i_low--) {
			if (!_pixelCompare.newSimilar(offset + i_low)) {
				i_low++;
				break;
			}
		}
		int i_high;
		for (i_high = lineIn.xMax+1; _max_x >= i_high; i_high++) {
			if (!_pixelCompare.newSimilar(offset + i_high)) {
				i_high--;
				break;
			}
		}
		int x1 = Math.max(_min_x,i_low);
		int x2 = Math.min(_max_x,i_high);
		SBPendingVertical newLine = new SBPendingVertical(x1,x2,lineIn.y, lineIn.isSearchUp());
		return newLine;
	}

	public void segmentAll()
	{
		for (int x=_min_x;x<=_max_x;x++) {
			for (int y=_min_y;y<=_max_y;y++) {
				if (!_pixelCompare.isHandled(pointToIndex(x, y))) {
					_pixelCompare.grabColorFromPixel(x, y);
					segment(x, y);
				}
			}
		}
	}
	
    /**
     * 
     * @param color
     */
	public void segmentAll(int color)
	{
        _pixelCompare.setCurrentColor(color);
		for (int y=_min_y;y<=_max_y;y++) {
    		for (int x=_min_x;x<=_max_x;x++) {
                int index = pointToIndex(x, y);
				if (!_pixelCompare.isHandled(index) &&
                        _pixelCompare.similar(index)) {
					segment(x, y);
				}
			}
		}
	}
	
	/** Start segmentation by selecting a point
	 * 
	 * Use the color of that point at your goal color
	 * 
	 * @param x
	 * @param y
	 */
	public void segment(int x, int y)
	{
		int index = pointToIndex(x,y);
		if (_segmentAreaFactory != null)
			_currentSegmentArea = _segmentAreaFactory.makePixelArea(x,y, _pixelCompare.getColorAsInt(index));
		if (!_pixelCompare.newSimilar(index)){
			_status = "First pixel did not match. Segmentation is empty.";
			return;
		}
		SBPendingVertical firstLine = expandSBPendingVertical(new SBPendingVertical(x,y));
		if (firstLine == null)
			return;
		storeLine(firstLine);
		storeLine(SBPendingVertical.opposite(firstLine));
		final int maxIterations = 1000 + _slImage.getPixelCount()/10;
		int i;
		for (i =1; i <= maxIterations; i++) {
			if (_vPV.size() == 0) 
				break;
			Object obj = _vPV.remove(_vPV.size()-1);
			SBPendingVertical curLine = (SBPendingVertical) obj;
			fullLineTreatment(curLine);
		}
		_pixelCompare.getNumberOfPixels();
	}
	
	
	/** line is at the edge of image and pointing away from the center	 */
	public void init()
	{
		Rectangle r = _slImage.getRoi();
		
		if (r == null) {
			_min_x = 0;
			_max_x = _slImage.getWidth()-1;
			_min_y = 0;
			_max_y = _slImage.getHeight()-1;
		}
		else {
			_min_x = r.x;
			_max_x = r.x + r.width -1;
			_min_y = r.y;
			_max_y = r.y + r.height -1;
		}
	}

	/** line is at the edge of image and pointing away from the center	 */
	boolean atEdge(SBPendingVertical curLine)
	{
		if (curLine.y == _max_y && curLine.isSearchUp())
			return true;
		if (curLine.y == _min_y && !curLine.isSearchUp())
			return true;
		return false;
	}

	boolean isExpandable(SBPendingVertical curLine)
	{
		int offset = offsetToLineStart(curLine.y);		
		if (_min_x <= curLine.xMin-1) {
			int indexLeft = offset + curLine.xMin-1;
			if (_pixelCompare.newSimilar(indexLeft)) {
				return true;
			}
		}
		if (_max_x >= curLine.xMax+1) {
			int indexRight = offset + curLine.xMax+1;
			if (_pixelCompare.newSimilar(indexRight)) {
				return true;
			}
		}
		return false;
	}
	
	/** If the whole line is handled */
	boolean isHandled(SBPendingVertical curLine)
	{
		int offset = offsetToLineStart(curLine.y);		
		for (int i = curLine.xMin; i <= curLine.xMax; i++) {
			if (!_pixelCompare.isHandled(offset + i)) {
				return false;
			}
		}
		return true;
	}
	
	/** Call action on the line itself and then setHandled, so it will not 
	 * be run again.
	 * 
	 * @param curLine, containing the current line, that is already found
	 */
	void handleLine(SBPendingVertical curLine)
	{
		int offset = offsetToLineStart(curLine.y);
		int y = curLine.y;
		for (int i = curLine.xMin; i <= curLine.xMax; i++) {
			if (_pixelCompare.isHandled(offset + i))
				continue;
			if (!_pixelCompare.isHandled(offset + i)) {
				_pixelCompare.action(offset + i);
				_pixelCompare.setHandled(offset + i);
				if (_currentSegmentArea != null)
					_currentSegmentArea.putPixel(i,y,_pixelCompare.getColorAsInt(offset + i));
			}
		}
	}

	/** After handling a line continue in the same direction.
	 * 
	 * @param curLine
	 */
	void handleNextLine(SBPendingVertical curLine)
	{
		if (atEdge(curLine))
			return;
		boolean insideSimilar = false;
		int lowX=0;
		int direction = -1; //down
		if (curLine.isSearchUp())
			direction = 1;
		int yNew = curLine.y+direction;
		if (!(_min_y <= yNew && yNew <= _max_y))
			return;
		int offset = offsetToLineStart(yNew);
		for (int i = curLine.xMin; i <= curLine.xMax; i++) {
			boolean curSimilar = _pixelCompare.newSimilar(offset + i);
			if (!insideSimilar && curSimilar) { //enter
				lowX = i;
				insideSimilar = true;
			}
			else if (insideSimilar && !curSimilar) { //leave
				SBPendingVertical newLine = new SBPendingVertical(lowX,i-1,yNew,
						curLine.isSearchUp());
				checkLine(newLine);
				storeLine(newLine);
				insideSimilar = false;
			}
		}
		if (insideSimilar) {
			SBPendingVertical newLine = new SBPendingVertical(lowX,curLine.xMax,yNew,
					curLine.isSearchUp());
			storeLine(newLine);
		}
	}

	void fullLineTreatment(SBPendingVertical curLine)
	{
		if (curLine == null)
			return;
		if (isExpandable(curLine)) {
			SBPendingVertical expanded = expandSBPendingVertical(curLine);
			//check that new line is still good
			if (!checkLine(expanded)) {
				expanded = expandSBPendingVertical(curLine);
			}
			curLine = expanded;
			storeLine(SBPendingVertical.opposite(expanded));
		}
		handleLine(curLine);
		try {
			handleNextLine(curLine);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * @param ip The ip to set.
	 */
	public void setSLImage(SLImage ip) {
		this._slImage = ip;
	}

	public SLImage getSLImage() {
		return _slImage;
	}
	
	/**
	 * @param pixelCompare The pixelCompare to set.
	 */
	public void setPixelCompare(SBPixelCompare pixelCompare) {
		this._pixelCompare = pixelCompare;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		if (_status == null || "".equals(_status) ) 
			_status = findStatus();
		return _status;
	}
	
	public String findStatus() {
		String status = "";
		if (_segmentAreaFactory != null) {
			int areas = _segmentAreaFactory.getStore().size();
			status += "Numbers of areas = " + areas;
			status += "\nPixels per area = " + _slImage.getPixelCount() / areas; 
		}
		return status;
	} 

	/** Make sure that every point on curLine is similar the the chosen color */
	boolean checkLine(SBPendingVertical curLine)
	{
		int offset = offsetToLineStart(curLine.y);
		boolean debugStop = false;
		for (int i = curLine.xMin; i <= curLine.xMax; i++) {
			if (_pixelCompare.similar(offset + i))
				continue;
			else {
				boolean handledBefore = _pixelCompare.similar(offset + i);
				debugStop = true;
			}
		}
		return ! debugStop;
	}
	
	void storeLine(SBPendingVertical curLine){
		if (!checkLine(curLine))
			checkLine(curLine);
		_vPV.add(curLine);
	}

	public void setSegmentAreaFactory(ValueAreaFactory areaFactory) {
		_segmentAreaFactory = areaFactory;
	}

	public ValueAreaFactory getSegmentAreaFactory() {
		return _segmentAreaFactory;
	}
}
