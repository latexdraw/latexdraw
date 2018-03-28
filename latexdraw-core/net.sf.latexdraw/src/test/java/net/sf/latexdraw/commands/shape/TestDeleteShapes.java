package net.sf.latexdraw.commands.shape;

import net.sf.latexdraw.commands.TestUndoableCommand;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;

import static org.junit.Assert.assertEquals;

public class TestDeleteShapes extends TestUndoableCommand<DeleteShapes, Object> {
	IRectangle shape1;
	IRectangle shape2;
	IRectangle shape3;
	IDrawing drawing;

	@Override
	protected void checkUndo() {
		assertEquals(3, drawing.size());
		assertEquals(shape1, drawing.getShapeAt(0));
		assertEquals(shape2, drawing.getShapeAt(1));
		assertEquals(shape3, drawing.getShapeAt(2));
	}

	@Override
	protected DeleteShapes createCmd() {
		return new DeleteShapes();
	}

	@Override
	protected void configCorrectCmd() {
		shape1 = ShapeFactory.INST.createRectangle();
		shape2 = ShapeFactory.INST.createRectangle();
		shape3 = ShapeFactory.INST.createRectangle();
		drawing = ShapeFactory.INST.createDrawing();
		drawing.addShape(shape1);
		drawing.addShape(shape2);
		drawing.addShape(shape3);
		cmd.setDrawing(drawing);
		cmd.setShape(shape1);
		cmd.addShape(shape3);
	}

	@Override
	protected void checkDo() {
		assertEquals(1, drawing.size());
		assertEquals(shape2, drawing.getShapeAt(0));
	}
}
