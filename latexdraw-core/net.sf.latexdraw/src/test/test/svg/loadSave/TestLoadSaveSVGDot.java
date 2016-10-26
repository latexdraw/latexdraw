package test.svg.loadSave;

import net.sf.latexdraw.models.interfaces.shape.Color;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.view.latex.DviPsColors;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestLoadSaveSVGDot extends TestLoadSaveSVGPositionShape<IDot> {
	@Before
	public void setUp() {
		shape = ShapeFactory.createDot(ShapeFactory.createPoint());
	}

	private void setDot(final double x, final double y, final DotStyle style, final double size, final Color lineCol, final Color fillCol) {
		shape.setPosition(x, y);
		shape.setDotStyle(style);
		shape.setDiametre(size);
		shape.setLineColour(lineCol);
		if(fillCol != null)
			shape.setFillingCol(fillCol);
	}

	@Override
	protected void setDefaultDimensions() {
		//
	}

	@Override
	protected void compareShapes(IDot d2) {
		assertEquals(shape.getDotStyle(), d2.getDotStyle());
		assertEquals(shape.getPosition(), d2.getPosition());
		assertEquals(shape.getDiametre(), d2.getDiametre(), 0.1);
		assertEquals(shape.getLineColour(), d2.getLineColour());
		assertEquals(shape.isFillable(), shape.isFillable());
		assertEquals(shape.isFilled(), shape.isFilled());
		if(shape.isFilled())
			assertEquals(shape.getFillingCol(), d2.getFillingCol());
	}

	@Test
	public void testLoadSaveDotDOT() {
		setDot(11.3, 82, DotStyle.DOT, 33, DviPsColors.RED, null);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotO() {
		setDot(11.3, 82, DotStyle.O, 22, DviPsColors.DARKGRAY, DviPsColors.YELLOW);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotSQUARE() {
		setDot(11.3, 82, DotStyle.SQUARE, 22, DviPsColors.DARKGRAY, DviPsColors.YELLOW);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotFSQUARE() {
		setDot(11.3, 82, DotStyle.FSQUARE, 22, DviPsColors.DARKGRAY, null);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotFPENTAGON() {
		setDot(11.3, 82, DotStyle.FPENTAGON, 33, DviPsColors.RED, null);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotPENTAGON() {
		setDot(11.3, 82, DotStyle.PENTAGON, 33, DviPsColors.RED, DviPsColors.BLUE);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotX() {
		setDot(11.3, 82, DotStyle.X, 23, DviPsColors.RED, null);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotFDIAMOND() {
		setDot(11.3, 82, DotStyle.FDIAMOND, 123, DviPsColors.CYAN, null);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotDIAMOND() {
		setDot(11.3, 82, DotStyle.DIAMOND, 123, DviPsColors.CYAN, DviPsColors.DARKGRAY);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotFTRIANGLE() {
		setDot(1.3, 82, DotStyle.FTRIANGLE, 12, DviPsColors.CYAN, null);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotTRIANGLE() {
		setDot(1.3, 82, DotStyle.TRIANGLE, 12, DviPsColors.CYAN, DviPsColors.GREEN);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotOTIMES() {
		setDot(1, 2, DotStyle.OTIMES, 54, DviPsColors.GREEN, null);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotPLUS() {
		setDot(145, 2, DotStyle.PLUS, 24, DviPsColors.MAGENTA, null);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotOPLUS() {
		setDot(111, 82.12, DotStyle.OPLUS, 2, DviPsColors.GRAY, null);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotASTERISK() {
		setDot(-20, 12.12, DotStyle.ASTERISK, 22, DviPsColors.BLUE, null);
		compareShapes(generateShape());
	}

	@Test
	public void testLoadSaveDotBAR() {
		setDot(20, -12.12, DotStyle.BAR, 3, DviPsColors.CYAN, null);
		compareShapes(generateShape());
	}
}
