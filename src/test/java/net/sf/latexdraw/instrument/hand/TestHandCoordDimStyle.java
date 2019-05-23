package net.sf.latexdraw.instrument.hand;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeCoordDimCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestCoordDimShapeGUI;
import net.sf.latexdraw.instrument.TextSetter;
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
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				pencil = mock(Pencil.class);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Hand.class);
				bindToInstance(Pencil.class, pencil);
				bindAsEagerSingleton(ShapeCoordDimCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerNotActivatedWhenSelectionEmpty() {
		Cmds.of(activateHand, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testControllerActivatedWhenSelection() {
		Cmds.of(selectionAddArc, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(tlxS.isVisible());
		assertTrue(tlyS.isVisible());
	}

	@Test
	public void testSetYSelection() {
		doTestSpinner(Cmds.of(activateHand, selectionAddRec, selectionAddRec, updateIns), tlyS,
			incrementY, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(0).orElseThrow().getTopLeftPoint().getY(),
			() ->  drawing.getSelection().getShapeAt(1).orElseThrow().getTopLeftPoint().getY()));
	}

	@Test
	public void testSetXSelection() {
		doTestSpinner(Cmds.of(activateHand, selectionAddRec, selectionAddRec, updateIns), tlxS,
			incrementX, Arrays.asList(
			() ->  drawing.getSelection().getShapeAt(0).orElseThrow().getTopLeftPoint().getX(),
			() ->  drawing.getSelection().getShapeAt(1).orElseThrow().getTopLeftPoint().getX()));
	}
}
