package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.util.Injector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TestShapeDeleter extends BaseTestCanvas {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(Hand.class);
				bindAsEagerSingleton(ShapeDeleter.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
			}
		};
	}

	@Override
	public void start(final Stage aStage) throws Exception {
		super.start(aStage);
		final Parent root = FXMLLoader.load(LaTeXDraw.class.getResource("/fxml/Deleter.fxml"), injector.getInstance(ResourceBundle.class),
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
		Cmds.of(CmdFXVoid.of(() -> hand.setActivated(true))).execute();
		when(pencil.isActivated()).thenReturn(false);
	}

	@Test
	public void testKeyDelRemoveShape() {
		Cmds.of(requestFocusCanvas, addRec, selectAllShapes, () -> type(KeyCode.DELETE)).execute();
		assertTrue(canvas.getDrawing().isEmpty());
	}

	@Test
	public void testClickDelRemoveShape() {
		Cmds.of(requestFocusCanvas, addRec, selectAllShapes, () -> clickOn("#deleteB")).execute();
		assertTrue(canvas.getDrawing().isEmpty());
		assertTrue(find("#deleteB").isDisabled());
	}

	@Test
	public void testActivationShape() {
		Cmds.of(requestFocusCanvas, addRec, selectAllShapes).execute();
		assertFalse(find("#deleteB").isDisabled());
		Cmds.of(() -> type(KeyCode.DELETE)).execute();
		assertTrue(find("#deleteB").isDisabled());
	}
}
