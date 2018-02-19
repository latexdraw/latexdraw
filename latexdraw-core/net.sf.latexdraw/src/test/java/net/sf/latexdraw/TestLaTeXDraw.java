package net.sf.latexdraw;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.malai.action.ActionsRegistry;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestLaTeXDraw extends ApplicationTest {
	LaTeXDraw app;

	@BeforeClass
	public static void beforeAll() throws Exception {
		launch(LaTeXDraw.class);
	}

	@Before
	public void setUp() {
		ActionsRegistry.INSTANCE.clear();
		BadaboomCollector.INSTANCE.clear();
		app = LaTeXDraw.getInstance();
		while(!app.getMainStage().isShowing()) {
			sleep(500L);
		}
		sleep(1000L);
	}

	@Test
	public void testNoException() {
		assertTrue(BadaboomCollector.INSTANCE.isEmpty());
	}

	@Test
	public void testNotModified() {
		assertFalse(app.isModified());
	}

	@Test
	public void testGetInstrument() {
		assertNotNull(app.getInstruments());
		assertFalse(app.getInstruments().isEmpty());
	}

	@Test
	public void testGetAdditionalComponents() {
		assertNotNull(app.getAdditionalComponents());
		assertFalse(app.getAdditionalComponents().isEmpty());
	}

	@Test
	public void testGetInjector() {
		assertNotNull(app.getInjector());
	}

	@Test
	public void testGetInstanceCallBack() {
		assertNotNull(app.getInstanceCallBack());
	}

	@Test
	public void testGetMainStage() {
		assertNotNull(app.getMainStage());
	}

	@Test
	public void testReinit() {
		app.reinit();
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(app.isModified());
		assertTrue(BadaboomCollector.INSTANCE.isEmpty());
	}
}
