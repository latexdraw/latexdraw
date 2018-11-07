package net.sf.latexdraw.data;

import com.codepoetics.protonpack.StreamUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.jupiter.params.provider.Arguments;

public class DoubleSupplier extends ParameterSupplier {
	public static DoubleStream badDoubles() {
		return DoubleStream.of(Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	public static DoubleStream okDoubles() {
		return DoubleStream.of(-0.00001, -1.34, -83.12, 0d, 0.00001, 1.34, 83.12);
	}

	public static Stream<Arguments> twoOkDoubles() {
		return StreamUtils.zip(okDoubles().boxed(), okDoubles().boxed(), (a, b) -> Arguments.of(a, b));
	}

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
