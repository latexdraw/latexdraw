package test.views.jfx;

import java.util.Arrays;
import javafx.scene.shape.Shape;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.jfx.ViewSingleShape;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

abstract class TestViewSingleShape<T extends ViewSingleShape<S, R>, S extends ISingleShape, R extends Shape> {
	protected T view;
	protected S model;
	protected R border;

	protected abstract S createModel();

	private T createView() {
		return (T)ViewFactory.INSTANCE.createView(createModel()).get();
	}

	@Before
	public void setUp() {
		BadaboomCollector.INSTANCE.clear();
		view = createView();
		model = view.getModel();
		border = view.getBorder();
	}

	@Test
	public void testLineColor() {
		model.setLineColour(DviPsColors.BITTERSWEET);
		assertEquals(DviPsColors.BITTERSWEET, ShapeFactory.INST.createColorFX((javafx.scene.paint.Color)border.getStroke()));
	}

	@Test
	public void testLineThickness() {
		if(model.isThicknessable()) {
			model.setThickness(10d);
			assertEquals(10d, border.getStrokeWidth(), 0.00d);
		}
	}

	@Test
	public void testLineStyleDashed() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DASHED);
			assertEquals(Arrays.asList(model.getDashSepBlack(), model.getDashSepWhite()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testDashSepBlack() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DASHED);
			model.setDashSepBlack(21d);
			assertEquals(Arrays.asList(21d, model.getDashSepWhite()), border.getStrokeDashArray());
		}
	}
}
