package net.sf.latexdraw.command.shape;

import net.sf.latexdraw.command.TestUndoableCommand;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Shape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAddShape extends TestUndoableCommand<AddShape, Shape> {
	@Override
	protected void checkUndo() {
		assertFalse(drawing.contains(memento));
		assertTrue(drawing.isEmpty());
	}

	@Override
	protected void configCorrectCmd() {
		cmd = new AddShape(ShapeFactory.INST.createRectangle(), drawing);
		memento = cmd.getShape().orElse(null);
	}

	@Override
	protected void checkDo() {
		assertTrue(drawing.contains(memento));
		assertEquals(1, drawing.size());
	}
}
