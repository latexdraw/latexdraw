package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import net.sf.latexdraw.CollectionMatcher;
import net.sf.latexdraw.handlers.Handler;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.Injector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TestBorder extends BaseTestCanvas implements CollectionMatcher {
	Border border;

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindAsEagerSingleton(Border.class);
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(Hand.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindAsEagerSingleton(MetaShapeCustomiser.class);
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
				bindToInstance(ShapeCoordDimCustomiser.class, Mockito.mock(ShapeCoordDimCustomiser.class));
				bindToInstance(ShapeAxesCustomiser.class, Mockito.mock(ShapeAxesCustomiser.class));
				bindToInstance(ShapeDoubleBorderCustomiser.class, Mockito.mock(ShapeDoubleBorderCustomiser.class));
				bindToInstance(ShapeFreeHandCustomiser.class, Mockito.mock(ShapeFreeHandCustomiser.class));
				bindToInstance(ShapeShadowCustomiser.class, Mockito.mock(ShapeShadowCustomiser.class));
				bindToInstance(ShapeFillingCustomiser.class, Mockito.mock(ShapeFillingCustomiser.class));
				bindToInstance(ShapeTransformer.class, Mockito.mock(ShapeTransformer.class));
				bindToInstance(ShapeGrouper.class, Mockito.mock(ShapeGrouper.class));
				bindToInstance(ShapeGridCustomiser.class, Mockito.mock(ShapeGridCustomiser.class));
				bindToInstance(ShapePositioner.class, Mockito.mock(ShapePositioner.class));
				bindToInstance(ShapeRotationCustomiser.class, Mockito.mock(ShapeRotationCustomiser.class));
				bindToInstance(ShapeArrowCustomiser.class, Mockito.mock(ShapeArrowCustomiser.class));
				bindToInstance(ShapeDotCustomiser.class, Mockito.mock(ShapeDotCustomiser.class));
				bindToInstance(ShapeStdGridCustomiser.class, Mockito.mock(ShapeStdGridCustomiser.class));
				bindToInstance(ShapeBorderCustomiser.class, Mockito.mock(ShapeBorderCustomiser.class));
				bindToInstance(ShapeArcCustomiser.class, Mockito.mock(ShapeArcCustomiser.class));
				bindAsEagerSingleton(TextSetter.class);
			}
		};
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
		border = (Border) injectorFactory.call(Border.class);
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Test
	public void testSelectPointsShapeSameNbHandlers() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents, selectAllShapes, waitFXEvents).execute();
		assertEquals(addedPolyline.getNbPoints(), border.mvPtHandlers.size());
	}

	@Test
	public void testSelectPointsShapeAllHandlersVisible() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents, selectAllShapes, waitFXEvents).execute();
		assertAllTrue(border.mvPtHandlers.stream(), handler -> handler.isVisible());
	}

	@Test
	public void testSelectPointsShapeAllHandlersCoordOK() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents, selectAllShapes, waitFXEvents).execute();
		for(int i=0, size = addedPolyline.getNbPoints(); i<size; i++) {
			assertEquals(addedPolyline.getPtAt(i), border.mvPtHandlers.get(i).getPoint());
		}
	}

	@Test
	public void testSelectPointsShapeAllHandlersTranslationOK() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents, selectAllShapes, waitFXEvents).execute();
		for(int i=0, size = addedPolyline.getNbPoints(); i<size; i++) {
			assertEquals(addedPolyline.getPtAt(i).getX() - Handler.DEFAULT_SIZE / 2d, border.mvPtHandlers.get(i).getTranslateX(), 0.00001);
			assertEquals(addedPolyline.getPtAt(i).getY() - Handler.DEFAULT_SIZE / 2d, border.mvPtHandlers.get(i).getTranslateY(), 0.00001);
			assertEquals(0d, border.mvPtHandlers.get(i).getX(), 0.00001);
			assertEquals(0d, border.mvPtHandlers.get(i).getY(), 0.00001);
		}
	}

	@Test
	public void testMovePtHandlerMovePt() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents, selectAllShapes, waitFXEvents).execute();
		final IPoint point = ShapeFactory.INST.createPoint(addedPolyline.getPtAt(1));
		point.translate(100d, 20d);
		drag(border.mvPtHandlers.get(1)).dropBy(100d, 20d);
		waitFXEvents.execute();
		assertEquals(point.getX(), addedPolyline.getPtAt(1).getX(), 1d);
		assertEquals(point.getY(), addedPolyline.getPtAt(1).getY(), 1d);
	}

	@Test
	public void testMovePtHandlerGoodPositionWithRotation() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents, selectAllShapes, waitFXEvents).execute();
		addedPolyline.setRotationAngle(Math.PI);
		waitFXEvents.execute();
		for(int i=0, size = addedPolyline.getNbPoints(); i<size; i++) {
			assertEquals(addedPolyline.getPtAt(i).rotatePoint(addedPolyline.getGravityCentre(),
				addedPolyline.getRotationAngle()).getX() - Handler.DEFAULT_SIZE / 2d, border.mvPtHandlers.get(i).getTranslateX(), 0.00001);
			assertEquals(addedPolyline.getPtAt(i).rotatePoint(addedPolyline.getGravityCentre(),
				addedPolyline.getRotationAngle()).getY() - Handler.DEFAULT_SIZE / 2d, border.mvPtHandlers.get(i).getTranslateY(), 0.00001);
			assertEquals(0d, border.mvPtHandlers.get(i).getX(), 0.00001);
			assertEquals(0d, border.mvPtHandlers.get(i).getY(), 0.00001);
		}
	}

	@Test
	public void testMovePtHandlerMovePtWithRotation() {
		new CompositeGUIVoidCommand(addLines, waitFXEvents, selectAllShapes, waitFXEvents).execute();
		final IPoint point = ShapeFactory.INST.createPoint(addedPolyline.getPtAt(1));
		addedPolyline.setRotationAngle(Math.PI);
		waitFXEvents.execute();
		point.translate(-100d, -20d);
		drag(border.mvPtHandlers.get(1)).dropBy(100d, 20d);
		waitFXEvents.execute();
		assertEquals(point.getX(), addedPolyline.getPtAt(1).getX(), 1d);
		assertEquals(point.getY(), addedPolyline.getPtAt(1).getY(), 1d);
	}
}
