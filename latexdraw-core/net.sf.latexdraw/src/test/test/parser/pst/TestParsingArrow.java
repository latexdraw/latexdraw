package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow.ArrowStyle;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingArrow extends TestPSTParser {
	@Test
	public void testParamArrowScaleDimDim() throws ParseException {
		parser.parsePSTCode("\\psline[arrows=<->, arrowscale=0.1 cm 3](1,1)");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowScaleDim() throws ParseException {
		parser.parsePSTCode("\\psline[arrows=<->, arrowscale=2.55](1,1)");
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowtBracketLength() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=<->, rbracketlength=2.55](1,1)").get().getShapeAt(0);
		assertEquals(2.55, line.getRBracketNum(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowBracketLength() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=<->, bracketlength=2.55](1,1)").get().getShapeAt(0);
		assertEquals(2.55, line.getBracketNum(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowInset() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=<->, arrowinset=2.5](1,1)").get().getShapeAt(0);
		assertEquals(2.5, line.getArrowInset(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowlength() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=<->, arrowlength=1.5](1,1)").get().getShapeAt(0);
		assertEquals(1.5, line.getArrowLength(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowtbarDimNum() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=<->, tbarsize=1.5cm 3](1,1)").get().getShapeAt(0);
		assertEquals(1.5*IShape.PPC, line.getTBarSizeDim(), 0.0001);
		assertEquals(3., line.getTBarSizeNum(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamtbarsizeDim() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=<->, tbarsize=2cm](1,1)").get().getShapeAt(0);
		assertEquals(2.*IShape.PPC, line.getTBarSizeDim(), 0.0001);
		assertEquals(0., line.getTBarSizeNum(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowsizeDimNum() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=<->, arrowsize=1.5cm 3](1,1)").get().getShapeAt(0);
		assertEquals(1.5*IShape.PPC, line.getArrowSizeDim(), 0.0001);
		assertEquals(3., line.getArrowSizeNum(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowsizeDim() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=<->, arrowsize=2cm](1,1)").get().getShapeAt(0);
		assertEquals(2.*IShape.PPC, line.getArrowSizeDim(), 0.0001);
		assertEquals(0., line.getArrowSizeNum(), 0.0001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowsArrows() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=<->]{-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowsRRBracker() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=)-](1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowsBarEnd() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=|*-](1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.BAR_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowsRLBracker() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=(-](1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.LEFT_ROUND_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrowsSLBracket() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=[-](1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testParamArrows() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline[arrows=<->](1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.LEFT_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneC() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-C}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.SQUARE_END, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{C-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.SQUARE_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNonecc() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-cc}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.ROUND_IN, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testccNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{cc-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.ROUND_IN, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNonec() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-c}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.ROUND_END, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testcNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{c-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.ROUND_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneDiskIn() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-**}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.DISK_IN, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDiskInNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{**-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.DISK_IN, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneCircleIn() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-oo}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.CIRCLE_IN, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCircleInNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{oo-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.CIRCLE_IN, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneDiskEnd() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-*}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.DISK_END, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDiskEndNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{*-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.DISK_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneCircleEnd() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-o}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.CIRCLE_END, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testCircleEndNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{o-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.CIRCLE_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneRightRoundBracket() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-(}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.LEFT_ROUND_BRACKET, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testLeftRoundBracketNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{)-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneLeftRoundBracket() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-)}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testRoundRightBracketNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{(-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.LEFT_ROUND_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneRightSquareBracket() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-[}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testSquareLeftBracketNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{]-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneLeftSquareBracket() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-]}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testSquareRightBracketTbarEnd() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{[-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneTbarEnd() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-|*}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.BAR_END, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testTbarEndNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{|*-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.BAR_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneTbar() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-|}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.BAR_IN, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testTbarNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{|-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.BAR_IN, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testDbleArrowLeftDbleArrowRight() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{<<->>}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.LEFT_DBLE_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testArrowheadRightArrowheadLeft() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{>-<}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.LEFT_ARROW, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneArrowheadRight() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{->}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(1));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testNoneArrowheadLeft() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{-<}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.LEFT_ARROW, line.getArrowStyle(1));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testArrowheadRightNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{>-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test
	public void testArrowheadLeftNone() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psline{<-}(1,1)").get().getShapeAt(0);
		assertEquals(ArrowStyle.LEFT_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	public String getCommandName() {
		return "";
	}

	@Override
	public String getBasicCoordinates() {
		return "";
	}
}
