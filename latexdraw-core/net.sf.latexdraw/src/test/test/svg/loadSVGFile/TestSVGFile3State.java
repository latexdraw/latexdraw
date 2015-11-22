package test.svg.loadSVGFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import net.sf.latexdraw.glib.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;
import net.sf.latexdraw.glib.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.TextPosition;
import net.sf.latexdraw.glib.views.latex.DviPsColors;

import org.junit.Test;

public class TestSVGFile3State extends TestLoadSVGFile {
	@Override
	public String getPathSVGFile() {
		return "src/resources/test/res/testLoad/3state.svg"; //$NON-NLS-1$
	}


	@Test public void testShape10() {
		assertTrue(group.getShapeAt(10) instanceof IText);
		final IText txt = (IText) group.getShapeAt(10);
		assertEquals("\\normalsize{Dragging}", txt.getText()); //$NON-NLS-1$
		assertEquals(579., txt.getPosition().getX(),0.0001);
		assertEquals(258., txt.getPosition().getY(),0.0001);
		assertEquals(TextPosition.BOT_LEFT, txt.getTextPosition());
		assertEquals(DviPsColors.BLACK, txt.getLineColour());
	}


	@Test public void testShape3() {
		assertTrue(group.getShapeAt(3) instanceof IBezierCurve);
		final IBezierCurve bez = (IBezierCurve) group.getShapeAt(3);
		assertEquals(2, bez.getNbPoints());
		assertEquals(460., bez.getPtAt(0).getX(),0.0001);
		assertEquals(180., bez.getPtAt(0).getY(),0.0001);
		assertEquals(580., bez.getPtAt(1).getX(),0.0001);
		assertEquals(180., bez.getPtAt(1).getY(),0.0001);
		assertEquals(480., bez.getFirstCtrlPtAt(0).getX(),0.0001);
		assertEquals(220., bez.getFirstCtrlPtAt(0).getY(),0.0001);
		assertEquals(440., bez.getSecondCtrlPtAt(0).getX(),0.0001);
		assertEquals(140., bez.getSecondCtrlPtAt(0).getY(),0.0001);
		assertEquals(560., bez.getFirstCtrlPtAt(1).getX(),0.0001);
		assertEquals(220., bez.getFirstCtrlPtAt(1).getY(),0.0001);
		assertEquals(600., bez.getSecondCtrlPtAt(1).getX(),0.0001);
		assertEquals(140., bez.getSecondCtrlPtAt(1).getY(),0.0001);
		assertEquals(0., bez.getRotationAngle(),0.0001);
		assertFalse(bez.hasShadow());
		assertFalse(bez.hasDbleBord());
		assertEquals(2., bez.getThickness(),0.0001);
		assertEquals(LineStyle.SOLID, bez.getLineStyle());
		assertFalse(bez.isClosed());
		assertEquals(BorderPos.INTO, bez.getBordersPosition());
		assertEquals(FillingStyle.NONE, bez.getFillingStyle());
		assertEquals(ArrowStyle.NONE, bez.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_ARROW, bez.getArrowStyle(-1));
		assertEquals(0., bez.getArrowAt(-1).getArrowInset(), 0.00001);
		assertEquals(5.65, bez.getArrowAt(-1).getArrowSizeDim(), 0.01);
		assertEquals(2.0, bez.getArrowAt(-1).getArrowSizeNum(), 0.00001);
		assertEquals(1.4, bez.getArrowAt(-1).getArrowLength(), 0.00001);
	}


	@Test public void testShape0() {
		assertTrue(group.getShapeAt(0) instanceof IGroup);
		final IGroup gp = (IGroup) group.getShapeAt(0);
		assertEquals(2, gp.size());
		final ICircle circle = (ICircle) gp.getShapeAt(0);
		final IGroup gp2 = (IGroup) gp.getShapeAt(1);
		assertEquals(2, gp2.size());
		final IText txt1 = (IText) gp2.getShapeAt(0);
		final IText txt2 = (IText) gp2.getShapeAt(1);

		assertEquals(DviPsColors.BLACK, circle.getLineColour());
		assertTrue(circle.isFilled());
		assertEquals(DviPsColors.WHITE, circle.getFillingCol());
		assertEquals(60., circle.getWidth(),0.0001);
		assertEquals(580., circle.getPosition().getX(),0.0001);
		assertEquals(180., circle.getPosition().getY(),0.0001);
		assertEquals(2., circle.getThickness(),0.0001);
		assertEquals(LineStyle.SOLID, circle.getLineStyle());
		assertEquals(BorderPos.INTO, circle.getBordersPosition());
		assertEquals(0., circle.getRotationAngle(),0.0001);
		assertFalse(circle.hasShadow());
		assertFalse(circle.hasDbleBord());
		assertEquals("\\normalsize{State}", txt1.getText()); //$NON-NLS-1$
		assertEquals("\\normalsize{2}", txt2.getText()); //$NON-NLS-1$
		assertEquals(DviPsColors.BLACK, txt1.getLineColour());
		assertEquals(DviPsColors.BLACK, txt2.getLineColour());
		assertEquals(591.7, txt1.getPosition().getX(), 0.1);
		assertEquals(146.1, txt1.getPosition().getY(), 0.1);
		assertEquals(605.7, txt2.getPosition().getX(), 0.1);
		assertEquals(166.1, txt2.getPosition().getY(), 0.1);
		assertEquals(TextPosition.BOT_LEFT, txt1.getTextPosition());
		assertEquals(TextPosition.BOT_LEFT, txt2.getTextPosition());
		assertEquals(0., txt1.getRotationAngle(),0.0001);
		assertEquals(0., txt2.getRotationAngle(),0.0001);
	}


	@Override
	public int getNbShapesExpected() {
		return 17;
	}
}
