package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.glib.models.interfaces.IGroup

trait PSTCommandParam2PosParser extends PSTAbstractParser {
	def parseCommandParame2Pos(context : PSTContext) : Parser[Option[IGroup]] = command ^^ {
		case _ => None
	}
}