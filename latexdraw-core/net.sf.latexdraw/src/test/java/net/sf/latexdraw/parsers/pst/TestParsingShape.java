package net.sf.latexdraw.parsers.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.view.latex.DviPsColors;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public abstract class TestParsingShape extends TestPSTParser {
	@Test
	public void testParamGradlines() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradlines=100]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isInteriorStylable()) {
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradlines=200]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradlines=300]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradlines=-100]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			PSTParser.errorLogs().clear();
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradlines=100.12]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertFalse(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamShadowangle() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowangle=10]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isShadowable()) {
			assertEquals(Math.toRadians(10.), sh.getShadowAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowangle=20.]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(20.), sh.getShadowAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowangle=0.5]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(0.5), sh.getShadowAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowangle=+---123.1]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(-123.1), sh.getShadowAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowangle=10, shadowangle=-12]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(-12.), sh.getShadowAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamGradangle() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradangle=10]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isInteriorStylable()) {
			assertEquals(Math.toRadians(10.), sh.getGradAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradangle=20.]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(20.), sh.getGradAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradangle=0.5]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(0.5), sh.getGradAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradangle=+---123.1]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(-123.1), sh.getGradAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradangle=10, gradangle=-12]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(-12.), sh.getGradAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamGradmidpoint() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradmidpoint=0]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isInteriorStylable()) {
			assertEquals(0., sh.getGradMidPt(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradmidpoint=1]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(1., sh.getGradMidPt(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradmidpoint=0.5]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.5, sh.getGradMidPt(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradmidpoint=0.22]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.22, sh.getGradMidPt(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradmidpoint=-1]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertFalse(PSTParser.errorLogs().isEmpty());
			PSTParser.errorLogs().clear();
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradmidpoint=2]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertFalse(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamHatchangle() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchangle=10]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isInteriorStylable()) {
			assertEquals(Math.toRadians(10.), sh.getHatchingsAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchangle=20.]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(20.), sh.getHatchingsAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchangle=0.5]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(0.5), sh.getHatchingsAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchangle=+---123.1]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(-123.1), sh.getHatchingsAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchangle=10, hatchangle=-12]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(Math.toRadians(-12.), sh.getHatchingsAngle(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamHatchsep() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchsep=0.2]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isInteriorStylable()) {
			assertEquals(0.2 * IShape.PPC, sh.getHatchingsSep(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchsep=0.2cm]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.2 * IShape.PPC, sh.getHatchingsSep(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchsep=2mm]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.2 * IShape.PPC, sh.getHatchingsSep(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchsep=0.1in]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.1 * IShape.PPC / PSTricksConstants.INCH_VAL_CM, sh.getHatchingsSep(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchsep=2mm, hatchsep=0.1pt]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.1 * IShape.PPC / PSTricksConstants.CM_VAL_PT, sh.getHatchingsSep(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamHatchwidth() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchwidth=0.2]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isInteriorStylable()) {
			assertEquals(0.2 * IShape.PPC, sh.getHatchingsWidth(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchwidth=0.2cm]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.2 * IShape.PPC, sh.getHatchingsWidth(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchwidth=2mm]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.2 * IShape.PPC, sh.getHatchingsWidth(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchwidth=0.1in]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.1 * IShape.PPC / PSTricksConstants.INCH_VAL_CM, sh.getHatchingsWidth(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchwidth=2mm, hatchwidth=0.1pt]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.1 * IShape.PPC / PSTricksConstants.CM_VAL_PT, sh.getHatchingsWidth(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamDoublesep() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[doubleline=true, doublesep=0.2]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isDbleBorderable()) {
			assertEquals(0.2 * IShape.PPC, sh.getDbleBordSep(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[doubleline=true, doublesep=0.2cm]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.2 * IShape.PPC, sh.getDbleBordSep(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[doubleline=true, doublesep=2mm]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.2 * IShape.PPC, sh.getDbleBordSep(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[doubleline=true, doublesep=0.1in]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.1 * IShape.PPC / PSTricksConstants.INCH_VAL_CM, sh.getDbleBordSep(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[doubleline=true, doublesep=2mm, doublesep=0.1pt]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.1 * IShape.PPC / PSTricksConstants.CM_VAL_PT, sh.getDbleBordSep(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamShadowsize() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowsize=0.2]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isShadowable()) {
			assertEquals(0.2 * IShape.PPC, sh.getShadowSize(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowsize=0.2cm]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.2 * IShape.PPC, sh.getShadowSize(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowsize=2mm]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.2 * IShape.PPC, sh.getShadowSize(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowsize=0.1in]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.1 * IShape.PPC / PSTricksConstants.INCH_VAL_CM, sh.getShadowSize(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowsize=2mm, shadowsize=0.1pt]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.1 * IShape.PPC / PSTricksConstants.CM_VAL_PT, sh.getShadowSize(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamLinewidth() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[linewidth=0.2]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isThicknessable()) {
			assertEquals(0.2 * IShape.PPC, sh.getThickness(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[linewidth=0.2cm]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.2 * IShape.PPC, sh.getThickness(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[linewidth=2mm]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.2 * IShape.PPC, sh.getThickness(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[linewidth=0.1in]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.1 * IShape.PPC / PSTricksConstants.INCH_VAL_CM, sh.getThickness(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[linewidth=0.2, linewidth=0.1pt]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(0.1 * IShape.PPC / PSTricksConstants.CM_VAL_PT, sh.getThickness(), 0.00001);
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testStarFillsShape() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "*" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(sh.isFilled());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testStarBorderPosOuter() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "*[" + "dimen=inner]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if(sh.isBordersMovable()) {
			assertEquals(BorderPos.INTO, sh.getBordersPosition());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testStarHasNoLineStyle() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "*[" + "linestyle=dashed]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if(sh.isLineStylable()) {
			assertEquals(LineStyle.SOLID, sh.getLineStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testStarHasNoDoubleBorder() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "*[" + "doubleline=true]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if(sh.isDbleBorderable()) {
			assertFalse(sh.hasDbleBord());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testStarFillingParametershaveNoEffect() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "*[" + "fillstyle=gradient]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertEquals(FillingStyle.PLAIN, sh.getFillingStyle());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testStarLineColourIsFillingColour() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "*[" + "linecolor=green]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertEquals(DviPsColors.GREEN, sh.getFillingCol());
		assertEquals(DviPsColors.GREEN, sh.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testStarNoShadow() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "*[" + "shadow=true]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if(sh.isShadowable()) {
			assertFalse(sh.hasShadow());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamDimen() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[dimen=inner]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isBordersMovable()) {
			assertEquals(BorderPos.OUT, sh.getBordersPosition());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$
			assertEquals(BorderPos.INTO, sh.getBordersPosition());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[dimen=outer]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(BorderPos.INTO, sh.getBordersPosition());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[dimen=middle]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(BorderPos.MID, sh.getBordersPosition());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[dimen=outer,dimen=middle]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(BorderPos.MID, sh.getBordersPosition());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamLineStyle() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[linestyle=dashed]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isLineStylable()) {
			assertEquals(LineStyle.DASHED, sh.getLineStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$
			assertEquals(LineStyle.SOLID, sh.getLineStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[linestyle=dotted]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(LineStyle.DOTTED, sh.getLineStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[linestyle=solid]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(LineStyle.SOLID, sh.getLineStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[linestyle=dashed,linestyle=dotted]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(LineStyle.DOTTED, sh.getLineStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamFillingStyle() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle =gradient]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isFillable()) {
			assertTrue(sh.hasGradient());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$
			assertFalse(sh.isFilled());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(FillingStyle.CLINES, sh.getFillingStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines*]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(FillingStyle.CLINES_PLAIN, sh.getFillingStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=hlines*]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(FillingStyle.HLINES_PLAIN, sh.getFillingStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=hlines]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(FillingStyle.HLINES, sh.getFillingStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=vlines*]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(FillingStyle.VLINES_PLAIN, sh.getFillingStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=vlines]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(FillingStyle.VLINES, sh.getFillingStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=none]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(FillingStyle.NONE, sh.getFillingStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient,fillstyle=clines]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(FillingStyle.CLINES, sh.getFillingStyle());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=solid]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(FillingStyle.PLAIN, sh.getFillingStyle());
			assertTrue(sh.isFilled());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamLinecolor() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[linecolor =blue]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(DviPsColors.BLUE, sh.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
		sh = parser.parsePSTCode("\\" + getCommandName() + "[linecolor=\\psfillcolor]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(DviPsColors.WHITE, sh.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
		sh = parser.parsePSTCode("\\" + getCommandName() + "[linecolor=\\psfillcolor,linecolor=red]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(DviPsColors.RED, sh.getLineColour());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test
	public void testParamShadow() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow =true]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isShadowable()) {
			assertTrue(sh.hasShadow());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow  =false]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertFalse(sh.hasShadow());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true,shadow  =false]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertFalse(sh.hasShadow());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamshadowcolor() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowcolor =blue]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isShadowable()) {
			assertEquals(DviPsColors.BLUE, sh.getShadowCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowcolor=\\psfillcolor]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(DviPsColors.WHITE, sh.getShadowCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[shadow=true, shadowcolor=\\psfillcolor,shadowcolor=red]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(DviPsColors.RED, sh.getShadowCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamDoublecolor() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[doubleline=true, doublecolor =blue]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isDbleBorderable()) {
			assertEquals(DviPsColors.BLUE, sh.getDbleBordCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[doubleline=true, doublecolor=\\psfillcolor]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(DviPsColors.WHITE, sh.getDbleBordCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[doubleline=true, doublecolor=\\psfillcolor,doublecolor=red]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(DviPsColors.RED, sh.getDbleBordCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamFillingcolor() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=solid, fillcolor =blue]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isFillable()) {
			assertEquals(DviPsColors.BLUE, sh.getFillingCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=solid, fillcolor=\\psfillcolor]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(DviPsColors.WHITE, sh.getFillingCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=solid, fillcolor=\\psfillcolor,fillcolor=red]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(DviPsColors.RED, sh.getFillingCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamGradbegin() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradbegin =blue]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isInteriorStylable()) {
			assertEquals(DviPsColors.BLUE, sh.getGradColStart());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradbegin=\\psfillcolor]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(DviPsColors.WHITE, sh.getGradColStart());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradbegin=\\psfillcolor,gradbegin=red]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(DviPsColors.RED, sh.getGradColStart());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamGradend() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradend =blue]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isInteriorStylable()) {
			assertEquals(DviPsColors.BLUE, sh.getGradColEnd());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradend=\\psfillcolor]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$//$NON-NLS-2$
			assertEquals(DviPsColors.WHITE, sh.getGradColEnd());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=gradient, gradend=\\psfillcolor,gradend=red]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(DviPsColors.RED, sh.getGradColEnd());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamHatchcolor() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchcolor =blue]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isInteriorStylable()) {
			assertEquals(DviPsColors.BLUE, sh.getHatchingsCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchcolor=\\psfillcolor]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(DviPsColors.WHITE, sh.getHatchingsCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[fillstyle=clines, hatchcolor=\\psfillcolor,hatchcolor=red]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals(DviPsColors.RED, sh.getHatchingsCol());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}

	@Test
	public void testParamDoubleLine() throws ParseException {
		IShape sh = parser.parsePSTCode("\\" + getCommandName() + "[doubleline = true]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
		if(sh.isDbleBorderable()) {
			assertTrue(sh.hasDbleBord());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[doubleline =false]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertFalse(sh.hasDbleBord());
			assertTrue(PSTParser.errorLogs().isEmpty());
			sh = parser.parsePSTCode("\\" + getCommandName() + "[doubleline =true, doubleline=false]" + getBasicCoordinates()).get().getShapeAt(0); //$NON-NLS-1$ //$NON-NLS-2$
			assertFalse(sh.hasDbleBord());
			assertTrue(PSTParser.errorLogs().isEmpty());
		}
	}
}
