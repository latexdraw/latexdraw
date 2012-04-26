package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IGroup
import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.DrawingTK

/**
 * A parser that parses commands composed of 1 optional parameter block,
 * and 1 mandatory position block, and 1 optional position block.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 2012-04-26<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
trait PSTCommandParam2PosParser extends PSTAbstractParser with PSTParamParser {
	def parseCommandParame2Pos(ctx : PSTContext) : Parser[List[IShape]] =
		command ~ opt(parseParam(ctx)) ^^ { case cmd ~ _ =>
			cmd.substring(1) match {
				case "psframe*" | "psframe" =>
					val rec = DrawingTK.getFactory.createRectangle(true)
					setShapeParameters(rec, ctx)
					List(rec)
				case name => println("Unknown command: " + name) ; Nil
			}
	}
}
