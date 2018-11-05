package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Ellipse;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class EllSupplier extends ParameterSupplier {
	public static Ellipse createEllipse() {
		return ShapeFactory.INST.createEllipse(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(801d, 801d));
	}

	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		return Stream.of(ShapeFactory.INST.createEllipse()).map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
