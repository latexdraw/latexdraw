package test.svg.loadSVGFile;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition;
import net.sf.latexdraw.glib.models.interfaces.IText;

public class TestSVGFile3State extends TestLoadSVGFile {
	@Override
	public String getPathSVGFile() {
		return "src/resources/test/res/testLoad/3state.svg";
	}


	public void testShape10() {
		assertTrue(group.getShapeAt(10) instanceof IText);
		final IText txt = (IText) group.getShapeAt(10);
		assertEquals("\\normalsize{Dragging}", txt.getText());
		assertEquals(579., txt.getPosition().getX());
		assertEquals(258., txt.getPosition().getY());
		assertEquals(TextPosition.BOT_LEFT, txt.getTextPosition());
		assertEquals(Color.BLACK, txt.getLineColour());
	}


	public void testShape3() {
		assertTrue(group.getShapeAt(3) instanceof IBezierCurve);
		final IBezierCurve bez = (IBezierCurve) group.getShapeAt(3);
		assertEquals(2, bez.getNbPoints());
		assertEquals(460., bez.getPtAt(0).getX());
		assertEquals(180., bez.getPtAt(0).getY());
		assertEquals(580., bez.getPtAt(1).getX());
		assertEquals(180., bez.getPtAt(1).getY());
		assertEquals(480., bez.getFirstCtrlPtAt(0).getX());
		assertEquals(220., bez.getFirstCtrlPtAt(0).getY());
		assertEquals(440., bez.getSecondCtrlPtAt(0).getX());
		assertEquals(140., bez.getSecondCtrlPtAt(0).getY());
		assertEquals(560., bez.getFirstCtrlPtAt(1).getX());
		assertEquals(220., bez.getFirstCtrlPtAt(1).getY());
		assertEquals(600., bez.getSecondCtrlPtAt(1).getX());
		assertEquals(140., bez.getSecondCtrlPtAt(1).getY());
		assertEquals(0., bez.getRotationAngle());
		assertFalse(bez.hasShadow());
		assertFalse(bez.hasDbleBord());
		assertEquals(2., bez.getThickness());
		assertEquals(LineStyle.SOLID, bez.getLineStyle());
		assertFalse(bez.isClosed());
		assertEquals(BorderPos.INTO, bez.getBordersPosition());
		assertEquals(FillingStyle.NONE, bez.getFillingStyle());
		assertEquals(ArrowStyle.NONE, bez.getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_ARROW, bez.getArrowStyle(1));
		assertEquals(0., bez.getArrowAt(1).getArrowInset(), 0.00001);
		assertEquals(5.65, bez.getArrowAt(1).getArrowSizeDim(), 0.01);
		assertEquals(2.0, bez.getArrowAt(1).getArrowSizeNum(), 0.00001);
		assertEquals(1.4, bez.getArrowAt(1).getArrowLength(), 0.00001);
	}


	public void testShape0() {
		assertTrue(group.getShapeAt(0) instanceof IGroup);
		final IGroup gp = (IGroup) group.getShapeAt(0);
		assertEquals(2, gp.size());
		final ICircle circle = (ICircle) gp.getShapeAt(0);
		final IGroup gp2 = (IGroup) gp.getShapeAt(1);
		assertEquals(2, gp2.size());
		final IText txt1 = (IText) gp2.getShapeAt(0);
		final IText txt2 = (IText) gp2.getShapeAt(1);

		assertEquals(Color.BLACK, circle.getLineColour());
		assertTrue(circle.isFilled());
		assertEquals(Color.WHITE, circle.getFillingCol());
		assertEquals(60., circle.getWidth());
		assertEquals(580., circle.getPosition().getX());
		assertEquals(180., circle.getPosition().getY());
		assertEquals(2., circle.getThickness());
		assertEquals(LineStyle.SOLID, circle.getLineStyle());
		assertEquals(BorderPos.INTO, circle.getBordersPosition());
		assertEquals(0., circle.getRotationAngle());
		assertFalse(circle.hasShadow());
		assertFalse(circle.hasDbleBord());
		assertEquals("\\normalsize{State}", txt1.getText());
		assertEquals("\\normalsize{2}", txt2.getText());
		assertEquals(Color.BLACK, txt1.getLineColour());
		assertEquals(Color.BLACK, txt2.getLineColour());
		assertEquals(591.7, txt1.getPosition().getX(), 0.1);
		assertEquals(146.1, txt1.getPosition().getY(), 0.1);
		assertEquals(605.7, txt2.getPosition().getX(), 0.1);
		assertEquals(166.1, txt2.getPosition().getY(), 0.1);
		assertEquals(TextPosition.BOT_LEFT, txt1.getTextPosition());
		assertEquals(TextPosition.BOT_LEFT, txt2.getTextPosition());
		assertEquals(0., txt1.getRotationAngle());
		assertEquals(0., txt2.getRotationAngle());
	}


	@Override
	public int getNbShapesExpected() {
		return 17;
	}
}
