package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.ShapeTextCustomiser;
import net.sf.latexdraw.instrument.TestTextStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.TextPosition;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilTextStyle extends TestTextStyleGUI {

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				hand = mock(Hand.class);
				bindToInstance(Hand.class, hand);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Pencil.class);
				bindAsEagerSingleton(ShapeTextCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testSelectBLPencil() {
		Cmds.of(activatePencil, pencilCreatesText, updateIns, () -> selectPosition.execute(TextPosition.BOT_LEFT)).execute();
		assertEquals(TextPosition.BOT_LEFT, ((Text) editing.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.BOT_LEFT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectBRPencil() {
		Cmds.of(activatePencil, pencilCreatesText, updateIns, () -> selectPosition.execute(TextPosition.BOT_RIGHT)).execute();
		assertEquals(TextPosition.BOT_RIGHT, ((Text) editing.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.BOT_RIGHT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectBPencil() {
		Cmds.of(activatePencil, pencilCreatesText, updateIns, () -> selectPosition.execute(TextPosition.BOT)).execute();
		assertEquals(TextPosition.BOT, ((Text) editing.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.BOT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectTPencil() {
		Cmds.of(activatePencil, pencilCreatesText, updateIns, () -> selectPosition.execute(TextPosition.TOP)).execute();
		assertEquals(TextPosition.TOP, ((Text) editing.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.TOP, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectTLPencil() {
		Cmds.of(activatePencil, pencilCreatesText, updateIns, () -> selectPosition.execute(TextPosition.TOP_LEFT)).execute();
		assertEquals(TextPosition.TOP_LEFT, ((Text) editing.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.TOP_LEFT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectTRPencil() {
		Cmds.of(activatePencil, pencilCreatesText, updateIns, () -> selectPosition.execute(TextPosition.TOP_RIGHT)).execute();
		assertEquals(TextPosition.TOP_RIGHT, ((Text) editing.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.TOP_RIGHT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectRPencil() {
		Cmds.of(activatePencil, pencilCreatesText, updateIns, () -> selectPosition.execute(TextPosition.RIGHT)).execute();
		assertEquals(TextPosition.RIGHT, ((Text) editing.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.RIGHT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectLPencil() {
		Cmds.of(activatePencil, pencilCreatesText, updateIns, () -> selectPosition.execute(TextPosition.LEFT)).execute();
		assertEquals(TextPosition.LEFT, ((Text) editing.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.LEFT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectCentrePencil() {
		Cmds.of(activatePencil, pencilCreatesText, updateIns, () -> selectPosition.execute(TextPosition.CENTER)).execute();
		assertEquals(TextPosition.CENTER, ((Text) editing.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.CENTER, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesText, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesText, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}
}
