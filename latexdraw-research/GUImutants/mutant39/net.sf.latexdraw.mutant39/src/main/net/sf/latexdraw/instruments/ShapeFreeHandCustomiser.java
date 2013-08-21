package net.sf.latexdraw.instruments;

import java.awt.Dimension;

import javax.swing.JLabel;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.LabelComboBox;
import net.sf.latexdraw.ui.LabelListCellRenderer;
import net.sf.latexdraw.util.LResources;

import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MCheckBox;
import org.malai.swing.widget.MComboBox;
import org.malai.swing.widget.MSpinner;
import org.malai.swing.widget.MSpinner.MSpinnerNumberModel;

/**
 * This instrument modifies free hand properties of shapes or the pencil.<br>
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
 * 2012-04-15<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeFreeHandCustomiser extends ShapePropertyCustomiser {
	/** The type of the freehand. */
	protected LabelComboBox freeHandType;

	/** The gap to consider between the points. */
	protected MSpinner gapPoints;

	/** Defines if the shape is open. */
	protected MCheckBox open;


	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given argument is null or if the drawing cannot
	 * be accessed from the hand.
	 * @since 3.0
	 */
	public ShapeFreeHandCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}


	@Override
	protected void update(final IShape shape) {
		if(shape.isTypeOf(IFreehand.class)) {
			final IFreehand fh = (IFreehand)shape;
			freeHandType.setSelectedItemSafely(fh.getType().toString());
			gapPoints.setValueSafely(fh.getInterval());
			open.setSelected(fh.isOpen());
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(freeHandType, activated);
		composer.setWidgetVisible(gapPoints, activated);
		composer.setWidgetVisible(open, activated);
	}


	@Override
	protected void initialiseWidgets() {
		freeHandType = new LabelComboBox();
		freeHandType.setLabel(new JLabel(LangTool.INSTANCE.getString19("ParametersAkinPointsFrame.2")));
		freeHandType.setRenderer(new LabelListCellRenderer());
		JLabel label = new JLabel(IFreehand.FreeHandType.CURVES.toString());
		label.setIcon(LResources.CURVES_FREEHAND_ICON);
		freeHandType.addItem(label);
		label = new JLabel(IFreehand.FreeHandType.LINES.toString());
		label.setIcon(LResources.LINES_FREEHAND_ICON);
		freeHandType.addItem(label);
		freeHandType.setPreferredSize(new Dimension(90, 30));
		freeHandType.setMaximumSize(new Dimension(90, 30));

		gapPoints = new MSpinner(new MSpinnerNumberModel(5, 1, 1000, 1), new JLabel(LangTool.INSTANCE.getString19("ParametersAkinPointsFrame.0")));

		open = new MCheckBox(LangTool.INSTANCE.getString19("ParametersAkinPointsFrame.1"));
	}


	/**
	 * @return The type of the freehand.
	 * @since 3.0
	 */
	public final MComboBox<JLabel> getFreeHandType() {
		return freeHandType;
	}

	/**
	 * @return The gap to consider between the points.
	 * @since 3.0
	 */
	public final MSpinner getGapPoints() {
		return gapPoints;
	}


	/**
	 * @return The check box that defines if the shape is open.
	 * @since 3.0
	 */
	public final MCheckBox getOpen() {
		return open;
	}


	@Override
	protected void initialiseLinks() {
		try {
			addLink(new Combobox2CustomPencilFH(this));
			addLink(new Combobox2CustomSelectedFH(this));
			addLink(new Spinner2PencilFreeHand(this));
			addLink(new Spinner2SelectionFreeHand(this));
			addLink(new Checkbox2PencilFreeHand(this));
			addLink(new Checkbox2SelectionFreeHand(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/** Maps a checkbox to an action. */
	private static abstract class CheckboxForShapeFreeHandCust<A extends ShapePropertyAction> extends CheckBoxForCustomiser<A, ShapeFreeHandCustomiser> {
		protected CheckboxForShapeFreeHandCust(final ShapeFreeHandCustomiser instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(instrument, clazzAction);
		}

		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.FREEHAND_OPEN);
			action.setValue(interaction.getCheckBox().isSelected());
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getCheckBox()==instrument.open;
		}
	}

	/** Maps a checkbox to an action that modifies the pencil. */
	private static class Checkbox2PencilFreeHand extends CheckboxForShapeFreeHandCust<ModifyPencilParameter> {
		protected Checkbox2PencilFreeHand(final ShapeFreeHandCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyPencilParameter.class);
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

	/** This link maps a colour button to the selected shapes. */
	private static class Checkbox2SelectionFreeHand extends CheckboxForShapeFreeHandCust<ModifyShapeProperty> {
		protected Checkbox2SelectionFreeHand(final ShapeFreeHandCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyShapeProperty.class);
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


	/** Maps a spinner to an action. */
	private static abstract class SpinnerForShapeFreeHandCust<A extends ShapePropertyAction> extends SpinnerForCustomiser<A, ShapeFreeHandCustomiser> {
		protected SpinnerForShapeFreeHandCust(final ShapeFreeHandCustomiser instrument, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(instrument, clazzAction);
		}

		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.FREEHAND_INTERVAL);
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getSpinner()==instrument.gapPoints;
		}

		@Override
		public void updateAction() {
			action.setValue(Integer.valueOf(interaction.getSpinner().getValue().toString()));
		}
	}


	/** Maps a spinner to an action that modifies the pencil. */
	private static class Spinner2PencilFreeHand extends SpinnerForShapeFreeHandCust<ModifyPencilParameter> {
		protected Spinner2PencilFreeHand(final ShapeFreeHandCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyPencilParameter.class);
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


	/**
	 * This link maps a colour button to the selected shapes.
	 */
	private static class Spinner2SelectionFreeHand extends SpinnerForShapeFreeHandCust<ModifyShapeProperty> {
		protected Spinner2SelectionFreeHand(final ShapeFreeHandCustomiser instrument) throws InstantiationException, IllegalAccessException {
			super(instrument, ModifyShapeProperty.class);
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


	/** Maps a combobox to an action that modifies freehand's parameters. */
	private static abstract class Combobox2CustomFH<A extends ShapePropertyAction> extends ListForCustomiser<A, ShapeFreeHandCustomiser> {
		protected Combobox2CustomFH(final ShapeFreeHandCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(ins, clazzAction);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.freeHandType==interaction.getList();
		}

		@Override
		public void initAction() {
			action.setProperty(ShapeProperties.FREEHAND_STYLE);
			if(getLabelText().equals(IFreehand.FreeHandType.CURVES.toString()))
				 action.setValue(IFreehand.FreeHandType.CURVES);
			else action.setValue(IFreehand.FreeHandType.LINES);
		}
	}


	/** Maps a combobox to an action that modifies freehand's parameters of the pencil. */
	private static class Combobox2CustomPencilFH extends Combobox2CustomFH<ModifyPencilParameter> {
		protected Combobox2CustomPencilFH(final ShapeFreeHandCustomiser ins) throws InstantiationException, IllegalAccessException {
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


	/** Maps a combobox to an action that modifies the freehand's parameters of the selection. */
	private static class Combobox2CustomSelectedFH extends Combobox2CustomFH<ModifyShapeProperty> {
		protected Combobox2CustomSelectedFH(final ShapeFreeHandCustomiser ins) throws InstantiationException, IllegalAccessException {
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
