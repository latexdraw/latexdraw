package net.sf.latexdraw.instruments;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.commands.shape.AddShape;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangTool;
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
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class TestUndoRedoManager extends BaseTestCanvas {
	final GUIVoidCommand addRecCmd = () -> Platform.runLater(() -> {
		addedRec = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(-Canvas.ORIGIN.getX() + 50, -Canvas.ORIGIN.getY() + 50), 200, 100);
		addedRec.setFilled(true);
		addedRec.setFillingCol(DviPsColors.APRICOT);
		final AddShape cmd = new AddShape(addedRec, canvas.getDrawing());
		cmd.doIt();
		CommandsRegistry.INSTANCE.addCommand(cmd, null);
	});

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(Hand.class);
				bindAsEagerSingleton(UndoRedoManager.class);
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
			final Parent root = FXMLLoader.load(LaTeXDraw.class.getResource("/fxml/Undo.fxml"), LangTool.INSTANCE.getBundle(),
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
		new CompositeGUIVoidCommand(addRecCmd, waitFXEvents).execute();
		assertNotNull(((Button) find("#undoB")).getTooltip());
		assertNull(((Button) find("#redoB")).getTooltip());
		clickOn("#undoB");
		waitFXEvents.execute();
		assertTrue(canvas.getDrawing().isEmpty());
	}

	@Test
	public void testRedoButtonOK() {
		new CompositeGUIVoidCommand(addRecCmd, waitFXEvents).execute();
		clickOn("#undoB");
		waitFXEvents.execute();
		assertNull(((Button) find("#undoB")).getTooltip());
		assertNotNull(((Button) find("#redoB")).getTooltip());
		clickOn("#redoB");
		waitFXEvents.execute();
		assertEquals(1, canvas.getDrawing().size());
	}

	@Test
	public void testUndoKeyOK() {
		new CompositeGUIVoidCommand(addRecCmd, waitFXEvents, requestFocusCanvas).execute();
		press(KeyCode.CONTROL).type(KeyCode.Z).release(KeyCode.CONTROL);
		waitFXEvents.execute();
		assertTrue(canvas.getDrawing().isEmpty());
	}

	@Test
	public void testRedoKeyOK() {
		new CompositeGUIVoidCommand(addRecCmd, waitFXEvents, requestFocusCanvas).execute();
		press(KeyCode.CONTROL).type(KeyCode.Z).release(KeyCode.CONTROL);
		waitFXEvents.execute();
		press(KeyCode.CONTROL).type(KeyCode.Y).release(KeyCode.CONTROL);
		waitFXEvents.execute();
		assertEquals(1, canvas.getDrawing().size());
	}
}
