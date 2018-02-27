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
package net.sf.latexdraw.instruments;

import java.util.Optional;
import net.sf.latexdraw.actions.shape.CopyShapes;
import net.sf.latexdraw.actions.shape.SelectShapes;
import org.malai.action.ActionsRegistry;

/**
 * A helper to search in the action registry.
 * @author  Arnaud Blouin
 */
public interface ActionRegistrySearcher {
	default Optional<CopyShapes> getCopyCutAction() {
		return ActionsRegistry.INSTANCE.getActions().parallelStream().
			filter(action -> action instanceof CopyShapes).map(action -> (CopyShapes) action).findFirst();
	}

	default Optional<SelectShapes> getSelectAction() {
		return ActionsRegistry.INSTANCE.getActions().parallelStream().
			filter(action -> action instanceof SelectShapes).map(action -> (SelectShapes) action).findFirst();
	}
}
