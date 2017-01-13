package net.sf.latexdraw.models.interfaces.shape;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import org.eclipse.jdt.annotation.NonNull;

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

	@NonNull DoubleProperty dashSepWhiteProperty();

	@NonNull DoubleProperty dashSepBlackProperty();

	@NonNull DoubleProperty dotSepProperty();

	@NonNull BooleanProperty dbleBordProperty();

	@NonNull DoubleProperty dbleBordSepProperty();

	@NonNull ObjectProperty<Color> dbleBordColProperty();

	@NonNull ObjectProperty<Color> gradColStartProperty();

	@NonNull ObjectProperty<Color> gradColEndProperty();

	@NonNull ObjectProperty<Color> fillingColProperty();

	@NonNull DoubleProperty gradAngleProperty();

	@NonNull DoubleProperty gradMidPtProperty();

	@NonNull BooleanProperty shadowProperty();

	@NonNull ObjectProperty<Color> shadowColProperty();

	@NonNull DoubleProperty shadowAngleProperty();

	@NonNull DoubleProperty shadowSizeProperty();
}
