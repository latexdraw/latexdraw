package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.ui.ScaleRuler.Unit;
import net.sf.latexdraw.ui.XScaleRuler;
import net.sf.latexdraw.ui.YScaleRuler;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;

import org.malai.javafx.instrument.JfxInstrument;
import org.malai.undo.Undoable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This instrument activates X and Y scale rulers.<br>
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
 * 11/12/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class ScaleRulersCustomiser extends JfxInstrument {
	/** The x ruler of the system. */
	protected XScaleRuler xRuler;

	/** The Y ruler of the system. */
	protected YScaleRuler yRuler;

	/** The menu for the centimetre unit. */
	@FXML protected RadioMenuItem unitCmItem;

	/** The menu for the inch unit. */
	@FXML protected RadioMenuItem unitInchItem;


//	/**
//	 * Creates the instrument.
//	 * @param xRuler The x ruler of the system.
//	 * @param yRuler The Y ruler of the system.
//	 * @throws IllegalArgumentException If one of the given rulers is null.
//	 * @since 3.0
//	 */
//	public ScaleRulersCustomiser(final XScaleRuler xRuler, final YScaleRuler yRuler) {
//		super();
//
//		this.xRuler = Objects.requireNonNull(xRuler);
//		this.yRuler = Objects.requireNonNull(yRuler);
//
//		// Mapping the instrument to the widgets that produce interactions that concerns its links.
//		addEventable(this.xRuler);
//		addEventable(this.yRuler);
//	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		unitCmItem.setDisable(!activated);
		unitInchItem.setDisable(!activated);
	}


	@Override
	public void interimFeedback() {
		update();
	}


	@Override
	public void onUndoableUndo(final Undoable undoable) {
		super.onUndoableUndo(undoable);
		update();
	}


	@Override
	public void onUndoableRedo(final Undoable undoable) {
		super.onUndoableRedo(undoable);
		update();
	}


	protected void update() {
		unitCmItem.setSelected(ScaleRuler.getUnit()==Unit.CM);
		unitInchItem.setSelected(!unitCmItem.isSelected());
	}


	@Override
	protected void initialiseInteractors() {
//		try{
//			addInteractor(new MenuItem2SetUnit(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}


	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element root) {
		super.load(generalPreferences, nsURI, root);

		final String name = root.getNodeName();

		if(name.endsWith(LNamespace.XML_UNIT)) {
			final String unit = root.getTextContent();
			final boolean isCm = Unit.CM.toString().equals(unit) || unitCmItem.getText().equals(unit);
			unitCmItem.setSelected(isCm);
			unitInchItem.setSelected(!isCm);
			ScaleRuler.setUnit(isCm ? Unit.CM : Unit.INCH);
		}
	}


	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

		if(document==null || root==null)
			return ;

		Element elt;
		final String ns = generalPreferences ? "" : LPath.INSTANCE.getNormaliseNamespaceURI(nsURI); //$NON-NLS-1$
		elt = document.createElement(ns + LNamespace.XML_UNIT);
        elt.setTextContent(String.valueOf(unitCmItem.isSelected() ? Unit.CM : Unit.INCH));
        root.appendChild(elt);
	}
}



///**
// * This link maps a menu item to an action that sets the unit of the rulers.
// */
//class MenuItem2SetUnit extends InteractorImpl<SetUnit, MenuItemPressed, ScaleRulersCustomiser> {
//	/**
//	 * Initialises the link.
//	 * @param ins The rulers activator.
//	 */
//	protected MenuItem2SetUnit(final ScaleRulersCustomiser ins) throws InstantiationException, IllegalAccessException {
//		super(ins, false, SetUnit.class, MenuItemPressed.class);
//	}
//
//	@Override
//	public void initAction() {
//		action.setUnit(interaction.getMenuItem()==instrument.unitCmItem ? Unit.CM : Unit.INCH);
//	}
//
//	@Override
//	public boolean isConditionRespected() {
//		final JMenuItem item = interaction.getMenuItem();
//		return item==instrument.unitCmItem || item==instrument.unitInchItem;
//	}
//}
