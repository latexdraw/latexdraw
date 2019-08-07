package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.LatexdrawBuilderFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.impl.ShapeFactoryImpl;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.MagneticGrid;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import net.sf.latexdraw.view.pst.PSTViewsFactory;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import net.sf.latexdraw.view.svg.SVGShapesFactory;
import io.github.interacto.jfx.ui.JfxUI;
import org.mockito.Mockito;

public class ShapePropInjector extends Injector {
	protected Pencil pencil;
	protected Hand hand;

	@Override
	protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		bindToInstance(JfxUI.class, Mockito.mock(LaTeXDraw.class));
		bindToInstance(Injector.class, this);
		bindToInstance(BuilderFactory.class, new LatexdrawBuilderFactory(this));
		bindAsEagerSingleton(LaTeXDataService.class);
		bindAsEagerSingleton(PreferencesService.class);
		bindAsEagerSingleton(EditingService.class);
		bindWithCommand(ResourceBundle.class, PreferencesService.class, pref -> pref.getBundle());
		bindAsEagerSingleton(ShapeFactoryImpl.class);
		bindAsEagerSingleton(PSTViewsFactory.class);
		bindAsEagerSingleton(ViewFactory.class);
		bindAsEagerSingleton(SVGShapesFactory.class);
		bindAsEagerSingleton(Canvas.class);
		bindWithCommand(Drawing.class, Canvas.class, canvas -> canvas.getDrawing());
		bindWithCommand(MagneticGrid.class, Canvas.class, canvas -> canvas.getMagneticGrid());
		bindWithCommand(ViewsSynchroniserHandler.class, Canvas.class, canvas -> canvas);
		bindAsEagerSingleton(PSTCodeGenerator.class);
		bindWithCommand(LaTeXGenerator.class, PSTCodeGenerator.class, gen -> gen);
		bindAsEagerSingleton(SVGDocumentGenerator.class);
	}
}
