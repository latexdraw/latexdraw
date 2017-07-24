package test.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class StringSupplier extends ParameterSupplier {
	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) throws Throwable {
		final StringData data = sig.getAnnotation(StringData.class);
		Stream<String> stream = Arrays.stream(data.vals());

		if(data.withNull()) {
			stream = Stream.concat(stream, Stream.of((String)null));
		}

		return stream.map(i -> PotentialAssignment.forValue("", i)).collect(Collectors.toList());
	}
}
