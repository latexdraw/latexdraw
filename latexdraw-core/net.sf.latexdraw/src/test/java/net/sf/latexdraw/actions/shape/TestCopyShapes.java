package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.actions.TestAction;
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

public class TestCopyShapes extends TestAction<CopyShapes> {
	IRectangle shape;

	@Override
	protected CopyShapes createAction() {
		return new CopyShapes(new SelectShapes());
	}

	@Override
	protected void configCorrectAction() {
		shape = ShapeFactory.INST.createRectangle();
		action.selection.setDrawing(Mockito.mock(IDrawing.class));
		action.selection.setShape(shape);
	}

	@Override
	protected void checkDo() {
		assertNotNull(action.copiedShapes);
		assertEquals(1, action.copiedShapes.size());
		assertThat(shape, not(sameInstance(action.copiedShapes.get(0))));
	}

	@Test
	public void testGetSelection() {
		assertEquals(action.selection, action.getSelection());
	}

	@Test
	public void testUnregistered() {
		assertTrue(action.unregisteredBy(createAction()));
	}

	@Test
	public void testUnregisteredKO() {
		assertFalse(action.unregisteredBy(null));
	}
}
