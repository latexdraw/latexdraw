package net.sf.latexdraw.instrument;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
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
import javafx.util.BuilderFactory;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.command.Export;
import net.sf.latexdraw.command.ExportTemplate;
import net.sf.latexdraw.data.StringData;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.pst.PSTCodeGenerator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import io.github.interacto.command.CommandsRegistry;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
				bindToSupplier(Stage.class, () -> stage);
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindToInstance(HostServices.class, Mockito.mock(HostServices.class));
				bindToInstance(StatusBarController.class, Mockito.mock(StatusBarController.class));
				bindAsEagerSingleton(PSTCodeGenerator.class);
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(ShapeCoordDimCustomiser.class, Mockito.mock(ShapeCoordDimCustomiser.class));
				bindAsEagerSingleton(Border.class);
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindToInstance(TemplateManager.class, Mockito.mock(TemplateManager.class));
				bindAsEagerSingleton(Exporter.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindAsEagerSingleton(Hand.class);
			}
		};
	}

	@Override
	public void start(final Stage aStage) throws Exception {
		super.start(aStage);
		final Parent root = FXMLLoader.load(LaTeXDraw.class.getResource("/fxml/Export.fxml"), injector.getInstance(ResourceBundle.class),
			injector.getInstance(BuilderFactory.class), cl -> injector.getInstance(cl));
		final BorderPane pane = new BorderPane();
		pane.setTop(root);
		pane.setCenter(stage.getScene().getRoot());
		stage.getScene().setRoot(pane);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		exporter = injector.getInstance(Exporter.class);
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
		WaitForAsyncUtils.waitForFxEvents();

		final TemplateManager template = injector.getInstance(TemplateManager.class);
		template.templatePane = new FlowPane();

		final StatusBarController status = injector.getInstance(StatusBarController.class);
		Mockito.when(status.getLabel()).thenReturn(new Label());
		Mockito.when(status.getProgressBar()).thenReturn(new ProgressBar());

		chooser = Mockito.mock(FileChooser.class);
		Mockito.when(chooser.getExtensionFilters()).thenReturn(FXCollections.observableArrayList());
		Mockito.when(chooser.showOpenDialog(Mockito.any())).thenReturn(tmp.newFile());
		Mockito.when(chooser.showSaveDialog(Mockito.any())).thenReturn(tmp.newFile());
		final Field field = Exporter.class.getDeclaredField("fileChooserExport");
		field.setAccessible(true);
		field.set(exporter, chooser);
	}

	@Test
	public void testNoExportOnNoShape() {
		assertFalse(exporter.isActivated());
		assertTrue(exporter.exportMenu.isDisabled());
	}

	@Test
	public void testExportOnShapeAdded() {
		Cmds.of(addRec).execute();
		assertTrue(exporter.isActivated());
		assertFalse(exporter.exportMenu.isDisabled());
	}


	@Test
	public void testExportTemplateKOOnNoSelection() {
		Cmds.of(addRec).execute();
		assertTrue(exporter.exportTemplateMenu.isDisable());
	}

	@Test
	public void testExportTemplateOKOnSelection() {
		Cmds.of(addRec, selectAllShapes).execute();
		assertFalse(exporter.exportTemplateMenu.isDisable());
	}

	@Theory
	public void testExportPicture(@StringData(vals = {"#menuItemBMP", "#menuItemPST", "#menuItemPNG",
		"#menuItemBMP", "#menuItemPDF", "#menuItemEPSLatex"}) final String widgetID) {
		Cmds.of(addRec, () -> clickOn(exporter.exportMenu), () -> clickOn(widgetID)).execute();
		assertEquals(1, CommandsRegistry.getInstance().getCommands().size());
		assertTrue(CommandsRegistry.getInstance().getCommands().get(0) instanceof Export);
	}

	@Test
	public void testExportTemplateNotExits() {
		Cmds.of(addRec, selectAllShapes, () -> clickOn(exporter.exportMenu), () -> clickOn("#exportTemplateMenu"),
			() -> write("fooo2"), () -> type(KeyCode.ENTER), () -> type(KeyCode.ENTER)).execute();
		assertEquals(2, CommandsRegistry.getInstance().getCommands().size());
		assertTrue(CommandsRegistry.getInstance().getCommands().get(1) instanceof ExportTemplate);
	}
}
