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

import javafx.scene.control.MenuButton;
import org.malai.action.ActionImpl;

import java.util.Optional;

/**
 * This action exports a set of shapes as a template.
 */
public class ExportTemplate extends ActionImpl implements TemplateAction { //extends IOAction[Void, JLabel]
	Optional<MenuButton> templatesMenu;

	@Override
	public boolean canDo() {
		return false; //ui!=null && _templatesMenu.isDefined
	}

	@Override
	protected void doActionBody() {
		//		var path = ""
		//		var ok = true
		//		var cancelled = false
		//
		//		do {
		//			val templateName = JOptionPane.showInputDialog(this, LangTool.INSTANCE.getStringOthers("DrawContainer.nameTemplate"))  //$NON-NLS-1$
		//			path = LPath.PATH_TEMPLATES_DIR_USER + File.separator + templateName + SVGFilter.SVG_EXTENSION
		//
		//			if(templateName==null)
		//				cancelled = true
		//			else
		//				if(new File(path).exists)
		//					ok = JOptionPane.showConfirmDialog(ui, LangTool.INSTANCE.getStringOthers("DrawContainer.overwriteTemplate"), //$NON-NLS-1$
		//							LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.42"), JOptionPane.YES_NO_OPTION) match { //$NON-NLS-1$
		//						case JOptionPane.YES_OPTION => true
		//						case _ => false
		//					}
		//				else ok = templateName.length>0
		//		}while(!ok && !cancelled)
		//
		//		if(!cancelled)
		//			SVGDocumentGenerator.INSTANCE.saveTemplate(path, ui, progressBar, statusWidget, _templatesMenu.get)
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
