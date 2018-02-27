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
package net.sf.latexdraw.view.pst;

import java.util.HashMap;
import java.util.Map;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.view.latex.DviPsColors;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.latex.VerticalPosition;

/**
 * Defines a PSTricks generator; it manages the PSTricks views and the latex additional code.
 * @author Arnaud Blouin
 */
public class PSTCodeGenerator extends LaTeXGenerator {
	static final  String PACKAGE_PSTRICKS = "% \\usepackage[usenames,dvipsnames]{pstricks}" + LSystem.EOL +//$NON-NLS-1$
		"% \\usepackage{epsfig}" + LSystem.EOL + "% \\usepackage{pst-grad} % For gradients" +//$NON-NLS-1$//$NON-NLS-2$
		LSystem.EOL + "% \\usepackage{pst-plot} % For axes" + LSystem.EOL; //$NON-NLS-1$

	public static final  String PACKAGE_FOR_SPACE_PICTURE = "\\usepackage[space]{grffile} % For spaces in paths" + LSystem.EOL +
		"\\usepackage{etoolbox} % For spaces in paths" + LSystem.EOL + "\\makeatletter % For spaces in paths" + LSystem.EOL +
		"\\patchcmd\\Gread@eps{\\@inputcheck#1 }{\\@inputcheck\"#1\"\\relax}{}{}" + LSystem.EOL + "\\makeatother" + LSystem.EOL;


	/**
	 * Creates and initialises the generator.
	 */
	public PSTCodeGenerator() {
		super();
	}


	@Override
	public String getDocumentCode() {
		final StringBuilder doc = new StringBuilder();
		final IPoint bl = handler.getBottomLeftDrawingPoint();
		final IPoint tr = handler.getTopRightDrawingPoint();
		final float ppc = handler.getPPCDrawing();

		if(tr.getY() < 0) {
			bl.setY(bl.getY() - tr.getY());
			tr.setY(0.0);
		}

		doc.append("\\documentclass{article}").append(LSystem.EOL).append("\\pagestyle{empty}").append(LSystem.EOL).append(getPackages()).append(LSystem.EOL).append( //$NON-NLS-1$ //$NON-NLS-2$
			"\\usepackage[left=0cm,top=0.1cm,right=0cm,bottom=0cm,nohead,nofoot,paperwidth=").append( //$NON-NLS-1$
			tr.getX() / ppc * scale).append("cm,paperheight=").append( //$NON-NLS-1$
			bl.getY() / ppc * scale + 0.2).append("cm]{geometry}").append( //$NON-NLS-1$
			LSystem.EOL).append("\\usepackage[usenames,dvipsnames]{pstricks}").append(//$NON-NLS-1$
			LSystem.EOL).append("\\usepackage{epsfig}").append(//$NON-NLS-1$
			LSystem.EOL).append("\\usepackage{pst-grad}").append(LSystem.EOL).append("\\usepackage{pst-plot}").append(LSystem.EOL).append(//$NON-NLS-1$//$NON-NLS-2$
			PSTCodeGenerator.PACKAGE_FOR_SPACE_PICTURE).append("\\begin{document}").append(LSystem.EOL).append( //$NON-NLS-1$
			"\\addtolength{\\oddsidemargin}{-0.2in}").append(LSystem.EOL).append("\\addtolength{\\evensidemargin}{-0.2in}").append( //$NON-NLS-1$ //$NON-NLS-2$
			LSystem.EOL).append(getDrawingCode()).append(LSystem.EOL).append("\\end{document}");//$NON-NLS-1$

		return doc.toString();
	}


	@Override
	public String getDrawingCode() {
		if(drawing.isEmpty()) return "";

		final StringBuilder cache = new StringBuilder();
		String pkg = LaTeXGenerator.getPackages();
		final IPoint origin = handler.getOriginDrawingPoint();
		final IPoint tl = handler.getTopRightDrawingPoint();
		final IPoint br = handler.getBottomLeftDrawingPoint();
		final int ppc = handler.getPPCDrawing();
		final Map<String, String> addedColours = new HashMap<>();
		final StringBuilder shapeCode = new StringBuilder();
		final boolean hasBeginFigure;

		if(withComments && comment != null && !comment.isEmpty()) cache.append(getCommentWithTag());

		cache.append(PACKAGE_PSTRICKS).append("% ").append(PACKAGE_FOR_SPACE_PICTURE.replaceAll(LSystem.EOL, LSystem.EOL + "% "));

		if(!pkg.isEmpty()) {
			pkg = "% User Packages:" + LSystem.EOL + "% " + pkg.replace(LSystem.EOL, LSystem.EOL + "% "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			cache.append(pkg).append(LSystem.EOL);
		}

		cache.append(LSystem.EOL);

		if(withLatexParams && (positionVertToken != VerticalPosition.NONE || !caption.isEmpty() || !label.isEmpty())) {
			cache.append("\\begin{figure}"); //$NON-NLS-1$

			if(positionVertToken == VerticalPosition.NONE) cache.append(LSystem.EOL);
			else cache.append('[').append(positionVertToken.getToken()).append(']').append(LSystem.EOL);

			hasBeginFigure = true;
		}else hasBeginFigure = false;

		if(withLatexParams && positionHoriCentre) cache.append("\\begin{center}").append(LSystem.EOL);//$NON-NLS-1$

		final float scaleF = MathUtils.INST.getCutNumberFloat(getScale());
		cache.append("\\psscalebox{").append(scaleF).append(' ').append(scaleF).append("} % Change this value to rescale the drawing.");//$NON-NLS-1$ //$NON-NLS-2$
		cache.append(LSystem.EOL).append('{').append(LSystem.EOL);
		cache.append("\\begin{pspicture}("); //$NON-NLS-1$
		cache.append(0).append(',').append(MathUtils.INST.getCutNumberFloat((origin.getY() - br.getY()) / ppc)).append(')').append('(');
		cache.append(MathUtils.INST.getCutNumberFloat((tl.getX() - origin.getX()) / ppc)).append(',').append(MathUtils.INST.getCutNumberFloat((origin.getY() - tl.getY()) / ppc));
		cache.append(')').append(LSystem.EOL);

		drawing.getShapes().forEach(shape -> PSTViewsFactory.INSTANCE.createView(shape).ifPresent(pstView -> {
			shapeCode.append(pstView.getCode(origin, ppc)).append(LSystem.EOL);
			cache.append(generateColourCode(pstView, addedColours));
		}));

		cache.append(shapeCode).append("\\end{pspicture}").append(LSystem.EOL).append('}').append(LSystem.EOL); //$NON-NLS-1$

		if(withLatexParams) {
			if(positionHoriCentre) cache.append("\\end{center}").append(LSystem.EOL);//$NON-NLS-1$
			if(!label.isEmpty()) cache.append("\\label{").append(label).append('}').append(LSystem.EOL);//$NON-NLS-1$
			if(!caption.isEmpty()) cache.append("\\caption{").append(caption).append('}').append(LSystem.EOL);//$NON-NLS-1$
			if(hasBeginFigure) cache.append("\\end{figure}").append(LSystem.EOL);//$NON-NLS-1$
		}

		return cache.toString();
	}


	/**
	 * Adds the PST colour code to the cache.
	 * @param pstView The shape which colour code will be generated.
	 * @param addedColours The PST colours already generated.
	 * @since 3.0
	 */
	private  String generateColourCode(final  PSTShapeView<?> pstView, final  Map<String, String> addedColours) {
		if(pstView.coloursName != null) {
			for(final String nameColour : pstView.coloursName) {
				if(addedColours.get(nameColour) == null && !DviPsColors.INSTANCE.getPredefinedColour(nameColour).isPresent()) {
					addedColours.put(nameColour, nameColour);
					return DviPsColors.INSTANCE.getUsercolourCode(nameColour) + LSystem.EOL;
				}
			}
		}
		return ""; //$NON-NLS-1$
	}
}
