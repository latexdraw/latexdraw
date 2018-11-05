package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Arc;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class ArcSupplier extends ParameterSupplier {
	public static Arc createArc() {
		return ShapeFactory.INST.createCircleArc(ShapeFactory.INST.createPoint(51d, 73d), 205d);
	}

	public static Stream<Arc> createDiversifiedArc() {
		return Stream.of(createArc(), ParameteriseShapeData.INST.setArcData1(createArc()), ParameteriseShapeData.INST.setArcData2(createArc()),
			ParameteriseShapeData.INST.setArcData3(createArc()));
	}

	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final ArcData shapeData = sig.getAnnotation(ArcData.class);
		final Stream<Arc> instances;

		if(shapeData.withParamVariants()) {
			instances = createDiversifiedArc();
		}else {
			instances = Stream.of(createArc());
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
