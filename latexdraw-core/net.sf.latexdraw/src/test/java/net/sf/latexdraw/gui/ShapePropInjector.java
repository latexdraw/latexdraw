package net.sf.latexdraw.gui;

import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.jfx.Canvas;

public class ShapePropInjector extends Injector {
	protected Pencil pencil;
	protected Hand hand;

	@Override
	protected void configure() throws InstantiationException, IllegalAccessException {
		bindAsEagerSingleton(Canvas.class);
		bindWithCommand(IDrawing.class, Canvas.class, canvas -> canvas.getDrawing());
		bindWithCommand(MagneticGrid.class, Canvas.class, canvas -> canvas.getMagneticGrid());
		bindWithCommand(ViewsSynchroniserHandler.class, Canvas.class, canvas -> canvas);
	}
}
