package net.sf.latexdraw.instruments;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.scene.control.TextInputControl;
import javafx.stage.DirectoryChooser;
import net.sf.latexdraw.commands.ModifyMagneticGrid;
import net.sf.latexdraw.instruments.robot.FxRobotListSelection;
import net.sf.latexdraw.instruments.robot.FxRobotSpinner;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.Preference;
import net.sf.latexdraw.view.GridStyle;
import net.sf.latexdraw.view.MagneticGrid;
import org.junit.Before;
import org.junit.Test;
import org.malai.command.CommandsRegistry;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPreferencesSetter extends TestLatexdrawGUI implements FxRobotListSelection, FxRobotSpinner {
	PreferencesSetter setter;

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
				bindAsEagerSingleton(PreferencesSetter.class);
				bindToInstance(FileLoaderSaver.class, Mockito.mock(FileLoaderSaver.class));
				bindToInstance(MagneticGrid.class, Mockito.mock(MagneticGrid.class));
				bindToInstance(Exporter.class, Mockito.mock(Exporter.class));
			}
		};
	}

	@Before
	public void setUp() {
		Preference.INSTANCE.flushPreferencesCache();
		setter = (PreferencesSetter) injectorFactory.call(PreferencesSetter.class);
		setter.setActivated(true);
	}

	private void writeReadPrefs() {
		Platform.runLater(() -> {
			setter.writeXMLPreferences();
			waitFXEvents.execute();
			setter.readXMLPreferences();
			waitFXEvents.execute();
		});
	}

	@Test
	public void testReadWriteMagneticGrid() {
		Platform.runLater(() -> setter.readXMLPreferences());
		waitFXEvents.execute();
		clickOn(setter.magneticCB);
		waitFXEvents.execute();
		final boolean value = setter.magneticCB.isSelected();
		writeReadPrefs();
		assertEquals(value, setter.magneticCB.isSelected());
		assertTrue(CommandsRegistry.INSTANCE.getCommands().get(0) instanceof ModifyMagneticGrid);
	}

	@Test
	public void testReadWriteCheckNewVersion() {
		Platform.runLater(() -> setter.readXMLPreferences());
		waitFXEvents.execute();
		clickOn(setter.checkNewVersion);
		waitFXEvents.execute();
		final boolean value = setter.checkNewVersion.isSelected();
		writeReadPrefs();
		assertEquals(value, setter.checkNewVersion.isSelected());
	}

	@Test
	public void testChangeMagneticGrid() {
		Platform.runLater(() -> setter.readXMLPreferences());
		waitFXEvents.execute();
		selectNextComboBoxItem(setter.styleList);
		waitFXEvents.execute();
		final GridStyle value = setter.styleList.getValue();
		writeReadPrefs();
		assertEquals(value, setter.styleList.getValue());
		assertTrue(CommandsRegistry.INSTANCE.getCommands().get(0) instanceof ModifyMagneticGrid);
	}

	@Test
	public void testGidGap() {
		Platform.runLater(() -> setter.readXMLPreferences());
		waitFXEvents.execute();
		incrementSpinner(setter.persoGridGapField);
		waitFXEvents.execute();
		final int value = setter.persoGridGapField.getValue();
		writeReadPrefs();
		assertEquals(value, setter.persoGridGapField.getValue().intValue());
		assertTrue(CommandsRegistry.INSTANCE.getCommands().get(0) instanceof ModifyMagneticGrid);
	}

	@Test
	public void testClickChooseLoadFolder() throws NoSuchFieldException, IllegalAccessException {
		final DirectoryChooser chooser = Mockito.mock(DirectoryChooser.class);
			Mockito.when(chooser.showDialog(Mockito.any())).thenReturn(new File("foo"));
		final Field field = PreferencesSetter.class.getDeclaredField("fileChooser");
		field.setAccessible(true);
		field.set(setter, chooser);
		clickOn("#buttonOpen");
		waitFXEvents.execute();
		assertEquals("foo", ((TextInputControl) find("#pathOpenField")).getText());
	}

	@Test
	public void testClickChooseExportFolder() throws NoSuchFieldException, IllegalAccessException {
		final DirectoryChooser chooser = Mockito.mock(DirectoryChooser.class);
			Mockito.when(chooser.showDialog(Mockito.any())).thenReturn(new File("bar"));
		final Field field = PreferencesSetter.class.getDeclaredField("fileChooser");
		field.setAccessible(true);
		field.set(setter, chooser);
		clickOn("#buttonExport");
		waitFXEvents.execute();
		assertEquals("bar", ((TextInputControl) find("#pathExportField")).getText());
	}
}
