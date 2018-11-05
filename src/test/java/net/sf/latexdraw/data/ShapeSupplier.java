package net.sf.latexdraw.data;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Rhombus;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.Triangle;
import net.sf.latexdraw.util.SystemService;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class ShapeSupplier extends ParameterSupplier {
	public static Picture createPicture() {
		return ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint(76, 45), new SystemService());
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

	public static Stream<Shape> getDiversifiedShapes() throws IOException {
		return
			Stream.concat(RectSupplier.createDiversifiedRectangle(), Stream.concat(ArcSupplier.createDiversifiedArc(),
				Stream.concat(DotSupplier.createDiversifiedDot(), Stream.concat(PlotSupplier.createDiversifiedPlot(),
				Stream.concat(GridSupplier.createDiversifiedGrid(), Stream.concat(AxesSupplier.createDiversifiedAxes(),
				Stream.concat(TextSupplier.createDiversifiedText(),
					Stream.of((Shape) EllSupplier.createEllipse(), createBezierCurve(), CircleSupplier.createCircle(), createPolyline(), createPolygon(), createSquare(),
			ShapeFactory.INST.createFreeHand(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
				ShapeFactory.INST.createPoint(251d, 33d), ShapeFactory.INST.createPoint(251d, 35d), ShapeFactory.INST.createPoint(151d, 233d))),
			createRhombus(), createTriangle(),
			ParameteriseShapeData.INST.setPictureData1(createPicture()))))))))).
			map(sh -> Arrays.asList(ParameteriseShapeData.INST.setShapeData1(sh.duplicate()),
				ParameteriseShapeData.INST.setShapeData2(sh.duplicate()),
				ParameteriseShapeData.INST.setShapeData3(sh.duplicate()),
				ParameteriseShapeData.INST.setShapeData4(sh.duplicate()))).flatMap(s -> s.stream());
	}

	public static Stream<Shape> getStdShapesStream() {
		return Stream.of(EllSupplier.createEllipse(), ArcSupplier.createArc(), GridSupplier.createGrid(), CircleSupplier.createCircle(),
			RectSupplier.createRectangle(), createBezierCurve(), TextSupplier.createText(), DotSupplier.createDot(), createPolyline(),
			createPolygon(), AxesSupplier.createAxes(), createSquare(),
			ShapeFactory.INST.createFreeHand(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
				ShapeFactory.INST.createPoint(251d, 33d), ShapeFactory.INST.createPoint(251d, 35d), ShapeFactory.INST.createPoint(151d, 233d))),
			PlotSupplier.createPlot(), createRhombus(), createTriangle(), createPicture());
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
