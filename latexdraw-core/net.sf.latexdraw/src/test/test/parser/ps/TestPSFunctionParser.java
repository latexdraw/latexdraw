package test.parser.ps;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
import net.sf.latexdraw.parsers.ps.PSFunctionParser;

import org.junit.Test;

public class TestPSFunctionParser {
	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testPSFunctionParserNull() throws InvalidFormatPSFunctionException {
		new PSFunctionParser(null);
	}

	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testPSFunctionParserEmpty() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("");
	}

	@SuppressWarnings("unused")
	@Test(expected=InvalidFormatPSFunctionException.class)
	public void testPSFunctionParserInvalid1() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("Y");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserSinOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("sin");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserCosOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("cos");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserAddOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("add");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserModOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("mod");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserMulOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("mul");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserAbsOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("abs");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserCeilingOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("ceiling");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserClearOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("clear");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserCountOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("count");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserDivOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("div");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserDupOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("dup");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserExchOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("exch");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserExpOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("exp");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserFloorOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("floor");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserIDivOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("idiv");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserNegOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("neg");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserPlotXOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("x");
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserPopOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("pop");
	}


	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserSubOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("sub");
	}

	@Test
	public void testPSFunctionParserValueOk() throws InvalidFormatPSFunctionException {
		PSFunctionParser parser = new PSFunctionParser("2.3");
		assertEquals(2.3,parser.getY(0),0.0);
	}

	@Test
	public void testGetYX() throws InvalidFormatPSFunctionException {
		PSFunctionParser parser = new PSFunctionParser("x");
		assertEquals(2.0,parser.getY(2),0.0);
	}

	@Test
	public void testGetYSinX() throws InvalidFormatPSFunctionException {
		PSFunctionParser parser = new PSFunctionParser("x sin");
		assertEquals(Math.sin(Math.toRadians(2.0)),parser.getY(2),0.0);
	}

	@Test
	public void testGetYXAdd2() throws InvalidFormatPSFunctionException {
		PSFunctionParser parser = new PSFunctionParser("x 2 add");
		assertEquals(5.0,parser.getY(3),0.0);
	}

	@Test(expected=InvalidFormatPSFunctionException.class)
	public void testGetYXAdd_notOK() throws InvalidFormatPSFunctionException {
		PSFunctionParser parser = new PSFunctionParser("x add");
		parser.getY(3);
	}
}
