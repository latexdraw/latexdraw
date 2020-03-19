package net.sf.latexdraw.instrument;

import io.github.interacto.command.CommandsRegistry;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.sf.latexdraw.command.shape.UpdateToGrid;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.GridStyle;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestShapeGridTransformer extends SelectionBasedTesting<ShapeGridTransformer> {
	Button updateToGrid;
	HBox mainPane;

	final CmdVoid clickAdaptGrid = () -> clickOn(updateToGrid);

	@Override
	protected String getFXMLPathFromLatexdraw() {
		return "/fxml/GridTransformer.fxml";
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		updateToGrid = find("#updateToGrid");
		mainPane = find("#mainPane");
		ins = injector.getInstance(ShapeGridTransformer.class);
		injector.getInstance(PreferencesService.class).gridStyleProperty().setValue(GridStyle.CUSTOMISED);
		ins.setActivated(true);
		ins.update();
	}

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				hand = mock(Hand.class);
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
				bindAsEagerSingleton(PreferencesService.class);
				bindToInstance(Pencil.class, pencil);
				bindAsEagerSingleton(ShapeGridTransformer.class);
				bindToInstance(MetaShapeCustomiser.class, Mockito.mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testDeactivatedEmpty() {
		assertFalse(ins.isActivated());
	}

	@Test
	public void testNotVisibleEmpty() {
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testActivateOnOneShapeSelected() {
		Cmds.of(selectOneShape).execute();
		assertTrue(ins.isActivated());
	}

	@Test
	public void testVisibleOnOneShapeSelected() {
		Cmds.of(selectOneShape).execute();
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testNotVisiblePencil() {
		when(pencil.isActivated()).thenReturn(true);
		when(hand.isActivated()).thenReturn(false);
		Cmds.of(selectOneShape).execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testNotActivatedPencil() {
		when(pencil.isActivated()).thenReturn(true);
		when(hand.isActivated()).thenReturn(false);
		Cmds.of(selectOneShape).execute();
		assertFalse(ins.isActivated());
	}

	@Test
	public void testNotActivatedGridStyleNone() {
		injector.getInstance(PreferencesService.class).gridStyleProperty().setValue(GridStyle.NONE);
		Cmds.of(selectOneShape).execute();
		assertFalse(ins.isActivated());
	}

	@Test
	public void testAlignBot() {
		Cmds.of(selectOneShape, clickAdaptGrid).execute();
		assertThat(CommandsRegistry.getInstance().getCommands().get(CommandsRegistry.getInstance().getCommands().size() - 1))
			.isInstanceOf(UpdateToGrid.class);
	}
}
