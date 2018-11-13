package net.sf.latexdraw.command.shape;

import java.util.Optional;
import net.sf.latexdraw.command.TestUndoableCommand;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Rectangle;

import static org.junit.Assert.assertEquals;

public class TestCutShapes extends TestUndoableCommand<CutShapes, Object> {
	Rectangle shape1;
	Rectangle shape2;
	Rectangle shape3;

	@Override
	protected void configCorrectCmd() {
		final SelectShapes selectShapes = new SelectShapes(drawing);
		cmd = new CutShapes(Optional.of(selectShapes));
		shape1 = ShapeFactory.INST.createRectangle();
		shape2 = ShapeFactory.INST.createRectangle();
		shape3 = ShapeFactory.INST.createRectangle();
		drawing.addShape(shape1);
		drawing.addShape(shape2);
		drawing.addShape(shape3);
		selectShapes.addShape(shape1);
		selectShapes.addShape(shape3);
	}

	@Override
	protected void checkDo() {
		assertEquals(1, drawing.size());
		assertEquals(shape2, drawing.getShapeAt(0).orElseThrow());
	}

	@Override
	protected void checkUndo() {
		assertEquals(3, drawing.size());
		assertEquals(shape1, drawing.getShapeAt(0).orElseThrow());
		assertEquals(shape2, drawing.getShapeAt(1).orElseThrow());
		assertEquals(shape3, drawing.getShapeAt(2).orElseThrow());
	}
}
