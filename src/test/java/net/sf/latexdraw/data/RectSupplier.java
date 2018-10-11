package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class RectSupplier extends ParameterSupplier {
	public static IRectangle createRectangle() {
		return ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(51d, 73d), 354d, 234d);
	}

	public static Stream<IRectangle> createDiversifiedRectangle() {
		return Stream.of(createRectangle(), ParameteriseShapeData.INST.setRectangleData1(createRectangle()));
	}


	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final RectData shapeData = sig.getAnnotation(RectData.class);
		final Stream<IRectangle> instances;

		if(shapeData.withParamVariants()) {
			instances = createDiversifiedRectangle();
		}else {
			instances = Stream.of(createRectangle());
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
