package net.sf.latexdraw.glib.models.interfaces;

import java.awt.Color;
import java.util.List;

import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;

/**
 * Defines an interface that classes defining a group of shapes should implement.<br>
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
 * 07/03/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
public interface IGroup extends ISetShapes, ILineArcShape, IText, Dottable, IArc, IAxes, IGrid, IFreehand {
	/**
	 * Duplicates the group of shapes.
	 * @param duplicateShapes True: the shapes will be duplicated as well.
	 * @return The duplicated group of shapes.
	 * @since 3.0
	 */
	IGroup duplicateDeep(final boolean duplicateShapes);


	/**
	 * @return The list of the border positions of the shapes contained by the group.
	 * If a shape of the group does not support the border position property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<BorderPos> getBordersPositionList();

	/**
	 * @return The list of line colours of the shapes contained by the group.
	 * If a shape of the group does not support the line colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getLineColourList();

	/**
	 * @return The list of start angle of the arc shapes contained by the group.
	 * If a shape of the group does not support the start angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getAngleStartList();

	/**
	 * @return The list of end angle of the arc shapes contained by the group.
	 * If a shape of the group does not support the end angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getAngleEndList();

	/**
	 * @return The list of arc styles of the arc shapes contained by the group.
	 * If a shape of the group does not support the arc style property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<ArcStyle> getArcStyleList();

	/**
	 * @return The list of arrow style of the shapes contained by the group.
	 * If a shape of the group does not support the arrow style property, null is added
	 * to the list. The list cannot be null.
	 * @param i The index of the arrows to get.
	 * @since 3.0
	 */
	List<ArrowStyle> getArrowStyleList(int i);

	/**
	 * @return The list of the rotation angles of the shapes contained by the group.
	 * If a shape of the group does not support the rotation angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getRotationAngleList();

	/**
	 * @return The list of the text positions the shapes contained by the group.
	 * If a shape of the group does not support the text position property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<TextPosition> getTextPositionList();

	/**
	 * @return The list of the text contents of the shapes contained by the group.
	 * If a shape of the group does not support the text property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<String> getTextList();

	/**
	 * @return The list of the hatchings angle of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getHatchingsAngleList();

	/**
	 * @return The list of the hatchings width of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings width property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getHatchingsWidthList();

	/**
	 * @return The list of the hatchings size of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings size property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getHatchingsSepList();

	/**
	 * @return The list of the gradient angle of the shapes contained by the group.
	 * If a shape of the group does not support the gradient angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getGradAngleList();

	/**
	 * @return The list of the gradient middle point of the shapes contained by the group.
	 * If a shape of the group does not support the gradient middle point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getGradMidPtList();

	/**
	 * @return The list of the line arc values of the shapes contained by the group.
	 * If a shape of the group does not support the line arc property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getLineArcList();

	/**
	 * @return The list of filling colours of the shapes contained by the group.
	 * If a shape of the group does not support the filling colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getFillingColList();

	/**
	 * @return The list of hatchings colours of the shapes contained by the group.
	 * If a shape of the group does not support the hatchings colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getHatchingsColList();

	/**
	 * @return The list of boolean defining if the shapes contained by the group have double borders.
	 * If a shape of the group does not support the double border property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> hasDbleBordList();

	/**
	 * @return The list of double border width of the shapes contained by the group.
	 * If a shape of the group does not support the double border width property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getDbleBordSepList();

	/**
	 * @return The list of double border colours of the shapes contained by the group.
	 * If a shape of the group does not support the double border colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getDbleBordColList();

	/**
	 * @return The list of boolean defining if the shapes contained by the group have shadow.
	 * If a shape of the group does not support the shadow property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> hasShadowList();

	/**
	 * @return The list of shadow size of the shapes contained by the group.
	 * If a shape of the group does not support the shadow size property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getShadowSizeList();

	/**
	 * @return The list of shadow angle of the shapes contained by the group.
	 * If a shape of the group does not support the shadow angle property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getShadowAngleList();

	/**
	 * @return The list of shadow colours of the shapes contained by the group.
	 * If a shape of the group does not support the shadow colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getShadowColList();

	/**
	 * @return The list of ending gradient colours of the shapes contained by the group.
	 * If a shape of the group does not support the ending gradient colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getGradColStartList();

	/**
	 * @return The list of starting gradient colours of the shapes contained by the group.
	 * If a shape of the group does not support the starting gradient colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getGradColEndList();

	/**
	 * @return The list of the thicknesses of the shapes contained by the group.
	 * If a shape of the group does not support the thickness property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getThicknessList();

	/**
	 * @return The list of the filling styles of the shapes contained by the group.
	 * If a shape of the group does not support the filling style property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<FillingStyle> getFillingStyleList();

	/**
	 * @return The list of the line styles of the shapes contained by the group.
	 * If a shape of the group does not support the line style property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<LineStyle> getLineStyleList();

	/**
	 * @return The list of filling colours of the dot shapes contained by the group.
	 * If a shape of the group does not support the dot filling colour property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getDotFillingColList();

	/**
	 * @return The list of the dot styles of the shapes contained by the group.
	 * If a shape of the group does not support the dot style property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<DotStyle> getDotStyleList();

	/**
	 * @return The list of the dot sizes of the shapes contained by the group.
	 * If a shape of the group does not support the dot size property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getDotSizeList();

	/**
	 * Sets the starting angle of the arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAngleStartList(final List<Double> values);

	/**
	 * Sets the border position of the shapes of the group.
	 * @param list The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setBordersPositionList(final List<BorderPos> list);

	/**
	 * Sets the line colour of the shapes of the group.
	 * @param list The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setLineColourList(final List<Color> list);

	/**
	 * Sets the dot style of the dottable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setDotStyleList(final List<DotStyle> values);

	/**
	 * Sets the ending angle of the arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAngleEndList(final List<Double> values);

	/**
	 * Sets the arc style of the arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setArcStyleList(final List<ArcStyle> values);

	/**
	 * Sets the arrow style of the arrowable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @param i The index of the arrow to set.
	 * @since 3.0
	 */
	void setArrowStyleList(final List<ArrowStyle> values, final int i);

	/**
	 * Sets the rotation angle of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setRotationAngleList(final List<Double> values);

	/**
	 * Sets the text position of the text shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setTextPositionList(final List<TextPosition> values);

	/**
	 * Sets the text content of the text shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setTextList(final List<String> values);

	/**
	 * Sets the hatchings angle of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHatchingsAngleList(final List<Double> values);

	/**
	 * Sets the hatchings width of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHatchingsWidthList(final List<Double> values);

	/**
	 * Sets the hatchings gap of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHatchingsSepList(final List<Double> values);

	/**
	 * Sets the starting angle of the gradient of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGradAngleList(final List<Double> values);

	/**
	 * Sets the middle point reference of the gradient of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGradMidPtList(final List<Double> values);

	/**
	 * Sets the line arc value of the line-arcable shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setLineArcList(final List<Double> values);

	/**
	 * Sets the filling colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setFillingColList(final List<Color> values);

	/**
	 * Sets the hatchings colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHatchingsColList(final List<Color> values);

	/**
	 * Defines if the shapes of the group have double borders.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHasDbleBordList(final List<Boolean> values);

	/**
	 * Sets the double border gap of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setDbleBordSepList(final List<Double> values);

	/**
	 * Sets the double borders colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setDbleBordColList(final List<Color> values);

	/**
	 * Defines if the shapes of the group have a shadow.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setHasShadowList(final List<Boolean> values);

	/**
	 * Sets the shadow sizes of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setShadowSizeList(final List<Double> values);

	/**
	 * Sets the shadow angles of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setShadowAngleList(final List<Double> values);

	/**
	 * Sets the shadow colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setShadowColList(final List<Color> values);

	/**
	 * Sets the first gradient colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGradColStartList(final List<Color> values);

	/**
	 * Sets the last gradient colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGradColEndList(final List<Color> values);

	/**
	 * Sets the thickness of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setThicknessList(final List<Double> values);

	/**
	 * Sets the style of the filling of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setFillingStyleList(final List<FillingStyle> values);

	/**
	 * Sets the line style colour of the shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setLineStyleList(final List<LineStyle> values);

	/**
	 * Sets the filling colour of the dot shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setDotFillingColList(final List<Color> values);

	/**
	 * Sets the size of the dot shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setDotSizeList(final List<Double> values);

	/**
	 * Sets the starting points of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridStartList(final List<IPoint> values);

	/**
	 * @return The list of the starting points of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<IPoint> getGridStartList();

	/**
	 * Sets the ending points of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridEndList(final List<IPoint> values);

	/**
	 * @return The list of the ending points of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<IPoint> getGridEndList();

	/**
	 * Sets the origin points of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridOriginList(final List<IPoint> values);

	/**
	 * @return The list of the origin points of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<IPoint> getGridOriginList();

	/**
	 * Sets the size of the labels of the grid shapes of the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridLabelSizeList(final List<Integer> values);

	/**
	 * @return The list of the sizes of the labels of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Integer> getGridLabelSizeList();

	/**
	 * Sets the Y-coordinate of the labels of the grid contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridXLabelSouthList(final List<Boolean> values);

	/**
	 * @return The list of the Y-coordinate labels of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> getGridXLabelSouthList();

	/**
	 * Sets the X-coordinate of the labels of the grid contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridYLabelWestList(final List<Boolean> values);

	/**
	 * @return The list of the Y-coordinate labels of the grid shapes contained by the group.
	 * If a shape of the group does not support the starting point property, null is added
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> getGridYLabelWestList();

	/**
	 * Sets the style of the axes contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesStyleList(final List<AxesStyle> values);

	/**
	 * @return The list of the styles of the axes contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<AxesStyle> getAxesStyleList();

	/**
	 * Sets the style of the axes' ticks contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesTicksStyleList(final List<TicksStyle> values);

	/**
	 * @return The list of the styles of the axes' ticks contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<TicksStyle> getAxesTicksStyleList();

	/**
	 * Sets the size of the axes' ticks contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesTicksSizeList(final List<Double> values);

	/**
	 * @return The list of the sizes of the axes' ticks contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getAxesTicksSizeList();

	/**
	 * Sets how the ticks of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesTicksDisplayedList(final List<PlottingStyle> values);

	/**
	 * @return The list of the plotting styles of the axes' ticks contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<PlottingStyle> getAxesTicksDisplayedList();

	/**
	 * Sets the labels' increments of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesIncrementsList(final List<IPoint> values);

	/**
	 * @return The list of the labels' increments of the axes' ticks contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<IPoint> getAxesIncrementsList();

	/**
	 * Sets how the labels of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesLabelsDisplayedList(final List<PlottingStyle> values);

	/**
	 * @return The list of the plotting styles of the axes' labels contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<PlottingStyle> getAxesLabelsDisplayedList();

	/**
	 * Defines if the origin of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesShowOriginList(final List<Boolean> values);

	/**
	 * @return The list of booleans defining if the origin of the axes contained in the group must be shown.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> getAxesShowOriginList();

	/**
	 * Sets the distances between the labels of the axes contained by the group are displayed.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setAxesDistLabelsList(final List<IPoint> values);

	/**
	 * @return The list of the distances between the labels of the axes contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<IPoint> getAxesDistLabelsList();

	/**
	 * Sets the labels' colours of the grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridLabelsColourList(final List<Color> values);

	/**
	 * @return The list of labels' colours of the grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getGridLabelsColourList();

	/**
	 * Sets the labels' colours of the grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setSubGridColourList(final List<Color> values);

	/**
	 * @return The list of labels' colours of the grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Color> getSubGridColourList();

	/**
	 * Sets the width of the grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridWidthList(final List<Double> values);

	/**
	 * @return The width of the grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getGridWidthList();

	/**
	 * Sets the width of the sub-grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setSubGridWidthList(final List<Double> values);

	/**
	 * @return The width of the sub-grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Double> getSubGridWidthList();

	/**
	 * Sets the number of dots composing the main lines of each grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setGridDotsList(final List<Integer> values);

	/**
	 * @return The number of dots composing the main lines of each grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Integer> getGridDotsList();

	/**
	 * Sets the number of dots composing the sub-lines of each grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setSubGridDotsList(final List<Integer> values);

	/**
	 * @return The number of dots composing the sub-lines of each grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Integer> getSubGridDotsList();

	/**
	 * Sets the division of the sub-lines of each grids contained by the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setSubGridDivList(final List<Integer> values);

	/**
	 * @return The division of the sub-lines of each grids contained by the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Integer> getSubGridDivList();

	/**
	 * Sets the type of the freehand shapes contained in the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setFreeHandTypeList(final List<FreeHandType> values);

	/**
	 * @return The types of the freehand shapes contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<FreeHandType> getFreeHandTypeList();

	/**
	 * Sets the interval of the freehand shapes contained in the group.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setFreeHandIntervalList(final List<Integer> values);

	/**
	 * @return The intervals of the freehand shapes contained in the group.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Integer> getFreeHandIntervalList();

	/**
	 * Defines if the freehand shapes contained in the group are open.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setFreeHandOpenList(final List<Boolean> values);

	/**
	 * @return The boolean value defining if the freehand shapes contained in the group are open.
	 * If a shape of the group is not an axe, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> getFreeHandOpenList();

	/**
	 * @return The boolean value defining if the shapes contained in the group must show their points.
	 * If a shape of the group cannot show their points, null is added.
	 * to the list. The list cannot be null.
	 * @since 3.0
	 */
	List<Boolean> getShowPointsList();

	/**
	 * Defines if the shapes contained in the group must show their points.
	 * @param values The list of values to use. Its must must equals the number of
	 * shapes of the group. If an element of the list is null, its corresponding
	 * shape will not be set.
	 * @since 3.0
	 */
	void setShowPointsList(final List<Boolean> values);
}
