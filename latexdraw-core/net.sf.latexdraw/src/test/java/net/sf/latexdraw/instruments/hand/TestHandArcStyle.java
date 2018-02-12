package net.sf.latexdraw.instruments.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestArcStyleGUI;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHandArcStyle extends TestArcStyleGUI {
	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
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
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns), endAngleS,
			incrementEndAngle, Arrays.asList(
			() ->  Math.toDegrees(((IArc)drawing.getSelection().getShapeAt(0)).getAngleEnd()),
			() ->  Math.toDegrees(((IArc)drawing.getSelection().getShapeAt(2)).getAngleEnd())));
	}

	@Test
	public void testArcStartAngleSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddArc, selectionAddRec, selectionAddArc, updateIns), startAngleS,
			incrementStartAngle, Arrays.asList(
			() ->  Math.toDegrees(((IArc)drawing.getSelection().getShapeAt(0)).getAngleStart()),
			() ->  Math.toDegrees(((IArc)drawing.getSelection().getShapeAt(2)).getAngleStart())));
	}
}
