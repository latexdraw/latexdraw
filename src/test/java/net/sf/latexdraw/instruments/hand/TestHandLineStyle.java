package net.sf.latexdraw.instruments.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeBorderCustomiser;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestLineStyleGUI;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.prop.IClosableProp;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
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
public class TestHandLineStyle extends TestLineStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapeBorderCustomiser.class);
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
	public void testControllerActivatedWhenSelectionNotEmpty() {
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(thicknessField.isVisible());
		assertFalse(thicknessField.isDisabled());
		assertTrue(lineColButton.isVisible());
		assertFalse(lineColButton.isDisabled());
		assertTrue(lineCB.isVisible());
		assertFalse(lineCB.isDisabled());
		assertTrue(bordersPosCB.isVisible());
		assertFalse(bordersPosCB.isDisabled());
		assertTrue(frameArcField.isVisible());
		assertFalse(frameArcField.isDisabled());
		assertFalse(showPoints.isVisible());
		assertFalse(opened.isVisible());
	}

	@Test
	public void testOpenVisibleIfBezierAndActivated() {
		new CompositeGUIVoidCommand(selectionAddBezier, activateHand, updateIns).execute();
		assertTrue(opened.isVisible());
	}

	@Test
	public void testChangeFrameArcSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddRec, updateIns)), frameArcField,
			incrementFrameArc, Arrays.asList(
				() -> ((IRectangle) drawing.getSelection().getShapeAt(0)).getLineArc(),
				() -> ((IRectangle) drawing.getSelection().getShapeAt(1)).getLineArc()));
	}

	@Test
	public void testChangeThicknessSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddRec, updateIns)), thicknessField,
			incrementThickness, Arrays.asList(
				() -> drawing.getSelection().getShapeAt(0).getThickness(),
				() -> drawing.getSelection().getShapeAt(1).getThickness()));
	}

	@Test
	public void testSelectBorderPosSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddRec, updateIns).execute();
		final BorderPos style = bordersPosCB.getSelectionModel().getSelectedItem();
		selectBorderPos.execute();
		waitFXEvents.execute();
		final BorderPos newStyle = bordersPosCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, drawing.getSelection().getShapeAt(0).getBordersPosition());
		assertEquals(newStyle, drawing.getSelection().getShapeAt(1).getBordersPosition());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLineStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddRec, updateIns).execute();
		final LineStyle style = lineCB.getSelectionModel().getSelectedItem();
		selectLineStyle.execute();
		waitFXEvents.execute();
		final LineStyle newStyle = lineCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, drawing.getSelection().getShapeAt(0).getLineStyle());
		assertEquals(newStyle, drawing.getSelection().getShapeAt(1).getLineStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testCheckShowPointSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddBezier, selectionAddBezier, updateIns).execute();
		final boolean sel = showPoints.isSelected();
		checkShowPts.execute();
		waitFXEvents.execute();
		assertEquals(!sel, drawing.getSelection().getShapeAt(0).isShowPts());
		assertEquals(!sel, drawing.getSelection().getShapeAt(1).isShowPts());
	}

	@Test
	public void testCheckCloseOKSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddBezier, selectionAddFreehand, updateIns).execute();
		assertEquals(((IClosableProp) drawing.getSelection().getShapeAt(0)).isOpened(), opened.isSelected());
	}

	@Test
	public void testCheckCloseSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddBezier, selectionAddFreehand, updateIns).execute();
		final boolean isOpen = opened.isSelected();
		checkOpened.execute();
		waitFXEvents.execute();
		assertEquals(!isOpen, ((IClosableProp) drawing.getSelection().getShapeAt(0)).isOpened());
		assertEquals(!isOpen, ((IClosableProp) drawing.getSelection().getShapeAt(1)).isOpened());
	}

	@Test
	public void testPickLineColourSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddBezier, updateIns).execute();
		final Color col = lineColButton.getValue();
		pickLineCol.execute();
		waitFXEvents.execute();
		assertEquals(lineColButton.getValue(), drawing.getSelection().getShapeAt(0).getLineColour().toJFX());
		assertEquals(lineColButton.getValue(), drawing.getSelection().getShapeAt(1).getLineColour().toJFX());
		assertNotEquals(col, lineColButton.getValue());
	}
}
