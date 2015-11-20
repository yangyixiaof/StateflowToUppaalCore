package SFLanguageParser;
// Generated from Expr.g4 by ANTLR 4.5
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExprParser}.
 */
public interface ExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExprParser#do_action}.
	 * @param ctx the parse tree
	 */
	void enterDo_action(ExprParser.Do_actionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#do_action}.
	 * @param ctx the parse tree
	 */
	void exitDo_action(ExprParser.Do_actionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#primary_expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimary_expression(ExprParser.Primary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#primary_expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimary_expression(ExprParser.Primary_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ExprParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ExprParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#if_then_else_expression}.
	 * @param ctx the parse tree
	 */
	void enterIf_then_else_expression(ExprParser.If_then_else_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#if_then_else_expression}.
	 * @param ctx the parse tree
	 */
	void exitIf_then_else_expression(ExprParser.If_then_else_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#if_then_else_expression_compatible}.
	 * @param ctx the parse tree
	 */
	void enterIf_then_else_expression_compatible(ExprParser.If_then_else_expression_compatibleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#if_then_else_expression_compatible}.
	 * @param ctx the parse tree
	 */
	void exitIf_then_else_expression_compatible(ExprParser.If_then_else_expression_compatibleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#assignment_expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_expression(ExprParser.Assignment_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#assignment_expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_expression(ExprParser.Assignment_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#logical_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_or_expression(ExprParser.Logical_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#logical_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_or_expression(ExprParser.Logical_or_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_and_expression(ExprParser.Logical_and_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_and_expression(ExprParser.Logical_and_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterInclusive_or_expression(ExprParser.Inclusive_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitInclusive_or_expression(ExprParser.Inclusive_or_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterExclusive_or_expression(ExprParser.Exclusive_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitExclusive_or_expression(ExprParser.Exclusive_or_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#and_expression}.
	 * @param ctx the parse tree
	 */
	void enterAnd_expression(ExprParser.And_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#and_expression}.
	 * @param ctx the parse tree
	 */
	void exitAnd_expression(ExprParser.And_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#equality_expression}.
	 * @param ctx the parse tree
	 */
	void enterEquality_expression(ExprParser.Equality_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#equality_expression}.
	 * @param ctx the parse tree
	 */
	void exitEquality_expression(ExprParser.Equality_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#relational_expression}.
	 * @param ctx the parse tree
	 */
	void enterRelational_expression(ExprParser.Relational_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#relational_expression}.
	 * @param ctx the parse tree
	 */
	void exitRelational_expression(ExprParser.Relational_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#additive_expression}.
	 * @param ctx the parse tree
	 */
	void enterAdditive_expression(ExprParser.Additive_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#additive_expression}.
	 * @param ctx the parse tree
	 */
	void exitAdditive_expression(ExprParser.Additive_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#subtractive_expression}.
	 * @param ctx the parse tree
	 */
	void enterSubtractive_expression(ExprParser.Subtractive_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#subtractive_expression}.
	 * @param ctx the parse tree
	 */
	void exitSubtractive_expression(ExprParser.Subtractive_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicative_expression(ExprParser.Multiplicative_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicative_expression(ExprParser.Multiplicative_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void enterUnary_expression(ExprParser.Unary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void exitUnary_expression(ExprParser.Unary_expressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link ExprParser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(ExprParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link ExprParser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(ExprParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObjectExpression}
	 * labeled alternative in {@link ExprParser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void enterObjectExpression(ExprParser.ObjectExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObjectExpression}
	 * labeled alternative in {@link ExprParser#postfix_expression}.
	 * @param ctx the parse tree
	 */
	void exitObjectExpression(ExprParser.ObjectExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#argument_expression_list}.
	 * @param ctx the parse tree
	 */
	void enterArgument_expression_list(ExprParser.Argument_expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#argument_expression_list}.
	 * @param ctx the parse tree
	 */
	void exitArgument_expression_list(ExprParser.Argument_expression_listContext ctx);
}