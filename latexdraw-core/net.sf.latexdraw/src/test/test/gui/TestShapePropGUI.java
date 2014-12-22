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

public abstract class TestShapePropGUI<T extends ShapePropertyCustomiser> extends TestLatexdrawGUI 
			implements FxRobotColourPicker, FxRobotListSelection, FxRobotSpinner {
	protected Pencil pencil;
	protected Hand hand;
	protected T ins;
	
	final protected GUICommand pencilCreatesRec = () -> pencil.setCurrentChoice(EditionChoice.RECT);
	final protected GUICommand pencilCreatesBezier = () -> pencil.setCurrentChoice(EditionChoice.BEZIER_CURVE); 
	final protected GUICommand pencilCreatesCircle = () -> pencil.setCurrentChoice(EditionChoice.CIRCLE);
	final protected GUICommand pencilCreatesText = () -> pencil.setCurrentChoice(EditionChoice.TEXT);
	final protected GUICommand pencilCreatesPic = () -> pencil.setCurrentChoice(EditionChoice.PICTURE);
	final protected GUICommand pencilCreatesArc = () -> pencil.setCurrentChoice(EditionChoice.CIRCLE_ARC);

	final protected GUICommand updateIns = () -> {
		Platform.runLater(() ->  ins.update());
		WaitForAsyncUtils.waitForFxEvents();
	};
	final protected GUICommand checkInsActivated = () -> assertTrue(ins.isActivated());
	final protected GUICommand checkInsDeactivated = () -> assertFalse(ins.isActivated());
	
	final protected GUICommand activatePencil = () -> {
		//when(pencil.isActivated()).thenReturn(true);
		pencil.setActivated(true);
		when(hand.isActivated()).thenReturn(false);
	};
	
	final protected GUICommand activateHand = () -> {
		when(pencil.isActivated()).thenReturn(false);
//		when(hand.isActivated()).thenReturn(true);
		hand.setActivated(true);
	};
	
//	final protected GUICommand pencilGroupParams = () -> {
//		IGroup g = ShapeFactory.createGroup();
//		g.addShape(ShapeFactory.createRectangle());
//		g.addShape(ShapeFactory.createDot(ShapeFactory.createPoint()));
//		g.addShape(ShapeFactory.createGrid(ShapeFactory.createPoint()));
//		g.addShape(ShapeFactory.createAxes(ShapeFactory.createPoint()));
//		g.addShape(ShapeFactory.createText());
//		g.addShape(ShapeFactory.createCircleArc());
//		g.addShape(ShapeFactory.createPolyline());
//		g.addShape(ShapeFactory.createBezierCurve());
//		g.addShape(ShapeFactory.createFreeHand());
//		g.addShape(ShapeFactory.createPlot(ShapeFactory.createPoint(), 1, 10, "x", false));
//		when(pencil.getGroupParams()).thenReturn(g);
//	};
	
	final protected GUICommand selectionAddRec = () -> {
		IShape sh = ShapeFactory.createRectangle();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};
	
	final protected GUICommand selectionAddArc = () -> {
		IShape sh = ShapeFactory.createCircleArc();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};
	
	final protected GUICommand selectionAddBezier = () -> {
		IShape sh = ShapeFactory.createBezierCurve();
		hand.getCanvas().getDrawing().addShape(sh);
		hand.getCanvas().getDrawing().getSelection().addShape(sh);
	};
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
		pencil	= (Pencil)guiceFactory.call(Pencil.class);
		hand	= (Hand)guiceFactory.call(Hand.class);
	}
	
	@Test public void testGetPencil() {
		assertNotNull(ins.getPencil());
	}
	
	@Test public void testGetHand() {
		assertNotNull(ins.getHand());
	}
}
