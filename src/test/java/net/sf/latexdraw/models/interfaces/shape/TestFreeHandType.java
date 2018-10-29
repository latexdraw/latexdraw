package net.sf.latexdraw.models.interfaces.shape;

import net.sf.latexdraw.data.StringData;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class TestFreeHandType {
	@Theory
	public void testGetType(final FreeHandStyle style) {
		assertEquals(style, FreeHandStyle.getType(style.toString()));
	}

	@Theory
	public void testGetTypeKO(@StringData(vals = {"", "ds qoqs"}, withNull = true) final String value) {
		assertEquals(FreeHandStyle.CURVES, FreeHandStyle.getType(value));
	}
}
