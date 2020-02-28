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
package net.sf.latexdraw;

import io.github.interacto.command.CommandsRegistry;
import io.github.interacto.error.ErrorCatcher;
import io.github.interacto.undo.UndoCollector;
import java.util.ArrayList;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.assertj.core.api.Assertions.assertThat;

public class LatexdrawExtension implements BeforeEachCallback, AfterEachCallback {
	ErrorCatcher mementoCatcher;
	SystemUtils mementoSystemUtils;
	CommandsRegistry mementoCommandsRegistry;
	UndoCollector mementoUndoCollector;

	@Override
	public void beforeEach(final ExtensionContext ctx) {
		mementoUndoCollector = UndoCollector.getInstance();
		mementoCommandsRegistry = CommandsRegistry.getInstance();
		mementoSystemUtils = SystemUtils.getInstance();
		mementoCatcher = ErrorCatcher.getInstance();
		clear();
	}

	@Override
	public void afterEach(final ExtensionContext ctx) {
		UndoCollector.setInstance(mementoUndoCollector);
		CommandsRegistry.setInstance(mementoCommandsRegistry);
		SystemUtils.setSingleton(mementoSystemUtils);
		ErrorCatcher.setInstance(mementoCatcher);
		ctx.getTestMethod()
			.filter(m -> !BadaboomCollector.INSTANCE.errorsProperty().isEmpty() && !m.isAnnotationPresent(NoBadaboomCheck.class))
			.ifPresent(m -> {
				final var copy = new ArrayList<>(BadaboomCollector.INSTANCE.errorsProperty());
				clear();
				assertThat(copy).isEmpty();
			});
	}

	private void clear() {
		DviPsColors.INSTANCE.clearUserColours();
		UndoCollector.getInstance().clear();
		BadaboomCollector.INSTANCE.clear();
		CommandsRegistry.getInstance().clear();
	}
}
