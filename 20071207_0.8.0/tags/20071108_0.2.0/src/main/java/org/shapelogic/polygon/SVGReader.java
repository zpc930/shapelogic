package org.shapelogic.polygon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class SVGReader {
	String fileName;
	Document _xml;
	Polygon _polygon = null ;
	ArrayList<String> rawLines = new ArrayList<String>();
	int iToken = 0;
	String[] tokens; 
    IPoint2D lastPoint = null;
	private DocumentBuilderFactory documentBuilderFactory;
	private DocumentBuilder documentBuilder;
	XPath xpath;
	private String expression = "//path";
	
	public SVGReader(String fileName) {
		this.fileName = fileName;
	}
	
	static public SVGReader makeInstance(String fileName) {
		return new SVGReader(fileName);
	}  

    DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
	    xpath = XPathFactory.newInstance().newXPath();
		return documentBuilder;
	}

	void read() throws SAXException, IOException, ParserConfigurationException {
        System.out.println(fileName);
        _xml = getDocumentBuilder().parse(fileName);
    }
    
    Polygon makePolygon() throws SAXException, IOException, XPathExpressionException, ParserConfigurationException {
        if (_polygon == null) {
            if (_xml == null)
                read();
            _polygon = new Polygon();
    	    NodeList nodes = (NodeList) xpath.evaluate(expression , _xml, XPathConstants.NODESET);
    	    for (int i = 0, n = nodes.getLength(); i < n; i++) {
    	        Node node = nodes.item(i);
    	        String rawLine = node.getAttributes().getNamedItem("d").getTextContent();
    	        if (rawLine != null && !"".equals(rawLine))
    	        	rawLines.add(rawLine);
    	    }

            for (String line : rawLines) {
                System.out.println(line);
                parseStringToPoints(line);
            }
        }
        return _polygon;
    }
    
    List<IPoint2D> parseStringToPoints(String input) {
        tokens = input.split("[ ,]");
        iToken = 0;
        while (iToken < tokens.length) {
            String current = tokens[iToken];
            if ("M".equalsIgnoreCase(current)) 
            	handleM(false);  
            else 
            	handleM(true);
        }
        for (String line : tokens)
             System.out.println(line);
        return null;
    }
    
    void handleM(boolean addLine) {
        if (iToken + 2 < tokens.length) {
            iToken = iToken + 1;
            float x  = java.lang.Float.parseFloat(tokens[iToken]);
            iToken = iToken + 1;
            float y = java.lang.Float.parseFloat(tokens[iToken]);
            iToken = iToken + 1;
            IPoint2D point  = new CPointDouble(x,y);
//            _polygon.addPoint(point);
            if (addLine) {
                _polygon.addLine(point, lastPoint);
            }
            lastPoint = point;
        }
        else
            iToken = tokens.length;
    }
    
    public Polygon getPolygon() throws ParserConfigurationException {
    	if (_polygon == null) {
			try {
				makePolygon();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return _polygon;
    }
}