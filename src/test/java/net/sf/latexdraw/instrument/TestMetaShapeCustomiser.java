package net.sf.latexdraw.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Stream;
import net.sf.latexdraw.CollectionMatcher;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.SystemService;
import net.sf.latexdraw.view.MagneticGrid;
import net.sf.latexdraw.view.jfx.Canvas;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestMetaShapeCustomiser implements CollectionMatcher {
	Injector injector;
	MetaShapeCustomiser meta;
	List<ShapePropertyCustomiser> ins;

	protected Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
				bindAsEagerSingleton(SystemService.class);
				bindToInstance(TextSetter.class, Mockito.mock(TextSetter.class));
				bindToInstance(MagneticGrid.class, Mockito.mock(MagneticGrid.class));
				bindToInstance(ShapeArcCustomiser.class, Mockito.mock(ShapeArcCustomiser.class));
				bindToInstance(ShapeArrowCustomiser.class, Mockito.mock(ShapeArrowCustomiser.class));
				bindToInstance(ShapeAxesCustomiser.class, Mockito.mock(ShapeAxesCustomiser.class));
				bindToInstance(ShapeBorderCustomiser.class, Mockito.mock(ShapeBorderCustomiser.class));
				bindToInstance(ShapeCoordDimCustomiser.class, Mockito.mock(ShapeCoordDimCustomiser.class));
				bindToInstance(ShapeDotCustomiser.class, Mockito.mock(ShapeDotCustomiser.class));
				bindToInstance(ShapeDoubleBorderCustomiser.class, Mockito.mock(ShapeDoubleBorderCustomiser.class));
				bindToInstance(ShapeFillingCustomiser.class, Mockito.mock(ShapeFillingCustomiser.class));
				bindToInstance(ShapeFreeHandCustomiser.class, Mockito.mock(ShapeFreeHandCustomiser.class));
				bindToInstance(ShapeGridCustomiser.class, Mockito.mock(ShapeGridCustomiser.class));
				bindToInstance(ShapeGrouper.class, Mockito.mock(ShapeGrouper.class));
				bindToInstance(ShapePlotCustomiser.class, Mockito.mock(ShapePlotCustomiser.class));
				bindToInstance(ShapePositioner.class, Mockito.mock(ShapePositioner.class));
				bindToInstance(ShapeRotationCustomiser.class, Mockito.mock(ShapeRotationCustomiser.class));
				bindToInstance(ShapeShadowCustomiser.class, Mockito.mock(ShapeShadowCustomiser.class));
				bindToInstance(ShapeStdGridCustomiser.class, Mockito.mock(ShapeStdGridCustomiser.class));
				bindToInstance(ShapeTextCustomiser.class, Mockito.mock(ShapeTextCustomiser.class));
				bindToInstance(ShapeTransformer.class, Mockito.mock(ShapeTransformer.class));
				bindToInstance(Hand.class, Mockito.mock(Hand.class));
				bindToInstance(Pencil.class, Mockito.mock(Pencil.class));
				bindToInstance(Canvas.class, Mockito.mock(Canvas.class));
				bindToInstance(Drawing.class, ShapeFactory.INST.createDrawing());
				bindAsEagerSingleton(MetaShapeCustomiser.class);
			}
		};
	}

	@Before
	public void setUp() {
		injector = createInjector();
		injector.initialise();
		meta = injector.getInstance(MetaShapeCustomiser.class);
	}

	Stream<ShapePropertyCustomiser> getPropInstruments() {
		return injector.getInstances().stream().filter(obj -> obj instanceof ShapePropertyCustomiser && obj != meta).map(obj -> (ShapePropertyCustomiser) obj);
	}

	@Test
	public void testAllInstrumentsNotActivated() {
		meta.setActivated(false);
		assertAllFalse(getPropInstruments(), obj -> obj.isActivated());
	}

	@Test
	public void testAllInstrumentUpdatedOnActivation() {
		meta.setActivated(true);
		getPropInstruments().forEach(ins -> Mockito.verify(ins, Mockito.times(1)).update(Mockito.any()));
	}

	@Test
	public void testAllInstrumentClearEvents() {
		meta.clearEvents();
		getPropInstruments().forEach(ins -> Mockito.verify(ins, Mockito.times(1)).clearEvents());
	}

	@Test
	public void testActivatedOnSelection() {
		meta.initialize(null, null);
		final Drawing dr = injector.getInstance(Drawing.class);
		dr.getSelection().addShape(ShapeFactory.INST.createRectangle());
		assertTrue(meta.isActivated());
	}

	@Test
	public void testActivatedOnSelectionRemoval() {
		Mockito.when(injector.getInstance(Hand.class).isActivated()).thenReturn(true);
		meta.initialize(null, null);
		final Drawing dr = injector.getInstance(Drawing.class);
		dr.getSelection().addShape(ShapeFactory.INST.createRectangle());
		dr.getSelection().removeShape(0);
		assertFalse(meta.isActivated());
	}
}
