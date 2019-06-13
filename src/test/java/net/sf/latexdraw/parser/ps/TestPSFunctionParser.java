package net.sf.latexdraw.parser.ps;

import net.sf.latexdraw.util.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPSFunctionParser {
	@Test
	void testPSFunctionParserNull() throws InvalidFormatPSFunctionException {
		assertThrows(IllegalArgumentException.class, () -> new PSFunctionParser(null));
	}

	@Test
	void testPSFunctionParserEmpty() throws InvalidFormatPSFunctionException {
		assertThrows(IllegalArgumentException.class, () -> new PSFunctionParser(""));
	}

	@Test
	void testPSFunctionParserInvalid1() throws InvalidFormatPSFunctionException {
		assertThrows(IllegalArgumentException.class, () -> new PSFunctionParser("Y"));
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserSinOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("sin");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserCosOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("cos");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserAddOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("add");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserModOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("mod");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserMulOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("mul");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserAbsOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("abs");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserCeilingOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("ceiling");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserClearOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("clear");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserCountOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("count");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserDivOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("div");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserDupOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("dup");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserExchOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("exch");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserExpOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("exp");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserFloorOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("floor");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserIDivOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("idiv");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserNegOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("neg");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserPlotXOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("x");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserPopOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("pop");
	}

	@SuppressWarnings("unused")
	@Test
	void testPSFunctionParserSubOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("sub");
	}

	@Test
	void testPSFunctionParserValueOk() throws InvalidFormatPSFunctionException {
		final PSFunctionParser parser = new PSFunctionParser("2.3");
		assertEquals(2.3, parser.getY(0), 0.0001);
	}

	@Test
	void testGetYX() throws InvalidFormatPSFunctionException {
		final PSFunctionParser parser = new PSFunctionParser("x");
		assertEquals(2.0, parser.getY(2), 0.0001);
	}

	@Test
	void testGetYSinX() throws InvalidFormatPSFunctionException {
		final PSFunctionParser parser = new PSFunctionParser("x sin");
		assertEquals(Math.sin(Math.toRadians(2.0)), parser.getY(2), 0.0001);
	}

	@Test
	void testGetYXAdd2() throws InvalidFormatPSFunctionException {
		final PSFunctionParser parser = new PSFunctionParser("x 2 add");
		assertEquals(5.0, parser.getY(3), 0.0001);
	}

	@ParameterizedTest
	@ValueSource(strings = {"x add", "  x    add    "})
	void testGetYXAddnotOK(final String cmd) throws InvalidFormatPSFunctionException {
		final PSFunctionParser parser = new PSFunctionParser(cmd);
		assertThrows(InvalidFormatPSFunctionException.class, () -> parser.getY(3));
	}

	@Test
	void testGetYNoCmd() {
		final PSFunctionParser parser = new PSFunctionParser("clear");
		assertThrows(InvalidFormatPSFunctionException.class, () -> parser.getY(0));
	}

	@Test
	void testNoCmd() {
		final PSFunctionParser parser = new PSFunctionParser("    ");
		assertThrows(InvalidFormatPSFunctionException.class, () -> parser.getY(0));
	}

	@Test
	void testIsValidPostFixEquationOK() {
		final Tuple<Boolean, String> res = PSFunctionParser.isValidPostFixEquation("x", 0, 10, 10);
		assertThat(res.b).isEmpty();
		assertThat(res.a).isTrue();
	}

	@Test
	void testIsValidPostFixEquationKOInfinite() {
		final Tuple<Boolean, String> res = PSFunctionParser.isValidPostFixEquation("x log", 0, 10, 10);
		assertThat(res.b).isNotEmpty();
		assertThat(res.a).isFalse();
	}

	@Test
	void testIsValidPostFixEquationKONaN() {
		final Tuple<Boolean, String> res = PSFunctionParser.isValidPostFixEquation("x log", -10, -1, 10);
		assertThat(res.b).isNotEmpty();
		assertThat(res.a).isFalse();
	}

	@Test
	void testIsValidPostFixEquationKOInfiniteEnd() {
		final Tuple<Boolean, String> res = PSFunctionParser.isValidPostFixEquation("x log", 10, 0, 10);
		assertThat(res.b).isNotEmpty();
		assertThat(res.a).isFalse();
	}

	@Test
	void testIsValidPostFixEquationKONaNEnd() {
		final Tuple<Boolean, String> res = PSFunctionParser.isValidPostFixEquation("x log", 0.1, -10, 10);
		assertThat(res.b).isNotEmpty();
		assertThat(res.a).isFalse();
	}

	@Test
	void testIsValidPostFixEquationKOArithmEx() {
		final Tuple<Boolean, String> res = PSFunctionParser.isValidPostFixEquation("x 0 div", 0, 10, 10);
		assertThat(res.b).isNotEmpty();
		assertThat(res.a).isFalse();
	}

	@Test
	void testIsValidPostFixEquationKOFormat() {
		final Tuple<Boolean, String> res = PSFunctionParser.isValidPostFixEquation("foo", 0, 10, 10);
		assertThat(res.b).isNotEmpty();
		assertThat(res.a).isFalse();
	}
}
