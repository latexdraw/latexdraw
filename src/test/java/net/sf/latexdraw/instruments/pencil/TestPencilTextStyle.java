package net.sf.latexdraw.instruments.pencil;

import java.lang.reflect.InvocationTargetException;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.ShapeTextCustomiser;
import net.sf.latexdraw.instruments.TestTextStyleGUI;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.TextPosition;
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
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeTextCustomiser.class);
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
			}
		};
	}

	@Test
	public void testSelectBLPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		selectPosition.execute(TextPosition.BOT_LEFT);
		waitFXEvents.execute();
		assertEquals(TextPosition.BOT_LEFT, ((IText) pencil.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.BOT_LEFT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectBRPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		selectPosition.execute(TextPosition.BOT_RIGHT);
		waitFXEvents.execute();
		assertEquals(TextPosition.BOT_RIGHT, ((IText) pencil.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.BOT_RIGHT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectBPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		selectPosition.execute(TextPosition.BOT);
		waitFXEvents.execute();
		assertEquals(TextPosition.BOT, ((IText) pencil.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.BOT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectTPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		selectPosition.execute(TextPosition.TOP);
		waitFXEvents.execute();
		assertEquals(TextPosition.TOP, ((IText) pencil.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.TOP, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectTLPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		selectPosition.execute(TextPosition.TOP_LEFT);
		waitFXEvents.execute();
		assertEquals(TextPosition.TOP_LEFT, ((IText) pencil.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.TOP_LEFT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectTRPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		selectPosition.execute(TextPosition.TOP_RIGHT);
		waitFXEvents.execute();
		assertEquals(TextPosition.TOP_RIGHT, ((IText) pencil.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.TOP_RIGHT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectRPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		selectPosition.execute(TextPosition.RIGHT);
		waitFXEvents.execute();
		assertEquals(TextPosition.RIGHT, ((IText) pencil.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.RIGHT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectLPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		selectPosition.execute(TextPosition.LEFT);
		waitFXEvents.execute();
		assertEquals(TextPosition.LEFT, ((IText) pencil.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.LEFT, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testSelectCentrePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		selectPosition.execute(TextPosition.CENTER);
		waitFXEvents.execute();
		assertEquals(TextPosition.CENTER, ((IText) pencil.createShapeInstance()).getTextPosition());
		assertEquals(TextPosition.CENTER, textPos.getSelectionModel().getSelectedItem());
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}
}
