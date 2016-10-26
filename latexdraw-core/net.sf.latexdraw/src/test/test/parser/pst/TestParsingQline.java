package test.parser.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingQline extends TestPSTParser {
	@Test
	public void testCoordinatesCm() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\" + getCommandName() + "(35cm,20cm)(11.12cm,-2cm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, line.getNbPoints());
		assertEquals(35. * IShape.PPC, line.getPtAt(0).getX(), 0.001);
		assertEquals(20. * IShape.PPC * -1., line.getPtAt(0).getY(), 0.001);
		assertEquals(11.12 * IShape.PPC, line.getPtAt(1).getX(), 0.001);
		assertEquals(-2. * IShape.PPC * -1., line.getPtAt(1).getY(), 0.001);
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testNoDbleBord() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psset{doubleline=true}\\" + getCommandName() + "(35cm,20cm)(11.12cm,-2cm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(line.hasDbleBord());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testNoShadow() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\psset{shadow=true}\\" + getCommandName() + "(35cm,20cm)(11.12cm,-2cm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(line.hasShadow());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testMustNotBeFilled() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\" + getCommandName() + "(35cm,20cm)(11.12cm,-2cm)").get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(FillingStyle.NONE, line.getFillingStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test(expected = ParseException.class)
	public void testMustNotHaveParam() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient]" + getBasicCoordinates()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test(expected = ParseException.class)
	public void testMustHaveTwoCoordinate() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + "(35cm,20cm)"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public String getCommandName() {
		return "qline"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return "(,){1}"; //$NON-NLS-1$
	}
}
