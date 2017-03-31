package net.sf.latexdraw.instruments;

import com.google.inject.Inject;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javax.swing.JFileChooser;
import net.sf.latexdraw.actions.LoadDrawing;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LangTool;
import org.malai.action.Action;
import org.malai.javafx.instrument.JfxInstrument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This instrument saves and loads documents.
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 06/01/2010
 * @author Arnaud BLOUIN, Jan-Cornelius MOLNAR
 * @since 3.0
 */
public class FileLoaderSaver extends JfxInstrument {
	/** The menu item used to save as a document. */
	@FXML protected MenuItem saveAsMenu;

	/** The menu used to save documents. */
	@FXML protected MenuItem saveMenu;

	/** The menu used to load documents. */
	@FXML protected MenuItem loadMenu;

	/** The menu used to create a new document. */
	@FXML protected MenuItem newMenu;

	/**
	 * The menu that contains the menu item corresponding to the recent
	 * documents.
	 */
	@FXML protected Menu recentFilesMenu;

	/** The path where documents are saved. */
	protected String pathSave;

	/** The current file loaded or saved. */
	protected File currentFile;

	/** The current working folder. */
	protected File currentFolder;

	/** The fileChooser used to save drawings. */
	protected JFileChooser fileChooser;

	// /** The UI to save/open. */
	// protected LFrame ui;

	// /** The field where messages are displayed. */
	// protected JLabel statusBar;

	/** The instrument used to manage preferences. */
	@Inject protected PreferencesSetter prefSetter;

	// /** The progress bar used to show the progress of loading and saving
	// operations. */
	// protected MProgressBar progressBar;

	/**
	 * Creates the file loader/saver.
	 */
	FileLoaderSaver() {
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

		if(action!=null&&action.hadEffect()) {
			final File file = fileChooser==null?null:fileChooser.getSelectedFile();

			if(file!=null) {
				currentFolder = file.getParentFile();
				currentFile = file;
				if(!currentFile.getPath().endsWith(".svg"))
					currentFile = new File(currentFile.getPath()+".svg");
			}

//			// Updating the recent files on I/O actions.
//			if(action instanceof IOAction) {
//				prefSetter.addRecentFile(((IOAction<?, ?>)action).getFile().getPath());
				// updateRecentMenuItems(prefSetter.recentFilesName);
//			}
		}
	}

	// @Override
	// public void setActivated(final boolean activated, final boolean hide) {
	// super.setActivated(activated);
	//
	// final boolean showButtons = activated || !hide;
	// newMenu.setVisible(showButtons);
	// saveAsMenu.setVisible(showButtons);
	// saveMenu.setVisible(showButtons);
	// loadMenu.setVisible(showButtons);
	//
	// if(showButtons) {
	// newMenu.setEnabled(activated);
	// saveAsMenu.setEnabled(activated);
	// saveMenu.setEnabled(activated);
	// loadMenu.setEnabled(activated);
	// }
	// }

	// @Override
	// public void setActivated(final boolean activated) {
	// setActivated(activated, true);
	// }

	@Override
	public void onActionExecuted(final Action action) {
		if(action instanceof LoadDrawing) {
//			currentFile = ((LoadDrawing)action).getFile();
//			if(currentFile!=null)
//				currentFolder = currentFile.getParentFile();
		}
	}

	@Override
	protected void initialiseInteractors() {
		// addInteractor(new ButtonClose2SaveInteractor(this));
		// addInteractor(new Shortcut2SavePrefInteractor(this));
		// addInteractor(new Menu2SaveInteractor(this));
		// addInteractor(new Shortcut2SaveInteractor(this));
		// addInteractor(new Menu2SaveAsInteractor(this));
		// addInteractor(new Menu2LoadInteractor(this));
		// addInteractor(new Shortcut2LoadInteractor(this));
		// addInteractor(new Menu2NewInteractor(this));
		// addInteractor(new Shortcut2NewInteractor(this));
		// addInteractor(new RecentMenuItem2LoadInteractor(this));
	}

	/**
	 * @return The path where documents are saved.
	 * @since 3.0
	 */
	public String getPathSave() {
		return pathSave;
	}

	/**
	 * Sets the path where documents are saved.
	 * 
	 * @param pathSave
	 *            The path where documents are saved.
	 * @since 3.0
	 */
	public void setPathSave(final String pathSave) {
		if(pathSave!=null)
			this.pathSave = pathSave;
	}

	/**
	 * @return The current file loaded or saved.
	 * @since 3.0
	 */
	public File getCurrentFile() {
		return currentFile;
	}

	/**
	 * Sets the current file loaded or saved.
	 * 
	 * @param currentFile
	 *            The current file loaded or saved.
	 * @since 3.0
	 */
	public void setCurrentFile(final File currentFile) {
		if(currentFile!=null)
			this.currentFile = currentFile;
	}

	// /**
	// * Updates the recent menu items.
	// * @param recentDocs The list of recent documents.
	// * @since 3.0
	// */
	// public void updateRecentMenuItems(final List<String> recentDocs) {
	// recentFilesMenu.removeAll();
	//
	// if(recentDocs!=null && !recentDocs.isEmpty()) {
	// MMenuItem item;
	//
	// for(final String fileName : recentDocs) {
	// item = new
	// MMenuItem(fileName.substring(fileName.lastIndexOf(LResources.FILE_SEP)+1));
	// item.setToolTipText(fileName);
	// recentFilesMenu.add(item);
	// }
	// }
	//
	// recentFilesMenu.setEnabled(recentFilesMenu.getMenuComponentCount()>0);
	// }

	/**
	 * @param save
	 *            True: the dialogue box will be configured for saving prupose.
	 *            Otherwose, for opening purpose.
	 * @return The dialogue box to open or save drawings.
	 * @since 3.0
	 */
	public JFileChooser getDialog(final boolean save) {
		if(fileChooser==null) {
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//			fileChooser.addChoosableFileFilter(new SVGFilter());
			fileChooser.setMultiSelectionEnabled(false);
		}

		if(save)
			fileChooser.setDialogTitle(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.188"));//$NON-NLS-1$
		else
			fileChooser.setDialogTitle(LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.200")); //$NON-NLS-1$

		return fileChooser;
	}

	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

		if(generalPreferences&&document!=null&&root!=null) {
			final Element elt = document.createElement(LNamespace.XML_PATH_OPEN);
			elt.setTextContent(pathSave);
			root.appendChild(elt);
		}
	}
}

// abstract class Interaction2NewInteractor<I extends Interaction> extends
// InteractorImpl<NewDrawing, I, FileLoaderSaver> {
// protected Interaction2NewInteractor(final FileLoaderSaver ins, final Class<I>
// interaction) throws InstantiationException, IllegalAccessException {
// super(ins, false, NewDrawing.class, interaction);
// }
//
// @Override
// public void initAction() {
// action.setPrefSetter(instrument.prefSetter);
// action.setUi(instrument.ui);
// action.setOpenSaveManager(SVGDocumentGenerator.INSTANCE);
// action.setFileChooser(instrument.getDialog(false));
// action.setCurrentFolder(instrument.currentFolder);
// }
// }
//
//
// class Menu2NewInteractor extends Interaction2NewInteractor<MenuItemPressed> {
// protected Menu2NewInteractor(final FileLoaderSaver fileLoader) throws
// InstantiationException, IllegalAccessException {
// super(fileLoader, MenuItemPressed.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getMenuItem()==instrument.newMenu;
// }
// }
//
//
//
// class Shortcut2NewInteractor extends Interaction2NewInteractor<KeysPressure>
// {
// protected Shortcut2NewInteractor(final FileLoaderSaver ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, KeysPressure.class);
// }
//
//
// @Override
// public boolean isConditionRespected() {
// final List<Integer> keys = getInteraction().getKeys();
// return keys.size()==2 && keys.contains(KeyEvent.VK_N) &&
// keys.contains(LSystem.INSTANCE.getControlKey());
// }
// }
//
//
// /**
// * This link maps a keyboard shortcut to an action that saves the preferences.
// */
// class Shortcut2SavePrefInteractor extends
// Interaction2SaveInteractor<KeysPressure> {
// /**
// * The constructor by default.
// * @param fileLoader The file loader/saver;
// * @since 3.0
// */
// protected Shortcut2SavePrefInteractor(final FileLoaderSaver fileLoader)
// throws InstantiationException, IllegalAccessException {
// super(fileLoader, KeysPressure.class);
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setSaveAs(true);
// action.setFileChooser(instrument.getDialog(true));
// action.setCurrentFolder(instrument.currentFolder);
// action.setSaveOnClose(true);
// }
//
// @Override
// public boolean isConditionRespected() {
// final List<Integer> keys = getInteraction().getKeys();
// return keys.size()==2 && keys.contains(KeyEvent.VK_W) &&
// keys.contains(LSystem.INSTANCE.getControlKey());
// }
// }
//
//
//
// /**
// * This link maps the close button of the main frame to an action that saves
// the drawing.
// */
// class ButtonClose2SaveInteractor extends
// Interaction2SaveInteractor<WindowClosed> {
// /**
// * The constructor by default.
// * @param fileLoader The file loader/saver;
// * @since 3.0
// */
// protected ButtonClose2SaveInteractor(final FileLoaderSaver fileLoader) throws
// InstantiationException, IllegalAccessException {
// super(fileLoader, WindowClosed.class);
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setFileChooser(instrument.getDialog(true));
// action.setCurrentFolder(instrument.currentFolder);
// action.setSaveAs(true);
// action.setSaveOnClose(true);
// }
//
// @Override
// public boolean isConditionRespected() {
// return getInteraction().getFrame()==getInstrument().ui;
// }
// }
//
//
//
// /**
// * This link maps a menu item to a save action.
// */
// class Menu2SaveAsInteractor extends
// Interaction2SaveInteractor<MenuItemPressed> {
// /**
// * The constructor by default.
// * @param fileLoader The file loader/saver;
// * @since 3.0
// */
// protected Menu2SaveAsInteractor(final FileLoaderSaver fileLoader) throws
// InstantiationException, IllegalAccessException {
// super(fileLoader, MenuItemPressed.class);
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setFileChooser(instrument.getDialog(true));
// action.setCurrentFolder(instrument.currentFolder);
// action.setSaveAs(true);
// action.setSaveOnClose(false);
// action.setFile(null);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getMenuItem()==instrument.saveAsMenu;
// }
// }
//
//
// /**
// * This link maps a keyboard shortcut to a save action.
// */
// class Shortcut2SaveInteractor extends
// Interaction2SaveInteractor<KeysPressure> {
// /**
// * Creates the link.
// */
// protected Shortcut2SaveInteractor(final FileLoaderSaver fileLoader) throws
// InstantiationException, IllegalAccessException {
// super(fileLoader, KeysPressure.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// final List<Integer> keys = getInteraction().getKeys();
// return keys.size()==2 && keys.contains(KeyEvent.VK_S) &&
// keys.contains(LSystem.INSTANCE.getControlKey());
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setSaveAs(false);
// action.setSaveOnClose(false);
// }
// }
//
//
// /**
// * This link maps a menu item to a save action.
// */
// class Menu2SaveInteractor extends Interaction2SaveInteractor<MenuItemPressed>
// {
// /**
// * The constructor by default.
// * @param fileLoader The file loader/saver;
// * @since 3.0
// */
// protected Menu2SaveInteractor(final FileLoaderSaver fileLoader) throws
// InstantiationException, IllegalAccessException {
// super(fileLoader, MenuItemPressed.class);
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setSaveOnClose(false);
// action.setSaveAs(false);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getMenuItem()==instrument.saveMenu;
// }
// }
//
//
// abstract class Interaction2IOInteractor<A extends IOAction<LFrame, JLabel>, I
// extends Interaction> extends InteractorImpl<A, I, FileLoaderSaver> {
// protected Interaction2IOInteractor(final FileLoaderSaver fileLoader, final
// Class<A> action, final Class<I> interaction) throws InstantiationException,
// IllegalAccessException {
// super(fileLoader, false, action, interaction);
// }
//
// @Override
// public void initAction() {
// action.setStatusWidget(instrument.statusBar);
// action.setProgressBar(instrument.progressBar);
// action.setUi(instrument.ui);
// action.setOpenSaveManager(SVGDocumentGenerator.INSTANCE);
// }
// }
//
//
// /**
// * This abstract link maps an interaction to a save action.
// */
// abstract class Interaction2SaveInteractor<I extends Interaction> extends
// Interaction2IOInteractor<SaveDrawing, I> {
// protected Interaction2SaveInteractor(final FileLoaderSaver fileLoader, final
// Class<I> interaction) throws InstantiationException, IllegalAccessException {
// super(fileLoader, SaveDrawing.class, interaction);
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setPrefSetter(instrument.prefSetter);
// action.setFileChooser(instrument.getDialog(true));
// action.setFile(instrument.currentFile);
// action.setCurrentFolder(instrument.currentFolder);
// }
// }
//
//
// /**
// * This abstract link maps an interaction to a load action.
// */
// abstract class Interaction2LoadInteractor<I extends Interaction> extends
// Interaction2IOInteractor<LoadDrawing, I> {
// protected Interaction2LoadInteractor(final FileLoaderSaver fileLoader, final
// Class<I> interaction) throws InstantiationException, IllegalAccessException {
// super(fileLoader, LoadDrawing.class, interaction);
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setFileChooser(instrument.getDialog(false));
// action.setCurrentFolder(instrument.currentFolder);
// }
// }
//
//
//
// /** The link maps recent menu items to an action that loads documents. */
// class RecentMenuItem2LoadInteractor extends
// Interaction2LoadInteractor<MenuItemPressed> {
// protected RecentMenuItem2LoadInteractor(final FileLoaderSaver fileLoader)
// throws InstantiationException, IllegalAccessException {
// super(fileLoader, MenuItemPressed.class);
// }
//
// @Override
// public void initAction() {
// super.initAction();
// action.setFile(new File(interaction.getMenuItem().getToolTipText()));
// }
//
//
// @Override
// public boolean isConditionRespected() {
// return instrument.recentFilesMenu.contains(interaction.getMenuItem());
// }
// }
//
//
//
// /**
// * This link maps a keyboard shortcut to a load action.
// */
// class Shortcut2LoadInteractor extends
// Interaction2LoadInteractor<KeysPressure> {
// /**
// * Creates the link.
// */
// protected Shortcut2LoadInteractor(final FileLoaderSaver fileLoader) throws
// InstantiationException, IllegalAccessException {
// super(fileLoader, KeysPressure.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// final List<Integer> keys = getInteraction().getKeys();
// return keys.size()==2 && keys.contains(KeyEvent.VK_O) &&
// keys.contains(KeyEvent.VK_CONTROL);
// }
// }
//
//
// /**
// * This link maps a menu item to a load action.
// */
// class Menu2LoadInteractor extends Interaction2LoadInteractor<MenuItemPressed>
// {
// /**
// * The constructor by default.
// * @param fileLoader The file loader/saver;
// * @since 3.0
// */
// protected Menu2LoadInteractor(final FileLoaderSaver fileLoader) throws
// InstantiationException, IllegalAccessException {
// super(fileLoader, MenuItemPressed.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getMenuItem()==instrument.loadMenu;
// }
// }
