package test.gui.hand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import net.sf.latexdraw.glib.models.interfaces.prop.IArcProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IArc;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeArcCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestArcStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestHandArcStyle extends TestArcStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				bind(ShapeArcCustomiser.class).asEagerSingleton();
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
	public void testControllerActivatedWhenSelectionArc() {
		new CompositeGUIVoidCommand(selectionAddArc, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(mainPane.isVisible());
	}
	
	@Test
	public void testControllerDeactivatedWhenSelectionNotArc() {
		new CompositeGUIVoidCommand(selectionAddRec, activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(mainPane.isVisible());
	}
	
	@Test
	public void testControllerDeactivatedWhenSelectionEmpty() {
		new CompositeGUIVoidCommand(activateHand, updateIns).execute();
		assertFalse(ins.isActivated());
		assertFalse(mainPane.isVisible());
	}
	
	@Test
	public void testArcTypeChordSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns, selectWedge, selectChord).execute();
		assertEquals(IArcProp.ArcStyle.CHORD, ((IArc)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getArcStyle());
		assertEquals(IArcProp.ArcStyle.CHORD, ((IArc)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getArcStyle());
	}
	
	@Test
	public void testArcTypeArcSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns, selectChord, selectArc).execute();
		assertEquals(IArcProp.ArcStyle.ARC, ((IArc)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getArcStyle());
		assertEquals(IArcProp.ArcStyle.ARC, ((IArc)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getArcStyle());
	}
	
	@Test
	public void testArcTypeWedgeSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns, selectChord, selectWedge).execute();
		assertEquals(IArcProp.ArcStyle.WEDGE, ((IArc)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getArcStyle());
		assertEquals(IArcProp.ArcStyle.WEDGE, ((IArc)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getArcStyle());
	}
	
	@Test
	public void testArcEndAngleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns).execute();
		double angle = endAngleS.getValue();
		incrementEndAngle.execute();
		assertEquals(endAngleS.getValue(), Math.toDegrees(((IArc)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getAngleEnd()), 0.0001);
		assertEquals(endAngleS.getValue(), Math.toDegrees(((IArc)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getAngleEnd()), 0.0001);
		assertNotEquals(angle, endAngleS.getValue(), 0.0001);
	}
	
	@Test
	public void testArcStartAngleSelection() {
		new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns).execute();
		double angle = startAngleS.getValue();
		incrementStartAngle.execute();
		assertEquals(startAngleS.getValue(), Math.toDegrees(((IArc)hand.getCanvas().getDrawing().getSelection().getShapeAt(0)).getAngleStart()), 0.0001);
		assertEquals(startAngleS.getValue(), Math.toDegrees(((IArc)hand.getCanvas().getDrawing().getSelection().getShapeAt(2)).getAngleStart()), 0.0001);
		assertNotEquals(angle, startAngleS.getValue(), 0.0001);
	}
}
