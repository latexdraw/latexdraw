package net.sf.latexdraw.models.interfaces.shape;

import org.eclipse.jdt.annotation.NonNull;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

public interface ISingleShape extends IShape {
	/**
	 * @return The property of the thickness.
	 */
	@NonNull DoubleProperty thicknessProperty();
	
	/**
	 * @return The property of the line style.
	 */
	@NonNull ObjectProperty<LineStyle> linestyleProperty();
	
	/**
	 * @return The property of the border position.
	 */
	@NonNull ObjectProperty<BorderPos> borderPosProperty();
	
	/**
	 * @return The property of the line colour.
	 */
	@NonNull ObjectProperty<Color> lineColourProperty();
	
	/**
	 * @return The property of the filling.
	 */
	@NonNull ObjectProperty<FillingStyle> fillingProperty();
}
