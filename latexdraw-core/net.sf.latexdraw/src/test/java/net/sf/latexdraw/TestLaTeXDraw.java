package net.sf.latexdraw;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.Page;
import net.sf.latexdraw.view.jfx.Canvas;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.malai.command.CommandsRegistry;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TestLaTeXDraw extends ApplicationTest {
	LaTeXDraw app;

	@BeforeClass
	public static void beforeAll() throws Exception {
		Canvas.setMargins(20);
		Canvas.setDefaultPage(Page.HORIZONTAL);
		launch(LaTeXDraw.class);
	}

	@Before
	public void setUp() {
		CommandsRegistry.INSTANCE.clear();
		BadaboomCollector.INSTANCE.clear();
		app = LaTeXDraw.getInstance();
		while(!app.getMainStage().isShowing()) {
			sleep(500L);
		}
		WaitForAsyncUtils.waitForFxEvents();
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

	@Test
	public void testMoveViewPort() {
		final ScrollPane pane = lookup("#scrollPane").query();
		final double hvalue = pane.getHvalue();
		final double vvalue = pane.getVvalue();
		drag("#canvas", MouseButton.MIDDLE).dropBy(100d, 200d);
		WaitForAsyncUtils.waitForFxEvents();
		assertThat(hvalue, Matchers.greaterThan(pane.getHvalue()));
		assertThat(vvalue, Matchers.greaterThan(pane.getVvalue()));
	}
}
