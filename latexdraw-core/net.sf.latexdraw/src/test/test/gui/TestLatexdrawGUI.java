package test.gui;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.LangTool;

import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.util.WaitForAsyncUtils;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class TestLatexdrawGUI extends GuiTest {
	protected Callback<Class<?>, Object> guiceFactory;

	final protected GUICommand waitFXEvents = () -> WaitForAsyncUtils.waitForFxEvents();
	
	@Before
	public void setUp() {
		BadaboomCollector.INSTANCE.clear();
	}
	
	
	@Override
	public Parent getRootNode() {
		try {
			Injector injector = Guice.createInjector(createModule());
			guiceFactory = clazz -> injector.getInstance(clazz);
			return FXMLLoader.load(LaTeXDraw.class.getResource(getFXMLPathFromLatexdraw()), 
					LangTool.INSTANCE.getBundle(), new LatexdrawBuilderFactory(injector), guiceFactory);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected abstract String getFXMLPathFromLatexdraw();

	protected AbstractModule createModule() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				//
			}
		};
	}

	@Test
	public void testLaunchNoCrash() {
		assertTrue(BadaboomCollector.INSTANCE.isEmpty());
	}
}
