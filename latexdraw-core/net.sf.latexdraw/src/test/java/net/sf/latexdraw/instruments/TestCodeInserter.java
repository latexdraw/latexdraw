package net.sf.latexdraw.instruments;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeoutException;
import javafx.application.Platform;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.util.Injector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestCodeInserter extends ApplicationTest {
	IDrawing drawing;
	CodeInserter inserter;

	Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				bindToInstance(CopierCutterPaster.class, Mockito.mock(CopierCutterPaster.class));
				bindAsEagerSingleton(StatusBarController.class);
				bindToInstance(IDrawing.class, ShapeFactory.INST.createDrawing());
				bindAsEagerSingleton(CodeInserter.class);
			}
		};
	}

	@Before
	public void setUp() throws NoSuchFieldException, IllegalAccessException {
		final Injector injector = createInjector();
		HelperTest.setFinalStaticFieldValue(LaTeXDraw.class.getDeclaredField("instance"), Mockito.mock(LaTeXDraw.class));
		Mockito.when(LaTeXDraw.getInstance().getInjector()).thenReturn(injector);
		Mockito.when(LaTeXDraw.getInstance().getInstanceCallBack()).thenReturn(cl -> injector.getInstance(cl));

		drawing = LaTeXDraw.getInstance().getInjector().getInstance(IDrawing.class);
		Platform.runLater(() -> {
			inserter = LaTeXDraw.getInstance().getInjector().getInstance(CodeInserter.class);
			try {
				FxToolkit.registerStage(() -> inserter.getInsertCodeDialogue().orElse(null));
				WaitForAsyncUtils.waitForFxEvents();
			}catch(final TimeoutException ex) {
				fail(ex.getMessage());
			}
			inserter.setActivated(true);
			WaitForAsyncUtils.waitForFxEvents();
			inserter.getInsertCodeDialogue().ifPresent(stage -> {
				stage.show();
				stage.toFront();
			});
		});
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Test
	public void testCancelDeactivateHide() {
		clickOn(inserter.cancel);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(inserter.isActivated());
	}

	@Test
	public void testOkWithNoCode() {
		clickOn(inserter.ok);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(inserter.isActivated());
		assertTrue(drawing.isEmpty());
	}

	@Test
	public void testTypeBadCodeOK() {
		clickOn(inserter.text).write("\\foo \\psframe[foo=10]");
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(inserter.errorLog.getText().isEmpty());
	}

	@Test
	public void testDeactivateHide() {
		Platform.runLater(() -> inserter.setActivated(false));
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(inserter.getInsertCodeDialogue().orElseThrow(() -> new IllegalArgumentException()).isShowing());
	}

	@Test
	public void testEnterCodeOKCreatesGoodShape() {
		clickOn(inserter.text).write("\\psframe(0,0)(100,100)").clickOn(inserter.ok);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IRectangle);
	}

	@Test
	public void testEnterCodeKOCreatesText() {
		clickOn(inserter.text).write("\\foobar").clickOn(inserter.ok);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof IText);
	}
}
