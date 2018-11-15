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
grammar SVGPath;

@header {
package net.sf.latexdraw.parser.svg;
}

svgpath : moveTo (moveTo|lineTo|closePath|hLineTo|vLineTo|curveTo|smoothCurveTo|quadraBezierCurveTo|smoothQuadraBezierCurveTo|ellipticalArc)+;

ellipticalArc: ellipticalArcRel | ellipticalArcAbs;
ellipticalArcRel: 'a' ellipticalArcSeq;
ellipticalArcAbs: 'A' ellipticalArcSeq;
ellipticalArcSeq: ellipticalArcArgument (','? ellipticalArcArgument)*;
ellipticalArcArgument: rx=NUMBER ','? ry=NUMBER ','? xAxisRot=NUMBER ','? largeArcFlag=NUMBER ','? sweepFlag=NUMBER ','? p=coordPair;

smoothQuadraBezierCurveTo: smoothQuadraBezierCurveToRel | smoothQuadraBezierCurveToAbs;
smoothQuadraBezierCurveToRel: 't' coordPairSeq;
smoothQuadraBezierCurveToAbs: 'T' coordPairSeq;

quadraBezierCurveTo: quadraBezierCurveToRel | quadraBezierCurveToAbs;
quadraBezierCurveToRel: 'q' quadraCoordSeq;
quadraBezierCurveToAbs: 'Q' quadraCoordSeq;
quadraCoordSeq: coordPairDouble (','? curveCoordSeq)*;

smoothCurveTo: smoothCurveToRel | smoothCurveToAbs;
smoothCurveToRel: 's' smoothCoordSeq;
smoothCurveToAbs: 'S' smoothCoordSeq;
smoothCoordSeq: coordPairDouble (','? coordPairDouble)*;
coordPairDouble: p2=coordPair ','? p=coordPair;

curveTo: curveToRel | curveToAbs;
curveToRel: 'c' curveCoordSeq;
curveToAbs: 'C' curveCoordSeq;
curveCoordSeq: coordTriple (','? coordTriple)*;
coordTriple: p1=coordPair ','? p2=coordPair ','? p=coordPair;

hLineTo: hLineToRel | hLineToAbs;
hLineToRel: 'h' x=coordSeq;
hLineToAbs: 'H' x=coordSeq;

vLineTo: vLineToRel | vLineToAbs;
vLineToRel: 'v' y=coordSeq;
vLineToAbs: 'V' y=coordSeq;

closePath: ('z'|'Z');

lineTo: lineToRel | lineToAbs;
lineToRel: 'l' p=coordPairSeq;
lineToAbs: 'L' p=coordPairSeq;

moveTo: moveToRel | moveToAbs;
moveToRel: 'm' p=coordPairSeq;
moveToAbs: 'M' p=coordPairSeq;

coordPairSeq: coordPair (','? coordPair)*;
coordPair: x=NUMBER ','? y=NUMBER;
coordSeq: coords=coord+;
coord: value=NUMBER;

NUMBER: ('+'|'-')* (('.' DIGIT+) | (DIGIT+ ('.' DIGIT*)? ));
fragment DIGIT: '0'..'9' ;
WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip;
