package net.sf.latexdraw.instruments;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.ui.TextAreaAutoSize;
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
	TextSetter setter;
	TextAreaAutoSize textAutoSize;

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(Hand.class, Mockito.mock(Hand.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		setter = (TextSetter) injectorFactory.call(TextSetter.class);
		when(hand.isActivated()).thenReturn(false);
		when(setter.isActivated()).thenReturn(false);
		textAutoSize = new TextAreaAutoSize();
		when(setter.getTextField()).thenReturn(textAutoSize);
		pencil.setActivated(true);
		drawing = canvas.getDrawing();
		canvas.getMagneticGrid().setMagnetic(false);
		WaitForAsyncUtils.waitForFxEvents(100);
		canvas.toFront();
	}

	@Test
	public void testDrawRectangle() {
		pencil.setCurrentChoice(EditionChoice.RECT);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(100d, 200d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IRectangle);
		assertEquals(100d, drawing.getShapeAt(0).getWidth(), 0.00001d);
		assertEquals(200d, drawing.getShapeAt(0).getHeight(), 0.00001d);

		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), ((IRectangle) drawing.getShapeAt(0)).getPosition().getX(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), drawing.getShapeAt(0).getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawEllipse() {
		pencil.setCurrentChoice(EditionChoice.ELLIPSE);
		final Point2D pos = point(canvas).atOffset(-10d, -200d).query();
		drag(pos, MouseButton.PRIMARY).dropBy(100d, 200d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IEllipse);
		assertEquals(100d, drawing.getShapeAt(0).getWidth(), 0.00001d);
		assertEquals(200d, drawing.getShapeAt(0).getHeight(), 0.00001d);

		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), drawing.getShapeAt(0).getTopLeftPoint().getX(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), drawing.getShapeAt(0).getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawSquare() {
		pencil.setCurrentChoice(EditionChoice.SQUARE);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(101d, 11d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof ISquare);
		assertEquals(202d, drawing.getShapeAt(0).getWidth(), 0.00001d);
		assertEquals(202d, drawing.getShapeAt(0).getHeight(), 0.00001d);

		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX() - 101d, drawing.getShapeAt(0).getTopLeftPoint().getX(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY() - 101d, drawing.getShapeAt(0).getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawCircle() {
		pencil.setCurrentChoice(EditionChoice.CIRCLE);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(101d, 11d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof ICircle);
		assertEquals(202d, drawing.getShapeAt(0).getWidth(), 0.00001d);
		assertEquals(202d, drawing.getShapeAt(0).getHeight(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX() - 101d, drawing.getShapeAt(0).getTopLeftPoint().getX(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY() - 101d, drawing.getShapeAt(0).getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawCircleArc() {
		pencil.setCurrentChoice(EditionChoice.CIRCLE_ARC);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(101d, 11d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof ICircleArc);
		assertEquals(202d, drawing.getShapeAt(0).getWidth(), 0.00001d);
		assertEquals(202d, drawing.getShapeAt(0).getHeight(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX() - 101d, drawing.getShapeAt(0).getTopLeftPoint().getX(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY() - 101d, drawing.getShapeAt(0).getTopLeftPoint().getY(), 0.00001d);
		assertEquals(ArcStyle.ARC, ((ICircleArc) drawing.getShapeAt(0)).getArcStyle());
	}

	@Test
	public void testDrawFreeHand() {
		pencil.setCurrentChoice(EditionChoice.FREE_HAND);
		pencil.getGroupParams().setInterval(1);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(100d, 200d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IFreehand);
		assertEquals(100d, drawing.getShapeAt(0).getWidth(), 1d);
		assertEquals(200d, drawing.getShapeAt(0).getHeight(), 1d);

		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), drawing.getShapeAt(0).getTopLeftPoint().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), drawing.getShapeAt(0).getTopLeftPoint().getY(), 1d);
	}

	@Test
	public void testDrawPolygon() {
		pencil.setCurrentChoice(EditionChoice.POLYGON);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).moveBy(-20d, -100d).clickOn(MouseButton.PRIMARY).moveBy(-100d, 50d).clickOn(MouseButton.SECONDARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IPolygon);
		final IPolygon sh = (IPolygon) drawing.getShapeAt(0);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPtAt(0).getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPtAt(0).getY(), 1d);
		assertEquals(3, sh.getNbPoints());
	}

	@Test
	public void testDrawPolylines() {
		pencil.setCurrentChoice(EditionChoice.LINES);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).moveBy(-20d, -100d).clickOn(MouseButton.PRIMARY).moveBy(-100d, 50d).clickOn(MouseButton.SECONDARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IPolyline);
		final IPolyline sh = (IPolyline) drawing.getShapeAt(0);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPtAt(0).getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPtAt(0).getY(), 1d);
		assertEquals(3, sh.getNbPoints());
	}

	@Test
	public void testDrawBezierCurve() {
		pencil.setCurrentChoice(EditionChoice.BEZIER_CURVE);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).moveBy(-20d, -100d).clickOn(MouseButton.PRIMARY).moveBy(-100d, 50d).clickOn(MouseButton.SECONDARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IBezierCurve);
		final IBezierCurve sh = (IBezierCurve) drawing.getShapeAt(0);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPtAt(0).getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPtAt(0).getY(), 1d);
		assertTrue(sh.isOpened());
		assertEquals(3, sh.getNbPoints());
	}

	@Test
	public void testDrawDot() {
		pencil.setCurrentChoice(EditionChoice.DOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IDot);
		final IDot sh = (IDot) drawing.getShapeAt(0);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testDrawGrid() {
		pencil.setCurrentChoice(EditionChoice.GRID);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IGrid);
		final IGrid sh = (IGrid) drawing.getShapeAt(0);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testDrawAxes() {
		pencil.setCurrentChoice(EditionChoice.AXES);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IAxes);
		final IAxes sh = (IAxes) drawing.getShapeAt(0);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testDrawText() {
		pencil.setCurrentChoice(EditionChoice.TEXT);
		when(setter.isActivated()).thenReturn(true);
		textAutoSize.setText("foo");
		final Point2D pos = point(canvas).query();
		when(setter.getPosition()).thenReturn(
			ShapeFactory.INST.createPoint(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(),-Canvas.getMargins() + canvas.screenToLocal(pos).getY()));
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IText);
		final IText sh = (IText) drawing.getShapeAt(0);
		assertEquals("foo", sh.getText());
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testDrawPicture() {
		final FileChooser c = Mockito.mock(FileChooser.class);
		final URL resource = getClass().getResource("/LaTeXDrawSmall.png");

		pencil.setCurrentChoice(EditionChoice.PICTURE);
		when(c.showOpenDialog(Mockito.any())).thenReturn(new File(resource.getPath()));
		pencil.setPictureFileChooser(c);
		when(setter.isActivated()).thenReturn(true);
		textAutoSize.setText("foo");
		final Point2D pos = point(canvas).query();
		when(setter.getPosition()).thenReturn(ShapeFactory.INST.createPoint(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(),
			-Canvas.getMargins() + canvas.screenToLocal(pos).getY()));
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IPicture);
		final IPicture sh = (IPicture) drawing.getShapeAt(0);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testPictureDialogueOK() {
		assertFalse(pencil.getPictureFileChooser().getExtensionFilters().isEmpty());
	}

	@Test
	public void testNotCrashDeactivate() {
		setter.setActivated(true);
		setter.setActivated(false);
		setter.setActivated(true);
	}
}
