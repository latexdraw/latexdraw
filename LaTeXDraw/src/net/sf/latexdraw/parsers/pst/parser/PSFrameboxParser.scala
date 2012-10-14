package net.sf.latexdraw.parsers.pst.parser

trait PSFrameboxParser extends PSTAbstractParser with PSTBracketBlockParser {
	def parsePsFrameboxCmds(ctx:PSTContext) : Parser[Unit] = parsePsFramebox(ctx)

	private def parsePsFramebox(ctx:PSTContext) : Parser[Unit] =
		("\\psframebox" | "\\psframebox*") ~ opt(parseSquaredBracket(ctx)) ~ parseBracket(ctx) ^^ {
		case nameCmd ~ param ~ block =>
			val paramStr = if(param.isDefined) "[" + param.get + "]" else ""
			ctx.textParsed += nameCmd + paramStr + "{" + block + "}"
	}
}
