package net.sf.latexdraw.util;

import java.util.Arrays;
import org.eclipse.jdt.annotation.NonNull;

/**
 * Defines the different possible units used by the rulers.
 */
public enum Unit {
	/** Centimetre */
	CM {
		@Override
		public String getLabel() {
			return LangTool.INSTANCE.getBundle().getString("XScale.cm"); //$NON-NLS-1$
		}
	},
	/** Inch */
	INCH {
		@Override
		public String getLabel() {
			return LangTool.INSTANCE.getBundle().getString("XScale.inch"); //$NON-NLS-1$
		}
	};

	/**
	 * @return The label of the unit.
	 * @since 3.0
	 */
	public abstract String getLabel();

	/**
	 * @param label The label to test.
	 * @return The unit corresponding to the given label, or CM.
	 * @since 3.0
	 */
	public static @NonNull Unit getUnit(final String label) {
		return Arrays.stream(values()).filter(it -> it.getLabel().equals(label)).findFirst().orElse(CM);
	}
}
