package net.sf.latexdraw.gui.hand;

import java.util.Arrays;
import javafx.scene.paint.Color;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestLineStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeBorderCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
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
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
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
		BorderPos style = bordersPosCB.getSelectionModel().getSelectedItem();
		selectBorderPos.execute();
		BorderPos newStyle = bordersPosCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, drawing.getSelection().getShapeAt(0).getBordersPosition());
		assertEquals(newStyle, drawing.getSelection().getShapeAt(1).getBordersPosition());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLineStyleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddRec, updateIns).execute();
		LineStyle style = lineCB.getSelectionModel().getSelectedItem();
		selectLineStyle.execute();
		LineStyle newStyle = lineCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, drawing.getSelection().getShapeAt(0).getLineStyle());
		assertEquals(newStyle, drawing.getSelection().getShapeAt(1).getLineStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testCheckShowPointSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddBezier, selectionAddBezier, updateIns).execute();
		boolean sel = showPoints.isSelected();
		checkShowPts.execute();
		assertEquals(!sel, drawing.getSelection().getShapeAt(0).isShowPts());
		assertEquals(!sel, drawing.getSelection().getShapeAt(1).isShowPts());
	}

	@Test
	public void testPickLineColourSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddBezier, updateIns).execute();
		Color col = lineColButton.getValue();
		pickLineCol.execute();
		assertEquals(lineColButton.getValue(), drawing.getSelection().getShapeAt(0).getLineColour().toJFX());
		assertEquals(lineColButton.getValue(), drawing.getSelection().getShapeAt(1).getLineColour().toJFX());
		assertNotEquals(col, lineColButton.getValue());
	}
}
