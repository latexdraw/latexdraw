package net.sf.latexdraw.view.pst;

import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.view.PolymorphFreeHandTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPSTFreeHand extends TestPSTBase<Freehand> implements PolymorphFreeHandTest {
	@Override
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedFreeHand")
	@Disabled
	public void testFreeHandInterval(final Freehand sh) {
		// PST import cannot determine the interval.
		final Freehand s2 = produceOutputShapeFrom(sh);
		assertEquals(sh.getNbPoints(), s2.getNbPoints());
	}
}
