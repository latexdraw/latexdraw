package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;

public class ViewRhombus extends ViewPathShape<IRhombus> {
	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewRhombus(final IRhombus sh) {
		super(sh);
		setupPath(border);
		setupPath(shadow);
	}


	private void setupPath(final Path path) {
		final MoveTo moveTo = ViewFactory.INSTANCE.createMoveTo(0d, 0d);
		moveTo.xProperty().bind(Bindings.createDoubleBinding(() -> model.getPtAt(0).getX() + model.getWidth() / 2d,
			model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		moveTo.yProperty().bind(model.getPtAt(0).yProperty());
		path.getElements().add(moveTo);

		LineTo lineTo = ViewFactory.INSTANCE.createLineTo(0d, 0d);
		lineTo.xProperty().bind(model.getPtAt(2).xProperty());
		lineTo.yProperty().bind(Bindings.createDoubleBinding(() -> model.getPtAt(1).getY() + model.getHeight() / 2d,
			model.getPtAt(1).yProperty(), model.getPtAt(2).yProperty()));
		path.getElements().add(lineTo);

		LineTo lineTo2 = ViewFactory.INSTANCE.createLineTo(0d, 0d);
		lineTo2.xProperty().bind(Bindings.createDoubleBinding(() -> model.getPtAt(0).getX() + model.getWidth() / 2d,
			model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		lineTo2.yProperty().bind(model.getPtAt(2).yProperty());
		path.getElements().add(lineTo2);

		LineTo lineTo3 = ViewFactory.INSTANCE.createLineTo(0d, 0d);
		lineTo3.xProperty().bind(model.getPtAt(0).xProperty());
		lineTo3.yProperty().bind(Bindings.createDoubleBinding(() -> model.getPtAt(0).getY() + model.getHeight() / 2d,
			model.getPtAt(0).yProperty(), model.getPtAt(2).yProperty()));
		path.getElements().add(lineTo3);

		path.getElements().add(ViewFactory.INSTANCE.createClosePath());
	}


	@Override
	public void flush() {
		border.getElements().forEach(elt -> ViewFactory.INSTANCE.flushPathElement(elt));
		shadow.getElements().forEach(elt -> ViewFactory.INSTANCE.flushPathElement(elt));
		super.flush();
	}
}
