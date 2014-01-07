package test.glib.views.pst;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.views.pst.PSTViewsFactory;


public class TestPSTViewFactory {
	public void testCreateGroupViewPST() {
		IGroup gp = ShapeFactory.createGroup(false);
		gp.addShape(ShapeFactory.createText(false));
		Object view = PSTViewsFactory.INSTANCE.createView(gp);
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("group"));
	}


	public void testCreateTextViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createText(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("text"));
	}


	public void testCreateArcCircleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createCircleArc(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("arc"));
	}

	public void testCreateRectangleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createRectangle(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rect"));
	}

	public void testCreateSquareViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createSquare(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rect"));
	}

	public void testCreateEllipseViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createEllipse(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("ellipse"));
	}

	public void testCreateCircleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createCircle(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("circle"));
	}

	public void testCreateGridViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createGrid(false, ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("grid"));
	}

	public void testCreateAxesViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createAxes(false, ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("axes"));
	}

	public void testCreatePolygonViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createPolygon(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("polygon"));
	}

	public void testCreatePolylineViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createPolyline(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("lines"));
	}

	public void testCreateTriangleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createTriangle(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("triangle"));
	}

	public void testCreateRhombusViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createRhombus(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rhombus"));
	}

	public void testCreateFreehandViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createFreeHand(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("freehand"));
	}

	public void testCreatePictureViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createPicture(false, ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("picture"));
	}

	public void testCreateDotViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createDot(ShapeFactory.createPoint(), false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("dot"));
	}

	public void testCreateBezierCurveViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createBezierCurve(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("beziercurve"));
	}
}
