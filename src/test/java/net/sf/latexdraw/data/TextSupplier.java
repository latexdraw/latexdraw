package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IText;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class TextSupplier extends ParameterSupplier {
	public static IText createText() {
		return ShapeFactory.INST.createText(ShapeFactory.INST.createPoint(51d, 73d), "$foo");
	}

	public static Stream<IText> createDiversifiedText() {
		return Stream.of(createText(), ParameteriseShapeData.INST.setTextData1(createText()));
	}


	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final TextData shapeData = sig.getAnnotation(TextData.class);
		final Stream<IText> instances;

		if(shapeData.withParamVariants()) {
			instances = createDiversifiedText();
		}else {
			instances = Stream.of(createText());
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
