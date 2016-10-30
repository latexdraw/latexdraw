package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
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

		moveTo.xProperty().bind(Bindings.createDoubleBinding(() -> sh.getPtAt(0).getX() + sh.getWidth() / 2.0, sh.getPtAt(0).xProperty(), sh.getPtAt(1).xProperty()));
		moveTo.yProperty().bind(sh.getPtAt(0).yProperty());
		border.getElements().add(moveTo);

		lineTo1 = new LineTo();
		lineTo1.xProperty().bind(sh.getPtAt(2).xProperty());
		lineTo1.yProperty().bind(Bindings.createDoubleBinding(() -> sh.getPtAt(1).getY() + sh.getHeight() / 2.0, sh.getPtAt(1).yProperty(), sh.getPtAt(2).yProperty()));
		border.getElements().add(lineTo1);

		lineTo2 = new LineTo();
		lineTo2.xProperty().bind(Bindings.createDoubleBinding(() -> sh.getPtAt(0).getX() + sh.getWidth() / 2.0, sh.getPtAt(0).xProperty(), sh.getPtAt(1).xProperty()));
		lineTo2.yProperty().bind(sh.getPtAt(2).yProperty());
		border.getElements().add(lineTo2);

		lineTo3 = new LineTo();
		lineTo3.xProperty().bind(sh.getPtAt(0).xProperty());
		lineTo3.yProperty().bind(Bindings.createDoubleBinding(() -> sh.getPtAt(0).getY() + sh.getHeight() / 2.0, sh.getPtAt(0).yProperty(), sh.getPtAt(2).yProperty()));
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
