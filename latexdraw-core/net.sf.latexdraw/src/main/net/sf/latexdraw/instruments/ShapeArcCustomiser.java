package net.sf.latexdraw.instruments;

import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.glib.models.interfaces.prop.IArcProp.ArcStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

import org.malai.javafx.instrument.library.SpinnerInteractor;
import org.malai.javafx.instrument.library.ToggleButtonInteractor;

/**
 * This instrument modifies arc parameters.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 08/21/2011<br>
 * 
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeArcCustomiser extends ShapePropertyCustomiser {
	/** The toggle button that selects the arc style. */
	@FXML protected ToggleButton arcB;

	/** The toggle button that selects the wedge style. */
	@FXML protected ToggleButton wedgeB;

	/** The toggle button that selects the chord style. */
	@FXML protected ToggleButton chordB;

	/** The spinner that sets the start angle. */
	@FXML protected Spinner<Double> startAngleS;

	/** The spinner that sets the end angle. */
	@FXML protected Spinner<Double> endAngleS;

	@FXML protected TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeArcCustomiser() {
		super();
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IArcProp.class)) {
			final ArcStyle type = shape.getArcStyle();
			arcB.setSelected(type==ArcStyle.ARC);
			wedgeB.setSelected(type==ArcStyle.WEDGE);
			chordB.setSelected(type==ArcStyle.CHORD);
			startAngleS.getValueFactory().setValue(Math.toDegrees(shape.getAngleStart()));
			endAngleS.getValueFactory().setValue(Math.toDegrees(shape.getAngleEnd()));
			setActivated(true);
		}else
			setActivated(false);
	}

	@Override
	protected void initialiseInteractors() {
		try {
			addInteractor(new Spinner2SelectionAngle(this));
			addInteractor(new Spinner2PencilAngle(this));
			addInteractor(new Button2SelectionArcStyle(this));
			addInteractor(new Button2PencilArcStyle(this));
		}catch(InstantiationException|IllegalAccessException e) {
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	private static abstract class Button2ArcStyle<T extends ShapePropertyAction> extends ToggleButtonInteractor<T, ShapeArcCustomiser> {
		Button2ArcStyle(final ShapeArcCustomiser ins, final Class<T> action) throws InstantiationException, IllegalAccessException {
			super(ins, action, Arrays.asList(ins.arcB, ins.chordB, ins.wedgeB));
		}

		@Override
		public void initAction() {
			final ToggleButton button = interaction.getWidget();
			final ArcStyle style;

			if(button==instrument.arcB)
				style = ArcStyle.ARC;
			else if(button==instrument.chordB)
				style = ArcStyle.CHORD;
			else
				style = ArcStyle.WEDGE;

			action.setProperty(ShapeProperties.ARC_STYLE);
			action.setValue(style);
		}
	}

	private static class Button2PencilArcStyle extends Button2ArcStyle<ModifyPencilParameter> {
		Button2PencilArcStyle(final ShapeArcCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setPencil(instrument.pencil);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	private static class Button2SelectionArcStyle extends Button2ArcStyle<ModifyShapeProperty> {
		Button2SelectionArcStyle(final ShapeArcCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class);
		}

		@Override
		public void initAction() {
			super.initAction();
			action.setGroup(instrument.pencil.getCanvas().getDrawing().getSelection().duplicateDeep(false));
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}

	private static class Spinner2PencilAngle extends SpinnerInteractor<ModifyPencilParameter, ShapeArcCustomiser> {
		Spinner2PencilAngle(final ShapeArcCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyPencilParameter.class, Arrays.asList(ins.startAngleS, ins.endAngleS));
		}

		@Override
		public void initAction() {
			if(interaction.getWidget()==instrument.startAngleS)
				action.setProperty(ShapeProperties.ARC_START_ANGLE);
			else
				action.setProperty(ShapeProperties.ARC_END_ANGLE);

			action.setPencil(instrument.pencil);
			action.setValue(Math.toRadians((double)interaction.getWidget().getValue()));
		}

		// TODO see whether spinner actions can be grouped as before.
		// @Override
		// public void updateAction() {
		// action.setValue(Math.toRadians((double)interaction.getWidget().getValue()));
		// }

		@Override
		public boolean isConditionRespected() {
			return instrument.pencil.isActivated();
		}
	}

	private static class Spinner2SelectionAngle extends SpinnerInteractor<ModifyShapeProperty, ShapeArcCustomiser> {
		Spinner2SelectionAngle(final ShapeArcCustomiser ins) throws InstantiationException, IllegalAccessException {
			super(ins, ModifyShapeProperty.class, Arrays.asList(ins.startAngleS, ins.endAngleS));
		}

		@Override
		public void initAction() {
			if(interaction.getWidget()==instrument.startAngleS)
				action.setProperty(ShapeProperties.ARC_START_ANGLE);
			else
				action.setProperty(ShapeProperties.ARC_END_ANGLE);
			action.setGroup(instrument.pencil.getCanvas().getDrawing().getSelection().duplicateDeep(false));
			action.setValue(Math.toRadians((double)interaction.getWidget().getValue()));
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}
}
