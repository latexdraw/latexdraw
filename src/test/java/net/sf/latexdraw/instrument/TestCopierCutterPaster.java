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
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TestCopierCutterPaster extends BaseTestCanvas {
	CopierCutterPaster copier;

	final CmdVoid clickCopy = () -> clickOn("#copierMenu").clickOn("#copyMenu");
	final CmdVoid clickCut = () -> clickOn("#copierMenu").clickOn("#cutMenu");
	final CmdVoid clickPaste = () -> clickOn("#copierMenu").clickOn("#pasteMenu");

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindAsEagerSingleton(Border.class);
				bindAsEagerSingleton(CopierCutterPaster.class);
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindAsEagerSingleton(Hand.class);
			}
		};
	}

	@Override
	public void start(final Stage aStage) throws Exception {
		super.start(aStage);
		final Parent root = FXMLLoader.load(LaTeXDraw.class.getResource("/fxml/CopyPaste.fxml"), injector.getInstance(ResourceBundle.class),
			injector.getInstance(BuilderFactory.class), cl -> injector.getInstance(cl));
		final BorderPane pane = new BorderPane();
		pane.setTop(root);
		pane.setCenter(aStage.getScene().getRoot());
		aStage.getScene().setRoot(pane);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		copier = injector.getInstance(CopierCutterPaster.class);
		hand.setActivated(true);
		copier.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Test
	public void testPasteCutMenuStatusKO() {
		assertTrue(copier.copyMenu.isDisable());
		assertTrue(copier.pasteMenu.isDisable());
		assertTrue(copier.cutMenu.isDisable());
	}

	@Test
	public void testPasteCutMenuStatusOK() {
		Cmds.of(addRec, selectAllShapes).execute();
		assertFalse(copier.copyMenu.isDisable());
		assertTrue(copier.pasteMenu.isDisable());
		assertFalse(copier.cutMenu.isDisable());
	}

	@Test
	public void testPasteCutMenuStatusOKAfterCopy() {
		Cmds.of(addRec, selectAllShapes, clickCopy).execute();
		assertFalse(copier.copyMenu.isDisable());
		assertFalse(copier.pasteMenu.isDisable());
		assertFalse(copier.cutMenu.isDisable());
	}

	@Test
	public void testCopyPasteOKNbShapes() {
		Cmds.of(addRec, addLines, selectAllShapes, clickCopy, clickPaste).execute();
		assertEquals(4, canvas.getDrawing().size());
	}

	@Test
	public void testCopyPasteKeyOKNbShapes() {
		Cmds.of(addRec, addLines, selectAllShapes, requestFocusCanvas,
			() -> press(KeyCode.CONTROL).type(KeyCode.C),
			() -> type(KeyCode.V).release(KeyCode.CONTROL)).execute();
		assertEquals(4, canvas.getDrawing().size());
	}

	@Test
	public void testCutKeyOKNbShapes() {
		Cmds.of(addRec, addLines, selectAllShapes, requestFocusCanvas,
			() -> press(KeyCode.CONTROL).type(KeyCode.X).release(KeyCode.CONTROL)).execute();
		assertTrue(canvas.getDrawing().isEmpty());
	}

	@Test
	public void testCutOKNbShapes() {
		Cmds.of(addRec, addLines, selectAllShapes, clickCut).execute();
		assertTrue(canvas.getDrawing().isEmpty());
	}

	@Test
	public void testCutPasteOKNbShapes() {
		Cmds.of(addRec, addLines, selectAllShapes, clickCut, clickPaste).execute();
		assertEquals(2, canvas.getDrawing().size());
	}
}
