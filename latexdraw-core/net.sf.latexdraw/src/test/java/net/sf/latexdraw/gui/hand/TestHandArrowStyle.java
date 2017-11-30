package net.sf.latexdraw.gui.hand;

import java.util.Arrays;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestArrowStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeArrowCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
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
public class TestHandArrowStyle extends TestArrowStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapeArrowCustomiser.class);
				bindAsEagerSingleton(Hand.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Pencil.class, pencil);
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
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack), tbarsizeNum,
			incrementtbarsizeNum, Arrays.asList(
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getTBarSizeNum(),
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getTBarSizeNum()));
	}

	@Test
	public void testIncrementtbarsizeDimHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack), tbarsizeDim,
			incrementtbarsizeDim, Arrays.asList(
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getTBarSizeDim(),
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getTBarSizeDim()));
	}

	@Test
	public void testIncrementdotSizeNumHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleDot), dotSizeNum,
			incrementdotSizeNum, Arrays.asList(
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getDotSizeNum(),
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getDotSizeNum()));
	}

	@Test
	public void testIncrementdotSizeDimHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleDot), dotSizeDim,
			incrementdotSizeDim, Arrays.asList(
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getDotSizeDim(),
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getDotSizeDim()));
	}

	@Test
	public void testIncrementrbracketNumHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack), rbracketNum,
			incrementrbracketNum, Arrays.asList(
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getRBracketNum(),
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getRBracketNum()));
	}

	@Test
	public void testIncrementbracketNumHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleSBrack), bracketNum,
			incrementbracketNum, Arrays.asList(
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getBracketNum(),
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getBracketNum()));
	}

	@Test
	public void testIncrementarrowLengthHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowLength,
			incrementarrowLength, Arrays.asList(
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getArrowLength(),
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getArrowLength()));
	}

	@Test
	public void testIncrementarrowInsetHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowInset,
			incrementarrowInset, Arrays.asList(
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getArrowInset(),
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getArrowInset()));
	}

	@Test
	public void testIncrementarrowSizeNumHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowSizeNum,
			incrementarrowSizeNum, Arrays.asList(
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getArrowSizeNum(),
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getArrowSizeNum()));
	}

	@Test
	public void testIncrementarrowSizeDimHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowSizeDim,
			incrementarrowSizeDim, Arrays.asList(
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getArrowSizeDim(),
			() ->  ((IArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getArrowSizeDim()));
	}
}
