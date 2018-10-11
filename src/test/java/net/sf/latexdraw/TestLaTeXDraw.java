package net.sf.latexdraw;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.Page;
import net.sf.latexdraw.view.jfx.Canvas;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.command.CommandsRegistry;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


//@ExtendWith(ApplicationExtension.class)
public class TestLaTeXDraw {
	LaTeXDraw app;

	@BeforeAll
	static void beforeAll() throws TimeoutException {
		Canvas.setMargins(20);
		Canvas.setDefaultPage(Page.HORIZONTAL);
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(LaTeXDraw::new);
		WaitForAsyncUtils.waitForFxEvents();
		WaitForAsyncUtils.waitFor(10L, TimeUnit.SECONDS, LaTeXDraw.getInstance().getMainStage().showingProperty());
	}

	@AfterAll
	static void afterAll() throws TimeoutException {
		FxToolkit.cleanupApplication(LaTeXDraw.getInstance());
	}

	@BeforeEach
	public void setUp() {
		CommandsRegistry.INSTANCE.clear();
		BadaboomCollector.INSTANCE.clear();
		app = LaTeXDraw.getInstance();
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

	//	@Test
	//	public void testMoveViewPort() {
	//		Platform.runLater(() -> app.getMainStage().showAndWait());
	//		WaitForAsyncUtils.waitForFxEvents();
	//		final ScrollPane pane = lookup("#scrollPane").query();
	//		final double hvalue = pane.getHvalue();
	//		final double vvalue = pane.getVvalue();
	//		drag("#canvas", MouseButton.MIDDLE).dropBy(100d, 200d);
	//		WaitForAsyncUtils.waitForFxEvents();
	//		assertThat(hvalue, Matchers.greaterThan(pane.getHvalue()));
	//		assertThat(vvalue, Matchers.greaterThan(pane.getVvalue()));
	//	}
}
