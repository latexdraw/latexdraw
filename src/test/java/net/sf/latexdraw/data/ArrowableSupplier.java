package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.model.api.shape.ArrowableSingleShape;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class ArrowableSupplier extends ParameterSupplier {
	public static Stream<ArrowableSingleShape> createArrowableShapes() {
		return Stream.of(ShapeSupplier.createPolyline(), ShapeSupplier.createAxes(), ShapeSupplier.createBezierCurve(), ArcSupplier.createArc());
	}

	public static Stream<ArrowableSingleShape> createDiversifiedArrowableShapes() {
		return createArrowableShapes().map(sh -> Stream.of(ParameteriseShapeData.INST.setArrowableData1((ArrowableSingleShape) sh.duplicate()),
			ParameteriseShapeData.INST.setArrowableData2((ArrowableSingleShape) sh.duplicate()),
			ParameteriseShapeData.INST.setArrowableData3((ArrowableSingleShape) sh.duplicate()),
			ParameteriseShapeData.INST.setArrowableData4((ArrowableSingleShape) sh.duplicate()),
			ParameteriseShapeData.INST.setArrowableData5((ArrowableSingleShape) sh.duplicate()))).flatMap(s -> s);
	}


	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final ArrowableData shapeData = sig.getAnnotation(ArrowableData.class);
		final Stream<ArrowableSingleShape> instances;

		if(shapeData.withParamVariants()) {
			instances = createDiversifiedArrowableShapes();
		}else {
			instances = createArrowableShapes();
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
