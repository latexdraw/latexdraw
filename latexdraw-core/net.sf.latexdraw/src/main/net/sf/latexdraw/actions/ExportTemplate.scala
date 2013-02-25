package net.sf.latexdraw.actions

import java.io.File

import org.malai.swing.action.library.IOAction

import javax.swing.JLabel
import javax.swing.JOptionPane
import net.sf.latexdraw.filters.SVGFilter
import net.sf.latexdraw.generators.svg.SVGDocumentGenerator
import net.sf.latexdraw.lang.LangTool
import net.sf.latexdraw.ui.LFrame
import net.sf.latexdraw.util.LPath

/**
 * This action exports a set of shapes as a template.
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 2012-04-20<br>
 * @author Arnaud Blouin
 * @since 3.0
 */
class ExportTemplate extends IOAction[LFrame, JLabel] with TemplateAction {

	override def canDo() = ui!=null && _templatesMenu.isDefined


	protected def doActionBody() {
		var path = ""
		var ok = true
		var cancelled = false

		do {
			val templateName = JOptionPane.showInputDialog(this, LangTool.INSTANCE.getStringOthers("DrawContainer.nameTemplate"))  //$NON-NLS-1$
			path = LPath.PATH_TEMPLATES_DIR_USER + File.separator + templateName + SVGFilter.SVG_EXTENSION

			if(templateName==null)
				cancelled = true
			else
				if(new File(path).exists)
					ok = JOptionPane.showConfirmDialog(ui, LangTool.INSTANCE.getStringOthers("DrawContainer.overwriteTemplate"), //$NON-NLS-1$
							LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.42"), JOptionPane.YES_NO_OPTION) match { //$NON-NLS-1$
						case JOptionPane.YES_OPTION => true
						case _ => false
					}
				else ok = templateName.length>0
		}while(!ok && !cancelled)

		if(!cancelled)
			SVGDocumentGenerator.INSTANCE.saveTemplate(path, ui, progressBar, statusWidget, _templatesMenu.get)
	}
}
