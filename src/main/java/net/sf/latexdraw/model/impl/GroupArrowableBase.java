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
package net.sf.latexdraw.model.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.ArrowableSingleShape;
import net.sf.latexdraw.model.api.shape.Group;

/**
 * This trait encapsulates the code of the group related to the support of arrowable getShapes().
 * @author Arnaud Blouin
 */
interface GroupArrowableBase extends Group {
	/** May return the first grid of the group. */
	default Optional<ArrowableSingleShape> firstIArrowable() {
		return arrowShapes().stream().filter(sh -> sh.isTypeOf(ArrowableSingleShape.class)).findFirst();
	}

	default List<ArrowableSingleShape> arrowShapes() {
		return getShapes().stream().filter(sh -> sh instanceof ArrowableSingleShape).map(sh -> (ArrowableSingleShape) sh).
			collect(Collectors.toList());
	}

	@Override
	default int getArrowIndex(final Arrow arrow) {
		return firstIArrowable().
			map(sh -> sh.getArrowIndex(arrow)).orElse(-1);
	}

	@Override
	default int getNbArrows() {
		return firstIArrowable().
			map(arc -> arc.getNbArrows()).orElse(0);
	}

	@Override
	default void setTBarSizeDimList(final List<Optional<Double>> values) {
		if(values != null && values.size() == getShapes().size()) {
			IntStream.range(0, values.size()).
				filter(i -> getShapes().get(i) instanceof ArrowableSingleShape).
				forEach(i -> ((ArrowableSingleShape) getShapes().get(i)).setTBarSizeDim(values.get(i).get()));
		}
	}

	@Override
	default List<Optional<Double>> getTBarSizeDimList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArrowableSingleShape ? Optional.of(((ArrowableSingleShape) sh).getTBarSizeDim()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	default void setTBarSizeNumList(final List<Optional<Double>> values) {
		if(values != null && values.size() == getShapes().size()) {
			IntStream.range(0, values.size()).
				filter(i -> getShapes().get(i) instanceof ArrowableSingleShape).
				forEach(i -> ((ArrowableSingleShape) getShapes().get(i)).setTBarSizeNum(values.get(i).get()));
		}
	}

	@Override
	default List<Optional<Double>> getTBarSizeNumList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArrowableSingleShape ? Optional.of(((ArrowableSingleShape) sh).getTBarSizeNum()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	default void setDotSizeNumList(final List<Optional<Double>> values) {
		if(values != null && values.size() == getShapes().size()) {
			IntStream.range(0, values.size()).
				filter(i -> getShapes().get(i) instanceof ArrowableSingleShape).
				forEach(i -> ((ArrowableSingleShape) getShapes().get(i)).setDotSizeNum(values.get(i).get()));
		}
	}

	@Override
	default List<Optional<Double>> getDotSizeNumList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArrowableSingleShape ? Optional.of(((ArrowableSingleShape) sh).getDotSizeNum()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	default void setDotSizeDimList(final List<Optional<Double>> values) {
		if(values != null && values.size() == getShapes().size()) {
			IntStream.range(0, values.size()).
				filter(i -> getShapes().get(i) instanceof ArrowableSingleShape).
				forEach(i -> ((ArrowableSingleShape) getShapes().get(i)).setDotSizeDim(values.get(i).get()));
		}
	}

	@Override
	default List<Optional<Double>> getDotSizeDimList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArrowableSingleShape ? Optional.of(((ArrowableSingleShape) sh).getDotSizeDim()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	default void setBracketNumList(final List<Optional<Double>> values) {
		if(values != null && values.size() == getShapes().size()) {
			IntStream.range(0, values.size()).
				filter(i -> getShapes().get(i) instanceof ArrowableSingleShape).
				forEach(i -> ((ArrowableSingleShape) getShapes().get(i)).setBracketNum(values.get(i).get()));
		}
	}

	@Override
	default List<Optional<Double>> getBracketNumList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArrowableSingleShape ? Optional.of(((ArrowableSingleShape) sh).getBracketNum()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	default void setRBracketNumList(final List<Optional<Double>> values) {
		if(values != null && values.size() == getShapes().size()) {
			IntStream.range(0, values.size()).
				filter(i -> getShapes().get(i) instanceof ArrowableSingleShape).
				forEach(i -> ((ArrowableSingleShape) getShapes().get(i)).setRBracketNum(values.get(i).get()));
		}
	}

	@Override
	default List<Optional<Double>> getRBracketNumList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArrowableSingleShape ? Optional.of(((ArrowableSingleShape) sh).getRBracketNum()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	default void setArrowSizeNumList(final List<Optional<Double>> values) {
		if(values != null && values.size() == getShapes().size()) {
			IntStream.range(0, values.size()).
				filter(i -> getShapes().get(i) instanceof ArrowableSingleShape).
				forEach(i -> ((ArrowableSingleShape) getShapes().get(i)).setArrowSizeNum(values.get(i).get()));
		}
	}

	@Override
	default List<Optional<Double>> getArrowSizeNumList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArrowableSingleShape ? Optional.of(((ArrowableSingleShape) sh).getArrowSizeNum()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	default void setArrowSizeDimList(final List<Optional<Double>> values) {
		if(values != null && values.size() == getShapes().size()) {
			IntStream.range(0, values.size()).
				filter(i -> getShapes().get(i) instanceof ArrowableSingleShape).
				forEach(i -> ((ArrowableSingleShape) getShapes().get(i)).setArrowSizeDim(values.get(i).get()));
		}
	}

	@Override
	default List<Optional<Double>> getArrowSizeDimList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArrowableSingleShape ? Optional.of(((ArrowableSingleShape) sh).getArrowSizeDim()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	default void setArrowLengthList(final List<Optional<Double>> values) {
		if(values != null && values.size() == getShapes().size()) {
			IntStream.range(0, values.size()).
				filter(i -> getShapes().get(i) instanceof ArrowableSingleShape).
				forEach(i -> ((ArrowableSingleShape) getShapes().get(i)).setArrowLength(values.get(i).get()));
		}
	}

	@Override
	default List<Optional<Double>> getArrowLengthList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArrowableSingleShape ? Optional.of(((ArrowableSingleShape) sh).getArrowLength()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	default void setArrowInsetList(final List<Optional<Double>> values) {
		if(values != null && values.size() == getShapes().size()) {
			IntStream.range(0, values.size()).
				filter(i -> getShapes().get(i) instanceof ArrowableSingleShape).
				forEach(i -> ((ArrowableSingleShape) getShapes().get(i)).setArrowInset(values.get(i).get()));
		}
	}

	@Override
	default List<Optional<Double>> getArrowInsetList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArrowableSingleShape ? Optional.of(((ArrowableSingleShape) sh).getArrowInset()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	default List<Optional<ArrowStyle>> getArrowStyleList(final int i) {
		return getShapes().stream().
			map(sh -> sh instanceof ArrowableSingleShape ? Optional.of(((ArrowableSingleShape) sh).getArrowStyle(i)) : Optional.<ArrowStyle>empty()).
			collect(Collectors.toList());
	}

	@Override
	default void setArrowStyleList(final List<Optional<ArrowStyle>> values, final int index) {
		if(values != null && values.size() == getShapes().size()) {
			IntStream.range(0, values.size()).
				filter(i -> getShapes().get(i) instanceof ArrowableSingleShape).
				forEach(i -> ((ArrowableSingleShape) getShapes().get(i)).setArrowStyle(values.get(i).get(), index));
		}
	}

	@Override
	default void setArrowStyle(final ArrowStyle style, final int position) {
		arrowShapes().forEach(sh -> sh.setArrowStyle(style, position));
	}

	@Override
	default ArrowStyle getArrowStyle(final int position) {
		return firstIArrowable().
			map(sh -> sh.getArrowStyle(position)).orElse(ArrowStyle.NONE);
	}

	@Override
	default Arrow getArrowAt(final int position) {
		return firstIArrowable().
			map(sh -> sh.getArrowAt(position)).orElse(null);
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
	default void onChanges(final Runnable run) {
	}
}
