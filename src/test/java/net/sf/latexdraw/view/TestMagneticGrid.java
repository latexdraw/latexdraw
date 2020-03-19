package net.sf.latexdraw.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.shape.Line;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.MagneticGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMagneticGrid {
	MagneticGrid grid;
	Canvas canvas;
	PreferencesService prefs;
	DoubleProperty zoom;
	DoubleProperty prefW;
	DoubleProperty prefH;

	@BeforeEach
	void setUp() {
		prefW = new SimpleDoubleProperty(100d);
		prefH = new SimpleDoubleProperty(50d);
		zoom = new SimpleDoubleProperty(1d);
		canvas = Mockito.mock(Canvas.class);
		Mockito.when(canvas.prefHeightProperty()).thenReturn(prefH);
		Mockito.when(canvas.prefWidthProperty()).thenReturn(prefW);
		Mockito.when(canvas.getPrefHeight()).thenReturn(50d);
		Mockito.when(canvas.getPrefWidth()).thenReturn(100d);
		Mockito.when(canvas.getPPCDrawing()).thenReturn(50);
		Mockito.when(canvas.zoomProperty()).thenReturn(zoom);
		Mockito.when(canvas.getZoom()).thenAnswer(inv -> zoom.get());
		prefs = new PreferencesService();
		grid = new MagneticGrid(canvas, prefs);
		prefs.gridStyleProperty().set(GridStyle.STANDARD);
	}

	@Test
	void testUpdateOnUnitChangeToSTD() {
		prefs.gridStyleProperty().set(GridStyle.NONE);
		prefs.gridStyleProperty().set(GridStyle.STANDARD);
		prefs.unitProperty().set(Unit.CM);
		assertThat(grid.getChildren()).isNotEmpty();
	}

	@Test
	void testUpdateOnUnitChangeToNONE() {
		prefs.gridStyleProperty().set(GridStyle.NONE);
		assertThat(grid.getChildren()).isEmpty();
	}

	@Test
	void testUpdateOnUnitChangeToCUSTOM() {
		final double x = ((Line) grid.getChildren().get(2)).getStartX();
		prefs.gridStyleProperty().set(GridStyle.CUSTOMISED);
		assertThat(x).isNotEqualTo(((Line) grid.getChildren().get(2)).getStartX());
	}

	@ParameterizedTest
	@ValueSource(ints = {15, 100})
	void testUpdateGridSize(final double gap) {
		prefs.gridStyleProperty().set(GridStyle.CUSTOMISED);
		final double x = ((Line) grid.getChildren().get(0)).getStartX();
		prefs.gridGapProperty().setValue(gap);
		assertThat(x).isNotEqualTo(((Line) grid.getChildren().get(0)).getStartX());
	}

	@Test
	void testUpdateUnitINCH() {
		final double x = ((Line) grid.getChildren().get(2)).getStartX();
		prefs.unitProperty().set(Unit.INCH);
		assertThat(x).isNotEqualTo(((Line) grid.getChildren().get(2)).getStartX());
	}

	@Test
	void testUpdateUnitCM() {
		prefs.unitProperty().set(Unit.INCH);
		final double x = ((Line) grid.getChildren().get(2)).getStartX();
		prefs.unitProperty().set(Unit.CM);
		assertThat(x).isNotEqualTo(((Line) grid.getChildren().get(2)).getStartX());
	}

	@Test
	void testUpdateZoom() {
		prefs.gridStyleProperty().set(GridStyle.CUSTOMISED);
		final double x = ((Line) grid.getChildren().get(2)).getStartX();
		zoom.setValue(0.1);
		assertThat(x).isNotEqualTo(((Line) grid.getChildren().get(2)).getStartX());
	}

	@Test
	void testGetTransformedPointToGridNotMagnetic() {
		prefs.magneticGridProperty().set(false);
		assertThat(grid.getTransformedPointToGrid(new Point3D(1, 2, 3))).isEqualTo(ShapeFactory.INST.createPoint(1, 2));
	}

	@Test
	void testGetTransformedPointToGridNoGrid() {
		prefs.magneticGridProperty().set(true);
		prefs.gridStyleProperty().setValue(GridStyle.NONE);
		assertThat(grid.getTransformedPointToGrid(new Point3D(2, 3, 4))).isEqualTo(ShapeFactory.INST.createPoint(2, 3));
	}

	@ParameterizedTest
	@CsvSource(value = {"9, 8, 0, 0",
		"11, 8, 20, 0",
		"4, 11, 0, 20",
		"30, 55, 40, 60",
		"-29, -55, -20, -60"})
	void testGetTransformedPointToGridCustomGrid(final double x1, final double y1, final double x2, final double y2) {
		prefs.gridStyleProperty().setValue(GridStyle.CUSTOMISED);
		prefs.gridGapProperty().setValue(20);
		prefs.magneticGridProperty().set(true);
		assertThat(grid.getTransformedPointToGrid(new Point3D(x1, y1, 11))).isEqualTo(ShapeFactory.INST.createPoint(x2, y2));
	}

	@ParameterizedTest
	@CsvSource(value = {"2, 1, 0, 0",
		"3, 2, 5, 0",
		"1, 3, 0, 5",
		"3, 8, 5, 10",
		"-6, -8, -5, -10"})
	void testGetTransformedPointToGridStdGridCM(final double x1, final double y1, final double x2, final double y2) {
		prefs.gridStyleProperty().setValue(GridStyle.STANDARD);
		prefs.unitProperty().setValue(Unit.CM);
		prefs.magneticGridProperty().set(true);
		assertThat(grid.getTransformedPointToGrid(new Point3D(x1, y1, 11))).isEqualTo(ShapeFactory.INST.createPoint(x2, y2));
	}

	@ParameterizedTest
	@CsvSource(value = {"2, 1, 0, 0",
		"7, 2, 13, 0",
		"6, 6.6, 0, 13",
		"13, 20, 13, 26",
		"-8, -20, -13, -26"})
	void testGetTransformedPointToGridStdGridINCH(final double x1, final double y1, final double x2, final double y2) {
		prefs.gridStyleProperty().setValue(GridStyle.STANDARD);
		prefs.unitProperty().setValue(Unit.INCH);
		prefs.magneticGridProperty().set(true);
		assertThat(grid.getTransformedPointToGrid(new Point3D(x1, y1, 11))).isEqualTo(ShapeFactory.INST.createPoint(x2, y2));
	}
}
