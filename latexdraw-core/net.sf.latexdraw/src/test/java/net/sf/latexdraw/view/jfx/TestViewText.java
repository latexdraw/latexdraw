package net.sf.latexdraw.view.jfx;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestViewText extends TestViewShape<ViewText, IText> {
	@BeforeClass
	public static void beforeClass() {
		Platform.startup(() -> {});
		LaTeXGenerator.setPackages("");
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
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
		assertFalse(getImage().isDisable());
		assertTrue(getText().isDisable());
		assertTrue(getImage().isVisible());
		assertFalse(getText().isVisible());
	}

	@Test
	public void testInvalidLaTeXTextProducesText() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		assertTrue(getImage().isDisable());
		assertFalse(getText().isDisable());
		assertFalse(getImage().isVisible());
		assertTrue(getText().isVisible());
	}

	@Test
	public void testInvalidLaTeXTextProducesTooltip() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		assertFalse(getTooltip().getText().isEmpty());
	}

	@Test
	public void testValidLaTeXTextProducesTooltip() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello$");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		assertTrue(HelperTest.getBadaboomMessages(), BadaboomCollector.INSTANCE.isEmpty());
		assertNull(getTooltip() == null ? "" : getTooltip().getText(), getTooltip());// Junit5 message
	}

	@Test
	public void testCompilationDataDuringCompilation() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello$");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
		assertTrue(HelperTest.getBadaboomMessages(), BadaboomCollector.INSTANCE.isEmpty());
		assertTrue(view.getCompilationData().isPresent());
	}

	@Test
	public void testCompilationDataNullBeforeCompilation() throws InterruptedException, TimeoutException, ExecutionException {
		model.setText("$hello$");
		assertFalse(view.getCompilationData().isPresent());
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
	}

	@Test
	public void testTextProducesImageWhenNotDefaultColour() throws InterruptedException, TimeoutException, ExecutionException {
		model.setLineColour(ShapeFactory.INST.createColorFX(Color.AQUAMARINE));
		model.setText("hello");
		view.getCurrentCompilation().get(5, TimeUnit.SECONDS);
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
		assertEquals(before, view.getBoundsInParent());
	}

	@Test
	@Override
	public void testOnTranslateY() {
		final Bounds before = view.getBoundsInParent();
		model.translate(0d, -19d);
		view.getChildren().get(0).setTranslateY(19d);
		assertEquals(before, view.getBoundsInParent());
	}
}
