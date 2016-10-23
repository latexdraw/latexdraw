package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import net.sf.latexdraw.glib.views.latex.LaTeXGenerator;
import net.sf.latexdraw.glib.views.pst.PSTCodeGenerator;

import java.net.URL;
import java.util.ResourceBundle;

public class CodePanelController extends CanvasInstrument implements Initializable {
	@FXML TextArea codeArea;
	@FXML Tab tab;

	/** The PSTricks generator. */
	LaTeXGenerator pstGenerator;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		pstGenerator = new PSTCodeGenerator(canvas.getDrawing(), canvas, true, true);

		tab.selectedProperty().addListener(evt -> codeArea.setText(pstGenerator.getDrawingCode()));
	}


	@Override
	protected void initialiseInteractors() throws InstantiationException, IllegalAccessException {

	}

	public LaTeXGenerator getPstGenerator() {
		return pstGenerator;
	}
}
