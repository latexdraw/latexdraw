package net.sf.latexdraw.view.jfx.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import net.sf.latexdraw.view.jfx.Canvas;

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
