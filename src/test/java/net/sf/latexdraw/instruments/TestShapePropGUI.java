package net.sf.latexdraw.instruments;

import java.util.Arrays;
import javafx.application.Platform;
import net.sf.latexdraw.instruments.robot.FxRobotColourPicker;
import net.sf.latexdraw.instruments.robot.FxRobotListSelection;
import net.sf.latexdraw.instruments.robot.FxRobotSpinner;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Before;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public abstract class TestShapePropGUI<T extends ShapePropertyCustomiser> extends TestLatexdrawGUI implements
	FxRobotColourPicker, FxRobotListSelection, FxRobotSpinner {
	protected Pencil pencil;
	protected Hand hand;
	protected IDrawing drawing;
	protected T ins;

	protected final GUIVoidCommand pencilCreatesRec = () -> pencil.setCurrentChoice(EditionChoice.RECT);
	protected final GUIVoidCommand pencilCreatesBezier = () -> pencil.setCurrentChoice(EditionChoice.BEZIER_CURVE);
	protected final GUIVoidCommand pencilCreatesCircle = () -> pencil.setCurrentChoice(EditionChoice.CIRCLE);
	protected final GUIVoidCommand pencilCreatesText = () -> pencil.setCurrentChoice(EditionChoice.TEXT);
	protected final GUIVoidCommand pencilCreatesPic = () -> pencil.setCurrentChoice(EditionChoice.PICTURE);
	protected final GUIVoidCommand pencilCreatesArc = () -> pencil.setCurrentChoice(EditionChoice.CIRCLE_ARC);
	protected final GUIVoidCommand pencilCreatesAxes = () -> pencil.setCurrentChoice(EditionChoice.AXES);
	protected final GUIVoidCommand pencilCreatesDot = () -> pencil.setCurrentChoice(EditionChoice.DOT);
	protected final GUIVoidCommand pencilCreatesFreehand = () -> pencil.setCurrentChoice(EditionChoice.FREE_HAND);
	protected final GUIVoidCommand pencilCreatesGrid = () -> pencil.setCurrentChoice(EditionChoice.GRID);
	protected final GUIVoidCommand pencilCreatesPlot = () -> pencil.setCurrentChoice(EditionChoice.PLOT);

	protected final GUIVoidCommand updateIns = () -> {
		Platform.runLater(() -> ins.update());
		WaitForAsyncUtils.waitForFxEvents();
	};
	protected final GUIVoidCommand checkInsActivated = () -> assertTrue(ins.isActivated());
	protected final GUIVoidCommand checkInsDeactivated = () -> assertFalse(ins.isActivated());

	protected final GUIVoidCommand activatePencil = () -> {
		pencil.setActivated(true);
		when(hand.isActivated()).thenReturn(false);
	};

	protected final GUIVoidCommand activateHand = () -> {
		when(pencil.isActivated()).thenReturn(false);
		hand.setActivated(true);
	};

	protected final GUIVoidCommand selectionAddRec = () -> {
		final IShape sh = ShapeFactory.INST.createRectangle();
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddArc = () -> {
		final IShape sh = ShapeFactory.INST.createCircleArc();
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddDot = () -> {
		final IShape sh = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddAxes = () -> {
		final IShape sh = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddBezier = () -> {
		final IShape sh = ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint(1d, 2d)));
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddFreehand = () -> {
		final IFreehand sh = ShapeFactory.INST.createFreeHand(Arrays.asList(
			ShapeFactory.INST.createPoint(),
			ShapeFactory.INST.createPoint(1d, 2d)));
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddGrid = () -> {
		final IShape sh = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddText = () -> {
		final IShape sh = ShapeFactory.INST.createText();
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddPlot = () -> {
		final IShape sh = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, 10, "x", false);
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	@Before
	public void setUp() {
		pencil = injector.getInstance(Pencil.class);
		hand = injector.getInstance(Hand.class);
		drawing = injector.getInstance(IDrawing.class);
	}
}
