package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IShape
import scala.collection.mutable.ListBuffer
import net.sf.latexdraw.glib.models.interfaces.DrawingTK
import net.sf.latexdraw.glib.models.interfaces.IPoint
import net.sf.latexdraw.glib.models.interfaces.IPolygon

trait PSPolygonParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser with PSTValueParser {
	/**
	 * Parses pspolygon commands.
	 */
	def parsePspolygon(ctx : PSTContext) : Parser[List[IShape]] =
		("\\pspolygon*" | "\\pspolygon") ~ opt(parseParam(ctx)) ~ parseCoord(ctx) ~ rep1(parseCoord(ctx)) ^^ { case cmdName ~ _ ~ pt1 ~ pts =>

		val ptList = pts.length match {
				case 1 => DrawingTK.getFactory.createPoint(ctx.origin.getX, ctx.origin.getY) :: pt1 :: pts
				case _ => pt1 :: pts
			}

		val ptList2 = new ListBuffer[IPoint]
		ptList.foreach{pt => ptList2 += transformPointTo2DScene(pt)}

		List(createPolygon(cmdName.endsWith("*"), ptList2, ctx))
	}


	/**
	 * Creates and initialises a line.
	 */
	private def createPolygon(hasStar : Boolean, pts : ListBuffer[IPoint], ctx : PSTContext) : IPolygon = {
		val pol = DrawingTK.getFactory.createPolygon(true)
		pts.foreach{pt => pol.addPoint(pt)}

		setShapeParameters(pol, ctx)

		if(hasStar)
			setShapeForStar(pol)
		pol
	}
}