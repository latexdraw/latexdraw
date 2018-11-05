package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestHelper extends TestLatexdrawGUI {
	Helper helper;
	HostServices services;

	@Override
	public String getFXMLPathFromLatexdraw() {
		return "/fxml/Help.fxml";
	}

	@Override
	protected Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
				bindToInstance(Injector.class, this);
				bindAsEagerSingleton(SystemService.class);
				bindAsEagerSingleton(LangService.class);
				bindToInstance(HostServices.class, Mockito.mock(HostServices.class));
				bindAsEagerSingleton(ShortcutsController.class);
				bindAsEagerSingleton(AboutController.class);
				bindAsEagerSingleton(Helper.class);
			}
		};
	}

	@Before
	public void setUp() {
		helper = injector.getInstance(Helper.class);
		services = injector.getInstance(HostServices.class);
	}

	@Test
	public void testClickAboutFrame() {
		clickOn("#helpMenu").clickOn("#aboutItem");
		waitFXEvents.execute();
		final Stage frame = helper.getAboutFrame();
		assertTrue(frame.isShowing());
		Platform.runLater(() -> helper.getAboutFrame().close());
		waitFXEvents.execute();
		assertEquals(frame, helper.getAboutFrame());
	}

	@Test
	public void testClickShortCutsFrame() {
		clickOn("#helpMenu").clickOn("#shortcutItem");
		waitFXEvents.execute();
		final Stage frame = helper.getShortcutsFrame();
		assertTrue(frame.isShowing());
		Platform.runLater(() -> helper.getShortcutsFrame().close());
		waitFXEvents.execute();
		assertEquals(frame, helper.getShortcutsFrame());
	}

	@Test
	public void testClickDonateItemFrame() {
		clickOn("#helpMenu").clickOn("#donateItem");
		waitFXEvents.execute();
		Mockito.verify(services, Mockito.times(1)).showDocument("http://sourceforge.net/project/project_donations.php?group_id=156523");
	}

	@Test
	public void testClickManualItemFrame() {
		clickOn("#helpMenu").clickOn("#manuelItem");
		waitFXEvents.execute();
		Mockito.verify(services, Mockito.times(1)).showDocument("https://github.com/arnobl/latexdraw/wiki/Manual");
	}

	@Test
	public void testClickReportItemFrame() {
		clickOn("#helpMenu").clickOn("#reportBugItem");
		waitFXEvents.execute();
		Mockito.verify(services, Mockito.times(1)).showDocument("https://github.com/arnobl/latexdraw/wiki/Manual#how-to-report-a-bug");
	}

	@Test
	public void testClickForumItemFrame() {
		clickOn("#helpMenu").clickOn("#forumItem");
		waitFXEvents.execute();
		Mockito.verify(services, Mockito.times(1)).showDocument("https://sourceforge.net/p/latexdraw/discussion/");
	}
}
