grammar Condition;

condition: operand1 operator operand2?;

operator:
	'equals'
	| '=='
	| 'not_equals'
	| '!='
	| 'contains'
	| 'not_contains'
	| 'starts_with'
	| 'not_starts_with'
	| 'ends_with'
	| 'not_ends_with'
	| 'match_regexp'
	| 'not_match_regexp'
	| 'greater'
	| '>'
	| 'greater_equal'
	| '>='
	| 'less'
	| '<'
	| 'less_equal'
	| '<='
	| 'exists'
	| 'not_exists'
	| 'find_regexp'
;

operand1: STRING;

operand2: STRING;

WHITE_SPACE: ' ' -> skip;

STRING :
    SINGLE_QUOTE_STRING
    | DOUBLE_QUOTE_STRING
    | WORD
;

WORD    : [a-zA-Z0-9${}_]+ ;

ESC_CHAR:
    '\\' .
;

SINGLE_QUOTE_STRING:
    '\'' (ESC_CHAR|~['\\])* '\''
;

DOUBLE_QUOTE_STRING:
    '"' (ESC_CHAR|~["\\])* '"'
;