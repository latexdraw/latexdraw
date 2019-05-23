package net.sf.latexdraw.instrument.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
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
public class TestHandArrowStyle extends TestArrowStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				bindToInstance(Pencil.class, pencil);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Hand.class);
				bindAsEagerSingleton(ShapeArrowCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		Cmds.of(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelectionArrowable() {
		Cmds.of(selectionAddBezier, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotArrowable() {
		Cmds.of(selectionAddRec, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		Cmds.of(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectLeftArrowStyleHand() {
		Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns).execute();
		final ArrowStyle style = arrowLeftCB.getSelectionModel().getSelectedItem();
		selectArrowLeftCB.execute(ArrowStyle.BAR_IN);
		waitFXEvents.execute();
		final ArrowStyle newStyle = arrowLeftCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.BAR_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getArrowAt(0).getArrowStyle());
		assertEquals(newStyle, ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getArrowAt(0).getArrowStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectRightArrowStyleHand() {
		Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns).execute();
		final ArrowStyle style = arrowRightCB.getSelectionModel().getSelectedItem();
		selectArrowRightCB.execute(ArrowStyle.ROUND_IN);
		waitFXEvents.execute();
		final ArrowStyle newStyle = arrowRightCB.getSelectionModel().getSelectedItem();
		assertEquals(ArrowStyle.ROUND_IN, newStyle);
		assertEquals(newStyle, ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getArrowAt(-1).getArrowStyle());
		assertEquals(newStyle, ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getArrowAt(-1).getArrowStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testIncrementtbarsizeNumHand() {
		doTestSpinner(Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack), tbarsizeNum,
			incrementtbarsizeNum, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getTBarSizeNum(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getTBarSizeNum()));
	}

	@Test
	public void testIncrementtbarsizeDimHand() {
		doTestSpinner(Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack), tbarsizeDim,
			incrementtbarsizeDim, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getTBarSizeDim(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getTBarSizeDim()));
	}

	@Test
	public void testIncrementdotSizeNumHand() {
		doTestSpinner(Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleDot), dotSizeNum,
			incrementdotSizeNum, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getDotSizeNum(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getDotSizeNum()));
	}

	@Test
	public void testIncrementdotSizeDimHand() {
		doTestSpinner(Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleDot), dotSizeDim,
			incrementdotSizeDim, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getDotSizeDim(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getDotSizeDim()));
	}

	@Test
	public void testIncrementrbracketNumHand() {
		doTestSpinner(Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleRBrack), rbracketNum,
			incrementrbracketNum, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getRBracketNum(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getRBracketNum()));
	}

	@Test
	public void testIncrementbracketNumHand() {
		doTestSpinner(Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleSBrack), bracketNum,
			incrementbracketNum, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getBracketNum(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getBracketNum()));
	}

	@Test
	public void testIncrementarrowLengthHand() {
		doTestSpinner(Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowLength,
			incrementarrowLength, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getArrowLength(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getArrowLength()));
	}

	@Test
	public void testIncrementarrowInsetHand() {
		doTestSpinner(Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowInset,
			incrementarrowInset, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getArrowInset(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getArrowInset()));
	}

	@Test
	public void testIncrementarrowSizeNumHand() {
		doTestSpinner(Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowSizeNum,
			incrementarrowSizeNum, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getArrowSizeNum(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getArrowSizeNum()));
	}

	@Test
	public void testIncrementarrowSizeDimHand() {
		doTestSpinner(Cmds.of(selectionAddBezier, selectionAddBezier, activateHand, updateIns, selectArrowStyleArrow), arrowSizeDim,
			incrementarrowSizeDim, Arrays.asList(
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(0).orElseThrow()).getArrowSizeDim(),
			() ->  ((ArrowableSingleShape) drawing.getSelection().getShapeAt(1).orElseThrow()).getArrowSizeDim()));
	}
}
