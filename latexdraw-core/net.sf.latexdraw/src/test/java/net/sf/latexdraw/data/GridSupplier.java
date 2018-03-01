package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class GridSupplier extends ParameterSupplier {
	public static IGrid createGrid() {
		return ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint(130d, 284d));
	}

	public static Stream<IGrid> createDiversifiedGrid() {
		return Stream.of(createGrid(), ParameteriseShapeData.INST.setGridData1(createGrid()), ParameteriseShapeData.INST.setGridData2(createGrid()),
			ParameteriseShapeData.INST.setGridData3(createGrid()));
	}

	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final GridData shapeData = sig.getAnnotation(GridData.class);
		final Stream<IGrid> instances;

		if(shapeData.withParamVariants()) {
			instances = createDiversifiedGrid();
		}else {
			instances = Stream.of(createGrid());
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
