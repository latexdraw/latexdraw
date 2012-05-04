package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IShape
import net.sf.latexdraw.glib.models.interfaces.DrawingTK

trait PSCurveParabolaParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser with PSTBracketBlockParser with PSTValueParser {
	/**
	 * Parses parabola commands.
	 */
	def parseParabola(ctx : PSTContext) : Parser[List[IShape]] =
		("\\parabola*" | "\\parabola") ~ opt(parseParam(ctx)) ~ opt(parseBracket(ctx)) ~ parseCoord(ctx) ~ parseCoord(ctx) ^^ {
			case cmdName ~ _ ~ arrowRaw ~ pt1Raw ~ pt2Raw =>

		PSTParser.errorLogs += "Command parabola not supported yet."
		Nil
	}
}