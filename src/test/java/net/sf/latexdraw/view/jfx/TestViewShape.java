package net.sf.latexdraw.view.jfx;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.After;
import org.junit.Before;
import org.malai.command.CommandsRegistry;
import org.malai.undo.UndoCollector;

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
	public void setUp() throws InterruptedException, ExecutionException, TimeoutException {
		BadaboomCollector.INSTANCE.clear();
		view = createView();
		model = view.getModel();
	}

	@After
	public void tearDown() {
		view.flush();
		CommandsRegistry.INSTANCE.clear();
		CommandsRegistry.INSTANCE.removeAllHandlers();
		BadaboomCollector.INSTANCE.clear();
		UndoCollector.INSTANCE.clear();
		DviPsColors.INSTANCE.clearUserColours();
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

	protected static List<PathElement> duplicatePath(final List<PathElement> path) {
		return path.stream().map(elt -> {
			PathElement dupelt;
			if(elt instanceof MoveTo) {
				final MoveTo moveTo = (MoveTo) elt;
				dupelt = ViewFactory.INSTANCE.createMoveTo(moveTo.getX(), moveTo.getY());
			}else if(elt instanceof LineTo) {
				final LineTo lineTo = (LineTo) elt;
				dupelt = ViewFactory.INSTANCE.createLineTo(lineTo.getX(), lineTo.getY());
			}else if(elt instanceof ClosePath) {
				dupelt = ViewFactory.INSTANCE.createClosePath();
			}else if(elt instanceof CubicCurveTo) {
				final CubicCurveTo cct = (CubicCurveTo) elt;
				dupelt = ViewFactory.INSTANCE.createCubicCurveTo(cct.getControlX1(), cct.getControlY1(), cct.getControlX2(), cct.getControlY2(), cct.getX(), cct.getY());
			}else {
				throw new IllegalArgumentException();
			}

			dupelt.setAbsolute(elt.isAbsolute());
			return dupelt;
		}).collect(Collectors.toList());
	}
}
