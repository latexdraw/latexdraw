package net.sf.latexdraw.instruments.pencil;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import net.sf.latexdraw.instruments.CompositeGUIVoidCommand;
import net.sf.latexdraw.instruments.ShapePropInjector;
import net.sf.latexdraw.instruments.TestAxesStyleGUI;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.ShapeAxesCustomiser;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.models.interfaces.prop.IAxesProp;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
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
				hand = mock(Hand.class);
				bindAsEagerSingleton(ShapeAxesCustomiser.class);
				bindAsEagerSingleton(Pencil.class);
				bindToInstance(MetaShapeCustomiser.class, mock(MetaShapeCustomiser.class));
				bindToInstance(TextSetter.class, mock(TextSetter.class));
				bindToInstance(Hand.class, hand);
			}
		};
	}

	@Test
	public void testControllerActivatedWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns, checkInsActivated).execute();
	}

	@Test
	public void testControllerNotActivatedWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns, checkInsDeactivated).execute();
	}

	@Test
	public void testWidgetsGoodStateWhenGoodPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		assertTrue(mainPane.isVisible());
	}

	@Test
	public void testWidgetsGoodStateWhenBadPencilUsed() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesRec, updateIns).execute();
		assertFalse(mainPane.isVisible());
	}

	@Test
	public void testCheckShowOriginPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		boolean sel = showOrigin.isSelected();
		selectShowOrigin.execute();
		assertEquals(!sel, ((IAxesProp)pencil.createShapeInstance()).isShowOrigin());
	}

	@Test
	public void testIncrementDistYPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns), distLabelsY,
			incrementDistLabelY, Collections.singletonList(() ->  ((IAxesProp)pencil.createShapeInstance()).getDistLabelsY()));
	}

	@Test
	public void testIncrementDistXPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns), distLabelsX,
			incrementDistLabelX, Collections.singletonList(() ->  ((IAxesProp)pencil.createShapeInstance()).getDistLabelsX()));
	}

	@Test
	public void testIncrementLabelYPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns), incrLabelY,
			incrementLabelY, Collections.singletonList(() ->  ((IAxesProp)pencil.createShapeInstance()).getIncrementY()));
	}

	@Test
	public void testIncrementLabelXPencil() {
		 doTestSpinner(new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns), incrLabelX,
			incrementLabelX, Collections.singletonList(() ->  ((IAxesProp)pencil.createShapeInstance()).getIncrementX()));
	}

	@Test
	public void testSelectShowLabelsPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		PlottingStyle style = showLabels.getSelectionModel().getSelectedItem();
		selectPlotLabel.execute();
		PlottingStyle newStyle = showLabels.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getLabelsDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectShowTicksPencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		PlottingStyle style = showTicks.getSelectionModel().getSelectedItem();
		selectPlotTicks.execute();
		PlottingStyle newStyle = showTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getTicksDisplayed());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectTicksStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		TicksStyle style = shapeTicks.getSelectionModel().getSelectedItem();
		selectTicksStyle.execute();
		TicksStyle newStyle = shapeTicks.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getTicksStyle());
		assertNotEquals(style, newStyle);
	}

	@Test
	public void testSelectLineStylePencil() {
		new CompositeGUIVoidCommand(activatePencil, pencilCreatesAxes, updateIns).execute();
		AxesStyle style = shapeAxes.getSelectionModel().getSelectedItem();
		selectAxeStyle.execute();
		AxesStyle newStyle = shapeAxes.getSelectionModel().getSelectedItem();
		assertEquals(newStyle, ((IAxesProp)pencil.createShapeInstance()).getAxesStyle());
		assertNotEquals(style, newStyle);
	}
}
