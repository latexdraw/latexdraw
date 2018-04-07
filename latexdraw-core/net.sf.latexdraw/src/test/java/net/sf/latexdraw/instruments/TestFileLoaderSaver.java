package net.sf.latexdraw.instruments;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.commands.LoadDrawing;
import net.sf.latexdraw.commands.NewDrawing;
import net.sf.latexdraw.commands.SaveDrawing;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.Canvas;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.malai.command.CommandsRegistry;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestFileLoaderSaver extends TestLatexdrawGUI {
	FileLoaderSaver loader;
	FileChooser chooser;
	@Rule public TemporaryFolder tmp = new TemporaryFolder();

	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/OpenSave.fxml";
	}

	@BeforeClass
	public static void beforeAll() throws NoSuchFieldException, IllegalAccessException {
		HelperTest.setFinalStaticFieldValue(LaTeXDraw.class.getDeclaredField("instance"), Mockito.mock(LaTeXDraw.class));
	}

	@Override
	protected Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				bindToInstance(PreferencesSetter.class, Mockito.mock(PreferencesSetter.class));
				bindToInstance(StatusBarController.class, Mockito.mock(StatusBarController.class));
				bindToInstance(IDrawing.class, ShapeFactory.INST.createDrawing());
				bindToInstance(Canvas.class, Mockito.mock(Canvas.class));
				bindAsEagerSingleton(FileLoaderSaver.class);
			}
		};
	}

	@Override
	public void start(final Stage aStage) {
		Mockito.when(LaTeXDraw.getInstance().getMainStage()).thenReturn(aStage);
		super.start(aStage);
	}

	@Before
	public void setUp() throws NoSuchFieldException, IllegalAccessException, IOException {
		Mockito.when(LaTeXDraw.getInstance().getInjector()).thenReturn(injector);
		loader = injector.getInstance(FileLoaderSaver.class);

		chooser = Mockito.mock(FileChooser.class);
		Mockito.when(chooser.getExtensionFilters()).thenReturn(FXCollections.observableArrayList());
		final Field field = FileLoaderSaver.class.getDeclaredField("fileChooser");
		field.setAccessible(true);
		field.set(loader, chooser);
		loader.setCurrentFile(tmp.newFile());

		Mockito.when(injector.getInstance(StatusBarController.class).getProgressBar()).thenReturn(new ProgressBar());
	}

	@Test
	public void testNewMenu() {
		Mockito.when(LaTeXDraw.getInstance().isModified()).thenReturn(false);
		clickOn("#fileMenu").clickOn("#newMenu");
		waitFXEvents.execute();
		assertEquals(1, CommandsRegistry.INSTANCE.getCommands().size());
		assertTrue(CommandsRegistry.INSTANCE.getCommands().get(0) instanceof NewDrawing);
	}

	@Test
	public void testSaveMenu() {
		Mockito.when(LaTeXDraw.getInstance().isModified()).thenReturn(true);
		clickOn("#fileMenu").clickOn("#saveMenu");
		waitFXEvents.execute();
		assertEquals(1, CommandsRegistry.INSTANCE.getCommands().size());
		assertTrue(CommandsRegistry.INSTANCE.getCommands().get(0) instanceof SaveDrawing);
	}

	@Test
	public void testSaveAsMenu() throws IOException {
		Mockito.when(chooser.showSaveDialog(Mockito.any())).thenReturn(tmp.newFile());
		Mockito.when(LaTeXDraw.getInstance().isModified()).thenReturn(true);
		clickOn("#fileMenu").clickOn("#saveAsMenu");
		waitFXEvents.execute();
		assertEquals(1, CommandsRegistry.INSTANCE.getCommands().size());
		assertTrue(CommandsRegistry.INSTANCE.getCommands().get(0) instanceof SaveDrawing);
	}

	@Test
	public void testLoadMenu() {
		final File file = new File(getClass().getResource("/test.svg").getFile());
		loader.setCurrentFile(file);
		Mockito.when(LaTeXDraw.getInstance().isModified()).thenReturn(false);
		clickOn("#fileMenu").clickOn("#loadMenu");
		waitFXEvents.execute();
		assertEquals(1, CommandsRegistry.INSTANCE.getCommands().size());
		assertTrue(CommandsRegistry.INSTANCE.getCommands().get(0) instanceof LoadDrawing);
	}

	@Test
	public void testRecentMenu() {
		loader.updateRecentMenuItems(Collections.singletonList(getClass().getResource("/test.svg").getFile()));
		Mockito.when(LaTeXDraw.getInstance().isModified()).thenReturn(false);
		clickOn("#fileMenu").clickOn("#recentFilesMenu").clickOn("#recent0");
		waitFXEvents.execute();
		assertEquals(1, CommandsRegistry.INSTANCE.getCommands().size());
		assertTrue(CommandsRegistry.INSTANCE.getCommands().get(0) instanceof LoadDrawing);
	}

	@Test
	public void testGetDialogSave() throws NoSuchFieldException, IllegalAccessException {
		final Field field = FileLoaderSaver.class.getDeclaredField("fileChooser");
		field.setAccessible(true);
		field.set(loader, null);
		assertNotNull(loader.getDialog(true));
	}

	@Test
	public void testGetDialogLoad() throws NoSuchFieldException, IllegalAccessException {
		final Field field = FileLoaderSaver.class.getDeclaredField("fileChooser");
		field.setAccessible(true);
		field.set(loader, null);
		assertNotNull(loader.getDialog(false));
	}
}
