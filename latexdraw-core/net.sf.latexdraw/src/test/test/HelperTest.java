package test;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;
import java.util.stream.IntStream;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import org.testfx.util.WaitForAsyncUtils;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public interface HelperTest {
	default Field getField(final Class<?> clazz, final String name) throws SecurityException, NoSuchFieldException {
		final Field field = clazz.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}

	default boolean isX11Set() {
		return !GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadlessInstance();
	}

	default void assertEqualsDouble(final double v1, final double v2) {
		assertThat(v1, closeTo(v2, 0.0000001));
	}


	default <T extends Node> void assertNotEqualsSnapshot(T node, Runnable toExecBetweenSnap) {
		Bounds bounds = node.getBoundsInLocal();
		final SnapshotParameters params = new SnapshotParameters();

		final WritableImage oracle = new WritableImage((int) bounds.getWidth(), (int) bounds.getHeight());
		Platform.runLater(() -> node.snapshot(params, oracle));
		WaitForAsyncUtils.waitForFxEvents();

		if(toExecBetweenSnap != null) {
			toExecBetweenSnap.run();
		}

		bounds = node.getBoundsInLocal();
		final WritableImage observ = new WritableImage((int) bounds.getWidth(), (int) bounds.getHeight());
		Platform.runLater(() -> node.snapshot(params, observ));
		WaitForAsyncUtils.waitForFxEvents();

		assertNotEquals("The two snapshots do not differ.", 100d, computeSnapshotSimilarity(oracle, observ), 0.001);
	}


	/**
	 * Compute the similarity of two JavaFX images.
	 * @param image1 The first image to test.
	 * @param image2 The second image to test.
	 * @return A double value in [0;100] corresponding to the similarity between the two images (pixel comparison).
	 * @throws NullPointerException If image1 or image2 is null.
	 */
	default double computeSnapshotSimilarity(final Image image1, final Image image2) {
		final int width = (int) image1.getWidth();
		final int height = (int) image1.getHeight();
		final PixelReader reader1 = image1.getPixelReader();
		final PixelReader reader2 = image2.getPixelReader();

		final double nbNonSimilarPixels = IntStream.range(0, width).parallel().mapToLong(i ->
			IntStream.range(0, height).parallel().filter(j -> reader1.getArgb(i, j) != reader2.getArgb(i, j)).count()).sum();

		return 100d - nbNonSimilarPixels / (width * height) * 100d;
	}
}
