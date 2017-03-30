package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;

public class ViewTriangle extends ViewPathShape<ITriangle> {
	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewTriangle(final ITriangle sh) {
		super(sh);
		setupPath(border);
		setupPath(shadow);
	}

	private void setupPath(final Path path) {
		MoveTo moveTo = ViewFactory.INSTANCE.createMoveTo(0d, 0d);

		moveTo.xProperty().bind(Bindings.createDoubleBinding(() -> model.getPtAt(0).getX() + model.getWidth() / 2d,
			model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		moveTo.yProperty().bind(model.getPtAt(0).yProperty());
		path.getElements().add(moveTo);

		LineTo lineTo = ViewFactory.INSTANCE.createLineTo(0d, 0d);
		lineTo.xProperty().bind(model.getPtAt(2).xProperty());
		lineTo.yProperty().bind(model.getPtAt(2).yProperty());
		path.getElements().add(lineTo);

		lineTo = ViewFactory.INSTANCE.createLineTo(0d, 0d);
		lineTo.xProperty().bind(model.getPtAt(3).xProperty());
		lineTo.yProperty().bind(model.getPtAt(3).yProperty());
		path.getElements().add(lineTo);

		path.getElements().add(ViewFactory.INSTANCE.createClosePath());
	}

	@Override
	public void flush() {
		border.getElements().forEach(elt -> ViewFactory.INSTANCE.flushPathElement(elt));
		shadow.getElements().forEach(elt -> ViewFactory.INSTANCE.flushPathElement(elt));
		super.flush();
	}
}
