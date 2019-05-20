package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.instrument.robot.FxRobotSpinner;
import net.sf.latexdraw.util.Injector;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TestZoomer extends BaseTestCanvas implements FxRobotSpinner {
	double zoom;

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(Hand.class);
				bindAsEagerSingleton(Zoomer.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
			}
		};
	}


	@Override
	public void start(final Stage aStage) throws Exception {
		super.start(aStage);
		final Parent root = FXMLLoader.load(LaTeXDraw.class.getResource("/fxml/Zoom.fxml"), injector.getInstance(ResourceBundle.class),
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
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
		zoom = canvas.getZoom();
	}

	@Test
	public void testIncrZoom() {
		incrementSpinner(find("#zoom"));
		waitFXEvents.execute();
		assertEquals(zoom + canvas.getZoomIncrement(), canvas.getZoom(), 0.00001);
	}

	@Ignore("Monocle does not manage num padd correctly https://bugs.openjdk.java.net/browse/JDK-8182572")
	@Test
	public void testIncrZoomKey() {
		requestFocusCanvas.execute();
		type(KeyCode.ADD);
		waitFXEvents.execute();
		assertEquals(zoom + canvas.getZoomIncrement(), canvas.getZoom(), 0.00001);
	}

	@Test
	public void testDecrZoomKey() {
		requestFocusCanvas.execute();
		type(KeyCode.MINUS);
		waitFXEvents.execute();
		assertEquals(zoom - canvas.getZoomIncrement(), canvas.getZoom(), 0.00001);
	}

	@Ignore("Monocle does not support modifier yet")
	@Test
	public void testScrollZoom() {
		requestFocusCanvas.execute();
		press(KeyCode.CONTROL).scroll(1, VerticalDirection.UP).release(KeyCode.CONTROL);
		waitFXEvents.execute();
		assertEquals(zoom + canvas.getZoomIncrement(), canvas.getZoom(), 0.00001);
	}
}
