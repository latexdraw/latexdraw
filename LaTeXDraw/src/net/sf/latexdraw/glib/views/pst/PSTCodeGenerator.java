package net.sf.latexdraw.glib.views.pst;

import java.util.HashMap;
import java.util.Map;

import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.views.latex.DviPsColors;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.glib.views.synchroniser.ViewsSynchroniserHandler;
import net.sf.latexdraw.util.LNumber;

/**
 * Defines a PSTricks generator; it manages the PSTricks views and the latex additional code.
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/23/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class PSTCodeGenerator extends LaTeXGenerator {
	public static final String PACKAGE_PSTRICKS = "% \\usepackage[usenames,dvipsnames]{pstricks}\n% \\usepackage{epsfig}\n% \\usepackage{pst-grad} % For gradients\n% \\usepackage{pst-plot} % For axes\n"; //$NON-NLS-1$


	/** The PSTricks views. */
	protected PSTViewsSynchroniser synchro;

	/** The code cache. */
	protected StringBuffer cache;

	/** Defines if the latex parameters (position, caption, etc.) must be generated. */
	protected boolean withLatexParams;

	/** Defines if the comments must be generated. */
	protected boolean withComments;


	/**
	 * Creates and initialises the generator.
	 * @param drawing  The shapes used to generate PST code.
	 * @param handler The handler that provides information to the generator.
	 * @param withLatexParams Defines if the latex parameters (position, caption, etc.) must be generated.
	 * @param withComments Defines if the comments must be generated.
	 * @throws IllegalArgumentException If the given drawing parameter is null.
	 * @since 3.0
	 */
	public PSTCodeGenerator(final IDrawing drawing, final ViewsSynchroniserHandler handler, final boolean withLatexParams,
							final boolean withComments) {
		super();

		if(drawing==null)
			throw new IllegalArgumentException();

		this.withComments 	 	= withComments;
		this.withLatexParams 	= withLatexParams;
		synchro 				= new PSTViewsSynchroniser(handler, drawing);
		cache   				= new StringBuffer();
	}



	/**
	 * @return the synchroniser.
	 * @since 3.0
	 */
	public PSTViewsSynchroniser getSynchro() {
		return synchro;
	}



	/**
	 * @return the cache.
	 * @since 3.0
	 */
	public StringBuffer getCache() {
		return cache;
	}



	@Override
	public void update() {
		emptyCache();

		final IDrawing drawing	= synchro.getDrawing();
		StringBuffer code;
		final String eol 		= System.getProperty("line.separator");//$NON-NLS-1$
		String pkg 				= LaTeXGenerator.getPackages();
		PSTShapeView<?> pstView;
		final ViewsSynchroniserHandler handler = synchro.getHandler();
		final IPoint origin 	= handler.getOriginDrawingPoint();
		final IPoint tl 	  	= handler.getTopRightDrawingPoint();
		final IPoint br 	  	= handler.getBottomLeftDrawingPoint();
		final int ppc 	  		= handler.getPPCDrawing();
		final Map<String, String> addedColours = new HashMap<String, String>();
		final StringBuffer shapeCode = new StringBuffer();

		if(drawing.isEmpty())
			return ;

		if(withComments && comment!=null && comment.length()>0)
			cache.append(comment);

		cache.append(PACKAGE_PSTRICKS);

		if(pkg.length()>0) {
			pkg = "% User Packages:" + eol + "% " + pkg.replace(eol, eol + "% ");
			cache.append(pkg).append(eol);
		}

		if(withLatexParams && positionVertToken!=VerticalPosition.NONE)
			cache.append("\\begin{figure}[").append(positionVertToken.getToken()).append(']').append(eol);//$NON-NLS-1$

		if(withLatexParams && positionHoriCentre)
			cache.append("\\begin{center}").append(eol);//$NON-NLS-1$

		cache.append("\\psscalebox{1 1} % Change this value to rescale the drawing.");//$NON-NLS-1$
		cache.append(eol).append('{').append(eol);
		cache.append("\\begin{pspicture}("); //$NON-NLS-1$
		cache.append(0).append(',').append((float)LNumber.INSTANCE.getCutNumber((origin.getY()-br.getY())/ppc)).append(')').append('(');
		cache.append((float)LNumber.INSTANCE.getCutNumber((tl.getX()-origin.getX())/ppc)).append(',').append((float)LNumber.INSTANCE.getCutNumber((origin.getY()-tl.getY())/ppc));
		cache.append(')').append(eol);

		for(IShape shape : drawing.getShapes()) {
			pstView = synchro.getView(shape);

			if(pstView!=null) {
				code = pstView.getCache();

				if(code!=null)
					shapeCode.append(code).append(eol);

				generateColourCode(pstView, addedColours);
			}
		}

		cache.append(shapeCode).append("\\end{pspicture}").append(eol).append('}').append(eol); //$NON-NLS-1$

		if(withLatexParams) {
			if(positionHoriCentre)
				cache.append("\\end{center}").append(eol);//$NON-NLS-1$

			if(label.length()>0)
				cache.append("\\label{").append(label).append('}');//$NON-NLS-1$

			if(caption.length()>0)
				cache.append("\\caption{").append(caption).append('}');//$NON-NLS-1$

			if(positionVertToken!=VerticalPosition.NONE)
				cache.append("\\end{figure}");//$NON-NLS-1$
		}
	}


	/**
	 * Adds the PST colour code to the cache.
	 * @param pstView The shape which colour code will be generated.
	 * @param addedColours The PST colours already generated.
	 * @since 3.0
	 */
	private void generateColourCode(final PSTShapeView<?> pstView, final Map<String, String> addedColours) {
		if(pstView.coloursName!=null)
			for(String nameColour : pstView.coloursName)
				if(addedColours.get(nameColour)==null && DviPsColors.INSTANCE.getPredefinedColour(nameColour)==null) {
					addedColours.put(nameColour, nameColour);
					cache.append(DviPsColors.INSTANCE.getUsercolourCode(nameColour));
				}
	}



	/**
	 * Empties the cache.
	 * @since 3.0
	 */
	protected void emptyCache() {
		if(cache!=null)
			cache.delete(0, cache.length());
	}



	/**
	 * Updates the cache of every shapes and those of this generator.
	 * @since 3.0
	 */
	public void updateFull() {
		synchro.updateFull();
		update();
	}


	/**
	 * @return True: The latex parameters must be used by the generated code.
	 * @since 3.0
	 */
	public boolean isWithLatexParams() {
		return withLatexParams;
	}


	/**
	 * Defines if the latex parameters must be used by the generated code.
	 * @param withLatexParams True: The latex parameters must be used by the generated code.
	 * @since 3.0
	 */
	public void setWithLatexParams(final boolean withLatexParams) {
		this.withLatexParams = withLatexParams;
	}


	/**
	 * @return True: comments will be included.
	 * @since 3.0
	 */
	public boolean isWithComments() {
		return withComments;
	}



	/**
	 * Defines if the code must contains comments.
	 * @param withComments True: comments will be included.
	 * @since 3.0
	 */
	public void setWithComments(final boolean withComments) {
		this.withComments = withComments;
	}
}
