package net.sf.latexdraw.command;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import net.sf.latexdraw.service.PreferencesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
public class TestSaveDrawing extends IOCmdBaseTest {
	@Mock
	PreferencesService prefs;
	SaveDrawing cmd;

	@Test
	void testUIModifiedCancel(final FxRobot robot) {
		cmd = new SaveDrawing(false, true, Optional.of(currentFolder), fileChooser, prefs, file, openSaver, progressBar, ui, statusWidget, mainstage);
		final ResourceBundle lang = Mockito.mock(ResourceBundle.class);
		Mockito.when(ui.isModified()).thenReturn(Boolean.TRUE);
		Mockito.when(prefs.getBundle()).thenReturn(lang);
		Platform.runLater(() -> cmd.doIt());
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.RIGHT).type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		cmd.done();
		assertThat(cmd.hadEffect()).isFalse();
	}

	@Test
	void testUIModifiedSave(final FxRobot robot) throws ExecutionException, InterruptedException {
		final Task<Boolean> task = Mockito.mock(Task.class);
		final File stubFile = new File("foo.svg");
		Mockito.when(task.get()).thenReturn(Boolean.TRUE);
		Mockito.when(openSaver.save("foo.svg", progressBar, statusWidget)).thenReturn(task);
		cmd = new SaveDrawing(false, true, Optional.of(currentFolder), fileChooser, prefs, file, openSaver, progressBar, ui, statusWidget, mainstage);
		Mockito.when(fileChooser.showSaveDialog(Mockito.any())).thenReturn(stubFile);
		final ResourceBundle lang = Mockito.mock(ResourceBundle.class);
		Mockito.when(ui.isModified()).thenReturn(Boolean.TRUE);
		Mockito.when(prefs.getBundle()).thenReturn(lang);
		Platform.runLater(() -> cmd.doIt());
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		cmd.done();
		assertThat(cmd.hadEffect()).isTrue();
		Mockito.verify(openSaver, Mockito.times(1)).save("foo.svg", progressBar, statusWidget);
		Mockito.verify(prefs, Mockito.times(1)).writePreferences();
	}

	@Test
	void testUIModifiedDoNotSave(final FxRobot robot) {
		cmd = new SaveDrawing(false, true, Optional.of(currentFolder), fileChooser, prefs, file, openSaver, progressBar, ui, statusWidget, mainstage);
		Mockito.when(ui.isModified()).thenReturn(Boolean.TRUE);
		final ResourceBundle lang = Mockito.mock(ResourceBundle.class);
		Mockito.when(prefs.getBundle()).thenReturn(lang);
		Platform.runLater(() -> cmd.doIt());
		WaitForAsyncUtils.waitForFxEvents();
		robot.type(KeyCode.LEFT, KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		cmd.done();
		Mockito.verify(prefs, Mockito.times(1)).writePreferences();
		Mockito.verify(mainstage, Mockito.times(1)).close();
	}

	@Test
	void testUINotModifiedDoNotSave() {
		cmd = new SaveDrawing(false, true, Optional.of(currentFolder), fileChooser, prefs, file, openSaver, progressBar, ui, statusWidget, mainstage);
		Mockito.when(ui.isModified()).thenReturn(Boolean.FALSE);
		cmd.doIt();
		cmd.done();
		Mockito.verify(prefs, Mockito.times(1)).writePreferences();
		Mockito.verify(mainstage, Mockito.times(1)).close();
	}

	@Nested
	class NotSaveOnClose {
		@BeforeEach
		protected void configCorrectCmd() {
			cmd = new SaveDrawing(false, false, Optional.of(currentFolder), fileChooser, prefs, file, openSaver, progressBar, ui, statusWidget, mainstage);
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
		void testCanDoOK() {
			assertThat(cmd.canDo()).isTrue();
			assertThat(cmd.hadEffect()).isFalse();
		}

		@Test
		void testNotSaveCloseFileNullKO() {
			cmd.setFile(null);
			cmd.doIt();
			cmd.done();
			assertThat(cmd.hadEffect()).isFalse();
			Mockito.verify(openSaver, Mockito.never()).save(Mockito.any(), Mockito.any(), Mockito.any());
		}

		@Nested
		class DoIt {
			@Mock Task<Boolean> task;

			@BeforeEach
			void setUp() throws ExecutionException, InterruptedException {
				Mockito.when(task.get()).thenReturn(true);
			}

			@Test
			void testNotSaveCloseFileNotNull() {
				Mockito.when(file.getPath()).thenReturn("foo");
				Mockito.when(openSaver.save("foo", progressBar, statusWidget)).thenReturn(task);
				final boolean ok = cmd.doIt();
				assertThat(ok).isTrue();
				Mockito.verify(openSaver, Mockito.times(1)).save("foo", progressBar, statusWidget);
			}

			@Test
			void testNotSaveCloseFileNullOK() {
				Mockito.when(openSaver.save("fooo.svg", progressBar, statusWidget)).thenReturn(task);
				final File stubFile = new File("fooo");
				cmd.setFile(null);
				Mockito.when(ui.isModified()).thenReturn(Boolean.TRUE);
				Mockito.when(fileChooser.showSaveDialog(Mockito.any())).thenReturn(stubFile);
				cmd.doIt();
				cmd.done();
				assertThat(cmd.hadEffect()).isTrue();
				Mockito.verify(openSaver, Mockito.times(1)).save("fooo.svg", progressBar, statusWidget);
			}
		}
	}
}
