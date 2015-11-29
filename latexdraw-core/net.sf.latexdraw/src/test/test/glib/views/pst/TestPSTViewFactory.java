package test.glib.views.pst;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.views.pst.PSTViewsFactory;

public class TestPSTViewFactory {
	@Test
	public void testCreateGroupViewPST() {
		IGroup gp = ShapeFactory.createGroup();
		gp.addShape(ShapeFactory.createText());
		Object view = PSTViewsFactory.INSTANCE.createView(gp);
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("group")); //$NON-NLS-1$
	}

	@Test
	public void testCreateTextViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createText());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("text")); //$NON-NLS-1$
	}

	@Test
	public void testCreateArcCircleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createCircleArc());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("arc")); //$NON-NLS-1$
	}

	@Test
	public void testCreateRectangleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createRectangle());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rect")); //$NON-NLS-1$
	}

	@Test
	public void testCreateSquareViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createSquare());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("square")); //$NON-NLS-1$
	}

	@Test
	public void testCreateEllipseViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createEllipse());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("ellipse")); //$NON-NLS-1$
	}

	@Test
	public void testCreateCircleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createCircle());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("circle")); //$NON-NLS-1$
	}

	@Test
	public void testCreateGridViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createGrid(ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("grid")); //$NON-NLS-1$
	}

	@Test
	public void testCreateAxesViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createAxes(ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("axes")); //$NON-NLS-1$
	}

	@Test
	public void testCreatePolygonViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createPolygon());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("polygon")); //$NON-NLS-1$
	}

	@Test
	public void testCreatePolylineViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createPolyline());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("lines")); //$NON-NLS-1$
	}

	@Test
	public void testCreateTriangleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createTriangle());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("triangle")); //$NON-NLS-1$
	}

	@Test
	public void testCreateRhombusViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createRhombus());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rhombus")); //$NON-NLS-1$
	}

	@Test
	public void testCreateFreehandViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createFreeHand());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("freehand")); //$NON-NLS-1$
	}

	@Test
	public void testCreatePictureViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createPicture(ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("picture")); //$NON-NLS-1$
	}

	@Test
	public void testCreateDotViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createDot(ShapeFactory.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("dot")); //$NON-NLS-1$
	}

	@Test
	public void testCreateBezierCurveViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.createBezierCurve());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("beziercurve")); //$NON-NLS-1$
	}
}
