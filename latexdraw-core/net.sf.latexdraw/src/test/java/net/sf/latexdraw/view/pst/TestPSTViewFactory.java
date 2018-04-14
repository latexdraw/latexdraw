package net.sf.latexdraw.view.pst;

import java.util.Collections;
import java.util.Optional;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestPSTViewFactory {
	@After
	public void tearDown() {
		DviPsColors.INSTANCE.clearUserColours();
	}

	@Test
	public void testCreateGroupViewPST() {
		final IGroup gp = ShapeFactory.INST.createGroup();
		gp.addShape(ShapeFactory.INST.createText());
		final Optional<PSTShapeView<IGroup>> view = PSTViewsFactory.INSTANCE.createView(gp);
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateTextViewPST() {
		final Optional<PSTShapeView<IText>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createText());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateArcCircleViewPST() {
		final Optional<PSTShapeView<ICircleArc>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createCircleArc());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateRectangleViewPST() {
		final Optional<PSTShapeView<IRectangle>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createRectangle());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateSquareViewPST() {
		final Optional<PSTShapeView<ISquare>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createSquare());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateEllipseViewPST() {
		final Optional<PSTShapeView<IEllipse>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createEllipse());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateCircleViewPST() {
		final Optional<PSTShapeView<ICircle>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createCircle());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateGridViewPST() {
		final Optional<PSTShapeView<IGrid>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateAxesViewPST() {
		final Optional<PSTShapeView<IAxes>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreatePolygonViewPST() {
		final Optional<PSTShapeView<IPolygon>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createPolygon(Collections.emptyList()));
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreatePolylineViewPST() {
		final Optional<PSTShapeView<IPolyline>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createPolyline(Collections.emptyList()));
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateTriangleViewPST() {
		final Optional<PSTShapeView<ITriangle>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createTriangle());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateRhombusViewPST() {
		final Optional<PSTShapeView<IRhombus>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createRhombus());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateFreehandViewPST() {
		final Optional<PSTShapeView<IFreehand>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createFreeHand(Collections.emptyList()));
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreatePictureViewPST() {
		final Optional<PSTShapeView<IPicture>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateDotViewPST() {
		final Optional<PSTShapeView<IDot>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateBezierCurveViewPST() {
		final Optional<PSTShapeView<IBezierCurve>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createBezierCurve(Collections.emptyList()));
		assertTrue(view.isPresent());
	}
}
