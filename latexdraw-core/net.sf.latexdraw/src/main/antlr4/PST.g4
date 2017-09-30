grammar PST;

@header {
package net.sf.latexdraw.parsers.pst;
}

pstCode[PSTContext pstctx] : (pstBlock[pstctx] | pspictureBlock[pstctx] | centerBlock[pstctx] | psset[pstctx] | psellipse[pstctx] | psframe[pstctx] | psdiamond[pstctx] |
            pstriangle[pstctx] | psline[pstctx] | psqline[pstctx] | pscircle[pstctx] | psqdisk[pstctx] | pspolygon[pstctx] | psbezier[pstctx] | psdot[pstctx] |
            psdots[pstctx] | psaxes[pstctx] | psgrid[pstctx] | rput[pstctx] | scalebox[pstctx] | psscalebox[pstctx] | pswedge[pstctx] | psellipticarc[pstctx] |
            psellipticarcn[pstctx] | psarcn[pstctx] | psarc[pstctx] | parabola[pstctx] | pscurve[pstctx] | psecurve[pstctx] | psccurve[pstctx] |
            readdata[pstctx] | savedata[pstctx] | parametricplot[pstctx] | psplot[pstctx] | listplot[pstctx] | dataplot[pstctx] | fileplot[pstctx] |
            newpsobject[pstctx] | newpsstyle[pstctx] | pscustom[pstctx] | definecolor[pstctx] | includegraphics[pstctx] | psframebox[pstctx] | psdblframebox[pstctx] |
            psshadowbox[pstctx] | pscirclebox[pstctx] | psovalbox[pstctx] | psdiabox[pstctx] | pstribox[pstctx] | upshape[pstctx] | itshape[pstctx] |
            slshape[pstctx] | scshape[pstctx] | it[pstctx] | sc[pstctx] | sl[pstctx] | mdseries[pstctx] | bfseries[pstctx] | bf[pstctx] | rmfamily[pstctx] |
            sffamily[pstctx] | ttfamily[pstctx] | textcolor[pstctx] | color[pstctx] | usefont[pstctx] | comment[pstctx] | unknowncmds[pstctx] | text[pstctx])* ;

pstcustomBlock[PSTContext pstctx]
@init {
	PSTContext newpstctx = new PSTContext(pstctx, true);
}
    : BRACE_OPEN (pstCode[newpstctx] | rlineto[newpstctx] | movepath[newpstctx] | closedshadow[newpstctx] | openshadow[newpstctx] | mrestore[newpstctx] |
        msave[newpstctx] | swapaxes[newpstctx] | rotate[newpstctx] | scale[newpstctx] | translate[newpstctx] | fill[newpstctx] |
        stroke[newpstctx] | grestore[newpstctx] | gsave[newpstctx] | rcurveto[newpstctx] | closepath[newpstctx] | curveto[newpstctx] | lineto[newpstctx] |
        moveto[pstctx] | newpath[newpstctx]) BRACE_CLOSE ;

pstBlock[PSTContext pstctx]
@init {
	PSTContext newpstctx = new PSTContext(pstctx, pstctx.isPsCustom);
}
    : BRACE_OPEN pstCode[newpstctx] BRACE_CLOSE ;

psellipse[PSTContext pstctx] : cmd=('\\psellipse*' | '\\psellipse') paramBlock[pstctx]? p1=coord p2=coord? ;

psframe[PSTContext pstctx] : cmd=('\\psframe*' | '\\psframe') paramBlock[pstctx]? p1=coord p2=coord? ;

psdiamond[PSTContext pstctx] : cmd=('\\psdiamond*' | '\\psdiamond') paramBlock[pstctx]? p1=coord p2=coord? ;

pstriangle[PSTContext pstctx] : cmd=('\\pstriangle*' | '\\pstriangle') paramBlock[pstctx]? p1=coord p2=coord? ;

psline[PSTContext pstctx] : cmd=('\\psline*' | '\\psline') paramBlock[pstctx]?  arrowBlock[pstctx]? pts+=coord+ ;

psqline[PSTContext pstctx] : '\\qline' p1=coord p2=coord;

pscircle[PSTContext pstctx] : cmd=('\\pscircle*' | '\\pscircle') paramBlock[pstctx]? centre=coord? bracketValueDim ;

psqdisk[PSTContext pstctx] : '\\qdisk' coord bracketValueDim ;

pspolygon[PSTContext pstctx] : ('\\pspolygon*' | '\\pspolygon') paramBlock[pstctx]?  p1=coord  ps+=coord+ ;

psbezier[PSTContext pstctx] : cmd=('\\psbezier*' | '\\psbezier') paramBlock[pstctx]? arrowBlock[pstctx]? (p1+=coord p2+=coord p3+=coord)* p4=coord? ;

psdot[PSTContext pstctx] : cmd=('\\psdot*' | '\\psdot') paramBlock[pstctx]? pt=coord? ;

psdots[PSTContext pstctx] : cmd=('\\psdots*' | '\\psdots') paramBlock[pstctx]? pts+=coord+ ;

psaxes[PSTContext pstctx] : '\\psaxes' paramBlock[pstctx]? arrowBlock[pstctx]? p1=coord? p2=coord? p3=coord? ;

psgrid[PSTContext pstctx] : '\\psgrid' paramBlock[pstctx]? p1=coord? p2=coord? p3=coord? ;

rput[PSTContext pstctx] : cmd=('\\rput*' | '\\rput') ('[' textpos? ']')? (BRACE_OPEN star='*'? (valueDim | put) BRACE_CLOSE)? coord pstBlock[pstctx] ;

scalebox[PSTContext pstctx] : '\\scalebox' BRACE_OPEN hscale=NUMBER BRACE_CLOSE ('[' vscale=NUMBER ']')? pstBlock[pstctx] ;

psscalebox[PSTContext pstctx] : '\\psscalebox' BRACE_OPEN hscale=NUMBER (vscale=NUMBER)? BRACE_CLOSE pstBlock[pstctx] ;

pswedge[PSTContext pstctx] : cmd=('\\pswedge*' | '\\pswedge') paramBlock[pstctx]? pos=coord? radius=bracketValueDim angle1=bracketValueDim angle2=bracketValueDim ;

psellipticarc[PSTContext pstctx] : cmd=('\\psellipticarc*' | '\\psellipticarc') paramBlock[pstctx]? arrowBlock[pstctx]? pos0=coord pos1=coord? angle1=bracketValueDim angle2=bracketValueDim ;

psellipticarcn[PSTContext pstctx] : cmd=('\\psellipticarcn*' | '\\psellipticarcn') paramBlock[pstctx]? arrowBlock[pstctx]? pos0=coord pos1=coord? angle1=bracketValueDim angle2=bracketValueDim ;

psarc[PSTContext pstctx] : cmd=('\\psarc*' | '\\psarc') paramBlock[pstctx]? arrowBlock[pstctx]? pos=coord? radius=bracketValueDim angle1=bracketValueDim angle2=bracketValueDim? ;

psarcn[PSTContext pstctx] : cmd=('\\psarcn*' | '\\psarcn') paramBlock[pstctx]? arrowBlock[pstctx]? pos=coord? radius=bracketValueDim angle1=bracketValueDim angle2=bracketValueDim? ;

parabola[PSTContext pstctx] : cmd=('\\parabola*' | '\\parabola') paramBlock[pstctx]? arrowBlock[pstctx]? pt1=coord pt2=coord ;

pscurve[PSTContext pstctx] : cmd=('\\pscurve*' | '\\pscurve') paramBlock[pstctx]? arrowBlock[pstctx]? (p1+=coord p2+=coord p3+=coord)* p4=coord? ;

psecurve[PSTContext pstctx] : cmd=('\\psecurve*' | '\\psecurve') paramBlock[pstctx]? arrowBlock[pstctx]? (p1+=coord p2+=coord p3+=coord)* p4=coord? ;

psccurve[PSTContext pstctx] : cmd=('\\psccurve*' | '\\psccurve') paramBlock[pstctx]? arrowBlock[pstctx]? (p1+=coord p2+=coord p3+=coord)* p4=coord? ;

rlineto[PSTContext pstctx] : '\\rlineto' coord ;

movepath[PSTContext pstctx] : '\\movepath' coord ;

closedshadow[PSTContext pstctx] : '\\closedshadow' paramBlock[pstctx]? ;

openshadow[PSTContext pstctx] : '\\openshadow' paramBlock[pstctx]? ;

mrestore[PSTContext pstctx] : '\\mrestore' ;

msave[PSTContext pstctx] : '\\msave' ;

swapaxes[PSTContext pstctx] : '\\swapaxes' ;

rotate[PSTContext pstctx] : '\\rotate' BRACE_OPEN .*? ~(BRACE_CLOSE) BRACE_CLOSE ;

scale[PSTContext pstctx] : '\\scale' BRACE_OPEN .*? ~(BRACE_CLOSE) BRACE_CLOSE ;

translate[PSTContext pstctx] : '\\translate' coord ;

fill[PSTContext pstctx] : '\\fill' paramBlock[pstctx]? ;

stroke[PSTContext pstctx] : '\\stroke' paramBlock[pstctx]? ;

grestore[PSTContext pstctx] : '\\grestore' ;

gsave[PSTContext pstctx] : '\\gsave' ;

rcurveto[PSTContext pstctx] : '\\rcurveto' p1=coord p2=coord p3=coord ;

closepath[PSTContext pstctx] : '\\closepath' ;

curveto[PSTContext pstctx] : '\\curveto' p1=coord p2=coord p3=coord ;

lineto[PSTContext pstctx] : '\\lineto' coord ;

moveto[PSTContext pstctx] : '\\moveto' coord ;

newpath[PSTContext pstctx] : '\\newpath' ;

psframebox[PSTContext pstctx] : cmd=('\\psframebox' | '\\psframebox*') paramBlock[pstctx]? BRACE_OPEN content=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

psdblframebox[PSTContext pstctx] : cmd=('\\psdblframebox' | '\\psdblframebox*') paramBlock[pstctx]? BRACE_OPEN content=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

psshadowbox[PSTContext pstctx] : cmd=('\\psshadowbox' | '\\psshadowbox*') paramBlock[pstctx]? BRACE_OPEN content=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

pscirclebox[PSTContext pstctx] : cmd=('\\pscirclebox' | '\\pscirclebox*') paramBlock[pstctx]? BRACE_OPEN content=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

psovalbox[PSTContext pstctx] : cmd=('\\psovalbox' | '\\psovalbox*') paramBlock[pstctx]? BRACE_OPEN content=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

psdiabox[PSTContext pstctx] : cmd=('\\psdiabox' | '\\psdiabox*') paramBlock[pstctx]? BRACE_OPEN content=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

pstribox[PSTContext pstctx] : cmd=('\\pstribox' | '\\pstribox*') paramBlock[pstctx]? BRACE_OPEN content=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

upshape[PSTContext pstctx] : '\\upshape' ;

itshape[PSTContext pstctx] : '\\itshape' ;

slshape[PSTContext pstctx] : '\\slshape' ;

scshape[PSTContext pstctx] : '\\scshape' ;

it[PSTContext pstctx] : '\\it' ;

sc[PSTContext pstctx] : '\\sc' ;

sl[PSTContext pstctx] : '\\sl' ;

mdseries[PSTContext pstctx] : '\\mdseries' ;

bfseries[PSTContext pstctx] : '\\bfseries' ;

bf[PSTContext pstctx] : '\\bf' ;

rmfamily[PSTContext pstctx] : '\\rmfamily' ;

sffamily[PSTContext pstctx] : '\\sffamily' ;

ttfamily[PSTContext pstctx] : '\\ttfamily' ;

textcolor[PSTContext pstctx] : '\\textcolor' BRACE_OPEN name=WORD BRACE_CLOSE pstBlock[pstctx] ;

color[PSTContext pstctx] : '\\color' BRACE_OPEN name=WORD BRACE_CLOSE pstBlock[pstctx] ;

usefont[PSTContext pstctx] : '\\usefont' BRACE_OPEN encoding=WORD BRACE_CLOSE BRACE_OPEN family=WORD BRACE_CLOSE BRACE_OPEN series=WORD BRACE_CLOSE BRACE_OPEN shapes=WORD BRACE_CLOSE ;

unknowncmds[PSTContext pstctx] : LATEXCMD ( ('[' .*? ~(']') ']') | (BRACE_OPEN content=.*? ~(BRACE_CLOSE) BRACE_CLOSE) | ('(' content=.*? ~(')') ')') )* ;

readdata[PSTContext pstctx] : '\\readdata' paramBlock[pstctx]? BRACE_OPEN LATEXCMD BRACE_CLOSE BRACE_OPEN (.|'/'|':')*? ~(BRACE_CLOSE) BRACE_CLOSE ;

savedata[PSTContext pstctx] : '\\savedata' BRACE_OPEN LATEXCMD BRACE_CLOSE '[' .*? ~(']') ']' ;

parametricplot[PSTContext pstctx] : cmd=('\\parametricplot*' | '\\parametricplot') paramBlock[pstctx]? BRACE_OPEN xmin=NUMBER BRACE_CLOSE BRACE_OPEN xmax=NUMBER BRACE_CLOSE BRACE_OPEN fct=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

psplot[PSTContext pstctx] : ('\\psplot*' | '\\psplot') paramBlock[pstctx]? BRACE_OPEN x0=NUMBER BRACE_CLOSE BRACE_OPEN x1=NUMBER BRACE_CLOSE BRACE_OPEN fct=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

listplot[PSTContext pstctx] : ('\\listplot*' | '\\listplot') paramBlock[pstctx]? BRACE_OPEN LATEXCMD+ BRACE_CLOSE ;

dataplot[PSTContext pstctx] : ('\\listplot*' | '\\listplot') paramBlock[pstctx]? BRACE_OPEN LATEXCMD+ BRACE_CLOSE ;

fileplot[PSTContext pstctx] : ('\\listplot*' | '\\listplot') paramBlock[pstctx]? BRACE_OPEN LATEXCMD+ BRACE_CLOSE ;

newpsobject[PSTContext pstctx] : '\\newpsobject' BRACE_OPEN name=IDENT BRACE_CLOSE BRACE_OPEN obj=IDENT BRACE_CLOSE BRACE_OPEN attrs=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

newpsstyle[PSTContext pstctx] : '\\newpsstyle' ('[' pkgname=.*? ~(']') ']')? BRACE_OPEN name=IDENT BRACE_CLOSE BRACE_OPEN def=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

pscustom[PSTContext pstctx] : ('\\pscustom*' | '\\pscustom') paramBlock[pstctx]? pstcustomBlock[pstctx] ;

definecolor[PSTContext pstctx] : '\\definecolor' BRACE_OPEN name=IDENT BRACE_CLOSE BRACE_OPEN colortype BRACE_CLOSE BRACE_OPEN NUMBER (',' NUMBER)* BRACE_CLOSE ;

includegraphics[PSTContext pstctx] : '\\includegraphics' paramBlock[pstctx] BRACE_OPEN path=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

pspictureBlock[PSTContext pstctx] locals [boolean hasStar = false;] : '\\begin' BRACE_OPEN (('pspicture*' {$hasStar=true;}) | ('pspicture' {$hasStar=false;})) BRACE_CLOSE
        p1=coord? p2=coord? pstCode[pstctx] '\\end' BRACE_OPEN ({$hasStar}? 'pspicture*' | 'pspicture') BRACE_CLOSE ;

centerBlock[PSTContext pstctx] : '\\begin' BRACE_OPEN 'center' BRACE_CLOSE pstCode[pstctx] '\\end' BRACE_OPEN 'center' BRACE_CLOSE ;

psset[PSTContext pstctx] : '\\psset' BRACE_OPEN (paramSetting[pstctx] (',' paramSetting[pstctx])*)? BRACE_CLOSE ;

paramBlock[PSTContext pstctx] : '[' (paramSetting[pstctx] (',' paramSetting[pstctx])*)? ']' ;

bracketValueDim : BRACE_OPEN valueDim BRACE_CLOSE ;

valueDim : NUMBER unit? ;

paramSetting[PSTContext pstctx] : paramArrow[pstctx] | paramArrowscale[pstctx] | paramRbracketlength[pstctx] | paramBracketlength[pstctx] |
        paramArrowinset[pstctx] | paramArrowlength[pstctx] | paramtbarsize[pstctx] | paramarrowsize[pstctx] | paramunit[pstctx] | paramaddfillstyle[pstctx] |
        paramborder[pstctx] | paramdotsep[pstctx] | paramdash[pstctx] | paramframearc[pstctx] | paramxunit[pstctx] | paramyunit[pstctx] | paramorigin[pstctx] |
        paramswapaxes[pstctx] | paramlinestyle[pstctx] | parambordercolor[pstctx] | paramdoubleline[pstctx] | paramdoublesep[pstctx] |
        paramdoublecolor[pstctx] | paramshadow[pstctx] | paramshadowsize[pstctx] | paramshadowangle[pstctx] | paramshadowcolor[pstctx] |
        paramdimen[pstctx] | paramfillstyle[pstctx] | paramfillcolor[pstctx] | paramhatchwidth[pstctx] | paramhatchsep[pstctx] | paramhatchcolor[pstctx] |
        paramhatchangle[pstctx] | paramliftpen[pstctx] | paramlabelsep[pstctx] | paramlabels[pstctx] | paramticks[pstctx] | paramtickstyle[pstctx] |
        paramshoworigin[pstctx] | paramticksize[pstctx] | paramaxesstyle[pstctx] | paramframesep[pstctx] | paramboxsep[pstctx] | paramrunit[pstctx] |
        paramlinewidth[pstctx] | paramlinecolor[pstctx] | paramshowpoints[pstctx] | paramlinearc[pstctx] | paramcornersize[pstctx] | paramarcsepA[pstctx] |
        paramarcsepB[pstctx] | paramarcsep[pstctx] | paramcurvature[pstctx] | paramdotstyle[pstctx] | paramdotscale[pstctx] | paramdotdotangle[pstctx] |
        paramgridwidth[pstctx] | paramgridcolor[pstctx] | paramgriddots[pstctx] | paramgridlabels[pstctx] | paramgridlabelcolor[pstctx] |
        paramsubgriddiv[pstctx] | paramsubgridwidth[pstctx] | paramsubgridcolor[pstctx] | paramsubgriddots[pstctx] | paramplotstyle[pstctx] |
        paramplotpoints[pstctx] | paramgradbegin[pstctx] | paramgradend[pstctx] | paramgradlines[pstctx] | paramgradmidpoint[pstctx] | paramgradangle[pstctx] |
        unkownParamSetting[pstctx] ;

unkownParamSetting[PSTContext pstctx] : name=WORD '=' .*?~(',') ;

paramArrow[PSTContext pstctx] : 'arrows' '=' arrowvalue[pstctx] ;
paramArrowscale[PSTContext pstctx] : 'arrowscale' '=' sx=valueDim sy=valueDim ;
paramRbracketlength[PSTContext pstctx] : 'rbracketlength' '=' NUMBER ;
paramBracketlength[PSTContext pstctx] : 'bracketlength' '=' NUMBER ;
paramArrowinset[PSTContext pstctx] : 'arrowinset' '=' NUMBER ;
paramArrowlength[PSTContext pstctx] : 'arrowlength' '=' NUMBER ;
paramtbarsize[PSTContext pstctx] : 'tbarsize' '=' valueDim NUMBER? ;
paramarrowsize[PSTContext pstctx] : 'arrowsize' '=' valueDim NUMBER? ;
paramunit[PSTContext pstctx] : 'unit' '=' valueDim ;
paramxunit[PSTContext pstctx] : 'xunit' '=' valueDim ;
paramyunit[PSTContext pstctx] : 'yunit' '=' valueDim ;
paramaddfillstyle[PSTContext pstctx] : 'addfillstyle' '=' fillstyle ;
paramborder[PSTContext pstctx] : 'border' '=' valueDim ;
paramdotsep[PSTContext pstctx] : 'dotsep' '=' valueDim ;
paramdash[PSTContext pstctx] : 'dash' '=' dash1=valueDim dash2=valueDim ;
paramframearc[PSTContext pstctx] : 'framearc' '=' NUMBER ;
paramorigin[PSTContext pstctx] : 'origin' '=' BRACE_OPEN x=valueDim? ',' y=valueDim? BRACE_CLOSE;
paramswapaxes[PSTContext pstctx] : 'swapaxes' '=' booleanvalue ;
paramlinestyle[PSTContext pstctx] : 'linestyle' '=' ('none' | 'solid' | 'dashed' | 'dotted') ;
parambordercolor[PSTContext pstctx] : 'bordercolor' '=' WORD ;
paramdoubleline[PSTContext pstctx] : 'doubleline' '=' booleanvalue ;
paramdoublesep[PSTContext pstctx] : 'doublesep' '=' valueDim ;
paramdoublecolor[PSTContext pstctx] : 'doublecolor' '=' WORD ;
paramshadow[PSTContext pstctx] : 'shadow' '=' booleanvalue ;
paramshadowsize[PSTContext pstctx] : 'shadowsize' '=' valueDim ;
paramshadowangle[PSTContext pstctx] : 'shadowangle' '=' NUMBER ;
paramshadowcolor[PSTContext pstctx] : 'shadowcolor' '=' WORD ;
paramdimen[PSTContext pstctx] : 'dimen' '=' ('outer' | 'inner' | 'middle') ;
paramfillstyle[PSTContext pstctx] : 'fillstyle' '=' fillstyle ;
paramfillcolor[PSTContext pstctx] : 'fillcolor' '=' WORD ;
paramhatchwidth[PSTContext pstctx] : 'hatchwidth' '=' valueDim ;
paramhatchsep[PSTContext pstctx] : 'hatchsep' '=' valueDim ;
paramhatchcolor[PSTContext pstctx] : 'hatchcolor' '=' WORD ;
paramhatchangle[PSTContext pstctx] : 'hatchangle' '=' NUMBER ;
paramliftpen[PSTContext pstctx] : 'liftpen' '=' INT ;
paramlabelsep[PSTContext pstctx] : 'labelsep' '=' valueDim ;
paramlabels[PSTContext pstctx] : 'labels' '=' show ;
paramticks[PSTContext pstctx] : 'ticks' '=' show ;
paramtickstyle[PSTContext pstctx] : 'tickstyle' '=' ('full'  | 'top' | 'bottom') ;
paramshoworigin[PSTContext pstctx] : 'showorigin' '=' booleanvalue ;
paramticksize[PSTContext pstctx] : 'ticksize' '=' valueDim ;
paramaxesstyle[PSTContext pstctx] : 'axesstyle' '=' ('axes' | 'frame' | 'none') ;
paramframesep[PSTContext pstctx] : 'framesep' '=' valueDim ;
paramboxsep[PSTContext pstctx] : 'boxsep' '=' booleanvalue ;
paramrunit[PSTContext pstctx] : 'runit' '=' valueDim ;
paramlinewidth[PSTContext pstctx] : 'linewidth' '=' valueDim ;
paramlinecolor[PSTContext pstctx] : 'linecolor' '=' WORD ;
paramshowpoints[PSTContext pstctx] : 'showpoints' '=' booleanvalue ;
paramlinearc[PSTContext pstctx] : 'linearc' '=' valueDim ;
paramcornersize[PSTContext pstctx] : 'cornersize' '=' ('relative' | 'absolute') ;
paramarcsepA[PSTContext pstctx] : 'arcsepA' '=' valueDim ;
paramarcsepB[PSTContext pstctx] : 'arcsepB' '=' valueDim ;
paramarcsep[PSTContext pstctx] : 'arcsep' '=' valueDim ;
paramcurvature[PSTContext pstctx] : 'curvature' '=' num1=NUMBER num2=NUMBER num3=NUMBER ;
paramdotstyle[PSTContext pstctx] : 'dotstyle' '=' ('*' | 'o' | '+' | 'triangle' | 'triangle*' | 'square' | 'square*' | 'pentagon' | 'pentagon*' | '|') ;
paramdotscale[PSTContext pstctx] : 'dotscale' '=' num1=NUMBER num2=NUMBER;
paramdotdotangle[PSTContext pstctx] : 'dotangle' '=' NUMBER ;
paramgridwidth[PSTContext pstctx] : 'gridwidth' '=' valueDim ;
paramgridcolor[PSTContext pstctx] : 'gridcolor' '=' WORD ;
paramgriddots[PSTContext pstctx] : 'griddots' '=' NUMBER ;
paramgridlabels[PSTContext pstctx] : 'gridlabels' '=' valueDim ;
paramgridlabelcolor[PSTContext pstctx] : 'gridlabelcolor' '=' WORD ;
paramsubgriddiv[PSTContext pstctx] : 'subgriddiv' '=' INT ;
paramsubgridwidth[PSTContext pstctx] : 'subgridwidth' '=' valueDim ;
paramsubgridcolor[PSTContext pstctx] : 'subgridcolor' '=' WORD ;
paramsubgriddots[PSTContext pstctx] : 'subgriddots' '=' INT ;
paramplotstyle[PSTContext pstctx] : 'plotstyle' '=' ('dots' | 'line' | 'polygon' | 'curve' | 'ecurve' | 'ccurve') ;
paramplotpoints[PSTContext pstctx] : 'plotpoints' '=' INT ;
paramgradbegin[PSTContext pstctx] : 'gradbegin' '=' WORD ;
paramgradend[PSTContext pstctx] : 'gradend' '=' WORD ;
paramgradlines[PSTContext pstctx] : 'gradlines' '=' INT ;
paramgradmidpoint[PSTContext pstctx] : 'gradmidpoint' '=' NUMBER ;
paramgradangle[PSTContext pstctx] : 'gradangle' '=' NUMBER ;

arrowBlock[PSTContext pstctx] : BRACE_OPEN arrowvalue[pstctx] BRACE_CLOSE ;

arrowvalue[PSTContext pstctx] : arrLeft=arrow? '-' arrRight=arrow? ;

coord : '(' x=valueDim? ',' y=valueDim? ')';

comment[PSTContext pstctx] : COMMENT+ ;

text[PSTContext pstctx] : (.)*?~'\\' ;

show : 'all'  | 'x' | 'y' | 'none' ;

fillstyle : 'none' | 'gradient' | 'solid' | 'vlines*' | 'vlines' | 'hlines*' | 'hlines' | 'crosshatch*' | 'crosshatch' ;

put : 'U' | 'L' | 'D' | 'R' | 'N' | 'W' | 'S' | 'E' ;

textpos : 'bl' | 'br' | 'b' | 'tl' | 'tr' | 't' | 'Bl' | 'Br' | 'B' | 'l' | 'r' ;

colortype : 'rgb' | 'RGB' | 'gray' | 'html' | 'cmyk' | 'cmy' | 'hsb' ;

arrow : '>' | '<' | '|' | '[' | ']' | '*' | '**' | '<<' | '>>' | 'c' | 'C' | 'cc' | '|*' | '(' | ')' | 'o' | 'oo' ;

unit : 'cm' | 'mm' | 'pt' | 'in' ;

booleanvalue : 'true' | 'false' ;

DOUBLEMATHTEXT : MATHMODE MATHTEXT MATHMODE ;

MATHTEXTPAR : '\\(' .*? '\\)' ;

MATHTEXTBRACK : '\\[' .*? '\\]' ;

MATHTEXT : MATHMODE .*? MATHMODE ;

LATEXCMD : '\\' ('\''|'`'|'^'|'¨'|'~'|'"'|'='|'.')? (IDENT|'&'|'@'|'['|']'|'{'|'}'|','|'#'|'_'|'%'|'('|')'|'$') ('`'|'\''|'='|'_')? ;

WORD : LETTER (LETTER | DIGIT)* ;

IDENT : WORD '*'? ;

NUMBER: ('+'|'-')* (('.' DIGIT+) | (DIGIT+ ('.' DIGIT*)? )) ;

INT: ('+'|'-')* DIGIT+ ;

fragment LETTER : 'A'..'Z' | 'a'..'z' | '\u00C0'..'\u00D6' | '\u00D8'..'\u00F6'| '\u00F8'..'\u02FF'| '\u0370'..'\u037D'| '\u037F'..'\u1FFF' |
                    '\u200C'..'\u200D'| '\u2070'..'\u218F'| '\u2C00'..'\u2FEF'| '\u3001'..'\uD7FF'| '\uF900'..'\uFDCF'| '\uFDF0'..'\uFFFD';

fragment DIGIT: '0'..'9' ;

COMMENT :  '%' ~('\r' | '\n')* ;

PUNCTUATION : [-+*.,?;:/!'"`°_^#~–] ;

BRACE_OPEN: '{' ;

BRACE_CLOSE: '}' ;

MATHMODE : '$' ;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;
