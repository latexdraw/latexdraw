package net.sf.latexdraw.util;

import net.sf.latexdraw.LatexdrawExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(LatexdrawExtension.class)
public class TestTriple {
	Triple<Double, String, Boolean> triple;

	@BeforeEach
	void setUp() {
		triple = new Triple<>(1.2, "bar", true);
	}

	@Test
	void testA() {
		assertEquals(1.2, triple.a, 0.00001);
	}

	@Test
	void testB() {
		assertEquals("bar", triple.b);
	}

	@Test
	void testC() {
		assertTrue(triple.c);
	}

	@Test
	void testToString() {
		assertThat(triple.toString()).isNotEmpty();
	}
}
