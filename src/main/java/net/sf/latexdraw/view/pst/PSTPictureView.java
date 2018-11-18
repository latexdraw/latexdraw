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
package net.sf.latexdraw.view.pst;

import java.util.ResourceBundle;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.util.SystemUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Defines a PSTricks view of the LPicture model.
 * @author Arnaud Blouin
 */
public class PSTPictureView extends PSTShapeView<Picture> {
	private final @NotNull ResourceBundle bundle;

	/**
	 * Creates and initialises a LPicture PSTricks view.
	 * @param model The model to view.
	 */
	protected PSTPictureView(final @NotNull Picture model, final @NotNull ResourceBundle bundle) {
		super(model);
		this.bundle = bundle;
	}


	@Override
	public @NotNull String getCode(final @NotNull Point origin, final float ppc) {
		String path = shape.getPathTarget();
		final StringBuilder start = new StringBuilder();
		final StringBuilder rot = getRotationHeaderCode(ppc, origin);
		final StringBuilder code = new StringBuilder();

		path = path.replaceAll("\\\\", "/");

		if(path.contains(" ")) {
			start.append(bundle.getString("Picture.0")).append(SystemUtils.getInstance().EOL);
		}

		if(rot != null) {
			code.append(rot);
		}

		code.append(start);
		code.append("\\rput("); //NON-NLS
		code.append(MathUtils.INST.getCutNumberFloat((shape.getX() + shape.getWidth() / 2. - origin.getX()) / ppc)).append(',');
		code.append(MathUtils.INST.getCutNumberFloat((origin.getY() - shape.getY() - shape.getHeight() / 2.) / ppc)).append(')').append('{');
		code.append("\\includegraphics{"); //NON-NLS
		code.append(SystemUtils.getInstance().normalizeForLaTeX(path));
		code.append('}').append('}');

		if(rot != null) {
			code.append('}');
		}

		return code.toString();
	}
}
