package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.control.Hyperlink;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;
import org.junit.Test;
import org.mockito.Mockito;

public class TestStatusBarController extends TestLatexdrawGUI {
	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/StatusBar.fxml";
	}

	@Override
	protected Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
				bindAsEagerSingleton(SystemService.class);
				bindAsEagerSingleton(LangService.class);
				bindToInstance(HostServices.class, Mockito.mock(HostServices.class));
				bindAsEagerSingleton(StatusBarController.class);
			}
		};
	}

	@Test
	public void testClickHyperlink() {
		final Hyperlink link = find("#link");
		Platform.runLater(() -> {
			link.setText("foo");
			link.setVisible(true);
		});
		waitFXEvents.execute();
		clickOn(link);
		waitFXEvents.execute();
		Mockito.verify(injector.getInstance(HostServices.class), Mockito.times(1)).showDocument("foo");
	}
}
