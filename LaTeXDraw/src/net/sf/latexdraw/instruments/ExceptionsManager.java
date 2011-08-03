package net.sf.latexdraw.instruments;

import net.sf.latexdraw.bordel.BordelCollector;
import net.sf.latexdraw.bordel.BordelHandler;
import net.sf.latexdraw.bordel.BordelManager;
import net.sf.latexdraw.util.LResources;
import fr.eseo.malai.action.library.ShowWidget;
import fr.eseo.malai.instrument.Instrument;
import fr.eseo.malai.instrument.Link;
import fr.eseo.malai.interaction.library.ButtonPressed;
import fr.eseo.malai.widget.MButton;

/**
 * This instrument allows to see exceptions launched during the exection of the program.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
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
public class ExceptionsManager extends Instrument implements BordelHandler {
	/** The button used to shows the panel of exceptions. */
	protected MButton exceptionB;

	/** The frame to show when exceptions occur. */
	protected BordelManager frame;


	/**
	 * Creates the instrument.
	 * @since 3.0
	 */
	public ExceptionsManager() {
		super();

		frame		= new BordelManager();
		exceptionB 	= new MButton(LResources.ERR_ICON);
		initialiseLinks();
		setActivated(false);
		BordelCollector.INSTANCE.addHandler(this);
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new ButtonPress2ShowExceptionFrame(this));
		}catch(InstantiationException e){
			BordelCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BordelCollector.INSTANCE.add(e);
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
	public ButtonPress2ShowExceptionFrame(final ExceptionsManager ins) throws InstantiationException, IllegalAccessException {
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
