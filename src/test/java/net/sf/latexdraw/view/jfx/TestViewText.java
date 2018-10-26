package net.sf.latexdraw.view.jfx;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.data.TextSupplier;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IText;
import net.sf.latexdraw.view.latex.LaTeXGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestViewText extends TestViewShape<ViewText, IText> {
	@BeforeAll
	public static void beforeClass() {
		ViewText.LOGGER.setLevel(Level.ALL);
		try {
			Platform.startup(() -> {});
		}catch(final IllegalStateException ignored) {
			// Ok
		}
		LaTeXGenerator.setPackages("");
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
	protected IText createModel() {
		return TextSupplier.createText();
	}

	private ImageView getImage() {
		return (ImageView) view.getChildren().stream().filter(node -> node instanceof ImageView).findAny().orElseThrow(() -> new IllegalArgumentException());
	}

	private Text getText() {
		return (Text) view.getChildren().stream().filter(node -> node instanceof Text).findAny().orElseThrow(() -> new IllegalArgumentException());
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
		assertTrue(BadaboomCollector.INSTANCE.isEmpty(), () -> HelperTest.getBadaboomMessages());
		assertNull(getTooltip() == null ? "" : getTooltip().getText(), getTooltip());// Junit5 message
	}

	@Test
	void testCompilationDataDuringCompilation() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello$");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(BadaboomCollector.INSTANCE.isEmpty(), () -> HelperTest.getBadaboomMessages());
		assertTrue(view.getCompilationData().isPresent());
	}

	@Test
	void testTextProducesImageWhenNotDefaultColour() throws InterruptedException, TimeoutException, ExecutionException {
		model.setLineColour(ShapeFactory.INST.createColorFX(Color.AQUAMARINE));
		WaitForAsyncUtils.waitForFxEvents();
		model.setText("hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(BadaboomCollector.INSTANCE.isEmpty(), () -> HelperTest.getBadaboomMessages());
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
