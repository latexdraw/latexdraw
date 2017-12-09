package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.actions.TestUndoableAction;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IShape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAddShape extends TestUndoableAction<AddShape, IShape> {
	@Override
	protected AddShape createAction() {
		return new AddShape(ShapeFactory.INST.createRectangle(), drawing);
	}

	@Override
	protected void checkUndo() {
		assertFalse(drawing.contains(memento));
		assertTrue(drawing.isEmpty());
	}

	@Override
	protected void configCorrectAction() {
		memento = action.getShape().orElse(null);
	}

	@Override
	protected void checkDo() {
		assertTrue(drawing.contains(memento));
		assertEquals(1, drawing.size());
	}
}
