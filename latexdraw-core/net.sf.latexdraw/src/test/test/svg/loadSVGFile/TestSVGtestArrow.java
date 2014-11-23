package test.svg.loadSVGFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline;

import org.junit.Test;

public class TestSVGtestArrow extends TestLoadSVGFile {
	@Override
	public String getPathSVGFile() {
		return "src/resources/test/res/testLoad/testArrow.svg"; //$NON-NLS-1$
	}


	@Test public void testArrow() {
		assertTrue(group.getShapeAt(0) instanceof IPolyline);
		final IPolyline pol = (IPolyline) group.getShapeAt(0);
		assertEquals(IArrow.ArrowStyle.RIGHT_ARROW, pol.getArrowAt(1).getArrowStyle());
	}


	@Override
	public int getNbShapesExpected() {
		return 2;
	}
}
