package SFLanguageParser;
// Generated from Expr.g4 by ANTLR 4.5
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExprParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, TRUE=14, FALSE=15, DOT=16, SEMICOL=17, 
		LPAREN=18, RPAREN=19, COMMA=20, QUOTE=21, IF=22, THEN=23, ELSE=24, END=25, 
		ID=26, INT=27, FLOAT=28, COMMENT=29, WS=30, STRING=31, LT_OP=32, GT_OP=33, 
		LE_OP=34, GE_OP=35, EQ_OP=36, NE_OP=37, AND_OP=38, OR_OP=39;
	public static final int
		RULE_do_action = 0, RULE_primary_expression = 1, RULE_statement = 2, RULE_if_then_else_expression = 3, 
		RULE_if_then_else_expression_compatible = 4, RULE_assignment_expression = 5, 
		RULE_logical_or_expression = 6, RULE_logical_and_expression = 7, RULE_inclusive_or_expression = 8, 
		RULE_exclusive_or_expression = 9, RULE_and_expression = 10, RULE_equality_expression = 11, 
		RULE_relational_expression = 12, RULE_additive_expression = 13, RULE_subtractive_expression = 14, 
		RULE_multiplicative_expression = 15, RULE_unary_expression = 16, RULE_postfix_expression = 17, 
		RULE_argument_expression_list = 18;
	public static final String[] ruleNames = {
		"do_action", "primary_expression", "statement", "if_then_else_expression", 
		"if_then_else_expression_compatible", "assignment_expression", "logical_or_expression", 
		"logical_and_expression", "inclusive_or_expression", "exclusive_or_expression", 
		"and_expression", "equality_expression", "relational_expression", "additive_expression", 
		"subtractive_expression", "multiplicative_expression", "unary_expression", 
		"postfix_expression", "argument_expression_list"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'='", "'|'", "'^'", "'&'", "'+'", "'-'", "'/'", "'%'", 
		"'*'", "'~'", "'!'", "'true'", "'false'", "'.'", "';'", "'('", "')'", 
		"','", "'''", "'if'", "''", "'else'", "'end'", null, null, null, null, 
		null, null, "'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'&&'", "'||'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "TRUE", "FALSE", "DOT", "SEMICOL", "LPAREN", "RPAREN", "COMMA", 
		"QUOTE", "IF", "THEN", "ELSE", "END", "ID", "INT", "FLOAT", "COMMENT", 
		"WS", "STRING", "LT_OP", "GT_OP", "LE_OP", "GE_OP", "EQ_OP", "NE_OP", 
		"AND_OP", "OR_OP"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Expr.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExprParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Do_actionContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Do_actionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_do_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterDo_action(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitDo_action(this);
		}
	}

	public final Do_actionContext do_action() throws RecognitionException {
		Do_actionContext _localctx = new Do_actionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_do_action);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__11) | (1L << T__12) | (1L << TRUE) | (1L << FALSE) | (1L << SEMICOL) | (1L << LPAREN) | (1L << IF) | (1L << ID) | (1L << INT) | (1L << STRING))) != 0)) {
				{
				{
				setState(38);
				statement();
				}
				}
				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Primary_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Token ID;
		public Token INT;
		public Token STRING;
		public TerminalNode ID() { return getToken(ExprParser.ID, 0); }
		public TerminalNode INT() { return getToken(ExprParser.INT, 0); }
		public TerminalNode STRING() { return getToken(ExprParser.STRING, 0); }
		public TerminalNode TRUE() { return getToken(ExprParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(ExprParser.FALSE, 0); }
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public Primary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterPrimary_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitPrimary_expression(this);
		}
	}

	public final Primary_expressionContext primary_expression() throws RecognitionException {
		Primary_expressionContext _localctx = new Primary_expressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_primary_expression);
		try {
			setState(58);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				((Primary_expressionContext)_localctx).ID = match(ID);
				((Primary_expressionContext)_localctx).tag = "Identifier"; ((Primary_expressionContext)_localctx).value =  (((Primary_expressionContext)_localctx).ID!=null?((Primary_expressionContext)_localctx).ID.getText():null);
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(46);
				((Primary_expressionContext)_localctx).INT = match(INT);
				((Primary_expressionContext)_localctx).tag = "Integer"; ((Primary_expressionContext)_localctx).value = Integer.parseInt((((Primary_expressionContext)_localctx).INT!=null?((Primary_expressionContext)_localctx).INT.getText():null));
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(48);
				((Primary_expressionContext)_localctx).STRING = match(STRING);
				((Primary_expressionContext)_localctx).tag = "String"; ((Primary_expressionContext)_localctx).value = (((Primary_expressionContext)_localctx).STRING!=null?((Primary_expressionContext)_localctx).STRING.getText():null);
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 4);
				{
				setState(50);
				match(TRUE);
				((Primary_expressionContext)_localctx).tag = "Boolean"; ((Primary_expressionContext)_localctx).value = true;
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 5);
				{
				setState(52);
				match(FALSE);
				((Primary_expressionContext)_localctx).tag = "Boolean"; ((Primary_expressionContext)_localctx).value = false;
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 6);
				{
				setState(54);
				match(LPAREN);
				setState(55);
				logical_or_expression();
				setState(56);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Assignment_expressionContext assignment_expression() {
			return getRuleContext(Assignment_expressionContext.class,0);
		}
		public TerminalNode SEMICOL() { return getToken(ExprParser.SEMICOL, 0); }
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public If_then_else_expressionContext if_then_else_expression() {
			return getRuleContext(If_then_else_expressionContext.class,0);
		}
		public If_then_else_expression_compatibleContext if_then_else_expression_compatible() {
			return getRuleContext(If_then_else_expression_compatibleContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(69);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(60);
				assignment_expression();
				setState(61);
				match(SEMICOL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(63);
				logical_or_expression();
				setState(64);
				match(SEMICOL);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(66);
				if_then_else_expression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(67);
				if_then_else_expression_compatible();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(68);
				match(SEMICOL);
				}
				break;
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_then_else_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public StatementContext statement;
		public List<StatementContext> then_stmts = new ArrayList<StatementContext>();
		public List<StatementContext> else_stmts = new ArrayList<StatementContext>();
		public TerminalNode IF() { return getToken(ExprParser.IF, 0); }
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public TerminalNode THEN() { return getToken(ExprParser.THEN, 0); }
		public TerminalNode END() { return getToken(ExprParser.END, 0); }
		public TerminalNode ELSE() { return getToken(ExprParser.ELSE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public If_then_else_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_then_else_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterIf_then_else_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitIf_then_else_expression(this);
		}
	}

	public final If_then_else_expressionContext if_then_else_expression() throws RecognitionException {
		If_then_else_expressionContext _localctx = new If_then_else_expressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_if_then_else_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(IF);
			setState(72);
			match(LPAREN);
			setState(73);
			logical_or_expression();
			setState(74);
			match(RPAREN);
			setState(75);
			match(THEN);
			setState(77); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(76);
				((If_then_else_expressionContext)_localctx).statement = statement();
				((If_then_else_expressionContext)_localctx).then_stmts.add(((If_then_else_expressionContext)_localctx).statement);
				}
				}
				setState(79); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__11) | (1L << T__12) | (1L << TRUE) | (1L << FALSE) | (1L << SEMICOL) | (1L << LPAREN) | (1L << IF) | (1L << ID) | (1L << INT) | (1L << STRING))) != 0) );
			setState(87);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(81);
				match(ELSE);
				setState(83); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(82);
					((If_then_else_expressionContext)_localctx).statement = statement();
					((If_then_else_expressionContext)_localctx).else_stmts.add(((If_then_else_expressionContext)_localctx).statement);
					}
					}
					setState(85); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__11) | (1L << T__12) | (1L << TRUE) | (1L << FALSE) | (1L << SEMICOL) | (1L << LPAREN) | (1L << IF) | (1L << ID) | (1L << INT) | (1L << STRING))) != 0) );
				}
			}

			setState(89);
			match(END);
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_then_else_expression_compatibleContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public StatementContext statement;
		public List<StatementContext> then_stmts = new ArrayList<StatementContext>();
		public List<StatementContext> else_stmts = new ArrayList<StatementContext>();
		public TerminalNode IF() { return getToken(ExprParser.IF, 0); }
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(ExprParser.ELSE, 0); }
		public If_then_else_expression_compatibleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_then_else_expression_compatible; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterIf_then_else_expression_compatible(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitIf_then_else_expression_compatible(this);
		}
	}

	public final If_then_else_expression_compatibleContext if_then_else_expression_compatible() throws RecognitionException {
		If_then_else_expression_compatibleContext _localctx = new If_then_else_expression_compatibleContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_if_then_else_expression_compatible);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			match(IF);
			setState(92);
			match(LPAREN);
			setState(93);
			logical_or_expression();
			setState(94);
			match(RPAREN);
			setState(104);
			switch (_input.LA(1)) {
			case T__0:
				{
				setState(95);
				match(T__0);
				setState(97); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(96);
					((If_then_else_expression_compatibleContext)_localctx).statement = statement();
					((If_then_else_expression_compatibleContext)_localctx).then_stmts.add(((If_then_else_expression_compatibleContext)_localctx).statement);
					}
					}
					setState(99); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__11) | (1L << T__12) | (1L << TRUE) | (1L << FALSE) | (1L << SEMICOL) | (1L << LPAREN) | (1L << IF) | (1L << ID) | (1L << INT) | (1L << STRING))) != 0) );
				setState(101);
				match(T__1);
				}
				break;
			case T__7:
			case T__11:
			case T__12:
			case TRUE:
			case FALSE:
			case SEMICOL:
			case LPAREN:
			case IF:
			case ID:
			case INT:
			case STRING:
				{
				setState(103);
				((If_then_else_expression_compatibleContext)_localctx).statement = statement();
				((If_then_else_expression_compatibleContext)_localctx).then_stmts.add(((If_then_else_expression_compatibleContext)_localctx).statement);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(118);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(106);
				match(ELSE);
				setState(116);
				switch (_input.LA(1)) {
				case T__0:
					{
					setState(107);
					match(T__0);
					setState(109); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(108);
						((If_then_else_expression_compatibleContext)_localctx).statement = statement();
						((If_then_else_expression_compatibleContext)_localctx).else_stmts.add(((If_then_else_expression_compatibleContext)_localctx).statement);
						}
						}
						setState(111); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__11) | (1L << T__12) | (1L << TRUE) | (1L << FALSE) | (1L << SEMICOL) | (1L << LPAREN) | (1L << IF) | (1L << ID) | (1L << INT) | (1L << STRING))) != 0) );
					setState(113);
					match(T__1);
					}
					break;
				case T__7:
				case T__11:
				case T__12:
				case TRUE:
				case FALSE:
				case SEMICOL:
				case LPAREN:
				case IF:
				case ID:
				case INT:
				case STRING:
					{
					setState(115);
					((If_then_else_expression_compatibleContext)_localctx).statement = statement();
					((If_then_else_expression_compatibleContext)_localctx).else_stmts.add(((If_then_else_expression_compatibleContext)_localctx).statement);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			}
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assignment_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Postfix_expressionContext postfix_expression() {
			return getRuleContext(Postfix_expressionContext.class,0);
		}
		public Logical_or_expressionContext logical_or_expression() {
			return getRuleContext(Logical_or_expressionContext.class,0);
		}
		public Assignment_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterAssignment_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitAssignment_expression(this);
		}
	}

	public final Assignment_expressionContext assignment_expression() throws RecognitionException {
		Assignment_expressionContext _localctx = new Assignment_expressionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assignment_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			postfix_expression();
			setState(121);
			match(T__2);
			setState(122);
			logical_or_expression();
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Logical_or_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public List<Logical_and_expressionContext> logical_and_expression() {
			return getRuleContexts(Logical_and_expressionContext.class);
		}
		public Logical_and_expressionContext logical_and_expression(int i) {
			return getRuleContext(Logical_and_expressionContext.class,i);
		}
		public List<TerminalNode> OR_OP() { return getTokens(ExprParser.OR_OP); }
		public TerminalNode OR_OP(int i) {
			return getToken(ExprParser.OR_OP, i);
		}
		public Logical_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_or_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterLogical_or_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitLogical_or_expression(this);
		}
	}

	public final Logical_or_expressionContext logical_or_expression() throws RecognitionException {
		Logical_or_expressionContext _localctx = new Logical_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_logical_or_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			logical_and_expression();
			setState(129);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR_OP) {
				{
				{
				setState(125);
				match(OR_OP);
				setState(126);
				logical_and_expression();
				}
				}
				setState(131);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Logical_and_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public List<Inclusive_or_expressionContext> inclusive_or_expression() {
			return getRuleContexts(Inclusive_or_expressionContext.class);
		}
		public Inclusive_or_expressionContext inclusive_or_expression(int i) {
			return getRuleContext(Inclusive_or_expressionContext.class,i);
		}
		public List<TerminalNode> AND_OP() { return getTokens(ExprParser.AND_OP); }
		public TerminalNode AND_OP(int i) {
			return getToken(ExprParser.AND_OP, i);
		}
		public Logical_and_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_and_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterLogical_and_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitLogical_and_expression(this);
		}
	}

	public final Logical_and_expressionContext logical_and_expression() throws RecognitionException {
		Logical_and_expressionContext _localctx = new Logical_and_expressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_logical_and_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			inclusive_or_expression();
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND_OP) {
				{
				{
				setState(133);
				match(AND_OP);
				setState(134);
				inclusive_or_expression();
				}
				}
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Inclusive_or_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Exclusive_or_expressionContext exclusive_or_expression;
		public List<Exclusive_or_expressionContext> eox = new ArrayList<Exclusive_or_expressionContext>();
		public List<Exclusive_or_expressionContext> exclusive_or_expression() {
			return getRuleContexts(Exclusive_or_expressionContext.class);
		}
		public Exclusive_or_expressionContext exclusive_or_expression(int i) {
			return getRuleContext(Exclusive_or_expressionContext.class,i);
		}
		public Inclusive_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inclusive_or_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterInclusive_or_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitInclusive_or_expression(this);
		}
	}

	public final Inclusive_or_expressionContext inclusive_or_expression() throws RecognitionException {
		Inclusive_or_expressionContext _localctx = new Inclusive_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_inclusive_or_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			exclusive_or_expression();
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(141);
				match(T__3);
				setState(142);
				((Inclusive_or_expressionContext)_localctx).exclusive_or_expression = exclusive_or_expression();
				((Inclusive_or_expressionContext)_localctx).eox.add(((Inclusive_or_expressionContext)_localctx).exclusive_or_expression);
				}
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Exclusive_or_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public List<And_expressionContext> and_expression() {
			return getRuleContexts(And_expressionContext.class);
		}
		public And_expressionContext and_expression(int i) {
			return getRuleContext(And_expressionContext.class,i);
		}
		public Exclusive_or_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exclusive_or_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterExclusive_or_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitExclusive_or_expression(this);
		}
	}

	public final Exclusive_or_expressionContext exclusive_or_expression() throws RecognitionException {
		Exclusive_or_expressionContext _localctx = new Exclusive_or_expressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_exclusive_or_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			and_expression();
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(149);
				match(T__4);
				setState(150);
				and_expression();
				}
				}
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class And_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public List<Equality_expressionContext> equality_expression() {
			return getRuleContexts(Equality_expressionContext.class);
		}
		public Equality_expressionContext equality_expression(int i) {
			return getRuleContext(Equality_expressionContext.class,i);
		}
		public And_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterAnd_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitAnd_expression(this);
		}
	}

	public final And_expressionContext and_expression() throws RecognitionException {
		And_expressionContext _localctx = new And_expressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_and_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			equality_expression();
			setState(161);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(157);
				match(T__5);
				setState(158);
				equality_expression();
				}
				}
				setState(163);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Equality_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Token EQ_OP;
		public List<Token> operators = new ArrayList<Token>();
		public Token NE_OP;
		public List<Relational_expressionContext> relational_expression() {
			return getRuleContexts(Relational_expressionContext.class);
		}
		public Relational_expressionContext relational_expression(int i) {
			return getRuleContext(Relational_expressionContext.class,i);
		}
		public TerminalNode EQ_OP() { return getToken(ExprParser.EQ_OP, 0); }
		public TerminalNode NE_OP() { return getToken(ExprParser.NE_OP, 0); }
		public Equality_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equality_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterEquality_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitEquality_expression(this);
		}
	}

	public final Equality_expressionContext equality_expression() throws RecognitionException {
		Equality_expressionContext _localctx = new Equality_expressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_equality_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			relational_expression();
			setState(170);
			_la = _input.LA(1);
			if (_la==EQ_OP || _la==NE_OP) {
				{
				setState(167);
				switch (_input.LA(1)) {
				case EQ_OP:
					{
					setState(165);
					((Equality_expressionContext)_localctx).EQ_OP = match(EQ_OP);
					((Equality_expressionContext)_localctx).operators.add(((Equality_expressionContext)_localctx).EQ_OP);
					}
					break;
				case NE_OP:
					{
					setState(166);
					((Equality_expressionContext)_localctx).NE_OP = match(NE_OP);
					((Equality_expressionContext)_localctx).operators.add(((Equality_expressionContext)_localctx).NE_OP);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(169);
				relational_expression();
				}
			}

			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Relational_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Token LT_OP;
		public List<Token> operators = new ArrayList<Token>();
		public Token GT_OP;
		public Token LE_OP;
		public Token GE_OP;
		public List<Additive_expressionContext> additive_expression() {
			return getRuleContexts(Additive_expressionContext.class);
		}
		public Additive_expressionContext additive_expression(int i) {
			return getRuleContext(Additive_expressionContext.class,i);
		}
		public TerminalNode LT_OP() { return getToken(ExprParser.LT_OP, 0); }
		public TerminalNode GT_OP() { return getToken(ExprParser.GT_OP, 0); }
		public TerminalNode LE_OP() { return getToken(ExprParser.LE_OP, 0); }
		public TerminalNode GE_OP() { return getToken(ExprParser.GE_OP, 0); }
		public Relational_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relational_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterRelational_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitRelational_expression(this);
		}
	}

	public final Relational_expressionContext relational_expression() throws RecognitionException {
		Relational_expressionContext _localctx = new Relational_expressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_relational_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			additive_expression();
			setState(180);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT_OP) | (1L << GT_OP) | (1L << LE_OP) | (1L << GE_OP))) != 0)) {
				{
				setState(177);
				switch (_input.LA(1)) {
				case LT_OP:
					{
					setState(173);
					((Relational_expressionContext)_localctx).LT_OP = match(LT_OP);
					((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).LT_OP);
					}
					break;
				case GT_OP:
					{
					setState(174);
					((Relational_expressionContext)_localctx).GT_OP = match(GT_OP);
					((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).GT_OP);
					}
					break;
				case LE_OP:
					{
					setState(175);
					((Relational_expressionContext)_localctx).LE_OP = match(LE_OP);
					((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).LE_OP);
					}
					break;
				case GE_OP:
					{
					setState(176);
					((Relational_expressionContext)_localctx).GE_OP = match(GE_OP);
					((Relational_expressionContext)_localctx).operators.add(((Relational_expressionContext)_localctx).GE_OP);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(179);
				additive_expression();
				}
			}

			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Additive_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public List<Subtractive_expressionContext> subtractive_expression() {
			return getRuleContexts(Subtractive_expressionContext.class);
		}
		public Subtractive_expressionContext subtractive_expression(int i) {
			return getRuleContext(Subtractive_expressionContext.class,i);
		}
		public Additive_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additive_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterAdditive_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitAdditive_expression(this);
		}
	}

	public final Additive_expressionContext additive_expression() throws RecognitionException {
		Additive_expressionContext _localctx = new Additive_expressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_additive_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			subtractive_expression();
			setState(187);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(183);
				match(T__6);
				setState(184);
				subtractive_expression();
				}
				}
				setState(189);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Subtractive_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public List<Multiplicative_expressionContext> multiplicative_expression() {
			return getRuleContexts(Multiplicative_expressionContext.class);
		}
		public Multiplicative_expressionContext multiplicative_expression(int i) {
			return getRuleContext(Multiplicative_expressionContext.class,i);
		}
		public Subtractive_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subtractive_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterSubtractive_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitSubtractive_expression(this);
		}
	}

	public final Subtractive_expressionContext subtractive_expression() throws RecognitionException {
		Subtractive_expressionContext _localctx = new Subtractive_expressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_subtractive_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			multiplicative_expression();
			setState(195);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(191);
				match(T__7);
				setState(192);
				multiplicative_expression();
				}
				}
				setState(197);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Multiplicative_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Token s9;
		public List<Token> operators = new ArrayList<Token>();
		public Token s10;
		public Token s11;
		public List<Unary_expressionContext> unary_expression() {
			return getRuleContexts(Unary_expressionContext.class);
		}
		public Unary_expressionContext unary_expression(int i) {
			return getRuleContext(Unary_expressionContext.class,i);
		}
		public Multiplicative_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicative_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterMultiplicative_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitMultiplicative_expression(this);
		}
	}

	public final Multiplicative_expressionContext multiplicative_expression() throws RecognitionException {
		Multiplicative_expressionContext _localctx = new Multiplicative_expressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_multiplicative_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			unary_expression();
			setState(207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__9) | (1L << T__10))) != 0)) {
				{
				{
				setState(202);
				switch (_input.LA(1)) {
				case T__8:
					{
					setState(199);
					((Multiplicative_expressionContext)_localctx).s9 = match(T__8);
					((Multiplicative_expressionContext)_localctx).operators.add(((Multiplicative_expressionContext)_localctx).s9);
					}
					break;
				case T__9:
					{
					setState(200);
					((Multiplicative_expressionContext)_localctx).s10 = match(T__9);
					((Multiplicative_expressionContext)_localctx).operators.add(((Multiplicative_expressionContext)_localctx).s10);
					}
					break;
				case T__10:
					{
					setState(201);
					((Multiplicative_expressionContext)_localctx).s11 = match(T__10);
					((Multiplicative_expressionContext)_localctx).operators.add(((Multiplicative_expressionContext)_localctx).s11);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(204);
				unary_expression();
				}
				}
				setState(209);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Unary_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Token op;
		public Postfix_expressionContext postfix_expression() {
			return getRuleContext(Postfix_expressionContext.class,0);
		}
		public Unary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterUnary_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitUnary_expression(this);
		}
	}

	public final Unary_expressionContext unary_expression() throws RecognitionException {
		Unary_expressionContext _localctx = new Unary_expressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_unary_expression);
		try {
			setState(217);
			switch (_input.LA(1)) {
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				setState(210);
				((Unary_expressionContext)_localctx).op = match(T__7);
				setState(211);
				postfix_expression();
				}
				break;
			case T__11:
			case T__12:
			case TRUE:
			case FALSE:
			case LPAREN:
			case ID:
			case INT:
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(214);
				switch (_input.LA(1)) {
				case T__11:
					{
					setState(212);
					((Unary_expressionContext)_localctx).op = match(T__11);
					}
					break;
				case T__12:
					{
					setState(213);
					((Unary_expressionContext)_localctx).op = match(T__12);
					}
					break;
				case TRUE:
				case FALSE:
				case LPAREN:
				case ID:
				case INT:
				case STRING:
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(216);
				postfix_expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Postfix_expressionContext extends ParserRuleContext {
		public String tag;
		public Object value;
		public Postfix_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfix_expression; }
	 
		public Postfix_expressionContext() { }
		public void copyFrom(Postfix_expressionContext ctx) {
			super.copyFrom(ctx);
			this.tag = ctx.tag;
			this.value = ctx.value;
		}
	}
	public static class FunctionCallContext extends Postfix_expressionContext {
		public Primary_expressionContext primary_expression() {
			return getRuleContext(Primary_expressionContext.class,0);
		}
		public Argument_expression_listContext argument_expression_list() {
			return getRuleContext(Argument_expression_listContext.class,0);
		}
		public FunctionCallContext(Postfix_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitFunctionCall(this);
		}
	}
	public static class ObjectExpressionContext extends Postfix_expressionContext {
		public List<Primary_expressionContext> primary_expression() {
			return getRuleContexts(Primary_expressionContext.class);
		}
		public Primary_expressionContext primary_expression(int i) {
			return getRuleContext(Primary_expressionContext.class,i);
		}
		public ObjectExpressionContext(Postfix_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterObjectExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitObjectExpression(this);
		}
	}

	public final Postfix_expressionContext postfix_expression() throws RecognitionException {
		Postfix_expressionContext _localctx = new Postfix_expressionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_postfix_expression);
		int _la;
		try {
			setState(236);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				_localctx = new FunctionCallContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(219);
				primary_expression();
				setState(226);
				switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
				case 1:
					{
					setState(220);
					match(LPAREN);
					setState(221);
					match(RPAREN);
					}
					break;
				case 2:
					{
					setState(222);
					match(LPAREN);
					setState(223);
					argument_expression_list();
					setState(224);
					match(RPAREN);
					}
					break;
				}
				}
				break;
			case 2:
				_localctx = new ObjectExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(228);
				primary_expression();
				setState(233);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DOT) {
					{
					{
					setState(229);
					match(DOT);
					setState(230);
					primary_expression();
					}
					}
					setState(235);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Argument_expression_listContext extends ParserRuleContext {
		public List<Logical_or_expressionContext> logical_or_expression() {
			return getRuleContexts(Logical_or_expressionContext.class);
		}
		public Logical_or_expressionContext logical_or_expression(int i) {
			return getRuleContext(Logical_or_expressionContext.class,i);
		}
		public Argument_expression_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument_expression_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterArgument_expression_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitArgument_expression_list(this);
		}
	}

	public final Argument_expression_listContext argument_expression_list() throws RecognitionException {
		Argument_expression_listContext _localctx = new Argument_expression_listContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_argument_expression_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			logical_or_expression();
			setState(243);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(239);
				match(COMMA);
				setState(240);
				logical_or_expression();
				}
				}
				setState(245);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (Exception e) {
			 throw e; 
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3)\u00f9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\7\2*\n\2\f\2\16\2-\13\2\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3=\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\5\4H\n\4\3\5\3\5\3\5\3\5\3\5\3\5\6\5P\n\5\r\5\16\5Q\3\5\3\5"+
		"\6\5V\n\5\r\5\16\5W\5\5Z\n\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\6\6d\n\6"+
		"\r\6\16\6e\3\6\3\6\3\6\5\6k\n\6\3\6\3\6\3\6\6\6p\n\6\r\6\16\6q\3\6\3\6"+
		"\3\6\5\6w\n\6\5\6y\n\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\7\b\u0082\n\b\f\b\16"+
		"\b\u0085\13\b\3\t\3\t\3\t\7\t\u008a\n\t\f\t\16\t\u008d\13\t\3\n\3\n\3"+
		"\n\7\n\u0092\n\n\f\n\16\n\u0095\13\n\3\13\3\13\3\13\7\13\u009a\n\13\f"+
		"\13\16\13\u009d\13\13\3\f\3\f\3\f\7\f\u00a2\n\f\f\f\16\f\u00a5\13\f\3"+
		"\r\3\r\3\r\5\r\u00aa\n\r\3\r\5\r\u00ad\n\r\3\16\3\16\3\16\3\16\3\16\5"+
		"\16\u00b4\n\16\3\16\5\16\u00b7\n\16\3\17\3\17\3\17\7\17\u00bc\n\17\f\17"+
		"\16\17\u00bf\13\17\3\20\3\20\3\20\7\20\u00c4\n\20\f\20\16\20\u00c7\13"+
		"\20\3\21\3\21\3\21\3\21\5\21\u00cd\n\21\3\21\7\21\u00d0\n\21\f\21\16\21"+
		"\u00d3\13\21\3\22\3\22\3\22\3\22\5\22\u00d9\n\22\3\22\5\22\u00dc\n\22"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00e5\n\23\3\23\3\23\3\23\7\23"+
		"\u00ea\n\23\f\23\16\23\u00ed\13\23\5\23\u00ef\n\23\3\24\3\24\3\24\7\24"+
		"\u00f4\n\24\f\24\16\24\u00f7\13\24\3\24\2\2\25\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \"$&\2\2\u010e\2+\3\2\2\2\4<\3\2\2\2\6G\3\2\2\2\bI\3\2"+
		"\2\2\n]\3\2\2\2\fz\3\2\2\2\16~\3\2\2\2\20\u0086\3\2\2\2\22\u008e\3\2\2"+
		"\2\24\u0096\3\2\2\2\26\u009e\3\2\2\2\30\u00a6\3\2\2\2\32\u00ae\3\2\2\2"+
		"\34\u00b8\3\2\2\2\36\u00c0\3\2\2\2 \u00c8\3\2\2\2\"\u00db\3\2\2\2$\u00ee"+
		"\3\2\2\2&\u00f0\3\2\2\2(*\5\6\4\2)(\3\2\2\2*-\3\2\2\2+)\3\2\2\2+,\3\2"+
		"\2\2,\3\3\2\2\2-+\3\2\2\2./\7\34\2\2/=\b\3\1\2\60\61\7\35\2\2\61=\b\3"+
		"\1\2\62\63\7!\2\2\63=\b\3\1\2\64\65\7\20\2\2\65=\b\3\1\2\66\67\7\21\2"+
		"\2\67=\b\3\1\289\7\24\2\29:\5\16\b\2:;\7\25\2\2;=\3\2\2\2<.\3\2\2\2<\60"+
		"\3\2\2\2<\62\3\2\2\2<\64\3\2\2\2<\66\3\2\2\2<8\3\2\2\2=\5\3\2\2\2>?\5"+
		"\f\7\2?@\7\23\2\2@H\3\2\2\2AB\5\16\b\2BC\7\23\2\2CH\3\2\2\2DH\5\b\5\2"+
		"EH\5\n\6\2FH\7\23\2\2G>\3\2\2\2GA\3\2\2\2GD\3\2\2\2GE\3\2\2\2GF\3\2\2"+
		"\2H\7\3\2\2\2IJ\7\30\2\2JK\7\24\2\2KL\5\16\b\2LM\7\25\2\2MO\7\31\2\2N"+
		"P\5\6\4\2ON\3\2\2\2PQ\3\2\2\2QO\3\2\2\2QR\3\2\2\2RY\3\2\2\2SU\7\32\2\2"+
		"TV\5\6\4\2UT\3\2\2\2VW\3\2\2\2WU\3\2\2\2WX\3\2\2\2XZ\3\2\2\2YS\3\2\2\2"+
		"YZ\3\2\2\2Z[\3\2\2\2[\\\7\33\2\2\\\t\3\2\2\2]^\7\30\2\2^_\7\24\2\2_`\5"+
		"\16\b\2`j\7\25\2\2ac\7\3\2\2bd\5\6\4\2cb\3\2\2\2de\3\2\2\2ec\3\2\2\2e"+
		"f\3\2\2\2fg\3\2\2\2gh\7\4\2\2hk\3\2\2\2ik\5\6\4\2ja\3\2\2\2ji\3\2\2\2"+
		"kx\3\2\2\2lv\7\32\2\2mo\7\3\2\2np\5\6\4\2on\3\2\2\2pq\3\2\2\2qo\3\2\2"+
		"\2qr\3\2\2\2rs\3\2\2\2st\7\4\2\2tw\3\2\2\2uw\5\6\4\2vm\3\2\2\2vu\3\2\2"+
		"\2wy\3\2\2\2xl\3\2\2\2xy\3\2\2\2y\13\3\2\2\2z{\5$\23\2{|\7\5\2\2|}\5\16"+
		"\b\2}\r\3\2\2\2~\u0083\5\20\t\2\177\u0080\7)\2\2\u0080\u0082\5\20\t\2"+
		"\u0081\177\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084"+
		"\3\2\2\2\u0084\17\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u008b\5\22\n\2\u0087"+
		"\u0088\7(\2\2\u0088\u008a\5\22\n\2\u0089\u0087\3\2\2\2\u008a\u008d\3\2"+
		"\2\2\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\21\3\2\2\2\u008d\u008b"+
		"\3\2\2\2\u008e\u0093\5\24\13\2\u008f\u0090\7\6\2\2\u0090\u0092\5\24\13"+
		"\2\u0091\u008f\3\2\2\2\u0092\u0095\3\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094"+
		"\3\2\2\2\u0094\23\3\2\2\2\u0095\u0093\3\2\2\2\u0096\u009b\5\26\f\2\u0097"+
		"\u0098\7\7\2\2\u0098\u009a\5\26\f\2\u0099\u0097\3\2\2\2\u009a\u009d\3"+
		"\2\2\2\u009b\u0099\3\2\2\2\u009b\u009c\3\2\2\2\u009c\25\3\2\2\2\u009d"+
		"\u009b\3\2\2\2\u009e\u00a3\5\30\r\2\u009f\u00a0\7\b\2\2\u00a0\u00a2\5"+
		"\30\r\2\u00a1\u009f\3\2\2\2\u00a2\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3"+
		"\u00a4\3\2\2\2\u00a4\27\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a6\u00ac\5\32\16"+
		"\2\u00a7\u00aa\7&\2\2\u00a8\u00aa\7\'\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00a8"+
		"\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ad\5\32\16\2\u00ac\u00a9\3\2\2\2"+
		"\u00ac\u00ad\3\2\2\2\u00ad\31\3\2\2\2\u00ae\u00b6\5\34\17\2\u00af\u00b4"+
		"\7\"\2\2\u00b0\u00b4\7#\2\2\u00b1\u00b4\7$\2\2\u00b2\u00b4\7%\2\2\u00b3"+
		"\u00af\3\2\2\2\u00b3\u00b0\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b2\3\2"+
		"\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b7\5\34\17\2\u00b6\u00b3\3\2\2\2\u00b6"+
		"\u00b7\3\2\2\2\u00b7\33\3\2\2\2\u00b8\u00bd\5\36\20\2\u00b9\u00ba\7\t"+
		"\2\2\u00ba\u00bc\5\36\20\2\u00bb\u00b9\3\2\2\2\u00bc\u00bf\3\2\2\2\u00bd"+
		"\u00bb\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\35\3\2\2\2\u00bf\u00bd\3\2\2"+
		"\2\u00c0\u00c5\5 \21\2\u00c1\u00c2\7\n\2\2\u00c2\u00c4\5 \21\2\u00c3\u00c1"+
		"\3\2\2\2\u00c4\u00c7\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6"+
		"\37\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c8\u00d1\5\"\22\2\u00c9\u00cd\7\13"+
		"\2\2\u00ca\u00cd\7\f\2\2\u00cb\u00cd\7\r\2\2\u00cc\u00c9\3\2\2\2\u00cc"+
		"\u00ca\3\2\2\2\u00cc\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00d0\5\""+
		"\22\2\u00cf\u00cc\3\2\2\2\u00d0\u00d3\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1"+
		"\u00d2\3\2\2\2\u00d2!\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d4\u00d5\7\n\2\2"+
		"\u00d5\u00dc\5$\23\2\u00d6\u00d9\7\16\2\2\u00d7\u00d9\7\17\2\2\u00d8\u00d6"+
		"\3\2\2\2\u00d8\u00d7\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da\3\2\2\2\u00da"+
		"\u00dc\5$\23\2\u00db\u00d4\3\2\2\2\u00db\u00d8\3\2\2\2\u00dc#\3\2\2\2"+
		"\u00dd\u00e4\5\4\3\2\u00de\u00df\7\24\2\2\u00df\u00e5\7\25\2\2\u00e0\u00e1"+
		"\7\24\2\2\u00e1\u00e2\5&\24\2\u00e2\u00e3\7\25\2\2\u00e3\u00e5\3\2\2\2"+
		"\u00e4\u00de\3\2\2\2\u00e4\u00e0\3\2\2\2\u00e5\u00ef\3\2\2\2\u00e6\u00eb"+
		"\5\4\3\2\u00e7\u00e8\7\22\2\2\u00e8\u00ea\5\4\3\2\u00e9\u00e7\3\2\2\2"+
		"\u00ea\u00ed\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ef"+
		"\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00dd\3\2\2\2\u00ee\u00e6\3\2\2\2\u00ef"+
		"%\3\2\2\2\u00f0\u00f5\5\16\b\2\u00f1\u00f2\7\26\2\2\u00f2\u00f4\5\16\b"+
		"\2\u00f3\u00f1\3\2\2\2\u00f4\u00f7\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f5\u00f6"+
		"\3\2\2\2\u00f6\'\3\2\2\2\u00f7\u00f5\3\2\2\2 +<GQWYejqvx\u0083\u008b\u0093"+
		"\u009b\u00a3\u00a9\u00ac\u00b3\u00b6\u00bd\u00c5\u00cc\u00d1\u00d8\u00db"+
		"\u00e4\u00eb\u00ee\u00f5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}