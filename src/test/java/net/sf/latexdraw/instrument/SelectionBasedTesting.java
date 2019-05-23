package net.sf.latexdraw.instrument;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.latexdraw.command.shape.SelectShapes;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.malai.command.CmdHandler;
import org.malai.command.CommandsRegistry;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
abstract class SelectionBasedTesting<T extends ShapePropertyCustomiser> extends TestShapePropGUI<T> {
	@Mock CmdHandler handler;

	final CmdFXVoid selectTwoShapes = () -> {
		drawing.addShape(ShapeFactory.INST.createCircle());
		drawing.addShape(ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(20, 30), 10));
		drawing.setSelection(Arrays.asList(drawing.getShapeAt(0).orElseThrow(), drawing.getShapeAt(1).orElseThrow()));
		final SelectShapes cmd = new SelectShapes(drawing);
		cmd.addShape(drawing.getShapeAt(0).orElseThrow());
		cmd.addShape(drawing.getShapeAt(1).orElseThrow());
		CommandsRegistry.INSTANCE.addCommand(cmd, handler);
		ins.update();
	};

	final CmdFXVoid selectThreeShapes = () -> {
		drawing.addShape(ShapeFactory.INST.createCircle(ShapeFactory.INST.createPoint(1, 3), 11));
		drawing.addShape(ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(270, 335), 13));
		drawing.addShape(ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(412, 711), 15));
		drawing.setSelection(Arrays.asList(drawing.getShapeAt(0).orElseThrow(), drawing.getShapeAt(1).orElseThrow(), drawing.getShapeAt(2).orElseThrow()));
		final SelectShapes cmd = new SelectShapes(drawing);
		cmd.addShape(drawing.getShapeAt(0).orElseThrow());
		cmd.addShape(drawing.getShapeAt(1).orElseThrow());
		cmd.addShape(drawing.getShapeAt(2).orElseThrow());
		CommandsRegistry.INSTANCE.addCommand(cmd, handler);
		ins.update();
	};

	final CmdFXVoid selectOneShape = () -> {
		drawing.addShape(ShapeFactory.INST.createCircle());
		drawing.setSelection(Collections.singletonList(drawing.getShapeAt(-1).orElseThrow()));
		final SelectShapes cmd = new SelectShapes(drawing);
		cmd.addShape(drawing.getShapeAt(-1).orElseThrow());
		CommandsRegistry.INSTANCE.addCommand(cmd, handler);
		ins.update();
	};

	final Cmd<List<Integer>> selectShapeAt = indexes -> {
		final List<Shape> selectedShapes = indexes.stream().map(i -> drawing.getShapeAt(i).orElseThrow()).collect(Collectors.toList());
		drawing.setSelection(selectedShapes);
		final SelectShapes cmd = new SelectShapes(drawing);
		selectedShapes.forEach(sh -> cmd.addShape(sh));
		CommandsRegistry.INSTANCE.addCommand(cmd, handler);
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
