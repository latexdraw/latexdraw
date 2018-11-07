package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.model.api.property.LineArcProp;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class LineArcSupplier extends ParameterSupplier {
	public static Stream<LineArcProp> lineArcDiversified() {
		return Stream.concat(ShapeSupplier.createDiversifiedRectangle(), ShapeSupplier.createDiversifiedSquare());
	}

	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final LineArcData shapeData = sig.getAnnotation(LineArcData.class);
		final Stream<LineArcProp> instances;

		if(shapeData.withParamVariants()) {
			instances = lineArcDiversified();
		}else {
			instances = Stream.of(ShapeSupplier.createRectangle(), ShapeSupplier.createSquare());
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
