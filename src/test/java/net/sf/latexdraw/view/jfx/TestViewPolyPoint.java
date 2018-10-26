package net.sf.latexdraw.view.jfx;

import java.util.List;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TestViewPolyPoint<R extends ViewPathShape<S>, S extends IModifiablePointsShape> extends TestViewBorderedShape<R, S, Path> {
	protected List<PathElement> before;

	@BeforeEach
	void setUpPolyPt() {
		before = duplicatePath(border.getElements());
	}

	@Override
	@Test
	void testShadowPositionSameThanBorder() {
		assertEquals(border.getElements(), view.getShadow().orElseThrow().getElements());
	}

	@Override
	@Test
	public void testOnTranslateX() {
		model.translate(11d, 0d);
		assertPathSameButNotEqual(before, border.getElements());
	}

	@Override
	@Test
	public void testOnTranslateY() {
		model.translate(0d, 11d);
		assertPathSameButNotEqual(before, border.getElements());
	}
}
