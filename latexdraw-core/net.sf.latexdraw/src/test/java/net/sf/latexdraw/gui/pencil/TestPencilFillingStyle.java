package net.sf.latexdraw.gui.pencil;

import javafx.scene.paint.Color;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestFillingStyleGUI;
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
public class TestPencilFillingStyle extends TestFillingStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeFillingCustomiser.class);
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
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesDot, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesPic, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectFillingPlainPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		FillingStyle style = fillStyleCB.getSelectionModel().getSelectedItem();
		selectStyle.execute(FillingStyle.PLAIN);
		FillingStyle newStyle = fillStyleCB.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, pencil.createShapeInstance().getFillingStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testPickFillingColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, selectPlainStyle, updateIns).execute();
		Color col = fillColButton.getValue();
		pickfillCol.execute();
		assertEquals(fillColButton.getValue(), pencil.createShapeInstance().getFillingCol().toJFX());
		assertNotEquals(col, fillColButton.getValue());
	}

	@Test
	public void testPickHatchingsColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, selectHatchingsStyle, updateIns).execute();
		Color col = hatchColButton.getValue();
		pickhatchCol.execute();
		assertEquals(hatchColButton.getValue(), pencil.createShapeInstance().getHatchingsCol().toJFX());
		assertNotEquals(col, hatchColButton.getValue());
	}

	@Test
	public void testPickGradStartColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, selectGradStyle, updateIns).execute();
		Color col = gradStartColButton.getValue();
		pickgradStartCol.execute();
		assertEquals(gradStartColButton.getValue(), pencil.createShapeInstance().getGradColStart().toJFX());
		assertNotEquals(col, gradStartColButton.getValue());
	}

	@Test
	public void testPickGradEndColourPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, selectGradStyle, updateIns).execute();
		Color col = gradEndColButton.getValue();
		pickgradEndCol.execute();
		assertEquals(gradEndColButton.getValue(), pencil.createShapeInstance().getGradColEnd().toJFX());
		assertNotEquals(col, gradEndColButton.getValue());
	}

	@Test
	public void testIncrementGradMidPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectGradStyle).execute();
		double val = gradMidPtField.getValue();
		incrementgradMidPt.execute();
		assertEquals(gradMidPtField.getValue(), pencil.createShapeInstance().getGradMidPt(), 0.0001);
		assertNotEquals(val, gradMidPtField.getValue(), 0.0001);
	}

	@Test
	public void testIncrementGradAnglePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectGradStyle).execute();
		double val = gradAngleField.getValue();
		incrementgradAngle.execute();
		assertEquals(gradAngleField.getValue(), Math.toDegrees(pencil.createShapeInstance().getGradAngle()), 0.0001);
		assertNotEquals(val, gradAngleField.getValue(), 0.0001);
	}

	@Test
	public void testIncrementHatchAnglePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectHatchingsStyle).execute();
		double val = hatchAngleField.getValue();
		incrementhatchAngle.execute();
		assertEquals(hatchAngleField.getValue(), Math.toDegrees(pencil.createShapeInstance().getHatchingsAngle()), 0.0001);
		assertNotEquals(val, hatchAngleField.getValue(), 0.0001);
	}

	@Test
	public void testIncrementHatchWidthPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectHatchingsStyle).execute();
		double val = hatchWidthField.getValue();
		incrementhatchWidth.execute();
		assertEquals(hatchWidthField.getValue(), pencil.createShapeInstance().getHatchingsWidth(), 0.0001);
		assertNotEquals(val, hatchWidthField.getValue(), 0.0001);
	}

	@Test
	public void testIncrementHatchSepPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesBezier, updateIns, selectHatchingsStyle).execute();
		double val = hatchSepField.getValue();
		incrementhatchSep.execute();
		assertEquals(hatchSepField.getValue(), pencil.createShapeInstance().getHatchingsSep(), 0.0001);
		assertNotEquals(val, hatchSepField.getValue(), 0.0001);
	}
}
