package net.sf.latexdraw.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.ShapeFactory;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class ShapeSupplier extends ParameterSupplier {
	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		return Stream.of(
			ShapeFactory.INST.createEllipse(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(101d, 201d)),
			ShapeFactory.INST.createCircleArc(ShapeFactory.INST.createPoint(51d, 73d), 105d),
			ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint(130d, 284d)),
			ShapeFactory.INST.createCircle(ShapeFactory.INST.createPoint(51d, 73d), 43d),
			ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(51d, 73d), 87d, 134d),
			ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
				ShapeFactory.INST.createPoint(251d, 33d))),
			ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(51d, 73d), "$foo"),
			ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint(120, 234)),
			ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
				ShapeFactory.INST.createPoint(251d, 33d))),
			ShapeFactory.INST.createPolygon(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
				ShapeFactory.INST.createPoint(251d, 33d))),
			ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint(133, 146)),
			ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(13d, 84d), 33),
			ShapeFactory.INST.createFreeHand(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d),
				ShapeFactory.INST.createPoint(251d, 33d), ShapeFactory.INST.createPoint(251d, 35d), ShapeFactory.INST.createPoint(151d, 233d))),
			ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(23, 300), 1d, 10d, "x log", false),
			ShapeFactory.INST.createRhombus(ShapeFactory.INST.createPoint(251d, 33d), 12, 76),
			ShapeFactory.INST.createTriangle(ShapeFactory.INST.createPoint(251d, 33d), 76, 12),
			ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint(76, 45))).
			map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
