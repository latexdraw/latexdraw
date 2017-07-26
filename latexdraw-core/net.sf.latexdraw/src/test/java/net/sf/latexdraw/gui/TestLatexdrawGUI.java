package net.sf.latexdraw.gui;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.util.LangTool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.malai.action.ActionsRegistry;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertTrue;

public abstract class TestLatexdrawGUI extends ApplicationTest {
	protected Callback<Class<?>, Object> guiceFactory;

	protected final GUIVoidCommand waitFXEvents = WaitForAsyncUtils::waitForFxEvents;
	protected final GUIVoidCommand waitFX1Second = () -> WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);

	protected TitledPane titledPane;
	protected Stage stage;

	@Before
	public void setUp() {
		ActionsRegistry.INSTANCE.clear();
		BadaboomCollector.INSTANCE.clear();
		WaitForAsyncUtils.waitForFxEvents();
	}

	@After
	public void tearDown() throws TimeoutException {
		FxToolkit.hideStage();
		FxToolkit.cleanupStages();
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}

	@Override
	public void start(Stage aStage) {
		stage = aStage;

		try {
			final Injector injector = Guice.createInjector(createModule());
			guiceFactory = injector::getInstance;
			final Parent root = FXMLLoader.load(LaTeXDraw.class.getResource(getFXMLPathFromLatexdraw()), LangTool.INSTANCE.getBundle(),
				new LatexdrawBuilderFactory(injector), guiceFactory);

			Parent parent = root;


			// If the root is not a pane, its content may not be visible.
			// So, need to add a fictive pane to contain the widgets.
			if(root instanceof Pane) {
				parent = root;
			}else {
				// TitledPane leads to flaky tests with TestFX. So, replacing the TitlePane with a classical pane.
				if(parent instanceof TitledPane) {
					titledPane = (TitledPane) parent;
					final Node content = ((TitledPane) parent).getContent();
					if(content instanceof Parent) {
						parent = (Parent) content;
					}else {
						final BorderPane pane = new BorderPane();
						pane.setCenter(content);
						parent = pane;
					}
				}
			}

			final Scene scene = new Scene(parent);
			aStage.setScene(scene);
			aStage.show();
			aStage.toFront();
			if(root instanceof Region) {
				aStage.minHeightProperty().bind(((Region)root).heightProperty());
				aStage.minWidthProperty().bind(((Region)root).widthProperty());
			}
			aStage.sizeToScene();
		}catch(final IOException ex) {
			ex.printStackTrace();
		}
	}

	public <T extends Node> T find(String query) {
		return lookup(query).query();
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
