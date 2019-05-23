package net.sf.latexdraw.instrument;

import java.util.Arrays;
import net.sf.latexdraw.instrument.robot.FxRobotColourPicker;
import net.sf.latexdraw.instrument.robot.FxRobotListSelection;
import net.sf.latexdraw.instrument.robot.FxRobotSpinner;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.service.EditingService;
import org.junit.Before;

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

	protected final CmdFXVoid pencilCreatesRec = () -> editing.setCurrentChoice(EditionChoice.RECT);
	protected final CmdFXVoid pencilCreatesBezier = () -> editing.setCurrentChoice(EditionChoice.BEZIER_CURVE);
	protected final CmdFXVoid pencilCreatesCircle = () -> editing.setCurrentChoice(EditionChoice.CIRCLE);
	protected final CmdFXVoid pencilCreatesText = () -> editing.setCurrentChoice(EditionChoice.TEXT);
	protected final CmdFXVoid pencilCreatesPic = () -> editing.setCurrentChoice(EditionChoice.PICTURE);
	protected final CmdFXVoid pencilCreatesArc = () -> editing.setCurrentChoice(EditionChoice.CIRCLE_ARC);
	protected final CmdFXVoid pencilCreatesAxes = () -> editing.setCurrentChoice(EditionChoice.AXES);
	protected final CmdFXVoid pencilCreatesDot = () -> editing.setCurrentChoice(EditionChoice.DOT);
	protected final CmdFXVoid pencilCreatesFreehand = () -> editing.setCurrentChoice(EditionChoice.FREE_HAND);
	protected final CmdFXVoid pencilCreatesGrid = () -> editing.setCurrentChoice(EditionChoice.GRID);
	protected final CmdFXVoid pencilCreatesPlot = () -> editing.setCurrentChoice(EditionChoice.PLOT);

	protected final CmdFXVoid updateIns = () -> ins.update();
	protected final CmdVoid checkInsActivated = () -> assertTrue(ins.isActivated());
	protected final CmdVoid checkInsDeactivated = () -> assertFalse(ins.isActivated());

	protected final CmdFXVoid activatePencil = () -> {
		pencil.setActivated(true);
		when(hand.isActivated()).thenReturn(false);
	};

	protected final CmdFXVoid activateHand = () -> {
		when(pencil.isActivated()).thenReturn(false);
		hand.setActivated(true);
	};

	protected final CmdFXVoid selectionAddRec = () -> {
		final Shape sh = ShapeFactory.INST.createRectangle();
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final CmdFXVoid selectionAddArc = () -> {
		final Shape sh = ShapeFactory.INST.createCircleArc();
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final CmdFXVoid selectionAddDot = () -> {
		final Shape sh = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final CmdFXVoid selectionAddAxes = () -> {
		final Shape sh = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final CmdFXVoid selectionAddBezier = () -> {
		final Shape sh = ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint(1d, 2d)));
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final CmdFXVoid selectionAddFreehand = () -> {
		final Freehand sh = ShapeFactory.INST.createFreeHand(Arrays.asList(
			ShapeFactory.INST.createPoint(),
			ShapeFactory.INST.createPoint(1d, 2d)));
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final CmdFXVoid selectionAddGrid = () -> {
		final Shape sh = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final CmdFXVoid selectionAddText = () -> {
		final Shape sh = ShapeFactory.INST.createText();
		drawing.addShape(sh);
		drawing.getSelection().addShape(sh);
	};

	protected final CmdFXVoid selectionAddPlot = () -> {
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
