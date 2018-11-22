package net.sf.latexdraw.util;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSystemUtils {
	@Test
	void testCreateXMLDocumentBuilder() {
		assertTrue(SystemUtils.getInstance().createXMLDocumentBuilder().isPresent());
	}

	@Test
	void testCreateElement() {
		final Document doc = SystemUtils.getInstance().createXMLDocumentBuilder().orElseThrow().newDocument();
		final Element root = doc.createElement("root");
		final Element elt = SystemUtils.getInstance().createElement(doc, "TAGG", "valueElt", root);
		assertEquals("TAGG", elt.getTagName());
		assertEquals("valueElt", elt.getTextContent());
		assertEquals(root, elt.getParentNode());
	}
}
