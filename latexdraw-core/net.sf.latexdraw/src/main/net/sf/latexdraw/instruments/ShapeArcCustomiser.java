package net.sf.latexdraw.instruments;

import javax.swing.JLabel;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.actions.shape.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IArc;
import net.sf.latexdraw.glib.models.interfaces.IArc.ArcStyle;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MSpinner;
import org.malai.swing.widget.MToggleButton;

/**
 * This instrument modifies arc parameters.<br>
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
 * 08/21/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeArcCustomiser extends ShapePropertyCustomiser {
	/** The toggle button that selects the arc style. */
	protected MToggleButton arcB;

	/** The toggle button that selects the wedge style. */
	protected MToggleButton wedgeB;

	/** The toggle button that selects the chord style. */
	protected MToggleButton chordB;

	/** The spinner that sets the start angle. */
	protected MSpinner startAngleS;

	/** The spinner that sets the end angle. */
	protected MSpinner endAngleS;


	/**
	 * Creates the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @throws IllegalArgumentException If one of the given argument is null.
	 * @since 3.0
	 */
	public ShapeArcCustomiser(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}



	@Override
	protected void initialiseWidgets() {
		arcB = new MToggleButton(LResources.ARC_ICON);
		arcB.setMargin(LResources.INSET_BUTTON);
		arcB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.128")); //$NON-NLS-1$

 		wedgeB = new MToggleButton(LResources.WEDGE_ICON);
 		wedgeB.setMargin(LResources.INSET_BUTTON);
 		wedgeB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.130")); //$NON-NLS-1$

 		chordB = new MToggleButton(LResources.CHORD_ICON);
 		chordB.setMargin(LResources.INSET_BUTTON);
 		chordB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.131")); //$NON-NLS-1$

     	startAngleS = new MSpinner(new MSpinner.MSpinnerNumberModel(0., -360., 360., 1.), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersArcFrame.0")));
     	startAngleS.setEditor(new MSpinner.NumberEditor(startAngleS, "0.0"));//$NON-NLS-1$

     	endAngleS = new MSpinner(new MSpinner.MSpinnerNumberModel(0., -360., 360., 1.), new JLabel(LangTool.INSTANCE.getStringDialogFrame("ParametersArcFrame.1")));
     	endAngleS.setEditor(new MSpinner.NumberEditor(endAngleS, "0.0"));//$NON-NLS-1$
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(arcB, visible);
		composer.setWidgetVisible(wedgeB, visible);
		composer.setWidgetVisible(chordB, visible);
		composer.setWidgetVisible(startAngleS, visible);
		composer.setWidgetVisible(endAngleS, visible);
	}


	@Override
	protected void update(final IShape shape) {
		boolean active = false;

		if(shape instanceof IArc) {
			final IArc arc = (IArc)shape;
			final ArcStyle type = arc.getArcStyle();

			if(type!=null) {
				active = true;
				arcB.setSelected(type==ArcStyle.ARC);
				wedgeB.setSelected(type==ArcStyle.WEDGE);
				chordB.setSelected(type==ArcStyle.CHORD);
				startAngleS.setValueSafely(Math.toDegrees(arc.getAngleStart()));
				endAngleS.setValueSafely(Math.toDegrees(arc.getAngleEnd()));
			}
		}

		if(!active)
			setActivated(false);
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new Spinner2SelectionEndAngle(this));
			addLink(new Spinner2SelectionStartAngle(this));
			addLink(new Spinner2PencilStartAngle(this));
			addLink(new Spinner2PencilEndAngle(this));
			addLink(new Button2SelectionArcStyle(this));
			addLink(new Button2PencilArcStyle(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/** @return The toggle button that selects the arc style. */
	public MToggleButton getArcB() { return arcB; }

	/** @return The toggle button that selects the wedge style. */
	public MToggleButton getWedgeB() { return wedgeB; }

	/**  @return The toggle button that selects the chord style. */
	public MToggleButton getChordB() { return chordB; }

	/** @return The spinner that sets the start angle. */
	public MSpinner getStartAngleS() { return startAngleS; }

	/**  @return The spinner that sets the end angle. */
	public MSpinner getEndAngleS() { return endAngleS; }
}


abstract class Button2ArcStyle<T extends ShapePropertyAction> extends ButtonPressedForCustomiser<T, ShapeArcCustomiser> {
	protected Button2ArcStyle(final ShapeArcCustomiser ins, final Class<T> action) throws InstantiationException, IllegalAccessException {
		super(ins, action);
	}

	@Override
	public void initAction() {
		final Object button = interaction.getButton();
		final ArcStyle style;

		if(button==instrument.arcB)
			 style = ArcStyle.ARC;
		else if(button==instrument.chordB)
			 style=ArcStyle.CHORD;
		else style=ArcStyle.WEDGE;

		action.setProperty(ShapeProperties.ARC_STYLE);
		action.setValue(style);
	}

	@Override
	public boolean isConditionRespected() {
		final Object button = interaction.getButton();
		return button==instrument.arcB || button==instrument.chordB || button==instrument.wedgeB;
	}
}



class Button2PencilArcStyle extends Button2ArcStyle<ModifyPencilParameter> {
	protected Button2PencilArcStyle(final ShapeArcCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setPencil(instrument.pencil);
	}

	@Override
	public boolean isConditionRespected() {
		return super.isConditionRespected() && instrument.pencil.isActivated();
	}
}



class Button2SelectionArcStyle extends Button2ArcStyle<ModifyShapeProperty> {
	protected Button2SelectionArcStyle(final ShapeArcCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		super.initAction();
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
	}

	@Override
	public boolean isConditionRespected() {
		return super.isConditionRespected() && instrument.hand.isActivated();
	}
}



class Spinner2PencilStartAngle extends SpinnerForCustomiser<ModifyPencilParameter, ShapeArcCustomiser> {
	protected Spinner2PencilStartAngle(final ShapeArcCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		action.setProperty(ShapeProperties.ARC_START_ANGLE);
		action.setPencil(instrument.pencil);
	}

	@Override
	public void updateAction() {
		action.setValue(Math.toRadians(Double.valueOf(instrument.startAngleS.getValue().toString())));
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getSpinner()==instrument.startAngleS && instrument.pencil.isActivated();
	}
}



class Spinner2SelectionStartAngle extends SpinnerForCustomiser<ModifyShapeProperty, ShapeArcCustomiser> {
	protected Spinner2SelectionStartAngle(final ShapeArcCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		action.setProperty(ShapeProperties.ARC_START_ANGLE);
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
	}

	@Override
	public void updateAction() {
		action.setValue(Math.toRadians(Double.valueOf(instrument.startAngleS.getValue().toString())));
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getSpinner()==instrument.startAngleS && instrument.hand.isActivated();
	}
}



class Spinner2PencilEndAngle extends SpinnerForCustomiser<ModifyPencilParameter, ShapeArcCustomiser> {
	protected Spinner2PencilEndAngle(final ShapeArcCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyPencilParameter.class);
	}

	@Override
	public void initAction() {
		action.setProperty(ShapeProperties.ARC_END_ANGLE);
		action.setPencil(instrument.pencil);
	}

	@Override
	public void updateAction() {
		action.setValue(Math.toRadians(Double.valueOf(instrument.endAngleS.getValue().toString())));
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getSpinner()==instrument.endAngleS && instrument.pencil.isActivated();
	}
}


class Spinner2SelectionEndAngle extends SpinnerForCustomiser<ModifyShapeProperty, ShapeArcCustomiser> {
	protected Spinner2SelectionEndAngle(final ShapeArcCustomiser ins) throws InstantiationException, IllegalAccessException {
		super(ins, ModifyShapeProperty.class);
	}

	@Override
	public void initAction() {
		action.setProperty(ShapeProperties.ARC_END_ANGLE);
		action.setGroup((IGroup)instrument.pencil.canvas.getDrawing().getSelection().duplicate());
	}

	@Override
	public void updateAction() {
		action.setValue(Math.toRadians(Double.valueOf(instrument.endAngleS.getValue().toString())));
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getSpinner()==instrument.endAngleS && instrument.hand.isActivated();
	}
}
