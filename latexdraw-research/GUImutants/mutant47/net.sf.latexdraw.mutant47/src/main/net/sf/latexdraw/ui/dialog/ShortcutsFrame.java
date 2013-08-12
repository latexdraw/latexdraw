package net.sf.latexdraw.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import net.sf.latexdraw.instruments.FileLoaderSaver;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.LSystem;

/**
 * A frame containing the shortcuts of the program.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
 * @since 1.9<br>
 */
public class ShortcutsFrame extends JFrame {
	private static final long serialVersionUID = 1L;


	/**  Creates a frame containing a table with all the shortcuts. */
	public ShortcutsFrame() {
		super(LangTool.INSTANCE.getString19("LaTeXDrawFrame.3")); //$NON-NLS-1$
		setIconImage(LResources.LATEXDRAW_ICON.getImage());

		JTable table;
		final String[] titles = {LangTool.INSTANCE.getString19("ShortcutsFrame.1"),  //$NON-NLS-1$
				LangTool.INSTANCE.getString16("ParserMessagesFrame.0"),  //$NON-NLS-1$
				LangTool.INSTANCE.getString19("ShortcutsFrame.3")};  //$NON-NLS-1$
		String catEdit	= LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.89"); //$NON-NLS-1$
		String catNav	= LangTool.INSTANCE.getString19("ShortcutsFrame.4"); //$NON-NLS-1$
		String catTran	= LangTool.INSTANCE.getString19("ShortcutsFrame.5"); //$NON-NLS-1$
		String catDraw	= LangTool.INSTANCE.getString19("ShortcutsFrame.6"); //$NON-NLS-1$
		String catFile	= LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.88"); //$NON-NLS-1$
		JButton okB 	= new JButton(LangTool.INSTANCE.getString18("LaTeXDrawFrame.23")); //$NON-NLS-1$
		JPanel panel 	= new JPanel();

		String ctrl = KeyEvent.getKeyModifiersText(InputEvent.CTRL_MASK);
		String shift = KeyEvent.getKeyModifiersText(InputEvent.SHIFT_MASK);
		String leftClick = LangTool.INSTANCE.getString19("ShortcutsFrame.8"); //$NON-NLS-1$
		final String st1 = LangTool.INSTANCE.getString19("ShortcutsFrame.9"); //$NON-NLS-1$
		final String st2 = LangTool.INSTANCE.getString19("ShortcutsFrame.10"); //$NON-NLS-1$
		final String st3 = LangTool.INSTANCE.getString19("ShortcutsFrame.11"); //$NON-NLS-1$
		final String st4 = LangTool.INSTANCE.getString19("ShortcutsFrame.12"); //$NON-NLS-1$
		final Object[][] data = {
			{ctrl+"+C", LResources.LABEL_COPY, catEdit},//$NON-NLS-1$
			{ctrl+"+V", LResources.LABEL_PASTE, catEdit},//$NON-NLS-1$
			{ctrl+"+X", LResources.LABEL_CUT, catEdit},//$NON-NLS-1$
			{ctrl+"+Z", LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.23"), catEdit},//$NON-NLS-1$
			{ctrl+"+Y", LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.22"), catEdit},//$NON-NLS-1$
			{ctrl+"+N", LResources.LABEL_NEW, catFile},//$NON-NLS-1$
			{ctrl+"+O", FileLoaderSaver.LABEL_OPEN, catFile},//$NON-NLS-1$
			{ctrl+"+S", FileLoaderSaver.LABEL_SAVE, catFile},//$NON-NLS-1$
			{ctrl+"+W", LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.18"), catFile},//$NON-NLS-1$
			{KeyEvent.getKeyText(KeyEvent.VK_ADD), LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.57"), catNav},
			{KeyEvent.getKeyText(KeyEvent.VK_SUBTRACT), LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.58"), catNav},
			{KeyEvent.getKeyText(KeyEvent.VK_DELETE), LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.17"), catDraw},
			{KeyEvent.getKeyText(KeyEvent.VK_RIGHT), st1, catNav},
			{KeyEvent.getKeyText(KeyEvent.VK_LEFT), st2, catNav},
			{KeyEvent.getKeyText(KeyEvent.VK_UP), st3, catNav},
			{KeyEvent.getKeyText(KeyEvent.VK_DOWN), st4, catNav},
			//TODO
//			{shift+'+'+KeyEvent.getKeyText(KeyEvent.VK_RIGHT), LangTool.INSTANCE.getString19("ShortcutsFrame.18"), catNav}, //$NON-NLS-1$
//			{shift+'+'+KeyEvent.getKeyText(KeyEvent.VK_LEFT), LangTool.INSTANCE.getString19("ShortcutsFrame.19"), catNav}, //$NON-NLS-1$
//			{shift+'+'+KeyEvent.getKeyText(KeyEvent.VK_UP), LangTool.INSTANCE.getString19("ShortcutsFrame.20"), catNav}, //$NON-NLS-1$
//			{shift+'+'+KeyEvent.getKeyText(KeyEvent.VK_DOWN), LangTool.INSTANCE.getString19("ShortcutsFrame.21"), catNav}, //$NON-NLS-1$
			{ctrl+"+U", LangTool.INSTANCE.getString19("ShortcutsFrame.23"), catTran}, //$NON-NLS-1$ //$NON-NLS-2$
			{ctrl+"+A", LangTool.INSTANCE.getString19("ShortcutsFrame.25"), catDraw}, //$NON-NLS-1$ //$NON-NLS-2$
			{ctrl+'+'+leftClick, LangTool.INSTANCE.getString19("ShortcutsFrame.26"), catDraw}, //$NON-NLS-1$
			{shift+'+'+leftClick, LangTool.INSTANCE.getString19("ShortcutsFrame.27"), catDraw}, //$NON-NLS-1$
			{ctrl+'+'+LangTool.INSTANCE.getString19("ShortcutsFrame.29"), LangTool.INSTANCE.getString19("ShortcutsFrame.30"), catDraw} //$NON-NLS-1$ //$NON-NLS-2$
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

 		colmodel.getColumn(0).setPreferredWidth(100);
 		colmodel.getColumn(1).setPreferredWidth(350);
 		colmodel.getColumn(2).setPreferredWidth(80);

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
 		setSize(700, 500);
 		Dimension dim = LSystem.INSTANCE.getScreenDimension();
 		Rectangle rec = getGraphicsConfiguration().getBounds();
 		setLocation((int)(rec.getX()+dim.width/2-getWidth()/2), (int)(rec.getY()+dim.height/2-getHeight()/2));
	}
}
