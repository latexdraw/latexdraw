package net.sf.latexdraw.gui.hand;

import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestArcStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeArcCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHandArcStyle extends TestArcStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				pencil = mock(Pencil.class);
				bindAsEagerSingleton(ShapeArcCustomiser.class);
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
	public void testControllerActivatedWhenSelectionArc() {
		new CompositeGUIVoidCommand(selectionAddArc, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotArc() {
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testArcTypeChordSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns, selectWedge, selectChord).execute();
		assertEquals(ArcStyle.CHORD, ((IArc)drawing.getSelection().getShapeAt(0)).getArcStyle());
		assertEquals(ArcStyle.CHORD, ((IArc)drawing.getSelection().getShapeAt(2)).getArcStyle());
	}

	@Test
	public void testArcTypeArcSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns, selectChord, selectArc).execute();
		assertEquals(ArcStyle.ARC, ((IArc)drawing.getSelection().getShapeAt(0)).getArcStyle());
		assertEquals(ArcStyle.ARC, ((IArc)drawing.getSelection().getShapeAt(2)).getArcStyle());
	}

	@Test
	public void testArcTypeWedgeSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns, selectChord, selectWedge).execute();
		assertEquals(ArcStyle.WEDGE, ((IArc)drawing.getSelection().getShapeAt(0)).getArcStyle());
		assertEquals(ArcStyle.WEDGE, ((IArc)drawing.getSelection().getShapeAt(2)).getArcStyle());
	}

	@Test
	public void testArcEndAngleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns).execute();
		double angle = endAngleS.getValue();
		incrementEndAngle.execute();
		assertEquals(endAngleS.getValue(), Math.toDegrees(((IArc)drawing.getSelection().getShapeAt(0)).getAngleEnd()), 0.0001);
		assertEquals(endAngleS.getValue(), Math.toDegrees(((IArc)drawing.getSelection().getShapeAt(2)).getAngleEnd()), 0.0001);
		assertNotEquals(angle, endAngleS.getValue(), 0.0001);
	}

	@Test
	public void testArcStartAngleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns).execute();
		double angle = startAngleS.getValue();
		incrementStartAngle.execute();
		assertEquals(startAngleS.getValue(), Math.toDegrees(((IArc)drawing.getSelection().getShapeAt(0)).getAngleStart()), 0.0001);
		assertEquals(startAngleS.getValue(), Math.toDegrees(((IArc)drawing.getSelection().getShapeAt(2)).getAngleStart()), 0.0001);
		assertNotEquals(angle, startAngleS.getValue(), 0.0001);
	}
}
