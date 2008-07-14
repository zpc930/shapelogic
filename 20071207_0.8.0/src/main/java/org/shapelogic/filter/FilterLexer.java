package org.shapelogic.filter;

// $ANTLR 3.0 Filter.g 2007-11-26 19:51:46

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class FilterLexer extends Lexer {
    public static final int PERIOD=15;
    public static final int LETTER=20;
    public static final int RIGHTPAR=12;
    public static final int NUMBER=25;
    public static final int NOT=10;
    public static final int MINUS=16;
    public static final int ID=24;
    public static final int AND=8;
    public static final int Tokens=27;
    public static final int EOF=-1;
    public static final int DOUBLEQUOTE=13;
    public static final int QUOTE=14;
    public static final int WS=19;
    public static final int BACKSPACE=17;
    public static final int VARIABLE=7;
    public static final int NEWLINE=18;
    public static final int OR=9;
    public static final int LEFTPAR=11;
    public static final int DOUBLE=6;
    public static final int BACKSPACE_SEQUENCE=23;
    public static final int ARGU=26;
    public static final int NONE_END=22;
    public static final int DIGIT=21;
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

    // $ANTLR start DOUBLEQUOTE
    public final void mDOUBLEQUOTE() throws RecognitionException {
        try {
            int _type = DOUBLEQUOTE;
            // Filter.g:8:15: ( '\"' )
            // Filter.g:8:15: '\"'
            {
            match('\"'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end DOUBLEQUOTE

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

    // $ANTLR start BACKSPACE
    public final void mBACKSPACE() throws RecognitionException {
        try {
            int _type = BACKSPACE;
            // Filter.g:12:13: ( '\\\\' )
            // Filter.g:12:13: '\\\\'
            {
            match('\\'); 

            }

            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end BACKSPACE

    // $ANTLR start NEWLINE
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            // Filter.g:29:11: ( ( '\\r' )? '\\n' )
            // Filter.g:29:11: ( '\\r' )? '\\n'
            {
            // Filter.g:29:11: ( '\\r' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='\r') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // Filter.g:29:11: '\\r'
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
            // Filter.g:30:10: ( ( ' ' | '\\t' )+ )
            // Filter.g:30:10: ( ' ' | '\\t' )+
            {
            // Filter.g:30:10: ( ' ' | '\\t' )+
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
            // Filter.g:34:11: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) )
            // Filter.g:34:11: ( 'a' .. 'z' | 'A' .. 'Z' | '_' )
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
            // Filter.g:37:9: ( '0' .. '9' )
            // Filter.g:37:9: '0' .. '9'
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
            // Filter.g:40:12: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '.' | '-' | '+' | ';' | ',' | '/' | ':' ) )
            // Filter.g:40:12: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '.' | '-' | '+' | ';' | ',' | '/' | ':' )
            {
            if ( (input.LA(1)>='+' && input.LA(1)<=';')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
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

    // $ANTLR start BACKSPACE_SEQUENCE
    public final void mBACKSPACE_SEQUENCE() throws RecognitionException {
        try {
            // Filter.g:44:4: ( BACKSPACE ( '\\'' | '\"' | BACKSPACE ) )
            // Filter.g:44:4: BACKSPACE ( '\\'' | '\"' | BACKSPACE )
            {
            mBACKSPACE(); 
            if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\' ) {
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
    // $ANTLR end BACKSPACE_SEQUENCE

    // $ANTLR start ID
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            // Filter.g:46:10: ( LETTER ( LETTER | DIGIT | PERIOD )* )
            // Filter.g:46:10: LETTER ( LETTER | DIGIT | PERIOD )*
            {
            mLETTER(); 
            // Filter.g:46:17: ( LETTER | DIGIT | PERIOD )*
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
            // Filter.g:48:15: ( ( MINUS )? ( DIGIT )+ ( PERIOD ( DIGIT )* )? )
            // Filter.g:48:15: ( MINUS )? ( DIGIT )+ ( PERIOD ( DIGIT )* )?
            {
            // Filter.g:48:15: ( MINUS )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='-') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Filter.g:48:15: MINUS
                    {
                    mMINUS(); 

                    }
                    break;

            }

            // Filter.g:48:22: ( DIGIT )+
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
            	    // Filter.g:48:22: DIGIT
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

            // Filter.g:48:29: ( PERIOD ( DIGIT )* )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='.') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // Filter.g:48:30: PERIOD ( DIGIT )*
                    {
                    mPERIOD(); 
                    // Filter.g:48:37: ( DIGIT )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // Filter.g:48:37: DIGIT
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
            // Filter.g:50:11: ( QUOTE ( NONE_END | BACKSPACE_SEQUENCE )* QUOTE | DOUBLEQUOTE ( NONE_END | BACKSPACE_SEQUENCE )* DOUBLEQUOTE )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='\'') ) {
                alt10=1;
            }
            else if ( (LA10_0=='\"') ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("50:1: ARGU : ( QUOTE ( NONE_END | BACKSPACE_SEQUENCE )* QUOTE | DOUBLEQUOTE ( NONE_END | BACKSPACE_SEQUENCE )* DOUBLEQUOTE );", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // Filter.g:50:11: QUOTE ( NONE_END | BACKSPACE_SEQUENCE )* QUOTE
                    {
                    mQUOTE(); 
                    // Filter.g:50:17: ( NONE_END | BACKSPACE_SEQUENCE )*
                    loop8:
                    do {
                        int alt8=3;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0>='+' && LA8_0<=';')||(LA8_0>='A' && LA8_0<='Z')||LA8_0=='_'||(LA8_0>='a' && LA8_0<='z')) ) {
                            alt8=1;
                        }
                        else if ( (LA8_0=='\\') ) {
                            alt8=2;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // Filter.g:50:18: NONE_END
                    	    {
                    	    mNONE_END(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // Filter.g:50:29: BACKSPACE_SEQUENCE
                    	    {
                    	    mBACKSPACE_SEQUENCE(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);

                    mQUOTE(); 

                    }
                    break;
                case 2 :
                    // Filter.g:50:58: DOUBLEQUOTE ( NONE_END | BACKSPACE_SEQUENCE )* DOUBLEQUOTE
                    {
                    mDOUBLEQUOTE(); 
                    // Filter.g:50:70: ( NONE_END | BACKSPACE_SEQUENCE )*
                    loop9:
                    do {
                        int alt9=3;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='+' && LA9_0<=';')||(LA9_0>='A' && LA9_0<='Z')||LA9_0=='_'||(LA9_0>='a' && LA9_0<='z')) ) {
                            alt9=1;
                        }
                        else if ( (LA9_0=='\\') ) {
                            alt9=2;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // Filter.g:50:71: NONE_END
                    	    {
                    	    mNONE_END(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // Filter.g:50:82: BACKSPACE_SEQUENCE
                    	    {
                    	    mBACKSPACE_SEQUENCE(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);

                    mDOUBLEQUOTE(); 

                    }
                    break;

            }
            this.type = _type;
        }
        finally {
        }
    }
    // $ANTLR end ARGU

    public void mTokens() throws RecognitionException {
        // Filter.g:1:10: ( AND | OR | NOT | LEFTPAR | RIGHTPAR | DOUBLEQUOTE | QUOTE | PERIOD | MINUS | BACKSPACE | NEWLINE | WS | ID | NUMBER | ARGU )
        int alt11=15;
        switch ( input.LA(1) ) {
        case '&':
            {
            alt11=1;
            }
            break;
        case '|':
            {
            alt11=2;
            }
            break;
        case '!':
            {
            alt11=3;
            }
            break;
        case '(':
            {
            alt11=4;
            }
            break;
        case ')':
            {
            alt11=5;
            }
            break;
        case '\"':
            {
            int LA11_6 = input.LA(2);

            if ( (LA11_6=='\"'||(LA11_6>='+' && LA11_6<=';')||(LA11_6>='A' && LA11_6<='Z')||LA11_6=='\\'||LA11_6=='_'||(LA11_6>='a' && LA11_6<='z')) ) {
                alt11=15;
            }
            else {
                alt11=6;}
            }
            break;
        case '\'':
            {
            int LA11_7 = input.LA(2);

            if ( (LA11_7=='\''||(LA11_7>='+' && LA11_7<=';')||(LA11_7>='A' && LA11_7<='Z')||LA11_7=='\\'||LA11_7=='_'||(LA11_7>='a' && LA11_7<='z')) ) {
                alt11=15;
            }
            else {
                alt11=7;}
            }
            break;
        case '.':
            {
            alt11=8;
            }
            break;
        case '-':
            {
            int LA11_9 = input.LA(2);

            if ( ((LA11_9>='0' && LA11_9<='9')) ) {
                alt11=14;
            }
            else {
                alt11=9;}
            }
            break;
        case '\\':
            {
            alt11=10;
            }
            break;
        case '\n':
        case '\r':
            {
            alt11=11;
            }
            break;
        case '\t':
        case ' ':
            {
            alt11=12;
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
            alt11=13;
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
            alt11=14;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( AND | OR | NOT | LEFTPAR | RIGHTPAR | DOUBLEQUOTE | QUOTE | PERIOD | MINUS | BACKSPACE | NEWLINE | WS | ID | NUMBER | ARGU );", 11, 0, input);

            throw nvae;
        }

        switch (alt11) {
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
                // Filter.g:1:38: DOUBLEQUOTE
                {
                mDOUBLEQUOTE(); 

                }
                break;
            case 7 :
                // Filter.g:1:50: QUOTE
                {
                mQUOTE(); 

                }
                break;
            case 8 :
                // Filter.g:1:56: PERIOD
                {
                mPERIOD(); 

                }
                break;
            case 9 :
                // Filter.g:1:63: MINUS
                {
                mMINUS(); 

                }
                break;
            case 10 :
                // Filter.g:1:69: BACKSPACE
                {
                mBACKSPACE(); 

                }
                break;
            case 11 :
                // Filter.g:1:79: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 12 :
                // Filter.g:1:87: WS
                {
                mWS(); 

                }
                break;
            case 13 :
                // Filter.g:1:90: ID
                {
                mID(); 

                }
                break;
            case 14 :
                // Filter.g:1:93: NUMBER
                {
                mNUMBER(); 

                }
                break;
            case 15 :
                // Filter.g:1:100: ARGU
                {
                mARGU(); 

                }
                break;

        }

    }


 

}