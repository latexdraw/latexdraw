package net.sf.latexdraw.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.sf.latexdraw.instruments.EditionChoice;
import net.sf.latexdraw.instruments.FileLoaderSaver;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

/**
 * This class defines a frame containing the shortcuts of the program.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE.  See the GNU General Public License for more details.<br>
 *<br>
 * 09/18/06<br>
 * @author Arnaud BLOUIN
 * @version 2.0.0<br>
 * @since 1.9<br>
 */
public class ShortcutsFrame extends JFrame {
	private static final long serialVersionUID = 1L;


	/**
	 * The constructor creates a frame containing a table with all the shortcuts.
	 */
	public ShortcutsFrame() {
		super(LangTool.LANG.getString19("LaTeXDrawFrame.3")); //$NON-NLS-1$

		setIconImage(LResources.LATEXDRAW_ICON.getImage());

		JTable table;
		final String[] titles = {LangTool.LANG.getString19("ShortcutsFrame.1"),  //$NON-NLS-1$
				LangTool.LANG.getString16("ParserMessagesFrame.0"),  //$NON-NLS-1$
				LangTool.LANG.getString19("ShortcutsFrame.3")};  //$NON-NLS-1$
		String catEdit	= LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.89"); //$NON-NLS-1$
		String catNav	= LangTool.LANG.getString19("ShortcutsFrame.4"); //$NON-NLS-1$
		String catTran	= LangTool.LANG.getString19("ShortcutsFrame.5"); //$NON-NLS-1$
		String catDraw	= LangTool.LANG.getString19("ShortcutsFrame.6"); //$NON-NLS-1$
		String catFile	= LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.88"); //$NON-NLS-1$
		String cathelp	= LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.93"); //$NON-NLS-1$
		String move = LangTool.LANG.getString19("ShortcutsFrame.7"); //$NON-NLS-1$
		JButton okB 	= new JButton(LangTool.LANG.getString18("LaTeXDrawFrame.23")); //$NON-NLS-1$
		JPanel panel 	= new JPanel();
		Dimension dim 	= Toolkit.getDefaultToolkit().getScreenSize();

		String ctrl = KeyEvent.getKeyModifiersText(InputEvent.CTRL_MASK);
		String shift = KeyEvent.getKeyModifiersText(InputEvent.SHIFT_MASK);
		String leftClick = LangTool.LANG.getString19("ShortcutsFrame.8"); //$NON-NLS-1$
		final String st1 = LangTool.LANG.getString19("ShortcutsFrame.9"); //$NON-NLS-1$
		final String st2 = LangTool.LANG.getString19("ShortcutsFrame.10"); //$NON-NLS-1$
		final String st3 = LangTool.LANG.getString19("ShortcutsFrame.11"); //$NON-NLS-1$
		final String st4 = LangTool.LANG.getString19("ShortcutsFrame.12"); //$NON-NLS-1$
		final Object[][] data = {
			{ctrl+"+C", LResources.LABEL_COPY, catEdit},//$NON-NLS-1$
			{ctrl+"+V", LResources.LABEL_PASTE, catEdit},//$NON-NLS-1$
			{ctrl+"+X", LResources.LABEL_CUT, catEdit},//$NON-NLS-1$
			{ctrl+"+Z", LResources.LABEL_UNDO, catEdit},//$NON-NLS-1$
			{ctrl+"+Y", LResources.LABEL_REDO, catEdit},//$NON-NLS-1$
			{ctrl+"+N", LResources.LABEL_NEW, catFile},//$NON-NLS-1$
			{ctrl+"+O", FileLoaderSaver.LABEL_OPEN, catFile},//$NON-NLS-1$
			{ctrl+"+S", FileLoaderSaver.LABEL_SAVE, catFile},//$NON-NLS-1$
			{ctrl+"+E", LResources.LABEL_PRINT_CODE, catFile},//$NON-NLS-1$
			{ctrl+"+D", LResources.LABEL_PRINT_DRAW, catFile},//$NON-NLS-1$
			{ctrl+"+W", LResources.LABEL_QUIT, catFile},//$NON-NLS-1$
			{KeyEvent.getKeyText(KeyEvent.VK_F9), LResources.LABEL_ZOOM_DEFAULT, catNav},
			{KeyEvent.getKeyText(KeyEvent.VK_ADD), LResources.LABEL_ZOOM_IN, catNav},
			{KeyEvent.getKeyText(KeyEvent.VK_SUBTRACT), LResources.LABEL_ZOOM_OUT, catNav},
			{'J', EditionChoice.LINES.getLabel(), catDraw},
			{'P', EditionChoice.POLYGON.getLabel(), catDraw},
			{'D', EditionChoice.DOT.getLabel(), catDraw},
			{'R', EditionChoice.RECT.getLabel(), catDraw},
			{'S', EditionChoice.SQUARE.getLabel(), catDraw},
			{'H', EditionChoice.RHOMBUS.getLabel(), catDraw},
			{'T', EditionChoice.TRIANGLE.getLabel(), catDraw},
			{'E', EditionChoice.ELLIPSE.getLabel(), catDraw},
			{'C', EditionChoice.CIRCLE.getLabel(), catDraw},
			{'A', EditionChoice.CIRCLE_ARC.getLabel(),	catDraw},
			{'W', EditionChoice.WEDGE.getLabel(), catDraw},
			{'O', EditionChoice.CHORD.getLabel(), catDraw},
			{'B', EditionChoice.BEZIER_CURVE.getLabel(), catDraw},
			{'G', EditionChoice.GRID.getLabel(), catDraw},
			{'Z', EditionChoice.AXES.getLabel(), catDraw},
			{'K', EditionChoice.FREE_HAND.getLabel(), catDraw},
			{'X', EditionChoice.TEXT.getLabel(), catDraw},
//			{'F', EditionChoice.SELECTION.getLabel(), catDraw},//FIXME
			{KeyEvent.getKeyText(KeyEvent.VK_DELETE), LResources.LABEL_DELETE, catDraw},
			{KeyEvent.getKeyText(KeyEvent.VK_F1), LResources.LABEL_HELP, cathelp},
			{KeyEvent.getKeyText(KeyEvent.VK_F2), LangTool.LANG.getString19("LaTeXDrawFrame.3"), cathelp}, //$NON-NLS-1$
			{KeyEvent.getKeyText(KeyEvent.VK_RIGHT), st1, catNav},
			{KeyEvent.getKeyText(KeyEvent.VK_LEFT), st2, catNav},
			{KeyEvent.getKeyText(KeyEvent.VK_UP), st3, catNav},
			{KeyEvent.getKeyText(KeyEvent.VK_DOWN), st4, catNav},
			{ctrl+'+'+KeyEvent.getKeyText(KeyEvent.VK_RIGHT), st1+move, catNav},
			{ctrl+'+'+KeyEvent.getKeyText(KeyEvent.VK_LEFT), st2+move, catNav},
			{ctrl+'+'+KeyEvent.getKeyText(KeyEvent.VK_UP), st3+move, catNav},
			{ctrl+'+'+KeyEvent.getKeyText(KeyEvent.VK_DOWN), st4+move, catNav},
			{shift+'+'+ctrl+'+'+KeyEvent.getKeyText(KeyEvent.VK_RIGHT), LangTool.LANG.getString19("ShortcutsFrame.14"), catNav}, //$NON-NLS-1$
			{shift+'+'+ctrl+'+'+KeyEvent.getKeyText(KeyEvent.VK_LEFT), LangTool.LANG.getString19("ShortcutsFrame.15"), catNav}, //$NON-NLS-1$
			{shift+'+'+ctrl+'+'+KeyEvent.getKeyText(KeyEvent.VK_UP), LangTool.LANG.getString19("ShortcutsFrame.16"), catNav}, //$NON-NLS-1$
			{shift+'+'+ctrl+'+'+KeyEvent.getKeyText(KeyEvent.VK_DOWN), LangTool.LANG.getString19("ShortcutsFrame.17"), catNav}, //$NON-NLS-1$
			{shift+'+'+KeyEvent.getKeyText(KeyEvent.VK_RIGHT), LangTool.LANG.getString19("ShortcutsFrame.18"), catNav}, //$NON-NLS-1$
			{shift+'+'+KeyEvent.getKeyText(KeyEvent.VK_LEFT), LangTool.LANG.getString19("ShortcutsFrame.19"), catNav}, //$NON-NLS-1$
			{shift+'+'+KeyEvent.getKeyText(KeyEvent.VK_UP), LangTool.LANG.getString19("ShortcutsFrame.20"), catNav}, //$NON-NLS-1$
			{shift+'+'+KeyEvent.getKeyText(KeyEvent.VK_DOWN), LangTool.LANG.getString19("ShortcutsFrame.21"), catNav}, //$NON-NLS-1$
			{ctrl+"+U", LangTool.LANG.getString19("ShortcutsFrame.23"), catTran}, //$NON-NLS-1$ //$NON-NLS-2$
			{ctrl+"+A", LangTool.LANG.getString19("ShortcutsFrame.25"), catDraw}, //$NON-NLS-1$ //$NON-NLS-2$
			{ctrl+'+'+leftClick, LangTool.LANG.getString19("ShortcutsFrame.26"), catDraw}, //$NON-NLS-1$
			{shift+'+'+leftClick, LangTool.LANG.getString19("ShortcutsFrame.27"), catDraw}, //$NON-NLS-1$
			{KeyEvent.getKeyText(KeyEvent.VK_SPACE),
				LangTool.LANG.getString19("ShortcutsFrame.28"), catDraw}, //$NON-NLS-1$
			{ctrl+'+'+LangTool.LANG.getString19("ShortcutsFrame.29"), LangTool.LANG.getString19("ShortcutsFrame.30"), catDraw} //$NON-NLS-1$ //$NON-NLS-2$
		};

        TableModel dataModel = new AbstractTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public int getColumnCount() { return titles.length; }
            @Override
			public int getRowCount() { return data.length;}
            @Override
			public Object getValueAt(final int row, final int col) {return data[row][col];}
            @Override
			public String getColumnName(final int column) {return titles[column];}
            @Override
			public Class<? extends Object> getColumnClass(final int c) {return getValueAt(0, c).getClass();}
		    @Override
			public boolean isCellEditable(final int row, final int col) {return false;}
            @Override
			public void setValueAt(final Object aValue, final int row, final int column) { data[row][column] = aValue; }
         };

		table = new JTable(dataModel);
 		table.getTableHeader().setReorderingAllowed(false);
 		TableColumnModel colmodel = table.getColumnModel();

 		colmodel.getColumn(0).setPreferredWidth(30);
 		colmodel.getColumn(1).setPreferredWidth(200);
 		colmodel.getColumn(2).setPreferredWidth(40);

 		JScrollPane scrollPane = new JScrollPane();
 		JViewport vp = scrollPane.getViewport();
	 	vp.add(table);

		okB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				setVisible(false);
			}
		});
		panel.add(okB);

		getContentPane().setLayout(new BorderLayout());
 		getContentPane().add(scrollPane, BorderLayout.CENTER);
 		getContentPane().add(panel, BorderLayout.SOUTH);
 		setSize(550, 500);
 		setLocation((dim.width-getWidth())/2, (dim.height-getHeight())/2);
	}
}
