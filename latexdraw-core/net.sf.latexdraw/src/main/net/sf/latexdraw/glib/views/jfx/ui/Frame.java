package net.sf.latexdraw.glib.views.jfx.ui;

import java.net.URL;
import java.util.ResourceBundle;

import net.sf.latexdraw.glib.views.jfx.Canvas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

public class Frame implements Initializable {

	@FXML protected ScrollPane scrollPane;
	@FXML protected Canvas canvas;
	
	public Frame() {
		super();
	}

	
	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		scrollPane.widthProperty().addListener(obs -> canvas.update());
		scrollPane.heightProperty().addListener(obs -> canvas.update());
	}
}
