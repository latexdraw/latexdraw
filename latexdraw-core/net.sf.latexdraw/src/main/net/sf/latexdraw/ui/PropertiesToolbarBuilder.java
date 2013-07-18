package net.sf.latexdraw.ui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.instruments.*;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MPanel;
import org.malai.swing.widget.MProgressBar;

/**
 * The composer that creates the properties tool bar of the application.<br>
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
 * 12/08/11<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class PropertiesToolbarBuilder extends UIComposer<MPanel> {
	/** The main frame of the application. */
	protected LFrame frame;

	/** The hash map used to map a widget to its container. */
	protected Map<Component, WidgetMiniToolbar> mapContainers;


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
		widget.add(composeMirrorShapes(metaShapeCustomiser.getShapeTransformer(), canvas));
		widget.add(composeAlignShapes(metaShapeCustomiser.getShapeTransformer(), canvas));
		widget.add(composeDistributeShapes(metaShapeCustomiser.getShapeTransformer(), canvas));
		widget.add(composeDimPosPropertiesToolbar(metaShapeCustomiser.getDimPosCustomiser(), canvas));
		widget.add(composeBorderPropertiesPanel(metaShapeCustomiser.getBorderCustomiser(), canvas));
		if(progressBar!=null) progressBar.addToProgressBar(5);
		widget.add(composeDoubleBorderPropertiesPanel(metaShapeCustomiser.getDoubleBorderCustomiser(), canvas));
		widget.add(composeShadowPropertiesPanel(metaShapeCustomiser.getShadowCustomiser(), canvas));
		widget.add(composeFillingPanel(metaShapeCustomiser.getFillingCustomiser(), canvas));
		if(progressBar!=null) progressBar.addToProgressBar(5);
		widget.add(composeArrowToolbar(metaShapeCustomiser.getArrowCustomiser(), canvas));
		widget.add(composeDotToolbar(metaShapeCustomiser.getDotCustomiser(), metaShapeCustomiser.getBorderCustomiser(), canvas));
		widget.add(composeTextPositionToolbar(metaShapeCustomiser.getTextCustomiser(), canvas));
		widget.add(composeTextPropertiesToolbar(metaShapeCustomiser.getTextCustomiser(), canvas));
		widget.add(composeArcPropertiesWidgets(metaShapeCustomiser.getArcCustomiser(), canvas));
		widget.add(composeStandardGridPropertiesToolbar(metaShapeCustomiser.getStandardGridCustomiser(), canvas));
		widget.add(composeGridPropertiesToolbar(metaShapeCustomiser.getGridCustomiser(), canvas));
		widget.add(composeAxesPropertiesToolbar(metaShapeCustomiser.getAxesCustomiser(), canvas));
		widget.add(composeGridLabelsPropertiesToolbar(metaShapeCustomiser.getAxesCustomiser(), metaShapeCustomiser.getGridCustomiser(),
					metaShapeCustomiser.getStandardGridCustomiser(), canvas));
		widget.add(composeFreeHandPropertiesToolbar(metaShapeCustomiser.getFreeHandCustomiser(), canvas));
		if(progressBar!=null) progressBar.addToProgressBar(5);
	}


	// Creates the tool bar for place shapes.
	protected WidgetMiniToolbar composeShapePositionerWidgets(final ShapePositioner ins, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.FOREGROUND_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
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
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.DIST_VERT_EQUAL_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
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
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.ALIGN_LEFT_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
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
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.MIRROR_H_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.6"));
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
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.FREE_HAND_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the properties of freehand drawings.");

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
																	final ShapeStandardGridCustomiser stdGridCust, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.GRID_LABELS, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the properties of grids' labels.");

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
		UIBuilder.addSpinner(p1, stdGridCust.getLabelsSizeS(), 50);
		UIBuilder.addCombobox(p1, axeCust.getShowLabels());
		p1.add(gridCust.getColourLabels());
		p1.add(axeCust.getShowOrigin());
		p1.add(stdGridCust.getLabelsXInvertedCB());
		p1.add(stdGridCust.getLabelsYInvertedCB());
		UIBuilder.addSpinner(p2, axeCust.getIncrLabelX(), 50);
		UIBuilder.addSpinner(p2, axeCust.getIncrLabelY(), 50);
		UIBuilder.addSpinner(p2, axeCust.getDistLabelsX(), 60);
		UIBuilder.addSpinner(p2, axeCust.getDistLabelsY(), 60);
		p3.add(p1);
		p3.add(p2);
		list.addComponent(p3);
		list.addSeparator();

		mapContainers.put(axeCust.getShowLabels(), list);
		mapContainers.put(gridCust.getColourLabels(), list);
		mapContainers.put(axeCust.getShowOrigin(), list);
		mapContainers.put(axeCust.getIncrLabelX(), list);
		mapContainers.put(axeCust.getIncrLabelY(), list);
		mapContainers.put(axeCust.getDistLabelsX(), list);
		mapContainers.put(axeCust.getDistLabelsY(), list);
		mapContainers.put(stdGridCust.getLabelsSizeS(), list);
		mapContainers.put(stdGridCust.getLabelsXInvertedCB(), list);
		mapContainers.put(stdGridCust.getLabelsYInvertedCB(), list);

		axeCust.addEventable(list.getToolbar());
		stdGridCust.addEventable(list.getToolbar());
		gridCust.addEventable(list.getToolbar());
		list.setVisible(false);
		return list;
	}


	/** Creates the toolbar containing the widgets that customises axes. */
	protected WidgetMiniToolbar composeAxesPropertiesToolbar(final ShapeAxesCustomiser cust, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.AXES_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the properties of axes.");
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


	protected WidgetMiniToolbar composeGridPropertiesToolbar(final ShapeGridCustomiser cust, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.SUBGRID_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the properties of grids.");

		UIBuilder.addSpinner(list, cust.getGridWidth(), 60);
		UIBuilder.addSpinner(list, cust.getSubGridWidth(), 60);
		list.addComponent(cust.getColourSubGrid());
		UIBuilder.addSpinner(list, cust.getGridDots(), 50);
		UIBuilder.addSpinner(list, cust.getSubGridDots(), 50);
		UIBuilder.addSpinner(list, cust.getSubGridDiv(), 50);
		list.addSeparator();

		mapContainers.put(cust.getColourSubGrid(), list);
		mapContainers.put(cust.getGridWidth(), list);
		mapContainers.put(cust.getSubGridWidth(), list);
		mapContainers.put(cust.getGridDots(), list);
		mapContainers.put(cust.getSubGridDots(), list);
		mapContainers.put(cust.getSubGridDiv(), list);
		cust.addEventable(list.getToolbar());
		list.setVisible(false);
		return list;
	}


	protected WidgetMiniToolbar composeStandardGridPropertiesToolbar(final ShapeStandardGridCustomiser cust, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.GRID_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the properties of grids and axes.");

		UIBuilder.addSpinner(list, cust.getxStartS(), 50);
		UIBuilder.addSpinner(list, cust.getyStartS(), 50);
		UIBuilder.addSpinner(list, cust.getxEndS(), 50);
		UIBuilder.addSpinner(list, cust.getyEndS(), 50);
		UIBuilder.addSpinner(list, cust.getxOriginS(), 50);
		UIBuilder.addSpinner(list, cust.getyOriginS(), 50);
		list.addSeparator();

		mapContainers.put(cust.getxStartS(), list);
		mapContainers.put(cust.getyStartS(), list);
		mapContainers.put(cust.getxEndS(), list);
		mapContainers.put(cust.getyEndS(), list);
		mapContainers.put(cust.getxOriginS(), list);
		mapContainers.put(cust.getyOriginS(), list);
		list.setVisible(false);
		cust.addEventable(list.getToolbar());
		return list;
	}


	protected WidgetMiniToolbar composeDimPosPropertiesToolbar(final ShapeCoordDimCustomiser cust, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.DIM_POS_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the dimensions and the position.");

		UIBuilder.addSpinner(list, cust.getTlxS(), 90);
		UIBuilder.addSpinner(list, cust.getTlyS(), 90);
		list.addSeparator();

		mapContainers.put(cust.getTlxS(), list);
		mapContainers.put(cust.getTlyS(), list);
		list.setVisible(false);
		cust.addEventable(list.getToolbar());
		return list;
	}


	protected WidgetMiniToolbar composeTextPropertiesToolbar(final TextCustomiser textCustomiser, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.TEXT_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the properties of the text.");

		list.addComponent(textCustomiser.getPackagesLabel());
		list.addComponent(textCustomiser.getPackagesField().getScrollpane());
		list.addSeparator();

		mapContainers.put(textCustomiser.getPackagesLabel(), list);
		mapContainers.put(textCustomiser.getPackagesField().getScrollpane(), list);
		list.setVisible(false);
		textCustomiser.addEventable(textCustomiser.getPackagesField());
		return list;
	}


	protected WidgetMiniToolbar composeTextPositionToolbar(final TextCustomiser textCustomiser, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.TEXTPOS_BL, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the position of the text.");
		list.addComponent(textCustomiser.getBlButton());
		list.addComponent(textCustomiser.getBButton());
		list.addComponent(textCustomiser.getBrButton());
		list.addComponent(textCustomiser.getTlButton());
		list.addComponent(textCustomiser.getTButton());
		list.addComponent(textCustomiser.getTrButton());
		list.addComponent(textCustomiser.getCentreButton());
		list.addComponent(textCustomiser.getLButton());
		list.addComponent(textCustomiser.getRButton());

		mapContainers.put(textCustomiser.getBlButton(), list);
		mapContainers.put(textCustomiser.getBButton(), list);
		mapContainers.put(textCustomiser.getBrButton(), list);
		mapContainers.put(textCustomiser.getTlButton(), list);
		mapContainers.put(textCustomiser.getTButton(), list);
		mapContainers.put(textCustomiser.getTrButton(), list);
		mapContainers.put(textCustomiser.getCentreButton(), list);
		mapContainers.put(textCustomiser.getLButton(), list);
		mapContainers.put(textCustomiser.getRButton(), list);
		list.addSeparator();
		list.setVisible(false);
		textCustomiser.addEventable(list.getToolbar());
		return list;
	}


	protected JComponent composeArcPropertiesWidgets(final ShapeArcCustomiser ins, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.ARC_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Customises the arcs.");

		list.addComponent(ins.getArcB());
		list.addComponent(ins.getChordB());
		list.addComponent(ins.getWedgeB());
		UIBuilder.addSpinner(list, ins.getStartAngleS(), 70);
		UIBuilder.addSpinner(list, ins.getEndAngleS(), 70);
		list.addSeparator();

		mapContainers.put(ins.getArcB(), list);
		mapContainers.put(ins.getChordB(), list);
		mapContainers.put(ins.getWedgeB(), list);
		mapContainers.put(ins.getStartAngleS(), list);
		mapContainers.put(ins.getEndAngleS(), list);

        ins.addEventable(list.getToolbar());
        list.setVisible(false);
		return list;
	}


	protected JComponent composeDotToolbar(final ShapeDotCustomiser ins, final ShapeBorderCustomiser sbc, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.DOT_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Customises the dots.");

		list.addComponent(sbc.getShowPoints());

		list.addComponent(ins.getDotCB());
		UIBuilder.addSpinner(list, ins.getDotSizeField(), 70);
		list.addComponent(ins.getFillingB());
		list.addSeparator();

		mapContainers.put(sbc.getShowPoints(), list);
		mapContainers.put(ins.getDotCB(), list);
		mapContainers.put(ins.getDotSizeField(), list);
		mapContainers.put(ins.getFillingB(), list);

        ins.addEventable(list.getToolbar());
        sbc.addEventable(list.getToolbar());
        list.setVisible(false);
		return list;
	}


	protected JComponent composeArrowToolbar(final ShapeArrowCustomiser ins, final LCanvas canvas) {
		final int size = 70;
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.ARROW_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Customises the arrows.");

		list.addComponent(ins.getArrowLeftCB());
		list.addComponent(ins.getArrowRightCB());
		UIBuilder.addSpinner(list, ins.getArrowInset(), size);
		UIBuilder.addSpinner(list, ins.getArrowLength(), size);
		UIBuilder.addSpinner(list, ins.getArrowSizeNum(), size);
		UIBuilder.addSpinner(list, ins.getArrowSizeDim(), size);
		UIBuilder.addSpinner(list, ins.getBracketNum(), size);
		UIBuilder.addSpinner(list, ins.getTbarsizeNum(), size);
		UIBuilder.addSpinner(list, ins.getTbarsizeDim(), size);
		UIBuilder.addSpinner(list, ins.getDotSizeNum(), size);
		UIBuilder.addSpinner(list, ins.getDotSizeDim(), size);
		UIBuilder.addSpinner(list, ins.getRbracketNum(), size);

		list.addSeparator();

		mapContainers.put(ins.getArrowLeftCB(), list);
		mapContainers.put(ins.getArrowRightCB(), list);
		mapContainers.put(ins.getArrowInset(), list);
		mapContainers.put(ins.getArrowLength(), list);
		mapContainers.put(ins.getArrowSizeNum(), list);
		mapContainers.put(ins.getArrowSizeDim(), list);
		mapContainers.put(ins.getTbarsizeNum(), list);
		mapContainers.put(ins.getTbarsizeDim(), list);
		mapContainers.put(ins.getBracketNum(), list);
		mapContainers.put(ins.getRbracketNum(), list);
		mapContainers.put(ins.getDotSizeNum(), list);
		mapContainers.put(ins.getDotSizeDim(), list);

        ins.addEventable(list.getToolbar());
        list.setVisible(false);
		return list;
	}



	protected JComponent composeRotationToolbar(final ShapeRotationCustomiser ins, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.ROTATE_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
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


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes filling properties.
	 * @param fillingCustomiser The instrument that contains the widgets.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent composeFillingPanel(final ShapeFillingCustomiser fillingCustomiser, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.FILLING_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the filling properties.");
		list.addComponent(fillingCustomiser.getFillStyleCB());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		list.addComponent(fillingCustomiser.getFillColButton());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		list.addComponent(fillingCustomiser.getHatchColButton());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		UIBuilder.addSpinner(list, fillingCustomiser.getHatchAngleField(), 65);
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		UIBuilder.addSpinner(list, fillingCustomiser.getHatchWidthField(), 60);
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		UIBuilder.addSpinner(list, fillingCustomiser.getHatchSepField(), 65);
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		list.addComponent(fillingCustomiser.getGradStartColButton());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		list.addComponent(fillingCustomiser.getGradEndColButton());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		UIBuilder.addSpinner(list, fillingCustomiser.getGradAngleField(), 60);
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		UIBuilder.addSpinner(list, fillingCustomiser.getGradMidPtField(), 70);
		list.addSeparator();

		mapContainers.put(fillingCustomiser.getFillStyleCB(), list);
		mapContainers.put(fillingCustomiser.getFillColButton(), list);
		mapContainers.put(fillingCustomiser.getHatchColButton(), list);
		mapContainers.put(fillingCustomiser.getHatchAngleField(), list);
		mapContainers.put(fillingCustomiser.getHatchWidthField(), list);
		mapContainers.put(fillingCustomiser.getHatchSepField(), list);
		mapContainers.put(fillingCustomiser.getGradStartColButton(), list);
		mapContainers.put(fillingCustomiser.getGradEndColButton(), list);
		mapContainers.put(fillingCustomiser.getGradAngleField(), list);
		mapContainers.put(fillingCustomiser.getGradMidPtField(), list);

		fillingCustomiser.addEventable(list.getToolbar());
		list.setVisible(false);
		return list;
	}


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes double border properties.
	 * @param shadowCustomiser The instrument that contains the widgets.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent composeShadowPropertiesPanel(final ShapeShadowCustomiser shadowCustomiser, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.SHADOW_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the shadow properties.");
		list.addComponent(shadowCustomiser.getShadowCB());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		list.addComponent(shadowCustomiser.getShadowColB());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		UIBuilder.addSpinner(list, shadowCustomiser.getShadowSizeField(), 75);
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		UIBuilder.addSpinner(list, shadowCustomiser.getShadowAngleField(), 75);
		list.addSeparator();

		mapContainers.put(shadowCustomiser.getShadowCB(), list);
		mapContainers.put(shadowCustomiser.getShadowColB(), list);
		mapContainers.put(shadowCustomiser.getShadowSizeField(), list);
		mapContainers.put(shadowCustomiser.getShadowAngleField(), list);

		shadowCustomiser.addEventable(list.getToolbar());
		list.setVisible(false);
		return list;
	}


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes double border properties.
	 * @param dbleBorderCustomiser The instrument that contains the widgets.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent composeDoubleBorderPropertiesPanel(final ShapeDoubleBorderCustomiser dbleBorderCustomiser, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.DOUBLE_BORDER_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the double border properties.");
		list.addComponent(dbleBorderCustomiser.getDbleBoundCB());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		list.addComponent(dbleBorderCustomiser.getDbleBoundColB());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		UIBuilder.addSpinner(list, dbleBorderCustomiser.getDbleSepField(), 55);
		list.addSeparator();

		mapContainers.put(dbleBorderCustomiser.getDbleBoundCB(), list);
		mapContainers.put(dbleBorderCustomiser.getDbleBoundColB(), list);
		mapContainers.put(dbleBorderCustomiser.getDbleSepField(), list);

		dbleBorderCustomiser.addEventable(list.getToolbar());
		list.setVisible(false);
		return list;
	}


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes border properties.
	 * @param borderCustomiser The instrument that contains the widgets.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent composeBorderPropertiesPanel(final ShapeBorderCustomiser borderCustomiser, final LCanvas canvas) {
		final WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.BORDER_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the border properties.");

		UIBuilder.addSpinner(list, borderCustomiser.getThicknessField(), 65);
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		list.addComponent(borderCustomiser.getLineColButton());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		list.addComponent(borderCustomiser.getLineCB());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		list.addComponent(borderCustomiser.getBordersPosCB());
		list.addComponent(Box.createHorizontalStrut(UIBuilder.SEPARATION_WIDTH));
		UIBuilder.addSpinner(list, borderCustomiser.getFrameArcField(), 65);
		list.addSeparator();

		mapContainers.put(borderCustomiser.getThicknessField(), list);
		mapContainers.put(borderCustomiser.getLineColButton(), list);
		mapContainers.put(borderCustomiser.getLineCB(), list);
		mapContainers.put(borderCustomiser.getBordersPosCB(), list);
		mapContainers.put(borderCustomiser.getFrameArcField(), list);

		borderCustomiser.addEventable(list.getToolbar());
		list.setVisible(false);
		return list;
	}
}
