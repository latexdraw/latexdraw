package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestArcStyle {
	@Test
	public void testSupportArrow() {
		assertFalse(ArcStyle.CHORD.supportArrow());
		assertFalse(ArcStyle.WEDGE.supportArrow());
		assertTrue(ArcStyle.ARC.supportArrow());
	}

	@Theory
	public void testGetLabel(final ArcStyle style) {
		assertNotNull(style.getLabel());
		assertFalse(style.getLabel().isEmpty());
	}
}
