package org.malai.interaction;

import java.awt.Component;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import org.malai.widget.MFrame;

/**
 * A Swing event manager gathers Swing events produces by widgets and tranfers them handlers.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2011 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 10/10/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public class SwingEventManager implements MouseListener, KeyListener, MouseMotionListener, MouseWheelListener, ActionListener, ItemListener, ChangeListener {
	/** The ID used to identify the mouse. Will change when multi-device will be supported. */
	public static final int ID_MOUSE = 0;

	/** The ID used to identify the keyboard. Will change when multi-device will be supported. */
	public static final int ID_KB 	 = 1;

	/** The handlers that are notified when events occur. */
	protected List<EventHandler> handlers;


	/**
	 * Creates a event manager that gathers Swing events and tranfers them to handlers.
	 * @since 0.1
	 */
	public SwingEventManager() {
		super();

		handlers = new CopyOnWriteArrayList<EventHandler>();
	}



	/**
	 * Attaches the SwingEventManager to the Java component to listen.
	 * @param comp The Java Component to listen.
	 */
	public void attachTo(final Component comp) {
		if(comp!=null) {
			comp.addMouseListener(this);
			comp.addMouseMotionListener(this);
			comp.addMouseWheelListener(this);
			comp.addKeyListener(this);

			if(comp instanceof AbstractButton)
				((AbstractButton)comp).addActionListener(this);

			if(comp instanceof JTextField)
				((JTextField)comp).addActionListener(this);

			if(comp instanceof ItemSelectable)
				((ItemSelectable)comp).addItemListener(this);

			if(comp instanceof JSpinner)
				((JSpinner)comp).addChangeListener(this);

			if(comp instanceof JTabbedPane)
				((JTabbedPane)comp).addChangeListener(this);
		}
	}



	/**
	 * Detaches the SwingEventManager to the Java listened component.
	 * @param comp The Java Component to detach.
	 */
	public void detachForm(final Component comp) {
		if(comp!=null) {
			comp.removeMouseListener(this);
			comp.removeMouseMotionListener(this);
			comp.removeMouseWheelListener(this);
			comp.removeKeyListener(this);

			if(comp instanceof AbstractButton)
				((AbstractButton)comp).removeActionListener(this);

			if(comp instanceof JTextField)
				((JTextField)comp).removeActionListener(this);

			if(comp instanceof ItemSelectable)
				((ItemSelectable)comp).removeItemListener(this);

			if(comp instanceof JSpinner)
				((JSpinner)comp).removeChangeListener(this);

			if(comp instanceof JTabbedPane)
				((JTabbedPane)comp).removeChangeListener(this);
		}
	}


	/**
	 * Adds a handler to the event manager.
	 * @param h The handler to add. Must not be null.
	 * @since 3.0
	 */
	public void addHandlers(final EventHandler h) {
		if(h!=null)
			handlers.add(h);
	}


	/**
	 * Removes a handler from the event manager.
	 * @param h The handler to remove. Must not be null.
	 * @since 3.0
	 */
	public void removeHandler(final EventHandler h) {
		if(h!=null)
			handlers.remove(h);
	}



	@Override
	public void mouseClicked(final MouseEvent e) {
		//
	}



	@Override
	public void mouseEntered(final MouseEvent e) {
		//
	}



	@Override
	public void mouseExited(final MouseEvent e) {
		//
	}



	@Override
	public void mousePressed(final MouseEvent e) {
		if(e==null) return;

		final Object src 	= e.getSource();
		final int x			= e.getX();
		final int y			= e.getY();
		final int button	= e.getButton();

		for(EventHandler handler : handlers)
			handler.onPressure(button, x, y, ID_MOUSE, src);
	}



	@Override
	public void mouseReleased(final MouseEvent e) {
		if(e==null) return;

		final Object src 	= e.getSource();
		final int x			= e.getX();
		final int y			= e.getY();
		final int button	= e.getButton();

		for(EventHandler handler : handlers)
			handler.onRelease(button, x, y, ID_MOUSE, src);
	}



	@Override
	public void keyPressed(final KeyEvent e) {
		if(e==null) return;

		for(EventHandler handler : handlers)
			handler.onKeyPressure(e.getKeyCode(), ID_KB, e.getSource());
	}



	@Override
	public void keyReleased(final KeyEvent e) {
		if(e==null) return;

		for(EventHandler handler : handlers)
			handler.onKeyRelease(e.getKeyCode(), ID_KB, e.getSource());
	}



	@Override
	public void keyTyped(final KeyEvent e) {
		//
	}



	@Override
	public void mouseDragged(final MouseEvent e) {
		if(e==null) return;

		final Object src 	= e.getSource();
		final int x			= e.getX();
		final int y			= e.getY();
		final int button	= e.getButton();

		for(EventHandler handler : handlers)
			handler.onMove(button, x, y, true, ID_MOUSE, src);
	}



	@Override
	public void mouseMoved(final MouseEvent e) {
		if(e==null) return;

		final Object src 	= e.getSource();
		final int x			= e.getX();
		final int y			= e.getY();
		final int button	= e.getButton();

		for(EventHandler handler : handlers)
			handler.onMove(button, x, y, false, ID_MOUSE, src);
	}



	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		if(e==null) return;

		final int posX 		= e.getX();
		final int posY 		= e.getY();
		final Object src 	= e.getSource();
		final int direction = e.getWheelRotation();
		final int type 		= e.getScrollType();
		final int amount 	= e.getScrollAmount();

		for(EventHandler handler : handlers)
			handler.onScroll(posX, posY, direction, amount, type, ID_MOUSE, src);
	}



	@Override
	public void actionPerformed(final ActionEvent e) {
		if(e==null) return;

		final Object src = e.getSource();

		if(src instanceof JMenuItem) {
			final JMenuItem mi = (JMenuItem) e.getSource();

    		for(EventHandler handler : handlers)
				handler.onMenuItemPressed(mi);
		}
		else if(src instanceof JCheckBox) {
			final JCheckBox cb = (JCheckBox) e.getSource();

    		for(EventHandler handler : handlers)
				handler.onCheckBoxModified(cb);
		}
		else if(src instanceof AbstractButton) {
			final AbstractButton ab = (AbstractButton)e.getSource();

    		for(EventHandler handler : handlers)
				handler.onButtonPressed(ab);
		}
		else if(src instanceof JTextComponent) {
			final JTextComponent tc = (JTextComponent)e.getSource();

    		for(EventHandler handler : handlers)
				handler.onTextChanged(tc);
		}
	}


	@Override
	public void itemStateChanged(final ItemEvent e) {
		if(e==null) return;

		if(e.getItemSelectable() instanceof JComboBox) {
			final JComboBox cb = (JComboBox) e.getItemSelectable();

    		for(EventHandler handler : handlers)
				handler.onItemSelected(cb);
		}
	}


	@Override
	public void stateChanged(final ChangeEvent e) {
		if(e==null) return;
		final Object src = e.getSource();

		if(src instanceof JSpinner) {
			final JSpinner spinner = (JSpinner)src;

    		for(final EventHandler handler : handlers)
				handler.onSpinnerChanged(spinner);
		}
		else if(src instanceof JTabbedPane) {
			final JTabbedPane tabbedPanel = (JTabbedPane)src;

    		for(final EventHandler handler : handlers)
				handler.onTabChanged(tabbedPanel);
		}
	}


	/**
	 * This pseudo-event is defined to manage pressure on the close button of frames.
	 * @param frame The frame closed. Must not be null.
	 * @since 0.2
	 */
	public void windowClosed(final MFrame frame) {
		if(frame==null) return;

		for(EventHandler handler : handlers)
			handler.onWindowClosed(frame);
	}
}
