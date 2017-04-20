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
package net.sf.latexdraw.util;

/**
 * Contains all the icons and the labels used by latexdraw.
 * @author Arnaud BLOUIN
 */
public final class LResources {
	/** The line separator of the current system. */
	public static final String EOL = System.getProperty("line.separator"); //$NON-NLS-1$
	/** The file separator of the current system. */
	public static final String FILE_SEP = System.getProperty("file.separator"); //$NON-NLS-1$
	/** The label of the newMenu item */
	public static final String LABEL_NEW = LangTool.INSTANCE.getBundle().getString("Res.2"); //$NON-NLS-1$
//	public static final String LABEL_UPDATE_TO_GRID = LangTool.INSTANCE.getBundle().getString("DrawPanel.0"); //$NON-NLS-1$
//	public static final ImageIcon DIM_POS_ICON = loadImageIcon("/res/dimPos.png");
//	public static final ImageIcon NEW_ICON = loadImageIcon("/res/New.png");
//	public static final ImageIcon OPEN_ICON = loadImageIcon("/res/document-open.png");
//	public static final ImageIcon SAVE_ICON = loadImageIcon("/res/document-save.png");
//	public static final ImageIcon SAVE_AS_ICON = loadImageIcon("/res/document-save-as.png");
//	public static final ImageIcon DEL_ICON = loadImageIcon("/res/del.png");
//	public static final ImageIcon EMPTY_ICON = loadImageIcon("/res/empty.png");
//	public static final ImageIcon PROPERTIES_ICON = loadImageIcon("/res/preferences-desktop-theme.png");
//	public static final ImageIcon ABOUT_ICON = loadImageIcon("/res/emblem-important.png");
//	public static final ImageIcon HELP_ICON = loadImageIcon("/res/help-browser.png");
//	public static final ImageIcon DRAWING_PROP_ICON = loadImageIcon("/res/document-properties.png");
//	public static final ImageIcon ZOOM_DEFAULT_ICON = loadImageIcon("/res/Magnify.png");
//	public static final ImageIcon UNDO_ICON = loadImageIcon("/res/Undo.png");
//	public static final ImageIcon REDO_ICON = loadImageIcon("/res/Redo.png");
//	public static final ImageIcon COMMENT_ICON = loadImageIcon("/res/comment.png");
//	public static final ImageIcon STOP_ICON = loadImageIcon("/res/stop.png");
//	public static final ImageIcon INSERT_PST_ICON = loadImageIcon("/res/text-x-generic.png");
//	public static final ImageIcon BUTTON_CLOSE_IN_ICON = loadImageIcon("/res/closeIn.png");
//	public static final ImageIcon BUTTON_CLOSE_OUT_ICON = loadImageIcon("/res/closeOut.png");
	public static final String LATEXDRAW_ICON_PATH = "/res/LaTeXDrawIcon.png";

	private LResources() {
		super();
	}
}
