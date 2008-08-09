grammar Filter;
options {
    output=AST;
    ASTLabelType=CommonTree; // type of $orExpr.tree ref etc...
}

tokens {
	CONSTRAINT; 
	STRING;
	DOUBLE;
	VARIABLE;
	AND =	 '&&';
	OR =	 '||';
	NOT =	 '!';
	LEFTPAR =	'(';
	RIGHTPAR =	')';
	DQUOTE 	=	'"';
	QUOTE 	=	'\'';
	PERIOD 	=	'.';
	MINUS 	=	'-';
} // define pseudo-operations

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

// START:tokens
NEWLINE	:	'\r'? '\n' ;
WS  :   	(' '|'\t')+ {skip();} ;
// END:tokens

fragment
LETTER 	:	('a'..'z'|'A'..'Z'|'_');

fragment
DIGIT	:	'0'..'9';

fragment
NONE_END :	('a'..'z'|'A'..'Z'|'_' | '0'..'9' | '.' | '-' | '+' | ';' | ',' | '/' | '\\' | ':');

ID  :   	LETTER (LETTER | DIGIT | PERIOD)* ;

NUMBER  :    	MINUS? DIGIT+ (PERIOD DIGIT*)? ;

ARGU :   	QUOTE NONE_END* QUOTE;

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

filter:   orExpr {System.out.println($orExpr.tree.toStringTree());} ;

orExpr:   andExpr (OR^ andExpr)*;

andExpr:   notExpr (AND^ notExpr)*;

notExpr:	
        NOT atom -> ^(NOT atom)
    |   atom -> atom;


argument:
      ARGU   -> ^(STRING ARGU)
    | NUMBER -> ^(DOUBLE NUMBER)
    | ID     -> ^(VARIABLE ID);
    
constraint:	
        name=ID LEFTPAR argument RIGHTPAR -> ^(CONSTRAINT $name argument)
    |   name=ID -> ^(CONSTRAINT $name);

atom:   
    LEFTPAR orExpr RIGHTPAR    -> orExpr
    | constraint;
