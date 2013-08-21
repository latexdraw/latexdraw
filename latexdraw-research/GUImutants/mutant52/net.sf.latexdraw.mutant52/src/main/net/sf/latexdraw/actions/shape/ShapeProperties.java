package net.sf.latexdraw.actions.shape;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.IArc.ArcStyle;
import net.sf.latexdraw.glib.models.interfaces.*;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.PlottingStyle;
import net.sf.latexdraw.glib.models.interfaces.IAxes.TicksStyle;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IFreehand.FreeHandType;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition;

/**
 * Defines shape properties.<br>
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
 * 05/19/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public enum ShapeProperties {
	/** Show/Hide the origin of the axes. */
	SHOW_POINTS {
		@Override
		public String getMessage() {
			return "show points";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}

		@Override
		public List<Boolean> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Boolean>() : group.getShowPointsList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setShowPts((Boolean)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setShowPointsList((List<Boolean>)values);
		}
	},
	/** Show/Hide the origin of the axes. */
	AXES_SHOW_ORIGIN {
		@Override
		public String getMessage() {
			return "axe's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}

		@Override
		public List<Boolean> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Boolean>() : group.getAxesShowOriginList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setShowOrigin((Boolean)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setAxesShowOriginList((List<Boolean>)values);
		}
	},
	/** The increment of the axes' labels. */
	AXES_LABELS_DIST {
		@Override
		public String getMessage() {
			return "axe's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof IPoint;
		}

		@Override
		public List<IPoint> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<IPoint>() : group.getAxesDistLabelsList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setDistLabels((IPoint)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setAxesDistLabelsList((List<IPoint>)values);
		}
	},
	/** The increment of the axes' labels. */
	AXES_LABELS_INCR {
		@Override
		public String getMessage() {
			return "axe's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof IPoint;
		}

		@Override
		public List<IPoint> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<IPoint>() : group.getAxesIncrementsList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setIncrement((IPoint)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setAxesIncrementsList((List<IPoint>)values);
		}
	},
	/** How the labels of axes are displayed. */
	AXES_LABELS_SHOW {
		@Override
		public String getMessage() {
			return "axe's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof PlottingStyle;
		}

		@Override
		public List<PlottingStyle> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<PlottingStyle>() : group.getAxesLabelsDisplayedList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setLabelsDisplayed((PlottingStyle)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setAxesLabelsDisplayedList((List<PlottingStyle>)values);
		}
	},
	/** How the ticks of axes are displayed. */
	AXES_TICKS_SHOW {
		@Override
		public String getMessage() {
			return "axe's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof PlottingStyle;
		}

		@Override
		public List<PlottingStyle> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<PlottingStyle>() : group.getAxesTicksDisplayedList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setTicksDisplayed((PlottingStyle)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setAxesTicksDisplayedList((List<PlottingStyle>)values);
		}
	},
	/** The width of the sub-grids. */
	GRID_SUBGRID_WIDTH {
		@Override
		public String getMessage() {
			return "grid's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getSubGridWidthList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setSubGridWidth((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setSubGridWidthList((List<Double>)values);
		}
	},
	/** Defines if the free hand shapes are open. */
	FREEHAND_OPEN {
		@Override
		public String getMessage() {
			return "free hand's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}

		@Override
		public List<Boolean> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Boolean>() : group.getFreeHandOpenList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setOpen((Boolean)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setFreeHandOpenList((List<Boolean>)values);
		}
	},
	/** The interval between the points of free hand shapes. */
	FREEHAND_INTERVAL {
		@Override
		public String getMessage() {
			return "free hand's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Integer;
		}

		@Override
		public List<Integer> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Integer>() : group.getFreeHandIntervalList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setInterval((Integer)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setFreeHandIntervalList((List<Integer>)values);
		}
	},
	/** The division the sub-lines of grids. */
	GRID_SUBGRID_DIV {
		@Override
		public String getMessage() {
			return "grid's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Integer;
		}

		@Override
		public List<Integer> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Integer>() : group.getSubGridDivList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setSubGridDiv((Integer)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setSubGridDivList((List<Integer>)values);
		}
	},
	/** The number of dots composing the sub-lines of grids. */
	GRID_SUBGRID_DOTS {
		@Override
		public String getMessage() {
			return "grid's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Integer;
		}

		@Override
		public List<Integer> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Integer>() : group.getSubGridDotsList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setSubGridDots((Integer)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setSubGridDotsList((List<Integer>)values);
		}
	},
	/** The number of dots composing the main lines of grids. */
	GRID_DOTS {
		@Override
		public String getMessage() {
			return "grid's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Integer;
		}

		@Override
		public List<Integer> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Integer>() : group.getGridDotsList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setGridDots((Integer)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGridDotsList((List<Integer>)values);
		}
	},
	/** The width of the grids. */
	GRID_WIDTH {
		@Override
		public String getMessage() {
			return "grid's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getGridWidthList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setGridWidth((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGridWidthList((List<Double>)values);
		}
	},
//	/** The size of the ticks of axes. */
//	AXES_TICKS_SIZE {
//		@Override
//		public String getMessage() {
//			return "axe's parameters";
//		}
//
//		@Override
//		public boolean isValueValid(final Object obj) {
//			return obj instanceof Double;
//		}
//
//		@Override
//		public List<Double> getPropertyValues(final IGroup group) {
//			return group==null ? new ArrayList<Double>() : group.getAxesTicksSizeList();
//		}
//
//		@Override
//		public void setPropertyValue(final IGroup group, final Object value) {
//			if(group!=null && isValueValid(value))
//				group.setTicksSize((Double)value);
//		}
//
//		@SuppressWarnings("unchecked")
//		@Override
//		public void setPropertyValueList(final IGroup group, final List<?> values) {
//			if(group!=null)
//				group.setAxesTicksSizeList((List<Double>)values);
//		}
//	},
	/** The style of the ticks of axes. */
	AXES_TICKS_STYLE {
		@Override
		public String getMessage() {
			return "axe's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof TicksStyle;
		}

		@Override
		public List<TicksStyle> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<TicksStyle>() : group.getAxesTicksStyleList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setTicksStyle((TicksStyle)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setAxesTicksStyleList((List<TicksStyle>)values);
		}
	},
	/** The style of axes. */
	FREEHAND_STYLE {
		@Override
		public String getMessage() {
			return "free hand's parameters";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof FreeHandType;
		}

		@Override
		public List<FreeHandType> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<FreeHandType>() : group.getFreeHandTypeList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setType((FreeHandType)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setFreeHandTypeList((List<FreeHandType>)values);
		}
	},
	/** The style of axes. */
	AXES_STYLE {
		@Override
		public String getMessage() {
			return "axe's style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof AxesStyle;
		}

		@Override
		public List<AxesStyle> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<AxesStyle>() : group.getAxesStyleList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setAxesStyle((AxesStyle)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setAxesStyleList((List<AxesStyle>)values);
		}
	},
	/** The X-coordinate of the grid's labels. */
	GRID_LABEL_POSITION_X {
		@Override
		public String getMessage() {
			return "grid's labels position";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}

		@Override
		public List<Boolean> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Boolean>() : group.getGridYLabelWestList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setYLabelWest((Boolean)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGridYLabelWestList((List<Boolean>)values);
		}
	},
	/** The Y-coordinate of the grid's labels. */
	GRID_LABEL_POSITION_Y {
		@Override
		public String getMessage() {
			return "grid's labels position";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}

		@Override
		public List<Boolean> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Boolean>() : group.getGridXLabelSouthList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setXLabelSouth((Boolean)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGridXLabelSouthList((List<Boolean>)values);
		}
	},
	/** The size of the labels of grids. */
	GRID_SIZE_LABEL {
		@Override
		public String getMessage() {
			return "grid's labels size";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Integer;
		}

		@Override
		public List<Integer> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Integer>() : group.getGridLabelSizeList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setLabelsSize((Integer)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGridLabelSizeList((List<Integer>)values);
		}
	},
	/** The t bar num of arrows. */
	ARROW_T_BAR_SIZE_DIM {
		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			final double val = (Double)value;

			for(final IShape sh : group.getShapes())
				if(sh.isArrowable())
					sh.setTBarSizeDim(val);
		}

		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			IShape sh;

			for(int i=0, size=group.size(); i<size; i++) {
				sh = group.getShapeAt(i);
				if(sh.isArrowable())
					sh.setTBarSizeDim((Double)values.get(i));
			}
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			final List<Double> vals = new ArrayList<>();
			IArrow arr;

			for(final IShape sh : group.getShapes()) {
				arr = sh.isArrowable() ? sh.getArrowAt(0) : null;
				vals.add(arr==null ? null : arr.getTBarSizeDim());
			}

			return vals;
		}

		@Override
		public String getMessage() {
			return "arrow parameter";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** The t bar num of arrows. */
	ARROW_T_BAR_SIZE_NUM {
		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			final double val = (Double)value;

			for(final IShape sh : group.getShapes())
				if(sh.isArrowable())
					sh.setTBarSizeNum(val);
		}

		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			IShape sh;

			for(int i=0, size=group.size(); i<size; i++) {
				sh = group.getShapeAt(i);
				if(sh.isArrowable())
					sh.setTBarSizeNum((Double)values.get(i));
			}
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			final List<Double> vals = new ArrayList<>();
			IArrow arr;

			for(final IShape sh : group.getShapes()) {
				arr = sh.isArrowable() ? sh.getArrowAt(0) : null;
				vals.add(arr==null ? null : arr.getTBarSizeNum());
			}

			return vals;
		}

		@Override
		public String getMessage() {
			return "arrow parameter";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** The dot size dim of arrows. */
	ARROW_DOT_SIZE_NUM {
		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			final double val = (Double)value;

			for(final IShape sh : group.getShapes())
				if(sh.isArrowable())
					sh.setDotSizeNum(val);
		}

		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			IShape sh;

			for(int i=0, size=group.size(); i<size; i++) {
				sh = group.getShapeAt(i);
				if(sh.isArrowable())
					sh.setDotSizeNum((Double)values.get(i));
			}
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			final List<Double> vals = new ArrayList<>();
			IArrow arr;

			for(final IShape sh : group.getShapes()) {
				arr = sh.isArrowable() ? sh.getArrowAt(0) : null;
				vals.add(arr==null ? null : arr.getDotSizeNum());
			}

			return vals;
		}

		@Override
		public String getMessage() {
			return "arrow parameter";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** The dot size dim of arrows. */
	ARROW_DOT_SIZE_DIM {
		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			final double val = (Double)value;

			for(final IShape sh : group.getShapes())
				if(sh.isArrowable())
					sh.setDotSizeDim(val);
		}

		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			IShape sh;

			for(int i=0, size=group.size(); i<size; i++) {
				sh = group.getShapeAt(i);
				if(sh.isArrowable())
					sh.setDotSizeDim((Double)values.get(i));
			}
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			final List<Double> vals = new ArrayList<>();
			IArrow arr;

			for(final IShape sh : group.getShapes()) {
				arr = sh.isArrowable() ? sh.getArrowAt(0) : null;
				vals.add(arr==null ? null : arr.getDotSizeDim());
			}

			return vals;
		}

		@Override
		public String getMessage() {
			return "arrow parameter";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** The bracket num of arrows. */
	ARROW_BRACKET_NUM {
		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			final double val = (Double)value;

			for(final IShape sh : group.getShapes())
				if(sh.isArrowable())
					sh.setBracketNum(val);
		}

		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			IShape sh;

			for(int i=0, size=group.size(); i<size; i++) {
				sh = group.getShapeAt(i);
				if(sh.isArrowable())
					sh.setBracketNum((Double)values.get(i));
			}
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			final List<Double> vals = new ArrayList<>();
			IArrow arr;

			for(final IShape sh : group.getShapes()) {
				arr = sh.isArrowable() ? sh.getArrowAt(0) : null;
				vals.add(arr==null ? null : arr.getBracketNum());
			}

			return vals;
		}

		@Override
		public String getMessage() {
			return "arrow parameter";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** The round bracket num of arrows. */
	ARROW_R_BRACKET_NUM {
		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			final double val = (Double)value;

			for(final IShape sh : group.getShapes())
				if(sh.isArrowable())
					sh.setRBracketNum(val);
		}

		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			IShape sh;

			for(int i=0, size=group.size(); i<size; i++) {
				sh = group.getShapeAt(i);
				if(sh.isArrowable())
					sh.setRBracketNum((Double)values.get(i));
			}
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			final List<Double> vals = new ArrayList<>();
			IArrow arr;

			for(final IShape sh : group.getShapes()) {
				arr = sh.isArrowable() ? sh.getArrowAt(0) : null;
				vals.add(arr==null ? null : arr.getRBracketNum());
			}

			return vals;
		}

		@Override
		public String getMessage() {
			return "arrow parameter";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** The size num of arrows. */
	ARROW_SIZE_NUM {
		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			final double val = (Double)value;

			for(final IShape sh : group.getShapes())
				if(sh.isArrowable())
					sh.setArrowSizeNum(val);
		}

		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			IShape sh;

			for(int i=0, size=group.size(); i<size; i++) {
				sh = group.getShapeAt(i);
				if(sh.isArrowable())
					sh.setArrowSizeNum((Double)values.get(i));
			}
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			final List<Double> vals = new ArrayList<>();
			IArrow arr;

			for(final IShape sh : group.getShapes()) {
				arr = sh.isArrowable() ? sh.getArrowAt(0) : null;
				vals.add(arr==null ? null : arr.getArrowSizeNum());
			}

			return vals;
		}

		@Override
		public String getMessage() {
			return "arrow parameter";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** The size dim of arrows. */
	ARROW_SIZE_DIM {
		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			final double val = (Double)value;

			for(final IShape sh : group.getShapes())
				if(sh.isArrowable())
					sh.setArrowSizeDim(val);
		}

		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			IShape sh;

			for(int i=0, size=group.size(); i<size; i++) {
				sh = group.getShapeAt(i);
				if(sh.isArrowable())
					sh.setArrowSizeDim((Double)values.get(i));
			}
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			final List<Double> vals = new ArrayList<>();
			IArrow arr;

			for(final IShape sh : group.getShapes()) {
				arr = sh.isArrowable() ? sh.getArrowAt(0) : null;
				vals.add(arr==null ? null : arr.getArrowSizeDim());
			}

			return vals;
		}

		@Override
		public String getMessage() {
			return "arrow parameter";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	ARROW_LENGTH {
		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			final double val = (Double)value;

			for(final IShape sh : group.getShapes())
				if(sh.isArrowable())
					sh.setArrowLength(val);
		}

		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			IShape sh;

			for(int i=0, size=group.size(); i<size; i++) {
				sh = group.getShapeAt(i);
				if(sh.isArrowable())
					sh.setArrowLength((Double)values.get(i));
			}
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			final List<Double> vals = new ArrayList<>();
			IArrow arr;

			for(final IShape sh : group.getShapes()) {
				arr = sh.isArrowable() ? sh.getArrowAt(0) : null;
				vals.add(arr==null ? null : arr.getArrowLength());
			}

			return vals;
		}

		@Override
		public String getMessage() {
			return "arrow parameter";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** The inset of arrows. */
	ARROW_INSET {
		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			final double val = (Double)value;

			for(final IShape sh : group.getShapes())
				if(sh.isArrowable())
					sh.setArrowInset(val);
		}

		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			IShape sh;

			for(int i=0, size=group.size(); i<size; i++) {
				sh = group.getShapeAt(i);
				if(sh.isArrowable())
					sh.setArrowInset((Double)values.get(i));
			}
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			final List<Double> vals = new ArrayList<>();
			IArrow arr;

			for(final IShape sh : group.getShapes()) {
				arr = sh.isArrowable() ? sh.getArrowAt(0) : null;
				vals.add(arr==null ? null : arr.getArrowInset());
			}

			return vals;
		}

		@Override
		public String getMessage() {
			return "arrow parameter";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}
	},
	/** Modification of the starting position of grids. */
	GRID_END {
		@Override
		public String getMessage() {
			return "Grid's coordinates";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof IPoint;
		}

		@Override
		public List<IPoint> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<IPoint>() : group.getGridEndList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value)) {
				final IPoint pt = (IPoint)value;
				group.setGridEnd(pt.getX(), pt.getY());
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGridEndList((List<IPoint>)values);
		}
	},
	/** Modification of the starting position of grids. */
	GRID_ORIGIN {
		@Override
		public String getMessage() {
			return "Grid's coordinates";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof IPoint;
		}

		@Override
		public List<IPoint> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<IPoint>() : group.getGridOriginList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value)) {
				final IPoint pt = (IPoint)value;
				group.setOrigin(pt.getX(), pt.getY());
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGridOriginList((List<IPoint>)values);
		}
	},
	/** Modification of the starting position of grids. */
	GRID_START {
		@Override
		public String getMessage() {
			return "Grid's coordinates";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof IPoint;
		}

		@Override
		public List<IPoint> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<IPoint>() : group.getGridStartList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value)) {
				final IPoint pt = (IPoint)value;
				group.setGridStart(pt.getX(), pt.getY());
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGridStartList((List<IPoint>)values);
		}
	},
	/** Modification of the start angle of arcs. */
	ARC_START_ANGLE {
		@Override
		public String getMessage() {
			return "Arc angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getAngleStartList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setAngleStart((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setAngleStartList((List<Double>)values);
		}
	},
	/** Modification of the end angle of arcs. */
	ARC_END_ANGLE {
		@Override
		public String getMessage() {
			return "Arc angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getAngleEndList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setAngleEnd((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setAngleEndList((List<Double>)values);
		}
	},
	/** Modification of the style of arcs. */
	ARC_STYLE {
		@Override
		public String getMessage() {
			return "Arc style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof ArcStyle;
		}

		@Override
		public List<ArcStyle> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<ArcStyle>() :group.getArcStyleList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setArcStyle((ArcStyle)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setArcStyleList((List<ArcStyle>)values);
		}
	},
	/** Defines if the shape has a second arrow. */
	ARROW2_STYLE {
		@Override
		public String getMessage() {
			return "second arrow style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof ArrowStyle;
		}

		@Override
		public List<ArrowStyle> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<ArrowStyle>() :group.getArrowStyleList(-1);
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setArrowStyle((ArrowStyle)value, -1);
		}
		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setArrowStyleList((List<ArrowStyle>)values, -1);
		}
	},
	/** Defines if the shape has a first arrow. */
	ARROW1_STYLE {
		@Override
		public String getMessage() {
			return "first arrow style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof ArrowStyle;
		}

		@Override
		public List<ArrowStyle> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<ArrowStyle>() :group.getArrowStyleList(0);
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setArrowStyle((ArrowStyle)value, 0);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setArrowStyleList((List<ArrowStyle>)values, 0);
		}
	},
	/** Modification of the position of texts. */
	TEXT_POSITION {
		@Override
		public String getMessage() {
			return "text position";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof TextPosition;
		}

		@Override
		public List<TextPosition> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<TextPosition>() : group.getTextPositionList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setTextPosition((TextPosition)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setTextPositionList((List<TextPosition>)values);
		}
	},
	/** Modification of the text. */
	TEXT {
		@Override
		public String getMessage() {
			return "text";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof String;
		}

		@Override
		public List<String> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<String>() : group.getTextList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setText((String)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setTextList((List<String>)values);
		}
	},
	/** Modification of the hatchings angle of shapes. */
	HATCHINGS_ANGLE {
		@Override
		public String getMessage() {
			return "hatchings angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getHatchingsAngleList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setHatchingsAngle((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setHatchingsAngleList((List<Double>)values);
		}
	},
	/** Modification of the hatchings width a shape. */
	HATCHINGS_WIDTH {
		@Override
		public String getMessage() {
			return "hatchings width";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getHatchingsWidthList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setHatchingsWidth((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setHatchingsWidthList((List<Double>)values);
		}
	},
	/** Modification of the hatching spacing a shape. */
	HATCHINGS_SEP {
		@Override
		public String getMessage() {
			return "hatchings spacing";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getHatchingsSepList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setHatchingsSep((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setHatchingsSepList((List<Double>)values);
		}
	},
	/** Modification of the gradient angle a shape. */
	GRAD_ANGLE {
		@Override
		public String getMessage() {
			return "gradient angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getGradAngleList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setGradAngle((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGradAngleList((List<Double>)values);
		}
	},
	/** Modification of the middle point of the gradient a shape. */
	GRAD_MID_POINT {
			@Override
			public String getMessage() {
				return "gradient middle point";
			}

			@Override
			public boolean isValueValid(final Object obj) {
				return obj instanceof Double;
			}

			@Override
			public List<Double> getPropertyValues(final IGroup group) {
				return group==null ? new ArrayList<Double>() : group.getGradMidPtList();
			}

			@Override
			public void setPropertyValue(final IGroup group, final Object value) {
				if(group!=null && isValueValid(value))
					group.setGradMidPt((Double)value);
			}

			@SuppressWarnings("unchecked")
			@Override
			public void setPropertyValueList(final IGroup group, final List<?> values) {
				if(group!=null)
					group.setGradMidPtList((List<Double>)values);
			}
	},
	/** Modification of the round corner value of a shape. */
	ROUND_CORNER_VALUE {
		@Override
		public String getMessage() {
			return "corner roundness";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getLineArcList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setLineArc((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setLineArcList((List<Double>)values);
		}
	},
	/** Modification of the colour of the labels of a grid. */
	GRID_SUBGRID_COLOUR {
		@Override
		public String getMessage() {
			return "sub-grid' colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}

		@Override
		public List<Color> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Color>() : group.getSubGridColourList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setSubGridColour((Color)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setSubGridColourList((List<Color>)values);
		}
	},
	/** Modification of the colour of the labels of a grid. */
	GRID_LABELS_COLOUR {
		@Override
		public String getMessage() {
			return "labels' colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}

		@Override
		public List<Color> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Color>() : group.getGridLabelsColourList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setGridLabelsColour((Color)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGridLabelsColourList((List<Color>)values);
		}
	},
	/** Modification of the colour of the filling of a shape. */
	COLOUR_FILLING {
		@Override
		public String getMessage() {
			return "interior colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}

		@Override
		public List<Color> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Color>() : group.getFillingColList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setFillingCol((Color)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setFillingColList((List<Color>)values);
		}
	},
	/** Modification of the colour of the borders of a shape. */
	COLOUR_LINE {
		@Override
		public String getMessage() {
			return "lines colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}

		@Override
		public List<Color> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Color>() : group.getLineColourList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setLineColour((Color)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setLineColourList((List<Color>)values);
		}
	},
	/** Modification of the colour of the hatchings of a shape. */
	COLOUR_HATCHINGS {
		@Override
		public String getMessage() {
			return "hatchings colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}

		@Override
		public List<Color> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Color>() : group.getHatchingsColList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setHatchingsCol((Color)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setHatchingsColList((List<Color>)values);
		}
	},
	/** Defines if a shape must have double borders. */
	DBLE_BORDERS {
		@Override
		public String getMessage() {
			return "double border";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}

		@Override
		public List<Boolean> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Boolean>() : group.hasDbleBordList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setHasDbleBord((Boolean)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setHasDbleBordList((List<Boolean>)values);
		}
	},
	/** Modification of the size of the double borders of a shape. */
	DBLE_BORDERS_SIZE {
		@Override
		public String getMessage() {
			return "double border size";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getDbleBordSepList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setDbleBordSep((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setDbleBordSepList((List<Double>)values);
		}
	},
	/** Modification of the colour of the double borders of a shape. */
	COLOUR_DBLE_BORD {
		@Override
		public String getMessage() {
			return "double border colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}

		@Override
		public List<Color> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Color>() : group.getDbleBordColList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setDbleBordCol((Color)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setDbleBordColList((List<Color>)values);
		}
	},
	/** Defines if a shape must have a shadow. */
	SHADOW {
		@Override
		public String getMessage() {
			return "shadow";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Boolean;
		}

		@Override
		public List<Boolean> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Boolean>() : group.hasShadowList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setHasShadow((Boolean)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setHasShadowList((List<Boolean>)values);
		}
	},
	/** Modification of the size of the shadow of a shape. */
	SHADOW_SIZE {
		@Override
		public String getMessage() {
			return "shadow size";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getShadowSizeList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setShadowSize((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setShadowSizeList((List<Double>)values);
		}
	},
	/** Modification of the angle of the shadow of a shape. */
	SHADOW_ANGLE {
		@Override
		public String getMessage() {
			return "shadow angle";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Double;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getShadowAngleList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setShadowAngle((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setShadowAngleList((List<Double>)values);
		}
	},
	/** Modification of colour of the shadow of a shape. */
	COLOUR_SHADOW {
		@Override
		public String getMessage() {
			return "shadow colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}

		@Override
		public List<Color> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Color>() : group.getShadowColList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setShadowCol((Color)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setShadowColList((List<Color>)values);
		}
	},
	/** Modification of the colour of the start gradient of a shape. */
	COLOUR_GRADIENT_START {
		@Override
		public String getMessage() {
			return "gradient start colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}

		@Override
		public List<Color> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Color>() : group.getGradColStartList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setGradColStart((Color)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGradColStartList((List<Color>)values);
		}
	},
	/** Modification of the colour of the end gradient of a shape. */
	COLOUR_GRADIENT_END {
		@Override
		public String getMessage() {
			return "gradient end colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}

		@Override
		public List<Color> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Color>() : group.getGradColEndList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setGradColEnd((Color)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setGradColEndList((List<Color>)values);
		}
	},
	/** Modification of the thickness of the borders of a shape. */
	LINE_THICKNESS {
		@Override
		public String getMessage() {
			return "thickness";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Integer || obj instanceof Double || obj instanceof Float;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getThicknessList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setThickness((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setThicknessList((List<Double>)values);
		}
	},
	/** Modification of the filling style of a shape. */
	FILLING_STYLE {
		@Override
		public String getMessage() {
			return "filling style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof FillingStyle;
		}

		@Override
		public List<FillingStyle> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<FillingStyle>() : group.getFillingStyleList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setFillingStyle((FillingStyle)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setFillingStyleList((List<FillingStyle>)values);
		}
	},
	/** Modification of the border position of a shape. */
	BORDER_POS {
		@Override
		public String getMessage() {
			return "border position";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof BorderPos;
		}

		@Override
		public List<BorderPos> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<BorderPos>() : group.getBordersPositionList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setBordersPosition((BorderPos)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setBordersPositionList((List<BorderPos>)values);
		}
	},
	/** Modification of the line style of a shape. */
	LINE_STYLE {
		@Override
		public String getMessage() {
			return "line style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof LineStyle;
		}

		@Override
		public List<LineStyle> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<LineStyle>() : group.getLineStyleList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setLineStyle((LineStyle)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setLineStyleList((List<LineStyle>)values);
		}
	},
	/** Modification of the filling colour of a dot. */
	DOT_FILLING_COL {
		@Override
		public String getMessage() {
			return "dot filling colour";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Color;
		}

		@Override
		public List<Color> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Color>() : group.getDotFillingColList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setDotFillingCol((Color)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setDotFillingColList((List<Color>)values);
		}
	},
	/** Modification of the style of a dot. */
	DOT_STYLE {
		@Override
		public String getMessage() {
			return "dot style";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof DotStyle;
		}

		@Override
		public List<DotStyle> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<DotStyle>() : group.getDotStyleList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setDotStyle((DotStyle)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setDotStyleList((List<DotStyle>)values);
		}
	},
	/** Modification of the size of dots. */
	DOT_SIZE {
		@Override
		public String getMessage() {
			return "dot size";
		}

		@Override
		public boolean isValueValid(final Object obj) {
			return obj instanceof Integer || obj instanceof Double || obj instanceof Float;
		}

		@Override
		public List<Double> getPropertyValues(final IGroup group) {
			return group==null ? new ArrayList<Double>() : group.getDotSizeList();
		}

		@Override
		public void setPropertyValue(final IGroup group, final Object value) {
			if(group!=null && isValueValid(value))
				group.setRadius((Double)value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setPropertyValueList(final IGroup group, final List<?> values) {
			if(group!=null)
				group.setDotSizeList((List<Double>)values);
		}
	};

	/**
	 * @param group The group to test.
	 * @return True if the given group supports the calling property.
	 * @since 3.0
	 */
	public boolean isPropertySupported(final IGroup group) {
		return group!=null;
	}

	/**
	 * Sets the given value of the property to the given group.
	 * @param group The group to modify.
	 * @param value The new value of the property to set.
	 * @since 3.0
	 */
	public abstract void setPropertyValue(final IGroup group, final Object value);

	/**
	 * Sets the given values of the property to the given group. The size of the list
	 * must equals the number of shapes of the group. If a shape of the group must not be set,
	 * its corresponding value in the list must be null.
	 * @param group The group to modify.
	 * @param values The set of new values of the property to set.
	 * @since 3.0
	 */
	public abstract void setPropertyValueList(final IGroup group, final List<?> values);

	/**
	 * @param group The group to explore.
	 * @return The list of property values of the shapes of the given group.
	 * @since 3.0
	 */
	public abstract List<?> getPropertyValues(final IGroup group);


	/**
	 * @return The title of the properties.
	 * @since 3.0
	 */
	public abstract String getMessage();


	/**
	 * @param obj The new value to test.
	 * @return True if the given value can be set to the shape property.
	 * @since 3.0
	 */
	public abstract boolean isValueValid(final Object obj);
}
