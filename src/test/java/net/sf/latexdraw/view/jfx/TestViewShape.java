package net.sf.latexdraw.view.jfx;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.data.ConfigureInjection;
import net.sf.latexdraw.data.InjectionExtension;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.SingleShape;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import io.github.interacto.command.CommandsRegistry;
import io.github.interacto.undo.UndoCollector;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(InjectionExtension.class)
abstract class TestViewShape<T extends ViewShape<S>, S extends SingleShape> implements HelperTest, ITestViewShape<T, S> {
	public static void assertPathSameButNotEqual(final List<PathElement> p1, final List<PathElement> p2) {
		assertEquals(p1.size(), p2.size());
		for(int i = 0, size = p2.size(); i < size; i++) {
			if(p1.get(i) instanceof ClosePath) {
				assertTrue(p2.get(i) instanceof ClosePath);
			}else {
				assertNotEquals(p1.get(i), p2.get(i));
			}
		}
	}
	protected T view;
	protected S model;
	ViewFactory factory;

	@ConfigureInjection
	Injector createInjector() {
		return new Injector() {
			@Override
			protected void configure() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
				bindAsEagerSingleton(LaTeXDataService.class);
				bindAsEagerSingleton(PreferencesService.class);
				bindWithCommand(ResourceBundle.class, PreferencesService.class, pref -> pref.getBundle());
				bindAsEagerSingleton(ViewFactory.class);
				bindAsEagerSingleton(Canvas.class);
				bindWithCommand(Drawing.class, Canvas.class, canvas -> canvas.getDrawing());
			}
		};
	}

	protected abstract S createModel();

	protected T createView() {
		return (T) factory.createView(createModel()).get();
	}

	@BeforeEach
	void setUp(final ViewFactory factory) {
		BadaboomCollector.INSTANCE.clear();
		this.factory = factory;
		view = createView();
		model = view.getModel();
	}

	@AfterEach
	void tearDown() {
		view.flush();
		CommandsRegistry.getInstance().clear();
		BadaboomCollector.INSTANCE.clear();
		UndoCollector.getInstance().clear();
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

	protected List<PathElement> duplicatePath(final List<PathElement> path) {
		return path.stream().map(elt -> {
			final PathElement dupelt;
			if(elt instanceof MoveTo) {
				final MoveTo moveTo = (MoveTo) elt;
				dupelt = factory.createMoveTo(moveTo.getX(), moveTo.getY());
			}else {
				if(elt instanceof LineTo) {
					final LineTo lineTo = (LineTo) elt;
					dupelt = factory.createLineTo(lineTo.getX(), lineTo.getY());
				}else {
					if(elt instanceof ClosePath) {
						dupelt = factory.createClosePath();
					}else {
						if(elt instanceof CubicCurveTo) {
							final CubicCurveTo cct = (CubicCurveTo) elt;
							dupelt = factory.createCubicCurveTo(cct.getControlX1(), cct.getControlY1(), cct.getControlX2(), cct.getControlY2(), cct.getX(), cct.getY());
						}else {
							throw new IllegalArgumentException();
						}
					}
				}
			}

			dupelt.setAbsolute(elt.isAbsolute());
			return dupelt;
		}).collect(Collectors.toList());
	}
}
