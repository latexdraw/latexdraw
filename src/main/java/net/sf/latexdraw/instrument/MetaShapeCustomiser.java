/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ListChangeListener;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.service.EditingService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.view.jfx.Canvas;
import org.jetbrains.annotations.NotNull;

/**
 * This meta-instrument manages the instruments that customises shape properties.
 * Facade pattern for all the instruments that handle shape's properties.
 * @author Arnaud BLOUIN
 */
public class MetaShapeCustomiser extends ShapePropertyCustomiser {
	private final @NotNull Set<ShapePropertyCustomiser> shapeControllers;

	@Inject
	public MetaShapeCustomiser(final ShapeBorderCustomiser borderCust, final ShapeDoubleBorderCustomiser dbleBordCust, final ShapeShadowCustomiser shadCust,
		final ShapeFillingCustomiser fillCust, final ShapeTextCustomiser txtCust, final ShapeRotationCustomiser rotCust, final ShapeArrowCustomiser arrCust,
		final ShapeDotCustomiser dotCust, final ShapeArcCustomiser arcCust, final ShapeCoordDimCustomiser dimPosCust, final ShapeStdGridCustomiser absGridCust,
		final ShapeGrouper grouper, final ShapeGridTransformer gridTransf, final ShapeAxesCustomiser axesCust, final ShapeGridCustomiser gridCust,
		final ShapeFreeHandCustomiser fhCust, final ShapeTransformer transformer, final ShapePositioner positioner, final ShapePlotCustomiser plotCust,
		final Hand hand, final Pencil pencil, final Canvas canvas, final Drawing drawing, final EditingService editing) {
		super(hand, pencil, canvas, drawing, editing);

		shapeControllers = new HashSet<>();
		shapeControllers.add(borderCust);
		shapeControllers.add(dbleBordCust);
		shapeControllers.add(shadCust);
		shapeControllers.add(fillCust);
		shapeControllers.add(txtCust);
		shapeControllers.add(rotCust);
		shapeControllers.add(arrCust);
		shapeControllers.add(dotCust);
		shapeControllers.add(arcCust);
		shapeControllers.add(dimPosCust);
		shapeControllers.add(absGridCust);
		shapeControllers.add(grouper);
		shapeControllers.add(axesCust);
		shapeControllers.add(gridCust);
		shapeControllers.add(fhCust);
		shapeControllers.add(transformer);
		shapeControllers.add(positioner);
		shapeControllers.add(plotCust);
		shapeControllers.add(gridTransf);
	}

	@Override
	public void setActivated(final boolean act) {
		super.setActivated(act);

		if(act) {
			update();
		}else {
			shapeControllers.forEach(c -> c.setActivated(false));
		}
	}

	@Override
	protected void update(final Group shape) {
		shapeControllers.forEach(c -> c.update(shape));
	}

	@Override
	public void clearEvents() {
		shapeControllers.forEach(c -> c.clearEvents());
	}

	@Override
	protected void configureBindings() {
		// This instrument does not have any link.
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		// This instrument does not have any widget.
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		super.initialize(location, resources);
		drawing.getSelection().getShapes().addListener((ListChangeListener.Change<? extends Shape> change) -> {
			while(change.next()) {
				if(change.wasRemoved()) {
					if(hand.isActivated()) {
						setActivated(!drawing.getSelection().isEmpty());
					}
				}else {
					setActivated(true);
				}
			}
		});
	}
}
