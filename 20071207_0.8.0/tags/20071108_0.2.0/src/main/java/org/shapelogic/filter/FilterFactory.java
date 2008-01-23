package org.shapelogic.filter;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.shapelogic.filter.FilterParser.filter_return;

/**
 * 
 * @author Sami Badawi
 *
 */
public class FilterFactory {
	public static final String AND = "&&";
	public static final String OR = "||";
	public static final String NOT = "!";
	public static final String CONSTRAINT = "CONSTRAINT";
	public static final String STRING_TYPE = "STRING";
	public static final String DOUBLE_TYPE = "DOUBLE";
	public static final String VARIABLE_TYPE = "VARIABLE";
	public static final String FILTER_PACKAGE = "org.shapelogic.filter.";
	
	public static <BaseClass, Element> IFilter<BaseClass, Element> makeFilter(String filterName) {
		IFilter<BaseClass, Element> filterObject = null;
		Class klass = null;
		try {
			klass = Class.forName(filterName);
			if (klass != null)
				filterObject = (IFilter) klass.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			if (filterName.indexOf(".") == -1)
				return makeFilter(FILTER_PACKAGE + filterName);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return filterObject;
	}

	public static <BaseClass, Element> IFilter<BaseClass, Element> makeFilter(String filterName, Object constraint) {
		IFilter<BaseClass, Element> filterObject = makeFilter(filterName);
		filterObject.setConstraint(constraint);
		return filterObject;
	}
	
	public static <BaseClass, Element> IFilter<BaseClass, Element> makeFilter(CommonTree tree) {
		String tokenText = getTokenText(tree);
		if (tokenText == null)
			return null;
		char firstChar = tokenText.charAt(0);
		if (AND.equals(tokenText)) {
			CommonTree child0 = (CommonTree)tree.getChild(0);
			CommonTree child1 = (CommonTree)tree.getChild(1);
			IFilter<BaseClass, Element> filter0 = makeFilter(child0);
			IFilter<BaseClass, Element> filter1 = makeFilter(child1);
			return new AndFilter<BaseClass, Element>(filter0,filter1);
		}
		else if (OR.equals(tokenText)) {
			CommonTree child0 = (CommonTree)tree.getChild(0);
			CommonTree child1 = (CommonTree)tree.getChild(1);
			IFilter<BaseClass, Element> filter0 = makeFilter(child0);
			IFilter<BaseClass, Element> filter1 = makeFilter(child1);
			return new OrFilter<BaseClass, Element>(filter0,filter1);
		}
		else if (NOT.equals(tokenText)) {
			CommonTree child0 = (CommonTree)tree.getChild(0);
			IFilter<BaseClass, Element> filter0 = makeFilter(child0);
			return new NotFilter<BaseClass, Element>(filter0);
		} 
		else if (CONSTRAINT.equals(tokenText)) {
			CommonTree child0 = (CommonTree)tree.getChild(0);
			CommonTree child1 = null; 
			if (tree.getChildCount() == 1) {
				return makeFilter(getTokenText(child0));
			}
			else if (tree.getChildCount() == 2) {
				child1 = (CommonTree)tree.getChild(1);
				return makeFilter(getTokenText(child0),parseCronstraintParameterTree(child1));
			}
			return null;
		} else {
			
		}
		return null;
	}
	
	public static <BaseClass, Element> IFilter<BaseClass, Element> makeTreeFilter(String inputExpression) {
		try {
			return makeFilter(makeASTFromString(inputExpression));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//Helper methods
	
	//Helper tree methods
	private static CommonTree getChild(CommonTree tree, int childNumbre){
		return (CommonTree) tree.getChild(childNumbre);
	}

	private static CommonTree getChild(CommonTree tree, int[] childNumbres){
		CommonTree result = tree;
		for (int index: childNumbres)
			result = (CommonTree) result.getChild(childNumbres[index]);
		return result;
	}

	private static String getChildToken(CommonTree tree, int[] childNumbres){
		return getTokenText(getChild(tree,childNumbres));
	}

	private static String getChildToken(CommonTree tree, int childNumbre){
		return getTokenText(getChild(tree,childNumbre));
	}

	private static String getTokenText(CommonTree tree){
		if (tree != null) {
			Token token = tree.getToken();
			if (tree.getToken() != null) {
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
	private static CommonTree makeASTFromString(String inputExpression) throws Exception {
		CharStream charStream = new ANTLRStringStream(inputExpression);
		CommonTree tree = runParser(charStream);
		return tree;
	}

	private static CommonTree makeASTFromFile(String fileName) throws Exception {
		CharStream charStream = new ANTLRFileStream(fileName);
		CommonTree tree = runParser(charStream);
		return tree;
	}
	
	private static CommonTree runParser(CharStream charStream) throws Exception {
		
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

	private static Object parseCronstraintParameterTree(CommonTree tree) {
		String tokenText = getTokenText(tree);
		if (tokenText == null)
			return null;
		if (STRING_TYPE.equals(tokenText)) {
			CommonTree child0 = (CommonTree)tree.getChild(0);
			String text = getTokenText(child0);
			if (text != null && text.length() > 2)
				return text.substring(1, text.length()-1);
			return null;
		}
		else if (DOUBLE_TYPE.equals(tokenText)) {
			CommonTree child0 = (CommonTree)tree.getChild(0);
			String text = getTokenText(child0);
			return new Double(Double.parseDouble(text));
		}
		else if (VARIABLE_TYPE.equals(tokenText)) {
			CommonTree child0 = (CommonTree)tree.getChild(0);
			String text = getTokenText(child0);
			return text; //XXX this is not correct
		}
		return null;
	}
}
