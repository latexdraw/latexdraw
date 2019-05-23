package net.sf.latexdraw.instrument;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.scene.Node;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
		Cmds.of(CmdFXVoid.of(() -> BadaboomCollector.INSTANCE.add(new IllegalArgumentException()))).execute();
		assertTrue(manager.isActivated());
		assertFalse(getButton().isDisabled());
	}

	@Test
	public void testGetStageCreatesStage() throws IllegalAccessException, NoSuchFieldException {
		final Field field = ExceptionsManager.class.getDeclaredField("stageEx");
		field.setAccessible(true);
		field.set(manager, null);
		Cmds.of(CmdFXVoid.of(() -> manager.getStageEx())).execute();
		assertNotNull(manager.getStageEx());
	}

	@Test
	public void testClickShowErrorStage() {
		BadaboomCollector.INSTANCE.add(new IllegalArgumentException());
		Cmds.of(() -> clickOn(getButton())).execute();
		final AtomicBoolean visible = new AtomicBoolean(false);
		Cmds.of(CmdFXVoid.of(() -> visible.set(manager.getStageEx().isShowing()))).execute();
		assertTrue(visible.get());
	}
}
