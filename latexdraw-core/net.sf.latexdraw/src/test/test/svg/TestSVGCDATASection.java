package test.svg;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.parsers.svg.SVGCDATASection;
import net.sf.latexdraw.parsers.svg.SVGDocument;

import org.junit.Test;
import org.w3c.dom.Node;

public class TestSVGCDATASection extends TestSVGText {
	@Override
	@Test
	public void testGetNodeType() {
		SVGCDATASection cdata = createSVGText("test", doc); //$NON-NLS-1$
		assertEquals(Node.CDATA_SECTION_NODE, cdata.getNodeType());
	}


	@Override
	protected SVGCDATASection createSVGText(String txt, SVGDocument document) throws IllegalArgumentException {
		return new SVGCDATASection(txt, document);
	}
}
