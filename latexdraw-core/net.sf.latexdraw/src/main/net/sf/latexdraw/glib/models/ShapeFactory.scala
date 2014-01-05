package net.sf.latexdraw.glib.models

import net.sf.latexdraw.glib.models.interfaces.IShapeFactory
import net.sf.latexdraw.glib.models.impl.LShapeFactory

/**
 * This class contains the factory that must be used to create shape instances.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 01/04/2011<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
object ShapeFactory {
	val factory : IShapeFactory = new LShapeFactory()
}
