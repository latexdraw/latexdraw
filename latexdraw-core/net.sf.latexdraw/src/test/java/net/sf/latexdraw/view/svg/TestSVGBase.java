package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGSVGElement;
import net.sf.latexdraw.util.LNamespace;
import org.junit.After;
import org.junit.Before;
import org.malai.command.CommandsRegistry;
import org.malai.undo.UndoCollector;

abstract class TestSVGBase<T extends IShape> {
	SVGDocument doc;

	@Before
	public void setUp() {
		doc = new SVGDocument();
		final SVGSVGElement root = doc.getFirstChild();
		root.setAttribute("xmlns:" + LNamespace.LATEXDRAW_NAMESPACE, LNamespace.LATEXDRAW_NAMESPACE_URI);
		root.appendChild(new SVGDefsElement(doc));
		root.setAttribute(SVGAttributes.SVG_VERSION, "1.1");
		root.setAttribute(SVGAttributes.SVG_BASE_PROFILE, "full");
	}

	@After
	public void tearDown() {
		CommandsRegistry.INSTANCE.clear();
		CommandsRegistry.INSTANCE.removeAllHandlers();
		BadaboomCollector.INSTANCE.clear();
		UndoCollector.INSTANCE.clear();
	}

	T produceOutputShapeFrom(final T sh) {
		final SVGElement elt = SVGShapesFactory.INSTANCE.createSVGElement(sh, doc);
		doc.getFirstChild().appendChild(elt);
		return (T) SVGShapesFactory.INSTANCE.createShape(elt);
	}
}
