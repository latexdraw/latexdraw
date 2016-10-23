package net.sf.latexdraw;

import com.google.inject.Provides;
import net.sf.latexdraw.glib.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.instruments.*;
import net.sf.latexdraw.ui.XScaleRuler;
import net.sf.latexdraw.ui.YScaleRuler;
import net.sf.latexdraw.view.jfx.Canvas;

import com.google.inject.AbstractModule;

class LatexdrawModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Canvas.class).asEagerSingleton();
		bind(AboutController.class).asEagerSingleton();
		bind(Border.class).asEagerSingleton();
		bind(CodeInserter.class).asEagerSingleton();
		bind(CopierCutterPaster.class).asEagerSingleton();
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

	@Provides IDrawing provideDrawing(final Canvas canvas) {
		return canvas.getDrawing();
	}

	@Provides LaTeXGenerator provideLaTeXGenerator(final CodePanelController controller) {
		return controller.getPstGenerator();
	}
}
