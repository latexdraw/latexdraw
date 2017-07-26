package net.sf.latexdraw.view.svg.loadSVGFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.view.svg.IShapeSVGFactory;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.junit.Assert.assertEquals;

public abstract class TestLoadSVGFile {
	protected IGroup group;

	@Before
	public void setUp() throws MalformedSVGDocument, URISyntaxException, IOException {
		final SVGDocument doc = new SVGDocument(new URI(getPathSVGFile()));
		group = toLatexdraw(doc);
	}

	public IGroup toLatexdraw(final SVGDocument doc) {
		final IGroup shapes = ShapeFactory.INST.createGroup();
		final NodeList elts = doc.getDocumentElement().getChildNodes();
		Node node;

		for(int i = 0, size = elts.getLength(); i < size; i++) {
			node = elts.item(i);

			if(node instanceof SVGElement)
				shapes.addShape(IShapeSVGFactory.INSTANCE.createShape((SVGElement)node));
		}

		if(shapes.size() == 1 && shapes.getShapeAt(0) instanceof IGroup)
			return (IGroup)shapes.getShapeAt(0);
		return shapes;
	}

	public abstract String getPathSVGFile();

	public abstract int getNbShapesExpected();

	@Test
	public void testNbShapesInDrawing() {
		assertEquals(getNbShapesExpected(), group.size());
	}
}
