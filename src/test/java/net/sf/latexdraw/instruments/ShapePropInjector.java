package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.jfx.Canvas;

public class ShapePropInjector extends Injector {
	protected Pencil pencil;
	protected Hand hand;

	@Override
	protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		bindAsEagerSingleton(Canvas.class);
		bindWithCommand(IDrawing.class, Canvas.class, canvas -> canvas.getDrawing());
		bindWithCommand(MagneticGrid.class, Canvas.class, canvas -> canvas.getMagneticGrid());
		bindWithCommand(ViewsSynchroniserHandler.class, Canvas.class, canvas -> canvas);
	}
}
