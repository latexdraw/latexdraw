package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.actions.TestUndoableAction;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCutShapes extends TestUndoableAction<CutShapes, Object> {
	IRectangle shape;
	IDrawing drawing;

	@Override
	protected CutShapes createAction() {
		return new CutShapes(new SelectShapes());
	}

	@Override
	protected void configCorrectAction() {
		shape = ShapeFactory.INST.createRectangle();
		drawing = ShapeFactory.INST.createDrawing();
		drawing.addShape(shape);
		action.selection.setDrawing(drawing);
		action.selection.setShape(shape);
	}

	@Override
	protected void checkDo() {
		assertTrue(drawing.isEmpty());
	}

	@Override
	protected void checkUndo() {
		assertEquals(1, drawing.size());
		assertEquals(shape, drawing.getShapeAt(0));
	}
}
