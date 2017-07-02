package test.gui.pencil;

import com.google.inject.AbstractModule;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeArrowCustomiser;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestArrowStyleGUI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilArrowStyle extends TestArrowStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapeArrowCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
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
	public void testSelectLeftArrowStyleLEFT_ARROWPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.LEFT_ARROW);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_ARROW, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleBAR_ENDPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.BAR_END);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.BAR_END, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleBAR_INPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.BAR_IN);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.BAR_IN, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleCIRCLE_ENDPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.CIRCLE_END);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.CIRCLE_END, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleCIRCLE_INPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.CIRCLE_IN);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.CIRCLE_IN, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleDISK_ENDPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.DISK_END);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.DISK_END, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleDISK_INPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.DISK_IN);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.DISK_IN, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleLEFT_DBLE_ARROWPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.LEFT_DBLE_ARROW);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_DBLE_ARROW, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleLEFT_ROUND_BRACKETPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.LEFT_ROUND_BRACKET);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_ROUND_BRACKET, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertTrue(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleLEFT_SQUARE_BRACKETPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.LEFT_SQUARE_BRACKET);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
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
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.NONE, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleRIGHT_ARROWPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.RIGHT_ARROW);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_ARROW, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleRIGHT_DBLE_ARROWPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.RIGHT_DBLE_ARROW);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleRIGHT_ROUND_BRACKETPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.RIGHT_ROUND_BRACKET);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertTrue(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleRIGHT_SQUARE_BRACKETPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.RIGHT_SQUARE_BRACKET);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertTrue(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleROUND_INPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.ROUND_IN);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.ROUND_IN, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleLEFT_ARROWPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.LEFT_ARROW);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_ARROW, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleBAR_ENDPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.BAR_END);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.BAR_END, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleBAR_INPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.BAR_IN);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.BAR_IN, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleCIRCLE_ENDPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.CIRCLE_END);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.CIRCLE_END, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleCIRCLE_INPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.CIRCLE_IN);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.CIRCLE_IN, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleDISK_ENDPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.DISK_END);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.DISK_END, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleDISK_INPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.DISK_IN);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.DISK_IN, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleLEFT_DBLE_ARROWPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.LEFT_DBLE_ARROW);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_DBLE_ARROW, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleLEFT_ROUND_BRACKETPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.LEFT_ROUND_BRACKET);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_ROUND_BRACKET, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertTrue(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleLEFT_SQUARE_BRACKETPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.LEFT_SQUARE_BRACKET);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
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
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.NONE, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleRIGHT_ARROWPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.RIGHT_ARROW);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_ARROW, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleRIGHT_DBLE_ARROWPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.RIGHT_DBLE_ARROW);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertTrue(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertFalse(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleRIGHT_ROUND_BRACKETPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.RIGHT_ROUND_BRACKET);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertTrue(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleRIGHT_SQUARE_BRACKETPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.RIGHT_SQUARE_BRACKET);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
		assertFalse(arrowPane.isVisible());
		assertFalse(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertTrue(bracketPane.isVisible());
		assertFalse(rbracketPane.isVisible());
	}

	@Test
	public void testSelectRightArrowStyleROUND_INPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.ROUND_IN);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.ROUND_IN, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowAt(-1).getArrowStyle());
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
		selectArrowLeftCB.execute(ArrowStyle.BAR_END);
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
		selectArrowLeftCB.execute(ArrowStyle.LEFT_ROUND_BRACKET);
		assertFalse(arrowPane.isVisible());
		assertTrue(dotPane.isVisible());
		assertTrue(barPane.isVisible());
		assertFalse(bracketPane.isVisible());
		assertTrue(rbracketPane.isVisible());
	}

	@Test
	public void testIncrementtbarsizeNumPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleRBrack).execute();
		double val = tbarsizeNum.getValue();
		incrementtbarsizeNum.execute();
		assertEquals(tbarsizeNum.getValue(), ((IArrowableSingleShape)pencil.createShapeInstance()).getTBarSizeNum(), 0.0001);
		assertNotEquals(val, tbarsizeNum.getValue(), 0.0001);
	}

	@Test
	public void testIncrementtbarsizeDimPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleRBrack).execute();
		double val = tbarsizeDim.getValue();
		incrementtbarsizeDim.execute();
		assertEquals(tbarsizeDim.getValue(), ((IArrowableSingleShape)pencil.createShapeInstance()).getTBarSizeDim(), 0.0001);
		assertNotEquals(val, tbarsizeDim.getValue(), 0.0001);
	}

	@Test
	public void testIncrementdotSizeNumPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleDot).execute();
		double val = dotSizeNum.getValue();
		incrementdotSizeNum.execute();
		assertEquals(dotSizeNum.getValue(), ((IArrowableSingleShape)pencil.createShapeInstance()).getDotSizeNum(), 0.0001);
		assertNotEquals(val, dotSizeNum.getValue(), 0.0001);
	}

	@Test
	public void testIncrementdotSizeDimPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleDot).execute();
		double val = dotSizeDim.getValue();
		incrementdotSizeDim.execute();
		assertEquals(dotSizeDim.getValue(), ((IArrowableSingleShape)pencil.createShapeInstance()).getDotSizeDim(), 0.0001);
		assertNotEquals(val, dotSizeDim.getValue(), 0.0001);
	}

	@Test
	public void testIncrementrbracketNumPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleRBrack).execute();
		double val = rbracketNum.getValue();
		incrementrbracketNum.execute();
		assertEquals(rbracketNum.getValue(), ((IArrowableSingleShape)pencil.createShapeInstance()).getRBracketNum(), 0.0001);
		assertNotEquals(val, rbracketNum.getValue(), 0.0001);
	}

	@Test
	public void testIncrementbracketNumPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleSBrack).execute();
		double val = bracketNum.getValue();
		incrementbracketNum.execute();
		assertEquals(bracketNum.getValue(), ((IArrowableSingleShape)pencil.createShapeInstance()).getBracketNum(), 0.0001);
		assertNotEquals(val, bracketNum.getValue(), 0.0001);
	}

	@Test
	public void testIncrementarrowLengthPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow).execute();
		double val = arrowLength.getValue();
		incrementarrowLength.execute();
		assertEquals(arrowLength.getValue(), ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowLength(), 0.0001);
		assertNotEquals(val, arrowLength.getValue(), 0.0001);
	}

	@Test
	public void testIncrementarrowInsetPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow).execute();
		double val = arrowInset.getValue();
		incrementarrowInset.execute();
		assertEquals(arrowInset.getValue(), ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowInset(), 0.0001);
		assertNotEquals(val, arrowInset.getValue(), 0.0001);
	}

	@Test
	public void testIncrementarrowSizeNumPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow).execute();
		double val = arrowSizeNum.getValue();
		incrementarrowSizeNum.execute();
		assertEquals(arrowSizeNum.getValue(), ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowSizeNum(), 0.0001);
		assertNotEquals(val, arrowSizeNum.getValue(), 0.0001);
	}

	@Test
	public void testIncrementarrowSizeDimPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectArrowStyleArrow).execute();
		double val = arrowSizeDim.getValue();
		incrementarrowSizeDim.execute();
		assertEquals(arrowSizeDim.getValue(), ((IArrowableSingleShape)pencil.createShapeInstance()).getArrowSizeDim(), 0.0001);
		assertNotEquals(val, arrowSizeDim.getValue(), 0.0001);
	}
}
