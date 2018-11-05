package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import javafx.stage.Stage;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.util.Injector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class TestShapeRotationCustomiser extends SelectionBasedTesting<ShapeRotationCustomiser> {
	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/Rotation.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		ins = injector.getInstance(ShapeRotationCustomiser.class);
		ins.setActivated(true);
		ins.update();
		selectTwoShapes.execute();
	}

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeRotationCustomiser.class);
				bindToInstance(Hand.class, hand);
				bindToInstance(Pencil.class, pencil);
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testNoSelectionNotRotation() {
		hand.canvas.getDrawing().getSelection().clear();
		injector.getInstance(ShapeRotationCustomiser.class).update(ShapeFactory.INST.createGroup());
		assertFalse(ins.isActivated());
		assertFalse(find("#mainPane").isVisible());
	}

	@Test
	public void testSelectionActivated() {
		injector.getInstance(ShapeRotationCustomiser.class).update(hand.canvas.getDrawing().getSelection());
		assertTrue(ins.isActivated());
		assertTrue(find("#mainPane").isVisible());
	}

	@Test
	public void testRotate90() {
		clickOn("#rotate90Button");
		waitFXEvents.execute();
		assertEquals(Math.PI / 2d, hand.canvas.getDrawing().getShapeAt(0).getRotationAngle(), 0.0001);
		assertEquals(Math.PI / 2d, hand.canvas.getDrawing().getShapeAt(1).getRotationAngle(), 0.0001);
	}

	@Test
	public void testRotate180() {
		clickOn("#rotate180Button");
		waitFXEvents.execute();
		assertEquals(Math.PI, hand.canvas.getDrawing().getShapeAt(0).getRotationAngle(), 0.0001);
		assertEquals(Math.PI, hand.canvas.getDrawing().getShapeAt(1).getRotationAngle(), 0.0001);
	}

	@Test
	public void testRotate270() {
		clickOn("#rotate270Button");
		waitFXEvents.execute();
		assertEquals(-Math.PI / 2d, hand.canvas.getDrawing().getShapeAt(0).getRotationAngle(), 0.0001);
		assertEquals(-Math.PI / 2d, hand.canvas.getDrawing().getShapeAt(1).getRotationAngle(), 0.0001);
	}

	@Test
	public void testIncrRotation() {
		incrementSpinner(find("#rotationField"));
		waitFXEvents.execute();
		assertEquals(1d, Math.toDegrees(hand.canvas.getDrawing().getShapeAt(0).getRotationAngle()), 0.0001);
		assertEquals(1d, Math.toDegrees(hand.canvas.getDrawing().getShapeAt(1).getRotationAngle()), 0.0001);
	}
}
