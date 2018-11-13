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
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.TextPosition;
import org.jetbrains.annotations.NotNull;

/**
 * This trait encapsulates the code of the group related to the support of texts.
 * @author Arnaud Blouin
 */
interface GroupTextBase extends Group {
	/** May return the first free hand shape of the group. */
	default @NotNull Optional<Text> firstIText() {
		return txtShapes().stream().filter(sh -> sh.isTypeOf(Text.class)).findFirst();
	}

	default @NotNull List<Text> txtShapes() {
		return getShapes().stream().filter(sh -> sh instanceof Text).map(sh -> (Text) sh).collect(Collectors.toList());
	}

	@Override
	default @NotNull TextPosition getTextPosition() {
		return firstIText().map(sh -> sh.getTextPosition()).orElse(TextPosition.BOT_LEFT);
	}

	@Override
	default void setTextPosition(final @NotNull TextPosition textPosition) {
		txtShapes().forEach(sh -> sh.setTextPosition(textPosition));
	}

	@Override
	default @NotNull String getText() {
		return firstIText().map(sh -> sh.getText()).orElse("");
	}

	@Override
	default void setText(final @NotNull String text) {
		txtShapes().forEach(sh -> sh.setText(text));
	}
}
