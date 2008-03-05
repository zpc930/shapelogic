package org.shapelogic.logic;

import org.shapelogic.imageprocessing.LineType;
import org.shapelogic.imageprocessing.PointType;
import org.shapelogic.polygon.MultiLinePolygon;
import org.shapelogic.polygon.Polygon;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

/** This the root object the root context and the root task.
 * 
 *  This is a singleton.
 *  
 *  I think that this should only have one child Task.
 * 
 * @author Sami Badawi
 *
 */
public class RootTask extends BaseTask {
	private static RootTask _instance;
	private static Module _module;
	
	private RootTask(Module module) {
		super(null,true);
		_localModule = module;
	}

	public static RootTask getInstance() {
		if (_instance == null) {
//			if (_module == null) {
//				_module = new AbstractModule() {
//
//					@Override
//					protected void configure() {
//						
//					}
//				};
//			}
			_instance = new RootTask(_module);
			_instance.setAllDefaultClassInContext();
		}
		return _instance;
	}
	
	private void setAllDefaultClassInContext() {
		Class[] classes = new Class[] {
			Boolean.class,
			Double.class,
			Integer.class,
			String.class,
			PointType.class,
			LineType.class,
			Polygon.class,
			MultiLinePolygon.class,
		};
		setClassInContext(classes);
	}
}
