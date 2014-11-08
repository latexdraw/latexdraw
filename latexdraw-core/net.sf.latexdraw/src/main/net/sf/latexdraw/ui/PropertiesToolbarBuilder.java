package net.sf.latexdraw.ui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.ShapeAxesCustomiser;
import net.sf.latexdraw.instruments.ShapeCoordDimCustomiser;
import net.sf.latexdraw.instruments.ShapeFreeHandCustomiser;
import net.sf.latexdraw.instruments.ShapeGridCustomiser;
import net.sf.latexdraw.instruments.ShapeGrouper;
import net.sf.latexdraw.instruments.ShapePlotCustomiser;
import net.sf.latexdraw.instruments.ShapePositioner;
import net.sf.latexdraw.instruments.ShapeRotationCustomiser;
import net.sf.latexdraw.instruments.ShapeStdGridCustomiser;
import net.sf.latexdraw.instruments.ShapeTransformer;
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
		widget.add(composeRotationToolbar(metaShapeCustomiser.getRotationCustomiser(), canvas));
		widget.add(composeShapePositionerWidgets(metaShapeCustomiser.getShapePositioner(), canvas));
		widget.add(composeMirrorShapes(metaShapeCustomiser.getShapeTransformer(), canvas));
		widget.add(composeAlignShapes(metaShapeCustomiser.getShapeTransformer(), canvas));
		widget.add(composeDistributeShapes(metaShapeCustomiser.getShapeTransformer(), canvas));
		widget.add(composeDimPosPropertiesToolbar(metaShapeCustomiser.getDimPosCustomiser(), canvas));
		if(progressBar!=null) progressBar.addToProgressBar(5);
		widget.add(composeAxesPropertiesToolbar(metaShapeCustomiser.getAxesCustomiser(), canvas));
		widget.add(composeGridLabelsPropertiesToolbar(metaShapeCustomiser.getAxesCustomiser(), metaShapeCustomiser.getGridCustomiser(),
					metaShapeCustomiser.getStandardGridCustomiser(), canvas));
		widget.add(composePlotWidgets(metaShapeCustomiser.getPlotCustomiser(), canvas));
		widget.add(composeFreeHandPropertiesToolbar(metaShapeCustomiser.getFreeHandCustomiser(), canvas));
		if(progressBar!=null) progressBar.addToProgressBar(5);
	}


	protected WidgetMiniToolbar composePlotWidgets(final ShapePlotCustomiser plotCustom, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(LResources.PLOT_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Customizes plots' properties.");
		list.addComponent(plotCustom.getPolarCB());
		list.addComponent(plotCustom.getPlotStyleCB());
		UIBuilder.addSpinner(list, plotCustom.getNbPtsSpinner(), 70);
		UIBuilder.addSpinner(list, plotCustom.getMinXSpinner(), 70);
		UIBuilder.addSpinner(list, plotCustom.getMaxXSpinner(), 70);
		UIBuilder.addSpinner(list, plotCustom.getXScaleSpinner(), 70);
		UIBuilder.addSpinner(list, plotCustom.getYScaleSpinner(), 70);
		list.addSeparator();
		mapContainers.put(plotCustom.getNbPtsSpinner(), list);
		mapContainers.put(plotCustom.getMinXSpinner(), list);
		mapContainers.put(plotCustom.getMaxXSpinner(), list);
		mapContainers.put(plotCustom.getXScaleSpinner(), list);
		mapContainers.put(plotCustom.getYScaleSpinner(), list);
		mapContainers.put(plotCustom.getPolarCB(), list);
		mapContainers.put(plotCustom.getPlotStyleCB(), list);
		plotCustom.addEventable(list.getToolbar());
		list.setVisible(false);
		return list;
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


	// Creates the tool bar for mirroring shapes.
	protected WidgetMiniToolbar composeDistributeShapes(final ShapeTransformer transformer, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(LResources.DIST_VERT_EQUAL_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.8")); //$NON-NLS-1$
		list.addComponent(transformer.distribVertEq());
		list.addComponent(transformer.distribVertMid());
		list.addComponent(transformer.distribVertTop());
		list.addComponent(transformer.distribVertBot());
		list.addComponent(transformer.distribHorizEq());
		list.addComponent(transformer.distribHorizMid());
		list.addComponent(transformer.distribHorizLeft());
		list.addComponent(transformer.distribHorizRight());
		list.addSeparator();
		mapContainers.put(transformer.distribHorizEq(), list);
		mapContainers.put(transformer.distribHorizLeft(), list);
		mapContainers.put(transformer.distribHorizRight(), list);
		mapContainers.put(transformer.distribHorizMid(), list);
		mapContainers.put(transformer.distribVertEq(), list);
		mapContainers.put(transformer.distribVertTop(), list);
		mapContainers.put(transformer.distribVertBot(), list);
		mapContainers.put(transformer.distribVertMid(), list);
		transformer.addEventable(list.getToolbar());
		list.setVisible(false);
		return list;
	}

	// Creates the tool bar for mirroring shapes.
	protected WidgetMiniToolbar composeAlignShapes(final ShapeTransformer transformer, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(LResources.ALIGN_LEFT_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LFrame2.1"));//$NON-NLS-1$
		list.addComponent(transformer.alignLeft());
		list.addComponent(transformer.alignRight());
		list.addComponent(transformer.alignBot());
		list.addComponent(transformer.alignTop());
		list.addComponent(transformer.alignMidHoriz());
		list.addComponent(transformer.alignMidVert());
		list.addSeparator();
		mapContainers.put(transformer.alignLeft(), list);
		mapContainers.put(transformer.alignRight(), list);
		mapContainers.put(transformer.alignBot(), list);
		mapContainers.put(transformer.alignTop(), list);
		mapContainers.put(transformer.alignMidHoriz(), list);
		mapContainers.put(transformer.alignMidVert(), list);
		transformer.addEventable(list.getToolbar());
		list.setVisible(false);
		return list;
	}


	// Creates the tool bar for mirroring shapes.
	protected WidgetMiniToolbar composeMirrorShapes(final ShapeTransformer transformer, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(LResources.MIRROR_H_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.6")); //$NON-NLS-1$
		list.addComponent(transformer.mirrorH());
		list.addComponent(transformer.mirrorV());
		list.addSeparator();
		mapContainers.put(transformer.mirrorH(), list);
		mapContainers.put(transformer.mirrorV(), list);
		transformer.addEventable(list.getToolbar());
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


	/** Creates the toolbar containing the widgets that customises grids' labels. */
	protected WidgetMiniToolbar composeGridLabelsPropertiesToolbar(final ShapeAxesCustomiser axeCust, final ShapeGridCustomiser gridCust,
																	final ShapeStdGridCustomiser stdGridCust, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(LResources.GRID_LABELS, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText(LangTool.INSTANCE.getStringActions("PropBuilder.2")); //$NON-NLS-1$

		final MPanel p1 = new MPanel(false, true);
		final MPanel p2 = new MPanel(false, true);
		final MPanel p3 = new MPanel(false, false);
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
		UIBuilder.addCombobox(p1, axeCust.getShowLabels());
		p1.add(axeCust.getShowOrigin());
		UIBuilder.addSpinner(p2, axeCust.getIncrLabelX(), 50);
		UIBuilder.addSpinner(p2, axeCust.getIncrLabelY(), 50);
		UIBuilder.addSpinner(p2, axeCust.getDistLabelsX(), 60);
		UIBuilder.addSpinner(p2, axeCust.getDistLabelsY(), 60);
		p3.add(p1);
		p3.add(p2);
		list.addComponent(p3);
		list.addSeparator();

		mapContainers.put(axeCust.getShowLabels(), list);
		mapContainers.put(axeCust.getShowOrigin(), list);
		mapContainers.put(axeCust.getIncrLabelX(), list);
		mapContainers.put(axeCust.getIncrLabelY(), list);
		mapContainers.put(axeCust.getDistLabelsX(), list);
		mapContainers.put(axeCust.getDistLabelsY(), list);

		axeCust.addEventable(p1);
		stdGridCust.addEventable(p1);
		gridCust.addEventable(p1);
		axeCust.addEventable(p2);
		stdGridCust.addEventable(p2);
		gridCust.addEventable(p2);
		list.setVisible(false);
		return list;
	}


	/** Creates the toolbar containing the widgets that customises axes. */
	protected WidgetMiniToolbar composeAxesPropertiesToolbar(final ShapeAxesCustomiser cust, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(LResources.AXES_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText(LangTool.INSTANCE.getStringActions("PropBuilder.3")); //$NON-NLS-1$
		final MPanel panel 	  = new MPanel(false, true);
		final MPanel ticksPanel = new MPanel(false, true);

		ticksPanel.setBorder(new CompoundBorder(new TitledBorder(null, LangTool.INSTANCE.getString18("ParametersAxeFrame.17"), //$NON-NLS-1$
				  TitledBorder.LEFT, TitledBorder.TOP), new EmptyBorder(0,0,0,0)));

		panel.add(cust.getShapeAxes());

		UIBuilder.addCombobox(ticksPanel, cust.getShapeTicks());
//		UIBuilder.addSpinner(ticksPanel, cust.getTicksSizeS(), 70);
		UIBuilder.addCombobox(ticksPanel, cust.getShowTicks());

		list.addComponent(panel);
		list.addComponent(ticksPanel);
		list.addSeparator();

		mapContainers.put(cust.getShapeAxes(), list);
		mapContainers.put(cust.getShapeTicks(), list);
//		mapContainers.put(cust.getTicksSizeS(), list);
		mapContainers.put(cust.getShowTicks(), list);
		list.setVisible(false);
		cust.addEventable(panel);
		cust.addEventable(ticksPanel);
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


	protected JComponent composeRotationToolbar(final ShapeRotationCustomiser ins, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(LResources.ROTATE_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
        list.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.2")); //$NON-NLS-1$

        UIBuilder.addSpinner(list, ins.getRotationField(), 65);
        list.addComponent(ins.getRotate90Button());
        list.addComponent(ins.getRotate180Button());
        list.addComponent(ins.getRotate270Button());
        list.addSeparator();

        mapContainers.put(ins.getRotationField(), list);
        mapContainers.put(ins.getRotate90Button(), list);
        mapContainers.put(ins.getRotate180Button(), list);
        mapContainers.put(ins.getRotate270Button(), list);

        ins.addEventable(list.getToolbar());
        list.setVisible(false);
		return list;
	}
}
