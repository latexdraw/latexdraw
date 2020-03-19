package net.sf.latexdraw.command.shape;

import io.github.interacto.jfx.test.UndoableCmdTest;
import java.util.stream.Stream;
import javafx.geometry.Point3D;
import net.sf.latexdraw.LatexdrawExtension;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.MagneticGrid;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the command UpdateToGrid. Generated by Interacto test-gen.
 */
@Tag("command")
@ExtendWith(LatexdrawExtension.class)
class UpdateToGridTest extends UndoableCmdTest<UpdateToGrid> {
	static int margins;
	MagneticGrid grid;
	Group shape;
	Shape s0;
	Shape s1;

	@BeforeEach
	void setUp() {
		bundle = new PreferencesService().getBundle();
	}

	@BeforeAll
	static void beforeAll() {
		margins = Canvas.getMargins();
		Canvas.setMargins(0);
	}

	@AfterAll
	static void tearDown() {
		Canvas.setMargins(margins);
	}

	@Override
	protected Stream<Runnable> canDoFixtures() {
		return Stream.of(() -> {
			shape = ShapeFactory.INST.createGroup();
			s0 = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(1, 2), 3, 4);
			s1 = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(10, 20), 30, 40);
			shape.addShape(s0);
			shape.addShape(s1);
			grid = Mockito.mock(MagneticGrid.class);
			Mockito.when(grid.getTransformedPointToGrid(new Point3D(1, 2, 0))).thenReturn(ShapeFactory.INST.createPoint(5, 6));
			Mockito.when(grid.getTransformedPointToGrid(new Point3D(4, 2, 0))).thenReturn(ShapeFactory.INST.createPoint(7, 8));
			Mockito.when(grid.getTransformedPointToGrid(new Point3D(4, 6, 0))).thenReturn(ShapeFactory.INST.createPoint(9, 10));
			Mockito.when(grid.getTransformedPointToGrid(new Point3D(1, 6, 0))).thenReturn(ShapeFactory.INST.createPoint(11, 12));
			Mockito.when(grid.getTransformedPointToGrid(new Point3D(10, 20, 0))).thenReturn(ShapeFactory.INST.createPoint(50, 60));
			Mockito.when(grid.getTransformedPointToGrid(new Point3D(40, 20, 0))).thenReturn(ShapeFactory.INST.createPoint(70, 80));
			Mockito.when(grid.getTransformedPointToGrid(new Point3D(40, 60, 0))).thenReturn(ShapeFactory.INST.createPoint(90, 100));
			Mockito.when(grid.getTransformedPointToGrid(new Point3D(10, 60, 0))).thenReturn(ShapeFactory.INST.createPoint(110, 120));
			cmd = new UpdateToGrid(grid, shape);
		});
	}

	@Override
	protected Stream<Runnable> doCheckers() {
		return Stream.of(() -> {
			assertThat(shape.isModified()).isTrue();
			assertThat(s0.getPoints().get(0)).isEqualTo(ShapeFactory.INST.createPoint(5, 6));
			assertThat(s0.getPoints().get(1)).isEqualTo(ShapeFactory.INST.createPoint(7, 8));
			assertThat(s0.getPoints().get(2)).isEqualTo(ShapeFactory.INST.createPoint(9, 10));
			assertThat(s0.getPoints().get(3)).isEqualTo(ShapeFactory.INST.createPoint(11, 12));

			assertThat(s1.getPoints().get(0)).isEqualTo(ShapeFactory.INST.createPoint(50, 60));
			assertThat(s1.getPoints().get(1)).isEqualTo(ShapeFactory.INST.createPoint(70, 80));
			assertThat(s1.getPoints().get(2)).isEqualTo(ShapeFactory.INST.createPoint(90, 100));
			assertThat(s1.getPoints().get(3)).isEqualTo(ShapeFactory.INST.createPoint(110, 120));
		});
	}

	@Override
	protected Stream<Runnable> undoCheckers() {
		return Stream.of(() -> {
			assertThat(shape.isModified()).isFalse();
			assertThat(s0.getTopLeftPoint()).isEqualTo(ShapeFactory.INST.createPoint(1, 2));
			assertThat(s0.getBottomRightPoint()).isEqualTo(ShapeFactory.INST.createPoint(4, 6));

			assertThat(s1.getTopLeftPoint()).isEqualTo(ShapeFactory.INST.createPoint(10, 20));
			assertThat(s1.getBottomRightPoint()).isEqualTo(ShapeFactory.INST.createPoint(40, 60));
		});
	}
}
