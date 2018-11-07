package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.model.api.shape.StandardGrid;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class StdGridSupplier extends ParameterSupplier {
	public static Stream<StandardGrid> createStdGrids() {
		return Stream.of(ShapeSupplier.createGrid(), ShapeSupplier.createAxes());
	}

	public static Stream<StandardGrid> createDiversifiedStdGrids() {
		return Stream.concat(ShapeSupplier.createDiversifiedGrid(), ShapeSupplier.createDiversifiedAxes());
	}

	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final StdGridData shapeData = sig.getAnnotation(StdGridData.class);
		final Stream<StandardGrid> instances;

		if(shapeData.withParamVariants()) {
			instances = createDiversifiedStdGrids();
		}else {
			instances = createStdGrids();
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
