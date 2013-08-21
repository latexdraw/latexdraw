package net.sf.latexdraw.mapping;

import java.util.List;
import java.util.Objects;

import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.instruments.Exporter;

import org.malai.mapping.IMapping;
import org.malai.mapping.IUnary;

/**
 * Creates a mapping between a list of shapes and the instrument exporter.<br>
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
 * 05/24/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeList2ExporterMapping implements IMapping {
	/** The list of shapes to listen. */
	protected List<IShape> shapes;

	/** The instrument exporter to update. */
	protected Exporter exporter;


	/**
	 * Creates a mapping between a list of shapes and the instrument exporter.
	 * @param shapes The list of shapes.
	 * @param exporter The instrument exporter to update.
	 * @throws IllegalArgumentException If one of the given parameters is null.
	 * @since 3.0
	 */
	public ShapeList2ExporterMapping(final List<IShape> shapes, final Exporter exporter) {
		super();
		this.shapes 	= Objects.requireNonNull(shapes);
		this.exporter 	= Objects.requireNonNull(exporter);
	}


	/**
	 * Updates the activation of the instrument exporter.
	 * @since 3.0
	 */
	protected void updateExporter() {
		exporter.setActivated(!shapes.isEmpty());
	}


	@Override
	public void onObjectAdded(final Object list, final Object object, final int index) {
		updateExporter();
	}



	@Override
	public void onObjectRemoved(final Object list, final Object object, final int index) {
		updateExporter();
	}


	@Override
	public void onListCleaned(final Object list) {
		updateExporter();
	}



	@Override
	public void onObjectMoved(final Object list, final Object object, final int srcIndex, final int targetIndex) {
		//
	}



	@Override
	public void onObjectReplaced(final IUnary<?> object, final Object replacedObject) {
		//
	}



	@Override
	public void onObjectModified(final Object object) {
		//
	}


	@Override
	public void init() {
		updateExporter();
	}


	@Override
	public void clear() {
		updateExporter();
	}


	@Override
	public List<IShape> getSource() {
		return shapes;
	}


	@Override
	public Exporter getTarget() {
		return exporter;
	}
}
