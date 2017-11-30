package net.sf.latexdraw.actions;

import net.sf.latexdraw.actions.shape.AddShape;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IShape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAddShape extends TestUndoableAction<AddShape, IShape> {
	@Override
	protected AddShape createAction() {
		return new AddShape();
	}

	@Override
	protected void checkUndo() {
		assertFalse(drawing.contains(memento));
		assertTrue(drawing.isEmpty());
	}

	@Override
	protected void configCorrectAction() {
		memento = ShapeFactory.INST.createRectangle();
		action.setDrawing(drawing);
		action.setShape(memento);
	}

	@Override
	protected void checkDo() {
		assertTrue(drawing.contains(memento));
		assertEquals(1, drawing.size());
	}
}
