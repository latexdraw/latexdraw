package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.ui.TextAreaAutoSize;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.jfx.Canvas;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestEditingSelector extends TestLatexdrawGUI {
	EditingSelector selector;
	TextSetter textSetter;
	Pencil pencil;
	IDrawing drawing;
	Hand hand;

	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/EditingModes.fxml";
	}

	@Override
	protected Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(StatusBarController.class, Mockito.mock(StatusBarController.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
				bindToInstance(IDrawing.class, ShapeFactory.INST.createDrawing());
				bindToInstance(Hand.class, Mockito.mock(Hand.class));
				bindToInstance(Canvas.class, Mockito.mock(Canvas.class));
				bindAsEagerSingleton(EditingSelector.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(ShapeDeleter.class, Mockito.mock(ShapeDeleter.class));
				bindToInstance(CodeInserter.class, Mockito.mock(CodeInserter.class));
				bindToInstance(MagneticGrid.class, Mockito.mock(MagneticGrid.class));
				bindToInstance(ShapeAxesCustomiser.class, Mockito.mock(ShapeAxesCustomiser.class));
				bindToInstance(ShapeDoubleBorderCustomiser.class, Mockito.mock(ShapeDoubleBorderCustomiser.class));
				bindToInstance(ShapeFreeHandCustomiser.class, Mockito.mock(ShapeFreeHandCustomiser.class));
				bindToInstance(ShapeCoordDimCustomiser.class, Mockito.mock(ShapeCoordDimCustomiser.class));
				bindToInstance(ShapeShadowCustomiser.class, Mockito.mock(ShapeShadowCustomiser.class));
				bindToInstance(ShapeTransformer.class, Mockito.mock(ShapeTransformer.class));
				bindToInstance(ShapeGrouper.class, Mockito.mock(ShapeGrouper.class));
				bindToInstance(ShapePositioner.class, Mockito.mock(ShapePositioner.class));
				bindToInstance(ShapeRotationCustomiser.class, Mockito.mock(ShapeRotationCustomiser.class));
				bindToInstance(ShapeDotCustomiser.class, Mockito.mock(ShapeDotCustomiser.class));
				bindToInstance(ShapeBorderCustomiser.class, Mockito.mock(ShapeBorderCustomiser.class));
				bindToInstance(ShapeArcCustomiser.class, Mockito.mock(ShapeArcCustomiser.class));
				bindToInstance(ShapeFillingCustomiser.class, Mockito.mock(ShapeFillingCustomiser.class));
				bindToInstance(ShapeGridCustomiser.class, Mockito.mock(ShapeGridCustomiser.class));
				bindToInstance(ShapeArrowCustomiser.class, Mockito.mock(ShapeArrowCustomiser.class));
				bindToInstance(ShapeStdGridCustomiser.class, Mockito.mock(ShapeStdGridCustomiser.class));
			}
		};
	}

	@Before
	public void setUp() {
		selector = injector.getInstance(EditingSelector.class);
		textSetter = injector.getInstance(TextSetter.class);
		drawing = injector.getInstance(IDrawing.class);
		pencil = injector.getInstance(Pencil.class);
		hand = injector.getInstance(Hand.class);
		pencil.textSetter = textSetter;
		Mockito.when(injector.getInstance(Canvas.class).getDrawing()).thenReturn(drawing);
	}

	@Theory
	public void testClickShapeMode(final EditionChoice mode) {
		final Map<EditionChoice, String> modeToID = new HashMap<>();
		modeToID.put(EditionChoice.PLOT, "#plotB");
		modeToID.put(EditionChoice.PICTURE, "#picB");
		modeToID.put(EditionChoice.CIRCLE_ARC, "#arcB");
		modeToID.put(EditionChoice.TRIANGLE, "#triangleB");
		modeToID.put(EditionChoice.RHOMBUS, "#rhombusB");
		modeToID.put(EditionChoice.AXES, "#axesB");
		modeToID.put(EditionChoice.GRID, "#gridB");
		modeToID.put(EditionChoice.BEZIER_CURVE, "#bezierB");
		modeToID.put(EditionChoice.POLYGON, "#polygonB");
		modeToID.put(EditionChoice.LINES, "#linesB");
		modeToID.put(EditionChoice.CIRCLE, "#circleB");
		modeToID.put(EditionChoice.ELLIPSE, "#ellipseB");
		modeToID.put(EditionChoice.SQUARE, "#squareB");
		modeToID.put(EditionChoice.RECT, "#recB");
		modeToID.put(EditionChoice.FREE_HAND, "#freeHandB");
		modeToID.put(EditionChoice.TEXT, "#textB");
		modeToID.put(EditionChoice.DOT, "#dotB");

		clickOn(modeToID.get(mode));
		waitFXEvents.execute();
		Mockito.verify(pencil, Mockito.times(1)).setCurrentChoice(mode);
		// Do not want to write another test case to limit the number of GUI test (memory and time issues)
		if(mode != EditionChoice.TEXT) {
			Mockito.verify(textSetter, Mockito.times(1)).setActivated(false, false);
		}
		Mockito.verify(hand, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(injector.getInstance(Border.class), Mockito.times(1)).setActivated(false, false);
		Mockito.verify(injector.getInstance(ShapeDeleter.class), Mockito.times(1)).setActivated(false, false);
		Mockito.verify(pencil, Mockito.times(1)).setActivated(true);
		Mockito.verify(injector.getInstance(MetaShapeCustomiser.class), Mockito.times(1)).setActivated(true);
	}

	@Test
	public void testClickTextNoTextCauseNotActivated() {
		final TextAreaAutoSize textfield = Mockito.mock(TextAreaAutoSize.class);
		Mockito.when(textSetter.getTextField()).thenReturn(textfield);
		Mockito.when(textSetter.isActivated()).thenReturn(false);
		clickOn(selector.handB);
		waitFXEvents.execute();
		assertTrue(drawing.isEmpty());
	}

	@Test
	public void testClickTextNoTextCauseNoText() {
		final TextAreaAutoSize textfield = Mockito.mock(TextAreaAutoSize.class);
		Mockito.when(textSetter.getTextField()).thenReturn(textfield);
		Mockito.when(textSetter.isActivated()).thenReturn(true);
		Mockito.when(textfield.getText()).thenReturn("");
		clickOn(selector.handB);
		waitFXEvents.execute();
		assertTrue(drawing.isEmpty());
	}

	@Test
	public void testClickTextCreatesText() {
		final TextAreaAutoSize textfield = Mockito.mock(TextAreaAutoSize.class);
		Mockito.when(textSetter.getTextField()).thenReturn(textfield);
		Mockito.when(textSetter.isActivated()).thenReturn(true);
		Mockito.when(textfield.getText()).thenReturn("foo");

		clickOn(selector.handB);
		waitFXEvents.execute();
		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IText);
		assertEquals("foo", ((IText) drawing.getShapeAt(0)).getText());
	}

	@Test
	public void testClickHandActivationNoSelection() {
		clickOn(selector.handB);
		waitFXEvents.execute();
		Mockito.verify(pencil, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(injector.getInstance(Border.class), Mockito.times(1)).setActivated(false, false);
		Mockito.verify(injector.getInstance(MetaShapeCustomiser.class), Mockito.times(1)).setActivated(false, false);
	}

	@Test
	public void testClickHandActivationSelection() {
		drawing.getSelection().addShape(ShapeFactory.INST.createRectangle());
		clickOn(selector.handB);
		waitFXEvents.execute();
		Mockito.verify(injector.getInstance(ShapeDeleter.class), Mockito.times(1)).setActivated(true);
		Mockito.verify(pencil, Mockito.times(1)).setActivated(false, false);
		Mockito.verify(injector.getInstance(Border.class), Mockito.times(1)).setActivated(true);
		Mockito.verify(injector.getInstance(MetaShapeCustomiser.class), Mockito.times(1)).setActivated(true);
	}

	@Test
	public void testClickCodeInsert() {
		clickOn(selector.codeB);
		waitFXEvents.execute();
		Mockito.verify(injector.getInstance(Hand.class), Mockito.times(1)).setActivated(true);
	}
}
