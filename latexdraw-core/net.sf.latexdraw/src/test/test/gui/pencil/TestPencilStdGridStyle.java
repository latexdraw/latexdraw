package test.gui.pencil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import net.sf.latexdraw.glib.models.interfaces.prop.IStdGridProp;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeStdGridCustomiser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import test.gui.CompositeGUIVoidCommand;
import test.gui.ShapePropModule;
import test.gui.TestStdGridStyleGUI;

import com.google.inject.AbstractModule;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilStdGridStyle extends TestStdGridStyleGUI {

	@Override
	protected AbstractModule createModule() {
		return new ShapePropModule() {
			@Override
			protected void configure() {
				super.configure();
				hand = mock(Hand.class);
				bind(ShapeStdGridCustomiser.class).asEagerSingleton();
				bind(Pencil.class).asEagerSingleton();
				bind(Hand.class).toInstance(hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testIncrLabelsSizePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = labelsSizeS.getValue();
		incrementlabelsSizeS.execute();
		assertEquals(labelsSizeS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getLabelsSize(), 0.0001);
		assertNotEquals(val, labelsSizeS.getValue(), 0.0001);
	}

	@Test
	public void testIncrxEndSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = xEndS.getValue();
		incrementxEndS.execute();
		assertEquals(xEndS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getGridEndX(), 0.0001);
		assertNotEquals(val, xEndS.getValue(), 0.0001);
	}

	@Test
	public void testIncryEndSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = yEndS.getValue();
		incrementyEndS.execute();
		assertEquals(yEndS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getGridEndY(), 0.0001);
		assertNotEquals(val, yEndS.getValue(), 0.0001);
	}

	@Test
	public void testIncrxStartSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = xStartS.getValue();
		decrementxStartS.execute();
		assertEquals(xStartS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getGridStartX(), 0.0001);
		assertNotEquals(val, xStartS.getValue(), 0.0001);
	}

	@Test
	public void testIncryStartSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = yStartS.getValue();
		decrementyStartS.execute();
		assertEquals(yStartS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getGridStartY(), 0.0001);
		assertNotEquals(val, yStartS.getValue(), 0.0001);
	}

	@Test
	public void testIncrxOriginSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = xOriginS.getValue();
		incrementxOriginS.execute();
		assertEquals(xOriginS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getOriginX(), 0.0001);
		assertNotEquals(val, xOriginS.getValue(), 0.0001);
	}

	@Test
	public void testIncryOriginSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = yOriginS.getValue();
		incrementyOriginS.execute();
		assertEquals(yOriginS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getOriginY(), 0.0001);
		assertNotEquals(val, yOriginS.getValue(), 0.0001);
	}
}
