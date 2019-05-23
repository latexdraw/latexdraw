package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.scene.control.Hyperlink;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Injector;
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
				bindAsEagerSingleton(PreferencesService.class);
				bindWithCommand(ResourceBundle.class, PreferencesService.class, pref -> pref.getBundle());
				bindToInstance(HostServices.class, Mockito.mock(HostServices.class));
				bindAsEagerSingleton(StatusBarController.class);
			}
		};
	}

	@Test
	public void testClickHyperlink() {
		final Hyperlink link = find("#link");
		Cmds.of(CmdFXVoid.of(() -> {
			link.setText("gridGapProp");
			link.setVisible(true);
		}), () -> clickOn(link)).execute();
		Mockito.verify(injector.getInstance(HostServices.class), Mockito.times(1)).showDocument("gridGapProp");
	}
}
