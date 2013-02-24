package test.svg;

import net.sf.latexdraw.parsers.svg.SVGCDATASection;
import net.sf.latexdraw.parsers.svg.SVGDocument;

import org.junit.Test;
import org.w3c.dom.Node;

public class TestSVGCDATASection extends TestSVGText {
	@Override
	@Test
	public void testGetNodeType() {
		SVGCDATASection cdata = createSVGText("test", doc);
		assertEquals(Node.CDATA_SECTION_NODE, cdata.getNodeType());
	}


	@Override
	protected SVGCDATASection createSVGText(String txt, SVGDocument document) throws IllegalArgumentException {
		return new SVGCDATASection(txt, document);
	}
}
