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
package net.sf.latexdraw.instrument;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.latexdraw.command.LoadDrawing;
import net.sf.latexdraw.command.NewDrawing;
import net.sf.latexdraw.command.SaveDrawing;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.malai.command.Command;
import org.malai.javafx.command.IOCommand;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.WindowClosed;
import org.malai.javafx.ui.JfxUI;

/**
 * Saves and loads documents.
 * @author Arnaud Blouin
 */
public class FileLoaderSaver extends JfxInstrument implements Initializable {
	/** The menu used to save documents. */
	@FXML private MenuItem saveMenu;
	/** The menu used to load documents. */
	@FXML private MenuItem loadMenu;
	/** The menu used to create a new document. */
	@FXML private MenuItem newMenu;
	/** The menu that contains the menu item corresponding to the recent documents. */
	@FXML private Menu recentFilesMenu;
	/** The menu item used to save as a document. */
	@FXML private MenuItem saveAsMenu;
	@FXML private MenuButton fileMenu;
	/** The current file loaded or saved. */
	private File currentFile;
	/** The current working folder. */
	private File currentFolder;
	/** The fileChooser used to save drawings. */
	private FileChooser fileChooser;
	/** The instrument used to manage preferences. */
	@Inject private PreferencesSetter prefSetter;
	@Inject private StatusBarController statusBar;
	@Inject private SystemService system;
	@Inject private SVGDocumentGenerator svgGen;
	@Inject private LangService lang;
	@Inject private JfxUI app;
	@Inject private Stage mainstage;

	/**
	 * Creates the file loader/saver.
	 */
	public FileLoaderSaver() {
		super();
	}

	@Override
	public void reinit() {
		currentFile = null;
		fileChooser = null;
	}

	@Override
	public void onCmdDone(final Command cmd) {
		super.onCmdDone(cmd);

		fileMenu.hide();

		// Updating the recent files on I/O commands.
		if(cmd instanceof IOCommand && cmd.hadEffect()) {
			final IOCommand<?> ioCmd = (IOCommand<?>) cmd;
			currentFile = ioCmd.getFile();
			currentFolder = currentFile.getParentFile();
			if(!currentFile.getPath().endsWith(".svg")) { //NON-NLS
				currentFile = new File(currentFile.getPath() + ".svg"); //NON-NLS
			}
			prefSetter.addRecentFile(((IOCommand<?>) cmd).getFile().getPath());
			updateRecentMenuItems(prefSetter.getRecentFileNames());
		}
	}

	@Override
	public void setActivated(final boolean activated, final boolean hide) {
		super.setActivated(activated);

		final boolean showButtons = activated || !hide;
		newMenu.setVisible(showButtons);
		saveAsMenu.setVisible(showButtons);
		saveMenu.setVisible(showButtons);
		loadMenu.setVisible(showButtons);

		if(showButtons) {
			newMenu.setDisable(!activated);
			saveAsMenu.setDisable(!activated);
			saveMenu.setDisable(!activated);
			loadMenu.setDisable(!activated);
		}
	}

	@Override
	public void setActivated(final boolean activated) {
		setActivated(activated, true);
	}

	@Override
	public void onCmdExecuted(final Command cmd) {
		if(cmd instanceof LoadDrawing) {
			currentFile = ((LoadDrawing) cmd).getFile();
			if(currentFile != null) {
				currentFolder = currentFile.getParentFile();
			}
		}
	}

	@Override
	protected void configureBindings() {
		// Close window
		windowBinder(new WindowClosed(), i -> new SaveDrawing(true, true, currentFolder, getDialog(true), prefSetter, currentFile,
			svgGen, statusBar.getProgressBar(), app, statusBar.getLabel(), lang, mainstage)).
			on(mainstage).bind();

		// Quit shortcut
		keyWindowBinder(i -> new SaveDrawing(true, true, currentFolder, getDialog(true), prefSetter, currentFile,
			svgGen, statusBar.getProgressBar(), app, statusBar.getLabel(), lang, mainstage)).
			on(mainstage).with(KeyCode.W, system.getControlKey()).bind();

		// Save menu
		menuItemBinder(i -> new SaveDrawing(false, false, currentFolder, getDialog(true),
			prefSetter, currentFile, svgGen, statusBar.getProgressBar(), app, statusBar.getLabel(), lang, mainstage)).
			on(saveMenu).bind();

		// Save shortcut
		keyWindowBinder(i -> new SaveDrawing(false, false, currentFolder, getDialog(true), prefSetter, currentFile,
			svgGen, statusBar.getProgressBar(), app, statusBar.getLabel(), lang, mainstage)).
			on(mainstage).with(KeyCode.S, system.getControlKey()).bind();

		// Save as menu
		menuItemBinder(i -> new SaveDrawing(true, false, currentFolder, getDialog(true), prefSetter, null,
			svgGen, statusBar.getProgressBar(), app, statusBar.getLabel(), lang, mainstage)).on(saveAsMenu).bind();

		// Load menu
		menuItemBinder(i -> new LoadDrawing(currentFile, svgGen, statusBar.getProgressBar(), statusBar.getLabel(), app,
			getDialog(false), currentFolder, lang, mainstage)).on(loadMenu).bind();

		// Load shortcut
		keyWindowBinder(i -> new LoadDrawing(currentFile, svgGen, statusBar.getProgressBar(), statusBar.getLabel(), app,
			getDialog(false), currentFolder, lang, mainstage)).on(mainstage).with(KeyCode.O, system.getControlKey()).bind();

		// New menu
		menuItemBinder(i -> new NewDrawing(currentFile, svgGen, statusBar.getProgressBar(), statusBar.getLabel(), app,
			getDialog(false), currentFolder, lang, mainstage)).on(newMenu).bind();

		// New shortcut
		keyWindowBinder(i -> new NewDrawing(currentFile, svgGen, statusBar.getProgressBar(), statusBar.getLabel(), app,
			getDialog(false), currentFolder, lang, mainstage)).on(mainstage).with(KeyCode.N, system.getControlKey()).bind();

		// Recent files menus
		menuItemBinder(i -> new LoadDrawing(new File((String) i.getWidget().getUserData()), svgGen,
			statusBar.getProgressBar(), statusBar.getLabel(), app, getDialog(false), currentFolder, lang, mainstage)).
			onMenu(recentFilesMenu.getItems()).bind();
	}


	/**
	 * Sets the path where documents are saved.
	 * @param path The path where documents are saved.
	 */
	public void setCurrentFolder(final String path) {
		if(path != null && new File(path).isDirectory()) {
			currentFolder = new File(path);
		}
	}

	/**
	 * @return The current file loaded or saved.
	 */
	public File getCurrentFile() {
		return currentFile;
	}

	/**
	 * Sets the current file loaded or saved.
	 * @param file The current file loaded or saved.
	 */
	public void setCurrentFile(final File file) {
		if(file != null) {
			currentFile = file;
		}
	}

	/**
	 * Updates the recent menu items.
	 * @param recentDocs The list of recent documents.
	 */
	public void updateRecentMenuItems(final List<String> recentDocs) {
		// Clean menus before removing them?
		recentFilesMenu.getItems().clear();

		if(recentDocs != null && !recentDocs.isEmpty()) {
			recentDocs.forEach(fileName -> {
				final MenuItem item = new MenuItem(new File(fileName).getName());
				item.setUserData(fileName);
				item.setId("recent" + recentDocs.indexOf(fileName)); //NON-NLS
				recentFilesMenu.getItems().add(item);
			});
		}

		recentFilesMenu.setDisable(recentFilesMenu.getItems().isEmpty());
	}

	/**
	 * @param save True: the dialogue box will be configured in a saving purpose. Otherwise, in an loading purpose.
	 * @return The dialogue box to open or save drawings.
	 */
	public FileChooser getDialog(final boolean save) {
		if(fileChooser == null) {
			fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().clear();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SVG", "*.svg")); //NON-NLS
		}

		fileChooser.setTitle(save ? lang.getBundle().getString("LaTeXDrawFrame.188") : //NON-NLS
			lang.getBundle().getString("LaTeXDrawFrame.200")); //NON-NLS

		return fileChooser;
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
	}
}
