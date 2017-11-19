package net.sf.latexdraw.gui.hand;

import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestCoordDimShapeGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeCoordDimCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHandCoordDimStyle extends TestCoordDimShapeGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapeCoordDimCustomiser.class);
				bindAsEagerSingleton(Hand.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Pencil.class, pencil);
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelection() {
		new CompositeGUIVoidCommand(selectionAddArc, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(tlxS.isVisible());
		assertTrue(tlyS.isVisible());
	}

	@Test
	public void testSetYSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddRec, updateIns).execute();
		double angle = tlyS.getValue();
		incrementY.execute();
		assertEquals(tlyS.getValue(), drawing.getSelection().getShapeAt(0).getTopLeftPoint().getY(), 0.0001);
		assertEquals(tlyS.getValue(), drawing.getSelection().getShapeAt(1).getTopLeftPoint().getY(), 0.0001);
		assertNotEquals(angle, tlyS.getValue(), 0.0001);
	}

	@Test
	public void testSetXSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddRec, updateIns).execute();
		double angle = tlxS.getValue();
		incrementX.execute();
		assertEquals(tlxS.getValue(), drawing.getSelection().getShapeAt(0).getTopLeftPoint().getX(), 0.0001);
		assertEquals(tlxS.getValue(), drawing.getSelection().getShapeAt(1).getTopLeftPoint().getX(), 0.0001);
		assertNotEquals(angle, tlxS.getValue(), 0.0001);
	}
}
