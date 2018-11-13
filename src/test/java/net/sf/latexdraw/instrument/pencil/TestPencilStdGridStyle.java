package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.CompositeGUIVoidCommand;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.ShapeStdGridCustomiser;
import net.sf.latexdraw.instrument.TestStdGridStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.property.IStdGridProp;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilStdGridStyle extends TestStdGridStyleGUI {

	@Override
	protected Injector createInjector() {
		return new ShapePropInjector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				super.configure();
				bindToSupplier(Stage.class, () -> stage);
				hand = mock(Hand.class);
				bindToInstance(Hand.class, hand);
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindAsEagerSingleton(Pencil.class);
				bindAsEagerSingleton(ShapeStdGridCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
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
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), labelsSizeS,
			incrementlabelsSizeS, Collections.singletonList(() ->  ((IStdGridProp) editing.createShapeInstance()).getLabelsSize()));
	}

	@Test
	public void testIncrxEndSPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), xEndS,
			incrementxEndS, Collections.singletonList(() ->  ((IStdGridProp) editing.createShapeInstance()).getGridEndX()));
	}

	@Test
	public void testIncryEndSPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), yEndS,
			incrementyEndS, Collections.singletonList(() ->  ((IStdGridProp) editing.createShapeInstance()).getGridEndY()));
	}

	@Test
	public void testIncrxStartSPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), xStartS,
			decrementxStartS, Collections.singletonList(() ->  ((IStdGridProp) editing.createShapeInstance()).getGridStartX()));
	}

	@Test
	public void testIncryStartSPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), yStartS,
			decrementyStartS, Collections.singletonList(() ->  ((IStdGridProp) editing.createShapeInstance()).getGridStartY()));
	}

	@Test
	public void testIncrxOriginSPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), xOriginS,
			incrementxOriginS, Collections.singletonList(() ->  ((IStdGridProp) editing.createShapeInstance()).getOriginX()));
	}

	@Test
	public void testIncryOriginSPencil() {
		doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), yOriginS,
			incrementyOriginS, Collections.singletonList(() ->  ((IStdGridProp) editing.createShapeInstance()).getOriginY()));
	}
}
