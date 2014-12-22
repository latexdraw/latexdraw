package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import net.sf.latexdraw.actions.shape.CopyShapes;
import net.sf.latexdraw.actions.shape.CutShapes;
import net.sf.latexdraw.actions.shape.SelectShapes;

import org.malai.action.Action;
import org.malai.action.ActionsRegistry;

/**
 * This instrument permits to copy, cut and paste the selected shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 06/03/2011<br>
 * 
 * @author Arnaud BLOUIN, Jan-Cornelius MOLNAR
 * @since 3.0
 */
public class CopierCutterPaster extends CanvasInstrument {
	/** The menu item to copy the shapes. */
	@FXML protected MenuItem copyMenu;

	/** The menu item to paste the shapes. */
	@FXML protected MenuItem pasteMenu;

	/** The menu item to cut the shapes. */
	@FXML protected MenuItem cutMenu;

	/**
	 * Creates the instrument.
	 */
	public CopierCutterPaster() {
		super();
		ActionsRegistry.INSTANCE.addHandler(this);
	}

	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		updateWidgets(null);
	}

	/**
	 * Updates the widgets of the instrument.
	 * 
	 * @param executedAction
	 *            The action currently executed. Can be null.
	 * @since 3.0
	 */
	protected void updateWidgets(final Action executedAction) {
		final SelectShapes sa = ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
		final boolean validSelectAction = sa!=null&&!sa.shapes().isEmpty();

		copyMenu.setDisable(!activated||!validSelectAction);
		cutMenu.setDisable(!activated||copyMenu.isDisable());
		pasteMenu
				.setDisable(!activated||!(executedAction instanceof CopyShapes||ActionsRegistry.INSTANCE.getAction(CopyShapes.class)!=null||ActionsRegistry.INSTANCE.getAction(CutShapes.class)!=null));
	}

	@Override
	protected void initialiseInteractors() {
		// try{
		// addInteractor(new MenuItem2PasteShapes(this));
		// addInteractor(new Shortcut2PasteShapes(this));
		// addInteractor(new MenuItem2CopyShapes(this));
		// addInteractor(new Shortcut2CopyShapes(this));
		// addInteractor(new MenuItem2CutShapes(this));
		// addInteractor(new Shortcut2CutShapes(this));
		// }catch(InstantiationException | IllegalAccessException e){
		// BadaboomCollector.INSTANCE.add(e);
		// }
	}

	@Override
	public void onActionAdded(final Action action) {
		updateWidgets(action);
	}
}

/**
 * // * This link maps an menu item interaction to an action dedicated to a
 * shape copy. //
 */
// abstract class Interaction2AbstractCopy<A extends CopyShapes, I extends
// Interaction> extends InteractorImpl<A, I, CopierCutterPaster> {
// /**
// * Creates the link.
// */
// protected Interaction2AbstractCopy(final CopierCutterPaster ins, final
// Class<A> classAction,
// final Class<I> classInteraction) throws InstantiationException,
// IllegalAccessException {
// super(ins, false, classAction, classInteraction);
// }
//
// @Override
// public void initAction() {
// final SelectShapes act =
// ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
// if(act!=null)
// action.setSelection(act);
// }
// }
//
//
// /**
// * This link maps a shortcut interaction to a copy action.
// */
// class Shortcut2CopyShapes extends Interaction2AbstractCopy<CopyShapes,
// KeysPressure> {
// /**
// * Creates the link.
// */
// protected Shortcut2CopyShapes(final CopierCutterPaster ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, CopyShapes.class, KeysPressure.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// return getInteraction().getKeys().size()==2 &&
// getInteraction().getKeys().contains(KeyEvent.VK_C) &&
// getInteraction().getKeys().contains(LSystem.INSTANCE.getControlKey());
// }
// }
//
//
// /**
// * This link maps a shortcut interaction to a cut action.
// */
// class Shortcut2CutShapes extends Interaction2AbstractCopy<CutShapes,
// KeysPressure> {
// /**
// * Creates the link.
// */
// protected Shortcut2CutShapes(final CopierCutterPaster ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, CutShapes.class, KeysPressure.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// return getInteraction().getKeys().size()==2 &&
// getInteraction().getKeys().contains(KeyEvent.VK_X) &&
// getInteraction().getKeys().contains(LSystem.INSTANCE.getControlKey());
// }
// }
//
//
// /**
// * This link maps an menu item interaction to a cut action.
// */
// class MenuItem2CutShapes extends Interaction2AbstractCopy<CutShapes,
// MenuItemPressed> {
// /**
// * Creates the link.
// */
// protected MenuItem2CutShapes(final CopierCutterPaster ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, CutShapes.class, MenuItemPressed.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// final SelectShapes act =
// ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
// return act != null && !act.shapes().isEmpty() &&
// getInteraction().getMenuItem()==getInstrument().cutMenu;
// }
// }
//
//
//
// /**
// * This link maps an menu item interaction to a copy action.
// */
// class MenuItem2CopyShapes extends Interaction2AbstractCopy<CopyShapes,
// MenuItemPressed> {
// /**
// * Creates the link.
// */
// protected MenuItem2CopyShapes(final CopierCutterPaster ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, CopyShapes.class, MenuItemPressed.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// final SelectShapes act =
// ActionsRegistry.INSTANCE.getAction(SelectShapes.class);
// final boolean okSelection = act != null && !act.shapes().isEmpty();
// return okSelection &&
// getInteraction().getMenuItem()==getInstrument().copyMenu;
// }
// }
//
//
// /**
// * This abstract link maps an interaction to a paste action.
// */
// abstract class Interaction2PasteShapes<I extends Interaction> extends
// InteractorImpl<PasteShapes, I, CopierCutterPaster> {
// /**
// * Creates the link.
// */
// protected Interaction2PasteShapes(final CopierCutterPaster ins, final
// Class<I> clazzInteraction) throws InstantiationException,
// IllegalAccessException {
// super(ins, false, PasteShapes.class, clazzInteraction);
// }
//
// @Override
// public void initAction() {
// CopyShapes act = ActionsRegistry.INSTANCE.getAction(CopyShapes.class);
//
// if(act==null)
// act = ActionsRegistry.INSTANCE.getAction(CutShapes.class);
//
// if(act != null) {
// action.setCopy(act);
// action.setDrawing(instrument.drawing);
// action.setGrid(instrument.grid);
// }
// }
// }
//
//
// /**
// * This link maps a shortcut interaction to a paste action.
// */
// class Shortcut2PasteShapes extends Interaction2PasteShapes<KeysPressure> {
// /**
// * Creates the link.
// */
// protected Shortcut2PasteShapes(final CopierCutterPaster ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, KeysPressure.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// return getInteraction().getKeys().size()==2 &&
// getInteraction().getKeys().contains(KeyEvent.VK_V) &&
// getInteraction().getKeys().contains(LSystem.INSTANCE.getControlKey());
// }
// }
//
//
// /**
// * This link maps an menu item interaction to paste action.
// */
// class MenuItem2PasteShapes extends Interaction2PasteShapes<MenuItemPressed> {
// /**
// * Creates the link.
// */
// protected MenuItem2PasteShapes(final CopierCutterPaster ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, MenuItemPressed.class);
// }
//
// @Override
// public boolean isConditionRespected() {
// return getInteraction().getMenuItem()==getInstrument().pasteMenu &&
// (ActionsRegistry.INSTANCE.getAction(CopyShapes.class)!=null ||
// ActionsRegistry.INSTANCE.getAction(CutShapes.class)!=null);
// }
// }
