package test.views.jfx;

import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.view.jfx.ViewSingleShape;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

//TODO JUNIT 5: use this trait in Rectangle/Square view test class
public interface TestLineArcView<T extends ViewSingleShape<S, Rectangle>, S extends ISingleShape & ILineArcProp> extends ITestViewBorderedShape<T, S, Rectangle> {
	@Test
	default void testBorderLineArcWidth() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getWidth(), getBorder().getArcWidth(), 0.000001);
	}

	@Test
	default void testBorderLineArcHeight() {
		getModel().setLineArc(0.33);
		assertEquals(0.33 * getModel().getHeight(), getBorder().getArcHeight(), 0.000001);
	}

	@Test
	default void testShadowLineArcWidth() {
		if(getModel().isShadowable()) {
			getModel().setLineArc(0.33);
			assertEquals(0.33 * getModel().getWidth(), getView().getShadow().get().getArcWidth(), 0.000001);
		}
	}

	@Test
	default void testShadowLineArcHeight() {
		if(getModel().isShadowable()) {
			getModel().setLineArc(0.33);
			assertEquals(0.33 * getModel().getHeight(), getView().getShadow().get().getArcHeight(), 0.000001);
		}
	}

	@Test
	default void testDbleLineArcWidth() {
		if(getModel().isDbleBorderable()) {
			getModel().setLineArc(0.33);
			assertEquals(0.33 * getModel().getWidth(), getView().getDbleBorder().get().getArcWidth(), 0.000001);
		}
	}

	@Test
	default void testDbleLineArcHeight() {
		if(getModel().isDbleBorderable()) {
			getModel().setLineArc(0.33);
			assertEquals(0.33 * getModel().getHeight(), getView().getDbleBorder().get().getArcHeight(), 0.000001);
		}
	}
}
