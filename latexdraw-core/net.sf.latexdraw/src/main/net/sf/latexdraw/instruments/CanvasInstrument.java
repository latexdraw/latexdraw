package net.sf.latexdraw.instruments;

import java.awt.Point;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.ui.ICanvas;

import org.malai.javafx.instrument.JfxInstrument;


/**
 * This abstract instrument encapsulates common operations dealing with a canvas.
 * @since 3.1
 */
abstract class CanvasInstrument extends JfxInstrument {
	protected ICanvas canvas;
	
	CanvasInstrument() {
		super();
	}
	
	
	/**
	 * Computes the point depending on the the zoom level and the origin of the canvas.
	 * @param pt The point to adapted.
	 * @return The computed point.
	 * @since 3.0
	 */
	public IPoint getAdaptedOriginPoint(final IPoint pt) {
		final IPoint pt2 = canvas.convertToOrigin(pt);
		return ShapeFactory.createPoint(canvas.getZoomedPoint(pt2.getX(), pt2.getY()));
	}
	

	/**
	 * Computes the point depending on the the zoom level and the magnetic grid.
	 * @param pt The point to adapted.
	 * @return The computed point.
	 * @since 3.1
	 */
	public IPoint getAdaptedGridPoint(final IPoint pt) {
		return ShapeFactory.createPoint();// canvas.getMagneticGrid.getTransformedPointToGrid(canvas.getZoomedPoint(pt.getX, pt.getY))
	}

	/**
	 * Computes the point depending on the the zoom level and the magnetic grid.
	 * @param pt The point to adapted.
	 * @return The computed point.
	 * @since 3.0
	 */
	public IPoint getAdaptedPoint(final Point pt) {
//		val pt2 = canvas.convertToOrigin(canvas.getMagneticGrid.getTransformedPointToGrid(pt))
//		ShapeFactory.createPoint(canvas.getZoomedPoint(pt2.getX, pt2.getY))
		return ShapeFactory.createPoint();
	}
	
	public ICanvas getCanvas() {//FIXME to remove
		return canvas;
	}
}
