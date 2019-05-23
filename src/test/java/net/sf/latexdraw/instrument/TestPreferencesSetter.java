package net.sf.latexdraw.instrument;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.instrument.robot.FxRobotListSelection;
import net.sf.latexdraw.instrument.robot.FxRobotSpinner;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.view.GridStyle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestPreferencesSetter extends TestLatexdrawGUI implements FxRobotListSelection, FxRobotSpinner {
	PreferencesSetter setter;
	PreferencesService prefs;

	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/Preferences.fxml";
	}

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				bindAsEagerSingleton(PreferencesSetter.class);
			}
		};
	}

	@Before
	public void setUp() {
		setter = injector.getInstance(PreferencesSetter.class);
		prefs = injector.getInstance(PreferencesService.class);
		setter.setActivated(true);
	}

	@Test
	public void testCheckVersion() {
		final boolean version = prefs.checkVersionProperty().get();
		Cmds.of(() -> clickOn(setter.checkNewVersion)).execute();
		assertNotEquals(version, prefs.checkVersionProperty().get());
	}

	@Test
	public void testMagnetic() {
		final boolean magnetic = prefs.isMagneticGrid();
		Cmds.of(() -> clickOn(setter.magneticCB)).execute();
		assertNotEquals(magnetic, prefs.isMagneticGrid());
	}

	@Test
	public void testLang() {
		Cmds.of(
			CmdFXVoid.of(() -> prefs.langProperty().set(Locale.FRENCH)),
			() -> selectGivenComboBoxItem(setter.langList, Locale.US)).execute();
		assertEquals(Locale.US, prefs.getLang());
	}

	@Test
	public void testGridStyle() {
		Cmds.of(
			CmdFXVoid.of(() -> prefs.gridStyleProperty().set(GridStyle.CUSTOMISED)),
			() -> selectGivenComboBoxItem(setter.styleList, GridStyle.STANDARD)).execute();
		assertEquals(GridStyle.STANDARD, prefs.getGridStyle());
	}

	@Test
	public void testUnit() {
		Cmds.of(
			CmdFXVoid.of(() -> prefs.unitProperty().set(Unit.CM)),
			() -> selectGivenComboBoxItem(setter.unitChoice, Unit.INCH)).execute();
		assertEquals(Unit.INCH, prefs.getUnit());
	}

	@Test
	public void testIncludes() {
		Cmds.of(
			CmdFXVoid.of(() -> prefs.includesProperty().setValue("")),
			() -> clickOn(setter.latexIncludes).write("fooo")).execute();
		HelperTest.waitForTimeoutTransitions();
		assertEquals("fooo", prefs.includesProperty().get());
	}

	@Test
	public void testNbRecentFiles() {
		Cmds.of(
			CmdFXVoid.of(() -> prefs.setNbRecentFiles(2)),
			() -> incrementSpinner(setter.nbRecentFilesField),
			() -> incrementSpinner(setter.nbRecentFilesField)).execute();
		assertEquals(4, prefs.getNbRecentFiles());
	}

	@Test
	public void testGridGap() {
		Cmds.of(
			CmdFXVoid.of(() -> prefs.gridGapProperty().set(4)),
			() -> incrementSpinner(setter.magneticGridGap),
			() -> incrementSpinner(setter.magneticGridGap)).execute();
		assertEquals(6, prefs.gridGapProperty().get());
	}

	@Test
	public void testClickChooseLoadFolder() throws NoSuchFieldException, IllegalAccessException {
		final DirectoryChooser chooser = Mockito.mock(DirectoryChooser.class);
		Mockito.when(chooser.showDialog(Mockito.any())).thenReturn(new File("/tmp"));
		final Field field = PreferencesSetter.class.getDeclaredField("fileChooser");
		field.setAccessible(true);
		field.set(setter, chooser);
		Cmds.of(() -> clickOn("#buttonOpen")).execute();
		assertEquals("/tmp", setter.pathOpenField.getText());
		assertEquals("/tmp", prefs.getPathOpen());
	}

	@Test
	public void testClickChooseExportFolder() throws NoSuchFieldException, IllegalAccessException {
		final DirectoryChooser chooser = Mockito.mock(DirectoryChooser.class);
		Mockito.when(chooser.showDialog(Mockito.any())).thenReturn(new File("/tmp"));
		final Field field = PreferencesSetter.class.getDeclaredField("fileChooser");
		field.setAccessible(true);
		field.set(setter, chooser);
		Cmds.of(() -> clickOn("#buttonExport")).execute();
		assertEquals("/tmp", setter.pathExportField.getText());
		assertEquals("/tmp", prefs.getPathExport());
	}
}
