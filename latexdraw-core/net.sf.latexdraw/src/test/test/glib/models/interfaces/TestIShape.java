package test.glib.models.interfaces;

import java.awt.Color;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IArrow;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;

import org.junit.Test;

public abstract class TestIShape<T extends IShape> extends TestCase {
	public T shape;

	public T shape2;

	@Test
	public abstract void testIsTypeOf();
	

	@Test
	public void testGetShadowGap() {
		final IPoint gc = shape.getGravityCentre();
		shape.setShadowAngle(Math.PI/4.);
		shape.setShadowSize(10);
		IPoint trans = shape.getShadowGap();

		assertNotNull(trans);

		if(shape.isShadowable()) {
			IPoint pt = DrawingTK.getFactory().createPoint(gc.getX()+10, gc.getY());
			pt = pt.rotatePoint(gc, Math.PI/4.);
			assertEquals(pt.getX()-gc.getX(), trans.getX());
			assertEquals(gc.getY()-pt.getY(), trans.getY());

			shape.setShadowAngle(0.);
			trans = shape.getShadowGap();
			pt = DrawingTK.getFactory().createPoint(gc.getX()+10, gc.getY());
			assertEquals(pt.getX()-gc.getX(), trans.getX());
			assertEquals(gc.getY()-pt.getY(), trans.getY());

			shape.setShadowAngle(2.*Math.PI);
			trans = shape.getShadowGap();
			pt = DrawingTK.getFactory().createPoint(gc.getX()+10, gc.getY());
			assertEquals(pt.getX()-gc.getX(), trans.getX());
			assertEquals(gc.getY()-pt.getY(), trans.getY());
		} else {
			assertEquals(0., trans.getX());
			assertEquals(0., trans.getY());
		}
	}


	@Test
	public void testGetPoints() {
		assertNotNull(shape.getPoints());
	}



	@Test
	public void testGetNbPoints() {
		IPoint pt = DrawingTK.getFactory().createPoint();

		assertEquals(shape.getPoints().size(), shape.getNbPoints());
		shape.getPoints().add(pt);
		assertEquals(shape.getPoints().size(), shape.getNbPoints());
		shape.getPoints().remove(pt);
		assertEquals(shape.getPoints().size(), shape.getNbPoints());
	}


	@Test
	public void testGetPtAt() {
		if(shape.getPoints().isEmpty()) {
			shape.getPoints().add(DrawingTK.getFactory().createPoint());
			shape.getPoints().add(DrawingTK.getFactory().createPoint());
		}

		for(int i=0; i<shape.getNbPoints(); i++)
			assertEquals(shape.getPoints().get(i), shape.getPtAt(i));
	}






	@Test
	public void testCopy() {
		if(shape2.isBordersMovable())
			shape2.setBordersPosition(BorderPos.MID);
		if(shape2.isLineStylable()) {
			shape2.setDashSepBlack(12.);
			shape2.setDashSepWhite(14.);
			shape2.setDotSep(25.);
			shape2.setLineStyle(LineStyle.DOTTED);
			shape2.setLineColour(Color.GREEN);
			shape2.setThickness(13);
		}
		if(shape2.isDbleBorderable()) {
			shape2.setDbleBordCol(Color.RED);
			shape2.setDbleBordSep(20.);
			shape2.setHasDbleBord(true);
		}
		if(shape2.isFillable()) {
			shape2.setFilled(true);
			shape2.setFillingCol(Color.BLUE);
			shape2.setFillingStyle(FillingStyle.GRAD);
		}
		if(shape2.isInteriorStylable()) {
			shape2.setGradAngle(90);
			shape2.setGradColEnd(Color.MAGENTA);
			shape2.setGradColStart(Color.DARK_GRAY);
			shape2.setGradMidPt(0.9);
			shape2.setHatchingsAngle(25);
			shape2.setHatchingsCol(Color.GRAY);
			shape2.setHatchingsSep(30);
			shape2.setHatchingsWidth(100);
		}

		if(shape2.isShadowable()) {
			shape2.setHasShadow(true);
			shape2.setShadowAngle(-40);
			shape2.setShadowCol(Color.ORANGE);
			shape2.setShadowSize(17);
		}
		if(shape2.isShowPtsable())
			shape2.setShowPts(true);

		if(shape2.isArrowable())
			shape2.getArrows().add(DrawingTK.getFactory().createArrow(shape));

		shape2.setOpacity(0.8);
		shape2.setRotationAngle(-30);

		shape.copy(shape2);

		if(shape.isBordersMovable()) {
			assertEquals(shape.getBordersPosition(), shape2.getBordersPosition());
			assertEquals(BorderPos.MID, shape2.getBordersPosition());
		}
		if(shape.isLineStylable()) {
			assertEquals(shape.getDashSepBlack(), shape2.getDashSepBlack());
			assertEquals(shape.getDashSepWhite(), shape2.getDashSepWhite());
			assertEquals(shape.getDotSep(), shape2.getDotSep());
			assertEquals(shape.getLineStyle(), shape2.getLineStyle());
			assertEquals(shape.getLineColour(), shape2.getLineColour());
			assertEquals(shape.getThickness(), shape2.getThickness());
		}
		if(shape.isDbleBorderable()) {
			assertEquals(shape.getDbleBordCol(), shape2.getDbleBordCol());
			assertEquals(shape.getDbleBordSep(), shape2.getDbleBordSep());
			assertEquals(shape.hasDbleBord(), shape2.hasDbleBord());
		}
		if(shape.isFillable()) {
			assertEquals(shape.isFilled(), shape2.isFilled());
			assertEquals(shape.getFillingCol(), shape2.getFillingCol());
			assertEquals(shape.getFillingStyle(), shape2.getFillingStyle());
		}
		if(shape.isInteriorStylable()) {
			assertEquals(shape.getGradAngle(), shape2.getGradAngle());
			assertEquals(shape.getGradColEnd(), shape2.getGradColEnd());
			assertEquals(shape.getGradColStart(), shape2.getGradColStart());
			assertEquals(shape.getGradMidPt(), shape2.getGradMidPt());
			assertEquals(shape.getHatchingsAngle(), shape2.getHatchingsAngle());
			assertEquals(shape.getHatchingsCol(), shape2.getHatchingsCol());
			assertEquals(shape.getHatchingsSep(), shape2.getHatchingsSep());
			assertEquals(shape.getHatchingsWidth(), shape2.getHatchingsWidth());
		}
		if(shape.isShadowable()) {
			assertEquals(shape.hasShadow(), shape2.hasShadow());
			assertEquals(shape.getShadowAngle(), shape2.getShadowAngle());
			assertEquals(shape.getShadowSize(), shape2.getShadowSize());
			assertEquals(shape.getShadowCol(), shape2.getShadowCol());
		}
		if(shape.isShowPtsable())
			assertEquals(shape.isShowPts(), shape2.isShowPts());

		assertEquals(shape2.getOpacity(), 0.8);
		assertEquals(shape.getOpacity(), shape2.getOpacity());
		assertEquals(shape.getRotationAngle(), shape2.getRotationAngle());

		assertEquals(shape.getPoints().size(), shape2.getPoints().size());

		for(int i=0; i<shape.getPoints().size(); i++)
			assertEquals(shape.getPtAt(i), shape2.getPtAt(i));

		if(shape.isArrowable())
			for(int i=0; i<shape.getArrows().size(); i++)
				assertEquals(shape.getArrowAt(i), shape2.getArrowAt(i));
	}


	@Test
	public void testGetGravityCentre() {
		assertNotNull(shape.getGravityCentre());
	}



	@Test
	public abstract void testGetTopLeftPoint();


	@Test
	public abstract void testGetTopRightPoint();


	@Test
	public abstract void testGetBottomRightPoint();


	@Test
	public abstract void testGetBottomLeftPoint();



	@Test
	public void testGetFullBottomRightPoint() {
		IPoint pt;
		double gap;

		shape.setThickness(10.);
		shape.setHasDbleBord(false);
		shape.setBordersPosition(BorderPos.INTO);
		pt  = shape.getBottomRightPoint();
		gap = shape.getBorderGap();

		pt.translate(gap, gap);
		assertEquals(pt, shape.getFullBottomRightPoint());

		shape.setThickness(20.);
		shape.setHasDbleBord(false);
		shape.setBordersPosition(BorderPos.MID);
		pt  = shape.getBottomRightPoint();
		gap = shape.getBorderGap();

		pt.translate(gap, gap);
		assertEquals(pt, shape.getFullBottomRightPoint());

		shape.setThickness(30.);
		shape.setHasDbleBord(false);
		shape.setBordersPosition(BorderPos.OUT);
		pt  = shape.getBottomRightPoint();
		gap = shape.getBorderGap();

		pt.translate(gap, gap);
		assertEquals(pt, shape.getFullBottomRightPoint());

		shape.setThickness(10.);
		shape.setHasDbleBord(true);
		shape.setBordersPosition(BorderPos.INTO);
		pt  = shape.getBottomRightPoint();
		gap = shape.getBorderGap();

		pt.translate(gap, gap);
		assertEquals(pt, shape.getFullBottomRightPoint());

		shape.setThickness(20.);
		shape.setHasDbleBord(true);
		shape.setBordersPosition(BorderPos.MID);
		pt  = shape.getBottomRightPoint();
		gap = shape.getBorderGap();

		pt.translate(gap, gap);
		assertEquals(pt, shape.getFullBottomRightPoint());

		shape.setThickness(30.);
		shape.setHasDbleBord(true);
		shape.setBordersPosition(BorderPos.OUT);
		pt  = shape.getBottomRightPoint();
		gap = shape.getBorderGap();

		pt.translate(gap, gap);
		assertEquals(pt, shape.getFullBottomRightPoint());
	}



	@Test
	public void testGetFullTopLeftPoint() {
		IPoint pt;
		double gap;

		shape.setThickness(10.);
		shape.setHasDbleBord(false);
		shape.setBordersPosition(BorderPos.INTO);
		pt  = shape.getTopLeftPoint();
		gap = shape.getBorderGap();

		pt.translate(-gap, -gap);
		assertEquals(pt, shape.getFullTopLeftPoint());

		shape.setThickness(20.);
		shape.setHasDbleBord(false);
		shape.setBordersPosition(BorderPos.MID);
		pt  = shape.getTopLeftPoint();
		gap = shape.getBorderGap();

		pt.translate(-gap, -gap);
		assertEquals(pt, shape.getFullTopLeftPoint());

		shape.setThickness(30.);
		shape.setHasDbleBord(false);
		shape.setBordersPosition(BorderPos.OUT);
		pt  = shape.getTopLeftPoint();
		gap = shape.getBorderGap();

		pt.translate(-gap, -gap);
		assertEquals(pt, shape.getFullTopLeftPoint());

		shape.setThickness(10.);
		shape.setHasDbleBord(true);
		shape.setBordersPosition(BorderPos.INTO);
		pt  = shape.getTopLeftPoint();
		gap = shape.getBorderGap();

		pt.translate(-gap, -gap);
		assertEquals(pt, shape.getFullTopLeftPoint());

		shape.setThickness(20.);
		shape.setHasDbleBord(true);
		shape.setBordersPosition(BorderPos.MID);
		pt  = shape.getTopLeftPoint();
		gap = shape.getBorderGap();

		pt.translate(-gap, -gap);
		assertEquals(pt, shape.getFullTopLeftPoint());

		shape.setThickness(30.);
		shape.setHasDbleBord(true);
		shape.setBordersPosition(BorderPos.OUT);
		pt  = shape.getTopLeftPoint();
		gap = shape.getBorderGap();

		pt.translate(-gap, -gap);
		assertEquals(pt, shape.getFullTopLeftPoint());
	}



//	@Test
//	public abstract void testScale();


	@Test
	public abstract void testMirrorHorizontal();


	@Test
	public abstract void testMirrorVertical();



	@Test
	public abstract void testTranslate();



	@Test
	public void testIsParametersEquals() {
		if(shape.isBordersMovable())
			shape.setBordersPosition(BorderPos.MID);
		if(shape.isLineStylable()) {
			shape.setDashSepBlack(12.);
			shape.setDashSepWhite(14.);
			shape.setDotSep(25.);
			shape.setLineStyle(LineStyle.DOTTED);
			shape.setLineColour(Color.GREEN);
			shape.setThickness(13);
		}
		if(shape.isDbleBorderable()) {
			shape.setDbleBordCol(Color.RED);
			shape.setDbleBordSep(20.);
			shape.setHasDbleBord(true);
		}
		if(shape.isFillable()) {
			shape.setFilled(true);
			shape.setFillingCol(Color.BLUE);
			shape.setFillingStyle(FillingStyle.GRAD);
		}
		if(shape.isInteriorStylable()) {
			shape.setGradAngle(90);
			shape.setGradColEnd(Color.MAGENTA);
			shape.setGradColStart(Color.DARK_GRAY);
			shape.setGradMidPt(0.9);
			shape.setHatchingsAngle(25);
			shape.setHatchingsCol(Color.GRAY);
			shape.setHatchingsSep(30);
			shape.setHatchingsWidth(100);
		}

		if(shape.isShadowable()) {
			shape.setHasShadow(true);
			shape.setShadowAngle(-40);
			shape.setShadowCol(Color.ORANGE);
			shape.setShadowSize(17);
		}
		if(shape.isShowPtsable())
			shape.setShowPts(true);

		shape.setOpacity(0.8);
		shape.setRotationAngle(-30);

		if(shape2.isBordersMovable())
			shape2.setBordersPosition(BorderPos.MID);
		if(shape2.isLineStylable()) {
			shape2.setDashSepBlack(12.);
			shape2.setDashSepWhite(14.);
			shape2.setDotSep(25.);
			shape2.setLineStyle(LineStyle.DOTTED);
			shape2.setLineColour(Color.GREEN);
			shape2.setThickness(13);
		}
		if(shape2.isDbleBorderable()) {
			shape2.setDbleBordCol(Color.RED);
			shape2.setDbleBordSep(20.);
			shape2.setHasDbleBord(true);
		}
		if(shape2.isFillable()) {
			shape2.setFilled(true);
			shape2.setFillingCol(Color.BLUE);
			shape2.setFillingStyle(FillingStyle.GRAD);
		}
		if(shape2.isInteriorStylable()) {
			shape2.setGradAngle(90);
			shape2.setGradColEnd(Color.MAGENTA);
			shape2.setGradColStart(Color.DARK_GRAY);
			shape2.setGradMidPt(0.9);
			shape2.setHatchingsAngle(25);
			shape2.setHatchingsCol(Color.GRAY);
			shape2.setHatchingsSep(30);
			shape2.setHatchingsWidth(100);
		}

		if(shape2.isShadowable()) {
			shape2.setHasShadow(true);
			shape2.setShadowAngle(-40);
			shape2.setShadowCol(Color.ORANGE);
			shape2.setShadowSize(17);
		}
		if(shape2.isShowPtsable())
			shape2.setShowPts(true);

		shape2.setOpacity(0.8);
		shape2.setRotationAngle(-30);

		assertTrue(shape.isParametersEquals(shape2, true));

		if(shape2.isShadowable()) {
			shape2.setHasShadow(false);
			shape2.setShadowAngle(-30);
			shape2.setShadowCol(Color.black);
			shape2.setShadowSize(15);
		}

		assertTrue(shape.isParametersEquals(shape2, false));
		shape2.setRotationAngle(-20);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setRotationAngle(-30);
		shape.setOpacity(1);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape.setOpacity(0.8);
		if(shape2.isShowPtsable()) {
			shape2.setShowPts(false);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setShowPts(true);
		}
		if(shape2.isShadowable()) {
			shape2.setHasShadow(true);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setHasShadow(false);
			shape2.setShadowAngle(-40);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setShadowAngle(-30);
			shape2.setShadowCol(Color.BLUE);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setShadowCol(Color.black);
			shape2.setShadowSize(11);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setShadowSize(15);
		}
		if(shape2.isInteriorStylable()) {
			shape2.setGradAngle(80);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setGradAngle(90);
			shape2.setGradColEnd(Color.BLACK);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setGradColEnd(Color.MAGENTA);
			shape2.setGradColStart(Color.BLACK);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setGradColStart(Color.DARK_GRAY);
			shape2.setGradMidPt(1);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setGradMidPt(0.9);
			shape2.setHatchingsAngle(20);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setHatchingsAngle(25);
			shape2.setHatchingsCol(Color.ORANGE);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setHatchingsCol(Color.GRAY);
			shape2.setHatchingsSep(20);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setHatchingsSep(30);
			shape2.setHatchingsWidth(10);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setHatchingsWidth(100);
		}
		if(shape2.isFillable()) {
			shape2.setFilled(false);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setFilled(true);
			shape2.setFillingCol(Color.BLACK);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setFillingCol(Color.BLUE);
			shape2.setFillingStyle(FillingStyle.CLINES);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setFillingStyle(FillingStyle.GRAD);
		}
		if(shape2.isBordersMovable()) {
			shape2.setBordersPosition(BorderPos.OUT);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setBordersPosition(BorderPos.MID);
		}
		if(shape2.isLineStylable()) {
			shape2.setDashSepBlack(10.);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setDashSepBlack(12.);
			shape2.setDashSepWhite(11.);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setDashSepWhite(14.);
			shape2.setDotSep(2.);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setDotSep(25.);
			shape2.setLineStyle(LineStyle.DASHED);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setLineStyle(LineStyle.DOTTED);
			shape2.setLineColour(Color.BLACK);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setLineColour(Color.GREEN);
			shape2.setThickness(1);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setThickness(13);
		}
		if(shape2.isDbleBorderable()) {
			shape2.setDbleBordCol(Color.BLUE);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setDbleBordCol(Color.RED);
			shape2.setDbleBordSep(2.);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setDbleBordSep(20.);
			shape2.setHasDbleBord(false);
			assertFalse(shape.isParametersEquals(shape2, true));
			shape2.setHasDbleBord(true);
		}
	}


	@Test
	public void testSetHasHatchings() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.CLINES);
			assertTrue(shape.hasHatchings());
			shape.setFillingStyle(FillingStyle.CLINES_PLAIN);
			assertTrue(shape.hasHatchings());
			shape.setFillingStyle(FillingStyle.HLINES);
			assertTrue(shape.hasHatchings());
			shape.setFillingStyle(FillingStyle.HLINES_PLAIN);
			assertTrue(shape.hasHatchings());
			shape.setFillingStyle(FillingStyle.VLINES);
			assertTrue(shape.hasHatchings());
			shape.setFillingStyle(FillingStyle.VLINES_PLAIN);
			assertTrue(shape.hasHatchings());
			shape.setFillingStyle(FillingStyle.GRAD);
			assertFalse(shape.hasHatchings());
			shape.setFillingStyle(FillingStyle.NONE);
			assertFalse(shape.hasHatchings());
			shape.setFillingStyle(FillingStyle.PLAIN);
			assertFalse(shape.hasHatchings());
		}
	}


	@Test
	public void testSetHasGradient() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.GRAD);
			assertTrue(shape.hasGradient());
			shape.setFillingStyle(FillingStyle.CLINES);
			assertFalse(shape.hasGradient());
			shape.setFillingStyle(FillingStyle.CLINES_PLAIN);
			assertFalse(shape.hasGradient());
			shape.setFillingStyle(FillingStyle.HLINES);
			assertFalse(shape.hasGradient());
			shape.setFillingStyle(FillingStyle.HLINES_PLAIN);
			assertFalse(shape.hasGradient());
			shape.setFillingStyle(FillingStyle.VLINES);
			assertFalse(shape.hasGradient());
			shape.setFillingStyle(FillingStyle.VLINES_PLAIN);
			assertFalse(shape.hasGradient());
			shape.setFillingStyle(FillingStyle.NONE);
			assertFalse(shape.hasGradient());
			shape.setFillingStyle(FillingStyle.PLAIN);
			assertFalse(shape.hasGradient());
		}
	}



	@Test
	public void testGetSetGradColEnd() {
		if(shape.isInteriorStylable()) {
			shape.setGradColEnd(Color.BLUE);
			assertEquals(Color.BLUE, shape.getGradColEnd());
			shape.setGradColEnd(null);
			assertEquals(Color.BLUE, shape.getGradColEnd());
		}
	}



	@Test
	public void testGetSetGradColStart() {
		if(shape.isInteriorStylable()) {
			shape.setGradColStart(Color.BLUE);
			assertEquals(Color.BLUE, shape.getGradColStart());
			shape.setGradColStart(null);
			assertEquals(Color.BLUE, shape.getGradColStart());
		}
	}



	@Test
	public void testGetSetGradAngle() {
		if(shape.isInteriorStylable()) {
			shape.setGradAngle(30.);
			assertEquals(30., shape.getGradAngle());
			shape.setGradAngle(20.);
			assertEquals(20., shape.getGradAngle());
			shape.setGradAngle(Double.NaN);
			assertEquals(20., shape.getGradAngle());
			shape.setGradAngle(Double.NEGATIVE_INFINITY);
			assertEquals(20., shape.getGradAngle());
			shape.setGradAngle(Double.POSITIVE_INFINITY);
			assertEquals(20., shape.getGradAngle());
		}
	}



	@Test
	public void testGetSetGradMidPt() {
		if(shape.isInteriorStylable()) {
			shape.setGradMidPt(0.2);
			assertEquals(0.2, shape.getGradMidPt());
			shape.setGradMidPt(0.4);
			assertEquals(0.4, shape.getGradMidPt());
			shape.setGradMidPt(Double.NaN);
			assertEquals(0.4, shape.getGradMidPt());
			shape.setGradMidPt(Double.NEGATIVE_INFINITY);
			assertEquals(0.4, shape.getGradMidPt());
			shape.setGradMidPt(Double.POSITIVE_INFINITY);
			assertEquals(0.4, shape.getGradMidPt());
			shape.setGradMidPt(0);
			assertEquals(0., shape.getGradMidPt());
			shape.setGradMidPt(1);
			assertEquals(1., shape.getGradMidPt());
			shape.setGradMidPt(1.0001);
			assertEquals(1., shape.getGradMidPt());
			shape.setGradMidPt(-0.0001);
			assertEquals(1., shape.getGradMidPt());
		}
	}




	@Test
	public void testGetSetHatchingsSep() {
		if(shape.isInteriorStylable()) {
			shape.setHatchingsSep(30.);
			assertEquals(30., shape.getHatchingsSep());
			shape.setHatchingsSep(20.);
			assertEquals(20., shape.getHatchingsSep());
			shape.setHatchingsSep(Double.NaN);
			assertEquals(20., shape.getHatchingsSep());
			shape.setHatchingsSep(Double.NEGATIVE_INFINITY);
			assertEquals(20., shape.getHatchingsSep());
			shape.setHatchingsSep(Double.POSITIVE_INFINITY);
			assertEquals(20., shape.getHatchingsSep());
			shape.setHatchingsSep(0);
			assertEquals(0., shape.getHatchingsSep());
			shape.setHatchingsSep(-1);
			assertEquals(0., shape.getHatchingsSep());
		}
	}


	@Test
	public void testGetSetHatchingsCol() {
		if(shape.isInteriorStylable()) {
			shape.setHatchingsCol(Color.CYAN);
			assertEquals(Color.CYAN, shape.getHatchingsCol());
			shape.setHatchingsCol(Color.BLACK);
			assertEquals(Color.BLACK, shape.getHatchingsCol());
			shape.setHatchingsCol(null);
			assertEquals(Color.BLACK, shape.getHatchingsCol());
		}
	}



	@Test
	public void testGetSetHatchingsAngle() {
		if(shape.isInteriorStylable()) {
			shape.setHatchingsAngle(30.);
			assertEquals(30., shape.getHatchingsAngle());
			shape.setHatchingsAngle(20.);
			assertEquals(20., shape.getHatchingsAngle());
			shape.setHatchingsAngle(Double.NaN);
			assertEquals(20., shape.getHatchingsAngle());
			shape.setHatchingsAngle(Double.NEGATIVE_INFINITY);
			assertEquals(20., shape.getHatchingsAngle());
			shape.setHatchingsAngle(Double.POSITIVE_INFINITY);
			assertEquals(20., shape.getHatchingsAngle());
			shape.setHatchingsAngle(0);
			assertEquals(0., shape.getHatchingsAngle());
			shape.setHatchingsAngle(-30);
			assertEquals(-30., shape.getHatchingsAngle());
		}
	}


	@Test
	public void testGetSetHatchingsWidth() {
		if(shape.isInteriorStylable()) {
			shape.setHatchingsWidth(30.);
			assertEquals(30., shape.getHatchingsWidth());
			shape.setHatchingsWidth(20.);
			assertEquals(20., shape.getHatchingsWidth());
			shape.setHatchingsWidth(Double.NaN);
			assertEquals(20., shape.getHatchingsWidth());
			shape.setHatchingsWidth(Double.NEGATIVE_INFINITY);
			assertEquals(20., shape.getHatchingsWidth());
			shape.setHatchingsWidth(Double.POSITIVE_INFINITY);
			assertEquals(20., shape.getHatchingsWidth());
			shape.setHatchingsWidth(0);
			assertEquals(20., shape.getHatchingsWidth());
			shape.setHatchingsWidth(-1);
			assertEquals(20., shape.getHatchingsWidth());
		}
	}


	@Test
	public void testGetSetRotationAngle() {
		shape.setRotationAngle(30.);
		assertEquals(30., shape.getRotationAngle());
		shape.setRotationAngle(20.);
		assertEquals(20., shape.getRotationAngle());
		shape.setRotationAngle(Double.NaN);
		assertEquals(20., shape.getRotationAngle());
		shape.setRotationAngle(Double.NEGATIVE_INFINITY);
		assertEquals(20., shape.getRotationAngle());
		shape.setRotationAngle(Double.POSITIVE_INFINITY);
		assertEquals(20., shape.getRotationAngle());
		shape.setRotationAngle(0);
		assertEquals(0., shape.getRotationAngle());
		shape.setRotationAngle(-30);
		assertEquals(-30., shape.getRotationAngle());
	}


	@Test
	public void testIsSetShowPts() {
		if(shape.isShowPtsable()) {
			shape.setShowPts(true);
			assertEquals(true, shape.isShowPts());
			shape.setShowPts(false);
			assertEquals(false, shape.isShowPts());
		}
	}


	@Test
	public void testhasSetDbleBord() {
		if(shape.isDbleBorderable()) {
			shape.setHasDbleBord(true);
			assertEquals(true, shape.hasDbleBord());
			shape.setHasDbleBord(false);
			assertEquals(false, shape.hasDbleBord());
		}
	}


	@Test
	public void testGetSetDbleBordCol() {
		if(shape.isDbleBorderable()) {
			shape.setDbleBordCol(Color.CYAN);
			assertEquals(Color.CYAN, shape.getDbleBordCol());
			shape.setDbleBordCol(Color.BLACK);
			assertEquals(Color.BLACK, shape.getDbleBordCol());
			shape.setDbleBordCol(null);
			assertEquals(Color.BLACK, shape.getDbleBordCol());
		}
	}



	@Test
	public void testGetSetDbleBordSep() {
		if(shape.isDbleBorderable()) {
			shape.setDbleBordSep(30.);
			assertEquals(30., shape.getDbleBordSep());
			shape.setDbleBordSep(20.);
			assertEquals(20., shape.getDbleBordSep());
			shape.setDbleBordSep(Double.NaN);
			assertEquals(20., shape.getDbleBordSep());
			shape.setDbleBordSep(Double.NEGATIVE_INFINITY);
			assertEquals(20., shape.getDbleBordSep());
			shape.setDbleBordSep(Double.POSITIVE_INFINITY);
			assertEquals(20., shape.getDbleBordSep());
			shape.setDbleBordSep(0);
			assertEquals(0., shape.getDbleBordSep());
			shape.setDbleBordSep(-1);
			assertEquals(0., shape.getDbleBordSep());
		}
	}



	@Test
	public void testHasSetShadow() {
		if(shape.isShadowable()) {
			shape.setHasShadow(true);
			assertEquals(true, shape.hasShadow());
			shape.setHasShadow(false);
			assertEquals(false, shape.hasShadow());
		}
	}



	@Test
	public void testGetSetShadowCol() {
		if(shape.isShadowable()) {
			shape.setShadowCol(Color.CYAN);
			assertEquals(Color.CYAN, shape.getShadowCol());
			shape.setShadowCol(Color.BLACK);
			assertEquals(Color.BLACK, shape.getShadowCol());
			shape.setShadowCol(null);
			assertEquals(Color.BLACK, shape.getShadowCol());
		}
	}



	@Test
	public void testGetSetShadowAngle() {
		if(shape.isShadowable()) {
			shape.setShadowAngle(30.);
			assertEquals(30., shape.getShadowAngle());
			shape.setShadowAngle(20.);
			assertEquals(20., shape.getShadowAngle());
			shape.setShadowAngle(Double.NaN);
			assertEquals(20., shape.getShadowAngle());
			shape.setShadowAngle(Double.NEGATIVE_INFINITY);
			assertEquals(20., shape.getShadowAngle());
			shape.setShadowAngle(Double.POSITIVE_INFINITY);
			assertEquals(20., shape.getShadowAngle());
			shape.setShadowAngle(0);
			assertEquals(0., shape.getShadowAngle());
			shape.setShadowAngle(-30);
			assertEquals(-30., shape.getShadowAngle());
		}
	}



	@Test
	public void testGetArrows() {
		if(shape.isArrowable()) {
			assertNotNull(shape.getArrows());
		}
	}



	@Test
	public void testGetArrowAt() {
		if(shape.isArrowable()) {
			IArrow a1 = DrawingTK.getFactory().createArrow(shape);
			IArrow a2 = DrawingTK.getFactory().createArrow(shape);
			IArrow a3 = DrawingTK.getFactory().createArrow(shape);

			shape.getArrows().clear();
			shape.getArrows().add(a1);
			shape.getArrows().add(a2);
			shape.getArrows().add(a3);

			assertEquals(shape.getArrowAt(0), a1);
			assertEquals(shape.getArrowAt(1), a2);
			assertEquals(shape.getArrowAt(2), a3);
			assertEquals(shape.getArrowAt(-1), a3);
			assertNull(shape.getArrowAt(-2));
			assertNull(shape.getArrowAt(3));
			assertNull(shape.getArrowAt(Integer.MAX_VALUE));
			assertNull(shape.getArrowAt(Integer.MIN_VALUE));
		}
	}



	@Test
	public void testIsSetFilled() {
		if(shape.isFillable()) {
			shape.setFilled(true);
			assertEquals(true, shape.isFilled());
			shape.setFilled(false);
			assertEquals(false, shape.isFilled());
		}
	}


	@Test
	public void testSetNewId() {
		int id = shape.getId();

		shape.setNewId();
		assertNotSame(id, shape.getId());
	}



	@Test
	public void testAddToRotationAngle() {
		shape.setRotationAngle(0);
		shape.addToRotationAngle(null, 30.5);
		assertEquals(30.5, shape.getRotationAngle());
		shape.addToRotationAngle(null, -41);
		assertEquals(-10.5, shape.getRotationAngle());
		shape.addToRotationAngle(null, Double.NaN);
		assertEquals(-10.5, shape.getRotationAngle());
		shape.addToRotationAngle(null, Double.NEGATIVE_INFINITY);
		assertEquals(-10.5, shape.getRotationAngle());
		shape.addToRotationAngle(null, Double.POSITIVE_INFINITY);
		assertEquals(-10.5, shape.getRotationAngle());
		shape.addToRotationAngle(null, 10.5);
		assertEquals(0., shape.getRotationAngle());
	}



	@Test
	public void testGetSetShadowSize() {
		if(shape.isShadowable()) {
			shape.setShadowSize(30.);
			assertEquals(30., shape.getShadowSize());
			shape.setShadowSize(20.);
			assertEquals(20., shape.getShadowSize());
			shape.setShadowSize(Double.NaN);
			assertEquals(20., shape.getShadowSize());
			shape.setShadowSize(Double.NEGATIVE_INFINITY);
			assertEquals(20., shape.getShadowSize());
			shape.setShadowSize(Double.POSITIVE_INFINITY);
			assertEquals(20., shape.getShadowSize());
			shape.setShadowSize(0);
			assertEquals(20., shape.getShadowSize());
			shape.setShadowSize(-1);
			assertEquals(20., shape.getShadowSize());
		}
	}



	@Test
	public void testGetSetBorderPosition() {
		if(shape.isBordersMovable()) {
			shape.setBordersPosition(BorderPos.INTO);
			assertEquals(BorderPos.INTO, shape.getBordersPosition());
			shape.setBordersPosition(BorderPos.MID);
			assertEquals(BorderPos.MID, shape.getBordersPosition());
			shape.setBordersPosition(BorderPos.OUT);
			assertEquals(BorderPos.OUT, shape.getBordersPosition());
			shape.setBordersPosition(BorderPos.INTO);
			assertEquals(BorderPos.INTO, shape.getBordersPosition());
			shape.setBordersPosition(null);
			assertEquals(BorderPos.INTO, shape.getBordersPosition());
		}
	}


	@Test
	public void testGetSetOpacity() {
		assertEquals(1., shape.getOpacity());
		shape.setOpacity(0.5);
		assertEquals(0.5, shape.getOpacity());
		shape.setOpacity(0.);
		assertEquals(0., shape.getOpacity());
		shape.setOpacity(1);
		assertEquals(1., shape.getOpacity());
		shape.setOpacity(0.6);
		assertEquals(0.6, shape.getOpacity());
		shape.setOpacity(Double.NaN);
		assertEquals(0.6, shape.getOpacity());
		shape.setOpacity(Double.NEGATIVE_INFINITY);
		assertEquals(0.6, shape.getOpacity());
		shape.setOpacity(Double.POSITIVE_INFINITY);
		assertEquals(0.6, shape.getOpacity());
		shape.setOpacity(-0.0001);
		assertEquals(0.6, shape.getOpacity());
		shape.setOpacity(1.001);
		assertEquals(0.6, shape.getOpacity());
	}





	@Test
	public void testGetBorderGap() {
		double gap = 0.;

		if(shape.isThicknessable())
			shape.setThickness(10.);

		shape.setBordersPosition(BorderPos.INTO);
		shape.setHasDbleBord(false);

		assertEquals(0., shape.getBorderGap());

		if(shape.isThicknessable())
			shape.setThickness(10.);

		if(shape.isDbleBorderable()) {
			shape.setHasDbleBord(true);
			shape.setDbleBordSep(5.);
		}

		assertEquals(0., shape.getBorderGap());
		gap = 0.;

		if(!shape.isBordersMovable())
			return ;

		shape.setBordersPosition(BorderPos.MID);

		if(shape.isThicknessable()) {
			shape.setThickness(10.);
			gap = 5.;
		}

		shape.setHasDbleBord(false);

		assertEquals(gap, shape.getBorderGap());
		gap = 0.;

		if(shape.isThicknessable()) {
			shape.setThickness(10.);
			gap = 5.;
		}

		if(shape.isDbleBorderable()) {
			shape.setHasDbleBord(true);
			shape.setDbleBordSep(20.);
			gap += shape.getThickness()/2. + 10.;
		}

		assertEquals(gap, shape.getBorderGap());
		gap = 0.;

		shape.setBordersPosition(BorderPos.OUT);

		if(shape.isThicknessable()) {
			shape.setThickness(50.);
			gap = 50.;
		}

		shape.setHasDbleBord(false);

		assertEquals(gap, shape.getBorderGap());
		gap = 0.;

		if(shape.isThicknessable()) {
			shape.setThickness(100.);
			gap = 100.;
		}

		if(shape.isDbleBorderable()) {
			shape.setHasDbleBord(true);
			shape.setDbleBordSep(30.);
			gap += shape.getThickness() + 30.;
		}

		assertEquals(gap, shape.getBorderGap());
		gap = 0.;
	}



	@Test
	public void testDuplicate() {
		IShape sh = shape.duplicate();

		assertNotNull(sh);
		assertEquals(sh.getClass(), shape.getClass());
		assertTrue(shape.isParametersEquals(sh, true));
	}



	@Test
	public void testSetGetThickness() {
		if(shape.isThicknessable()) {
			shape.setThickness(10.);
			assertEquals(10., shape.getThickness());
			shape.setThickness(Double.NaN);
			assertEquals(10., shape.getThickness());
			shape.setThickness(Double.POSITIVE_INFINITY);
			assertEquals(10., shape.getThickness());
			shape.setThickness(Double.NEGATIVE_INFINITY);
			assertEquals(10., shape.getThickness());
			shape.setThickness(0);
			assertEquals(10., shape.getThickness());
			shape.setThickness(-1);
			assertEquals(10., shape.getThickness());
		}
	}


	@Test
	public void testSetGetLineColour() {
		shape.setLineColour(Color.CYAN);
		assertEquals(Color.CYAN, shape.getLineColour());
		shape.setLineColour(null);
		assertEquals(Color.CYAN, shape.getLineColour());
		shape.setLineColour(Color.RED);
		assertEquals(Color.RED, shape.getLineColour());
		shape.setLineColour(new Color(100,100,100));
		assertEquals(new Color(100,100,100), shape.getLineColour());
	}


	@Test
	public void testSetGetLineStyle() {
		if(shape.isLineStylable()) {
			shape.setLineStyle(LineStyle.DOTTED);
			assertEquals(shape.getLineStyle(), LineStyle.DOTTED);
			shape.setLineStyle(LineStyle.DASHED);
			assertEquals(shape.getLineStyle(), LineStyle.DASHED);
			shape.setLineStyle(LineStyle.NONE);
			assertEquals(shape.getLineStyle(), LineStyle.NONE);
			shape.setLineStyle(LineStyle.SOLID);
			assertEquals(shape.getLineStyle(), LineStyle.SOLID);
			shape.setLineStyle(null);
			assertEquals(shape.getLineStyle(), LineStyle.SOLID);
		}
	}


	@Test
	public void testSetGetDashSepWhite() {
		if(shape.isLineStylable()) {
			shape.setDashSepWhite(10.);
			assertEquals(shape.getDashSepWhite(), 10.);
			shape.setDashSepWhite(5.);
			assertEquals(shape.getDashSepWhite(), 5.);
			shape.setDashSepWhite(0.);
			assertEquals(shape.getDashSepWhite(), 5.);
			shape.setDashSepWhite(-10.);
			assertEquals(shape.getDashSepWhite(), 5.);
			shape.setDashSepWhite(Double.NaN);
			assertEquals(shape.getDashSepWhite(), 5.);
			shape.setDashSepWhite(Double.POSITIVE_INFINITY);
			assertEquals(shape.getDashSepWhite(), 5.);
			shape.setDashSepWhite(Double.NEGATIVE_INFINITY);
			assertEquals(shape.getDashSepWhite(), 5.);
		}
	}


	@Test
	public void testSetGetDashSepBlack() {
		if(shape.isLineStylable()) {
			shape.setDashSepBlack(10.);
			assertEquals(shape.getDashSepBlack(), 10.);
			shape.setDashSepBlack(5.);
			assertEquals(shape.getDashSepBlack(), 5.);
			shape.setDashSepBlack(0.);
			assertEquals(shape.getDashSepBlack(), 5.);
			shape.setDashSepBlack(-10.);
			assertEquals(shape.getDashSepBlack(), 5.);
			shape.setDashSepBlack(Double.NaN);
			assertEquals(shape.getDashSepBlack(), 5.);
			shape.setDashSepBlack(Double.POSITIVE_INFINITY);
			assertEquals(shape.getDashSepBlack(), 5.);
			shape.setDashSepBlack(Double.NEGATIVE_INFINITY);
			assertEquals(shape.getDashSepBlack(), 5.);
		}
	}


	@Test
	public void testSetGetDotSep() {
		if(shape.isLineStylable()) {
			shape.setDotSep(10.);
			assertEquals(shape.getDotSep(), 10.);
			shape.setDotSep(5.);
			assertEquals(shape.getDotSep(), 5.);
			shape.setDotSep(0.);
			assertEquals(shape.getDotSep(), 0.);
			shape.setDotSep(-10.);
			assertEquals(shape.getDotSep(), 0.);
			shape.setDotSep(Double.NaN);
			assertEquals(shape.getDotSep(), 0.);
			shape.setDotSep(Double.POSITIVE_INFINITY);
			assertEquals(shape.getDotSep(), 0.);
			shape.setDotSep(Double.NEGATIVE_INFINITY);
			assertEquals(shape.getDotSep(), 0.);
		}
	}


	@Test
	public void testSetGetFillingCol() {
		if(shape.isInteriorStylable()) {
			shape.setFillingCol(Color.DARK_GRAY);
			assertEquals(shape.getFillingCol(), Color.DARK_GRAY);
			shape.setFillingCol(null);
			assertEquals(shape.getFillingCol(), Color.DARK_GRAY);
			shape.setFillingCol(Color.BLUE);
			assertEquals(shape.getFillingCol(), Color.BLUE);
			shape.setFillingCol(new Color(200,100,40));
			assertEquals(shape.getFillingCol(), new Color(200,100,40));
		}
	}



	@Test
	public void testSetGetFillingStyle() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.GRAD);
			assertEquals(shape.getFillingStyle(), FillingStyle.GRAD);
			shape.setFillingStyle(null);
			assertEquals(shape.getFillingStyle(), FillingStyle.GRAD);
			shape.setFillingStyle(FillingStyle.CLINES);
			assertEquals(shape.getFillingStyle(), FillingStyle.CLINES);
			shape.setFillingStyle(FillingStyle.NONE);
			assertEquals(shape.getFillingStyle(), FillingStyle.NONE);
			shape.setFillingStyle(FillingStyle.CLINES_PLAIN);
			assertEquals(shape.getFillingStyle(), FillingStyle.CLINES_PLAIN);
			shape.setFillingStyle(FillingStyle.HLINES);
			assertEquals(shape.getFillingStyle(), FillingStyle.HLINES);
			shape.setFillingStyle(FillingStyle.HLINES_PLAIN);
			assertEquals(shape.getFillingStyle(), FillingStyle.HLINES_PLAIN);
			shape.setFillingStyle(FillingStyle.PLAIN);
			assertEquals(shape.getFillingStyle(), FillingStyle.PLAIN);
			shape.setFillingStyle(FillingStyle.VLINES);
			assertEquals(shape.getFillingStyle(), FillingStyle.VLINES);
			shape.setFillingStyle(FillingStyle.VLINES_PLAIN);
			assertEquals(shape.getFillingStyle(), FillingStyle.VLINES_PLAIN);
		}
	}
}
