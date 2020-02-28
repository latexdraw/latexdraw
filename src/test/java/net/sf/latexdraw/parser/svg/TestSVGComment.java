package net.sf.latexdraw.parser.svg;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSVGComment extends TestSVGText {
	@Override
	@Test
	void testGetNodeType() {
		final SVGComment cdata = createSVGText("test", doc);
		assertEquals(Node.COMMENT_NODE, cdata.getNodeType());
	}

	@Override
	SVGComment createSVGText(final String str, final SVGDocument document) {
		return new SVGComment(str, document);
	}
}
