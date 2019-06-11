package net.sf.latexdraw.command;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
public class TestLoadDrawing extends IOCmdBaseTest {
	@Mock ResourceBundle lang;
	LoadDrawing cmd;

	@BeforeEach
	void setUp() {
		cmd = new LoadDrawing(file, openSaver, progressBar, statusWidget, ui, fileChooser, Optional.of(currentFolder), lang, mainstage);
	}

	@Test
	void testCanDoKO2() {
		cmd.setUi(null);
		assertThat(cmd.canDo()).isFalse();
	}

	@Test
	void testCanDoKO1() {
		cmd.setOpenSaveManager(null);
		assertThat(cmd.canDo()).isFalse();
	}

	@Test
	void testCanDoOK() {
		assertThat(cmd.canDo()).isTrue();
		assertThat(cmd.hadEffect()).isFalse();
	}

	@Test
	void testIsNotModifiedFileNotNull() throws ExecutionException, InterruptedException {
		final Task<Boolean> task = Mockito.mock(Task.class);
		Mockito.when(task.get()).thenReturn(Boolean.TRUE);
		Mockito.when(ui.isModified()).thenReturn(Boolean.FALSE);
		Mockito.when(file.canRead()).thenReturn(Boolean.TRUE);
		Mockito.when(file.getPath()).thenReturn("bar");
		Mockito.when(openSaver.open(file.getPath(), progressBar, statusWidget)).thenReturn(task);
		cmd.doIt();
		cmd.done();
		Mockito.verify(ui, Mockito.times(1)).reinit();
		Mockito.verify(openSaver, Mockito.times(1)).open("bar", progressBar, statusWidget);
		assertThat(cmd.hadEffect()).isTrue();
	}

	@Test
	void testIsNotModifiedFileNull() throws ExecutionException, InterruptedException {
		cmd.setFile(null);
		final File stubFile = Mockito.mock(File.class);
		final Task<Boolean> task = Mockito.mock(Task.class);
		Mockito.when(task.get()).thenReturn(Boolean.TRUE);
		Mockito.when(ui.isModified()).thenReturn(Boolean.FALSE);
		Mockito.when(stubFile.canRead()).thenReturn(Boolean.TRUE);
		Mockito.when(stubFile.getPath()).thenReturn("bar");
		Mockito.when(openSaver.open(stubFile.getPath(), progressBar, statusWidget)).thenReturn(task);
		Mockito.when(fileChooser.showOpenDialog(mainstage)).thenReturn(stubFile);
		cmd.doIt();
		cmd.done();
		Mockito.verify(ui, Mockito.times(1)).reinit();
		Mockito.verify(openSaver, Mockito.times(1)).open("bar", progressBar, statusWidget);
		assertThat(cmd.hadEffect()).isTrue();
	}

	@Test
	void testIsNotModifiedFileCannotRead() {
		Mockito.when(ui.isModified()).thenReturn(Boolean.FALSE);
		Mockito.when(file.canRead()).thenReturn(Boolean.FALSE);
		cmd.doIt();
		cmd.done();
		Mockito.verify(ui, Mockito.never()).reinit();
		Mockito.verify(openSaver, Mockito.never()).open("bar", progressBar, statusWidget);
		assertThat(cmd.hadEffect()).isFalse();
	}

	@Test
	void testIsModifiedAndDoNotSave(final FxRobot robot) throws ExecutionException, InterruptedException {
		final Task<Boolean> taskLoad = Mockito.mock(Task.class);
		Mockito.when(taskLoad.get()).thenReturn(Boolean.TRUE);
		Mockito.when(ui.isModified()).thenReturn(Boolean.TRUE);
		Mockito.when(file.getPath()).thenReturn("foo.svg");
		Mockito.when(file.canRead()).thenReturn(Boolean.TRUE);
		Mockito.when(openSaver.open("foo.svg", progressBar, statusWidget)).thenReturn(taskLoad);


		Platform.runLater(() -> cmd.doIt());
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.LEFT, KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		cmd.done();

		Mockito.verify(openSaver, Mockito.never()).save(Mockito.any(), Mockito.any(), Mockito.any());
		Mockito.verify(openSaver, Mockito.times(1)).open("foo.svg", progressBar, statusWidget);
		Mockito.verify(ui, Mockito.times(1)).reinit();
		assertThat(cmd.hadEffect()).isTrue();
	}

	@Test
	void testIsModifiedAndSaveNoFile(final FxRobot robot) throws ExecutionException, InterruptedException {
		final File stubFile = Mockito.mock(File.class);
		final Task<Boolean> taskSave = Mockito.mock(Task.class);
		final Task<Boolean> taskLoad = Mockito.mock(Task.class);
		cmd.setFile(null);
		Mockito.when(taskSave.get()).thenReturn(Boolean.TRUE);
		Mockito.when(taskLoad.get()).thenReturn(Boolean.TRUE);
		Mockito.when(ui.isModified()).thenReturn(Boolean.TRUE);
		Mockito.when(stubFile.getPath()).thenReturn("bar.svg");
		Mockito.when(file.getPath()).thenReturn("foo.svg");
		Mockito.when(file.canRead()).thenReturn(Boolean.TRUE);
		Mockito.when(fileChooser.showSaveDialog(mainstage)).thenReturn(stubFile);
		Mockito.when(fileChooser.showOpenDialog(mainstage)).thenReturn(file);
		Mockito.when(openSaver.save("bar.svg", progressBar, statusWidget)).thenReturn(taskSave);
		Mockito.when(openSaver.open("foo.svg", progressBar, statusWidget)).thenReturn(taskLoad);


		Platform.runLater(() -> cmd.doIt());
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		cmd.done();

		Mockito.verify(openSaver, Mockito.times(1)).save("bar.svg", progressBar, statusWidget);
		Mockito.verify(openSaver, Mockito.times(1)).open("foo.svg", progressBar, statusWidget);
		Mockito.verify(ui, Mockito.times(1)).reinit();
		Mockito.verify(fileChooser, Mockito.times(2)).setInitialDirectory(currentFolder);
		assertThat(cmd.hadEffect()).isTrue();
	}

	@Test
	void testIsModifiedAndSaveWithFile(final FxRobot robot) throws ExecutionException, InterruptedException {
		final File stubFile = Mockito.mock(File.class);
		final Task<Boolean> taskSave = Mockito.mock(Task.class);
		final Task<Boolean> taskLoad = Mockito.mock(Task.class);
		Mockito.when(taskSave.get()).thenReturn(Boolean.TRUE);
		Mockito.when(taskLoad.get()).thenReturn(Boolean.TRUE);
		Mockito.when(ui.isModified()).thenReturn(Boolean.TRUE);
		Mockito.when(stubFile.getPath()).thenReturn("bar.svg");
		Mockito.when(file.getPath()).thenReturn("foo.svg");
		Mockito.when(file.canRead()).thenReturn(Boolean.TRUE);
		Mockito.when(fileChooser.showSaveDialog(mainstage)).thenReturn(stubFile);
		Mockito.when(openSaver.save("bar.svg", progressBar, statusWidget)).thenReturn(taskSave);
		Mockito.when(openSaver.open("foo.svg", progressBar, statusWidget)).thenReturn(taskLoad);


		Platform.runLater(() -> cmd.doIt());
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		cmd.done();

		Mockito.verify(openSaver, Mockito.times(1)).save("bar.svg", progressBar, statusWidget);
		Mockito.verify(openSaver, Mockito.times(1)).open("foo.svg", progressBar, statusWidget);
		Mockito.verify(ui, Mockito.times(1)).reinit();
		Mockito.verify(fileChooser, Mockito.never()).showOpenDialog(mainstage);
		assertThat(cmd.hadEffect()).isTrue();
	}


	@Test
	void testIsModifiedAndSaveStop(final FxRobot robot) throws ExecutionException, InterruptedException {
		final File stubFile = Mockito.mock(File.class);
		final Task<Boolean> taskSave = Mockito.mock(Task.class);
		cmd.setFile(null);
		Mockito.when(taskSave.get()).thenReturn(Boolean.TRUE);
		Mockito.when(ui.isModified()).thenReturn(Boolean.TRUE);
		Mockito.when(stubFile.getPath()).thenReturn("bar.svg");
		Mockito.when(file.canRead()).thenReturn(Boolean.FALSE);
		Mockito.when(fileChooser.showSaveDialog(mainstage)).thenReturn(stubFile);
		Mockito.when(fileChooser.showOpenDialog(mainstage)).thenReturn(file);
		Mockito.when(openSaver.save("bar.svg", progressBar, statusWidget)).thenReturn(taskSave);


		Platform.runLater(() -> cmd.doIt());
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		cmd.done();

		Mockito.verify(openSaver, Mockito.times(1)).save("bar.svg", progressBar, statusWidget);
		assertThat(cmd.hadEffect()).isFalse();
	}
}
