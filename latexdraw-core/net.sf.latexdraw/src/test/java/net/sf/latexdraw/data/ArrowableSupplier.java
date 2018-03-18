package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class ArrowableSupplier extends ParameterSupplier {
	public static Stream<IArrowableSingleShape> createArrowableShapes() {
		return Stream.of(ShapeSupplier.createPolyline(), AxesSupplier.createAxes(), ShapeSupplier.createBezierCurve(), ArcSupplier.createArc());
	}

	public static Stream<IArrowableSingleShape> createDivsersifiedArrowableShapes() {
		return createArrowableShapes().map(sh -> Stream.of(ParameteriseShapeData.INST.setArrowableData1((IArrowableSingleShape) sh.duplicate()),
			ParameteriseShapeData.INST.setArrowableData2((IArrowableSingleShape) sh.duplicate()),
			ParameteriseShapeData.INST.setArrowableData3((IArrowableSingleShape) sh.duplicate()),
			ParameteriseShapeData.INST.setArrowableData4((IArrowableSingleShape) sh.duplicate()),
			ParameteriseShapeData.INST.setArrowableData5((IArrowableSingleShape) sh.duplicate()))).flatMap(s -> s);
	}


	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final ArrowableData shapeData = sig.getAnnotation(ArrowableData.class);
		final Stream<IArrowableSingleShape> instances;

		if(shapeData.withParamVariants()) {
			instances = createDivsersifiedArrowableShapes();
		}else {
			instances = createArrowableShapes();
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
