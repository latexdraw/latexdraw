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
package net.sf.latexdraw.models.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.sf.latexdraw.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;

/**
 * This trait encapsulates the code of the group related to the support of IArcProp shapes.
 */
interface LGroupArc extends IGroup {
	/** May return the first IArcProp shape of the group. */
	default <T extends IShape & IArcProp> Optional<T> firstIArcProp() {
		return (Optional<T>) arcShapes().stream().filter(sh -> sh.isTypeOf(IArcProp.class)).findFirst();
	}

	default <T extends IShape & IArcProp> List<T> arcShapes() {
		return getShapes().stream().filter(sh -> sh instanceof IArcProp).map(sh -> (T) sh).collect(Collectors.toList());
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
