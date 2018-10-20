package net.sf.latexdraw.instruments;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.commands.Export;
import net.sf.latexdraw.commands.ExportTemplate;
import net.sf.latexdraw.data.StringData;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.malai.command.CommandsRegistry;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(Theories.class)
public class TestExporter extends BaseTestCanvas {
	Exporter exporter;
	FileChooser chooser;
	@Rule public TemporaryFolder tmp = new TemporaryFolder();

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindAsEagerSingleton(Border.class);
				bindAsEagerSingleton(Exporter.class);
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(HostServices.class, Mockito.mock(HostServices.class));
				bindToInstance(StatusBarController.class, Mockito.mock(StatusBarController.class));
				bindToInstance(TemplateManager.class, Mockito.mock(TemplateManager.class));
				bindAsEagerSingleton(PSTCodeGenerator.class);
				bindToInstance(FileLoaderSaver.class, Mockito.mock(FileLoaderSaver.class));
				bindAsEagerSingleton(Hand.class);
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
			}
		};
	}

	@Override
	public void start(final Stage aStage) {
		super.start(aStage);
		try {
			final Parent root = FXMLLoader.load(LaTeXDraw.class.getResource("/fxml/Export.fxml"), LangTool.INSTANCE.getBundle(),
				new LatexdrawBuilderFactory(injector), injectorFactory);
			final BorderPane pane = new BorderPane();
			pane.setTop(root);
			pane.setCenter(stage.getScene().getRoot());
			stage.getScene().setRoot(pane);
		}catch(final IOException ex) {
			fail(ex.getMessage());
		}
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		exporter = (Exporter) injectorFactory.call(Exporter.class);
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
		WaitForAsyncUtils.waitForFxEvents();

		final TemplateManager template = (TemplateManager) injectorFactory.call(TemplateManager.class);
		template.templatePane = new FlowPane();

		final StatusBarController status = (StatusBarController) injectorFactory.call(StatusBarController.class);
		Mockito.when(status.getLabel()).thenReturn(new Label());
		Mockito.when(status.getProgressBar()).thenReturn(new ProgressBar());

		chooser = Mockito.mock(FileChooser.class);
		Mockito.when(chooser.getExtensionFilters()).thenReturn(FXCollections.observableArrayList());
		try {
			Mockito.when(chooser.showOpenDialog(Mockito.any())).thenReturn(tmp.newFile());
			Mockito.when(chooser.showSaveDialog(Mockito.any())).thenReturn(tmp.newFile());
			Field field = Exporter.class.getDeclaredField("fileChooserExport");
			field.setAccessible(true);
			field.set(exporter, chooser);

			final LaTeXDraw ld = Mockito.mock(LaTeXDraw.class);
			field = LaTeXDraw.class.getDeclaredField("instance");
			field.setAccessible(true);
			field.set(null, ld);
		}catch(final IllegalAccessException | NoSuchFieldException | IOException ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testNoExportOnNoShape() {
		assertFalse(exporter.isActivated());
		assertTrue(exporter.exportMenu.isDisabled());
	}

	@Test
	public void testExportOnShapeAdded() {
		new CompositeGUIVoidCommand(addRec).execute();
		assertTrue(exporter.isActivated());
		assertFalse(exporter.exportMenu.isDisabled());
	}


	@Test
	public void testExportTemplateKOOnNoSelection() {
		new CompositeGUIVoidCommand(addRec).execute();
		assertTrue(exporter.exportTemplateMenu.isDisable());
	}

	@Test
	public void testExportTemplateOKOnSelection() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		assertFalse(exporter.exportTemplateMenu.isDisable());
	}

	@Theory
	public void testExportPicture(@StringData(vals = {"#menuItemBMP", "#menuItemPST", "#menuItemPNG",
		"#menuItemBMP", "#menuItemPDF", "#menuItemEPSLatex", "#menuItemPDFcrop"}) final String widgetID) {
		new CompositeGUIVoidCommand(addRec, () -> clickOn(exporter.exportMenu), () -> clickOn(widgetID)).execute();
		assertEquals(1, CommandsRegistry.INSTANCE.getCommands().size());
		assertTrue(CommandsRegistry.INSTANCE.getCommands().get(0) instanceof Export);
	}

	@Test
	public void testExportTemplate() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes, () -> clickOn(exporter.exportMenu), () -> clickOn("#exportTemplateMenu"),
			() -> write("fooo").type(KeyCode.ENTER)).execute();
		assertEquals(2, CommandsRegistry.INSTANCE.getCommands().size());
		assertTrue(CommandsRegistry.INSTANCE.getCommands().get(1) instanceof ExportTemplate);
	}
}
