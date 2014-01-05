package test.glib.models.interfaces;

import static org.junit.Assert.*;

import net.sf.latexdraw.glib.models.interfaces.prop.IFreeHandProp.FreeHandType;

import org.junit.Test;

public class TestFreeHandType {
	@Test
	public void testGetType() {
		assertEquals(FreeHandType.getType(FreeHandType.CURVES.toString()), FreeHandType.CURVES);
		assertEquals(FreeHandType.getType(FreeHandType.LINES.toString()), FreeHandType.LINES);
		assertEquals(FreeHandType.getType(null), FreeHandType.CURVES);
		assertEquals(FreeHandType.getType(""), FreeHandType.CURVES);
		assertEquals(FreeHandType.getType("ds qoqs"), FreeHandType.CURVES);
	}
}
