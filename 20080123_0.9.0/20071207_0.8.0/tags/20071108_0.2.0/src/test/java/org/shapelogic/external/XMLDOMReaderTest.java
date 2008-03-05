package org.shapelogic.external;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class XMLDOMReaderTest extends TestCase {

	String dir = null;
	String baseDir = "./";
	String relDir = "src/test/resources/svg/letter/";
	String fileName = "A.svg";
	String expression = "/svg/g/path";
	private String expectedPathD = "M 145.71429,823.79075 L 206.35349,597.48217 " +
	"L 271.99269,352.51334 L 332.,595. L 396.19277,816.03434";

	
	private String getDir() {
		return baseDir + relDir;
	}
	
	public void testElementaryTreeWalking() {
		DOMParser p = new DOMParser(getDir()+fileName);
		p.visit();
		assertEquals("There should be 29 nodes in A.svg",29,p.nodeCount);
		
		Element root = p.doc.getDocumentElement();
		assertEquals("root of A.svg is svg","svg",root.getTagName());
		
		NodeList elements = root.getElementsByTagName("g");
		Element gElement = (Element) elements.item(0);
		assertEquals("second element of A.svg is g","g",gElement.getTagName());

		NodeList elements2 = gElement.getElementsByTagName("path");
		Element pElement = (Element) elements2.item(0);
		assertEquals("second element of A.svg is path","path",pElement.getTagName());
		assertEquals("second element of A.svg is path",2,elements2.getLength());
		String d = pElement.getAttribute("d");
		assertEquals("first attribute d of A.svg is path",expectedPathD,d);
	}
	
	public void testXPathElement1() throws Exception {
		DOMParser p = new DOMParser(getDir()+fileName);
	    XPath xpath = XPathFactory.newInstance().newXPath();
	
	    System.out.println("XPath test");
//	    System.out.println(xpath.evaluate(expression, p.doc));
	    System.out.println(xpath.evaluate("//path", p.doc));
	
	    NodeList nodes = (NodeList) xpath.evaluate(expression, p.doc, XPathConstants.NODESET); //Orig
	    assertEquals("There are 2 path elements",2,nodes.getLength());
	    for (int i = 0, n = nodes.getLength(); i < n; i++) {
	        Node node = nodes.item(i);
	        System.out.println(node.getAttributes().getNamedItem("d"));
	    }
	}

	public void testXPathElement2() throws Exception {
		DOMParser p = new DOMParser(getDir()+fileName);
	    XPath xpath = XPathFactory.newInstance().newXPath();
	
	    NodeList nodes = (NodeList) xpath.evaluate(expression, p.doc, XPathConstants.NODESET); //Orig
	    assertEquals("There are 2 path elements",2,nodes.getLength());
	    for (int i = 0, n = nodes.getLength(); i < n; i++) {
	        Node node = nodes.item(i);
	        System.out.println(node.getAttributes().getNamedItem("d"));
	    }
	}

	public void testXPathAttributtes() throws Exception {
		DOMParser p = new DOMParser(getDir()+fileName);
	    XPath xpath = XPathFactory.newInstance().newXPath();
	
	    System.out.println("XPath test 2");
	    String x = xpath.evaluate("//@d", p.doc);
	    System.out.println(x);
	
	    String nodes = (String) xpath.evaluate("//@d", p.doc, XPathConstants.STRING);
	    assertEquals(expectedPathD,x);
	    assertEquals(expectedPathD,nodes);

	}
}

class DOMParser
{
	Document doc = null;
	int nodeCount = 0;
	DocumentBuilderFactory documentBuilderFactory;
	DocumentBuilder documentBuilder;
	
	public DOMParser(String pathName)
	{
		try
		{
			parserXML(new File(pathName));
			
		}
		catch(Exception error)
		{
			error.printStackTrace();
		}
	}
	
	public void visit() {
		visit(doc,0);
	}

	public void visit(Node node, int level)
	{
		NodeList nl = node.getChildNodes();
		nodeCount++;
		
		for(int i=0, cnt=nl.getLength(); i<cnt; i++)
		{
//			System.out.println("["+nl.item(i)+"]");
			
			visit(nl.item(i), level+1);
		}
	}
	
	public void parserXML(File file) throws SAXException, IOException, ParserConfigurationException
	{
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilder = documentBuilderFactory.newDocumentBuilder(); 
		doc = documentBuilder.parse(file);
	}
	
}
