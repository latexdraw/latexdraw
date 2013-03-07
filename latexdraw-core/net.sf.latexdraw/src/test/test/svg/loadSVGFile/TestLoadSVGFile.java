package test.svg.loadSVGFile;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.sf.latexdraw.generators.svg.IShapeSVGFactory;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.views.Java2D.impl.FlyweightThumbnail;
import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;

import org.junit.Before;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class TestLoadSVGFile {
	protected IGroup group;

	static {
		DrawingTK.setFactory(new LShapeFactory());
	}

	@Before
	public void setUp() throws MalformedSVGDocument, URISyntaxException, IOException {
		FlyweightThumbnail.images().clear();
		FlyweightThumbnail.setThread(false);
		final SVGDocument doc = new SVGDocument(new URI(getPathSVGFile()));
		group = toLatexdraw(doc);
	}


	public IGroup toLatexdraw(final SVGDocument doc) {
		final IGroup shapes = DrawingTK.getFactory().createGroup(false);
		final NodeList elts = doc.getDocumentElement().getChildNodes();
		Node node;

		for(int i=0, size=elts.getLength(); i<size; i++) {
			node = elts.item(i);

			if(node instanceof SVGElement)
				shapes.addShape(IShapeSVGFactory.INSTANCE.createShape((SVGElement)node));
		}

		return (IGroup) shapes.getShapeAt(0);
	}


	public abstract String getPathSVGFile();

	public abstract int getNbShapesExpected();


	public void testNbShapesInDrawing() {
		assertEquals(getNbShapesExpected(), group.size());
	}
}
