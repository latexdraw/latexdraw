package test.svg.loadSave;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import net.sf.latexdraw.generators.svg.IShapeSVGFactory;
import net.sf.latexdraw.generators.svg.SVGShapesFactory;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.LineStyle;
import net.sf.latexdraw.glib.views.Java2D.impl.LViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;
import net.sf.latexdraw.glib.views.latex.DviPsColors;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGSVGElement;
import net.sf.latexdraw.util.LNamespace;

import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class TestLoadSaveSVG<T extends IShape>{

	protected T shape;

	static {
		View2DTK.setFactory(new LViewsFactory());
	}


	public T saveLoadShape(T sh) {
		IDrawing drawing = ShapeFactory.createDrawing();
		drawing.addShape(sh);
		SVGDocument doc = toSVG(drawing);
		return toLatexdraw(doc);
	}


	@SuppressWarnings("unchecked")
	protected T toLatexdraw(final SVGDocument doc) {
		final IGroup shapes = ShapeFactory.createGroup();
		final NodeList elts = doc.getDocumentElement().getChildNodes();
		Node node;

		for(int i=0, size=elts.getLength(); i<size; i++) {
			node = elts.item(i);

			if(node instanceof SVGElement)
				shapes.addShape(IShapeSVGFactory.INSTANCE.createShape((SVGElement)node));
		}

		return (T) (shapes.size() == 1 ? shapes.getShapeAt(0) : shapes.size()==0 ? null : shapes);
	}


	protected SVGDocument toSVG(final IDrawing drawing) {
		// Creation of the SVG document.
		final List<IShape> shapes	= drawing.getShapes();
		final SVGDocument doc 		= new SVGDocument();
		final SVGSVGElement root 	= doc.getFirstChild();
		final SVGGElement g 		= new SVGGElement(doc);
		final SVGDefsElement defs	= new SVGDefsElement(doc);
		SVGElement elt;

		root.appendChild(defs);
		root.appendChild(g);
		root.setAttribute("xmlns:"+LNamespace.LATEXDRAW_NAMESPACE, LNamespace.LATEXDRAW_NAMESPACE_URI);//$NON-NLS-1$

        for(final IShape sh : shapes)
        	if(sh!=null) {
        		// For each shape an SVG element is created.
        		elt = SVGShapesFactory.INSTANCE.createSVGElement(sh, doc);

	        	if(elt!=null)
	        		g.appendChild(elt);
	        }

		// Setting SVG attributes to the created document.
		root.setAttribute(SVGAttributes.SVG_VERSION, "1.1");//$NON-NLS-1$
		root.setAttribute(SVGAttributes.SVG_BASE_PROFILE, "full");//$NON-NLS-1$

		return doc;
	}



	protected T generateShape() {
		final T sh = saveLoadShape(shape);
		assertNotNull(sh);
		assertEquals(sh.getClass(), shape.getClass());
		return sh;
	}


	protected void compareShapes(final T sh2) {
		if(shape.isShowPtsable()) {
			assertEquals(shape.isShowPts(), sh2.isShowPts());
		}
		if(shape.isThicknessable()) {
			assertEquals(shape.getThickness(), sh2.getThickness(), 0.0001);
		}
		if(shape.isLineStylable()) {
			assertEquals(shape.getLineStyle(), sh2.getLineStyle());
			assertEquals(shape.getLineColour(), sh2.getLineColour());
			switch(shape.getLineStyle()) {
				case DASHED:
					assertEquals(shape.getDashSepBlack(), sh2.getDashSepBlack(), 0.0001);
					assertEquals(shape.getDashSepWhite(), sh2.getDashSepWhite(), 0.0001);
					break;
				case DOTTED:
					assertEquals(shape.getDotSep(), sh2.getDotSep(), 0.0001);
					break;
				default:
			}
		}
		if(shape.isShadowable()) {
			assertEquals(shape.hasShadow(), sh2.hasShadow());
			assertEquals(shape.getShadowAngle(), sh2.getShadowAngle(), 0.0001);
			assertEquals(shape.getShadowCol(), sh2.getShadowCol());
			assertEquals(shape.getShadowSize(), sh2.getShadowSize(), 0.0001);
		}
		if(shape.isDbleBorderable()) {
			assertEquals(shape.hasDbleBord(), sh2.hasDbleBord());
			assertEquals(shape.getDbleBordCol(), sh2.getDbleBordCol());
			assertEquals(shape.getDbleBordSep(), sh2.getDbleBordSep(), 0.0001);
		}
		if(shape.isInteriorStylable()) {
			assertEquals(shape.getFillingStyle(), sh2.getFillingStyle());
			assertEquals(shape.isFilled(), sh2.isFilled());
			assertEquals(shape.getFillingCol(), sh2.getFillingCol());
			if(shape.getFillingStyle().isHatchings()) {
				assertEquals(shape.getHatchingsAngle(), sh2.getHatchingsAngle(), 0.0001);
				assertEquals(shape.getHatchingsCol(), sh2.getHatchingsCol());
				assertEquals(shape.getHatchingsSep(), sh2.getHatchingsSep(), 0.0001);
				assertEquals(shape.getHatchingsWidth(), sh2.getHatchingsWidth(), 0.0001);
			}else if(shape.getFillingStyle().isGradient()) {
				assertEquals(shape.getGradAngle(), sh2.getGradAngle(), 0.0001);
				assertEquals(shape.getGradMidPt(), sh2.getGradMidPt(), 0.0001);
				assertEquals(shape.getGradColEnd(), sh2.getGradColEnd());
				assertEquals(shape.getGradColStart(), sh2.getGradColStart());
			}
		}
	}

	
	@Test
	public void testShowPoints() {
		if(shape.isShowPtsable()) {
			setDefaultDimensions();
			shape.setShowPts(true);
			compareShapes(generateShape());
		}
	}
	
	@Test
	public void testShadow() {
		if(shape.isShadowable()) {
			setDefaultDimensions();
			shape.setFillingStyle(FillingStyle.PLAIN);// Must fill the shape before.
			shape.setHasShadow(true);
			shape.setShadowAngle(-1);
			shape.setShadowCol(DviPsColors.RED);
			shape.setShadowSize(11.2);
			compareShapes(generateShape());
		}
	}

	@Test
	public void testDoubleBorders() {
		if(shape.isDbleBorderable()) {
			setDefaultDimensions();
			shape.setHasDbleBord(true);
			shape.setDbleBordCol(DviPsColors.GREEN);
			shape.setDbleBordSep(3.);
			compareShapes(generateShape());
		}
	}

	@Test
	public void testDoubleBordersWithShadow() {
		if(shape.isShadowable() && shape.isDbleBorderable()) {
			setDefaultDimensions();
			shape.setFillingStyle(FillingStyle.PLAIN);// Must fill the shape before.
			shape.setHasDbleBord(true);
			shape.setDbleBordCol(DviPsColors.GREEN);
			shape.setDbleBordSep(3.);
			shape.setHasShadow(true);
			shape.setShadowAngle(-1);
			shape.setShadowCol(DviPsColors.RED);
			shape.setShadowSize(11.2);
			compareShapes(generateShape());
		}
	}

	@Test
	public void testBorderStyle() {
		if(shape.isLineStylable()) {
			shape.setLineStyle(LineStyle.DASHED);
			shape.setThickness(10);
			shape.setLineColour(DviPsColors.YELLOW);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}

	@Test
	public void testFillingHatchingsPLAIN() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.PLAIN);
			shape.setHatchingsCol(DviPsColors.GRAY);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}

	@Test
	public void testFillingHatchingsVLINESPLAIN() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.VLINES_PLAIN);
			shape.setHatchingsAngle(1.2);
			shape.setHatchingsCol(DviPsColors.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}

	@Test
	public void testFillingHatchingsVLINES() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.VLINES);
			shape.setHatchingsAngle(1.2);
			shape.setHatchingsCol(DviPsColors.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}

	@Test
	public void testFillingHatchingsHLINESPLAIN() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.HLINES_PLAIN);
			shape.setHatchingsAngle(1.2);
			shape.setHatchingsCol(DviPsColors.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}

	@Test
	public void testFillingHatchingsHLINES() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.HLINES);
			shape.setHatchingsAngle(1.2);
			shape.setHatchingsCol(DviPsColors.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}

	@Test
	public void testFillingHatchingsCLINESPLAIN() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.CLINES_PLAIN);
			shape.setHatchingsAngle(1.2);
			shape.setFillingCol(DviPsColors.LIGHTGRAY);
			shape.setHatchingsCol(DviPsColors.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}
	
	@Test
	public void testFillingHatchingsCLINES() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.CLINES);
			shape.setHatchingsAngle(1.2);
			shape.setHatchingsCol(DviPsColors.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}

	@Test public void testFillingGradient() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.GRAD);
			shape.setGradAngle(0.2);
			shape.setGradColEnd(DviPsColors.BLUE);
			shape.setGradColStart(DviPsColors.CYAN);
			shape.setGradMidPt(0.1);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}

	protected abstract void setDefaultDimensions();
}
