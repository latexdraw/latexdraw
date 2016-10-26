package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;

public class ViewTriangle extends ViewPathShape<ITriangle> {
	final MoveTo moveTo;
	final LineTo lineTo1;
	final LineTo lineTo2;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	public ViewTriangle(final ITriangle sh) {
		super(sh);
		moveTo = new MoveTo();
		final IPoint pt0 = sh.getPtAt(0);
		final IPoint pt1 = sh.getPtAt(1);
		final IPoint pt2 = sh.getPtAt(2);
		final IPoint pt3 = sh.getPtAt(3);
		double width = sh.getWidth() / 2.0;
		
		moveTo.xProperty().bind(Bindings.createDoubleBinding(() -> pt0.getX() + width, pt0.xProperty(), pt1.xProperty()));
		moveTo.yProperty().bind(pt0.yProperty());
		border.getElements().add(moveTo);

		lineTo1 = new LineTo();
		lineTo1.xProperty().bind(pt2.xProperty());
		lineTo1.yProperty().bind(pt2.yProperty());
		border.getElements().add(lineTo1);

		lineTo2 = new LineTo();
		lineTo2.xProperty().bind(pt3.xProperty());
		lineTo2.yProperty().bind(pt3.yProperty());
		border.getElements().add(lineTo2);

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
		super.flush();
	}
}
