package org.shapelogic.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.PropertyUtils;
import org.shapelogic.logic.DynaBeanFactory;


/** This should read a csv file and parse it into objects of an 
 * given class or into DynaBean
 * It assumes that the first time is the heading with the right name
 * the second line is the type of each field. 
 * 
 * @author Sami Badawi
 *
 */
public class CSVParser {
	private String SPLIT_REGEX = ","; 
	private Class _resultClass;
	private DynaClass _dynaClass;
	private String[] _headerNames;
	private String[] _headerTypes;
	private BufferedReader _br;
	private String _nameOfDynaClass = "CVSClass";
	
	public CSVParser(Reader in, Class resultClass) {
		_resultClass = resultClass;
		_br = new BufferedReader(in);
	}

	private String[] parseLine() throws IOException {
		String currentLine = _br.readLine();
		if (currentLine == null)
			return null;
		return currentLine.split(SPLIT_REGEX);
	}
	
	private void parseHeaders() throws IOException, ParseException {
		_headerNames = parseLine();
		_headerTypes = parseLine();
		if (_headerTypes == null)
			return;
		if (_resultClass == null) {
			Map<String, String> classMap = zipToMap(_headerNames,_headerTypes);
			_dynaClass = DynaBeanFactory.getDynaClassInstance(classMap, _nameOfDynaClass);
		}
			
	}

	private Map zipToMap(String[] s1, String[] s2) throws ParseException  
	{
		Map<String,String> result = new TreeMap<String,String>();
		if (s1 == null ) 
			throw new ParseException("Header line is missing",0);
		if (s2 == null ) 
			throw new ParseException("Type line is missing",0);
		if (s1.length != s2.length)
			throw new ParseException("Header line and type line have different length",0);
		for (int i=0; i<s1.length; i++) {
			result.put(s1[i], s2[i]);
		}
		return result;
	}
	
	public List parseAll() 
	throws IOException, IllegalAccessException, InvocationTargetException, 
	NoSuchMethodException, InstantiationException, ParseException 
	{
		parseHeaders();
		List result = new ArrayList();
		String[] line = parseLine();
		while (line != null) {
			Map map = zipToMap(_headerNames,line);
			Object bean = null;
			if (_resultClass != null) {
				bean = _resultClass.newInstance();
				for (Object obj: map.entrySet()) {
					Entry entry = (Entry) obj;
					BeanUtils.setProperty(bean,(String)entry.getKey(),entry.getValue());
				}
			}
			else {
				bean = DynaBeanFactory.getDynaBeanInstance(map, _dynaClass);
			}
			result.add(bean);
			line = parseLine();
		}
		return result;
	}
}