package net.sf.latexdraw.parsers.svg.parsers;

import java.text.ParseException;
import net.sf.latexdraw.parsers.TestCodeParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestCSSStyleParser extends TestCodeParser implements CSSStyleHandler {
	protected String name;

	protected String value;

	@Before
	public void setUp() {
		parser = new CSSStyleParser("", this); //$NON-NLS-1$
		parser2 = new CSSStyleParser("", this); //$NON-NLS-1$
	}

	@Test
	@Override
	public void testParse() throws ParseException {
		name = null;
		value = null;
		parser.setCode(""); //$NON-NLS-1$
		parser.parse();
		assertNull(name);
		assertNull(value);
		name = null;
		value = null;
		parser.setCode("\t \n/**/ \n\r \t /* fldijfsd */ \n"); //$NON-NLS-1$
		parser.parse();
		assertNull(name);
		assertNull(value);
		parser.setCode("stroke\t \n/**/ :\n\r \t red/* fldijfsd */ \n"); //$NON-NLS-1$
		parser.parse();
		assertEquals(name, "stroke"); //$NON-NLS-1$
		assertEquals(value, "red"); //$NON-NLS-1$
		parser.setCode("stroke\t \n/**/ :\n\r \t red/* fldijfsd */ ;\nstroke-width : 2cm"); //$NON-NLS-1$
		parser.parse();
		assertEquals(name, "stroke-width"); //$NON-NLS-1$
		assertEquals(value, "2cm"); //$NON-NLS-1$
	}

	@Test
	@Override
	public void testSkipComment() throws ParseException {
		parser.setCode("fill:/*test*/green"); //$NON-NLS-1$
		parser.parse();
		assertEquals(name, "fill"); //$NON-NLS-1$
		assertEquals(value, "green"); //$NON-NLS-1$
		parser.setCode(" /* fksduh fdssd \n \t fdsf d */ stroke : blue"); //$NON-NLS-1$
		parser.parse();
		assertEquals(name, "stroke"); //$NON-NLS-1$
		assertEquals(value, "blue"); //$NON-NLS-1$
		parser.setCode("fill:green /* fkdhf fss */  "); //$NON-NLS-1$
		parser.parse();
		assertEquals(name, "fill"); //$NON-NLS-1$
		assertEquals(value, "green"); //$NON-NLS-1$
		parser.setCode("stroke: /**/ blue   "); //$NON-NLS-1$
		parser.parse();
		assertEquals(name, "stroke"); //$NON-NLS-1$
		assertEquals(value, "blue"); //$NON-NLS-1$
		parser.setCode("stroke:/**/blue;"); //$NON-NLS-1$
		parser.parse();
		assertEquals(name, "stroke"); //$NON-NLS-1$
		assertEquals(value, "blue"); //$NON-NLS-1$
		parser.setCode("fill:green /* fkdhf fss */;/*f*/stroke/*fds fsd*/:/**/blue/*fgdds */"); //$NON-NLS-1$
		parser.parse();
		assertEquals(name, "stroke"); //$NON-NLS-1$
		assertEquals(value, "blue"); //$NON-NLS-1$
	}

	@Test
	@Override
	public void testSkipWSP() throws ParseException {
		parser.setCode("fill:green ;stroke:blue"); //$NON-NLS-1$
		parser.parse();
		assertEquals(name, "stroke"); //$NON-NLS-1$
		assertEquals(value, "blue"); //$NON-NLS-1$
		parser.setCode(" \n \t \r stroke\t\n \r: \r \t\n blue \r\t \n \t ;\t\t\n\r fill\t\r :\n green \r \t "); //$NON-NLS-1$
		parser.parse();
		assertEquals(name, "fill"); //$NON-NLS-1$
		assertEquals(value, "green"); //$NON-NLS-1$
	}

	@Override
	public void onCSSStyle(String n, String v) {
		name = n;
		value = v;
	}
}
