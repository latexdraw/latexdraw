package net.sf.latexdraw.gui.hand;

import com.google.inject.AbstractModule;
import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropModule;
import net.sf.latexdraw.gui.TestCoordDimShapeGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeCoordDimCustomiser;
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
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				bind(ShapeCoordDimCustomiser.class).asEagerSingleton();
				bind(Hand.class).asEagerSingleton();
				bind(Pencil.class).toInstance(pencil);
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
