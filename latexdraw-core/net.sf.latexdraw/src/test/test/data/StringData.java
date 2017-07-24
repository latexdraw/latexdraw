package test.data;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.experimental.theories.ParametersSuppliedBy;

import static java.lang.annotation.ElementType.PARAMETER;

@Retention(RetentionPolicy.RUNTIME)
@ParametersSuppliedBy(StringSupplier.class)
@Target(PARAMETER)
public @interface StringData {
	String[] vals() default {"foo", "b", "foo bar bar bar foo bar"};

	boolean withNull() default false;
}
