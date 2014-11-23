package test.glib.views.java2D;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.views.Java2D.impl.LViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewShape;
import net.sf.latexdraw.glib.views.Java2D.interfaces.IViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;

import org.junit.Before;
import org.junit.Test;

public class TestViewFactory {
	protected IViewsFactory viewFac;

	static {
		View2DTK.setFactory(new LViewsFactory());
	}


	@Before
	public void setUp() {
		viewFac = View2DTK.getFactory();
	}


	@Test public void testCreateGroupView() {
		IGroup gp = ShapeFactory.createGroup();
		gp.addShape(ShapeFactory.createText());
		IViewShape view = viewFac.createView(gp);
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("group")); //$NON-NLS-1$
	}


	@Test public void testCreateTextView() {
		IViewShape view = viewFac.createView(ShapeFactory.createText());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("text")); //$NON-NLS-1$
	}


	@Test public void testCreateArcCircleView() {
		IViewShape view = viewFac.createView(ShapeFactory.createCircleArc());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("circlearc")); //$NON-NLS-1$
	}

	@Test public void testCreateRectangleView() {
		IViewShape view = viewFac.createView(ShapeFactory.createRectangle());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rectangle")); //$NON-NLS-1$
	}

	@Test public void testCreateSquareView() {
		IViewShape view = viewFac.createView(ShapeFactory.createSquare());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("square")); //$NON-NLS-1$
	}

	@Test public void testCreateEllipseView() {
		IViewShape view = viewFac.createView(ShapeFactory.createEllipse());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("ellipse")); //$NON-NLS-1$
	}

	@Test public void testCreateCircleView() {
		IViewShape view = viewFac.createView(ShapeFactory.createCircle());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("circle")); //$NON-NLS-1$
	}

	@Test public void testCreateGridView() {
		IViewShape view = viewFac.createView(ShapeFactory.createGrid(ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("grid")); //$NON-NLS-1$
	}

	@Test public void testCreateAxesView() {
		IViewShape view = viewFac.createView(ShapeFactory.createAxes(ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("axes")); //$NON-NLS-1$
	}

	@Test public void testCreatePolygonView() {
		IViewShape view = viewFac.createView(ShapeFactory.createPolygon());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("polygon")); //$NON-NLS-1$
	}

	@Test public void testCreatePolylineView() {
		IViewShape view = viewFac.createView(ShapeFactory.createPolyline());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("polyline")); //$NON-NLS-1$
	}

	@Test public void testCreateTriangleView() {
		IViewShape view = viewFac.createView(ShapeFactory.createTriangle());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("triangle")); //$NON-NLS-1$
	}

	@Test public void testCreateRhombusView() {
		IViewShape view = viewFac.createView(ShapeFactory.createRhombus());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rhombus")); //$NON-NLS-1$
	}

	@Test public void testCreateFreehandView() {
		IViewShape view = viewFac.createView(ShapeFactory.createFreeHand());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("freehand")); //$NON-NLS-1$
	}

	@Test public void testCreatePictureView() {
		IViewShape view = viewFac.createView(ShapeFactory.createPicture(ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("picture")); //$NON-NLS-1$
	}

	@Test public void testCreateDotView() {
		IViewShape view = viewFac.createView(ShapeFactory.createDot(ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("dot")); //$NON-NLS-1$
	}

	@Test public void testCreateBezierCurveView() {
		IViewShape view = viewFac.createView(ShapeFactory.createBezierCurve());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("beziercurve")); //$NON-NLS-1$
	}
}
