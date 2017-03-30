package net.sf.latexdraw.view.jfx;

import net.sf.latexdraw.models.interfaces.shape.IPolyline;

public class ViewPolyline extends ViewPolyPoint<IPolyline> {
	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPolyline(final IPolyline sh) {
		super(sh);
	}
}
