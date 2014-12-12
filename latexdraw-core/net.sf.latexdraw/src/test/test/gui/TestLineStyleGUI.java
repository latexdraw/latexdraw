package test.gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.instruments.ShapeBorderCustomiser;

import org.junit.Before;
import org.junit.Test;
import org.testfx.util.WaitForAsyncUtils;

import com.google.inject.AbstractModule;

public class TestLineStyleGUI extends TestShapePropGUI<ShapeBorderCustomiser> {
	Spinner<Double> thicknessField;
	ColorPicker lineColButton;
	ComboBox<ImageView> lineCB;
	ComboBox<ImageView> bordersPosCB;
	Spinner<Double> frameArcField;
	CheckBox showPoints;

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "glib/views/jfx/ui/LineStyle.fxml";
	}
	 
	@Override
	@Before
	public void setUp() {
		super.setUp();
		thicknessField 	= find("#thicknessField");
		lineColButton 	= find("#lineColButton");
		lineCB 			= find("#lineCB");
		bordersPosCB 	= find("#bordersPosCB");
		frameArcField 	= find("#frameArcField");
		showPoints 		= find("#showPoints");
		ins				= (ShapeBorderCustomiser)guiceFactory.call(ShapeBorderCustomiser.class);
		ins.setActivated(true);
	}
	
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				bind(ShapeBorderCustomiser.class).asEagerSingleton();
			}
		};
	}
	
	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		when(pencil.isActivated()).thenReturn(true);
		when(hand.isActivated()).thenReturn(false);
		when(pencil.createShapeInstance()).thenReturn(ShapeFactory.createRectangle());
		Platform.runLater(() ->  ins.update());
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(ins.isActivated());
	}
	
	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		when(pencil.isActivated()).thenReturn(false);
		when(hand.isActivated()).thenReturn(true);
		Platform.runLater(() ->  ins.update());
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(ins.isActivated());
	}
	
	
	@Test
	public void testWidgetsGoodStateWhenNotShowPointPencil() {
		when(pencil.isActivated()).thenReturn(true);
		when(pencil.createShapeInstance()).thenReturn(ShapeFactory.createRectangle());
		Platform.runLater(() ->  ins.update());
		WaitForAsyncUtils.waitForFxEvents();
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
		when(pencil.isActivated()).thenReturn(true);
		when(pencil.createShapeInstance()).thenReturn(ShapeFactory.createBezierCurve());
		Platform.runLater(() ->  ins.update());
		WaitForAsyncUtils.waitForFxEvents();
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
		when(pencil.isActivated()).thenReturn(true);
		when(pencil.createShapeInstance()).thenReturn(ShapeFactory.createCircle());
		Platform.runLater(() ->  ins.update());
		WaitForAsyncUtils.waitForFxEvents();
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
		when(pencil.isActivated()).thenReturn(true);
		when(pencil.createShapeInstance()).thenReturn(ShapeFactory.createText());
		Platform.runLater(() ->  ins.update());
		WaitForAsyncUtils.waitForFxEvents();
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
		when(pencil.isActivated()).thenReturn(true);
		when(pencil.createShapeInstance()).thenReturn(ShapeFactory.createPicture(ShapeFactory.createPoint()));
		Platform.runLater(() ->  ins.update());
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(thicknessField.isVisible());
		assertFalse(lineColButton.isVisible());
		assertFalse(lineCB.isVisible());
		assertFalse(bordersPosCB.isVisible());
		assertFalse(frameArcField.isVisible());
		assertFalse(showPoints.isVisible());
	}
	
	@Test
	public void testControllerActivatedWhenSelectionNotEmpty() {
		IShape sh = ShapeFactory.createRectangle();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().setSelection(Arrays.asList(sh));
		when(pencil.isActivated()).thenReturn(false);
		when(hand.isActivated()).thenReturn(true);
		Platform.runLater(() ->  ins.update());
		WaitForAsyncUtils.waitForFxEvents();
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
}









