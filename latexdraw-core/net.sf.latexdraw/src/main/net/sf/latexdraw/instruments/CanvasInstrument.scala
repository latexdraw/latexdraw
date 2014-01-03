package net.sf.latexdraw.instruments

import net.sf.latexdraw.glib.ui.ICanvas
import org.malai.swing.instrument.library.WidgetZoomer
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import java.awt.Point
import org.malai.instrument.Instrument
import net.sf.latexdraw.glib.models.interfaces.IPoint

/**
 * This abstract instrument encapsulates common operations dealing with a canvas.
 * @since 3.1
 */
abstract class CanvasInstrument(val canvas : ICanvas) extends Instrument {
	/**
	 * Computes the point depending on the the zoom level and the origin of the canvas.
	 * @param point The point to adapted.
	 * @return The computed point.
	 * @since 3.0
	 */
	def getAdaptedOriginPoint(pt:IPoint) = canvas.convertToOrigin(DrawingTK.getFactory.createPoint(canvas.getZoomedPoint(pt.getX, pt.getY)))

	/**
	 * Computes the point depending on the the zoom level and the magnetic grid.
	 * @param point The point to adapted.
	 * @return The computed point.
	 * @since 3.1
	 */
	def getAdaptedGridPoint(pt:IPoint) = canvas.getMagneticGrid.getTransformedPointToGrid(canvas.getZoomedPoint(pt.getX, pt.getY))

	/**
	 * Computes the point depending on the the zoom level and the magnetic grid.
	 * @param point The point to adapted.
	 * @return The computed point.
	 * @since 3.0
	 */
	def getAdaptedPoint(pt:Point) = canvas.convertToOrigin(canvas.getMagneticGrid.getTransformedPointToGrid(canvas.getZoomedPoint(pt)))
}
