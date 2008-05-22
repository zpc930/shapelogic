package org.shapelogic.util;

/** Parse arguments and set them in an object.
 *
 * @author Sami Badawi
 */
public interface KeyValueParser {
    
    /** Parse arguments and set in JavaBean.<br />
     * 
     * @param bean a JavaBean to set the parsed arguements in
     * @param arg String argument to parse
     * @return true if parsing was successful.
     */
    boolean parse(Object bean, String arg);
    
    void setProperty(Object bean, String key, Object value) throws Exception;
}
