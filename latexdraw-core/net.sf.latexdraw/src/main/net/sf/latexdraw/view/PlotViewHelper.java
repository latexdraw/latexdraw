package net.sf.latexdraw.view;

import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;

public class PlotViewHelper {
	public static final PlotViewHelper INSTANCE = new PlotViewHelper();

	private PlotViewHelper() {
		super();
	}

	
	private IPoint getPolarPoint(final IPlot shape, final double x, final double xs, final double ys, final double posX, final double posY) {
		final double radius = shape.getY(x);
		final double angle = Math.toRadians(x);
		final double x1 = radius * Math.cos(angle);
		final double y1 = -radius * Math.sin(angle);
		return ShapeFactory.INST.createPoint(x1 * IShape.PPC * xs + posX, y1 * IShape.PPC * ys + posY);
	}
	
	public void fillPoints(final IPlot shape, final IModifiablePointsShape sh, final double posX, final double posY, 
							final double minX, final double maxX, final double step) {
		final double xs = shape.getXScale();
		final double ys = shape.getYScale();
		double x = minX;
		
		if(shape.isPolar()) {
			for(int i=0; i<shape.getNbPlottedPoints(); i++, x += step)
				sh.addPoint(getPolarPoint(shape, x, xs, ys, posX, posY));
			sh.addPoint(getPolarPoint(shape, maxX, xs, ys, posX, posY));
		}else {
			for(int i=0; i<shape.getNbPlottedPoints(); i++, x += step)
				sh.addPoint(ShapeFactory.INST.createPoint(x * IShape.PPC * xs + posX, -shape.getY(x) * IShape.PPC * ys + posY));
			sh.addPoint(ShapeFactory.INST.createPoint(maxX * IShape.PPC * xs + posX, -shape.getY(maxX) * IShape.PPC * ys + posY));
		}
	}

	
	public List<IDot> updatePoints(final IPlot shape, final double posX, final double posY, final double minX, final double maxX, final double step) {
		final IPolyline pl = ShapeFactory.INST.createPolyline();
		final List<IDot> dots = new ArrayList<>();
		fillPoints(shape, pl, posX, posY, minX, maxX, step);

		for(IPoint pt : pl.getPoints()) {
			IDot dot = ShapeFactory.INST.createDot(pt);
			dot.copy(shape);
			dot.setRotationAngle(0.0);
			dots.add(dot);
		}
		return dots;
	}

	
	public IPolygon updatePolygon(final IPlot shape, final double posX, final double posY, final double minX, final double maxX, final double step) {
		final IPolygon pg = ShapeFactory.INST.createPolygon();
		fillPoints(shape, pg, posX, posY, minX, maxX, step);
		pg.copy(shape);
		return pg;
	}

	
	public IPolyline updateLine(final IPlot shape, final double posX, final double posY, final double minX, final double maxX, final double step) {
		final IPolyline pl = ShapeFactory.INST.createPolyline();
		fillPoints(shape, pl, posX, posY, minX, maxX, step);
		pl.copy(shape);
		return pl;
	}

	
	public IBezierCurve updateCurve(final IPlot shape, final double posX, final double posY, final double minX, final double maxX, final double step) {
		// The algorithm follows this definition:
		// https://stackoverflow.com/questions/15864441/how-to-make-a-line-curve-through-points
		final double scale = 0.33;
		final IBezierCurve bc = ShapeFactory.INST.createBezierCurve();

		fillPoints(shape, bc, posX, posY, minX, maxX, step);
		if(shape.getPlotStyle() == PlotStyle.CCURVE)
			bc.setIsClosed(true);
		else
			bc.setIsClosed(false);
		bc.copy(shape);
		int i = 0;
		final int last = bc.getPoints().size() - 1;

		for(IPoint pt : bc.getPoints()) {
			final IPoint p1 = pt;
			if(i == 0) {
				final IPoint p2 = bc.getPtAt(i + 1);
				final IPoint tangent = p2.substract(p1);
				final IPoint q1 = p1.add(tangent.zoom(scale));
				bc.setXFirstCtrlPt(q1.getX(), i);
				bc.setYFirstCtrlPt(q1.getY(), i);
			}else if(i == last) {
				final IPoint p0 = bc.getPtAt(i - 1);
				final IPoint tangent = p1.substract(p0);
				final IPoint q0 = p1.substract(tangent.zoom(scale));
				bc.setXFirstCtrlPt(q0.getX(), i);
				bc.setYFirstCtrlPt(q0.getY(), i);
			}else {
				final IPoint p0 = bc.getPtAt(i - 1);
				final IPoint p2 = bc.getPtAt(i + 1);
				final IPoint tangent = p2.substract(p0).normalise();
				final IPoint q0 = p1.substract(tangent.zoom(scale * p1.substract(p0).magnitude()));
				bc.setXFirstCtrlPt(q0.getX(), i);
				bc.setYFirstCtrlPt(q0.getY(), i);
			}
			i++;
		}
		bc.updateSecondControlPoints();
		return bc;
	}
}
