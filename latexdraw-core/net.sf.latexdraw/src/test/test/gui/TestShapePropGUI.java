package test.gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import javafx.application.Platform;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.instruments.EditionChoice;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapePropertyCustomiser;

import org.junit.Before;
import org.junit.Test;
import org.testfx.util.WaitForAsyncUtils;

import test.gui.robot.FxRobotColourPicker;
import test.gui.robot.FxRobotListSelection;
import test.gui.robot.FxRobotSpinner;

public abstract class TestShapePropGUI<T extends ShapePropertyCustomiser> extends TestLatexdrawGUI implements FxRobotColourPicker, FxRobotListSelection, FxRobotSpinner {
	protected Pencil pencil;
	protected Hand hand;
	protected T ins;

	final protected GUIVoidCommand pencilCreatesRec = () -> pencil.setCurrentChoice(EditionChoice.RECT);
	final protected GUIVoidCommand pencilCreatesBezier = () -> pencil.setCurrentChoice(EditionChoice.BEZIER_CURVE);
	final protected GUIVoidCommand pencilCreatesCircle = () -> pencil.setCurrentChoice(EditionChoice.CIRCLE);
	final protected GUIVoidCommand pencilCreatesText = () -> pencil.setCurrentChoice(EditionChoice.TEXT);
	final protected GUIVoidCommand pencilCreatesPic = () -> pencil.setCurrentChoice(EditionChoice.PICTURE);
	final protected GUIVoidCommand pencilCreatesArc = () -> pencil.setCurrentChoice(EditionChoice.CIRCLE_ARC);
	final protected GUIVoidCommand pencilCreatesAxes = () -> pencil.setCurrentChoice(EditionChoice.AXES);
	final protected GUIVoidCommand pencilCreatesDot = () -> pencil.setCurrentChoice(EditionChoice.DOT);
	final protected GUIVoidCommand pencilCreatesFreehand = () -> pencil.setCurrentChoice(EditionChoice.FREE_HAND);
	final protected GUIVoidCommand pencilCreatesGrid = () -> pencil.setCurrentChoice(EditionChoice.GRID);
	final protected GUIVoidCommand pencilCreatesPlot = () -> pencil.setCurrentChoice(EditionChoice.PLOT);

	final protected GUIVoidCommand updateIns = () -> {
		Platform.runLater(() -> ins.update());
		WaitForAsyncUtils.waitForFxEvents();
	};
	final protected GUIVoidCommand checkInsActivated = () -> assertTrue(ins.isActivated());
	final protected GUIVoidCommand checkInsDeactivated = () -> assertFalse(ins.isActivated());

	final protected GUIVoidCommand activatePencil = () -> {
		// when(pencil.isActivated()).thenReturn(true);
		pencil.setActivated(true);
		when(hand.isActivated()).thenReturn(false);
	};

	final protected GUIVoidCommand activateHand = () -> {
		when(pencil.isActivated()).thenReturn(false);
		// when(hand.isActivated()).thenReturn(true);
		hand.setActivated(true);
	};

	// final protected GUICommand pencilGroupParams = () -> {
	// IGroup g = ShapeFactory.createGroup();
	// g.addShape(ShapeFactory.createRectangle());
	// g.addShape(ShapeFactory.createDot(ShapeFactory.createPoint()));
	// g.addShape(ShapeFactory.createGrid(ShapeFactory.createPoint()));
	// g.addShape(ShapeFactory.createAxes(ShapeFactory.createPoint()));
	// g.addShape(ShapeFactory.createText());
	// g.addShape(ShapeFactory.createCircleArc());
	// g.addShape(ShapeFactory.createPolyline());
	// g.addShape(ShapeFactory.createBezierCurve());
	// g.addShape(ShapeFactory.createFreeHand());
	// g.addShape(ShapeFactory.createPlot(ShapeFactory.createPoint(), 1, 10, "x", false));
	// when(pencil.getGroupParams()).thenReturn(g);
	// };

	final protected GUIVoidCommand selectionAddRec = () -> {
		IShape sh = ShapeFactory.createRectangle();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	final protected GUIVoidCommand selectionAddArc = () -> {
		IShape sh = ShapeFactory.createCircleArc();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	final protected GUIVoidCommand selectionAddDot = () -> {
		IShape sh = ShapeFactory.createDot(ShapeFactory.createPoint());
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	final protected GUIVoidCommand selectionAddAxes = () -> {
		IShape sh = ShapeFactory.createAxes(ShapeFactory.createPoint());
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	final protected GUIVoidCommand selectionAddBezier = () -> {
		IShape sh = ShapeFactory.createBezierCurve();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	final protected GUIVoidCommand selectionAddFreehand = () -> {
		IShape sh = ShapeFactory.createFreeHand();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	final protected GUIVoidCommand selectionAddGrid = () -> {
		IShape sh = ShapeFactory.createGrid(ShapeFactory.createPoint());
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	final protected GUIVoidCommand selectionAddText = () -> {
		IShape sh = ShapeFactory.createText();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	final protected GUIVoidCommand selectionAddPlot = () -> {
		IShape sh = ShapeFactory.createPlot(ShapeFactory.createPoint(), 1, 10, "x", false);
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	@Override
	@Before
	public void setUp() {
		super.setUp();
		pencil = (Pencil)guiceFactory.call(Pencil.class);
		hand = (Hand)guiceFactory.call(Hand.class);
	}

	@Test
	public void testGetPencil() {
		assertNotNull(ins.getPencil());
	}

	@Test
	public void testGetHand() {
		assertNotNull(ins.getHand());
	}
}
