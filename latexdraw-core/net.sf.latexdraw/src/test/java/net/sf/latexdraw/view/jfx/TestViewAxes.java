package net.sf.latexdraw.view.jfx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import javafx.scene.shape.PathElement;
import javafx.scene.text.Text;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TestViewAxes extends TestViewStdGrid<ViewAxes, IAxes> {
	List<PathElement> mainAxesBefore;
	List<PathElement> pathTicksBefore;

	@BeforeClass
	public static void beforeClass() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		mainAxesBefore = new ArrayList<>(view.getMainAxes().getElements());
		pathTicksBefore = new ArrayList<>(view.getPathTicks().getElements());
	}

	@Override
	protected IAxes createModel() {
		return ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint(101, 67));
	}

	@Test
	public void testChangeincrementXProperty() {
		final List<String> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList());
		model.setIncrementX(model.getIncrementX() + 1d);
		assertNotEquals(textBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList()));
	}

	@Test
	public void testChangeincrementYProperty() {
		final List<String> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList());
		model.setIncrementY(model.getIncrementY() + 1d);
		assertNotEquals(textBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList()));
	}

	@Test
	public void testChangedistLabelsXProperty() {
		final List<Double> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		model.setDistLabelsX(model.getDistLabelsX() + 0.53);
		assertNotEquals(textBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList()));
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	public void testChangedistLabelsYProperty() {
		final List<Double> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		model.setDistLabelsY(model.getDistLabelsY() + 0.53);
		assertNotEquals(textBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList()));
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	public void testChangelabelsDisplayedXProperty() {
		final int sizeBefore = view.getLabels().getChildren().size();
		model.setLabelsDisplayed(PlottingStyle.X);
		assertNotEquals(sizeBefore, view.getLabels().getChildren().size());
	}

	@Test
	public void testChangelabelsDisplayedYProperty() {
		final int sizeBefore = view.getLabels().getChildren().size();
		model.setLabelsDisplayed(PlottingStyle.Y);
		assertNotEquals(sizeBefore, view.getLabels().getChildren().size());
	}

	@Test
	public void testChangelabelsDisplayedNoneProperty() {
		model.setLabelsDisplayed(PlottingStyle.NONE);
		assertTrue(view.getLabels().getChildren().isEmpty());
	}


	@Test
	public void testChangelabelsDisplayedAllProperty() {
		model.setLabelsDisplayed(PlottingStyle.X);
		final int sizeBefore = view.getLabels().getChildren().size();
		model.setLabelsDisplayed(PlottingStyle.ALL);
		assertNotEquals(sizeBefore, view.getLabels().getChildren().size());
	}

	@Test
	public void testChangeshowOriginProperty() {
		final int sizeBefore = view.getLabels().getChildren().size();
		model.setShowOrigin(!model.isShowOrigin());
		assertNotEquals(sizeBefore, view.getLabels().getChildren().size());
	}

	@Test
	public void testChangeticksDisplayedXProperty() {
		model.setTicksDisplayed(PlottingStyle.X);
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	public void testChangeticksDisplayedYProperty() {
		model.setTicksDisplayed(PlottingStyle.Y);
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	public void testChangeticksDisplayedNoneProperty() {
		model.setTicksDisplayed(PlottingStyle.NONE);
		assertTrue(view.getPathTicks().getElements().isEmpty());
	}

	@Test
	public void testChangeticksDisplayedAllProperty() {
		model.setTicksDisplayed(PlottingStyle.X);
		pathTicksBefore = new ArrayList<>(view.getPathTicks().getElements());
		model.setTicksDisplayed(PlottingStyle.ALL);
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	public void testChangeticksStyleBottomProperty() {
		model.setTicksStyle(TicksStyle.BOTTOM);
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	public void testChangeticksStyleTopProperty() {
		model.setTicksStyle(TicksStyle.TOP);
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	public void testChangeticksStyleFullProperty() {
		model.setTicksStyle(TicksStyle.TOP);
		pathTicksBefore = new ArrayList<>(view.getPathTicks().getElements());
		model.setTicksStyle(TicksStyle.FULL);
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	public void testChangeticksSizeProperty() {
		model.setTicksSize(model.getTicksSize() + 2.34);
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	public void testChangeaxesStyleFrameProperty() {
		model.setAxesStyle(AxesStyle.FRAME);
		assertNotEquals(mainAxesBefore, view.getMainAxes().getElements());
	}

	@Test
	public void testChangeaxesStyleNoneProperty() {
		model.setAxesStyle(AxesStyle.NONE);
		assertTrue(view.getMainAxes().getElements().isEmpty());
	}

	@Test
	public void testChangeaxesStyleAxesProperty() {
		model.setAxesStyle(AxesStyle.NONE);
		mainAxesBefore = new ArrayList<>(view.getMainAxes().getElements());
		model.setAxesStyle(AxesStyle.FRAME);
		assertNotEquals(mainAxesBefore, view.getMainAxes().getElements());
	}

	@Override
	@Test
	public void testChangeGridEndX() {
		model.setGridEndX(model.getGridEndX() + 1d);
		assertNotEquals(mainAxesBefore, view.getMainAxes().getElements());
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	@Override
	public void testChangeGridEndY() {
		model.setGridEndY(model.getGridEndY() + 1d);
		assertNotEquals(mainAxesBefore, view.getMainAxes().getElements());
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	@Override
	public void testChangeGridStartX() {
		model.setGridStartX(model.getGridStartX() - 1d);
		assertNotEquals(mainAxesBefore, view.getMainAxes().getElements());
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}

	@Test
	@Override
	public void testChangeGridStartY() {
		model.setGridStartY(model.getGridStartY() - 1d);
		assertNotEquals(mainAxesBefore, view.getMainAxes().getElements());
		assertNotEquals(pathTicksBefore, view.getPathTicks().getElements());
	}


	@Override
	@Test
	public void testChangeOriginX() {
		final List<String> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList());
		model.setOriginX(model.getOriginX() + 1d);
		assertNotEquals(textBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList()));
	}

	@Override
	@Test
	public void testChangeOriginY() {
		final List<String> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList());
		model.setOriginX(model.getOriginY() + 1d);
		assertNotEquals(textBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList()));
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
