package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import javafx.stage.Stage;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.Injector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

public class TestShapePositioner extends SelectionBasedTesting<ShapePositioner> {
	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/PositionZ.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		ins = injector.getInstance(ShapePositioner.class);
		ins.setActivated(true);
		ins.update();
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
				bindAsEagerSingleton(ShapePositioner.class);
				bindToInstance(Hand.class, hand);
				bindToInstance(Pencil.class, pencil);
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testDeactivatedEmpty() {
		assertFalse(ins.isActivated());
		assertFalse(find("#mainPane").isVisible());
	}

	@Test
	public void testOneShapeBackground() {
		new CompositeGUIVoidCommand(selectOneShape, selectOneShape).execute();
		final IShape s1 = hand.canvas.getDrawing().getShapeAt(0);
		final IShape s2 = hand.canvas.getDrawing().getShapeAt(1);
		clickOn("#backgroundB");
		waitFXEvents.execute();
		assertEquals(s2, hand.canvas.getDrawing().getShapeAt(0));
		assertEquals(s1, hand.canvas.getDrawing().getShapeAt(1));
	}

	@Test
	public void testOneShapeForeground() {
		new CompositeGUIVoidCommand(selectOneShape, selectOneShape).execute();
		selectShapeAt.execute(Collections.singletonList(0));
		final IShape s1 = hand.canvas.getDrawing().getShapeAt(0);
		final IShape s2 = hand.canvas.getDrawing().getShapeAt(1);
		clickOn("#foregroundB");
		waitFXEvents.execute();
		assertEquals(s2, hand.canvas.getDrawing().getShapeAt(0));
		assertEquals(s1, hand.canvas.getDrawing().getShapeAt(1));
	}

	@Test
	public void testSeveralShapesBackground() {
		new CompositeGUIVoidCommand(selectThreeShapes, selectTwoShapes).execute();
		selectShapeAt.execute(Arrays.asList(1, 2));
		final IShape s2 = hand.canvas.getDrawing().getShapeAt(1);
		final IShape s3 = hand.canvas.getDrawing().getShapeAt(2);
		clickOn("#backgroundB");
		waitFXEvents.execute();
		assertEquals(s2, hand.canvas.getDrawing().getShapeAt(0));
		assertEquals(s3, hand.canvas.getDrawing().getShapeAt(1));
	}

	@Test
	public void testSeveralShapesForeground() {
		new CompositeGUIVoidCommand(selectThreeShapes, selectTwoShapes).execute();
		selectShapeAt.execute(Arrays.asList(1, 2));
		final IShape s2 = hand.canvas.getDrawing().getShapeAt(1);
		final IShape s3 = hand.canvas.getDrawing().getShapeAt(2);
		clickOn("#foregroundB");
		waitFXEvents.execute();
		assertEquals(s2, hand.canvas.getDrawing().getShapeAt(hand.canvas.getDrawing().size() - 2));
		assertEquals(s3, hand.canvas.getDrawing().getShapeAt(-1));
	}
}
