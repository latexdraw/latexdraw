package net.sf.latexdraw.instruments;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import net.sf.latexdraw.actions.LoadDrawing;
import net.sf.latexdraw.actions.NewDrawing;
import net.sf.latexdraw.actions.SaveDrawing;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.filters.SVGFilter;
import net.sf.latexdraw.generators.svg.SVGDocumentGenerator;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LResources;

import org.malai.action.Action;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.Interaction;
import org.malai.interaction.library.ButtonPressed;
import org.malai.interaction.library.KeysPressure;
import org.malai.interaction.library.MenuItemPressed;
import org.malai.interaction.library.WindowClosed;
import org.malai.ui.UI;
import org.malai.widget.MButton;
import org.malai.widget.MMenuItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This instrument saves and loads documents.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 06/01/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class FileLoaderSaver extends Instrument {
	/** The label of the saveMenu item */
	public final static String LABEL_SAVE = "Save drawing";

	/** The label of the saveAsMenu item */
	public final static String LABEL_SAVE_AS = "Save drawing as";

	/** The label of the openMenu item */
	public final static String LABEL_OPEN = "Open drawing";

	/** The path where documents are saved. */
	protected String pathSave;

	/** The current file loaded or saved. */
	protected File currentFile;

	/** The button used to save documents. */
	protected MButton saveButton;

	/** The button used to load documents. */
	protected MButton loadButton;

	/** The button used to create a new document. */
	protected MButton newButton;

	/** The menu item used to save as a document. */
	protected MMenuItem saveAsMenu;

	/** The menu used to save documents. */
	protected MMenuItem saveMenu;

	/** The menu used to load documents. */
	protected MMenuItem loadMenu;

	/** The menu used to create a new document. */
	protected MMenuItem newMenu;

    /** The fileChooser used to save drawings. */
    protected JFileChooser fileChooser;

    /** The UI to save/open. */
    protected UI ui;

	/** The field where messages are displayed. */
	protected JTextField statusBar;

	/** The instrument used to manage preferences. */
	protected PreferencesSetter prefSetter;


	/**
	 * Creates the file loader/saver.
	 * @param ui The user interface that contains the presentations and the instruments to save/load.
	 * @param statusBar The status bar where messages are displayed.
	 * @param prefSetter The instrument used to manage preferences.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 3.0
	 */
	public FileLoaderSaver(final UI ui, final JTextField statusBar, final PreferencesSetter prefSetter) {
		super();

		if(ui==null || statusBar==null || prefSetter==null)
			throw new IllegalArgumentException();

		this.statusBar	= statusBar;
		this.ui			= ui;
		this.prefSetter = prefSetter;

		newMenu	= new MMenuItem(LResources.LABEL_NEW, KeyEvent.VK_N);
		newMenu.setIcon(LResources.NEW_ICON);

		newButton = new MButton(LResources.NEW_ICON);
		newButton.setMargin(LResources.INSET_BUTTON);// FIXME: remove LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.108")
		newButton.setToolTipText("Creation of a new drawing.");

		loadButton = new MButton(LResources.OPEN_ICON);
		loadButton.setMargin(LResources.INSET_BUTTON);
		loadButton.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.109")); //$NON-NLS-1$

		saveButton = new MButton(LResources.SAVE_ICON);
		saveButton.setMargin(LResources.INSET_BUTTON);
		saveButton.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.110")); //$NON-NLS-1$

		loadMenu = new MMenuItem(LABEL_OPEN, KeyEvent.VK_O);
		loadMenu.setIcon(LResources.OPEN_ICON);

        saveMenu = new MMenuItem(LABEL_SAVE, KeyEvent.VK_S);
        saveMenu.setIcon(LResources.SAVE_ICON);

        saveAsMenu = new MMenuItem(LABEL_SAVE_AS, KeyEvent.VK_A);
        saveAsMenu.setIcon(LResources.SAVE_AS_ICON);

        reinit();
		initialiseLinks();
	}


	@Override
	public void reinit() {
		if(fileChooser!=null)
			fileChooser.setSelectedFile(null);

		currentFile	= null;
		pathSave 	= "/home/arno/Bureau"; //FIXME when preferences will be managed.
	}


	@Override
	public void interimFeedback() {
		super.interimFeedback();
		File file = fileChooser==null ? null : fileChooser.getSelectedFile();
		if(file!=null)
			currentFile = file;
	}



	@Override
	protected void initialiseLinks() {
		try{
			links.add(new ButtonClose2SaveLink(this));
			links.add(new ShortCut2SaveLink(this));
			links.add(new Button2SaveLink(this));
			links.add(new Menu2SaveLink(this));
			links.add(new Shortcut2SaveLink(this));
			links.add(new Menu2SaveAsLink(this));
			links.add(new Menu2LoadLink(this));
			links.add(new Button2LoadLink(this));
			links.add(new Shortcut2LoadLink(this));
			links.add(new Menu2NewLink(this));
			links.add(new Button2NewLink(this));
			links.add(new ShortCut2NewLink(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
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
	 * @param pathSave The path where documents are saved.
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
	 * @param currentFile The current file loaded or saved.
	 * @since 3.0
	 */
	public void setCurrentFile(final File currentFile) {
		if(currentFile!=null)
			this.currentFile = currentFile;
	}


	/**
	 * @return The button used to save documents.
	 * @since 3.0
	 */
	public MButton getSaveButton() {
		return saveButton;
	}


	/**
	 * @return The button used to load documents.
	 * @since 3.0
	 */
	public MButton getLoadButton() {
		return loadButton;
	}


	/**
	 * @param save True: the dialogue box will be configured for saving prupose. Otherwose, for opening purpose.
	 * @return The dialogue box to open or save drawings.
	 * @since 3.0
	 */
	public JFileChooser getDialog(final boolean save) {
		if(fileChooser==null) {
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.addChoosableFileFilter(new SVGFilter());
			fileChooser.setMultiSelectionEnabled(false);
		}

		if(save)
			fileChooser.setDialogTitle(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.188"));//$NON-NLS-1$
		else
			fileChooser.setDialogTitle(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.200")); //$NON-NLS-1$

		return fileChooser;
	}


	@Override
	public void onActionExecuted(final Action action) {
		statusBar.setText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.191")); //$NON-NLS-1$
	}



	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

		if(generalPreferences && document!=null && root!=null) {
			Element elt = document.createElement(LNamespace.XML_PATH_OPEN);
            elt.setTextContent(pathSave);
            root.appendChild(elt);
		}
	}


	/**
	 * @return The menu item used to save as a document.
	 * @since 3.0
	 */
	public MMenuItem getSaveAsMenu() {
		return saveAsMenu;
	}


	/**
	 * @return The menu item used to save a document.
	 * @since 3.0
	 */
	public MMenuItem getSaveMenu() {
		return saveMenu;
	}


	/**
	 * @return The menu item used to load a document.
	 * @since 3.0
	 */
	public MMenuItem getLoadMenu() {
		return loadMenu;
	}

	/**
	 * @return The button used to create a new document.
	 */
	public MButton getNewButton() {
		return newButton;
	}

	/**
	 * @return The menu used to create a new document.
	 */
	public MMenuItem getNewMenu() {
		return newMenu;
	}
}


abstract class Interaction2NewLink<I extends Interaction> extends Link<NewDrawing, I, FileLoaderSaver> {
	protected Interaction2NewLink(final FileLoaderSaver ins, final Class<I> interaction) throws InstantiationException, IllegalAccessException {
		super(ins, false, NewDrawing.class, interaction);
	}

	@Override
	public void initAction() {
		action.setPrefSetter(instrument.prefSetter);
		action.setUi(instrument.ui);
		action.setOpenSaveManager(SVGDocumentGenerator.SVG_GENERATOR);
		action.setFileChooser(instrument.getDialog(false));
	}
}



class Button2NewLink extends Interaction2NewLink<ButtonPressed> {
	protected Button2NewLink(final FileLoaderSaver fileLoader) throws InstantiationException, IllegalAccessException {
		super(fileLoader, ButtonPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		return getInteraction().getButton()==instrument.newButton;
	}
}



class Menu2NewLink extends Interaction2NewLink<MenuItemPressed> {
	protected Menu2NewLink(final FileLoaderSaver fileLoader) throws InstantiationException, IllegalAccessException {
		super(fileLoader, MenuItemPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getMenuItem()==instrument.newMenu;
	}
}



class ShortCut2NewLink extends Interaction2NewLink<KeysPressure> {
	protected ShortCut2NewLink(final FileLoaderSaver ins) throws InstantiationException, IllegalAccessException {
		super(ins, KeysPressure.class);
	}


	@Override
	public boolean isConditionRespected() {
		final List<Integer> keys = getInteraction().getKeys();
		return keys.size()==2 && keys.contains(KeyEvent.VK_N) && keys.contains(KeyEvent.VK_CONTROL);
	}
}


/**
 * This link maps a keyboard shortcut to an action that saves the preferences.
 */
class ShortCut2SaveLink extends Interaction2SaveLink<KeysPressure> {
	/**
	 * The constructor by default.
	 * @param fileLoader The file loader/saver;
	 * @since 3.0
	 */
	public ShortCut2SaveLink(final FileLoaderSaver fileLoader) throws InstantiationException, IllegalAccessException {
		super(fileLoader, KeysPressure.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setSaveAs(true);
		action.setFileChooser(instrument.getDialog(true));
		action.setSaveOnClose(true);
	}

	@Override
	public boolean isConditionRespected() {
		final List<Integer> keys = getInteraction().getKeys();
		return keys.size()==2 && keys.contains(KeyEvent.VK_W) && keys.contains(KeyEvent.VK_CONTROL);
	}
}



/**
 * This link maps the close button of the main frame to an action that saves the drawing.
 */
class ButtonClose2SaveLink extends Interaction2SaveLink<WindowClosed> {
	/**
	 * The constructor by default.
	 * @param fileLoader The file loader/saver;
	 * @since 3.0
	 */
	public ButtonClose2SaveLink(final FileLoaderSaver fileLoader) throws InstantiationException, IllegalAccessException {
		super(fileLoader, WindowClosed.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setFileChooser(instrument.getDialog(true));
		action.setSaveAs(true);
		action.setSaveOnClose(true);
	}

	@Override
	public boolean isConditionRespected() {
		return getInteraction().getFrame()==getInstrument().ui;
	}
}



/**
 * This link maps a menu item to a save action.
 */
class Menu2SaveAsLink extends Interaction2SaveLink<MenuItemPressed> {
	/**
	 * The constructor by default.
	 * @param fileLoader The file loader/saver;
	 * @since 3.0
	 */
	public Menu2SaveAsLink(final FileLoaderSaver fileLoader) throws InstantiationException, IllegalAccessException {
		super(fileLoader, MenuItemPressed.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setFileChooser(instrument.getDialog(true));
		action.setSaveAs(true);
		action.setSaveOnClose(false);
		action.setFile(null);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getMenuItem()==instrument.saveAsMenu;
	}
}


/**
 * This abstract link maps an interaction to a save action.
 */
abstract class Interaction2SaveLink<I extends Interaction> extends Link<SaveDrawing, I, FileLoaderSaver> {
	/**
	 * Creates the link.
	 */
	public Interaction2SaveLink(final FileLoaderSaver fileLoader, final Class<I> interaction) throws InstantiationException, IllegalAccessException {
		super(fileLoader, false, SaveDrawing.class, interaction);
	}

	@Override
	public void initAction() {
		action.setFile(instrument.currentFile);
		action.setUi(instrument.ui);
		action.setOpenSaveManager(SVGDocumentGenerator.SVG_GENERATOR);
		action.setFileChooser(instrument.getDialog(true));
	}
}


/**
 * This link maps a keyboard shortcut to a save action.
 */
class Shortcut2SaveLink extends Interaction2SaveLink<KeysPressure> {
	/**
	 * Creates the link.
	 */
	public Shortcut2SaveLink(final FileLoaderSaver fileLoader) throws InstantiationException, IllegalAccessException {
		super(fileLoader, KeysPressure.class);
	}

	@Override
	public boolean isConditionRespected() {
		final List<Integer> keys = getInteraction().getKeys();
		return keys.size()==2 && keys.contains(KeyEvent.VK_S) && keys.contains(KeyEvent.VK_CONTROL);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setSaveAs(false);
		action.setSaveOnClose(false);
	}
}


/**
 * This link maps a menu item to a save action.
 */
class Menu2SaveLink extends Interaction2SaveLink<MenuItemPressed> {
	/**
	 * The constructor by default.
	 * @param fileLoader The file loader/saver;
	 * @since 3.0
	 */
	public Menu2SaveLink(final FileLoaderSaver fileLoader) throws InstantiationException, IllegalAccessException {
		super(fileLoader, MenuItemPressed.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setSaveOnClose(false);
		action.setSaveAs(false);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getMenuItem()==instrument.saveMenu;
	}
}



/**
 * This link maps a button to a save action.
 */
class Button2SaveLink extends Interaction2SaveLink<ButtonPressed> {
	/**
	 * The constructor by default.
	 * @param fileLoader The file loader/saver;
	 * @since 3.0
	 */
	public Button2SaveLink(final FileLoaderSaver fileLoader) throws InstantiationException, IllegalAccessException {
		super(fileLoader, ButtonPressed.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setSaveOnClose(false);
		action.setSaveAs(false);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.saveButton;
	}
}


/**
 * This abstract link maps an interaction to a load action.
 */
abstract class Interaction2LoadLink<I extends Interaction> extends Link<LoadDrawing, I, FileLoaderSaver> {
	/**
	 * Creates the link.
	 */
	public Interaction2LoadLink(final FileLoaderSaver fileLoader, final Class<I> interaction) throws InstantiationException, IllegalAccessException {
		super(fileLoader, false, LoadDrawing.class, interaction);
	}

	@Override
	public void initAction() {
		action.setFileChooser(instrument.getDialog(false));
		action.setUi(instrument.ui);
		action.setOpenSaveManager(SVGDocumentGenerator.SVG_GENERATOR);
	}
}


/**
 * This link maps a keyboard shortcut to a load action.
 */
class Shortcut2LoadLink extends Interaction2LoadLink<KeysPressure> {
	/**
	 * Creates the link.
	 */
	public Shortcut2LoadLink(final FileLoaderSaver fileLoader) throws InstantiationException, IllegalAccessException {
		super(fileLoader, KeysPressure.class);
	}

	@Override
	public boolean isConditionRespected() {
		final List<Integer> keys = getInteraction().getKeys();
		return keys.size()==2 && keys.contains(KeyEvent.VK_O) && keys.contains(KeyEvent.VK_CONTROL);
	}
}


/**
 * This link maps a button to a load action.
 */
class Button2LoadLink extends Interaction2LoadLink<ButtonPressed> {
	/**
	 * The constructor by default.
	 * @param fileLoader The file loader/saver;
	 * @since 3.0
	 */
	public Button2LoadLink(final FileLoaderSaver fileLoader) throws InstantiationException, IllegalAccessException {
		super(fileLoader, ButtonPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.loadButton;
	}
}



/**
 * This link maps a menu item to a load action.
 */
class Menu2LoadLink extends Interaction2LoadLink<MenuItemPressed> {
	/**
	 * The constructor by default.
	 * @param fileLoader The file loader/saver;
	 * @since 3.0
	 */
	public Menu2LoadLink(final FileLoaderSaver fileLoader) throws InstantiationException, IllegalAccessException {
		super(fileLoader, MenuItemPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getMenuItem()==instrument.loadMenu;
	}
}
