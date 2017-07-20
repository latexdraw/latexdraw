package test.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class DoubleSupplier extends ParameterSupplier {
	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) throws Throwable {
		DoubleData doubledata = sig.getAnnotation(DoubleData.class);
		double[] vals = doubledata.vals();

		if(vals.length == 0) {
			vals = ValuesGenerator.doubleValues;
		}

		DoubleStream stream = Arrays.stream(vals);

		if(doubledata.bads()) {
			stream = DoubleStream.concat(stream, DoubleStream.of(Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
		}

		return stream.mapToObj(i -> PotentialAssignment.forValue("", i)).collect(Collectors.toList());
	}
}
