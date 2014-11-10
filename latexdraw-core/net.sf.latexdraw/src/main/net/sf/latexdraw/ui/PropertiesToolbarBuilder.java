package net.sf.latexdraw.ui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.IdentityHashMap;
import java.util.Map;

import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.ShapeCoordDimCustomiser;
import net.sf.latexdraw.instruments.ShapeFreeHandCustomiser;
import net.sf.latexdraw.instruments.ShapeGrouper;
import net.sf.latexdraw.instruments.ShapePositioner;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.swing.ui.SwingUIComposer;
import org.malai.swing.widget.MPanel;
import org.malai.swing.widget.MProgressBar;

/**
 * The composer that creates the properties tool bar of the application.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 12/08/11<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class PropertiesToolbarBuilder extends SwingUIComposer<MPanel> {
	/** The main frame of the application. */
	protected final LFrame frame;

	/** The hash map used to map a widget to its container. */
	protected final Map<Component, WidgetMiniToolbar> mapContainers;


	/**
	 * Creates the bottom panel that contains a set of widgets to modify shapes.
	 * @param frame The frame that will contains the tool bar.
	 * @throws IllegalArgumentException If the given instrument is null.
	 * @since 3.0
	 */
	public PropertiesToolbarBuilder(final LFrame frame) {
		super();
		this.frame 		= frame;
		mapContainers	= new IdentityHashMap<>();
	}


	@Override
	public void compose(final MProgressBar progressBar) {
		final LCanvas canvas = frame.getCanvas();
		final MetaShapeCustomiser metaShapeCustomiser = frame.getMetaShapeCustomiser();

		widget = new MPanel(false, true);
		widget.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Creation of the widgets layout of the shape properties instruments.
		composeJoinShapesWidgets(metaShapeCustomiser.getShapeGrouper());
		widget.add(composeShapePositionerWidgets(metaShapeCustomiser.getShapePositioner(), canvas));
		widget.add(composeDimPosPropertiesToolbar(metaShapeCustomiser.getDimPosCustomiser(), canvas));
		if(progressBar!=null) progressBar.addToProgressBar(5);
		widget.add(composeFreeHandPropertiesToolbar(metaShapeCustomiser.getFreeHandCustomiser(), canvas));
		if(progressBar!=null) progressBar.addToProgressBar(5);
	}


	// Creates the tool bar for place shapes.
	protected WidgetMiniToolbar composeShapePositionerWidgets(final ShapePositioner ins, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(LResources.FOREGROUND_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText(LangTool.INSTANCE.getString17("LaTeXDrawFrame.6")); //$NON-NLS-1$
		list.addComponent(ins.getForegroundButton());
		list.addComponent(ins.getBackgroundButton());
		list.addSeparator();
		mapContainers.put(ins.getForegroundButton(), list);
		mapContainers.put(ins.getBackgroundButton(), list);
		ins.addEventable(list.getToolbar());
		list.setVisible(false);
		return list;
	}


	// Composition of the widgets that joins/separates shapes.
	protected void composeJoinShapesWidgets(final ShapeGrouper grouper) {
		widget.add(grouper.getGroupB());
		widget.add(grouper.getSepB());
		grouper.addEventable(widget);
	}


	/** Creates the toolbar containing the widgets that customises axes. */
	protected WidgetMiniToolbar composeFreeHandPropertiesToolbar(final ShapeFreeHandCustomiser cust, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(LResources.FREE_HAND_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText(LangTool.INSTANCE.getStringActions("PropBuilder.1")); //$NON-NLS-1$

		UIBuilder.addCombobox(list, cust.getFreeHandType());
		UIBuilder.addSpinner(list, cust.getGapPoints(), 50);
		list.addComponent(cust.getOpen());
		list.addSeparator();

		mapContainers.put(cust.getFreeHandType(), list);
		mapContainers.put(cust.getGapPoints(), list);
		mapContainers.put(cust.getOpen(), list);

		cust.addEventable(list.getToolbar());
		list.setVisible(false);
		return list;
	}


	protected WidgetMiniToolbar composeDimPosPropertiesToolbar(final ShapeCoordDimCustomiser cust, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(LResources.DIM_POS_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText(LangTool.INSTANCE.getStringActions("PropBuilder.6")); //$NON-NLS-1$

		UIBuilder.addSpinner(list, cust.getTlxS(), 90);
		UIBuilder.addSpinner(list, cust.getTlyS(), 90);
		list.addSeparator();

		mapContainers.put(cust.getTlxS(), list);
		mapContainers.put(cust.getTlyS(), list);
		list.setVisible(false);
		cust.addEventable(list.getToolbar());
		return list;
	}
}
