package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Axes;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class AxesSupplier extends ParameterSupplier {
	public static Axes createAxes() {
		return ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint(133, 146));
	}

	public static Stream<Axes> createDiversifiedAxes() {
		return Stream.of(createAxes(), ParameteriseShapeData.INST.setAxesData1(createAxes()), ParameteriseShapeData.INST.setAxesData2(createAxes()));
	}

	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final AxesData shapeData = sig.getAnnotation(AxesData.class);
		final Stream<Axes> instances;

		if(shapeData.withParamVariants()) {
			instances = createDiversifiedAxes();
		}else {
			instances = Stream.of(createAxes());
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
