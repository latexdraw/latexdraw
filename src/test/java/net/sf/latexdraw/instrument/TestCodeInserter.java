package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;
import javafx.application.HostServices;
import net.sf.latexdraw.data.ConfigureInjection;
import net.sf.latexdraw.data.InjectionExtension;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.Injector;
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
				bindAsEagerSingleton(PreferencesService.class);
				bindWithCommand(ResourceBundle.class, PreferencesService.class, pref -> pref.getBundle());
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
		Cmds.of(CmdFXVoid.of(() -> {
			try {
				FxToolkit.registerStage(() -> inserter.getInsertCodeDialogue().orElse(null));
				WaitForAsyncUtils.waitForFxEvents();
			}catch(final TimeoutException ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
			inserter.setActivated(true);
		}), CmdFXVoid.of(() -> {
			inserter.getInsertCodeDialogue().ifPresent(stage -> {
				stage.show();
				stage.toFront();
			});
		})).execute();
	}

	@Test
	public void testCancelDeactivateHide(final FxRobot robot) {
		Cmds.of(() -> robot.clickOn(inserter.cancel)).execute();
		assertFalse(inserter.isActivated());
	}

	@Test
	public void testOkWithNoCode(final FxRobot robot) {
		Cmds.of(() -> robot.clickOn(inserter.ok)).execute();
		assertFalse(inserter.isActivated());
		assertTrue(drawing.isEmpty());
	}

	@Test
	public void testTypeBadCodeOK(final FxRobot robot) {
		Cmds.of(() -> robot.clickOn(inserter.text).write("\\gridGapProp \\psframe[gridGapProp=10]")).execute();
		assertFalse(inserter.errorLog.getText().isEmpty());
	}

	@Test
	public void testDeactivateHide() {
		Cmds.of(CmdFXVoid.of(() -> inserter.setActivated(false))).execute();
		assertFalse(inserter.getInsertCodeDialogue().orElseThrow(() -> new IllegalArgumentException()).isShowing());
	}

	@Test
	public void testEnterCodeOKCreatesGoodShape(final FxRobot robot) {
		Cmds.of(() -> robot.clickOn(inserter.text).write("\\psframe(0,0)(100,100)").clickOn(inserter.ok)).execute();
		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Rectangle);
	}

	@Test
	public void testEnterCodeKOCreatesText(final FxRobot robot) {
		Cmds.of(() -> robot.clickOn(inserter.text).write("\\foobar").clickOn(inserter.ok)).execute();
		assertEquals(1, drawing.size());
		assertTrue(drawing.getShapeAt(0).orElseThrow() instanceof Text);
	}

	@Test
	public void testFlushCodeOnOpen(final FxRobot robot) {
		Cmds.of(
			() -> robot.clickOn(inserter.text).write("foobar").clickOn(inserter.ok),
			CmdFXVoid.of(() -> inserter.setActivated(true))).execute();
		assertEquals(0, inserter.text.getLength());
	}
}
