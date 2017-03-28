package net.sf.latexdraw.view.jfx;

import net.sf.latexdraw.models.interfaces.shape.IPolygon;

public class ViewPolygon extends ViewPolyPoint<IPolygon> {
	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	public ViewPolygon(final IPolygon sh) {
		super(sh);
		border.getElements().add(ViewFactory.INSTANCE.createClosePath());
		shadow.getElements().add(ViewFactory.INSTANCE.createClosePath());
	}
}
