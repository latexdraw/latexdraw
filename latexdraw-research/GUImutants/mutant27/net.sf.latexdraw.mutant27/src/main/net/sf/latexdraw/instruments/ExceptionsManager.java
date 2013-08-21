package net.sf.latexdraw.instruments;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.badaboom.BadaboomHandler;
import net.sf.latexdraw.badaboom.BadaboomManager;
import net.sf.latexdraw.util.LResources;

import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.swing.action.library.ShowWidget;
import org.malai.swing.interaction.library.ButtonPressed;
import org.malai.swing.widget.MButton;

/**
 * This instrument allows to see exceptions launched during the execution of the program.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 01/05/11<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class ExceptionsManager extends Instrument implements BadaboomHandler {
	/** The button used to shows the panel of exceptions. */
	protected MButton exceptionB;

	/** The frame to show when exceptions occur. */
	protected BadaboomManager frame;


	/**
	 * Creates the instrument.
	 * @since 3.0
	 */
	public ExceptionsManager() {
		super();

		frame		= new BadaboomManager();
		exceptionB 	= new MButton(LResources.ERR_ICON);
		setActivated(false);
		BadaboomCollector.INSTANCE.addHandler(this);
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new ButtonPress2ShowExceptionFrame(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	@Override
	public void notifyEvent(final Throwable ex) {
		setActivated(true);
	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		exceptionB.setVisible(activated);
	}


	/**
	 * @return The button used to shows the panel of exceptions.
	 * @since 3.0
	 */
	public MButton getExceptionB() {
		return exceptionB;
	}


	@Override
	public void notifyEvents() {
		setActivated(true);
	}
}


/**
 * Links a button pressed interaction to an action that show the exceptions frame.
 */
class ButtonPress2ShowExceptionFrame extends Link<ShowWidget, ButtonPressed, ExceptionsManager> {
	/**
	 * Creates the link.
	 */
	protected ButtonPress2ShowExceptionFrame(final ExceptionsManager ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ShowWidget.class, ButtonPressed.class);
	}

	@Override
	public void initAction() {
		action.setComponent(instrument.frame);
		action.setVisible(true);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.exceptionB;
	}
}
