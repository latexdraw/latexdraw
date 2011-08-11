package net.sf.latexdraw.instruments;

import javax.swing.JSplitPane;

import net.sf.latexdraw.actions.ShowHideCodePanel;
import net.sf.latexdraw.bordel.BordelCollector;
import net.sf.latexdraw.ui.CloseButton;
import net.sf.latexdraw.ui.LCodePanel;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LResources;

import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.library.ButtonPressed;
import org.malai.interaction.library.MenuItemPressed;
import org.malai.widget.MCheckBoxMenuItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * This instrument activates the code panel.<br>
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
 * 05/13/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class CodePanelActivator extends Instrument {
	/** The code panel that must be activated or not. */
	protected LCodePanel codePanel;

	/** The split pane that contains the code panel. */
	protected JSplitPane splitPane;

	/** The close button of the code panel. */
	protected CloseButton closeButton;

	/** The menu item that allows the hide or show the code panel. */
	protected MCheckBoxMenuItem closeMenuItem;

	/** The position of the split pane divider ]0; 1]. */
	protected double dividerPosition;

	/** Defines if by default the code panel must be visible or not. */
	protected boolean defaultCodePanelVisible;


	/**
	 * Creates the instrument.
	 * @param codePanel The panel that contains the code.
	 * @param splitPane The panel that contains the code panel.
	 * @throw IllegalArgumentException If one of the given attributes is null.
	 * @since 3.0
	 */
	public CodePanelActivator(final LCodePanel codePanel, final JSplitPane splitPane) {
		super();

		if(codePanel==null || splitPane==null)
			throw new IllegalArgumentException();

		this.splitPane	= splitPane;
		this.codePanel 	= codePanel;
		closeButton		= new CloseButton();
		closeMenuItem	= new MCheckBoxMenuItem(LResources.LABEL_DISPLAY_CODE_PANEL, LResources.EMPTY_ICON);

		reinit();
		initialiseLinks();
	}


	@Override
	public void reinit() {
		dividerPosition	= 0.7;
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new ButtonPressed2CloseCodePanel(this));
			links.add(new MenuItem2ShowHideCodePanel(this));
		}catch(InstantiationException e){
			BordelCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BordelCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The close button of the code panel.
	 * @since 3.0
	 */
	public CloseButton getCloseButton() {
		return closeButton;
	}


	/**
	 * @return The menu item that allows the hide or show the code panel.
	 * @since 3.0
	 */
	public MCheckBoxMenuItem getCloseMenuItem() {
		return closeMenuItem;
	}


	/**
	 * @return The code panel that the activator manages.
	 * @since 3.0
	 */
	public LCodePanel getCodePanel() {
		return codePanel;
	}


	/**
	 * @return The split pane that contains the code panel.
	 * @since 3.0
	 */
	public JSplitPane getSplitPane() {
		return splitPane;
	}


	/**
	 * @return The position of the split pane divider ]0; 1].
	 * @since 3.0
	 */
	public double getDividerPosition() {
		return dividerPosition;
	}


	/**
	 * Sets the position of the split pane divider ]0; 1].
	 * @param dividerPosition The new position of the split pane divider.
	 * @since 3.0
	 */
	public void setDividerPosition(final double dividerPosition) {
		if(dividerPosition>0. && dividerPosition<=1.)
			this.dividerPosition = dividerPosition;
	}



	@Override
	public void interimFeedback() {
		if(codePanel.isVisible()) {
			splitPane.setDividerLocation(dividerPosition);
			splitPane.revalidate();
		}
	}


	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element root) {
		super.load(generalPreferences, nsURI, root);

		final String name = root.getNodeName();

		if(name.endsWith(LNamespace.XML_DIVIDER_POSITION))
			dividerPosition = Double.valueOf(root.getTextContent());
		else if(name.endsWith(LNamespace.XML_DISPLAY_CODE_PANEL)) {
			codePanel.setVisible(Boolean.valueOf(root.getTextContent()));
			closeMenuItem.setSelected(codePanel.isVisible());
		}
	}



	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

		if(document==null || root==null)
			return ;

		Element elt;

		if(generalPreferences) {
			elt = document.createElement(LNamespace.XML_DIVIDER_POSITION);
            elt.setTextContent(String.valueOf(dividerPosition));
            root.appendChild(elt);
	        elt = document.createElement(LNamespace.XML_DISPLAY_CODE_PANEL);
	        elt.setTextContent(String.valueOf(defaultCodePanelVisible));
	        root.appendChild(elt);
		} else {
			final String ns = LPath.INSTANCE.getNormaliseNamespaceURI(nsURI);

			elt = document.createElement(ns + LNamespace.XML_DIVIDER_POSITION);
	        elt.setTextContent(String.valueOf(dividerPosition));
	        root.appendChild(elt);
	        elt = document.createElement(ns + LNamespace.XML_DISPLAY_CODE_PANEL);
	        elt.setTextContent(String.valueOf(codePanel.isVisible()));
	        root.appendChild(elt);
		}
	}



	/**
	 * @return True: By default the code panel must be visible.
	 * @since 3.0
	 */
	public boolean isDefaultCodePanelVisible() {
		return defaultCodePanelVisible;
	}



	/**
	 * @param defaultCodePanelVisible Defines if by default the code panel must be visible or not.
	 * @since 3.0
	 */
	public void setDefaultCodePanelVisible(final boolean defaultCodePanelVisible) {
		this.defaultCodePanelVisible = defaultCodePanelVisible;
	}
}



/**
 * This link maps a menu item to an action that shows/hides the code panel.
 */
class MenuItem2ShowHideCodePanel extends Link<ShowHideCodePanel, MenuItemPressed, CodePanelActivator> {
	/**
	 * Initialises the link.
	 * @param ins The code panel activator.
	 */
	public MenuItem2ShowHideCodePanel(final CodePanelActivator ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ShowHideCodePanel.class, MenuItemPressed.class);
	}


	@Override
	public void initAction() {
		action.setCodePanelActivator(instrument);
		action.setVisible(interaction.getMenuItem().isSelected());
	}

	@Override
	public boolean isConditionRespected() {
		return interaction.getMenuItem()==instrument.closeMenuItem;
	}
}




/**
 * This links maps a close button to the action close/show code panel.
 */
class ButtonPressed2CloseCodePanel extends Link<ShowHideCodePanel, ButtonPressed, CodePanelActivator> {
	/**
	 * Initialises the link.
	 * @param ins The code panel activator.
	 */
	public ButtonPressed2CloseCodePanel(final CodePanelActivator ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ShowHideCodePanel.class, ButtonPressed.class);
	}


	@Override
	public void initAction() {
		action.setCodePanelActivator(instrument);
		action.setVisible(false);
	}


	@Override
	public boolean isConditionRespected() {
		return interaction.getButton()==instrument.closeButton;
	}
}
