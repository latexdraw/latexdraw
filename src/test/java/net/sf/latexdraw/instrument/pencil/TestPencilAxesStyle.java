package net.sf.latexdraw.instrument.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import javafx.stage.Stage;
import net.sf.latexdraw.instrument.Cmds;
import net.sf.latexdraw.instrument.Hand;
import net.sf.latexdraw.instrument.MetaShapeCustomiser;
import net.sf.latexdraw.instrument.Pencil;
import net.sf.latexdraw.instrument.ShapeAxesCustomiser;
import net.sf.latexdraw.instrument.ShapePropInjector;
import net.sf.latexdraw.instrument.TestAxesStyleGUI;
import net.sf.latexdraw.instrument.TextSetter;
import net.sf.latexdraw.model.api.property.AxesProp;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.TicksStyle;
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
public class TestPencilAxesStyle extends TestAxesStyleGUI {
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
				bindAsEagerSingleton(ShapeAxesCustomiser.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesAxes, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesAxes, updateIns).execute();
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		Cmds.of(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testCheckShowOriginPencil() {
		Cmds.of(activatePencil, pencilCreatesAxes, updateIns).execute();
		final boolean sel = showOrigin.isSelected();
		Cmds.of(selectShowOrigin).execute();
		assertEquals(!sel, ((AxesProp) editing.createShapeInstance()).isShowOrigin());
	}

	@Test
	public void testIncrementDistYPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesAxes, updateIns), distLabelsY,
			incrementDistLabelY, Collections.singletonList(() ->  ((AxesProp) editing.createShapeInstance()).getDistLabelsY()));
	}

	@Test
	public void testIncrementDistXPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesAxes, updateIns), distLabelsX,
			incrementDistLabelX, Collections.singletonList(() ->  ((AxesProp) editing.createShapeInstance()).getDistLabelsX()));
	}

	@Test
	public void testIncrementLabelYPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesAxes, updateIns), incrLabelY,
			incrementLabelY, Collections.singletonList(() ->  ((AxesProp) editing.createShapeInstance()).getIncrementY()));
	}

	@Test
	public void testIncrementLabelXPencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesAxes, updateIns), incrLabelX,
			incrementLabelX, Collections.singletonList(() ->  ((AxesProp) editing.createShapeInstance()).getIncrementX()));
	}

	@Test
	public void testTicksSizePencil() {
		doTestSpinner(Cmds.of(activatePencil, pencilCreatesAxes, updateIns), ticksSize,
			incrementTicksSize, Collections.singletonList(() ->  ((AxesProp) editing.createShapeInstance()).getTicksSize()));
	}

	@Test
	public void testSelectShowLabelsPencil() {
		Cmds.of(activatePencil, pencilCreatesAxes, updateIns).execute();
		final PlottingStyle style = showLabels.getSelectionModel().getSelectedItem();
		Cmds.of(selectPlotLabel).execute();
		final PlottingStyle newStyle = showLabels.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) editing.createShapeInstance()).getLabelsDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectShowTicksPencil() {
		Cmds.of(activatePencil, pencilCreatesAxes, updateIns).execute();
		final PlottingStyle style = showTicks.getSelectionModel().getSelectedItem();
		Cmds.of(selectPlotTicks).execute();
		final PlottingStyle newStyle = showTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) editing.createShapeInstance()).getTicksDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectTicksStylePencil() {
		Cmds.of(activatePencil, pencilCreatesAxes, updateIns).execute();
		final TicksStyle style = shapeTicks.getSelectionModel().getSelectedItem();
		Cmds.of(selectTicksStyle).execute();
		final TicksStyle newStyle = shapeTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) editing.createShapeInstance()).getTicksStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLineStylePencil() {
		Cmds.of(activatePencil, pencilCreatesAxes, updateIns).execute();
		final AxesStyle style = shapeAxes.getSelectionModel().getSelectedItem();
		Cmds.of(selectAxeStyle).execute();
		final AxesStyle newStyle = shapeAxes.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((AxesProp) editing.createShapeInstance()).getAxesStyle());
		assertNotEquals(style, newStyle);
	}
}
