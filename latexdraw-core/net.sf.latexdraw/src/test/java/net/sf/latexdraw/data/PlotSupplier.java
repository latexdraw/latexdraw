package net.sf.latexdraw.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class PlotSupplier extends ParameterSupplier {
	public static IPlot createPlot() {
		return ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(23, 300), 1d, 10d, "x", false);
	}

	public static Stream<IPlot> createDiversifiedPlot() {
		return Stream.of(createPlot(), ParameteriseShapeData.INST.setPlotData1(createPlot()), ParameteriseShapeData.INST.setPlotData2(createPlot()),
			ParameteriseShapeData.INST.setPlotData3(createPlot()), ParameteriseShapeData.INST.setPlotData4(createPlot()));
	}

	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final PlotData shapeData = sig.getAnnotation(PlotData.class);
		final Stream<IPlot> instances;

		if(shapeData.withParamVariants()) {
			instances = createDiversifiedPlot();
		}else {
			instances = Stream.of(createPlot());
		}

		return instances.map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
