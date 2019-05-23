package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import net.sf.latexdraw.CollectionMatcher;
import net.sf.latexdraw.handler.Handler;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Line;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
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
	final CmdVoid rotateDown = () -> drag(border.rotHandler).dropBy(txDown, tyDown);
	final CmdFXVoid setRotPI = () -> addedPolyline.setRotationAngle(Math.PI);

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
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
				bindAsEagerSingleton(Hand.class);
				bindAsEagerSingleton(MetaShapeCustomiser.class);
				bindAsEagerSingleton(Border.class);
				bindAsEagerSingleton(FacadeCanvasController.class);
			}
		};
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
		border = injector.getInstance(Border.class);
		WaitForAsyncUtils.waitForFxEvents();
		((Arc) border.rotHandler.getChildren().get(0)).setFill(Color.WHITE);
	}

	@Test
	public void testSelectPointsShapeSameNbHandlers() {
		Cmds.of(addLines, selectAllShapes).execute();
		assertEquals(addedPolyline.getNbPoints(), border.mvPtHandlers.size());
	}

	@Test
	public void testSelectPointsShapeAllHandlersVisible() {
		Cmds.of(addLines, selectAllShapes).execute();
		assertAllTrue(border.mvPtHandlers.stream(), handler -> handler.isVisible());
	}

	@Test
	public void testSelectPointsShapeAllHandlersCoordOK() {
		Cmds.of(addLines, selectAllShapes).execute();
		for(int i = 0, size = addedPolyline.getNbPoints(); i < size; i++) {
			assertEquals(addedPolyline.getPtAt(i), border.mvPtHandlers.get(i).getPoint());
		}
	}

	@Test
	public void testSelectPointsShapeAllHandlersTranslationOK() {
		Cmds.of(addLines, selectAllShapes).execute();
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
		Cmds.of(addBezier, addRec, addArc, addLines, () -> selectShape.execute(0)).execute();
		assertFalse(border.arcHandlerStart.isVisible());
		assertFalse(border.arcHandlerEnd.isVisible());
		assertAllTrue(border.mvPtHandlers.stream(), h -> h.isVisible());
		assertAllTrue(border.ctrlPt2Handlers.stream(), h -> h.isVisible());
		assertAllTrue(border.ctrlPt1Handlers.stream(), h -> h.isVisible());
		Cmds.of(() -> selectShape.execute(1)).execute();
		assertAllFalse(border.mvPtHandlers.stream(), h -> h.isVisible());
		assertAllFalse(border.ctrlPt2Handlers.stream(), h -> h.isVisible());
		assertAllFalse(border.ctrlPt1Handlers.stream(), h -> h.isVisible());
		Cmds.of(() -> selectShape.execute(2)).execute();
		assertTrue(border.arcHandlerStart.isVisible());
		assertTrue(border.arcHandlerEnd.isVisible());
		Cmds.of(() -> selectShape.execute(3)).execute();
		assertAllTrue(border.mvPtHandlers.stream(), h -> h.isVisible());
		assertAllFalse(border.ctrlPt2Handlers.stream(), h -> h.isVisible());
		assertAllFalse(border.ctrlPt1Handlers.stream(), h -> h.isVisible());
	}

	@Test
	public void testMovePtHandlerMovePt() {
		Cmds.of(addLines, selectAllShapes).execute();
		final Point point = ShapeFactory.INST.createPoint(addedPolyline.getPtAt(1));
		point.translate(100d, 20d);
		Cmds.of(() -> drag(border.mvPtHandlers.get(1)).dropBy(100d, 20d)).execute();
		assertEquals(point.getX(), addedPolyline.getPtAt(1).getX(), 1d);
		assertEquals(point.getY(), addedPolyline.getPtAt(1).getY(), 1d);
	}

	@Test
	public void testMovePtHandlerGoodPositionWithRotation() {
		Cmds.of(addLines, selectAllShapes, setRotPI).execute();

		for(int i = 0, size = addedPolyline.getNbPoints(); i < size; i++) {
			assertEquals(addedPolyline.getPtAt(i).getX() - Handler.DEFAULT_SIZE / 2d, border.mvPtHandlers.get(i).getTranslateX(), 0.00001);
			assertEquals(addedPolyline.getPtAt(i).getY() - Handler.DEFAULT_SIZE / 2d, border.mvPtHandlers.get(i).getTranslateY(), 0.00001);
			assertEquals(0d, border.mvPtHandlers.get(i).getX(), 0.00001);
			assertEquals(0d, border.mvPtHandlers.get(i).getY(), 0.00001);
		}
	}

	@Test
	public void testMovePtHandlerMovePtWithRotation() {
		Cmds.of(addLines, selectAllShapes, setRotPI).execute();
		final Point point = ShapeFactory.INST.createPoint(addedPolyline.getPtAt(1));
		point.translate(100d, 20d);
		Cmds.of(() -> drag(border.mvPtHandlers.get(1)).dropBy(100d, 20d)).execute();
		assertEquals(point.getX(), addedPolyline.getPtAt(1).getX(), 1d);
		assertEquals(point.getY(), addedPolyline.getPtAt(1).getY(), 1d);
	}

	@Test
	public void testMoveCtrl1PtHandlerMovePt() {
		Cmds.of(addBezier, selectAllShapes).execute();
		final Point point = ShapeFactory.INST.createPoint(addedBezier.getFirstCtrlPtAt(1));
		point.translate(100d, 20d);
		Cmds.of(() -> drag(border.ctrlPt1Handlers.get(1)).dropBy(100d, 20d)).execute();
		assertEquals(point.getX(), addedBezier.getFirstCtrlPtAt(1).getX(), 1d);
		assertEquals(point.getY(), addedBezier.getFirstCtrlPtAt(1).getY(), 1d);
	}

	@Test
	public void testMoveCtrl2PtHandlerMovePt() {
		Cmds.of(addBezier, selectAllShapes).execute();
		final Point point = ShapeFactory.INST.createPoint(addedBezier.getSecondCtrlPtAt(2));
		point.translate(100d, 20d);
		Cmds.of(() -> drag(border.ctrlPt2Handlers.get(2)).dropBy(100d, 20d)).execute();
		assertEquals(point.getX(), addedBezier.getSecondCtrlPtAt(2).getX(), 1d);
		assertEquals(point.getY(), addedBezier.getSecondCtrlPtAt(2).getY(), 1d);
	}

	@Test
	public void testRotateRectangle() {
		Cmds.of(addRec, selectAllShapes).execute();
		final Point pt1 = ShapeFactory.INST.createPoint(point(border.rotHandler).query());
		final Point gc = ShapeFactory.INST.createPoint(point(getPane().getChildren().get(0)).query());
		Cmds.of(rotateDown).execute();
		final double a1 = ShapeFactory.INST.createLine(pt1, gc).getLineAngle();
		final double a2 = 2d * Math.PI - ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(pt1.getX() + txDown, pt1.getY() + tyDown), gc).getLineAngle();
		assertFalse("No rotation", ((ViewRectangle) canvas.getSelectedViews().get(0)).getBorder().getTransforms().isEmpty());
		assertEquals(a1 + a2, ((Shape) getPane().getChildren().get(0).getUserData()).getRotationAngle(), 0.01);
		assertEquals(Math.toDegrees(a1 + a2),
			((Rotate) ((ViewRectangle) canvas.getSelectedViews().get(0)).getBorder().getTransforms().get(0)).getAngle(), 1d);
	}

	@Test
	public void testRotateTwoRectangles() {
		final CmdFXVoid tr = () -> canvas.getDrawing().getShapeAt(1).orElseThrow().translate(150, 60);
		Cmds.of(addRec, addRec, tr, selectAllShapes).execute();
		final Point pt1 = ShapeFactory.INST.createPoint(point(border.rotHandler).query());
		final Point gc = ShapeFactory.INST.createPoint(point(getPane().getChildren().get(0)).query());
		Cmds.of(rotateDown).execute();
		final double a1 = ShapeFactory.INST.createLine(pt1, gc).getLineAngle();
		final double a2 = 2d * Math.PI - ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(pt1.getX() + txDown, pt1.getY() + tyDown), gc).getLineAngle();
		assertFalse("No rotation", ((ViewRectangle) canvas.getSelectedViews().get(0)).getBorder().getTransforms().isEmpty());
		assertFalse("No rotation", ((ViewRectangle) canvas.getSelectedViews().get(1)).getBorder().getTransforms().isEmpty());
		assertEquals(a1 + a2, ((Shape) getPane().getChildren().get(0).getUserData()).getRotationAngle(), 0.3);
		assertEquals(Math.toDegrees(a1 + a2),
			((Rotate) ((ViewRectangle) canvas.getSelectedViews().get(0)).getBorder().getTransforms().get(0)).getAngle(), 15d);
		assertEquals(Math.toDegrees(a1 + a2),
			((Rotate) ((ViewRectangle) canvas.getSelectedViews().get(1)).getBorder().getTransforms().get(0)).getAngle(), 15d);
	}

	@Test
	public void testArcStartHandler() {
		Cmds.of(addArc, selectAllShapes).execute();
		final Point gc = addedArc.getGravityCentre();
		final Point point = ShapeFactory.INST.createPoint(addedArc.getStartPoint());
		final Point newpoint = point.rotatePoint(gc, -Math.PI / 4d);
		Cmds.of(() -> drag(border.arcHandlerStart).dropBy(newpoint.getX() - point.getX(), newpoint.getY() - point.getY())).execute();
		final Line l1 = ShapeFactory.INST.createLine(gc, ShapeFactory.INST.createPoint(addedArc.getStartPoint()));
		final Line l2 = ShapeFactory.INST.createLine(gc, newpoint);
		assertEquals(l1.getA(), l2.getA(), 0.02);
	}

	@Test
	public void testArcEndHandler() {
		Cmds.of(addArc, selectAllShapes).execute();
		final Point gc = addedArc.getGravityCentre();
		final Point point = ShapeFactory.INST.createPoint(addedArc.getEndPoint());
		final Point newpoint = point.rotatePoint(gc, Math.PI / 3d);
		Cmds.of(() -> drag(border.arcHandlerEnd).dropBy(newpoint.getX() - point.getX(), newpoint.getY() - point.getY())).execute();
		final Line l1 = ShapeFactory.INST.createLine(gc, ShapeFactory.INST.createPoint(addedArc.getEndPoint()));
		final Line l2 = ShapeFactory.INST.createLine(gc, newpoint);
		assertEquals(l1.getA(), l2.getA(), 0.02);
	}

	@Test
	public void testArcStartHandlerRotated() {
		Cmds.of(addArc, selectAllShapes, () -> addedArc.setRotationAngle(0.5)).execute();
		final Point gc = addedArc.getGravityCentre();
		final Point point = addedArc.getStartPoint().rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle());
		final Point newpoint = point.rotatePoint(gc, -Math.PI / 4d).rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle());
		Cmds.of(() -> drag(border.arcHandlerStart).dropBy(newpoint.getX() - point.getX(), newpoint.getY() - point.getY())).execute();
		final Line l1 = ShapeFactory.INST.createLine(gc, addedArc.getStartPoint().rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle()));
		final Line l2 = ShapeFactory.INST.createLine(gc, newpoint);
		assertEquals(l1.getA(), l2.getA(), 0.02);
	}

	@Test
	public void testArcEndHandlerRotated() {
		Cmds.of(addArc, selectAllShapes, () -> addedArc.setRotationAngle(0.5)).execute();
		final Point gc = addedArc.getGravityCentre();
		final Point point = addedArc.getEndPoint().rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle());
		final Point newpoint = point.rotatePoint(gc, Math.PI / 3d).rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle());
		Cmds.of(() -> drag(border.arcHandlerEnd).dropBy(newpoint.getX() - point.getX(), newpoint.getY() - point.getY())).execute();
		final Line l1 = ShapeFactory.INST.createLine(gc, addedArc.getEndPoint().rotatePoint(addedArc.getGravityCentre(), addedArc.getRotationAngle()));
		final Line l2 = ShapeFactory.INST.createLine(gc, newpoint);
		assertEquals(l1.getA(), l2.getA(), 0.02);
	}

	@Test
	public void testScaleWRectangle() {
		Cmds.of(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final Point tl = addedRec.getTopLeftPoint();
		final CmdFXVoid tr = () -> tl.translate(50d, 0d);
		Cmds.of(tr, () -> drag(border.scaleHandlers.get(3)).dropBy(50d, 10d)).execute();
		assertEquals(height, addedRec.getHeight(), 0.001);
		assertEquals(width - 50d, addedRec.getWidth(), 3d);
		assertEquals(tl.getX(), addedRec.getTopLeftPoint().getX(), 2d);
		assertEquals(tl.getY(), addedRec.getTopLeftPoint().getY(), 2d);
	}

	@Test
	public void testScaleERectangle() {
		Cmds.of(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final Point tr = addedRec.getTopRightPoint();
		final CmdFXVoid trCmd = () -> tr.translate(50d, 0d);
		Cmds.of(trCmd, () -> drag(border.scaleHandlers.get(4)).dropBy(50d, 10d)).execute();
		assertEquals(height, addedRec.getHeight(), 0.001);
		assertEquals(width + 50d, addedRec.getWidth(), 3d);
		assertEquals(tr.getX(), addedRec.getTopRightPoint().getX(), 2d);
		assertEquals(tr.getY(), addedRec.getTopRightPoint().getY(), 2d);
	}

	@Test
	public void testScaleNRectangle() {
		Cmds.of(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final Point tr = addedRec.getTopRightPoint();
		final CmdFXVoid trCmd = () -> tr.translate(0d, -20d);
		Cmds.of(trCmd, () -> drag(border.scaleHandlers.get(1)).dropBy(30d, -20d)).execute();
		assertEquals(width, addedRec.getWidth(), 0.001);
		assertEquals(height + 20d, addedRec.getHeight(), 3d);
		assertEquals(tr.getX(), addedRec.getTopRightPoint().getX(), 3d);
		assertEquals(tr.getY(), addedRec.getTopRightPoint().getY(), 3d);
	}

	@Test
	public void testScaleSRectangle() {
		Cmds.of(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final Point bl = addedRec.getBottomLeftPoint();
		final CmdFXVoid trCmd = () -> bl.translate(0d, 50d);
		Cmds.of(trCmd, () -> drag(border.scaleHandlers.get(6)).dropBy(30d, 50d)).execute();
		assertEquals(width, addedRec.getWidth(), 0.001);
		assertEquals(height + 50d, addedRec.getHeight(), 3d);
		assertEquals(bl.getX(), addedRec.getBottomLeftPoint().getX(), 3d);
		assertEquals(bl.getY(), addedRec.getBottomLeftPoint().getY(), 3d);
	}

	@Test
	public void testScaleNORectangle() {
		Cmds.of(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final Point tl = addedRec.getTopLeftPoint();
		final CmdFXVoid trCmd = () -> tl.translate(50d, 70d);
		Cmds.of(trCmd, () -> drag(border.scaleHandlers.get(0)).dropBy(50d, 70d)).execute();
		assertEquals(width - 50d, addedRec.getWidth(), 3d);
		assertEquals(height - 70d, addedRec.getHeight(), 3d);
		assertEquals(tl.getX(), addedRec.getTopLeftPoint().getX(), 3d);
		assertEquals(tl.getY(), addedRec.getTopLeftPoint().getY(), 3d);
	}

	@Test
	public void testScaleNERectangle() {
		Cmds.of(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final Point tr = addedRec.getTopRightPoint();
		final CmdFXVoid trCmd = () -> tr.translate(50d, -40d);
		Cmds.of(trCmd, () -> drag(border.scaleHandlers.get(2)).dropBy(50d, -40d)).execute();
		assertEquals(width + 50d, addedRec.getWidth(), 3d);
		assertEquals(height + 40d, addedRec.getHeight(), 3d);
		assertEquals(tr.getX(), addedRec.getTopRightPoint().getX(), 3d);
		assertEquals(tr.getY(), addedRec.getTopRightPoint().getY(), 3d);
	}

	@Test
	public void testScaleSORectangle() {
		Cmds.of(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final Point bl = addedRec.getBottomLeftPoint();
		final CmdFXVoid trCmd = () -> bl.translate(50d, 70d);
		Cmds.of(trCmd, () -> drag(border.scaleHandlers.get(5)).dropBy(50d, 70d)).execute();
		waitFXEvents.execute();
		assertEquals(width - 50d, addedRec.getWidth(), 3d);
		assertEquals(height + 70d, addedRec.getHeight(), 3d);
		assertEquals(bl.getX(), addedRec.getBottomLeftPoint().getX(), 3d);
		assertEquals(bl.getY(), addedRec.getBottomLeftPoint().getY(), 3d);
	}

	@Test
	public void testScaleSERectangle() {
		Cmds.of(addRec, selectAllShapes).execute();
		final double width = addedRec.getWidth();
		final double height = addedRec.getHeight();
		final Point br = addedRec.getBottomRightPoint();
		final CmdFXVoid trCmd = () -> br.translate(-55d, 45d);
		Cmds.of(trCmd, () -> drag(border.scaleHandlers.get(7)).dropBy(-55d, 45d)).execute();
		assertEquals(width - 55d, addedRec.getWidth(), 3d);
		assertEquals(height + 45d, addedRec.getHeight(), 3d);
		assertEquals(br.getX(), addedRec.getBottomRightPoint().getX(), 3d);
		assertEquals(br.getY(), addedRec.getBottomRightPoint().getY(), 3d);
	}
}
