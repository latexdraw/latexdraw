package net.sf.latexdraw.parsers.pst2;

import java.util.List;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestParsingPsdots extends TestPSTParser {
	@Test
	public void test2Points() {
		parser("\\psdots(35.5,50.5)(2,2)");
		final List<IShape> group = listener.getShapes();
		assertEquals(2, group.size());
		assertTrue(group.get(0) instanceof IDot);
		assertTrue(group.get(1) instanceof IDot);
		assertEquals(35.5 * IShape.PPC, group.get(0).getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, group.get(0).getPtAt(0).getY(), 0.0001);
		assertEquals(2d * IShape.PPC, group.get(1).getPtAt(0).getX(), 0.0001);
		assertEquals(-2d * IShape.PPC, group.get(1).getPtAt(0).getY(), 0.0001);
	}

	@Theory
	public void testDotStyleo(final DotStyle style) {
		parser("\\psdots[dotstyle=" + style.getPSTToken() + "](1,1)(2,2)");
		final List<IShape> group = listener.getShapes();
		assertEquals(2, group.size());
		assertEquals(style, ((IDot) group.get(0)).getDotStyle());
		assertEquals(style, ((IDot) group.get(1)).getDotStyle());
	}

	@Test
	public void testDotStyleDot() {
		parser("\\psdots" + "[dotstyle=*](1,1)(2,2)");
		final List<IShape> group = listener.getShapes();
		assertEquals(2, group.size());
		assertEquals(DotStyle.DOT, ((IDot) group.get(0)).getDotStyle());
		assertEquals(DotStyle.DOT, ((IDot) group.get(1)).getDotStyle());
	}

	@Test
	public void testNoDotStyle() {
		parser("\\psdots(1,1)(2,2)");
		final List<IShape> group = listener.getShapes();
		assertEquals(2, group.size());
		assertEquals(DotStyle.DOT, ((IDot) group.get(0)).getDotStyle());
		assertEquals(DotStyle.DOT, ((IDot) group.get(1)).getDotStyle());
	}

	@Test
	public void test1Coordinates() {
		parser("\\psdots" + "(5,10)");
		final List<IShape> group = listener.getShapes();
		assertEquals(1, group.size());
		assertEquals(5d * IShape.PPC, group.get(0).getPtAt(0).getX(), 0.0001);
		assertEquals(-10d * IShape.PPC, group.get(0).getPtAt(0).getY(), 0.0001);
	}

	@Test
	public void testFloatSigns() {
		parser("\\psdots(+++35.5,--50.5)(+-++35.5,---50.5)");
		final List<IShape> group = listener.getShapes();
		assertEquals(35.5 * IShape.PPC, group.get(0).getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, group.get(0).getPtAt(0).getY(), 0.0001);
		assertEquals(-35.5 * IShape.PPC, group.get(1).getPtAt(0).getX(), 0.0001);
		assertEquals(50.5 * IShape.PPC, group.get(1).getPtAt(0).getY(), 0.0001);
	}

	@Test
	public void testStarLineColourIsFillingColour() {
		parser("\\psdots*[" + "linecolor=green, dotstyle=o](1,1)(2,2)");
		final List<IShape> group = listener.getShapes();
		assertEquals(DviPsColors.GREEN, group.get(0).getFillingCol());
		assertEquals(DviPsColors.GREEN, group.get(0).getLineColour());
		assertEquals(DviPsColors.GREEN, group.get(1).getFillingCol());
		assertEquals(DviPsColors.GREEN, group.get(1).getLineColour());
	}

	@Test
	public void testCoordinatesFloat2() {
		parser("\\psdots(35.5,50.5)");
		final IDot dot = (IDot) listener.getShapes().get(0);
		assertEquals(35.5 * IShape.PPC, dot.getPtAt(0).getX(), 0.0001);
		assertEquals(-50.5 * IShape.PPC, dot.getPtAt(0).getY(), 0.0001);
	}
}
