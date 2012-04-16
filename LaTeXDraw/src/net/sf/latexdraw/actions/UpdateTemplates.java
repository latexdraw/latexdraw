package net.sf.latexdraw.actions;

import net.sf.latexdraw.generators.svg.SVGDocumentGenerator;

import org.malai.action.Action;
import org.malai.widget.MMenu;

/**
 * This action updates the templates.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-04-16<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class UpdateTemplates extends Action {
	/** The menu that contains the template menu items. */
	protected MMenu templatesMenu;

	/** Defines if the thumbnails must be updated. */
	protected boolean updateThumbnails;


	public UpdateTemplates() {
		super();
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	@Override
	protected void doActionBody() {
		SVGDocumentGenerator.INSTANCE.updateTemplates(templatesMenu, updateThumbnails);
	}


	@Override
	public boolean canDo() {
		return templatesMenu!=null;
	}

	/**
	 * @param templatesMenu The menu that contains the template menu items.
	 * @since 3.0
	 */
	public final void setTemplatesMenu(final MMenu templatesMenu) {
		this.templatesMenu = templatesMenu;
	}


	/**
	 * @param updateThumbnails Defines if the thumbnails must be updated.
	 * @since 3.0
	 */
	public final void setUpdateThumbnails(final boolean updateThumbnails) {
		this.updateThumbnails = updateThumbnails;
	}
}
