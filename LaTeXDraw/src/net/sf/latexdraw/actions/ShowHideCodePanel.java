package net.sf.latexdraw.actions;

import net.sf.latexdraw.instruments.CodePanelActivator;

/**
 * This action modifies the visibility of the code panel.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/21/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShowHideCodePanel extends ShowHideAction {
	/** The instrument that allows to (des-)activate the code panel. */
	protected CodePanelActivator codePanelActivator;


	/**
	 * Creates the action.
	 * @since 3.0
	 */
	public ShowHideCodePanel() {
		super();
	}


	@Override
	public void flush() {
		super.flush();
		codePanelActivator = null;
	}


	@Override
	public boolean canDo() {
		return codePanelActivator!=null;
	}


	@Override
	protected void doActionBody() {
		if(!show)
			codePanelActivator.setDividerPosition(codePanelActivator.getSplitPane().getDividerLocation()/(double)codePanelActivator.getSplitPane().getWidth());

		codePanelActivator.getCodePanel().setVisible(show);
	}


	@Override
	public boolean isRegisterable() {
		return false;
	}


	/**
	 * Sets the code panel activator.
	 * @param activator The activator.
	 * @since 3.0
	 */
	public void setCodePanelActivator(final CodePanelActivator activator) {
		codePanelActivator = activator;
	}
}
