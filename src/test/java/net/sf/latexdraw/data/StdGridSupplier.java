package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class StdGridSupplier extends ParameterSupplier {
	public static Stream<IStandardGrid> createStdGrids() {
		return Stream.of(GridSupplier.createGrid(), AxesSupplier.createAxes());
	}

	public static Stream<IStandardGrid> createDiversifiedStdGrids() {
		return Stream.concat(GridSupplier.createDiversifiedGrid(), AxesSupplier.createDiversifiedAxes());
	}

	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final StdGridData shapeData = sig.getAnnotation(StdGridData.class);
		final Stream<IStandardGrid> instances;

		if(shapeData.withParamVariants()) {
			instances = createDiversifiedStdGrids();
		}else {
			instances = createStdGrids();
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
