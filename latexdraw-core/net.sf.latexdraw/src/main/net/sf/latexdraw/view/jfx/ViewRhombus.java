package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;

public class ViewRhombus extends ViewPathShape<IRhombus> {
	final MoveTo moveTo;
	final LineTo lineTo1;
	final LineTo lineTo2;
	final LineTo lineTo3;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	public ViewRhombus(final IRhombus sh) {
		super(sh);
		moveTo = new MoveTo();
		final IPoint pt0 = sh.getPtAt(0);
		final IPoint pt1 = sh.getPtAt(1);
		final IPoint pt2 = sh.getPtAt(2);
		final IPoint pt3 = sh.getPtAt(2);
		double width = sh.getWidth() / 2.0;
		double height = sh.getHeight() / 2.0;

		moveTo.xProperty().bind(Bindings.createDoubleBinding(() -> pt0.getX() + width, pt0.xProperty(), pt1.xProperty()));
		moveTo.yProperty().bind(pt0.yProperty());
		border.getElements().add(moveTo);

		lineTo1 = new LineTo();
		lineTo1.xProperty().bind(pt2.xProperty());
		lineTo1.yProperty().bind(Bindings.createDoubleBinding(() -> pt1.getY() + height, pt1.yProperty(), pt2.yProperty()));
		border.getElements().add(lineTo1);

		lineTo2 = new LineTo();
		lineTo2.xProperty().bind(Bindings.createDoubleBinding(() -> pt0.getX() + width, pt0.xProperty(), pt1.xProperty()));
		lineTo2.yProperty().bind(pt2.yProperty());
		border.getElements().add(lineTo2);

		lineTo3 = new LineTo();
		lineTo3.xProperty().bind(pt0.xProperty());
		lineTo3.yProperty().bind(Bindings.createDoubleBinding(() -> pt0.getY() + height, pt0.yProperty(), pt3.yProperty()));
		border.getElements().add(lineTo3);

		border.getElements().add(new ClosePath());
	}

	@Override
	public void flush() {
		moveTo.xProperty().unbind();
		moveTo.yProperty().unbind();
		lineTo1.xProperty().unbind();
		lineTo1.yProperty().unbind();
		lineTo2.xProperty().unbind();
		lineTo2.yProperty().unbind();
		lineTo3.xProperty().unbind();
		lineTo3.yProperty().unbind();
		super.flush();
	}
}
