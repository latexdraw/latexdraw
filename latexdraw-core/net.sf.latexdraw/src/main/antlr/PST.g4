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
            newpsobject | newpsstyle | pscustom | definecolor | includegraphics | psframebox | psdblframebox | psshadowbox | pscirclebox |
            psovalbox | psdiabox | pstribox | upshape | itshape | slshape | scshape | it | sc | sl | mdseries | bfseries | bf | rmfamily | sffamily |
            ttfamily | textcolor | color | usefont | comment | unknowncmds | text)* ;

pstcustomBlock : '{' (pstCode | rlineto | movepath | closedshadow | openshadow | mrestore | msave | swapaxes | rotate | scale | translate | fill |
                    stroke | grestore | gsave | rcurveto | closepath | curveto | lineto | moveto | newpath) '}' ;

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

rlineto : '\\rlineto' coord ;

movepath : '\\movepath' coord ;

closedshadow : '\\closedshadow' paramBlock? ;

openshadow : '\\openshadow' paramBlock? ;

mrestore : '\\mrestore' ;

msave : '\\msave' ;

swapaxes : '\\swapaxes' ;

rotate : '\\rotate' '{' .*? ~('}') '}' ;

scale : '\\scale' '{' .*? ~('}') '}' ;

translate : '\\translate' coord ;

fill : '\\fill' paramBlock? ;

stroke : '\\stroke' paramBlock? ;

grestore : '\\grestore' ;

gsave : '\\gsave' ;

rcurveto : '\\rcurveto' p1=coord p2=coord p3=coord ;

closepath : '\\closepath' ;

curveto : '\\curveto' p1=coord p2=coord p3=coord ;

lineto : '\\lineto' coord ;

moveto : '\\moveto' coord ;

newpath : '\\newpath' ;

psframebox : cmd=('\\psframebox' | '\\psframebox*') paramBlock? '{' content=.*? ~('}') '}' ;

psdblframebox : cmd=('\\psdblframebox' | '\\psdblframebox*') paramBlock? '{' content=.*? ~('}') '}' ;

psshadowbox : cmd=('\\psshadowbox' | '\\psshadowbox*') paramBlock? '{' content=.*? ~('}') '}' ;

pscirclebox : cmd=('\\pscirclebox' | '\\pscirclebox*') paramBlock? '{' content=.*? ~('}') '}' ;

psovalbox : cmd=('\\psovalbox' | '\\psovalbox*') paramBlock? '{' content=.*? ~('}') '}' ;

psdiabox : cmd=('\\psdiabox' | '\\psdiabox*') paramBlock? '{' content=.*? ~('}') '}' ;

pstribox : cmd=('\\pstribox' | '\\pstribox*') paramBlock? '{' content=.*? ~('}') '}' ;

upshape : '\\upshape' ;

itshape : '\\itshape' ;

slshape : '\\slshape' ;

scshape : '\\scshape' ;

it : '\\it' ;

sc : '\\sc' ;

sl : '\\sl' ;

mdseries : '\\mdseries' ;

bfseries : '\\bfseries' ;

bf : '\\bf' ;

rmfamily : '\\rmfamily' ;

sffamily : '\\sffamily' ;

ttfamily : '\\ttfamily' ;

textcolor : '\\textcolor' '{' name=WORD '}' pstBlock ;

color : '\\color' '{' name=WORD '}' pstBlock ;

usefont : '\\usefont' '{' encoding=WORD '}' '{' family=WORD '}' '{' series=WORD '}' '{' shapes=WORD '}' ;

text : TEXT+ ;

unknowncmds : CMD ( ('[' .*? ~(']') ']') | ('{' content=.*? ~('}') '}') | ('(' content=.*? ~(')') ')') )* ;

readdata : '\\readdata' paramBlock? '{' CMD '}' '{' (.|'/'|':')*? ~('}') '}' ;

savedata : '\\savedata' '{' CMD '}' '[' .*? ~(']') ']' ;

parametricplot : cmd=('\\parametricplot*' | '\\parametricplot') paramBlock? '{' xmin=NUMBER '}' '{' xmax=NUMBER '}' '{' fct=.*? ~('}') '}' ;

psplot : ('\\psplot*' | '\\psplot') paramBlock? '{' x0=NUMBER '}' '{' x1=NUMBER '}' '{' fct=.*? ~('}') '}' ;

listplot : ('\\listplot*' | '\\listplot') paramBlock? '{' CMD+ '}' ;

dataplot : ('\\listplot*' | '\\listplot') paramBlock? '{' CMD+ '}' ;

fileplot : ('\\listplot*' | '\\listplot') paramBlock? '{' CMD+ '}' ;

newpsobject : '\\newpsobject' '{' name=IDENT '}' '{' obj=IDENT '}' '{' attrs=.*? ~('}') '}' ;

newpsstyle : '\\newpsstyle' ('[' pkgname=.*? ~(']') ']')? '{' name=IDENT '}' '{' def=.*? ~('}') '}' ;

pscustom : ('\\pscustom*' | '\\pscustom') paramBlock? pstcustomBlock ;

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

comment : COMMENT+ ;

TEXT : (LETTER | DIGIT | PUNCTUATION)+ ;

CMD : '\\' IDENT ;

WORD : LETTER (LETTER | DIGIT)* ;

IDENT : WORD '*'? ;

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

COMMENT :  '%' ~('\r' | '\n')* ;

PUNCTUATION : [-+*.,?;:/!'"`] ;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;
