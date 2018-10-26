/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.commands.shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewShape;

/**
 * This command aligns the provided shapes.
 * @author Arnaud Blouin
 */
public class AlignShapes extends AlignDistribCmd {
	/**
	 * This enumeration describes the different possible alignment types.
	 */
	public enum Alignment {
		LEFT, RIGHT, TOP, BOTTOM, MID_HORIZ, MID_VERT
	}

	/** The alignment to perform. */
	private final Alignment alignment;


	public AlignShapes(final Canvas canvas, final Alignment alignment, final IGroup sh) {
		super(canvas, sh);
		this.alignment = alignment;
	}

	@Override
	protected void doCmdBody() {
		views = shape.map(gp -> gp.getShapes().stream().map(sh -> canvas.getViewFromShape(sh)).filter(opt -> opt.isPresent()).
			map(opt -> opt.get()).collect(Collectors.<ViewShape<?>>toList())).orElse(Collections.emptyList());

		oldPositions = shape.map(gp -> gp.getShapes().stream().map(sh -> sh.getTopLeftPoint()).collect(Collectors.toList())).orElse(Collections.emptyList());
		redo();
	}

	/**
	 * Middle-horizontal aligning the provided shapes.
	 */
	private void alignMidHoriz() {
		double theMaxY = Double.MIN_VALUE;
		double theMinY = Double.MAX_VALUE;
		final List<Double> middles = new ArrayList<>();

		for(final ViewShape<?> view : views) {
			final double maxY = view.getBoundsInLocal().getMaxY();
			final double minY = view.getBoundsInLocal().getMinY();
			if(maxY > theMaxY) {
				theMaxY = maxY;
			}
			if(minY < theMinY) {
				theMinY = minY;
			}
			middles.add((minY + maxY) / 2d);
		}

		translateY(middles, (theMaxY + theMinY) / 2d);
	}

	/**
	 * Middle-vertical aligning the provided shapes.
	 */
	private void alignMidVert() {
		double theMaxX = Double.MIN_VALUE;
		double theMinX = Double.MAX_VALUE;
		final List<Double> middles = new ArrayList<>();

		for(final ViewShape<?> view : views) {
			final double maxX = view.getBoundsInLocal().getMaxX();
			final double minX = view.getBoundsInLocal().getMinX();
			if(maxX > theMaxX) {
				theMaxX = maxX;
			}
			if(minX < theMinX) {
				theMinX = minX;
			}
			middles.add((minX + maxX) / 2d);
		}

		translateX(middles, (theMaxX + theMinX) / 2d);
	}

	private void translateX(final List<Double> vals, final double ref) {
		int i = 0;

		for(final IShape sh : shape.map(gp -> (List<IShape>) gp.getShapes()).orElse(Collections.emptyList())) {
			final double middle2 = vals.get(i);
			if(!MathUtils.INST.equalsDouble(middle2, ref)) {
				sh.translate(ref - middle2, 0d);
			}
			i++;
		}
	}

	private void translateY(final List<Double> vals, final double ref) {
		int i = 0;

		for(final IShape sh : shape.map(gp -> (List<IShape>) gp.getShapes()).orElse(Collections.emptyList())) {
			final double y = vals.get(i);
			if(!MathUtils.INST.equalsDouble(y, ref)) {
				sh.translate(0d, ref - y);
			}
			i++;
		}
	}

	/**
	 * Bottom aligning the provided shapes.
	 */
	private void alignBottom() {
		double theMaxY = Double.MIN_VALUE;
		final List<Double> ys = new ArrayList<>();

		for(final ViewShape<?> view : views) {
			final double maxY = view.getBoundsInLocal().getMaxY();
			if(maxY > theMaxY) {
				theMaxY = maxY;
			}
			ys.add(maxY);
		}

		translateY(ys, theMaxY);
	}

	/**
	 * Top aligning the provided shapes.
	 */
	private void alignTop() {
		double theMinY = Double.MAX_VALUE;
		final List<Double> ys = new ArrayList<>();

		for(final ViewShape<?> view : views) {
			final double minY = view.getBoundsInLocal().getMinY();
			if(minY < theMinY) {
				theMinY = minY;
			}
			ys.add(minY);
		}

		translateY(ys, theMinY);
	}

	/**
	 * Right aligning the provided shapes.
	 */
	private void alignRight() {
		double theMaxX = Double.MIN_VALUE;
		final List<Double> xs = new ArrayList<>();

		for(final ViewShape<?> view : views) {
			final double maxX = view.getBoundsInLocal().getMaxX();
			if(maxX > theMaxX) {
				theMaxX = maxX;
			}
			xs.add(maxX);
		}

		translateX(xs, theMaxX);
	}

	/**
	 * Left aligning the provided shapes.
	 */
	private void alignLeft() {
		double theMinX = Double.MAX_VALUE;
		final List<Double> xs = new ArrayList<>();

		for(final ViewShape<?> view : views) {
			final double minX = view.getBoundsInLocal().getMinX();
			if(minX < theMinX) {
				theMinX = minX;
			}
			xs.add(minX);
		}

		translateX(xs, theMinX);
	}

	@Override
	public boolean canDo() {
		return canvas != null && alignment != null && shape.isPresent() && !shape.get().isEmpty();
	}

	@Override
	public void redo() {
		switch(alignment) {
			case LEFT:
				alignLeft();
				break;
			case RIGHT:
				alignRight();
				break;
			case TOP:
				alignTop();
				break;
			case BOTTOM:
				alignBottom();
				break;
			case MID_HORIZ:
				alignMidHoriz();
				break;
			case MID_VERT:
				alignMidVert();
				break;
		}

		shape.ifPresent(sh -> sh.setModified(true));
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("Actions.30");
	}
}
