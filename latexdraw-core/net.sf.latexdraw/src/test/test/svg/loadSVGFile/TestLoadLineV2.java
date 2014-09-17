package test.svg.loadSVGFile;

import static org.junit.Assert.*;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.shape.IArrow;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.LineStyle;
import net.sf.latexdraw.glib.views.latex.DviPsColors;

import org.junit.Test;

public class TestLoadLineV2 extends TestLoadSVGFile {

	@Override
	public String getPathSVGFile() {
		return "src/resources/test/res/testLoad/testLine.svg"; //$NON-NLS-1$
	}

	@Override
	public int getNbShapesExpected() {
		return 1;
	}


	public IPolyline getLine() { return (IPolyline)group.getShapeAt(0); }


	@Test public void testPoints() {
		final IPolyline line = getLine();
		assertEquals(2, line.getNbPoints());
		assertEquals(140.0, line.getPtAt(0).getX(), 0.001);
		assertEquals(260.0, line.getPtAt(0).getY(), 0.001);
		assertEquals(320.0, line.getPtAt(1).getX(), 0.001);
		assertEquals(40.0, line.getPtAt(1).getY(), 0.001);
	}


	@Test public void testLine() {
		final IPolyline line = getLine();
		assertEquals(5, line.getThickness(), 0.01);
		assertEquals(new Color(209, 169, 169), line.getLineColour());
		assertEquals(LineStyle.DASHED, line.getLineStyle());
	}

	@Test public void testShadow() {
		final IPolyline line = getLine();
		assertTrue(line.hasShadow());
		assertEquals(DviPsColors.INSTANCE.convertHTML2rgb("#e9e937"), line.getShadowCol()); //$NON-NLS-1$
		assertEquals(90.0, Math.toDegrees(line.getShadowAngle()), 0.00001);
		assertEquals(10.0, line.getShadowSize(), 0.0001);
	}

	@Test public void testArrows() {
		final IPolyline line = getLine();
		assertEquals(IArrow.ArrowStyle.DISK_IN, line.getArrowStyle(0));
		assertEquals(IArrow.ArrowStyle.RIGHT_ARROW, line.getArrowStyle(1));
		assertEquals(4.0, line.getDotSizeNum(), 0.0001);
		assertEquals(4.53, line.getDotSizeDim(), 0.0001);
		assertEquals(2.4, line.getArrowLength(), 0.0001);
		assertEquals(0.1, line.getArrowInset(), 0.0001);
		assertEquals(3.0, line.getArrowSizeNum(), 0.0001);
		assertEquals(2.65, line.getArrowSizeDim(), 0.01);
	}

	@Test public void testDbleBorder() {
		final IPolyline line = getLine();
		assertTrue(line.hasDbleBord());
		assertEquals(new Color(224, 197, 227), line.getDbleBordCol());
		assertEquals(6, line.getDbleBordSep(), 0.001);
	}
}
