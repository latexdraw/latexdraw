package net.sf.latexdraw.instruments;

import net.sf.latexdraw.actions.ModifyPencilParameter;
import net.sf.latexdraw.actions.ModifyShapeProperty;
import net.sf.latexdraw.actions.ShapeProperties;
import net.sf.latexdraw.actions.ShapePropertyAction;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.IAxes.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.TicksStyle;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.malai.ui.UIComposer;
import org.malai.widget.MComboBox;

/**
 * This instrument modifies axes properties of shapes or the pencil.<br>
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
 * 2012-04-05<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeAxesCustomiser extends ShapePropertyCustomiser {
	/** The widget that permits to select the style of the axes. */
	protected MComboBox shapeAxes;

	/** The widget that permits to select the style of the ticks. */
	protected MComboBox shapeTicks;


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
		if(shape instanceof IAxes && (!(shape instanceof IGroup) || ((IGroup)shape).containsAxes())) {
			final IAxes axes = (IAxes)shape;
			shapeAxes.setSelectedItemSafely(axes.getAxesStyle());
			shapeTicks.setSelectedItemSafely(axes.getTicksStyle());
		}
		else setActivated(false);
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(shapeAxes, activated);
		composer.setWidgetVisible(shapeTicks, activated);
	}


	@Override
	protected void initialiseWidgets() {
		shapeAxes = new MComboBox(AxesStyle.values());
		shapeTicks = new MComboBox(TicksStyle.values());
	}


	@Override
	protected void initialiseLinks() {
		try {
			addLink(new Combobox2CustomSelectedAxes(this));
			addLink(new Combobox2CustomPencilAxes(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The widget that permits to select the style of the axes.
	 * @since 3.0
	 */
	public final MComboBox getShapeAxes() {
		return shapeAxes;
	}

	/**
	 * @return The widget that permits to select the style of the ticks.
	 * @since 3.0
	 */
	public final MComboBox getShapeTicks() {
		return shapeTicks;
	}


	/** Maps a combobox to an action that modifies the axe's style. */
	private static abstract class Combobox2CustomAxes<A extends ShapePropertyAction> extends ListForCustomiser<A, ShapeAxesCustomiser> {
		protected Combobox2CustomAxes(final ShapeAxesCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
			super(ins, clazzAction);
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.shapeAxes==interaction.getList() || instrument.shapeTicks==interaction.getList();
		}

		@Override
		public void initAction() {
			if(instrument.shapeAxes==interaction.getList())
				action.setProperty(ShapeProperties.AXES_STYLE);
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
			action.setGroup(instrument.pencil.drawing.getSelection().duplicate());
		}

		@Override
		public boolean isConditionRespected() {
			return instrument.hand.isActivated() && super.isConditionRespected();
		}
	}
}
