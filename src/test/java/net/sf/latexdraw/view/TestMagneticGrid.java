package net.sf.latexdraw.view;

public class TestMagneticGrid {
//	MagneticGrid grid;
//	Canvas canvas;
//	PreferencesService prefs;
//	DoubleProperty zoom;
//
//	@BeforeEach
//	void setUp() {
//		zoom = new SimpleDoubleProperty();
//		canvas = Mockito.mock(Canvas.class);
//		Mockito.when(canvas.getPrefHeight()).thenReturn(50d);
//		Mockito.when(canvas.getPrefWidth()).thenReturn(100d);
//		Mockito.when(canvas.getPPCDrawing()).thenReturn(20);
//		Mockito.when(canvas.zoomProperty()).thenReturn(zoom);
//		prefs = new PreferencesService();
//		prefs.gridStyleProperty().set(GridStyle.NONE);
//		prefs.gridGapProperty().set(20);
//		grid = new MagneticGrid(canvas, prefs);
//	}
//
//	@Test
//	void testUpdateOnUnitChange() {
//		prefs.gridStyleProperty().set(GridStyle.STANDARD);
//		prefs.unitProperty().set(Unit.CM);
//		final ViewFactory fact = new ViewFactory(Mockito.mock(LaTeXDataService.class));
//		final Path path = fact.clonePath(grid);
//		prefs.unitProperty().set(Unit.INCH);
//		assertNotEquals(path.getElements().get(2), grid.getElements().get(2));
//		assertNotEquals(path.getElements().get(3), grid.getElements().get(3));
//	}
//
//	@Test
//	void testUpdateOnStyleChange() {
//		final ViewFactory fact = new ViewFactory(Mockito.mock(LaTeXDataService.class));
//		prefs.gridStyleProperty().set(GridStyle.STANDARD);
//		final Path path = fact.clonePath(grid);
//		prefs.gridStyleProperty().set(GridStyle.NONE);
//		assertTrue(grid.getElements().isEmpty());
//		assertFalse(path.getElements().isEmpty());
//	}
//
//	@Test
//	void testUpdateOnGridGapChange() {
//		final ViewFactory fact = new ViewFactory(Mockito.mock(LaTeXDataService.class));
//		prefs.gridStyleProperty().set(GridStyle.CUSTOMISED);
//		prefs.gridGapProperty().set(15);
//		final Path path = fact.clonePath(grid);
//		prefs.gridGapProperty().set(20);
//		assertNotEquals(path.getElements().get(2), grid.getElements().get(2));
//		assertNotEquals(path.getElements().get(3), grid.getElements().get(3));
//	}
//
//	@Test
//	void testGetCustomMagneticGridGap() {
//		prefs.gridGapProperty().set(11);
//		prefs.gridStyleProperty().set(GridStyle.CUSTOMISED);
//		assertEquals(11, grid.getMagneticGridGap());
//	}
//
//	@ParameterizedTest
//	@CsvSource(value = {"33,3", "36,4"})
//	void testGetStdCMMagneticGridGap(final int value, final double expected) {
//		Mockito.when(canvas.getPPCDrawing()).thenReturn(value);
//		prefs.unitProperty().set(Unit.CM);
//		prefs.gridStyleProperty().set(GridStyle.STANDARD);
//		assertEquals(expected, grid.getMagneticGridGap(), 0.0001);
//	}
//
//	@ParameterizedTest
//	@CsvSource(value = {"33,8", "35,9"})
//	void testGetStdINCHMagneticGridGap(final int value, final double expected) {
//		Mockito.when(canvas.getPPCDrawing()).thenReturn(value);
//		prefs.unitProperty().set(Unit.INCH);
//		prefs.gridStyleProperty().set(GridStyle.STANDARD);
//		assertEquals(expected, grid.getMagneticGridGap(), 0.0001);
//	}
//
//	@Test
//	void testGetTransformedPointToGridNoMagnetic() {
//		prefs.magneticGridProperty().set(false);
//		assertTrue(grid.getTransformedPointToGrid(new Point3D(1.1, 2.3, 4.3)).equals(ShapeFactory.INST.createPoint(1.1, 2.3), 0.0001));
//	}
//
//	@Test
//	void testGetTransformedPointToGridNoGrid() {
//		prefs.gridStyleProperty().set(GridStyle.NONE);
//		assertTrue(grid.getTransformedPointToGrid(new Point3D(1.1, 2.3, 4.3)).equals(ShapeFactory.INST.createPoint(1.1, 2.3), 0.0001));
//	}
//
//	@ParameterizedTest
//	@CsvSource(value = {"10, 100, 200, 100, 200", "10, 102, 206, 100, 210"})
//	void testGetTransformedPointToGridOK(final int gap, final int x, final int y, final double x2, final double y2) {
//		prefs.unitProperty().set(Unit.CM);
//		prefs.gridStyleProperty().set(GridStyle.CUSTOMISED);
//		prefs.gridGapProperty().set(gap);
//		prefs.magneticGridProperty().set(true);
//		assertTrue(grid.getTransformedPointToGrid(new Point3D(x, y, 0d)).equals(ShapeFactory.INST.createPoint(x2, y2), 0.0001));
//	}
//
//	@Test
//	void testGridMainLinesSTDCM() {
//		prefs.gridStyleProperty().set(GridStyle.STANDARD);
//		prefs.unitProperty().set(Unit.CM);
//		final MoveTo m0 = (MoveTo) grid.getElements().get(0);
//		final LineTo l0 = (LineTo) grid.getElements().get(1);
//		final MoveTo m1 = (MoveTo) grid.getElements().get(2);
//		final LineTo l1 = (LineTo) grid.getElements().get(3);
//		assertEquals(0d, m0.getX());
//		assertEquals(0d, m0.getY());
//		assertEquals(0d, l0.getX());
//		assertEquals(50d, l0.getY());
//		assertEquals(2d, m1.getX());
//		assertEquals(0d, m1.getY());
//		assertEquals(2d, l1.getX());
//		assertEquals(50d, l1.getY());
//	}
//
//	@Test
//	void testGridLinesSTDINCH() {
//		prefs.gridStyleProperty().set(GridStyle.STANDARD);
//		prefs.unitProperty().set(Unit.INCH);
//		final MoveTo m0 = (MoveTo) grid.getElements().get(0);
//		final LineTo l0 = (LineTo) grid.getElements().get(1);
//		final MoveTo m1 = (MoveTo) grid.getElements().get(2);
//		final LineTo l1 = (LineTo) grid.getElements().get(3);
//		assertEquals(0d, m0.getX());
//		assertEquals(0d, m0.getY());
//		assertEquals(0d, l0.getX());
//		assertEquals(50d, l0.getY());
//		assertEquals(2d * PSTricksConstants.INCH_VAL_CM, m1.getX());
//		assertEquals(0d, m1.getY());
//		assertEquals(2d * PSTricksConstants.INCH_VAL_CM, l1.getX());
//		assertEquals(50d, l1.getY());
//	}
//
////	@Test
////	void testGridLinesCUSTOM() {
////		prefs.gridStyleProperty().set(GridStyle.CUSTOMISED);
////		prefs.unitProperty().set(Unit.CM);
////		final MoveTo m0 = (MoveTo) grid.getElements().get(0);
////		final LineTo l0 = (LineTo) grid.getElements().get(1);
////		final MoveTo m1 = (MoveTo) grid.getElements().get(2);
////		final LineTo l1 = (LineTo) grid.getElements().get(3);
////		assertEquals(0d, m0.getX());
////		assertEquals(0d, m0.getY());
////		assertEquals(0d, l0.getX());
////		assertEquals(50d, l0.getY());
////		assertEquals(20d, m1.getX());
////		assertEquals(0d, m1.getY());
////		assertEquals(20d, l1.getX());
////		assertEquals(50d, l1.getY());
////	}
}
