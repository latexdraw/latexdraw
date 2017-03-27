package test.views.jfx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import javafx.scene.paint.Paint;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.view.jfx.ViewGrid;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestViewGrid extends TestViewStdGrid<ViewGrid, IGrid> {
	List<PathElement> mainGridBefore;
	List<PathElement> subGridBefore;

	@BeforeClass
	public static void beforeClass() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		mainGridBefore = new ArrayList<>(view.getMaingrid().getElements());
		subGridBefore = new ArrayList<>(view.getSubgrid().getElements());
	}

	@Override
	protected IGrid createModel() {
		return ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint(101, 67));
	}

	@Test
	public void testChangeUnit() {
		final List<Double> xBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		final List<Double> yBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		model.setUnit(1.345);
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
		assertNotEquals(xBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList()));
		assertNotEquals(yBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList()));
	}

	@Test
	public void testChangeLabelsColour() {
		final Paint strokeBefore = ((Shape) view.getLabels().getChildren().get(0)).getStroke();
		model.setGridLabelsColour(DviPsColors.CARNATIONPINK);
		assertNotEquals(strokeBefore, ((Shape) view.getLabels().getChildren().get(0)).getStroke());
	}

	@Test
	public void testChangeGridDots() {
		model.setGridDots(23);
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertEquals(StrokeLineCap.ROUND, view.getMaingrid().getStrokeLineCap());
		assertEquals(subGridBefore, view.getSubgrid().getElements());
	}

	@Test
	public void testChangeSubGridDots() {
		model.setSubGridDots(21);
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
		assertEquals(StrokeLineCap.ROUND, view.getSubgrid().getStrokeLineCap());
		assertEquals(mainGridBefore, view.getMaingrid().getElements());
	}

	@Test
	public void testChangeSubGridDiv() {
		model.setSubGridDiv(11);
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
		assertEquals(mainGridBefore, view.getMaingrid().getElements());
	}

	@Test
	public void testChangeGridWidthImpactOnStrokeWidth() {
		final double strokeBefore = view.getMaingrid().getStrokeWidth();
		model.setGridWidth(21d);
		assertNotEquals(strokeBefore, view.getMaingrid().getStrokeWidth());
	}

	@Test
	public void testChangeGridWidthImpactOnLabels() {
		final List<Double> xBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		final List<Double> yBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		model.setGridWidth(43d);
		assertNotEquals(xBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList()));
		assertNotEquals(yBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList()));
	}


	@Test
	public void testChangeSubGridWidth() {
		final double strokeBefore = view.getSubgrid().getStrokeWidth();
		model.setSubGridWidth(11d);
		assertNotEquals(strokeBefore, view.getSubgrid().getStrokeWidth());
	}

	@Test
	public void testChangeYLabelWest() {
		final List<Double> xBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		model.setYLabelWest(!model.isYLabelWest());
		assertNotEquals(xBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList()));
	}

	@Test
	public void testChangeXLabelSouth() {
		final List<Double> yBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		model.setXLabelSouth(!model.isXLabelSouth());
		assertNotEquals(yBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList()));
	}

	@Override
	@Test
	public void testChangeGridEndX() {
		model.setGridEndX(model.getGridEndX() + 1d);
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
	}

	@Override
	@Test
	public void testChangeGridEndY() {
		model.setGridEndY(model.getGridEndY() + 1d);
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
	}

	@Override
	@Test
	public void testChangeGridStartX() {
		model.setGridStartX(model.getGridStartX() - 1d);
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
	}

	@Override
	@Test
	public void testChangeGridStartY() {
		model.setGridStartY(model.getGridStartY() - 1d);
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
	}

	@Override
	@Test
	public void testChangeOriginX() {
		final List<Double> yBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		final List<Double> xBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		model.setOriginX(model.getOriginX() + 1d);
		assertNotEquals(xBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList()));
		assertEquals(yBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList()));
	}

	@Override
	@Test
	public void testChangeOriginY() {
		final List<Double> yBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		final List<Double> xBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		model.setOriginY(model.getOriginY() + 1d);
		assertNotEquals(yBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList()));
		assertEquals(xBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList()));
	}

	@Override
	@Test
	public void testOnTranslateX() {
		final double x = view.getTranslateX();
		model.translate(11d, 0d);
		assertEquals(x + 11d, view.getTranslateX(), 0.0000001);
	}

	@Override
	@Test
	public void testOnTranslateY() {
		final double y = view.getTranslateY();
		model.translate(0d, 13d);
		assertEquals(y + 13d, view.getTranslateY(), 0.0000001);
	}
}
