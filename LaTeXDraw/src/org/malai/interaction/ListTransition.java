package org.malai.interaction;

import java.awt.ItemSelectable;

import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;



/**
 * This transition is mapped to a list (ItemSelectable) that has been modified.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 05/19/2010<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class ListTransition extends Transition {
	/** The list of objects which selection has been modified. */
	protected ItemSelectable list;


	/**
	 * {@link Transition#Transition(SourceableState, TargetableState)}
	 * @since 0.2
	 */
	public ListTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}


	/**
	 * @return The modified list.
	 * @since 0.2
	 */
	public ItemSelectable getList() {
		return list;
	}


	/**
	 * Sets the modified list.
	 * @param list The modified list.
	 * @since 0.2
	 */
	public void setList(final ItemSelectable list) {
		if(list!=null)
			this.list = list;
	}

}
