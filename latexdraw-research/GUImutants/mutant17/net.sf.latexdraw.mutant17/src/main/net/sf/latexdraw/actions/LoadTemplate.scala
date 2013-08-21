package net.sf.latexdraw.actions

import javax.swing.JLabel
import net.sf.latexdraw.generators.svg.SVGDocumentGenerator
import net.sf.latexdraw.ui.LFrame
import org.malai.swing.action.library.IOAction

/**
 * This action loads a given template.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2012-04-19<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
class LoadTemplate extends IOAction[LFrame, JLabel] {
	protected def doActionBody() {
		SVGDocumentGenerator.INSTANCE.insert(file.getPath, ui)
	}
}