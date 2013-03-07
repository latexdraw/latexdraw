package net.sf.latexdraw.instruments

import org.malai.swing.instrument.WidgetInstrument
import org.malai.swing.ui.UIComposer
import net.sf.latexdraw.glib.models.interfaces.IShape
import org.malai.swing.widget.MButton
import net.sf.latexdraw.util.LResources
import net.sf.latexdraw.lang.LangTool
import org.malai.instrument.Link
import org.malai.swing.interaction.library.ButtonPressed
import net.sf.latexdraw.actions.shape.MirrorShapes
import net.sf.latexdraw.badaboom.BadaboomCollector

/**
 * This instrument transforms (mirror, etc.) the selected shapes.<br>
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
 * 2013-03-07<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class ShapeTransformer(composer:UIComposer[_], hand:Hand, pencil:Pencil) extends ShapePropertyCustomiser(composer, hand, pencil) {
	/** The widget to mirror horizontally. */
	lazy val _mirrorH = new MButton(LResources.MIRROR_H_ICON)

	/** The widget to mirror vertically. */
	lazy val _mirrorV = new MButton(LResources.MIRROR_V_ICON)


	protected override def initialiseWidgets() {
		_mirrorH.setMargin(LResources.INSET_BUTTON)
		_mirrorV.setMargin(LResources.INSET_BUTTON)
		_mirrorH.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.7"))
		_mirrorV.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.8"))
	}


	protected override def setWidgetsVisible(visible:Boolean) {
		composer.setWidgetVisible(_mirrorH, visible)
		composer.setWidgetVisible(_mirrorV, visible)
	}


	protected override def update(shape:IShape) {
		// Nothing to do
	}


	protected override def initialiseLinks() {
		try{
			addLink(new Button2Mirror(this))
		}catch{case ex => BadaboomCollector.INSTANCE.add(ex)}
	}

	/** The widget to mirror horizontally. */
	def mirrorH = _mirrorH

	/** The widget to mirror vertically. */
	def mirrorV = _mirrorV
}

/**
 * Maps a button interaction with an action that mirrors the selected shapes.
 */
class Button2Mirror(ins:ShapeTransformer) extends Link[MirrorShapes, ButtonPressed, ShapeTransformer](ins, false, classOf[MirrorShapes], classOf[ButtonPressed]) {
	override def initAction() {
		action.setShape(instrument.pencil.canvas.getDrawing().getSelection().duplicate)
		action.setHorizontally(interaction.getButton()==instrument._mirrorH)
	}

	override def isConditionRespected() = interaction.getButton==instrument._mirrorH || interaction.getButton==instrument._mirrorV
}

