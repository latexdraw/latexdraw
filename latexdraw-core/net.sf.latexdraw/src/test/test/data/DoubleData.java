package test.data;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.experimental.theories.ParametersSuppliedBy;

import static java.lang.annotation.ElementType.PARAMETER;

@Retention(RetentionPolicy.RUNTIME)
@ParametersSuppliedBy(DoubleSupplier.class)
@Target(PARAMETER)
public @interface DoubleData {
	double[] vals() default {-0.00001, -1.34, -83.12, 0d, 0.00001, 1.34, 83.12};
	boolean bads() default false;
}
