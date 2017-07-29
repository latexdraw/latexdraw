package net.sf.latexdraw.view.pst;

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
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestPSTViewFactory {
	@Test
	public void testCreateGroupViewPST() {
		IGroup gp = ShapeFactory.INST.createGroup();
		gp.addShape(ShapeFactory.INST.createText());
		Optional<PSTShapeView<IGroup>> view = PSTViewsFactory.INSTANCE.createView(gp);
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateTextViewPST() {
		Optional<PSTShapeView<IText>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createText());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateArcCircleViewPST() {
		Optional<PSTShapeView<ICircleArc>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createCircleArc());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateRectangleViewPST() {
		Optional<PSTShapeView<IRectangle>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createRectangle());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateSquareViewPST() {
		Optional<PSTShapeView<ISquare>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createSquare());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateEllipseViewPST() {
		Optional<PSTShapeView<IEllipse>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createEllipse());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateCircleViewPST() {
		Optional<PSTShapeView<ICircle>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createCircle());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateGridViewPST() {
		Optional<PSTShapeView<IGrid>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateAxesViewPST() {
		Optional<PSTShapeView<IAxes>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreatePolygonViewPST() {
		Optional<PSTShapeView<IPolygon>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createPolygon());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreatePolylineViewPST() {
		Optional<PSTShapeView<IPolyline>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createPolyline());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateTriangleViewPST() {
		Optional<PSTShapeView<ITriangle>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createTriangle());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateRhombusViewPST() {
		Optional<PSTShapeView<IRhombus>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createRhombus());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateFreehandViewPST() {
		Optional<PSTShapeView<IFreehand>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createFreeHand());
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreatePictureViewPST() {
		Optional<PSTShapeView<IPicture>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateDotViewPST() {
		Optional<PSTShapeView<IDot>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	public void testCreateBezierCurveViewPST() {
		Optional<PSTShapeView<IBezierCurve>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createBezierCurve());
		assertTrue(view.isPresent());
	}
}
