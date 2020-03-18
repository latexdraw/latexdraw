package net.sf.latexdraw.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Line;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.view.jfx.Canvas;
import net.sf.latexdraw.view.jfx.MagneticGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
		Mockito.when(canvas.getPPCDrawing()).thenReturn(20);
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
}
