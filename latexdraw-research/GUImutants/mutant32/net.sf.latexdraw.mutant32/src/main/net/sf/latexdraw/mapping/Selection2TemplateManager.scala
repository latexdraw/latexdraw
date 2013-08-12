package net.sf.latexdraw.mapping

import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.instruments.TemplateManager

/**
 * This mapping maps the selected shapes of the drawing to the instrument that can export the selection as templates.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 2012-04-19<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
class Selection2TemplateManager(selection : java.util.List[IShape], val template : TemplateManager) extends SelectionMapping(selection) {

	override def getTarget() : TemplateManager = template


	override def onObjectAdded(list : Object, obj : Object, index : Int) {
		template.exportTemplateMenu.setEnabled(true)
	}


	override def onObjectRemoved(list : Object, obj : Object, index : Int) {
		if(list.isInstanceOf[java.util.List[_]])
			template.exportTemplateMenu.setEnabled(!list.asInstanceOf[java.util.List[_]].isEmpty)
	}


	override def onListCleaned(list : Object) {
		template.exportTemplateMenu.setEnabled(false)
	}
}
