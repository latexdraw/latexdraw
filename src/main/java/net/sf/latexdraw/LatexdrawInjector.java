/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw;

import io.github.interacto.jfx.ui.JfxUI;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.instrument.AboutController;
import net.sf.latexdraw.instrument.BadaboomController;
import net.sf.latexdraw.instrument.Border;
import net.sf.latexdraw.instrument.CanvasController;
import net.sf.latexdraw.instrument.CodeInserter;
import net.sf.latexdraw.instrument.CodePanelController;
import net.sf.latexdraw.instrument.CopierCutterPaster;
import net.sf.latexdraw.instrument.DrawingPropertiesCustomiser;
import net.sf.latexdraw.instrument.EditingSelector;
import net.sf.latexdraw.instrument.ExceptionsManager;
import net.sf.latexdraw.instrument.Exporter;
import net.sf.latexdraw.instrument.FacadeCanvasController;
import net.sf.latexdraw.instrument.FileLoaderSaver;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.Helper;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.PreferencesSetter;
import net.sf.latexdraw.instrument.ShapeArcCustomiser;
import net.sf.latexdraw.instrument.ShapeArrowCustomiser;
import net.sf.latexdraw.instrument.ShapeAxesCustomiser;
import net.sf.latexdraw.instrument.ShapeBorderCustomiser;
import net.sf.latexdraw.instrument.ShapeCoordDimCustomiser;
import net.sf.latexdraw.instrument.ShapeDeleter;
import net.sf.latexdraw.instrument.ShapeDotCustomiser;
import net.sf.latexdraw.instrument.ShapeDoubleBorderCustomiser;
import net.sf.latexdraw.instrument.ShapeFillingCustomiser;
import net.sf.latexdraw.instrument.ShapeFreeHandCustomiser;
import net.sf.latexdraw.instrument.ShapeGridCustomiser;
import net.sf.latexdraw.instrument.ShapeGrouper;
import net.sf.latexdraw.instrument.ShapePlotCustomiser;
import net.sf.latexdraw.instrument.ShapePositioner;
import net.sf.latexdraw.instrument.ShapeRotationCustomiser;
import net.sf.latexdraw.instrument.ShapeShadowCustomiser;
import net.sf.latexdraw.instrument.ShapeStdGridCustomiser;
import net.sf.latexdraw.instrument.ShapeTextCustomiser;
import net.sf.latexdraw.instrument.ShapeTransformer;
import net.sf.latexdraw.instrument.ShortcutsController;
import net.sf.latexdraw.instrument.StatusBarController;
import net.sf.latexdraw.instrument.TabSelector;
import net.sf.latexdraw.instrument.TemplateManager;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.instrument.UndoRedoManager;
import net.sf.latexdraw.instrument.Zoomer;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.MagneticGrid;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import net.sf.latexdraw.view.pst.PSTViewsFactory;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import net.sf.latexdraw.view.svg.SVGShapesFactory;

/**
 * @author Arnaud Blouin
 */
public class LatexdrawInjector extends Injector {
	private final LaTeXDraw app;

	public LatexdrawInjector(final LaTeXDraw latexdraw) {
		super();
		app = latexdraw;
		initialise();
	}

	@Override
	protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		bindToInstance(Injector.class, this);
		bindToInstance(JfxUI.class, app);
		bindToInstance(Application.class, app);
		bindToInstance(LaTeXDraw.class, app);
		bindToSupplier(Stage.class, () -> app.getMainStage());
		bindToInstance(BuilderFactory.class, new LatexdrawBuilderFactory(this));
		bindAsEagerSingleton(LaTeXDataService.class);
		bindAsEagerSingleton(PreferencesService.class);
		bindAsEagerSingleton(BadaboomController.class);
		bindAsEagerSingleton(EditingService.class);
		bindWithCommand(ResourceBundle.class, PreferencesService.class, pref -> pref.getBundle());
		bindAsEagerSingleton(ViewFactory.class);
		bindAsEagerSingleton(PSTViewsFactory.class);
		bindAsEagerSingleton(SVGShapesFactory.class);
		bindAsEagerSingleton(ExceptionsManager.class);
		bindAsEagerSingleton(ShortcutsController.class);
		bindWithCommand(HostServices.class, Application.class, fxApp -> fxApp.getHostServices());
		bindAsEagerSingleton(StatusBarController.class);
		bindAsEagerSingleton(AboutController.class);
		bindAsEagerSingleton(Canvas.class);
		bindWithCommand(MagneticGrid.class, Canvas.class, canvas -> canvas.getMagneticGrid());
		bindAsEagerSingleton(CanvasController.class);
		bindWithCommand(Drawing.class, Canvas.class, canvas -> canvas.getDrawing());
		bindWithCommand(ViewsSynchroniserHandler.class, Canvas.class, canvas -> canvas);
		bindAsEagerSingleton(SVGDocumentGenerator.class);
		bindAsEagerSingleton(Zoomer.class);
		bindAsEagerSingleton(UndoRedoManager.class);
		bindAsEagerSingleton(PSTCodeGenerator.class);
		bindWithCommand(LaTeXGenerator.class, PSTCodeGenerator.class, gen -> gen);
		bindAsEagerSingleton(CodePanelController.class);
		bindAsEagerSingleton(DrawingPropertiesCustomiser.class);
		bindAsEagerSingleton(TemplateManager.class);
		bindAsEagerSingleton(CodeInserter.class);
		bindAsEagerSingleton(CopierCutterPaster.class);
		bindAsEagerSingleton(Exporter.class);
		bindAsEagerSingleton(PreferencesSetter.class);
		bindAsEagerSingleton(FileLoaderSaver.class);
		bindAsEagerSingleton(Helper.class);
		bindAsEagerSingleton(TextSetter.class);
		bindAsEagerSingleton(Pencil.class);
		bindAsEagerSingleton(Hand.class);
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
		bindAsEagerSingleton(MetaShapeCustomiser.class);
		bindAsEagerSingleton(Border.class);
		bindAsEagerSingleton(FacadeCanvasController.class);
		bindAsEagerSingleton(EditingSelector.class);
		bindAsEagerSingleton(TabSelector.class);
	}
}
