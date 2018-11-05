package net.sf.latexdraw.instrument;

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
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class TestCopierCutterPaster extends BaseTestCanvas {
	CopierCutterPaster copier;

	final GUIVoidCommand clickCopy = () -> {
		clickOn("#copierMenu").clickOn("#copyMenu");
		WaitForAsyncUtils.waitForFxEvents();
	};
	final GUIVoidCommand clickCut = () -> {
		clickOn("#copierMenu").clickOn("#cutMenu");
		WaitForAsyncUtils.waitForFxEvents();
	};
	final GUIVoidCommand clickPaste = () -> {
		clickOn("#copierMenu").clickOn("#pasteMenu");
		WaitForAsyncUtils.waitForFxEvents();
	};

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				bindAsEagerSingleton(Border.class);
				bindAsEagerSingleton(CopierCutterPaster.class);
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
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
			final Parent root = FXMLLoader.load(LaTeXDraw.class.getResource("/fxml/CopyPaste.fxml"), injector.getInstance(LangService.class).getBundle(),
				injector.getInstance(BuilderFactory.class), cl -> injector.getInstance(cl));
			final BorderPane pane = new BorderPane();
			pane.setTop(root);
			pane.setCenter(aStage.getScene().getRoot());
			aStage.getScene().setRoot(pane);
		}catch(final IOException ex) {
			fail(ex.getMessage());
		}
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		copier = injector.getInstance(CopierCutterPaster.class);
		hand.setActivated(true);
		copier.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Test
	public void testPasteCustMenuStatusKO() {
		assertTrue(copier.copyMenu.isDisable());
		assertTrue(copier.pasteMenu.isDisable());
		assertTrue(copier.cutMenu.isDisable());
	}

	@Test
	public void testPasteCustMenuStatusOK() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes).execute();
		assertFalse(copier.copyMenu.isDisable());
		assertTrue(copier.pasteMenu.isDisable());
		assertFalse(copier.cutMenu.isDisable());
	}

	@Test
	public void testPasteCustMenuStatusOKAfterCopy() {
		new CompositeGUIVoidCommand(addRec, selectAllShapes, clickCopy).execute();
		assertFalse(copier.copyMenu.isDisable());
		assertFalse(copier.pasteMenu.isDisable());
		assertFalse(copier.cutMenu.isDisable());
	}

	@Test
	public void testCopyPasteOKNbShapes() {
		new CompositeGUIVoidCommand(addRec, addLines, selectAllShapes, clickCopy, clickPaste).execute();
		assertEquals(4, canvas.getDrawing().size());
	}

	@Test
	public void testCopyPasteKeyOKNbShapes() {
		new CompositeGUIVoidCommand(addRec, addLines, selectAllShapes).execute();
		Platform.runLater(() -> canvas.requestFocus());
		waitFXEvents.execute();
		press(KeyCode.CONTROL).type(KeyCode.C).sleep(5).type(KeyCode.V).release(KeyCode.CONTROL);
		waitFXEvents.execute();
		assertEquals(4, canvas.getDrawing().size());
	}

	@Test
	public void testCutKeyOKNbShapes() {
		new CompositeGUIVoidCommand(addRec, addLines, selectAllShapes).execute();
		Platform.runLater(() -> canvas.requestFocus());
		waitFXEvents.execute();
		press(KeyCode.CONTROL).type(KeyCode.X).release(KeyCode.CONTROL);
		waitFXEvents.execute();
		assertTrue(canvas.getDrawing().isEmpty());
	}

	@Test
	public void testCutOKNbShapes() {
		new CompositeGUIVoidCommand(addRec, addLines, selectAllShapes, clickCut).execute();
		assertTrue(canvas.getDrawing().isEmpty());
	}

	@Test
	public void testCutPasteOKNbShapes() {
		new CompositeGUIVoidCommand(addRec, addLines, selectAllShapes, clickCut, clickPaste).execute();
		assertEquals(2, canvas.getDrawing().size());
	}
}
