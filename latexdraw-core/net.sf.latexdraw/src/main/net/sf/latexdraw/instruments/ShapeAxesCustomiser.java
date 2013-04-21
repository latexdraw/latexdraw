package net.sf.latexdraw.instruments;

import javax.swing.JLabel;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.IAxes.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.PlottingStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.TicksStyle;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;

import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MCheckBox;
import org.malai.swing.widget.MComboBox;
import org.malai.swing.widget.MSpinner;

/**
 * This instrument modifies axes properties of shapes or the pencil.<br>
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
 * 2012-04-05<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeAxesCustomiser extends ShapePropertyCustomiser {
	/** The widget that permits to select the style of the axes. */
	protected MComboBox<AxesStyle> shapeAxes;

	/** The widget that permits to select the style of the ticks. */
	protected MComboBox<TicksStyle> shapeTicks;

//	/** The widget that permits to set the size of the ticks. */
//	protected MSpinner ticksSizeS;

	/** The widget that permits to show/hide the ticks of the axes. */
	protected MComboBox<PlottingStyle> showTicks;

	/** The widget that permits to set the increment of X-labels. */
	protected MSpinner incrLabelX;

	/** The widget that permits to set the increment of Y-labels. */
	protected MSpinner incrLabelY;

	/** The widget that permits to set the visibility of the labels. */
	protected MComboBox<PlottingStyle> showLabels;

	/** The widget that permits to set the visibility of the origin point. */
	protected MCheckBox showOrigin;

	/** The distance between the X-labels. */
	protected MSpinner distLabelsX;

	/** The distance between the Y-labels. */
	protected MSpinner distLabelsY;


	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null or if the drawing cannot
	 * be accessed from the hand.
	 * @since 3.0
	 */
	public ShapeAxesCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}


	@Override
	protected void update(final IShape shape) {
		if(shape.isTypeOf(IAxes.class)) {
			final IAxes axes = (IAxes)shape;
			shapeAxes.setSelectedItemSafely(axes.getAxesStyle());
			shapeTicks.setSelectedItemSafely(axes.getTicksStyle());
//			ticksSizeS.setValueSafely(axes.getTicksSize());
			showTicks.setSelectedItemSafely(axes.getTicksDisplayed());
			incrLabelX.setValueSafely(axes.getIncrementX());
			incrLabelY.setValueSafely(axes.getIncrementY());
			showLabels.setSelectedItemSafely(axes.getLabelsDisplayed());
			showOrigin.setSelected(axes.isShowOrigin());
			distLabelsX.setValueSafely(axes.getDistLabelsX());
			distLabelsY.setValueSafely(axes.getDistLabelsY());
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(shapeAxes, activated);
		composer.setWidgetVisible(shapeTicks, activated);
//		composer.setWidgetVisible(ticksSizeS, activated);
		composer.setWidgetVisible(showTicks, activated);
		composer.setWidgetVisible(incrLabelX, activated);
		composer.setWidgetVisible(incrLabelY, activated);
		composer.setWidgetVisible(showLabels, activated);
		composer.setWidgetVisible(showOrigin, activated);
		composer.setWidgetVisible(distLabelsX, activated);
		composer.setWidgetVisible(distLabelsY, activated);
	}


	@Override
	protected void initialiseWidgets() {
		shapeAxes = new MComboBox<>(AxesStyle.values(), null);
		shapeTicks = new MComboBox<>(TicksStyle.values(), new JLabel(LangTool.INSTANCE.getString18("ParametersAxeFrame.0")));
		showTicks = new MComboBox<>(PlottingStyle.values(), new JLabel("Visibility:"));
		showLabels = new MComboBox<>(PlottingStyle.values(), new JLabel("Visibility:"));
//		ticksSizeS = new MSpinner(new MSpinner.MSpinnerNumberModel(1., 1., 1000., 0.5), new JLabel(LangTool.INSTANCE.getString18("ParametersAxeFrame.13")));
		incrLabelX = new MSpinner(new MSpinner.MSpinnerNumberModel(0.0001, 0.0001, 1000., 1.), new JLabel(LangTool.INSTANCE.getString18("ParametersAxeFrame.8")));
		incrLabelY = new MSpinner(new MSpinner.MSpinnerNumberModel(0.0001, 0.0001, 1000., 1.), new JLabel(LangTool.INSTANCE.getString18("ParametersAxeFrame.9")));
		distLabelsX = new MSpinner(new MSpinner.MSpinnerNumberModel(0.01, 0.01, 1000., 0.05), new JLabel(LangTool.INSTANCE.getString18("ParametersAxeFrame.6")));
		distLabelsY = new MSpinner(new MSpinner.MSpinnerNumberModel(0.01, 0.01, 1000., 0.05), new JLabel(LangTool.INSTANCE.getString18("ParametersAxeFrame.7")));
		showOrigin = new MCheckBox(LangTool.INSTANCE.getString18("ParametersAxeFrame.1"), true); //$NON-NLS-1$
	}


	@Override
	protected void initialiseLinks() {
		try {
			addLink(new Combobox2CustomSelectedAxes(this));
			addLink(new Combobox2CustomPencilAxes(this));
			addLink(new Spinner2CustomPencilAxes(this));
			addLink(new Spinner2CustomSelectedAxes(this));
			addLink(new CheckBox2CustomPencilAxes(this));
			addLink(new CheckBox2CustomSelectedAxes(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The widget that permits to select the style of the axes.
	 * @since 3.0
	 */
	public MComboBox<AxesStyle> getShapeAxes() {
		return shapeAxes;
	}

	/**
	 * @return The widget that permits to select the style of the ticks.
	 * @since 3.0
	 */
	public MComboBox<TicksStyle> getShapeTicks() {
		return shapeTicks;
	}

//	/**
//	 * @return The widget that permits to set the size of the ticks.
//	 * @since 3.0
//	 */
//	public MSpinner getTicksSizeS() {
//		return ticksSizeS;
//	}

	/**
	 * @return The widget that permits to show/hide the ticks of the axes.
	 * @since 3.0
	 */
	public MComboBox<PlottingStyle> getShowTicks() {
		return showTicks;
	}

	/**
	 * @return The widget that permits to show/hide the labels of the axes.
	 * @since 3.0
	 */
	public MComboBox<PlottingStyle> getShowLabels() {
		return showLabels;
	}

	/**
	 * @return The widget that permits to set the increment of X-labels.
	 * @since 3.0
	 */
	public MSpinner getIncrLabelX() {
		return incrLabelX;
	}

	/**
	 * @return The widget that permits to set the increment of Y-labels.
	 * @since 3.0
	 */
	public MSpinner getIncrLabelY() {
		return incrLabelY;
	}

	/**
	 * @return The widget that permits to set the visibility of the origin point.
	 * @since 3.0
	 */
	public MCheckBox getShowOrigin() {
		return showOrigin;
	}

	/**
	 * @return The distance between the X-labels.
	 * @since 3.0
	 */
	public MSpinner getDistLabelsX() {
		return distLabelsX;
	}

	/**
	 * @return The distance between the Y-labels.
	 * @since 3.0
	 */
	public MSpinner getDistLabelsY() {
		return distLabelsY;
	}


	/** Maps a checkbox to an action that modifies several axes' parameters. */
	private static abstract class CheckBox2CustomAxes<A extends ShapePropertyAction> extends CheckBoxForCustomiser<A, ShapeAxesCustomiser> {
		protected CheckBox2CustomAxes(final ShapeAxesCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(ins, clazzAction);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.showOrigin==interaction.getCheckBox();
		}

		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.AXES_SHOW_ORIGIN);
		}

		@Override
		public void updateAction() {
			action.setValue(interaction.getCheckBox().isSelected());
		}
	}


	/** Maps a spinner to an action that modifies the ticks size of the selected shapes. */
	private static class CheckBox2CustomSelectedAxes extends CheckBox2CustomAxes<ModifyShapeProperty> {
		protected CheckBox2CustomSelectedAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated() && super.isConditionRespected();
		}
	}


	/** Maps a spinner to an action that modifies the ticks size of the pencil. */
	private static class CheckBox2CustomPencilAxes extends CheckBox2CustomAxes<ModifyPencilParameter> {
		protected CheckBox2CustomPencilAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setPencil(instrument.pencil);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated() && super.isConditionRespected();
		}
	}


	/** Maps a spinner to an action that modifies several axes' parameters. */
	private static abstract class Spinner2CustomAxes<A extends ShapePropertyAction> extends SpinnerForCustomiser<A, ShapeAxesCustomiser> {
		protected Spinner2CustomAxes(final ShapeAxesCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(ins, clazzAction);
		}

		@Override
		public boolean isConditionRespected() {
			final Object spinner = interaction.getSpinner();
			return
//					instrument.ticksSizeS==spinner ||
					instrument.incrLabelX==spinner || instrument.incrLabelY==spinner ||
				   instrument.distLabelsX==spinner || instrument.distLabelsY==spinner;
		}

		@Override
		public void initAction() {
			final Object spinner = interaction.getSpinner();

//			if(spinner==instrument.ticksSizeS)
//				action.setProperty(ShapeProperties.AXES_TICKS_SIZE);
//			else
			if(spinner==instrument.distLabelsX || spinner==instrument.distLabelsY)
				action.setProperty(ShapeProperties.AXES_LABELS_DIST);
			else
				action.setProperty(ShapeProperties.AXES_LABELS_INCR);
		}

		@Override
		public void updateAction() {
			final Object spinner = interaction.getSpinner();

//			if(spinner==instrument.ticksSizeS)
//				action.setValue(Double.valueOf(interaction.getSpinner().getValue().toString()));
//			else
			if(spinner==instrument.distLabelsX || spinner==instrument.distLabelsY)
				action.setValue(DrawingTK.getFactory().createPoint(Double.valueOf(instrument.distLabelsX.getValue().toString()),
								Double.valueOf(instrument.distLabelsY.getValue().toString())));
			else
				action.setValue(DrawingTK.getFactory().createPoint(Double.valueOf(instrument.incrLabelX.getValue().toString()),
							Double.valueOf(instrument.incrLabelY.getValue().toString())));
		}
	}


	/** Maps a spinner to an action that modifies the ticks size of the selected shapes. */
	private static class Spinner2CustomSelectedAxes extends Spinner2CustomAxes<ModifyShapeProperty> {
		protected Spinner2CustomSelectedAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated() && super.isConditionRespected();
		}
	}


	/** Maps a spinner to an action that modifies the ticks size of the pencil. */
	private static class Spinner2CustomPencilAxes extends Spinner2CustomAxes<ModifyPencilParameter> {
		protected Spinner2CustomPencilAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setPencil(instrument.pencil);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated() && super.isConditionRespected();
		}
	}



	/** Maps a combobox to an action that modifies the axe's style. */
	private static abstract class Combobox2CustomAxes<A extends ShapePropertyAction> extends ListForCustomiser<A, ShapeAxesCustomiser> {
		protected Combobox2CustomAxes(final ShapeAxesCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(ins, clazzAction);
		}

		@Override
		public boolean isConditionRespected() {
			final Object list = interaction.getList();
			return instrument.shapeAxes==list || instrument.shapeTicks==list || instrument.showTicks==list || instrument.showLabels==list;
		}

		@Override
		public void initAction() {
			final Object list = interaction.getList();

			if(instrument.shapeAxes==list)
				action.setProperty(ShapeProperties.AXES_STYLE);
			else
				if(instrument.showTicks==list)
					action.setProperty(ShapeProperties.AXES_TICKS_SHOW);
				else
					if(instrument.showLabels==list)
						action.setProperty(ShapeProperties.AXES_LABELS_SHOW);
					else
						action.setProperty(ShapeProperties.AXES_TICKS_STYLE);

			action.setValue(interaction.getList().getSelectedObjects()[0]);
		}
	}


	/** Maps a combobox to an action that modifies the axe's style of the pencil. */
	private static class Combobox2CustomPencilAxes extends Combobox2CustomAxes<ModifyPencilParameter> {
		protected Combobox2CustomPencilAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setPencil(instrument.pencil);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated() && super.isConditionRespected();
		}
	}

	/** Maps a combobox to an action that modifies the axe's style of the selection. */
	private static class Combobox2CustomSelectedAxes extends Combobox2CustomAxes<ModifyShapeProperty> {
		protected Combobox2CustomSelectedAxes(final ShapeAxesCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated() && super.isConditionRespected();
		}
	}
}
