package net.sf.latexdraw.glib.views.pst;

import org.eclipse.jdt.annotation.NonNull;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp.PlotStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LNumber;

class PSTPlotView extends PSTClassicalView<IPlot>{
	protected PSTPlotView(@NonNull final IPlot model) {
		super(model);
		update();
	}

	@Override
	public void updateCache(final IPoint position, final float ppc) {
		if(!GLibUtilities.isValidPoint(position) || ppc<1) return;

		emptyCache();

		final StringBuilder params = getPropertiesCode(ppc);
		final StringBuilder rotation = getRotationHeaderCode(ppc, position);

		if(rotation!=null)
			cache.append(rotation);

		cache.append("\\rput(");//$NON-NLS-1$
		cache.append(LNumber.getCutNumberFloat((shape.getX()-position.getX())/ppc)).append(',');
		cache.append(LNumber.getCutNumberFloat((position.getY()-shape.getY())/ppc)).append(')').append('{');
		cache.append("\\psplot[");	//$NON-NLS-1$
		cache.append(params).append(", plotstyle=").append(shape.getPlotStyle().getPSTToken()).append(", plotpoints=").
			append(shape.getNbPlottedPoints()).append(", xunit=").append(shape.getXScale()).append(", yunit=").
			append(shape.getYScale()).append(", polarplot=").append(shape.isPolar());
		if(shape.getPlotStyle()==PlotStyle.DOTS) {
			cache.append(", dotstyle=").append(shape.getDotStyle().getPSTToken()).
				append(", dotsize=").append(LNumber.getCutNumberFloat(shape.getDiametre()/ppc));
			if(shape.getDotStyle().isFillable())
				cache.append(", fillcolor=").append(getColourName(shape.getFillingCol()));
		}
		cache.append("]{").append(shape.getPlotMinX()).append("}{").append(shape.getPlotMaxX()).append("}{").
			append(shape.getPlotEquation()).append('}');

		if(rotation!=null)
			cache.append('}');
		cache.append('}');
	}
}
