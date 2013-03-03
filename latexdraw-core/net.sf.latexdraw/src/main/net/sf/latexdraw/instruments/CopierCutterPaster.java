package net.sf.latexdraw.instruments;

import java.awt.event.KeyEvent;
import java.util.Objects;

import net.sf.latexdraw.actions.shape.CopyShapes;
import net.sf.latexdraw.actions.shape.CutShapes;
import net.sf.latexdraw.actions.shape.PasteShapes;
import net.sf.latexdraw.actions.shape.SelectShapes;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.LSystem;

import org.malai.action.Action;
import org.malai.action.ActionsRegistry;
import org.malai.instrument.Link;
import org.malai.interaction.Interaction;
import org.malai.interaction.library.KeysPressure;
import org.malai.swing.instrument.WidgetInstrument;
import org.malai.swing.interaction.library.MenuItemPressed;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MMenuItem;

/**
 * This instrument permits to copy, cut and paste the selected shapes.<br>
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
 * 06/03/2011<br>
 * @author Arnaud BLOUIN, Jan-Cornelius MOLNAR
 * @since 3.0
 */
public class CopierCutterPaster extends WidgetInstrument {
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
	 * @param composer The composer that manages the widgets of the instrument.
	 * @throws IllegalArgumentException If the given drawing is null.
	 * @since 3.0
	 */
	public CopierCutterPaster(final UIComposer<?> composer, final IDrawing drawing) {
		super(composer);

		this.drawing = Objects.requireNonNull(drawing);
		initialiseWidgets();
		ActionsRegistry.INSTANCE.addHandler(this);
	}


	@Override
	protected void initialiseWidgets() {
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
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		updateWidgets(null);
	}


	/**
	 * Updates the widgets of the instrument.
	 * @param executedAction The action currently executed. Can be null.
	 * @since 3.0
	 */
	protected void updateWidgets(final Action executedAction) {
		boolean validSelectAction = activated;

//		if(activated)
//			if(executedAction instanceof SelectShapes) {
//				validSelectAction = !((SelectShapes)executedAction).getShapes().isEmpty();
//			}else {
				SelectShapes sa = ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
				validSelectAction = sa!=null && !sa.shapes().isEmpty();
//			}

		copyMenu.setEnabled(activated && validSelectAction);
		cutMenu.setEnabled(activated && copyMenu.isEnabled());
		pasteMenu.setEnabled(activated && (executedAction instanceof CopyShapes ||
							ActionsRegistry.INSTANCE.getAction(CopyShapes.class)!=null || ActionsRegistry.INSTANCE.getAction(CutShapes.class)!=null));
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new MenuItem2PasteShapes(this));
			addLink(new Shortcut2PasteShapes(this));
			addLink(new MenuItem2CopyShapes(this));
			addLink(new Shortcut2CopyShapes(this));
			addLink(new MenuItem2CutShapes(this));
			addLink(new Shortcut2CutShapes(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
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
	public void onActionAdded(final Action action) {
		updateWidgets(action);
	}
}


/**
 * This link maps an menu item interaction to an action dedicated to a shape copy.
 */
abstract class Interaction2AbstractCopy<A extends CopyShapes, I extends Interaction> extends Link<A, I, CopierCutterPaster> {
	/**
	 * Creates the link.
	 */
	protected Interaction2AbstractCopy(final CopierCutterPaster ins, final Class<A> classAction,
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
	protected Shortcut2CopyShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, CopyShapes.class, KeysPressure.class);
	}

	@Override
	public boolean isConditionRespected() {
		return getInteraction().getKeys().size()==2 && getInteraction().getKeys().contains(KeyEvent.VK_C) &&
				getInteraction().getKeys().contains(LSystem.INSTANCE.getControlKey());
	}
}


/**
 * This link maps a shortcut interaction to a cut action.
 */
class Shortcut2CutShapes extends Interaction2AbstractCopy<CutShapes, KeysPressure> {
	/**
	 * Creates the link.
	 */
	protected Shortcut2CutShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, CutShapes.class, KeysPressure.class);
	}

	@Override
	public boolean isConditionRespected() {
		return getInteraction().getKeys().size()==2 && getInteraction().getKeys().contains(KeyEvent.VK_X) &&
				getInteraction().getKeys().contains(LSystem.INSTANCE.getControlKey());
	}
}


/**
 * This link maps an menu item interaction to a cut action.
 */
class MenuItem2CutShapes extends Interaction2AbstractCopy<CutShapes, MenuItemPressed> {
	/**
	 * Creates the link.
	 */
	protected MenuItem2CutShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, CutShapes.class, MenuItemPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		Action act = ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
		boolean okSelection = act instanceof SelectShapes && !((SelectShapes)act).shapes().isEmpty();

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
	protected MenuItem2CopyShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, CopyShapes.class, MenuItemPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		Action act = ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
		boolean okSelection = act instanceof SelectShapes && !((SelectShapes)act).shapes().isEmpty();

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
	protected Interaction2PasteShapes(final CopierCutterPaster ins, final Class<I> clazzInteraction) throws InstantiationException, IllegalAccessException {
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
	protected Shortcut2PasteShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, KeysPressure.class);
	}

	@Override
	public boolean isConditionRespected() {
		return getInteraction().getKeys().size()==2 && getInteraction().getKeys().contains(KeyEvent.VK_V) &&
				getInteraction().getKeys().contains(LSystem.INSTANCE.getControlKey());
	}
}


/**
 * This link maps an menu item interaction to paste action.
 */
class MenuItem2PasteShapes extends Interaction2PasteShapes<MenuItemPressed> {
	/**
	 * Creates the link.
	 */
	protected MenuItem2PasteShapes(final CopierCutterPaster ins) throws InstantiationException, IllegalAccessException {
		super(ins, MenuItemPressed.class);
	}

	@Override
	public boolean isConditionRespected() {
		return getInteraction().getMenuItem()==getInstrument().pasteMenu &&
				(ActionsRegistry.INSTANCE.getAction(CopyShapes.class)!=null || ActionsRegistry.INSTANCE.getAction(CutShapes.class)!=null);
	}
}
