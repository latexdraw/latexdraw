package net.sf.latexdraw.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.MagneticGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMagneticGrid {
	MagneticGrid grid;
	Canvas canvas;
	PreferencesService prefs;

	@BeforeEach
	void setUp() {
		canvas = Mockito.mock(Canvas.class);
		Mockito.when(canvas.zoomProperty()).thenReturn(new SimpleDoubleProperty());
		prefs = new PreferencesService();
		grid = new MagneticGrid(canvas, prefs);
	}

	@Test
	void testGetCustomMagneticGridGap() {
		prefs.gridGapProperty().set(11);
		prefs.gridStyleProperty().set(GridStyle.CUSTOMISED);
		assertEquals(11, grid.getMagneticGridGap());
	}

	@ParameterizedTest
	@CsvSource(value = {"33,3", "36,4"})
	void testGetStdCMMagneticGridGap(final int value, final double expected) {
		Mockito.when(canvas.getPPCDrawing()).thenReturn(value);
		prefs.unitProperty().set(Unit.CM);
		prefs.gridStyleProperty().set(GridStyle.STANDARD);
		assertEquals(expected, grid.getMagneticGridGap(), 0.0001);
	}

	@ParameterizedTest
	@CsvSource(value = {"33,8", "35,9"})
	void testGetStdINCHMagneticGridGap(final int value, final double expected) {
		Mockito.when(canvas.getPPCDrawing()).thenReturn(value);
		prefs.unitProperty().set(Unit.INCH);
		prefs.gridStyleProperty().set(GridStyle.STANDARD);
		assertEquals(expected, grid.getMagneticGridGap(), 0.0001);
	}

	@Test
	void testGetTransformedPointToGridNoMagnetic() {
		prefs.magneticGridProperty().set(false);
		assertTrue(grid.getTransformedPointToGrid(new Point3D(1.1, 2.3, 4.3)).equals(ShapeFactory.INST.createPoint(1.1, 2.3), 0.0001));
	}

	@Test
	void testGetTransformedPointToGridNoGrid() {
		prefs.gridStyleProperty().set(GridStyle.NONE);
		assertTrue(grid.getTransformedPointToGrid(new Point3D(1.1, 2.3, 4.3)).equals(ShapeFactory.INST.createPoint(1.1, 2.3), 0.0001));
	}

	@ParameterizedTest
	@CsvSource(value = {"10, 100, 200, 100, 200", "10, 102, 206, 100, 210"})
	void testGetTransformedPointToGridOK(final int gap, final int x, final int y, final double x2, final double y2) {
//		Mockito.when(canvas.getPPCDrawing()).thenReturn(20);
		prefs.unitProperty().set(Unit.CM);
		prefs.gridStyleProperty().set(GridStyle.CUSTOMISED);
		prefs.gridGapProperty().set(gap);
		prefs.magneticGridProperty().set(true);
		assertTrue(grid.getTransformedPointToGrid(new Point3D(x, y, 0d)).equals(ShapeFactory.INST.createPoint(x2, y2), 0.0001),
			() -> String.valueOf(grid.getTransformedPointToGrid(new Point3D(x, y, 0d))));
	}
}
