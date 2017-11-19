package net.sf.latexdraw.gui.pencil;

import net.sf.latexdraw.gui.CompositeGUIVoidCommand;
import net.sf.latexdraw.gui.ShapePropInjector;
import net.sf.latexdraw.gui.TestStdGridStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeStdGridCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.prop.IStdGridProp;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilStdGridStyle extends TestStdGridStyleGUI {

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException {
				super.configure();
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeStdGridCustomiser.class);
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
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
		assertTrue(titledPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(titledPane.isVisible());
	}

	@Test
	public void testIncrLabelsSizePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = labelsSizeS.getValue();
		incrementlabelsSizeS.execute();
		WaitForAsyncUtils.waitForFxEvents(50);
		assertEquals(labelsSizeS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getLabelsSize(), 0.0001);
		assertNotEquals(val, labelsSizeS.getValue(), 0.0001);
	}

	@Test
	public void testIncrxEndSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = xEndS.getValue();
		incrementxEndS.execute();
		WaitForAsyncUtils.waitForFxEvents(50);
		assertEquals(xEndS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getGridEndX(), 0.0001);
		assertNotEquals(val, xEndS.getValue(), 0.0001);
	}

	@Test
	public void testIncryEndSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = yEndS.getValue();
		incrementyEndS.execute();
		WaitForAsyncUtils.waitForFxEvents(50);
		assertEquals(yEndS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getGridEndY(), 0.0001);
		assertNotEquals(val, yEndS.getValue(), 0.0001);
	}

	@Test
	public void testIncrxStartSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = xStartS.getValue();
		decrementxStartS.execute();
		WaitForAsyncUtils.waitForFxEvents(50);
		assertEquals(xStartS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getGridStartX(), 0.0001);
		assertNotEquals(val, xStartS.getValue(), 0.0001);
	}

	@Test
	public void testIncryStartSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = yStartS.getValue();
		decrementyStartS.execute();
		WaitForAsyncUtils.waitForFxEvents(50);
		assertEquals(yStartS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getGridStartY(), 0.0001);
		assertNotEquals(val, yStartS.getValue(), 0.0001);
	}

	@Test
	public void testIncrxOriginSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = xOriginS.getValue();
		incrementxOriginS.execute();
		WaitForAsyncUtils.waitForFxEvents(50);
		assertEquals(xOriginS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getOriginX(), 0.0001);
		assertNotEquals(val, xOriginS.getValue(), 0.0001);
	}

	@Test
	public void testIncryOriginSPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns).execute();
		double val = yOriginS.getValue();
		incrementyOriginS.execute();
		WaitForAsyncUtils.waitForFxEvents(50);
		assertEquals(yOriginS.getValue(), ((IStdGridProp)pencil.createShapeInstance()).getOriginY(), 0.0001);
		assertNotEquals(val, yOriginS.getValue(), 0.0001);
	}
}
