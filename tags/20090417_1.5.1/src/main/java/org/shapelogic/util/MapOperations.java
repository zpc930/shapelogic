package org.shapelogic.util;

import java.util.Map;

import org.shapelogic.polygon.IPoint2D;

/** Map Operations utility class.<br />
 * 
 * XXX this should not contain references to any other packages.
 * 
 * @author Sami Badawi
 *
 */
public class MapOperations {
    /** Get from map with Default. Where default == input key. 
     * 
     * XXX IPoint2D is from another package.
     */ 
    public static IPoint2D getPointWithDefault(Map<IPoint2D, IPoint2D> map, IPoint2D point) {
    	IPoint2D result = map.get(point);
    	if (result == null)
    		result = point;
    	return result;
    }
}
