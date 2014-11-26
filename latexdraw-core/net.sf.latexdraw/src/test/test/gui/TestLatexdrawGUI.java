package test.gui;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.LangTool;

import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

public abstract class TestLatexdrawGUI extends GuiTest {
	@Before
	public void setUp() {
		BadaboomCollector.INSTANCE.clear();
	}
	
	@Override
	public Parent getRootNode() {
		try {
			return FXMLLoader.load(LaTeXDraw.class.getResource(getFXMLPathFromLatexdraw()), LangTool.INSTANCE.getBundle());
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract String getFXMLPathFromLatexdraw();

	
	@Test
	public void testLaunchNoCrash() {
		assertTrue(BadaboomCollector.INSTANCE.isEmpty());
	}
}
