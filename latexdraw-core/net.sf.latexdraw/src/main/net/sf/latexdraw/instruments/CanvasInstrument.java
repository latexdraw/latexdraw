package net.sf.latexdraw.instruments;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.view.jfx.Canvas;

import org.malai.javafx.instrument.JfxInstrument;

import com.google.inject.Inject;

/**
 * This abstract instrument encapsulates common operations dealing with a
 * canvas.
 * 
 * @since 3.1
 */
abstract class CanvasInstrument extends JfxInstrument {
	@Inject protected Canvas canvas;

	CanvasInstrument() {
		super();
	}

	/**
	 * Computes the point depending on the the zoom level and the origin of the
	 * canvas.
	 * 
	 * @param pt
	 *            The point to adapted.
	 * @return The computed point.
	 * @since 3.0
	 */
	public IPoint getAdaptedOriginPoint(final IPoint pt) {
		final IPoint pt2 = canvas.convertToOrigin(pt);
		return ShapeFactory.createPoint(canvas.getZoomedPoint(pt2.getX(), pt2.getY()));
	}

	public Canvas getCanvas() {// FIXME to remove
		return canvas;
	}
}
