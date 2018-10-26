package net.sf.latexdraw.instruments.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.stage.Stage;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.ShapeStdGridCustomiser;
import net.sf.latexdraw.instruments.TestStdGridStyleGUI;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.prop.IStdGridProp;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.malai.javafx.ui.JfxUI;
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
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), labelsSizeS,
			incrementlabelsSizeS, Collections.singletonList(() ->  ((IStdGridProp)pencil.createShapeInstance()).getLabelsSize()));
	}

	@Test
	public void testIncrxEndSPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), xEndS,
			incrementxEndS, Collections.singletonList(() ->  ((IStdGridProp)pencil.createShapeInstance()).getGridEndX()));
	}

	@Test
	public void testIncryEndSPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), yEndS,
			incrementyEndS, Collections.singletonList(() ->  ((IStdGridProp)pencil.createShapeInstance()).getGridEndY()));
	}

	@Test
	public void testIncrxStartSPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), xStartS,
			decrementxStartS, Collections.singletonList(() ->  ((IStdGridProp)pencil.createShapeInstance()).getGridStartX()));
	}

	@Test
	public void testIncryStartSPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), yStartS,
			decrementyStartS, Collections.singletonList(() ->  ((IStdGridProp)pencil.createShapeInstance()).getGridStartY()));
	}

	@Test
	public void testIncrxOriginSPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), xOriginS,
			incrementxOriginS, Collections.singletonList(() ->  ((IStdGridProp)pencil.createShapeInstance()).getOriginX()));
	}

	@Test
	public void testIncryOriginSPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesGrid, updateIns), yOriginS,
			incrementyOriginS, Collections.singletonList(() ->  ((IStdGridProp)pencil.createShapeInstance()).getOriginY()));
	}
}
