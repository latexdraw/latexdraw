package net.sf.latexdraw.parsers.svg;

import org.junit.Test;
import org.w3c.dom.Node;

import static org.junit.Assert.assertEquals;

public class TestSVGComment extends TestSVGText {
	@Override
	@Test
	public void testGetNodeType() {
		SVGComment cdata = createSVGText("test", doc);
		assertEquals(Node.COMMENT_NODE, cdata.getNodeType());
	}

	@Override
	protected SVGComment createSVGText(String str, SVGDocument document) {
		return new SVGComment(str, document);
	}
}
