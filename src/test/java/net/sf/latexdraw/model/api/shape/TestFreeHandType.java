package net.sf.latexdraw.model.api.shape;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFreeHandType {
	@ParameterizedTest
	@EnumSource
	public void testGetType(final FreeHandStyle style) {
		assertEquals(style, FreeHandStyle.getType(style.toString()));
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "ds qoqs"})
	public void testGetTypeKO(final String value) {
		assertEquals(FreeHandStyle.CURVES, FreeHandStyle.getType(value));
	}
}
