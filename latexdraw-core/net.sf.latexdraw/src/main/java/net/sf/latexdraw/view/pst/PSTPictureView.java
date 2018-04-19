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

import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.util.LFileUtils;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.LangTool;

/**
 * Defines a PSTricks view of the LPicture model.
 * @author Arnaud Blouin
 */
public class PSTPictureView extends PSTShapeView<IPicture> {
	/**
	 * Creates and initialises a LPicture PSTricks view.
	 * @param model The model to view.
	 * @throws IllegalArgumentException If the given model is not valid.
	 * @since 3.0
	 */
	protected PSTPictureView(final IPicture model) {
		super(model);
	}


	@Override
	public String getCode(final IPoint origin, final float ppc) {
		if(!MathUtils.INST.isValidPt(origin) || ppc < 1) {
			return "";
		}

		String path = shape.getPathTarget();
		final StringBuilder start = new StringBuilder();
		final StringBuilder rot = getRotationHeaderCode(ppc, origin);
		final StringBuilder code = new StringBuilder();

		path = path.replaceAll("\\\\", "/");

		if(path.contains(" ")) {
			start.append(LangTool.INSTANCE.getBundle().getString("Picture.0")).append(LSystem.EOL);
		}

		if(rot != null) {
			code.append(rot);
		}

		code.append(start);
		code.append("\\rput("); //NON-NLS
		code.append(MathUtils.INST.getCutNumberFloat((shape.getX() + shape.getWidth() / 2. - origin.getX()) / ppc)).append(',');
		code.append(MathUtils.INST.getCutNumberFloat((origin.getY() - shape.getY() - shape.getHeight() / 2.) / ppc)).append(')').append('{');
		code.append("\\includegraphics{"); //NON-NLS
		code.append(LFileUtils.INSTANCE.normalizeForLaTeX(path));
		code.append('}').append('}');

		if(rot != null) {
			code.append('}');
		}

		return code.toString();
	}
}
