package test.svg.loadSVGFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;
import net.sf.latexdraw.glib.views.latex.DviPsColors;

import org.junit.Test;

public class TestSVGFileDiracs extends TestLoadSVGFile {

	@Override
	public String getPathSVGFile() {
		return "src/resources/test/res/testLoad/Diracs.svg"; //$NON-NLS-1$
	}

	@Override
	public int getNbShapesExpected() {
		return 22;
	}


	@Test public void testShape0() {
		assertTrue(group.getShapeAt(0) instanceof IPolyline);
		IPolyline pol = (IPolyline) group.getShapeAt(0);
		assertEquals(DviPsColors.BLACK, pol.getLineColour());
		assertEquals(2, pol.getNbPoints());
		assertEquals(50., pol.getPtAt(0).getX(),0.0001);
		assertEquals(145., pol.getPtAt(0).getY(),0.0001);
		assertEquals(410., pol.getPtAt(1).getX(),0.0001);
		assertEquals(145., pol.getPtAt(1).getY(),0.0001);
		assertEquals(0., pol.getRotationAngle(),0.0001);
		assertEquals(1., pol.getThickness(),0.0001);
		assertEquals(ArrowStyle.NONE, pol.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_ARROW, pol.getArrowStyle(1));
		assertEquals(0.4, pol.getArrowAt(1).getArrowInset(), 0.00001);
		assertEquals(2.64583, pol.getArrowAt(1).getArrowSizeDim(), 0.00001);
		assertEquals(2.0, pol.getArrowAt(1).getArrowSizeNum(), 0.00001);
		assertEquals(1.4, pol.getArrowAt(1).getArrowLength(), 0.00001);
	}


	@Test public void testShape2() {
		assertTrue(group.getShapeAt(2) instanceof IGroup);
		IGroup gp = (IGroup) group.getShapeAt(2);
		assertEquals(7, gp.size());
	}


	@Test public void testShape3() {
		assertTrue(group.getShapeAt(3) instanceof IText);
		IText txt = (IText) group.getShapeAt(3);
		assertEquals("\\normalsize{$t$}", txt.getText()); //$NON-NLS-1$
		assertEquals(DviPsColors.BLACK, txt.getLineColour());
		assertEquals(405., txt.getPosition().getX(), 0.000001);
		assertEquals(160., txt.getPosition().getY(), 0.000001);
	}


	@Test public void testShape6() {
		assertTrue(group.getShapeAt(6) instanceof IText);
		IText txt = (IText) group.getShapeAt(6);
		assertEquals("\\normalsize{$t_7$}", txt.getText()); //$NON-NLS-1$
		assertEquals(DviPsColors.BLACK, txt.getLineColour());
		assertEquals(365., txt.getPosition().getX(), 0.000001);
		assertEquals(160., txt.getPosition().getY(), 0.000001);
	}


	@Test public void testShape7() {
		assertTrue(group.getShapeAt(7) instanceof IText);
		IText txt = (IText) group.getShapeAt(7);
		assertEquals("\\normalsize{$\\cdots\\cdots$}", txt.getText()); //$NON-NLS-1$
		assertEquals(DviPsColors.BLACK, txt.getLineColour());
		assertEquals(365., txt.getPosition().getX(), 0.000001);
		assertEquals(125., txt.getPosition().getY(), 0.000001);
	}
}
