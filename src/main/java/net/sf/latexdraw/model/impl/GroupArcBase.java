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
import net.sf.latexdraw.model.api.property.ArcProp;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;

/**
 * This trait encapsulates the code of the group related to the support of IArcProp shapes.
 * @author Arnaud Blouin
 */
interface GroupArcBase extends Group {
	/** May return the first IArcProp shape of the group. */
	default <T extends Shape & ArcProp> Optional<T> firstIArcProp() {
		return (Optional<T>) arcShapes().stream().filter(sh -> sh.isTypeOf(ArcProp.class)).findFirst();
	}

	default <T extends Shape & ArcProp> List<T> arcShapes() {
		return getShapes().stream().filter(sh -> sh instanceof ArcProp).map(sh -> (T) sh).collect(Collectors.toList());
	}

	@Override
	default ArcStyle getArcStyle() {
		return firstIArcProp().map(arc -> arc.getArcStyle()).orElse(ArcStyle.ARC);
	}

	@Override
	default void setArcStyle(final ArcStyle typeArc) {
		arcShapes().forEach(sh -> sh.setArcStyle(typeArc));
	}

	@Override
	default double getAngleStart() {
		return firstIArcProp().map(arc -> arc.getAngleStart()).orElse(Double.NaN);
	}

	@Override
	default void setAngleStart(final double angleStart) {
		arcShapes().forEach(sh -> sh.setAngleStart(angleStart));
	}

	@Override
	default double getAngleEnd() {
		return firstIArcProp().map(arc -> arc.getAngleEnd()).orElse(Double.NaN);
	}


	@Override
	default void setAngleEnd(final double angleEnd) {
		arcShapes().forEach(sh -> sh.setAngleEnd(angleEnd));
	}
}
