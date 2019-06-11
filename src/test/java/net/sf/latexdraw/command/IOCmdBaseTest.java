package net.sf.latexdraw.command;

import java.io.File;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.malai.javafx.ui.JfxUI;
import org.malai.javafx.ui.OpenSaver;
import org.mockito.Mock;

abstract class IOCmdBaseTest {
	@Mock
	File file;
	@Mock
	OpenSaver<Label> openSaver;
	@Mock
	ProgressBar progressBar;
	@Mock
	Label statusWidget;
	@Mock
	JfxUI ui;
	@Mock
	FileChooser fileChooser;
	@Mock
	File currentFolder;
	@Mock
	Stage mainstage;
}
