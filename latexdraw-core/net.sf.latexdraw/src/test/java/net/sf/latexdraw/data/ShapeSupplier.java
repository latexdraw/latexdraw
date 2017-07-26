package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.ShapeFactory;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class ShapeSupplier extends ParameterSupplier {
	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) throws Throwable {
		return Stream.of(ShapeFactory.INST.createEllipse(),
			ShapeFactory.INST.createCircleArc(),
			ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()),
			ShapeFactory.INST.createCircle(),
			ShapeFactory.INST.createRectangle(),
			ShapeFactory.INST.createBezierCurve(),
			ShapeFactory.INST.createText(),
			ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()),
			ShapeFactory.INST.createPolyline(),
			ShapeFactory.INST.createPolygon(),
			ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()),
			ShapeFactory.INST.createSquare(),
			ShapeFactory.INST.createFreeHand(),
			ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 0d, 10d, "x", false),
			ShapeFactory.INST.createRhombus(),
			ShapeFactory.INST.createTriangle(),
			ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint())).
			map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
