package net.sf.latexdraw.view.pst;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Optional;
import net.sf.latexdraw.data.ConfigureInjection;
import net.sf.latexdraw.data.InjectionExtension;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.util.LangService;
import net.sf.latexdraw.util.SystemService;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertTrue;

@ExtendWith(InjectionExtension.class)
public class TestPSTViewFactory {
	PSTViewsFactory factory;

	@ConfigureInjection
	Injector configureInjection() {
		return new Injector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(SystemService.class);
				bindAsEagerSingleton(LangService.class);
				bindAsEagerSingleton(PSTViewsFactory.class);
			}
		};
	}

	@BeforeEach
	void setUp(final PSTViewsFactory factory) {
		this.factory = factory;
	}

	@AfterEach
	void tearDown() {
		DviPsColors.INSTANCE.clearUserColours();
	}

	@Test
	void testCreateGroupViewPST() {
		final IGroup gp = ShapeFactory.INST.createGroup();
		gp.addShape(ShapeFactory.INST.createText());
		final Optional<PSTShapeView<IGroup>> view = factory.createView(gp);
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateTextViewPST() {
		final Optional<PSTShapeView<IText>> view = factory.createView(ShapeFactory.INST.createText());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateArcCircleViewPST() {
		final Optional<PSTShapeView<ICircleArc>> view = factory.createView(ShapeFactory.INST.createCircleArc());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateRectangleViewPST() {
		final Optional<PSTShapeView<IRectangle>> view = factory.createView(ShapeFactory.INST.createRectangle());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateSquareViewPST() {
		final Optional<PSTShapeView<ISquare>> view = factory.createView(ShapeFactory.INST.createSquare());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateEllipseViewPST() {
		final Optional<PSTShapeView<IEllipse>> view = factory.createView(ShapeFactory.INST.createEllipse());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateCircleViewPST() {
		final Optional<PSTShapeView<ICircle>> view = factory.createView(ShapeFactory.INST.createCircle());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateGridViewPST() {
		final Optional<PSTShapeView<IGrid>> view = factory.createView(ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateAxesViewPST() {
		final Optional<PSTShapeView<IAxes>> view = factory.createView(ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreatePolygonViewPST() {
		final Optional<PSTShapeView<IPolygon>> view = factory.createView(ShapeFactory.INST.createPolygon(Collections.emptyList()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreatePolylineViewPST() {
		final Optional<PSTShapeView<IPolyline>> view = factory.createView(ShapeFactory.INST.createPolyline(Collections.emptyList()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateTriangleViewPST() {
		final Optional<PSTShapeView<ITriangle>> view = factory.createView(ShapeFactory.INST.createTriangle());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateRhombusViewPST() {
		final Optional<PSTShapeView<IRhombus>> view = factory.createView(ShapeFactory.INST.createRhombus());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateFreehandViewPST() {
		final Optional<PSTShapeView<IFreehand>> view = factory.createView(ShapeFactory.INST.createFreeHand(Collections.emptyList()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreatePictureViewPST() {
		final Optional<PSTShapeView<IPicture>> view = factory.createView(ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint(), new SystemService()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateDotViewPST() {
		final Optional<PSTShapeView<IDot>> view = factory.createView(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateBezierCurveViewPST() {
		final Optional<PSTShapeView<IBezierCurve>> view = factory.createView(ShapeFactory.INST.createBezierCurve(Collections.emptyList()));
		assertTrue(view.isPresent());
	}
}
