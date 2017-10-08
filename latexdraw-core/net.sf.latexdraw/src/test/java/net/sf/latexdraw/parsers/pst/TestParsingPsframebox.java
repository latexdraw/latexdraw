package net.sf.latexdraw.parsers.pst;

import net.sf.latexdraw.models.interfaces.shape.IText;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestParsingPsframebox extends TestPSTParser {
	@Test
	public void testParse_pstribox_star() {
		parser("\\pstribox*[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\pstribox*[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_pstribox() {
		parser("\\pstribox[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\pstribox[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_psdiabox_star() {
		parser("\\psdiabox*[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\psdiabox*[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_psdiabox() {
		parser("\\psdiabox[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\psdiabox[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_psovalbox_star() {
		parser("\\psovalbox*[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\psovalbox*[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_psovalbox() {
		parser("\\psovalbox[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\psovalbox[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_pscirclebox_star() {
		parser("\\pscirclebox*[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\pscirclebox*[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_pscirclebox() {
		parser("\\pscirclebox[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\pscirclebox[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_psshadowbox_star() {
		parser("\\psshadowbox*[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\psshadowbox*[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_psshadowbox() {
		parser("\\psshadowbox[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\psshadowbox[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testBug911816() {
		// https://bugs.launchpad.net/latexdraw/+bug/911816
		parser("\\psframebox{$E=mc^2$}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\psframebox{$E=mc^2$}", text.getText());
	}

	@Test
	public void testParse_psframebox() {
		parser("\\psframebox[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\psframebox[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_psdblframebox() {
		parser("\\psdblframebox{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\psdblframebox{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_psframebox_star() {
		parser("\\psframebox*[doubleline=true]{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\psframebox*[doubleline=true]{\\psframe(0,1)}", text.getText());
	}

	@Test
	public void testParse_psdblframebox_star() {
		parser("\\psdblframebox*{\\psframe(0,1)}");
		assertEquals(1, listener.getShapes().size());
		final IText text = getShapeAt(0);
		assertEquals("\\psdblframebox*{\\psframe(0,1)}", text.getText());
	}
}
