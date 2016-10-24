/*
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 */
package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.views.latex.DviPsColors;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.glib.views.latex.VerticalPosition;
import net.sf.latexdraw.util.LNumber;
import net.sf.latexdraw.util.LResources;
import org.eclipse.jdt.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a PSTricks generator; it manages the PSTricks views and the latex additional code.
 */
public class PSTCodeGenerator extends LaTeXGenerator {
	static final @NonNull String PACKAGE_PSTRICKS = "% \\usepackage[usenames,dvipsnames]{pstricks}" + LResources.EOL +//$NON-NLS-1$
		"% \\usepackage{epsfig}" + LResources.EOL + "% \\usepackage{pst-grad} % For gradients" +//$NON-NLS-1$//$NON-NLS-2$
		LResources.EOL + "% \\usepackage{pst-plot} % For axes" + LResources.EOL; //$NON-NLS-1$

	public static final @NonNull String PACKAGE_FOR_SPACE_PICTURE = "\\usepackage[space]{grffile} % For spaces in paths" + LResources.EOL +
		"\\usepackage{etoolbox} % For spaces in paths" + LResources.EOL + "\\makeatletter % For spaces in paths" + LResources.EOL +
		"\\patchcmd\\Gread@eps{\\@inputcheck#1 }{\\@inputcheck\"#1\"\\relax}{}{}" + LResources.EOL + "\\makeatother" + LResources.EOL;


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

		doc.append("\\documentclass{article}").append(LResources.EOL).append("\\pagestyle{empty}").append(LResources.EOL).append(getPackages()).append(LResources.EOL).append( //$NON-NLS-1$ //$NON-NLS-2$
			"\\usepackage[left=0cm,top=0.1cm,right=0cm,bottom=0cm,nohead,nofoot,paperwidth=").append( //$NON-NLS-1$
			tr.getX() / ppc * scale).append("cm,paperheight=").append( //$NON-NLS-1$
			bl.getY() / ppc * scale + 0.2).append("cm]{geometry}").append( //$NON-NLS-1$
			LResources.EOL).append("\\usepackage[usenames,dvipsnames]{pstricks}").append(//$NON-NLS-1$
			LResources.EOL).append("\\usepackage{epsfig}").append(//$NON-NLS-1$
			LResources.EOL).append("\\usepackage{pst-grad}").append(LResources.EOL).append("\\usepackage{pst-plot}").append(LResources.EOL).append(//$NON-NLS-1$//$NON-NLS-2$
			PSTCodeGenerator.PACKAGE_FOR_SPACE_PICTURE).append("\\begin{document}").append(LResources.EOL).append( //$NON-NLS-1$
			"\\addtolength{\\oddsidemargin}{-0.2in}").append(LResources.EOL).append("\\addtolength{\\evensidemargin}{-0.2in}").append( //$NON-NLS-1$ //$NON-NLS-2$
			LResources.EOL).append(getDrawingCode()).append(LResources.EOL).append("\\end{document}");//$NON-NLS-1$

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

		if(withComments && comment != null && !comment.isEmpty()) cache.append(comment);

		cache.append(PACKAGE_PSTRICKS).append("% ").append(PACKAGE_FOR_SPACE_PICTURE.replaceAll(LResources.EOL, LResources.EOL + "% "));

		if(!pkg.isEmpty()) {
			pkg = "% User Packages:" + LResources.EOL + "% " + pkg.replace(LResources.EOL, LResources.EOL + "% "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			cache.append(pkg).append(LResources.EOL);
		}

		if(withLatexParams && (positionVertToken != VerticalPosition.NONE || !caption.isEmpty() || !label.isEmpty())) {
			cache.append("\\begin{figure}"); //$NON-NLS-1$

			if(positionVertToken == VerticalPosition.NONE) cache.append(LResources.EOL);
			else cache.append('[').append(positionVertToken.getToken()).append(']').append(LResources.EOL);

			hasBeginFigure = true;
		}else hasBeginFigure = false;

		if(withLatexParams && positionHoriCentre) cache.append("\\begin{center}").append(LResources.EOL);//$NON-NLS-1$

		final float scaleF = LNumber.getCutNumberFloat(getScale());
		cache.append("\\psscalebox{").append(scaleF).append(' ').append(scaleF).append("} % Change this value to rescale the drawing.");//$NON-NLS-1$ //$NON-NLS-2$
		cache.append(LResources.EOL).append('{').append(LResources.EOL);
		cache.append("\\begin{pspicture}("); //$NON-NLS-1$
		cache.append(0).append(',').append(LNumber.getCutNumberFloat((origin.getY() - br.getY()) / ppc)).append(')').append('(');
		cache.append(LNumber.getCutNumberFloat((tl.getX() - origin.getX()) / ppc)).append(',').append(LNumber.getCutNumberFloat((origin.getY() - tl.getY()) / ppc));
		cache.append(')').append(LResources.EOL);

		drawing.getShapes().forEach(shape -> PSTViewsFactory.INSTANCE.createView(shape).ifPresent(pstView -> {
			shapeCode.append(pstView.getCode(origin, ppc)).append(LResources.EOL);
			cache.append(generateColourCode(pstView, addedColours));
		}));

		cache.append(shapeCode).append("\\end{pspicture}").append(LResources.EOL).append('}').append(LResources.EOL); //$NON-NLS-1$

		if(withLatexParams) {
			if(positionHoriCentre) cache.append("\\end{center}").append(LResources.EOL);//$NON-NLS-1$
			if(!label.isEmpty()) cache.append("\\label{").append(label).append('}').append(LResources.EOL);//$NON-NLS-1$
			if(!caption.isEmpty()) cache.append("\\caption{").append(caption).append('}').append(LResources.EOL);//$NON-NLS-1$
			if(hasBeginFigure) cache.append("\\end{figure}").append(LResources.EOL);//$NON-NLS-1$
		}

		return cache.toString();
	}


	/**
	 * Adds the PST colour code to the cache.
	 * @param pstView The shape which colour code will be generated.
	 * @param addedColours The PST colours already generated.
	 * @since 3.0
	 */
	private @NonNull String generateColourCode(final @NonNull PSTShapeView<?> pstView, final @NonNull Map<String, String> addedColours) {
		if(pstView.coloursName != null) {
			for(final String nameColour : pstView.coloursName) {
				if(addedColours.get(nameColour) == null && !DviPsColors.INSTANCE.getPredefinedColour(nameColour).isPresent()) {
					addedColours.put(nameColour, nameColour);
					return DviPsColors.INSTANCE.getUsercolourCode(nameColour) + LResources.EOL;
				}
			}
		}
		return ""; //$NON-NLS-1$
	}
}
