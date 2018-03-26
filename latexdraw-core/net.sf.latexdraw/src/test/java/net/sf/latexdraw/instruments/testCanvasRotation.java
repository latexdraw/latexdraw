package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.Injector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class testCanvasRotation extends BaseTestCanvas {
	Border border;

	final double txDown = 50;
	final double tyDown = 200d;
	final GUIVoidCommand selectShapes = () -> {
		Platform.runLater(() -> canvas.getDrawing().getShapes().forEach(sh -> canvas.getDrawing().getSelection().addShape(sh)));
		WaitForAsyncUtils.waitForFxEvents();
	};
	final GUIVoidCommand rotateDown = () -> drag(border.rotHandler).dropBy(txDown, tyDown);

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
				bindAsEagerSingleton(TextSetter.class);
				bindAsEagerSingleton(Border.class);
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(Hand.class);
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		border = (Border) injectorFactory.call(Border.class);
		// To select the handler on click
		((Arc) border.rotHandler.getChildren().get(0)).setFill(Color.WHITE);
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
	}

	@Test
	public void testRotateRectangle() {
		new CompositeGUIVoidCommand(addRec, waitFXEvents, selectShapes).execute();
		final IPoint pt1 = ShapeFactory.INST.createPoint(point(border.rotHandler).query());
		final IPoint gc = ShapeFactory.INST.createPoint(point(getPane().getChildren().get(0)).query());
		new CompositeGUIVoidCommand(rotateDown, waitFXEvents).execute();
		final double a1 = ShapeFactory.INST.createLine(pt1, gc).getLineAngle();
		final double a2 = 2d * Math.PI - ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(pt1.getX() + txDown, pt1.getY() + tyDown), gc).getLineAngle();
		assertEquals(a1 + a2, ((IShape) getPane().getChildren().get(0).getUserData()).getRotationAngle(), 1d);
	}

	@Test
	public void testRotateTwoRectangles() {
		new CompositeGUIVoidCommand(addRec, addRec, waitFXEvents).execute();
		Platform.runLater(() -> canvas.getDrawing().getShapeAt(1).translate(150, 60));
		selectShapes.execute();
		final IPoint pt1 = ShapeFactory.INST.createPoint(point(border.rotHandler).query());
		final IPoint gc = ShapeFactory.INST.createPoint(point(getPane().getChildren().get(0)).query());
		new CompositeGUIVoidCommand(rotateDown, waitFXEvents).execute();
		final double a1 = ShapeFactory.INST.createLine(pt1, gc).getLineAngle();
		final double a2 = 2d * Math.PI - ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(pt1.getX() + txDown, pt1.getY() + tyDown), gc).getLineAngle();
		assertEquals(a1 + a2, ((IShape) getPane().getChildren().get(0).getUserData()).getRotationAngle(), 1d);
	}
}
