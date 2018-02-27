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
package net.sf.latexdraw.actions;

import javafx.scene.layout.Pane;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.malai.action.ActionImpl;

/**
 * This action updates the templates.
 * @author Arnaud Blouin
 */
public class UpdateTemplates extends ActionImpl implements TemplateAction {
	/** Defines if the thumbnails must be updated. */
	private boolean updateThumbnails;
	private Pane templatesPane;

	public UpdateTemplates() {
		super();
		updateThumbnails = false;
	}

	@Override
	public void doActionBody() {
		SVGDocumentGenerator.INSTANCE.updateTemplates(templatesPane, updateThumbnails);
	}

	/**
	 * @param update Defines if the thumbnails must be updated.
	 */
	public void updateThumbnails(final boolean update) {
		updateThumbnails = update;
	}

	@Override
	public void setTemplatesPane(final Pane templatePanel) {
		templatesPane = templatePanel;
	}

	@Override
	public Pane getTemplatesPane() {
		return templatesPane;
	}
}
