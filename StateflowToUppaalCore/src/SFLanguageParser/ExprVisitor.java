package SFLanguageParser;
// Generated from Expr.g4 by ANTLR 4.5
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExprParser#do_action}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDo_action(ExprParser.Do_actionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#primary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_expression(ExprParser.Primary_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ExprParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#if_then_else_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_then_else_expression(ExprParser.If_then_else_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#if_then_else_expression_compatible}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_then_else_expression_compatible(ExprParser.If_then_else_expression_compatibleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#assignment_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_expression(ExprParser.Assignment_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#logical_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_or_expression(ExprParser.Logical_or_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#logical_and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_and_expression(ExprParser.Logical_and_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusive_or_expression(ExprParser.Inclusive_or_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusive_or_expression(ExprParser.Exclusive_or_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_expression(ExprParser.And_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#equality_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquality_expression(ExprParser.Equality_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#relational_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_expression(ExprParser.Relational_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#additive_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive_expression(ExprParser.Additive_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#subtractive_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtractive_expression(ExprParser.Subtractive_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicative_expression(ExprParser.Multiplicative_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#unary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_expression(ExprParser.Unary_expressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link ExprParser#postfix_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(ExprParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObjectExpression}
	 * labeled alternative in {@link ExprParser#postfix_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectExpression(ExprParser.ObjectExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExprParser#argument_expression_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument_expression_list(ExprParser.Argument_expression_listContext ctx);
}