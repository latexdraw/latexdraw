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
package net.sf.latexdraw.command.shape;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.jetbrains.annotations.NotNull;

/**
 * This command distributes the provided shapes.
 * @author Arnaud Blouin
 */
public class DistributeShapes extends AlignDistribCmd {
	/**
	 * This enumeration describes the different possible alignment types.
	 */
	public enum Distribution {
		VERT_BOT, VERT_TOP, VERT_MID, VERT_EQ, HORIZ_LEFT, HORIZ_RIGHT, HORIZ_MID, HORIZ_EQ;

		public static boolean isVertical(final Distribution distrib) {
			return distrib == VERT_BOT || distrib == VERT_TOP || distrib == VERT_MID || distrib == VERT_EQ;
		}
	}

	/** The alignment to perform. */
	private final @NotNull Distribution distribution;

	public DistributeShapes(final @NotNull Canvas canvas, final @NotNull Distribution distribution, final @NotNull Group gp) {
		super(canvas, gp);
		this.distribution = distribution;
	}

	@Override
	public boolean canDo() {
		return !shape.isEmpty();
	}

	/**
	 * Distributes at equal distance between the shapes.
	 */
	private void distributeEq() {
		final List<ViewShape<?>> sortedSh = new ArrayList<>();
		final List<Double> mins = new ArrayList<>();
		final List<Double> maxs = new ArrayList<>();

		for(final ViewShape<?> view : views) {
			final double coord = distribution == Distribution.HORIZ_EQ ? view.getBoundsInLocal().getMinX() : view.getBoundsInLocal().getMinY();
			final OptionalInt res = IntStream.range(0, mins.size()).filter(index -> coord < mins.get(index)).findFirst();

			if(res.isPresent()) {
				final int i = res.getAsInt();
				sortedSh.add(i, view);
				mins.add(i, coord);
				maxs.add(i, distribution == Distribution.HORIZ_EQ ? view.getBoundsInLocal().getMaxX() : view.getBoundsInLocal().getMaxY());
			}else {
				sortedSh.add(view);
				mins.add(coord);
				maxs.add(distribution == Distribution.HORIZ_EQ ? view.getBoundsInLocal().getMaxX() : view.getBoundsInLocal().getMaxY());
			}
		}

		double gap = mins.get(mins.size() - 1) - maxs.get(0);

		for(int i = 1, size = sortedSh.size() - 1; i < size; i++) {
			gap -= maxs.get(i) - mins.get(i);
		}

		gap /= sortedSh.size() - 1;
		final double finalGap = gap;

		if(Distribution.isVertical(distribution)) {
			IntStream.range(1, sortedSh.size() - 1).forEach(i -> ((Shape) sortedSh.get(i).getUserData()).
				translate(0d, sortedSh.get(i - 1).getBoundsInLocal().getMaxY() + finalGap - mins.get(i)));
		}else {
			IntStream.range(1, sortedSh.size() - 1).forEach(i -> ((Shape) sortedSh.get(i).getUserData()).
				translate(sortedSh.get(i - 1).getBoundsInLocal().getMaxX() + finalGap - mins.get(i), 0d));
		}
	}

	/**
	 * Distributes using bottom/top/left/right reference.
	 */
	private void distributeNotEq() {
		final List<ViewShape<?>> sortedSh = new ArrayList<>();
		final List<Double> centres = new ArrayList<>();

		for(final ViewShape<?> view : views) {
			double x = 0;
			switch(distribution) {
				case HORIZ_LEFT:
					x = view.getBoundsInLocal().getMinX();
					break;
				case HORIZ_MID:
					x = (view.getBoundsInLocal().getMinX() + view.getBoundsInLocal().getMaxX()) / 2d;
					break;
				case HORIZ_RIGHT:
					x = view.getBoundsInLocal().getMaxX();
					break;
				case VERT_BOT:
					x = view.getBoundsInLocal().getMaxY();
					break;
				case VERT_MID:
					x = (view.getBoundsInLocal().getMinY() + view.getBoundsInLocal().getMaxY()) / 2d;
					break;
				case VERT_TOP:
					x = view.getBoundsInLocal().getMinY();
					break;
				default:
					// Nothing to do for the other ones.
			}

			final double finalX = x;
			final OptionalInt res = IntStream.range(0, centres.size()).filter(index -> finalX < centres.get(index)).findFirst();

			if(res.isPresent()) {
				final int i = res.getAsInt();
				sortedSh.add(i, view);
				centres.add(i, x);
			}else {
				sortedSh.add(view);
				centres.add(x);
			}
		}

		final double gap = (centres.get(centres.size() - 1) - centres.get(0)) / (views.size() - 1);

		if(Distribution.isVertical(distribution)) {
			IntStream.range(1, sortedSh.size() - 1).forEach(i -> ((Shape) sortedSh.get(i).getUserData()).
				translate(0d, centres.get(0) + i * gap - centres.get(i)));
		}else {
			IntStream.range(1, sortedSh.size() - 1).forEach(i -> ((Shape) sortedSh.get(i).getUserData()).
				translate(centres.get(0) + i * gap - centres.get(i), 0d));
		}
	}

	@Override
	public void redo() {
		if(distribution == Distribution.HORIZ_EQ || distribution == Distribution.VERT_EQ) {
			distributeEq();
		}else {
			distributeNotEq();
		}
		shape.setModified(true);
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("Actions.6");
	}
}
