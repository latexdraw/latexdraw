package net.sf.latexdraw.actions

import org.malai.action.Action

import net.sf.latexdraw.generators.svg.SVGDocumentGenerator

/**
 * This action updates the templates.<br>
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
 * 2012-04-19<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class UpdateTemplates extends Action with TemplateAction{
	/** Defines if the thumbnails must be updated. */
	protected var _updateThumbnails : Boolean = false


	override def isRegisterable() = false

	override def doActionBody() {
		SVGDocumentGenerator.INSTANCE.updateTemplates(_templatesMenu.get, _updateThumbnails)
	}

	override def canDo() = _templatesMenu.isDefined

	/**
	 * @param updateThumbnails Defines if the thumbnails must be updated.
	 */
	def updateThumbnails_=(update : Boolean) { _updateThumbnails = update }

	def updateThumbnails = _updateThumbnails
}
