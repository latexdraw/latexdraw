package net.sf.latexdraw.view;

import net.sf.latexdraw.model.api.shape.Closable;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface PolymorphClosableShapeTest extends PolymorphicConversion<Shape> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedOpenedShape")
	default void testOpened(final Shape sh) {
		final Closable s2 = (Closable) produceOutputShapeFrom(sh);
		assertEquals(((Closable) sh).isOpened(), s2.isOpened());
	}
}
