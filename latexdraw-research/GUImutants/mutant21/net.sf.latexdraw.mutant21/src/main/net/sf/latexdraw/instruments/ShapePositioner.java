package net.sf.latexdraw.instruments;

import net.sf.latexdraw.actions.shape.MoveBackForegroundShapes;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.instrument.Link;
import org.malai.swing.interaction.library.ButtonPressed;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MButton;

/**
 * Puts shapes in background / foreground.<br>
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
 * 2013-04-21<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapePositioner extends ShapePropertyCustomiser {
	/** The foreground button. */
	protected MButton foregroundB;

	/** The background button. */
	protected MButton backgroundB;

	/**
	 * Creates the instrument.
	 * @param composer The composer that manages the widgets of the instrument.
	 * @param hand The Hand instrument.
	 * @param pencil The Pencil instrument.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 3.0
	 */
	public ShapePositioner(final UIComposer<?> composer, final Hand hand, final Pencil pencil) {
		super(composer, hand, pencil);
		initialiseWidgets();
	}


	@Override
	protected void initialiseWidgets() {
        foregroundB = new MButton(LResources.FOREGROUND_ICON);
        foregroundB.setMargin(LResources.INSET_BUTTON);
        foregroundB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.70")); //$NON-NLS-1$

        backgroundB = new MButton(LResources.BACKGROUND_ICON);
        backgroundB.setMargin(LResources.INSET_BUTTON);
        backgroundB.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.71")); //$NON-NLS-1$
	}


	@Override
	protected void initialiseLinks() {
		try {
			addLink(new Button2MoveBackForeground(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	/** @return The foreground button. */
	public MButton getForegroundButton() { return foregroundB; }

	/**  @return The background button. */
	public MButton getBackgroundButton() { return backgroundB; }


	@Override
	protected void update(final IShape shape) {
		// Nothing to do.
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		composer.setWidgetVisible(foregroundB, visible);
		composer.setWidgetVisible(backgroundB, visible);
	}
}

/** This link maps a button interaction to an action that puts shapes in foreground / background. */
class Button2MoveBackForeground extends Link<MoveBackForegroundShapes, ButtonPressed, ShapePositioner> {
	protected Button2MoveBackForeground(ShapePositioner ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, MoveBackForegroundShapes.class, ButtonPressed.class);
	}

	@Override
	public void initAction() {
		action.setIsForeground(interaction.getButton()==instrument.foregroundB);
		action.setDrawing(instrument.pencil.canvas.getDrawing());
		action.setShape(instrument.pencil.canvas.getDrawing().getSelection().duplicate());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.backgroundB || interaction.getButton()==instrument.foregroundB;
	}
}
