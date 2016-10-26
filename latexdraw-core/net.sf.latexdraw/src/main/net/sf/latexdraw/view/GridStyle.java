package net.sf.latexdraw.view;

import java.util.Arrays;
import java.util.Optional;

import net.sf.latexdraw.lang.LangTool;

/**
 * This enumeration contains the different style
 * of a magnetic grid.
 */
public enum GridStyle {
	/** A grid customised by users. */
	CUSTOMISED {@Override public String getLabel() {return LangTool.INSTANCE.getStringDialogFrame("Res.3");}},
	/** The standard grid. */
	STANDARD {@Override public String getLabel() {return LangTool.INSTANCE.getString18("PreferencesFrame.4");}},
	/** No grid. */
	NONE {@Override public String getLabel() { return "None";}}; //$NON-NLS-1$

	/**
	 * @return The label of the style.
	 * @since 3.0
	 */
	public abstract String getLabel();

	/**
	 * Searches the style which label matches the given name.
	 * @param name The name of the style to find.
	 * @return The found style or empty.
	 * @since 4.0
	 */
	public static Optional<GridStyle> getStylefromName(final String name) {
		 return Arrays.stream(values()).filter(v -> v.name().equals(name)).findFirst();
	}

	/**
	 * Searches the style which label matches the given label. Warning,
	 * labels change depending on the language.
	 * @param label The label of the style to find.
	 * @return The found style or empty.
	 * @since 4.0
	 */
	public static Optional<GridStyle> getStyleFromLabel(final String label) {
		return Arrays.stream(values()).filter(v -> v.getLabel().equals(label)).findFirst();
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
}
