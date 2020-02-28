package net.sf.latexdraw.util;

import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.service.PreferencesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(LatexdrawExtension.class)
public class TestUnit {
	@ParameterizedTest
	@EnumSource
	public void testGetUnit(final Unit unit) {
		assertEquals(unit, Unit.getUnit(unit.name()).orElseThrow());
	}

	@ParameterizedTest
	@EnumSource
	public void testGetLabel(final Unit unit) {
		assertThat(unit.getLabel(new PreferencesService().getBundle())).isNotEmpty();
	}

	@Test
	public void testGetUnitNULL() {
		assertTrue(Unit.getUnit(null).isEmpty());
	}

	@Test
	public void testGetUnitKO() {
		assertTrue(Unit.getUnit("bad").isEmpty());
	}
}
