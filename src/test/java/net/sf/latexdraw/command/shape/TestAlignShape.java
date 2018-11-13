package net.sf.latexdraw.command.shape;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.sf.latexdraw.command.TestUndoableCommand;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Factory;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestAlignShape extends TestUndoableCommand<AlignShapes, List<Tuple<Point, Point>>> {
	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object> data() {
		return Arrays.stream(AlignShapes.Alignment.values()).collect(Collectors.toList());
	}

	@Parameterized.Parameter
	public AlignShapes.Alignment alignment;
	Group shapes;
	Canvas canvas;
	javafx.scene.Group views;

	@Override
	@Before
	public void setUp() {
		canvas = Mockito.mock(Canvas.class);
		final Factory fac = ShapeFactory.INST;
		shapes = fac.createGroup();
		shapes.addShape(fac.createRectangle(fac.createPoint(10d, -2d), 6d, 6d));
		shapes.addShape(fac.createRectangle(fac.createPoint(-5d, 20d), 12d, 15d));
		shapes.addShape(fac.createRectangle(fac.createPoint(14d, 60d), 20d, 16d));
		views = new javafx.scene.Group();

		final ViewFactory vfac = new ViewFactory(Mockito.mock(LaTeXDataService.class));
		IntStream.range(0, shapes.size()).forEach(i -> {
			views.getChildren().add(vfac.createView(shapes.getShapeAt(i).orElseThrow()).get());
			Mockito.when(canvas.getViewFromShape(shapes.getShapeAt(i).orElseThrow())).thenReturn(Optional.of((ViewShape<?>) views.getChildren().get(i)));
		});
		super.setUp();
	}

	@Override
	@After
	public void tearDown() {
		super.tearDown();
		views.getChildren().stream().filter(v -> v instanceof ViewShape).forEach(v -> ((ViewShape<?>) v).flush());
		views.getChildren().clear();
		shapes.clear();
	}

	@Override
	protected void checkUndo() {
		int i = 0;
		for(final Shape shape : shapes.getShapes()) {
			assertEquals(shape.getTopLeftPoint(), memento.get(i).a);
			assertEquals(shape.getBottomRightPoint(), memento.get(i).b);
			i++;
		}
	}

	@Override
	protected void configCorrectCmd() {
		cmd = new AlignShapes(canvas, alignment, shapes);
		memento = shapes.getShapes().stream().map(sh -> new Tuple<>(sh.getTopLeftPoint(), sh.getBottomRightPoint())).collect(Collectors.toList());
	}

	@Override
	protected void checkDo() {
		switch(alignment) {
			case TOP:
				assertThat(shapes.getShapes().stream().mapToDouble(sh -> sh.getTopLeftPoint().getY()).boxed().collect(Collectors.toList()),
					everyItem(closeTo(-2d, 0.000001d)));
				break;
			case BOTTOM:
				assertThat(shapes.getShapes().stream().mapToDouble(sh -> sh.getBottomRightPoint().getY()).boxed().collect(Collectors.toList()),
					everyItem(closeTo(76d, 0.000001d)));
				break;
			case LEFT:
				assertThat(shapes.getShapes().stream().mapToDouble(sh -> sh.getTopLeftPoint().getX()).boxed().collect(Collectors.toList()),
					everyItem(closeTo(-5d, 0.000001d)));
				break;
			case RIGHT:
				assertThat(shapes.getShapes().stream().mapToDouble(sh -> sh.getBottomRightPoint().getX()).boxed().collect(Collectors.toList()),
					everyItem(closeTo(34d, 0.000001d)));
				break;
			case MID_HORIZ:
				assertThat(shapes.getShapes().stream().mapToDouble(sh -> sh.getGravityCentre().getY()).boxed().collect(Collectors.toList()),
					everyItem(closeTo(37d, 0.000001d)));
				break;
			case MID_VERT:
				assertThat(shapes.getShapes().stream().mapToDouble(sh -> sh.getGravityCentre().getX()).boxed().collect(Collectors.toList()),
					everyItem(closeTo(14.5d, 0.000001d)));
				break;
		}
	}
}
