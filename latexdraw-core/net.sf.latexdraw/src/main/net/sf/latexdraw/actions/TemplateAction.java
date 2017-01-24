/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
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
import org.malai.action.Action;

/**
 * This trait encapsulates the template menu.
 */
public interface TemplateAction extends Action {
	/**
	 * @param menu The menu that contains the template menu items.
	 */
	void templatesMenu(final MenuButton menu);

	Optional<MenuButton> getTemplatesMenu();

	@Override
	default boolean isRegisterable() {
		return false;
	}

	@Override
	default boolean canDo() {
		return getTemplatesMenu().isPresent();
	}
}