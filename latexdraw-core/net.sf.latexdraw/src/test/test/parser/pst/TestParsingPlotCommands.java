package test.parser.pst;

import static org.junit.Assert.*;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPlotCommands extends TestPSTParser {
	@Test
	public void testParametricplot() throws ParseException {
		parser.parsePSTCode("\\parametricplot[plotstyle=dots,plotpoints=13]{-6}{6}{1.2 t exp 1.2 t neg exp}"); //$NON-NLS-1$
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testParametricplotStar() throws ParseException {
		parser.parsePSTCode("\\parametricplot*[plotstyle=dots,plotpoints=13]{-6}{6}{1.2 t exp 1.2 t neg exp}"); //$NON-NLS-1$
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test public void testPsplot() throws ParseException {
		final IPlot plot = (IPlot)parser.parsePSTCode("\\psplot{0}{720}{x sin}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(0.0, plot.getPlotMinX(), 0.0);
		assertEquals(720.0, plot.getPlotMaxX(), 0.0);
		assertEquals("x sin", plot.getPlotEquation()); //$NON-NLS-1$
	}


	@Test public void testPsplotNbPoints() throws ParseException {
		final IPlot plot = (IPlot)parser.parsePSTCode("\\psplot[plotpoints=213]{0}{720}{x sin}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(213, plot.getNbPlottedPoints());
	}


	@Test public void testPsplotPlotStyleCurve() throws ParseException {
		final IPlot plot = (IPlot)parser.parsePSTCode("\\psplot[plotstyle=curve]{0}{720}{x sin}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(IPlotProp.PlotStyle.CURVE, plot.getPlotStyle());
	}

	@Test public void testPsplotPlotStyleCCurve() throws ParseException {
		final IPlot plot = (IPlot)parser.parsePSTCode("\\psplot[plotstyle=ccurve]{0}{720}{x sin}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(IPlotProp.PlotStyle.CCURVE, plot.getPlotStyle());
	}

	@Test public void testPsplotPlotStyleECurve() throws ParseException {
		final IPlot plot = (IPlot)parser.parsePSTCode("\\psplot[plotstyle=ecurve]{0}{720}{x sin}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(IPlotProp.PlotStyle.ECURVE, plot.getPlotStyle());
	}

	@Test public void testPsplotPlotStyleDots() throws ParseException {
		final IPlot plot = (IPlot)parser.parsePSTCode("\\psplot[plotstyle=dots]{0}{720}{x sin}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(IPlotProp.PlotStyle.DOTS, plot.getPlotStyle());
	}

	@Test public void testPsplotPlotStyleLine() throws ParseException {
		final IPlot plot = (IPlot)parser.parsePSTCode("\\psplot[plotstyle=line]{0}{720}{x sin}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(IPlotProp.PlotStyle.LINE, plot.getPlotStyle());
	}

	@Test public void testPsplotPlotStylePloygon() throws ParseException {
		final IPlot plot = (IPlot)parser.parsePSTCode("\\psplot[plotstyle=polygon]{0}{720}{x sin}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(IPlotProp.PlotStyle.POLYGON, plot.getPlotStyle());
	}

	@Test public void testPsplotXUnit() throws ParseException {
		final IPlot plot = (IPlot)parser.parsePSTCode("\\psplot[xunit=0.1]{0}{720}{x sin}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(0.1, plot.getXScale(), 0.0);
	}

	@Test public void testPsplotYUnit() throws ParseException {
		final IPlot plot = (IPlot)parser.parsePSTCode("\\psplot[yunit=0.1]{0}{720}{x sin}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(0.1, plot.getYScale(), 0.0);
	}

	@Test
	public void testPsplotStar() throws ParseException {
		final IPlot plot = (IPlot)parser.parsePSTCode("\\psplot*[plotpoints=200]{0}{720}{x sin}").get().getShapeAt(0); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertEquals(0.0, plot.getPlotMinX(), 0.0);
		assertEquals(720.0, plot.getPlotMaxX(), 0.0);
		assertEquals("x sin", plot.getPlotEquation()); //$NON-NLS-1$
		assertEquals(IShape.FillingStyle.PLAIN, plot.getFillingStyle());
	}


	@Test
	public void testListplot() throws ParseException {
		parser.parsePSTCode("\\listplot[plotstyle=line]{\\mydata}"); //$NON-NLS-1$
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testListplotStar() throws ParseException {
		parser.parsePSTCode("\\listplot*[plotstyle=line]{\\mydata}"); //$NON-NLS-1$
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testFileplot() throws ParseException {
		parser.parsePSTCode("\\fileplot[plotstyle=line]{file}"); //$NON-NLS-1$
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testFileplotStar() throws ParseException {
		parser.parsePSTCode("\\fileplot*[plotstyle=line]{file}"); //$NON-NLS-1$
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testDataplot() throws ParseException {
		parser.parsePSTCode("\\dataplot[plotstyle=line]{\\cmdName}"); //$NON-NLS-1$
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testDataplotStar() throws ParseException {
		parser.parsePSTCode("\\dataplot*[plotstyle=line]{\\foo\\bar}"); //$NON-NLS-1$
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testSavedata() throws ParseException {
		parser.parsePSTCode("\\savedata{\\foo}[{{0, 0}, {1., 0.946083}, {2., 1.60541}, {3., 1.84865}, {4., 1.7582}," + //$NON-NLS-1$
				"{5., 1.54993}, {6., 1.42469}, {7., 1.4546}, {8., 1.57419}," + //$NON-NLS-1$
				"{9., 1.66504}, {10., 1.65835}, {11., 1.57831}, {12., 1.50497}," + //$NON-NLS-1$
				"{13., 1.49936}, {14., 1.55621}, {15., 1.61819}, {16., 1.6313}," + //$NON-NLS-1$
				"{17., 1.59014}, {18., 1.53661}, {19., 1.51863}, {20., 1.54824}}]"); //$NON-NLS-1$
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testReaddata() throws ParseException {
		parser.parsePSTCode("\\readdata{\\foo}{foo.data}"); //$NON-NLS-1$
		assertEquals(1, PSTParser.errorLogs().size());
	}


	@Test
	public void testPlotstyle() throws ParseException {
		parser.parsePSTCode("\\psframe[plotstyle=line](1,1)"); //$NON-NLS-1$
		parser.parsePSTCode("\\psframe[plotstyle=dots](1,1)"); //$NON-NLS-1$
		parser.parsePSTCode("\\psframe[plotstyle=polygon](1,1)"); //$NON-NLS-1$
		parser.parsePSTCode("\\psframe[plotstyle=curve](1,1)"); //$NON-NLS-1$
		parser.parsePSTCode("\\psframe[plotstyle=ecurve](1,1)"); //$NON-NLS-1$
		parser.parsePSTCode("\\psframe[plotstyle=ccurve](1,1)"); //$NON-NLS-1$
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	public String getCommandName() {
		return "dataplot"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return null;
	}
}
