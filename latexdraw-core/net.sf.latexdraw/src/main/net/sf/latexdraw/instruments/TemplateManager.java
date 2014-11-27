package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument manages the templates.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
public class TemplateManager extends JfxInstrument {
//	/** The menu item that permits to update the templates. */
	@FXML protected Button updateTemplates;


//	override def setActivated(activated : Boolean) {
//		super.setActivated(activated)
//		templateMenu.setEnabled(isActivated)
//	}


	@Override 
	protected void initialiseInteractors() {
//		try	{
//			addInteractor(new MenuItem2UpdateTemplates(this))
//			addInteractor(new MenuItem2LoadTemplate(this))
//			addInteractor(new MenuItem2ExportTemplate(this))
//		}
//		catch{case ex: Throwable => BadaboomCollector.INSTANCE.add(ex)}
	}


//	override protected def initialiseWidgets() {
//		val action = new UpdateTemplates()
//		action.templatesMenu = _templateMenu
//		action.updateThumbnails = false
//		action.doIt
//		action.flush
//	}
}


//private sealed class MenuItem2ExportTemplate(ins : TemplateManager) extends
//				InteractorImpl[ExportTemplate, MenuItemPressed, TemplateManager](ins, false, classOf[ExportTemplate], classOf[MenuItemPressed]) {
//	override def initAction() {
//		action.setUi(instrument.ui)
//		action.templatesMenu = instrument.templateMenu
//	}
//
//	override def isConditionRespected = interaction.getMenuItem==instrument.exportTemplateMenu
//}
//
//
//private sealed class MenuItem2LoadTemplate(ins : TemplateManager) extends
//				InteractorImpl[LoadTemplate, MenuItemPressed, TemplateManager](ins, false, classOf[LoadTemplate], classOf[MenuItemPressed]) {
//	override def initAction() {
//		action.setFile(new File(interaction.getMenuItem.getName))
//		action.setOpenSaveManager(SVGDocumentGenerator.INSTANCE)
//		action.setUi(instrument.ui)
//	}
//
//	override def isConditionRespected = interaction.getMenuItem!=instrument.updateTemplatesMenu && instrument.templateMenu.contains(interaction.getMenuItem)
//}
//
//
///** Maps a menu item interaction to an action that updates the templates. */
//private sealed class MenuItem2UpdateTemplates(ins : TemplateManager) extends
//				InteractorImpl[UpdateTemplates, MenuItemPressed, TemplateManager](ins, false, classOf[UpdateTemplates], classOf[MenuItemPressed]) {
//	override def initAction() {
//		action.updateThumbnails = true
//		action.templatesMenu = instrument.templateMenu
//	}
//
//	override def isConditionRespected = interaction.getMenuItem==instrument.updateTemplatesMenu
//}
