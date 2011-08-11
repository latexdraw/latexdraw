package net.sf.latexdraw.instruments;

import java.awt.event.KeyEvent;
import java.util.List;

import org.malai.action.ActionsRegistry;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.Interaction;
import org.malai.interaction.library.ButtonPressed;
import org.malai.interaction.library.KeyPressure;
import org.malai.widget.MButton;

import net.sf.latexdraw.actions.DeleteShape;
import net.sf.latexdraw.actions.SelectShapes;
import net.sf.latexdraw.bordel.BordelCollector;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

/**
 * This instrument deletes the selected shapes.<br>
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
 * 01/05/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeDeleter extends Instrument {
	/** The button used to remove the selected shapes. */
	protected MButton deleteB;
	
	
	/**
	 * Creates the instrument. 
	 * @since 3.0
	 */
	public ShapeDeleter() {
		super();
		
		deleteB = new MButton(LResources.DEL_ICON);
		deleteB.setToolTipText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.139")); //$NON-NLS-1$
		initialiseLinks();
	}

	
	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		deleteB.setEnabled(activated);
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new ButtonPressed2DeleteShapes(this));
			links.add(new KeyPressed2DeleteShapes(this));
		}catch(InstantiationException e){
			BordelCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BordelCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The button used to remove the selected shapes.
	 * @since 3.0
	 */
	public MButton getDeleteB() {
		return deleteB;
	}
}

/**
 * This abstract link maps an interaction to an action that delete shapes.
 */
abstract class DeleteShapesLink<I extends Interaction> extends Link<DeleteShape, I, ShapeDeleter> {
	/**
	 * Creates the link.
	 */
	public DeleteShapesLink(final ShapeDeleter ins, final Class<I> clazzInteraction) throws InstantiationException, IllegalAccessException {
		super(ins, false, DeleteShape.class, clazzInteraction);
	}
	
	
	@Override
	public void initAction() {
		final SelectShapes selection = ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
		final List<IShape> shapes 	 = selection.getShapes();
		
		for(final IShape sh : shapes)
			action.addShape(sh);
		
		action.setDrawing(selection.getDrawing());
	}
	
	
	@Override
	public boolean isConditionRespected() {
		final SelectShapes selection = ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
		return selection!=null && !selection.getShapes().isEmpty();
	}
}


/**
 * This link maps an key pressure interaction to an action that delete shapes.
 */
class KeyPressed2DeleteShapes extends DeleteShapesLink<KeyPressure> {
	/**
	 * Creates the link.
	 */
	public KeyPressed2DeleteShapes(final ShapeDeleter ins) throws InstantiationException, IllegalAccessException {
		super(ins, KeyPressure.class);
	}

	@Override
	public boolean isConditionRespected() {
		return super.isConditionRespected() && interaction.getKey()==KeyEvent.VK_DELETE;
	}
}


/**
 * This link maps an button pressure interaction to an action that delete shapes.
 */
class ButtonPressed2DeleteShapes extends DeleteShapesLink<ButtonPressed> {
	/**
	 * Creates the link.
	 */
	public ButtonPressed2DeleteShapes(final ShapeDeleter ins) throws InstantiationException, IllegalAccessException {
		super(ins, ButtonPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.deleteB && super.isConditionRespected();
	}
}
