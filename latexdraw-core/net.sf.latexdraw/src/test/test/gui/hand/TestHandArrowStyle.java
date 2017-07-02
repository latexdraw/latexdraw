package test.gui.hand;

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
public class TestHandArrowStyle extends TestArrowStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				bind(ShapeArrowCustomiser.class).asEagerSingleton();
				bind(Hand.class).asEagerSingleton();
				bind(Pencil.class).toInstance(pencil);
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelectionArrowable() {
		new CompositeGUIVoidCommand(selectionAddBezier, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotArrowable() {
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns).execute();
		ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.BAR_IN);
		ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.BAR_IN, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getArrowAt(0).getArrowStyle());
		assertEquals(newStyle, ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectRightArrowStyleHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns).execute();
		ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.ROUND_IN);
		ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.ROUND_IN, newStyle);
		assertEquals(newStyle, ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getArrowAt(-1).getArrowStyle());
		assertEquals(newStyle, ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testIncrementtbarsizeNumHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack).execute();
		double val = tbarsizeNum.getValue();
		incrementtbarsizeNum.execute();
		assertEquals(tbarsizeNum.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getTBarSizeNum(), 0.001);
		assertEquals(tbarsizeNum.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getTBarSizeNum(), 0.001);
		assertNotEquals(val, tbarsizeNum.getValue(), 0.0001);
	}

	@Test
	public void testIncrementtbarsizeDimHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack).execute();
		double val = tbarsizeDim.getValue();
		incrementtbarsizeDim.execute();
		assertEquals(tbarsizeDim.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getTBarSizeDim(), 0.001);
		assertEquals(tbarsizeDim.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getTBarSizeDim(), 0.001);
		assertNotEquals(val, tbarsizeDim.getValue(), 0.0001);
	}

	@Test
	public void testIncrementdotSizeNumHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleDot).execute();
		double val = dotSizeNum.getValue();
		incrementdotSizeNum.execute();
		assertEquals(dotSizeNum.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getDotSizeNum(), 0.001);
		assertEquals(dotSizeNum.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getDotSizeNum(), 0.001);
		assertNotEquals(val, dotSizeNum.getValue(), 0.0001);
	}

	@Test
	public void testIncrementdotSizeDimHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleDot).execute();
		double val = dotSizeDim.getValue();
		incrementdotSizeDim.execute();
		assertEquals(dotSizeDim.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getDotSizeDim(), 0.001);
		assertEquals(dotSizeDim.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getDotSizeDim(), 0.001);
		assertNotEquals(val, dotSizeDim.getValue(), 0.0001);
	}

	@Test
	public void testIncrementrbracketNumHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack).execute();
		double val = rbracketNum.getValue();
		incrementrbracketNum.execute();
		assertEquals(rbracketNum.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getRBracketNum(), 0.001);
		assertEquals(rbracketNum.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getRBracketNum(), 0.001);
		assertNotEquals(val, rbracketNum.getValue(), 0.0001);
	}

	@Test
	public void testIncrementbracketNumHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleSBrack).execute();
		double val = bracketNum.getValue();
		incrementbracketNum.execute();
		assertEquals(bracketNum.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getBracketNum(), 0.001);
		assertEquals(bracketNum.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getBracketNum(), 0.001);
		assertNotEquals(val, bracketNum.getValue(), 0.0001);
	}

	@Test
	public void testIncrementarrowLengthHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow).execute();
		double val = arrowLength.getValue();
		incrementarrowLength.execute();
		assertEquals(arrowLength.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getArrowLength(), 0.001);
		assertEquals(arrowLength.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getArrowLength(), 0.001);
		assertNotEquals(val, arrowLength.getValue(), 0.0001);
	}

	@Test
	public void testIncrementarrowInsetHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow).execute();
		double val = arrowInset.getValue();
		incrementarrowInset.execute();
		assertEquals(arrowInset.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getArrowInset(), 0.001);
		assertEquals(arrowInset.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getArrowInset(), 0.001);
		assertNotEquals(val, arrowInset.getValue(), 0.0001);
	}

	@Test
	public void testIncrementarrowSizeNumHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow).execute();
		double val = arrowSizeNum.getValue();
		incrementarrowSizeNum.execute();
		assertEquals(arrowSizeNum.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getArrowSizeNum(), 0.001);
		assertEquals(arrowSizeNum.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getArrowSizeNum(), 0.001);
		assertNotEquals(val, arrowSizeNum.getValue(), 0.0001);
	}

	@Test
	public void testIncrementarrowSizeDimHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow).execute();
		double val = arrowSizeDim.getValue();
		incrementarrowSizeDim.execute();
		assertEquals(arrowSizeDim.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(0)).getArrowSizeDim(), 0.001);
		assertEquals(arrowSizeDim.getValue(), ((IArrowableSingleShape)drawing.getSelection().getShapeAt(1)).getArrowSizeDim(), 0.001);
		assertNotEquals(val, arrowSizeDim.getValue(), 0.0001);
	}
}
