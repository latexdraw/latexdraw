package net.sf.latexdraw.ui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.IdentityHashMap;
import java.util.Map;

import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
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
		widget.add(composeShapePositionerWidgets(metaShapeCustomiser.getShapePositioner(), canvas));
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
}
