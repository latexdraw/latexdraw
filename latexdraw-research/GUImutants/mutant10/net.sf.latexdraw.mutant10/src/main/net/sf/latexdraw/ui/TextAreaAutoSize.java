package net.sf.latexdraw.ui;

import java.awt.FontMetrics;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

import org.malai.swing.interaction.SwingEventManager;
import org.malai.swing.widget.MTextArea;

/**
 * This widgets is a text area which automatically resizes is width and height
 * according to its text.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 20/12/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class TextAreaAutoSize extends MTextArea {
	private static final long serialVersionUID = 1L;


	/**
	 * Creates the widget.
	 */
	public TextAreaAutoSize() {
		super(false, true);

		setRows(1);
		setColumns(1);
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_MASK), "insertBreakLD"); //$NON-NLS-1$
		getActionMap().put("insertBreakLD", new DefaultEditorKit.InsertBreakAction()); //$NON-NLS-1$
		addKeyListener(new TextAreaKeyListener());
		eventManager = new SwingEventManager();
		eventManager.attachTo(this);
	}


	@Override
	public void setVisible(final boolean visible) {
		super.setVisible(visible);

		if(visible)
			updateDimension();
	}


	@Override
	public void setText(final String text) {
		super.setText(text);
		updateDimension();
	}


	/**
	 * Updates the size of the widget according to its text.
	 */
	public void updateDimension() {
		// A space is added at the end of the text to consider all the \n characters.
		final String text = getText() + ' ';
		final String[] textSplited = text.split("\n");
		Rectangle2D rec;
		double width 	= 0.;
		double height 	= 0.;
		final FontMetrics fm 	= getFontMetrics(getFont());
		final double heightInc 	= fm.getAscent() + fm.getDescent() + fm.getLeading();

		if(textSplited.length>0) // Removing the space added at the beginning of the method.
			textSplited[textSplited.length-1] = textSplited[textSplited.length-1].substring(0, textSplited[textSplited.length-1].length()-1);

		for(String str : textSplited) {
			rec = fm.getStringBounds(str, null);
			if(rec.getWidth()>width)
				width = rec.getWidth();
			height += heightInc;
		}

		setBounds(getX(), getY(), (int)width+10, (int)height);
	}


	/**
	 * This listener is used to override the key binding "enter" that adds an end of line
	 * character.
	 */
	class TextAreaKeyListener implements KeyListener {
		@Override
		public void keyTyped(final KeyEvent e) {
			//
		}

		@Override
		public void keyReleased(final KeyEvent e) {
			updateDimension();
		}

		@Override
		public void keyPressed(final KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER && e.getModifiers()==0)
				e.consume();
		}
	}
}
