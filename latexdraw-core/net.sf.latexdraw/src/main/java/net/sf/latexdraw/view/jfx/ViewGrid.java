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
package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * The JFX view of a grid.
 * @author Arnaud Blouin
 */
public class ViewGrid extends ViewStdGrid<IGrid> {
	private final Path subgrid;
	private final Path maingrid;
	private final ChangeListener<Number> mainGridLineCapUpdate;
	private final ChangeListener<Number> subGridLineCapUpdate;
	private final ChangeListener<Number> gridWidthUpdate;
	private final ChangeListener<Number> subGridUpdate;
	private final ChangeListener<Object> gridUpdate;
	private final ChangeListener<Object> labelUpdate;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewGrid(final IGrid sh) {
		super(sh);
		maingrid = new Path();
		subgrid = new Path();
		mainGridLineCapUpdate = (o, formerv, newv) -> {
			maingrid.setStrokeLineCap(newv.doubleValue() > 0d ? StrokeLineCap.ROUND : StrokeLineCap.SQUARE);
			updatePath(true, false, false);
		};
		subGridLineCapUpdate = (o, formerv, newv) -> {
			subgrid.setStrokeLineCap(newv.doubleValue() > 0d ? StrokeLineCap.ROUND : StrokeLineCap.SQUARE);
			updatePath(false, true, false);
		};
		gridUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(true, true, true));
		labelUpdate = (o, formerv, newv) -> checkToExecuteOnUIThread(() -> updatePath(false, false, true));
		gridWidthUpdate = (o, formerv, newv) -> {
			maingrid.setStrokeWidth(model.getGridWidth());
			checkToExecuteOnUIThread(() -> updatePath(false, false, true));
		};
		subGridUpdate = (o, formerv, newv) -> updatePath(false, true, false);

		getChildren().add(subgrid);
		getChildren().add(maingrid);

		maingrid.strokeProperty().bind(Bindings.createObjectBinding(() -> model.getLineColour().toJFX(), model.lineColourProperty()));
		model.gridDotsProperty().addListener(mainGridLineCapUpdate);
		subgrid.strokeProperty().bind(Bindings.createObjectBinding(() -> model.getSubGridColour().toJFX(), model.subGridColourProperty()));
		model.subGridDotsProperty().addListener(subGridLineCapUpdate);
		model.gridEndXProperty().addListener(gridUpdate);
		model.gridEndYProperty().addListener(gridUpdate);
		model.gridStartXProperty().addListener(gridUpdate);
		model.gridStartYProperty().addListener(gridUpdate);
		model.originXProperty().addListener(labelUpdate);
		model.originYProperty().addListener(labelUpdate);
		model.labelsSizeProperty().addListener(labelUpdate);
		model.yLabelWestProperty().addListener(labelUpdate);
		model.xLabelSouthProperty().addListener(labelUpdate);
		model.subGridDivProperty().addListener(subGridUpdate);
		model.gridWidthProperty().addListener(gridWidthUpdate);
		model.subGridWidthProperty().addListener(subGridUpdate);
		subgrid.strokeWidthProperty().bind(model.subGridWidthProperty());
		model.unitProperty().addListener(gridUpdate);

		updatePath(true, true, true);
	}


	private void updatePathMainGridDots(final double unit, final double minX, final double maxX, final double minY, final double maxY,
										final double posX, final double posY, final double xStep, final double yStep, final double tlx,
										final double tly, final double brx, final double bry, final double absStep) {
		final int gridDots = model.getGridDots();
		final double dotStep = unit * IShape.PPC / gridDots;
		final ObservableList<PathElement> elements = maingrid.getElements();

		for(double k = minX, i = posX; k <= maxX; i += xStep, k++) {
			for(double m = tly, n = minY; n < maxY; n++, m += absStep) {
				for(double l = 0d, j = m; l < gridDots; l++, j += dotStep) {
					elements.add(ViewFactory.INSTANCE.createMoveTo(i, j));
					elements.add(ViewFactory.INSTANCE.createLineTo(i, j));
				}
			}
		}

		for(double k = minY, i = posY; k <= maxY; i -= yStep, k++) {
			for(double m = tlx, n = minX; n < maxX; n++, m += absStep) {
				for(double l = 0d, j = m; l < gridDots; l++, j += dotStep) {
					elements.add(ViewFactory.INSTANCE.createMoveTo(j, i));
					elements.add(ViewFactory.INSTANCE.createLineTo(j, i));
				}
			}
		}

		elements.add(ViewFactory.INSTANCE.createMoveTo(brx, bry));
		elements.add(ViewFactory.INSTANCE.createLineTo(brx, bry));
	}


	private void updatePathMainGrid(final double unit, final double minX, final double maxX, final double minY, final double maxY,
									final double posX, final double posY, final double xStep, final double yStep, final double tlx,
									final double tly, final double brx, final double bry, final double absStep) {
		if(model.getGridDots() > 0) {
			updatePathMainGridDots(unit, minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, brx, bry, absStep);
		}else {
			final ObservableList<PathElement> elements = maingrid.getElements();

			for(double k = minX, i = posX; k <= maxX; i += xStep, k++) {
				elements.add(ViewFactory.INSTANCE.createMoveTo(i, bry));
				elements.add(ViewFactory.INSTANCE.createLineTo(i, tly));
			}

			for(double k = minY, i = posY; k <= maxY; i -= yStep, k++) {
				elements.add(ViewFactory.INSTANCE.createMoveTo(tlx, i));
				elements.add(ViewFactory.INSTANCE.createLineTo(brx, i));
			}
		}
	}


	private void updatePathSubGrid(final double unit, final double minX, final double maxX, final double minY, final double maxY,
							final double posX, final double posY, final double xStep, final double yStep, final double tlx,
							final double tly, final double brx, final double bry) {
		final double subGridDiv = model.getSubGridDiv();
		final double subGridDots = model.getSubGridDots();
		final double xSubStep = xStep / subGridDiv;
		final double ySubStep = yStep / subGridDiv;
		final ObservableList<PathElement> elements = subgrid.getElements();

		if(subGridDots > 0d) {
			final double dotStep = unit * IShape.PPC / (subGridDots * subGridDiv);
			final double nbX = (maxX - minX) * subGridDiv;
			final double nbY = (maxY - minY) * subGridDiv;

			for(double i = 0d, n = tlx; i < nbX; i++, n += xSubStep) {
				for(double j = 0d, m = tly; j <= nbY; j++, m += ySubStep) {
					for(double k = 0d; k < subGridDots; k++) {
						elements.add(ViewFactory.INSTANCE.createMoveTo(n + k * dotStep, m));
						elements.add(ViewFactory.INSTANCE.createLineTo(n + k * dotStep, m));
					}
				}
			}

			for(double j = 0d, n = tly; j < nbY; j++, n += ySubStep) {
				for(double i = 0d, m = tlx; i <= nbX; i++, m += xSubStep) {
					for(double k = 0d; k < subGridDots; k++) {
						elements.add(ViewFactory.INSTANCE.createMoveTo(m, n + k * dotStep));
						elements.add(ViewFactory.INSTANCE.createLineTo(m, n + k * dotStep));
					}
				}
			}

			elements.add(ViewFactory.INSTANCE.createMoveTo(brx, bry));
			elements.add(ViewFactory.INSTANCE.createLineTo(brx, bry));
		}else {
			if(subGridDiv > 1d) {
				for(double k = minX, i = posX; k < maxX; i += xStep, k++) {
					for(double j = 0d; j <= subGridDiv; j++) {
						elements.add(ViewFactory.INSTANCE.createMoveTo(i + xSubStep * j, bry));
						elements.add(ViewFactory.INSTANCE.createLineTo(i + xSubStep * j, tly));
					}
				}

				for(double k = minY, i = posY; k < maxY; i -= yStep, k++) {
					for(double j = 0d; j <= subGridDiv; j++) {
						elements.add(ViewFactory.INSTANCE.createMoveTo(tlx, i - ySubStep * j));
						elements.add(ViewFactory.INSTANCE.createLineTo(brx, i - ySubStep * j));
					}
				}
			}
		}
	}


	private void updatePathLabels(final double minX, final double maxX, final double minY, final double maxY, final double posX,
								final double posY, final double xStep, final double yStep, final double tlx, final double tly, final double absStep) {
		final int labelsSize = model.getLabelsSize();
		if(labelsSize < 0) {
			return;
		}

		final Font font = new Font("cmr10", model.getLabelsSize());
		final Text fooText = new Text(String.valueOf((int) maxX));
		fooText.setFont(font);
		// The max height of the font.
		final double labelHeight = fooText.getLayoutBounds().getHeight();
		final double labelWidth = fooText.getBoundsInLocal().getWidth();

		final double origX = model.getOriginX();
		final double origY = model.getOriginY();
		final boolean isWest = model.isYLabelWest();
		final boolean isSouth = model.isXLabelSouth();
		final double xorig = posX + (origX - minX) * xStep;
		final double yorig = isSouth ? posY - yStep * (origY - minY) + labelHeight : posY - yStep * (origY - minY) - 2d;
		final double width = model.getGridWidth() / 2d;
		final double tmp = isSouth ? width : -width;
		String label;
		final double yPos = yorig + tmp;

		for(double i = tlx + (isWest ? width + labelsSize / 4d : -width - labelWidth - labelsSize / 4d), j = minX; j <= maxX; i += absStep, j++) {
			createTextLabel(String.valueOf((int) j), i, yPos, font);
		}

		final double xGapNotWest = xorig + labelsSize / 4d + width;

		for(double i = tly + (isSouth ? -width - labelsSize / 4d : width + labelHeight), j = maxY; j >= minY; i += absStep, j--) {
			label = String.valueOf((int) j);
			fooText.setText(label);
			final double x = isWest ? xorig - fooText.getBoundsInLocal().getWidth() - labelsSize / 4d - width : xGapNotWest;
			createTextLabel(label, x, i, font);
		}
	}


	@Override
	public Text createTextLabel(final String text, final double x, final double y, final Font font) {
		final Text label = super.createTextLabel(text, x, y, font);

		label.strokeProperty().bind(Bindings.createObjectBinding(() -> model.getGridLabelsColour().toJFX(), model.gridLabelsColourProperty()));
		label.fontProperty().bind(Bindings.createObjectBinding(() -> new Font(font.getFamily(), model.getLabelsSize()), model.labelsSizeProperty()));

		return label;
	}


	private void updatePath(final boolean mainGrid, final boolean subGrid, final boolean labels) {
		final double minY = model.getGridMinY();
		final double maxY = model.getGridMaxY();
		final double minX = model.getGridMinX();
		final double maxX = model.getGridMaxX();
		final double unit = model.getUnit();
		double yStep = IShape.PPC * unit;
		double xStep = IShape.PPC * unit;
		xStep *= model.getGridEndX() < model.getGridStartX() ? -1d : 1d;
		yStep *= model.getGridEndY() < model.getGridStartY() ? -1d : 1d;
		final double posX = Math.min(model.getGridStartX(), model.getGridEndX()) * IShape.PPC * unit;
		final double posY = -Math.min(model.getGridStartY(), model.getGridEndY()) * IShape.PPC * unit;
		final double absStep = Math.abs(xStep);
		final Rectangle2D bounds = getGridBounds(posX, posY);
		final double tlx = bounds.getMinX();
		final double tly = bounds.getMinY();

		if(subGrid) {
			subgrid.getElements().clear();
			updatePathSubGrid(unit, minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, bounds.getMaxX(), bounds.getMaxY());
		}

		if(mainGrid) {
			maingrid.getElements().clear();
			updatePathMainGrid(unit, minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, bounds.getMaxX(), bounds.getMaxY(), absStep);
		}

		if(labels) {
			cleanLabels();
			updatePathLabels(minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, absStep);
		}
	}

	private Rectangle2D getGridBounds(final double posX, final double posY) {
		final double gridStartx = model.getGridStartX();
		final double gridStarty = model.getGridStartY();
		final double gridEndx = model.getGridEndX();
		final double gridEndy = model.getGridEndY();
		final double step = IShape.PPC * model.getUnit();
		final double x = gridStartx < gridEndx ? posX : posX - step * Math.abs(gridEndx - gridStartx);
		final double y = gridStarty < gridEndy ? posY - step * Math.abs(gridEndy - gridStarty) : posY;
		final double width = step * Math.abs(gridEndx - gridStartx);
		final double height = step * Math.abs(gridEndy - gridStarty);

		return new Rectangle2D(x, y, width, height);
	}

	@Override
	public void flush() {
		super.flush();
		maingrid.getElements().clear();
		maingrid.strokeProperty().unbind();
		subgrid.strokeProperty().unbind();
		model.gridDotsProperty().removeListener(mainGridLineCapUpdate);
		model.subGridDotsProperty().removeListener(subGridLineCapUpdate);
		model.gridEndXProperty().removeListener(gridUpdate);
		model.gridEndYProperty().removeListener(gridUpdate);
		model.gridStartXProperty().removeListener(gridUpdate);
		model.gridStartYProperty().removeListener(gridUpdate);
		model.originXProperty().removeListener(gridUpdate);
		model.originYProperty().removeListener(gridUpdate);
		model.labelsSizeProperty().removeListener(labelUpdate);
		model.yLabelWestProperty().removeListener(labelUpdate);
		model.xLabelSouthProperty().removeListener(labelUpdate);
		model.subGridDivProperty().removeListener(subGridUpdate);
		model.gridWidthProperty().removeListener(gridWidthUpdate);
		model.subGridWidthProperty().removeListener(subGridUpdate);
		subgrid.strokeWidthProperty().unbind();
		model.unitProperty().removeListener(gridUpdate);
	}

	/**
	 * @return The JFX sub path of the grid.
	 */
	public Path getSubgrid() {
		return subgrid;
	}

	/**
	 * @return The JFX main path of the grid.
	 */
	public Path getMaingrid() {
		return maingrid;
	}
}
