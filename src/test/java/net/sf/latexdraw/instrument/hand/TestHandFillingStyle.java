package net.sf.latexdraw.instrument.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.CompositeGUIVoidCommand;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeFillingCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestFillingStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.shape.FillingStyle;
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
public class TestHandFillingStyle extends TestFillingStyleGUI {
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
				bindAsEagerSingleton(ShapeFillingCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelection() {
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotFillable() {
		new CompositeGUIVoidCommand(selectionAddAxes, activateHand, updateIns).execute();
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
	public void testNotFillingWidgetsNotEnabledHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectGradStyle, updateIns).execute();
		assertFalse(fillColButton.getParent().isVisible());
	}

	@Test
	public void testNotGradWidgetsNotEnabledHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectHatchingsStyle, updateIns).execute();
		assertFalse(gradAngleField.getParent().isVisible());
	}

	@Test
	public void testNotHatchWidgetsNotEnabledHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectGradStyle, updateIns).execute();
		assertFalse(hatchAngleField.getParent().isVisible());
	}

	@Test
	public void testSelectFillingPlainHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns).execute();
		final FillingStyle style = fillStyleCB.getSelectionModel().getSelectedItem();
		selectStyle.execute(FillingStyle.PLAIN);
		waitFXEvents.execute();
		final FillingStyle newStyle = fillStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, drawing.getSelection().getShapeAt(1).orElseThrow().getFillingStyle());
		assertEquals(newStyle, drawing.getSelection().getShapeAt(2).orElseThrow().getFillingStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testPickFillingColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectPlainStyle, updateIns).execute();
		final Color col = fillColButton.getValue();
		pickfillCol.execute();
		waitFXEvents.execute();
		assertEquals(fillColButton.getValue(), drawing.getSelection().getShapeAt(1).orElseThrow().getFillingCol().toJFX());
		assertEquals(fillColButton.getValue(), drawing.getSelection().getShapeAt(2).orElseThrow().getFillingCol().toJFX());
		assertNotEquals(col, fillColButton.getValue());
	}

	@Test
	public void testPickHatchingsColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectHatchingsStyle, updateIns).execute();
		final Color col = hatchColButton.getValue();
		pickhatchCol.execute();
		waitFXEvents.execute();
		assertEquals(hatchColButton.getValue(), drawing.getSelection().getShapeAt(1).orElseThrow().getHatchingsCol().toJFX());
		assertEquals(hatchColButton.getValue(), drawing.getSelection().getShapeAt(2).orElseThrow().getHatchingsCol().toJFX());
		assertNotEquals(col, hatchColButton.getValue());
	}

	@Test
	public void testPickGradStartColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectGradStyle, updateIns).execute();
		final Color col = gradStartColButton.getValue();
		pickgradStartCol.execute();
		waitFXEvents.execute();
		assertEquals(gradStartColButton.getValue(), drawing.getSelection().getShapeAt(1).orElseThrow().getGradColStart().toJFX());
		assertEquals(gradStartColButton.getValue(), drawing.getSelection().getShapeAt(2).orElseThrow().getGradColStart().toJFX());
		assertNotEquals(col, gradStartColButton.getValue());
	}

	@Test
	public void testPickGradEndColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectGradStyle, updateIns).execute();
		final Color col = gradEndColButton.getValue();
		pickgradEndCol.execute();
		waitFXEvents.execute();
		assertEquals(gradEndColButton.getValue(), drawing.getSelection().getShapeAt(1).orElseThrow().getGradColEnd().toJFX());
		assertEquals(gradEndColButton.getValue(), drawing.getSelection().getShapeAt(2).orElseThrow().getGradColEnd().toJFX());
		assertNotEquals(col, gradEndColButton.getValue());
	}

	@Test
	public void testIncrementGradMidHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectGradStyle), gradMidPtField,
			incrementgradMidPt, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(1).orElseThrow().getGradMidPt(),
			() ->  drawing.getSelection().getShapeAt(2).orElseThrow().getGradMidPt()));
	}

	@Test
	public void testIncrementGradAngleHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectGradStyle), gradAngleField,
			incrementgradAngle, Arrays.asList(
			() ->  Math.toDegrees(drawing.getSelection().getShapeAt(1).orElseThrow().getGradAngle()),
			() ->  Math.toDegrees(drawing.getSelection().getShapeAt(2).orElseThrow().getGradAngle())));
	}

	@Test
	public void testIncrementHatchAngleHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectHatchingsStyle), hatchAngleField,
			incrementhatchAngle, Arrays.asList(
			() ->  Math.toDegrees(drawing.getSelection().getShapeAt(1).orElseThrow().getHatchingsAngle()),
			() ->  Math.toDegrees(drawing.getSelection().getShapeAt(2).orElseThrow().getHatchingsAngle())));
	}

	@Test
	public void testIncrementHatchWidthHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectHatchingsStyle), hatchWidthField,
			incrementhatchWidth, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(1).orElseThrow().getHatchingsWidth(),
			() ->  drawing.getSelection().getShapeAt(2).orElseThrow().getHatchingsWidth()));
	}

	@Test
	public void testIncrementHatchSepHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectHatchingsStyle), hatchSepField,
			incrementhatchSep, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(1).orElseThrow().getHatchingsSep(),
			() ->  drawing.getSelection().getShapeAt(2).orElseThrow().getHatchingsSep()));
	}
}
