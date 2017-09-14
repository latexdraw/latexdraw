grammar PST;

@header {
package insa.pst;
}

@members {
boolean isPsCustom = false;
}

pstCode : (pstBlock | pspictureBlock | centerBlock | psset | psellipse | psframe | psdiamond | pstriangle | psline)* ;

pstBlock : '{' pstCode '}';

psellipse : cmd=('\\psellipse*' | '\\psellipse') bodyStdShape;

psframe : cmd=('\\psframe*' | '\\psframe') bodyStdShape;

psdiamond : cmd=('\\psdiamond*' | '\\psdiamond') bodyStdShape;

pstriangle : cmd=('\\pstriangle*' | '\\pstriangle') bodyStdShape;

bodyStdShape : paramBlock? coord coord? ;

psline : cmd=('\\psline*' | '\\psline') paramBlock?  arrowBlock?  coord+ ;

pspictureBlock locals [boolean hasStar = false;] : '\\begin' '{' (('pspicture*' {$hasStar=true;}) | ('pspicture' {$hasStar=false;})) '}' coord? coord? pstCode '\\end' '{' ({$hasStar}? 'pspicture*' | 'pspicture') '}';

centerBlock : '\\begin' '{' 'center' '}' pstCode '\\end' '{' 'center' '}';

psset : '\\psset' '{' (paramSetting (',' paramSetting)*)? '}' ;

paramBlock : '[' (paramSetting (',' paramSetting)*)? ']';

paramSetting : ident '=' paramvalue;

paramvalue : .*? ~(','|'}') ;

arrowBlock : '{' ARROW? '-' ARROW? '}' ;

coord : '(' number? ',' number? ')';

ident : LETTER (LETTER | DIGIT)* '*'?;

number: ('+'|'-')* (('.' DIGIT+) | (DIGIT+ ('.' DIGIT*)? )) UNIT? ;


ARROW : '>' | '<' | '|' | '[' | ']' | '*' | '**' | '<<' | '>>' | 'c' | 'C' | 'cc' | '|*' | '(' | ')' | 'o' | 'oo' ;

UNIT : 'cm' | 'mm' | 'pt' | 'in' ;

LETTER : [a-zA-Z];

DIGIT: '0'..'9';

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;
