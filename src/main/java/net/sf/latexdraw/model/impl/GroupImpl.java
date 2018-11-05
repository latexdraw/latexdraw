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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.ArcProp;
import net.sf.latexdraw.model.api.property.AxesProp;
import net.sf.latexdraw.model.api.property.ClosableProp;
import net.sf.latexdraw.model.api.property.DotProp;
import net.sf.latexdraw.model.api.property.FreeHandProp;
import net.sf.latexdraw.model.api.property.GridProp;
import net.sf.latexdraw.model.api.property.LineArcProp;
import net.sf.latexdraw.model.api.property.PlotProp;
import net.sf.latexdraw.model.api.property.Scalable;
import net.sf.latexdraw.model.api.property.IStdGridProp;
import net.sf.latexdraw.model.api.property.TextProp;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.Color;
import net.sf.latexdraw.model.api.shape.DotStyle;
import net.sf.latexdraw.model.api.shape.FillingStyle;
import net.sf.latexdraw.model.api.shape.FreeHandStyle;
import net.sf.latexdraw.model.api.shape.Arrow;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.LineStyle;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.TextPosition;
import net.sf.latexdraw.model.api.shape.TicksStyle;

/**
 * An implemenation of the IGroup interface.
 * @author Arnaud Blouin
 */
class GroupImpl implements GroupArcBase, GroupArrowableBase, GroupAxesBase, GroupDotBase, GroupFreeHandBase, GroupLineArcBase, GroupGridBase, GroupShapeBase, GroupStdGridBase, GroupTextBase, SetShapesBase, GroupPlotBase, GroupClosableBase {
	/** The set of shapes. */
	private final ListProperty<Shape> shapes;
	private final DoubleProperty rotationAngle;

	GroupImpl() {
		super();
		shapes = new SimpleListProperty<>(FXCollections.observableArrayList());
		rotationAngle = new SimpleDoubleProperty();
	}

	@Override
	public Group duplicate() {
		return duplicateDeep(true);
	}

	@Override
	public void setModified(final boolean modified) {
		getShapes().forEach(sh -> sh.setModified(modified));
	}

	@Override
	public boolean isModified() {
		return getShapes().parallelStream().anyMatch(sh -> sh.isModified());
	}

	@Override
	public Group duplicateDeep(final boolean duplicateShapes) {
		final Group dup = ShapeFactory.INST.createGroup();

		if(duplicateShapes) {
			getShapes().forEach(sh -> dup.addShape(sh.duplicate()));
		}else {
			getShapes().forEach(sh -> dup.addShape(sh));
		}

		return dup;
	}


	@Override
	public boolean isTypeOf(final Class<?> clazz) {
		if(clazz == null) {
			return false;
		}
		if(clazz.equals(getClass()) || clazz.equals(Shape.class) || clazz.equals(ShapeBase.class) || clazz.equals(Group.class)) {
			return true;
		}
		return shapes.parallelStream().anyMatch(sh -> sh.isTypeOf(clazz));
	}

	@Override
	public DoubleProperty rotationAngleProperty() {
		return rotationAngle;
	}

	@Override
	public void setPlotPolarList(final List<Optional<Boolean>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof PlotProp).
				forEach(i -> ((PlotProp) shapes.get(i)).setPolar(values.get(i).get()));
		}
	}

	@Override
	public final List<Optional<Boolean>> getPlotPolarList() {
		return getShapes().stream().
			map(sh -> sh instanceof PlotProp ? Optional.of(((PlotProp) sh).isPolar()) : Optional.<Boolean>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setYScaleList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof Scalable).
				forEach(i -> ((Scalable) shapes.get(i)).setYScale(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<Double>> getYScaleList() {
		return getShapes().stream().
			map(sh -> sh instanceof Scalable ? Optional.of(((Scalable) sh).getYScale()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setXScaleList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof Scalable).
				forEach(i -> ((Scalable) shapes.get(i)).setXScale(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<Double>> getXScaleList() {
		return getShapes().stream().
			map(sh -> sh instanceof Scalable ? Optional.of(((Scalable) sh).getXScale()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setPlotMinXList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof PlotProp).
				forEach(i -> ((PlotProp) shapes.get(i)).setPlotMinX(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<Double>> getPlotMinXList() {
		return getShapes().stream().
			map(sh -> sh instanceof PlotProp ? Optional.of(((PlotProp) sh).getPlotMinX()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setPlotMaxXList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof PlotProp).
				forEach(i -> ((PlotProp) shapes.get(i)).setPlotMaxX(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<Double>> getPlotMaxXList() {
		return getShapes().stream().
			map(sh -> sh instanceof PlotProp ? Optional.of(((PlotProp) sh).getPlotMaxX()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setNbPlottedPointsList(final List<Optional<Integer>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof PlotProp).
				forEach(i -> ((PlotProp) shapes.get(i)).setNbPlottedPoints(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<Integer>> getNbPlottedPointsList() {
		return getShapes().stream().
			map(sh -> sh instanceof PlotProp ? Optional.of(((PlotProp) sh).getNbPlottedPoints()) : Optional.<Integer>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setPlotStyleList(final List<Optional<PlotStyle>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof PlotProp).
				forEach(i -> ((PlotProp) shapes.get(i)).setPlotStyle(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<PlotStyle>> getPlotStyleList() {
		return getShapes().stream().
			map(sh -> sh instanceof PlotProp ? Optional.of(((PlotProp) sh).getPlotStyle()) : Optional.<PlotStyle>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setPlotEquationList(final List<Optional<String>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof PlotProp).
				forEach(i -> ((PlotProp) shapes.get(i)).setPlotEquation(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<String>> getPlotEquationList() {
		return getShapes().stream().
			map(sh -> sh instanceof PlotProp ? Optional.of(((PlotProp) sh).getPlotEquation()) : Optional.<String>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setFreeHandIntervalList(final List<Optional<Integer>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof FreeHandProp).
				forEach(i -> ((FreeHandProp) shapes.get(i)).setInterval(values.get(i).get()));
		}
	}


	@Override
	public List<Optional<Integer>> getFreeHandIntervalList() {
		return getShapes().stream().
			map(sh -> sh instanceof FreeHandProp ? Optional.of(((FreeHandProp) sh).getInterval()) : Optional.<Integer>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setOpenList(final List<Optional<Boolean>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof ClosableProp).
				forEach(i -> ((ClosableProp) shapes.get(i)).setOpened(values.get(i).get()));
		}
	}


	@Override
	public final List<Optional<Boolean>> getOpenList() {
		return getShapes().stream().
			map(sh -> sh instanceof ClosableProp ? Optional.of(((ClosableProp) sh).isOpened()) : Optional.<Boolean>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setGridLabelsColourList(final List<Optional<Color>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof GridProp).
				forEach(i -> ((GridProp) shapes.get(i)).setGridLabelsColour(values.get(i).get()));
		}
	}


	@Override
	public List<Optional<Color>> getGridLabelsColourList() {
		return getShapes().stream().
			map(sh -> sh instanceof GridProp ? Optional.of(((GridProp) sh).getGridLabelsColour()) : Optional.<Color>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setSubGridColourList(final List<Optional<Color>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof GridProp).
				forEach(i -> ((GridProp) shapes.get(i)).setSubGridColour(values.get(i).get()));
		}
	}


	@Override
	public List<Optional<Color>> getSubGridColourList() {
		return getShapes().stream().
			map(sh -> sh instanceof GridProp ? Optional.of(((GridProp) sh).getSubGridColour()) : Optional.<Color>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setGridWidthList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof GridProp).
				forEach(i -> ((GridProp) shapes.get(i)).setGridWidth(values.get(i).get()));
		}
	}


	@Override
	public final List<Optional<Double>> getGridWidthList() {
		return getShapes().stream().
			map(sh -> sh instanceof GridProp ? Optional.of(((GridProp) sh).getGridWidth()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public final void setSubGridWidthList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof GridProp).
				forEach(i -> ((GridProp) shapes.get(i)).setSubGridWidth(values.get(i).get()));
		}
	}


	@Override
	public final List<Optional<Double>> getSubGridWidthList() {
		return getShapes().stream().
			map(sh -> sh instanceof GridProp ? Optional.of(((GridProp) sh).getSubGridWidth()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setGridDotsList(final List<Optional<Integer>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof GridProp).
				forEach(i -> ((GridProp) shapes.get(i)).setGridDots(values.get(i).get()));
		}
	}


	@Override
	public List<Optional<Integer>> getGridDotsList() {
		return getShapes().stream().
			map(sh -> sh instanceof GridProp ? Optional.of(((GridProp) sh).getGridDots()) : Optional.<Integer>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setSubGridDotsList(final List<Optional<Integer>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof GridProp).
				forEach(i -> ((GridProp) shapes.get(i)).setSubGridDots(values.get(i).get()));
		}
	}


	@Override
	public List<Optional<Integer>> getSubGridDotsList() {
		return getShapes().stream().
			map(sh -> sh instanceof GridProp ? Optional.of(((GridProp) sh).getSubGridDots()) : Optional.<Integer>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setSubGridDivList(final List<Optional<Integer>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof GridProp).
				forEach(i -> ((GridProp) shapes.get(i)).setSubGridDiv(values.get(i).get()));
		}
	}


	@Override
	public List<Optional<Integer>> getSubGridDivList() {
		return getShapes().stream().
			map(sh -> sh instanceof GridProp ? Optional.of(((GridProp) sh).getSubGridDiv()) : Optional.<Integer>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setFreeHandTypeList(final List<Optional<FreeHandStyle>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof FreeHandProp).
				forEach(i -> ((FreeHandProp) shapes.get(i)).setType(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<FreeHandStyle>> getFreeHandTypeList() {
		return getShapes().stream().
			map(sh -> sh instanceof FreeHandProp ? Optional.of(((FreeHandProp) sh).getType()) : Optional.<FreeHandStyle>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setAxesDistLabelsList(final List<Optional<Point>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof AxesProp).
				forEach(i -> ((AxesProp) shapes.get(i)).setDistLabels(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<Point>> getAxesDistLabelsList() {
		return getShapes().stream().
			map(sh -> sh instanceof AxesProp ? Optional.of(((AxesProp) sh).getDistLabels()) : Optional.<Point>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setAxesLabelsDisplayedList(final List<Optional<PlottingStyle>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof AxesProp).
				forEach(i -> ((AxesProp) shapes.get(i)).setLabelsDisplayed(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<PlottingStyle>> getAxesLabelsDisplayedList() {
		return getShapes().stream().
			map(sh -> sh instanceof AxesProp ? Optional.of(((AxesProp) sh).getLabelsDisplayed()) : Optional.<PlottingStyle>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setAxesShowOriginList(final List<Optional<Boolean>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof AxesProp).
				forEach(i -> ((AxesProp) shapes.get(i)).setShowOrigin(values.get(i).get()));
		}
	}


	@Override
	public final List<Optional<Boolean>> getAxesShowOriginList() {
		return getShapes().stream().
			map(sh -> sh instanceof AxesProp ? Optional.of(((AxesProp) sh).isShowOrigin()) : Optional.<Boolean>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setAxesTicksStyleList(final List<Optional<TicksStyle>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof AxesProp).
				forEach(i -> ((AxesProp) shapes.get(i)).setTicksStyle(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<TicksStyle>> getAxesTicksStyleList() {
		return getShapes().stream().
			map(sh -> sh instanceof AxesProp ? Optional.of(((AxesProp) sh).getTicksStyle()) : Optional.<TicksStyle>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setAxesTicksSizeList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof AxesProp).
				forEach(i -> ((AxesProp) shapes.get(i)).setTicksSize(values.get(i).get()));
		}
	}


	@Override
	public final List<Optional<Double>> getAxesTicksSizeList() {
		return getShapes().stream().
			map(sh -> sh instanceof AxesProp ? Optional.of(((AxesProp) sh).getTicksSize()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setAxesTicksDisplayedList(final List<Optional<PlottingStyle>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof AxesProp).
				forEach(i -> ((AxesProp) shapes.get(i)).setTicksDisplayed(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<PlottingStyle>> getAxesTicksDisplayedList() {
		return getShapes().stream().
			map(sh -> sh instanceof AxesProp ? Optional.of(((AxesProp) sh).getTicksDisplayed()) : Optional.<PlottingStyle>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setAxesIncrementsList(final List<Optional<Point>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof AxesProp).
				forEach(i -> ((AxesProp) shapes.get(i)).setIncrement(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<Point>> getAxesIncrementsList() {
		return getShapes().stream().
			map(sh -> sh instanceof AxesProp ? Optional.of(((AxesProp) sh).getIncrement()) : Optional.<Point>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setGridLabelSizeList(final List<Optional<Integer>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof IStdGridProp).
				forEach(i -> ((IStdGridProp) shapes.get(i)).setLabelsSize(values.get(i).get()));
		}
	}


	@Override
	public List<Optional<Integer>> getGridLabelSizeList() {
		return getShapes().stream().
			map(sh -> sh instanceof IStdGridProp ? Optional.of(((IStdGridProp) sh).getLabelsSize()) : Optional.<Integer>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setGridXLabelSouthList(final List<Optional<Boolean>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof GridProp).
				forEach(i -> ((GridProp) shapes.get(i)).setXLabelSouth(values.get(i).get()));
		}
	}


	@Override
	public final List<Optional<Boolean>> getGridXLabelSouthList() {
		return getShapes().stream().
			map(sh -> sh instanceof GridProp ? Optional.of(((GridProp) sh).isXLabelSouth()) : Optional.<Boolean>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setGridYLabelWestList(final List<Optional<Boolean>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof GridProp).
				forEach(i -> ((GridProp) shapes.get(i)).setYLabelWest(values.get(i).get()));
		}
	}


	@Override
	public final List<Optional<Boolean>> getGridYLabelWestList() {
		return getShapes().stream().
			map(sh -> sh instanceof GridProp ? Optional.of(((GridProp) sh).isYLabelWest()) : Optional.<Boolean>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setAxesStyleList(final List<Optional<AxesStyle>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof AxesProp).
				forEach(i -> ((AxesProp) shapes.get(i)).setAxesStyle(values.get(i).get()));
		}
	}

	@Override
	public List<Optional<AxesStyle>> getAxesStyleList() {
		return getShapes().stream().
			map(sh -> sh instanceof AxesProp ? Optional.of(((AxesProp) sh).getAxesStyle()) : Optional.<AxesStyle>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setGridOriginList(final List<Optional<Point>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof IStdGridProp).
				forEach(i -> ((IStdGridProp) shapes.get(i)).setOrigin(values.get(i).get().getX(),  values.get(i).get().getY()));
		}
	}

	@Override
	public List<Optional<Point>> getGridOriginList() {
		return getShapes().stream().
			map(sh -> sh instanceof IStdGridProp ? Optional.of(ShapeFactory.INST.createPoint(((IStdGridProp) sh).getOriginX(), ((IStdGridProp) sh).getOriginY()))
				: Optional.<Point>empty()).
			collect(Collectors.toList());
	}

	@Override
	public void setGridEndList(final List<Optional<Point>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof IStdGridProp).
				forEach(i -> ((IStdGridProp) shapes.get(i)).setGridEnd(values.get(i).get().getX(),  values.get(i).get().getY()));
		}
	}


	@Override
	public void setGridStartList(final List<Optional<Point>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof IStdGridProp).
				forEach(i -> ((IStdGridProp) shapes.get(i)).setGridStart(values.get(i).get().getX(),  values.get(i).get().getY()));
		}
	}

	@Override
	public List<Optional<Point>> getGridStartList() {
		return getShapes().stream().
			map(sh -> sh instanceof IStdGridProp ? Optional.of(((IStdGridProp) sh).getGridStart()) : Optional.<Point>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<Point>> getGridEndList() {
		return getShapes().stream().
			map(sh -> sh instanceof IStdGridProp ? Optional.of(((IStdGridProp) sh).getGridEnd()) : Optional.<Point>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<BorderPos>> getBordersPositionList() {
		return getShapes().stream().
			map(sh -> sh.isBordersMovable() ? Optional.of(sh.getBordersPosition()) : Optional.<BorderPos>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<Color>> getLineColourList() {
		return getShapes().stream().map(sh -> Optional.of(sh.getLineColour())).collect(Collectors.toList());
	}

	@Override
	public void setBordersPositionList(final List<Optional<BorderPos>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isBordersMovable()).
				forEach(i -> shapes.get(i).setBordersPosition(values.get(i).get()));
		}
	}


	@Override
	public void setLineColourList(final List<Optional<Color>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> shapes.get(i).setLineColour(values.get(i).get()));
		}
	}


	@Override
	public final List<Optional<Double>> getAngleStartList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArcProp ? Optional.of(((ArcProp) sh).getAngleStart()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public final List<Optional<Double>> getAngleEndList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArcProp ? Optional.of(((ArcProp) sh).getAngleEnd()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<ArcStyle>> getArcStyleList() {
		return getShapes().stream().
			map(sh -> sh instanceof ArcProp ? Optional.of(((ArcProp) sh).getArcStyle()) : Optional.<ArcStyle>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<TextPosition>> getTextPositionList() {
		return getShapes().stream().
			map(sh -> sh instanceof TextProp ? Optional.of(((TextProp) sh).getTextPosition()) : Optional.<TextPosition>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<String>> getTextList() {
		return getShapes().stream().
			map(sh -> sh instanceof TextProp ? Optional.of(((TextProp) sh).getText()) : Optional.<String>empty()).
			collect(Collectors.toList());
	}


	@Override
	public final List<Optional<Double>> getHatchingsAngleList() {
		return getShapes().stream().
			map(sh -> sh.isInteriorStylable() ? Optional.of(sh.getHatchingsAngle()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public final List<Optional<Double>> getHatchingsWidthList() {
		return getShapes().stream().
			map(sh -> sh.isInteriorStylable() ? Optional.of(sh.getHatchingsWidth()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public List<Optional<Double>> getHatchingsSepList() {
		return getShapes().stream().
			map(sh -> sh.isInteriorStylable() ? Optional.of(sh.getHatchingsSep()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public List<Optional<Double>> getGradAngleList() {
		return getShapes().stream().
			map(sh -> sh.isInteriorStylable() ? Optional.of(sh.getGradAngle()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public List<Optional<Double>> getGradMidPtList() {
		return getShapes().stream().
			map(sh -> sh.isInteriorStylable() ? Optional.of(sh.getGradMidPt()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public List<Optional<Double>> getLineArcList() {
		return getShapes().stream().
			map(sh -> sh instanceof LineArcProp ? Optional.of(((LineArcProp) sh).getLineArc()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public List<Optional<Color>> getFillingColList() {
		return getShapes().stream().
			map(sh -> sh.isInteriorStylable() ? Optional.of(sh.getFillingCol()) : Optional.<Color>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<Color>> getHatchingsColList() {
		return getShapes().stream().
			map(sh -> sh.isInteriorStylable() ? Optional.of(sh.getHatchingsCol()) : Optional.<Color>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<Boolean>> hasDbleBordList() {
		return getShapes().stream().
			map(sh -> sh.isDbleBorderable() ? Optional.of(sh.hasDbleBord()) : Optional.<Boolean>empty()).
			collect(Collectors.toList());
	}


	@Override
	public List<Optional<Double>> getDbleBordSepList() {
		return getShapes().stream().
			map(sh -> sh.isDbleBorderable() ? Optional.of(sh.getDbleBordSep()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public List<Optional<Color>> getDbleBordColList() {
		return getShapes().stream().
			map(sh -> sh.isDbleBorderable() ? Optional.of(sh.getDbleBordCol()) : Optional.<Color>empty()).
			collect(Collectors.toList());
	}

	@Override
	public final List<Optional<Boolean>> hasShadowList() {
		return getShapes().stream().
			map(sh -> sh.isShadowable() ? Optional.of(sh.hasShadow()) : Optional.<Boolean>empty()).
			collect(Collectors.toList());
	}


	@Override
	public List<Optional<Double>> getShadowSizeList() {
		return getShapes().stream().
			map(sh -> sh.isShadowable() ? Optional.of(sh.getShadowSize()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public List<Optional<Double>> getShadowAngleList() {
		return getShapes().stream().
			map(sh -> sh.isShadowable() ? Optional.of(sh.getShadowAngle()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<Color>> getShadowColList() {
		return getShapes().stream().
			map(sh -> sh.isShadowable() ? Optional.of(sh.getShadowCol()) : Optional.<Color>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<Color>> getGradColStartList() {
		return getShapes().stream().
			map(sh -> sh.isInteriorStylable() ? Optional.of(sh.getGradColStart()) : Optional.<Color>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<Color>> getGradColEndList() {
		return getShapes().stream().
			map(sh -> sh.isInteriorStylable() ? Optional.of(sh.getGradColEnd()) : Optional.<Color>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<Double>> getThicknessList() {
		return getShapes().stream().
			map(sh -> sh.isThicknessable() ? Optional.of(sh.getThickness()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<FillingStyle>> getFillingStyleList() {
		return getShapes().stream().
			map(sh -> sh.isInteriorStylable() ? Optional.of(sh.getFillingStyle()) : Optional.<FillingStyle>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<LineStyle>> getLineStyleList() {
		return getShapes().stream().
			map(sh -> sh.isLineStylable() ? Optional.of(sh.getLineStyle()) : Optional.<LineStyle>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<Color>> getDotFillingColList() {
		return getShapes().stream().
			map(sh -> sh instanceof DotProp ? Optional.of(((DotProp) sh).getDotFillingCol()) : Optional.<Color>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<DotStyle>> getDotStyleList() {
		return getShapes().stream().
			map(sh -> sh instanceof DotProp ? Optional.of(((DotProp) sh).getDotStyle()) : Optional.<DotStyle>empty()).
			collect(Collectors.toList());
	}

	@Override
	public List<Optional<Double>> getDotSizeList() {
		return getShapes().stream().
			map(sh -> sh instanceof DotProp ? Optional.of(((DotProp) sh).getDiametre()) : Optional.<Double>empty()).
			collect(Collectors.toList());
	}


	@Override
	public void setAngleStartList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof ArcProp).
				forEach(i -> ((ArcProp) shapes.get(i)).setAngleStart(values.get(i).get()));
		}
	}


	@Override
	public void setDotStyleList(final List<Optional<DotStyle>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof DotProp).
				forEach(i -> ((DotProp) shapes.get(i)).setDotStyle(values.get(i).get()));
		}
	}


	@Override
	public void setAngleEndList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof ArcProp).
				forEach(i -> ((ArcProp) shapes.get(i)).setAngleEnd(values.get(i).get()));
		}
	}


	@Override
	public void setArcStyleList(final List<Optional<ArcStyle>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof ArcProp).
				forEach(i -> ((ArcProp) shapes.get(i)).setArcStyle(values.get(i).get()));
		}
	}


	@Override
	public void setTextPositionList(final List<Optional<TextPosition>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof TextProp).
				forEach(i -> ((TextProp) shapes.get(i)).setTextPosition(values.get(i).get()));
		}
	}


	@Override
	public void setTextList(final List<Optional<String>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof TextProp).
				forEach(i -> ((TextProp) shapes.get(i)).setText(values.get(i).get()));
		}
	}


	@Override
	public void setHatchingsAngleList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isInteriorStylable()).
				forEach(i -> shapes.get(i).setHatchingsAngle(values.get(i).get()));
		}
	}


	@Override
	public void setHatchingsWidthList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isInteriorStylable()).
				forEach(i -> shapes.get(i).setHatchingsWidth(values.get(i).get()));
		}
	}


	@Override
	public void setHatchingsSepList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isInteriorStylable()).
				forEach(i -> shapes.get(i).setHatchingsSep(values.get(i).get()));
		}
	}


	@Override
	public void setGradAngleList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isInteriorStylable()).
				forEach(i -> shapes.get(i).setGradAngle(values.get(i).get()));
		}
	}


	@Override
	public void setGradMidPtList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isInteriorStylable()).
				forEach(i -> shapes.get(i).setGradMidPt(values.get(i).get()));
		}
	}


	@Override
	public void setLineArcList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof LineArcProp).
				forEach(i -> ((LineArcProp) shapes.get(i)).setLineArc(values.get(i).get()));
		}
	}


	@Override
	public void setFillingColList(final List<Optional<Color>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isInteriorStylable()).
				forEach(i -> shapes.get(i).setFillingCol(values.get(i).get()));
		}
	}


	@Override
	public void setHatchingsColList(final List<Optional<Color>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isInteriorStylable()).
				forEach(i -> shapes.get(i).setHatchingsCol(values.get(i).get()));
		}
	}


	@Override
	public void setHasDbleBordList(final List<Optional<Boolean>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isDbleBorderable()).
				forEach(i -> shapes.get(i).setHasDbleBord(values.get(i).get()));
		}
	}


	@Override
	public void setDbleBordSepList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isDbleBorderable()).
				forEach(i -> shapes.get(i).setDbleBordSep(values.get(i).get()));
		}
	}


	@Override
	public void setDbleBordColList(final List<Optional<Color>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isDbleBorderable()).
				forEach(i -> shapes.get(i).setDbleBordCol(values.get(i).get()));
		}
	}


	@Override
	public void setHasShadowList(final List<Optional<Boolean>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isShadowable()).
				forEach(i -> shapes.get(i).setHasShadow(values.get(i).get()));
		}
	}


	@Override
	public void setShadowSizeList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isShadowable()).
				forEach(i -> shapes.get(i).setShadowSize(values.get(i).get()));
		}
	}


	@Override
	public void setShadowAngleList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isShadowable()).
				forEach(i -> shapes.get(i).setShadowAngle(values.get(i).get()));
		}
	}


	@Override
	public void setShadowColList(final List<Optional<Color>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isShadowable()).
				forEach(i -> shapes.get(i).setShadowCol(values.get(i).get()));
		}
	}


	@Override
	public void setGradColStartList(final List<Optional<Color>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isInteriorStylable()).
				forEach(i -> shapes.get(i).setGradColStart(values.get(i).get()));
		}
	}


	@Override
	public void setGradColEndList(final List<Optional<Color>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isInteriorStylable()).
				forEach(i -> shapes.get(i).setGradColEnd(values.get(i).get()));
		}
	}


	@Override
	public void setThicknessList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isThicknessable()).
				forEach(i -> shapes.get(i).setThickness(values.get(i).get()));
		}
	}


	@Override
	public void setFillingStyleList(final List<Optional<FillingStyle>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isInteriorStylable()).
				forEach(i -> shapes.get(i).setFillingStyle(values.get(i).get()));
		}
	}

	@Override
	public void setLineStyleList(final List<Optional<LineStyle>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isLineStylable()).
				forEach(i -> shapes.get(i).setLineStyle(values.get(i).get()));
		}
	}


	@Override
	public void setDotFillingColList(final List<Optional<Color>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof DotProp).
				forEach(i -> ((DotProp) shapes.get(i)).setDotFillingCol(values.get(i).get()));
		}
	}


	@Override
	public void setDotSizeList(final List<Optional<Double>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i) instanceof DotProp).
				forEach(i -> ((DotProp) shapes.get(i)).setDiametre(values.get(i).get()));
		}
	}


	@Override
	public void setShowPointsList(final List<Optional<Boolean>> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).
				filter(i -> values.get(i).isPresent() && shapes.get(i).isShowPtsable()).
				forEach(i -> shapes.get(i).setShowPts(values.get(i).get()));
		}
	}


	@Override
	public List<Optional<Boolean>> getShowPointsList() {
		return getShapes().stream().
			map(sh -> sh.isShowPtsable() ? Optional.of(sh.isShowPts()) : Optional.<Boolean>empty()).
			collect(Collectors.toList());
	}

	@Override
	public ListProperty<Shape> getShapes() {
		return shapes;
	}

	@Override
	public List<Arrow> getArrows() {
		return Collections.emptyList(); // FIXME: collect all the arrows?
	}
}
