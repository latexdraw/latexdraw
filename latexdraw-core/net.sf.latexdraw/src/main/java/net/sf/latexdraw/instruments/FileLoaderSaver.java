/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
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
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.actions.LoadDrawing;
import net.sf.latexdraw.actions.NewDrawing;
import net.sf.latexdraw.actions.SaveDrawing;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.malai.action.Action;
import org.malai.javafx.action.IOAction;
import org.malai.javafx.binding.JfxMenuItemBinding;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.MenuItemPressed;
import org.malai.javafx.interaction.library.WindowClosed;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Saves and loads documents.
 * @author Arnaud Blouin
 */
public class FileLoaderSaver extends JfxInstrument implements Initializable {
	private static final BiConsumer<IOAction<Label>, FileLoaderSaver> initIOAction = (action, instrument) -> {
		action.setStatusWidget(instrument.statusBar.getLabel());
		action.setProgressBar(instrument.statusBar.getProgressBar());
		action.setOpenSaveManager(SVGDocumentGenerator.INSTANCE);
		action.setUi(LaTeXDraw.getInstance());
	};

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
	private RecentMenuItem2LoadInteractor recentInterator;
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
	public void onActionDone(final Action action) {
		super.onActionDone(action);

		fileMenu.hide();

		// Updating the recent files on I/O actions.
		if(action instanceof IOAction && action.hadEffect()) {
			final IOAction<?> ioAction = (IOAction<?>) action;
			currentFile = ioAction.getFile();
			currentFolder = currentFile.getParentFile();
			if(!currentFile.getPath().endsWith(".svg")) {//$NON-NLS-1$
				currentFile = new File(currentFile.getPath() + ".svg");//$NON-NLS-1$
			}
			prefSetter.addRecentFile(((IOAction<?>) action).getFile().getPath());
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
	public void onActionExecuted(final Action action) {
		if(action instanceof LoadDrawing) {
			currentFile = ((LoadDrawing) action).getFile();
			if(currentFile != null) {
				currentFolder = currentFile.getParentFile();
			}
		}
	}

	@Override
	protected void configureBindings() throws IllegalAccessException, InstantiationException {
		final BiConsumer<SaveDrawing, FileLoaderSaver> initSaveAction = (action, instrument) -> {
			initIOAction.accept(action, instrument);
			action.setFileChooser(instrument.getDialog(true));
			action.setFile(instrument.currentFile);
			action.setCurrentFolder(instrument.currentFolder);
			action.setPrefSetter(prefSetter);
		};

		final Consumer<SaveDrawing> saveAction = action -> {
			initSaveAction.accept(action, FileLoaderSaver.this);
			action.setSaveAs(false);
			action.setSaveOnClose(false);
		};

		final Consumer<LoadDrawing> loadAction = action -> {
			initIOAction.accept(action, this);
			action.setFileChooser(getDialog(false));
			action.setCurrentFolder(currentFolder);
		};

		final Consumer<NewDrawing> newAction = action -> {
			initIOAction.accept(action, this);
			action.setPrefSetter(prefSetter);
			action.setFileChooser(getDialog(false));
			action.setCurrentFolder(currentFolder);
		};

		// Close window
		windowBinder(SaveDrawing.class, new WindowClosed()).on(LaTeXDraw.getInstance().getMainStage()).init(action -> {
			initSaveAction.accept(action, FileLoaderSaver.this);
			action.setSaveAs(true);
			action.setSaveOnClose(true);
		}).bind();

		// Quit shortcut
		keyWindowBinder(SaveDrawing.class).on(LaTeXDraw.getInstance().getMainStage()).with(KeyCode.W, LSystem.INSTANCE.getControlKey()).
			init(action -> {
				initSaveAction.accept(action, FileLoaderSaver.this);
				action.setSaveAs(true);
				action.setSaveOnClose(true);
		}).bind();

		// Save menu
		menuItemBinder(SaveDrawing.class).on(saveMenu).init(saveAction).bind();

		// Save shortcut
		keyWindowBinder(SaveDrawing.class).on(LaTeXDraw.getInstance().getMainStage()).with(KeyCode.S, LSystem.INSTANCE.getControlKey()).
			init(saveAction).bind();

		// Save as menu
		menuItemBinder(SaveDrawing.class).on(saveAsMenu).init(action -> {
			initSaveAction.accept(action, FileLoaderSaver.this);
			action.setSaveAs(true);
			action.setSaveOnClose(false);
			action.setFile(null);
		}).bind();

		// Load menu
		menuItemBinder(LoadDrawing.class).on(loadMenu).init(loadAction).bind();

		// Load shortcut
		keyWindowBinder(LoadDrawing.class).on(LaTeXDraw.getInstance().getMainStage()).with(KeyCode.O, LSystem.INSTANCE.getControlKey()).
			init(loadAction).bind();

		// New menu
		menuItemBinder(NewDrawing.class).on(newMenu).init(newAction).bind();

		// New shortcut
		keyWindowBinder(NewDrawing.class).on(LaTeXDraw.getInstance().getMainStage()).with(KeyCode.N, LSystem.INSTANCE.getControlKey()).
			init(newAction).bind();

		// Recent files menus
		recentInterator = new RecentMenuItem2LoadInteractor(this);
		addBinding(recentInterator);
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
				recentFilesMenu.getItems().add(item);
			});
			if(recentInterator != null) {
				recentInterator.getInteraction().registerToMenuItems(recentFilesMenu.getItems());
			}
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
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SVG", "*.svg")); //$NON-NLS-1$ //$NON-NLS-2$
		}

		fileChooser.setTitle(save ? LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.188") : //$NON-NLS-1$
			LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.200")); //$NON-NLS-1$

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

	private static final class RecentMenuItem2LoadInteractor extends JfxMenuItemBinding<LoadDrawing, MenuItemPressed, FileLoaderSaver> {
		private RecentMenuItem2LoadInteractor(final FileLoaderSaver ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, LoadDrawing.class, new MenuItemPressed(), Collections.emptyList());
		}

		@Override
		public void initAction() {
			initIOAction.accept(action, instrument);
			action.setFileChooser(instrument.getDialog(false));
			action.setCurrentFolder(instrument.currentFolder);
			action.setFile(new File((String) interaction.getWidget().getUserData()));
		}
	}
}
