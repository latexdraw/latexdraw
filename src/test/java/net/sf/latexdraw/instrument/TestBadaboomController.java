package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertFalse;

public class TestBadaboomController extends TestLatexdrawGUI {
	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/Badaboom.fxml";
	}

	@Override
	protected Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
				bindAsEagerSingleton(BadaboomController.class);
				bindAsEagerSingleton(PreferencesService.class);
			}
		};
	}

	@Test
	public void testTableNotEmpty() {
		Platform.runLater(() -> BadaboomCollector.INSTANCE.add(new IllegalArgumentException("test")));
		WaitForAsyncUtils.waitForFxEvents();
		final TableView<?> table = find("#table");
		assertFalse(table.getItems().isEmpty());
	}

	@Test
	public void testTableClickErrorShowsIt() {
		Platform.runLater(() -> BadaboomCollector.INSTANCE.add(new IllegalArgumentException("test exception")));
		WaitForAsyncUtils.waitForFxEvents();
		Platform.runLater(() -> {
			final TableView<?> table = find("#table");
			table.getSelectionModel().select(0);
		});
		WaitForAsyncUtils.waitForFxEvents();
		final TextArea stack = find("#stack");
		assertFalse(stack.getText().isEmpty());
	}
}
