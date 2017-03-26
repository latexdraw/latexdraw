/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.view.jfx;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import java.awt.geom.Rectangle2D;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
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

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewGrid(final IGrid sh) {
		super(sh);
		maingrid = new Path();
		subgrid = new Path();
		mainGridLineCapUpdate = (o, formerv, newv) -> maingrid.setStrokeLineCap(newv.doubleValue() > 0d ? StrokeLineCap.ROUND : StrokeLineCap.SQUARE);
		subGridLineCapUpdate = (o, formerv, newv) -> subgrid.setStrokeLineCap(newv.doubleValue() > 0d ? StrokeLineCap.ROUND : StrokeLineCap.SQUARE);

		getChildren().add(subgrid);
		getChildren().add(maingrid);

		maingrid.strokeProperty().bind(Bindings.createObjectBinding(() -> model.getLineColour().toJFX(), model.lineColourProperty()));
		model.gridDotsProperty().addListener(mainGridLineCapUpdate);
		subgrid.strokeProperty().bind(Bindings.createObjectBinding(() -> model.getSubGridColour().toJFX(), model.subGridColourProperty()));
		model.subGridDotsProperty().addListener(subGridLineCapUpdate);
		updatePath();
	}


	//		// Drawing the labels.
	//		if(model.getLabelsSize()>0) {
	//			g.setColor(model.getGridLabelsColour());
	//			g.fill(pathLabels);
	//		}


	private void updatePathMainGridDots(final double unit, final double minX, final double maxX, final double minY, final double maxY,
										final double posX, final double posY, final double xStep, final double yStep, final double tlx,
										final double tly, final double brx, final double bry, final double absStep) {
		final int gridDots = model.getGridDots();
		final double dotStep = unit * IShape.PPC / gridDots;
		final ObservableList<PathElement> elements = maingrid.getElements();

		for(double k = minX, i = posX; k <= maxX; i += xStep, k++) {
			for(double m = tly, n = minY; n < maxY; n++, m += absStep) {
				for(double l = 0d, j = m; l < gridDots; l++, j += dotStep) {
					elements.add(new MoveTo(i, j));
					elements.add(new LineTo(i, j));
				}
			}
		}

		for(double k = minY, i = posY; k <= maxY; i -= yStep, k++) {
			for(double m = tlx, n = minX; n < maxX; n++, m += absStep) {
				for(double l = 0d, j = m; l < gridDots; l++, j += dotStep) {
					elements.add(new MoveTo(j, i));
					elements.add(new LineTo(j, i));
				}
			}
		}

		elements.add(new MoveTo(brx, bry));
		elements.add(new LineTo(brx, bry));
	}


	private void updatePathMainGrid(final double unit, final double minX, final double maxX, final double minY, final double maxY,
									final double posX, final double posY, final double xStep, final double yStep, final double tlx,
									final double tly, final double brx, final double bry, final double absStep) {
		if(model.getGridDots() > 0) {
			updatePathMainGridDots(unit, minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, brx, bry, absStep);
		}else {
			final ObservableList<PathElement> elements = maingrid.getElements();

			for(double k = minX, i = posX; k <= maxX; i += xStep, k++) {
				elements.add(new MoveTo(i, bry));
				elements.add(new LineTo(i, tly));
			}

			for(double k = minY, i = posY; k <= maxY; i -= yStep, k++) {
				elements.add(new MoveTo(tlx, i));
				elements.add(new LineTo(brx, i));
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
						elements.add(new MoveTo(n + k * dotStep, m));
						elements.add(new LineTo(n + k * dotStep, m));
					}
				}
			}

			for(double j = 0d, n = tly; j < nbY; j++, n += ySubStep) {
				for(double i = 0d, m = tlx; i <= nbX; i++, m += xSubStep) {
					for(double k = 0d; k < subGridDots; k++) {
						elements.add(new MoveTo(m, n + k * dotStep));
						elements.add(new LineTo(m, n + k * dotStep));
					}
				}
			}

			elements.add(new MoveTo(brx, bry));
			elements.add(new LineTo(brx, bry));
		}else {
			if(subGridDiv > 1d) {
				for(double k = minX, i = posX; k < maxX; i += xStep, k++) {
					for(double j = 0d; j <= subGridDiv; j++) {
						elements.add(new MoveTo(i + xSubStep * j, bry));
						elements.add(new LineTo(i + xSubStep * j, tly));
					}
				}

				for(double k = minY, i = posY; k < maxY; i -= yStep, k++) {
					for(double j = 0d; j <= subGridDiv; j++) {
						elements.add(new MoveTo(tlx, i - ySubStep * j));
						elements.add(new LineTo(brx, i - ySubStep * j));
					}
				}
			}
		}
	}


	private void updatePathLabels(final double minX, final double maxX, final double minY, final double maxY, final double posX,
								  final double posY, final double xStep, final double yStep, final double tlx, final double tly, final double absStep) {
		final int labelsSize = model.getLabelsSize();
		if(labelsSize < 0) return;

		final FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		final Font font = fontLoader.font("cmr10", FontWeight.NORMAL, FontPosture.REGULAR, model.getLabelsSize());
		final float labelHeight = fontLoader.getFontMetrics(font).getLineHeight();
		final float labelWidth = fontLoader.computeStringWidth(String.valueOf((int) maxX), font);
		final double origX = model.getOriginX();
		final double origY = model.getOriginY();
		final boolean isWest = model.isYLabelWest();
		final boolean isSouth = model.isXLabelSouth();
		final double xorig = posX + (origX - minX) * xStep;
		final double yorig = isSouth ? posY - yStep * (origY - minY) + labelHeight : posY - yStep * (origY - minY) - 2;
		final double width = model.getGridWidth() / 2d;
		final double tmp = isSouth ? width : -width;
		String label;
		final double yPos = yorig + tmp;

		for(double i = tlx + (isWest ? width + labelsSize / 4d : -width - labelWidth - labelsSize / 4d), j = minX; j <= maxX; i += absStep, j++) {
			addTextLabel(String.valueOf((int) j), i, yPos, font);
		}

		final double xGapNotWest = xorig + labelsSize / 4d + width;

		for(double i = tly + (isSouth ? -width - labelsSize / 4d : width + labelHeight), j = maxY; j >= minY; i += absStep, j--) {
			label = String.valueOf((int) j);
			double x = isWest ? xorig - fontLoader.computeStringWidth(label, font) - labelsSize / 4. - width : xGapNotWest;
			addTextLabel(label, x, i, font);
		}
	}


	@Override
	protected Text addTextLabel(final String text, final double x, final double y, final Font font) {
		final Text label = super.addTextLabel(text, x, y, font);
		final FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();

		label.strokeProperty().bind(Bindings.createObjectBinding(() -> model.getLineColour().toJFX(), model.gridLabelsColourProperty()));
		label.fontProperty().bind(Bindings.createObjectBinding(() ->
			fontLoader.font(font.getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, model.getLabelsSize()), model.gridWidthProperty()));

		return label;
	}


	private void updatePath() {
		final double minY = model.getGridMinY();
		final double maxY = model.getGridMaxY();
		final double minX = model.getGridMinX();
		final double maxX = model.getGridMaxX();
		final double unit = model.getUnit();
		double yStep = IShape.PPC * unit;
		double xStep = IShape.PPC * unit;
		xStep *= model.getGridEndX() < model.getGridStartX() ? -1d : 1d;
		yStep *= model.getGridEndY() < model.getGridStartY() ? -1d : 1d;
		final IPoint pos = model.getPosition();
		final double posX = pos.getX() + Math.min(model.getGridStartX(), model.getGridEndX()) * IShape.PPC * unit;
		final double posY = pos.getY() - Math.min(model.getGridStartY(), model.getGridEndY()) * IShape.PPC * unit;
		final double absStep = Math.abs(xStep);
		final Rectangle2D bounds = getGridBounds(posX, posY);
		final double tlx = bounds.getMinX();
		final double tly = bounds.getMinY();

		maingrid.getElements().clear();
		subgrid.getElements().clear();
		cleanLabels();

		updatePathSubGrid(unit, minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, bounds.getMaxX(), bounds.getMaxY());
		updatePathMainGrid(unit, minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, bounds.getMaxX(), bounds.getMaxY(), absStep);
		updatePathLabels(minX, maxX, minY, maxY, posX, posY, xStep, yStep, tlx, tly, absStep);
	}


	private Rectangle2D getGridBounds(final double posX, final double posY) {
		final Rectangle2D rec = new Rectangle2D.Double();
		final double gridStartx = model.getGridStartX();
		final double gridStarty = model.getGridStartY();
		final double gridEndx = model.getGridEndX();
		final double gridEndy = model.getGridEndY();
		final double step = IShape.PPC * model.getUnit();

		if(gridStartx < gridEndx) {
			if(gridStarty < gridEndy) {
				rec.setFrameFromDiagonal(posX, posY - step * Math.abs(gridEndy - gridStarty), posX + step * Math.abs(gridEndx - gridStartx), posY);
			}else {
				rec.setFrameFromDiagonal(posX, posY, posX + step * Math.abs(gridEndx - gridStartx), posY + step * Math.abs(gridEndy - gridStarty));
			}
		}else {
			if(gridStarty < gridEndy) {
				rec.setFrameFromDiagonal(posX - step * Math.abs(gridEndx - gridStartx), posY - step * Math.abs(gridEndy - gridStarty), posX, posY);
			}else {
				rec.setFrameFromDiagonal(posX - step * Math.abs(gridEndx - gridStartx), posY, posX, posY + step * Math.abs(gridEndy - gridStarty));
			}
		}

		return rec;
	}


	@Override
	public void flush() {
		super.flush();
		maingrid.getElements().clear();
		maingrid.strokeProperty().unbind();
		subgrid.strokeProperty().unbind();
		model.gridDotsProperty().removeListener(mainGridLineCapUpdate);
		model.subGridDotsProperty().removeListener(subGridLineCapUpdate);
	}
}
