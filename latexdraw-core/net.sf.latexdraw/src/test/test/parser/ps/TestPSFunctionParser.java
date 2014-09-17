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
		new PSFunctionParser(""); //$NON-NLS-1$
	}

	@SuppressWarnings("unused")
	@Test(expected=InvalidFormatPSFunctionException.class)
	public void testPSFunctionParserInvalid1() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("Y"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserSinOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("sin"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserCosOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("cos"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserAddOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("add"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserModOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("mod"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserMulOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("mul"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserAbsOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("abs"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserCeilingOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("ceiling"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserClearOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("clear"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserCountOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("count"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserDivOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("div"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserDupOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("dup"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserExchOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("exch"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserExpOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("exp"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserFloorOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("floor"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserIDivOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("idiv"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserNegOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("neg"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserPlotXOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("x"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserPopOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("pop"); //$NON-NLS-1$
	}


	@SuppressWarnings("unused") @Test
	public void testPSFunctionParserSubOk() throws InvalidFormatPSFunctionException {
		new PSFunctionParser("sub"); //$NON-NLS-1$
	}

	@Test
	public void testPSFunctionParserValueOk() throws InvalidFormatPSFunctionException {
		PSFunctionParser parser = new PSFunctionParser("2.3"); //$NON-NLS-1$
		assertEquals(2.3,parser.getY(0),0.0);
	}

	@Test
	public void testGetYX() throws InvalidFormatPSFunctionException {
		PSFunctionParser parser = new PSFunctionParser("x"); //$NON-NLS-1$
		assertEquals(2.0,parser.getY(2),0.0);
	}

	@Test
	public void testGetYSinX() throws InvalidFormatPSFunctionException {
		PSFunctionParser parser = new PSFunctionParser("x sin"); //$NON-NLS-1$
		assertEquals(Math.sin(Math.toRadians(2.0)),parser.getY(2),0.0);
	}

	@Test
	public void testGetYXAdd2() throws InvalidFormatPSFunctionException {
		PSFunctionParser parser = new PSFunctionParser("x 2 add"); //$NON-NLS-1$
		assertEquals(5.0,parser.getY(3),0.0);
	}

	@Test(expected=InvalidFormatPSFunctionException.class)
	public void testGetYXAdd_notOK() throws InvalidFormatPSFunctionException {
		PSFunctionParser parser = new PSFunctionParser("x add"); //$NON-NLS-1$
		parser.getY(3);
	}
}
