package net.sf.latexdraw.gui;

import java.util.Arrays;
import java.util.Collections;
import net.sf.latexdraw.actions.shape.SelectShapes;
import net.sf.latexdraw.instruments.ShapePropertyCustomiser;
import net.sf.latexdraw.models.ShapeFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.malai.action.ActionHandler;
import org.malai.action.ActionsRegistry;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
abstract class SelectionBasedTesting<T extends ShapePropertyCustomiser> extends TestShapePropGUI<T> {
	@Mock ActionHandler handler;

	final GUIVoidCommand selectTwoShapes = () -> {
		drawing.addShape(ShapeFactory.INST.createCircle());
		drawing.addShape(ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(20, 30), 10));
		drawing.setSelection(Arrays.asList(drawing.getShapeAt(0), drawing.getShapeAt(1)));
		SelectShapes action = new SelectShapes();
		action.addShape(drawing.getShapeAt(0));
		action.addShape(drawing.getShapeAt(1));
		ActionsRegistry.INSTANCE.addAction(action, handler);
		ins.update();
	};

	final GUIVoidCommand selectThreeShapes = () -> {
		drawing.addShape(ShapeFactory.INST.createCircle(ShapeFactory.INST.createPoint(1, 3), 11));
		drawing.addShape(ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(270, 335), 13));
		drawing.addShape(ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(412, 711), 15));
		drawing.setSelection(Arrays.asList(drawing.getShapeAt(0), drawing.getShapeAt(1), drawing.getShapeAt(2)));
		SelectShapes action = new SelectShapes();
		action.addShape(drawing.getShapeAt(0));
		action.addShape(drawing.getShapeAt(1));
		action.addShape(drawing.getShapeAt(2));
		ActionsRegistry.INSTANCE.addAction(action, handler);
		ins.update();
	};

	final GUIVoidCommand selectOneShape = () -> {
		drawing.addShape(ShapeFactory.INST.createCircle());
		drawing.setSelection(Collections.singletonList(drawing.getShapeAt(0)));
		SelectShapes action = new SelectShapes();
		action.addShape(drawing.getShapeAt(0));
		ActionsRegistry.INSTANCE.addAction(action, handler);
		ins.update();
	};


	@Override
	@Before
	public void setUp() {
		super.setUp();
		when(pencil.isActivated()).thenReturn(false);
		when(hand.isActivated()).thenReturn(true);
	}
}
