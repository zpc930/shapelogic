package org.shapelogic.filter;

// $ANTLR 3.0 Filter.g 2007-11-07 20:40:07

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class FilterLexer extends Lexer {
    public static final int LETTER=19;
    public static final int PERIOD=15;
    public static final int RIGHTPAR=12;
    public static final int NUMBER=23;
    public static final int DQUOTE=13;
    public static final int NOT=10;
    public static final int MINUS=16;
    public static final int ID=22;
    public static final int AND=8;
    public static final int Tokens=25;
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
    public FilterLexer() {;} 
    public FilterLexer(CharStream input) {
        super(input);
    }
    public String getGrammarFileName() { return "Filter.g"; }

    // $ANTLR start AND
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            // Filter.g:3:7: ( '&&' )
            // Filter.g:3:7: '&&'
            {
            match("&&"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end AND

    // $ANTLR start OR
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            // Filter.g:4:6: ( '||' )
            // Filter.g:4:6: '||'
            {
            match("||"); 


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end OR

    // $ANTLR start NOT
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            // Filter.g:5:7: ( '!' )
            // Filter.g:5:7: '!'
            {
            match('!'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end NOT

    // $ANTLR start LEFTPAR
    public final void mLEFTPAR() throws RecognitionException {
        try {
            int _type = LEFTPAR;
            // Filter.g:6:11: ( '(' )
            // Filter.g:6:11: '('
            {
            match('('); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end LEFTPAR

    // $ANTLR start RIGHTPAR
    public final void mRIGHTPAR() throws RecognitionException {
        try {
            int _type = RIGHTPAR;
            // Filter.g:7:12: ( ')' )
            // Filter.g:7:12: ')'
            {
            match(')'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end RIGHTPAR

    // $ANTLR start DQUOTE
    public final void mDQUOTE() throws RecognitionException {
        try {
            int _type = DQUOTE;
            // Filter.g:8:10: ( '\"' )
            // Filter.g:8:10: '\"'
            {
            match('\"'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end DQUOTE

    // $ANTLR start QUOTE
    public final void mQUOTE() throws RecognitionException {
        try {
            int _type = QUOTE;
            // Filter.g:9:9: ( '\\'' )
            // Filter.g:9:9: '\\''
            {
            match('\''); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end QUOTE

    // $ANTLR start PERIOD
    public final void mPERIOD() throws RecognitionException {
        try {
            int _type = PERIOD;
            // Filter.g:10:10: ( '.' )
            // Filter.g:10:10: '.'
            {
            match('.'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end PERIOD

    // $ANTLR start MINUS
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            // Filter.g:11:9: ( '-' )
            // Filter.g:11:9: '-'
            {
            match('-'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end MINUS

    // $ANTLR start NEWLINE
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            // Filter.g:28:11: ( ( '\\r' )? '\\n' )
            // Filter.g:28:11: ( '\\r' )? '\\n'
            {
            // Filter.g:28:11: ( '\\r' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='\r') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // Filter.g:28:11: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end NEWLINE

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            // Filter.g:29:10: ( ( ' ' | '\\t' )+ )
            // Filter.g:29:10: ( ' ' | '\\t' )+
            {
            // Filter.g:29:10: ( ' ' | '\\t' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\t'||LA2_0==' ') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Filter.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);

            skip();

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end WS

    // $ANTLR start LETTER
    public final void mLETTER() throws RecognitionException {
        try {
            // Filter.g:33:11: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) )
            // Filter.g:33:11: ( 'a' .. 'z' | 'A' .. 'Z' | '_' )
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
        }
    }
    // $ANTLR end LETTER

    // $ANTLR start DIGIT
    public final void mDIGIT() throws RecognitionException {
        try {
            // Filter.g:36:9: ( '0' .. '9' )
            // Filter.g:36:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end DIGIT

    // $ANTLR start NONE_END
    public final void mNONE_END() throws RecognitionException {
        try {
            // Filter.g:39:12: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '.' | '-' | '+' | ';' | ',' | '/' | '\\\\' | ':' ) )
            // Filter.g:39:12: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '.' | '-' | '+' | ';' | ',' | '/' | '\\\\' | ':' )
            {
            if ( (input.LA(1)>='+' && input.LA(1)<=';')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='\\'||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse =
                    new MismatchedSetException(null,input);
                recover(mse);    throw mse;
            }


            }

        }
        finally {
        }
    }
    // $ANTLR end NONE_END

    // $ANTLR start ID
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            // Filter.g:41:10: ( LETTER ( LETTER | DIGIT | PERIOD )* )
            // Filter.g:41:10: LETTER ( LETTER | DIGIT | PERIOD )*
            {
            mLETTER(); 
            // Filter.g:41:17: ( LETTER | DIGIT | PERIOD )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='.'||(LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // Filter.g:
            	    {
            	    if ( input.LA(1)=='.'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse =
            	            new MismatchedSetException(null,input);
            	        recover(mse);    throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end ID

    // $ANTLR start NUMBER
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            // Filter.g:43:15: ( ( MINUS )? ( DIGIT )+ ( PERIOD ( DIGIT )* )? )
            // Filter.g:43:15: ( MINUS )? ( DIGIT )+ ( PERIOD ( DIGIT )* )?
            {
            // Filter.g:43:15: ( MINUS )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='-') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Filter.g:43:15: MINUS
                    {
                    mMINUS(); 

                    }
                    break;

            }

            // Filter.g:43:22: ( DIGIT )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // Filter.g:43:22: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);

            // Filter.g:43:29: ( PERIOD ( DIGIT )* )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='.') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // Filter.g:43:30: PERIOD ( DIGIT )*
                    {
                    mPERIOD(); 
                    // Filter.g:43:37: ( DIGIT )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // Filter.g:43:37: DIGIT
                    	    {
                    	    mDIGIT(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end NUMBER

    // $ANTLR start ARGU
    public final void mARGU() throws RecognitionException {
        try {
            int _type = ARGU;
            // Filter.g:45:11: ( QUOTE ( NONE_END )* QUOTE )
            // Filter.g:45:11: QUOTE ( NONE_END )* QUOTE
            {
            mQUOTE(); 
            // Filter.g:45:17: ( NONE_END )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='+' && LA8_0<=';')||(LA8_0>='A' && LA8_0<='Z')||LA8_0=='\\'||LA8_0=='_'||(LA8_0>='a' && LA8_0<='z')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // Filter.g:45:17: NONE_END
            	    {
            	    mNONE_END(); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            mQUOTE(); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end ARGU

    public void mTokens() throws RecognitionException {
        // Filter.g:1:10: ( AND | OR | NOT | LEFTPAR | RIGHTPAR | DQUOTE | QUOTE | PERIOD | MINUS | NEWLINE | WS | ID | NUMBER | ARGU )
        int alt9=14;
        switch ( input.LA(1) ) {
        case '&':
            {
            alt9=1;
            }
            break;
        case '|':
            {
            alt9=2;
            }
            break;
        case '!':
            {
            alt9=3;
            }
            break;
        case '(':
            {
            alt9=4;
            }
            break;
        case ')':
            {
            alt9=5;
            }
            break;
        case '\"':
            {
            alt9=6;
            }
            break;
        case '\'':
            {
            int LA9_7 = input.LA(2);

            if ( (LA9_7=='\''||(LA9_7>='+' && LA9_7<=';')||(LA9_7>='A' && LA9_7<='Z')||LA9_7=='\\'||LA9_7=='_'||(LA9_7>='a' && LA9_7<='z')) ) {
                alt9=14;
            }
            else {
                alt9=7;}
            }
            break;
        case '.':
            {
            alt9=8;
            }
            break;
        case '-':
            {
            int LA9_9 = input.LA(2);

            if ( ((LA9_9>='0' && LA9_9<='9')) ) {
                alt9=13;
            }
            else {
                alt9=9;}
            }
            break;
        case '\n':
        case '\r':
            {
            alt9=10;
            }
            break;
        case '\t':
        case ' ':
            {
            alt9=11;
            }
            break;
        case 'A':
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case '_':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt9=12;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt9=13;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( AND | OR | NOT | LEFTPAR | RIGHTPAR | DQUOTE | QUOTE | PERIOD | MINUS | NEWLINE | WS | ID | NUMBER | ARGU );", 9, 0, input);

            throw nvae;
        }

        switch (alt9) {
            case 1 :
                // Filter.g:1:10: AND
                {
                mAND(); 

                }
                break;
            case 2 :
                // Filter.g:1:14: OR
                {
                mOR(); 

                }
                break;
            case 3 :
                // Filter.g:1:17: NOT
                {
                mNOT(); 

                }
                break;
            case 4 :
                // Filter.g:1:21: LEFTPAR
                {
                mLEFTPAR(); 

                }
                break;
            case 5 :
                // Filter.g:1:29: RIGHTPAR
                {
                mRIGHTPAR(); 

                }
                break;
            case 6 :
                // Filter.g:1:38: DQUOTE
                {
                mDQUOTE(); 

                }
                break;
            case 7 :
                // Filter.g:1:45: QUOTE
                {
                mQUOTE(); 

                }
                break;
            case 8 :
                // Filter.g:1:51: PERIOD
                {
                mPERIOD(); 

                }
                break;
            case 9 :
                // Filter.g:1:58: MINUS
                {
                mMINUS(); 

                }
                break;
            case 10 :
                // Filter.g:1:64: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 11 :
                // Filter.g:1:72: WS
                {
                mWS(); 

                }
                break;
            case 12 :
                // Filter.g:1:75: ID
                {
                mID(); 

                }
                break;
            case 13 :
                // Filter.g:1:78: NUMBER
                {
                mNUMBER(); 

                }
                break;
            case 14 :
                // Filter.g:1:85: ARGU
                {
                mARGU(); 

                }
                break;

        }

    }


 

}