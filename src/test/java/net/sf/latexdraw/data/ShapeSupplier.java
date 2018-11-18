package net.sf.latexdraw.data;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Closable;
import net.sf.latexdraw.model.api.shape.ControlPointShape;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Rhombus;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.Triangle;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class ShapeSupplier extends ParameterSupplier {
	public static Rectangle createRectangle() {
		return ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(51d, 73d), 354d, 234d);
	}

	public static Stream<Rectangle> createDiversifiedRectangle() {
		return Stream.of(createRectangle(), ParameteriseShapeData.INST.setRectangleData1(createRectangle()));
	}

	public static Plot createPlot() {
		return ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(23, 300), 1d, 10d, "x", false);
	}

	public static Stream<Plot> createDiversifiedPlot() {
		return Stream.of(createPlot(), ParameteriseShapeData.INST.setPlotData1(createPlot()), ParameteriseShapeData.INST.setPlotData2(createPlot()),
			ParameteriseShapeData.INST.setPlotData3(createPlot()), ParameteriseShapeData.INST.setPlotData4(createPlot()));
	}

	public static Grid createGrid() {
		return ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint(130d, 284d));
	}

	public static Stream<Grid> createDiversifiedGrid() {
		return Stream.of(createGrid(), ParameteriseShapeData.INST.setGridData1(createGrid()), ParameteriseShapeData.INST.setGridData2(createGrid()),
			ParameteriseShapeData.INST.setGridData3(createGrid()));
	}

	public static Dot createDot() {
		return ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint(120, 234));
	}

	public static Stream<Dot> createDiversifiedDot() {
		return Stream.of(createDot(), ParameteriseShapeData.INST.setDotData1(createDot()),
			ParameteriseShapeData.INST.setDotData2(createDot()), ParameteriseShapeData.INST.setDotData3(createDot()));
	}

	public static Stream<ControlPointShape> createDiversifiedCtrlPtShape() {
		return Stream.of(createBezierCurve());
	}

	public static Stream<Closable> createDiversifiedOpenedShape() {
		return Stream.of(ParameteriseShapeData.INST.setClosableOpened(createBezierCurve()), ParameteriseShapeData.INST.setClosableClosed(createBezierCurve()),
			ParameteriseShapeData.INST.setClosableOpened(createFreeHand()), ParameteriseShapeData.INST.setClosableClosed(createFreeHand()));
	}

	public static Stream<Freehand> createDiversifiedFreeHand() {
		return Stream.of(ParameteriseShapeData.INST.setFreeHand1(createFreeHand()), ParameteriseShapeData.INST.setFreeHand2(createFreeHand()));
	}

	public static Axes createAxes() {
		return ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint(133, 146));
	}

	public static Stream<Axes> createDiversifiedAxes() {
		return Stream.of(createAxes(), ParameteriseShapeData.INST.setAxesData1(createAxes()), ParameteriseShapeData.INST.setAxesData2(createAxes()));
	}

	public static Text createText() {
		return ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(51d, 73d), "$foo");
	}

	public static Stream<Text> createDiversifiedText() {
		return Stream.of(createText(), ParameteriseShapeData.INST.setTextData1(createText()));
	}

	public static Picture createPicture() {
		return ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint(76, 45));
	}

	public static Square createSquare() {
		return ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(13d, 84d), 233);
	}

	public static Stream<Square> createDiversifiedSquare() {
		return Stream.of(ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(13d, 84d), 233), ParameteriseShapeData.INST.setSquareData1(createSquare()));
	}

	public static Rhombus createRhombus() {
		return ShapeFactory.INST.createRhombus(ShapeFactory.INST.createPoint(251d, 33d), 220, 376);
	}

	public static Triangle createTriangle() {
		return ShapeFactory.INST.createTriangle(ShapeFactory.INST.createPoint(251d, 33d), 76, 12);
	}

	public static BezierCurve createBezierCurve() {
		return ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
			ShapeFactory.INST.createPoint(251d, 33d)));
	}

	public static Polyline createPolyline() {
		return ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
			ShapeFactory.INST.createPoint(251d, 33d)));
	}

	public static Polygon createPolygon() {
		return ShapeFactory.INST.createPolygon(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
			ShapeFactory.INST.createPoint(251d, 33d)));
	}

	public static Freehand createFreeHand() {
		return ShapeFactory.INST.createFreeHand(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
			ShapeFactory.INST.createPoint(251d, 33d), ShapeFactory.INST.createPoint(251d, 35d), ShapeFactory.INST.createPoint(151d, 233d)));
	}

	public static Stream<Shape> getDiversifiedShapes() throws IOException {
		return
			Stream.concat(createDiversifiedRectangle(), Stream.concat(ArcSupplier.createDiversifiedArc(),
				Stream.concat(createDiversifiedDot(), Stream.concat(createDiversifiedPlot(),
				Stream.concat(createDiversifiedGrid(), Stream.concat(createDiversifiedAxes(),
				Stream.concat(createDiversifiedText(), Stream.of((Shape) EllSupplier.createEllipse(), createBezierCurve(), CircleSupplier.createCircle(),
					createPolyline(), createPolygon(), createSquare(), createFreeHand(), createRhombus(), createTriangle(),
			ParameteriseShapeData.INST.setPictureData1(createPicture()))))))))).
			map(sh -> Arrays.asList(ParameteriseShapeData.INST.setShapeData1(sh.duplicate()),
				ParameteriseShapeData.INST.setShapeData2(sh.duplicate()),
				ParameteriseShapeData.INST.setShapeData3(sh.duplicate()),
				ParameteriseShapeData.INST.setShapeData4(sh.duplicate()))).flatMap(s -> s.stream());
	}

	public static Stream<Shape> getStdShapesStream() {
		return Stream.of(EllSupplier.createEllipse(), ArcSupplier.createArc(), createGrid(), CircleSupplier.createCircle(),
			createRectangle(), createBezierCurve(), createText(), createDot(), createPolyline(),
			createPolygon(), createAxes(), createSquare(),
			ShapeFactory.INST.createFreeHand(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
				ShapeFactory.INST.createPoint(251d, 33d), ShapeFactory.INST.createPoint(251d, 35d), ShapeFactory.INST.createPoint(151d, 233d))),
			createPlot(), createRhombus(), createTriangle(), createPicture());
	}

	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) throws IOException {
		final ShapeData shapeData = sig.getAnnotation(ShapeData.class);
		final Stream<Shape> instances;

		if(shapeData.withParamVariants()) {
			instances = getDiversifiedShapes();
		}else {
			instances = getStdShapesStream();
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
