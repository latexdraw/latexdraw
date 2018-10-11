package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class LineArcSupplier extends ParameterSupplier {
	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final LineArcData shapeData = sig.getAnnotation(LineArcData.class);
		final Stream<ILineArcProp> instances;

		if(shapeData.withParamVariants()) {
			instances =Stream.concat(RectSupplier.createDiversifiedRectangle(), ShapeSupplier.createDiversifiedSquare());
		}else {
			instances = Stream.of(RectSupplier.createRectangle(), ShapeSupplier.createSquare());
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
