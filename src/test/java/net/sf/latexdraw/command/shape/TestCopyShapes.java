package net.sf.latexdraw.command.shape;

import java.util.Optional;
import net.sf.latexdraw.command.TestCommand;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Rectangle;
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
	Rectangle shape;

	@Override
	protected void configCorrectCmd() {
		final SelectShapes selectShapes = new SelectShapes(Mockito.mock(Drawing.class));
		cmd = new CopyShapes(Optional.of(selectShapes));
		shape = ShapeFactory.INST.createRectangle();
		selectShapes.setShape(shape);
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
		assertTrue(cmd.unregisteredBy(new CopyShapes(Optional.of(new SelectShapes(Mockito.mock(Drawing.class))))));
	}

	@Test
	public void testUnregisteredKO() {
		assertFalse(cmd.unregisteredBy(null));
	}
}
