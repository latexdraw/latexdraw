package net.sf.latexdraw.actions

import org.malai.swing.widget.MMenu

/**
 * This trait encapsulates the template menu.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-04-20<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
trait TemplateAction {
	/** The menu that contains the template menu items. */
	protected var _templatesMenu : Option[MMenu] = None

	/**
	 * @param menu The menu that contains the template menu items.
	 */
	def templatesMenu_=(menu : MMenu) {
		_templatesMenu = Some(menu)
	}

	def templatesMenu = _templatesMenu
}