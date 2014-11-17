package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.badaboom.BadaboomHandler;
import net.sf.latexdraw.badaboom.BadaboomManager;

import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument allows to see exceptions launched during the execution of the program.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
public class ExceptionsManager extends JfxInstrument implements BadaboomHandler, Initializable {
	/** The button used to shows the panel of exceptions. */
	@FXML protected Button exceptionB;

	/** The frame to show when exceptions occur. */
	private BadaboomManager frame;


	/**
	 * Creates the instrument.
	 * @since 3.0
	 */
	public ExceptionsManager() {
		super();
		BadaboomCollector.INSTANCE.addHandler(this);
	}
	
	/**
	 * @return The frame showing the exceptions. Cannot be null.
	 */
	public BadaboomManager getFrame() {
		if(frame==null) frame = new BadaboomManager();
		return frame;
	}
	

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		setActivated(false);
	}

	@Override
	protected void initialiseInteractors() {
//		try{
//			addInteractor(new ButtonPress2ShowExceptionFrame(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}


	@Override
	public void notifyEvent(final Throwable ex) {
		setActivated(true);
	}


	@Override
	public void setActivated(final boolean isActivated) {
		super.setActivated(isActivated);
		exceptionB.setVisible(isActivated);
	}

	@Override
	public void notifyEvents() {
		setActivated(true);
	}
}

//
///**
// * Links a button pressed interaction to an action that show the exceptions frame.
// */
//class ButtonPress2ShowExceptionFrame extends InteractorImpl<ShowWidget, ButtonPressed, ExceptionsManager> {
//	/**
//	 * Creates the link.
//	 */
//	protected ButtonPress2ShowExceptionFrame(final ExceptionsManager ins) throws InstantiationException, IllegalAccessException {
//		super(ins, false, ShowWidget.class, ButtonPressed.class);
//	}
//
//	@Override
//	public void initAction() {
//		action.setComponent(instrument.frame);
//		action.setVisible(true);
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		return interaction.getButton()==instrument.exceptionB;
//	}
//}
