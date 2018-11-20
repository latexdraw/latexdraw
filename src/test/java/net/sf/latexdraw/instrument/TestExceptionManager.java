package net.sf.latexdraw.instrument;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
import javafx.scene.Node;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestExceptionManager extends TestLatexdrawGUI {
	ExceptionsManager manager;

	@Override
	protected Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				bindToInstance(Injector.class, this);
				bindAsEagerSingleton(PreferencesService.class);
				bindWithCommand(ResourceBundle.class, PreferencesService.class, pref -> pref.getBundle());
				bindAsEagerSingleton(ExceptionsManager.class);
				bindToInstance(BadaboomController.class, Mockito.mock(BadaboomController.class));
			}
		};
	}

	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/Error.fxml";
	}

	@Before
	public void setUp() {
		manager = injector.getInstance(ExceptionsManager.class);
	}

	@Override
	@After
	public void tearDown() throws TimeoutException {
		super.tearDown();
		BadaboomCollector.INSTANCE.clear();
	}

	private Node getButton() {
		return find("#exceptionB");
	}

	@Test
	public void testNotActivatedOnNoCrash() {
		assertFalse(manager.isActivated());
	}

	@Test
	public void testActivatedOnCrash() {
		BadaboomCollector.INSTANCE.add(new IllegalArgumentException());
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(manager.isActivated());
		assertFalse(getButton().isDisabled());
	}

	@Test
	public void testGetStageCreatesStage() {
		try {
			final Field field = ExceptionsManager.class.getDeclaredField("stageEx");
			field.setAccessible(true);
			field.set(manager, null);
		}catch(final IllegalAccessException | NoSuchFieldException ex) {
			fail(ex.getMessage());
		}
		Platform.runLater(() -> manager.getStageEx());
		WaitForAsyncUtils.waitForFxEvents();
		assertNotNull(manager.getStageEx());
	}

	@Test
	public void testClickShowErrorStage() {
		BadaboomCollector.INSTANCE.add(new IllegalArgumentException());
		clickOn(getButton());
		waitFXEvents.execute();
		final AtomicBoolean visible = new AtomicBoolean(false);
		Platform.runLater(() -> visible.set(manager.getStageEx().isShowing()));
		waitFXEvents.execute();
		assertTrue(visible.get());
	}
}
