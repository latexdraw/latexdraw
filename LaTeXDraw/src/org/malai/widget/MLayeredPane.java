package org.malai.widget;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JScrollBar;

import org.malai.interaction.Eventable;
import org.malai.interaction.SwingEventManager;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;

/**
 * Overrides the widget JLayeredPane to be used within Malai.<br>
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
 * 12/20/2010<br>
 * @author Arnaud BLOUIN
 * @version 0.2
 * @since 0.2
 */
public class MLayeredPane extends JLayeredPane implements Picker, Eventable, Scrollable {
	private static final long serialVersionUID = 1L;

	/** The possible scrollpane that contains the panel. */
	protected MScrollPane scrollpane;

	/** The event manager that listens events produced by the panel. May be null. */
	protected SwingEventManager eventManager;

	/** The components that must be resized when the layered pane dimensions change. */
	protected List<JComponent> componentsToResize;


	/**
	 * Creates and initialises the layred pane.
	 * @param withScrollPane True: a scrollpane will be created and will contain the panel.
	 * @param withEvtManager True: the panel will have an event manager.
	 * @since 0.1
	 */
	public MLayeredPane(final boolean withScrollPane, final boolean withEvtManager) {
		super();

		if(withScrollPane) {
			scrollpane = new MScrollPane();
			scrollpane.getViewport().add(this);
		}

		if(withEvtManager) {
			eventManager = new SwingEventManager();
			eventManager.attachTo(this);
		}

		addComponentListener(new ComponentAdapter() {
            @Override
			public void componentResized(final ComponentEvent e){
            	if(componentsToResize!=null)
            		for(JComponent comp : componentsToResize)
            			updateComponentSize(comp);
            }
        });
	}


	/**
	 * Updates the size of the given component.
	 * @param component The component to modify.
	 * @since 0.2
	 */
	protected void updateComponentSize(final JComponent component) {
		component.setBounds(0, 0, getWidth(), getHeight());
	}


	/**
	 * Components of the pane may have to be automatically resized
	 * when the pane is resized. This method sets that the given
	 * component must be resized when the pane is resized.
	 * @param component The component to resize.
	 * @since 0.2
	 */
	public void addComponentsToResize(final JComponent component) {
		if(component!=null) {
			if(componentsToResize==null)
				componentsToResize = new ArrayList<JComponent>();
			componentsToResize.add(component);
			updateComponentSize(component);
		}
	}


	@Override
	public Point2D getRelativePoint(final double x, final double y, final Object o){
		return WidgetUtilities.INSTANCE.getRelativePoint(getComponents(), x, y, o);
	}


	@Override
	public Pickable getPickableAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickableAt(this, getComponents(), x, y);
	}


	@Override
	public Picker getPickerAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickerAt(this, getComponents(), x, y);
	}


	@Override
	public boolean contains(final Object obj) {
		return WidgetUtilities.INSTANCE.contains(getComponents(), obj);
	}


	@Override
	public boolean hasScrollPane() {
		return scrollpane!=null;
	}


	@Override
	public MScrollPane getScrollpane() {
		return scrollpane;
	}


	/**
	 * @return True if the horizontal scroll bar is visible.
	 * @since 0.1
	 */
	public boolean isHorizontalScrollbarVisible() {
		return getScrollbar(true).isVisible();
	}



	/**
	 * @return True if the vertical scrool bar is visible.
	 * @since 0.1
	 */
	public boolean isVerticalScrollbarVisible() {
		return getScrollbar(true).isVisible();
	}



	/**
	 * @param vertical True: the vertical scrollbar is returned. Otherwise, the horizontal scroll bar.
	 * @return The required scroll bar or null if the panel has no scrollpane.
	 * @since 0.1
	 */
	protected JScrollBar getScrollbar(final boolean vertical) {
		if(hasScrollPane())
			return vertical ? getScrollpane().getVerticalScrollBar() : getScrollpane().getHorizontalScrollBar();

		return null;
	}



	/**
	 * Scroll the vertical scroll bar, if possible, using the given increment.
	 * @param increment The increment to apply on the vertical scroll bar.
	 * @since 0.1
	 */
	public void scrollHorizontally(final int increment) {
		scroll(increment, false);
	}



	/**
	 * Scroll the vertical scroll bar, if possible, using the given increment.
	 * @param increment The increment to apply on the vertical scroll bar.
	 * @since 0.1
	 */
	public void scrollVertically(final int increment) {
		scroll(increment, true);
	}



	/**
	 * Scroll the vertical or horizontal scroll bar, if possible, using the given increment.
	 * @param increment The increment to apply on the vertical scroll bar.
	 * @param vertical True: the vertical scroll bar is
	 * @since 0.1
	 */
	protected void scroll(final int increment, final boolean vertical) {
		JScrollBar scrollbar = getScrollbar(vertical);

		if(scrollbar!=null && scrollbar.isVisible())
			scrollbar.setValue(scrollbar.getValue() - increment);
	}



	@Override
	public SwingEventManager getEventManager() {
		return eventManager;
	}


	@Override
	public Component add(final Component comp, final int index) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, comp);
		return super.add(comp, index);
	}


	@Override
	public void add(final Component comp, final Object constraints, final int index) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, comp);
		super.add(comp, constraints, index);
	}


	@Override
	public void add(final Component comp, final Object constraints) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, comp);
		super.add(comp, constraints);
	}



	@Override
	public Component add(final Component comp) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, comp);
		return super.add(comp);
	}



	@Override
	public Component add(final String name, final Component comp) {
		WidgetUtilities.INSTANCE.attachAddedComponent(eventManager, comp);
		return super.add(name, comp);
	}


	@Override
	public boolean hasEventManager() {
		return eventManager!=null;
	}
}
