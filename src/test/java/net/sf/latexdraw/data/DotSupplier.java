package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Dot;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class DotSupplier extends ParameterSupplier {
	public static Dot createDot() {
		return ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint(120, 234));
	}

	public static Stream<Dot> createDiversifiedDot() {
		return Stream.of(createDot(), ParameteriseShapeData.INST.setDotData1(createDot()),
			ParameteriseShapeData.INST.setDotData2(createDot()), ParameteriseShapeData.INST.setDotData3(createDot()));
	}


	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final DotData shapeData = sig.getAnnotation(DotData.class);
		final Stream<Dot> instances;

		if(shapeData.withParamVariants()) {
			instances = createDiversifiedDot();
		}else {
			instances = Stream.of(createDot());
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
