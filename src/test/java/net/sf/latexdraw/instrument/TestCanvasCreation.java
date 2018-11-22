package net.sf.latexdraw.instrument;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.CircleArc;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.service.PreferencesService;
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
	Drawing drawing;
	TextSetter setter;
	TextAreaAutoSize textAutoSize;

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(Hand.class, Mockito.mock(Hand.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		setter = injector.getInstance(TextSetter.class);
		when(hand.isActivated()).thenReturn(false);
		when(setter.isActivated()).thenReturn(false);
		textAutoSize = new TextAreaAutoSize();
		when(setter.getTextField()).thenReturn(textAutoSize);
		pencil.setActivated(true);
		drawing = canvas.getDrawing();
		injector.getInstance(PreferencesService.class).magneticGridProperty().set(false);
		WaitForAsyncUtils.waitForFxEvents(100);
		canvas.toFront();
	}

	@Test
	public void testDrawRectangle() {
		editing.setCurrentChoice(EditionChoice.RECT);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(100d, 200d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Rectangle);
		assertEquals(100d, drawing.getShapeAt(0).orElseThrow().getWidth(), 0.00001d);
		assertEquals(200d, drawing.getShapeAt(0).orElseThrow().getHeight(), 0.00001d);

		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), ((Rectangle) drawing.getShapeAt(0).orElseThrow()).getPosition().getX(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), drawing.getShapeAt(0).orElseThrow().getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawEllipse() {
		editing.setCurrentChoice(EditionChoice.ELLIPSE);
		final Point2D pos = point(canvas).atOffset(-10d, -200d).query();
		drag(pos, MouseButton.PRIMARY).dropBy(100d, 200d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Ellipse);
		assertEquals(100d, drawing.getShapeAt(0).orElseThrow().getWidth(), 0.00001d);
		assertEquals(200d, drawing.getShapeAt(0).orElseThrow().getHeight(), 0.00001d);

		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), drawing.getShapeAt(0).orElseThrow().getTopLeftPoint().getX(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), drawing.getShapeAt(0).orElseThrow().getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawSquare() {
		editing.setCurrentChoice(EditionChoice.SQUARE);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(101d, 11d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Square);
		assertEquals(202d, drawing.getShapeAt(0).orElseThrow().getWidth(), 0.00001d);
		assertEquals(202d, drawing.getShapeAt(0).orElseThrow().getHeight(), 0.00001d);

		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX() - 101d, drawing.getShapeAt(0).orElseThrow().getTopLeftPoint().getX(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY() - 101d, drawing.getShapeAt(0).orElseThrow().getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawCircle() {
		editing.setCurrentChoice(EditionChoice.CIRCLE);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(101d, 11d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Circle);
		assertEquals(202d, drawing.getShapeAt(0).orElseThrow().getWidth(), 0.00001d);
		assertEquals(202d, drawing.getShapeAt(0).orElseThrow().getHeight(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX() - 101d, drawing.getShapeAt(0).orElseThrow().getTopLeftPoint().getX(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY() - 101d, drawing.getShapeAt(0).orElseThrow().getTopLeftPoint().getY(), 0.00001d);
	}

	@Test
	public void testDrawCircleArc() {
		editing.setCurrentChoice(EditionChoice.CIRCLE_ARC);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(101d, 11d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof CircleArc);
		assertEquals(202d, drawing.getShapeAt(0).orElseThrow().getWidth(), 0.00001d);
		assertEquals(202d, drawing.getShapeAt(0).orElseThrow().getHeight(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX() - 101d, drawing.getShapeAt(0).orElseThrow().getTopLeftPoint().getX(), 0.00001d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY() - 101d, drawing.getShapeAt(0).orElseThrow().getTopLeftPoint().getY(), 0.00001d);
		assertEquals(ArcStyle.ARC, ((CircleArc) drawing.getShapeAt(0).orElseThrow()).getArcStyle());
	}

	@Test
	public void testDrawFreeHand() {
		editing.setCurrentChoice(EditionChoice.FREE_HAND);
		editing.getGroupParams().setInterval(1);
		final Point2D pos = point(canvas).query();
		drag(pos, MouseButton.PRIMARY).dropBy(100d, 200d);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Freehand);
		assertEquals(100d, drawing.getShapeAt(0).orElseThrow().getWidth(), 1d);
		assertEquals(200d, drawing.getShapeAt(0).orElseThrow().getHeight(), 1d);

		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), drawing.getShapeAt(0).orElseThrow().getTopLeftPoint().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), drawing.getShapeAt(0).orElseThrow().getTopLeftPoint().getY(), 1d);
	}

	@Test
	public void testDrawPolygon() {
		editing.setCurrentChoice(EditionChoice.POLYGON);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).moveBy(-20d, -100d).clickOn(MouseButton.PRIMARY).moveBy(-100d, 50d).clickOn(MouseButton.SECONDARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Polygon);
		final Polygon sh = (Polygon) drawing.getShapeAt(0).orElseThrow();
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPtAt(0).getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPtAt(0).getY(), 1d);
		assertEquals(3, sh.getNbPoints());
	}

	@Test
	public void testDrawPolylines() {
		editing.setCurrentChoice(EditionChoice.LINES);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).moveBy(-20d, -100d).clickOn(MouseButton.PRIMARY).moveBy(-100d, 50d).clickOn(MouseButton.SECONDARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Polyline);
		final Polyline sh = (Polyline) drawing.getShapeAt(0).orElseThrow();
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPtAt(0).getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPtAt(0).getY(), 1d);
		assertEquals(3, sh.getNbPoints());
	}

	@Test
	public void testDrawBezierCurve() {
		editing.setCurrentChoice(EditionChoice.BEZIER_CURVE);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY).moveBy(-20d, -100d).clickOn(MouseButton.PRIMARY).moveBy(-100d, 50d).clickOn(MouseButton.SECONDARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof BezierCurve);
		final BezierCurve sh = (BezierCurve) drawing.getShapeAt(0).orElseThrow();
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPtAt(0).getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPtAt(0).getY(), 1d);
		assertTrue(sh.isOpened());
		assertEquals(3, sh.getNbPoints());
	}

	@Test
	public void testDrawDot() {
		editing.setCurrentChoice(EditionChoice.DOT);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Dot);
		final Dot sh = (Dot) drawing.getShapeAt(0).orElseThrow();
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testDrawGrid() {
		editing.setCurrentChoice(EditionChoice.GRID);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Grid);
		final Grid sh = (Grid) drawing.getShapeAt(0).orElseThrow();
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testDrawAxes() {
		editing.setCurrentChoice(EditionChoice.AXES);
		final Point2D pos = point(canvas).query();
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Axes);
		final Axes sh = (Axes) drawing.getShapeAt(0).orElseThrow();
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testDrawText() {
		editing.setCurrentChoice(EditionChoice.TEXT);
		when(setter.isActivated()).thenReturn(true);
		textAutoSize.setText("gridGapProp");
		final Point2D pos = point(canvas).query();
		when(setter.getPosition()).thenReturn(
			ShapeFactory.INST.createPoint(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), -Canvas.getMargins() + canvas.screenToLocal(pos).getY()));
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Text);
		final Text sh = (Text) drawing.getShapeAt(0).orElseThrow();
		assertEquals("gridGapProp", sh.getText());
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(), sh.getPosition().getX(), 1d);
		assertEquals(-Canvas.getMargins() + canvas.screenToLocal(pos).getY(), sh.getPosition().getY(), 1d);
	}

	@Test
	public void testDrawPicture() {
		final FileChooser c = Mockito.mock(FileChooser.class);
		final URL resource = getClass().getResource("/LaTeXDrawSmall.png");

		editing.setCurrentChoice(EditionChoice.PICTURE);
		when(c.showOpenDialog(Mockito.any())).thenReturn(new File(resource.getPath()));
		pencil.setPictureFileChooser(c);
		when(setter.isActivated()).thenReturn(true);
		textAutoSize.setText("gridGapProp");
		final Point2D pos = point(canvas).query();
		when(setter.getPosition()).thenReturn(ShapeFactory.INST.createPoint(-Canvas.getMargins() + canvas.screenToLocal(pos).getX(),
			-Canvas.getMargins() + canvas.screenToLocal(pos).getY()));
		moveTo(pos).clickOn(MouseButton.PRIMARY);
		waitFXEvents.execute();

		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Picture);
		final Picture sh = (Picture) drawing.getShapeAt(0).orElseThrow();
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
