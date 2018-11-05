package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeoutException;
import javafx.application.HostServices;
import javafx.application.Platform;
import net.sf.latexdraw.data.ConfigureInjection;
import net.sf.latexdraw.data.InjectionExtension;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


@ExtendWith(InjectionExtension.class)
@ExtendWith(ApplicationExtension.class)
public class TestCodeInserter {
	Drawing drawing;
	CodeInserter inserter;

	@ConfigureInjection
	Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				bindToInstance(Injector.class, this);
				bindAsEagerSingleton(SystemService.class);
				bindAsEagerSingleton(LangService.class);
				bindToInstance(HostServices.class, Mockito.mock(HostServices.class));
				bindToInstance(CopierCutterPaster.class, Mockito.mock(CopierCutterPaster.class));
				bindAsEagerSingleton(StatusBarController.class);
				bindToInstance(Drawing.class, ShapeFactory.INST.createDrawing());
				bindAsEagerSingleton(CodeInserter.class);
			}
		};
	}

	@BeforeEach
	public void setUp(final Drawing drawing, final CodeInserter codeInserter) {
		this.drawing = drawing;
		inserter = codeInserter;
		Platform.runLater(() -> {
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
	public void testCancelDeactivateHide(final FxRobot robot) {
		robot.clickOn(inserter.cancel);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(inserter.isActivated());
	}

	@Test
	public void testOkWithNoCode(final FxRobot robot) {
		robot.clickOn(inserter.ok);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(inserter.isActivated());
		assertTrue(drawing.isEmpty());
	}

	@Test
	public void testTypeBadCodeOK(final FxRobot robot) {
		robot.clickOn(inserter.text).write("\\foo \\psframe[foo=10]");
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
	public void testEnterCodeOKCreatesGoodShape(final FxRobot robot) {
		robot.clickOn(inserter.text).write("\\psframe(0,0)(100,100)").clickOn(inserter.ok);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof Rectangle);
	}

	@Test
	public void testEnterCodeKOCreatesText(final FxRobot robot) {
		robot.clickOn(inserter.text).write("\\foobar").clickOn(inserter.ok);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0) instanceof Text);
	}

	@Test
	public void testFlushCodeOnOpen(final FxRobot robot) {
		robot.clickOn(inserter.text).write("foobar").clickOn(inserter.ok);
		WaitForAsyncUtils.waitForFxEvents();
		Platform.runLater(() -> inserter.setActivated(true));
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0, inserter.text.getLength());
	}
}
