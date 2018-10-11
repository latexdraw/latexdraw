package net.sf.latexdraw.util;

import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Theories.class)
public class TestUnit {
	@Theory
	public void testGetUnit(final Unit unit) {
		assertEquals(unit, Unit.getUnit(unit.getLabel()));
	}

	@Theory
	public void testgetLabel(final Unit unit) {
		assertThat(unit.getLabel(), not(is(emptyOrNullString())));
	}

	@Test
	public void testGetUnitNULL() {
		assertEquals(Unit.CM, Unit.getUnit(null));
	}

	@Test
	public void testGetUnitKO() {
		assertEquals(Unit.CM, Unit.getUnit("bad"));
	}
}
