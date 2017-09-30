package net.sf.latexdraw.parsers.pst2;

import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestParsingQline extends TestPSTParser {
	@Test
	public void testCoordinatesCm() {
		parser("\\" + getCommandName() + "(35cm,20cm)(11.12cm,-2cm)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(2, line.getNbPoints());
		assertEquals(35d * IShape.PPC, line.getPtAt(0).getX(), 0.001);
		assertEquals(20d * IShape.PPC * -1d, line.getPtAt(0).getY(), 0.001);
		assertEquals(11.12 * IShape.PPC, line.getPtAt(1).getX(), 0.001);
		assertEquals(-2d * IShape.PPC * -1d, line.getPtAt(1).getY(), 0.001);
	}

	@Test
	public void testNoDbleBord() {
		parser("\\psset{doubleline=true}\\" + getCommandName() + "(35cm,20cm)(11.12cm,-2cm)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertFalse(line.hasDbleBord());
	}

	@Test
	public void testNoShadow() {
		parser("\\psset{shadow=true}\\" + getCommandName() + "(35cm,20cm)(11.12cm,-2cm)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertFalse(line.hasShadow());
	}

	@Test
	public void testMustNotBeFilled() {
		parser("\\" + getCommandName() + "(35cm,20cm)(11.12cm,-2cm)");
		final IPolyline line = (IPolyline) listener.getShapes().get(0);
		assertEquals(FillingStyle.NONE, line.getFillingStyle());
	}

	@Override
	public String getCommandName() {
		return "qline";
	}

	@Override
	public String getBasicCoordinates() {
		return "(,){1}";
	}
}
