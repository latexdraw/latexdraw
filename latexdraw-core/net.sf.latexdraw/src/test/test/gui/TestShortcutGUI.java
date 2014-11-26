package test.gui;

import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.badaboom.BadaboomCollector;

import org.junit.Test;

public class TestShortcutGUI extends TestLatexdrawGUI {
	@Override
	public String getFXMLPathFromLatexdraw() {
		return "glib/views/jfx/ui/Shortcuts.fxml";
	}
	
	@Test
	public void testLaunchNoCrash() {
		assertTrue(BadaboomCollector.INSTANCE.isEmpty());
	}
}
