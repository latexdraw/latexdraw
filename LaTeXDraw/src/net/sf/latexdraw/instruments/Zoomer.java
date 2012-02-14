package net.sf.latexdraw.instruments;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

import org.malai.action.library.Zoom;
import org.malai.instrument.Instrument;
import org.malai.instrument.Link;
import org.malai.interaction.library.ButtonPressed;
import org.malai.interaction.library.KeysScrolling;
import org.malai.interaction.library.SpinnerModified;
import org.malai.properties.Zoomable;
import org.malai.widget.MButton;
import org.malai.widget.MSpinner;

/**
 * This instrument allows to zoom on the canvas.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
 * 05/20/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class Zoomer extends Instrument {
	/** The spinner that helps to change the zoom. */
	protected MSpinner zoomSpinner;

	/** This button allows to set the default zoom level. */
	protected MButton zoomDefaultButton;

	/** The canvas to zoom in/out. */
	protected Zoomable zoomable;


	/**
	 * Creates and initialises the zoomer.
	 * @param zoomable The zoomable object to zoom in/out.
	 * @throws IllegalArgumentException If the given canvas is null;
	 * @since 3.0
	 */
	public Zoomer(final Zoomable zoomable) {
		super();

		if(zoomable==null)
			throw new IllegalArgumentException();

		this.zoomable = zoomable;

		zoomDefaultButton = new MButton(LResources.ZOOM_DEFAULT_ICON);
		zoomDefaultButton.setToolTipText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.113")); //$NON-NLS-1$

		zoomSpinner = new MSpinner(new MSpinner.MSpinnerNumberModel(zoomable.getZoom()*100., Zoomable.MIN_ZOOM*100., Zoomable.MAX_ZOOM*100., Zoomable.ZOOM_INCREMENT*100.), null);
		zoomSpinner.setMaximumSize(new Dimension(55, 28));
		zoomSpinner.setToolTipText(LangTool.INSTANCE.getString19("ShortcutsFrame.30"));//$NON-NLS-1$
	}


	@Override
	public void reinit() {
		zoomSpinner.setValueSafely(zoomable.getZoom()*100);
	}


	@Override
	protected void initialiseLinks() {
		try{
			addLink(new Scroll2Zoom(this));
			addLink(new Spinner2Zoom(this));
			addLink(new Button2Zoom(this));
		}catch(InstantiationException e){
			BadaboomCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	@Override
	public void interimFeedback() {
		zoomSpinner.setValueSafely(zoomable.getZoom()*100);
	}


	/**
	 * @return The button that sets the zoom to its default value.
	 * @since 3.0
	 */
	public MButton getZoomDefaultButton() {
		return zoomDefaultButton;
	}


	/**
	 * @return The spinner that modifies the zoom level.
	 * @since 3.0
	 */
	public MSpinner getZoomSpinner() {
		return zoomSpinner;
	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		zoomDefaultButton.setVisible(activated);
		zoomSpinner.setVisible(activated);
	}
}


/**
 * This link maps a scroll interaction to a zoom action.
 */
class Scroll2Zoom extends Link<Zoom, KeysScrolling, Zoomer> {
	/**
	 * Creates the action.
	 */
	protected Scroll2Zoom(final Zoomer ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, Zoom.class, KeysScrolling.class);
	}

	@Override
	public void initAction() {
		action.setZoomable(instrument.zoomable);
	}

	@Override
	public void updateAction() {
		action.setZoomLevel(instrument.zoomable.getZoom() + (interaction.getIncrement()>0 ? Zoomable.ZOOM_INCREMENT : -Zoomable.ZOOM_INCREMENT));
	}

	@Override
	public boolean isConditionRespected() {
		final List<Integer> keys = interaction.getKeys();
		return keys.size()==1 && keys.get(0)==KeyEvent.VK_CONTROL;
	}
}



/**
 * This link maps a button that changes the zoom to a button-pressed interaction.
 */
class Button2Zoom extends Link<Zoom, ButtonPressed, Zoomer> {
	/**
	 * Initialises the link.
	 * @param ins The zoomer.
	 */
	protected Button2Zoom(final Zoomer ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, Zoom.class, ButtonPressed.class);
	}


	@Override
	public void initAction() {
		action.setZoomable(instrument.zoomable);
		action.setZoomLevel(1.);
	}


	@Override
	public boolean isConditionRespected() {
		return instrument.zoomDefaultButton==interaction.getButton();
	}
}



/**
 * The links maps the zoom spinner to the zoom action.
 */
class Spinner2Zoom extends Link<Zoom, SpinnerModified, Zoomer> {
	/**
	 * Initialises the link.
	 * @param ins The zoomer.
	 */
	protected Spinner2Zoom(final Zoomer ins) throws InstantiationException, IllegalAccessException {
		super(ins, true, Zoom.class, SpinnerModified.class);
	}


	@Override
	public void initAction() {
		action.setZoomable(instrument.zoomable);

	}


	@Override
	public void updateAction() {
		action.setZoomLevel(Double.valueOf(interaction.getSpinner().getValue().toString())/100.);
	}


	@Override
	public boolean isConditionRespected() {
		return instrument.zoomSpinner==interaction.getSpinner();
	}
}
