package net.sf.latexdraw.instruments;

import java.awt.event.KeyEvent;

import org.malai.action.Action;
import org.malai.action.ActionHandler;
import org.malai.action.ActionsRegistry;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.Interaction;
import org.malai.interaction.library.KeysPressure;
import org.malai.interaction.library.MenuItemPressed;
import org.malai.widget.MMenuItem;

import net.sf.latexdraw.actions.CopyShapes;
import net.sf.latexdraw.actions.CutShapes;
import net.sf.latexdraw.actions.PasteShapes;
import net.sf.latexdraw.actions.SelectShapes;
import net.sf.latexdraw.badaboom.BordelCollector;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.util.LResources;

/**
 * This instrument permits to copy, cut and paste the selected shapes.<br>
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
 * 06/03/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class CopierCutterPaster extends Instrument implements ActionHandler {
	/** The menu item to copy the shapes. */
	protected MMenuItem copyMenu;

	/** The menu item to paste the shapes. */
	protected MMenuItem pasteMenu;

	/** The menu item to cut the shapes. */
	protected MMenuItem cutMenu;

	/** The drawing that contains the shapes. */
	protected IDrawing drawing;


	/**
	 * Creates the instrument.
	 * @param drawing The drawing that contains the shapes.
	 * @throws IllegalArgumentException If the given drawing is null.
	 * @since 3.0
	 */
	public CopierCutterPaster(final IDrawing drawing) {
		super();

		if(drawing==null)
			throw new IllegalArgumentException();

		this.drawing = drawing;
		initWidgets();
		initialiseLinks();
		ActionsRegistry.INSTANCE.addHandler(this);
	}


	/**
	 * Initialises the widgets.
	 * @since 3.0
	 */
	protected void initWidgets() {
		copyMenu = new MMenuItem(LResources.LABEL_COPY, KeyEvent.VK_C);
		copyMenu.setIcon(LResources.COPY_ICON);
		copyMenu.setEnabled(false);
		cutMenu = new MMenuItem(LResources.LABEL_CUT, KeyEvent.VK_X);
		cutMenu.setIcon(LResources.CUT_ICON);
		cutMenu.setEnabled(false);
		pasteMenu = new MMenuItem(LResources.LABEL_PASTE, KeyEvent.VK_V);
		pasteMenu.setIcon(LResources.PASTE_ICON);
		pasteMenu.setEnabled(false);
	}



	@Override
	protected void initialiseLinks() {
		try{
			links.add(new MenuItem2PasteShapes(this));
			links.add(new Shortcut2PasteShapes(this));
			links.add(new MenuItem2CopyShapes(this));
			links.add(new Shortcut2CopyShapes(this));
			links.add(new MenuItem2CutShapes(this));
			links.add(new Shortcut2CutShapes(this));
		}catch(InstantiationException e){
			BordelCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BordelCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The menu item used to copy selected shapes.
	 * @since 3.0
	 */
	public MMenuItem getCopyMenu() {
		return copyMenu;
	}


	/**
	 * @return The menu item used to paste selected shapes.
	 * @since 3.0
	 */
	public MMenuItem getPasteMenu() {
		return pasteMenu;
	}


	/**
	 * @return The menu item used to cut selected shapes.
	 * @since 3.0
	 */
	public MMenuItem getCutMenu() {
		return cutMenu;
	}


	@Override
	public void onAction(final Action action, final ActionEvent evt) {
		//
	}


	@Override
	public void onActionExecuted(final Action action) {
		if(action instanceof CopyShapes)
			pasteMenu.setEnabled(true);
		else if(action instanceof SelectShapes) {
			final boolean empty = ((SelectShapes)action).getShapes().isEmpty();
			copyMenu.setEnabled(!empty);
			cutMenu.setEnabled(!empty);
		}
	}
}


/**
 * This link maps an menu item interaction to an action dedicated to a shape copy.
 */
abstract class Interaction2AbstractCopy<A extends CopyShapes, I extends Interaction> extends Link<A, I, CopierCutterPaster> {
	/**
	 * Creates the link.
	 */
	public Interaction2AbstractCopy(final CopierCutterPaster ins, final Class<A> classAction,
								 final Class<I> classInteraction) throws InstantiationException, IllegalAccessException {
		super(ins, false, classAction, classInteraction);
	}

	@Override
	public void initAction() {
		Action act = ActionsRegistry.INSTANCE.getAction(SelectShapes.class);

		if(act instanceof SelectShapes)
			action.setSelection((SelectShapes)act);
	}
}


/**
 * This link maps a shortcut interaction to a copy action.
 */
class Shortcut2CopyShapes extends Interaction2AbstractCopy<CopyShapes, KeysPressure> {
	/**
	 * Creates the link.
	 */
	public Shortcut2CopyShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, CopyShapes.class, KeysPressure.class);
	}

	@Override
	public boolean isConditionRespected() {
		return getInteraction().getKeys().size()==2 && getInteraction().getKeys().contains(KeyEvent.VK_C) &&
				getInteraction().getKeys().contains(KeyEvent.VK_CONTROL);
	}
}


/**
 * This link maps a shortcut interaction to a cut action.
 */
class Shortcut2CutShapes extends Interaction2AbstractCopy<CutShapes, KeysPressure> {
	/**
	 * Creates the link.
	 */
	public Shortcut2CutShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, CutShapes.class, KeysPressure.class);
	}

	@Override
	public boolean isConditionRespected() {
		return getInteraction().getKeys().size()==2 && getInteraction().getKeys().contains(KeyEvent.VK_X) &&
				getInteraction().getKeys().contains(KeyEvent.VK_CONTROL);
	}
}


/**
 * This link maps an menu item interaction to a cut action.
 */
class MenuItem2CutShapes extends Interaction2AbstractCopy<CutShapes, MenuItemPressed> {
	/**
	 * Creates the link.
	 */
	public MenuItem2CutShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, CutShapes.class, MenuItemPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		Action act = ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
		boolean okSelection = act instanceof SelectShapes && !((SelectShapes)act).getShapes().isEmpty();

		return okSelection && getInteraction().getMenuItem()==getInstrument().cutMenu;
	}
}



/**
 * This link maps an menu item interaction to a copy action.
 */
class MenuItem2CopyShapes extends Interaction2AbstractCopy<CopyShapes, MenuItemPressed> {
	/**
	 * Creates the link.
	 */
	public MenuItem2CopyShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, CopyShapes.class, MenuItemPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		Action act = ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
		boolean okSelection = act instanceof SelectShapes && !((SelectShapes)act).getShapes().isEmpty();

		return okSelection && getInteraction().getMenuItem()==getInstrument().copyMenu;
	}
}


/**
 * This abstract link maps an interaction to a paste action.
 */
abstract class Interaction2PasteShapes<I extends Interaction> extends Link<PasteShapes, I, CopierCutterPaster> {
	/**
	 * Creates the link.
	 */
	public Interaction2PasteShapes(final CopierCutterPaster ins, final Class<I> clazzInteraction) throws InstantiationException, IllegalAccessException {
		super(ins, false, PasteShapes.class, clazzInteraction);
	}

	@Override
	public void initAction() {
		Action act = ActionsRegistry.INSTANCE.getAction(CopyShapes.class);

		if(act==null)
			act = ActionsRegistry.INSTANCE.getAction(CutShapes.class);

		if(act instanceof CopyShapes) {
			action.setCopy((CopyShapes)act);
			action.setDrawing(instrument.drawing);
		}
	}
}


/**
 * This link maps a shortcut interaction to a paste action.
 */
class Shortcut2PasteShapes extends Interaction2PasteShapes<KeysPressure> {
	/**
	 * Creates the link.
	 */
	public Shortcut2PasteShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, KeysPressure.class);
	}

	@Override
	public boolean isConditionRespected() {
		return getInteraction().getKeys().size()==2 && getInteraction().getKeys().contains(KeyEvent.VK_V) &&
				getInteraction().getKeys().contains(KeyEvent.VK_CONTROL);
	}
}


/**
 * This link maps an menu item interaction to paste action.
 */
class MenuItem2PasteShapes extends Interaction2PasteShapes<MenuItemPressed> {
	/**
	 * Creates the link.
	 */
	public MenuItem2PasteShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, MenuItemPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		return getInteraction().getMenuItem()==getInstrument().pasteMenu &&
				(ActionsRegistry.INSTANCE.getAction(CopyShapes.class)!=null || ActionsRegistry.INSTANCE.getAction(CutShapes.class)!=null);
	}
}

