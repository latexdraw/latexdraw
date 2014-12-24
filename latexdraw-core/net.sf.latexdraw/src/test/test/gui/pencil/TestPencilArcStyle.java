package test.gui.pencil;

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
public class TestPencilArcStyle extends TestArcStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapeArcCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
			}
		};
	}
	
	
	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, checkInsActivated).execute();
	}
	
	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}
	
	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns).execute();
		assertTrue(mainPane.isVisible());
	}
	
	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
	}
	
	@Test
	public void testClickChordUnselectOthersPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectWedge, selectChord).execute();
		assertFalse(arcB.isSelected());
		assertFalse(wedgeB.isSelected());
		assertTrue(chordB.isSelected());
	}
	
	@Test
	public void testClickWedgeUnselectOthersPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectChord, selectWedge).execute();
		assertFalse(arcB.isSelected());
		assertTrue(wedgeB.isSelected());
		assertFalse(chordB.isSelected());
	}
	
	@Test
	public void testClickArcUnselectOthersPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectChord, selectArc).execute();
		assertTrue(arcB.isSelected());
		assertFalse(wedgeB.isSelected());
		assertFalse(chordB.isSelected());
	}
	
	@Test
	public void testArcEndAnglePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns).execute();
		double angle = ((IArc)pencil.createShapeInstance()).getAngleEnd();
		incrementEndAngle.execute();
		assertEquals(endAngleS.getValue(), Math.toDegrees(((IArc)pencil.createShapeInstance()).getAngleEnd()), 0.0001);
		assertNotEquals(angle, ((IArc)pencil.createShapeInstance()).getAngleEnd(), 0.0001);
	}
	
	@Test
	public void testArcStartAnglePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns).execute();
		double angle = ((IArc)pencil.createShapeInstance()).getAngleStart();
		incrementStartAngle.execute();
		assertEquals(startAngleS.getValue(), Math.toDegrees(((IArc)pencil.createShapeInstance()).getAngleStart()), 0.0001);
		assertNotEquals(angle, ((IArc)pencil.createShapeInstance()).getAngleStart(), 0.0001);
	}
	
	@Test
	public void testArcTypeWedgePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectChord, selectWedge).execute();
		assertEquals(IArcProp.ArcStyle.WEDGE, ((IArc)pencil.createShapeInstance()).getArcStyle());
	}
	
	@Test
	public void testArcTypeArcPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectChord, selectArc).execute();
		assertEquals(IArcProp.ArcStyle.ARC, ((IArc)pencil.createShapeInstance()).getArcStyle());
	}
	
	@Test
	public void testArcTypeChordPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesArc, updateIns, selectArc, selectChord).execute();
		assertEquals(IArcProp.ArcStyle.CHORD, ((IArc)pencil.createShapeInstance()).getArcStyle());
	}
}
