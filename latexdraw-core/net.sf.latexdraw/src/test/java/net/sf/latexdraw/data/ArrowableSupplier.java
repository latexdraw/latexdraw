package net.sf.latexdraw.data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.ShapeFactory;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class ArrowableSupplier extends ParameterSupplier {
	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		return Stream.of(ShapeFactory.INST.createPolyline(Collections.emptyList()),
			ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()),
			ShapeFactory.INST.createBezierCurve(Collections.emptyList()),
			ShapeFactory.INST.createCircleArc()).map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
