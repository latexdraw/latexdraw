package net.sf.latexdraw.ui;

import java.awt.BorderLayout;

import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.views.pst.PSTCodeGenerator;
import net.sf.latexdraw.glib.views.synchroniser.ViewsSynchroniserHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.eseo.malai.action.Action;
import fr.eseo.malai.action.ActionHandler;
import fr.eseo.malai.action.ActionsRegistry;
import fr.eseo.malai.presentation.ConcretePresentation;
import fr.eseo.malai.undo.UndoCollector;
import fr.eseo.malai.undo.UndoHandler;
import fr.eseo.malai.undo.Undoable;
import fr.eseo.malai.widget.MEditorPane;
import fr.eseo.malai.widget.MPanel;

/**
 * Defines the panel which contains the code generated from the drawing.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public class LCodePanel extends MPanel implements ConcretePresentation, ActionHandler, UndoHandler {
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

		ActionsRegistry.INSTANCE.addHandler(this);
		UndoCollector.INSTANCE.addHandler(this);
		pstGenerator = new PSTCodeGenerator(drawing, viewsHandler, true, true);
	}



	@Override
	public void setVisible(final boolean show) {
		super.setVisible(show);
		//TODO (des-)activate its instruments.
	}



	@Override
	public void onAction(final Action action, final ActionEvent evt) {
		if(action!=null) {
			//TODO only updates the modified shape(s) for some actions.
			pstGenerator.updateFull();
			editorPane.setText(pstGenerator.getCache().toString());
		}
	}



	@Override
	public void onActionExecuted(final Action action) {
		// Nothing to do.
	}



	@Override
	public void onUndoableAdded(final Undoable undoable) {
		// Nothing to do.
	}



	@Override
	public void onUndoableUndo(final Undoable undoable) {
		onUndoableRedo(undoable);
	}



	@Override
	public void onUndoableRedo(final Undoable undoable) {
		if(undoable instanceof Action)
			//TODO only updates the modified shape(s) for some actions.
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
		pstGenerator.updateFull();
		editorPane.setText(pstGenerator.getCache().toString());
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
		// TODO Auto-generated method stub
	}
}
