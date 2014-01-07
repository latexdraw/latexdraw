package test.parser.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.prop.IFreeHandProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IDot;
import net.sf.latexdraw.glib.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPscustom extends TestPSTParser {
	@Test
	public void testPsCustomRlineto() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\rlineto(2,3)}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomMovepath() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\movepath(2,3)}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomClosedshadow() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\closedshadow[linewidth=2cm]}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomOpenshadow() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\openshadow[linewidth=2cm]}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomMrestore() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\mrestore}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomMsave() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\msave}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomSwapaxes() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\swapaxes}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomRotate() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\rotate{10}}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomScale1() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\scale{10}}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomScale2() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\scale{10 20}}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomTranslate() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\translate(1,2cm)}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomFill() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\fill[linewidth=0.2cm]}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomStroke() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\stroke[linewidth=0.2cm]}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomGrestore() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\grestore}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomGsave() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\gsave}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomRcurveto() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\rcurveto(3.1,4.1)(3.2,4.3)(3,4)}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomFreeHandClose() throws ParseException {
		IGroup group = parser.parsePSTCode("\\pscustom{\\moveto(0.0,2.64)\\lineto(0.19,2.82)\\curveto(0.285,2.91)(1.49,3.16)(2.6,3.32)"+
				"\\curveto(3.71,3.48)(5.625,3.205)(6.43,2.77)\\curveto(7.235,2.335)(8.07,1.135)(8.1,0.37)"+
				"\\curveto(8.13,-0.395)(7.64,-1.63)(7.12,-2.1)\\curveto(6.6,-2.57)(5.45,-3.18)(4.82,-3.32)\\closepath}").get();
		assertEquals(1, group.size());
		IFreehand fh = (IFreehand)group.getShapeAt(0);
		assertEquals(7, fh.getNbPoints());
		assertEquals(0., fh.getPtAt(0).getX(), 0.001);
		assertEquals(-2.64*IShape.PPC, fh.getPtAt(0).getY(), 0.001);
		assertEquals(0.19*IShape.PPC, fh.getPtAt(1).getX(), 0.001);
		assertEquals(-2.82*IShape.PPC, fh.getPtAt(1).getY(), 0.001);
		assertEquals(2.6*IShape.PPC, fh.getPtAt(2).getX(), 0.001);
		assertEquals(-3.32*IShape.PPC, fh.getPtAt(2).getY(), 0.001);
		assertEquals(6.43*IShape.PPC, fh.getPtAt(3).getX(), 0.001);
		assertEquals(-2.77*IShape.PPC, fh.getPtAt(3).getY(), 0.001);
		assertEquals(8.1*IShape.PPC, fh.getPtAt(4).getX(), 0.001);
		assertEquals(-0.37*IShape.PPC, fh.getPtAt(4).getY(), 0.001);
		assertEquals(IFreeHandProp.FreeHandType.CURVES, fh.getType());
		assertEquals(0, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomFreeHandOpen() throws ParseException {
		IGroup group = parser.parsePSTCode("\\pscustom{\\moveto(0.0,2.64)\\lineto(0.19,2.82)\\curveto(0.285,2.91)(1.49,3.16)(2.6,3.32)"+
			"\\curveto(3.71,3.48)(5.625,3.205)(6.43,2.77)\\curveto(7.235,2.335)(8.07,1.135)(8.1,0.37)"+
			"\\curveto(8.13,-0.395)(7.64,-1.63)(7.12,-2.1)\\curveto(6.6,-2.57)(5.45,-3.18)(4.82,-3.32)}").get();
		assertEquals(1, group.size());
		IFreehand fh = (IFreehand)group.getShapeAt(0);
		assertEquals(7, fh.getNbPoints());
		assertEquals(0., fh.getPtAt(0).getX(), 0.001);
		assertEquals(-2.64*IShape.PPC, fh.getPtAt(0).getY(), 0.001);
		assertEquals(0.19*IShape.PPC, fh.getPtAt(1).getX(), 0.001);
		assertEquals(-2.82*IShape.PPC, fh.getPtAt(1).getY(), 0.001);
		assertEquals(2.6*IShape.PPC, fh.getPtAt(2).getX(), 0.001);
		assertEquals(-3.32*IShape.PPC, fh.getPtAt(2).getY(), 0.001);
		assertEquals(6.43*IShape.PPC, fh.getPtAt(3).getX(), 0.001);
		assertEquals(-2.77*IShape.PPC, fh.getPtAt(3).getY(), 0.001);
		assertEquals(8.1*IShape.PPC, fh.getPtAt(4).getX(), 0.001);
		assertEquals(-0.37*IShape.PPC, fh.getPtAt(4).getY(), 0.001);
		assertTrue(fh.isOpen());
		assertEquals(IFreeHandProp.FreeHandType.CURVES, fh.getType());
		assertEquals(0, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomMovetoCurvetoLineTo() throws ParseException {
		IGroup group = parser.parsePSTCode("\\"+getCommandName()+"{%\n\\moveto(1,2)\\curveto(3.1,4.1)(3.2,4.3)(3,4)\\lineto(5,6)}").get();
		assertEquals(1, group.size());
		IFreehand fh = (IFreehand)group.getShapeAt(0);
		assertEquals(3, fh.getNbPoints());
		assertEquals(1.*IShape.PPC, fh.getPtAt(0).getX(), 0.001);
		assertEquals(-2.*IShape.PPC, fh.getPtAt(0).getY(), 0.001);
		assertEquals(3.*IShape.PPC, fh.getPtAt(1).getX(), 0.001);
		assertEquals(-4.*IShape.PPC, fh.getPtAt(1).getY(), 0.001);
		assertEquals(5.*IShape.PPC, fh.getPtAt(2).getX(), 0.001);
		assertEquals(-6.*IShape.PPC, fh.getPtAt(2).getY(), 0.001);
		assertEquals(IFreeHandProp.FreeHandType.LINES, fh.getType());
		assertEquals(0, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomMovetoCurveto() throws ParseException {
		IFreehand fh = (IFreehand)parser.parsePSTCode("\\"+getCommandName()+"[linewidth=10cm]{%\n\\moveto(1,2)\\curveto(3.1,4.1)(3.2,4.3)(3,4)}").get().getShapeAt(0);
		assertEquals(2, fh.getNbPoints());
		assertEquals(1.*IShape.PPC, fh.getPtAt(0).getX(), 0.001);
		assertEquals(-2.*IShape.PPC, fh.getPtAt(0).getY(), 0.001);
		assertEquals(3.*IShape.PPC, fh.getPtAt(1).getX(), 0.001);
		assertEquals(-4.*IShape.PPC, fh.getPtAt(1).getY(), 0.001);
		assertEquals(IFreeHandProp.FreeHandType.CURVES, fh.getType());
		assertEquals(0, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomMovetoLineto() throws ParseException {
		IFreehand fh = (IFreehand)parser.parsePSTCode("\\"+getCommandName()+"[linewidth=10cm]{%\n\\moveto(1,2)\\lineto(3,4)}").get().getShapeAt(0);
		assertEquals(2, fh.getNbPoints());
		assertEquals(1.*IShape.PPC, fh.getPtAt(0).getX(), 0.001);
		assertEquals(-2.*IShape.PPC, fh.getPtAt(0).getY(), 0.001);
		assertEquals(3.*IShape.PPC, fh.getPtAt(1).getX(), 0.001);
		assertEquals(-4.*IShape.PPC, fh.getPtAt(1).getY(), 0.001);
		assertEquals(IFreeHandProp.FreeHandType.LINES, fh.getType());
		assertEquals(0, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomNothingWithNewpathCommand() throws ParseException {
		parser.parsePSTCode("\\"+getCommandName()+"{\\newpath\n}");
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomMustConsiderParametersCommand() throws ParseException {
		IPolyline line = (IPolyline)parser.parsePSTCode("\\"+getCommandName()+"[linewidth=10cm]{%\n\\psline(4,3)}").get().getShapeAt(0);
		assertEquals(10.*IShape.PPC, line.getThickness(), 0.001);
		assertEquals(0, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomStarCommand() throws ParseException {
		IRectangle rec = (IRectangle) parser.parsePSTCode("\\"+getCommandName()+"*{%\n\\psframe(2,2)\n}").get().getShapeAt(0);
		assertTrue(rec.isFilled());
		assertEquals(0, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomNotEmptyCommand() throws ParseException {
		IGroup group = parser.parsePSTCode("\\"+getCommandName()+"{%\n\\psdots(1,1)\n\\psframe(2,2)\n}").get();
		assertEquals(2, group.size());
		assertTrue(group.getShapeAt(0) instanceof IDot);
		assertTrue(group.getShapeAt(1) instanceof IRectangle);
		assertEquals(0, PSTParser.errorLogs().size());
	}


	@Test
	public void testPsCustomEmptyCommand() throws ParseException {
		assertTrue(parser.parsePSTCode("\\"+getCommandName()+"{}").get().isEmpty());
		assertEquals(0, PSTParser.errorLogs().size());
	}


	@Override
	public String getCommandName() {
		return "pscustom";
	}


	@Override
	public String getBasicCoordinates() {
		return "";
	}
}
