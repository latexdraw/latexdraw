/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import org.malai.javafx.binding.ToggleButtonBinding;

/**
 * This instrument modifies arc parameters.
 * @author Arnaud BLOUIN
 */
public class ShapeArcCustomiser extends ShapePropertyCustomiser implements Initializable {
	/** The toggle button that selects the arc style. */
	@FXML private ToggleButton arcB;
	/** The toggle button that selects the wedge style. */
	@FXML private ToggleButton wedgeB;
	/** The toggle button that selects the chord style. */
	@FXML private ToggleButton chordB;
	/** The spinner that sets the start angle. */
	@FXML private Spinner<Double> startAngleS;
	/** The spinner that sets the end angle. */
	@FXML private Spinner<Double> endAngleS;
	@FXML private TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	ShapeArcCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IArcProp.class)) {
			final ArcStyle type = shape.getArcStyle();
			arcB.setSelected(type == ArcStyle.ARC);
			wedgeB.setSelected(type == ArcStyle.WEDGE);
			chordB.setSelected(type == ArcStyle.CHORD);
			startAngleS.getValueFactory().setValue(Math.toDegrees(shape.getAngleStart()));
			endAngleS.getValueFactory().setValue(Math.toDegrees(shape.getAngleEnd()));
			setActivated(true);
		}else {
			setActivated(false);
		}
	}

	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		addBinding(new Spinner4Selection(this, startAngleS, ShapeProperties.ARC_START_ANGLE, true));
		addBinding(new Spinner4Selection(this, endAngleS, ShapeProperties.ARC_END_ANGLE, true));
		addBinding(new Spinner4Pencil(this, startAngleS, ShapeProperties.ARC_START_ANGLE, true));
		addBinding(new Spinner4Pencil(this, endAngleS, ShapeProperties.ARC_END_ANGLE, true));
		addBinding(new Button2SelectionArcStyle(this));
		addBinding(new Button2PencilArcStyle(this));
	}

	private abstract static class Button2ArcStyle<T extends ShapePropertyAction> extends ToggleButtonBinding<T, ShapeArcCustomiser> {
		Button2ArcStyle(final ShapeArcCustomiser ins, final Class<T> act) throws InstantiationException, IllegalAccessException {
			super(ins, act, ins.arcB, ins.chordB, ins.wedgeB);
		}

		@Override
		public void initAction() {
			final ToggleButton button = interaction.getWidget();
			final ArcStyle style;

			if(button == instrument.arcB) {
				style = ArcStyle.ARC;
			}
			else {
				if(button == instrument.chordB) {
					style = ArcStyle.CHORD;
				}
				else {
					style = ArcStyle.WEDGE;
				}
			}

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
			action.setGroup(instrument.drawing.getSelection().duplicateDeep(false));
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated();
		}
	}
}
