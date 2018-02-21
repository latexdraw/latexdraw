package net.sf.latexdraw.actions;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import org.junit.Before;
import org.junit.Test;
import org.malai.action.Action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class TestAction<T extends Action> {
	protected T action;
	protected IDrawing drawing;

	@Before
	public void setUp() {
		drawing = ShapeFactory.INST.createDrawing();
		action = createAction();
	}

	protected abstract T createAction();

	@Test
	public void testFlush() {
		action.flush();
	}

	protected abstract void configCorrectAction();

	protected abstract void checkDo();

	@Test
	public void testDo() {
		configCorrectAction();
		action.doIt();
		checkDo();
	}

	@Test
	public void testFlushAfterExecution() {
		configCorrectAction();
		action.doIt();
		action.flush();
	}

	@Test
	public void testCanDo() {
		configCorrectAction();
		assertTrue(action.canDo());
	}

	@Test
	public void testGetRegisterablePolicy() {
		assertEquals(Action.RegistrationPolicy.NONE, action.getRegistrationPolicy());
	}

	@Test
	public void testGetRegisterablePolicyOK() {
		action.doIt();
		action.done();
		assertEquals(Action.RegistrationPolicy.LIMITED, action.getRegistrationPolicy());
	}

	@Test
	public void testHadEffect() {
		configCorrectAction();
		action.doIt();
		action.done();
		assertTrue(action.hadEffect());
	}
}
