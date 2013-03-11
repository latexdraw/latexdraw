package test.action;

import java.awt.Color;
import static org.junit.Assert.*;
import java.lang.reflect.Field;

import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.*;
import net.sf.latexdraw.glib.models.interfaces.IArc.ArcStyle;
import net.sf.latexdraw.glib.models.interfaces.IArrow.ArrowStyle;
import net.sf.latexdraw.glib.models.interfaces.IDot.DotStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.models.interfaces.IText.TextPosition;

import org.junit.Before;

import test.HelperTest;


public class TestModifyShapeProperty extends TestAbstractAction<ModifyShapeProperty> {
	protected IGroup g;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		DrawingTK.setFactory(new LShapeFactory());
		g = DrawingTK.getFactory().createGroup(false);
	}

	public void testDoArrowDotSizeNum() {
		IPolyline line1 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline line2 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
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


	public void testUndoArrowDotSizeNum() {
		testDoArrowDotSizeNum();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getDotSizeNum(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getDotSizeNum(), 0.0001);
	}


	public void testRedoArrowDotSizeNum() {
		testUndoArrowDotSizeNum();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getDotSizeNum(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getDotSizeNum(), 0.0001);
	}




	public void testDoArrowDotSizeDim() {
		IPolyline line1 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline line2 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
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


	public void testUndoArrowDotSizeDim() {
		testDoArrowDotSizeDim();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getDotSizeDim(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getDotSizeDim(), 0.0001);
	}


	public void testRedoArrowDotSizeDim() {
		testUndoArrowDotSizeDim();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getDotSizeDim(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getDotSizeDim(), 0.0001);
	}




	public void testDoArrowTBarSizeNum() {
		IPolyline line1 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline line2 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
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


	public void testUndoArrowTBarSizeNum() {
		testDoArrowTBarSizeNum();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getTBarSizeNum(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getTBarSizeNum(), 0.0001);
	}


	public void testRedoArrowTBarSizeNum() {
		testUndoArrowTBarSizeNum();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getTBarSizeNum(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getTBarSizeNum(), 0.0001);
	}



	public void testDoArrowTBarSizeDim() {
		IPolyline line1 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline line2 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
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


	public void testUndoArrowTBarSizeDim() {
		testDoArrowTBarSizeDim();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getTBarSizeDim(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getTBarSizeDim(), 0.0001);
	}


	public void testRedoArrowTBarSizeDim() {
		testUndoArrowTBarSizeDim();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getTBarSizeDim(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getTBarSizeDim(), 0.0001);
	}



	public void testDoArrowBracketNum() {
		IPolyline line1 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline line2 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
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


	public void testUndoArrowBracketNum() {
		testDoArrowBracketNum();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getBracketNum(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getBracketNum(), 0.0001);
	}


	public void testRedoArrowBracketNum() {
		testUndoArrowBracketNum();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getBracketNum(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getBracketNum(), 0.0001);
	}



	public void testDoArrowRBracketNum() {
		IPolyline line1 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline line2 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
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


	public void testUndoArrowRBracketNum() {
		testDoArrowRBracketNum();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getRBracketNum(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getRBracketNum(), 0.0001);
	}


	public void testRedoArrowRBracketNum() {
		testUndoArrowRBracketNum();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getRBracketNum(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getRBracketNum(), 0.0001);
	}


	public void testDoArrowSizeNum() {
		IPolyline line1 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline line2 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
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


	public void testUndoArrowSizeNum() {
		testDoArrowSizeNum();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getArrowSizeNum(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getArrowSizeNum(), 0.0001);
	}


	public void testRedoArrowSizeNum() {
		testUndoArrowSizeNum();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getArrowSizeNum(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getArrowSizeNum(), 0.0001);
	}

	public void testDoArrowSizeDim() {
		IPolyline line1 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline line2 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
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


	public void testUndoArrowSizeDim() {
		testDoArrowSizeDim();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getArrowSizeDim(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getArrowSizeDim(), 0.0001);
	}


	public void testRedoArrowsSizeDim() {
		testUndoArrowSizeDim();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getArrowSizeDim(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getArrowSizeDim(), 0.0001);
	}


	public void testDoArrowLength() {
		IPolyline line1 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline line2 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
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


	public void testUndoArrowLength() {
		testDoArrowLength();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getArrowLength(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getArrowLength(), 0.0001);
	}


	public void testRedoArrowLength() {
		testUndoArrowLength();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getArrowLength(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getArrowLength(), 0.0001);
	}

	public void testDoArrowInset() {
		IPolyline line1 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline line2 = DrawingTK.getFactory().createPolyline(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(10, 10), false);
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


	public void testUndoArrowInset() {
		testDoArrowInset();
		action.undo();
		assertEquals(0.12, ((IPolyline)g.getShapeAt(0)).getArrowInset(), 0.0001);
		assertEquals(0.23, ((IPolyline)g.getShapeAt(2)).getArrowInset(), 0.0001);
	}


	public void testRedoArrowInset() {
		testUndoArrowInset();
		action.redo();
		assertEquals(0.33, ((IPolyline)g.getShapeAt(0)).getArrowInset(), 0.0001);
		assertEquals(0.33, ((IPolyline)g.getShapeAt(2)).getArrowInset(), 0.0001);
	}


	public void testDoGridStart() {
		IGrid grid = DrawingTK.getFactory().createGrid(false, DrawingTK.getFactory().createPoint());
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IAxes axe = DrawingTK.getFactory().createAxes(false, DrawingTK.getFactory().createPoint());
		g.addShape(grid);
		g.addShape(rec);
		g.addShape(axe);
		grid.setGridStart(-3, -2);
		axe.setGridStart(-1, -4);
		action.setGroup(g);
		action.setProperty(ShapeProperties.GRID_START);
		action.setValue(DrawingTK.getFactory().createPoint(-5, -6));
		assertTrue(action.doIt());

		assertEquals(-5., grid.getGridStartX(), 0.0001);
		assertEquals(-6., grid.getGridStartY(), 0.0001);
		assertEquals(-5., axe.getGridStartX(), 0.0001);
		assertEquals(-6., axe.getGridStartY(), 0.0001);
	}


	public void testUndoGridStart() {
		testDoGridStart();
		action.undo();
		assertEquals(-3., ((IGrid)g.getShapeAt(0)).getGridStartX(), 0.0001);
		assertEquals(-2., ((IGrid)g.getShapeAt(0)).getGridStartY(), 0.0001);
		assertEquals(-1., ((IAxes)g.getShapeAt(2)).getGridStartX(), 0.0001);
		assertEquals(-4., ((IAxes)g.getShapeAt(2)).getGridStartY(), 0.0001);
	}


	public void testRedoGridStart() {
		testUndoGridStart();
		action.redo();
		assertEquals(-5., ((IGrid)g.getShapeAt(0)).getGridStartX(), 0.0001);
		assertEquals(-6., ((IGrid)g.getShapeAt(0)).getGridStartY(), 0.0001);
		assertEquals(-5., ((IAxes)g.getShapeAt(2)).getGridStartX(), 0.0001);
		assertEquals(-6., ((IAxes)g.getShapeAt(2)).getGridStartY(), 0.0001);
	}


	public void testDoRotationAngle() {
		IDot d1 = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IDot d2 = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		g.addShape(d1);
		g.addShape(rec);
		g.addShape(d2);
		d1.setRotationAngle(11.);
		d2.setRotationAngle(22.2);
		rec.setRotationAngle(-123.4);
		action.setGroup(g);
		action.setProperty(ShapeProperties.ROTATION_ANGLE);
		action.setValue(33.3);
		assertTrue(action.doIt());

		assertEquals(33.3, d1.getRotationAngle(), 0.0001);
		assertEquals(33.3, d2.getRotationAngle(), 0.0001);
	}


	public void testUndoRotationAngle() {
		testDoRotationAngle();
		action.undo();
		assertEquals(11., g.getShapeAt(0).getRotationAngle(), 0.0001);
		assertEquals(-123.4, g.getShapeAt(1).getRotationAngle(), 0.0001);
		assertEquals(22.2, g.getShapeAt(2).getRotationAngle(), 0.0001);
	}


	public void testRedoRotationAngle() {
		testUndoRotationAngle();
		action.redo();
		assertEquals(33.3, g.getShapeAt(0).getRotationAngle(), 0.0001);
		assertEquals(33.3, g.getShapeAt(1).getRotationAngle(), 0.0001);
		assertEquals(33.3, g.getShapeAt(2).getRotationAngle(), 0.0001);
	}



	public void testDoDotSize() {
		IDot d1 = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IDot d2 = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		g.addShape(d1);
		g.addShape(rec);
		g.addShape(d2);
		d1.setRadius(11.);
		d2.setRadius(22.2);
		action.setGroup(g);
		action.setProperty(ShapeProperties.DOT_SIZE);
		action.setValue(33.3);
		assertTrue(action.doIt());

		assertEquals(33.3, d1.getRadius(), 0.0001);
		assertEquals(33.3, d2.getRadius(), 0.0001);
	}


	public void testUndoDotSize() {
		testDoDotSize();
		action.undo();
		assertEquals(11., ((IDot)g.getShapeAt(0)).getRadius(), 0.0001);
		assertEquals(22.2, ((IDot)g.getShapeAt(2)).getRadius(), 0.0001);
	}


	public void testRedoDotSize() {
		testUndoDotSize();
		action.redo();
		assertEquals(33.3, ((IDot)g.getShapeAt(0)).getRadius(), 0.0001);
		assertEquals(33.3, ((IDot)g.getShapeAt(2)).getRadius(), 0.0001);
	}



	public void testDoDotStyle() {
		IDot d1 = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IDot d2 = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
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


	public void testUndoDotStyle() {
		testDoDotStyle();
		action.undo();
		assertEquals(DotStyle.BAR, ((IDot)g.getShapeAt(0)).getDotStyle());
		assertEquals(DotStyle.PLUS, ((IDot)g.getShapeAt(2)).getDotStyle());
	}


	public void testRedoDotStyle() {
		testUndoDotStyle();
		action.redo();
		assertEquals(DotStyle.FDIAMOND, ((IDot)g.getShapeAt(0)).getDotStyle());
		assertEquals(DotStyle.FDIAMOND, ((IDot)g.getShapeAt(2)).getDotStyle());
	}



	public void testDoLineStyle() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoLineStyle() {
		testDoLineStyle();
		action.undo();
		assertEquals(LineStyle.DASHED, g.getShapeAt(0).getLineStyle());
		assertEquals(LineStyle.DOTTED, g.getShapeAt(2).getLineStyle());
	}


	public void testRedoLineStyle() {
		testUndoLineStyle();
		action.redo();
		assertEquals(LineStyle.SOLID, g.getShapeAt(0).getLineStyle());
		assertEquals(LineStyle.SOLID, g.getShapeAt(2).getLineStyle());
	}


	public void testDoFillingStyle() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoFillingStyle() {
		testDoFillingStyle();
		action.undo();
		assertEquals(FillingStyle.CLINES_PLAIN, g.getShapeAt(0).getFillingStyle());
		assertEquals(FillingStyle.GRAD, g.getShapeAt(2).getFillingStyle());
	}


	public void testRedoFillingStyle() {
		testUndoFillingStyle();
		action.redo();
		assertEquals(FillingStyle.VLINES, g.getShapeAt(0).getFillingStyle());
		assertEquals(FillingStyle.VLINES, g.getShapeAt(2).getFillingStyle());
	}



	public void testDoThickness() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoThickness() {
		testDoThickness();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getThickness(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getThickness(), 0.0001);
	}


	public void testRedoThickness() {
		testUndoThickness();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getThickness(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getThickness(), 0.0001);
	}



	public void testDoShadowAngle() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoShadowAngle() {
		testDoShadowAngle();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getShadowAngle(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getShadowAngle(), 0.0001);
	}


	public void testRedoShadowAngle() {
		testUndoShadowAngle();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getShadowAngle(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getShadowAngle(), 0.0001);
	}



	public void testDoShadowSize() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoShadowSize() {
		testDoShadowSize();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getShadowSize(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getShadowSize(), 0.0001);
	}


	public void testRedoShadowSize() {
		testUndoShadowSize();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getShadowSize(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getShadowSize(), 0.0001);
	}



	public void testDoHasShadow() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoHasShadow() {
		testDoHasShadow();
		action.undo();
		assertTrue(g.getShapeAt(0).hasShadow());
		assertFalse(g.getShapeAt(2).hasShadow());
	}


	public void testRedoHasShadow() {
		testUndoHasShadow();
		action.redo();
		assertTrue(g.getShapeAt(0).hasShadow());
		assertTrue(g.getShapeAt(2).hasShadow());
	}



	public void testDoDbleBordSep() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoDbleBordSep() {
		testDoDbleBordSep();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getDbleBordSep(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getDbleBordSep(), 0.0001);
	}


	public void testRedoDbleBordSep() {
		testUndoDbleBordSep();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getDbleBordSep(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getDbleBordSep(), 0.0001);
	}



	public void testDoHasDbleBord() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoHasDbleBord() {
		testDoHasDbleBord();
		action.undo();
		assertTrue(g.getShapeAt(0).hasDbleBord());
		assertFalse(g.getShapeAt(2).hasDbleBord());
	}


	public void testRedoHasDbleBord() {
		testUndoHasDbleBord();
		action.redo();
		assertTrue(g.getShapeAt(0).hasDbleBord());
		assertTrue(g.getShapeAt(2).hasDbleBord());
	}



	public void testDoFillingDotCol() {
		IDot d1 = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IDot d2 = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		d1.setDotStyle(DotStyle.DIAMOND);
		d2.setDotStyle(DotStyle.DIAMOND);
		g.addShape(d1);
		g.addShape(rec);
		g.addShape(d2);
		d1.setDotFillingCol(Color.RED);
		d2.setDotFillingCol(Color.GREEN);
		rec.setFillingCol(Color.CYAN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.DOT_FILLING_COL);
		action.setValue(Color.GRAY);
		assertTrue(action.doIt());

		assertEquals(Color.GRAY, d1.getDotFillingCol());
		assertEquals(Color.GRAY, d2.getDotFillingCol());
		assertEquals(Color.CYAN, rec.getFillingCol());
	}


	public void testUndoFillingDotCol() {
		testDoFillingDotCol();
		action.undo();
		assertEquals(Color.RED, ((IDot)g.getShapeAt(0)).getDotFillingCol());
		assertEquals(Color.CYAN, g.getShapeAt(1).getFillingCol());
		assertEquals(Color.GREEN, ((IDot)g.getShapeAt(2)).getDotFillingCol());
	}


	public void testRedoFillingDotCol() {
		testUndoFillingDotCol();
		action.redo();
		assertEquals(Color.GRAY, ((IDot)g.getShapeAt(0)).getDotFillingCol());
		assertEquals(Color.CYAN, g.getShapeAt(1).getFillingCol());
		assertEquals(Color.GRAY, ((IDot)g.getShapeAt(2)).getDotFillingCol());
	}


	public void testDoStartGradCol() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
		IDot dot = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setGradColStart(Color.RED);
		rec2.setGradColStart(Color.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_GRADIENT_START);
		action.setValue(Color.GRAY);
		assertTrue(action.doIt());

		assertEquals(Color.GRAY, rec1.getGradColStart());
		assertEquals(Color.GRAY, rec2.getGradColStart());
	}


	public void testUndoStartGradCol() {
		testDoStartGradCol();
		action.undo();
		assertEquals(Color.RED, g.getShapeAt(0).getGradColStart());
		assertEquals(Color.GREEN, g.getShapeAt(2).getGradColStart());
	}


	public void testRedoStartGradCol() {
		testUndoStartGradCol();
		action.redo();
		assertEquals(Color.GRAY, g.getShapeAt(0).getGradColStart());
		assertEquals(Color.GRAY, g.getShapeAt(2).getGradColStart());
	}


	public void testDoEndGradCol() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
		IDot dot = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setGradColEnd(Color.RED);
		rec2.setGradColEnd(Color.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_GRADIENT_END);
		action.setValue(Color.GRAY);
		assertTrue(action.doIt());

		assertEquals(Color.GRAY, rec1.getGradColEnd());
		assertEquals(Color.GRAY, rec2.getGradColEnd());
	}


	public void testUndoEndGradCol() {
		testDoEndGradCol();
		action.undo();
		assertEquals(Color.RED, g.getShapeAt(0).getGradColEnd());
		assertEquals(Color.GREEN, g.getShapeAt(2).getGradColEnd());
	}


	public void testRedoEndGradCol() {
		testUndoEndGradCol();
		action.redo();
		assertEquals(Color.GRAY, g.getShapeAt(0).getGradColEnd());
		assertEquals(Color.GRAY, g.getShapeAt(2).getGradColEnd());
	}


	public void testDoShadowCol() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
		IDot dot = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setShadowCol(Color.RED);
		rec2.setShadowCol(Color.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_SHADOW);
		action.setValue(Color.GRAY);
		assertTrue(action.doIt());

		assertEquals(Color.GRAY, rec1.getShadowCol());
		assertEquals(Color.GRAY, rec2.getShadowCol());
	}


	public void testUndoShadowCol() {
		testDoShadowCol();
		action.undo();
		assertEquals(Color.RED, g.getShapeAt(0).getShadowCol());
		assertEquals(Color.GREEN, g.getShapeAt(2).getShadowCol());
	}


	public void testRedoShadowCol() {
		testUndoShadowCol();
		action.redo();
		assertEquals(Color.GRAY, g.getShapeAt(0).getShadowCol());
		assertEquals(Color.GRAY, g.getShapeAt(2).getShadowCol());
	}


	public void testDoDbleBordCol() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
		IDot dot = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setDbleBordCol(Color.RED);
		rec2.setDbleBordCol(Color.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_DBLE_BORD);
		action.setValue(Color.GRAY);
		assertTrue(action.doIt());

		assertEquals(Color.GRAY, rec1.getDbleBordCol());
		assertEquals(Color.GRAY, rec2.getDbleBordCol());
	}


	public void testUndoDbleBordCol() {
		testDoDbleBordCol();
		action.undo();
		assertEquals(Color.RED, g.getShapeAt(0).getDbleBordCol());
		assertEquals(Color.GREEN, g.getShapeAt(2).getDbleBordCol());
	}


	public void testRedoDbleBordCol() {
		testUndoDbleBordCol();
		action.redo();
		assertEquals(Color.GRAY, g.getShapeAt(0).getDbleBordCol());
		assertEquals(Color.GRAY, g.getShapeAt(2).getDbleBordCol());
	}


	public void testDoHatchingsCol() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
		IDot dot = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setHatchingsCol(Color.RED);
		rec2.setHatchingsCol(Color.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_HATCHINGS);
		action.setValue(Color.GRAY);
		assertTrue(action.doIt());

		assertEquals(Color.GRAY, rec1.getHatchingsCol());
		assertEquals(Color.GRAY, rec2.getHatchingsCol());
	}


	public void testUndoHatchingsCol() {
		testDoHatchingsCol();
		action.undo();
		assertEquals(Color.RED, g.getShapeAt(0).getHatchingsCol());
		assertEquals(Color.GREEN, g.getShapeAt(2).getHatchingsCol());
	}


	public void testRedoHatchingsCol() {
		testUndoHatchingsCol();
		action.redo();
		assertEquals(Color.GRAY, g.getShapeAt(0).getHatchingsCol());
		assertEquals(Color.GRAY, g.getShapeAt(2).getHatchingsCol());
	}


	public void testDoFillingCol() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
		IDot dot = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setFillingCol(Color.RED);
		rec2.setFillingCol(Color.GREEN);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_FILLING);
		action.setValue(Color.GRAY);
		assertTrue(action.doIt());

		assertEquals(Color.GRAY, rec1.getFillingCol());
		assertEquals(Color.GRAY, rec2.getFillingCol());
	}


	public void testUndoFillingCol() {
		testDoFillingCol();
		action.undo();
		assertEquals(Color.RED, g.getShapeAt(0).getFillingCol());
		assertEquals(Color.GREEN, g.getShapeAt(2).getFillingCol());
	}


	public void testRedoFillingCol() {
		testUndoFillingCol();
		action.redo();
		assertEquals(Color.GRAY, g.getShapeAt(0).getFillingCol());
		assertEquals(Color.GRAY, g.getShapeAt(2).getFillingCol());
	}


	public void testDoLineArc() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoLineArc() {
		testDoLineArc();
		action.undo();
		assertEquals(0.1, ((ILineArcShape)g.getShapeAt(0)).getLineArc(), 0.0001);
		assertEquals(0.2, ((ILineArcShape)g.getShapeAt(2)).getLineArc(), 0.0001);
	}


	public void testRedoLineArc() {
		testUndoLineArc();
		action.redo();
		assertEquals(0.3, ((ILineArcShape)g.getShapeAt(0)).getLineArc(), 0.0001);
		assertEquals(0.3, ((ILineArcShape)g.getShapeAt(2)).getLineArc(), 0.0001);
	}


	public void testDoGradMidPt() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoGradMidPt() {
		testDoGradMidPt();
		action.undo();
		assertEquals(0.1, g.getShapeAt(0).getGradMidPt(), 0.0001);
		assertEquals(0.2, g.getShapeAt(2).getGradMidPt(), 0.0001);
	}


	public void testRedoGradMidPt() {
		testUndoGradMidPt();
		action.redo();
		assertEquals(0.3, g.getShapeAt(0).getGradMidPt(), 0.0001);
		assertEquals(0.3, g.getShapeAt(2).getGradMidPt(), 0.0001);
	}



	public void testDoGradAngle() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoGradAngle() {
		testDoGradAngle();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getGradAngle(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getGradAngle(), 0.0001);
	}


	public void testRedoGradAngle() {
		testUndoGradAngle();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getGradAngle(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getGradAngle(), 0.0001);
	}


	public void testDoHatchingsSep() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoHatchingsSep() {
		testDoHatchingsSep();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getHatchingsSep(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getHatchingsSep(), 0.0001);
	}


	public void testRedoHatchingsSep() {
		testUndoHatchingsSep();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getHatchingsSep(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getHatchingsSep(), 0.0001);
	}


	public void testDoHatchingsWidth() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoHatchingsWidth() {
		testDoHatchingsWidth();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getHatchingsWidth(), 0.0001);
		assertEquals(33., g.getShapeAt(2).getHatchingsWidth(), 0.0001);
	}


	public void testRedoHatchingsWidth() {
		testUndoHatchingsWidth();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getHatchingsWidth(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getHatchingsWidth(), 0.0001);
	}


	public void testDoHatchingsAngle() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IText p = DrawingTK.getFactory().createText(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
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


	public void testUndoHatchingsAngle() {
		testDoHatchingsAngle();
		action.undo();
		assertEquals(12.5, g.getShapeAt(0).getHatchingsAngle(), 0.0001);
		assertEquals(-33., g.getShapeAt(2).getHatchingsAngle(), 0.0001);
	}


	public void testRedoHatchingsAngle() {
		testUndoHatchingsAngle();
		action.redo();
		assertEquals(101., g.getShapeAt(0).getHatchingsAngle(), 0.0001);
		assertEquals(101., g.getShapeAt(2).getHatchingsAngle(), 0.0001);
	}


	public void testDoText() {
		IText p1 = DrawingTK.getFactory().createText(false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IText p2 = DrawingTK.getFactory().createText(false);
		g.addShape(p1);
		g.addShape(rec);
		g.addShape(p2);
		p1.setText("foo1");
		p2.setText("foo2");
		action.setGroup(g);
		action.setProperty(ShapeProperties.TEXT);
		action.setValue("foo3");
		assertTrue(action.doIt());

		assertEquals("foo3", p1.getText());
		assertEquals("foo3", p2.getText());
	}


	public void testUndoText() {
		testDoText();
		action.undo();
		assertEquals("foo1", ((IText)g.getShapeAt(0)).getText());
		assertEquals("foo2", ((IText)g.getShapeAt(2)).getText());
	}


	public void testRedoText() {
		testUndoText();
		action.redo();
		assertEquals("foo3", ((IText)g.getShapeAt(0)).getText());
		assertEquals("foo3", ((IText)g.getShapeAt(2)).getText());
	}


	public void testDoTextPosition() {
		IText p1 = DrawingTK.getFactory().createText(false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IText p2 = DrawingTK.getFactory().createText(false);
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


	public void testUndoTextPosition() {
		testDoTextPosition();
		action.undo();
		assertEquals(TextPosition.BOT_LEFT, ((IText)g.getShapeAt(0)).getTextPosition());
		assertEquals(TextPosition.TOP, ((IText)g.getShapeAt(2)).getTextPosition());
	}


	public void testRedoTextPosition() {
		testUndoTextPosition();
		action.redo();
		assertEquals(TextPosition.BOT, ((IText)g.getShapeAt(0)).getTextPosition());
		assertEquals(TextPosition.BOT, ((IText)g.getShapeAt(2)).getTextPosition());
	}


	public void testDoArrowStyle1() {
		IPolyline p1 = DrawingTK.getFactory().createPolyline(false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline p2 = DrawingTK.getFactory().createPolyline(false);
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


	public void testUndoArrowStyle1() {
		testDoArrowStyle1();
		action.undo();
		assertEquals(ArrowStyle.LEFT_DBLE_ARROW, g.getShapeAt(0).getArrowStyle(1));
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW, g.getShapeAt(2).getArrowStyle(1));
	}


	public void testRedoArrowStyle1() {
		testUndoArrowStyle1();
		action.redo();
		assertEquals(ArrowStyle.BAR_IN, g.getShapeAt(0).getArrowStyle(1));
		assertEquals(ArrowStyle.BAR_IN, g.getShapeAt(2).getArrowStyle(1));
	}


	public void testDoArrowStyle0() {
		IPolyline p1 = DrawingTK.getFactory().createPolyline(false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IPolyline p2 = DrawingTK.getFactory().createPolyline(false);
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


	public void testUndoArrowStyle0() {
		testDoArrowStyle0();
		action.undo();
		assertEquals(ArrowStyle.LEFT_DBLE_ARROW, g.getShapeAt(0).getArrowStyle(0));
		assertEquals(ArrowStyle.RIGHT_DBLE_ARROW, g.getShapeAt(2).getArrowStyle(0));
	}


	public void testRedoArrowStyle0() {
		testUndoArrowStyle0();
		action.redo();
		assertEquals(ArrowStyle.BAR_IN, g.getShapeAt(0).getArrowStyle(0));
		assertEquals(ArrowStyle.BAR_IN, g.getShapeAt(2).getArrowStyle(0));
	}


	public void testDoArcStyle() {
		IArc arc1 = DrawingTK.getFactory().createArc(false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IArc arc2 = DrawingTK.getFactory().createArc(false);
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


	public void testUndoArcStyle() {
		testDoArcStyle();
		action.undo();
		assertEquals(ArcStyle.CHORD, ((IArc)g.getShapeAt(0)).getArcStyle());
		assertEquals(ArcStyle.WEDGE, ((IArc)g.getShapeAt(2)).getArcStyle());
	}


	public void testRedoArcStyle() {
		testUndoArcStyle();
		action.redo();
		assertEquals(ArcStyle.ARC, ((IArc)g.getShapeAt(0)).getArcStyle());
		assertEquals(ArcStyle.ARC, ((IArc)g.getShapeAt(2)).getArcStyle());
	}


	public void testDoAngleEnd() {
		IArc arc1 = DrawingTK.getFactory().createArc(false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IArc arc2 = DrawingTK.getFactory().createArc(false);
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


	public void testUndoAngleEnd() {
		testDoAngleEnd();
		action.undo();
		assertEquals(111., ((IArc)g.getShapeAt(0)).getAngleEnd(), 0.0001);
		assertEquals(-23.43, ((IArc)g.getShapeAt(2)).getAngleEnd(), 0.0001);
	}


	public void testRedoAngleEnd() {
		testUndoAngleEnd();
		action.redo();
		assertEquals(21., ((IArc)g.getShapeAt(0)).getAngleEnd(), 0.0001);
		assertEquals(21., ((IArc)g.getShapeAt(2)).getAngleEnd(), 0.0001);
	}

	public void testDoAngleStart() {
		IArc arc1 = DrawingTK.getFactory().createArc(false);
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		IArc arc2 = DrawingTK.getFactory().createArc(false);
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


	public void testUndoAngleStart() {
		testDoAngleStart();
		action.undo();
		assertEquals(111., ((IArc)g.getShapeAt(0)).getAngleStart(), 0.0001);
		assertEquals(-23.43, ((IArc)g.getShapeAt(2)).getAngleStart(), 0.0001);
	}


	public void testRedoAngleStart() {
		testUndoAngleStart();
		action.redo();
		assertEquals(21., ((IArc)g.getShapeAt(0)).getAngleStart(), 0.0001);
		assertEquals(21., ((IArc)g.getShapeAt(2)).getAngleStart(), 0.0001);
	}


	public void testDoLineColour() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
		IDot dot = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
		g.addShape(rec1);
		g.addShape(dot);
		g.addShape(rec2);
		rec1.setLineColour(Color.RED);
		rec2.setLineColour(Color.GREEN);
		dot.setLineColour(Color.YELLOW);
		action.setGroup(g);
		action.setProperty(ShapeProperties.COLOUR_LINE);
		action.setValue(Color.GRAY);
		assertTrue(action.doIt());

		assertEquals(Color.GRAY, rec1.getLineColour());
		assertEquals(Color.GRAY, rec2.getLineColour());
		assertEquals(Color.GRAY, dot.getLineColour());
	}


	public void testUndoLineColour() {
		testDoLineColour();
		action.undo();
		assertEquals(Color.RED, g.getShapeAt(0).getLineColour());
		assertEquals(Color.YELLOW, g.getShapeAt(1).getLineColour());
		assertEquals(Color.GREEN, g.getShapeAt(2).getLineColour());
	}


	public void testRedoLineColour() {
		testUndoLineColour();
		action.redo();
		assertEquals(Color.GRAY, g.getShapeAt(0).getLineColour());
		assertEquals(Color.GRAY, g.getShapeAt(1).getLineColour());
		assertEquals(Color.GRAY, g.getShapeAt(2).getLineColour());
	}


	public void testDoBorderPosition() {
		IRectangle rec1 = DrawingTK.getFactory().createRectangle(false);
		IRectangle rec2 = DrawingTK.getFactory().createRectangle(false);
		IDot dot = DrawingTK.getFactory().createDot(DrawingTK.getFactory().createPoint(), false);
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


	public void testUndoBorderPosition() {
		testDoBorderPosition();
		action.undo();
		assertEquals(BorderPos.MID, g.getShapeAt(0).getBordersPosition());
		assertEquals(BorderPos.INTO, g.getShapeAt(2).getBordersPosition());
	}


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
	public void testConstructor() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		new ModifyShapeProperty();
	}

	@Override
	public void testFlush() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		g.addShape(rec);
		action.setGroup(g);
		action.setProperty(ShapeProperties.BORDER_POS);
		action.setValue(BorderPos.OUT);
		action.doIt();
		action.flush();
		Field f = HelperTest.getField(ModifyShapeProperty.class, "shapes");
		assertNull(f.get(action));
		f = HelperTest.getField(ModifyShapeProperty.class, "oldValue");
		assertNull(f.get(action));
		action.flush();
	}

	@Override
	public void testDo() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		assertFalse(action.doIt());
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		g.addShape(rec);
		action.setGroup(g);
		action.setProperty(ShapeProperties.BORDER_POS);
		action.setValue(BorderPos.OUT);
		assertTrue(action.doIt());
	}

	@Override
	public void testCanDo() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		IGroup varTmp = DrawingTK.getFactory().createGroup(false);
		assertFalse(action.canDo());
		action.setGroup(varTmp);
		assertFalse(action.canDo());
		action.setProperty(ShapeProperties.ARC_END_ANGLE);
		assertFalse(action.canDo());
		action.setValue(100.);
		assertFalse(action.canDo());
		varTmp.addShape(DrawingTK.getFactory().createArc(false));
		assertTrue(action.canDo());
		action.setGroup(null);
		assertFalse(action.canDo());
	}

	@Override
	public void testIsRegisterable() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		assertTrue(action.isRegisterable());
	}

	@Override
	public void testHadEffect() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		assertFalse(action.hadEffect());
		action.done();
		assertTrue(action.hadEffect());
	}


	public void testGetUndoName() {
		assertNotNull(action.getUndoName());
		action.setProperty(ShapeProperties.ARC_END_ANGLE);
		assertNotNull(action.getUndoName());
		action.setProperty(null);
		assertNotNull(action.getUndoName());
	}
}
