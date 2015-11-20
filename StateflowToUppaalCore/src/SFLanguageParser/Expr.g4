/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

grammar Expr;

TRUE : 'true';
FALSE : 'false';

DOT   :       '.';
SEMICOL       :       ';';
LPAREN        :       '(';
RPAREN        :       ')';
COMMA :       ',';
QUOTE :       '\'';

IF : 'if';
THEN : '';
ELSE : 'else';
END   :       'end';

ID  : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

INT : '0'..'9'+
    ;

FLOAT
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '.' ('0'..'9')+ EXPONENT?
    |   ('0'..'9')+ EXPONENT
    ;

COMMENT
    :  (  '/*' .*? '*/'
    |   '//' ~[\r\n]* ) -> channel(HIDDEN)
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) -> skip
    ;

STRING
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;



fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;


LT_OP         :       '<';
GT_OP   :     '>';
LE_OP :       '<=';
GE_OP :       '>=';
EQ_OP :       '==';
NE_OP :       '!=';
AND_OP        :       '&&';
OR_OP :       '||';


// Rules for action language

do_action
    : (statement)*
    ;
catch[Exception e] { throw e; }

primary_expression
    returns [String tag, Object value]
      : ID  {$tag="Identifier"; $value = $ID.text;}
      | INT {$tag="Integer"; $value=Integer.parseInt($INT.text);}
//      | FLOAT
      | STRING {$tag="String"; $value=$STRING.text;}
      | TRUE {$tag="Boolean"; $value=true;}
      | FALSE {$tag="Boolean"; $value=false;}
      | '(' logical_or_expression ')'
      ;
catch[Exception e] { throw e; }

statement
    returns [String tag, Object value]
      : assignment_expression SEMICOL
      | logical_or_expression SEMICOL
      | if_then_else_expression
      | if_then_else_expression_compatible
      | SEMICOL
      ;
catch[Exception e] { throw e; }

if_then_else_expression
    returns [String tag, Object value]
    : IF '(' logical_or_expression ')' THEN (then_stmts+=statement)+
      (ELSE (else_stmts+=statement )+)?
      END
    ;
catch[Exception e] { throw e; }

if_then_else_expression_compatible
    returns [String tag, Object value]
    : IF '(' logical_or_expression ')' ('{' (then_stmts+=statement)+ '}' | then_stmts+=statement)
      (ELSE ('{' (else_stmts+=statement)+ '}' | else_stmts+=statement))?
    ;
catch[Exception e] { throw e; }

assignment_expression
    returns [String tag, Object value]
      : postfix_expression '=' logical_or_expression
      ;
catch[Exception e] { throw e; }

logical_or_expression
    returns [String tag, Object value]
      : logical_and_expression
        (OR_OP logical_and_expression )*
        ;
catch[Exception e] { throw e; }

logical_and_expression
    returns [String tag, Object value]
      : inclusive_or_expression
        (AND_OP inclusive_or_expression)*
      ;
catch[Exception e] { throw e; }

inclusive_or_expression
    returns [String tag, Object value]
      :  exclusive_or_expression
         ('|' eox+=exclusive_or_expression)*
      ;
catch[Exception e] { throw e; }


exclusive_or_expression
    returns [String tag, Object value]
      : and_expression
        ('^' and_expression)*
      ;
catch[Exception e] { throw e; }


and_expression
    returns [String tag, Object value]
      : equality_expression
        ('&' equality_expression)*
      ;
catch[Exception e] { throw e; }

equality_expression
    returns [String tag, Object value]
      : relational_expression ((operators+=EQ_OP|operators+=NE_OP) relational_expression)?
      ;
catch[Exception e] { throw e; }

relational_expression
    returns [String tag, Object value]
      : additive_expression 
        ((operators+=LT_OP|operators+=GT_OP|operators+=LE_OP|operators+=GE_OP) additive_expression)?
      ;
catch[Exception e] { throw e; }

additive_expression
    returns [String tag, Object value]
      : subtractive_expression
        ('+'subtractive_expression)*
      ;
catch[Exception e] { throw e; }

subtractive_expression
    returns [String tag, Object value]
  : multiplicative_expression
    ('-'  multiplicative_expression)*
  ;
catch[Exception e] { throw e; }

multiplicative_expression
    returns [String tag, Object value]
      :  unary_expression ((operators+='/'|operators+='%'|operators+='*') unary_expression)*
      ;
catch[Exception e] { throw e; }

unary_expression
    returns [String tag, Object value]
      : op='-' postfix_expression
       | (op='~'| op='!')? postfix_expression
      ;
catch[Exception e] { throw e; }

postfix_expression
    returns [String tag, Object value]
      : 
          //primary_expression ( '[' logical_or_expression ']')* 
      //|
          primary_expression ( '(' ')'| 
                               '(' argument_expression_list ')' )  #FunctionCall
      |
          primary_expression ('.' primary_expression)*  #ObjectExpression
      ;
catch[Exception e] { throw e; }

argument_expression_list
     : logical_or_expression (',' logical_or_expression)*
      ;
catch[Exception e] { throw e; }
