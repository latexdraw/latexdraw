package test.glib.models.interfaces;

import static org.junit.Assert.*;

import net.sf.latexdraw.glib.models.interfaces.shape.BorderPos;
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
		assertEquals(BorderPos.INTO, BorderPos.getStyle(null));
		assertEquals(BorderPos.INTO, BorderPos.getStyle("")); //$NON-NLS-1$
		assertEquals(BorderPos.INTO, BorderPos.getStyle("ezpoke zae")); //$NON-NLS-1$
		assertEquals(BorderPos.getStyle(BorderPos.INTO.toString()), BorderPos.INTO);
		assertEquals(BorderPos.getStyle(BorderPos.MID.toString()), BorderPos.MID);
		assertEquals(BorderPos.getStyle(BorderPos.OUT.toString()), BorderPos.OUT);
		assertEquals(BorderPos.getStyle(PSTricksConstants.BORDERS_INSIDE), BorderPos.INTO);
		assertEquals(BorderPos.getStyle(PSTricksConstants.BORDERS_MIDDLE), BorderPos.MID);
		assertEquals(BorderPos.getStyle(PSTricksConstants.BORDERS_OUTSIDE), BorderPos.OUT);
	}
}
