package org.shapelogic.filter;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

import junit.framework.TestCase;
import org.shapelogic.filter.FilterParser;
import org.shapelogic.filter.FilterParser.filter_return;

/**   
 * 
 * @author Sami Badawi
 *
 */
public class AntlrTest extends TestCase {
	public static final String AND = "&&";
	public static final String OR = "||";
	public static final String NOT = "!";
	public static final String CONSTRAINT = "CONSTRAINT";
	
	/** If I have a grammer in a text file */
	public void te_stFile() throws Exception {
		CommonTree tree = runFile("/tmp/antlrworks/__Test___input.txt");
	}
	
	public void testString() throws Exception {
		System.out.println("Place 1");
		String inputExpression = "a&& b( -1.23 ) || !c('Sami')";
		CommonTree tree = runString(inputExpression);
		assertNotNull(tree);
		System.out.println("Place 2");
		String exprecte = "(|| (&& (CONSTRAINT a) (CONSTRAINT b -1.23)) (! (CONSTRAINT c 'Sami')))";
		exprecte = "(|| (&& (CONSTRAINT a) (CONSTRAINT b (DOUBLE -1.23))) (! (CONSTRAINT c (STRING 'Sami'))))";

		assertEquals(exprecte,tree.toStringTree());
		assertEquals(exprecte,tree.toStringTree());
		String tokenText = getTokenText(tree);
		assertNotNull("Token text should not be null",tokenText);
		assertEquals(OR ,getTokenText(tree));
		assertEquals(AND ,getChildToken(tree,0));
		assertEquals(NOT ,getChildToken(tree,1));
		assertEquals(CONSTRAINT ,getChildToken(tree,new int[] {0,0}));
		assertEquals("a" ,getChildToken(tree,new int[] {0,0,0}));
	}
	
//Helper methods
	
//Helper tree methods
	private CommonTree getChild(CommonTree tree, int childNumbre){
		return (CommonTree) tree.getChild(childNumbre);
	}

	private CommonTree getChild(CommonTree tree, int[] childNumbres){
		CommonTree result = tree;
		for (int index: childNumbres)
			result = (CommonTree) result.getChild(childNumbres[index]);
		return result;
	}

	private String getChildToken(CommonTree tree, int[] childNumbres){
		return getTokenText(getChild(tree,childNumbres));
	}

	private String getChildToken(CommonTree tree, int childNumbre){
		return getTokenText(getChild(tree,childNumbre));
	}

	private String getTokenText(CommonTree tree){
		if (tree != null) {
			System.out.println("tree not null");
			Token token = tree.getToken();
			if (tree.getToken() != null) {
				System.out.println("token not null");
				System.out.println("Token: "+token);
				System.out.println("Token cannel: "+token.getChannel());
				System.out.println("Token class: "+token.getClass());
				String tokenText = null;
				try {
					tokenText = token.getText();
					return tokenText;
				}
				catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
		}
		return null;
	}
	
//Helper run methods
	private CommonTree runString(String inputExpression) throws Exception {
		CharStream charStream = new ANTLRStringStream(inputExpression);
		CommonTree tree = runParser(charStream);
		return tree;
	}

	private CommonTree runFile(String fileName) throws Exception {
		CharStream charStream = new ANTLRFileStream(fileName);
		CommonTree tree = runParser(charStream);
		return tree;
	}
	
	private CommonTree runParser(CharStream charStream) throws Exception {
		
        FilterLexer lex = new FilterLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lex);

        FilterParser g = new FilterParser(tokens);
        try {
    		filter_return fr = g.filter();
    		CommonTree tree = (CommonTree) fr.getTree();
            return tree;
        } catch (RecognitionException e) {
            e.printStackTrace();
            return null;
        }
	}

}