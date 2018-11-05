package net.sf.latexdraw.view.jfx;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import net.sf.latexdraw.data.ShapeSupplier;
import net.sf.latexdraw.model.api.shape.Picture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestViewPicture extends TestViewShape<ViewPicture, Picture> {
	@BeforeAll
	public static void beforeClass() {
		try {
			Platform.startup(() -> {
			});
		}catch(final IllegalStateException ex) {
			// Ok
		}
	}

	@Override
	protected Picture createModel() {
		return ShapeSupplier.createPicture();
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
