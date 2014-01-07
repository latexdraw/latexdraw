package test.glib.models.interfaces;

import static org.junit.Assert.*;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.BorderPos;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;

import org.junit.Test;

public class TestBorderPos {
	@Test
	public void testGetLatexToken() {
		assertEquals(BorderPos.INTO.getLatexToken(), PSTricksConstants.BORDERS_INSIDE);
		assertEquals(BorderPos.MID.getLatexToken(), PSTricksConstants.BORDERS_MIDDLE);
		assertEquals(BorderPos.OUT.getLatexToken(), PSTricksConstants.BORDERS_OUTSIDE);
	}


	@Test
	public void testGetStyle() {
		assertNull(BorderPos.getStyle(null));
		assertNull(BorderPos.getStyle(""));
		assertNull(BorderPos.getStyle("ezpoke zae"));
		assertNull(BorderPos.getStyle("7dsqd 4ds'"));
		assertEquals(BorderPos.getStyle(BorderPos.INTO.toString()), BorderPos.INTO);
		assertEquals(BorderPos.getStyle(BorderPos.MID.toString()), BorderPos.MID);
		assertEquals(BorderPos.getStyle(BorderPos.OUT.toString()), BorderPos.OUT);

		assertNull(BorderPos.getStyle(null));
		assertNull(BorderPos.getStyle(""));
		assertNull(BorderPos.getStyle("dez"));
		assertNull(BorderPos.getStyle("&ç eydfs h"));
		assertEquals(BorderPos.getStyle(PSTricksConstants.BORDERS_INSIDE), BorderPos.INTO);
		assertEquals(BorderPos.getStyle(PSTricksConstants.BORDERS_MIDDLE), BorderPos.MID);
		assertEquals(BorderPos.getStyle(PSTricksConstants.BORDERS_OUTSIDE), BorderPos.OUT);
	}
}
