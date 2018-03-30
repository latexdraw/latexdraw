package net.sf.latexdraw.view.jfx;

import javafx.scene.text.Text;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;
import org.junit.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertNotEquals;

abstract class TestViewStdGrid<S extends ViewStdGrid<T>, T extends IStandardGrid> extends TestViewShape<S, T> {
	@Test
	public abstract void testChangeGridEndX();

	@Test
	public abstract void testChangeGridEndY();

	@Test
	public abstract void testChangeGridStartX();

	@Test
	public abstract void testChangeGridStartY();

	@Test
	public abstract void testChangeOriginX();

	@Test
	public abstract void testChangeOriginY();

	@Test
	public void testChangeLabelSize() {
		final double sizeBefore = ((Text) view.getLabels().getChildren().get(0)).getFont().getSize();
		model.setLabelsSize(model.getLabelsSize() + 5);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(sizeBefore, ((Text) view.getLabels().getChildren().get(0)).getFont().getSize());
	}
}
