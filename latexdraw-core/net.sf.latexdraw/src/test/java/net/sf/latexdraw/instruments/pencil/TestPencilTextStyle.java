package net.sf.latexdraw.instruments.pencil;

import java.lang.reflect.InvocationTargetException;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestTextStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeTextCustomiser;
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
		clickOnblButton.execute();
		assertEquals(TextPosition.BOT_LEFT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(blButton.isSelected());
	}

	@Test
	public void testSelectBRPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOnbrButton.execute();
		assertEquals(TextPosition.BOT_RIGHT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(brButton.isSelected());
	}

	@Test
	public void testSelectBPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOnbButton.execute();
		assertEquals(TextPosition.BOT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(bButton.isSelected());
	}

	@Test
	public void testSelectTPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOntButton.execute();
		assertEquals(TextPosition.TOP, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(tButton.isSelected());
	}

	@Test
	public void testSelectTLPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOntlButton.execute();
		assertEquals(TextPosition.TOP_LEFT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(tlButton.isSelected());
	}

	@Test
	public void testSelectTRPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOntrButton.execute();
		assertEquals(TextPosition.TOP_RIGHT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(trButton.isSelected());
	}

	@Test
	public void testSelectRPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOnrButton.execute();
		assertEquals(TextPosition.RIGHT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(rButton.isSelected());
	}

	@Test
	public void testSelectLPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOnlButton.execute();
		assertEquals(TextPosition.LEFT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(lButton.isSelected());
	}

	@Test
	public void testSelectCentrePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOncentreButton.execute();
		assertEquals(TextPosition.CENTER, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(centreButton.isSelected());
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
