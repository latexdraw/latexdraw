package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeArrowCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestArrowStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.ArrowableSingleShape;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilArrowStyle extends TestArrowStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				hand = mock(Hand.class);
				bindToInstance(Hand.class, hand);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Pencil.class);
				bindAsEagerSingleton(ShapeArrowCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleLEFTARROWPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.LEFT_ARROW)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_ARROW, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleBARENDPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.BAR_END)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.BAR_END, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleBARINPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.BAR_IN)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.BAR_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleCIRCLEENDPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.CIRCLE_END)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.CIRCLE_END, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleCIRCLEINPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.CIRCLE_IN)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.CIRCLE_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleDISKENDPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.DISK_END)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.DISK_END, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleDISKINPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.DISK_IN)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.DISK_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleLEFTDBLEARROWPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.LEFT_DBLE_ARROW)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_DBLE_ARROW, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleLEFTROUNDBRACKETPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.LEFT_ROUND_BRACKET)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_ROUND_BRACKET, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertTrue(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleLEFTSQUAREBRACKETPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.LEFT_SQUARE_BRACKET)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertTrue(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleNONEPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns, () -> selectArrowLeftCB.execute(ArrowStyle.NONE)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.NONE, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleRIGHTARROWPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.RIGHT_ARROW)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_ARROW, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleRIGHTDBLEARROWPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.RIGHT_DBLE_ARROW)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleRIGHTROUNDBRACKETPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.RIGHT_ROUND_BRACKET)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertTrue(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleRIGHTSQUAREBRACKETPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.RIGHT_SQUARE_BRACKET)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertTrue(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleROUNDINPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowLeftCB.execute(ArrowStyle.ROUND_IN)).execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.ROUND_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleLEFTARROWPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.LEFT_ARROW)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_ARROW, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleBARENDPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.BAR_END)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.BAR_END, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleBARINPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.BAR_IN)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.BAR_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleCIRCLEENDPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.CIRCLE_END)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.CIRCLE_END, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleCIRCLEINPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.CIRCLE_IN)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.CIRCLE_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleDISKENDPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.DISK_END)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.DISK_END, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleDISKINPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.DISK_IN)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.DISK_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleLEFTDBLEARROWPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.LEFT_DBLE_ARROW)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_DBLE_ARROW, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleLEFTROUNDBRACKETPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.LEFT_ROUND_BRACKET)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_ROUND_BRACKET, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertTrue(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleLEFTSQUAREBRACKETPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.LEFT_SQUARE_BRACKET)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertTrue(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleNONEPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns, () -> selectArrowRightCB.execute(ArrowStyle.NONE)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.NONE, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleRIGHTARROWPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.RIGHT_ARROW)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_ARROW, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleRIGHTDBLEARROWPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.RIGHT_DBLE_ARROW)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleRIGHTROUNDBRACKETPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.RIGHT_ROUND_BRACKET)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertTrue(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleRIGHTSQUAREBRACKETPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.RIGHT_SQUARE_BRACKET)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertTrue(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleROUNDINPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		Cmds.of(() -> selectArrowRightCB.execute(ArrowStyle.ROUND_IN)).execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.ROUND_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) editing.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectArrowStyleArrowBarPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns,
			() -> selectArrowRightCB.execute(ArrowStyle.LEFT_ARROW),
			() -> selectArrowLeftCB.execute(ArrowStyle.BAR_END)).execute();
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectArrowStyleDiskRBracketPencil() {
		Cmds.of(activatePencil, pencilCreatesBezier, updateIns,
			() -> selectArrowRightCB.execute(ArrowStyle.DISK_END),
			() -> selectArrowLeftCB.execute(ArrowStyle.LEFT_ROUND_BRACKET)).execute();
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertTrue(rbracketPane.isVisible());
	}

	@Test
	public void testIncrementtbarsizeNumPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleRBrack), tbarsizeNum,
			incrementtbarsizeNum, Collections.singletonList(() -> ((ArrowableSingleShape) editing.createShapeInstance()).getTBarSizeNum()));
	}

	@Test
	public void testIncrementtbarsizeDimPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleRBrack), tbarsizeDim,
			incrementtbarsizeDim, Collections.singletonList(() -> ((ArrowableSingleShape) editing.createShapeInstance()).getTBarSizeDim()));
	}

	@Test
	public void testIncrementdotSizeNumPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleDot), dotSizeNum,
			incrementdotSizeNum, Collections.singletonList(() -> ((ArrowableSingleShape) editing.createShapeInstance()).getDotSizeNum()));
	}

	@Test
	public void testIncrementdotSizeDimPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleDot), dotSizeDim,
			incrementdotSizeDim, Collections.singletonList(() -> ((ArrowableSingleShape) editing.createShapeInstance()).getDotSizeDim()));
	}

	@Test
	public void testIncrementrbracketNumPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleRBrack), rbracketNum, incrementrbracketNum,
			Collections.singletonList(() -> ((ArrowableSingleShape) editing.createShapeInstance()).getRBracketNum()));
	}

	@Test
	public void testIncrementbracketNumPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleSBrack), bracketNum,
			incrementbracketNum, Collections.singletonList(() ->  ((ArrowableSingleShape) editing.createShapeInstance()).getBracketNum()));
	}

	@Test
	public void testIncrementarrowLengthPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow), arrowLength,
			incrementarrowLength, Collections.singletonList(() ->  ((ArrowableSingleShape) editing.createShapeInstance()).getArrowLength()));
	}

	@Test
	public void testIncrementarrowInsetPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow), arrowInset,
			incrementarrowInset, Collections.singletonList(() ->  ((ArrowableSingleShape) editing.createShapeInstance()).getArrowInset()));
	}

	@Test
	public void testIncrementarrowSizeNumPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow), arrowSizeNum,
			incrementarrowSizeNum, Collections.singletonList(() ->  ((ArrowableSingleShape) editing.createShapeInstance()).getArrowSizeNum()));
	}

	@Test
	public void testIncrementarrowSizeDimPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow), arrowSizeDim,
			incrementarrowSizeDim, Collections.singletonList(() ->  ((ArrowableSingleShape) editing.createShapeInstance()).getArrowSizeDim()));
	}
}
