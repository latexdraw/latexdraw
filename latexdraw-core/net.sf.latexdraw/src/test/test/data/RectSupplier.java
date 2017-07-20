package test.data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.sf.latexdraw.models.ShapeFactory;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class RectSupplier extends ParameterSupplier {
	@Override
	public List<PotentialAssignment> getValueSources(final ParameterSignature sig) throws Throwable {
		return Stream.of(ShapeFactory.INST.createRectangle()).map(r -> PotentialAssignment.forValue("", r)).collect(Collectors.toList());
	}
}
