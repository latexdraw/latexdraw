package net.sf.latexdraw.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.ShapeArcCustomiser;
import net.sf.latexdraw.instruments.ShapeArrowCustomiser;
import net.sf.latexdraw.instruments.ShapeAxesCustomiser;
import net.sf.latexdraw.instruments.ShapeBorderCustomiser;
import net.sf.latexdraw.instruments.ShapeCoordDimCustomiser;
import net.sf.latexdraw.instruments.ShapeDotCustomiser;
import net.sf.latexdraw.instruments.ShapeDoubleBorderCustomiser;
import net.sf.latexdraw.instruments.ShapeFillingCustomiser;
import net.sf.latexdraw.instruments.ShapeGridCustomiser;
import net.sf.latexdraw.instruments.ShapeGrouper;
import net.sf.latexdraw.instruments.ShapeRotationCustomiser;
import net.sf.latexdraw.instruments.ShapeShadowCustomiser;
import net.sf.latexdraw.instruments.TextCustomiser;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.ui.UIComposer;
import org.malai.widget.MPanel;
import org.malai.widget.MProgressBar;
import org.malai.widget.MSpinner;

/**
 * The composer that creates the properties tool bar of the application.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
	/** The max height of the textfield widget. */
	protected static final int HEIGHT_TEXTFIELD = 30;

	/** The space added between widgets. */
	protected static final int SEPARATION_WIDTH = 5;

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
		mapContainers	= new IdentityHashMap<Component, WidgetMiniToolbar>();
	}


	@Override
	public void compose(final MProgressBar progressBar) {
		final LCanvas canvas = frame.getCanvas();
		MetaShapeCustomiser metaShapeCustomiser = frame.getMetaShapeCustomiser();

		widget = new MPanel(false, true);
		widget.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Creation of the widgets layout of the shape properties instruments.
		composeJoinShapesWidgets(metaShapeCustomiser.getShapeGrouper());
		widget.add(composeRotationToolbar(metaShapeCustomiser.getRotationCustomiser(), canvas));
		widget.add(composeDimPosPropertiesToolbar(metaShapeCustomiser.getDimPosCustomiser(), canvas));
		widget.add(composeBorderPropertiesPanel(metaShapeCustomiser.getBorderCustomiser(), canvas));
		if(progressBar!=null) progressBar.addToProgressBar(5);
		widget.add(composeDoubleBorderPropertiesPanel(metaShapeCustomiser.getDoubleBorderCustomiser(), canvas));
		widget.add(composeShadowPropertiesPanel(metaShapeCustomiser.getShadowCustomiser(), canvas));
		widget.add(composeFillingPanel(metaShapeCustomiser.getFillingCustomiser(), canvas));
		if(progressBar!=null) progressBar.addToProgressBar(5);
		widget.add(composeArrowToolbar(metaShapeCustomiser.getArrowCustomiser(), canvas));
		widget.add(composeDotToolbar(metaShapeCustomiser.getDotCustomiser(), canvas));
		widget.add(composeTextPositionToolbar(metaShapeCustomiser.getTextCustomiser(), canvas));
		widget.add(composeTextPropertiesToolbar(metaShapeCustomiser.getTextCustomiser(), canvas));
		widget.add(composeArcPropertiesWidgets(metaShapeCustomiser.getArcCustomiser(), canvas));
		widget.add(composeGridPropertiesToolbar(metaShapeCustomiser.getGridCustomiser(), canvas));
		widget.add(composeAxesPropertiesToolbar(metaShapeCustomiser.getAxesCustomiser(), canvas));
		if(progressBar!=null) progressBar.addToProgressBar(5);
	}


	// Composition of the widgets that joins/separates shapes.
	protected void composeJoinShapesWidgets(final ShapeGrouper grouper) {
		widget.add(grouper.getGroupB());
		widget.add(grouper.getSepB());
		grouper.addEventable(widget);
	}


	protected WidgetMiniToolbar composeAxesPropertiesToolbar(final ShapeAxesCustomiser cust, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.AXES_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the properties of axes.");
		MPanel panel 	  = new MPanel(false, true);
		MPanel ticksPanel = new MPanel(false, true);

		ticksPanel.setBorder(new CompoundBorder(new TitledBorder(null, LangTool.INSTANCE.getString18("ParametersAxeFrame.17"), //$NON-NLS-1$
				  TitledBorder.LEFT, TitledBorder.TOP), new EmptyBorder(0,0,0,0)));

		panel.add(cust.getShapeAxes());
		ticksPanel.add(cust.getShapeTicks());

		list.addComponent(panel);
		list.addComponent(ticksPanel);
		list.addSeparator();

		mapContainers.put(cust.getShapeAxes(), list);

		cust.addEventable(panel);
		cust.addEventable(ticksPanel);
		return list;
	}



	protected WidgetMiniToolbar composeGridPropertiesToolbar(final ShapeGridCustomiser cust, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.GRID_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the properties of grids and axes.");

		addSpinner(list, cust.getxStartS(), true, 50);
		addSpinner(list, cust.getyStartS(), true, 50);
		addSpinner(list, cust.getxEndS(), true, 50);
		addSpinner(list, cust.getyEndS(), true, 50);
		addSpinner(list, cust.getxOriginS(), true, 50);
		addSpinner(list, cust.getyOriginS(), true, 50);
		addSpinner(list, cust.getLabelsSizeS(), true, 50);
		list.addComponent(cust.getLabelsXInvertedCB());
		list.addComponent(cust.getLabelsYInvertedCB());
		list.addSeparator();

		mapContainers.put(cust.getxStartS(), list);
		mapContainers.put(cust.getyStartS(), list);
		mapContainers.put(cust.getxEndS(), list);
		mapContainers.put(cust.getyEndS(), list);
		mapContainers.put(cust.getxOriginS(), list);
		mapContainers.put(cust.getyOriginS(), list);
		mapContainers.put(cust.getLabelsSizeS(), list);
		mapContainers.put(cust.getLabelsXInvertedCB(), list);
		mapContainers.put(cust.getLabelsYInvertedCB(), list);

		cust.addEventable(list.getToolbar());
		return list;
	}


	protected WidgetMiniToolbar composeDimPosPropertiesToolbar(final ShapeCoordDimCustomiser cust, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.DIM_POS_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the dimensions and the position.");

		addSpinner(list, cust.getTlxS(), true, 90);
		addSpinner(list, cust.getTlyS(), true, 90);
		list.addSeparator();

		mapContainers.put(cust.getTlxS(), list);
		mapContainers.put(cust.getTlyS(), list);

		cust.addEventable(list.getToolbar());
		return list;
	}


	protected WidgetMiniToolbar composeTextPropertiesToolbar(final TextCustomiser textCustomiser, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.TEXT_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the properties of the text.");

		list.addComponent(textCustomiser.getPackagesLabel());
		list.addComponent(textCustomiser.getPackagesField().getScrollpane());
		list.addSeparator();

		mapContainers.put(textCustomiser.getPackagesLabel(), list);
		mapContainers.put(textCustomiser.getPackagesField().getScrollpane(), list);

		textCustomiser.addEventable(textCustomiser.getPackagesField());
		return list;
	}


	protected WidgetMiniToolbar composeTextPositionToolbar(final TextCustomiser textCustomiser, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.TEXTPOS_BL, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the position of the text.");
		list.addComponent(textCustomiser.getBlButton());
		list.addComponent(textCustomiser.getBButton());
		list.addComponent(textCustomiser.getBrButton());
		list.addComponent(textCustomiser.getTlButton());
		list.addComponent(textCustomiser.getTButton());
		list.addComponent(textCustomiser.getTrButton());

		mapContainers.put(textCustomiser.getBlButton(), list);
		mapContainers.put(textCustomiser.getBButton(), list);
		mapContainers.put(textCustomiser.getBrButton(), list);
		mapContainers.put(textCustomiser.getTlButton(), list);
		mapContainers.put(textCustomiser.getTButton(), list);
		mapContainers.put(textCustomiser.getTrButton(), list);

		list.addSeparator();
		textCustomiser.addEventable(list.getToolbar());
		return list;
	}


	protected void addSpinner(final WidgetMiniToolbar list, final MSpinner spinner, final boolean label, final int width) {
		spinner.setPreferredSize(new Dimension(width, HEIGHT_TEXTFIELD));
		if(label)
			list.addComponent(spinner.getLabel());
		list.addComponent(spinner);
	}


	protected JComponent composeArcPropertiesWidgets(final ShapeArcCustomiser ins, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.ARC_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Customises the arcs.");

		list.addComponent(ins.getArcB());
		list.addComponent(ins.getChordB());
		list.addComponent(ins.getWedgeB());
		addSpinner(list, ins.getStartAngleS(), true, 70);
		addSpinner(list, ins.getEndAngleS(), true, 70);
		list.addSeparator();

		mapContainers.put(ins.getArcB(), list);
		mapContainers.put(ins.getChordB(), list);
		mapContainers.put(ins.getWedgeB(), list);
		mapContainers.put(ins.getStartAngleS(), list);
		mapContainers.put(ins.getEndAngleS(), list);

        ins.addEventable(list.getToolbar());

		return list;
	}


	protected JComponent composeDotToolbar(final ShapeDotCustomiser ins, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.DOT_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Customises the dots.");

		list.addComponent(ins.getDotCB());
		addSpinner(list, ins.getDotSizeField(), false, 70);
		list.addComponent(ins.getFillingB());
		list.addSeparator();

		mapContainers.put(ins.getDotCB(), list);
		mapContainers.put(ins.getDotSizeField(), list);
		mapContainers.put(ins.getFillingB(), list);

        ins.addEventable(list.getToolbar());

		return list;
	}


	protected JComponent composeArrowToolbar(final ShapeArrowCustomiser ins, final LCanvas canvas) {
		final int size = 70;
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.ARROW_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Customises the arrows.");

		list.addComponent(ins.getArrowLeftCB());
		list.addComponent(ins.getArrowRightCB());
		addSpinner(list, ins.getArrowInset(), true, size);
		addSpinner(list, ins.getArrowLength(), true, size);
		addSpinner(list, ins.getArrowSizeNum(), true, size);
		addSpinner(list, ins.getArrowSizeDim(), true, size);
		addSpinner(list, ins.getBracketNum(), true, size);
		addSpinner(list, ins.getTbarsizeNum(), true, size);
		addSpinner(list, ins.getTbarsizeDim(), true, size);
		addSpinner(list, ins.getDotSizeNum(), true, size);
		addSpinner(list, ins.getDotSizeDim(), true, size);
		addSpinner(list, ins.getRbracketNum(), true, size);

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

		return list;
	}



	protected JComponent composeRotationToolbar(final ShapeRotationCustomiser ins, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.ROTATE_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
        list.setToolTipText(LangTool.INSTANCE.getString18("LaTeXDrawFrame.2")); //$NON-NLS-1$

        addSpinner(list, ins.getRotationField(), true, 65);
        list.addComponent(ins.getRotate90Button());
        list.addComponent(ins.getRotate180Button());
        list.addComponent(ins.getRotate270Button());
        list.addSeparator();

        mapContainers.put(ins.getRotationField(), list);
        mapContainers.put(ins.getRotate90Button(), list);
        mapContainers.put(ins.getRotate180Button(), list);
        mapContainers.put(ins.getRotate270Button(), list);

        ins.addEventable(list.getToolbar());

		return list;
	}


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes filling properties.
	 * @param fillingCustomiser The instrument that contains the widgets.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent composeFillingPanel(final ShapeFillingCustomiser fillingCustomiser, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.FILLING_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the filling properties.");
		list.addComponent(fillingCustomiser.getFillStyleCB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(fillingCustomiser.getFillColButton());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(fillingCustomiser.getHatchColButton());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, fillingCustomiser.getHatchAngleField(), true, 65);
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, fillingCustomiser.getHatchWidthField(), true, 60);
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, fillingCustomiser.getHatchSepField(), true, 65);
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(fillingCustomiser.getGradStartColButton());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(fillingCustomiser.getGradEndColButton());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, fillingCustomiser.getGradAngleField(), true, 60);
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, fillingCustomiser.getGradMidPtField(), true, 70);
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

		return list;
	}


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes double border properties.
	 * @param shadowCustomiser The instrument that contains the widgets.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent composeShadowPropertiesPanel(final ShapeShadowCustomiser shadowCustomiser, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.SHADOW_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the shadow properties.");
		list.addComponent(shadowCustomiser.getShadowCB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(shadowCustomiser.getShadowColB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, shadowCustomiser.getShadowSizeField(), true, 75);
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, shadowCustomiser.getShadowAngleField(), true, 75);
		list.addSeparator();

		mapContainers.put(shadowCustomiser.getShadowCB(), list);
		mapContainers.put(shadowCustomiser.getShadowColB(), list);
		mapContainers.put(shadowCustomiser.getShadowSizeField(), list);
		mapContainers.put(shadowCustomiser.getShadowAngleField(), list);

		shadowCustomiser.addEventable(list.getToolbar());

		return list;
	}


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes double border properties.
	 * @param dbleBorderCustomiser The instrument that contains the widgets.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent composeDoubleBorderPropertiesPanel(final ShapeDoubleBorderCustomiser dbleBorderCustomiser, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.DOUBLE_BORDER_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the double border properties.");
		list.addComponent(dbleBorderCustomiser.getDbleBoundCB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(dbleBorderCustomiser.getDbleBoundColB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, dbleBorderCustomiser.getDbleSepField(), true, 55);
		list.addSeparator();

		mapContainers.put(dbleBorderCustomiser.getDbleBoundCB(), list);
		mapContainers.put(dbleBorderCustomiser.getDbleBoundColB(), list);
		mapContainers.put(dbleBorderCustomiser.getDbleSepField(), list);

		dbleBorderCustomiser.addEventable(list.getToolbar());

		return list;
	}


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes border properties.
	 * @param borderCustomiser The instrument that contains the widgets.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent composeBorderPropertiesPanel(final ShapeBorderCustomiser borderCustomiser, final LCanvas canvas) {
		WidgetMiniToolbar list = new WidgetMiniToolbar(frame, LResources.BORDER_ICON, WidgetMiniToolbar.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the border properties.");

		addSpinner(list, borderCustomiser.getThicknessField(), true, 65);
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(borderCustomiser.getLineColButton());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(borderCustomiser.getLineCB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(borderCustomiser.getBordersPosCB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, borderCustomiser.getFrameArcField(), true, 65);
		list.addSeparator();

		mapContainers.put(borderCustomiser.getThicknessField(), list);
		mapContainers.put(borderCustomiser.getLineColButton(), list);
		mapContainers.put(borderCustomiser.getLineCB(), list);
		mapContainers.put(borderCustomiser.getBordersPosCB(), list);
		mapContainers.put(borderCustomiser.getFrameArcField(), list);

		borderCustomiser.addEventable(list.getToolbar());

		return list;
	}
}
