package test.glib.views.pst;

import static org.junit.Assert.*;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShapeFactory;
import net.sf.latexdraw.glib.views.pst.PSTViewsFactory;

import org.junit.Before;

public class TestPSTViewFactory {
	protected IShapeFactory shFac;

	static {
		DrawingTK.setFactory(new LShapeFactory());
	}


	@Before
	public void setUp() {
		shFac = DrawingTK.getFactory();
	}



	public void testCreateGroupViewPST() {
		IGroup gp = shFac.createGroup(false);
		gp.addShape(shFac.createText(false));
		Object view = PSTViewsFactory.INSTANCE.createView(gp);
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("group"));
	}


	public void testCreateTextViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createText(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("text"));
	}


	public void testCreateArcCircleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createCircleArc(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("arc"));
	}

	public void testCreateRectangleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createRectangle(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rect"));
	}

	public void testCreateSquareViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createSquare(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rect"));
	}

	public void testCreateEllipseViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createEllipse(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("ellipse"));
	}

	public void testCreateCircleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createCircle(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("circle"));
	}

	public void testCreateGridViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createGrid(false, shFac.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("grid"));
	}

	public void testCreateAxesViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createAxes(false, shFac.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("axes"));
	}

	public void testCreatePolygonViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createPolygon(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("polygon"));
	}

	public void testCreatePolylineViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createPolyline(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("lines"));
	}

	public void testCreateTriangleViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createTriangle(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("triangle"));
	}

	public void testCreateRhombusViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createRhombus(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("rhombus"));
	}

	public void testCreateFreehandViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createFreeHand(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("freehand"));
	}

	public void testCreatePictureViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createPicture(false, shFac.createPoint()));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("picture"));
	}

	public void testCreateDotViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createDot(shFac.createPoint(), false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("dot"));
	}

	public void testCreateBezierCurveViewPST() {
		Object view = PSTViewsFactory.INSTANCE.createView(shFac.createBezierCurve(false));
		assertNotNull(view);
		assertTrue(view.getClass().getName().toLowerCase().contains("beziercurve"));
	}
}
