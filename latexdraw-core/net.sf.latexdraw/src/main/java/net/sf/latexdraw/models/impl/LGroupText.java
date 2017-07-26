/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.beans.property.StringProperty;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;

/**
 * This trait encapsulates the code of the group related to the support of texts.
 * @author Arnaud Blouin
 */
interface LGroupText extends IGroup {
	/** May return the first free hand shape of the group. */
	default Optional<IText> firstIText() {
		return txtShapes().stream().filter(sh -> sh.isTypeOf(IText.class)).findFirst();
	}

	default List<IText> txtShapes() {
		return getShapes().stream().filter(sh -> sh instanceof IText).map(sh -> (IText) sh).collect(Collectors.toList());
	}

	@Override
	default TextPosition getTextPosition() {
		return firstIText().map(sh -> sh.getTextPosition()).orElse(null);
	}

	@Override
	default void setTextPosition(final TextPosition textPosition) {
		txtShapes().forEach(sh -> sh.setTextPosition(textPosition));
	}

	@Override
	default String getText() {
		return firstIText().map(sh -> sh.getText()).orElse(null);
	}

	@Override
	default void setText(final String text) {
		txtShapes().forEach(sh -> sh.setText(text));
	}

	@Override
	default StringProperty textProperty() {//FIXME create a TextProp
		return null;
	}
}
