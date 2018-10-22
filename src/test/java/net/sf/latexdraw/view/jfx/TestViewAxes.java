package net.sf.latexdraw.view.jfx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.scene.shape.PathElement;
import javafx.scene.text.Text;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TestViewAxes extends TestViewStdGrid<ViewAxes, IAxes> {
	List<PathElement> mainAxesBefore;
	List<PathElement> pathTicksBefore;
	// Nested junit5:
	IPoint ptH1;
	IPoint ptH2;
	IPoint ptV1;
	IPoint ptV2;

	@BeforeClass
	public static void beforeClass() {
		try {
			Platform.startup(() -> {});
		}catch(final IllegalStateException ignored) {
			// Ok
		}
	}

	@Override
	@Before
	public void setUp() throws InterruptedException, ExecutionException, TimeoutException {
		super.setUp();
		mainAxesBefore = new ArrayList<>(view.framePath.getElements());
		pathTicksBefore = new ArrayList<>(view.pathTicks.getElements());
		ptH1 = ShapeFactory.INST.createPoint(view.axesHoriz.getModel().getPtAt(0));
		ptH2 = ShapeFactory.INST.createPoint(view.axesHoriz.getModel().getPtAt(1));
		ptV1 = ShapeFactory.INST.createPoint(view.axesVert.getModel().getPtAt(0));
		ptV2 = ShapeFactory.INST.createPoint(view.axesVert.getModel().getPtAt(1));
	}

	@Override
	protected IAxes createModel() {
		return ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint(101, 67));
	}

	@Test
	public void testChangeincrementXProperty() {
		final List<String> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList());
		model.setIncrementX(model.getIncrementX() + 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(textBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList()));
	}

	@Test
	public void testChangeincrementYProperty() {
		final List<String> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList());
		model.setIncrementY(model.getIncrementY() + 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(textBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList()));
	}

	@Test
	public void testChangedistLabelsXProperty() {
		final List<Double> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList());
		model.setDistLabelsX(model.getDistLabelsX() + 0.53);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(textBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getX()).collect(Collectors.toList()));
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	public void testChangedistLabelsYProperty() {
		final List<Double> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList());
		model.setDistLabelsY(model.getDistLabelsY() + 0.53);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(textBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getY()).collect(Collectors.toList()));
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	public void testChangelabelsDisplayedXProperty() {
		final int sizeBefore = view.getLabels().getChildren().size();
		model.setLabelsDisplayed(PlottingStyle.X);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(sizeBefore, view.getLabels().getChildren().size());
	}

	@Test
	public void testChangelabelsDisplayedYProperty() {
		final int sizeBefore = view.getLabels().getChildren().size();
		model.setLabelsDisplayed(PlottingStyle.Y);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(sizeBefore, view.getLabels().getChildren().size());
	}

	@Test
	public void testChangelabelsDisplayedNoneProperty() {
		model.setLabelsDisplayed(PlottingStyle.NONE);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getLabels().getChildren().isEmpty());
	}


	@Test
	public void testChangelabelsDisplayedAllProperty() {
		model.setLabelsDisplayed(PlottingStyle.X);
		WaitForAsyncUtils.waitForFxEvents();
		final int sizeBefore = view.getLabels().getChildren().size();
		model.setLabelsDisplayed(PlottingStyle.ALL);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(sizeBefore, view.getLabels().getChildren().size());
	}

	@Test
	public void testChangeshowOriginProperty() {
		final int sizeBefore = view.getLabels().getChildren().size();
		model.setShowOrigin(!model.isShowOrigin());
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(sizeBefore, view.getLabels().getChildren().size());
	}

	@Test
	public void testChangeticksDisplayedXProperty() {
		model.setTicksDisplayed(PlottingStyle.X);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	public void testChangeticksDisplayedYProperty() {
		model.setTicksDisplayed(PlottingStyle.Y);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	public void testChangeticksDisplayedNoneProperty() {
		model.setTicksDisplayed(PlottingStyle.NONE);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.pathTicks.getElements().isEmpty());
	}

	@Test
	public void testChangeticksDisplayedAllProperty() {
		model.setTicksDisplayed(PlottingStyle.X);
		WaitForAsyncUtils.waitForFxEvents();
		pathTicksBefore = new ArrayList<>(view.pathTicks.getElements());
		model.setTicksDisplayed(PlottingStyle.ALL);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	public void testChangeticksStyleBottomProperty() {
		model.setTicksStyle(TicksStyle.BOTTOM);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	public void testChangeticksStyleTopProperty() {
		model.setTicksStyle(TicksStyle.TOP);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	public void testChangeticksStyleFullProperty() {
		model.setTicksStyle(TicksStyle.TOP);
		WaitForAsyncUtils.waitForFxEvents();
		pathTicksBefore = new ArrayList<>(view.pathTicks.getElements());
		model.setTicksStyle(TicksStyle.FULL);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	public void testChangeticksSizeProperty() {
		model.setTicksSize(model.getTicksSize() + 2.34);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	public void testChangeaxesStyleFrameProperty() {
		model.setAxesStyle(AxesStyle.FRAME);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(mainAxesBefore, view.framePath.getElements());
	}

	@Test
	public void testChangeaxesStyleNoneProperty() {
		model.setAxesStyle(AxesStyle.NONE);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.framePath.getElements().isEmpty());
	}

	@Test
	public void testChangeaxesStyleAxesProperty() {
		model.setAxesStyle(AxesStyle.NONE);
		WaitForAsyncUtils.waitForFxEvents();
		mainAxesBefore = new ArrayList<>(view.framePath.getElements());
		model.setAxesStyle(AxesStyle.FRAME);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(mainAxesBefore, view.framePath.getElements());
	}

	@Override
	@Test
	public void testChangeGridEndX() {
		model.setGridEndX(model.getGridEndX() + 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(ptH1, view.axesHoriz.getModel().getPtAt(0));
		assertNotEquals(ptH2, view.axesHoriz.getModel().getPtAt(1));
		assertEquals(ptV1, view.axesVert.getModel().getPtAt(0));
		assertEquals(ptV2, view.axesVert.getModel().getPtAt(1));
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	@Override
	public void testChangeGridEndY() {
		model.setGridEndY(model.getGridEndY() + 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(ptH1, view.axesHoriz.getModel().getPtAt(0));
		assertEquals(ptH2, view.axesHoriz.getModel().getPtAt(1));
		assertEquals(ptV1, view.axesVert.getModel().getPtAt(0));
		assertNotEquals(ptV2, view.axesVert.getModel().getPtAt(1));
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	@Override
	public void testChangeGridStartX() {
		model.setGridStartX(model.getGridStartX() - 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(ptH1, view.axesHoriz.getModel().getPtAt(0));
		assertEquals(ptH2, view.axesHoriz.getModel().getPtAt(1));
		assertEquals(ptV1, view.axesVert.getModel().getPtAt(0));
		assertEquals(ptV2, view.axesVert.getModel().getPtAt(1));
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}

	@Test
	@Override
	public void testChangeGridStartY() {
		model.setGridStartY(model.getGridStartY() - 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(ptH1, view.axesHoriz.getModel().getPtAt(0));
		assertEquals(ptH2, view.axesHoriz.getModel().getPtAt(1));
		assertNotEquals(ptV1, view.axesVert.getModel().getPtAt(0));
		assertEquals(ptV2, view.axesVert.getModel().getPtAt(1));
		assertNotEquals(pathTicksBefore, view.pathTicks.getElements());
	}


	@Override
	@Test
	public void testChangeOriginX() {
		final List<String> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList());
		model.setOriginX(model.getOriginX() + 1d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(textBefore, view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList()));
	}

	@Override
	@Test
	public void testChangeOriginY() {
		final List<String> textBefore = view.getLabels().getChildren().stream().map(c -> ((Text) c).getText()).collect(Collectors.toList());
		model.setOriginX(model.getOriginY() + 1d);
		WaitForAsyncUtils.waitForFxEvents();
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
