package test.gui;

import net.sf.latexdraw.glib.views.jfx.Canvas;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;

import com.google.inject.AbstractModule;

public class ShapePropModule extends AbstractModule {
	protected Pencil pencil;
	protected Hand hand;

	@Override
	protected void configure() {
		bind(Canvas.class).asEagerSingleton();
	}
}
