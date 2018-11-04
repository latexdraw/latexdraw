package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import net.sf.latexdraw.CollectionMatcher;
import net.sf.latexdraw.handlers.Handler;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.ViewRectangle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TestBorder extends BaseTestCanvas implements CollectionMatcher {
	Border border;
	final double txDown = 50;
	final double tyDown = 200d;
	final GUIVoidCommand rotateDown = () -> drag(border.rotHandler).dropBy(txDown, tyDown);

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
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
		border = injector.getInstance(Border.class);
		WaitForAsyncUtils.waitForFxEvents();
		((Arc) border.rotHandler.getChildren().get(0)).setFill(Color.WHITE);
	}

	@Test
	public void testSelectPointsShapeSameNbHandlers() {
		new CompositeGUIVoidCommand(addLines, selectAllShapes).execute();
		assertEquals(addedPolyline.getNbPoints(), border.mvPtHandlers.size());
	}

	@Test
	public void testSelectPointsShapeAllHandlersVisible() {
		new CompositeGUIVoidCommand(addLines, selectAllShapes).execute();
		assertAllTrue(border.mvPtHandlers.stream(), handler -> handler.isVisible());
	}

	@Test
	public void testSelectPointsShapeAllHandlersCoordOK() {
		new CompositeGUIVoidCommand(addLines, selectAllShapes).execute();
		for(int i = 0, size = addedPolyline.getNbPoints(); i < size; i++) {
			assertEquals(addedPolyline.getPtAt(i), border.mvPtHandlers.get(i).getPoint());
		}
	}

	@Test
	public void testSelectPointsShapeAllHandlersTranslationOK() {
		new CompositeGUIVoidCommand(addLines, selectAllShapes).execute();
		for(int i = 0, size = addedPolyline.getNbPoints(); i < size; i++) {
			assertEquals(addedPolyline.getPtAt(i).getX() - Handler.DEFAULT_SIZE / 2d, border.mvPtHandlers.get(i).getTranslateX(), 0.00001);
			assertEquals(addedPolyline.getPtAt(i).getY() - Handler.DEFAULT_SIZE / 2d, border.mvPtHandlers.get(i).getTranslateY(), 0.00001);
			assertEquals(0d, border.mvPtHandlers.get(i).getX(), 0.00001);
			assertEquals(0d, border.mvPtHandlers.get(i).getY(), 0.00001);
		}
	}


	@Test
	public void testUpdateHandlersOKOnSelectionChanges() {
		// Keep in the same test method to limit the number of GUI tests
		new CompositeGUIVoidCommand(addBezier, addRec, addArc, addLines).execute();
		selectShape.execute(0);
		assertFalse(border.arcHandlerStart.isVisible());
		assertFalse(border.arcHandlerEnd.isVisible());
		assertAllTrue(border.mvPtHandlers.stream(), h -> h.isVisible());
		assertAllTrue(border.ctrlPt2Handlers.stream(), h -> h.isVisible());
		assertAllTrue(border.ctrlPt1Handlers.stream(), h -> h.isVisible());
		selectShape.execute(1);
		assertAllFalse(border.mvPtHandlers.stream(), h -> h.isVisible());
		assertAllFalse(border.ctrlPt2Handlers.stream(), h -> h.isVisible());
		assertAllFalse(border.ctrlPt1Handlers.stream(), h -> h.isVisible());
		selectShape.execute(2);
		assertTrue(border.arcHandlerStart.isVisible());
		assertTrue(border.arcHandlerEnd.isVisible());
		selectShape.execute(3);
		assertAllTrue(border.mvPtHandlers.stream(), h -> h.isVisible());
		assertAllFalse(border.ctrlPt2Handlers.stream(), h -> h.isVisible());
		assertAllFalse(border.ctrlPt1Handlers.stream(), h -> h.isVisible());
		selectShape.execute(0);
	}

	@Test
	public void testMovePtHandlerMovePt() {
		new CompositeGUIVoidCommand(addLines, selectAllShapes).execute();
		final IPoint point = ShapeFactory.INST.createPoint(addedPolyline.getPtAt(1));
		point.translate(100d, 20d);
		drag(border.mvPtHandlers.get(1)).dropBy(100d, 20d);
		waitFXEvents.execute();
		assertEquals(point.getX(), addedPolyline.getPtAt(1).getX(), 1d);
		assertEquals(point.getY(), addedPolyline.getPtAt(1).getY(), 1d);
	}

	@Test
	public void testMovePtHandlerGoodPositionWithRotation() {
		new CompositeGUIVoidCommand(addLines, selectAllShapes).execute();
		addedPolyline.setRotationAngle(Math.PI);
		waitFXEvents.execute();
		for(int i = 0, size = addedPolyline.getNbPoints(); i < size; i++) {
			assertEquals(addedPolyline.getPtAt(i).getX() - Handler.DEFAULT_SIZE / 2d, border.mvPtHandlers.get(i).getTranslateX(), 0.00001);
			assertEquals(addedPolyline.getPtAt(i).getY() - Handler.DEFAULT_SIZE / 2d, border.mvPtHandlers.get(i).getTranslateY(), 0.00001);
			assertEquals(0d, border.mvPtHandlers.get(i).getX(), 0.00001);
			assertEquals(0d, border.mvPtHandlers.get(i).getY(), 0.00001);
		}
	}

	@Test
	public void testMovePtHandlerMovePtWithRotation() {
		new CompositeGUIVoidCommand(addLines, selectAllShapes).execute();
		addedPolyline.setRotationAngle(Math.PI);
		waitFXEvents.execute();
		final IPoint point = ShapeFactory.INST.createPoint(addedPolyline.getPtAt(1));
		point.translate(100d, 20d);
		drag(border.mvPtHandlers.get(1)).dropBy(100d, 20d);
		waitFXEvents.execute();
		assertEquals(point.getX(), addedPolyline.getPtAt(1).getX(), 1d);
		assertEquals(point.getY(), addedPolyline.getPtAt(1).getY(), 1d);
	}

	@Test
	public void testMoveCtrl1PtHandlerMovePt() {
		new CompositeGUIVoidCommand(addBezier, selectAllShapes).execute();
		final IPoint point = ShapeFactory.INST.createPoint(addedBezier.getFirstCtrlPtAt(1));
		point.translate(100d, 20d);
		drag(border.ctrlPt1Handlers.get(1)).dropBy(100d, 20d);
		waitFXEvents.execute();
		assertEquals(point.getX(), addedBezier.getFirstCtrlPtAt(1).getX(), 1d);
		assertEquals(point.getY(), addedBezier.getFirstCtrlPtAt(1).getY(), 1d);
	}

	@Test
	public void testMoveCtrl2PtHandlerMovePt() {
		new CompositeGUIVoidCommand(addBezier, selectAllShapes).execute();
		final IPoint point = ShapeFactory.INST.createPoint(addedBezier.getSecondCtrlPtAt(2));
		point.translate(100d, 20d);
		drag(border.ctrlPt2Handlers.get(2)).dropBy(100d, 20d);
		waitFXEvents.execute();
		assertEquals(point.getX(), addedBezier.getSecondCtrlPtAt(2).getX(), 1d);
		assertEquals(point.getY(), addedBezier.getSecondCtrlPtAt(2).getY(), 1d);
	}

	@Test
	public void testRotateRectangle() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		final IPoint pt1 = ShapeFactory.INST.createPoint(point(border.rotHandler).query());
		final IPoint gc = ShapeFactory.INST.createPoint(point(getPane().getChildren().get(0)).query());
		new CompositeGUIVoidCommand(rotateDown).execute();
		final double a1 = ShapeFactory.INST.createLine(pt1, gc).getLineAngle();
		final double a2 = 2d * Math.PI - ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(pt1.getX() + txDown, pt1.getY() + tyDown), gc).getLineAngle();
		assertFalse("No rotation", ((ViewRectangle) canvas.getSelectedViews().get(0)).getBorder().getTransforms().isEmpty());
		assertEquals(a1 + a2, ((IShape) getPane().getChildren().get(0).getUserData()).getRotationAngle(), 0.01);
		assertEquals(Math.toDegrees(a1 + a2),
			((Rotate) ((ViewRectangle) canvas.getSelectedViews().get(0)).getBorder().getTransforms().get(0)).getAngle(), 1d);
	}

	@Test
	public void testRotateTwoRectangles() {
		new CompositeGUIVoidCommand(addRec, addRec).execute();
		Platform.runLater(() -> canvas.getDrawing().getShapeAt(1).translate(150, 60));
		selectAllShapes.execute();
		final IPoint pt1 = ShapeFactory.INST.createPoint(point(border.rotHandler).query());
		final IPoint gc = ShapeFactory.INST.createPoint(point(getPane().getChildren().get(0)).query());
		new CompositeGUIVoidCommand(rotateDown).execute();
		final double a1 = ShapeFactory.INST.createLine(pt1, gc).getLineAngle();
		final double a2 = 2d * Math.PI - ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(pt1.getX() + txDown, pt1.getY() + tyDown), gc).getLineAngle();
		assertFalse("No rotation", ((ViewRectangle) canvas.getSelectedViews().get(0)).getBorder().getTransforms().isEmpty());
		assertFalse("No rotation", ((ViewRectangle) canvas.getSelectedViews().get(1)).getBorder().getTransforms().isEmpty());
		assertEquals(a1 + a2, ((IShape) getPane().getChildren().get(0).getUserData()).getRotationAngle(), 0.3);
		assertEquals(Math.toDegrees(a1 + a2),
			((Rotate) ((ViewRectangle) canvas.getSelectedViews().get(0)).getBorder().getTransforms().get(0)).getAngle(), 15d);
		assertEquals(Math.toDegrees(a1 + a2),
			((Rotate) ((ViewRectangle) canvas.getSelectedViews().get(1)).getBorder().getTransforms().get(0)).getAngle(), 15d);
	}

	@Test
	public void testArcStartHandler() {
		new CompositeGUIVoidCommand(addArc, selectAllShapes).execute();
		final IPoint gc = addedArc.getGravityCentre();
		final IPoint point = ShapeFactory.INST.createPoint(addedArc.getStartPoint());
		final IPoint newpoint = point.rotatePoint(gc, -Math.PI / 4d);
		drag(border.arcHandlerStart).dropBy(newpoint.getX() - point.getX(), newpoint.getY() - point.getY());
		waitFXEvents.execute();
		final ILine l1 = ShapeFactory.INST.createLine(gc, ShapeFactory.INST.createPoint(addedArc.getStartPoint()));
		final ILine l2 = ShapeFactory.INST.createLine(gc, newpoint);
		assertEquals(l1.getA(), l2.getA(), 0.02);
	}

	@Test
	public void testArcEndHandler() {
		new CompositeGUIVoidCommand(addArc, selectAllShapes).execute();
		final IPoint gc = addedArc.getGravityCentre();
		final IPoint point = ShapeFactory.INST.createPoint(addedArc.getEndPoint());
		final IPoint newpoint = point.rotatePoint(gc, Math.PI / 3d);
		drag(border.arcHandlerEnd).dropBy(newpoint.getX() - point.getX(), newpoint.getY() - point.getY());
		waitFXEvents.execute();
		final ILine l1 = ShapeFactory.INST.createLine(gc, ShapeFactory.INST.createPoint(addedArc.getEndPoint()));
		final ILine l2 = ShapeFactory.INST.createLine(gc, newpoint);
		assertEquals(l1.getA(), l2.getA(), 0.02);
	}

	@Test
	public void testArcStartHandlerRotated() {
		new CompositeGUIVoidCommand(addArc, selectAllShapes).execute();
		Platform.runLater(() -> addedArc.setRotationAngle(0.5));
		WaitForAsyncUtils.waitForFxEvents();
		final IPoint gc = addedArc.getGravityCentre();
		final IPoint point = addedArc.getStartPoint().rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle());
		final IPoint newpoint = point.rotatePoint(gc, -Math.PI / 4d).rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle());
		drag(border.arcHandlerStart).dropBy(newpoint.getX() - point.getX(), newpoint.getY() - point.getY());
		waitFXEvents.execute();
		final ILine l1 = ShapeFactory.INST.createLine(gc, addedArc.getStartPoint().rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle()));
		final ILine l2 = ShapeFactory.INST.createLine(gc, newpoint);
		assertEquals(l1.getA(), l2.getA(), 0.02);
	}

	@Test
	public void testArcEndHandlerRotated() {
		new CompositeGUIVoidCommand(addArc, selectAllShapes).execute();
		Platform.runLater(() -> addedArc.setRotationAngle(0.5));
		WaitForAsyncUtils.waitForFxEvents();
		final IPoint gc = addedArc.getGravityCentre();
		final IPoint point = addedArc.getEndPoint().rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle());
		final IPoint newpoint = point.rotatePoint(gc, Math.PI / 3d).rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle());
		drag(border.arcHandlerEnd).dropBy(newpoint.getX() - point.getX(), newpoint.getY() - point.getY());
		waitFXEvents.execute();
		final ILine l1 = ShapeFactory.INST.createLine(gc, addedArc.getEndPoint().rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle()));
		final ILine l2 = ShapeFactory.INST.createLine(gc, newpoint);
		assertEquals(l1.getA(), l2.getA(), 0.02);
	}

	@Test
	public void testScaleWRectangle() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final IPoint tl = addedRec.getTopLeftPoint();
		tl.translate(50d, 0d);
		drag(border.scaleHandlers.get(3)).dropBy(50d, 10d);
		waitFXEvents.execute();
		assertEquals(height, addedRec.getHeight(), 0.001);
		assertEquals(width - 50d, addedRec.getWidth(), 3d);
		assertEquals(tl.getX(), addedRec.getTopLeftPoint().getX(), 2d);
		assertEquals(tl.getY(), addedRec.getTopLeftPoint().getY(), 2d);
	}

	@Test
	public void testScaleERectangle() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final IPoint tr = addedRec.getTopRightPoint();
		tr.translate(50d, 0d);
		drag(border.scaleHandlers.get(4)).dropBy(50d, 10d);
		waitFXEvents.execute();
		assertEquals(height, addedRec.getHeight(), 0.001);
		assertEquals(width + 50d, addedRec.getWidth(), 3d);
		assertEquals(tr.getX(), addedRec.getTopRightPoint().getX(), 2d);
		assertEquals(tr.getY(), addedRec.getTopRightPoint().getY(), 2d);
	}

	@Test
	public void testScaleNRectangle() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final IPoint tr = addedRec.getTopRightPoint();
		tr.translate(0d, -20d);
		drag(border.scaleHandlers.get(1)).dropBy(30d, -20d);
		waitFXEvents.execute();
		assertEquals(width, addedRec.getWidth(), 0.001);
		assertEquals(height + 20d, addedRec.getHeight(), 3d);
		assertEquals(tr.getX(), addedRec.getTopRightPoint().getX(), 3d);
		assertEquals(tr.getY(), addedRec.getTopRightPoint().getY(), 3d);
	}

	@Test
	public void testScaleSRectangle() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final IPoint bl = addedRec.getBottomLeftPoint();
		bl.translate(0d, 50d);
		drag(border.scaleHandlers.get(6)).dropBy(30d, 50d);
		waitFXEvents.execute();
		assertEquals(width, addedRec.getWidth(), 0.001);
		assertEquals(height + 50d, addedRec.getHeight(), 3d);
		assertEquals(bl.getX(), addedRec.getBottomLeftPoint().getX(), 3d);
		assertEquals(bl.getY(), addedRec.getBottomLeftPoint().getY(), 3d);
	}

	@Test
	public void testScaleNORectangle() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final IPoint tl = addedRec.getTopLeftPoint();
		tl.translate(50d, 70d);
		drag(border.scaleHandlers.get(0)).dropBy(50d, 70d);
		waitFXEvents.execute();
		assertEquals(width - 50d, addedRec.getWidth(), 3d);
		assertEquals(height - 70d, addedRec.getHeight(), 3d);
		assertEquals(tl.getX(), addedRec.getTopLeftPoint().getX(), 3d);
		assertEquals(tl.getY(), addedRec.getTopLeftPoint().getY(), 3d);
	}

	@Test
	public void testScaleNERectangle() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final IPoint tr = addedRec.getTopRightPoint();
		tr.translate(50d, -40d);
		drag(border.scaleHandlers.get(2)).dropBy(50d, -40d);
		waitFXEvents.execute();
		assertEquals(width + 50d, addedRec.getWidth(), 3d);
		assertEquals(height + 40d, addedRec.getHeight(), 3d);
		assertEquals(tr.getX(), addedRec.getTopRightPoint().getX(), 3d);
		assertEquals(tr.getY(), addedRec.getTopRightPoint().getY(), 3d);
	}

	@Test
	public void testScaleSORectangle() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final IPoint bl = addedRec.getBottomLeftPoint();
		bl.translate(50d, 70d);
		drag(border.scaleHandlers.get(5)).dropBy(50d, 70d);
		waitFXEvents.execute();
		assertEquals(width - 50d, addedRec.getWidth(), 3d);
		assertEquals(height + 70d, addedRec.getHeight(), 3d);
		assertEquals(bl.getX(), addedRec.getBottomLeftPoint().getX(), 3d);
		assertEquals(bl.getY(), addedRec.getBottomLeftPoint().getY(), 3d);
	}

	@Test
	public void testScaleSERectangle() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final IPoint br = addedRec.getBottomRightPoint();
		br.translate(-55d, 45d);
		drag(border.scaleHandlers.get(7)).dropBy(-55d, 45d);
		waitFXEvents.execute();
		assertEquals(width - 55d, addedRec.getWidth(), 3d);
		assertEquals(height + 45d, addedRec.getHeight(), 3d);
		assertEquals(br.getX(), addedRec.getBottomRightPoint().getX(), 3d);
		assertEquals(br.getY(), addedRec.getBottomRightPoint().getY(), 3d);
	}
}
