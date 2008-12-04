package org.shapelogic.util;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;

/** IPaser that is using the BeanUtils for setting JavaBeans.<br />
 * 
 * The current solution is not elegant. I would have thougth that there should
 * be a simpler way to parse a property file style string and set it in a 
 * JavaBean.<br />
 * 
 * The reason that I am not using Antlr is that I do not what to create 
 * dependency on it, since it is using so many other libraries.
 * 
 * Not sure if this should be in util package when it it using Commons BeanUtils.<br />
 * Maybe if the jar file is missing it will just fail.<br />
 * Otherwise there would need to be special packages for dependencies of other 
 * libraries.<br />
 * 
 * XXX: This will not work with the format of the arg from ImageJ.<br />
 * 
 * Current implementation assumes this format of arg: key1=value1 key2=value2<br />
 * 
 * @author Sami Badawi
 */
public class BeanUtilsParser implements KeyValueParser {

	protected String _seperators = " ;=";
    
    /** Split the arg into a map. */
    public Map<String,String> split(String arg) {
        TreeMap<String,String> result = new TreeMap();
        if (arg == null || arg.trim().length() == 0)
            return null;
        String currentToken = null;
        String currentKey = null;
        String currentValue = null;
        StringTokenizer st = new StringTokenizer(arg,_seperators);
        for (int i = 0; st.hasMoreTokens(); i++) {
            currentToken = st.nextToken();
            if ((i & 1) == 0)
                currentKey = currentToken;
            else {
                currentValue = currentToken;
                result.put(currentKey, currentValue);
            }
        }
        return result;
    }
    
    @Override
    public void setProperty(Object bean, String key, Object value) throws Exception
    {
        BeanUtils.setProperty(bean, key, value);
    }
    
    boolean setProperties(Object bean, Map<String,?> map) {
        for (Map.Entry<String,?> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            try {
                setProperty(bean, key, value);
            } catch (Exception ex) {
                Logger.getLogger(getClass().getSimpleName()).log(Level.SEVERE, 
                "Could not set key value: " + key +", "+ value, ex);
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean parse(Object bean, String arg) {
        if (arg == null || "".equals(arg))
            return true;
        try {
            Map<String,?> properties = split(arg);
            return setProperties(bean,properties);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getSimpleName()).log(Level.SEVERE, 
                "Could not parse arg: " + arg, ex);
            return false;
        }
    }

}
