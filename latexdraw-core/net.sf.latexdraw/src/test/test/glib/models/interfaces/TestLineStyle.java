package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.sf.latexdraw.glib.models.interfaces.shape.IShape.LineStyle;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

public class TestLineStyle {
	@Test public void testgetLatexToken() {
		assertEquals(LineStyle.DASHED.getLatexToken(), PSTricksConstants.LINE_DASHED_STYLE);
		assertEquals(LineStyle.DOTTED.getLatexToken(), PSTricksConstants.LINE_DOTTED_STYLE);
//		assertEquals(LineStyle.NONE.getLatexToken(), PSTricksConstants.LINE_NONE_STYLE);
		assertEquals(LineStyle.SOLID.getLatexToken(), PSTricksConstants.LINE_SOLID_STYLE);
	}

	@Test public void testGetStyle() {
		assertEquals(LineStyle.SOLID, LineStyle.getStyle(null));
		assertEquals(LineStyle.SOLID, LineStyle.getStyle("")); //$NON-NLS-1$
		assertEquals(LineStyle.getStyle(LineStyle.DASHED.toString()), LineStyle.DASHED);
		assertEquals(LineStyle.getStyle(LineStyle.DOTTED.toString()), LineStyle.DOTTED);
//		assertEquals(LineStyle.getStyle(LineStyle.NONE.toString()), LineStyle.NONE);
		assertEquals(LineStyle.getStyle(LineStyle.SOLID.toString()), LineStyle.SOLID);
	}
}
