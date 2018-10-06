module latexdraw {
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.swing;
	requires java.logging;
	requires malai.core;
	requires malai.javafx;
	requires antlr4.runtime;
	requires pdf.renderer;

	exports net.sf.latexdraw to javafx.graphics;
	exports net.sf.latexdraw.ui to javafx.fxml;
	exports net.sf.latexdraw.util to javafx.fxml;
	exports net.sf.latexdraw.view to javafx.fxml;
	exports net.sf.latexdraw.view.jfx to javafx.fxml;

	opens net.sf.latexdraw.instruments to javafx.fxml;
	opens res;
	opens lang;
	opens fxml;
	opens css;
	opens res.hatch;
	opens res.align;
	opens res.arrowStyles;
	opens res.cursors;
	opens res.distrib;
	opens res.dotStyles;
	opens res.doubleBoundary;
	opens res.freehand;
	opens res.lineStyles;
}
