package net.sf.latexdraw.commands.shape;

import net.sf.latexdraw.commands.TestCommand;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TestCopyShapes extends TestCommand<CopyShapes> {
	IRectangle shape;

	@Override
	protected CopyShapes createCmd() {
		return new CopyShapes(new SelectShapes());
	}

	@Override
	protected void configCorrectCmd() {
		shape = ShapeFactory.INST.createRectangle();
		cmd.selection.setDrawing(Mockito.mock(IDrawing.class));
		cmd.selection.setShape(shape);
	}

	@Override
	protected void checkDo() {
		assertNotNull(cmd.copiedShapes);
		assertEquals(1, cmd.copiedShapes.size());
		assertThat(shape, not(sameInstance(cmd.copiedShapes.get(0))));
	}

	@Test
	public void testGetSelection() {
		assertEquals(cmd.selection, cmd.getSelection());
	}

	@Test
	public void testUnregistered() {
		assertTrue(cmd.unregisteredBy(createCmd()));
	}

	@Test
	public void testUnregisteredKO() {
		assertFalse(cmd.unregisteredBy(null));
	}
}
