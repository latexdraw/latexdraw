package test.svg.loadSVGFile;

import static org.junit.Assert.*;
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolygon;

import org.junit.Test;

public class TestSVGtestArrow extends TestLoadSVGFile {
	@Override
	public String getPathSVGFile() {
		return "src/resources/test/res/testLoad/testArrow.svg";
	}


	@Test public void testArrow() {
		assertTrue(group.getShapeAt(0) instanceof IPolygon);
		final IPolygon pol = (IPolygon) group.getShapeAt(0);
		assertEquals(IArrow.ArrowStyle.RIGHT_ARROW, pol.getArrowAt(1).getArrowStyle());
	}


	@Override
	public int getNbShapesExpected() {
		return 1;
	}
}
