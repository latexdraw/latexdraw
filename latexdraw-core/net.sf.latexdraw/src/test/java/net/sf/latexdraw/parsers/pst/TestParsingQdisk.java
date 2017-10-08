package net.sf.latexdraw.parsers.pst;

import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestParsingQdisk extends TestPSTParser {
	@Test
	public void testCoordinatesCm() {
		parser("\\qdisk(35cm,20cm){.5cm}");
		ICircle cir = getShapeAt(0);
		assertEquals(35d * IShape.PPC - 0.5 * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((20d * IShape.PPC - 0.5 * IShape.PPC) * -1d, cir.getPosition().getY(), 0.001);
		assertEquals(0.5 * IShape.PPC * 2d, cir.getWidth(), 0.0000001);
		assertEquals(0.5 * IShape.PPC * 2d, cir.getHeight(), 0.0000001);
	}

	@Test
	public void testLineColourIsFillColour() {
		parser("\\psset{linecolor=green}\\qdisk(35pt,20pt){10pt}");
		ICircle cir = getShapeAt(0);
		assertEquals(DviPsColors.GREEN, cir.getFillingCol());
		assertEquals(DviPsColors.GREEN, cir.getLineColour());
	}

	@Test
	public void testLineStylePlain() {
		parser("\\psset{linestyle=dotted}\\qdisk(35pt,20pt){10pt}");
		ICircle cir = getShapeAt(0);
		assertEquals(LineStyle.SOLID, cir.getLineStyle());
	}

	@Test
	public void testNoDbleBord() {
		parser("\\psset{doubleline=true}\\qdisk(35pt,20pt){10pt}");
		ICircle cir = getShapeAt(0);
		assertFalse(cir.hasDbleBord());
	}

	@Test
	public void testNoShadow() {
		parser("\\psset{shadow=true}\\qdisk(35pt,20pt){10pt}");
		ICircle cir = getShapeAt(0);
		assertFalse(cir.hasShadow());
	}

	@Test
	public void testBorderMustBeInto() {
		parser("\\psset{dimen=middle}\\qdisk(35pt,20pt){10pt}");
		ICircle cir = getShapeAt(0);
		assertEquals(BorderPos.INTO, cir.getBordersPosition());
	}

	@Test
	public void testMustBeFilled() {
		parser("\\qdisk(35pt,20pt){10pt}");
		ICircle cir = getShapeAt(0);
		assertEquals(FillingStyle.PLAIN, cir.getFillingStyle());
	}
}
