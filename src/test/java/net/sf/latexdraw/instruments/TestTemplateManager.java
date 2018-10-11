package net.sf.latexdraw.instruments;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.LaTeXDraw;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.view.svg.SVGDocumentGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class TestTemplateManager extends BaseTestCanvas {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToInstance(Border.class, Mockito.mock(Border.class));
				bindToInstance(CanvasController.class, Mockito.mock(CanvasController.class));
				bindAsEagerSingleton(FacadeCanvasController.class);
				bindAsEagerSingleton(Hand.class);
				bindAsEagerSingleton(TemplateManager.class);
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(StatusBarController.class, Mockito.mock(StatusBarController.class));
			}
		};
	}

	@Override
	public void start(final Stage aStage) {
		super.start(aStage);
		try {
			final TitledPane root = FXMLLoader.load(LaTeXDraw.class.getResource("/fxml/Template.fxml"), LangTool.INSTANCE.getBundle(),
				new LatexdrawBuilderFactory(injector), injectorFactory);
			final BorderPane pane = new BorderPane();
			pane.setTop(root.getContent());
			pane.setCenter(stage.getScene().getRoot());
			stage.getScene().setRoot(pane);
		}catch(final IOException ex) {
			fail(ex.getMessage());
		}
	}

	@Override
	@Before
	public void setUp() {
		final LaTeXDraw ld = Mockito.mock(LaTeXDraw.class);
		try {
			HelperTest.setFinalStaticFieldValue(LaTeXDraw.class.getDeclaredField("instance"), ld);
		}catch(final NoSuchFieldException | IllegalAccessException ex) {
			fail(ex.getMessage());
		}
		Mockito.when(LaTeXDraw.getInstance().getInjector()).thenReturn(injector);
		Mockito.when(LaTeXDraw.getInstance().getInstanceCallBack()).thenReturn(cl -> injector.getInstance(cl));
		super.setUp();
		hand.setActivated(true);
		when(pencil.isActivated()).thenReturn(false);
	}

	@Test
	public void testDnDInsertTemplate() {
		final ImageView view = new ImageView(new Image(getClass().getResourceAsStream("/Condenser.svg.png"))); //NON-NLS
		view.setUserData(getClass().getResource("/Condenser.svg").getPath());
		final FlowPane pane = find("#templatePane");
		Platform.runLater(() -> pane.getChildren().add(0, view));
		waitFXEvents.execute();
		drag(pane.getChildren().get(0)).dropTo(canvas);
		waitFXEvents.execute();
		assertEquals(1, canvas.getDrawing().size());
	}

	@Test
	public void testClickRefreshTemplates() {
		final SVGDocumentGenerator gen = SVGDocumentGenerator.INSTANCE;
		final SVGDocumentGenerator mockGen = Mockito.mock(SVGDocumentGenerator.class);
		try {
			HelperTest.setFinalStaticFieldValue(SVGDocumentGenerator.class.getDeclaredField("INSTANCE"), mockGen);
		}catch(final NoSuchFieldException | IllegalAccessException ex) {
			fail(ex.getMessage());
		}
		clickOn("#updateTemplates");
		waitFXEvents.execute();
		Mockito.verify(mockGen, Mockito.times(1)).updateTemplates(Mockito.any(), Mockito.anyBoolean());

		try {
			HelperTest.setFinalStaticFieldValue(SVGDocumentGenerator.class.getDeclaredField("INSTANCE"), gen);
		}catch(final NoSuchFieldException | IllegalAccessException ex) {
			fail(ex.getMessage());
		}
	}
}
