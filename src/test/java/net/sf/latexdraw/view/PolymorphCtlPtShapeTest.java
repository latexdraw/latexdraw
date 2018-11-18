package net.sf.latexdraw.view;

import net.sf.latexdraw.CollectionMatcher;
import net.sf.latexdraw.model.api.shape.ControlPointShape;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface PolymorphCtlPtShapeTest extends PolymorphicConversion<ControlPointShape>, CollectionMatcher {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedCtrlPtShape")
	default void testCtrlPt1(final ControlPointShape sh) {
		final ControlPointShape s2 = produceOutputShapeFrom(sh);
		assertListEquals(sh.getFirstCtrlPts(), s2.getFirstCtrlPts(), (p1, p2) -> assertTrue(p1.equals(p2, 0.0001)));
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedCtrlPtShape")
	default void testCtrlPt2(final ControlPointShape sh) {
		final ControlPointShape s2 = produceOutputShapeFrom(sh);
		assertListEquals(sh.getSecondCtrlPts(), s2.getSecondCtrlPts(), (p1, p2) -> assertTrue(p1.equals(p2, 0.0001)));
	}
}
