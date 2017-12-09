package net.sf.latexdraw.actions.shape;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.scene.Group;
import net.sf.latexdraw.actions.TestUndoableAction;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IShapeFactory;
import net.sf.latexdraw.util.Tuple;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.jfx.ViewShape;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestAlignShape extends TestUndoableAction<AlignShapes, List<Tuple<IPoint, IPoint>>> {
	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object> data() {
		return Arrays.stream(AlignShapes.Alignment.values()).collect(Collectors.toList());
	}

	@Parameterized.Parameter
	public AlignShapes.Alignment alignment;
	IGroup shapes;
	@Mock Canvas canvas;
	Group views;

	@Override
	protected AlignShapes createAction() {
		return new AlignShapes();
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		MockitoAnnotations.initMocks(this);
		final IShapeFactory fac = ShapeFactory.INST;
		shapes = fac.createGroup();
		shapes.addShape(fac.createRectangle(fac.createPoint(10d, -2d), 6d, 6d));
		shapes.addShape(fac.createRectangle(fac.createPoint(-5d, 20d), 12d, 15d));
		shapes.addShape(fac.createRectangle(fac.createPoint(14d, 60d), 20d, 16d));
		views = new Group();

		IntStream.range(0, shapes.size()).forEach(i -> {
			views.getChildren().add(ViewFactory.INSTANCE.createView(shapes.getShapeAt(i)).get());
			Mockito.when(canvas.getViewFromShape(shapes.getShapeAt(i))).thenReturn(Optional.of((ViewShape<?>) views.getChildren().get(i)));
		});
	}

	@Override
	protected void checkUndo() {
		int i=0;
		for(IShape shape : shapes.getShapes()) {
			assertEquals(shape.getTopLeftPoint(), memento.get(i).a);
			assertEquals(shape.getBottomRightPoint(), memento.get(i).b);
			i++;
		}
	}

	@Override
	protected void configCorrectAction() {
		action.setCanvas(canvas);
		action.setShape(shapes);
		action.setAlignment(alignment);
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
