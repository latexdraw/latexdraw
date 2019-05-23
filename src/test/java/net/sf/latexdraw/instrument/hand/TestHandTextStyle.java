package net.sf.latexdraw.instrument.hand;

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
public class TestHandTextStyle extends TestTextStyleGUI {

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				bindToInstance(Pencil.class, pencil);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Hand.class);
				bindAsEagerSingleton(ShapeTextCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		Cmds.of(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelectionGrid() {
		Cmds.of(selectionAddText, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotGrid() {
		Cmds.of(selectionAddRec, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		Cmds.of(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testSelectBLPencil() {
		Cmds.of(activateHand, selectionAddText, selectionAddGrid, selectionAddText, updateIns).execute();
		selectPosition.execute(TextPosition.BOT_LEFT);
		waitFXEvents.execute();
		assertEquals(TextPosition.BOT_LEFT, ((Text) drawing.getSelection().getShapeAt(0).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.BOT_LEFT, ((Text) drawing.getSelection().getShapeAt(2).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.BOT_LEFT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectBRPencil() {
		Cmds.of(activateHand, selectionAddText, selectionAddGrid, selectionAddText, updateIns).execute();
		selectPosition.execute(TextPosition.BOT_RIGHT);
		waitFXEvents.execute();
		assertEquals(TextPosition.BOT_RIGHT, ((Text) drawing.getSelection().getShapeAt(0).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.BOT_RIGHT, ((Text) drawing.getSelection().getShapeAt(2).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.BOT_RIGHT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectBPencil() {
		Cmds.of(activateHand, selectionAddText, selectionAddGrid, selectionAddText, updateIns).execute();
		selectPosition.execute(TextPosition.BOT);
		waitFXEvents.execute();
		assertEquals(TextPosition.BOT, ((Text) drawing.getSelection().getShapeAt(0).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.BOT, ((Text) drawing.getSelection().getShapeAt(2).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.BOT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectTPencil() {
		Cmds.of(activateHand, selectionAddText, selectionAddGrid, selectionAddText, updateIns).execute();
		selectPosition.execute(TextPosition.TOP);
		waitFXEvents.execute();
		assertEquals(TextPosition.TOP, ((Text) drawing.getSelection().getShapeAt(0).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.TOP, ((Text) drawing.getSelection().getShapeAt(2).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.TOP, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectTLPencil() {
		Cmds.of(activateHand, selectionAddText, selectionAddGrid, selectionAddText, updateIns).execute();
		selectPosition.execute(TextPosition.TOP_LEFT);
		waitFXEvents.execute();
		assertEquals(TextPosition.TOP_LEFT, ((Text) drawing.getSelection().getShapeAt(0).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.TOP_LEFT, ((Text) drawing.getSelection().getShapeAt(2).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.TOP_LEFT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectTRPencil() {
		Cmds.of(activateHand, selectionAddText, selectionAddGrid, selectionAddText, updateIns).execute();
		selectPosition.execute(TextPosition.TOP_RIGHT);
		waitFXEvents.execute();
		assertEquals(TextPosition.TOP_RIGHT, ((Text) drawing.getSelection().getShapeAt(0).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.TOP_RIGHT, ((Text) drawing.getSelection().getShapeAt(2).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.TOP_RIGHT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectRPencil() {
		Cmds.of(activateHand, selectionAddText, selectionAddGrid, selectionAddText, updateIns).execute();
		selectPosition.execute(TextPosition.RIGHT);
		waitFXEvents.execute();
		assertEquals(TextPosition.RIGHT, ((Text) drawing.getSelection().getShapeAt(0).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.RIGHT, ((Text) drawing.getSelection().getShapeAt(2).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.RIGHT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectLPencil() {
		Cmds.of(activateHand, selectionAddText, selectionAddGrid, selectionAddText, updateIns).execute();
		selectPosition.execute(TextPosition.LEFT);
		waitFXEvents.execute();
		assertEquals(TextPosition.LEFT, ((Text) drawing.getSelection().getShapeAt(0).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.LEFT, ((Text) drawing.getSelection().getShapeAt(2).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.LEFT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectCentrePencil() {
		Cmds.of(activateHand, selectionAddText, selectionAddGrid, selectionAddText, updateIns).execute();
		selectPosition.execute(TextPosition.CENTER);
		waitFXEvents.execute();
		assertEquals(TextPosition.CENTER, ((Text) drawing.getSelection().getShapeAt(0).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.CENTER, ((Text) drawing.getSelection().getShapeAt(2).orElseThrow()).getTextPosition());
		assertEquals(TextPosition.CENTER, textPos.getSelectionModel().getSelectedItem());
	}
}
