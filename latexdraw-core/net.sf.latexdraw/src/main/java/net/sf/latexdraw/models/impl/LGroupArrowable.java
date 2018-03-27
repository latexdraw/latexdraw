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
package net.sf.latexdraw.models.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * This trait encapsulates the code of the group related to the support of arrowable shapes.
 * @author Arnaud Blouin
 */
interface LGroupArrowable extends IGroup {
	/** May return the first grid of the group. */
	default Optional<IArrowableSingleShape> firstIArrowable() {
		return arrowShapes().stream().filter(sh -> sh.isTypeOf(IArrowableSingleShape.class)).findFirst();
	}

	default List<IArrowableSingleShape> arrowShapes() {
		return getShapes().stream().filter(sh -> sh instanceof IArrowableSingleShape).map(sh -> (IArrowableSingleShape) sh).collect(Collectors.toList());
	}

	@Override
	default int getArrowIndex(final IArrow arrow) {
		return firstIArrowable().map(sh -> sh.getArrowIndex(arrow)).orElse(-1);
	}

	@Override
	default int getNbArrows() {
		return firstIArrowable().map(arc -> arc.getNbArrows()).orElse(0);
	}

	@Override
	default void setTBarSizeDimList(final List<Double> values) {
		final List<IShape> shapes = getShapes();
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArrowableSingleShape) {
					((IArrowableSingleShape) sh).setTBarSizeDim(values.get(i));
				}
			});
		}
	}

	@Override
	default List<Double> getTBarSizeDimList() {
		return getShapes().stream().map(sh -> sh instanceof IArrowableSingleShape ? ((IArrowableSingleShape) sh).getTBarSizeDim() : Double.NaN).collect(Collectors.toList());
	}

	@Override
	default void setTBarSizeNumList(final List<Double> values) {
		final List<IShape> shapes = getShapes();
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArrowableSingleShape) {
					((IArrowableSingleShape) sh).setTBarSizeNum(values.get(i));
				}
			});
		}
	}

	@Override
	default List<Double> getTBarSizeNumList() {
		return getShapes().stream().map(sh -> sh instanceof IArrowableSingleShape ? ((IArrowableSingleShape) sh).getTBarSizeNum() : Double.NaN).collect(Collectors.toList());
	}

	@Override
	default void setDotSizeNumList(final List<Double> values) {
		final List<IShape> shapes = getShapes();
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArrowableSingleShape) {
					((IArrowableSingleShape) sh).setDotSizeNum(values.get(i));
				}
			});
		}
	}

	@Override
	default List<Double> getDotSizeNumList() {
		return getShapes().stream().map(sh -> sh instanceof IArrowableSingleShape ? ((IArrowableSingleShape) sh).getDotSizeNum() : Double.NaN).collect(Collectors.toList());
	}

	@Override
	default void setDotSizeDimList(final List<Double> values) {
		final List<IShape> shapes = getShapes();
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArrowableSingleShape) {
					((IArrowableSingleShape) sh).setDotSizeDim(values.get(i));
				}
			});
		}
	}

	@Override
	default List<Double> getDotSizeDimList() {
		return getShapes().stream().map(sh -> sh instanceof IArrowableSingleShape ? ((IArrowableSingleShape) sh).getDotSizeDim() : Double.NaN).collect(Collectors.toList());
	}

	@Override
	default void setBracketNumList(final List<Double> values) {
		final List<IShape> shapes = getShapes();
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArrowableSingleShape) {
					((IArrowableSingleShape) sh).setBracketNum(values.get(i));
				}
			});
		}
	}

	@Override
	default List<Double> getBracketNumList() {
		return getShapes().stream().map(sh -> sh instanceof IArrowableSingleShape ? ((IArrowableSingleShape) sh).getBracketNum() : Double.NaN).collect(Collectors.toList());
	}

	@Override
	default void setRBracketNumList(final List<Double> values) {
		final List<IShape> shapes = getShapes();
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArrowableSingleShape) {
					((IArrowableSingleShape) sh).setRBracketNum(values.get(i));
				}
			});
		}
	}

	@Override
	default List<Double> getRBracketNumList() {
		return getShapes().stream().map(sh -> sh instanceof IArrowableSingleShape ? ((IArrowableSingleShape) sh).getRBracketNum() : Double.NaN).collect(Collectors.toList());
	}

	@Override
	default void setArrowSizeNumList(final List<Double> values) {
		final List<IShape> shapes = getShapes();
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArrowableSingleShape) {
					((IArrowableSingleShape) sh).setArrowSizeNum(values.get(i));
				}
			});
		}
	}

	@Override
	default List<Double> getArrowSizeNumList() {
		return getShapes().stream().map(sh -> sh instanceof IArrowableSingleShape ? ((IArrowableSingleShape) sh).getArrowSizeNum() : Double.NaN).collect(Collectors.toList());
	}

	@Override
	default void setArrowSizeDimList(final List<Double> values) {
		final List<IShape> shapes = getShapes();
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArrowableSingleShape) {
					((IArrowableSingleShape) sh).setArrowSizeDim(values.get(i));
				}
			});
		}
	}

	@Override
	default List<Double> getArrowSizeDimList() {
		return getShapes().stream().map(sh -> sh instanceof IArrowableSingleShape ? ((IArrowableSingleShape) sh).getArrowSizeDim() : Double.NaN).collect(Collectors.toList());
	}

	@Override
	default void setArrowLengthList(final List<Double> values) {
		final List<IShape> shapes = getShapes();
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArrowableSingleShape) {
					((IArrowableSingleShape) sh).setArrowLength(values.get(i));
				}
			});
		}
	}

	@Override
	default List<Double> getArrowLengthList() {
		return getShapes().stream().map(sh -> sh instanceof IArrowableSingleShape ? ((IArrowableSingleShape) sh).getArrowLength() : Double.NaN).collect(Collectors.toList());
	}

	@Override
	default void setArrowInsetList(final List<Double> values) {
		final List<IShape> shapes = getShapes();
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArrowableSingleShape) {
					((IArrowableSingleShape) sh).setArrowInset(values.get(i));
				}
			});
		}
	}

	@Override
	default List<Double> getArrowInsetList() {
		return getShapes().stream().map(sh -> sh instanceof IArrowableSingleShape ? ((IArrowableSingleShape) sh).getArrowInset() : Double.NaN).collect(Collectors.toList());
	}

	@Override
	default List<ArrowStyle> getArrowStyleList(final int i) {
		return getShapes().stream().map(sh -> sh instanceof IArrowableSingleShape ? ((IArrowableSingleShape) sh).getArrowStyle(i) : ArrowStyle.NONE).collect(Collectors.toList());
	}

	@Override
	default void setArrowStyleList(final List<ArrowStyle> values, final int index) {
		final List<IShape> shapes = getShapes();
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArrowableSingleShape) {
					((IArrowableSingleShape) sh).setArrowStyle(values.get(i), index);
				}
			});
		}
	}

	@Override
	default void setArrowStyle(final ArrowStyle style, final int position) {
		arrowShapes().forEach(sh -> sh.setArrowStyle(style, position));
	}

	@Override
	default ArrowStyle getArrowStyle(final int position) {
		return firstIArrowable().map(sh -> sh.getArrowStyle(position)).orElse(ArrowStyle.NONE);
	}

	@Override
	default IArrow getArrowAt(final int position) {
		return firstIArrowable().map(sh -> sh.getArrowAt(position)).orElse(null);
	}

	@Override
	default void setDotSizeDim(final double dotSizeDim) {
		arrowShapes().forEach(sh -> sh.setDotSizeDim(dotSizeDim));
	}

	@Override
	default void setDotSizeNum(final double dotSizeNum) {
		arrowShapes().forEach(sh -> sh.setDotSizeNum(dotSizeNum));
	}

	@Override
	default void setTBarSizeNum(final double tbarSizeNum) {
		arrowShapes().forEach(sh -> sh.setTBarSizeNum(tbarSizeNum));
	}

	@Override
	default void setTBarSizeDim(final double tbarSizeDim) {
		arrowShapes().forEach(sh -> sh.setTBarSizeDim(tbarSizeDim));
	}

	@Override
	default double getTBarSizeDim() {
		return firstIArrowable().map(sh -> sh.getTBarSizeDim()).orElse(Double.NaN);
	}

	@Override
	default double getTBarSizeNum() {
		return firstIArrowable().map(sh -> sh.getTBarSizeNum()).orElse(Double.NaN);
	}

	@Override
	default void setRBracketNum(final double rBracketNum) {
		arrowShapes().forEach(sh -> sh.setRBracketNum(rBracketNum));
	}

	@Override
	default void setBracketNum(final double bracketNum) {
		arrowShapes().forEach(sh -> sh.setBracketNum(bracketNum));
	}

	@Override
	default void setArrowLength(final double lgth) {
		arrowShapes().forEach(sh -> sh.setArrowLength(lgth));
	}

	@Override
	default void setArrowSizeDim(final double arrowSizeDim) {
		arrowShapes().forEach(sh -> sh.setArrowSizeDim(arrowSizeDim));
	}

	@Override
	default void setArrowSizeNum(final double arrowSizeNum) {
		arrowShapes().forEach(sh -> sh.setArrowSizeNum(arrowSizeNum));
	}

	@Override
	default void setArrowInset(final double inset) {
		arrowShapes().forEach(sh -> sh.setArrowInset(inset));
	}

	@Override
	default double getDotSizeDim() {
		return firstIArrowable().map(sh -> sh.getDotSizeDim()).orElse(Double.NaN);
	}

	@Override
	default double getDotSizeNum() {
		return firstIArrowable().map(sh -> sh.getDotSizeNum()).orElse(Double.NaN);
	}

	@Override
	default double getBracketNum() {
		return firstIArrowable().map(sh -> sh.getBracketNum()).orElse(Double.NaN);
	}

	@Override
	default double getArrowSizeNum() {
		return firstIArrowable().map(sh -> sh.getArrowSizeNum()).orElse(Double.NaN);
	}

	@Override
	default double getArrowSizeDim() {
		return firstIArrowable().map(sh -> sh.getArrowSizeDim()).orElse(Double.NaN);
	}

	@Override
	default double getArrowInset() {
		return firstIArrowable().map(sh -> sh.getArrowInset()).orElse(Double.NaN);
	}

	@Override
	default double getArrowLength() {
		return firstIArrowable().map(sh -> sh.getArrowLength()).orElse(Double.NaN);
	}

	@Override
	default double getRBracketNum() {
		return firstIArrowable().map(sh -> sh.getRBracketNum()).orElse(Double.NaN);
	}

	@Override
	default void setOnArrowChanged(final Runnable run) {
		//TODO
	}
}
