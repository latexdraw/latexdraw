package net.sf.latexdraw.command;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.interacto.command.Command;
import io.github.interacto.command.CommandsRegistry;
import io.github.interacto.undo.UndoCollector;
import io.github.interacto.undo.Undoable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
public class TestNewDrawing extends IOCmdBaseTest {
	@Mock
	ResourceBundle lang;
	NewDrawing cmd;


	@BeforeEach
	protected void configCorrectCmd() {
		cmd = new NewDrawing(file, openSaver, progressBar, statusWidget, ui, fileChooser, Optional.of(currentFolder), lang, mainstage);
	}

	@Test
	void testCanDoOK() {
		assertThat(cmd.canDo()).isTrue();
		assertThat(cmd.hadEffect()).isFalse();
	}

	@Test
	void testCanDoKO1() {
		cmd.setOpenSaveManager(null);
		assertThat(cmd.canDo()).isFalse();
	}

	@Test
	void testCanDoKO2() {
		cmd.setUi(null);
		assertThat(cmd.canDo()).isFalse();
	}

	@Test
	void testDoNotModified() {
		UndoCollector.INSTANCE.add(Mockito.mock(Undoable.class), null);
		CommandsRegistry.INSTANCE.addCommand(Mockito.mock(Command.class), null);
		Mockito.when(ui.isModified()).thenReturn(Boolean.FALSE);
		cmd.doIt();
		cmd.done();
		assertThat(cmd.hadEffect()).isTrue();
		Mockito.verify(ui, Mockito.times(1)).reinit();
		assertThat(UndoCollector.INSTANCE.getUndo()).isEmpty();
		assertThat(CommandsRegistry.INSTANCE.getCommands()).isEmpty();
	}

	@Test
	void testModifiedSave(final FxRobot robot) {
		UndoCollector.INSTANCE.add(Mockito.mock(Undoable.class), null);
		CommandsRegistry.INSTANCE.addCommand(Mockito.mock(Command.class), null);
		final Task<Boolean> task = Mockito.mock(Task.class);
		final File file = new File("foo.svg");
		Mockito.when(ui.isModified()).thenReturn(Boolean.TRUE);
		Mockito.when(fileChooser.showSaveDialog(mainstage)).thenReturn(file);
		Mockito.when(openSaver.save(file.getPath(), progressBar, statusWidget)).thenReturn(task);
		Platform.runLater(() -> cmd.doIt());
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		cmd.done();
		Mockito.verify(openSaver, Mockito.times(1)).save(file.getPath(), progressBar, statusWidget);
		assertThat(cmd.hadEffect()).isTrue();
		Mockito.verify(ui, Mockito.times(1)).reinit();
		assertThat(UndoCollector.INSTANCE.getUndo()).isEmpty();
		assertThat(CommandsRegistry.INSTANCE.getCommands()).isEmpty();
	}

	@Test
	void testModifiedDoNotSave(final FxRobot robot) {
		UndoCollector.INSTANCE.add(Mockito.mock(Undoable.class), null);
		CommandsRegistry.INSTANCE.addCommand(Mockito.mock(Command.class), null);
		Mockito.when(ui.isModified()).thenReturn(Boolean.TRUE);
		Platform.runLater(() -> cmd.doIt());
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.LEFT, KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		assertThat(cmd.hadEffect()).isFalse();
		Mockito.verify(ui, Mockito.times(1)).reinit();
		assertThat(UndoCollector.INSTANCE.getUndo()).isEmpty();
		assertThat(CommandsRegistry.INSTANCE.getCommands()).isEmpty();
	}

	@Test
	void testModifiedCancel(final FxRobot robot) {
		Mockito.when(ui.isModified()).thenReturn(Boolean.TRUE);
		Platform.runLater(() -> cmd.doIt());
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.ESCAPE);
		WaitForAsyncUtils.waitForFxEvents();
		assertThat(cmd.hadEffect()).isFalse();
		Mockito.verify(ui, Mockito.never()).reinit();
	}
}
