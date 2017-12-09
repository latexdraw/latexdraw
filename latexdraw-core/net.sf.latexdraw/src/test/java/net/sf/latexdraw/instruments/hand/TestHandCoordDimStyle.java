package net.sf.latexdraw.instruments.hand;

import java.util.Arrays;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestCoordDimShapeGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeCoordDimCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddRec, updateIns), tlyS,
			incrementY, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(0).getTopLeftPoint().getY(),
			() ->  drawing.getSelection().getShapeAt(1).getTopLeftPoint().getY()));
	}

	@Test
	public void testSetXSelection() {
		doTestSpinner(new CompositeGUIVoidCommand(activateHand, selectionAddRec, selectionAddRec, updateIns), tlxS,
			incrementX, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(0).getTopLeftPoint().getX(),
			() ->  drawing.getSelection().getShapeAt(1).getTopLeftPoint().getX()));
	}
}
