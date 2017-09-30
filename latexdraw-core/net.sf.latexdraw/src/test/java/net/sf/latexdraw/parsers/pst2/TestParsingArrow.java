package net.sf.latexdraw.parsers.pst2;

import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestParsingArrow extends TestPSTParser {
	@Test
	public void testParamArrowScaleDimDim() {
		parser("\\psline[arrows=<->, arrowscale=0.1 cm 3](1,1)(2,2)");
	}

	@Test
	public void testParamArrowScaleDim() {
		parser("\\psline[arrows=<->, arrowscale=2.55](1,1)");
	}

	@Test
	public void testParamArrowtRBracketLength() {
		parser("\\psline[arrows=<->, rbracketlength=2.55](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(2.55, line.getRBracketNum(), 0.0001);
	}

	@Test
	public void testParamArrowBracketLength() {
		parser("\\psline[arrows=<->, bracketlength=2.55](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(2.55, line.getBracketNum(), 0.0001);
	}

	@Test
	public void testParamArrowInset() {
		parser("\\psline[arrows=<->, arrowinset=2.5](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(2.5, line.getArrowInset(), 0.0001);
	}

	@Test
	public void testParamArrowlength() {
		parser("\\psline[arrows=<->, arrowlength=1.5](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(1.5, line.getArrowLength(), 0.0001);
	}

	@Test
	public void testParamArrowtbarDimNum() {
		parser("\\psline[arrows=<->, tbarsize=1.5cm 3](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(1.5 * IShape.PPC, line.getTBarSizeDim(), 0.0001);
		assertEquals(3d, line.getTBarSizeNum(), 0.0001);
	}

	@Test
	public void testParamArrowsizeDimNum() {
		parser("\\psline[arrows=<->, arrowsize=1.5cm 3](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(1.5 * IShape.PPC, line.getArrowSizeDim(), 0.0001);
		assertEquals(3d, line.getArrowSizeNum(), 0.0001);
	}

	@Test
	public void testParamArrowsizeDim() {
		parser("\\psline[arrows=<->, arrowsize=2cm](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(2d * IShape.PPC, line.getArrowSizeDim(), 0.0001);
		assertEquals(0d, line.getArrowSizeNum(), 0.0001);
	}

	@Test
	public void testParamArrowsArrows() {
		parser("\\psline[arrows=<->]{-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testParamArrowsRRBracker() {
		parser("\\psline[arrows=)-](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testParamArrowsBarEnd() {
		parser("\\psline[arrows=|*-](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.BAR_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testParamArrowsRLBracker() {
		parser("\\psline[arrows=(-](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.LEFT_ROUND_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testParamArrowsSLBracket() {
		parser("\\psline[arrows=[-](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testParamArrows() {
		parser("\\psline[arrows=<->](1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.LEFT_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(1));
	}

	@Test
	public void testNoneC() {
		parser("\\psline{-C}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.SQUARE_END, line.getArrowStyle(1));
	}

	@Test
	public void testCNone() {
		parser("\\psline{C-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.SQUARE_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNonecc() {
		parser("\\psline{-cc}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.ROUND_IN, line.getArrowStyle(1));
	}

	@Test
	public void testccNone() {
		parser("\\psline{cc-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.ROUND_IN, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNonec() {
		parser("\\psline{-c}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.ROUND_END, line.getArrowStyle(1));
	}

	@Test
	public void testcNone() {
		parser("\\psline{c-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.ROUND_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNoneDiskIn() {
		parser("\\psline{-**}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.DISK_IN, line.getArrowStyle(1));
	}

	@Test
	public void testDiskInNone() {
		parser("\\psline{**-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.DISK_IN, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNoneCircleIn() {
		parser("\\psline{-oo}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.CIRCLE_IN, line.getArrowStyle(1));
	}

	@Test
	public void testCircleInNone() {
		parser("\\psline{oo-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.CIRCLE_IN, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNoneDiskEnd() {
		parser("\\psline{-*}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.DISK_END, line.getArrowStyle(1));
	}

	@Test
	public void testDiskEndNone() {
		parser("\\psline{*-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.DISK_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNoneCircleEnd() {
		parser("\\psline{-o}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.CIRCLE_END, line.getArrowStyle(1));
	}

	@Test
	public void testCircleEndNone() {
		parser("\\psline{o-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.CIRCLE_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNoneRightRoundBracket() {
		parser("\\psline{-(}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.LEFT_ROUND_BRACKET, line.getArrowStyle(1));
	}

	@Test
	public void testLeftRoundBracketNone() {
		parser("\\psline{)-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNoneLeftRoundBracket() {
		parser("\\psline{-)}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_ROUND_BRACKET, line.getArrowStyle(1));
	}

	@Test
	public void testRoundRightBracketNone() {
		parser("\\psline{(-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.LEFT_ROUND_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNoneRightSquareBracket() {
		parser("\\psline{-[}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET, line.getArrowStyle(1));
	}

	@Test
	public void testSquareLeftBracketNone() {
		parser("\\psline{]-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNoneLeftSquareBracket() {
		parser("\\psline{-]}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_SQUARE_BRACKET, line.getArrowStyle(1));
	}

	@Test
	public void testSquareRightBracketTbarEnd() {
		parser("\\psline{[-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNoneTbarEnd() {
		parser("\\psline{-|*}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.BAR_END, line.getArrowStyle(1));
	}

	@Test
	public void testTbarEndNone() {
		parser("\\psline{|*-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.BAR_END, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testNoneTbar() {
		parser("\\psline{-|}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.BAR_IN, line.getArrowStyle(1));
	}

	@Test
	public void testTbarNone() {
		parser("\\psline{|-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.BAR_IN, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testDbleArrowLeftDbleArrowRight() {
		parser("\\psline{<<->>}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.LEFT_DBLE_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW, line.getArrowStyle(1));
	}

	@Test
	public void testNoneNone() {
		parser("\\psline{-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testArrowheadRightArrowheadLeft() {
		parser("\\psline{>-<}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.LEFT_ARROW, line.getArrowStyle(1));
	}

	@Test
	public void testNoneArrowheadRight() {
		parser("\\psline{->}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(1));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
	}

	@Test
	public void testNoneArrowheadLeft() {
		parser("\\psline{-<}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.LEFT_ARROW, line.getArrowStyle(1));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
	}

	@Test
	public void testArrowheadRightNone() {
		parser("\\psline{>-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
	}

	@Test
	public void testArrowheadLeftNone() {
		parser("\\psline{<-}(1,1)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(ArrowStyle.LEFT_ARROW, line.getArrowStyle(0));
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(1));
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
