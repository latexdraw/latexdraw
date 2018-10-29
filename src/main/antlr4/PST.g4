/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
 grammar PST;

@header {
package net.sf.latexdraw.parsers.pst;
}

// PST is a internal DSL of LaTeX. PST is a dynamic language so defining its grammar may be considered as a non-sense.
// PST, however, can be considered as a static language (i.e., all its commands are known and predefined) as the PST packages are quite stable
// since many years.
// The goal of this parser is to ease the process of common PST commands.
// PSTContext is a grammar context -- a Java implementation is provided -- that supplements the ANTLR one with information related to the current PST values.
pstCode[PSTContext pstctx] : (pstBlock[pstctx] | psset[pstctx] | pspictureBlock[pstctx] | centerBlock[pstctx] | pspicturecmd[pstctx] |
        psellipse[new PSTContext(pstctx, true)] | psframe[new PSTContext(pstctx, true)] |
        psdiamond[new PSTContext(pstctx, true)] | pstriangle[new PSTContext(pstctx, true)] |
        psline[new PSTContext(pstctx, true)] | psqline[new PSTContext(pstctx, true)] | pscircle[new PSTContext(pstctx, true)] | psqdisk[new PSTContext(pstctx, true)] |
        pspolygon[new PSTContext(pstctx, true)] | psbezier[new PSTContext(pstctx, true)] | psdot[new PSTContext(pstctx, true)] | psdots[new PSTContext(pstctx, true)] |
        psaxes[new PSTContext(pstctx, true)] | psgrid[new PSTContext(pstctx, true)] | pswedge[new PSTContext(pstctx, true)] | psellipticarc[new PSTContext(pstctx, true)] |
        psellipticarcn[new PSTContext(pstctx, true)] | psarcn[new PSTContext(pstctx, true)] |
        psarc[new PSTContext(pstctx, true)] | parabola[new PSTContext(pstctx, true)] | pscurve[new PSTContext(pstctx, true)] | psecurve[new PSTContext(pstctx, true)] |
        psccurve[new PSTContext(pstctx, true)] | readdata[new PSTContext(pstctx, true)] | parametricplot[new PSTContext(pstctx, true)] |
        psplot[new PSTContext(pstctx, true)] | listplot[new PSTContext(pstctx, true)] | dataplot[new PSTContext(pstctx, true)] | fileplot[new PSTContext(pstctx, true)] |
        pscustom[new PSTContext(pstctx, true)] | includegraphics[new PSTContext(pstctx, true)] |
        psframebox[new PSTContext(pstctx, true)] | psdblframebox[new PSTContext(pstctx, true)] | psshadowbox[new PSTContext(pstctx, true)] | pscirclebox[new PSTContext(pstctx, true)] |
        psovalbox[new PSTContext(pstctx, true)] | psdiabox[new PSTContext(pstctx, true)] | pstribox[new PSTContext(pstctx, true)] |
        rput[pstctx] | psrotate[pstctx] | scalebox[pstctx] | psscalebox[pstctx] | definecolor[pstctx] | newpsobject[pstctx] | newpsstyle[pstctx] | textcolor[pstctx] |
        savedata[pstctx] | color[pstctx] | unknowncmds[pstctx] | text[pstctx])* ;

pstcustomBlock[PSTContext pstctx]
@init {
	PSTContext newpstctx = new PSTContext(pstctx, false);
}
    : BRACE_OPEN (rlineto[newpstctx] | movepath[newpstctx] | closedshadow[newpstctx] | openshadow[newpstctx] | mrestore[newpstctx] |
        msave[newpstctx] | swapaxes[newpstctx] | rotate[newpstctx] | scale[newpstctx] | translate[newpstctx] | fill[newpstctx] |
        stroke[newpstctx] | grestore[newpstctx] | gsave[newpstctx] | rcurveto[newpstctx] | closepath[newpstctx] | curveto[newpstctx] | lineto[newpstctx] |
        moveto[pstctx] | newpath[newpstctx])* BRACE_CLOSE ;

pstBlock[PSTContext pstctx]
@init {
	PSTContext newpstctx = new PSTContext(pstctx, false);
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

pspolygon[PSTContext pstctx] : cmd=('\\pspolygon*' | '\\pspolygon') paramBlock[pstctx]?  p1=coord  ps+=coord+ ;

psbezier[PSTContext pstctx] : cmd=('\\psbezier*' | '\\psbezier') paramBlock[pstctx]? arrowBlock[pstctx]? (p1+=coord p2+=coord p3+=coord)* p4=coord? ;

psdot[PSTContext pstctx] : cmd=('\\psdot*' | '\\psdot') paramBlock[pstctx]? pt=coord? ;

psdots[PSTContext pstctx] : cmd=('\\psdots*' | '\\psdots') paramBlock[pstctx]? pts+=coord+ ;

psaxes[PSTContext pstctx] : '\\psaxes' paramBlock[pstctx]? arrowBlock[pstctx]? p1=coord p2=coord? p3=coord? ;

psgrid[PSTContext pstctx] : '\\psgrid' paramBlock[pstctx]? p1=coord? p2=coord? p3=coord? ;

psrotate[PSTContext pstctx]
@init {
	PSTContext newpstctx = new PSTContext(pstctx, false);
}
    : '\\psrotate' paramBlock[pstctx]? centre=coord angle=bracketValueDim pstBlock[newpstctx] ;

rput[PSTContext pstctx]
@init {
	PSTContext newpstctx = new PSTContext(pstctx, false);
}
    : cmd=('\\rput*' | '\\rput') ('[' textpos? { newpstctx.textPosition = $textpos.text; } ']')?
    (BRACE_OPEN star='*'? (rot=valueDim | angleChar=put) {newpstctx.setRputAngle($star, $rot.ctx, $angleChar.ctx);} BRACE_CLOSE)? coord pstBlock[newpstctx] ;

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

textcolor[PSTContext pstctx] : '\\textcolor' BRACE_OPEN name=WORD BRACE_CLOSE pstBlock[pstctx] ;

color[PSTContext pstctx] : '\\color' BRACE_OPEN name=WORD BRACE_CLOSE ;

unknowncmds[PSTContext pstctx] : LATEXCMD ( ('[' ~(']') ']') | (BRACE_OPEN ~BRACE_CLOSE BRACE_CLOSE) | ('(' ~(')') ')') )* ;

readdata[PSTContext pstctx] : '\\readdata' paramBlock[pstctx]? BRACE_OPEN LATEXCMD BRACE_CLOSE BRACE_OPEN (.|'/'|':')*? ~(BRACE_CLOSE) BRACE_CLOSE ;

savedata[PSTContext pstctx] : '\\savedata' BRACE_OPEN LATEXCMD BRACE_CLOSE '[' .*? ~(']') ']' ;

parametricplot[PSTContext pstctx] : cmd=('\\parametricplot*' | '\\parametricplot') paramBlock[pstctx]? BRACE_OPEN xmin=NUMBER BRACE_CLOSE BRACE_OPEN xmax=NUMBER BRACE_CLOSE BRACE_OPEN fct+=text[pstctx]+ ~(BRACE_CLOSE) BRACE_CLOSE ;

psplot[PSTContext pstctx] : cmd=('\\psplot*' | '\\psplot') paramBlock[pstctx]? BRACE_OPEN x0=NUMBER BRACE_CLOSE BRACE_OPEN x1=NUMBER BRACE_CLOSE BRACE_OPEN fct+=text[pstctx]+ BRACE_CLOSE ;

listplot[PSTContext pstctx] : cmd=('\\listplot*' | '\\listplot') paramBlock[pstctx]? BRACE_OPEN LATEXCMD+ BRACE_CLOSE ;

dataplot[PSTContext pstctx] : cmd=('\\listplot*' | '\\listplot') paramBlock[pstctx]? BRACE_OPEN LATEXCMD+ BRACE_CLOSE ;

fileplot[PSTContext pstctx] : cmd=('\\listplot*' | '\\listplot') paramBlock[pstctx]? BRACE_OPEN LATEXCMD+ BRACE_CLOSE ;

newpsobject[PSTContext pstctx] : '\\newpsobject' BRACE_OPEN name=IDENT BRACE_CLOSE BRACE_OPEN obj=IDENT BRACE_CLOSE BRACE_OPEN attrs=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

newpsstyle[PSTContext pstctx] : '\\newpsstyle' ('[' pkgname=.*? ~(']') ']')? BRACE_OPEN name=IDENT BRACE_CLOSE BRACE_OPEN def=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

pscustom[PSTContext pstctx] : cmd=('\\pscustom*' | '\\pscustom') paramBlock[pstctx]? pstcustomBlock[pstctx] ;

definecolor[PSTContext pstctx] : '\\definecolor' BRACE_OPEN name=WORD BRACE_CLOSE BRACE_OPEN colortype=WORD BRACE_CLOSE BRACE_OPEN (NUMBER (',' NUMBER)* | HEXA) BRACE_CLOSE ;

includegraphics[PSTContext pstctx] : '\\includegraphics' paramBlock[pstctx] BRACE_OPEN path=.*? ~(BRACE_CLOSE) BRACE_CLOSE ;

pspicturecmd[PSTContext pstctx] : '\\pspicture' p1=coord? p2=coord? {$pstctx.setPspicturePoints($p1.ctx, $p2.ctx);} pstCode[pstctx] '\\endpspicture' ;

pspictureBlock[PSTContext pstctx] locals [boolean hasStar = false;] : '\\begin' BRACE_OPEN (('pspicture*' {$hasStar=true;}) | ('pspicture' {$hasStar=false;}))
        BRACE_CLOSE p1=coord? p2=coord? {$pstctx.setPspicturePoints($p1.ctx, $p2.ctx);} pstCode[pstctx] '\\end' BRACE_OPEN ({$hasStar}? 'pspicture*' | 'pspicture') BRACE_CLOSE ;

centerBlock[PSTContext pstctx] : '\\begin' BRACE_OPEN 'center' BRACE_CLOSE pstCode[pstctx] '\\end' BRACE_OPEN 'center' BRACE_CLOSE ;

psset[PSTContext pstctx] : '\\psset' BRACE_OPEN (paramSetting[pstctx] (',' paramSetting[pstctx])*)? BRACE_CLOSE ;

paramBlock[PSTContext pstctx] : '[' (paramSetting[pstctx] (',' paramSetting[pstctx])*)? ']' ;

bracketValueDim : BRACE_OPEN valueDim BRACE_CLOSE ;

valueDim : NUMBER unit? ;

// This complefixies the grammar but eases, IMO, the support of the predefined PST commands' parameters
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
        paramdotsize[pstctx] | paramgangle[pstctx] | paramstrokeopacity[pstctx] | paramopacity[pstctx] | paramdx[pstctx] | paramdy[pstctx] |
        paramDx[pstctx] | paramDy[pstctx] | paramOx[pstctx] | paramOy[pstctx] | parampolarplot[pstctx] | unknownParamSetting[pstctx] ;

unknownParamSetting[PSTContext pstctx] : name=WORD '=' (valueDim | NUMBER | booleanvalue | WORD)+ ;

paramArrow[PSTContext pstctx] : 'arrows' '=' arrowvalue[pstctx] ;
paramArrowscale[PSTContext pstctx] : 'arrowscale' '=' sx=valueDim sy=valueDim? ;
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
paramlinestyle[PSTContext pstctx] : 'linestyle' '=' style=('none' | 'solid' | 'dashed' | 'dotted') ;
parambordercolor[PSTContext pstctx] : 'bordercolor' '=' WORD ;
paramdoubleline[PSTContext pstctx] : 'doubleline' '=' booleanvalue ;
paramdoublesep[PSTContext pstctx] : 'doublesep' '=' valueDim ;
paramdoublecolor[PSTContext pstctx] : 'doublecolor' '=' WORD ;
paramshadow[PSTContext pstctx] : 'shadow' '=' booleanvalue ;
paramshadowsize[PSTContext pstctx] : 'shadowsize' '=' valueDim ;
paramshadowangle[PSTContext pstctx] : 'shadowangle' '=' NUMBER ;
paramshadowcolor[PSTContext pstctx] : 'shadowcolor' '=' WORD ;
paramdimen[PSTContext pstctx] : 'dimen' '=' type=('outer' | 'inner' | 'middle') ;
paramfillstyle[PSTContext pstctx] : 'fillstyle' '=' fillstyle ;
paramfillcolor[PSTContext pstctx] : 'fillcolor' '=' WORD ;
paramhatchwidth[PSTContext pstctx] : 'hatchwidth' '=' valueDim ;
paramhatchsep[PSTContext pstctx] : 'hatchsep' '=' valueDim ;
paramhatchcolor[PSTContext pstctx] : 'hatchcolor' '=' WORD ;
paramhatchangle[PSTContext pstctx] : 'hatchangle' '=' NUMBER ;
paramliftpen[PSTContext pstctx] : 'liftpen' '=' NUMBER ;
paramlabelsep[PSTContext pstctx] : 'labelsep' '=' valueDim ;
paramlabels[PSTContext pstctx] : 'labels' '=' show ;
paramticks[PSTContext pstctx] : 'ticks' '=' show ;
paramtickstyle[PSTContext pstctx] : 'tickstyle' '=' style=('full'  | 'top' | 'bottom') ;
paramshoworigin[PSTContext pstctx] : 'showorigin' '=' booleanvalue ;
paramticksize[PSTContext pstctx] : 'ticksize' '=' valueDim ;
paramaxesstyle[PSTContext pstctx] : 'axesstyle' '=' style=('axes' | 'frame' | 'none') ;
paramframesep[PSTContext pstctx] : 'framesep' '=' valueDim ;
paramboxsep[PSTContext pstctx] : 'boxsep' '=' booleanvalue ;
paramrunit[PSTContext pstctx] : 'runit' '=' valueDim ;
paramlinewidth[PSTContext pstctx] : 'linewidth' '=' valueDim ;
paramlinecolor[PSTContext pstctx] : 'linecolor' '=' WORD ;
paramshowpoints[PSTContext pstctx] : 'showpoints' '=' booleanvalue ;
paramlinearc[PSTContext pstctx] : 'linearc' '=' valueDim ;
paramcornersize[PSTContext pstctx] : 'cornersize' '=' type=('relative' | 'absolute') ;
paramarcsepA[PSTContext pstctx] : 'arcsepA' '=' valueDim ;
paramarcsepB[PSTContext pstctx] : 'arcsepB' '=' valueDim ;
paramarcsep[PSTContext pstctx] : 'arcsep' '=' valueDim ;
paramcurvature[PSTContext pstctx] : 'curvature' '=' num1=NUMBER num2=NUMBER num3=NUMBER ;
paramdotstyle[PSTContext pstctx] : 'dotstyle' '=' style=('*' | 'o' | '+' | 'triangle' | 'triangle*' | 'square' | 'square*' | 'pentagon' | 'pentagon*' | '|' |
                                                    'otimes' | 'oplus' | 'x' | 'asterisk' | 'diamond*' | 'diamond') ;
paramdotscale[PSTContext pstctx] : 'dotscale' '=' num1=NUMBER num2=NUMBER?;
paramdotdotangle[PSTContext pstctx] : 'dotangle' '=' NUMBER ;
paramgridwidth[PSTContext pstctx] : 'gridwidth' '=' valueDim ;
paramgridcolor[PSTContext pstctx] : 'gridcolor' '=' WORD ;
paramgriddots[PSTContext pstctx] : 'griddots' '=' NUMBER ;
paramgridlabels[PSTContext pstctx] : 'gridlabels' '=' valueDim ;
paramgridlabelcolor[PSTContext pstctx] : 'gridlabelcolor' '=' WORD ;
paramsubgriddiv[PSTContext pstctx] : 'subgriddiv' '=' NUMBER ;
paramsubgridwidth[PSTContext pstctx] : 'subgridwidth' '=' valueDim ;
paramsubgridcolor[PSTContext pstctx] : 'subgridcolor' '=' WORD ;
paramsubgriddots[PSTContext pstctx] : 'subgriddots' '=' NUMBER ;
paramplotstyle[PSTContext pstctx] : 'plotstyle' '=' style=('dots' | 'line' | 'polygon' | 'curve' | 'ecurve' | 'ccurve') ;
paramplotpoints[PSTContext pstctx] : 'plotpoints' '=' NUMBER ;
paramgradbegin[PSTContext pstctx] : 'gradbegin' '=' WORD ;
paramgradend[PSTContext pstctx] : 'gradend' '=' WORD ;
paramgradlines[PSTContext pstctx] : 'gradlines' '=' NUMBER ;
paramgradmidpoint[PSTContext pstctx] : 'gradmidpoint' '=' NUMBER ;
paramgradangle[PSTContext pstctx] : 'gradangle' '=' NUMBER ;
paramdotsize[PSTContext pstctx] : 'dotsize' '=' dim=valueDim num=NUMBER? ;
paramgangle[PSTContext pstctx] : 'gangle' '=' NUMBER ;
paramstrokeopacity[PSTContext pstctx] : 'strokeopacity' '=' NUMBER ;
paramopacity[PSTContext pstctx] : 'opacity' '=' NUMBER ;
paramdx[PSTContext pstctx] : 'dx' '=' valueDim ;
paramdy[PSTContext pstctx] : 'dy' '=' valueDim ;
paramDx[PSTContext pstctx] : 'Dx' '=' NUMBER ;
paramDy[PSTContext pstctx] : 'Dy' '=' NUMBER ;
paramOx[PSTContext pstctx] : 'Ox' '=' NUMBER ;
paramOy[PSTContext pstctx] : 'Oy' '=' NUMBER ;
parampolarplot[PSTContext pstctx] : 'polarplot' '=' booleanvalue ;

arrowBlock[PSTContext pstctx] : BRACE_OPEN arrowvalue[pstctx] BRACE_CLOSE ;

arrowvalue[PSTContext pstctx] : arrLeft=arrow? '-' arrRight=arrow? ;

coord : '(' x=valueDim? ',' y=valueDim? ')';

text[PSTContext pstctx] : ~(BRACE_OPEN|BRACE_CLOSE)+? ;

show : 'all'  | 'x' | 'y' | 'none' ;

fillstyle : 'none' | 'gradient' | 'solid' | 'vlines*' | 'vlines' | 'hlines*' | 'hlines' | 'crosshatch*' | 'crosshatch'  | 'clines*' | 'clines';

put : 'U' | 'L' | 'D' | 'R' | 'N' | 'W' | 'S' | 'E' ;

textpos : 'bl' | 'br' | 'b' | 'tl' | 'tr' | 't' | 'Bl' | 'Br' | 'B' | 'l' | 'r' ;

arrow : '>' | '<' | '|' | '[' | ']' | '*' | '**' | '<<' | '>>' | 'c' | 'C' | 'cc' | '|*' | '(' | ')' | 'o' | 'oo' ;

unit : 'cm' | 'mm' | 'pt' | 'in' ;

booleanvalue : 'true' | 'false' ;

HEXA : '#' [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F] ;

DOUBLEMATHTEXT : MATHMODE MATHTEXT MATHMODE ;

MATHTEXTPAR : '\\(' .*? '\\)' ;

MATHTEXTBRACK : '\\[' .*? '\\]' ;

MATHTEXT : MATHMODE .*? MATHMODE ;

LATEXCMD : '\\' (IDENT|'&'|'@'|'['|']'|'{'|'}'|','|'#'|'_'|'%'|'('|')'|'$'|'\\'|'*'|'/'|'`'|'\''|'='|'"'|'^'|'~'|'.') ;

WORD : LETTER (LETTER | DIGIT)* ;

IDENT : WORD '*'? ;

NUMBER: ('+'|'-')* (('.' DIGIT+) | (DIGIT+ ('.' DIGIT*)? )) ;

fragment LETTER : 'A'..'Z' | 'a'..'z' | '\u00C0'..'\u00D6' | '\u00D8'..'\u00F6'| '\u00F8'..'\u02FF'| '\u0370'..'\u037D'| '\u037F'..'\u1FFF' |
                    '\u200C'..'\u200D'| '\u2070'..'\u218F'| '\u2C00'..'\u2FEF'| '\u3001'..'\uD7FF'| '\uF900'..'\uFDCF'| '\uFDF0'..'\uFFFD';

fragment DIGIT: '0'..'9' ;

COMMENT :  '%' ~('\r' | '\n')* -> skip ;

PUNCTUATION : [-+*.,?;:/!'"`°_^#~–\\] ;

BRACE_OPEN: '{' ;

BRACE_CLOSE: '}' ;

MATHMODE : '$' ;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip ;
