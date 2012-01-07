package org.malai.interaction;

import java.awt.ItemSelectable;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.text.JTextComponent;

import org.malai.widget.MFrame;

/**
 * This interface can be used for object that want to gather events (mouse pressed, etc.) produced by HIDs.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/19/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.1
 */
public interface EventHandler {
	/**
	 * Defines action to do when a timeout is elapsed.
	 * @param timeoutTransition The transition which produced the timeout event.
	 * @since 0.2
	 */
	void onTimeout(final TimeoutTransition timeoutTransition);


	/**
	 * Defines action to do when the button of a mouse or something equivalent is pressed.
	 * @param button The identifier of the pressed button.
	 * @param x The X-coordinate of the pressure location.
	 * @param y The Y-coordinate of the pressure location.
	 * @param idHID The identifier of the used HID.
	 * @param source The object that produces the event.
	 * @since 0.1
	 */
	void onPressure(final int button, final int x, final int y, final int idHID, final Object source);

	/**
	 * Defines action to do when the button of a mouse or something equivalent is released.
	 * @param button The identifier of the released button.
	 * @param x The X-coordinate of the release location.
	 * @param y The Y-coordinate of the release location.
	 * @param idHID The identifier of the used HID.
	 * @param source The object that produces the event.
	 * @since 0.1
	 */
	void onRelease(final int button, final int x, final int y, final int idHID, final Object source);

	/**
	 * Defines action to do when a mouse or something equivalent is moved.
	 * @param button The identifier of the pressed button, if a button is pressed (else -1).
	 * @param x The X-coordinate of the pressure location.
	 * @param y The Y-coordinate of the pressure location.
	 * @param pressed True: a button of the mouse is pressed.
	 * @param idHID The identifier of the used HID.
	 * @param source The object that produces the event.
	 * @since 0.1
	 */
	void onMove(final int button, final int x, final int y, final boolean pressed, final int idHID, final Object source);

	/**
	 * Defines actions to do when a key of a keyboard is pressed.
	 * @param key The pressed key code.
	 * @param idHID The identifier of the HID that produced the event.
	 * @param source The object that produces the event.
	 * @since 0.2
	 */
	void onKeyPressure(final int key, final int idHID, final Object source);

	/**
	 * Defines actions to do when a key of a keyboard is released.
	 * @param key The released key code.
	 * @param idHID The identifier of the HID that produced the event.
	 * @param source The object that produces the event.
	 * @since 0.2
	 */
	void onKeyRelease(final int key, final int idHID, final Object source);

	/**
	 * Defines actions to do when a button is activated.
	 * @param button The pressed button.
	 * @since 0.1
	 */
	void onButtonPressed(final AbstractButton button);

	/**
	 * Defines actions to do when the value of the given spinner change.
	 * @param spinner The spinner that changed.
	 * @since 0.2
	 */
	void onSpinnerChanged(final JSpinner spinner);

	/**
	 * Defines actions to do when the selected items of the given list changed.
	 * @param itemSelectable The list that changed.
	 * @since 0.2
	 */
	void onItemSelected(final ItemSelectable itemSelectable);

	/**
	 * Defines actions to do when the given check box is selected/unselected.
	 * @param checkbox The modified check box;
	 * @since 0.2
	 */
	void onCheckBoxModified(final JCheckBox checkbox);

	/**
	 * Defines actions to do when the given menu item is pressed.
	 * @param menuItem The pressed menu item.
	 * @since 0.2
	 */
	void onMenuItemPressed(final JMenuItem menuItem);

	/**
	 * Defines actions to do when the text of a text field or something equivalent is modified.
	 * @param textComp The text field.
	 * @since 0.1
	 */
	void onTextChanged(final JTextComponent textComp);

	/**
	 * Defines actions to do when a scrolling device (e.g. a mouse wheel) is used.
	 * @param posX The X-coordinate of the position where the event occurred.
	 * @param posY The Y-coordinate of the position where the event occurred.
	 * @param direction Defines if the scrolling is up (positive value) or down (negative value).
	 * @param amount The number of units to scroll by scroll.
	 * @param type The type of scrolling that should take place in response to this event (block or unit increment).
	 * @param idHID The identifier of the used HID.
	 * @param src The object that threw the event.
	 * @since 0.2
	 */
	void onScroll(final int posX, final int posY, final int direction, final int amount,
				  final int type, final int idHID, final Object src);

	/**
	 * Defines actions to do when the decorative close button of a frame is pressed.
	 * @param frame The frame closed.
	 * @since 0.2
	 */
	void onWindowClosed(final MFrame frame);

	/**
	 * Defines actions to do when the selected tab of a tabbed panel has changed.
	 * @param tabbedPanel The tabbed panel that produces the event.
	 * @since 0.2
	 */
	void onTabChanged(final JTabbedPane tabbedPanel);
}
