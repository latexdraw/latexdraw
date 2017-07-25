package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import test.data.StringData;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class TestFreeHandType {
	@Theory
	public void testGetType(final FreeHandStyle style) {
		assertEquals(FreeHandStyle.getType(style.toString()), style);
	}

	@Theory
	public void testGetTypeKO(@StringData(vals = {"", "ds qoqs"}, withNull = true) final String value) {
		assertEquals(FreeHandStyle.getType(value), FreeHandStyle.CURVES);
	}
}
