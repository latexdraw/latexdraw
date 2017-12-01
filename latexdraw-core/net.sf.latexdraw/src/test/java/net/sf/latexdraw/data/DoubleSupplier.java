package net.sf.latexdraw.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class DoubleSupplier extends ParameterSupplier {
	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) {
		final DoubleData doubledata = sig.getAnnotation(DoubleData.class);
		DoubleStream stream = Arrays.stream(doubledata.vals());

		if(doubledata.angle()) {
			stream = DoubleStream.of(0d, Math.PI / 2d, Math.PI, 3d * Math.PI / 2d, 2d * Math.PI, -Math.PI / 2d, -Math.PI,
				-3d * Math.PI / 2d, -2d * Math.PI, 1.234, -1.234, 3d * Math.PI, -3d * Math.PI);
		}

		if(doubledata.bads()) {
			stream = DoubleStream.concat(stream, DoubleStream.of(Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
		}

		return stream.mapToObj(i -> PotentialAssignment.forValue("", i)).collect(Collectors.toList());
	}
}
