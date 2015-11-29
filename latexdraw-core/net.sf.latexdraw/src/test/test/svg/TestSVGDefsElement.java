package test.svg;

import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGMarkerElement;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSVGDefsElement extends AbstractTestSVGElement {
	@Test
	public void testEnableRendering() throws MalformedSVGDocument {
		SVGDefsElement defs = new SVGDefsElement(node, null);
		assertFalse(defs.enableRendering());
	}

	@Test
	public void testGetDef() throws MalformedSVGDocument {
		SVGDefsElement defs = new SVGDefsElement(node, null);
		SVGMarkerElement mark = new SVGMarkerElement(node.getOwnerDocument());

		mark.setAttribute(SVGAttributes.SVG_ID, SVGAttributes.SVG_ID);
		defs.appendChild(mark);

		assertNull(defs.getDef(null));
		assertNull(defs.getDef("")); //$NON-NLS-1$
		assertNull(defs.getDef("dsqd")); //$NON-NLS-1$
		assertEquals(mark, defs.getDef("id")); //$NON-NLS-1$
	}

	@SuppressWarnings("unused")
	@Test
	public void testContructor() throws MalformedSVGDocument {
		try {
			new SVGDefsElement(null, null);
			fail();
		}catch(Exception e) {
			/**/}

		new SVGDefsElement(node, null);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_DEFS;
	}
}
