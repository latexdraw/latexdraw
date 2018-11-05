package net.sf.latexdraw.view.jfx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.scene.paint.Paint;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TestViewGrid extends TestViewStdGrid<ViewGrid, Grid> {
	List<PathElement> mainGridBefore;
	List<PathElement> subGridBefore;

	@BeforeAll
	public static void beforeClass() {
		try {
			Platform.startup(() -> {
			});
		}catch(final IllegalStateException ex) {
			// Ok
		}
	}

	@BeforeEach
	void setUpViewGrid() {
		mainGridBefore = new ArrayList<>(view.getMaingrid().getElements());
		subGridBefore = new ArrayList<>(view.getSubgrid().getElements());
	}

	@Override
	protected Grid createModel() {
		return ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint(101, 67));
	}

	@Test
	void testChangeUnit() {
		final List<Double> xBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		final List<Double> yBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		model.setUnit(1.345);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
		assertNotEquals(xBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList()));
		assertNotEquals(yBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList()));
	}

	@Test
	void testChangeLabelsColour() {
		final Paint strokeBefore = ((Shape) view.getLabels().getChildren().get(0)).getStroke();
		model.setGridLabelsColour(DviPsColors.CARNATIONPINK);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(strokeBefore, ((Shape) view.getLabels().getChildren().get(0)).getStroke());
	}

	@Test
	void testChangeGridDots() {
		model.setGridDots(23);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertEquals(StrokeLineCap.ROUND, view.getMaingrid().getStrokeLineCap());
		assertEquals(subGridBefore, view.getSubgrid().getElements());
	}

	@Test
	void testChangeSubGridDots() {
		model.setSubGridDots(21);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
		assertEquals(StrokeLineCap.ROUND, view.getSubgrid().getStrokeLineCap());
		assertEquals(mainGridBefore, view.getMaingrid().getElements());
	}

	@Test
	void testChangeSubGridDiv() {
		model.setSubGridDiv(11);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
		assertEquals(mainGridBefore, view.getMaingrid().getElements());
	}

	@Test
	void testChangeGridWidthImpactOnStrokeWidth() {
		final double strokeBefore = view.getMaingrid().getStrokeWidth();
		model.setGridWidth(21d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(strokeBefore, view.getMaingrid().getStrokeWidth());
	}

	@Test
	void testChangeGridWidthImpactOnLabels() {
		final List<Double> xBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		final List<Double> yBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		model.setGridWidth(43d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(xBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList()));
		assertNotEquals(yBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList()));
	}


	@Test
	void testChangeSubGridWidth() {
		final double strokeBefore = view.getSubgrid().getStrokeWidth();
		model.setSubGridWidth(11d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(strokeBefore, view.getSubgrid().getStrokeWidth());
	}

	@Test
	void testChangeYLabelWest() {
		final List<Double> xBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		model.setYLabelWest(!model.isYLabelWest());
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(xBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList()));
	}

	@Test
	void testChangeXLabelSouth() {
		final List<Double> yBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		model.setXLabelSouth(!model.isXLabelSouth());
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(yBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList()));
	}

	@Override
	@Test
	void testChangeGridEndX() {
		model.setGridEndX(model.getGridEndX() + 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
	}

	@Override
	@Test
	void testChangeGridEndY() {
		model.setGridEndY(model.getGridEndY() + 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
	}

	@Override
	@Test
	void testChangeGridStartX() {
		model.setGridStartX(model.getGridStartX() - 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
	}

	@Override
	@Test
	void testChangeGridStartY() {
		model.setGridStartY(model.getGridStartY() - 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(mainGridBefore, view.getMaingrid().getElements());
		assertNotEquals(subGridBefore, view.getSubgrid().getElements());
	}

	@Override
	@Test
	void testChangeOriginX() {
		final List<Double> yBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		final List<Double> xBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		model.setOriginX(model.getOriginX() + 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(xBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList()));
		assertEquals(yBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList()));
	}

	@Override
	@Test
	void testChangeOriginY() {
		final List<Double> yBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		final List<Double> xBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		model.setOriginY(model.getOriginY() + 1d);
		WaitForAsyncUtils.waitForFxEvents();
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
