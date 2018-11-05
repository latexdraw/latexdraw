package net.sf.latexdraw.command.shape;

import net.sf.latexdraw.command.TestUndoableCommand;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Rectangle;

import static org.junit.Assert.assertEquals;

public class TestDeleteShapes extends TestUndoableCommand<DeleteShapes, Object> {
	Rectangle shape1;
	Rectangle shape2;
	Rectangle shape3;

	@Override
	protected void checkUndo() {
		assertEquals(3, drawing.size());
		assertEquals(shape1, drawing.getShapeAt(0));
		assertEquals(shape2, drawing.getShapeAt(1));
		assertEquals(shape3, drawing.getShapeAt(2));
	}

	@Override
	protected void configCorrectCmd() {
		cmd = new DeleteShapes(drawing);
		shape1 = ShapeFactory.INST.createRectangle();
		shape2 = ShapeFactory.INST.createRectangle();
		shape3 = ShapeFactory.INST.createRectangle();
		drawing.addShape(shape1);
		drawing.addShape(shape2);
		drawing.addShape(shape3);
		cmd.setShape(shape1);
		cmd.addShape(shape3);
	}

	@Override
	protected void checkDo() {
		assertEquals(1, drawing.size());
		assertEquals(shape2, drawing.getShapeAt(0));
	}
}
