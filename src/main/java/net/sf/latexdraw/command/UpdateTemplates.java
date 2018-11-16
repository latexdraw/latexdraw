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
package net.sf.latexdraw.command;

import javafx.scene.layout.Pane;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.jetbrains.annotations.NotNull;
import org.malai.command.CommandImpl;

/**
 * This command updates the templates.
 * @author Arnaud Blouin
 */
public class UpdateTemplates extends CommandImpl {
	/** Defines if the thumbnails must be updated. */
	private final boolean updateThumbnails;
	private final @NotNull Pane templatesPane;
	private final @NotNull SVGDocumentGenerator svgGen;

	public UpdateTemplates(final @NotNull Pane templatesPane, final @NotNull SVGDocumentGenerator svgGen, final boolean updateThumbnails) {
		super();
		this.templatesPane = templatesPane;
		this.updateThumbnails = updateThumbnails;
		this.svgGen = svgGen;
	}

	@Override
	public void doCmdBody() {
		svgGen.updateTemplates(templatesPane, updateThumbnails);
	}
}
