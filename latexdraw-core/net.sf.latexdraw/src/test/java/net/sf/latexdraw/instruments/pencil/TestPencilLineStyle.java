package net.sf.latexdraw.instruments.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.scene.paint.Color;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeBorderCustomiser;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestLineStyleGUI;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
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
public class TestPencilLineStyle extends TestLineStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeBorderCustomiser.class);
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testIncrementFrameArcPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns), frameArcField,
			incrementFrameArc, Collections.singletonList(() ->  ((IRectangle)pencil.createShapeInstance()).getLineArc()));
	}

	@Test
	public void testIncrementThicknessPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns), thicknessField,
			incrementThickness, Collections.singletonList(() ->  pencil.createShapeInstance().getThickness()));
	}

	@Test
	public void testSelectBorderPosPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		final BorderPos style = bordersPosCB.getSelectionModel().getSelectedItem();
		selectBorderPos.execute();
		waitFXEvents.execute();
		assertEquals(bordersPosCB.getSelectionModel().getSelectedItem(), pencil.createShapeInstance().getBordersPosition());
		assertNotEquals(style, bordersPosCB.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectLineStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		final LineStyle style = lineCB.getSelectionModel().getSelectedItem();
		selectLineStyle.execute();
		waitFXEvents.execute();
		assertEquals(lineCB.getSelectionModel().getSelectedItem(), pencil.createShapeInstance().getLineStyle());
		assertNotEquals(style, lineCB.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testPickLineColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		final Color col = lineColButton.getValue();
		pickLineCol.execute();
		waitFXEvents.execute();
		assertEquals(lineColButton.getValue(), pencil.createShapeInstance().getLineColour().toJFX());
		assertNotEquals(col, lineColButton.getValue());
	}

	@Test
	public void testCheckShowPointPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final boolean sel = showPoints.isSelected();
		checkShowPts.execute();
		waitFXEvents.execute();
		assertEquals(!sel, pencil.createShapeInstance().isShowPts());
	}

	@Test
	public void testWidgetsGoodStateWhenNotShowPointPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesCircle, updateIns).execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
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
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPic, updateIns).execute();
		assertFalse(thicknessField.isVisible());
		assertFalse(lineColButton.isVisible());
		assertFalse(lineCB.isVisible());
		assertFalse(bordersPosCB.isVisible());
		assertFalse(frameArcField.isVisible());
		assertFalse(showPoints.isVisible());
	}

	@Test
	public void testSelectOpenFreehandPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesFreehand, updateIns).execute();
		final boolean isopen = opened.isSelected();
		checkOpened.execute();
		waitFXEvents.execute();
		assertEquals(opened.isSelected(), ((IFreehand) pencil.createShapeInstance()).isOpened());
		assertNotEquals(isopen, opened.isSelected());
	}

	@Test
	public void testSelectOpenBezierPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns).execute();
		final boolean isopen = opened.isSelected();
		checkOpened.execute();
		waitFXEvents.execute();
		assertEquals(opened.isSelected(), ((IBezierCurve) pencil.createShapeInstance()).isOpened());
		assertNotEquals(isopen, opened.isSelected());
	}
}
