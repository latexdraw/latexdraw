package net.sf.latexdraw.glib.views.pst;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.ISquare;
import net.sf.latexdraw.util.LNumber;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Defines a PSTricks view of the LSquare model.
 * @author Arnaud Blouin
 */
class PSTSquareView extends PSTClassicalView<ISquare>{

	protected PSTSquareView(@NonNull final ISquare model) {
		super(model);
		update();
	}

	@Override
	public void updateCache(IPoint position, float ppc) {
		if(!GLibUtilities.isValidPoint(position) || ppc<1) return;

		emptyCache();
		final StringBuilder params  = getPropertiesCode(ppc);
		final IPoint tl = shape.getTopLeftPoint();
		final IPoint br = shape.getBottomRightPoint();
		final double x1 = tl.getX() - position.getX();
		final double x2 = br.getX() - position.getX();
		final double y1 = position.getY() - tl.getY();
		final double y2 = position.getY() - br.getY();

		if(shape.isRoundCorner())
			params.append(", framearc=").append(LNumber.getCutNumberFloat(shape.getLineArc())); //$NON-NLS-1$

		final StringBuilder rotation = getRotationHeaderCode(ppc, position);

		if(rotation!=null)
			cache.append(rotation);

		cache.append("\\psframe[");	//$NON-NLS-1$
		cache.append(params);
		cache.append(']').append('(');
		cache.append(LNumber.getCutNumberFloat(x2 / ppc)).append(',');
		cache.append(LNumber.getCutNumberFloat(y1 / ppc)).append(')').append('(');
		cache.append(LNumber.getCutNumberFloat(x1 / ppc)).append(',');
		cache.append(LNumber.getCutNumberFloat(y2 / ppc)).append(')');

		if(rotation!=null)
			cache.append('}');
	}
}
