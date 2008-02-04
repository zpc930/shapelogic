package org.shapelogic.imageprocessing;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.shapelogic.entities.NumericRule;
import org.shapelogic.logic.BaseTask;
import org.shapelogic.logic.LetterTaskFactory;
import org.shapelogic.logic.RootTask;
import org.shapelogic.polygon.CLine;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.IPoint2D;
import org.shapelogic.polygon.MultiLinePolygon;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.polygon.PolygonEndPointAdjuster;
import org.shapelogic.util.Constants;


import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

/** Input image needs to be binary, that is gray scale with inverted LUT.
 *  
 * That the background is white, 0, and the foreground is black, 255.
 * 
 * When it handles a point it will mark a point as used and what type it has. 
 * I put the result in a polygon.
 * 
 * How do I know where to start? 
 * I will start taking the pixels until I meet the first black pixel, 
 * and I will work out from there. 
 * 
 * Cycle starting at (1,0) to (1,1) to (0,1) so in what would normally be 
 * counter clockwise, but since the coordinate system is turned upside down it 
 * is clockwise
 * 
 * Terminology:
 * 
 * Current: the short line that is currently handled
 * 
 * Short line: there are 2 pieces, a short line at the end and everything before that
 * 
 * @author Sami Badawi
 *
 */
public abstract class BaseVectorizer implements PlugInFilter, IPixelTypeFinder {

	//Static
	public static final String POLYGON = "polygon";
	public static final String RAW_POLYGON = "rawPolygon";
	public static final int MAX_DISTANCE_BETWEEN_CLUSTER_POINTS = 2;
	public static final byte STRAIGHT_LINE_COLOR = 127; //Draw color

	//Image related
	ByteProcessor _ip;
	protected byte[] _pixels;
	//Dimension of image
	protected int _minX;
	protected int _maxX;
	protected int _minY;
	protected int _maxY;

	//Half static
	/** What you need to add to the the index in the pixels array to get to the indexed point */
	protected int[] _cyclePoints;

	/** last point where you are */
	protected CPointInt _currentPoint;
	protected CPointInt _firstPointInMultiLine;
	protected PixelTypeCalculator _pixelTypeCalculator = new PixelTypeCalculator(); 
	protected ArrayList<CPointInt> _unfinishedPoints = new ArrayList<CPointInt>();
	private Polygon _polygon = null;
	private Polygon _cleanedupPolygon = null;
	/** this is the index into the _pixels where the current point is */
	protected int _currentPixelIndex;

	protected byte _currentDirection = Constants.DIRECTION_NOT_USED;
	
	protected int _numberOfPointsInAllLines = 0;
	protected Object _matchingOH;
	
	protected String _errorMessage;
	
	protected List<Set<IPoint2D> > _endPointsClusters;
	protected int _firstPointInLineIndex = 0;
	protected IPixelTypeFinder _pixelTypeFinder; 
	protected NumericRule[] _rulesArrayForLetterMatching;
	
	/** To be overridden. */
	public boolean isGuiEnabled() {
		return false;
	}

	@Override
	public void run(ImageProcessor ip) {
		init(ip);
		findAllLines();
		if (_currentPoint != null)
			showMessage(
					"Last line point is: " + _currentPoint + "\n" +
					"_numberOfPointsInLine: " + _numberOfPointsInAllLines+ "\n" +
					"Points count: " + getPoints().size()); 
		else
			showMessage("No line point found.");
		drawLines();
		matchLines();
	}

	/** This does really not belong in a vectorizer. */
	protected void matchLines() {
		RootTask rootTask = RootTask.getInstance();
		rootTask.setNamedValue(RAW_POLYGON, getPolygon());
		PolygonEndPointAdjuster adjuster = new PolygonEndPointAdjuster(getPolygon());
		_cleanedupPolygon = adjuster.getValue();
		_cleanedupPolygon = _cleanedupPolygon.improve(); 
		rootTask.setNamedValue(POLYGON, _cleanedupPolygon);
		List<NumericRule> rulesList = Arrays.asList(_rulesArrayForLetterMatching);
		BaseTask letterTask = LetterTaskFactory.createLetterTasksFromRule(rootTask, rulesList, null);
		_matchingOH = letterTask.invoke();
		if (_matchingOH == null) {
			System.out.println("\n\nLetter matched failed for this:\n" + _cleanedupPolygon);
		}
		showMessage("Letter match result: " + _matchingOH);
	}

	protected void findAllLines() {
		findFirstLinePoint();

		while (_unfinishedPoints.size() > 0) {
			//Find whole line
			findMultiLine();
		}
	}
	
	abstract protected void findMultiLine();

	abstract protected byte handleProblematicPoints();

	/** Not background and the used bit set to 0 */
	public boolean isPixelUsed(int pixelIndex) {
		byte pixel = _pixels[pixelIndex];
		return PixelType.isUsed(pixel);
	}

	public PixelTypeCalculator findPointType(int pixelIndex, PixelTypeCalculator reusedPixelTypeCalculator) {
		return _pixelTypeFinder.findPointType(pixelIndex, reusedPixelTypeCalculator);
	}
	
	public static byte oppesiteDirection(byte direction){
		byte newDirection = (byte) (direction + Constants.DIRECTIONS_AROUND_POINT/2); 
		return (byte) (newDirection % Constants.DIRECTIONS_AROUND_POINT);
	}
	
	protected void moveCurrentPointForwards(byte newDirection) {
		byte startPixelValue = _pixels[_currentPixelIndex];
		_pixels[_currentPixelIndex] = PixelType.toUsed(startPixelValue);
		_currentPixelIndex += _cyclePoints[newDirection];
		_currentPoint.x += Constants.CYCLE_POINTS_X[newDirection];
		_currentPoint.y += Constants.CYCLE_POINTS_Y[newDirection];
		_currentDirection = newDirection;
	}
	
	abstract protected boolean lastPixelOk(byte newDirection);

	/** Cannot handle the last pixel at the edge, so for now just ignore it. */
	protected void init(ImageProcessor ip) {
		_ip = (ByteProcessor) ip;
		Rectangle r = _ip.getRoi();
		_pixels = (byte[]) _ip.getPixels();
		int width = _ip.getWidth();
		//
		_cyclePoints = new int[] {1, 1 + width, width, -1 + width, -1, -1 - width, -width, 1 - width};
		
		if (r == null) { 
			_minX = 1;
			_maxX = _ip.getWidth()-2;
			_minY = 1;
			_maxY = _ip.getHeight()-2;
		}
		else {
			_minX = Math.max(1, r.x);
			_maxX = Math.min(_ip.getWidth()-2,r.x + r.width -1);
			_minY = Math.max(1, r.y);
			_maxY = Math.min(_ip.getHeight()-2,r.y + r.height -1);
		}
		internalFactory(); 
	}

	/** All the objects that needs special version should be created here. */
	abstract protected void internalFactory();

	public BaseVectorizer() {
		super();
	}

	public int setup(String arg, ImagePlus imp) {
		if (arg.equals("about"))
			{showAbout(); return DONE;}
		return DOES_8G+SUPPORTS_MASKING;
	}

	void showAbout() {
		showMessage("About LineVectorizer...",
			"Works for 8 bit Gray\n" +
			"works with rectangular ROIs\n"
		);
	}

	public int pointToPixelIndex(int x, int y) {
		return _ip.getWidth() * y + x;
	}

	public int pointToPixelIndex(IPoint2D point) {
		return _ip.getWidth() * (int)point.getY() + (int)point.getX();
	}

	public CPointInt pixelIndexToPoint(int pixelIndex) {
		int y = pixelIndex / _ip.getWidth();
		int x = pixelIndex % _ip.getWidth();
		return new CPointInt(x,y);
	}

	protected boolean findFirstLinePoint() {
		for (int iY = _minY; iY <= _maxY; iY++) {
			int lineOffset = _ip.getWidth() * iY; 
			for (int iX = _minX; iX <= _maxX; iX++) {
				_currentPixelIndex = lineOffset + iX;
				if (PixelType.PIXEL_FOREGROUND_UNKNOWN.color == _pixels[_currentPixelIndex]) {
					_currentPoint = new CPointInt(iX,iY);
					addToUnfinishedPoints((CPointInt) _currentPoint.copy());
					return true;
				}
			}
		}
		return false;
	}

	/** A normal line has a crossing index of 4.
	 */
	public int countRegionCrossingsAroundPoint(int pixelIndex) {
		int countRegionCrossings = 0;
		boolean isBackground;
		boolean wasBackground = PixelType.BACKGROUND_POINT.color == _pixels[pixelIndex + _cyclePoints[Constants.DIRECTIONS_AROUND_POINT-1]];
		for (int i=0; i < Constants.DIRECTIONS_AROUND_POINT; i++) {
			isBackground = PixelType.BACKGROUND_POINT.color == _pixels[pixelIndex + _cyclePoints[i]];
			if (wasBackground != isBackground) {
				countRegionCrossings++;
			}
			wasBackground = isBackground;
		}
		return countRegionCrossings;
	}

	/** To be overridden. If I want to do more matching at the end. */
	protected void findMultiLinePostProcess() {
		_pixels[_currentPixelIndex] = PixelType.toUsed(_pixels[_currentPixelIndex]); //Last point
		getPolygon().endMultiLine();
	}

	protected boolean findMultiLinePreProcess() {
		_currentDirection = Constants.DIRECTION_NOT_USED;
		getPolygon().startMultiLine();
		_currentPoint = _unfinishedPoints.get(_unfinishedPoints.size()-1);
		_currentPoint = (CPointInt)_currentPoint.copy();
		_firstPointInMultiLine = (CPointInt) _currentPoint.copy();
		_currentPixelIndex = pointToPixelIndex(_currentPoint);
		findPointType(_currentPixelIndex , _pixelTypeCalculator);
		boolean firstPointDone = (_pixelTypeCalculator.unusedNeighbors == 0); //Take first step so you can set the first point to unused
		if (firstPointDone) {
			_unfinishedPoints.remove(_currentPoint);
			return false;
		}
		else {
			return true;
		}
	}
	
	protected void addToUnfinishedPoints(CPointInt newPoint) {
		if (_unfinishedPoints.indexOf(newPoint) == -1)
			_unfinishedPoints.add(newPoint);
	}
	
	/** Draws the vectorized lines on the original image for visual inspection.
	 * 
	 * This is probably not needed in the final version of this class
	 */
	protected void drawLines() {
			_ip.setColor(STRAIGHT_LINE_COLOR);
			if (getPoints().size() == 0)
				return;
			for (CLine line: getPolygon().getLines()) {
				drawLine(line);
			}
		}

	public void showMessage(String text) {
		if (isGuiEnabled())
			IJ.showMessage(text);
	}

	public void showMessage(String title, String text) {
		if (isGuiEnabled())
			IJ.showMessage(title,text);
	}

	public Collection<IPoint2D> getPoints() {
		getPolygon().getValue();
		return getPolygon().getPoints();
	}

	protected void drawLine(CLine line) {
		_ip.drawLine((int)line.getStart().getX(), (int)line.getStart().getY(),
				(int)line.getEnd().getX(), (int)line.getEnd().getY());
	
	}
	
	protected Polygon polygonFactory() {
		return new MultiLinePolygon();
	}
	

	public Polygon getPolygon() {
		if (_polygon == null)
			_polygon = polygonFactory(); 
		return _polygon;
	}

	public Object getMatchingOH() {
		return _matchingOH;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public Polygon getCleanedupPolygon() {
		return _cleanedupPolygon;
	}

	@Override
	public int[] getCyclePoints() {
		return _cyclePoints;
	}

	@Override
	public int getMaxX() {
		return _maxX;
	}

	@Override
	public int getMaxY() {
		return _maxY;
	}

	@Override
	public int getMinX() {
		return _minX;
	}

	@Override
	public int getMinY() {
		return _minY;
	}

	@Override
	public byte[] getPixels() {
		return _pixels;
	}

}