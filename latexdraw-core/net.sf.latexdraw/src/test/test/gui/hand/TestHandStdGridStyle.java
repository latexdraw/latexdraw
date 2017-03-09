package test.gui.hand;

import com.google.inject.AbstractModule;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeStdGridCustomiser;
import net.sf.latexdraw.models.interfaces.prop.IStdGridProp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestStdGridStyleGUI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestHandStdGridStyle extends TestStdGridStyleGUI {
	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				pencil = mock(Pencil.class);
				bind(ShapeStdGridCustomiser.class).asEagerSingleton();
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
	public void testControllerActivatedWhenSelectionGrid() {
		new CompositeGUIVoidCommand(selectionAddGrid, activateHand, updateIns).execute();
		assertTrue(ins.isActivated());
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testControllerDeactivatedWhenSelectionNotGrid() {
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
	public void testIncrLabelsSizeHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns).execute();
		double val = labelsSizeS.getValue();
		incrementlabelsSizeS.execute();
		assertEquals(labelsSizeS.getValue(), ((IStdGridProp)drawing.getSelection().getShapeAt(1)).getLabelsSize(), 0.0001);
		assertEquals(labelsSizeS.getValue(), ((IStdGridProp)drawing.getSelection().getShapeAt(2)).getLabelsSize(), 0.0001);
		assertNotEquals(val, labelsSizeS.getValue(), 0.0001);
	}

	@Test
	public void testIncrxEndSHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns).execute();
		double val = xEndS.getValue();
		incrementxEndS.execute();
		assertEquals(xEndS.getValue(), ((IStdGridProp)drawing.getSelection().getShapeAt(1)).getGridEndX(), 0.0001);
		assertEquals(xEndS.getValue(), ((IStdGridProp)drawing.getSelection().getShapeAt(2)).getGridEndX(), 0.0001);
		assertNotEquals(val, xEndS.getValue(), 0.0001);
	}

	@Test
	public void testIncryEndSHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns).execute();
		double val = yEndS.getValue();
		incrementyEndS.execute();
		assertEquals(yEndS.getValue(), ((IStdGridProp)drawing.getSelection().getShapeAt(1)).getGridEndY(), 0.0001);
		assertEquals(yEndS.getValue(), ((IStdGridProp)drawing.getSelection().getShapeAt(2)).getGridEndY(), 0.0001);
		assertNotEquals(val, yEndS.getValue(), 0.0001);
	}

	@Test
	public void testIncrxStartSHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns).execute();
		double val = xStartS.getValue();
		decrementxStartS.execute();
		assertEquals(xStartS.getValue(), ((IStdGridProp)drawing.getSelection().getShapeAt(1)).getGridStartX(), 0.0001);
		assertEquals(xStartS.getValue(), ((IStdGridProp)drawing.getSelection().getShapeAt(2)).getGridStartX(), 0.0001);
		assertNotEquals(val, xStartS.getValue(), 0.0001);
	}

	@Test
	public void testIncryStartSHand() {
		new CompositeGUIVoidCommand(activateHand, selectionAddDot, selectionAddGrid, selectionAddAxes, updateIns).execute();
		double val = yStartS.getValue();
		decrementyStartS.execute();
		assertEquals(yStartS.getValue(), ((IStdGridProp)drawing.getSelection().getShapeAt(1)).getGridStartY(), 0.0001);
		assertEquals(yStartS.getValue(), ((IStdGridProp)drawing.getSelection().getShapeAt(2)).getGridStartY(), 0.0001);
		assertNotEquals(val, yStartS.getValue(), 0.0001);
	}
}
