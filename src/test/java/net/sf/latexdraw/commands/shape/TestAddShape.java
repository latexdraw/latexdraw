package net.sf.latexdraw.commands.shape;

import net.sf.latexdraw.commands.TestUndoableCommand;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IShape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAddShape extends TestUndoableCommand<AddShape, IShape> {
	@Override
	protected AddShape createCmd() {
		return new AddShape(ShapeFactory.INST.createRectangle(), drawing);
	}

	@Override
	protected void checkUndo() {
		assertFalse(drawing.contains(memento));
		assertTrue(drawing.isEmpty());
	}

	@Override
	protected void configCorrectCmd() {
		memento = cmd.getShape().orElse(null);
	}

	@Override
	protected void checkDo() {
		assertTrue(drawing.contains(memento));
		assertEquals(1, drawing.size());
	}
}
