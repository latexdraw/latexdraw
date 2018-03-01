package net.sf.latexdraw.data;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.experimental.theories.ParametersSuppliedBy;

import static java.lang.annotation.ElementType.PARAMETER;

@Retention(RetentionPolicy.RUNTIME)
@ParametersSuppliedBy(GridSupplier.class)
@Target(PARAMETER)
public @interface GridData {
	boolean withParamVariants() default false;
}
