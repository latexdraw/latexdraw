package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.stage.Stage;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Injector;
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
				bindAsEagerSingleton(PreferencesService.class);
				bindWithCommand(ResourceBundle.class, PreferencesService.class, pref -> pref.getBundle());
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
		Cmds.of(() -> clickOn("#helpMenu").clickOn("#aboutItem")).execute();
		final Stage frame = helper.getAboutFrame();
		assertTrue(frame.isShowing());
		Cmds.of(CmdFXVoid.of(() -> helper.getAboutFrame().close())).execute();
		assertEquals(frame, helper.getAboutFrame());
	}

	@Test
	public void testClickShortCutsFrame() {
		Cmds.of(() -> clickOn("#helpMenu").clickOn("#shortcutItem")).execute();
		final Stage frame = helper.getShortcutsFrame();
		assertTrue(frame.isShowing());
		Cmds.of(CmdFXVoid.of(() -> helper.getShortcutsFrame().close())).execute();
		assertEquals(frame, helper.getShortcutsFrame());
	}

	@Test
	public void testClickDonateItemFrame() {
		Cmds.of(() -> clickOn("#helpMenu").clickOn("#donateItem")).execute();
		Mockito.verify(services, Mockito.times(1)).showDocument("http://sourceforge.net/project/project_donations.php?group_id=156523");
	}

	@Test
	public void testClickManualItemFrame() {
		Cmds.of(() -> clickOn("#helpMenu").clickOn("#manuelItem")).execute();
		Mockito.verify(services, Mockito.times(1)).showDocument("https://github.com/arnobl/latexdraw/wiki/Manual");
	}

	@Test
	public void testClickReportItemFrame() {
		Cmds.of(() -> clickOn("#helpMenu").clickOn("#reportBugItem")).execute();
		Mockito.verify(services, Mockito.times(1)).showDocument("https://github.com/arnobl/latexdraw/wiki/Manual#how-to-report-a-bug");
	}

	@Test
	public void testClickForumItemFrame() {
		Cmds.of(() -> clickOn("#helpMenu").clickOn("#forumItem")).execute();
		Mockito.verify(services, Mockito.times(1)).showDocument("https://sourceforge.net/p/latexdraw/discussion/");
	}
}
