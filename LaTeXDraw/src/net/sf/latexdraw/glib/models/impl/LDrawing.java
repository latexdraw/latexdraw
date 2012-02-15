package net.sf.latexdraw.glib.models.impl;

import java.util.List;

import org.malai.mapping.ActiveUnary;
import org.malai.mapping.IUnary;

import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;

/**
 * Defines a drawing that contains a set of shapes and
 * a set of selected shapes. It corresponds to the
 * model of a canvas that contains representations of shapes.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 02/25/2010<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LDrawing extends LGroup implements IDrawing {
	/** The selected shapes of the drawing. */
	protected IGroup selection;

	/** A temporary shape in the drawing. */
	protected IUnary<IShape> tempShape;


	/**
	 * Initialises a drawing.
	 * @since 3.0
	 */
	protected LDrawing() {
		super(false);

		selection 	= new LGroup(false);
		tempShape	= new ActiveUnary<IShape>();
	}


	@Override
	public void addToSelection(final IShape sh) {
		selection.addShape(sh);
	}


	@Override
	public void addToSelection(final List<IShape> newSelection) {
		if(newSelection!=null)
			for(IShape sh : newSelection)
				selection.addShape(sh);
	}


	@Override
	public IGroup getSelection() {
		return selection;
	}


	@Override
	public void removeFromSelection(final IShape sh) {
		selection.removeShape(sh);
	}


	@Override
	public void removeSelection() {
		selection.clear();
	}


	@Override
	public void setSelection(final IShape sh) {
		selection.clear();
		selection.addShape(sh);
	}


	@Override
	public void setSelection(final List<IShape> newSelection) {
		selection.clear();

		if(newSelection!=null)
			for(IShape sh : newSelection)
				selection.addShape(sh);
	}


	@Override
	public void clear() {
		super.clear();
		selection.clear();
		tempShape.setValue(null);
	}


	@Override
	public boolean removeShape(final IShape sh) {
		selection.removeShape(sh);
		return super.removeShape(sh);
	}
	
	
	@Override
	public IShape removeShape(final int i) {
		// Must be removed from the selection before removing from the main list (otherwise mapping selection2border will fail.
		final IShape removedSh = shapes.isEmpty() || i<-1 || i>=shapes.size() ? null : i==-1 ? shapes.get(shapes.size()-1) : shapes.get(i);
		
		if(removedSh!=null)
			selection.removeShape(removedSh);
		
		return super.removeShape(i);
	}


	@Override
	public IShape getTempShape() {
		return tempShape.getValue();
	}


	@Override
	public IUnary<IShape> getUnaryTempShape() {
		return tempShape;
	}


	@Override
	public void setTempShape(final IShape tempShape) {
		this.tempShape.setValue(tempShape);
	}


	@Override
	public boolean isModified() {
		boolean ok = super.isModified();

		for(int i=0, size=shapes.size(); i<size && !ok; i++)
			if(shapes.get(i).isModified())
				ok = true;

		return ok;
	}


	@Override
	public void reinit() {
		clear();
	}
}
