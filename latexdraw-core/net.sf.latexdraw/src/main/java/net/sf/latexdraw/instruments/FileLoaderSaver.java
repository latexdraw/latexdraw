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
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.commands.LoadDrawing;
import net.sf.latexdraw.commands.NewDrawing;
import net.sf.latexdraw.commands.SaveDrawing;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.malai.command.Command;
import org.malai.javafx.command.IOCommand;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.WindowClosed;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	/** The path where documents are saved. */
	private String pathSave;
	/** The current file loaded or saved. */
	private File currentFile;
	/** The current working folder. */
	private File currentFolder;
	/** The fileChooser used to save drawings. */
	private FileChooser fileChooser;
	/** The instrument used to manage preferences. */
	@Inject private PreferencesSetter prefSetter;
	@Inject private StatusBarController statusBar;

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
		windowBinder(new WindowClosed(), SaveDrawing.class).on(LaTeXDraw.getInstance().getMainStage()).
			map(i -> new SaveDrawing(true, true, currentFolder, getDialog(true), prefSetter, currentFile,
				SVGDocumentGenerator.INSTANCE, statusBar.getProgressBar(), LaTeXDraw.getInstance(), statusBar.getLabel())).bind();

		// Quit shortcut
		keyWindowBinder(SaveDrawing.class).on(LaTeXDraw.getInstance().getMainStage()).with(KeyCode.W, LSystem.INSTANCE.getControlKey()).
			map(i -> new SaveDrawing(true, true, currentFolder, getDialog(true), prefSetter, currentFile,
				SVGDocumentGenerator.INSTANCE, statusBar.getProgressBar(), LaTeXDraw.getInstance(), statusBar.getLabel())).bind();

		// Save menu
		menuItemBinder(SaveDrawing.class).on(saveMenu).map(i -> new SaveDrawing(false, false, currentFolder, getDialog(true),
			prefSetter, currentFile, SVGDocumentGenerator.INSTANCE, statusBar.getProgressBar(), LaTeXDraw.getInstance(), statusBar.getLabel())).bind();

		// Save shortcut
		keyWindowBinder(SaveDrawing.class).on(LaTeXDraw.getInstance().getMainStage()).with(KeyCode.S, LSystem.INSTANCE.getControlKey()).
			map(i -> new SaveDrawing(false, false, currentFolder, getDialog(true), prefSetter, currentFile,
				SVGDocumentGenerator.INSTANCE, statusBar.getProgressBar(), LaTeXDraw.getInstance(), statusBar.getLabel())).bind();

		// Save as menu
		menuItemBinder(SaveDrawing.class).on(saveAsMenu).
			map(i -> new SaveDrawing(true, false, currentFolder, getDialog(true), prefSetter, null,
				SVGDocumentGenerator.INSTANCE, statusBar.getProgressBar(), LaTeXDraw.getInstance(), statusBar.getLabel())).bind();

		// Load menu
		menuItemBinder(LoadDrawing.class).on(loadMenu).
			map(i -> new LoadDrawing(currentFile, SVGDocumentGenerator.INSTANCE, statusBar.getProgressBar(), statusBar.getLabel(), LaTeXDraw.getInstance(),
				getDialog(false), currentFolder)).bind();

		// Load shortcut
		keyWindowBinder(LoadDrawing.class).on(LaTeXDraw.getInstance().getMainStage()).with(KeyCode.O, LSystem.INSTANCE.getControlKey()).
			map(i -> new LoadDrawing(currentFile, SVGDocumentGenerator.INSTANCE, statusBar.getProgressBar(), statusBar.getLabel(), LaTeXDraw.getInstance(),
				getDialog(false), currentFolder)).bind();

		// New menu
		menuItemBinder(NewDrawing.class).on(newMenu).
			map(i -> new NewDrawing(currentFile, SVGDocumentGenerator.INSTANCE, statusBar.getProgressBar(), statusBar.getLabel(), LaTeXDraw.getInstance(),
				getDialog(false), prefSetter, currentFolder)).bind();

		// New shortcut
		keyWindowBinder(NewDrawing.class).on(LaTeXDraw.getInstance().getMainStage()).with(KeyCode.N, LSystem.INSTANCE.getControlKey()).
			map(i -> new NewDrawing(currentFile, SVGDocumentGenerator.INSTANCE, statusBar.getProgressBar(), statusBar.getLabel(), LaTeXDraw.getInstance(),
				getDialog(false), prefSetter, currentFolder)).bind();

		// Recent files menus
		menuItemBinder(LoadDrawing.class).
			onMenu(recentFilesMenu.getItems()).
			map(i -> new LoadDrawing(new File((String) i.getWidget().getUserData()), SVGDocumentGenerator.INSTANCE,
					statusBar.getProgressBar(), statusBar.getLabel(), LaTeXDraw.getInstance(), getDialog(false), currentFolder)).
			bind();
	}

	/**
	 * @return The path where documents are saved.
	 */
	public String getPathSave() {
		return pathSave;
	}

	/**
	 * Sets the path where documents are saved.
	 * @param path The path where documents are saved.
	 */
	public void setPathSave(final String path) {
		if(path != null && new File(path).isDirectory()) {
			pathSave = path;
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
				item.setId("recent" + recentDocs.indexOf(fileName));
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

		fileChooser.setTitle(save ? LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.188") : //NON-NLS
			LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.200")); //NON-NLS

		return fileChooser;
	}

	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

		if(generalPreferences && document != null && root != null) {
			final Element elt = document.createElement(LNamespace.XML_PATH_OPEN);
			elt.setTextContent(pathSave);
			root.appendChild(elt);
		}
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(true);
	}
}
