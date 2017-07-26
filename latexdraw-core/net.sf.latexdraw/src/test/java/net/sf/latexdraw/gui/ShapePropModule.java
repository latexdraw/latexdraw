package net.sf.latexdraw.gui;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.ViewsSynchroniserHandler;
import net.sf.latexdraw.view.jfx.Canvas;

public class ShapePropModule extends AbstractModule {
	protected Pencil pencil;
	protected Hand hand;

	@Override
	protected void configure() {
		bind(Canvas.class).asEagerSingleton();
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
	MagneticGrid provideMagneticGrid(final Canvas canvas) {
		return canvas.getMagneticGrid();
	}
}
