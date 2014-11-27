package net.sf.latexdraw.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.lang.LangTool;

/**
 * Contains all the icons and the labels used by latexdraw.<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 09/21/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 2.0.0
 */
public final class LResources {
	private LResources() {
		super();
	}

	/** The line separator of the current system. */
	public static final String EOL = System.getProperty("line.separator"); //$NON-NLS-1$

	/** The file separator of the current system. */
	public static final String FILE_SEP = System.getProperty("file.separator"); //$NON-NLS-1$

	/** The name of the application */
    public static final String LABEL_APP = "LaTeXDraw";//$NON-NLS-1$

	/** The label of the newMenu item */
    public static final String LABEL_NEW = LangTool.INSTANCE.getStringDialogFrame("Res.2"); //$NON-NLS-1$

	public static final String LABEL_NEW_WITH_SEL =LangTool.INSTANCE.getStringLaTeXDrawFrame("LRes.0"); //$NON-NLS-1$

	/** The label of the menu copy */
	public static final String LABEL_COPY = LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.40"); //$NON-NLS-1$

	/** The label of the menu paste */
	public static final String LABEL_PASTE = LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.43"); //$NON-NLS-1$

	/** The label of the menu cut */
	public static final String LABEL_CUT = LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.44"); //$NON-NLS-1$

	public static final String LABEL_UPDATE_TO_GRID = LangTool.INSTANCE.getString19("DrawPanel.0"); //$NON-NLS-1$

	public static final ImageIcon DIM_POS_ICON;
	public static final ImageIcon NEW_ICON;
	public static final ImageIcon OPEN_ICON;
	public static final ImageIcon SAVE_ICON ;
	public static final ImageIcon SAVE_AS_ICON;
	public static final ImageIcon DEL_ICON;
	public static final ImageIcon EMPTY_ICON;
	public static final ImageIcon PROPERTIES_ICON;
	public static final ImageIcon UPDATE_ICON;
	public static final ImageIcon ABOUT_ICON;
	public static final ImageIcon HELP_ICON;
	public static final ImageIcon DRAWING_PROP_ICON;
	public static final ImageIcon ZOOM_DEFAULT_ICON;
	public static final ImageIcon UNDO_ICON;
	public static final ImageIcon REDO_ICON;
	public static final ImageIcon BACKGROUND_ICON;
	public static final ImageIcon FOREGROUND_ICON;
	public static final ImageIcon COPY_ICON;
	public static final ImageIcon PASTE_ICON;
	public static final ImageIcon CUT_ICON;
	public static final ImageIcon COMMENT_ICON;
	public static final ImageIcon STOP_ICON;
	public static final ImageIcon INSERT_PST_ICON;
	public static final ImageIcon BUTTON_CLOSE_IN_ICON;
	public static final ImageIcon BUTTON_CLOSE_OUT_ICON;
	public static final ImageIcon RELOAD_ICON;


	static {
		RELOAD_ICON			= loadImageIcon("/res/view-refresh.png");//$NON-NLS-1$
		DIM_POS_ICON		= loadImageIcon("/res/dimPos.png");//$NON-NLS-1$
		COPY_ICON  		= loadImageIcon("/res/Copy.png");//$NON-NLS-1$
		PASTE_ICON 		= loadImageIcon("/res/Paste.png");//$NON-NLS-1$
		CUT_ICON   		= loadImageIcon("/res/Cut.png");//$NON-NLS-1$
		BACKGROUND_ICON 	= loadImageIcon("/res/background.png");	//$NON-NLS-1$
		FOREGROUND_ICON 	= loadImageIcon("/res/foreground.png");//$NON-NLS-1$
		UNDO_ICON 		= loadImageIcon("/res/Undo.png");//$NON-NLS-1$
		REDO_ICON 		= loadImageIcon("/res/Redo.png");	//$NON-NLS-1$
		ZOOM_DEFAULT_ICON = loadImageIcon("/res/Magnify.png");	//$NON-NLS-1$
		NEW_ICON 		= loadImageIcon("/res/New.png");	//$NON-NLS-1$
		OPEN_ICON 		= loadImageIcon("/res/document-open.png");	//$NON-NLS-1$
		SAVE_ICON 		= loadImageIcon("/res/document-save.png");	//$NON-NLS-1$
		SAVE_AS_ICON 		= loadImageIcon("/res/document-save-as.png");//$NON-NLS-1$
		DEL_ICON 		= loadImageIcon("/res/del.png");		//$NON-NLS-1$
		EMPTY_ICON 		= loadImageIcon("/res/empty.png");	//$NON-NLS-1$
		PROPERTIES_ICON 	= loadImageIcon("/res/preferences-desktop-theme.png");	//$NON-NLS-1$
		HELP_ICON 		= loadImageIcon("/res/help-browser.png");	//$NON-NLS-1$
		ABOUT_ICON 		= loadImageIcon("/res/emblem-important.png");	//$NON-NLS-1$
		COMMENT_ICON 	= loadImageIcon("/res/comment.png"); //$NON-NLS-1$
		STOP_ICON 		= loadImageIcon("/res/stop.png"); //$NON-NLS-1$
		INSERT_PST_ICON = loadImageIcon("/res/text-x-generic.png"); //$NON-NLS-1$
		UPDATE_ICON			= loadImageIcon("/res/system-software-update.png"); //$NON-NLS-1$
		DRAWING_PROP_ICON		= loadImageIcon("/res/document-properties.png"); //$NON-NLS-1$
		BUTTON_CLOSE_OUT_ICON = loadImageIcon("/res/closeOut.png"); //$NON-NLS-1$
		BUTTON_CLOSE_IN_ICON = loadImageIcon("/res/closeIn.png"); //$NON-NLS-1$
	}



	/**
	 * Allows to load a image icon from a path.
	 * @param path The path.
	 * @return The image icon or null.
	 */
	public static ImageIcon loadImageIcon(final String path) {
		try {
			final URL url = Class.class.getResource(path);

			if(url==null)
				throw new MalformedURLException(path);

			return new ImageIcon(url);
		}
		catch(final Exception e) {
			e.printStackTrace();
			BadaboomCollector.INSTANCE.add(e);
			return null;
		}
	}
}
