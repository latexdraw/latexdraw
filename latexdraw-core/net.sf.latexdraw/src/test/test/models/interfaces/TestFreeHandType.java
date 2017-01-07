package test.models.interfaces;

import static org.junit.Assert.*;

import org.junit.Test;

import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;

public class TestFreeHandType {
	@Test
	public void testGetType() {
		assertEquals(FreeHandStyle.getType(FreeHandStyle.CURVES.toString()), FreeHandStyle.CURVES);
		assertEquals(FreeHandStyle.getType(FreeHandStyle.LINES.toString()), FreeHandStyle.LINES);
		assertEquals(FreeHandStyle.CURVES, FreeHandStyle.getType(null));
		assertEquals(FreeHandStyle.getType(""), FreeHandStyle.CURVES); //$NON-NLS-1$
		assertEquals(FreeHandStyle.getType("ds qoqs"), FreeHandStyle.CURVES); //$NON-NLS-1$
	}
}
