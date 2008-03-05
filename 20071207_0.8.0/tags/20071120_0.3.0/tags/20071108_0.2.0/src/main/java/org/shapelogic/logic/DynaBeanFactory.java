package org.shapelogic.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class DynaBeanFactory {
	private static Map<String,Class> _string2class;

	private static Map<String,Class> getString2class() {
		if (_string2class == null) {
		_string2class = new HashMap<String,Class>();
		
		_string2class.put("class", Class.class);
		_string2class.put("date", Date.class);
		_string2class.put("double", Double.class);
		_string2class.put("float", Float.class);
		_string2class.put("integer", Integer.class);
		_string2class.put("long", Long.class);
		_string2class.put("string", String.class);

		_string2class.put("c", Class.class);
		_string2class.put("d", Double.class);
		_string2class.put("f", Float.class);
		_string2class.put("i", Integer.class);
		_string2class.put("l", Long.class);
		_string2class.put("s", String.class);
		}
		return _string2class;
	}
	public static DynaBean getDynaBeanInstance(Map<String,Object> map, DynaClass dynaClass) 
	throws IllegalAccessException, InstantiationException, InvocationTargetException
	{
		if (dynaClass == null)
			return null;
		DynaBean result = dynaClass.newInstance();
		for (Entry<String, Object> entry: map.entrySet())
			BeanUtils.setProperty(result, entry.getKey(), entry.getValue());
		return result;
	}

	public static DynaClass getDynaClassInstance(Map<String,String> map, String nameForClass)
	{
		DynaProperty[] props = new DynaProperty[map.size()];
		getString2class();
		int i = 0;
		for (Entry<String, String> entry: map.entrySet()) {
			String className = entry.getValue();
			Class klass = null;
			if (className != null) {
				klass = _string2class.get(className.trim().toLowerCase());
			if (klass == null) 
				throw new ParseException("className: " + className + " not found", 0); 
			}
			props[i] = new DynaProperty(entry.getKey().trim(), klass);
			i++;
		}
		BasicDynaClass dynaClass = new BasicDynaClass(nameForClass, null, props);
		return dynaClass;
	}
}
