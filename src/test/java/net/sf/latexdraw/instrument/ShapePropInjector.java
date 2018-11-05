package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.LatexdrawBuilderFactory;
import net.sf.latexdraw.model.impl.ShapeFactoryImpl;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import net.sf.latexdraw.view.pst.PSTViewsFactory;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import net.sf.latexdraw.view.svg.SVGShapesFactory;
import org.malai.javafx.ui.JfxUI;
import org.mockito.Mockito;

public class ShapePropInjector extends Injector {
	protected Pencil pencil;
	protected Hand hand;

	@Override
	protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		bindToInstance(JfxUI.class, Mockito.mock(LaTeXDraw.class));
		bindToInstance(Injector.class, this);
		bindToInstance(BuilderFactory.class, new LatexdrawBuilderFactory(this));
		bindAsEagerSingleton(SystemService.class);
		bindAsEagerSingleton(LangService.class);
		bindAsEagerSingleton(ShapeFactoryImpl.class);
		bindAsEagerSingleton(PSTCodeGenerator.class);
		bindWithCommand(LaTeXGenerator.class, PSTCodeGenerator.class, gen -> gen);
		bindAsEagerSingleton(ViewFactory.class);
		bindAsEagerSingleton(PSTViewsFactory.class);
		bindAsEagerSingleton(SVGShapesFactory.class);
		bindAsEagerSingleton(SVGDocumentGenerator.class);
		bindAsEagerSingleton(Canvas.class);
		bindWithCommand(Drawing.class, Canvas.class, canvas -> canvas.getDrawing());
		bindWithCommand(MagneticGrid.class, Canvas.class, canvas -> canvas.getMagneticGrid());
		bindWithCommand(ViewsSynchroniserHandler.class, Canvas.class, canvas -> canvas);
	}
}
