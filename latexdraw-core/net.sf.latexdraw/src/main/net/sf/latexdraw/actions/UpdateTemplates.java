/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2015 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.actions;

import java.util.Optional;
import javafx.scene.control.MenuButton;
import org.malai.action.ActionImpl;

/**
 * This action updates the templates.
 */
public class UpdateTemplates extends ActionImpl implements TemplateAction {
	/** Defines if the thumbnails must be updated. */
	boolean updateThumbnails;

	Optional<MenuButton> templatesMenu;


	@Override
	public void doActionBody() {
//		SVGDocumentGenerator.INSTANCE.updateTemplates(_templatesMenu.get, _updateThumbnails)
	}

	/**
	 * @param update Defines if the thumbnails must be updated.
	 */
	public void updateThumbnails(final boolean update) {
		updateThumbnails = update;
	}

	public boolean getUpdateThumbnails() {
		return updateThumbnails;
	}

	@Override
	public void templatesMenu(final MenuButton menu) {
		templatesMenu = Optional.ofNullable(menu);
	}

	@Override
	public Optional<MenuButton> getTemplatesMenu() {
		return templatesMenu;
	}
}
