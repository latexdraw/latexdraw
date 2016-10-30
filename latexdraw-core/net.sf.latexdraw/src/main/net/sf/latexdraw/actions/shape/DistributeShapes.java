/*
  * This file is part of LaTeXDraw.
  * Copyright (c) 2005-2014 Arnaud BLOUIN
  * LaTeXDraw is free software; you can redistribute it and/or modify it under
  * the terms of the GNU General Public License as published by the Free Software
  * Foundation; either version 2 of the License, or (at your option) any later version.
  * LaTeXDraw is distributed without any warranty; without even the implied
  * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * General Public License for more details.
 */
package net.sf.latexdraw.actions.shape;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.sf.latexdraw.actions.Modifying;
import net.sf.latexdraw.actions.ShapeActionImpl;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.malai.undo.Undoable;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This action distributes the provided shapes.
 */
class DistributeShapes extends ShapeActionImpl<IGroup> implements Undoable, Modifying {
	/**
	 * This enumeration describes the different possible alignment types.
	 */
	public enum DistributionType {
		vertBot, vertTop, vertMid, vertEq, horizLeft, horizRight, horizMid, horizEq;

		public boolean isVertical(final DistributionType distrib) {
			return distrib == vertBot || distrib == vertTop || distrib == vertMid || distrib == vertEq;
		}
	}

	/** The reference border that must bounds the shapes to align. */
	Rectangle2D border;

	/** The views corresponding to the shapes to align. */
	final List<ViewShape<?, ?>> views;

	/** The alignment to perform. */
	DistributionType distribution;

	/** The former positions of the shapes to align. Used for undoing. */
	final List<IPoint> oldPositions;


	public DistributeShapes() {
		super();
		views = new ArrayList<>();
		oldPositions = new ArrayList<>();
	}

	@Override
	protected void doActionBody() {
		//		var v : IViewShape = null
		//		shape.get.getShapes.foreach{sh=>
		//			// Because the views have already computed the border of their shape, we get the corresponding views.
		//			_views += MappingRegistry.REGISTRY.getTargetFromSource(sh, classOf[IViewShape])
		//			// Saving the old position of the shape for undoing.
		//			_oldPositions += sh.getTopLeftPoint
		//		}
		//		redo
	}

	@Override
	public boolean canDo() {
		return shape.isPresent() && !shape.get().isEmpty() && border != null && distribution != null;
	}

	@Override
	public void undo() {
		final IntegerProperty pos = new SimpleIntegerProperty(0);

		shape.ifPresent(gp -> {
			gp.getShapes().forEach(sh -> {
				// Reusing the old position.
				IPoint pt = sh.getTopLeftPoint();
				IPoint oldPt = oldPositions.get(pos.get());
				if(!pt.equals(oldPt)) sh.translate(oldPt.getX() - pt.getX(), oldPt.getY() - pt.getY());
				pos.set(pos.get() + 1);
			});
			gp.setModified(true);
		});
	}

	/**
	 * Distributes at equal distance between the shapes.
	 */
	protected void distributeEq() {
		//		var sortedSh= new ListBuffer[IViewShape]()
		//		var mins	= new ListBuffer[Double]()
		//		var maxs	= new ListBuffer[Double]()
		//		var i : Int = 0
		//		val shapes = shape.get.getShapes
		//
		//		_views.foreach{view =>
		//			val coord = _distribution match {
		//				case DistributionType.horizEq => view.getBorder.getMinX
		//				case DistributionType.vertEq => view.getBorder.getMinY
		//			}
		//
		//			i = mins.indexWhere{value=> coord<value}
		//
		//			if(i == -1) {
		//				sortedSh+=view
		//				mins+=coord
		//				if(_distribution==DistributionType.horizEq)
		//					 maxs+=view.getBorder.getMaxX
		//				else maxs+=view.getBorder.getMaxY
		//			}else {
		//				sortedSh.insert(i, view)
		//				mins.insert(i, coord)
		//				if(_distribution==DistributionType.horizEq)
		//					 maxs.insert(i, view.getBorder.getMaxX)
		//				else maxs.insert(i, view.getBorder.getMaxY)
		//			}
		//		}
		//
		//		var gap = mins.last - maxs.head
		//
		//		for(i <- 1 until sortedSh.size-1)
		//			gap -= maxs(i) - mins(i)
		//
		//		gap/=(sortedSh.size-1)
		//
		//		if(DistributionType.isVertical(_distribution))
		//			for(i <- 1 until sortedSh.size-1) {
		//				sortedSh(i).getShape.translate(0, (sortedSh(i-1).getBorder.getMaxY + gap) - mins(i))
		//				sortedSh(i).updateBorder
		//			}
		//		else
		//			for(i <- 1 until sortedSh.size-1) {
		//				sortedSh(i).getShape.translate((sortedSh(i-1).getBorder.getMaxX + gap) - mins(i), 0)
		//				sortedSh(i).updateBorder
		//			}
		//
		//		sortedSh.clear
		//		mins.clear
		//		maxs.clear
	}

	/**
	 * Distributes using bottom/top/left/right reference.
	 */
	protected void distributeNotEq() {
		//		var sortedSh= new ListBuffer[IViewShape]()
		//		var centres	= new ListBuffer[Double]()
		//		var i : Int = 0
		//		val shapes = shape.get.getShapes
		//
		//		_views.foreach{view =>
		//			val x = _distribution match {
		//				case DistributionType.horizLeft => view.getBorder.getMinX
		//				case DistributionType.horizMid => view.getBorder.getCenterX
		//				case DistributionType.horizRight => view.getBorder.getMaxX
		//				case DistributionType.vertBot => view.getBorder.getMaxY
		//				case DistributionType.vertMid => view.getBorder.getCenterY
		//				case DistributionType.vertTop => view.getBorder.getMinY
		//			}
		//
		//			i = centres.indexWhere{value=> x<value}
		//
		//			if(i == -1) {
		//				sortedSh+=view
		//				centres+=x
		//			}else {
		//				sortedSh.insert(i, view)
		//				centres.insert(i, x)
		//			}
		//		}
		//
		//		val gap = (centres.last - centres.head)/(_views.size-1)
		//
		//		if(DistributionType.isVertical(_distribution))
		//			for(i <- 1 until sortedSh.size-1)
		//				sortedSh(i).getShape.translate(0, (centres.head+i*gap)-centres(i))
		//		else
		//			for(i <- 1 until sortedSh.size-1)
		//				sortedSh(i).getShape.translate((centres.head+i*gap)-centres(i), 0)
		//
		//		sortedSh.clear
		//		centres.clear
	}

	@Override
	public void redo() {
		if(distribution == DistributionType.horizEq || distribution == DistributionType.vertEq) {
			distributeEq();
		}else {
			distributeNotEq();
		}
		shape.ifPresent(sh -> sh.setModified(true));
	}

	/**
	 * Sets the alignment to perform.
	 */
	public void setDistribution(final DistributionType distrib) {
		distribution = distrib;
	}

	/**
	 * Sets the reference border that must bounds the shapes to align.
	 */
	public void setBorder(final Rectangle2D rec) {
		border = rec;
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getStringActions("Actions.6");
	}

	@Override
	public boolean isRegisterable() {
		return true;
	}

	@Override
	public void flush() {
		super.flush();
		views.clear();
		oldPositions.clear();
	}
}
