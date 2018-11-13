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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.latex.VerticalPosition;
import org.jetbrains.annotations.NotNull;

/**
 * Defines a PSTricks generator; it manages the PSTricks views and the latex additional code.
 * @author Arnaud Blouin
 */
public class PSTCodeGenerator extends LaTeXGenerator {
	private final String packagePstricks = "% \\usepackage[usenames,dvipsnames]{pstricks}" + SystemUtils.getInstance().EOL + //NON-NLS
		"% \\usepackage{pstricks-add}" + SystemUtils.getInstance().EOL + "% \\usepackage{epsfig}" + SystemUtils.getInstance().EOL + "% \\usepackage{pst-grad} % For gradients" + //NON-NLS
		SystemUtils.getInstance().EOL + "% \\usepackage{pst-plot} % For axes" + SystemUtils.getInstance().EOL; //NON-NLS

	private final String packageForSpacePicture = "\\usepackage[space]{grffile} % For spaces in paths" + SystemUtils.getInstance().EOL + //NON-NLS
		"\\usepackage{etoolbox} % For spaces in paths" + SystemUtils.getInstance().EOL + "\\makeatletter % For spaces in paths" + SystemUtils.getInstance().EOL + //NON-NLS
		"\\patchcmd\\Gread@eps{\\@inputcheck#1 }{\\@inputcheck\"#1\"\\relax}{}{}" + SystemUtils.getInstance().EOL + "\\makeatother" + SystemUtils.getInstance().EOL; //NON-NLS

	private final @NotNull PSTViewsFactory viewsFactory;

	/**
	 * Creates and initialises the generator.
	 */
	@Inject
	public PSTCodeGenerator(final Drawing drawing, final ViewsSynchroniserHandler handler, final PSTViewsFactory viewsFactory, final LaTeXDataService latexdata) {
		super(drawing, handler, latexdata);
		this.viewsFactory = Objects.requireNonNull(viewsFactory);
	}


	@Override
	public String getDocumentCode() {
		final StringBuilder doc = new StringBuilder();
		final Point bl = handler.getBottomLeftDrawingPoint();
		final Point tr = handler.getTopRightDrawingPoint();
		final float ppc = handler.getPPCDrawing();

		if(tr.getY() < 0d) {
			bl.setY(bl.getY() - tr.getY());
			tr.setY(0d);
		}

		doc.append("\\documentclass{article}").append(SystemUtils.getInstance().EOL).append("\\pagestyle{empty}"). //NON-NLS
			append(SystemUtils.getInstance().EOL).append(latexdata.getPackages()). //NON-NLS
			append(SystemUtils.getInstance().EOL).append("\\usepackage[left=0cm,top=0.1cm,right=0cm,bottom=0cm,nohead,nofoot,paperwidth="). //NON-NLS
			append(tr.getX() / ppc * latexdata.getScale()).append("cm,paperheight=").append(bl.getY() / ppc * latexdata.getScale() + 0.2).append("cm]{geometry}").append(SystemUtils.getInstance().EOL). //NON-NLS
			append("\\usepackage[usenames,dvipsnames]{pstricks}").append(SystemUtils.getInstance().EOL).append("\\usepackage{epsfig}").append(SystemUtils.getInstance().EOL). //NON-NLS
			append("\\usepackage{pst-grad}").append(SystemUtils.getInstance().EOL).append("\\usepackage{pst-plot}").append(SystemUtils.getInstance().EOL). //NON-NLS
			append(packageForSpacePicture).append("\\begin{document}").append(SystemUtils.getInstance().EOL). //NON-NLS
			append("\\addtolength{\\oddsidemargin}{-0.2in}").append(SystemUtils.getInstance().EOL).append("\\addtolength{\\evensidemargin}{-0.2in}"). //NON-NLS
			append(SystemUtils.getInstance().EOL).append(getDrawingCode()).append(SystemUtils.getInstance().EOL).append("\\end{document}"); //NON-NLS

		return doc.toString();
	}


	@Override
	public String getDrawingCode() {
		if(drawing.isEmpty()) {
			return "";
		}

		final StringBuilder cache = new StringBuilder();
		String pkg = latexdata.getPackages();
		final Point origin = handler.getOriginDrawingPoint();
		final Point tl = handler.getTopRightDrawingPoint();
		final Point br = handler.getBottomLeftDrawingPoint();
		final int ppc = handler.getPPCDrawing();
		final Set<String> addedColours = new HashSet<>();
		final StringBuilder shapeCode = new StringBuilder();
		final boolean hasBeginFigure;

		if(withComments && !latexdata.getComment().isEmpty()) {
			cache.append(latexdata.getCommentWithTag());
		}

		cache.append(packagePstricks).append("% ").append(packageForSpacePicture.replaceAll(SystemUtils.getInstance().EOL, SystemUtils.getInstance().EOL + "% "));

		if(!pkg.isEmpty()) {
			pkg = "% User Packages:" + SystemUtils.getInstance().EOL + "% " + pkg.replace(SystemUtils.getInstance().EOL, SystemUtils.getInstance().EOL + "% "); //NON-NLS
			cache.append(pkg).append(SystemUtils.getInstance().EOL);
		}

		cache.append(SystemUtils.getInstance().EOL);

		if(withLatexParams && (latexdata.getPositionVertToken() != VerticalPosition.NONE || !latexdata.getCaption().isEmpty() || !latexdata.getLabel().isEmpty())) {
			cache.append("\\begin{figure}"); //NON-NLS

			if(latexdata.getPositionVertToken() == VerticalPosition.NONE) {
				cache.append(SystemUtils.getInstance().EOL);
			}else {
				cache.append('[').append(latexdata.getPositionVertToken().getToken()).append(']').append(SystemUtils.getInstance().EOL);
			}

			hasBeginFigure = true;
		}else {
			hasBeginFigure = false;
		}

		if(withLatexParams && latexdata.isPositionHoriCentre()) {
			cache.append("\\begin{center}").append(SystemUtils.getInstance().EOL); //NON-NLS
		}

		final float scaleF = MathUtils.INST.getCutNumberFloat(latexdata.getScale());
		cache.append("\\psscalebox{").append(scaleF).append(' ').append(scaleF).append("} % Change this value to rescale the drawing."); //NON-NLS
		cache.append(SystemUtils.getInstance().EOL).append('{').append(SystemUtils.getInstance().EOL);
		cache.append("\\begin{pspicture}("); //NON-NLS
		cache.append(0).append(',').append(MathUtils.INST.getCutNumberFloat((origin.getY() - br.getY()) / ppc)).append(')').append('(');
		cache.append(MathUtils.INST.getCutNumberFloat((tl.getX() - origin.getX()) / ppc)).append(',').append(MathUtils.INST.getCutNumberFloat((origin.getY() - tl.getY()) / ppc));
		cache.append(')').append(SystemUtils.getInstance().EOL);

		drawing.getShapes().forEach(shape -> viewsFactory.createView(shape).ifPresent(pstView -> {
			shapeCode.append(pstView.getCode(origin, ppc)).append(SystemUtils.getInstance().EOL);
			cache.append(pstView.generateColourCode(addedColours)).append(SystemUtils.getInstance().EOL);
		}));

		cache.append(shapeCode).append("\\end{pspicture}").append(SystemUtils.getInstance().EOL).append('}').append(SystemUtils.getInstance().EOL); //NON-NLS

		if(withLatexParams) {
			if(latexdata.isPositionHoriCentre()) {
				cache.append("\\end{center}").append(SystemUtils.getInstance().EOL); //NON-NLS
			}
			if(!latexdata.getLabel().isEmpty()) {
				cache.append("\\label{").append(latexdata.getLabel()).append('}').append(SystemUtils.getInstance().EOL); //NON-NLS
			}
			if(!latexdata.getCaption().isEmpty()) {
				cache.append("\\caption{").append(latexdata.getCaption()).append('}').append(SystemUtils.getInstance().EOL); //NON-NLS
			}
			if(hasBeginFigure) {
				cache.append("\\end{figure}").append(SystemUtils.getInstance().EOL); //NON-NLS
			}
		}

		return cache.toString();
	}
}
