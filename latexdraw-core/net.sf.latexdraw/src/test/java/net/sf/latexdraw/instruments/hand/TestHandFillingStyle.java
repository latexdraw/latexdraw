package net.sf.latexdraw.instruments.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.scene.paint.Color;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestFillingStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeFillingCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
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
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapeFillingCustomiser.class);
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
		FillingStyle style = fillStyleCB.getSelectionModel().getSelectedItem();
		selectStyle.execute(FillingStyle.PLAIN);
		FillingStyle newStyle = fillStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, drawing.getSelection().getShapeAt(1).getFillingStyle());
		assertEquals(newStyle, drawing.getSelection().getShapeAt(2).getFillingStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testPickFillingColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectPlainStyle, updateIns).execute();
		Color col = fillColButton.getValue();
		pickfillCol.execute();
		assertEquals(fillColButton.getValue(), drawing.getSelection().getShapeAt(1).getFillingCol().toJFX());
		assertEquals(fillColButton.getValue(), drawing.getSelection().getShapeAt(2).getFillingCol().toJFX());
		assertNotEquals(col, fillColButton.getValue());
	}

	@Test
	public void testPickHatchingsColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectHatchingsStyle, updateIns).execute();
		Color col = hatchColButton.getValue();
		pickhatchCol.execute();
		assertEquals(hatchColButton.getValue(), drawing.getSelection().getShapeAt(1).getHatchingsCol().toJFX());
		assertEquals(hatchColButton.getValue(), drawing.getSelection().getShapeAt(2).getHatchingsCol().toJFX());
		assertNotEquals(col, hatchColButton.getValue());
	}

	@Test
	public void testPickGradStartColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectGradStyle, updateIns).execute();
		Color col = gradStartColButton.getValue();
		pickgradStartCol.execute();
		assertEquals(gradStartColButton.getValue(), drawing.getSelection().getShapeAt(1).getGradColStart().toJFX());
		assertEquals(gradStartColButton.getValue(), drawing.getSelection().getShapeAt(2).getGradColStart().toJFX());
		assertNotEquals(col, gradStartColButton.getValue());
	}

	@Test
	public void testPickGradEndColourHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, selectGradStyle, updateIns).execute();
		Color col = gradEndColButton.getValue();
		pickgradEndCol.execute();
		assertEquals(gradEndColButton.getValue(), drawing.getSelection().getShapeAt(1).getGradColEnd().toJFX());
		assertEquals(gradEndColButton.getValue(), drawing.getSelection().getShapeAt(2).getGradColEnd().toJFX());
		assertNotEquals(col, gradEndColButton.getValue());
	}

	@Test
	public void testIncrementGradMidHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectGradStyle), gradMidPtField,
			incrementgradMidPt, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(1).getGradMidPt(),
			() ->  drawing.getSelection().getShapeAt(2).getGradMidPt()));
	}

	@Test
	public void testIncrementGradAngleHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectGradStyle), gradAngleField,
			incrementgradAngle, Arrays.asList(
			() ->  Math.toDegrees(drawing.getSelection().getShapeAt(1).getGradAngle()),
			() ->  Math.toDegrees(drawing.getSelection().getShapeAt(2).getGradAngle())));
	}

	@Test
	public void testIncrementHatchAngleHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectHatchingsStyle), hatchAngleField,
			incrementhatchAngle, Arrays.asList(
			() ->  Math.toDegrees(drawing.getSelection().getShapeAt(1).getHatchingsAngle()),
			() ->  Math.toDegrees(drawing.getSelection().getShapeAt(2).getHatchingsAngle())));
	}

	@Test
	public void testIncrementHatchWidthHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectHatchingsStyle), hatchWidthField,
			incrementhatchWidth, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(1).getHatchingsWidth(),
			() ->  drawing.getSelection().getShapeAt(2).getHatchingsWidth()));
	}

	@Test
	public void testIncrementHatchSepHand() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddRec, selectionAddBezier, updateIns, selectHatchingsStyle), hatchSepField,
			incrementhatchSep, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(1).getHatchingsSep(),
			() ->  drawing.getSelection().getShapeAt(2).getHatchingsSep()));
	}
}
