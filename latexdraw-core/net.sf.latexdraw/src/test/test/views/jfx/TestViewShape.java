package test.views.jfx;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.junit.After;
import org.junit.Before;
import test.HelperTest;

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
}
