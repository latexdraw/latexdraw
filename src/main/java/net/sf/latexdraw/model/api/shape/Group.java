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
package net.sf.latexdraw.model.api.shape;

import java.util.List;
import java.util.Optional;
import net.sf.latexdraw.model.api.property.ArcProp;
import net.sf.latexdraw.model.api.property.AxesProp;
import net.sf.latexdraw.model.api.property.FreeHandProp;
import net.sf.latexdraw.model.api.property.GridProp;
import net.sf.latexdraw.model.api.property.LineArcProp;
import net.sf.latexdraw.model.api.property.PlotProp;
import net.sf.latexdraw.model.api.property.SetShapesProp;
import net.sf.latexdraw.model.api.property.TextProp;

/**
 * The API for groups of shapes.
 * @author Arnaud BLOUIN
 */
public interface Group extends ArrowableShape, SetShapesProp, LineArcProp, TextProp, ArcProp, AxesProp, GridProp, FreeHandProp, PlotProp {
	/**
	 * Duplicates the group of shapes.
	 * @param duplicateShapes True: the shapes will be duplicated as well.
	 * @return The duplicated group of shapes.
	 */
	Group duplicateDeep(final boolean duplicateShapes);

	@Override
	Group duplicate();

	/**
	 * @return The list of polar/cartesian coordinates of the plots contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Boolean>> getPlotPolarList();

	/**
	 * Sets if polar coordinates for the plots of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setPlotPolarList(final List<Optional<Boolean>> values);

	/**
	 * @return The list of max X of the plots contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getPlotMaxXList();

	/**
	 * Sets the max X of the plots of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setPlotMaxXList(final List<Optional<Double>> values);

	/**
	 * @return The list of min X of the plots contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getPlotMinXList();

	/**
	 * Sets the min X of the plots of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setPlotMinXList(final List<Optional<Double>> values);

	/**
	 * @return The list of number of plotted points the shapes contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Integer>> getNbPlottedPointsList();

	/**
	 * Sets the number of plotted points of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setNbPlottedPointsList(final List<Optional<Integer>> values);

	/**
	 * @return The list of the plot style of the shapes contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<PlotStyle>> getPlotStyleList();

	/**
	 * Sets the plot style of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setPlotStyleList(final List<Optional<PlotStyle>> values);

	/**
	 * @return The list of Y-scale of the shapes contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getYScaleList();

	/**
	 * Sets the Y-scale shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setYScaleList(final List<Optional<Double>> values);

	/**
	 * @return The list of X scale of the shapes contained by the group.
	 * If a shape of the group does not support this property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getXScaleList();

	/**
	 * Sets the X-scale shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setXScaleList(final List<Optional<Double>> values);

	/**
	 * @return The list of plot equations of the shapes contained by the group.
	 * If a shape of the group does not support the border position property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<String>> getPlotEquationList();

	/**
	 * Sets the equation of plot shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setPlotEquationList(final List<Optional<String>> values);

	/**
	 * @return The list of the border positions of the shapes contained by the group.
	 * If a shape of the group does not support the border position property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<BorderPos>> getBordersPositionList();

	/**
	 * Sets the border position of the shapes of the group.
	 * @param list The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setBordersPositionList(final List<Optional<BorderPos>> list);

	/**
	 * @return The list of line colours of the shapes contained by the group.
	 * If a shape of the group does not support the line colour property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Color>> getLineColourList();

	/**
	 * Sets the line colour of the shapes of the group.
	 * @param list The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setLineColourList(final List<Optional<Color>> list);

	/**
	 * @return The list of start angle of the arc shapes contained by the group.
	 * If a shape of the group does not support the start angle property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getAngleStartList();

	/**
	 * Sets the starting angle of the arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setAngleStartList(final List<Optional<Double>> values);

	/**
	 * @return The list of end angle of the arc shapes contained by the group.
	 * If a shape of the group does not support the end angle property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getAngleEndList();

	/**
	 * Sets the ending angle of the arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setAngleEndList(final List<Optional<Double>> values);

	/**
	 * @return The list of arc styles of the arc shapes contained by the group.
	 * If a shape of the group does not support the arc style property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<ArcStyle>> getArcStyleList();

	/**
	 * Sets the arc style of the arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setArcStyleList(final List<Optional<ArcStyle>> values);

	/**
	 * @param i The index of the arrows to get.
	 * @return The list of arrow style of the shapes contained by the group.
	 * If a shape of the group does not support the arrow style property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<ArrowStyle>> getArrowStyleList(final int i);

	/**
	 * @return The list of the text positions the shapes contained by the group.
	 * If a shape of the group does not support the text position property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<TextPosition>> getTextPositionList();

	/**
	 * Sets the text position of the text shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setTextPositionList(final List<Optional<TextPosition>> values);

	/**
	 * @return The list of the text contents of the shapes contained by the group.
	 * If a shape of the group does not support the text property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<String>> getTextList();

	/**
	 * Sets the text content of the text shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setTextList(final List<Optional<String>> values);

	/**
	 * @return The list of the hatchings angle of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings angle property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getHatchingsAngleList();

	/**
	 * Sets the hatchings angle of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setHatchingsAngleList(final List<Optional<Double>> values);

	/**
	 * @return The list of the hatchings width of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings width property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getHatchingsWidthList();

	/**
	 * Sets the hatchings width of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setHatchingsWidthList(final List<Optional<Double>> values);

	/**
	 * @return The list of the hatchings size of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings size property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getHatchingsSepList();

	/**
	 * Sets the hatchings gap of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setHatchingsSepList(final List<Optional<Double>> values);

	/**
	 * @return The list of the gradient angle of the shapes contained by the group.
	 * If a shape of the group does not support the gradient angle property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getGradAngleList();

	/**
	 * Sets the starting angle of the gradient of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGradAngleList(final List<Optional<Double>> values);

	/**
	 * @return The list of the gradient middle point of the shapes contained by the group.
	 * If a shape of the group does not support the gradient middle point property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getGradMidPtList();

	/**
	 * Sets the middle point reference of the gradient of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGradMidPtList(final List<Optional<Double>> values);

	/**
	 * @return The list of the line arc values of the shapes contained by the group.
	 * If a shape of the group does not support the line arc property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getLineArcList();

	/**
	 * Sets the line arc value of the line-arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setLineArcList(final List<Optional<Double>> values);

	/**
	 * @return The list of filling colours of the shapes contained by the group.
	 * If a shape of the group does not support the filling colour property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Color>> getFillingColList();

	/**
	 * Sets the filling colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setFillingColList(final List<Optional<Color>> values);

	/**
	 * @return The list of hatchings colours of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings colour property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Color>> getHatchingsColList();

	/**
	 * Sets the hatchings colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setHatchingsColList(final List<Optional<Color>> values);

	/**
	 * @return The list of boolean defining if the shapes contained by the group have double borders.
	 * If a shape of the group does not support the double border property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Boolean>> hasDbleBordList();

	/**
	 * @return The list of double border width of the shapes contained by the group.
	 * If a shape of the group does not support the double border width property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getDbleBordSepList();

	/**
	 * Sets the double border gap of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setDbleBordSepList(final List<Optional<Double>> values);

	/**
	 * @return The list of double border colours of the shapes contained by the group.
	 * If a shape of the group does not support the double border colour property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Color>> getDbleBordColList();

	/**
	 * Sets the double borders colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setDbleBordColList(final List<Optional<Color>> values);

	/**
	 * @return The list of boolean defining if the shapes contained by the group have shadow.
	 * If a shape of the group does not support the shadow property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Boolean>> hasShadowList();

	/**
	 * @return The list of shadow size of the shapes contained by the group.
	 * If a shape of the group does not support the shadow size property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getShadowSizeList();

	/**
	 * Sets the shadow sizes of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setShadowSizeList(final List<Optional<Double>> values);

	/**
	 * @return The list of shadow angle of the shapes contained by the group.
	 * If a shape of the group does not support the shadow angle property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getShadowAngleList();

	/**
	 * Sets the shadow angles of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setShadowAngleList(final List<Optional<Double>> values);

	/**
	 * @return The list of shadow colours of the shapes contained by the group.
	 * If a shape of the group does not support the shadow colour property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Color>> getShadowColList();

	/**
	 * Sets the shadow colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setShadowColList(final List<Optional<Color>> values);

	/**
	 * @return The list of ending gradient colours of the shapes contained by the group.
	 * If a shape of the group does not support the ending gradient colour property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Color>> getGradColStartList();

	/**
	 * Sets the first gradient colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGradColStartList(final List<Optional<Color>> values);

	/**
	 * @return The list of starting gradient colours of the shapes contained by the group.
	 * If a shape of the group does not support the starting gradient colour property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Color>> getGradColEndList();

	/**
	 * Sets the last gradient colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGradColEndList(final List<Optional<Color>> values);

	/**
	 * @return The list of the thicknesses of the shapes contained by the group.
	 * If a shape of the group does not support the thickness property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getThicknessList();

	/**
	 * Sets the thickness of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setThicknessList(final List<Optional<Double>> values);

	/**
	 * @return The list of the filling styles of the shapes contained by the group.
	 * If a shape of the group does not support the filling style property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<FillingStyle>> getFillingStyleList();

	/**
	 * Sets the style of the filling of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setFillingStyleList(final List<Optional<FillingStyle>> values);

	/**
	 * @return The list of the line styles of the shapes contained by the group.
	 * If a shape of the group does not support the line style property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<LineStyle>> getLineStyleList();

	/**
	 * Sets the line style colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setLineStyleList(final List<Optional<LineStyle>> values);

	/**
	 * @return The list of filling colours of the dot shapes contained by the group.
	 * If a shape of the group does not support the dot filling colour property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Color>> getDotFillingColList();

	/**
	 * Sets the filling colour of the dot shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setDotFillingColList(final List<Optional<Color>> values);

	/**
	 * @return The list of the dot styles of the shapes contained by the group.
	 * If a shape of the group does not support the dot style property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<DotStyle>> getDotStyleList();

	/**
	 * Sets the dot style of the dottable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setDotStyleList(final List<Optional<DotStyle>> values);

	/**
	 * @return The list of the dot sizes of the shapes contained by the group.
	 * If a shape of the group does not support the dot size property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getDotSizeList();

	/**
	 * Sets the size of the dot shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setDotSizeList(final List<Optional<Double>> values);

	/**
	 * Sets the arrow style of the arrowable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @param i The index of the arrow to set.
	 */
	void setArrowStyleList(final List<Optional<ArrowStyle>> values, final int i);

	/**
	 * Defines if the shapes of the group have double borders.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setHasDbleBordList(final List<Optional<Boolean>> values);

	/**
	 * Defines if the shapes of the group have a shadow.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setHasShadowList(final List<Optional<Boolean>> values);

	/**
	 * @return The list of the starting points of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Point>> getGridStartList();

	/**
	 * Sets the starting points of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGridStartList(final List<Optional<Point>> values);

	/**
	 * @return The list of the ending points of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Point>> getGridEndList();

	/**
	 * Sets the ending points of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGridEndList(final List<Optional<Point>> values);

	/**
	 * @return The list of the origin points of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Point>> getGridOriginList();

	/**
	 * Sets the origin points of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGridOriginList(final List<Optional<Point>> values);

	/**
	 * @return The list of the sizes of the labels of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Integer>> getGridLabelSizeList();

	/**
	 * Sets the size of the labels of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGridLabelSizeList(final List<Optional<Integer>> values);

	/**
	 * @return The list of the Y-coordinate labels of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Boolean>> getGridXLabelSouthList();

	/**
	 * Sets the Y-coordinate of the labels of the grid contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGridXLabelSouthList(final List<Optional<Boolean>> values);

	/**
	 * @return The list of the Y-coordinate labels of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 */
	List<Optional<Boolean>> getGridYLabelWestList();

	/**
	 * Sets the X-coordinate of the labels of the grid contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGridYLabelWestList(final List<Optional<Boolean>> values);

	/**
	 * @return The list of the styles of the axes contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<AxesStyle>> getAxesStyleList();

	/**
	 * Sets the style of the axes contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setAxesStyleList(final List<Optional<AxesStyle>> values);

	/**
	 * @return The list of the styles of the axes' ticks contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<TicksStyle>> getAxesTicksStyleList();

	/**
	 * Sets the style of the axes' ticks contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setAxesTicksStyleList(final List<Optional<TicksStyle>> values);

	/**
	 * @return The list of the sizes of the axes' ticks contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getAxesTicksSizeList();

	/**
	 * Sets the size of the axes' ticks contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setAxesTicksSizeList(final List<Optional<Double>> values);

	/**
	 * @return The list of the plotting styles of the axes' ticks contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<PlottingStyle>> getAxesTicksDisplayedList();

	/**
	 * Sets how the ticks of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setAxesTicksDisplayedList(final List<Optional<PlottingStyle>> values);

	/**
	 * @return The list of the labels' increments of the axes' ticks contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Point>> getAxesIncrementsList();

	/**
	 * Sets the labels' increments of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setAxesIncrementsList(final List<Optional<Point>> values);

	/**
	 * @return The list of the plotting styles of the axes' labels contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<PlottingStyle>> getAxesLabelsDisplayedList();

	/**
	 * Sets how the labels of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setAxesLabelsDisplayedList(final List<Optional<PlottingStyle>> values);

	/**
	 * @return The list of booleans defining if the origin of the axes contained in the group must be shown.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Boolean>> getAxesShowOriginList();

	/**
	 * Defines if the origin of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setAxesShowOriginList(final List<Optional<Boolean>> values);

	/**
	 * @return The list of the distances between the labels of the axes contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Point>> getAxesDistLabelsList();

	/**
	 * Sets the distances between the labels of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setAxesDistLabelsList(final List<Optional<Point>> values);

	/**
	 * @return The list of labels' colours of the grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Color>> getGridLabelsColourList();

	/**
	 * Sets the labels' colours of the grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGridLabelsColourList(final List<Optional<Color>> values);

	/**
	 * @return The list of labels' colours of the grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Color>> getSubGridColourList();

	/**
	 * Sets the labels' colours of the grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setSubGridColourList(final List<Optional<Color>> values);

	/**
	 * @return The width of the grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getGridWidthList();

	/**
	 * Sets the width of the grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGridWidthList(final List<Optional<Double>> values);

	/**
	 * @return The width of the sub-grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Double>> getSubGridWidthList();

	/**
	 * Sets the width of the sub-grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setSubGridWidthList(final List<Optional<Double>> values);

	/**
	 * @return The number of dots composing the main lines of each grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Integer>> getGridDotsList();

	/**
	 * Sets the number of dots composing the main lines of each grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setGridDotsList(final List<Optional<Integer>> values);

	/**
	 * @return The number of dots composing the sub-lines of each grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Integer>> getSubGridDotsList();

	/**
	 * Sets the number of dots composing the sub-lines of each grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setSubGridDotsList(final List<Optional<Integer>> values);

	/**
	 * @return The division of the sub-lines of each grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Integer>> getSubGridDivList();

	/**
	 * Sets the division of the sub-lines of each grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setSubGridDivList(final List<Optional<Integer>> values);

	/**
	 * @return The types of the freehand shapes contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<FreeHandStyle>> getFreeHandTypeList();

	/**
	 * Sets the type of the freehand shapes contained in the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setFreeHandTypeList(final List<Optional<FreeHandStyle>> values);

	/**
	 * @return The intervals of the freehand shapes contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Integer>> getFreeHandIntervalList();

	/**
	 * Sets the interval of the freehand shapes contained in the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setFreeHandIntervalList(final List<Optional<Integer>> values);

	/**
	 * @return The boolean value defining whether the shapes contained in the group are opened.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Boolean>> getOpenList();

	/**
	 * Defines whether the shapes contained in the group are opened.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setOpenList(final List<Optional<Boolean>> values);

	/**
	 * @return The boolean value defining if the shapes contained in the group must show their points.
	 * If a shape of the group cannot show their points, null is added.
	 * to the list. The list cannot be null.
	 */
	List<Optional<Boolean>> getShowPointsList();

	/**
	 * Defines if the shapes contained in the group must show their points.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 */
	void setShowPointsList(final List<Optional<Boolean>> values);

	/**
	 * @return The tbarsizedim values of the shapes of the group.
	 */
	List<Optional<Double>> getTBarSizeDimList();

	/**
	 * Sets the tbarsizedim parameters to the shapes of the group.
	 * @param values The values to use.
	 */
	void setTBarSizeDimList(List<Optional<Double>> values);

	/**
	 * @return The tbarsizenum values of the shapes of the group.
	 */
	List<Optional<Double>> getTBarSizeNumList();

	/**
	 * Sets the tbarsizenum parameters to the shapes of the group.
	 * @param values The values to use.
	 */
	void setTBarSizeNumList(List<Optional<Double>> values);

	/**
	 * @return The dotsizenum values of the shapes of the group.
	 */
	List<Optional<Double>> getDotSizeNumList();

	/**
	 * Sets the dotsizenum parameters to the shapes of the group.
	 * @param values The values to use.
	 */
	void setDotSizeNumList(List<Optional<Double>> values);

	/**
	 * @return The dotsizedim values of the shapes of the group.
	 */
	List<Optional<Double>> getDotSizeDimList();

	/**
	 * Sets the dotsizedim parameters to the shapes of the group.
	 * @param values The values to use.
	 */
	void setDotSizeDimList(List<Optional<Double>> values);

	/**
	 * @return The bracketNum values of the shapes of the group.
	 */
	List<Optional<Double>> getBracketNumList();

	/**
	 * Sets the bracketNum parameters to the shapes of the group.
	 * @param values The values to use.
	 */
	void setBracketNumList(List<Optional<Double>> values);

	/**
	 * @return The rbracketNum values of the shapes of the group.
	 */
	List<Optional<Double>> getRBracketNumList();

	/**
	 * Sets the rbracketNum parameters to the shapes of the group.
	 * @param values The values to use.
	 */
	void setRBracketNumList(List<Optional<Double>> values);

	/**
	 * @return The arrowsizenum values of the shapes of the group.
	 */
	List<Optional<Double>> getArrowSizeNumList();

	/**
	 * Sets the arrowsizenum parameters to the shapes of the group.
	 * @param values The values to use.
	 */
	void setArrowSizeNumList(List<Optional<Double>> values);

	/**
	 * @return The arrowsizedim values of the shapes of the group.
	 */
	List<Optional<Double>> getArrowSizeDimList();

	/**
	 * Sets the arrowsizedim parameters to the shapes of the group.
	 * @param values The values to use.
	 */
	void setArrowSizeDimList(List<Optional<Double>> values);

	/**
	 * @return The arrowLength values of the shapes of the group.
	 */
	List<Optional<Double>> getArrowLengthList();

	/**
	 * Sets the arrowLength parameters to the shapes of the group.
	 * @param values The values to use.
	 */
	void setArrowLengthList(List<Optional<Double>> values);

	/**
	 * @return The arrowInset values of the shapes of the group.
	 */
	List<Optional<Double>> getArrowInsetList();

	/**
	 * Sets the arrowInset parameters to the shapes of the group.
	 * @param values The values to use.
	 */
	void setArrowInsetList(List<Optional<Double>> values);
}
