package net.sf.latexdraw.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TestTriple {
	Triple<Double, String, Boolean> triple;

	@Before
	public void setUp() throws Exception {
		triple = new Triple<>(1.2, "bar", true);
	}

	@Test
	public void testA() {
		assertEquals(1.2, triple.a, 0.00001);
	}

	@Test
	public void testB() {
		assertEquals("bar", triple.b);
	}

	@Test
	public void testC() {
		assertTrue(triple.c);
	}

	@Test
	public void testToString() {
		assertThat(triple.toString(), not(isEmptyOrNullString()));
	}
}
