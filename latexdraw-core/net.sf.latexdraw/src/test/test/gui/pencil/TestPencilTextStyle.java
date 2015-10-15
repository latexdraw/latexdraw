package test.gui.pencil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import net.sf.latexdraw.glib.models.interfaces.prop.ITextProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeTextCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestTextStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilTextStyle extends TestTextStyleGUI {

	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapeTextCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
			}
		};
	}

	@Test
	public void testSelectBLPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOnblButton.execute();
		assertEquals(ITextProp.TextPosition.BOT_LEFT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(blButton.isSelected());
	}

	@Test
	public void testSelectBRPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOnbrButton.execute();
		assertEquals(ITextProp.TextPosition.BOT_RIGHT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(brButton.isSelected());
	}

	@Test
	public void testSelectBPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOnbButton.execute();
		assertEquals(ITextProp.TextPosition.BOT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(bButton.isSelected());
	}

	@Test
	public void testSelectTPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOntButton.execute();
		assertEquals(ITextProp.TextPosition.TOP, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(tButton.isSelected());
	}

	@Test
	public void testSelectTLPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOntlButton.execute();
		assertEquals(ITextProp.TextPosition.TOP_LEFT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(tlButton.isSelected());
	}

	@Test
	public void testSelectTRPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOntrButton.execute();
		assertEquals(ITextProp.TextPosition.TOP_RIGHT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(trButton.isSelected());
	}

	@Test
	public void testSelectRPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOnrButton.execute();
		assertEquals(ITextProp.TextPosition.RIGHT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(rButton.isSelected());
	}

	@Test
	public void testSelectLPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOnlButton.execute();
		assertEquals(ITextProp.TextPosition.LEFT, ((IText)pencil.createShapeInstance()).getTextPosition());
		assertTrue(lButton.isSelected());
	}

	@Test
	public void testSelectCentrePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesText, updateIns).execute();
		clickOncentreButton.execute();
		assertEquals(ITextProp.TextPosition.CENTER, ((IText)pencil.createShapeInstance()).getTextPosition());
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
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
	}
}
