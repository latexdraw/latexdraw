package test.views.jfx;

import java.util.List;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.junit.After;
import org.junit.Before;
import test.HelperTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

abstract class TestViewShape<T extends ViewShape<S>, S extends ISingleShape> implements HelperTest, ITestViewShape<T, S> {
	protected T view;
	protected S model;

	protected abstract S createModel();

	protected T createView() {
		return (T) ViewFactory.INSTANCE.createView(createModel()).get();
	}

	@Before
	public void setUp() {
		BadaboomCollector.INSTANCE.clear();
		view = createView();
		model = view.getModel();
	}

	@After
	public void tearDown() throws Exception {
		view.flush();
	}

	@Override
	public T getView() {
		return view;
	}

	@Override
	public S getModel() {
		return model;
	}

	public static void assertPathSameButNotEqual(final List<PathElement> p1, final List<PathElement> p2) {
		assertEquals(p1.size(), p2.size());
		for(int i=0, size=p2.size(); i<size; i++) {
			if(p1.get(i) instanceof ClosePath) {
				assertTrue(p2.get(i) instanceof ClosePath);
			}else {
				assertNotEquals(p1.get(i), p2.get(i));
			}
		}
	}
}
