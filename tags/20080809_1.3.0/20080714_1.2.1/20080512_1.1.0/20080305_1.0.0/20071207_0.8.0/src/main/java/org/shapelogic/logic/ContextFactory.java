package org.shapelogic.logic;

import org.apache.commons.jexl.JexlContext;
/**   
 * 
 * @author Sami Badawi
 *
 * The idea is that based on a context and a name you should be able to get any value back 
 * Where should they come from. Anything that is calculate already is not a problem.
 * 
 * What are the keys?
 * Maybe a class, name pair
 * name
 * 
 * How is the different from a normal context?
 * A normal context is mainly a map, and that is already in the JEXL context.
 * 
 * If something is not there what should be done? 
 * This could be the place where it will try to create missing objects.
 * 
 * How should that be done?
 * I could have a Guice module.
 * 
 * */
public class ContextFactory {

	public static Object getFromContext(JexlContext context, Object key) {
		Object result = null;
		if (context == null)
			return result;
		result = context.getVars().get(key);
		return result;
	}
}
