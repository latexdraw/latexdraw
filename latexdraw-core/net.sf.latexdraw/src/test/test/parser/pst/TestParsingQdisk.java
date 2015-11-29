package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.glib.views.latex.DviPsColors;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingQdisk extends TestPSTParser {
	@Test
	public void testCoordinatesCm() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\" + getCommandName() + "(35cm,20cm){.5cm}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(35. * IShape.PPC - .5 * IShape.PPC, cir.getPosition().getX(), 0.001);
		assertEquals((20. * IShape.PPC - .5 * IShape.PPC) * -1., cir.getPosition().getY(), 0.001);
		assertEquals(.5 * IShape.PPC * 2., cir.getWidth(), 0.0000001);
		assertEquals(.5 * IShape.PPC * 2., cir.getHeight(), 0.0000001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testLineColourIsFillColour() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\psset{linecolor=green}\\" + getCommandName() + "(35pt,20pt){10pt}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(DviPsColors.GREEN, cir.getFillingCol());
		assertEquals(DviPsColors.GREEN, cir.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testLineStylePlain() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\psset{linestyle=dotted}\\" + getCommandName() + "(35pt,20pt){10pt}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(LineStyle.SOLID, cir.getLineStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testNoDbleBord() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\psset{doubleline=true}\\" + getCommandName() + "(35pt,20pt){10pt}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(cir.hasDbleBord());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testNoShadow() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\psset{shadow=true}\\" + getCommandName() + "(35pt,20pt){10pt}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(cir.hasShadow());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testBorderMustBeInto() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\psset{dimen=middle}\\" + getCommandName() + "(35pt,20pt){10pt}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(BorderPos.INTO, cir.getBordersPosition());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testMustBeFilled() throws ParseException {
		ICircle cir = (ICircle)parser.parsePSTCode("\\" + getCommandName() + "(35pt,20pt){10pt}").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(FillingStyle.PLAIN, cir.getFillingStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test(expected = ParseException.class)
	public void testMustNotHaveParam() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient]" + getBasicCoordinates()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test(expected = ParseException.class)
	public void testMustNotHaveCoordinate() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "{1}"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public String getCommandName() {
		return "qdisk"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return "(,){1}"; //$NON-NLS-1$
	}
}
