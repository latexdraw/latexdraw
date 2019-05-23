package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeGridCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestGridStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.util.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TestPencilGridStyle extends TestGridStyleGUI {
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
				bindAsEagerSingleton(ShapeGridCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesGrid, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesGrid, updateIns).execute();
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testPickcolourLabelsColourPencil() {
		Cmds.of(activatePencil, pencilCreatesGrid, updateIns).execute();
		final Color col = colourLabels.getValue();
		Cmds.of(pickcolourLabels).execute();
		assertEquals(colourLabels.getValue(), ((Grid) editing.createShapeInstance()).getGridLabelsColour().toJFX());
		assertNotEquals(col, colourLabels.getValue());
	}

	@Test
	public void testPickLineColourPencil() {
		Cmds.of(activatePencil, pencilCreatesGrid, updateIns).execute();
		final Color col = colourSubGrid.getValue();
		Cmds.of(pickcolourSubGrid).execute();
		assertEquals(colourSubGrid.getValue(), ((Grid) editing.createShapeInstance()).getSubGridColour().toJFX());
		assertNotEquals(col, colourSubGrid.getValue());
	}

	@Test
	public void testIncrementgridWidthPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesGrid, updateIns), gridWidth,
			incrementgridWidth, Collections.singletonList(() ->  ((Grid) editing.createShapeInstance()).getGridWidth()));
	}

	@Test
	public void testIncrementsubGridWidthPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesGrid, updateIns), subGridWidth,
			incrementsubGridWidth, Collections.singletonList(() ->  ((Grid) editing.createShapeInstance()).getSubGridWidth()));
	}

	@Test
	public void testIncrementgridDotsPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesGrid, updateIns), gridDots,
			incrementgridDots, Collections.singletonList(() ->  ((Grid) editing.createShapeInstance()).getGridDots()));
	}

	@Test
	public void testIncrementsubGridDotsPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesGrid, updateIns), subGridDots,
			incrementsubGridDots, Collections.singletonList(() ->  ((Grid) editing.createShapeInstance()).getSubGridDots()));
	}

	@Test
	public void testIncrementsubGridDivPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesGrid, updateIns), subGridDiv,
			incrementsubGridDiv, Collections.singletonList(() ->  ((Grid) editing.createShapeInstance()).getSubGridDiv()));
	}

	@Test
	public void testSelectlabelsYInvertedCBPencil() {
		Cmds.of(activatePencil, pencilCreatesGrid, updateIns).execute();
		final boolean sel = labelsYInvertedCB.isSelected();
		Cmds.of(clicklabelsYInvertedCB).execute();
		assertEquals(sel, ((Grid) editing.createShapeInstance()).isXLabelSouth());
		assertNotEquals(sel, labelsYInvertedCB.isSelected());
	}

	@Test
	public void testSelectlabelsXInvertedCBPencil() {
		Cmds.of(activatePencil, pencilCreatesGrid, updateIns).execute();
		final boolean sel = labelsXInvertedCB.isSelected();
		Cmds.of(clicklabelsXInvertedCB).execute();
		assertEquals(sel, ((Grid) editing.createShapeInstance()).isYLabelWest());
		assertNotEquals(sel, labelsXInvertedCB.isSelected());
	}
}
