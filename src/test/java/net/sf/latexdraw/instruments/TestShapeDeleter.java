package net.sf.latexdraw.instruments;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(Hand.class);
				bindAsEagerSingleton(ShapeDeleter.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
			}
		};
	}

	@Override
	public void start(final Stage aStage) {
		super.start(aStage);
		try {
			final Parent root = FXMLLoader.load(LaTeXDraw.class.getResource("/fxml/Deleter.fxml"), injector.getInstance(LangService.class).getBundle(),
				injector.getInstance(BuilderFactory.class), cl -> injector.getInstance(cl));
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
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
	}

	@Test
	public void testKeyDelRemoveShape() {
		Platform.runLater(() -> canvas.requestFocus());
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		type(KeyCode.DELETE);
		waitFXEvents.execute();
		assertTrue(canvas.getDrawing().isEmpty());
	}

	@Test
	public void testClickDelRemoveShape() {
		Platform.runLater(() -> canvas.requestFocus());
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		clickOn("#deleteB");
		waitFXEvents.execute();
assertTrue(canvas.getDrawing().isEmpty());
		assertTrue(find("#deleteB").isDisabled());
	}

	@Test
	public void testActivationShape() {
		Platform.runLater(() -> canvas.requestFocus());
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		assertFalse(find("#deleteB").isDisabled());
		type(KeyCode.DELETE);
		waitFXEvents.execute();
		assertTrue(find("#deleteB").isDisabled());
	}
}
