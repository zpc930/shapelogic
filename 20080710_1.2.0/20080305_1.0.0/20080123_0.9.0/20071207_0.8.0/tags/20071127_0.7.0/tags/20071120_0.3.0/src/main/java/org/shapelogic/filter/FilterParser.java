package org.shapelogic.filter;

// $ANTLR 3.0 Filter.g 2007-11-07 20:40:07

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class FilterParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CONSTRAINT", "STRING", "DOUBLE", "VARIABLE", "AND", "OR", "NOT", "LEFTPAR", "RIGHTPAR", "DQUOTE", "QUOTE", "PERIOD", "MINUS", "NEWLINE", "WS", "LETTER", "DIGIT", "NONE_END", "ID", "NUMBER", "ARGU"
    };
    public static final int PERIOD=15;
    public static final int LETTER=19;
    public static final int RIGHTPAR=12;
    public static final int NUMBER=23;
    public static final int DQUOTE=13;
    public static final int NOT=10;
    public static final int MINUS=16;
    public static final int ID=22;
    public static final int AND=8;
    public static final int EOF=-1;
    public static final int QUOTE=14;
    public static final int WS=18;
    public static final int VARIABLE=7;
    public static final int NEWLINE=17;
    public static final int OR=9;
    public static final int DOUBLE=6;
    public static final int LEFTPAR=11;
    public static final int ARGU=24;
    public static final int NONE_END=21;
    public static final int DIGIT=20;
    public static final int CONSTRAINT=4;
    public static final int STRING=5;

        public FilterParser(TokenStream input) {
            super(input);
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return tokenNames; }
    public String getGrammarFileName() { return "Filter.g"; }


    public static class filter_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start filter
    // Filter.g:51:1: filter : orExpr ;
    public final filter_return filter() throws RecognitionException {
        filter_return retval = new filter_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        orExpr_return orExpr1 = null;



        try {
            // Filter.g:51:11: ( orExpr )
            // Filter.g:51:11: orExpr
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_orExpr_in_filter334);
            orExpr1=orExpr();
            _fsp--;

            adaptor.addChild(root_0, orExpr1.getTree());
            System.out.println(((CommonTree)orExpr1.tree).toStringTree());

            }

            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end filter

    public static class orExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start orExpr
    // Filter.g:53:1: orExpr : andExpr ( OR andExpr )* ;
    public final orExpr_return orExpr() throws RecognitionException {
        orExpr_return retval = new orExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token OR3=null;
        andExpr_return andExpr2 = null;

        andExpr_return andExpr4 = null;


        CommonTree OR3_tree=null;

        try {
            // Filter.g:53:11: ( andExpr ( OR andExpr )* )
            // Filter.g:53:11: andExpr ( OR andExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_andExpr_in_orExpr346);
            andExpr2=andExpr();
            _fsp--;

            adaptor.addChild(root_0, andExpr2.getTree());
            // Filter.g:53:19: ( OR andExpr )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==OR) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Filter.g:53:20: OR andExpr
            	    {
            	    OR3=(Token)input.LT(1);
            	    match(input,OR,FOLLOW_OR_in_orExpr349); 
            	    OR3_tree = (CommonTree)adaptor.create(OR3);
            	    root_0 = (CommonTree)adaptor.becomeRoot(OR3_tree, root_0);

            	    pushFollow(FOLLOW_andExpr_in_orExpr352);
            	    andExpr4=andExpr();
            	    _fsp--;

            	    adaptor.addChild(root_0, andExpr4.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end orExpr

    public static class andExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start andExpr
    // Filter.g:55:1: andExpr : notExpr ( AND notExpr )* ;
    public final andExpr_return andExpr() throws RecognitionException {
        andExpr_return retval = new andExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token AND6=null;
        notExpr_return notExpr5 = null;

        notExpr_return notExpr7 = null;


        CommonTree AND6_tree=null;

        try {
            // Filter.g:55:12: ( notExpr ( AND notExpr )* )
            // Filter.g:55:12: notExpr ( AND notExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_notExpr_in_andExpr363);
            notExpr5=notExpr();
            _fsp--;

            adaptor.addChild(root_0, notExpr5.getTree());
            // Filter.g:55:20: ( AND notExpr )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==AND) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Filter.g:55:21: AND notExpr
            	    {
            	    AND6=(Token)input.LT(1);
            	    match(input,AND,FOLLOW_AND_in_andExpr366); 
            	    AND6_tree = (CommonTree)adaptor.create(AND6);
            	    root_0 = (CommonTree)adaptor.becomeRoot(AND6_tree, root_0);

            	    pushFollow(FOLLOW_notExpr_in_andExpr369);
            	    notExpr7=notExpr();
            	    _fsp--;

            	    adaptor.addChild(root_0, notExpr7.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end andExpr

    public static class notExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start notExpr
    // Filter.g:57:1: notExpr : ( NOT atom -> ^( NOT atom ) | atom -> atom );
    public final notExpr_return notExpr() throws RecognitionException {
        notExpr_return retval = new notExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NOT8=null;
        atom_return atom9 = null;

        atom_return atom10 = null;


        CommonTree NOT8_tree=null;
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleSubtreeStream stream_atom=new RewriteRuleSubtreeStream(adaptor,"rule atom");
        try {
            // Filter.g:58:9: ( NOT atom -> ^( NOT atom ) | atom -> atom )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==NOT) ) {
                alt3=1;
            }
            else if ( (LA3_0==LEFTPAR||LA3_0==ID) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("57:1: notExpr : ( NOT atom -> ^( NOT atom ) | atom -> atom );", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // Filter.g:58:9: NOT atom
                    {
                    NOT8=(Token)input.LT(1);
                    match(input,NOT,FOLLOW_NOT_in_notExpr387); 
                    stream_NOT.add(NOT8);

                    pushFollow(FOLLOW_atom_in_notExpr389);
                    atom9=atom();
                    _fsp--;

                    stream_atom.add(atom9.getTree());

                    // AST REWRITE
                    // elements: atom, NOT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 58:18: -> ^( NOT atom )
                    {
                        // Filter.g:58:21: ^( NOT atom )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_NOT.next(), root_1);

                        adaptor.addChild(root_1, stream_atom.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;
                case 2 :
                    // Filter.g:59:9: atom
                    {
                    pushFollow(FOLLOW_atom_in_notExpr407);
                    atom10=atom();
                    _fsp--;

                    stream_atom.add(atom10.getTree());

                    // AST REWRITE
                    // elements: atom
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 59:14: -> atom
                    {
                        adaptor.addChild(root_0, stream_atom.next());

                    }



                    }
                    break;

            }
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end notExpr

    public static class argument_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start argument
    // Filter.g:62:1: argument : ( ARGU -> ^( STRING ARGU ) | NUMBER -> ^( DOUBLE NUMBER ) | ID -> ^( VARIABLE ID ) );
    public final argument_return argument() throws RecognitionException {
        argument_return retval = new argument_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token ARGU11=null;
        Token NUMBER12=null;
        Token ID13=null;

        CommonTree ARGU11_tree=null;
        CommonTree NUMBER12_tree=null;
        CommonTree ID13_tree=null;
        RewriteRuleTokenStream stream_ARGU=new RewriteRuleTokenStream(adaptor,"token ARGU");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_NUMBER=new RewriteRuleTokenStream(adaptor,"token NUMBER");

        try {
            // Filter.g:63:7: ( ARGU -> ^( STRING ARGU ) | NUMBER -> ^( DOUBLE NUMBER ) | ID -> ^( VARIABLE ID ) )
            int alt4=3;
            switch ( input.LA(1) ) {
            case ARGU:
                {
                alt4=1;
                }
                break;
            case NUMBER:
                {
                alt4=2;
                }
                break;
            case ID:
                {
                alt4=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("62:1: argument : ( ARGU -> ^( STRING ARGU ) | NUMBER -> ^( DOUBLE NUMBER ) | ID -> ^( VARIABLE ID ) );", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // Filter.g:63:7: ARGU
                    {
                    ARGU11=(Token)input.LT(1);
                    match(input,ARGU,FOLLOW_ARGU_in_argument425); 
                    stream_ARGU.add(ARGU11);


                    // AST REWRITE
                    // elements: ARGU
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 63:14: -> ^( STRING ARGU )
                    {
                        // Filter.g:63:17: ^( STRING ARGU )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(STRING, "STRING"), root_1);

                        adaptor.addChild(root_1, stream_ARGU.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;
                case 2 :
                    // Filter.g:64:7: NUMBER
                    {
                    NUMBER12=(Token)input.LT(1);
                    match(input,NUMBER,FOLLOW_NUMBER_in_argument443); 
                    stream_NUMBER.add(NUMBER12);


                    // AST REWRITE
                    // elements: NUMBER
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 64:14: -> ^( DOUBLE NUMBER )
                    {
                        // Filter.g:64:17: ^( DOUBLE NUMBER )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(DOUBLE, "DOUBLE"), root_1);

                        adaptor.addChild(root_1, stream_NUMBER.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;
                case 3 :
                    // Filter.g:65:7: ID
                    {
                    ID13=(Token)input.LT(1);
                    match(input,ID,FOLLOW_ID_in_argument459); 
                    stream_ID.add(ID13);


                    // AST REWRITE
                    // elements: ID
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 65:14: -> ^( VARIABLE ID )
                    {
                        // Filter.g:65:17: ^( VARIABLE ID )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(VARIABLE, "VARIABLE"), root_1);

                        adaptor.addChild(root_1, stream_ID.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;

            }
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end argument

    public static class constraint_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start constraint
    // Filter.g:67:1: constraint : (name= ID LEFTPAR argument RIGHTPAR -> ^( CONSTRAINT $name argument ) | name= ID -> ^( CONSTRAINT $name) );
    public final constraint_return constraint() throws RecognitionException {
        constraint_return retval = new constraint_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token name=null;
        Token LEFTPAR14=null;
        Token RIGHTPAR16=null;
        argument_return argument15 = null;


        CommonTree name_tree=null;
        CommonTree LEFTPAR14_tree=null;
        CommonTree RIGHTPAR16_tree=null;
        RewriteRuleTokenStream stream_LEFTPAR=new RewriteRuleTokenStream(adaptor,"token LEFTPAR");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_RIGHTPAR=new RewriteRuleTokenStream(adaptor,"token RIGHTPAR");
        RewriteRuleSubtreeStream stream_argument=new RewriteRuleSubtreeStream(adaptor,"rule argument");
        try {
            // Filter.g:68:9: (name= ID LEFTPAR argument RIGHTPAR -> ^( CONSTRAINT $name argument ) | name= ID -> ^( CONSTRAINT $name) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==ID) ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==LEFTPAR) ) {
                    alt5=1;
                }
                else if ( (LA5_1==EOF||(LA5_1>=AND && LA5_1<=OR)||LA5_1==RIGHTPAR) ) {
                    alt5=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("67:1: constraint : (name= ID LEFTPAR argument RIGHTPAR -> ^( CONSTRAINT $name argument ) | name= ID -> ^( CONSTRAINT $name) );", 5, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("67:1: constraint : (name= ID LEFTPAR argument RIGHTPAR -> ^( CONSTRAINT $name argument ) | name= ID -> ^( CONSTRAINT $name) );", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // Filter.g:68:9: name= ID LEFTPAR argument RIGHTPAR
                    {
                    name=(Token)input.LT(1);
                    match(input,ID,FOLLOW_ID_in_constraint493); 
                    stream_ID.add(name);

                    LEFTPAR14=(Token)input.LT(1);
                    match(input,LEFTPAR,FOLLOW_LEFTPAR_in_constraint495); 
                    stream_LEFTPAR.add(LEFTPAR14);

                    pushFollow(FOLLOW_argument_in_constraint497);
                    argument15=argument();
                    _fsp--;

                    stream_argument.add(argument15.getTree());
                    RIGHTPAR16=(Token)input.LT(1);
                    match(input,RIGHTPAR,FOLLOW_RIGHTPAR_in_constraint499); 
                    stream_RIGHTPAR.add(RIGHTPAR16);


                    // AST REWRITE
                    // elements: name, argument
                    // token labels: name
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 68:43: -> ^( CONSTRAINT $name argument )
                    {
                        // Filter.g:68:46: ^( CONSTRAINT $name argument )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(CONSTRAINT, "CONSTRAINT"), root_1);

                        adaptor.addChild(root_1, stream_name.next());
                        adaptor.addChild(root_1, stream_argument.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;
                case 2 :
                    // Filter.g:69:9: name= ID
                    {
                    name=(Token)input.LT(1);
                    match(input,ID,FOLLOW_ID_in_constraint522); 
                    stream_ID.add(name);


                    // AST REWRITE
                    // elements: name
                    // token labels: name
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_name=new RewriteRuleTokenStream(adaptor,"token name",name);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 69:17: -> ^( CONSTRAINT $name)
                    {
                        // Filter.g:69:20: ^( CONSTRAINT $name)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(adaptor.create(CONSTRAINT, "CONSTRAINT"), root_1);

                        adaptor.addChild(root_1, stream_name.next());

                        adaptor.addChild(root_0, root_1);
                        }

                    }



                    }
                    break;

            }
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end constraint

    public static class atom_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start atom
    // Filter.g:71:1: atom : ( LEFTPAR orExpr RIGHTPAR -> orExpr | constraint );
    public final atom_return atom() throws RecognitionException {
        atom_return retval = new atom_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token LEFTPAR17=null;
        Token RIGHTPAR19=null;
        orExpr_return orExpr18 = null;

        constraint_return constraint20 = null;


        CommonTree LEFTPAR17_tree=null;
        CommonTree RIGHTPAR19_tree=null;
        RewriteRuleTokenStream stream_LEFTPAR=new RewriteRuleTokenStream(adaptor,"token LEFTPAR");
        RewriteRuleTokenStream stream_RIGHTPAR=new RewriteRuleTokenStream(adaptor,"token RIGHTPAR");
        RewriteRuleSubtreeStream stream_orExpr=new RewriteRuleSubtreeStream(adaptor,"rule orExpr");
        try {
            // Filter.g:72:5: ( LEFTPAR orExpr RIGHTPAR -> orExpr | constraint )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==LEFTPAR) ) {
                alt6=1;
            }
            else if ( (LA6_0==ID) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("71:1: atom : ( LEFTPAR orExpr RIGHTPAR -> orExpr | constraint );", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // Filter.g:72:5: LEFTPAR orExpr RIGHTPAR
                    {
                    LEFTPAR17=(Token)input.LT(1);
                    match(input,LEFTPAR,FOLLOW_LEFTPAR_in_atom545); 
                    stream_LEFTPAR.add(LEFTPAR17);

                    pushFollow(FOLLOW_orExpr_in_atom547);
                    orExpr18=orExpr();
                    _fsp--;

                    stream_orExpr.add(orExpr18.getTree());
                    RIGHTPAR19=(Token)input.LT(1);
                    match(input,RIGHTPAR,FOLLOW_RIGHTPAR_in_atom549); 
                    stream_RIGHTPAR.add(RIGHTPAR19);


                    // AST REWRITE
                    // elements: orExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 72:32: -> orExpr
                    {
                        adaptor.addChild(root_0, stream_orExpr.next());

                    }



                    }
                    break;
                case 2 :
                    // Filter.g:73:7: constraint
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_constraint_in_atom564);
                    constraint20=constraint();
                    _fsp--;

                    adaptor.addChild(root_0, constraint20.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

                retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
                adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end atom


 

    public static final BitSet FOLLOW_orExpr_in_filter334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpr_in_orExpr346 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_OR_in_orExpr349 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_andExpr_in_orExpr352 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_notExpr_in_andExpr363 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_AND_in_andExpr366 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_notExpr_in_andExpr369 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_NOT_in_notExpr387 = new BitSet(new long[]{0x0000000000400800L});
    public static final BitSet FOLLOW_atom_in_notExpr389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_notExpr407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARGU_in_argument425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_argument443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_argument459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_constraint493 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LEFTPAR_in_constraint495 = new BitSet(new long[]{0x0000000001C00000L});
    public static final BitSet FOLLOW_argument_in_constraint497 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RIGHTPAR_in_constraint499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_constraint522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFTPAR_in_atom545 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_orExpr_in_atom547 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RIGHTPAR_in_atom549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constraint_in_atom564 = new BitSet(new long[]{0x0000000000000002L});

}