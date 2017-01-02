package test.glib.views.pst;

import java.util.Optional;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.view.pst.PSTShapeView;
import net.sf.latexdraw.view.pst.PSTViewsFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestPSTViewFactory {
	@Test
	public void testCreateGroupViewPST() {
		IGroup gp = ShapeFactory.INST.createGroup();
		gp.addShape(ShapeFactory.INST.createText());
		Object view = PSTViewsFactory.INSTANCE.createView(gp);
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("group")); //$NON-NLS-1$
	}

	@Test
	public void testCreateTextViewPST() {
		Optional<PSTShapeView<?>> view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createText());
		assertNotNull(view);
		assertTrue(view.isPresent());
		assertTrue(view.getClass().getName().toLowerCase().contains("text")); //$NON-NLS-1$
	}

	@Test
	public void testCreateArcCircleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createCircleArc());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("arc")); //$NON-NLS-1$
	}

	@Test
	public void testCreateRectangleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createRectangle());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rect")); //$NON-NLS-1$
	}

	@Test
	public void testCreateSquareViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createSquare());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("square")); //$NON-NLS-1$
	}

	@Test
	public void testCreateEllipseViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createEllipse());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("ellipse")); //$NON-NLS-1$
	}

	@Test
	public void testCreateCircleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createCircle());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("circle")); //$NON-NLS-1$
	}

	@Test
	public void testCreateGridViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("grid")); //$NON-NLS-1$
	}

	@Test
	public void testCreateAxesViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("axes")); //$NON-NLS-1$
	}

	@Test
	public void testCreatePolygonViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createPolygon());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("polygon")); //$NON-NLS-1$
	}

	@Test
	public void testCreatePolylineViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createPolyline());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("lines")); //$NON-NLS-1$
	}

	@Test
	public void testCreateTriangleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createTriangle());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("triangle")); //$NON-NLS-1$
	}

	@Test
	public void testCreateRhombusViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createRhombus());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rhombus")); //$NON-NLS-1$
	}

	@Test
	public void testCreateFreehandViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createFreeHand());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("freehand")); //$NON-NLS-1$
	}

	@Test
	public void testCreatePictureViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("picture")); //$NON-NLS-1$
	}

	@Test
	public void testCreateDotViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("dot")); //$NON-NLS-1$
	}

	@Test
	public void testCreateBezierCurveViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(ShapeFactory.INST.createBezierCurve());
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("beziercurve")); //$NON-NLS-1$
	}
}
