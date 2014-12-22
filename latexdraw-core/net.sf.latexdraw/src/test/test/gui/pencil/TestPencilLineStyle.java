package test.gui.pencil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import javafx.scene.paint.Color;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.LineStyle;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeBorderCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.gui.CompositeGUICommand;
import test.gui.ShapePropModule;
import test.gui.TestLineStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilLineStyle extends TestLineStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapeBorderCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUICommand(activatePencil, pencilCreatesRec, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testIncrementFrameArcPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesRec, updateIns).execute();
		double val = frameArcField.getValue();
		incrementFrameArc.execute();
		assertEquals(frameArcField.getValue(), ((IRectangle)pencil.createShapeInstance()).getLineArc(), 0.0001);
		assertNotEquals(val, frameArcField.getValue(), 0.0001);
	}

	@Test
	public void testIncrementThicknessPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesRec, updateIns).execute();
		double val = thicknessField.getValue();
		incrementThickness.execute();
		assertEquals(thicknessField.getValue(), pencil.createShapeInstance().getThickness(), 0.0001);
		assertNotEquals(val, thicknessField.getValue(), 0.0001);
	}

	@Test
	public void testSelectBorderPosPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesRec, updateIns).execute();
		IShape.BorderPos style = (IShape.BorderPos)bordersPosCB.getSelectionModel().getSelectedItem().getUserData();
		selectBorderPos.execute();
		assertEquals(bordersPosCB.getSelectionModel().getSelectedItem().getUserData(), pencil.createShapeInstance().getBordersPosition());
		assertNotEquals(style, bordersPosCB.getSelectionModel().getSelectedItem().getUserData());
	}

	@Test
	public void testSelectLineStylePencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesRec, updateIns).execute();
		LineStyle style = (LineStyle)lineCB.getSelectionModel().getSelectedItem().getUserData();
		selectLineStyle.execute();
		assertEquals(lineCB.getSelectionModel().getSelectedItem().getUserData(), pencil.createShapeInstance().getLineStyle());
		assertNotEquals(style, lineCB.getSelectionModel().getSelectedItem().getUserData());
	}

	@Test
	public void testPickLineColourPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesRec, updateIns).execute();
		Color col = lineColButton.getValue();
		pickLineCol.execute();
		assertEquals(lineColButton.getValue(), pencil.createShapeInstance().getLineColour().toJFX());
		assertNotEquals(col, lineColButton.getValue());
	}

	@Test
	public void testCheckShowPointPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		boolean sel = showPoints.isSelected();
		checkShowPts.execute();
		assertEquals(!sel, pencil.createShapeInstance().isShowPts());
	}

	@Test
	public void testWidgetsGoodStateWhenNotShowPointPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesRec, updateIns).execute();
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
	public void testWidgetsGoodStateWhenNotBorderMovableShowPointablePencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		assertTrue(thicknessField.isVisible());
		assertFalse(thicknessField.isDisabled());
		assertTrue(lineColButton.isVisible());
		assertFalse(lineColButton.isDisabled());
		assertTrue(lineCB.isVisible());
		assertFalse(lineCB.isDisabled());
		assertFalse(bordersPosCB.isVisible());
		assertFalse(frameArcField.isDisabled());
		assertTrue(showPoints.isVisible());
		assertFalse(showPoints.isDisabled());
	}

	@Test
	public void testWidgetsGoodStateWhenNotFrameArcPencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesCircle, updateIns).execute();
		assertTrue(thicknessField.isVisible());
		assertFalse(thicknessField.isDisabled());
		assertTrue(lineColButton.isVisible());
		assertFalse(lineColButton.isDisabled());
		assertTrue(lineCB.isVisible());
		assertFalse(lineCB.isDisabled());
		assertTrue(bordersPosCB.isVisible());
		assertFalse(bordersPosCB.isDisabled());
		assertFalse(frameArcField.isVisible());
		assertFalse(showPoints.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenNotThicknessablePencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesText, updateIns).execute();
		assertFalse(thicknessField.isVisible());
		assertTrue(lineColButton.isVisible());
		assertFalse(lineColButton.isDisabled());
		assertFalse(lineCB.isVisible());
		assertFalse(bordersPosCB.isVisible());
		assertFalse(frameArcField.isVisible());
		assertFalse(showPoints.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenNotColourablePencil() {
		new CompositeGUICommand(activatePencil, pencilCreatesPic, updateIns).execute();
		assertFalse(thicknessField.isVisible());
		assertFalse(lineColButton.isVisible());
		assertFalse(lineCB.isVisible());
		assertFalse(bordersPosCB.isVisible());
		assertFalse(frameArcField.isVisible());
		assertFalse(showPoints.isVisible());
	}
}
