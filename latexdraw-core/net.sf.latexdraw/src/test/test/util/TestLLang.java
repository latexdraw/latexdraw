package test.util;

import static net.sf.latexdraw.lang.LangTool.INSTANCE;
import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.lang.LangTool;

import org.junit.Test;

public class TestLLang{
	@Test
	public void testGetOthersString() {
		assertEquals(INSTANCE.getStringOthers(null), LangTool.MISSING_KEY);
		assertEquals(INSTANCE.getStringOthers("thisisnotavalidkey;)"), "!thisisnotavalidkey;)!"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(INSTANCE.getStringOthers(""), "!!"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testGetStringLaTeXDrawFrame() {
		assertEquals(INSTANCE.getStringLaTeXDrawFrame(null), LangTool.MISSING_KEY);
		assertEquals(INSTANCE.getStringLaTeXDrawFrame("thisisnotavalidkey;)"), "!thisisnotavalidkey;)!"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(INSTANCE.getStringLaTeXDrawFrame(""), "!!"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testGetString1_6() {
		assertEquals(INSTANCE.getString16(null), LangTool.MISSING_KEY);
		assertEquals(INSTANCE.getString16("thisisnotavalidkey;)"), "!thisisnotavalidkey;)!"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(INSTANCE.getString16(""), "!!"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testGetString1_7() {
		assertEquals(INSTANCE.getString17(null), LangTool.MISSING_KEY);
		assertEquals(INSTANCE.getString17("thisisnotavalidkey;)"), "!thisisnotavalidkey;)!"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(INSTANCE.getString17(""), "!!"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testGetString1_8() {
		assertEquals(INSTANCE.getString18(null), LangTool.MISSING_KEY);
		assertEquals(INSTANCE.getString18("thisisnotavalidkey;)"), "!thisisnotavalidkey;)!"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(INSTANCE.getString18(""), "!!"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testGetString1_9() {
		assertEquals(INSTANCE.getString19(null), LangTool.MISSING_KEY);
		assertEquals(INSTANCE.getString19("thisisnotavalidkey;)"), "!thisisnotavalidkey;)!"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(INSTANCE.getString19(""), "!!"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testGetDialogFrameString() {
		assertEquals(INSTANCE.getStringDialogFrame(null), LangTool.MISSING_KEY);
		assertEquals(INSTANCE.getStringDialogFrame("thisisnotavalidkey;)"), "!thisisnotavalidkey;)!"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(INSTANCE.getStringDialogFrame(""), "!!"); //$NON-NLS-1$ //$NON-NLS-2$
	}


	@Test
	public void testLang() {
		INSTANCE.readLang();
		LangTool.getCurrentLanguage();
	}
}
