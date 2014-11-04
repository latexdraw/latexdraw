package test.svg.loadSVGFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.interfaces.shape.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;
import net.sf.latexdraw.glib.views.latex.DviPsColors;
import net.sf.latexdraw.parsers.svg.CSSColors;

import org.junit.Test;

public class TestSVGFileActionDepOr extends TestLoadSVGFile {

	@Override
	public String getPathSVGFile() {
		return "src/resources/test/res/testLoad/action-depOr.svg"; //$NON-NLS-1$
	}

	@Override
	public int getNbShapesExpected() {
		return 18;
	}


	@Test public void testShape3() {
		assertTrue(group.getShapeAt(3) instanceof IText);
		final IText txt = (IText) group.getShapeAt(3);
		assertEquals(345.08930858, txt.getPosition().getX(), 0.0001);
		assertEquals(198.570298, txt.getPosition().getY(), 0.0001);
		assertEquals("\\normalsize{needs}", txt.getText()); //$NON-NLS-1$
		assertEquals(DviPsColors.BLACK, txt.getLineColour());
		assertEquals(Math.toRadians(-28.917426838501797), txt.getRotationAngle(), 0.0001);
	}


	@Test public void testShape2() {
		assertTrue(group.getShapeAt(2) instanceof IPolyline);
		final IPolyline line = (IPolyline) group.getShapeAt(2);
		assertEquals(2, line.getNbPoints());
		assertEquals(336.5569380759364, line.getPtAt(0).getX(), 0.0001);
		assertEquals(212.8049502670051, line.getPtAt(0).getY(), 0.0001);
		assertEquals(424.08868741702884, line.getPtAt(1).getX(), 0.0001);
		assertEquals(164.45008642386978, line.getPtAt(1).getY(), 0.0001);
		assertEquals(0., line.getRotationAngle(),0.0001);
		assertEquals(6., line.getThickness(),0.0001);
		assertEquals(CSSColors.INSTANCE.getRGBColour("#909090"), line.getLineColour()); //$NON-NLS-1$
		assertFalse(line.isFilled());
		assertEquals(FillingStyle.NONE, line.getFillingStyle());
		assertFalse(line.hasShadow());
		assertEquals(ArrowStyle.NONE, line.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_ARROW, line.getArrowStyle(1));
		assertEquals(0., line.getArrowAt(1).getArrowInset(), 0.00001);
		assertEquals(2.64583, line.getArrowAt(1).getArrowSizeDim(), 0.00001);
		assertEquals(2.0, line.getArrowAt(1).getArrowSizeNum(), 0.00001);
		assertEquals(1.4, line.getArrowAt(1).getArrowLength(), 0.00001);
	}


	@Test public void testShape1() {
		assertTrue(group.getShapeAt(1) instanceof IText);
		final IText txt = (IText) group.getShapeAt(1);
		assertEquals(177.8671875, txt.getPosition().getX(), 0.01);
		assertEquals(234.328125, txt.getPosition().getY(), 0.01);
		assertEquals("\\normalsize{ActionPasteNode}", txt.getText()); //$NON-NLS-1$
		assertEquals(DviPsColors.BLACK, txt.getLineColour());
		assertEquals(0., txt.getRotationAngle(),0.0001);
	}


	@Test public void testShape0() {
		assertTrue(group.getShapeAt(0) instanceof IRectangle);
		final IRectangle rec = (IRectangle) group.getShapeAt(0);
		assertEquals(160.1796875, rec.getPosition().getX(), 0.01);
		assertEquals(280., rec.getPosition().getY(), 0.01);
		assertEquals(CSSColors.INSTANCE.getRGBColour("#909090"), rec.getLineColour()); //$NON-NLS-1$
		assertEquals(0., rec.getRotationAngle(),0.0001);
		assertEquals(2., rec.getThickness(),0.0001);
		assertEquals(BorderPos.INTO, rec.getBordersPosition());
		assertFalse(rec.hasShadow());
		assertFalse(rec.hasDbleBord());
		assertTrue(rec.isFilled());
		assertEquals(DviPsColors.WHITE, rec.getFillingCol());
		assertEquals(160., rec.getWidth(),0.0001);
		assertEquals(100., rec.getHeight(),0.0001);
	}
}
