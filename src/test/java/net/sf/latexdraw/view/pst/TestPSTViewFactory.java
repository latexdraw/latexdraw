package net.sf.latexdraw.view.pst;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.data.ConfigureInjection;
import net.sf.latexdraw.data.InjectionExtension;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.CircleArc;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Rhombus;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.Triangle;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertTrue;

@ExtendWith(LatexdrawExtension.class)
@ExtendWith(InjectionExtension.class)
public class TestPSTViewFactory {
	PSTViewsFactory factory;

	@ConfigureInjection
	Injector configureInjection() {
		return new Injector() {
			@Override
			protected void configure() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
				bindAsEagerSingleton(PreferencesService.class);
				bindWithCommand(ResourceBundle.class, PreferencesService.class, pref -> pref.getBundle());
				bindAsEagerSingleton(PSTViewsFactory.class);
			}
		};
	}

	@BeforeEach
	void setUp(final PSTViewsFactory factory) {
		this.factory = factory;
	}

	@Test
	void testCreateGroupViewPST() {
		final Group gp = ShapeFactory.INST.createGroup();
		gp.addShape(ShapeFactory.INST.createText());
		final Optional<PSTShapeView<Group>> view = factory.createView(gp);
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateTextViewPST() {
		final Optional<PSTShapeView<Text>> view = factory.createView(ShapeFactory.INST.createText());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateArcCircleViewPST() {
		final Optional<PSTShapeView<CircleArc>> view = factory.createView(ShapeFactory.INST.createCircleArc());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateRectangleViewPST() {
		final Optional<PSTShapeView<Rectangle>> view = factory.createView(ShapeFactory.INST.createRectangle());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateSquareViewPST() {
		final Optional<PSTShapeView<Square>> view = factory.createView(ShapeFactory.INST.createSquare());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateEllipseViewPST() {
		final Optional<PSTShapeView<Ellipse>> view = factory.createView(ShapeFactory.INST.createEllipse());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateCircleViewPST() {
		final Optional<PSTShapeView<Circle>> view = factory.createView(ShapeFactory.INST.createCircle());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateGridViewPST() {
		final Optional<PSTShapeView<Grid>> view = factory.createView(ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateAxesViewPST() {
		final Optional<PSTShapeView<Axes>> view = factory.createView(ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreatePolygonViewPST() {
		final Optional<PSTShapeView<Polygon>> view = factory.createView(ShapeFactory.INST.createPolygon(Collections.emptyList()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreatePolylineViewPST() {
		final Optional<PSTShapeView<Polyline>> view = factory.createView(ShapeFactory.INST.createPolyline(Collections.emptyList()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateTriangleViewPST() {
		final Optional<PSTShapeView<Triangle>> view = factory.createView(ShapeFactory.INST.createTriangle());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateRhombusViewPST() {
		final Optional<PSTShapeView<Rhombus>> view = factory.createView(ShapeFactory.INST.createRhombus());
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateFreehandViewPST() {
		final Optional<PSTShapeView<Freehand>> view = factory.createView(ShapeFactory.INST.createFreeHand(Collections.emptyList()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreatePictureViewPST() {
		final Optional<PSTShapeView<Picture>> view = factory.createView(ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateDotViewPST() {
		final Optional<PSTShapeView<Dot>> view = factory.createView(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
		assertTrue(view.isPresent());
	}

	@Test
	void testCreateBezierCurveViewPST() {
		final Optional<PSTShapeView<BezierCurve>> view = factory.createView(ShapeFactory.INST.createBezierCurve(Collections.emptyList()));
		assertTrue(view.isPresent());
	}
}
