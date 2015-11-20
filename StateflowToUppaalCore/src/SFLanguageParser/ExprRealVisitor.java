package SFLanguageParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created with IntelliJ IDEA.
 * User: wind
 * Date: 13-9-28
 * To change this template use File | Settings | File Templates.
 */
public class ExprRealVisitor implements ExprVisitor<Map<String, Object>> {
	/**
	 * Visit a parse tree produced by {@link ExprParser#inclusive_or_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitInclusive_or_expression(@NotNull ExprParser.Inclusive_or_expressionContext ctx) {

		List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
		Map<String, Object> node = new HashMap<String, Object>();

		if (ctx.exclusive_or_expression().size() > 1)
		{
			for (ExprParser.Exclusive_or_expressionContext e: ctx.exclusive_or_expression())
			{
				argList.add(visitExclusive_or_expression(e));
			}

			node.put("tag", "OP");
			node.put("value", "|");
			node.put("arg-list", argList);
		}
		else
		{
			node = visitExclusive_or_expression(ctx.exclusive_or_expression(0));
		}

		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#assignment_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitAssignment_expression(@NotNull ExprParser.Assignment_expressionContext ctx) {

		List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();

		argList.add(visitPostfix_expression(ctx.postfix_expression()));
		argList.add(visitLogical_or_expression(ctx.logical_or_expression()));

		Map<String, Object> node = new HashMap<String, Object>();

		node.put("tag", "OP");
		node.put("value", "=");
		node.put("arg-list", argList);

		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#multiplicative_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitMultiplicative_expression(@NotNull ExprParser.Multiplicative_expressionContext ctx) {
		Map<String, Object> node;

		int count = ctx.operators.size();
		if (count > 0)
		{
			node = visitUnary_expression(ctx.unary_expression(ctx.unary_expression().size() - 1));
			for (int i = count - 1; i >= 0 ; i--)
			{
				List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
				Map<String, Object> tmp = new HashMap<String, Object>();

				argList.add(visitUnary_expression(ctx.unary_expression(i)));
				argList.add(node);

				tmp.put("tag", "OP");
				tmp.put("value", ctx.operators.get(i).getText());
				tmp.put("arg-list", argList);

				node = tmp;
			}
		}
		else
		{
			node = visitUnary_expression(ctx.unary_expression(0));
		}

		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#relational_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitRelational_expression(@NotNull ExprParser.Relational_expressionContext ctx) {
		Map<String, Object> node = new HashMap<String, Object>();

		int count = ctx.operators.size();
		if (count > 0)
		{
			node = visitAdditive_expression((ctx.additive_expression(ctx.additive_expression().size() - 1)));
			for (int i = count - 1; i >= 0 ; i--)
			{
				List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
				Map<String, Object> tmp = new HashMap<String, Object>();

				argList.add(visitAdditive_expression(ctx.additive_expression(i)));
				argList.add(node);

				tmp.put("tag", "OP");
				tmp.put("value", ctx.operators.get(i).getText());
				tmp.put("arg-list", argList);

				node = tmp;
			}
		}
		else
		{
			node = visitAdditive_expression(ctx.additive_expression(0));
		}

		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by .
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> visitFunctionCall(@NotNull ExprParser.FunctionCallContext ctx) {
		Map<String, Object> node = new HashMap<String, Object>();
		
		//testing
		//System.err.println(ctx.primary_expression().getText());
		//System.err.println(ctx.argument_expression_list().getText());
		
		List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
		if (ctx.argument_expression_list() != null)
		{
			List<Map<String, Object>> list = (List<Map<String, Object>>)visitArgument_expression_list(ctx.argument_expression_list()).get("arg-list");
			if (list != null)
			{
				argList.addAll(list);
			}
		}
		node.put("tag", "FunctionCall");
		node.put("value", ctx.primary_expression().getText());
		node.put("arg-list", argList);
		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#exclusive_or_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitExclusive_or_expression(@NotNull ExprParser.Exclusive_or_expressionContext ctx) {
		List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
		Map<String, Object> node = new HashMap<String, Object>();

		if (ctx.and_expression().size() > 1)
		{
			for (ExprParser.And_expressionContext e: ctx.and_expression())
			{
				argList.add(visitAnd_expression(e));
			}
			node.put("tag", "OP");
			node.put("value", "^");
			node.put("arg-list", argList);
		}
		else
		{
			node = visitAnd_expression(ctx.and_expression(0));
		}
		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#statement}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitStatement(@NotNull ExprParser.StatementContext ctx) {

		Map<String, Object> node;

		if (ctx.assignment_expression() != null)
		{
			node = visitAssignment_expression(ctx.assignment_expression());
		}
		else if (ctx.logical_or_expression() != null)
		{
			node = visitLogical_or_expression(ctx.logical_or_expression());
		}
		else if (ctx.if_then_else_expression() != null)
		{
			node = visitIf_then_else_expression(ctx.if_then_else_expression());
		}
		else if (ctx.if_then_else_expression_compatible() != null)
		{
			node = visitIf_then_else_expression_compatible(ctx.if_then_else_expression_compatible());
		}
		else
		{
			node = null;
		}
		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#if_then_else_expression_compatible}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitIf_then_else_expression_compatible(@NotNull ExprParser.If_then_else_expression_compatibleContext ctx) {
		List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();

		// condition
		argList.add(visitLogical_or_expression(ctx.logical_or_expression()));

		// then
		List<Map<String, Object>> thenStmtList = new ArrayList<Map<String, Object>>();

		for (ExprParser.StatementContext e: ctx.then_stmts)
		{
			thenStmtList.add(visitStatement(e));
		}

		Map<String, Object> thenClause = new HashMap<String, Object>();

		thenClause.put("tag", "Keyword");
		thenClause.put("value", "do");
		thenClause.put("arg-list", thenStmtList);

		// else
		List<Map<String, Object>> elseStmtList = new ArrayList<Map<String, Object>>();

		for (ExprParser.StatementContext e: ctx.else_stmts)
		{
			elseStmtList.add(visitStatement(e));
		}

		Map<String, Object> elseClause = new HashMap<String, Object>();
		elseClause.put("tag", "Keyword");
		elseClause.put("value", "do");
		elseClause.put("arg-list", elseStmtList);

		argList.add(thenClause);
		argList.add(elseClause);

		Map<String, Object> node = new HashMap<String, Object>();

		node.put("tag", "Keyword");
		node.put("value", "if");
		node.put("arg-list", argList);

		return node;   //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#logical_and_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitLogical_and_expression(@NotNull ExprParser.Logical_and_expressionContext ctx) {
		List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
		Map<String, Object> node = new HashMap<String, Object>();

		if (ctx.inclusive_or_expression().size() > 1)
		{
			for (ExprParser.Inclusive_or_expressionContext e: ctx.inclusive_or_expression())
			{
				argList.add(visitInclusive_or_expression(e));
			}

			node.put("tag", "OP");
			node.put("value", "&&");
			node.put("arg-list", argList);
		}
		else
		{
			node = visitInclusive_or_expression(ctx.inclusive_or_expression(0));
		}

		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#additive_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitAdditive_expression(@NotNull ExprParser.Additive_expressionContext ctx) {
        Map<String, Object> node = new HashMap<String, Object>();

		if (ctx.subtractive_expression().size() > 1)
		{
			List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
			for (ExprParser.Subtractive_expressionContext e: ctx.subtractive_expression())
			{
				argList.add(visitSubtractive_expression(e));
			}
			node.put("tag", "OP");
			node.put("value", "+");
			node.put("arg-list", argList);
		}
		else
		{
			node = visitSubtractive_expression(ctx.subtractive_expression(0));
		}


		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#if_then_else_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitIf_then_else_expression(@NotNull ExprParser.If_then_else_expressionContext ctx) {
		List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();

		// condition
		argList.add(visitLogical_or_expression(ctx.logical_or_expression()));

		// then
		List<Map<String, Object>> thenStmtList = new ArrayList<Map<String, Object>>();

		for (ExprParser.StatementContext e: ctx.then_stmts)
		{
			thenStmtList.add(visitStatement(e));
		}

		Map<String, Object> thenClause = new HashMap<String, Object>();

		thenClause.put("tag", "Keyword");
		thenClause.put("value", "do");
		thenClause.put("arg-list", thenStmtList);

		// else
		List<Map<String, Object>> elseStmtList = new ArrayList<Map<String, Object>>();

		for (ExprParser.StatementContext e: ctx.else_stmts)
		{
			elseStmtList.add(visitStatement(e));
		}

		Map<String, Object> elseClause = new HashMap<String, Object>();
		elseClause.put("tag", "Keyword");
		elseClause.put("value", "do");
		elseClause.put("arg-list", elseStmtList);

		argList.add(thenClause);
		argList.add(elseClause);

		Map<String, Object> node = new HashMap<String, Object>();

		node.put("tag", "Keyword");
		node.put("value", "if");
		node.put("arg-list", argList);

		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#postfix_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	public Map<String, Object> visitPostfix_expression(@NotNull ExprParser.Postfix_expressionContext ctx) {

		if (ctx instanceof ExprParser.ObjectExpressionContext)
		{
			return visitObjectExpression((ExprParser.ObjectExpressionContext) ctx);
		}
		else if (ctx instanceof ExprParser.FunctionCallContext)
		{
			return visitFunctionCall((ExprParser.FunctionCallContext) ctx);
		}
		else {
			System.err.println("This should not happen.");
			return  null;
		}
	}

	@Override
	public Map<String, Object> visitObjectExpression(@NotNull ExprParser.ObjectExpressionContext ctx) {
		Map<String, Object> node = new HashMap<String, Object>();

		if (ctx.primary_expression().size() > 1)
		{
			List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
			for (ExprParser.Primary_expressionContext e: ctx.primary_expression())
			{
				argList.add(visitPrimary_expression(e));
			}
			node.put("tag", "OP");
			node.put("value", ".");
			node.put("arg-list", argList);
		}
		else
		{
			node = visitPrimary_expression(ctx.primary_expression(0));
		}
		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#do_action}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitDo_action(@NotNull ExprParser.Do_actionContext ctx) {
		List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();

		Map<String, Object> node = new HashMap<String, Object>();

		node.put("tag", "Keyword");
		node.put("value", "do");

		if (ctx.statement().size() > 0)
		{
			for (ExprParser.StatementContext e: ctx.statement())
			{
				Map<String, Object> stmt = visitStatement(e);
				if (stmt != null)
				{
					argList.add(stmt);
				}
			}
		}
		node.put("arg-list", argList);
		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#equality_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitEquality_expression(@NotNull ExprParser.Equality_expressionContext ctx) {
		Map<String, Object> node;

		// These code snips looks so similar ...
		int count = ctx.operators.size();
		if (count > 0)
		{
			node = visitRelational_expression(ctx.relational_expression(ctx.relational_expression().size() - 1));
			for (int i = count - 1; i >= 0 ; i--)
			{
				List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
				Map<String, Object> tmp = new HashMap<String, Object>();

				argList.add(visitRelational_expression(ctx.relational_expression(i)));
				argList.add(node);

				tmp.put("tag", "OP");
				tmp.put("value", ctx.operators.get(i).getText());
				tmp.put("arg-list", argList);

				node = tmp;
			}
		}
		else
		{
			node = visitRelational_expression(ctx.relational_expression(0));
		}

		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#primary_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitPrimary_expression(@NotNull ExprParser.Primary_expressionContext ctx) {
		Map<String, Object> node = new HashMap<String, Object>();

		if (ctx.logical_or_expression() != null)
		{
			node = visitLogical_or_expression(ctx.logical_or_expression());
		}
		else
		{
			node.put("tag", ctx.tag);
			node.put("value", ctx.value);
			node.put("arg-list", new ArrayList<Map<String, Object>>());
		}

		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#and_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitAnd_expression(@NotNull ExprParser.And_expressionContext ctx) {
		Map<String, Object> node = new HashMap<String, Object>();

		if (ctx.equality_expression().size() > 1)
		{
			List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
			for (ExprParser.Equality_expressionContext e: ctx.equality_expression())
			{
				argList.add(visitEquality_expression(e));
			}
			node.put("tag", "OP");
			node.put("value", "&");
			node.put("arg-list", argList);
		}
		else
		{
			node = visitEquality_expression(ctx.equality_expression(0));
		}

		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#unary_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitUnary_expression(@NotNull ExprParser.Unary_expressionContext ctx) {
		Map<String, Object> node = new HashMap<String, Object>();

		if (ctx.op != null)
		{
			node.put("tag", "OP");
			node.put("value", ctx.op.getText());
			List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();

			argList.add(visitPostfix_expression(ctx.postfix_expression()));
			node.put("arg-list", argList);
		}
		else
		{
			node = visitPostfix_expression(ctx.postfix_expression());
		}

		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#logical_or_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitLogical_or_expression(@NotNull ExprParser.Logical_or_expressionContext ctx) {
		Map<String, Object> node = new HashMap<String, Object>();

		if (ctx.logical_and_expression().size() > 1)
		{
			List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
			for (ExprParser.Logical_and_expressionContext e: ctx.logical_and_expression())
			{
				argList.add(visitLogical_and_expression(e));
			}
			node.put("tag", "OP");
			node.put("value", "||");
			node.put("arg-list", argList);
		}
		else
		{
			node = visitLogical_and_expression(ctx.logical_and_expression(0));
		}

		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ExprParser#subtractive_expression}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitSubtractive_expression(@NotNull ExprParser.Subtractive_expressionContext ctx) {
		Map<String, Object> node = new HashMap<String, Object>();

		if (ctx.multiplicative_expression().size() > 1)
		{
			List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();
			for (ExprParser.Multiplicative_expressionContext e: ctx.multiplicative_expression())
			{
				argList.add(visitMultiplicative_expression(e));
			}
			node.put("tag", "OP");
			node.put("value", "-");
			node.put("arg-list", argList);
		}
		else
		{
			node = visitMultiplicative_expression(ctx.multiplicative_expression(0));
		}


		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Visit a parse tree produced by {@link ast.ExprParser#argument_expression_list}.
	 *
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	@Override
	public Map<String, Object> visitArgument_expression_list(@NotNull ExprParser.Argument_expression_listContext ctx) {
		Map<String, Object> node = new HashMap<String, Object>();

		List<Map<String, Object>> argList = new ArrayList<Map<String, Object>>();

		for (ExprParser.Logical_or_expressionContext e: ctx.logical_or_expression())
		{
			argList.add(visitLogical_or_expression(e));
		}

		node.put("tag", "Keyword");
		node.put("value", "argument-list");
		node.put("arg-list", argList);


		return node;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Map<String, Object> visit(@NotNull ParseTree parseTree) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Map<String, Object> visitChildren(@NotNull RuleNode ruleNode) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Map<String, Object> visitTerminal(@NotNull TerminalNode terminalNode) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public Map<String, Object> visitErrorNode(@NotNull ErrorNode errorNode) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
