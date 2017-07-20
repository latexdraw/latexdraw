package test.util;

import com.google.common.collect.Streams;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public interface ValuesGenerator {
	static double[] posNegZeroDoubleValues() {
		return Streams.concat(DoubleStream.of(0d), IntStream.range(0, 20).mapToDouble(i -> {
			final Random random = new Random();
			return random.nextDouble() * 100d * (random.nextBoolean() ? 1d : -1d);
		})).toArray();
	}

	static float[] posNegZeroFloatValues() {
		final double[] doubleValues = posNegZeroDoubleValues();
		final float[] values = new float[doubleValues.length];
		for(int i = 0; i < doubleValues.length; i++) {
			values[i] = (float) doubleValues[i];
		}
		return values;
	}
}
