package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.command.LoadTemplate;
import net.sf.latexdraw.command.UpdateTemplates;
import net.sf.latexdraw.util.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.github.interacto.command.CmdHandler;
import io.github.interacto.command.CommandsRegistry;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TestTemplateManager extends BaseTestCanvas {
	CmdHandler handler;

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(Hand.class);
				bindToInstance(StatusBarController.class, Mockito.mock(StatusBarController.class));
				bindAsEagerSingleton(TemplateManager.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Override
	public void start(final Stage aStage) throws Exception {
		super.start(aStage);
		final TitledPane root = FXMLLoader.load(LaTeXDraw.class.getResource("/fxml/Template.fxml"), injector.getInstance(ResourceBundle.class),
			injector.getInstance(BuilderFactory.class), cl -> injector.getInstance(cl));
		final BorderPane pane = new BorderPane();
		pane.setTop(root.getContent());
		pane.setCenter(stage.getScene().getRoot());
		stage.getScene().setRoot(pane);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		handler = Mockito.mock(CmdHandler.class);
		Cmds.of(CmdFXVoid.of(() -> hand.setActivated(true))).execute();
		when(pencil.isActivated()).thenReturn(false);
		CommandsRegistry.INSTANCE.addHandler(handler);
	}

	@Override
	@After
	public void tearDown() {
		CommandsRegistry.INSTANCE.removeHandler(handler);
	}

	@Test
	public void testDnDInsertTemplate() {
		final ImageView view = new ImageView(new Image(getClass().getResourceAsStream("/Condenser.svg.png"))); //NON-NLS
		view.setUserData(getClass().getResource("/Condenser.svg").getPath());
		final FlowPane pane = find("#templatePane");
		Cmds.of(
			CmdFXVoid.of(() -> pane.getChildren().add(0, view)),
			() -> drag(pane.getChildren().get(0)).dropTo(canvas)).execute();
		assertEquals(1, canvas.getDrawing().size());
		Mockito.verify(handler, Mockito.times(1)).onCmdDone(Mockito.any(LoadTemplate.class));
	}

	@Test
	public void testClickRefreshTemplates() {
		Cmds.of(() -> clickOn("#updateTemplates")).execute();
		Mockito.verify(handler, Mockito.times(1)).onCmdDone(Mockito.any(UpdateTemplates.class));
	}
}
