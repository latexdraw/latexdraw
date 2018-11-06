package net.sf.latexdraw.view.jfx;

import javafx.scene.text.Text;
import net.sf.latexdraw.model.api.shape.StandardGrid;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

abstract class TestViewStdGrid<S extends ViewStdGrid<T>, T extends StandardGrid> extends TestViewShape<S, T> {
	abstract void testChangeGridEndX();

	abstract void testChangeGridEndY();

	abstract void testChangeGridStartX();

	abstract void testChangeGridStartY();

	abstract void testChangeOriginX();

	abstract void testChangeOriginY();

	@Test
	void testChangeLabelSize() {
		final double sizeBefore = ((Text) view.getLabels().getChildren().get(0)).getFont().getSize();
		model.setLabelsSize(model.getLabelsSize() + 5);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(sizeBefore, ((Text) view.getLabels().getChildren().get(0)).getFont().getSize());
	}
}
