package net.sf.latexdraw.instrument;

import java.util.Arrays;
import javafx.application.Platform;
import net.sf.latexdraw.instrument.robot.FxRobotColourPicker;
import net.sf.latexdraw.instrument.robot.FxRobotListSelection;
import net.sf.latexdraw.instrument.robot.FxRobotSpinner;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.service.EditingService;
import org.junit.Before;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public abstract class TestShapePropGUI<T extends ShapePropertyCustomiser> extends TestLatexdrawGUI implements
	FxRobotColourPicker, FxRobotListSelection, FxRobotSpinner {
	protected EditingService editing;
	protected Pencil pencil;
	protected Hand hand;
	protected Drawing drawing;
	protected T ins;

	protected final GUIVoidCommand pencilCreatesRec = () -> editing.setCurrentChoice(EditionChoice.RECT);
	protected final GUIVoidCommand pencilCreatesBezier = () -> editing.setCurrentChoice(EditionChoice.BEZIER_CURVE);
	protected final GUIVoidCommand pencilCreatesCircle = () -> editing.setCurrentChoice(EditionChoice.CIRCLE);
	protected final GUIVoidCommand pencilCreatesText = () -> editing.setCurrentChoice(EditionChoice.TEXT);
	protected final GUIVoidCommand pencilCreatesPic = () -> editing.setCurrentChoice(EditionChoice.PICTURE);
	protected final GUIVoidCommand pencilCreatesArc = () -> editing.setCurrentChoice(EditionChoice.CIRCLE_ARC);
	protected final GUIVoidCommand pencilCreatesAxes = () -> editing.setCurrentChoice(EditionChoice.AXES);
	protected final GUIVoidCommand pencilCreatesDot = () -> editing.setCurrentChoice(EditionChoice.DOT);
	protected final GUIVoidCommand pencilCreatesFreehand = () -> editing.setCurrentChoice(EditionChoice.FREE_HAND);
	protected final GUIVoidCommand pencilCreatesGrid = () -> editing.setCurrentChoice(EditionChoice.GRID);
	protected final GUIVoidCommand pencilCreatesPlot = () -> editing.setCurrentChoice(EditionChoice.PLOT);

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
		final Shape sh = ShapeFactory.INST.createRectangle();
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddArc = () -> {
		final Shape sh = ShapeFactory.INST.createCircleArc();
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddDot = () -> {
		final Shape sh = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddAxes = () -> {
		final Shape sh = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddBezier = () -> {
		final Shape sh = ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint(1d, 2d)));
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddFreehand = () -> {
		final Freehand sh = ShapeFactory.INST.createFreeHand(Arrays.asList(
			ShapeFactory.INST.createPoint(),
			ShapeFactory.INST.createPoint(1d, 2d)));
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddGrid = () -> {
		final Shape sh = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddText = () -> {
		final Shape sh = ShapeFactory.INST.createText();
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddPlot = () -> {
		final Shape sh = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, 10, "x", false);
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	@Before
	public void setUp() {
		editing = injector.getInstance(EditingService.class);
		pencil = injector.getInstance(Pencil.class);
		hand = injector.getInstance(Hand.class);
		drawing = injector.getInstance(Drawing.class);
	}
}
