package net.sf.latexdraw.instrument.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
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
public class TestHandArrowStyle extends TestArrowStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
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
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.BAR_IN);
		waitFXEvents.execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.BAR_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getArrowAt(0).getArrowStyle());
		assertEquals(newStyle, ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectRightArrowStyleHand() {
		new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.ROUND_IN);
		waitFXEvents.execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.ROUND_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getArrowAt(-1).getArrowStyle());
		assertEquals(newStyle, ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testIncrementtbarsizeNumHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack), tbarsizeNum,
			incrementtbarsizeNum, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getTBarSizeNum(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getTBarSizeNum()));
	}

	@Test
	public void testIncrementtbarsizeDimHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack), tbarsizeDim,
			incrementtbarsizeDim, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getTBarSizeDim(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getTBarSizeDim()));
	}

	@Test
	public void testIncrementdotSizeNumHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleDot), dotSizeNum,
			incrementdotSizeNum, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getDotSizeNum(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getDotSizeNum()));
	}

	@Test
	public void testIncrementdotSizeDimHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleDot), dotSizeDim,
			incrementdotSizeDim, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getDotSizeDim(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getDotSizeDim()));
	}

	@Test
	public void testIncrementrbracketNumHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack), rbracketNum,
			incrementrbracketNum, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getRBracketNum(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getRBracketNum()));
	}

	@Test
	public void testIncrementbracketNumHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleSBrack), bracketNum,
			incrementbracketNum, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getBracketNum(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getBracketNum()));
	}

	@Test
	public void testIncrementarrowLengthHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowLength,
			incrementarrowLength, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getArrowLength(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getArrowLength()));
	}

	@Test
	public void testIncrementarrowInsetHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowInset,
			incrementarrowInset, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getArrowInset(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getArrowInset()));
	}

	@Test
	public void testIncrementarrowSizeNumHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowSizeNum,
			incrementarrowSizeNum, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getArrowSizeNum(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getArrowSizeNum()));
	}

	@Test
	public void testIncrementarrowSizeDimHand() {
		doTestSpinner(new CompositeGUIVoidCommand(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowSizeDim,
			incrementarrowSizeDim, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0)).getArrowSizeDim(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1)).getArrowSizeDim()));
	}
}
