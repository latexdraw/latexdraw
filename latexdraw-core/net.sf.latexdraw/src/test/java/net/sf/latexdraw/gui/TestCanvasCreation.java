package net.sf.latexdraw.gui;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import net.sf.latexdraw.instruments.EditionChoice;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.Canvas;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TestCanvasCreation extends BaseTestCanvas {
	IDrawing drawing;

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(Hand.class, Mockito.mock(Hand.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		pencil.setActivated(true);
		when(hand.isActivated()).thenReturn(false);
		drawing = canvas.getDrawing();
		canvas.getMagneticGrid().setMagnetic(false);
		WaitForAsyncUtils.waitForFxEvents(100);
		canvas.toFront();
	}

	@Test
	public void testDrawRectangle() {
		pencil.setCurrentChoice(EditionChoice.RECT);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(100d, 200d).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IRectangle);
		assertEquals(100d, drawing.getShapeAt(0).getWidth(), 0.00001d);
		assertEquals(200d, drawing.getShapeAt(0).getHeight(), 0.00001d);

		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX(), ((IRectangle) drawing.getShapeAt(0)).getPosition().getX(), 0.00001d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY(), drawing.getShapeAt(0).getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawEllipse() {
		pencil.setCurrentChoice(EditionChoice.ELLIPSE);
		final Point2D pos = point(canvas).atOffset(-10d, -200d).query();
		drag(pos, MouseButton.PRIMARY).dropBy(100d, 200d).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IEllipse);
		assertEquals(100d, drawing.getShapeAt(0).getWidth(), 0.00001d);
		assertEquals(200d, drawing.getShapeAt(0).getHeight(), 0.00001d);

		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX(), drawing.getShapeAt(0).getTopLeftPoint().getX(), 0.00001d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY(), drawing.getShapeAt(0).getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawSquare() {
		pencil.setCurrentChoice(EditionChoice.SQUARE);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(101d, 11d).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof ISquare);
		assertEquals(202d, drawing.getShapeAt(0).getWidth(), 0.00001d);
		assertEquals(202d, drawing.getShapeAt(0).getHeight(), 0.00001d);

		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX() - 101d, drawing.getShapeAt(0).getTopLeftPoint().getX(), 0.00001d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY() - 101d, drawing.getShapeAt(0).getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawCircle() {
		pencil.setCurrentChoice(EditionChoice.CIRCLE);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(101d, 11d).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof ICircle);
		assertEquals(202d, drawing.getShapeAt(0).getWidth(), 0.00001d);
		assertEquals(202d, drawing.getShapeAt(0).getHeight(), 0.00001d);

		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX() - 101d, drawing.getShapeAt(0).getTopLeftPoint().getX(), 0.00001d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY() - 101d, drawing.getShapeAt(0).getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawFreeHand() {
		pencil.setCurrentChoice(EditionChoice.FREE_HAND);
		pencil.getGroupParams().setInterval(1);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(100d, 200d).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IFreehand);
		assertEquals(100d, drawing.getShapeAt(0).getWidth(), 1d);
		assertEquals(200d, drawing.getShapeAt(0).getHeight(), 1d);

		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX(), drawing.getShapeAt(0).getTopLeftPoint().getX(), 1d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY(), drawing.getShapeAt(0).getTopLeftPoint().getY(), 1d);
	}

	@Test
	public void testDrawPolygon() {
		pencil.setCurrentChoice(EditionChoice.POLYGON);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).moveBy(-20d, -100d).clickOn(MouseButton.PRIMARY).moveBy(-100d, 50d).clickOn(MouseButton.SECONDARY).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IPolygon);
		final IPolygon sh = (IPolygon) drawing.getShapeAt(0);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX(), sh.getPtAt(0).getX(), 1d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY(), sh.getPtAt(0).getY(), 1d);
	}

	@Test
	public void testDrawPolylines() {
		pencil.setCurrentChoice(EditionChoice.LINES);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).moveBy(-20d, -100d).clickOn(MouseButton.PRIMARY).moveBy(-100d, 50d).clickOn(MouseButton.SECONDARY).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IPolyline);
		final IPolyline sh = (IPolyline) drawing.getShapeAt(0);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX(), sh.getPtAt(0).getX(), 1d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY(), sh.getPtAt(0).getY(), 1d);
	}

	@Test
	public void testDrawBezierCurve() {
		pencil.setCurrentChoice(EditionChoice.BEZIER_CURVE);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).moveBy(-20d, -100d).clickOn(MouseButton.PRIMARY).moveBy(-100d, 50d).clickOn(MouseButton.SECONDARY).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IBezierCurve);
		final IBezierCurve sh = (IBezierCurve) drawing.getShapeAt(0);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX(), sh.getPtAt(0).getX(), 1d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY(), sh.getPtAt(0).getY(), 1d);
		assertFalse(sh.isClosed());
	}

	@Test
	public void testDrawBezierCurveClose() {
		pencil.setCurrentChoice(EditionChoice.BEZIER_CURVE_CLOSED);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).moveBy(-20d, -100d).clickOn(MouseButton.PRIMARY).moveBy(-100d, 50d).clickOn(MouseButton.SECONDARY).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IBezierCurve);
		final IBezierCurve sh = (IBezierCurve) drawing.getShapeAt(0);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX(), sh.getPtAt(0).getX(), 1d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY(), sh.getPtAt(0).getY(), 1d);
		assertTrue(sh.isClosed());
	}

	@Test
	public void testDrawDot() {
		pencil.setCurrentChoice(EditionChoice.DOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IDot);
		final IDot sh = (IDot) drawing.getShapeAt(0);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testDrawGrid() {
		pencil.setCurrentChoice(EditionChoice.GRID);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IGrid);
		final IGrid sh = (IGrid) drawing.getShapeAt(0);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testDrawAxes() {
		pencil.setCurrentChoice(EditionChoice.AXES);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).sleep(SLEEP);

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IAxes);
		final IAxes sh = (IAxes) drawing.getShapeAt(0);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.MARGINS + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}
}
