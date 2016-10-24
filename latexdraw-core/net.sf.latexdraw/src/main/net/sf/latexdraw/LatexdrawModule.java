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
package net.sf.latexdraw;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import net.sf.latexdraw.glib.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.glib.views.ViewsSynchroniserHandler;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.glib.views.pst.PSTCodeGenerator;
import net.sf.latexdraw.instruments.AboutController;
import net.sf.latexdraw.instruments.Border;
import net.sf.latexdraw.instruments.CodeInserter;
import net.sf.latexdraw.instruments.CodePanelController;
import net.sf.latexdraw.instruments.CopierCutterPaster;
import net.sf.latexdraw.instruments.DrawingPropertiesCustomiser;
import net.sf.latexdraw.instruments.EditingSelector;
import net.sf.latexdraw.instruments.ExceptionsManager;
import net.sf.latexdraw.instruments.Exporter;
import net.sf.latexdraw.instruments.FileLoaderSaver;
import net.sf.latexdraw.instruments.FrameController;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Helper;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.PreferencesSetter;
import net.sf.latexdraw.instruments.ShapeArcCustomiser;
import net.sf.latexdraw.instruments.ShapeArrowCustomiser;
import net.sf.latexdraw.instruments.ShapeAxesCustomiser;
import net.sf.latexdraw.instruments.ShapeBorderCustomiser;
import net.sf.latexdraw.instruments.ShapeCoordDimCustomiser;
import net.sf.latexdraw.instruments.ShapeDeleter;
import net.sf.latexdraw.instruments.ShapeDotCustomiser;
import net.sf.latexdraw.instruments.ShapeDoubleBorderCustomiser;
import net.sf.latexdraw.instruments.ShapeFillingCustomiser;
import net.sf.latexdraw.instruments.ShapeFreeHandCustomiser;
import net.sf.latexdraw.instruments.ShapeGridCustomiser;
import net.sf.latexdraw.instruments.ShapeGrouper;
import net.sf.latexdraw.instruments.ShapePlotCustomiser;
import net.sf.latexdraw.instruments.ShapePositioner;
import net.sf.latexdraw.instruments.ShapeRotationCustomiser;
import net.sf.latexdraw.instruments.ShapeShadowCustomiser;
import net.sf.latexdraw.instruments.ShapeStdGridCustomiser;
import net.sf.latexdraw.instruments.ShapeTextCustomiser;
import net.sf.latexdraw.instruments.ShapeTransformer;
import net.sf.latexdraw.instruments.ShortcutsController;
import net.sf.latexdraw.instruments.TabSelector;
import net.sf.latexdraw.instruments.TemplateManager;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.instruments.UndoRedoManager;
import net.sf.latexdraw.ui.XScaleRuler;
import net.sf.latexdraw.ui.YScaleRuler;
import net.sf.latexdraw.view.jfx.Canvas;

class LatexdrawModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Canvas.class).asEagerSingleton();
		bind(PSTCodeGenerator.class).asEagerSingleton();
		bind(AboutController.class).asEagerSingleton();
		bind(Border.class).asEagerSingleton();
		bind(CodeInserter.class).asEagerSingleton();
		bind(CopierCutterPaster.class).asEagerSingleton();
		bind(CodePanelController.class).asEagerSingleton();
		bind(DrawingPropertiesCustomiser.class).asEagerSingleton();
		bind(EditingSelector.class).asEagerSingleton();
		bind(ExceptionsManager.class).asEagerSingleton();
		bind(Exporter.class).asEagerSingleton();
		bind(FileLoaderSaver.class).asEagerSingleton();
		bind(Hand.class).asEagerSingleton();
		bind(Helper.class).asEagerSingleton();
		bind(MetaShapeCustomiser.class).asEagerSingleton();
		bind(Pencil.class).asEagerSingleton();
		bind(PreferencesSetter.class).asEagerSingleton();
		bind(ShapeArcCustomiser.class).asEagerSingleton();
		bind(ShapeArrowCustomiser.class).asEagerSingleton();
		bind(ShapeAxesCustomiser.class).asEagerSingleton();
		bind(ShapeBorderCustomiser.class).asEagerSingleton();
		bind(ShapeCoordDimCustomiser.class).asEagerSingleton();
		bind(ShapeDeleter.class).asEagerSingleton();
		bind(ShapeDotCustomiser.class).asEagerSingleton();
		bind(ShapeDoubleBorderCustomiser.class).asEagerSingleton();
		bind(ShapeFillingCustomiser.class).asEagerSingleton();
		bind(ShapeFreeHandCustomiser.class).asEagerSingleton();
		bind(ShapeGridCustomiser.class).asEagerSingleton();
		bind(ShapeGrouper.class).asEagerSingleton();
		bind(ShapePlotCustomiser.class).asEagerSingleton();
		bind(ShapePositioner.class).asEagerSingleton();
		bind(ShapeRotationCustomiser.class).asEagerSingleton();
		bind(ShapeShadowCustomiser.class).asEagerSingleton();
		bind(ShapeStdGridCustomiser.class).asEagerSingleton();
		bind(ShapeTextCustomiser.class).asEagerSingleton();
		bind(ShapeTransformer.class).asEagerSingleton();
		bind(ShortcutsController.class).asEagerSingleton();
		bind(TabSelector.class).asEagerSingleton();
		bind(TemplateManager.class).asEagerSingleton();
		bind(TextSetter.class).asEagerSingleton();
		bind(XScaleRuler.class).asEagerSingleton();
		bind(YScaleRuler.class).asEagerSingleton();
		bind(FrameController.class).asEagerSingleton();
		bind(UndoRedoManager.class).asEagerSingleton();
	}

	@Provides
	IDrawing provideDrawing(final Canvas canvas) {
		return canvas.getDrawing();
	}

	@Provides
	ViewsSynchroniserHandler provideViewsSynchroniserHandler(final Canvas canvas) {
		return canvas;
	}

	@Provides
	LaTeXGenerator provideLaTeXGenerator(final PSTCodeGenerator gen) {
		return gen;
	}
}
