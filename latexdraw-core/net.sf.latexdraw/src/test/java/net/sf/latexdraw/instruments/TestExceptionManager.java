package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeoutException;
import javafx.application.Platform;
import javafx.scene.Node;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestExceptionManager extends TestLatexdrawGUI {
	ExceptionsManager manager;

	@Override
	protected Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(ExceptionsManager.class);
			}
		};
	}

	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/Error.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		manager = injector.getInstance(ExceptionsManager.class);
	}

	@Override
	@After
	public void tearDown() throws TimeoutException {
		super.tearDown();
		BadaboomCollector.INSTANCE.removeHandler(manager);
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
		assertTrue(manager.isActivated());
		assertFalse(getButton().isDisabled());
	}

	@Test
	public void testClickShowErrorStage() {
		BadaboomCollector.INSTANCE.add(new IllegalArgumentException());
		clickOn(getButton());
		waitFXEvents.execute();
		Platform.runLater(() -> assertTrue(manager.getStageEx().isShowing()));
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Test
	public void testActivatedOnAddedAsHandlerWithErrors() {
		BadaboomCollector.INSTANCE.removeHandler(manager);
		BadaboomCollector.INSTANCE.add(new IllegalArgumentException());
		manager.setActivated(false);
		BadaboomCollector.INSTANCE.addHandler(manager);
		assertTrue(manager.isActivated());
	}
}
