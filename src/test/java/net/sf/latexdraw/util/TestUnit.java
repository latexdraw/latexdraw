package net.sf.latexdraw.util;

import net.sf.latexdraw.service.PreferencesService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUnit {
	@ParameterizedTest
	@EnumSource(Unit.class)
	public void testGetUnit(final Unit unit) {
		assertEquals(unit, Unit.getUnit(unit.name()).orElseThrow());
	}

	@ParameterizedTest
	@EnumSource(Unit.class)
	public void testGetLabel(final Unit unit) {
		assertThat(unit.getLabel(new PreferencesService().getBundle())).isNotEmpty();
	}

	@ParameterizedTest
	@EnumSource(Unit.class)
	public void testGetUnitNULL() {
		assertTrue(Unit.getUnit(null).isEmpty());
	}

	@ParameterizedTest
	@EnumSource(Unit.class)
	public void testGetUnitKO() {
		assertTrue(Unit.getUnit("bad").isEmpty());
	}
}
