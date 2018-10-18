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
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestViewText extends TestViewShape<ViewText, IText> {
	@BeforeClass
	public static void beforeClass() {
		ViewText.LOGGER.setLevel(Level.ALL);
		try {
			Platform.startup(() -> {});
		}catch(final IllegalStateException ignored) {
			// Ok
		}
		LaTeXGenerator.setPackages("");
	}

	@AfterClass
	public static void afterClass() {
		ViewText.LOGGER.setLevel(Level.OFF);
	}

	@Override
	@Before
	public void setUp() throws InterruptedException, ExecutionException, TimeoutException {
		super.setUp();
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
	public void testTextProducesImage() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(getImage().isDisable());
		assertTrue(getText().isDisable());
		assertTrue(getImage().isVisible());
		assertFalse(getText().isVisible());
	}

	@Test
	public void testInvalidLaTeXTextProducesText() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(getImage().isDisable());
		assertFalse(getText().isDisable());
		assertFalse(getImage().isVisible());
		assertTrue(getText().isVisible());
	}

	@Test
	public void testInvalidLaTeXTextProducesTooltip() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(getTooltip().getText().isEmpty());
	}

	@Test
	public void testValidLaTeXTextProducesTooltip() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello$");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(HelperTest.getBadaboomMessages(), BadaboomCollector.INSTANCE.isEmpty());
		assertNull(getTooltip() == null ? "" : getTooltip().getText(), getTooltip());// Junit5 message
	}

	@Test
	public void testCompilationDataDuringCompilation() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello$");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(HelperTest.getBadaboomMessages(), BadaboomCollector.INSTANCE.isEmpty());
		assertTrue(view.getCompilationData().isPresent());
	}

	@Test
	public void testTextProducesImageWhenNotDefaultColour() throws InterruptedException, TimeoutException, ExecutionException {
		model.setLineColour(ShapeFactory.INST.createColorFX(Color.AQUAMARINE));
		WaitForAsyncUtils.waitForFxEvents();
		model.setText("hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(HelperTest.getBadaboomMessages(), BadaboomCollector.INSTANCE.isEmpty());
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
