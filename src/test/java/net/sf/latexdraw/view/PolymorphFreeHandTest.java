package net.sf.latexdraw.view;

import net.sf.latexdraw.model.api.shape.Freehand;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface PolymorphFreeHandTest extends PolymorphicConversion<Freehand> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedFreeHand")
	default void testFreeHandInterval(final Freehand sh) {
		final Freehand s2 = produceOutputShapeFrom(sh);
		assertEquals(sh.getInterval(), s2.getInterval());
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedFreeHand")
	default void testFreeHandType(final Freehand sh) {
		final Freehand s2 = produceOutputShapeFrom(sh);
		assertEquals(sh.getType(), s2.getType());
	}
}
