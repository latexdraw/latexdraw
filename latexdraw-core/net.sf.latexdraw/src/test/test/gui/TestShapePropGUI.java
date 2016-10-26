package test.gui;

import javafx.application.Platform;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IShape;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public abstract class TestShapePropGUI<T extends ShapePropertyCustomiser> extends TestLatexdrawGUI implements FxRobotColourPicker, FxRobotListSelection, FxRobotSpinner {
	protected Pencil pencil;
	protected Hand hand;
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
		// when(pencil.isActivated()).thenReturn(true);
		pencil.setActivated(true);
		when(hand.isActivated()).thenReturn(false);
	};

	protected final GUIVoidCommand activateHand = () -> {
		when(pencil.isActivated()).thenReturn(false);
		// when(hand.isActivated()).thenReturn(true);
		hand.setActivated(true);
	};

	// protected final GUICommand pencilGroupParams = () -> {
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

	protected final GUIVoidCommand selectionAddRec = () -> {
		IShape sh = ShapeFactory.createRectangle();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddArc = () -> {
		IShape sh = ShapeFactory.createCircleArc();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddDot = () -> {
		IShape sh = ShapeFactory.createDot(ShapeFactory.createPoint());
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddAxes = () -> {
		IShape sh = ShapeFactory.createAxes(ShapeFactory.createPoint());
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddBezier = () -> {
		IShape sh = ShapeFactory.createBezierCurve();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddFreehand = () -> {
		IShape sh = ShapeFactory.createFreeHand();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddGrid = () -> {
		IShape sh = ShapeFactory.createGrid(ShapeFactory.createPoint());
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddText = () -> {
		IShape sh = ShapeFactory.createText();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};

	protected final GUIVoidCommand selectionAddPlot = () -> {
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
