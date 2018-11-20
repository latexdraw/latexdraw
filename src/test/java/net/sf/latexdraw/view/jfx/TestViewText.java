package net.sf.latexdraw.view.jfx;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import javafx.geometry.Bounds;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Text;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestViewText extends TestViewShape<ViewText, Text> {
	@BeforeAll
	public static void beforeClass() {
		ViewText.LOGGER.setLevel(Level.ALL);
	}

	@AfterAll
	public static void afterClass() {
		ViewText.LOGGER.setLevel(Level.OFF);
	}

	@BeforeEach
	void setUp() throws InterruptedException, ExecutionException, TimeoutException {
		// Have to wait for the compilation of the text used during the initialisation of the text shape
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
	}

	@Override
	protected Text createModel() {
		return ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(51d, 73d), "$foo");
	}

	private ImageView getImage() {
		return (ImageView) view.getChildren().stream().filter(node -> node instanceof ImageView).findAny().orElseThrow(() -> new IllegalArgumentException());
	}

	private javafx.scene.text.Text getText() {
		return (javafx.scene.text.Text) view.getChildren().stream().filter(node -> node instanceof javafx.scene.text.Text).findAny().orElseThrow(() -> new IllegalArgumentException());
	}

	private Tooltip getTooltip() {
		return (Tooltip) getText().getProperties().get("javafx.scene.control.Tooltip");
	}

	@Test
	void testTextProducesImage() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(getImage().isDisable());
		assertTrue(getText().isDisable());
		assertTrue(getImage().isVisible());
		assertFalse(getText().isVisible());
	}

	@Test
	void testInvalidLaTeXTextProducesText() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(getImage().isDisable());
		assertFalse(getText().isDisable());
		assertFalse(getImage().isVisible());
		assertTrue(getText().isVisible());
	}

	@Test
	void testInvalidLaTeXTextProducesTooltip() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(getTooltip().getText().isEmpty());
	}

	@Test
	void testValidLaTeXTextProducesTooltip() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello$");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(BadaboomCollector.INSTANCE.errorsProperty().isEmpty(), () -> HelperTest.getBadaboomMessages());
		assertNull(getTooltip(), () -> getTooltip().getText());
	}

	@Test
	void testCompilationDataDuringCompilation() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello$");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(BadaboomCollector.INSTANCE.errorsProperty().isEmpty(), () -> HelperTest.getBadaboomMessages());
		assertTrue(view.getCompilationData().isPresent());
	}

	@Test
	void testTextProducesImageWhenNotDefaultColour() throws InterruptedException, TimeoutException, ExecutionException {
		model.setLineColour(ShapeFactory.INST.createColorFX(Color.AQUAMARINE));
		WaitForAsyncUtils.waitForFxEvents();
		model.setText("hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(BadaboomCollector.INSTANCE.errorsProperty().isEmpty(), () -> HelperTest.getBadaboomMessages());
		assertFalse(getImage().isDisable());
		assertTrue(getImage().isVisible());
	}

	@Test
	@Override
	public void testOnTranslateX() {
		final Bounds before = view.getBoundsInParent();
		model.translate(17d, 0d);
		view.getChildren().get(0).setTranslateX(-17d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(before, view.getBoundsInParent());
	}

	@Test
	@Override
	public void testOnTranslateY() {
		final Bounds before = view.getBoundsInParent();
		model.translate(0d, -19d);
		view.getChildren().get(0).setTranslateY(19d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(before, view.getBoundsInParent());
	}
}
