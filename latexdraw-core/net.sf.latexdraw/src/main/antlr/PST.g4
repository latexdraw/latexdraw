grammar PST;

@header {
package net.sf.latexdraw.parsers.pst;
}

@members {
boolean isPsCustom = false;
}

pstCode : (pstBlock | pspictureBlock | centerBlock | psset | psellipse | psframe | psdiamond | pstriangle | psline | psqline | pscircle | psqdisk |
            pspolygon | psbezier | psdot | psdots | psaxes | psgrid | rput | scalebox | psscalebox | pswedge | psellipticarc | psellipticarcn | psarcn |
            psarc | parabola | pscurve | psecurve | psccurve | readdata | savedata | parametricplot | psplot | listplot | dataplot | fileplot |
            newpsobject | newpsstyle | pscustom | definecolor | includegraphics)* ;

pstBlock : '{' pstCode '}' ;

psellipse : cmd=('\\psellipse*' | '\\psellipse') paramBlock? p1=coord p2=coord? ;

psframe : cmd=('\\psframe*' | '\\psframe') paramBlock? p1=coord p2=coord? ;

psdiamond : cmd=('\\psdiamond*' | '\\psdiamond') paramBlock? p1=coord p2=coord? ;

pstriangle : cmd=('\\pstriangle*' | '\\pstriangle') paramBlock? p1=coord p2=coord? ;

psline : cmd=('\\psline*' | '\\psline') paramBlock?  arrowBlock?  pts=coord+ ;

psqline : '\\qline' p1=coord p2=coord;

pscircle : cmd=('\\pscircle*' | '\\pscircle') paramBlock? centre=coord? bracketValueDim ;

psqdisk : '\\qdisk' coord bracketValueDim ;

pspolygon : ('\\pspolygon*' | '\\pspolygon') paramBlock?  p1=coord  ps=coord+ ;

psbezier : cmd=('\\psbezier*' | '\\psbezier') paramBlock? arrowBlock? (p1=coord p2=coord p3=coord)* p4=coord? ;

psdot : cmd=('\\psdot*' | '\\psdot') paramBlock? coord? ;

psdots : cmd=('\\psdots*' | '\\psdots') paramBlock? coord+ ;

psaxes : '\\psaxes' paramBlock? arrowBlock? p1=coord? p2=coord? p3=coord? ;

psgrid : '\\psgrid' paramBlock? p1=coord? p2=coord? p3=coord? ;

rput : cmd=('\\rput*' | '\\rput') ('[' TEXTPOS? ']')? ('{' star='*'? (valueDim | PUT) '}')? coord pstBlock ; //FIXME context

scalebox : '\\scalebox' '{' hscale=NUMBER '}' ('[' vscale=NUMBER ']')? pstBlock ;

psscalebox : '\\psscalebox' '{' hscale=NUMBER (vscale=NUMBER)? '}' pstBlock ;

pswedge : cmd=('\\pswedge*' | '\\pswedge') paramBlock? pos=coord? radius=bracketValueDim angle1=bracketValueDim angle2=bracketValueDim ;

psellipticarc : cmd=('\\psellipticarc*' | '\\psellipticarc') paramBlock? arrow=arrowBlock? pos0=coord pos1=coord? angle1=bracketValueDim angle2=bracketValueDim ;

psellipticarcn : cmd=('\\psellipticarcn*' | '\\psellipticarcn') paramBlock? arrow=arrowBlock? pos0=coord pos1=coord? angle1=bracketValueDim angle2=bracketValueDim ;

psarc : cmd=('\\psarc*' | '\\psarc') paramBlock? arrow=arrowBlock? pos=coord? radius=bracketValueDim angle1=bracketValueDim angle2=bracketValueDim? ;

psarcn : cmd=('\\psarcn*' | '\\psarcn') paramBlock? arrow=arrowBlock? pos=coord? radius=bracketValueDim angle1=bracketValueDim angle2=bracketValueDim? ;

parabola : cmd=('\\parabola*' | '\\parabola') paramBlock? arrow=arrowBlock? pt1=coord pt2=coord ;

pscurve : cmd=('\\pscurve*' | '\\pscurve') paramBlock? arrowBlock? (p1=coord p2=coord p3=coord)* p4=coord? ;

psecurve : cmd=('\\psecurve*' | '\\psecurve') paramBlock? arrowBlock? (p1=coord p2=coord p3=coord)* p4=coord? ;

psccurve : cmd=('\\psccurve*' | '\\psccurve') paramBlock? arrowBlock? (p1=coord p2=coord p3=coord)* p4=coord? ;

readdata : '\\readdata' paramBlock? '{' CMD '}' '{' (.|'/'|':')*? ~('}') '}' ;

savedata : '\\savedata' '{' CMD '}' '[' .*? ~(']') ']' ;

parametricplot : cmd=('\\parametricplot*' | '\\parametricplot') paramBlock? '{' xmin=NUMBER '}' '{' xmax=NUMBER '}' '{' fct=.*? ~('}') '}' ;

psplot : ('\\psplot*' | '\\psplot') paramBlock? '{' x0=NUMBER '}' '{' x1=NUMBER '}' '{' fct=.*? ~('}') '}' ;

listplot : ('\\listplot*' | '\\listplot') paramBlock? '{' CMD+ '}' ;

dataplot : ('\\listplot*' | '\\listplot') paramBlock? '{' CMD+ '}' ;

fileplot : ('\\listplot*' | '\\listplot') paramBlock? '{' CMD+ '}' ;

newpsobject : '\\newpsobject' '{' name=IDENT '}' '{' obj=IDENT '}' '{' attrs=.*? ~('}') '}' ;

newpsstyle : '\\newpsstyle' ('[' pkgname=.*? ~(']') ']')? '{' name=IDENT '}' '{' def=.*? ~('}') '}' ;

pscustom : ('\\pscustom*' | '\\pscustom') paramBlock? pstBlock ;

definecolor : '\\definecolor' '{' name=IDENT '}' '{' COLORTYPE '}' '{' NUMBER (',' NUMBER)* '}' ;

includegraphics : '\\includegraphics' paramBlock '{' path=.*? ~('}') '}' ;

pspictureBlock locals [boolean hasStar = false;] : '\\begin' '{' (('pspicture*' {$hasStar=true;}) | ('pspicture' {$hasStar=false;})) '}'
        p1=coord? p2=coord? pstCode '\\end' '{' ({$hasStar}? 'pspicture*' | 'pspicture') '}';

centerBlock : '\\begin' '{' 'center' '}' pstCode '\\end' '{' 'center' '}';

psset : '\\psset' '{' (paramSetting (',' paramSetting)*)? '}' ;

paramBlock : '[' (paramSetting (',' paramSetting)*)? ']';

bracketValueDim : '{' valueDim '}' ;

valueDim : NUMBER UNIT? ;

paramSetting : IDENT '=' paramvalue ;

paramvalue : .*? ~(','|'}') ;

arrowBlock : '{' ARROW? '-' ARROW? '}' ;

coord : '(' NUMBER? ',' NUMBER? ')';

CMD : '\\' IDENT ;

IDENT : LETTER (LETTER | DIGIT)* '*'? ;

NUMBER: ('+'|'-')* ((DOT DIGIT+) | (DIGIT+ (DOT DIGIT*)? )) UNIT? ;

PUT : 'U' | 'L' | 'D' | 'R' | 'N' | 'W' | 'S' | 'E' ;

TEXTPOS : 'bl' | 'br' | 'b' | 'tl' | 'tr' | 't' | 'Bl' | 'Br' | 'B' | 'l' | 'r' ;

COLORTYPE : 'rgb' | 'RGB' | 'gray' | 'html' | 'cmyk' | 'cmy' | 'hsb' ;

ARROW : '>' | '<' | '|' | '[' | ']' | '*' | '**' | '<<' | '>>' | 'c' | 'C' | 'cc' | '|*' | '(' | ')' | 'o' | 'oo' ;

UNIT : 'cm' | 'mm' | 'pt' | 'in' ;

DOT : '.' ;

fragment LETTER : 'A'..'Z' | 'a'..'z' | '\u00C0'..'\u00D6' | '\u00D8'..'\u00F6'| '\u00F8'..'\u02FF'| '\u0370'..'\u037D'| '\u037F'..'\u1FFF' |
                    '\u200C'..'\u200D'| '\u2070'..'\u218F'| '\u2C00'..'\u2FEF'| '\u3001'..'\uD7FF'| '\uF900'..'\uFDCF'| '\uFDF0'..'\uFFFD';

fragment DIGIT: '0'..'9' ;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;
