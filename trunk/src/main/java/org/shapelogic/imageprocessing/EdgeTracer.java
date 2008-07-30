package org.shapelogic.imageprocessing;

import static org.shapelogic.util.Constants.DOWN;
import static org.shapelogic.util.Constants.LEFT;
import static org.shapelogic.util.Constants.RIGHT;
import static org.shapelogic.util.Constants.UP;

import org.shapelogic.color.ColorFactory;
import org.shapelogic.color.IColorDistanceWithImage;
import org.shapelogic.imageutil.SLImage;
import org.shapelogic.polygon.CPointInt;
import org.shapelogic.polygon.Polygon;
import org.shapelogic.util.Constants;

/** Edge Tracer. <br />
 * 
 * The first version is based on Wand from ImageJ 1.38.<br />
 * 
 * It traces with a 2 x 2 square that put the top left pixels inside the 
 * particle and the bottom right outside.<br />
 * 
 * Might be replaced with a version that has all the pixels inside.<br />
 * 
 * @author Sami Badawi
 *
 */
public class EdgeTracer implements IEdgeTracer {
	
	private IColorDistanceWithImage _colorDistanceWithImage; 
	private int width, height;
	private double _maxDistance;
	private boolean _traceCloseToColor;

	/** Constructs a Wand object from an ImageProcessor. */
	public EdgeTracer(SLImage image, int referenceColor, double maxDistance, boolean traceCloseToColor) {
		_colorDistanceWithImage = ColorFactory.makeColorDistanceWithImage(image);
		_colorDistanceWithImage.setReferenceColor(referenceColor);
		_maxDistance = maxDistance;
		_traceCloseToColor = traceCloseToColor;
		width = image.getWidth();
		height = image.getHeight();
	}
	
	/** Use XOR to either handle colors close to reference color or far away. */
	private boolean inside(int x, int y) {
		if (x < 0 || y < 0)
			return false;
		if (width <= x || height <= y)
			return false;
		return _traceCloseToColor ^ (_maxDistance < _colorDistanceWithImage.distanceToReferenceColor(x, y));
	}

	/** Traces the boundary of an area of uniform color, where
		'startX' and 'startY' are somewhere inside the area. 
		A 16 entry lookup table is used to determine the
		direction at each step of the tracing process. */
	public Polygon autoOutline(int startX, int startY) {
		int x = startX;
		int y = startY;
		//Find top point inside
		do {
			y--;
		} while (inside(x,y));
		y++;
		//Find leftmost top point inside
		do {
			x--;
		} while (inside(x,y));
		x++;
		return traceEdge(x, y, 2);
	}
	
	int nextDirection(int x, int y, int lastDirection) {
		boolean[] directions = makeDirections(x, y);
		int lastDirectionReleativeCurrent = lastDirection/2+ Constants.DIRECTIONS_4_AROUND_POINT/2;
		for (int i=1; i <= Constants.DIRECTIONS_4_AROUND_POINT; i++) {
			int real_direction = (lastDirectionReleativeCurrent + i) % Constants.DIRECTIONS_4_AROUND_POINT;
			//Return first point that is inside
			if (directions[real_direction])
				return real_direction*2;
		}
		return -1; //Not found
	}

	private boolean[] makeDirections(int x, int y) {
		boolean L = inside(x-1, y);	// upper left
		boolean D = inside(x, y+1);	// lower left
		boolean R = inside(x+1, y);		// lower right
		boolean U = inside(x, y-1);	// upper right
		boolean[] directions = {L,D,R,U};
		return directions;
	}
		
	Polygon traceEdge(int xstart, int ystart, int startingDirection) {
		Polygon polygon = new Polygon();
		polygon.startMultiLine();
		ChainCodeHandler chainCodeHandler = new ChainCodeHandler(polygon.getAnnotatedShape());
		chainCodeHandler = new ChainCodeHandler(polygon.getAnnotatedShape());
		chainCodeHandler.setup();
		chainCodeHandler.setMultiLine(polygon.getCurrentMultiLine());
		chainCodeHandler.setFirstPoint(new CPointInt(xstart,ystart));
		int x = xstart;
		int y = ystart;
		int direction = startingDirection;

		int count = 0;
		do {
			count++;
			direction = nextDirection(x,y,direction);
			if (-1 == direction)
				break;
			switch (direction) {
				case UP:
					y = y-1;
					break;
				case DOWN:
					y = y + 1;
					break;
				case LEFT:
					x = x-1;
					break;
				case RIGHT:
					x = x + 1;
					break;
			}
			//If the chain becomes too long just give up
			if (!chainCodeHandler.addChainCode((byte)direction))
				break;
		} while ((x!=xstart || y!=ystart));
		//Original clause causes termination problems
//		} while ((x!=xstart || y!=ystart || direction!=startingDirection));
		chainCodeHandler.getValue();
		polygon.setPerimeter(chainCodeHandler.getLastChain()+1);
		polygon.getValue();
		polygon.getBBox().add(chainCodeHandler._bBox);
		return polygon;
	}
}
