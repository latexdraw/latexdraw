package net.sf.latexdraw.instruments;

import java.util.List;

import net.sf.latexdraw.actions.shape.JoinShapes;
import net.sf.latexdraw.actions.shape.SelectShapes;
import net.sf.latexdraw.actions.shape.SeparateShapes;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.action.ActionsRegistry;
import org.malai.instrument.Link;
import org.malai.swing.interaction.library.ButtonPressed;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MButton;

/**
 * This instrument groups and separates shapes.<br>
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
 * 02/13/2012<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeGrouper extends ShapePropertyCustomiser {
	/** The widget to group shapes. */
	protected MButton groupB;

	/** The widget to separate shapes. */
	protected MButton sepB;


	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 3.0
	 */
	public ShapeGrouper(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}


	@Override
	protected void initialiseWidgets() {
		groupB = new MButton(LResources.JOIN_ICON);
		groupB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.75"));
		sepB   = new MButton(LResources.SEPARATE_ICON);
		sepB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.76"));
	}


	@Override
	protected void update(final IShape shape) {
		if(shape instanceof IGroup && !((IGroup)shape).isEmpty()) {
			final IGroup selection = (IGroup)shape;
			final boolean separate = selection.size()==1 && selection.getShapeAt(0) instanceof IGroup;

			groupB.setVisible(selection.size()>1);
			sepB.setVisible(separate);
		}
	}


	@Override
	protected void setWidgetsVisible(final boolean visible) {
		groupB.setVisible(visible);
		sepB.setVisible(visible);
	}



	@Override
	protected void initialiseLinks() {
		try{
			addLink(new Button2GroupShapes(this));
			addLink(new Button2SeparateShapes(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The widget to group shapes.
	 * @since 3.0
	 */
	public MButton getGroupB() {
		return groupB;
	}

	/**
	 * @return The widget to separate shapes.
	 * @since 3.0
	 */
	public MButton getSepB() {
		return sepB;
	}


	/** This link maps a button to an action that separates the selected group. */
	private static class Button2SeparateShapes extends Link<SeparateShapes, ButtonPressed, ShapeGrouper> {
		protected Button2SeparateShapes(final ShapeGrouper ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, SeparateShapes.class, ButtonPressed.class);
		}

		@Override
		public void initAction() {
			final SelectShapes selection = ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
			final List<IShape> shapes 	 = selection.shapes();

			if(shapes.size()==1 && shapes.get(0) instanceof IGroup)
				action.setShape(shapes.get(0));

			action.setDrawing(instrument.pencil.canvas.getDrawing());
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getButton()==instrument.getSepB();
		}
	}


	/** This link maps a button to an action that groups the selected shapes. */
	private static class Button2GroupShapes extends Link<JoinShapes, ButtonPressed, ShapeGrouper> {
		protected Button2GroupShapes(final ShapeGrouper ins) throws InstantiationException, IllegalAccessException {
			super(ins, false, JoinShapes.class, ButtonPressed.class);
		}

		@Override
		public void initAction() {
			final SelectShapes selection = ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
			final List<IShape> shapes 	 = selection.shapes();

			for(final IShape sh : shapes)
				action.addShape(sh);

			action.setDrawing(instrument.pencil.canvas.getDrawing());
		}

		@Override
		public boolean isConditionRespected() {
			return interaction.getButton()==instrument.getGroupB();
		}
	}
}
