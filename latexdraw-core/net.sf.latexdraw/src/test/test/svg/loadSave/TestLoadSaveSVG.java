package test.svg.loadSave;

import java.awt.Color;
import java.util.List;

import junit.framework.TestCase;
import net.sf.latexdraw.generators.svg.IShapeSVGFactory;
import net.sf.latexdraw.generators.svg.SVGShapesFactory;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.IShape.FillingStyle;
import net.sf.latexdraw.glib.models.interfaces.IShape.LineStyle;
import net.sf.latexdraw.glib.views.Java2D.impl.LViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGSVGElement;
import net.sf.latexdraw.util.LNamespace;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class TestLoadSaveSVG<T extends IShape> extends TestCase {

	protected T shape;

	static {
		View2DTK.setFactory(new LViewsFactory());
	}


	public T saveLoadShape(T sh) {
		IDrawing drawing = ShapeFactory.factory().createDrawing();
		drawing.addShape(sh);
		SVGDocument doc = toSVG(drawing);
		return toLatexdraw(doc);
	}


	protected T toLatexdraw(final SVGDocument doc) {
		if(doc==null)
			return null;

		final IGroup shapes = ShapeFactory.factory().createGroup(false);
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
		if(drawing==null)
			return null;

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


	protected void compareShapes(@SuppressWarnings("unused") T sh2) {
//		assertTrue(shape.isParametersEquals(sh2, true));
		//TODO
	}


	public void testShadow() {
		if(shape.isShadowable()) {
			shape.setFillingStyle(FillingStyle.PLAIN);// Must fill the shape before.
			setDefaultDimensions();
			shape.setHasShadow(true);
			shape.setShadowAngle(-1);
			shape.setShadowCol(Color.RED);
			shape.setShadowSize(11.2);
			compareShapes(generateShape());
		}
	}


	public void testDoubleBorders() {
		if(shape.isDbleBorderable()) {
			setDefaultDimensions();
			shape.setHasDbleBord(true);
			shape.setDbleBordCol(Color.GREEN);
			shape.setDbleBordSep(3.);
			compareShapes(generateShape());
		}
	}


	public void testDoubleBordersWithShadow() {
		if(shape.isShadowable() && shape.isDbleBorderable()) {
			shape.setFillingStyle(FillingStyle.PLAIN);// Must fill the shape before.
			setDefaultDimensions();
			shape.setHasDbleBord(true);
			shape.setDbleBordCol(Color.GREEN);
			shape.setDbleBordSep(3.);
			shape.setHasShadow(true);
			shape.setShadowAngle(-1);
			shape.setShadowCol(Color.RED);
			shape.setShadowSize(11.2);
			compareShapes(generateShape());
		}
	}


	public void testBorderStyle() {
		if(shape.isLineStylable()) {
			shape.setLineStyle(LineStyle.DASHED);
			shape.setThickness(10);
			shape.setLineColour(Color.YELLOW);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}


	public void testFillingHatchingsPLAIN() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.PLAIN);
			shape.setHatchingsCol(Color.GRAY);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}


	public void testFillingHatchingsVLINESPLAIN() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.VLINES_PLAIN);
			shape.setHatchingsAngle(1.2);
			shape.setHatchingsCol(Color.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}


	public void testFillingHatchingsVLINES() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.VLINES);
			shape.setHatchingsAngle(1.2);
			shape.setHatchingsCol(Color.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}


	public void testFillingHatchingsHLINESPLAIN() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.HLINES_PLAIN);
			shape.setHatchingsAngle(1.2);
			shape.setHatchingsCol(Color.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}


	public void testFillingHatchingsHLINES() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.HLINES);
			shape.setHatchingsAngle(1.2);
			shape.setHatchingsCol(Color.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}


	public void testFillingHatchingsCLINESPLAIN() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.CLINES_PLAIN);
			shape.setHatchingsAngle(1.2);
			shape.setFillingCol(Color.LIGHT_GRAY);
			shape.setHatchingsCol(Color.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}

	public void testFillingHatchingsCLINES() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.CLINES);
			shape.setHatchingsAngle(1.2);
			shape.setHatchingsCol(Color.GRAY);
			shape.setHatchingsSep(3.12);
			shape.setHatchingsWidth(123.3);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}

	public void testFillingGradient() {
		if(shape.isInteriorStylable()) {
			shape.setFillingStyle(FillingStyle.GRAD);
			shape.setGradAngle(0.2);// FIXME its fails because the grad angle is recomputed and thus approximated.
			shape.setGradColEnd(Color.BLUE);
			shape.setGradColStart(Color.CYAN);
			shape.setGradMidPt(0.1);
			setDefaultDimensions();
			compareShapes(generateShape());
		}
	}

	protected abstract void setDefaultDimensions();
}
