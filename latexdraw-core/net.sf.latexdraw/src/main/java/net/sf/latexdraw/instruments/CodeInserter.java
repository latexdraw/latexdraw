/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.commands.InsertPSTCode;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.parsers.pst.PSTContext;
import net.sf.latexdraw.parsers.pst.PSTLatexdrawListener;
import net.sf.latexdraw.parsers.pst.PSTLexer;
import net.sf.latexdraw.parsers.pst.PSTParser;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.LangTool;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.malai.command.library.InactivateInstrument;
import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument converts PST code into graphical shapes.
 * @author Arnaud BLOUIN
 */
public class CodeInserter extends JfxInstrument implements Initializable {
	@FXML protected TextArea label;
	@FXML protected Button ok;
	@FXML protected Button cancel;
	@FXML protected TextArea text;
	@FXML protected TextArea errorLog;
	private Stage codeInserterDialogue;
	@Inject private IDrawing drawing;
	@Inject private StatusBarController statusBar;

	/**
	 * Creates the instrument.
	 */
	public CodeInserter() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		label.setText(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.16"));

		// Collecting errors from the parser.
		final ANTLRErrorListener errorListener = new BaseErrorListener() {
			@Override
			public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line, final int charPositionInLine,
									final String msg, final RecognitionException e) {
				errorLog.setText(errorLog.getText() + "Syntax error: " + msg + LSystem.EOL); //NON-NLS
			}
		};

		final PSTLatexdrawListener listener = new PSTLatexdrawListener() {
			@Override
			public void exitUnknowncmds(final PSTParser.UnknowncmdsContext ctx) {
				errorLog.setText(errorLog.getText() + "Unknown command: " + ctx.LATEXCMD().getSymbol().getText() + LSystem.EOL); //NON-NLS
			}

			@Override
			public void enterUnknownParamSetting(final PSTParser.UnknownParamSettingContext ctx) {
				errorLog.setText(errorLog.getText() + "Unknown parameter: " + ctx.name.getText() + LSystem.EOL); //NON-NLS
			}

			@Override
			public void visitErrorNode(final ErrorNode node) {
				errorLog.setText(errorLog.getText() + "Error: " + node.getText() + LSystem.EOL); //NON-NLS
			}

			@Override
			public void exitText(final PSTParser.TextContext ctx) {
				super.exitText(ctx);
				if(ctx.getText().startsWith("\\")) {
					errorLog.setText(errorLog.getText() + "Bad command: '" + ctx.getText() + "'?" + LSystem.EOL); //NON-NLS
				}
			}
		};

		listener.log.addHandler(new Handler() {
			@Override
			public void publish(final LogRecord record) {
				errorLog.setText(errorLog.getText() + record.getMessage() + LSystem.EOL);
			}

			@Override
			public void flush() {
			}

			@Override
			public void close() {
			}
		});

		// On each text change, the code is parsed and errors reported.
		text.textProperty().addListener((observable, oldValue, newValue) -> {
			errorLog.setText("");
			final PSTLexer lexer = new PSTLexer(CharStreams.fromString(newValue));
			lexer.addErrorListener(errorListener);
			final PSTParser parser = new PSTParser(new CommonTokenStream(lexer));
			parser.addParseListener(listener);
			parser.addErrorListener(errorListener);
			parser.pstCode(new PSTContext());
			parser.getInterpreter().clearDFA();
			lexer.getInterpreter().clearDFA();
		});
	}


	/** @return The created latexdraw dialogue box. */
	protected Optional<Stage> getInsertCodeDialogue() {
		if(codeInserterDialogue == null) {
			try {
				// The FXML file only loaded only when this method is called: this JFX controller is created by
				// the app injector and lives as a singleton. A call to this function loads the FXML.
				final Parent root = FXMLLoader.load(getClass().getResource("/fxml/InsertCode.fxml"), LangTool.INSTANCE.getBundle(), //NON-NLS
					new JavaFXBuilderFactory(),
					LaTeXDraw.getInstance().getInstanceCallBack());
				final Scene scene = new Scene(root);
				codeInserterDialogue = new Stage(StageStyle.UTILITY);
				codeInserterDialogue.setTitle(LangTool.INSTANCE.getBundle().getString("InsertPSTricksCodeFrame.0"));
				codeInserterDialogue.setScene(scene);
				codeInserterDialogue.setOnHiding(evt -> setActivated(false));
			}catch(final Exception ex) {
				BadaboomCollector.INSTANCE.add(ex);
			}
		}
		return Optional.ofNullable(codeInserterDialogue);
	}

	@Override
	public void configureBindings() {
		buttonBinder(InsertPSTCode.class).on(ok).map(i -> new InsertPSTCode(text.getText(), statusBar.getLabel(), drawing)).bind();

		buttonBinder(InactivateInstrument.class).on(cancel, ok).first(cmd -> cmd.setInstrument(this)).bind();
	}

	@Override
	public void setActivated(final boolean activated) {
		if(isActivated() != activated) {
			getInsertCodeDialogue().ifPresent(dialogue -> {
				super.setActivated(activated);
				if(activated) {
					dialogue.show();
					dialogue.centerOnScreen();
				}else {
					dialogue.hide();
				}
			});
		}
	}
}
