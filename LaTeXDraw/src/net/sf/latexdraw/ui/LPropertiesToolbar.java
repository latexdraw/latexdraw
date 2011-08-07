package net.sf.latexdraw.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JComponent;

import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.ShapeArrowCustomiser;
import net.sf.latexdraw.instruments.ShapeBorderCustomiser;
import net.sf.latexdraw.instruments.ShapeDoubleBorderCustomiser;
import net.sf.latexdraw.instruments.ShapeFillingCustomiser;
import net.sf.latexdraw.instruments.ShapeRotationCustomiser;
import net.sf.latexdraw.instruments.ShapeShadowCustomiser;
import net.sf.latexdraw.instruments.TextCustomiser;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;
import fr.eseo.malai.widget.MPanel;
import fr.eseo.malai.widget.MSpinner;

/**
 * Defines a tool bar that contains fields that manipulate the shapes properties.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/16/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class LPropertiesToolbar extends MPanel {
	private static final long serialVersionUID = 1L;

	/** The max height of the textfield widget. */
	protected static final int HEIGHT_TEXTFIELD = 30;

	/** The space added between widgets. */
	protected static final int SEPARATION_WIDTH = 5;


	/**
	 * Creates the bottom panel that contains a set of widgets to modify shapes.
	 * @param metaShapeCustomiser The meta instrument that contains the instruments containing the
	 * widgets to put into the panel.
	 * @param frame The frame that will contains the toolbar.
	 * @throws IllegalArgumentException If the given instrument is null.
	 * @since 3.0
	 */
	public LPropertiesToolbar(final MetaShapeCustomiser metaShapeCustomiser, final LFrame frame) {
		super(false, true);

		if(metaShapeCustomiser==null || frame==null)
			throw new IllegalArgumentException();

		createToolbar(metaShapeCustomiser, frame);
	}


	/**
	 * Initialises the toolbar.
	 * @param metaShapeCustomiser The meta-instrument that contains all the instrument containing the widgets that
	 * will compose the toolbar.
	 * @param frame The main frame of the system.
	 * @since 3.0
	 */
	protected void createToolbar(final MetaShapeCustomiser metaShapeCustomiser, final LFrame frame) {
		setLayout(new FlowLayout(FlowLayout.LEFT));

		final LCanvas canvas = frame.getCanvas();
		// this panel is now the component of the meta instrument.
		metaShapeCustomiser.setWidgetContainer(this);
		// Creation of the widgets layout of the shape properties instruments.
		add(createRotationToolbar(metaShapeCustomiser.getRotationCustomiser(), frame, canvas));
		add(createBorderPropertiesPanel(metaShapeCustomiser.getBorderCustomiser(), frame, canvas));
		add(createDoubleBorderPropertiesPanel(metaShapeCustomiser.getDoubleBorderCustomiser(), frame, canvas));
		add(createShadowPropertiesPanel(metaShapeCustomiser.getShadowCustomiser(), frame, canvas));
		add(createFillingPanel(metaShapeCustomiser.getFillingCustomiser(), frame, canvas));
		add(createArrowToolbar(metaShapeCustomiser.getArrowCustomiser(), frame, canvas));
		addTextWidgets(metaShapeCustomiser.getTextCustomiser(), frame, canvas);
	}


	protected void addTextWidgets(final TextCustomiser textCustomiser, final LFrame frame, final LCanvas canvas) {
		ListToggleButton list = new ListToggleButton(frame, LResources.TEXTPOS_BL, ListToggleButton.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the position of the text.");
		list.addComponent(textCustomiser.getBlButton());
		list.addComponent(textCustomiser.getBButton());
		list.addComponent(textCustomiser.getBrButton());
		list.addComponent(textCustomiser.getTlButton());
		list.addComponent(textCustomiser.getTButton());
		list.addComponent(textCustomiser.getTrButton());
		list.addSeparator();
		textCustomiser.setWidgetContainer(list);
		textCustomiser.addEventable(list.getToolbar());
		add(list);
	}


	protected void addSpinner(final ListToggleButton list, final MSpinner spinner, final boolean label, final int width) {
		spinner.setPreferredSize(new Dimension(width, HEIGHT_TEXTFIELD));
		if(label)
			list.addComponent(spinner.getLabel());
		list.addComponent(spinner);
	}



	protected JComponent createArrowToolbar(final ShapeArrowCustomiser ins, final LFrame frame, final LCanvas canvas) {
		ListToggleButton list = new ListToggleButton(frame, LResources.ARROW_ICON, ListToggleButton.LOCATION_NORTH, canvas);
		list.setToolTipText("Customises the arrows.");

		list.addComponent(ins.getArrowLeftCB());
		list.addComponent(ins.getArrowRightCB());
		list.addSeparator();

        ins.addEventable(list.getToolbar());
        ins.setWidgetContainer(list);

		return list;
	}



	protected JComponent createRotationToolbar(final ShapeRotationCustomiser ins, final LFrame frame, final LCanvas canvas) {
		ListToggleButton list = new ListToggleButton(frame, LResources.ROTATE_ICON, ListToggleButton.LOCATION_NORTH, canvas);
        list.setToolTipText(LangTool.LANG.getString18("LaTeXDrawFrame.2")); //$NON-NLS-1$

        addSpinner(list, ins.getRotationField(), true, 65);
        list.addComponent(ins.getRotate90Button());
        list.addComponent(ins.getRotate180Button());
        list.addComponent(ins.getRotate270Button());
        list.addSeparator();

        ins.addEventable(list.getToolbar());
        ins.setWidgetContainer(list);

		return list;
	}


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes filling properties.
	 * @param fillingCustomiser The instrument that contains the widgets.
	 * @param frame The main frame of the system.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent createFillingPanel(final ShapeFillingCustomiser fillingCustomiser, final LFrame frame, final LCanvas canvas) {
		ListToggleButton list = new ListToggleButton(frame, LResources.FILLING_ICON, ListToggleButton.LOCATION_NORTH, canvas);
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
		fillingCustomiser.setWidgetContainer(list);
		fillingCustomiser.addEventable(list.getToolbar());

		return list;
	}


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes double border properties.
	 * @param borderCustomiser The instrument that contains the widgets.
	 * @param frame The main frame of the system.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent createShadowPropertiesPanel(final ShapeShadowCustomiser shadowCustomiser, final LFrame frame, final LCanvas canvas) {
		ListToggleButton list = new ListToggleButton(frame, LResources.SHADOW_ICON, ListToggleButton.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the shadow properties.");
		list.addComponent(shadowCustomiser.getShadowCB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(shadowCustomiser.getShadowColB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, shadowCustomiser.getShadowSizeField(), true, 75);
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, shadowCustomiser.getShadowAngleField(), true, 75);
		list.addSeparator();
		shadowCustomiser.setWidgetContainer(list);
		shadowCustomiser.addEventable(list.getToolbar());

		return list;
	}


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes double border properties.
	 * @param borderCustomiser The instrument that contains the widgets.
	 * @param frame The main frame of the system.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent createDoubleBorderPropertiesPanel(final ShapeDoubleBorderCustomiser dbleBorderCustomiser, final LFrame frame, final LCanvas canvas) {
		ListToggleButton list = new ListToggleButton(frame, LResources.DOUBLE_BORDER_ICON, ListToggleButton.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the double border properties.");
		list.addComponent(dbleBorderCustomiser.getDbleBoundCB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(dbleBorderCustomiser.getDbleBoundColB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, dbleBorderCustomiser.getDbleSepField(), true, 55);
		list.addSeparator();
		dbleBorderCustomiser.setWidgetContainer(list);
		dbleBorderCustomiser.addEventable(list.getToolbar());

		return list;
	}


	/**
	 * Creates the widget that contains the widgets dedicated to the modification of shapes border properties.
	 * @param borderCustomiser The instrument that contains the widgets.
	 * @param frame The main frame of the system.
	 * @return The created widget. Cannot be null.
	 * @since 3.0
	 */
	protected JComponent createBorderPropertiesPanel(final ShapeBorderCustomiser borderCustomiser, final LFrame frame, final LCanvas canvas) {
		ListToggleButton list = new ListToggleButton(frame, LResources.BORDER_ICON, ListToggleButton.LOCATION_NORTH, canvas);
		list.setToolTipText("Modifies the border properties.");

		addSpinner(list, borderCustomiser.getThicknessField(), true, 65);
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(borderCustomiser.getLineColButton());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(borderCustomiser.getLineCB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(borderCustomiser.getBordersPosCB());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		list.addComponent(borderCustomiser.getIsRound());
		list.addComponent(Box.createHorizontalStrut(SEPARATION_WIDTH));
		addSpinner(list, borderCustomiser.getFrameArcField(), true, 65);
		list.addSeparator();
		borderCustomiser.setWidgetContainer(list);
		borderCustomiser.addEventable(list.getToolbar());

		return list;
	}
}
