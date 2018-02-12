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
package net.sf.latexdraw;

import java.lang.reflect.InvocationTargetException;
import net.sf.latexdraw.instruments.AboutController;
import net.sf.latexdraw.instruments.Border;
import net.sf.latexdraw.instruments.CanvasController;
import net.sf.latexdraw.instruments.CodeInserter;
import net.sf.latexdraw.instruments.CodePanelController;
import net.sf.latexdraw.instruments.CopierCutterPaster;
import net.sf.latexdraw.instruments.DrawingPropertiesCustomiser;
import net.sf.latexdraw.instruments.EditingSelector;
import net.sf.latexdraw.instruments.ExceptionsManager;
import net.sf.latexdraw.instruments.Exporter;
import net.sf.latexdraw.instruments.FacadeCanvasController;
import net.sf.latexdraw.instruments.FileLoaderSaver;
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
import net.sf.latexdraw.instruments.StatusBarController;
import net.sf.latexdraw.instruments.TabSelector;
import net.sf.latexdraw.instruments.TemplateManager;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.instruments.UndoRedoManager;
import net.sf.latexdraw.instruments.Zoomer;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;

/**
 * @author Arnaud Blouin
 */
public class LatexdrawInjector extends Injector {
	@Override
	protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		bindAsEagerSingleton(ExceptionsManager.class);
		bindAsEagerSingleton(ShortcutsController.class);
		bindAsEagerSingleton(StatusBarController.class);
		bindAsEagerSingleton(AboutController.class);
		bindAsEagerSingleton(Canvas.class);
		bindAsEagerSingleton(FacadeCanvasController.class);
		bindAsEagerSingleton(CanvasController.class);
		bindWithCommand(IDrawing.class, Canvas.class, canvas -> canvas.getDrawing());
		bindWithCommand(MagneticGrid.class, Canvas.class, canvas -> canvas.getMagneticGrid());
		bindWithCommand(ViewsSynchroniserHandler.class, Canvas.class, canvas -> canvas);
		bindAsEagerSingleton(Zoomer.class);
		bindAsEagerSingleton(UndoRedoManager.class);
		bindAsEagerSingleton(PSTCodeGenerator.class);
		bindWithCommand(LaTeXGenerator.class, PSTCodeGenerator.class, gen -> gen);
		bindAsEagerSingleton(CodePanelController.class);
		bindAsEagerSingleton(DrawingPropertiesCustomiser.class);
		bindAsEagerSingleton(TemplateManager.class);
		bindAsEagerSingleton(CodeInserter.class);
		bindAsEagerSingleton(CopierCutterPaster.class);
		bindAsEagerSingleton(MetaShapeCustomiser.class);
		bindAsEagerSingleton(Border.class);
		bindAsEagerSingleton(PreferencesSetter.class);
		bindAsEagerSingleton(FileLoaderSaver.class);
		bindAsEagerSingleton(Exporter.class);
		bindAsEagerSingleton(EditingSelector.class);
		bindAsEagerSingleton(TextSetter.class);
		bindAsEagerSingleton(Hand.class);
		bindAsEagerSingleton(Helper.class);
		bindAsEagerSingleton(Pencil.class);
		bindAsEagerSingleton(ShapeArcCustomiser.class);
		bindAsEagerSingleton(ShapeArrowCustomiser.class);
		bindAsEagerSingleton(ShapeAxesCustomiser.class);
		bindAsEagerSingleton(ShapeBorderCustomiser.class);
		bindAsEagerSingleton(ShapeCoordDimCustomiser.class);
		bindAsEagerSingleton(ShapeDeleter.class);
		bindAsEagerSingleton(ShapeDotCustomiser.class);
		bindAsEagerSingleton(ShapeDoubleBorderCustomiser.class);
		bindAsEagerSingleton(ShapeFillingCustomiser.class);
		bindAsEagerSingleton(ShapeFreeHandCustomiser.class);
		bindAsEagerSingleton(ShapeGridCustomiser.class);
		bindAsEagerSingleton(ShapeGrouper.class);
		bindAsEagerSingleton(ShapePlotCustomiser.class);
		bindAsEagerSingleton(ShapePositioner.class);
		bindAsEagerSingleton(ShapeRotationCustomiser.class);
		bindAsEagerSingleton(ShapeShadowCustomiser.class);
		bindAsEagerSingleton(ShapeStdGridCustomiser.class);
		bindAsEagerSingleton(ShapeTextCustomiser.class);
		bindAsEagerSingleton(ShapeTransformer.class);
		bindAsEagerSingleton(TabSelector.class);
	}
}
