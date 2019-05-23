package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.command.shape.AddShape;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Before;
import org.junit.Test;
import org.malai.command.CommandsRegistry;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TestUndoRedoManager extends BaseTestCanvas {
	final CmdFXVoid addRecCmd = () -> {
		addedRec = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 50, -Canvas.ORIGIN.getY() + 50), 200, 100);
		addedRec.setFilled(true);
		addedRec.setFillingCol(DviPsColors.APRICOT);
		final AddShape cmd = new AddShape(addedRec, canvas.getDrawing());
		cmd.doIt();
		CommandsRegistry.INSTANCE.addCommand(cmd, null);
	};

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
				bindAsEagerSingleton(UndoRedoManager.class);
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
		final Parent root = FXMLLoader.load(LaTeXDraw.class.getResource("/fxml/Undo.fxml"), injector.getInstance(ResourceBundle.class),
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
	}

	@Test
	public void testDisableOnEmpty() {
		assertTrue(find("#undoB").isDisabled());
		assertTrue(find("#redoB").isDisabled());
	}

	@Test
	public void testUndoButtonOK() {
		Cmds.of(addRecCmd).execute();
		assertNotNull(((Button) find("#undoB")).getTooltip());
		assertNull(((Button) find("#redoB")).getTooltip());
		Cmds.of(() -> clickOn("#undoB")).execute();
		assertTrue(canvas.getDrawing().isEmpty());
	}

	@Test
	public void testRedoButtonOK() {
		Cmds.of(addRecCmd, () -> clickOn("#undoB")).execute();
		assertNull(((Button) find("#undoB")).getTooltip());
		assertNotNull(((Button) find("#redoB")).getTooltip());
		Cmds.of(() -> clickOn("#redoB")).execute();
		assertEquals(1, canvas.getDrawing().size());
	}

	@Test
	public void testUndoKeyOK() {
		Cmds.of(addRecCmd, requestFocusCanvas,
			() -> press(KeyCode.CONTROL).type(KeyCode.Z).release(KeyCode.CONTROL)).execute();
		assertTrue(canvas.getDrawing().isEmpty());
	}

	@Test
	public void testRedoKeyOK() {
		Cmds.of(addRecCmd, requestFocusCanvas,
			() -> press(KeyCode.CONTROL).type(KeyCode.Z).release(KeyCode.CONTROL),
			() -> press(KeyCode.CONTROL).type(KeyCode.Y).release(KeyCode.CONTROL)).execute();
		assertEquals(1, canvas.getDrawing().size());
	}
}
