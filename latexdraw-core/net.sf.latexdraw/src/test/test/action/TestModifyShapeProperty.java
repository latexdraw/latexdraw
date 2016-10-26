package test.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.*;
import net.sf.latexdraw.view.latex.DviPsColors;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;

public class TestModifyShapeProperty extends TestAbstractAction<ModifyShapeProperty> {
	protected IGroup g;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		g = ShapeFactory.createGroup();
	}

	@Test
	public void testDoArrowDotSizeNum() {
		IPolyline line1 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline line2 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		g.addShape(line1);
		g.addShape(rec);
		g.addShape(line2);
		line1.setDotSizeNum(0.12);
		line2.setDotSizeNum(0.23);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW_DOT_SIZE_NUM);
		action.setValue(0.33);
		assertTrue(action.doIt());

		assertEquals(0.33, line1.getDotSizeNum(), 0.0001);
		assertEquals(0.33, line2.getDotSizeNum(), 0.0001);
	}

	@Test
	public void testUndoArrowDotSizeNum() {
		testDoArrowDotSizeNum();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getDotSizeNum(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getDotSizeNum(), 0.0001);
	}

	@Test
	public void testRedoArrowDotSizeNum() {
		testUndoArrowDotSizeNum();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getDotSizeNum(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getDotSizeNum(), 0.0001);
	}

	@Test
	public void testDoArrowDotSizeDim() {
		IPolyline line1 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline line2 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		g.addShape(line1);
		g.addShape(rec);
		g.addShape(line2);
		line1.setDotSizeDim(0.12);
		line2.setDotSizeDim(0.23);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW_DOT_SIZE_DIM);
		action.setValue(0.33);
		assertTrue(action.doIt());

		assertEquals(0.33, line1.getDotSizeDim(), 0.0001);
		assertEquals(0.33, line2.getDotSizeDim(), 0.0001);
	}

	@Test
	public void testUndoArrowDotSizeDim() {
		testDoArrowDotSizeDim();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getDotSizeDim(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getDotSizeDim(), 0.0001);
	}

	@Test
	public void testRedoArrowDotSizeDim() {
		testUndoArrowDotSizeDim();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getDotSizeDim(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getDotSizeDim(), 0.0001);
	}

	@Test
	public void testDoArrowTBarSizeNum() {
		IPolyline line1 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline line2 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		g.addShape(line1);
		g.addShape(rec);
		g.addShape(line2);
		line1.setTBarSizeNum(0.12);
		line2.setTBarSizeNum(0.23);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW_T_BAR_SIZE_NUM);
		action.setValue(0.33);
		assertTrue(action.doIt());

		assertEquals(0.33, line1.getTBarSizeNum(), 0.0001);
		assertEquals(0.33, line2.getTBarSizeNum(), 0.0001);
	}

	@Test
	public void testUndoArrowTBarSizeNum() {
		testDoArrowTBarSizeNum();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getTBarSizeNum(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getTBarSizeNum(), 0.0001);
	}

	@Test
	public void testRedoArrowTBarSizeNum() {
		testUndoArrowTBarSizeNum();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getTBarSizeNum(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getTBarSizeNum(), 0.0001);
	}

	@Test
	public void testDoArrowTBarSizeDim() {
		IPolyline line1 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline line2 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		g.addShape(line1);
		g.addShape(rec);
		g.addShape(line2);
		line1.setTBarSizeDim(0.12);
		line2.setTBarSizeDim(0.23);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW_T_BAR_SIZE_DIM);
		action.setValue(0.33);
		assertTrue(action.doIt());

		assertEquals(0.33, line1.getTBarSizeDim(), 0.0001);
		assertEquals(0.33, line2.getTBarSizeDim(), 0.0001);
	}

	@Test
	public void testUndoArrowTBarSizeDim() {
		testDoArrowTBarSizeDim();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getTBarSizeDim(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getTBarSizeDim(), 0.0001);
	}

	@Test
	public void testRedoArrowTBarSizeDim() {
		testUndoArrowTBarSizeDim();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getTBarSizeDim(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getTBarSizeDim(), 0.0001);
	}

	@Test
	public void testDoArrowBracketNum() {
		IPolyline line1 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline line2 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		g.addShape(line1);
		g.addShape(rec);
		g.addShape(line2);
		line1.setBracketNum(0.12);
		line2.setBracketNum(0.23);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW_BRACKET_NUM);
		action.setValue(0.33);
		assertTrue(action.doIt());

		assertEquals(0.33, line1.getBracketNum(), 0.0001);
		assertEquals(0.33, line2.getBracketNum(), 0.0001);
	}

	@Test
	public void testUndoArrowBracketNum() {
		testDoArrowBracketNum();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getBracketNum(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getBracketNum(), 0.0001);
	}

	@Test
	public void testRedoArrowBracketNum() {
		testUndoArrowBracketNum();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getBracketNum(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getBracketNum(), 0.0001);
	}

	@Test
	public void testDoArrowRBracketNum() {
		IPolyline line1 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline line2 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		g.addShape(line1);
		g.addShape(rec);
		g.addShape(line2);
		line1.setRBracketNum(0.12);
		line2.setRBracketNum(0.23);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW_R_BRACKET_NUM);
		action.setValue(0.33);
		assertTrue(action.doIt());

		assertEquals(0.33, line1.getRBracketNum(), 0.0001);
		assertEquals(0.33, line2.getRBracketNum(), 0.0001);
	}

	@Test
	public void testUndoArrowRBracketNum() {
		testDoArrowRBracketNum();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getRBracketNum(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getRBracketNum(), 0.0001);
	}

	@Test
	public void testRedoArrowRBracketNum() {
		testUndoArrowRBracketNum();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getRBracketNum(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getRBracketNum(), 0.0001);
	}

	@Test
	public void testDoArrowSizeNum() {
		IPolyline line1 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline line2 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		g.addShape(line1);
		g.addShape(rec);
		g.addShape(line2);
		line1.setArrowSizeNum(0.12);
		line2.setArrowSizeNum(0.23);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW_SIZE_NUM);
		action.setValue(0.33);
		assertTrue(action.doIt());

		assertEquals(0.33, line1.getArrowSizeNum(), 0.0001);
		assertEquals(0.33, line2.getArrowSizeNum(), 0.0001);
	}

	@Test
	public void testUndoArrowSizeNum() {
		testDoArrowSizeNum();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getArrowSizeNum(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getArrowSizeNum(), 0.0001);
	}

	@Test
	public void testRedoArrowSizeNum() {
		testUndoArrowSizeNum();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getArrowSizeNum(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getArrowSizeNum(), 0.0001);
	}

	@Test
	public void testDoArrowSizeDim() {
		IPolyline line1 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline line2 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		g.addShape(line1);
		g.addShape(rec);
		g.addShape(line2);
		line1.setArrowSizeDim(0.12);
		line2.setArrowSizeDim(0.23);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW_SIZE_DIM);
		action.setValue(0.33);
		assertTrue(action.doIt());

		assertEquals(0.33, line1.getArrowSizeDim(), 0.0001);
		assertEquals(0.33, line2.getArrowSizeDim(), 0.0001);
	}

	@Test
	public void testUndoArrowSizeDim() {
		testDoArrowSizeDim();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getArrowSizeDim(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getArrowSizeDim(), 0.0001);
	}

	@Test
	public void testRedoArrowsSizeDim() {
		testUndoArrowSizeDim();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getArrowSizeDim(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getArrowSizeDim(), 0.0001);
	}

	@Test
	public void testDoArrowLength() {
		IPolyline line1 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline line2 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		g.addShape(line1);
		g.addShape(rec);
		g.addShape(line2);
		line1.setArrowLength(0.12);
		line2.setArrowLength(0.23);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW_LENGTH);
		action.setValue(0.33);
		assertTrue(action.doIt());

		assertEquals(0.33, line1.getArrowLength(), 0.0001);
		assertEquals(0.33, line2.getArrowLength(), 0.0001);
	}

	@Test
	public void testUndoArrowLength() {
		testDoArrowLength();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getArrowLength(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getArrowLength(), 0.0001);
	}

	@Test
	public void testRedoArrowLength() {
		testUndoArrowLength();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getArrowLength(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getArrowLength(), 0.0001);
	}

	@Test
	public void testDoArrowInset() {
		IPolyline line1 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline line2 = ShapeFactory.createPolyline(ShapeFactory.createPoint(), ShapeFactory.createPoint(10, 10));
		g.addShape(line1);
		g.addShape(rec);
		g.addShape(line2);
		line1.setArrowInset(0.12);
		line2.setArrowInset(0.23);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW_INSET);
		action.setValue(0.33);
		assertTrue(action.doIt());

		assertEquals(0.33, line1.getArrowInset(), 0.0001);
		assertEquals(0.33, line2.getArrowInset(), 0.0001);
	}

	@Test
	public void testUndoArrowInset() {
		testDoArrowInset();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getArrowInset(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getArrowInset(), 0.0001);
	}

	@Test
	public void testRedoArrowInset() {
		testUndoArrowInset();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getArrowInset(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getArrowInset(), 0.0001);
	}

	@Test
	public void testDoGridStart() {
		IGrid grid = ShapeFactory.createGrid(ShapeFactory.createPoint());
		IRectangle rec = ShapeFactory.createRectangle();
		IAxes axe = ShapeFactory.createAxes(ShapeFactory.createPoint());
		g.addShape(grid);
		g.addShape(rec);
		g.addShape(axe);
		grid.setGridStart(-3, -2);
		axe.setGridStart(-1, -4);
		action.setGroup(g);
		action.setProperty(ShapeProperties.GRID_START);
		action.setValue(ShapeFactory.createPoint(-5, -6));
		assertTrue(action.doIt());

		assertEquals(-5., grid.getGridStartX(), 0.0001);
		assertEquals(-6., grid.getGridStartY(), 0.0001);
		assertEquals(-5., axe.getGridStartX(), 0.0001);
		assertEquals(-6., axe.getGridStartY(), 0.0001);
	}

	@Test
	public void testUndoGridStart() {
		testDoGridStart();
		action.undo();
		assertEquals(-3., ((IGrid)g.getShapeAt(0)).getGridStartX(), 0.0001);
		assertEquals(-2., ((IGrid)g.getShapeAt(0)).getGridStartY(), 0.0001);
		assertEquals(-1., ((IAxes)g.getShapeAt(2)).getGridStartX(), 0.0001);
		assertEquals(-4., ((IAxes)g.getShapeAt(2)).getGridStartY(), 0.0001);
	}

	@Test
	public void testRedoGridStart() {
		testUndoGridStart();
		action.redo();
		assertEquals(-5., ((IGrid)g.getShapeAt(0)).getGridStartX(), 0.0001);
		assertEquals(-6., ((IGrid)g.getShapeAt(0)).getGridStartY(), 0.0001);
		assertEquals(-5., ((IAxes)g.getShapeAt(2)).getGridStartX(), 0.0001);
		assertEquals(-6., ((IAxes)g.getShapeAt(2)).getGridStartY(), 0.0001);
	}

	@Test
	public void testDoDotSize() {
		IDot d1 = ShapeFactory.createDot(ShapeFactory.createPoint());
		IRectangle rec = ShapeFactory.createRectangle();
		IDot d2 = ShapeFactory.createDot(ShapeFactory.createPoint());
		g.addShape(d1);
		g.addShape(rec);
		g.addShape(d2);
		d1.setDiametre(11.);
		d2.setDiametre(22.2);
		action.setGroup(g);
		action.setProperty(ShapeProperties.DOT_SIZE);
		action.setValue(33.3);
		assertTrue(action.doIt());

		assertEquals(33.3, d1.getDiametre(), 0.0001);
		assertEquals(33.3, d2.getDiametre(), 0.0001);
	}

	@Test
	public void testUndoDotSize() {
		testDoDotSize();
		action.undo();
		assertEquals(11., ((IDot)g.getShapeAt(0)).getDiametre(), 0.0001);
		assertEquals(22.2, ((IDot)g.getShapeAt(2)).getDiametre(), 0.0001);
	}

	@Test
	public void testRedoDotSize() {
		testUndoDotSize();
		action.redo();
		assertEquals(33.3, ((IDot)g.getShapeAt(0)).getDiametre(), 0.0001);
		assertEquals(33.3, ((IDot)g.getShapeAt(2)).getDiametre(), 0.0001);
	}

	@Test
	public void testDoDotStyle() {
		IDot d1 = ShapeFactory.createDot(ShapeFactory.createPoint());
		IRectangle rec = ShapeFactory.createRectangle();
		IDot d2 = ShapeFactory.createDot(ShapeFactory.createPoint());
		g.addShape(d1);
		g.addShape(rec);
		g.addShape(d2);
		d1.setDotStyle(DotStyle.BAR);
		d2.setDotStyle(DotStyle.PLUS);
		action.setGroup(g);
		action.setProperty(ShapeProperties.DOT_STYLE);
		action.setValue(DotStyle.FDIAMOND);
		assertTrue(action.doIt());

		assertEquals(DotStyle.FDIAMOND, d1.getDotStyle());
		assertEquals(DotStyle.FDIAMOND, d2.getDotStyle());
	}

	@Test
	public void testUndoDotStyle() {
		testDoDotStyle();
		action.undo();
		assertEquals(DotStyle.BAR, ((IDot)g.getShapeAt(0)).getDotStyle());
		assertEquals(DotStyle.PLUS, ((IDot)g.getShapeAt(2)).getDotStyle());
	}

	@Test
	public void testRedoDotStyle() {
		testUndoDotStyle();
		action.redo();
		assertEquals(DotStyle.FDIAMOND, ((IDot)g.getShapeAt(0)).getDotStyle());
		assertEquals(DotStyle.FDIAMOND, ((IDot)g.getShapeAt(2)).getDotStyle());
	}

	@Test
	public void testDoLineStyle() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setLineStyle(LineStyle.DASHED);
		rec2.setLineStyle(LineStyle.DOTTED);
		action.setGroup(g);
		action.setProperty(ShapeProperties.LINE_STYLE);
		action.setValue(LineStyle.SOLID);
		assertTrue(action.doIt());

		assertEquals(LineStyle.SOLID, rec1.getLineStyle());
		assertEquals(LineStyle.SOLID, rec2.getLineStyle());
	}

	@Test
	public void testUndoLineStyle() {
		testDoLineStyle();
		action.undo();
		assertEquals(LineStyle.DASHED, g.getShapeAt(0).getLineStyle());
		assertEquals(LineStyle.DOTTED, g.getShapeAt(2).getLineStyle());
	}

	@Test
	public void testRedoLineStyle() {
		testUndoLineStyle();
		action.redo();
		assertEquals(LineStyle.SOLID, g.getShapeAt(0).getLineStyle());
		assertEquals(LineStyle.SOLID, g.getShapeAt(2).getLineStyle());
	}

	@Test
	public void testDoFillingStyle() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setFillingStyle(FillingStyle.CLINES_PLAIN);
		rec2.setFillingStyle(FillingStyle.GRAD);
		action.setGroup(g);
		action.setProperty(ShapeProperties.FILLING_STYLE);
		action.setValue(FillingStyle.VLINES);
		assertTrue(action.doIt());

		assertEquals(FillingStyle.VLINES, rec1.getFillingStyle());
		assertEquals(FillingStyle.VLINES, rec2.getFillingStyle());
	}

	@Test
	public void testUndoFillingStyle() {
		testDoFillingStyle();
		action.undo();
		assertEquals(FillingStyle.CLINES_PLAIN, g.getShapeAt(0).getFillingStyle());
		assertEquals(FillingStyle.GRAD, g.getShapeAt(2).getFillingStyle());
	}

	@Test
	public void testRedoFillingStyle() {
		testUndoFillingStyle();
		action.redo();
		assertEquals(FillingStyle.VLINES, g.getShapeAt(0).getFillingStyle());
		assertEquals(FillingStyle.VLINES, g.getShapeAt(2).getFillingStyle());
	}

	@Test
	public void testDoThickness() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setThickness(12.5);
		rec2.setThickness(33.);
		action.setGroup(g);
		action.setProperty(ShapeProperties.LINE_THICKNESS);
		action.setValue(101.);
		assertTrue(action.doIt());

		assertEquals(101., rec1.getThickness(), 0.0001);
		assertEquals(101., rec2.getThickness(), 0.0001);
	}

	@Test
	public void testUndoThickness() {
		testDoThickness();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getThickness(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getThickness(), 0.0001);
	}

	@Test
	public void testRedoThickness() {
		testUndoThickness();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getThickness(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getThickness(), 0.0001);
	}

	@Test
	public void testDoShadowAngle() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setShadowAngle(12.5);
		rec2.setShadowAngle(33.);
		action.setGroup(g);
		action.setProperty(ShapeProperties.SHADOW_ANGLE);
		action.setValue(101.);
		assertTrue(action.doIt());

		assertEquals(101., rec1.getShadowAngle(), 0.0001);
		assertEquals(101., rec2.getShadowAngle(), 0.0001);
	}

	@Test
	public void testUndoShadowAngle() {
		testDoShadowAngle();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getShadowAngle(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getShadowAngle(), 0.0001);
	}

	@Test
	public void testRedoShadowAngle() {
		testUndoShadowAngle();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getShadowAngle(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getShadowAngle(), 0.0001);
	}

	@Test
	public void testDoShadowSize() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setShadowSize(12.5);
		rec2.setShadowSize(33.);
		action.setGroup(g);
		action.setProperty(ShapeProperties.SHADOW_SIZE);
		action.setValue(101.);
		assertTrue(action.doIt());

		assertEquals(101., rec1.getShadowSize(), 0.0001);
		assertEquals(101., rec2.getShadowSize(), 0.0001);
	}

	@Test
	public void testUndoShadowSize() {
		testDoShadowSize();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getShadowSize(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getShadowSize(), 0.0001);
	}

	@Test
	public void testRedoShadowSize() {
		testUndoShadowSize();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getShadowSize(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getShadowSize(), 0.0001);
	}

	@Test
	public void testDoHasShadow() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setHasShadow(true);
		rec2.setHasShadow(false);
		action.setGroup(g);
		action.setProperty(ShapeProperties.SHADOW);
		action.setValue(true);
		assertTrue(action.doIt());

		assertTrue(rec1.hasShadow());
		assertTrue(rec2.hasShadow());
	}

	@Test
	public void testUndoHasShadow() {
		testDoHasShadow();
		action.undo();
		assertTrue(g.getShapeAt(0).hasShadow());
		assertFalse(g.getShapeAt(2).hasShadow());
	}

	@Test
	public void testRedoHasShadow() {
		testUndoHasShadow();
		action.redo();
		assertTrue(g.getShapeAt(0).hasShadow());
		assertTrue(g.getShapeAt(2).hasShadow());
	}

	@Test
	public void testDoDbleBordSep() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setDbleBordSep(12.5);
		rec2.setDbleBordSep(33.);
		action.setGroup(g);
		action.setProperty(ShapeProperties.DBLE_BORDERS_SIZE);
		action.setValue(101.);
		assertTrue(action.doIt());

		assertEquals(101., rec1.getDbleBordSep(), 0.0001);
		assertEquals(101., rec2.getDbleBordSep(), 0.0001);
	}

	@Test
	public void testUndoDbleBordSep() {
		testDoDbleBordSep();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getDbleBordSep(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getDbleBordSep(), 0.0001);
	}

	@Test
	public void testRedoDbleBordSep() {
		testUndoDbleBordSep();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getDbleBordSep(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getDbleBordSep(), 0.0001);
	}

	@Test
	public void testDoHasDbleBord() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setHasDbleBord(true);
		rec2.setHasDbleBord(false);
		action.setGroup(g);
		action.setProperty(ShapeProperties.DBLE_BORDERS);
		action.setValue(true);
		assertTrue(action.doIt());

		assertTrue(rec1.hasDbleBord());
		assertTrue(rec2.hasDbleBord());
	}

	@Test
	public void testUndoHasDbleBord() {
		testDoHasDbleBord();
		action.undo();
		assertTrue(g.getShapeAt(0).hasDbleBord());
		assertFalse(g.getShapeAt(2).hasDbleBord());
	}

	@Test
	public void testRedoHasDbleBord() {
		testUndoHasDbleBord();
		action.redo();
		assertTrue(g.getShapeAt(0).hasDbleBord());
		assertTrue(g.getShapeAt(2).hasDbleBord());
	}

	@Test
	public void testDoFillingDotCol() {
		IDot d1 = ShapeFactory.createDot(ShapeFactory.createPoint());
		IRectangle rec = ShapeFactory.createRectangle();
		IDot d2 = ShapeFactory.createDot(ShapeFactory.createPoint());
		d1.setDotStyle(DotStyle.DIAMOND);
		d2.setDotStyle(DotStyle.DIAMOND);
		g.addShape(d1);
		g.addShape(rec);
		g.addShape(d2);
		d1.setDotFillingCol(DviPsColors.RED);
		d2.setDotFillingCol(DviPsColors.GREEN);
		rec.setFillingCol(DviPsColors.CYAN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.DOT_FILLING_COL);
		action.setValue(DviPsColors.GRAY);
		assertTrue(action.doIt());

		assertEquals(DviPsColors.GRAY, d1.getDotFillingCol());
		assertEquals(DviPsColors.GRAY, d2.getDotFillingCol());
		assertEquals(DviPsColors.CYAN, rec.getFillingCol());
	}

	@Test
	public void testUndoFillingDotCol() {
		testDoFillingDotCol();
		action.undo();
		assertEquals(DviPsColors.RED, ((IDot)g.getShapeAt(0)).getDotFillingCol());
		assertEquals(DviPsColors.CYAN, g.getShapeAt(1).getFillingCol());
		assertEquals(DviPsColors.GREEN, ((IDot)g.getShapeAt(2)).getDotFillingCol());
	}

	@Test
	public void testRedoFillingDotCol() {
		testUndoFillingDotCol();
		action.redo();
		assertEquals(DviPsColors.GRAY, ((IDot)g.getShapeAt(0)).getDotFillingCol());
		assertEquals(DviPsColors.CYAN, g.getShapeAt(1).getFillingCol());
		assertEquals(DviPsColors.GRAY, ((IDot)g.getShapeAt(2)).getDotFillingCol());
	}

	@Test
	public void testDoStartGradCol() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IRectangle rec2 = ShapeFactory.createRectangle();
		IDot dot = ShapeFactory.createDot(ShapeFactory.createPoint());
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setGradColStart(DviPsColors.RED);
		rec2.setGradColStart(DviPsColors.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_GRADIENT_START);
		action.setValue(DviPsColors.GRAY);
		assertTrue(action.doIt());

		assertEquals(DviPsColors.GRAY, rec1.getGradColStart());
		assertEquals(DviPsColors.GRAY, rec2.getGradColStart());
	}

	@Test
	public void testUndoStartGradCol() {
		testDoStartGradCol();
		action.undo();
		assertEquals(DviPsColors.RED, g.getShapeAt(0).getGradColStart());
		assertEquals(DviPsColors.GREEN, g.getShapeAt(2).getGradColStart());
	}

	@Test
	public void testRedoStartGradCol() {
		testUndoStartGradCol();
		action.redo();
		assertEquals(DviPsColors.GRAY, g.getShapeAt(0).getGradColStart());
		assertEquals(DviPsColors.GRAY, g.getShapeAt(2).getGradColStart());
	}

	@Test
	public void testDoEndGradCol() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IRectangle rec2 = ShapeFactory.createRectangle();
		IDot dot = ShapeFactory.createDot(ShapeFactory.createPoint());
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setGradColEnd(DviPsColors.RED);
		rec2.setGradColEnd(DviPsColors.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_GRADIENT_END);
		action.setValue(DviPsColors.GRAY);
		assertTrue(action.doIt());

		assertEquals(DviPsColors.GRAY, rec1.getGradColEnd());
		assertEquals(DviPsColors.GRAY, rec2.getGradColEnd());
	}

	@Test
	public void testUndoEndGradCol() {
		testDoEndGradCol();
		action.undo();
		assertEquals(DviPsColors.RED, g.getShapeAt(0).getGradColEnd());
		assertEquals(DviPsColors.GREEN, g.getShapeAt(2).getGradColEnd());
	}

	@Test
	public void testRedoEndGradCol() {
		testUndoEndGradCol();
		action.redo();
		assertEquals(DviPsColors.GRAY, g.getShapeAt(0).getGradColEnd());
		assertEquals(DviPsColors.GRAY, g.getShapeAt(2).getGradColEnd());
	}

	@Test
	public void testDoShadowCol() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IRectangle rec2 = ShapeFactory.createRectangle();
		IDot dot = ShapeFactory.createDot(ShapeFactory.createPoint());
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setShadowCol(DviPsColors.RED);
		rec2.setShadowCol(DviPsColors.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.SHADOW_COLOUR);
		action.setValue(DviPsColors.GRAY);
		assertTrue(action.doIt());

		assertEquals(DviPsColors.GRAY, rec1.getShadowCol());
		assertEquals(DviPsColors.GRAY, rec2.getShadowCol());
	}

	@Test
	public void testUndoShadowCol() {
		testDoShadowCol();
		action.undo();
		assertEquals(DviPsColors.RED, g.getShapeAt(0).getShadowCol());
		assertEquals(DviPsColors.GREEN, g.getShapeAt(2).getShadowCol());
	}

	@Test
	public void testRedoShadowCol() {
		testUndoShadowCol();
		action.redo();
		assertEquals(DviPsColors.GRAY, g.getShapeAt(0).getShadowCol());
		assertEquals(DviPsColors.GRAY, g.getShapeAt(2).getShadowCol());
	}

	@Test
	public void testDoDbleBordCol() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IRectangle rec2 = ShapeFactory.createRectangle();
		IDot dot = ShapeFactory.createDot(ShapeFactory.createPoint());
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setDbleBordCol(DviPsColors.RED);
		rec2.setDbleBordCol(DviPsColors.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_DBLE_BORD);
		action.setValue(DviPsColors.GRAY);
		assertTrue(action.doIt());

		assertEquals(DviPsColors.GRAY, rec1.getDbleBordCol());
		assertEquals(DviPsColors.GRAY, rec2.getDbleBordCol());
	}

	@Test
	public void testUndoDbleBordCol() {
		testDoDbleBordCol();
		action.undo();
		assertEquals(DviPsColors.RED, g.getShapeAt(0).getDbleBordCol());
		assertEquals(DviPsColors.GREEN, g.getShapeAt(2).getDbleBordCol());
	}

	@Test
	public void testRedoDbleBordCol() {
		testUndoDbleBordCol();
		action.redo();
		assertEquals(DviPsColors.GRAY, g.getShapeAt(0).getDbleBordCol());
		assertEquals(DviPsColors.GRAY, g.getShapeAt(2).getDbleBordCol());
	}

	@Test
	public void testDoHatchingsCol() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IRectangle rec2 = ShapeFactory.createRectangle();
		IDot dot = ShapeFactory.createDot(ShapeFactory.createPoint());
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setHatchingsCol(DviPsColors.RED);
		rec2.setHatchingsCol(DviPsColors.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_HATCHINGS);
		action.setValue(DviPsColors.GRAY);
		assertTrue(action.doIt());

		assertEquals(DviPsColors.GRAY, rec1.getHatchingsCol());
		assertEquals(DviPsColors.GRAY, rec2.getHatchingsCol());
	}

	@Test
	public void testUndoHatchingsCol() {
		testDoHatchingsCol();
		action.undo();
		assertEquals(DviPsColors.RED, g.getShapeAt(0).getHatchingsCol());
		assertEquals(DviPsColors.GREEN, g.getShapeAt(2).getHatchingsCol());
	}

	@Test
	public void testRedoHatchingsCol() {
		testUndoHatchingsCol();
		action.redo();
		assertEquals(DviPsColors.GRAY, g.getShapeAt(0).getHatchingsCol());
		assertEquals(DviPsColors.GRAY, g.getShapeAt(2).getHatchingsCol());
	}

	@Test
	public void testDoFillingCol() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IRectangle rec2 = ShapeFactory.createRectangle();
		IDot dot = ShapeFactory.createDot(ShapeFactory.createPoint());
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setFillingCol(DviPsColors.RED);
		rec2.setFillingCol(DviPsColors.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_FILLING);
		action.setValue(DviPsColors.GRAY);
		assertTrue(action.doIt());

		assertEquals(DviPsColors.GRAY, rec1.getFillingCol());
		assertEquals(DviPsColors.GRAY, rec2.getFillingCol());
	}

	@Test
	public void testUndoFillingCol() {
		testDoFillingCol();
		action.undo();
		assertEquals(DviPsColors.RED, g.getShapeAt(0).getFillingCol());
		assertEquals(DviPsColors.GREEN, g.getShapeAt(2).getFillingCol());
	}

	@Test
	public void testRedoFillingCol() {
		testUndoFillingCol();
		action.redo();
		assertEquals(DviPsColors.GRAY, g.getShapeAt(0).getFillingCol());
		assertEquals(DviPsColors.GRAY, g.getShapeAt(2).getFillingCol());
	}

	@Test
	public void testDoLineArc() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setLineArc(0.1);
		rec2.setLineArc(0.2);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ROUND_CORNER_VALUE);
		action.setValue(0.3);
		assertTrue(action.doIt());

		assertEquals(0.3, rec1.getLineArc(), 0.0001);
		assertEquals(0.3, rec2.getLineArc(), 0.0001);
	}

	@Test
	public void testUndoLineArc() {
		testDoLineArc();
		action.undo();
		assertEquals(0.1, ((ILineArcProp)g.getShapeAt(0)).getLineArc(), 0.0001);
		assertEquals(0.2, ((ILineArcProp)g.getShapeAt(2)).getLineArc(), 0.0001);
	}

	@Test
	public void testRedoLineArc() {
		testUndoLineArc();
		action.redo();
		assertEquals(0.3, ((ILineArcProp)g.getShapeAt(0)).getLineArc(), 0.0001);
		assertEquals(0.3, ((ILineArcProp)g.getShapeAt(2)).getLineArc(), 0.0001);
	}

	@Test
	public void testDoGradMidPt() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setGradMidPt(0.1);
		rec2.setGradMidPt(0.2);
		action.setGroup(g);
		action.setProperty(ShapeProperties.GRAD_MID_POINT);
		action.setValue(0.3);
		assertTrue(action.doIt());

		assertEquals(0.3, rec1.getGradMidPt(), 0.0001);
		assertEquals(0.3, rec2.getGradMidPt(), 0.0001);
	}

	@Test
	public void testUndoGradMidPt() {
		testDoGradMidPt();
		action.undo();
		assertEquals(0.1, g.getShapeAt(0).getGradMidPt(), 0.0001);
		assertEquals(0.2, g.getShapeAt(2).getGradMidPt(), 0.0001);
	}

	@Test
	public void testRedoGradMidPt() {
		testUndoGradMidPt();
		action.redo();
		assertEquals(0.3, g.getShapeAt(0).getGradMidPt(), 0.0001);
		assertEquals(0.3, g.getShapeAt(2).getGradMidPt(), 0.0001);
	}

	@Test
	public void testDoGradAngle() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setGradAngle(12.5);
		rec2.setGradAngle(33.);
		action.setGroup(g);
		action.setProperty(ShapeProperties.GRAD_ANGLE);
		action.setValue(101.);
		assertTrue(action.doIt());

		assertEquals(101., rec1.getGradAngle(), 0.0001);
		assertEquals(101., rec2.getGradAngle(), 0.0001);
	}

	@Test
	public void testUndoGradAngle() {
		testDoGradAngle();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getGradAngle(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getGradAngle(), 0.0001);
	}

	@Test
	public void testRedoGradAngle() {
		testUndoGradAngle();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getGradAngle(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getGradAngle(), 0.0001);
	}

	@Test
	public void testDoHatchingsSep() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setHatchingsSep(12.5);
		rec2.setHatchingsSep(33.);
		action.setGroup(g);
		action.setProperty(ShapeProperties.HATCHINGS_SEP);
		action.setValue(101.);
		assertTrue(action.doIt());

		assertEquals(101., rec1.getHatchingsSep(), 0.0001);
		assertEquals(101., rec2.getHatchingsSep(), 0.0001);
	}

	@Test
	public void testUndoHatchingsSep() {
		testDoHatchingsSep();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getHatchingsSep(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getHatchingsSep(), 0.0001);
	}

	@Test
	public void testRedoHatchingsSep() {
		testUndoHatchingsSep();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getHatchingsSep(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getHatchingsSep(), 0.0001);
	}

	@Test
	public void testDoHatchingsWidth() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setHatchingsWidth(12.5);
		rec2.setHatchingsWidth(33.);
		action.setGroup(g);
		action.setProperty(ShapeProperties.HATCHINGS_WIDTH);
		action.setValue(101.);
		assertTrue(action.doIt());

		assertEquals(101., rec1.getHatchingsWidth(), 0.0001);
		assertEquals(101., rec2.getHatchingsWidth(), 0.0001);
	}

	@Test
	public void testUndoHatchingsWidth() {
		testDoHatchingsWidth();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getHatchingsWidth(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getHatchingsWidth(), 0.0001);
	}

	@Test
	public void testRedoHatchingsWidth() {
		testUndoHatchingsWidth();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getHatchingsWidth(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getHatchingsWidth(), 0.0001);
	}

	@Test
	public void testDoHatchingsAngle() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IText p = ShapeFactory.createText();
		IRectangle rec2 = ShapeFactory.createRectangle();
		g.addShape(rec1);
		g.addShape(p);
		g.addShape(rec2);
		rec1.setHatchingsAngle(12.5);
		rec2.setHatchingsAngle(-33.);
		action.setGroup(g);
		action.setProperty(ShapeProperties.HATCHINGS_ANGLE);
		action.setValue(101.);
		assertTrue(action.doIt());

		assertEquals(101., rec1.getHatchingsAngle(), 0.0001);
		assertEquals(101., rec2.getHatchingsAngle(), 0.0001);
	}

	@Test
	public void testUndoHatchingsAngle() {
		testDoHatchingsAngle();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getHatchingsAngle(), 0.0001);
		assertEquals(-33., g.getShapeAt(2).getHatchingsAngle(), 0.0001);
	}

	@Test
	public void testRedoHatchingsAngle() {
		testUndoHatchingsAngle();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getHatchingsAngle(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getHatchingsAngle(), 0.0001);
	}

	@Test
	public void testDoText() {
		IText p1 = ShapeFactory.createText();
		IRectangle rec = ShapeFactory.createRectangle();
		IText p2 = ShapeFactory.createText();
		g.addShape(p1);
		g.addShape(rec);
		g.addShape(p2);
		p1.setText("foo1"); //$NON-NLS-1$
		p2.setText("foo2"); //$NON-NLS-1$
		action.setGroup(g);
		action.setProperty(ShapeProperties.TEXT);
		action.setValue("foo3"); //$NON-NLS-1$
		assertTrue(action.doIt());

		assertEquals("foo3", p1.getText()); //$NON-NLS-1$
		assertEquals("foo3", p2.getText()); //$NON-NLS-1$
	}

	@Test
	public void testUndoText() {
		testDoText();
		action.undo();
		assertEquals("foo1", ((IText)g.getShapeAt(0)).getText()); //$NON-NLS-1$
		assertEquals("foo2", ((IText)g.getShapeAt(2)).getText()); //$NON-NLS-1$
	}

	@Test
	public void testRedoText() {
		testUndoText();
		action.redo();
		assertEquals("foo3", ((IText)g.getShapeAt(0)).getText()); //$NON-NLS-1$
		assertEquals("foo3", ((IText)g.getShapeAt(2)).getText()); //$NON-NLS-1$
	}

	@Test
	public void testDoTextPosition() {
		IText p1 = ShapeFactory.createText();
		IRectangle rec = ShapeFactory.createRectangle();
		IText p2 = ShapeFactory.createText();
		g.addShape(p1);
		g.addShape(rec);
		g.addShape(p2);
		p1.setTextPosition(TextPosition.BOT_LEFT);
		p2.setTextPosition(TextPosition.TOP);
		action.setGroup(g);
		action.setProperty(ShapeProperties.TEXT_POSITION);
		action.setValue(TextPosition.BOT);
		assertTrue(action.doIt());

		assertEquals(TextPosition.BOT, p1.getTextPosition());
		assertEquals(TextPosition.BOT, p2.getTextPosition());
	}

	@Test
	public void testUndoTextPosition() {
		testDoTextPosition();
		action.undo();
		assertEquals(TextPosition.BOT_LEFT, ((IText)g.getShapeAt(0)).getTextPosition());
		assertEquals(TextPosition.TOP, ((IText)g.getShapeAt(2)).getTextPosition());
	}

	@Test
	public void testRedoTextPosition() {
		testUndoTextPosition();
		action.redo();
		assertEquals(TextPosition.BOT, ((IText)g.getShapeAt(0)).getTextPosition());
		assertEquals(TextPosition.BOT, ((IText)g.getShapeAt(2)).getTextPosition());
	}

	@Test
	public void testDoArrowStyle1() {
		IPolyline p1 = ShapeFactory.createPolyline();
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline p2 = ShapeFactory.createPolyline();
		g.addShape(p1);
		g.addShape(rec);
		g.addShape(p2);
		p1.setArrowStyle(ArrowStyle.LEFT_DBLE_ARROW, 1);
		p2.setArrowStyle(ArrowStyle.RIGHT_DBLE_ARROW, 1);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW2_STYLE);
		action.setValue(ArrowStyle.BAR_IN);
		assertTrue(action.doIt());

		assertEquals(ArrowStyle.BAR_IN, p1.getArrowStyle(1));
		assertEquals(ArrowStyle.BAR_IN, p2.getArrowStyle(1));
	}

	@Test
	public void testUndoArrowStyle1() {
		testDoArrowStyle1();
		action.undo();
		assertEquals(ArrowStyle.LEFT_DBLE_ARROW, ((IArrowableShape)g.getShapeAt(0)).getArrowStyle(1));
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW, ((IArrowableShape)g.getShapeAt(2)).getArrowStyle(1));
	}

	@Test
	public void testRedoArrowStyle1() {
		testUndoArrowStyle1();
		action.redo();
		assertEquals(ArrowStyle.BAR_IN, ((IArrowableShape)g.getShapeAt(0)).getArrowStyle(1));
		assertEquals(ArrowStyle.BAR_IN, ((IArrowableShape)g.getShapeAt(2)).getArrowStyle(1));
	}

	@Test
	public void testDoArrowStyle0() {
		IPolyline p1 = ShapeFactory.createPolyline();
		IRectangle rec = ShapeFactory.createRectangle();
		IPolyline p2 = ShapeFactory.createPolyline();
		g.addShape(p1);
		g.addShape(rec);
		g.addShape(p2);
		p1.setArrowStyle(ArrowStyle.LEFT_DBLE_ARROW, 0);
		p2.setArrowStyle(ArrowStyle.RIGHT_DBLE_ARROW, 0);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARROW1_STYLE);
		action.setValue(ArrowStyle.BAR_IN);
		assertTrue(action.doIt());

		assertEquals(ArrowStyle.BAR_IN, p1.getArrowStyle(0));
		assertEquals(ArrowStyle.BAR_IN, p2.getArrowStyle(0));
	}

	@Test
	public void testUndoArrowStyle0() {
		testDoArrowStyle0();
		action.undo();
		assertEquals(ArrowStyle.LEFT_DBLE_ARROW, ((IArrowableShape)g.getShapeAt(0)).getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW, ((IArrowableShape)g.getShapeAt(2)).getArrowStyle(0));
	}

	@Test
	public void testRedoArrowStyle0() {
		testUndoArrowStyle0();
		action.redo();
		assertEquals(ArrowStyle.BAR_IN, ((IArrowableShape)g.getShapeAt(0)).getArrowStyle(0));
		assertEquals(ArrowStyle.BAR_IN, ((IArrowableShape)g.getShapeAt(2)).getArrowStyle(0));
	}

	@Test
	public void testDoArcStyle() {
		IArc arc1 = ShapeFactory.createCircleArc();
		IRectangle rec = ShapeFactory.createRectangle();
		IArc arc2 = ShapeFactory.createCircleArc();
		g.addShape(arc1);
		g.addShape(rec);
		g.addShape(arc2);
		arc1.setArcStyle(ArcStyle.CHORD);
		arc2.setArcStyle(ArcStyle.WEDGE);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARC_STYLE);
		action.setValue(ArcStyle.ARC);
		assertTrue(action.doIt());

		assertEquals(ArcStyle.ARC, arc1.getArcStyle());
		assertEquals(ArcStyle.ARC, arc2.getArcStyle());
	}

	@Test
	public void testUndoArcStyle() {
		testDoArcStyle();
		action.undo();
		assertEquals(ArcStyle.CHORD, ((IArc)g.getShapeAt(0)).getArcStyle());
		assertEquals(ArcStyle.WEDGE, ((IArc)g.getShapeAt(2)).getArcStyle());
	}

	@Test
	public void testRedoArcStyle() {
		testUndoArcStyle();
		action.redo();
		assertEquals(ArcStyle.ARC, ((IArc)g.getShapeAt(0)).getArcStyle());
		assertEquals(ArcStyle.ARC, ((IArc)g.getShapeAt(2)).getArcStyle());
	}

	@Test
	public void testDoAngleEnd() {
		IArc arc1 = ShapeFactory.createCircleArc();
		IRectangle rec = ShapeFactory.createRectangle();
		IArc arc2 = ShapeFactory.createCircleArc();
		g.addShape(arc1);
		g.addShape(rec);
		g.addShape(arc2);
		arc1.setAngleEnd(111.);
		arc2.setAngleEnd(-23.43);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARC_END_ANGLE);
		action.setValue(21.);
		assertTrue(action.doIt());

		assertEquals(21., arc1.getAngleEnd(), 0.0001);
		assertEquals(21., arc2.getAngleEnd(), 0.0001);
	}

	@Test
	public void testUndoAngleEnd() {
		testDoAngleEnd();
		action.undo();
		assertEquals(111., ((IArc)g.getShapeAt(0)).getAngleEnd(), 0.0001);
		assertEquals(-23.43, ((IArc)g.getShapeAt(2)).getAngleEnd(), 0.0001);
	}

	@Test
	public void testRedoAngleEnd() {
		testUndoAngleEnd();
		action.redo();
		assertEquals(21., ((IArc)g.getShapeAt(0)).getAngleEnd(), 0.0001);
		assertEquals(21., ((IArc)g.getShapeAt(2)).getAngleEnd(), 0.0001);
	}

	@Test
	public void testDoAngleStart() {
		IArc arc1 = ShapeFactory.createCircleArc();
		IRectangle rec = ShapeFactory.createRectangle();
		IArc arc2 = ShapeFactory.createCircleArc();
		g.addShape(arc1);
		g.addShape(rec);
		g.addShape(arc2);
		arc1.setAngleStart(111.);
		arc2.setAngleStart(-23.43);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ARC_START_ANGLE);
		action.setValue(21.);
		assertTrue(action.doIt());

		assertEquals(21., arc1.getAngleStart(), 0.0001);
		assertEquals(21., arc2.getAngleStart(), 0.0001);
	}

	@Test
	public void testUndoAngleStart() {
		testDoAngleStart();
		action.undo();
		assertEquals(111., ((IArc)g.getShapeAt(0)).getAngleStart(), 0.0001);
		assertEquals(-23.43, ((IArc)g.getShapeAt(2)).getAngleStart(), 0.0001);
	}

	@Test
	public void testRedoAngleStart() {
		testUndoAngleStart();
		action.redo();
		assertEquals(21., ((IArc)g.getShapeAt(0)).getAngleStart(), 0.0001);
		assertEquals(21., ((IArc)g.getShapeAt(2)).getAngleStart(), 0.0001);
	}

	@Test
	public void testDoLineColour() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IRectangle rec2 = ShapeFactory.createRectangle();
		IDot dot = ShapeFactory.createDot(ShapeFactory.createPoint());
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setLineColour(DviPsColors.RED);
		rec2.setLineColour(DviPsColors.GREEN);
		dot.setLineColour(DviPsColors.YELLOW);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_LINE);
		action.setValue(DviPsColors.GRAY);
		assertTrue(action.doIt());

		assertEquals(DviPsColors.GRAY, rec1.getLineColour());
		assertEquals(DviPsColors.GRAY, rec2.getLineColour());
		assertEquals(DviPsColors.GRAY, dot.getLineColour());
	}

	@Test
	public void testUndoLineColour() {
		testDoLineColour();
		action.undo();
		assertEquals(DviPsColors.RED, g.getShapeAt(0).getLineColour());
		assertEquals(DviPsColors.YELLOW, g.getShapeAt(1).getLineColour());
		assertEquals(DviPsColors.GREEN, g.getShapeAt(2).getLineColour());
	}

	@Test
	public void testRedoLineColour() {
		testUndoLineColour();
		action.redo();
		assertEquals(DviPsColors.GRAY, g.getShapeAt(0).getLineColour());
		assertEquals(DviPsColors.GRAY, g.getShapeAt(1).getLineColour());
		assertEquals(DviPsColors.GRAY, g.getShapeAt(2).getLineColour());
	}

	@Test
	public void testDoBorderPosition() {
		IRectangle rec1 = ShapeFactory.createRectangle();
		IRectangle rec2 = ShapeFactory.createRectangle();
		IDot dot = ShapeFactory.createDot(ShapeFactory.createPoint());
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setBordersPosition(BorderPos.MID);
		rec2.setBordersPosition(BorderPos.INTO);
		action.setGroup(g);
		action.setProperty(ShapeProperties.BORDER_POS);
		action.setValue(BorderPos.OUT);
		assertTrue(action.doIt());

		assertEquals(BorderPos.OUT, rec1.getBordersPosition());
		assertEquals(BorderPos.OUT, rec2.getBordersPosition());
	}

	@Test
	public void testUndoBorderPosition() {
		testDoBorderPosition();
		action.undo();
		assertEquals(BorderPos.MID, g.getShapeAt(0).getBordersPosition());
		assertEquals(BorderPos.INTO, g.getShapeAt(2).getBordersPosition());
	}

	@Test
	public void testRedoBorderPosition() {
		testUndoBorderPosition();
		action.redo();
		assertEquals(BorderPos.OUT, g.getShapeAt(0).getBordersPosition());
		assertEquals(BorderPos.OUT, g.getShapeAt(2).getBordersPosition());
	}

	@Override
	protected ModifyShapeProperty createAction() {
		return new ModifyShapeProperty();
	}

	@SuppressWarnings("unused")
	@Override
	@Test
	public void testConstructor() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		new ModifyShapeProperty();
	}

	@Override
	@Test
	public void testFlush() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		IRectangle rec = ShapeFactory.createRectangle();
		g.addShape(rec);
		action.setGroup(g);
		action.setProperty(ShapeProperties.BORDER_POS);
		action.setValue(BorderPos.OUT);
		action.doIt();
		action.flush();
		Field f = HelperTest.getField(ModifyShapeProperty.class, "shapes"); //$NON-NLS-1$
		assertNull(f.get(action));
		f = HelperTest.getField(ModifyShapeProperty.class, "oldValue"); //$NON-NLS-1$
		assertNull(f.get(action));
		action.flush();
	}

	@Override
	@Test
	public void testDo() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		assertFalse(action.doIt());
		IRectangle rec = ShapeFactory.createRectangle();
		g.addShape(rec);
		action.setGroup(g);
		action.setProperty(ShapeProperties.BORDER_POS);
		action.setValue(BorderPos.OUT);
		assertTrue(action.doIt());
	}

	@Override
	@Test
	public void testCanDo() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		IGroup varTmp = ShapeFactory.createGroup();
		assertFalse(action.canDo());
		action.setGroup(varTmp);
		assertFalse(action.canDo());
		action.setProperty(ShapeProperties.ARC_END_ANGLE);
		assertFalse(action.canDo());
		action.setValue(100.);
		assertFalse(action.canDo());
		varTmp.addShape(ShapeFactory.createCircleArc());
		assertTrue(action.canDo());
		action.setGroup(null);
		assertFalse(action.canDo());
	}

	@Override
	@Test
	public void testIsRegisterable() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		assertTrue(action.isRegisterable());
	}

	@Override
	@Test
	public void testHadEffect() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		assertFalse(action.hadEffect());
		action.done();
		assertTrue(action.hadEffect());
	}

	@Test
	public void testGetUndoName() {
		assertNotNull(action.getUndoName());
		action.setProperty(ShapeProperties.ARC_END_ANGLE);
		assertNotNull(action.getUndoName());
		action.setProperty(null);
		assertNotNull(action.getUndoName());
	}
}
