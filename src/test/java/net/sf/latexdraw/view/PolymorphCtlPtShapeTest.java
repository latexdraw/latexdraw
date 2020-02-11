package net.sf.latexdraw.view;

import net.sf.latexdraw.model.api.shape.ControlPointShape;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public interface PolymorphCtlPtShapeTest extends PolymorphicConversion<ControlPointShape> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedCtrlPtShape")
	default void testCtrlPt1(final ControlPointShape sh) {
		final ControlPointShape s2 = produceOutputShapeFrom(sh);
		assertThat(sh.getFirstCtrlPts()).isEqualTo(s2.getFirstCtrlPts());
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedCtrlPtShape")
	default void testCtrlPt2(final ControlPointShape sh) {
		final ControlPointShape s2 = produceOutputShapeFrom(sh);
		assertThat(sh.getSecondCtrlPts()).isEqualTo(s2.getSecondCtrlPts());
	}
}
