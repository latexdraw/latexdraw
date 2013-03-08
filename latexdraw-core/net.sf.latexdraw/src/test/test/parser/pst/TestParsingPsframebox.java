package test.parser.pst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IText;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;

import org.junit.Test;

public class TestParsingPsframebox extends TestPSTParser {
	@Test public void testFrameboxWithSpecialColour() throws ParseException {
		IText txt = (IText)parser.parsePSTCode("\\definecolor{color3b}{rgb}{0.7725490196078432,0.09803921568627451,0.09803921568627451}"+
				"\\psframebox[linewidth=0.04,doubleline=true,doublesep=0.12,fillstyle=solid,fillcolor=color3b]{coucou}").get().getShapeAt(0);
		assertTrue(PSTParser.errorLogs().isEmpty());
		assertTrue(txt.getText().contains("definecolor{color3b}{rgb}{"));
	}

	@Test public void testParse_pstribox_star() throws ParseException {
		IGroup group = parser.parsePSTCode("\\pstribox*[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\pstribox*[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test public void testParse_pstribox() throws ParseException {
		IGroup group = parser.parsePSTCode("\\pstribox[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\pstribox[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParse_psdiabox_star() throws ParseException {
		IGroup group = parser.parsePSTCode("\\psdiabox*[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psdiabox*[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test public void testParse_psdiabox() throws ParseException {
		IGroup group = parser.parsePSTCode("\\psdiabox[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psdiabox[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParse_psovalbox_star() throws ParseException {
		IGroup group = parser.parsePSTCode("\\psovalbox*[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psovalbox*[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test public void testParse_psovalbox() throws ParseException {
		IGroup group = parser.parsePSTCode("\\psovalbox[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psovalbox[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParse_pscirclebox_star() throws ParseException {
		IGroup group = parser.parsePSTCode("\\pscirclebox*[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\pscirclebox*[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test public void testParse_pscirclebox() throws ParseException {
		IGroup group = parser.parsePSTCode("\\pscirclebox[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\pscirclebox[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testParse_psshadowbox_star() throws ParseException {
		IGroup group = parser.parsePSTCode("\\psshadowbox*[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psshadowbox*[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}



	@Test public void testParse_psshadowbox() throws ParseException {
		IGroup group = parser.parsePSTCode("\\psshadowbox[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psshadowbox[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Test public void testBug911816() throws ParseException {
		// https://bugs.launchpad.net/latexdraw/+bug/911816
		IGroup group = parser.parsePSTCode("\\psframebox{$E=mc^2$}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psframebox{$E=mc^2$}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParse_psframebox() throws ParseException {
		IGroup group = parser.parsePSTCode("\\psframebox[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psframebox[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParse_psdblframebox() throws ParseException {
		IGroup group = parser.parsePSTCode("\\psdblframebox{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psdblframebox{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParse_psframebox_star() throws ParseException {
		IGroup group = parser.parsePSTCode("\\psframebox*[doubleline=true]{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psframebox*[doubleline=true]{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}

	@Test public void testParse_psdblframebox_star() throws ParseException {
		IGroup group = parser.parsePSTCode("\\psdblframebox*{\\psframe(0,1)}").get();
		assertEquals(1, group.size());
		assertTrue(group.getShapeAt(0) instanceof IText);
		IText text = ((IText)group.getShapeAt(0));
		assertEquals("\\psdblframebox*{\\psframe(0,1)}", text.getText());
		assertTrue(PSTParser.errorLogs().isEmpty());
	}


	@Override
	public String getCommandName() {
		return "";
	}

	@Override
	public String getBasicCoordinates() {
		return "";
	}
}
