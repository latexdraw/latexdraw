package net.sf.latexdraw.ui;

import java.awt.BorderLayout;

import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.views.pst.PSTCodeGenerator;
import net.sf.latexdraw.glib.views.synchroniser.ViewsSynchroniserHandler;

import org.malai.presentation.ConcretePresentation;
import org.malai.swing.widget.MEditorPane;
import org.malai.swing.widget.MPanel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Defines the panel which contains the code generated from the drawing.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 05/20/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class LCodePanel extends MPanel implements ConcretePresentation {
	private static final long serialVersionUID = 1L;

	/** The editor that contains the code. */
	protected MEditorPane editorPane;

	/** The PSTricks generator. */
	protected PSTCodeGenerator pstGenerator;


	/**
	 * Creates the code panel.
	 * @param drawing The drawing to transform in code.
	 * @param viewsHandler the handler that provides information to a views synchroniser
	 * @throws IllegalArgumentException If the given drawing is null.
	 * @since 3.0
	 */
	public LCodePanel(final IDrawing drawing, final ViewsSynchroniserHandler viewsHandler) {
		super(false, true);

		if(drawing==null)
			throw new IllegalArgumentException();

		editorPane = new MEditorPane(true, false);
		editorPane.setEditable(false);
		editorPane.setDragEnabled(true);

		setLayout(new BorderLayout());
		add(editorPane.getScrollpane(), BorderLayout.CENTER);

		pstGenerator = new PSTCodeGenerator(drawing, viewsHandler, true, true);
	}



	@Override
	public void setVisible(final boolean show) {
		super.setVisible(show);

		if(show)
			update();
	}


	/**
	 * @return the PST code generator of the code panel.
	 * @since 3.0
	 */
	public PSTCodeGenerator getPstGenerator() {
		return pstGenerator;
	}



	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		// TODO Auto-generated method stub
	}



	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		// TODO Auto-generated method stub
	}


	@Override
	public void update() {
		if(isVisible()) {
			pstGenerator.updateFull();
			editorPane.setText(pstGenerator.getCache().toString());
		}
	}


	@Override
	public void setModified(final boolean modified) {
		pstGenerator.setModified(modified);
	}


	@Override
	public boolean isModified() {
		return pstGenerator.isModified();
	}


	@Override
	public void reinit() {
		update();
	}
}
