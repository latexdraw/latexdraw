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

package net.sf.latexdraw.parsers.pst.parser

/**
 * A point which coordinates may have a unit.
 * @author Arnaud BLOUIN
 */
class PointUnit(val x:Double, val y:Double, val xUnit:String, val yUnit:String) {
	/**
	 * Duplicates the point.
	 */
	def dup() = new PointUnit(x, y, xUnit, yUnit)
}
