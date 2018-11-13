package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.CompositeGUIVoidCommand;
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleLEFTARROWPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.LEFT_ARROW);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.BAR_END);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.BAR_IN);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.CIRCLE_END);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.CIRCLE_IN);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.DISK_END);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.DISK_IN);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.LEFT_DBLE_ARROW);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.LEFT_ROUND_BRACKET);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.LEFT_SQUARE_BRACKET);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		selectArrowLeftCB.execute(ArrowStyle.NONE);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.RIGHT_ARROW);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.RIGHT_DBLE_ARROW);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.RIGHT_ROUND_BRACKET);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.RIGHT_SQUARE_BRACKET);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.ROUND_IN);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.LEFT_ARROW);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.BAR_END);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.BAR_IN);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.CIRCLE_END);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.CIRCLE_IN);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.DISK_END);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.DISK_IN);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.LEFT_DBLE_ARROW);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.LEFT_ROUND_BRACKET);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.LEFT_SQUARE_BRACKET);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		selectArrowRightCB.execute(ArrowStyle.NONE);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.RIGHT_ARROW);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.RIGHT_DBLE_ARROW);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.RIGHT_ROUND_BRACKET);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.RIGHT_SQUARE_BRACKET);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.ROUND_IN);
		waitFXEvents.execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		selectArrowRightCB.execute(ArrowStyle.LEFT_ARROW);
		waitFXEvents.execute();
		selectArrowLeftCB.execute(ArrowStyle.BAR_END);
		waitFXEvents.execute();
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectArrowStyleDiskRBracketPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		selectArrowRightCB.execute(ArrowStyle.DISK_END);
		waitFXEvents.execute();
		selectArrowLeftCB.execute(ArrowStyle.LEFT_ROUND_BRACKET);
		waitFXEvents.execute();
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertTrue(rbracketPane.isVisible());
	}

	@Test
	public void testIncrementtbarsizeNumPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleRBrack), tbarsizeNum,
			incrementtbarsizeNum, Collections.singletonList(() -> ((ArrowableSingleShape) editing.createShapeInstance()).getTBarSizeNum()));
	}

	@Test
	public void testIncrementtbarsizeDimPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleRBrack), tbarsizeDim,
			incrementtbarsizeDim, Collections.singletonList(() -> ((ArrowableSingleShape) editing.createShapeInstance()).getTBarSizeDim()));
	}

	@Test
	public void testIncrementdotSizeNumPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleDot), dotSizeNum,
			incrementdotSizeNum, Collections.singletonList(() -> ((ArrowableSingleShape) editing.createShapeInstance()).getDotSizeNum()));
	}

	@Test
	public void testIncrementdotSizeDimPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleDot), dotSizeDim,
			incrementdotSizeDim, Collections.singletonList(() -> ((ArrowableSingleShape) editing.createShapeInstance()).getDotSizeDim()));
	}

	@Test
	public void testIncrementrbracketNumPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleRBrack), rbracketNum, incrementrbracketNum,
			Collections.singletonList(() -> ((ArrowableSingleShape) editing.createShapeInstance()).getRBracketNum()));
	}

	@Test
	public void testIncrementbracketNumPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleSBrack), bracketNum,
			incrementbracketNum, Collections.singletonList(() ->  ((ArrowableSingleShape) editing.createShapeInstance()).getBracketNum()));
	}

	@Test
	public void testIncrementarrowLengthPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow), arrowLength,
			incrementarrowLength, Collections.singletonList(() ->  ((ArrowableSingleShape) editing.createShapeInstance()).getArrowLength()));
	}

	@Test
	public void testIncrementarrowInsetPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow), arrowInset,
			incrementarrowInset, Collections.singletonList(() ->  ((ArrowableSingleShape) editing.createShapeInstance()).getArrowInset()));
	}

	@Test
	public void testIncrementarrowSizeNumPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow), arrowSizeNum,
			incrementarrowSizeNum, Collections.singletonList(() ->  ((ArrowableSingleShape) editing.createShapeInstance()).getArrowSizeNum()));
	}

	@Test
	public void testIncrementarrowSizeDimPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow), arrowSizeDim,
			incrementarrowSizeDim, Collections.singletonList(() ->  ((ArrowableSingleShape) editing.createShapeInstance()).getArrowSizeDim()));
	}
}
