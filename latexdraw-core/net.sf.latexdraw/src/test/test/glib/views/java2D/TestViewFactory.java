package test.glib.views.java2D;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.views.Java2D.impl.LViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;

import org.junit.Before;

public class TestViewFactory {
	protected IViewsFactory viewFac;

	static {
		View2DTK.setFactory(new LViewsFactory());
	}


	@Before
	public void setUp() {
		viewFac = View2DTK.getFactory();
	}


	public void testCreateGroupView() {
		IGroup gp = ShapeFactory.createGroup(false);
		gp.addShape(ShapeFactory.createText(false));
		IViewShape view = viewFac.createView(gp);
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("group"));
	}


	public void testCreateTextView() {
		IViewShape view = viewFac.createView(ShapeFactory.createText(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("text"));
	}


	public void testCreateArcCircleView() {
		IViewShape view = viewFac.createView(ShapeFactory.createCircleArc(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("circlearc"));
	}

	public void testCreateRectangleView() {
		IViewShape view = viewFac.createView(ShapeFactory.createRectangle(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rectangle"));
	}

	public void testCreateSquareView() {
		IViewShape view = viewFac.createView(ShapeFactory.createSquare(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("square"));
	}

	public void testCreateEllipseView() {
		IViewShape view = viewFac.createView(ShapeFactory.createEllipse(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("ellipse"));
	}

	public void testCreateCircleView() {
		IViewShape view = viewFac.createView(ShapeFactory.createCircle(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("circle"));
	}

	public void testCreateGridView() {
		IViewShape view = viewFac.createView(ShapeFactory.createGrid(false, ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("grid"));
	}

	public void testCreateAxesView() {
		IViewShape view = viewFac.createView(ShapeFactory.createAxes(false, ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("axes"));
	}

	public void testCreatePolygonView() {
		IViewShape view = viewFac.createView(ShapeFactory.createPolygon(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("polygon"));
	}

	public void testCreatePolylineView() {
		IViewShape view = viewFac.createView(ShapeFactory.createPolyline(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("polyline"));
	}

	public void testCreateTriangleView() {
		IViewShape view = viewFac.createView(ShapeFactory.createTriangle(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("triangle"));
	}

	public void testCreateRhombusView() {
		IViewShape view = viewFac.createView(ShapeFactory.createRhombus(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rhombus"));
	}

	public void testCreateFreehandView() {
		IViewShape view = viewFac.createView(ShapeFactory.createFreeHand(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("freehand"));
	}

	public void testCreatePictureView() {
		IViewShape view = viewFac.createView(ShapeFactory.createPicture(false, ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("picture"));
	}

	public void testCreateDotView() {
		IViewShape view = viewFac.createView(ShapeFactory.createDot(ShapeFactory.createPoint(), false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("dot"));
	}

	public void testCreateBezierCurveView() {
		IViewShape view = viewFac.createView(ShapeFactory.createBezierCurve(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("beziercurve"));
	}
}
