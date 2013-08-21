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
import net.sf.latexdraw.actions.shape.AlignShapes
import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.actions.shape.AlignShapes
import net.sf.latexdraw.actions.shape.AlignmentType
import net.sf.latexdraw.actions.shape.DistributeShapes
import net.sf.latexdraw.actions.shape.DistributionType

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
class ShapeTransformer(composer:UIComposer[_], hand:Hand, pencil:Pencil, val border:Border) extends ShapePropertyCustomiser(composer, hand, pencil) {
	/** The widget to mirror horizontally. */
	lazy val _mirrorH = new MButton(LResources.MIRROR_H_ICON)

	/** The widget to mirror vertically. */
	lazy val _mirrorV = new MButton(LResources.MIRROR_V_ICON)

	/** The widget to bottom align the shapes. */
	lazy val _alignBot = new MButton(LResources.ALIGN_BOTTOM_ICON)

	/** The widget to left align the shapes. */
	lazy val _alignLeft = new MButton(LResources.ALIGN_LEFT_ICON)

	/** The widget to right align the shapes. */
	lazy val _alignRight = new MButton(LResources.ALIGN_RIGHT_ICON)

	/** The widget to top align the shapes. */
	lazy val _alignTop = new MButton(LResources.ALIGN_TOP_ICON)

	/** The widget to middle horizontal align the shapes. */
	lazy val _alignMidHoriz = new MButton(LResources.ALIGN_MID_HORIZ_ICON)

	/** The widget to middle vertical align the shapes. */
	lazy val _alignMidVert = new MButton(LResources.ALIGN_MID_VERT_ICON)

	/** The widget to bottom-vertically distribute the shapes. */
	lazy val _distribVertBot = new MButton(LResources.DIST_VERT_BOTTOM_ICON)

	/** The widget to equal-vertically distribute the shapes. */
	lazy val _distribVertEq = new MButton(LResources.DIST_VERT_EQUAL_ICON)

	/** The widget to middle-vertically distribute the shapes. */
	lazy val _distribVertMid = new MButton(LResources.DIST_VERT_MID_ICON)

	/** The widget to top-vertically distribute the shapes. */
	lazy val _distribVertTop = new MButton(LResources.DIST_VERT_TOP_ICON)

	/** The widget to equal-horizontally distribute the shapes. */
	lazy val _distribHorizEq = new MButton(LResources.DIST_HORIZ_EQUAL_ICON)

	/** The widget to left-horizontally distribute the shapes. */
	lazy val _distribHorizLeft = new MButton(LResources.DIST_HORIZ_LEFT_ICON)

	/** The widget to middle-horizontally distribute the shapes. */
	lazy val _distribHorizMid = new MButton(LResources.DIST_HORIZ_MID_ICON)

	/** The widget to right-horizontally distribute the shapes. */
	lazy val _distribHorizRight = new MButton(LResources.DIST_HORIZ_RIGHT_ICON)


	protected override def initialiseWidgets() {
		_mirrorH.setMargin(LResources.INSET_BUTTON)
		_mirrorV.setMargin(LResources.INSET_BUTTON)
		_alignBot.setMargin(LResources.INSET_BUTTON)
		_alignLeft.setMargin(LResources.INSET_BUTTON)
		_alignRight.setMargin(LResources.INSET_BUTTON)
		_alignTop.setMargin(LResources.INSET_BUTTON)
		_alignMidHoriz.setMargin(LResources.INSET_BUTTON)
		_alignMidVert.setMargin(LResources.INSET_BUTTON)

		_mirrorH.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.7"))
		_mirrorV.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.8"))
		_alignBot.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.5"))
		_alignTop.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.4"))
		_alignLeft.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.2"))
		_alignRight.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.3"))
		_alignMidHoriz.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.6"))
		_alignMidVert.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.7"))

		_distribVertBot.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.9"))
		_distribVertEq.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.10"))
		_distribVertMid.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.11"))
		_distribVertTop.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.12"))
		_distribHorizLeft.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.13"))
		_distribHorizEq.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.14"))
		_distribHorizMid.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.15"))
		_distribHorizRight.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.16"))
	}


	protected override def setWidgetsVisible(visible:Boolean) {
		composer.setWidgetVisible(_mirrorH, visible)
		composer.setWidgetVisible(_mirrorV, visible)

		composer.setWidgetVisible(_alignBot, visible)
		composer.setWidgetVisible(_alignTop, visible)
		composer.setWidgetVisible(_alignLeft, visible)
		composer.setWidgetVisible(_alignRight, visible)
		composer.setWidgetVisible(_alignMidHoriz, visible)
		composer.setWidgetVisible(_alignMidVert, visible)

		composer.setWidgetVisible(_distribVertBot, visible)
		composer.setWidgetVisible(_distribVertEq, visible)
		composer.setWidgetVisible(_distribVertMid, visible)
		composer.setWidgetVisible(_distribVertTop, visible)
		composer.setWidgetVisible(_distribHorizLeft, visible)
		composer.setWidgetVisible(_distribHorizEq, visible)
		composer.setWidgetVisible(_distribHorizMid, visible)
		composer.setWidgetVisible(_distribHorizRight, visible)
	}


	protected override def update(shape:IShape) {
		// Nothing to do
	}


	protected override def initialiseLinks() {
		try{
			addLink(new Button2Mirror(this))
			addLink(new Button2Align(this))
			addLink(new Button2Distribute(this))
		}catch{case ex => BadaboomCollector.INSTANCE.add(ex)}
	}

	/** The widget to mirror horizontally. */
	def mirrorH = _mirrorH

	/** The widget to mirror vertically. */
	def mirrorV = _mirrorV

	/** The widget to left align the shapes. */
	def alignLeft = _alignLeft

	/** The widget to right align the shapes. */
	def alignRight = _alignRight

	/** The widget to middle horizontal align the shapes. */
	def alignMidHoriz = _alignMidHoriz

	/** The widget to middle vertical align the shapes. */
	def alignMidVert = _alignMidVert

	/** The widget to top align the shapes. */
	def alignTop = _alignTop

	/** The widget to bottom align the shapes. */
	def alignBot = _alignBot

	/** The widget to equal-vertically distribute the shapes. */
	def distribVertEq = _distribVertEq

	/** The widget to bottom-vertically distribute the shapes. */
	def distribVertBot = _distribVertBot

	/** The widget to middle-vertically distribute the shapes. */
	def distribVertMid = _distribVertMid

	/** The widget to top-vertically distribute the shapes. */
	def distribVertTop = _distribVertTop

	/** The widget to left-horizontally distribute the shapes. */
	def distribHorizLeft = _distribHorizLeft

	/** The widget to equal-horizontally distribute the shapes. */
	def distribHorizEq = _distribHorizEq

	/** The widget to middle-horizontally distribute the shapes. */
	def distribHorizMid = _distribHorizMid

	/** The widget to right-horizontally distribute the shapes. */
	def distribHorizRight = _distribHorizRight
}

/**
 * Maps a button interaction with an action that aligns the selected shapes.
 */
class Button2Align(ins:ShapeTransformer) extends Link[AlignShapes, ButtonPressed, ShapeTransformer](ins, false, classOf[AlignShapes], classOf[ButtonPressed]) {
	override def initAction() {
		val but = interaction.getButton

		action.setShape(instrument.pencil.canvas.getDrawing.getSelection.duplicate.asInstanceOf[IGroup])
		//if(but==instrument._alignBot) action.setAlignment(AlignmentType.bottom)
		//Mutant43
		if(but!=instrument._alignBot) action.setAlignment(AlignmentType.bottom)
		else if(but==instrument._alignLeft) action.setAlignment(AlignmentType.left)
		else if(but==instrument._alignMidHoriz) action.setAlignment(AlignmentType.midHoriz)
		else if(but==instrument._alignMidVert) action.setAlignment(AlignmentType.midVert)
		else if(but==instrument._alignRight) action.setAlignment(AlignmentType.right)
		else if(but==instrument._alignTop) action.setAlignment(AlignmentType.top)
		action.setBorder(instrument.border.border)
	}

	override def isConditionRespected() = {
		val but = interaction.getButton
		but==instrument._alignBot || but==instrument._alignLeft || but==instrument._alignMidHoriz || but==instrument._alignMidVert ||
		but==instrument._alignRight || but==instrument._alignTop
	}
}

/**
 * Maps a button interaction with an action that distributes the selected shapes.
 */
class Button2Distribute(ins:ShapeTransformer) extends Link[DistributeShapes, ButtonPressed, ShapeTransformer](ins, false, classOf[DistributeShapes], classOf[ButtonPressed]) {
	override def initAction() {
		val but = interaction.getButton

		action.setShape(instrument.pencil.canvas.getDrawing.getSelection.duplicate.asInstanceOf[IGroup])
		if(but==instrument._distribHorizEq) action.setDistribution(DistributionType.horizEq)
		else if(but==instrument._distribHorizLeft) action.setDistribution(DistributionType.horizLeft)
		else if(but==instrument._distribHorizMid) action.setDistribution(DistributionType.horizMid)
		else if(but==instrument._distribHorizRight) action.setDistribution(DistributionType.horizRight)
		else if(but==instrument._distribVertBot) action.setDistribution(DistributionType.vertBot)
		else if(but==instrument._distribVertEq) action.setDistribution(DistributionType.vertEq)
		else if(but==instrument._distribVertMid) action.setDistribution(DistributionType.vertMid)
		else if(but==instrument._distribVertTop) action.setDistribution(DistributionType.vertTop)
		action.setBorder(instrument.border.border)
	}

	override def isConditionRespected() = {
		val but = interaction.getButton
		but==instrument._distribHorizEq || but==instrument._distribHorizLeft || but==instrument._distribHorizMid || but==instrument._distribHorizRight ||
		but==instrument._distribVertBot || but==instrument._distribVertEq || but==instrument._distribVertMid || but==instrument._distribVertTop
	}
}

/**
 * Maps a button interaction with an action that mirrors the selected shapes.
 */
class Button2Mirror(ins:ShapeTransformer) extends Link[MirrorShapes, ButtonPressed, ShapeTransformer](ins, false, classOf[MirrorShapes], classOf[ButtonPressed]) {
	override def initAction() {
		action.setShape(instrument.pencil.canvas.getDrawing.getSelection.duplicate)
		action.setHorizontally(interaction.getButton==instrument._mirrorH)
	}

	override def isConditionRespected() = interaction.getButton==instrument._mirrorH || interaction.getButton==instrument._mirrorV
}

