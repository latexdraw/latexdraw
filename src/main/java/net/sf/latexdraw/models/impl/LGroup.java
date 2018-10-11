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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.models.interfaces.prop.IClosableProp;
import net.sf.latexdraw.models.interfaces.prop.IDotProp;
import net.sf.latexdraw.models.interfaces.prop.IFreeHandProp;
import net.sf.latexdraw.models.interfaces.prop.IGridProp;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.models.interfaces.prop.IScalable;
import net.sf.latexdraw.models.interfaces.prop.IStdGridProp;
import net.sf.latexdraw.models.interfaces.prop.ITextProp;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import net.sf.latexdraw.view.latex.DviPsColors;

/**
 * An implemenation of the IGroup interface.
 * @author Arnaud Blouin
 */
class LGroup implements LGroupArc, LGroupArrowable, LGroupAxes, LGroupDot, LGroupFreeHand, LGroupLineArc, LGroupGrid,
	LGroupShape, LGroupStdGrid, LGroupText, LSetShapes, LGroupPlot, LGroupClosable {
	/** The set of shapes. */
	private final ObservableList<IShape> shapes;
	private final DoubleProperty rotationAngle;

	LGroup() {
		super();
		shapes = FXCollections.observableArrayList();
		rotationAngle = new SimpleDoubleProperty();
	}

	@Override
	public IGroup duplicate() {
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
	public IGroup duplicateDeep(final boolean duplicateShapes) {
		final IGroup dup = ShapeFactory.INST.createGroup();

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
		if(clazz.equals(getClass()) || clazz.equals(IShape.class) || clazz.equals(LShape.class) || clazz.equals(IGroup.class)) {
			return true;
		}
		return shapes.parallelStream().anyMatch(sh -> sh.isTypeOf(clazz));
	}

	@Override
	public DoubleProperty rotationAngleProperty() {
		return rotationAngle;
	}

	@Override
	public void setPlotPolarList(final List<Boolean> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IPlotProp) {
					((IPlotProp) sh).setPolar(values.get(i));
				}
			});
		}
	}

	@Override
	public final List<Boolean> getPlotPolarList() {
		return getShapes().stream().map(sh -> sh instanceof IPlotProp && ((IPlotProp) sh).isPolar()).collect(Collectors.toList());
	}

	@Override
	public void setYScaleList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IScalable) {
					((IScalable) sh).setYScale(values.get(i));
				}
			});
		}
	}

	@Override
	public List<Double> getYScaleList() {
		return getShapes().stream().map(sh -> sh instanceof IScalable ? ((IScalable) sh).getYScale() : null).collect(Collectors.toList());
	}

	@Override
	public void setXScaleList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IScalable) {
					((IPlotProp) sh).setXScale(values.get(i));
				}
			});
		}
	}

	@Override
	public List<Double> getXScaleList() {
		return getShapes().stream().map(sh -> sh instanceof IScalable ? ((IScalable) sh).getXScale() : null).collect(Collectors.toList());
	}

	@Override
	public void setPlotMinXList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IPlotProp) {
					((IPlotProp) sh).setPlotMinX(values.get(i));
				}
			});
		}
	}

	@Override
	public List<Double> getPlotMinXList() {
		return getShapes().stream().map(sh -> sh instanceof IPlotProp ? ((IPlotProp) sh).getPlotMinX() : null).collect(Collectors.toList());
	}

	@Override
	public void setPlotMaxXList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IPlotProp) {
					((IPlotProp) sh).setPlotMaxX(values.get(i));
				}
			});
		}
	}

	@Override
	public List<Double> getPlotMaxXList() {
		return getShapes().stream().map(sh -> sh instanceof IPlotProp ? ((IPlotProp) sh).getPlotMaxX() : null).collect(Collectors.toList());
	}

	@Override
	public void setNbPlottedPointsList(final List<Integer> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IPlotProp) {
					((IPlotProp) sh).setNbPlottedPoints(values.get(i));
				}
			});
		}
	}

	@Override
	public List<Integer> getNbPlottedPointsList() {
		return getShapes().stream().map(sh -> sh instanceof IPlotProp ? ((IPlotProp) sh).getNbPlottedPoints() : null).collect(Collectors.toList());
	}

	@Override
	public void setPlotStyleList(final List<PlotStyle> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IPlotProp) {
					((IPlotProp) sh).setPlotStyle(values.get(i));
				}
			});
		}
	}

	@Override
	public List<PlotStyle> getPlotStyleList() {
		return getShapes().stream().map(sh -> sh instanceof IPlotProp ? ((IPlotProp) sh).getPlotStyle() : null).collect(Collectors.toList());
	}

	@Override
	public void setPlotEquationList(final List<String> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IPlotProp) {
					((IPlotProp) sh).setPlotEquation(values.get(i));
				}
			});
		}
	}

	@Override
	public List<String> getPlotEquationList() {
		return getShapes().stream().map(sh -> sh instanceof IPlotProp ? ((IPlotProp) sh).getPlotEquation() : null).collect(Collectors.toList());
	}

	@Override
	public void setFreeHandIntervalList(final List<Integer> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IFreeHandProp) {
					((IFreeHandProp) sh).setInterval(values.get(i));
				}
			});
		}
	}


	@Override
	public List<Integer> getFreeHandIntervalList() {
		return getShapes().stream().map(sh -> sh instanceof IFreeHandProp ? ((IFreeHandProp) sh).getInterval() : null).collect(Collectors.toList());
	}


	@Override
	public void setOpenList(final List<Boolean> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IClosableProp) {
					((IClosableProp) sh).setOpened(values.get(i));
				}
			});
		}
	}


	@Override
	public final List<Boolean> getOpenList() {
		return getShapes().stream().map(sh -> sh instanceof IClosableProp ? ((IClosableProp) sh).isOpened() : null).collect(Collectors.toList());
	}


	@Override
	public void setGridLabelsColourList(final List<Color> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IGridProp) {
					((IGridProp) sh).setGridLabelsColour(values.get(i));
				}
			});
		}
	}


	@Override
	public List<Color> getGridLabelsColourList() {
		return getShapes().stream().map(sh -> sh instanceof IGridProp ? ((IGridProp) sh).getGridLabelsColour() : DviPsColors.BLACK).collect(Collectors.toList());
	}

	@Override
	public void setSubGridColourList(final List<Color> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IGridProp) {
					((IGridProp) sh).setSubGridColour(values.get(i));
				}
			});
		}
	}


	@Override
	public List<Color> getSubGridColourList() {
		return getShapes().stream().map(sh -> sh instanceof IGridProp ? ((IGridProp) sh).getSubGridColour() : DviPsColors.BLACK).collect(Collectors.toList());
	}

	@Override
	public void setGridWidthList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IGridProp) {
					((IGridProp) sh).setGridWidth(values.get(i));
				}
			});
		}
	}


	@Override
	public final List<Double> getGridWidthList() {
		return getShapes().stream().map(sh -> sh instanceof IGridProp ? ((IGridProp) sh).getGridWidth() : null).collect(Collectors.toList());
	}


	@Override
	public final void setSubGridWidthList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IGridProp) {
					((IGridProp) sh).setSubGridWidth(values.get(i));
				}
			});
		}
	}


	@Override
	public final List<Double> getSubGridWidthList() {
		return getShapes().stream().map(sh -> sh instanceof IGridProp ? ((IGridProp) sh).getSubGridWidth() : null).collect(Collectors.toList());
	}


	@Override
	public void setGridDotsList(final List<Integer> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IGridProp) {
					((IGridProp) sh).setGridDots(values.get(i));
				}
			});
		}
	}


	@Override
	public List<Integer> getGridDotsList() {
		return getShapes().stream().map(sh -> sh instanceof IGridProp ? ((IGridProp) sh).getGridDots() : null).collect(Collectors.toList());
	}


	@Override
	public void setSubGridDotsList(final List<Integer> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IGridProp) {
					((IGridProp) sh).setSubGridDots(values.get(i));
				}
			});
		}
	}


	@Override
	public List<Integer> getSubGridDotsList() {
		return getShapes().stream().map(sh -> sh instanceof IGridProp ? ((IGridProp) sh).getSubGridDots() : null).collect(Collectors.toList());
	}


	@Override
	public void setSubGridDivList(final List<Integer> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IGridProp) {
					((IGridProp) sh).setSubGridDiv(values.get(i));
				}
			});
		}
	}


	@Override
	public List<Integer> getSubGridDivList() {
		return getShapes().stream().map(sh -> sh instanceof IGridProp ? ((IGridProp) sh).getSubGridDiv() : null).collect(Collectors.toList());
	}


	@Override
	public void setFreeHandTypeList(final List<FreeHandStyle> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IFreeHandProp) {
					((IFreeHandProp) sh).setType(values.get(i));
				}
			});
		}
	}

	@Override
	public List<FreeHandStyle> getFreeHandTypeList() {
		return getShapes().stream().map(sh -> sh instanceof IFreeHandProp ? ((IFreeHandProp) sh).getType() : FreeHandStyle.CURVES).collect(Collectors.toList());
	}

	@Override
	public void setAxesDistLabelsList(final List<IPoint> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IAxesProp) {
					((IAxesProp) sh).setDistLabels(values.get(i));
				}
			});
		}
	}

	@Override
	public List<IPoint> getAxesDistLabelsList() {
		return getShapes().stream().map(sh -> sh instanceof IAxesProp ? ((IAxesProp) sh).getDistLabels() : null).collect(Collectors.toList());
	}

	@Override
	public void setAxesLabelsDisplayedList(final List<PlottingStyle> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IAxesProp) {
					((IAxesProp) sh).setLabelsDisplayed(values.get(i));
				}
			});
		}
	}

	@Override
	public List<PlottingStyle> getAxesLabelsDisplayedList() {
		return getShapes().stream().map(sh -> sh instanceof IAxesProp ? ((IAxesProp) sh).getLabelsDisplayed() : PlottingStyle.NONE).collect(Collectors.toList());
	}

	@Override
	public void setAxesShowOriginList(final List<Boolean> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IAxesProp) {
					((IAxesProp) sh).setShowOrigin(values.get(i));
				}
			});
		}
	}


	@Override
	public final List<Boolean> getAxesShowOriginList() {
		return getShapes().stream().map(sh -> sh instanceof IAxesProp ? ((IAxesProp) sh).isShowOrigin() : null).collect(Collectors.toList());
	}


	@Override
	public void setAxesTicksStyleList(final List<TicksStyle> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IAxesProp) {
					((IAxesProp) sh).setTicksStyle(values.get(i));
				}
			});
		}
	}

	@Override
	public List<TicksStyle> getAxesTicksStyleList() {
		return getShapes().stream().map(sh -> sh instanceof IAxesProp ? ((IAxesProp) sh).getTicksStyle() : TicksStyle.FULL).collect(Collectors.toList());
	}

	@Override
	public void setAxesTicksSizeList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IAxesProp) {
					((IAxesProp) sh).setTicksSize(values.get(i));
				}
			});
		}
	}


	@Override
	public final List<Double> getAxesTicksSizeList() {
		return getShapes().stream().map(sh -> sh instanceof IAxesProp ? ((IAxesProp) sh).getTicksSize() : null).collect(Collectors.toList());
	}


	@Override
	public void setAxesTicksDisplayedList(final List<PlottingStyle> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IAxesProp) {
					((IAxesProp) sh).setTicksDisplayed(values.get(i));
				}
			});
		}
	}

	@Override
	public List<PlottingStyle> getAxesTicksDisplayedList() {
		return getShapes().stream().map(sh -> sh instanceof IAxesProp ? ((IAxesProp) sh).getTicksDisplayed() : PlottingStyle.NONE).collect(Collectors.toList());
	}

	@Override
	public void setAxesIncrementsList(final List<IPoint> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IAxesProp) {
					((IAxesProp) sh).setIncrement(values.get(i));
				}
			});
		}
	}

	@Override
	public List<IPoint> getAxesIncrementsList() {
		return getShapes().stream().map(sh -> sh instanceof IAxesProp ? ((IAxesProp) sh).getIncrement() : null).collect(Collectors.toList());
	}

	@Override
	public void setGridLabelSizeList(final List<Integer> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IStdGridProp) {
					((IStdGridProp) sh).setLabelsSize(values.get(i));
				}
			});
		}
	}


	@Override
	public List<Integer> getGridLabelSizeList() {
		return getShapes().stream().map(sh -> sh instanceof IStdGridProp ? ((IStdGridProp) sh).getLabelsSize() : null).collect(Collectors.toList());
	}


	@Override
	public void setGridXLabelSouthList(final List<Boolean> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IGridProp) {
					((IGridProp) sh).setXLabelSouth(values.get(i));
				}
			});
		}
	}


	@Override
	public final List<Boolean> getGridXLabelSouthList() {
		return getShapes().stream().map(sh -> sh instanceof IGridProp ? ((IGridProp) sh).isXLabelSouth() : null).collect(Collectors.toList());
	}


	@Override
	public void setGridYLabelWestList(final List<Boolean> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IGridProp) {
					((IGridProp) sh).setYLabelWest(values.get(i));
				}
			});
		}
	}


	@Override
	public final List<Boolean> getGridYLabelWestList() {
		return getShapes().stream().map(sh -> sh instanceof IGridProp ? ((IGridProp) sh).isYLabelWest() : null).collect(Collectors.toList());
	}


	@Override
	public void setAxesStyleList(final List<AxesStyle> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IAxesProp) {
					((IAxesProp) sh).setAxesStyle(values.get(i));
				}
			});
		}
	}

	@Override
	public List<AxesStyle> getAxesStyleList() {
		return getShapes().stream().map(sh -> sh instanceof IAxesProp ? ((IAxesProp) sh).getAxesStyle() : AxesStyle.NONE).collect(Collectors.toList());
	}

	@Override
	public void setGridOriginList(final List<IPoint> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				final IPoint pt = values.get(i);
				if(pt != null && sh instanceof IStdGridProp) {
					((IStdGridProp) sh).setOrigin(pt.getX(), pt.getY());
				}
			});
		}
	}

	@Override
	public List<IPoint> getGridOriginList() {
		return getShapes().stream().map(sh -> sh instanceof IStdGridProp ?
			ShapeFactory.INST.createPoint(((IStdGridProp) sh).getOriginX(), ((IStdGridProp) sh).getOriginY()) : null).collect(Collectors.toList());
	}

	@Override
	public void setGridEndList(final List<IPoint> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				final IPoint pt = values.get(i);
				if(pt != null && sh instanceof IStdGridProp) {
					((IStdGridProp) sh).setGridEnd(pt.getX(), pt.getY());
				}
			});
		}
	}


	@Override
	public void setGridStartList(final List<IPoint> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				final IPoint pt = values.get(i);
				if(pt != null && sh instanceof IStdGridProp) {
					((IStdGridProp) sh).setGridStart(pt.getX(), pt.getY());
				}
			});
		}
	}

	@Override
	public List<IPoint> getGridStartList() {
		return getShapes().stream().map(sh -> sh instanceof IStdGridProp ? ((IStdGridProp) sh).getGridStart() : null).collect(Collectors.toList());
	}

	@Override
	public List<IPoint> getGridEndList() {
		return getShapes().stream().map(sh -> sh instanceof IStdGridProp ? ((IStdGridProp) sh).getGridEnd() : null).collect(Collectors.toList());
	}

	@Override
	public List<BorderPos> getBordersPositionList() {
		return getShapes().stream().map(sh -> sh.isBordersMovable() ? sh.getBordersPosition() : null).collect(Collectors.toList());
	}

	@Override
	public List<Color> getLineColourList() {
		return getShapes().stream().map(sh -> sh.getLineColour()).collect(Collectors.toList());
	}

	@Override
	public void setBordersPositionList(final List<BorderPos> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isBordersMovable()) {
					sh.setBordersPosition(values.get(i));
				}
			});
		}
	}


	@Override
	public void setLineColourList(final List<Color> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> shapes.get(i).setLineColour(values.get(i)));
		}
	}


	@Override
	public final List<Double> getAngleStartList() {
		return getShapes().stream().map(sh -> sh instanceof IArcProp ? ((IArcProp) sh).getAngleStart() : null).collect(Collectors.toList());
	}


	@Override
	public final List<Double> getAngleEndList() {
		return getShapes().stream().map(sh -> sh instanceof IArcProp ? ((IArcProp) sh).getAngleEnd() : null).collect(Collectors.toList());
	}

	@Override
	public List<ArcStyle> getArcStyleList() {
		return getShapes().stream().map(sh -> sh instanceof IArcProp ? ((IArcProp) sh).getArcStyle() : null).collect(Collectors.toList());
	}

	@Override
	public final List<Double> getRotationAngleList() {
		return getShapes().stream().map(sh -> sh.getRotationAngle()).collect(Collectors.toList());
	}

	@Override
	public List<TextPosition> getTextPositionList() {
		return getShapes().stream().map(sh -> sh instanceof ITextProp ? ((ITextProp) sh).getTextPosition() : null).collect(Collectors.toList());
	}

	@Override
	public List<String> getTextList() {
		return getShapes().stream().map(sh -> sh instanceof ITextProp ? ((ITextProp) sh).getText() : null).collect(Collectors.toList());
	}


	@Override
	public final List<Double> getHatchingsAngleList() {
		return getShapes().stream().map(sh -> sh.isInteriorStylable() ? sh.getHatchingsAngle() : null).collect(Collectors.toList());
	}


	@Override
	public final List<Double> getHatchingsWidthList() {
		return getShapes().stream().map(sh -> sh.isInteriorStylable() ? sh.getHatchingsWidth() : null).collect(Collectors.toList());
	}


	@Override
	public List<Double> getHatchingsSepList() {
		return getShapes().stream().map(sh -> sh.isInteriorStylable() ? sh.getHatchingsSep() : null).collect(Collectors.toList());
	}


	@Override
	public List<Double> getGradAngleList() {
		return getShapes().stream().map(sh -> sh.isInteriorStylable() ? sh.getGradAngle() : null).collect(Collectors.toList());
	}


	@Override
	public List<Double> getGradMidPtList() {
		return getShapes().stream().map(sh -> sh.isInteriorStylable() ? sh.getGradMidPt() : null).collect(Collectors.toList());
	}


	@Override
	public List<Double> getLineArcList() {
		return getShapes().stream().map(sh -> sh instanceof ILineArcProp ? ((ILineArcProp) sh).getLineArc() : null).collect(Collectors.toList());
	}


	@Override
	public List<Color> getFillingColList() {
		return getShapes().stream().map(sh -> sh.isInteriorStylable() ? sh.getFillingCol() : null).collect(Collectors.toList());
	}

	@Override
	public List<Color> getHatchingsColList() {
		return getShapes().stream().map(sh -> sh.isInteriorStylable() ? sh.getHatchingsCol() : null).collect(Collectors.toList());
	}

	@Override
	public List<Boolean> hasDbleBordList() {
		return getShapes().stream().map(sh -> sh.isDbleBorderable() ? sh.hasDbleBord() : null).collect(Collectors.toList());
	}


	@Override
	public List<Double> getDbleBordSepList() {
		return getShapes().stream().map(sh -> sh.isDbleBorderable() ? sh.getDbleBordSep() : null).collect(Collectors.toList());
	}


	@Override
	public List<Color> getDbleBordColList() {
		return getShapes().stream().map(sh -> sh.isDbleBorderable() ? sh.getDbleBordCol() : null).collect(Collectors.toList());
	}

	@Override
	public final List<Boolean> hasShadowList() {
		return getShapes().stream().map(sh -> sh.isShadowable() ? sh.hasShadow() : null).collect(Collectors.toList());
	}


	@Override
	public List<Double> getShadowSizeList() {
		return getShapes().stream().map(sh -> sh.isShadowable() ? sh.getShadowSize() : null).collect(Collectors.toList());
	}


	@Override
	public List<Double> getShadowAngleList() {
		return getShapes().stream().map(sh -> sh.isShadowable() ? sh.getShadowAngle() : null).collect(Collectors.toList());
	}

	@Override
	public List<Color> getShadowColList() {
		return getShapes().stream().map(sh -> sh.isShadowable() ? sh.getShadowCol() : null).collect(Collectors.toList());
	}

	@Override
	public List<Color> getGradColStartList() {
		return getShapes().stream().map(sh -> sh.isInteriorStylable() ? sh.getGradColStart() : null).collect(Collectors.toList());
	}

	@Override
	public List<Color> getGradColEndList() {
		return getShapes().stream().map(sh -> sh.isInteriorStylable() ? sh.getGradColEnd() : null).collect(Collectors.toList());
	}

	@Override
	public List<Double> getThicknessList() {
		return getShapes().stream().map(sh -> sh.isThicknessable() ? sh.getThickness() : null).collect(Collectors.toList());
	}

	@Override
	public List<FillingStyle> getFillingStyleList() {
		return getShapes().stream().map(sh -> sh.isInteriorStylable() ? sh.getFillingStyle() : null).collect(Collectors.toList());
	}

	@Override
	public List<LineStyle> getLineStyleList() {
		return getShapes().stream().map(sh -> sh.isLineStylable() ? sh.getLineStyle() : null).collect(Collectors.toList());
	}

	@Override
	public List<Color> getDotFillingColList() {
		return getShapes().stream().map(sh -> sh instanceof IDotProp ? ((IDotProp) sh).getDotFillingCol() : null).collect(Collectors.toList());
	}

	@Override
	public List<DotStyle> getDotStyleList() {
		return getShapes().stream().map(sh -> sh instanceof IDotProp ? ((IDotProp) sh).getDotStyle() : null).collect(Collectors.toList());
	}

	@Override
	public List<Double> getDotSizeList() {
		return getShapes().stream().map(sh -> sh instanceof IDotProp ? ((IDotProp) sh).getDiametre() : null).collect(Collectors.toList());
	}


	@Override
	public void setAngleStartList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArcProp) {
					((IArcProp) sh).setAngleStart(values.get(i));
				}
			});
		}
	}


	@Override
	public void setDotStyleList(final List<DotStyle> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IDotProp) {
					((IDotProp) sh).setDotStyle(values.get(i));
				}
			});
		}
	}


	@Override
	public void setAngleEndList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArcProp) {
					((IArcProp) sh).setAngleEnd(values.get(i));
				}
			});
		}
	}


	@Override
	public void setArcStyleList(final List<ArcStyle> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IArcProp) {
					((IArcProp) sh).setArcStyle(values.get(i));
				}
			});
		}
	}


	@Override
	public void setRotationAngleList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> shapes.get(i).setRotationAngle(values.get(i)));
		}
	}


	@Override
	public void setTextPositionList(final List<TextPosition> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof ITextProp) {
					((ITextProp) sh).setTextPosition(values.get(i));
				}
			});
		}
	}


	@Override
	public void setTextList(final List<String> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof ITextProp) {
					((ITextProp) sh).setText(values.get(i));
				}
			});
		}
	}


	@Override
	public void setHatchingsAngleList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isInteriorStylable()) {
					sh.setHatchingsAngle(values.get(i));
				}
			});
		}
	}


	@Override
	public void setHatchingsWidthList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isInteriorStylable()) {
					sh.setHatchingsWidth(values.get(i));
				}
			});
		}
	}


	@Override
	public void setHatchingsSepList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isInteriorStylable()) {
					sh.setHatchingsSep(values.get(i));
				}
			});
		}
	}


	@Override
	public void setGradAngleList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isInteriorStylable()) {
					sh.setGradAngle(values.get(i));
				}
			});
		}
	}


	@Override
	public void setGradMidPtList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isInteriorStylable()) {
					sh.setGradMidPt(values.get(i));
				}
			});
		}
	}


	@Override
	public void setLineArcList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof ILineArcProp) {
					((ILineArcProp) sh).setLineArc(values.get(i));
				}
			});
		}
	}


	@Override
	public void setFillingColList(final List<Color> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isInteriorStylable()) {
					sh.setFillingCol(values.get(i));
				}
			});
		}
	}


	@Override
	public void setHatchingsColList(final List<Color> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isInteriorStylable()) {
					sh.setHatchingsCol(values.get(i));
				}
			});
		}
	}


	@Override
	public void setHasDbleBordList(final List<Boolean> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isDbleBorderable()) {
					sh.setHasDbleBord(values.get(i));
				}
			});
		}
	}


	@Override
	public void setDbleBordSepList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isDbleBorderable()) {
					sh.setDbleBordSep(values.get(i));
				}
			});
		}
	}


	@Override
	public void setDbleBordColList(final List<Color> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isDbleBorderable()) {
					sh.setDbleBordCol(values.get(i));
				}
			});
		}
	}


	@Override
	public void setHasShadowList(final List<Boolean> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isShadowable()) {
					sh.setHasShadow(values.get(i));
				}
			});
		}
	}


	@Override
	public void setShadowSizeList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isShadowable()) {
					sh.setShadowSize(values.get(i));
				}
			});
		}
	}


	@Override
	public void setShadowAngleList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isShadowable()) {
					sh.setShadowAngle(values.get(i));
				}
			});
		}
	}


	@Override
	public void setShadowColList(final List<Color> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isShadowable()) {
					sh.setShadowCol(values.get(i));
				}
			});
		}
	}


	@Override
	public void setGradColStartList(final List<Color> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isInteriorStylable()) {
					sh.setGradColStart(values.get(i));
				}
			});
		}
	}


	@Override
	public void setGradColEndList(final List<Color> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isInteriorStylable()) {
					sh.setGradColEnd(values.get(i));
				}
			});
		}
	}


	@Override
	public void setThicknessList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isThicknessable()) {
					sh.setThickness(values.get(i));
				}
			});
		}
	}


	@Override
	public void setFillingStyleList(final List<FillingStyle> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isInteriorStylable()) {
					sh.setFillingStyle(values.get(i));
				}
			});
		}
	}

	@Override
	public void setLineStyleList(final List<LineStyle> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isLineStylable()) {
					sh.setLineStyle(values.get(i));
				}
			});
		}
	}


	@Override
	public void setDotFillingColList(final List<Color> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IDotProp) {
					((IDotProp) sh).setDotFillingCol(values.get(i));
				}
			});
		}
	}


	@Override
	public void setDotSizeList(final List<Double> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh instanceof IDotProp) {
					((IDotProp) sh).setDiametre(values.get(i));
				}
			});
		}
	}


	@Override
	public void setShowPointsList(final List<Boolean> values) {
		if(values != null && values.size() == shapes.size()) {
			IntStream.range(0, values.size()).forEach(i -> {
				final IShape sh = shapes.get(i);
				if(sh.isShowPtsable()) {
					sh.setShowPts(values.get(i));
				}
			});
		}
	}


	@Override
	public List<Boolean> getShowPointsList() {
		return getShapes().stream().map(sh -> sh.isShowPtsable() ? sh.isShowPts() : null).collect(Collectors.toList());
	}

	@Override
	public ObservableList<IShape> getShapes() {
		return shapes;
	}

	@Override
	public List<IArrow> getArrows() {
		return Collections.emptyList(); // FIXME: collect all the arrows?
	}
}
